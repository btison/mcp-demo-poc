package org.globex.ai.model;

public record HumanMessage(
        String content
) implements Message {
}
