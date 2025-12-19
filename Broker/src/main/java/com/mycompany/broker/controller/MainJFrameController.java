package com.mycompany.broker.controller;

import com.mycompany.broker.model.Agent;
import com.mycompany.broker.model.MainModel;
import com.mycompany.broker.model.Operation;
import com.mycompany.broker.view.MainJFrame;
import com.mycompany.broker.view.AgentJDialog;
import com.mycompany.broker.view.OperationsJDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author dam2_alu04@inf.ald
 */
public class MainJFrameController {

    private MainJFrame view;
    private MainModel model;

    public MainJFrameController(MainJFrame view, MainModel model) {
        this.view = view;
        this.model = model;
        repaintAgentsTable();
        //repaintOperationsTable();
        this.view.addAddButtonActionListener(this.addAddButtonActionListener());
        this.view.addOperationsButtonActionListener(this.addOperationsButtonActionListener());
    }

    public void repaintAgentsTable() {
        view.clearAgentsTable();
        for (Agent a : model.getAgents()) {
            Vector row = new Vector();
            row.add(a.getName());
            row.add(a.getBalance());
            row.add(a.getStock());
            view.addAgentRow(row);

        }

    }
    public void repaintOperationsTable() {
        view.clearOperationsTable();
        for (Operation o : model.getOperations()) {
            Vector row = new Vector();
            row.add(o.getType());
            row.add(o.getPrice());
            row.add(o.getQuantity());
            row.add(o.getAgent().getName());
            view.addOperationRow(row);

        }

    }
    
    public int selectedAgent(){
        return view.getSelectedAgent();
    }

    private ActionListener addAddButtonActionListener() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AgentJDialog sjd = new AgentJDialog(view, true);
                try {
                    CreateAgentController sjdc = new CreateAgentController(sjd, model, MainJFrameController.this);
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
    
    private ActionListener addOperationsButtonActionListener() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (view.getSelectedAgent()>=0) {
                    OperationsJDialog ojd = new OperationsJDialog(view, true);
                    OperationsController ojdc = new OperationsController(ojd, model, MainJFrameController.this);
                    ojd.setVisible(true);
                }else{
                    JOptionPane.showMessageDialog(view, "Selecciona un agente");
                }
                
            }
        };
        return al;
    }

}
