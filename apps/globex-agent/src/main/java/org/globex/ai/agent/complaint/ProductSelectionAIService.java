package org.globex.ai.agent.complaint;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

public interface ProductSelectionAIService {

    @SystemMessage("""
            You are a helpful product complaint handling assistant.
            Speak directly to users in a conversational and professional manner.
            CRITICAL do not share your internal thinking.
            """)
    @UserMessage("""
            The user said: {input}
            
            Identify the product from the user order history:
            {orderHistory}
            
            If you find the product in the order history, you must answer strictly in the following JSON format:
            {
            "orderId": (type: java.lang.Long),
            "productCode": (type: string),
            "productName": (type: string)
            }
            
            If you cannot find the product in the order history, you must answer with PRODUCT_NOT_SELECTED. Do not add anything else to your response.
            
            """)
    String selectProduct(String input, String orderHistory);
}
