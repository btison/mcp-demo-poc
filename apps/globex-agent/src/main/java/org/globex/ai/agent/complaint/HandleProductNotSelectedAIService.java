package org.globex.ai.agent.complaint;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

public interface HandleProductNotSelectedAIService {
    @SystemMessage("""
            You are a helpful product complaint handling assistant.
            Speak directly to users in a conversational and professional manner.
            CRITICAL do not share your internal thinking.
            """)
    @UserMessage("""
            The user said: "{userMessage}"
            
            The user input does not allow to find the product they want to file a complaint for in the order history.
            
            Ask them to clarify what product they want to file a complaint for.
            Suggest them to specify the order number and the product code.
            
            Be helpful and professional.
            """)
    String handleRequest(String userMessage);

}
