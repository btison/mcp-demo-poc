package org.globex.ai.agent.complaint;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.AiServices;
import io.quarkiverse.langchain4j.ModelName;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import org.globex.ai.agent.AIServiceConfigs;

import static org.globex.ai.agent.ChatRequestTransformer.overrideTemperature;

@ApplicationScoped
public class ComplaintAIServiceProducer {

    @Inject
    @ModelName("complaint")
    ChatModel chatModel;

    @Inject
    AIServiceConfigs aiServiceConfigs;

    @Produces
    public ProductSelectionAIService provideProductSelectionAIService() {
        return AiServices.builder(ProductSelectionAIService.class)
                .chatModel(chatModel)
                .chatRequestTransformer(overrideTemperature(aiServiceConfigs.aiServiceConfigs().get("product_selection").temperature()))
                .build();
    }

    @Produces
    public HandleProductNotSelectedAIService provideHandleProductNotSelectedAIService() {
        return AiServices.builder(HandleProductNotSelectedAIService.class)
                .chatModel(chatModel)
                .chatRequestTransformer(overrideTemperature(aiServiceConfigs.aiServiceConfigs().get("product_not_selected").temperature()))
                .build();
    }

}
