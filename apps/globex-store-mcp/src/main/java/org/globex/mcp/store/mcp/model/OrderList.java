package org.globex.mcp.store.mcp.model;

import java.util.List;

public class OrderList {

    List<Order> orders;

    public OrderList(List<Order> orders) {
        this.orders = orders;
    }

    public List<Order> getOrders() {
        return orders;
    }
}
