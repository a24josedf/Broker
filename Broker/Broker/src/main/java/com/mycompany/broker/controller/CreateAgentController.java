package com.mycompany.broker.controller;

import com.mycompany.broker.model.MainModel;
import com.mycompany.broker.view.AgentJDialog;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class CreateAgentController {

    private final AgentJDialog view;
    private final MainModel model;

    public CreateAgentController(AgentJDialog view, MainModel model) {
        this.view = view;
        this.model = model;
        this.view.addAddButtonActionListener(addAddButtonActionListener());
        this.view.addCancelButtonActionListener(event -> view.dispose());
    }

    private ActionListener addAddButtonActionListener() {
        return event -> {
            try {
                String name = view.getAgentName().trim();
                double balance = Double.parseDouble(view.getBalance().trim());
                int stock = Integer.parseInt(view.getStock().trim());

                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Enter a name");
                    return;
                }
                if (model.nameExists(name)) {
                    JOptionPane.showMessageDialog(view, "This agent already exists");
                    return;
                }
                if (balance < 0 || stock < 0) {
                    JOptionPane.showMessageDialog(view, "Balance and stock cannot be negative");
                    return;
                }

                model.addAgent(name, balance, stock);
                view.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "Enter valid numbers for balance and stock");
            }
        };
    }
}
