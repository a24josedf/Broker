package com.mycompany.broker.model;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author dam2_alu04@inf.ald
 */
public class Graphic extends JFrame{
     public static void addGraphic(JPanel panel) {

        // 1. Datos
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // 2. Crear gráfica
        JFreeChart chart = ChartFactory.createLineChart(
                "Broker",
                "Tiempo",
                "Precio",
                dataset
        );

        // 3. ChartPanel (ESTO es lo que se agrega)
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(panel.getSize());

        // 4. Limpiar y agregar al JPanel
        panel.removeAll();
        panel.setLayout(new BorderLayout());
        panel.add(chartPanel, BorderLayout.CENTER);

        // 5. Refrescar
        panel.validate();
        panel.repaint();
    }

}
