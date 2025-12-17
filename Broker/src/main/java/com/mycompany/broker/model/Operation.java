package com.mycompany.broker.model;

/**
 *
 * @author dam2_alu04@inf.ald
 */
public class Operation implements Runnable {

    private String type;
    private double limit;
    private double quantity;
    private User user;
    private Thread executorThread;

    public Operation(User user, String type, double limit, double quantity) {
        this.user= user;
        setType(type);
        this.limit = limit;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        if (type.equalsIgnoreCase("compra")||type.equalsIgnoreCase("venta")) {
            this.type = type;
        }
    }

    public double getLimit() {
        return limit;
    }

    public void setLimit(double limit) {
        this.limit = limit;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

}
