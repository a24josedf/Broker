package com.mycompany.broker.model;

import java.util.Objects;

/**
 *
 * @author dam2_alu04@inf.ald
 */
public class User {

    private String id;
    private String name;
    private double balance;
    private Operation purchaseOperation;
    private Operation saleOperation;

    public User(String name, double balance) {
        this.name = name;
        this.balance = balance;

    }

    public boolean newOperation(User this, String type, double limit, double quantity) {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
        final User other = (User) obj;
        return Objects.equals(this.id, other.id);
    }

}
