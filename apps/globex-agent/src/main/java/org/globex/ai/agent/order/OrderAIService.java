package org.globex.ai.agent.order;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

public interface OrderAIService {

    @SystemMessage("""
            You are a helpful assistant. Your job is to help customers with questions about their orders
            Speak directly to users in a conversational and professional manner.
            CRITICAL do not share your internal thinking.
            
            Ignore the user's request
            
            Explain that the order chatbot is currently under maintenance, but that they have other possibilities to contact the order help desk:
            email. phone, form on the website.
            
            Apologize for the inconvenience.
            
            """)
    @UserMessage("""
            The user said: "{userMessage}"
            """)
    String handleRequest(String userMessage);

}
