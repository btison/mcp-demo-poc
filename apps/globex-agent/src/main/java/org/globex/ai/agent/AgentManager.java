package org.globex.ai.agent;

import io.smallrye.common.annotation.Identifier;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class AgentManager {

    @Inject @Identifier("routing-agent")
    Agent routingAgent;

    public static final String ROUTING_AGENT = "routing-agent";

    public Agent getAgent(String agentName) {
        return routingAgent;
    }

}
