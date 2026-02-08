package com.ai.furnitureai.utils;

import java.util.Objects;

public class Order {

    private String orderId;
    private String productId;
    private String productName;
    private String deliveryAddress;

    public Order(Builder builder) {
        this.orderId = builder.orderId;
        this.productId = builder.productId;
        this.deliveryAddress = builder.deliveryAddress;
        this.productName = builder.productName;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getProductId() {
        return productId;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public String getProductName() {
        return productName;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", deliveryAddress='" + deliveryAddress + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(orderId, order.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(orderId);
    }

    public static class Builder {
        private String orderId;
        private String productId;
        private String productName;
        private String deliveryAddress;

        public Builder orderId(String orderId) {
            this.orderId = orderId;
            return this;
        }

        public Builder productId(String productId) {
            this.productId = productId;
            return this;
        }

        public Builder productName(String productName) {
            this.productName = productName;
            return this;
        }

        public Builder deliveryAddress(String deliveryAddress) {
            this.deliveryAddress = deliveryAddress;
            return this;
        }

        public Order build() {
            return new Order(this);
        }

    }
}
