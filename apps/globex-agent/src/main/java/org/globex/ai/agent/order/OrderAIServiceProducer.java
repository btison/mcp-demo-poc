package org.globex.ai.agent.order;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.AiServices;
import io.quarkiverse.langchain4j.ModelName;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import org.globex.ai.agent.AIServiceConfigs;

import static org.globex.ai.agent.ChatRequestTransformer.overrideTemperature;

@ApplicationScoped
public class OrderAIServiceProducer {

    @Inject
    @ModelName("order")
    ChatModel chatModel;

    @Inject
    AIServiceConfigs aiServiceConfigs;

    @Produces
    public OrderAIService provideOrderAIService() {
        return AiServices.builder(OrderAIService.class)
                .chatModel(chatModel)
                .chatRequestTransformer(overrideTemperature(aiServiceConfigs.aiServiceConfigs().get("order").temperature()))
                .build();
    }


}
