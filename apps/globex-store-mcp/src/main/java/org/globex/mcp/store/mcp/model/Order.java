package org.globex.mcp.store.mcp.model;

import java.time.Instant;
import java.util.List;

public class Order {

    private long id;

    private String customer;

    private Instant timestamp;

    private List<LineItem> lineItems;

    public long getId() {
        return id;
    }

    public String getCustomer() {
        return customer;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public List<LineItem> getLineItems() {
        return lineItems;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final Order order;

        public Builder() {
            order = new Order();
        }

        public Builder withId(Long id) {
            order.id = id;
            return this;
        }

        public Builder withCustomer(String customer) {
            order.customer = customer;
            return this;
        }

        public Builder withTimestamp(Instant ts) {
            order.timestamp = ts;
            return this;
        }

        public Builder withOrderLineItems(List<LineItem> lineItems) {
            order.lineItems = lineItems;
            return this;
        }

        public Order build() {
            return order;
        }
    }
}
