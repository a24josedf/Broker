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
    private static ArrayList<Agent> agents;

    public static ArrayList<Agent> getAgents() {
        return agents;
    }

    public static void setAgents(ArrayList<Agent> agents) {
        Agents.agents = agents;
    }
    
}
