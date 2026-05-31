package com.mycompany.broker.controller;

import com.mycompany.broker.model.Agent;
import com.mycompany.broker.model.MainModel;
import com.mycompany.broker.model.Operation;
import com.mycompany.broker.view.AgentJDialog;
import com.mycompany.broker.view.MainJFrame;
import com.mycompany.broker.view.OperationsJDialog;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class MainJFrameController {

    private final MainJFrame view;
    private final MainModel model;

    public MainJFrameController(MainJFrame view, MainModel model) {
        this.view = view;
        this.model = model;
        this.model.addListener(() -> SwingUtilities.invokeLater(this::refreshAll));
        this.view.addAddButtonActionListener(event -> openAgentDialog());
        this.view.addOperationsButtonActionListener(event -> openOperationsDialog());
        this.view.addEditOrderButtonActionListener(event -> editSelectedOrder());
        this.view.addCancelOrderButtonActionListener(event -> cancelSelectedOrder());
        this.view.addDemoButtonActionListener(event -> loadDemoData());
        new Timer(500, event -> view.getGraphic().update(model)).start();
        this.view.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                model.stopAndSave();
            }
        });
        refreshAll();
    }

    private void refreshAll() {
        refreshAgentsTable();
        refreshOrdersTable();
        view.getGraphic().update(model);
    }

    public void refreshAgentsTable() {
        String selectedName = null;
        int selectedRow = view.getSelectedAgent();
        if (selectedRow >= 0) {
            selectedName = String.valueOf(view.getAgentsTable().getValueAt(selectedRow, 0));
        }

        view.clearAgentsTable();
        int rowToSelect = -1;
        int rowIndex = 0;
        for (Agent agent : model.getAgents()) {
            Vector<Object> row = new Vector<>();
            row.add(agent.getName());
            row.add(round(agent.getBalance()));
            row.add(agent.getStock());
            view.addAgentRow(row);
            if (agent.getName().equals(selectedName)) {
                rowToSelect = rowIndex;
            }
            rowIndex++;
        }

        if (rowToSelect >= 0) {
            view.getAgentsTable().setRowSelectionInterval(rowToSelect, rowToSelect);
        }
    }

    public void refreshOrdersTable() {
        Long selectedId = view.getSelectedOrderId();

        view.clearOperationsTable();
        int rowToSelect = -1;
        int rowIndex = 0;
        for (Operation operation : model.getOperations()) {
            Vector<Object> row = new Vector<>();
            row.add(operation.getId());
            row.add(operation.getType());
            row.add(round(operation.getPrice()));
            row.add(operation.getQuantity());
            row.add(operation.getRemainingQuantity());
            row.add(operation.getAgent().getName());
            row.add(operation.getCreatedAtText());
            view.addOperationRow(row);
            if (selectedId != null && operation.getId() == selectedId) {
                rowToSelect = rowIndex;
            }
            rowIndex++;
        }

        if (rowToSelect >= 0) {
            view.getOperationsTable().setRowSelectionInterval(rowToSelect, rowToSelect);
        }
    }

    private void openAgentDialog() {
        AgentJDialog dialog = new AgentJDialog(view, true);
        new CreateAgentController(dialog, model);
        dialog.setLocationRelativeTo(view);
        dialog.setVisible(true);
    }

    private void editSelectedOrder() {
        Operation operation = selectedOperation();
        if (operation == null) {
            JOptionPane.showMessageDialog(view, "Select an order in the Orders tab");
            return;
        }

        OperationsJDialog dialog = new OperationsJDialog(view, true);
        new OperationsController(dialog, model, operation.getAgent());
        dialog.setTitle("Orders for " + operation.getAgent().getName());
        dialog.setLocationRelativeTo(view);
        dialog.setVisible(true);
    }

    private void cancelSelectedOrder() {
        Operation operation = selectedOperation();
        if (operation == null) {
            JOptionPane.showMessageDialog(view, "Select an order in the Orders tab");
            return;
        }

        model.cancelOperation(operation.getAgent(), operation.getType());
    }

    private void loadDemoData() {
        int option = JOptionPane.showConfirmDialog(
                view,
                "This will replace the current market with demo data. Continue?",
                "Load demo data",
                JOptionPane.YES_NO_OPTION
        );
        if (option == JOptionPane.YES_OPTION) {
            model.resetDemoData();
        }
    }

    private Operation selectedOperation() {
        Long id = view.getSelectedOrderId();
        if (id == null) {
            return null;
        }
        return model.getOperationById(id);
    }

    private void openOperationsDialog() {
        int selectedRow = view.getSelectedAgent();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(view, "Select an agent in the Agents tab");
            return;
        }
        Agent agent = model.getAgentAt(selectedRow);
        OperationsJDialog dialog = new OperationsJDialog(view, true);
        new OperationsController(dialog, model, agent);
        dialog.setTitle("Orders for " + agent.getName());
        dialog.setLocationRelativeTo(view);
        dialog.setVisible(true);
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
