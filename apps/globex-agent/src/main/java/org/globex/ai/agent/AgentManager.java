package org.globex.ai.agent;

import io.quarkus.arc.Arc;
import io.quarkus.arc.ArcContainer;
import io.quarkus.arc.InjectableInstance;
import io.quarkus.logging.Log;
import io.smallrye.common.annotation.Identifier;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.UnsatisfiedResolutionException;

@ApplicationScoped
public class AgentManager {

    public static final String ROUTING_AGENT = "routing-agent";

    public Agent getAgent(String agentName) {
        ArcContainer container = Arc.container();
        InjectableInstance<Agent> agentInstanceHandle = container.select(Agent.class, Identifier.Literal.of(agentName));
        if (agentInstanceHandle != null && agentInstanceHandle.isResolvable()) {
            return agentInstanceHandle.get();
        } else {
            Log.warnf("Agent with qualifier %s not found", agentName);
            throw new UnsatisfiedResolutionException("Agent with qualifier " + agentName + " not found");
        }
    }

}
