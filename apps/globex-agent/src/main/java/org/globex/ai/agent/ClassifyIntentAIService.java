package org.globex.ai.agent;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

public interface ClassifyIntentAIService {

    @SystemMessage("""
            You are a routing agent specializing in getting users to the correct specialist agent.
            
            CRITICAL: do not share your internal thinking.
            """)
    @UserMessage("""
            The user said: "{userMessage}"
            
            Analyze their request and determine what they need help with:
            
            1. PRODUCT_COMPLAINT - User wants to report a defect or a problem with a product. Also includes requests that just say "problem" or "defect" or similar terms.
            2. ORDER - User needs help with an order. This includes inquiries about delivery, shipping delays, backorders.
            3. OTHER - User needs help with something else or request is unclear
            
            Examples of PRODUCT_COMPLAINT requests:
              - "There is a problem with product xxxxxx"
              - "Product xxxxx doesn't match the description"
              - "I want to report a defect with a product"
              - "My xxxxx is broken"
              - "defect"
            
            Respond with exactly one of: PRODUCT_COMPLAINT, ORDER, or OTHER.
            Do not add anything else to your response.
            """)
    String process(String userMessage);

}
