package org.globex.ai.graph;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.memory.ChatMemory;
import org.bsc.langgraph4j.action.NodeAction;
import org.globex.ai.agent.ConversationChatMemory;
import org.globex.ai.model.Message;

import java.util.Map;

public class InitChatHistoryNodeAction {

    public static NodeAction<State> get(ConversationChatMemory conversationChatMemory) {
        return state -> {
            String threadId = state.value("thread_id").orElse("default").toString();
            Message assistantMessage = state.lastAIMessage();
            ChatMemory chatMemory = conversationChatMemory.get(threadId);
            chatMemory.add(new AiMessage(assistantMessage.content()));
            return Map.of();
        };
    }
}
