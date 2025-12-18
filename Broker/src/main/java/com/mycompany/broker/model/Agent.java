package com.mycompany.broker.model;

import java.util.Objects;

/**
 *
 * @author dam2_alu04@inf.ald
 */
public class Agent {

    private int id = 0;
    private String name;
    private double balance;
    private int stock;
    private Operation purchaseOperation;
    private Operation saleOperation;

    public Agent(String name, double balance, int stock) {
        this.setId(id++);
        this.name = name;
        this.balance = balance;
        this.stock = stock;

    }

    public boolean newOperation(Agent this, String type, double limit, double quantity) {
        switch (type) {
            case "compra":
                if (purchaseOperation == null) {
                    purchaseOperation = new Operation(this, type, limit, quantity);
                } else {
                    System.out.println("Ya existe una operacion de compra para el agente " + getName());
                    return false;
                }
                break;
            case "venta":
                if (saleOperation == null) {
                    saleOperation = new Operation(this, type, limit, quantity);
                } else {
                    System.out.println("Ya existe una operacion de venta para el agente " + getName());
                    return false;
                }
                break;
            default:
                return false;
        }
        return true;
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

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Usuario{" + "id = " + id + ", name = " + name + ", balance = " + balance + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Agent other = (Agent) obj;
        return Objects.equals(this.id, other.id);
    }

}
