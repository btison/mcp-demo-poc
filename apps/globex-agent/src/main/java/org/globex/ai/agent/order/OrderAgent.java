package org.globex.ai.agent.order;

import io.quarkus.arc.Unremovable;
import io.smallrye.common.annotation.Identifier;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bsc.langgraph4j.CompiledGraph;
import org.globex.ai.agent.Agent;
import org.globex.ai.agent.AgentRequest;
import org.globex.ai.agent.AgentResponse;
import org.globex.ai.agent.BaseAgent;
import org.globex.ai.graph.State;

@ApplicationScoped
@Identifier("order-agent")
@Unremovable
public class OrderAgent extends BaseAgent implements Agent {

    @Inject
    @Identifier("order-agent")
    CompiledGraph<State> graph;

    @Override
    public AgentResponse sendRequestToAgent(AgentRequest request) {
        return invokeAgent(request, graph);
    }
}
