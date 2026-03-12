package org.globex.ai.graph;

import org.bsc.langgraph4j.action.NodeAction;
import org.globex.ai.model.AIMessage;
import org.globex.ai.model.Message;

import java.util.Map;
import java.util.function.Function;

public class LlmNodeAction {

    public static NodeAction<State> get(Function<String, String> llmAction) {
        return get(llmAction, (r,state) -> state);
    }

    public static NodeAction<State> get(Function<String, String> llmAction, StateAction stateAction) {
        return state -> {
            Message lastHumanMessage = state.lastHumanMessage();
            String request = lastHumanMessage == null ? "" : lastHumanMessage.content();
            String response = llmAction.apply(request);
            Map<String, Object> updateState = state.addMessage(new AIMessage(response));
            return stateAction.apply(response, updateState);
        };
    }

}
