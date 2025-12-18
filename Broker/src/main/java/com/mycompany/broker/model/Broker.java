package com.mycompany.broker.model;

import java.util.ArrayList;

/**
 *
 * @author dam2_alu04@inf.ald
 */
public class Broker {
    private static double price;
    private static ArrayList<Operation> operations;

    public static double getPrice() {
        return price;
    }

    public static void setPrice(double price) {
        Broker.price = price;
    }

    public static ArrayList<Operation> getOperations() {
        return operations;
    }

    public static void setOperations(ArrayList<Operation> operations) {
        Broker.operations = operations;
    }
    
}
