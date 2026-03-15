package org.globex.mcp.store.service.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Product {

    private String itemId;

    private String name;

    @JsonProperty("desc")
    private String description;

    private String category;

    private Double price;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer quantity;

    public String getItemId() {
        return itemId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

}
