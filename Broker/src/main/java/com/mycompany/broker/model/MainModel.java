/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.broker.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author dam2_alu04@inf.ald
 */
public class MainModel implements Serializable{
    private ArrayList<Agent> agents;
    private ArrayList<Operation> operations;
    private final File file = new File("agents.dat");
    private final File file1 = new File("operations.dat");

    public MainModel(){
        agents = deserializedAgentsList();
        operations = deserializedOperationsList();
    }
    public ArrayList<Agent> getAgents() {
        return agents;
    }

    public void setAgents(ArrayList<Agent> agents) {
        this.agents = agents;
    }
    
    public void addAgent(Agent a ){
        agents.add(a);
    }
    
    public ArrayList<Operation> getOperations() {
        return operations;
    }

    public void setOperations(ArrayList<Operation> operations) {
        operations = operations;
    }
    
    public void addOperation(Operation op){
        operations.add(op);
    }
    public void serializeAgentsList() throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(agents);
        }
    }

    public ArrayList<Agent> deserializedAgentsList() {
        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (ArrayList<Agent>) ois.readObject();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return new ArrayList<>();   
        }
    }
    
    public void serializeOperationsList() throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file1))) {
            oos.writeObject(operations);
        }
    }

    public ArrayList<Operation> deserializedOperationsList() {
        if (!file1.exists() || file1.length() == 0) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file1))) {
            return (ArrayList<Operation>) ois.readObject();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return new ArrayList<>();   
        }
    }
    
}
