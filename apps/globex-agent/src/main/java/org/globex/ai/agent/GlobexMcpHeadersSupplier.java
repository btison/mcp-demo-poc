package org.globex.ai.agent;

import dev.langchain4j.mcp.client.McpCallContext;
import dev.langchain4j.mcp.client.McpHeadersSupplier;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.control.ActivateRequestContext;
import jakarta.inject.Inject;
import org.globex.ai.service.AuthoritativeUserIdHolder;

import java.util.Map;

@ApplicationScoped
public class GlobexMcpHeadersSupplier implements McpHeadersSupplier {

    @Inject
    AuthoritativeUserIdHolder authorativeUserIdHolder;

    @Override
    @ActivateRequestContext
    public Map<String, String> apply(McpCallContext mcpCallContext) {
        String userId;
        if (authorativeUserIdHolder.getUserId() == null) {
            userId = "system";
        } else {
            userId = authorativeUserIdHolder.getUserId();
        }
        return Map.of("X-User-Id", userId);
    }
}
