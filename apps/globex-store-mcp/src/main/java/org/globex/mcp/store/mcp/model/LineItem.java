package org.globex.mcp.store.mcp.model;

public class LineItem {

    private String productCode;

    private String productName;

    private int quantity;

    public String getProductCode() {
        return productCode;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final LineItem lineItem;

        public Builder() {
            this.lineItem = new LineItem();
        }

        public Builder withProductCode(String productCode) {
            lineItem.productCode = productCode;
            return this;
        }

        public Builder withProductName(String productName) {
            lineItem.productName = productName;
            return this;
        }

        public Builder withQuantity(Integer quantity) {
            lineItem.quantity = quantity;
            return this;
        }

        public LineItem build() {
            return this.lineItem;
        }
    }

}
