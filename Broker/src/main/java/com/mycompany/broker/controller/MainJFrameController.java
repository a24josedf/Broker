package com.mycompany.broker.controller;

import com.mycompany.broker.model.MainModel;
import com.mycompany.broker.view.MainJFrame;
import com.mycompany.broker.view.SessionJDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class MainJFrameController {

    private MainJFrame view;
    private MainModel model;


    public MainJFrameController(MainJFrame view, MainModel model) {
        this.view = view;
        this.model = model;
        this.view.addAgentMenuItemActionListener(this.getSessionMenuActionListener());
    }

    private ActionListener getQuitMenuActionListener() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose();
                System.exit(0);
            }
        };
        return al;
    }
    private ActionListener getSessionMenuActionListener() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SessionJDialog sjd = new SessionJDialog(view, true);
                try {
                    SessionJDialogController sjdc = new SessionJDialogController(sjd, model);
                } catch (IOException ex) {
                    Logger.getLogger(MainJFrameController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(MainJFrameController.class.getName()).log(Level.SEVERE, null, ex);
                }
                sjd.setVisible(true);
            }
        };
        return al;
    }
    

    
    
}
