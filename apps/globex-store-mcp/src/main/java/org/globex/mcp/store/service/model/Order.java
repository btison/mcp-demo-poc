package org.globex.mcp.store.service.model;

import java.time.Instant;
import java.util.List;

public class Order {

    private long id;

    private String customer;

    private Instant timestamp;

    private ShippingAddress shippingAddress;

    private List<LineItem> lineItems;

    public Long getId() {
        return id;
    }

    public String getCustomer() {
        return customer;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public ShippingAddress getShippingAddress() {
        return shippingAddress;
    }

    public List<LineItem> getLineItems() {
        return lineItems;
    }

}
