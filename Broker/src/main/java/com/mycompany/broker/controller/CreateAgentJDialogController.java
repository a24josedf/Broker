package com.mycompany.broker.controller;

import com.mycompany.broker.model.Agent;
import com.mycompany.broker.model.Agents;
import com.mycompany.broker.view.AgentJDialog;
import com.mycompany.broker.view.MainJFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author dam2_alu09@inf.ald
 */
public class CreateAgentJDialogController {
    
    private final AgentJDialog view;
    private final Agents model;
    private final MainJFrameController parent;
    
    public CreateAgentJDialogController(AgentJDialog view, Agents model, MainJFrameController parent) throws IOException, ClassNotFoundException {
        this.view = view;
        this.model = model;
        this.parent = parent;
        this.view.addAddButtonActionListener(this.addAddButtonActionListener());
        this.view.addCancelButtonActionListener(this.addCancelButtonActionListener());
    }
    
    private void verify() throws IOException, ClassNotFoundException {
        File file = new File("agents.ser");
        String name = view.getName().trim();
        String balance = view.getBalance().trim();
        String stock = view.getStock().trim();
        /*if (true) {
            else{*/
        if (Integer.parseInt(stock.trim()) < 0) {
            JOptionPane.showMessageDialog(view, "El stock no puede ser negativo");
        }
        if (Double.parseDouble(view.getBalance().trim()) < 0) {
            JOptionPane.showMessageDialog(view, "El balance no puede ser negativo");
        } else {
            Agent u = new Agent(name, Double.parseDouble(view.getBalance().trim()), Integer.parseInt(stock.trim()));
            System.out.println(u.toString());
            model.addAgent(u);
            parent.repaintTable();
        }
        /*
        }
        }*/
        
    }
    
    private void clearView() {
        view.setName("");
        view.setBalance("");
        view.setStock("");
    }
    
    private boolean stockTextFieldEmpty() {
        String stock = view.getStock();
        if (stock.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
    
    private boolean balanceTextFieldEmpty() {
        String balance = view.getBalance();
        if (balance.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
    
    private boolean nameTextFieldEmpty() {
        String username = view.getName();
        if (username.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
    
    public ActionListener addCancelButtonActionListener() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                view.dispose();
            }
            
        };
        
        return al;
    }
    
    public ActionListener addAddButtonActionListener() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (nameTextFieldEmpty()) {
                    JOptionPane.showMessageDialog(view, "Introduce a name");
                }
                if (stockTextFieldEmpty()) {
                    JOptionPane.showMessageDialog(view, "Introduce balance");
                }
                if (!nameTextFieldEmpty() && !stockTextFieldEmpty()) {
                    try {
                        verify();
                    } catch (IOException ex) {
                        Logger.getLogger(CreateAgentJDialogController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(CreateAgentJDialogController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
        return al;
    }
    
}
