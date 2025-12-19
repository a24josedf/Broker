/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.broker.model;

import java.util.ArrayList;

/**
 *
 * @author dam2_alu04@inf.ald
 */
public class Agents {
    private ArrayList<Agent> agents;

    public Agents(){
        agents = new ArrayList<>();
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
}
