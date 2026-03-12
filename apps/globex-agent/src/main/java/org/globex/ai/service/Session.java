package org.globex.ai.service;

import org.globex.ai.agent.Agent;

public class Session {

    private String sessionId;

    private String userId;

    private String agentName;

    private Agent agent;

    private String threadId;

    private String checkpointId;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public String getThreadId() {
        return threadId;
    }

    public void setThreadId(String threadId) {
        this.threadId = threadId;
    }

    public String getCheckpointId() {
        return checkpointId;
    }

    public void setCheckpointId(String checkpointId) {
        this.checkpointId = checkpointId;
    }
}
