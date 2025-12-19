package com.mycompany.broker;

import com.mycompany.broker.controller.MainJFrameController;
import com.mycompany.broker.model.Agents;
import com.mycompany.broker.view.MainJFrame;

/**
 *
 * @author dam2_alu04@inf.ald
 */
public class Main {

    public static void main(String[] args) {
        MainJFrame mainView = new MainJFrame();
        Agents mainModel = new Agents();
        MainJFrameController mainController = new MainJFrameController(mainView, mainModel);
        mainView.setVisible(true);
        //Persistencia usuarios y operaciones

        //Tenemos que recuperar el precio y los valores anteriores
        
        //Pintar Precio/Tiempo --> Interfaz gráfica
        
        //Crear agentes que tienen operaciones de entrada y salida
            //Lectura de precio y compran o venden --> 2 tipos de hilos
            
        //Logica de compra-venta en el broker -- Hilo
        
        //Agentes con un capital que puedan lanzar las operaciones de compraventa
        
        //Nuevos agentes
        
        //Crear operaciones
    }
}
