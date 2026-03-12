package org.globex.ai.agent;

import io.quarkus.arc.Unremovable;
import io.smallrye.common.annotation.Identifier;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bsc.langgraph4j.CompiledGraph;
import org.globex.ai.graph.State;

@ApplicationScoped
@Identifier("routing-agent")
@Unremovable
public class RoutingAgent extends BaseAgent implements Agent {

    @Inject
    @Identifier("routing-agent")
    CompiledGraph<State> graph;

    @Override
    public AgentResponse sendRequestToAgent(AgentRequest request) {
        return invokeAgent(request, graph);
    }

}
