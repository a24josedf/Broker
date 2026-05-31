package com.mycompany.broker.model;

import java.io.Serializable;
import java.util.Objects;

public class Agent implements Serializable {

    private static final long serialVersionUID = 1L;
    private static int nextId = 1;

    private final int id;
    private String name;
    private double balance;
    private int stock;
    private Operation purchaseOperation;
    private Operation saleOperation;

    public Agent(String name, double balance, int stock) {
        this.id = nextId++;
        this.name = name;
        this.balance = balance;
        this.stock = stock;
    }

    public static void refreshNextId(Iterable<Agent> agents) {
        int maxId = 0;
        for (Agent agent : agents) {
            maxId = Math.max(maxId, agent.getId());
        }
        nextId = maxId + 1;
    }

    public Operation getOperation(String type) {
        if (Operation.TYPE_BUY.equals(type)) {
            return purchaseOperation;
        }
        if (Operation.TYPE_SELL.equals(type)) {
            return saleOperation;
        }
        return null;
    }

    public void setOperation(Operation operation) {
        if (Operation.TYPE_BUY.equals(operation.getType())) {
            purchaseOperation = operation;
        } else if (Operation.TYPE_SELL.equals(operation.getType())) {
            saleOperation = operation;
        }
    }

    public void clearOperation(String type) {
        if (Operation.TYPE_BUY.equals(type)) {
            purchaseOperation = null;
        } else if (Operation.TYPE_SELL.equals(type)) {
            saleOperation = null;
        }
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Operation getPurchaseOperation() {
        return purchaseOperation;
    }

    public void setPurchaseOperation(Operation purchaseOperation) {
        this.purchaseOperation = purchaseOperation;
    }

    public Operation getSaleOperation() {
        return saleOperation;
    }

    public void setSaleOperation(Operation saleOperation) {
        this.saleOperation = saleOperation;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name + " - balance: " + String.format("%.2f", balance) + ", stock: " + stock;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Agent)) {
            return false;
        }
        Agent other = (Agent) obj;
        return id == other.id;
    }
}
