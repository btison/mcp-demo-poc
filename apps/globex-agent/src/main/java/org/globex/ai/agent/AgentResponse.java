package org.globex.ai.agent;

public record AgentResponse(
        String response,
        boolean requiresRouting,
        String routingTarget,
        String checkpointId
) {
}
