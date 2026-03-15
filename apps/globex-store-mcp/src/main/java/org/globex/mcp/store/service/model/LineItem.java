package org.globex.mcp.store.service.model;

import java.math.BigDecimal;

public class LineItem {

    private String product;

    private int quantity;

    private BigDecimal price;

    public String getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

}
