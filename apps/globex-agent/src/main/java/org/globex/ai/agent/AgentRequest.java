package org.globex.ai.agent;

public record AgentRequest(
        String request,
        String threadId,
        String checkpointId
) {
}
