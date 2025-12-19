package com.mycompany.broker.controller;

import com.mycompany.broker.model.Agent;
import com.mycompany.broker.model.MainModel;
import com.mycompany.broker.model.Operation;
import com.mycompany.broker.view.MainJFrame;
import com.mycompany.broker.view.OperationsJDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 *
 * @author dam2_alu04@inf.ald
 */
public class OperationsController {
    private OperationsJDialog view;
    private MainModel model;
    private MainJFrameController parent;
    private Agent currentAgent;

    public OperationsController(OperationsJDialog view, MainModel model, MainJFrameController parent) {
        this.view = view;
        this.model = model;
        this.parent = parent;
        this.currentAgent = model.getAgents().get(parent.selectedAgent());
        this.view.addAcceptBButtonActionListener(this.addAcceptBButtonActionListener());
        this.view.addAcceptSButtonActionListener(this.addAcceptSButtonActionListener());
        this.view.addCancelBButtonActionListener(this.addCancelBButtonActionListener());
        this.view.addCancelSButtonActionListener(this.addCancelSButtonActionListener());
    }
    
    private boolean priceBTextFieldEmpty() {
        String priceB = view.getPriceB();
        if (priceB.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
    
    private boolean priceSTextFieldEmpty() {
        String priceS = view.getPriceS();
        if (priceS.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
    
    private boolean quantityBTextFieldEmpty() {
        String quantityB = view.getQuantityB();
        if (quantityB.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
    
    private boolean quantitySTextFieldEmpty() {
        String quantityS = view.getQuantityS();
        if (quantityS.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
    
    private void verifyB() throws IOException{
        String type = "Compra";
        String priceB = view.getPriceB();
        String quantityB = view.getQuantityB();
        Agent a = this.currentAgent;
        if (quantityBTextFieldEmpty()||priceBTextFieldEmpty()) {
            JOptionPane.showMessageDialog(view, "Rellena todos los campos");
        }else{
            if (Double.parseDouble(priceB)<0) {
                JOptionPane.showMessageDialog(view, "Introduce un precio valido");
            }
            if (Integer.parseInt(quantityB)<0) {
                JOptionPane.showMessageDialog(view, "Introduce una cantidad valida");
            }else{
                if (this.currentAgent.getPurchaseOperation()!=null) {
                    JOptionPane.showMessageDialog(view, "Este agente ya tiene una operacion de compra");
                }else{
                    createOperation(type, Double.parseDouble(priceB), Integer.parseInt(quantityB));
                    model.addOperation(currentAgent.getPurchaseOperation());
                    model.serializeOperationsList();
                    parent.repaintOperationsTable();
                }
            }
        }
    }
    
    private void verifyS() throws IOException{
        String type = "Venta";
        String priceS = view.getPriceS();
        String quantityS = view.getQuantityS();
        Agent a = this.currentAgent;
        if (quantitySTextFieldEmpty()||priceSTextFieldEmpty()) {
            JOptionPane.showMessageDialog(view, "Rellena todos los campos");
        }else{
            if (Double.parseDouble(priceS)<0) {
                JOptionPane.showMessageDialog(view, "Introduce un precio valido");
            }
            if (Integer.parseInt(quantityS)<0) {
                JOptionPane.showMessageDialog(view, "Introduce una cantidad valida");
            }else{
                if (this.currentAgent.getSaleOperation()!=null) {
                    JOptionPane.showMessageDialog(view, "Este agente ya tiene una operacion de venta");
                }else{
                    createOperation(type, Double.parseDouble(priceS), Integer.parseInt(quantityS));  
                    model.getAgents().remove(currentAgent);                  
                    model.addOperation(currentAgent.getSaleOperation());
                    model.addAgent(currentAgent);
                    model.serializeOperationsList();
                    parent.repaintOperationsTable();
                }
            }
        }
    }
    
    private void createOperation(String type, Double price, int quantity){
        Operation b = new Operation(currentAgent, type, price, quantity);
        this.currentAgent.newOperation(type, price, quantity);
        
    }
    
    
    private ActionListener addAcceptBButtonActionListener(){
        ActionListener al = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    verifyB();
                } catch (IOException ex) {
                    System.getLogger(OperationsController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                }
            }
            
        };
        return al;
    }
    
    private ActionListener addAcceptSButtonActionListener(){
        ActionListener al = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    verifyS();
                } catch (IOException ex) {
                    System.getLogger(OperationsController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                }
            }
            
        };
        return al;
    }
    
    private ActionListener addCancelBButtonActionListener(){
        ActionListener al = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose();
            }
            
        };
        return al;
    }
    private ActionListener addCancelSButtonActionListener(){
        ActionListener al = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose();
            }
            
        };
        return al;
    }
}
