package org.globex.ai.agent;

import dev.langchain4j.mcp.McpToolProvider;
import dev.langchain4j.mcp.client.McpClient;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.AiServices;
import io.quarkiverse.langchain4j.ModelName;
import io.quarkiverse.langchain4j.mcp.runtime.McpClientName;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;

@ApplicationScoped
public class ComplaintAIServiceProducer {

    @Inject
    @ModelName("complaint")
    ChatModel chatModel;

    @Inject
    @McpClientName("globex-store")
    McpClient client;

    @Inject
    AIServiceConfigs aiServiceConfigs;

    @Produces
    public LookupOrderHistoryAIService provideLookupOrderHistoryAIService() {
        McpToolProvider toolProvider = McpToolProvider.builder()
                .mcpClients(client)
                .filterToolNames("get_order_history")
                .build();

        return AiServices.builder(LookupOrderHistoryAIService.class)
                .chatModel(chatModel)
                .chatRequestTransformer(ChatRequestTransformer.overrideTemperature(aiServiceConfigs.aiServiceConfigs().get("lookup-order-history").temperature()))
                .toolProvider(toolProvider)
                .build();
    }

}
