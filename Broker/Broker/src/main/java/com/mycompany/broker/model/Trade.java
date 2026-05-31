package com.mycompany.broker.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Trade implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String buyerName;
    private final String sellerName;
    private final int quantity;
    private final double price;
    private final LocalDateTime executedAt;

    public Trade(String buyerName, String sellerName, int quantity, double price) {
        this.buyerName = buyerName;
        this.sellerName = sellerName;
        this.quantity = quantity;
        this.price = price;
        this.executedAt = LocalDateTime.now();
    }

    public String getBuyerName() {
        return buyerName;
    }

    public String getSellerName() {
        return sellerName;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public String getExecutedAtText() {
        return executedAt.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }
}
