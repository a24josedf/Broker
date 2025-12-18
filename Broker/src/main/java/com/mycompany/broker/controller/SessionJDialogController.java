package com.mycompany.broker.controller;

import com.mycompany.broker.model.MainModel;
import com.mycompany.broker.model.Agent;
import com.mycompany.broker.view.SessionJDialog;
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
public class SessionJDialogController {

    private final SessionJDialog view;
    private final MainModel model;

    public SessionJDialogController(SessionJDialog view, MainModel model) throws IOException, ClassNotFoundException {
        this.view = view;
        this.model = model;
        manageLogInButton();
        this.view.addLogInButtonActionListener(this.getLogInButtonActionListener());
        this.view.addSingUpButtonActionListener(this.getSingUpButtonActionListener());
    }

    private void verifyLogIn() throws IOException, ClassNotFoundException {
        File file = new File ("users.ser");
        String username = view.getUsername().trim();
        Double balance = Double.parseDouble(view.getBalance().trim());
        //Agent u = new Agent(username, balance);
            
        
    }
    private void verifySingUp() throws IOException, ClassNotFoundException {
        File file = new File ("users.ser");
        String username = view.getUsername().trim();
        Double balance = Double.parseDouble(view.getBalance().trim());
        //Agent u = new Agent(username, balance);
        
    }
    
    private void clearView(){
        view.setUsername("");
        view.setBalance("");
    }

    private boolean balanceTextFieldEmpty() {
        String balance = view.getBalance();
        if (balance.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    private boolean usernameTextFieldEmpty() {
        String username = view.getUsername();
        if (username.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
    
    private void manageLogInButton() throws IOException, ClassNotFoundException{
        File file = new File ("users.ser");
        if (file.length()<1) {
            view.enableDisableLogInButton(false);
        }else{
            view.enableDisableLogInButton(true);
        }
    }
    
    public ActionListener getLogInButtonActionListener() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (usernameTextFieldEmpty()) {
                    JOptionPane.showMessageDialog(view, "Introduce an user");
                }
                if (balanceTextFieldEmpty()) {

                    JOptionPane.showMessageDialog(view, "Introduce balance");
                }if(!usernameTextFieldEmpty() && !balanceTextFieldEmpty()){
                    try {
                        verifyLogIn();
                    } catch (IOException ex) {
                        Logger.getLogger(SessionJDialogController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(SessionJDialogController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
                try {
                    manageLogInButton();
                } catch (IOException ex) {
                    Logger.getLogger(SessionJDialogController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(SessionJDialogController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        };
        
        return al;
    }

    public ActionListener getSingUpButtonActionListener() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (usernameTextFieldEmpty()) {
                    JOptionPane.showMessageDialog(view, "Introduce an user");
                }if (balanceTextFieldEmpty()) {
                    JOptionPane.showMessageDialog(view, "Introduce a password");
                }if(!usernameTextFieldEmpty() && !balanceTextFieldEmpty()){
                    try {
                    verifySingUp();
                    } catch (IOException ex) {
                        Logger.getLogger(SessionJDialogController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(SessionJDialogController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                try {
                    manageLogInButton();
                } catch (IOException ex) {
                    Logger.getLogger(SessionJDialogController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(SessionJDialogController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        return al;
    }

}
