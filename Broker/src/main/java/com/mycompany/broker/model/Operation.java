package com.mycompany.broker.model;

import java.io.Serializable;

/**
 *
 * @author dam2_alu04@inf.ald
 */
public class Operation implements Runnable, Serializable {

    private static final long serialVersionUID = 1L;
    private String type;
    private double price;
    private int quantity;
    private Agent agent;
    private transient Thread executorThread;
    
    public Operation(Agent agent, String type, double price, int quantity) {
        this.agent= agent;
        setType(type);
        this.price = price;
        this.quantity = quantity;
        executorThread = new Thread(this);
        executorThread.start();
    }    

    @Override
    public void run() {
        //bucle
            //comprobar precio broker
                //si 
                    //pedir el stock 
                    //sumar o restar 
                    //liberar stock
                //no
                    //duermo
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        if (type.equalsIgnoreCase("compra")||type.equalsIgnoreCase("venta")) {
            this.type = type;
        }
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Operation{" + "type=" + type + ", price=" + price + ", quantity=" + quantity + ", agent=" + agent + ", executorThread=" + executorThread + '}';
    }

}
