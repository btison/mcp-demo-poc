package org.globex.ai.agent;

public class ClassifyIntentAIService {

    public String process(String input) {
        if ("route".equalsIgnoreCase(input)) {
            return "ROUTE_TO_SPECIALIST";
        }
        return "OTHER";
    };

}
