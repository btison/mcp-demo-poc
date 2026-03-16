package org.globex.ai.graph;

import io.smallrye.common.annotation.Identifier;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import org.bsc.langgraph4j.*;
import org.bsc.langgraph4j.action.AsyncNodeAction;
import org.bsc.langgraph4j.checkpoint.PostgresSaver;
import org.globex.ai.agent.complaint.LookupOrderHistoryAIService;
import org.globex.ai.persistence.PostgresqlConfig;

import java.sql.SQLException;
import java.util.Map;

@ApplicationScoped
public class ComplaintAgentGraphProducer {

    @Inject
    PostgresqlConfig postgresqlConfig;

    @Inject
    LookupOrderHistoryAIService lookupOrderHistoryAIService;

    @Produces
    @Identifier("complaint-agent")
    public CompiledGraph<State> buildGraph() {
        try {
            return compiledGraph();
        } catch (GraphStateException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    CompiledGraph<State> compiledGraph() throws GraphStateException, SQLException {
        AsyncNodeAction<State> lookupOrderHistory = AsyncNodeAction.node_async(LlmNodeAction.get(s -> lookupOrderHistoryAIService.handleGetOrderHistory(s)));
        AsyncNodeAction<State> waitForUserInput =AsyncNodeAction.node_async(state -> Map.of());

        StateGraph<State> graph = new StateGraph<>(State::new)
                .addNode("lookup_order_history", lookupOrderHistory)
                .addNode("wait_for_input", waitForUserInput)
                .addEdge(GraphDefinition.START, "lookup_order_history")
                .addEdge("lookup_order_history", "wait_for_input")
                .addEdge("wait_for_input", GraphDefinition.END);

        PostgresSaver saver = PostgresSaver.builder()
                .host(postgresqlConfig.host())
                .port(postgresqlConfig.port())
                .user(postgresqlConfig.username())
                .password(postgresqlConfig.password())
                .database(postgresqlConfig.database())
                .stateSerializer(graph.getStateSerializer())
                .createTables(false)
                .dropTablesFirst(false)
                .build();

        CompileConfig compileConfig = CompileConfig.builder()
                .checkpointSaver(saver)
                .interruptAfter("wait_for_input")
                .releaseThread(true)
                .build();

        return graph.compile(compileConfig);

    }

}
