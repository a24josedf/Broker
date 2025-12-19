package com.mycompany.broker.controller;

import com.mycompany.broker.model.Agent;
import com.mycompany.broker.model.Agents;
import com.mycompany.broker.view.MainJFrame;
import com.mycompany.broker.view.AgentJDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class MainJFrameController {

    private MainJFrame view;
    private Agents model;


    public MainJFrameController(MainJFrame view, Agents model) {
        this.view = view;
        this.model = model;
        this.view.addAAddButtonActionListener(this.addAddButtonActionListener());
    }
    
    public void repaintTable() {
        view.clearTable();
        for (Agent a : model.getAgents()) {
            Vector row = new Vector();
            row.add(a.getName());
            row.add(a.getBalance());
            row.add(a.getStock());
            view.addRow(row);
            

        }

    }

    private ActionListener addAddButtonActionListener() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AgentJDialog sjd = new AgentJDialog(view, true);
                try {
                    CreateAgentJDialogController sjdc = new CreateAgentJDialogController(sjd, model, MainJFrameController.this);
                } catch (IOException ex) {
                    System.getLogger(MainJFrameController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                } catch (ClassNotFoundException ex) {
                    System.getLogger(MainJFrameController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                }
                sjd.setVisible(true);
            }
        };
        return al;
    }
    

    
    
}
