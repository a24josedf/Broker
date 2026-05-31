package com.mycompany.broker.model;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Graphic {

    private final PriceChartPanel chartPanel = new PriceChartPanel();
    private final JLabel priceLabel = new JLabel();
    private final JLabel maxLabel = new JLabel();
    private final JLabel minLabel = new JLabel();
    private final JLabel tradesLabel = new JLabel();
    private final JLabel volumeLabel = new JLabel();

    public static Graphic addGraphic(JPanel panel) {
        Graphic graphic = new Graphic();
        graphic.install(panel);
        return graphic;
    }

    private void install(JPanel panel) {
        JPanel indicators = new JPanel(new GridLayout(1, 5, 8, 0));
        indicators.add(priceLabel);
        indicators.add(maxLabel);
        indicators.add(minLabel);
        indicators.add(tradesLabel);
        indicators.add(volumeLabel);

        panel.removeAll();
        panel.setLayout(new BorderLayout(8, 8));
        panel.add(indicators, BorderLayout.NORTH);
        panel.add(chartPanel, BorderLayout.CENTER);
        panel.validate();
        panel.repaint();
    }

    public void update(MainModel model) {
        chartPanel.setPrices(model.getPrices());
        priceLabel.setText(String.format("Current: %.2f", model.getCurrentPrice()));
        maxLabel.setText(String.format("High: %.2f", model.getSessionMax()));
        minLabel.setText(String.format("Low: %.2f", model.getSessionMin()));
        tradesLabel.setText("Trades: " + model.getExecutedTradesCount());
        volumeLabel.setText("Volume: " + model.getTradedVolume());
    }

    private static class PriceChartPanel extends JPanel {

        private List<Double> prices = new ArrayList<>();

        PriceChartPanel() {
            setPreferredSize(new Dimension(800, 420));
            setBackground(Color.WHITE);
        }

        void setPrices(List<Double> prices) {
            int start = Math.max(0, prices.size() - 120);
            this.prices = new ArrayList<>(prices.subList(start, prices.size()));
            repaint();
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            Graphics2D g2 = (Graphics2D) graphics.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int left = 55;
            int right = 20;
            int top = 20;
            int bottom = 40;
            int width = getWidth() - left - right;
            int height = getHeight() - top - bottom;

            g2.setColor(new Color(230, 230, 230));
            g2.drawRect(left, top, width, height);
            for (int i = 1; i < 5; i++) {
                int y = top + (height * i / 5);
                g2.drawLine(left, y, left + width, y);
            }

            if (prices.isEmpty()) {
                g2.dispose();
                return;
            }

            double min = prices.stream().mapToDouble(Double::doubleValue).min().orElse(0);
            double max = prices.stream().mapToDouble(Double::doubleValue).max().orElse(1);
            if (Math.abs(max - min) < 0.01) {
                max += 1;
                min -= 1;
            }

            g2.setColor(Color.DARK_GRAY);
            g2.drawString(String.format("%.2f", max), 8, top + 5);
            g2.drawString(String.format("%.2f", min), 8, top + height);
            g2.drawString("Time", left + width / 2 - 20, getHeight() - 10);

            g2.setColor(new Color(37, 99, 235));
            g2.setStroke(new BasicStroke(2.2f));
            for (int i = 1; i < prices.size(); i++) {
                int x1 = left + (int) ((i - 1) * (width / Math.max(1.0, prices.size() - 1.0)));
                int x2 = left + (int) (i * (width / Math.max(1.0, prices.size() - 1.0)));
                int y1 = top + height - (int) ((prices.get(i - 1) - min) * height / (max - min));
                int y2 = top + height - (int) ((prices.get(i) - min) * height / (max - min));
                g2.drawLine(x1, y1, x2, y2);
            }
            g2.dispose();
        }
    }
}
