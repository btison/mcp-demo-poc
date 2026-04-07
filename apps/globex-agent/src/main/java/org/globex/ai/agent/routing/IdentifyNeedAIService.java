package org.globex.ai.agent.routing;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

public interface IdentifyNeedAIService {

    @SystemMessage("""
            You are a routing agent specializing in getting users to the correct specialist agent.
            Be helpful, friendly, and efficient in determining their needs.
            """)
    @UserMessage("""
            The user said: {input}
            
            You can help users with the following requests:
             - product complaints
             - inquiries about orders.
            
            If the user request is a statement or question that you can help with, proceed with the following instructions:
            
            ------
            Respond with "CONTINUE". Do not add anything else to your answer
            ------
            
            If the user input is empty, or is merely a greeting, proceed with the following instructions:
            
            ---------
            Greet the user and clearly identify yourself as the routing agent. Ask them what they need help with today.
            
            Tell them what you can help them with.
            
            Currently you can help with:
              - product complaints
              - inquiries about orders
            Ask them to describe what they need help with.
            -------
            
            Examples of greetings:
            * Hi
            * Hello
            * What can you do?
            * I need help
            
            In all other cases, proceed with the instructions for an empty user input.
            
            """)
    String process(String input);

}
