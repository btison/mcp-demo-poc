package org.globex.ai.graph;

import io.smallrye.common.annotation.Identifier;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import org.bsc.langgraph4j.*;
import org.bsc.langgraph4j.action.AsyncNodeAction;
import org.bsc.langgraph4j.checkpoint.PostgresSaver;
import org.globex.ai.agent.order.OrderAIService;
import org.globex.ai.persistence.PostgresqlConfig;

import java.sql.SQLException;

import static org.bsc.langgraph4j.action.AsyncNodeAction.node_async;

@ApplicationScoped
public class OrderAgentGraphProducer {

    @Inject
    PostgresqlConfig postgresqlConfig;

    @Inject
    OrderAIService orderAIService;

    @Produces
    @Identifier("order-agent")
    public CompiledGraph<State> buildGraph() {
        try {
            return compiledGraph();
        } catch (GraphStateException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    CompiledGraph<State> compiledGraph() throws GraphStateException, SQLException {
        AsyncNodeAction<State> handleRequest = node_async(LlmNodeAction.get(input -> orderAIService.handleRequest(input), (value, state) -> {
            state.put("routing_target", "routing-agent");
            return state;
        }));

        StateGraph<State> graph = new StateGraph<>(State::new)
                .addNode("handle_request", handleRequest)
                .addEdge(GraphDefinition.START, "handle_request")
                .addEdge("handle_request", GraphDefinition.END);

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
                .releaseThread(true)
                .build();

        return graph.compile(compileConfig);
    }
}
