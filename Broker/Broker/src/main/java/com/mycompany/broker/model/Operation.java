package com.mycompany.broker.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Operation implements Serializable {

    public static final String TYPE_BUY = "Buy";
    public static final String TYPE_SELL = "Sell";
    private static final long serialVersionUID = 1L;
    private static long nextId = 1L;

    private long id;
    private final String type;
    private final double price;
    private final int quantity;
    private int remainingQuantity;
    private final Agent agent;
    private final LocalDateTime createdAt;

    public Operation(Agent agent, String type, double price, int quantity) {
        this.id = nextId++;
        this.agent = agent;
        this.type = normalizeType(type);
        this.price = price;
        this.quantity = quantity;
        this.remainingQuantity = quantity;
        this.createdAt = LocalDateTime.now();
    }

    public static void refreshNextId(Iterable<Operation> operations) {
        long maxId = 0L;
        for (Operation operation : operations) {
            maxId = Math.max(maxId, operation.getId());
        }
        nextId = maxId + 1L;
    }

    private String normalizeType(String type) {
        if (TYPE_BUY.equalsIgnoreCase(type)) {
            return TYPE_BUY;
        }
        if (TYPE_SELL.equalsIgnoreCase(type)) {
            return TYPE_SELL;
        }
        throw new IllegalArgumentException("Invalid order type");
    }

    public void consume(int quantity) {
        if (quantity <= 0 || quantity > remainingQuantity) {
            throw new IllegalArgumentException("Invalid executed quantity");
        }
        remainingQuantity -= quantity;
    }

    public boolean isCompleted() {
        return remainingQuantity == 0;
    }

    public Agent getAgent() {
        return agent;
    }

    public long getId() {
        return id;
    }

    public void setIdIfMissing(long id) {
        if (this.id == 0L) {
            this.id = id;
        }
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getRemainingQuantity() {
        return remainingQuantity;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getCreatedAtText() {
        return createdAt.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }
}
