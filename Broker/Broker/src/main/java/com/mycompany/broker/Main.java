package com.mycompany.broker;

import com.mycompany.broker.controller.MainJFrameController;
import com.mycompany.broker.model.MainModel;
import com.mycompany.broker.view.MainJFrame;

public class Main {

    public static void main(String[] args) {
        MainJFrame view = new MainJFrame();
        MainModel model = new MainModel();
        new MainJFrameController(view, model);
        view.setVisible(true);
    }
}
