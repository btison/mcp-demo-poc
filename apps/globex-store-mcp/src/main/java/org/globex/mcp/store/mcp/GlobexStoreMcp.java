package org.globex.mcp.store.mcp;

import io.quarkiverse.mcp.server.Tool;
import io.quarkiverse.mcp.server.ToolArg;
import io.quarkus.logging.Log;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.globex.mcp.store.mcp.model.LineItem;
import org.globex.mcp.store.mcp.model.Order;
import org.globex.mcp.store.service.GlobexStoreApi;
import org.globex.mcp.store.service.model.Customer;

import java.util.List;

public class GlobexStoreMcp {

    @RestClient
    GlobexStoreApi globexStoreApi;

    @Tool(name = "getOrderHistoryByCustomerEmail", description = "Retrieve a customer's order history based on their customer email")
    public List<Order> getOrderHistory(@ToolArg(description = "the customer email") String customerEmail) {
        Log.infof("getOrderHistoryByCustomerEmail Tool invoked for customer Email %s", customerEmail);
        Customer customer = globexStoreApi.getCustomerByUserEmail(customerEmail);
        if (customer == null) {
            return List.of();
        }
        List<org.globex.mcp.store.service.model.Order> orders = globexStoreApi.getOrdersByCustomerId(customer.getUserId());
        return orders.stream().map(o -> Order.builder()
                .withId(o.getId())
                .withCustomer(o.getCustomer())
                .withTimestamp(o.getTimestamp())
                .withOrderLineItems(o.getLineItems().stream().map(lineItem -> LineItem.builder()
                                .withProductCode(lineItem.getProduct())
                                .withProductName(globexStoreApi.getProductById(lineItem.getProduct()).getName())
                                .build())
                        .toList())
                .build()).toList();
    }

}
