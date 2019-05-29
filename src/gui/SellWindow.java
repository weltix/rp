/*
 * Copyright (c) RESONANCE JSC, 28.05.2019
 */

package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Dmitriy Bludov
 */

public class SellWindow extends JFrame {
    private JPanel panel1;
    private JButton button1;
    private GraphicsDevice graphicsDevice;

    public SellWindow() {
        initComponents();
    }

    private void initComponents() {
        // remove cursor from current JFrame
        setCursor(getToolkit().createCustomCursor(
                new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB),
                new Point(),
                null));

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        setContentPane(panel1);

        // set full screen exclusive mode
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        graphicsDevice = ge.getDefaultScreenDevice();
        graphicsDevice.setFullScreenWindow(this);

        setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
