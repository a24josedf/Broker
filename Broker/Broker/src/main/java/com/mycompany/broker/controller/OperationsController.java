package com.mycompany.broker.controller;

import com.mycompany.broker.model.Agent;
import com.mycompany.broker.model.MainModel;
import com.mycompany.broker.model.Operation;
import com.mycompany.broker.view.OperationsJDialog;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class OperationsController {

    private final OperationsJDialog view;
    private final MainModel model;
    private final Agent agent;

    public OperationsController(OperationsJDialog view, MainModel model, Agent agent) {
        this.view = view;
        this.model = model;
        this.agent = agent;
        this.view.addAcceptBButtonActionListener(createOrderListener(Operation.TYPE_BUY));
        this.view.addAcceptSButtonActionListener(createOrderListener(Operation.TYPE_SELL));
        this.view.addCancelBButtonActionListener(cancelListener(Operation.TYPE_BUY));
        this.view.addCancelSButtonActionListener(cancelListener(Operation.TYPE_SELL));
    }

    private ActionListener createOrderListener(String type) {
        return event -> {
            try {
                String priceText = Operation.TYPE_BUY.equals(type) ? view.getBuyPrice() : view.getSellPrice();
                String quantityText = Operation.TYPE_BUY.equals(type) ? view.getBuyQuantity() : view.getSellQuantity();
                double price = Double.parseDouble(priceText.trim());
                int quantity = Integer.parseInt(quantityText.trim());

                if (price <= 0 || quantity <= 0) {
                    JOptionPane.showMessageDialog(view, "Price and quantity must be greater than zero");
                    return;
                }
                if (Operation.TYPE_BUY.equals(type) && agent.getBalance() < price * quantity) {
                    JOptionPane.showMessageDialog(view, "The agent does not have enough cash");
                    return;
                }
                if (Operation.TYPE_SELL.equals(type) && agent.getStock() < quantity) {
                    JOptionPane.showMessageDialog(view, "The agent does not have enough stock");
                    return;
                }
                if (agent.getOperation(type) != null) {
                    int option = JOptionPane.showConfirmDialog(
                            view,
                            "There is already a " + type.toLowerCase() + " order. Do you want to edit it?",
                            "Edit order",
                            JOptionPane.YES_NO_OPTION
                    );
                    if (option != JOptionPane.YES_OPTION) {
                        return;
                    }
                }

                model.submitOperation(agent, type, price, quantity);
                view.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "Enter valid numbers");
            }
        };
    }

    private ActionListener cancelListener(String type) {
        return event -> {
            model.cancelOperation(agent, type);
            view.dispose();
        };
    }
}
