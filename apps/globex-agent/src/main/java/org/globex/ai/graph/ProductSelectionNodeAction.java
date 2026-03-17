package org.globex.ai.graph;

import io.quarkus.logging.Log;
import io.vertx.core.json.DecodeException;
import io.vertx.core.json.JsonObject;
import org.bsc.langgraph4j.action.NodeAction;
import org.globex.ai.model.AssistantMessage;

import java.util.Map;
import java.util.function.BiFunction;

public class ProductSelectionNodeAction {

    public static NodeAction<State> get(BiFunction<String, String, String> action) {
        return state -> {
            String orderHistory = state.value("order_history").orElse("{}").toString();
            String userMessage = state.lastHumanMessage().content();
            String response = action.apply(userMessage, orderHistory);
            Map<String, Object> updateState = state.addMessage(new AssistantMessage(response));
            if (response != null && !response.equalsIgnoreCase("PRODUCT_NOT_SELECTED")) {
                JsonObject selectedProduct = getAndValidateSelectedProduct(response);
                if (selectedProduct != null) {
                    updateState.put("orderId", selectedProduct.getLong("orderId"));
                    updateState.put("productCode", selectedProduct.getString("productCode"));
                    updateState.put("productName", selectedProduct.getString("productName"));
                    updateState.put("product_selection", "PRODUCT_SELECTED");
                } else {
                    // need a better way to handle this. This is actually the llm failing, not the user
                    updateState.put("product_selection", "PRODUCT_NOT_SELECTED");
                }
            } else {
                updateState.put("product_selection", "PRODUCT_NOT_SELECTED");
            }
            return updateState;
        };
    }

    static JsonObject getAndValidateSelectedProduct(String response) {
        try {
            JsonObject json = new JsonObject(response);
            Long orderId = json.getLong("orderId");
            if (orderId == null) {
                Log.warnf("The response from the LLM is missing the orderId: %s", response);
                return null;
            }
            String productCode = json.getString("productCode");
            if (productCode == null || productCode.isEmpty()) {
                Log.warnf("The response from the LLM is missing the product code: %s", response);
                return null;
            }
            String productName = json.getString("productName");
            if (productName == null || productName.isEmpty()) {
                Log.warnf("The response from the LLM is missing the product name: %s", response);
                return null;
            }
            return json;
        } catch (DecodeException e) {
            Log.warnf("The response from the LLM is not a valid JSON structure: %s", response);
            return null;
        }
    }

}
