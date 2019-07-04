/*
 * Copyright (c) RESONANCE JSC, 28.05.2019
 */

package gui;

import gui.custom_components.KeypadPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import static gui.FontProvider.*;

/**
 * @author Dmitriy Bludov
 */

public class SellWindow extends JFrame {
    private JPanel contentPane;

    private JLabel toPayLabel;
    private JLabel toPaySumLabel;
    private JLabel discountLabel;
    private JLabel discountSumLabel;
    private JButton discountButton;
    private JButton paymentButton;


    private JLabel resposLabel;
    private JLabel marketLabel;
    private JPanel addProductPanel;
    private JLabel addProductIcon;
    private JLabel addProductLabel;
    private JPanel workWithReceiptPanel;
    private JLabel workWithReceiptIcon;
    private JLabel workWithReceiptLabel;
    private JPanel cashboxPanel;
    private JLabel cashboxIcon;
    private JLabel cashboxLabel;
    private JPanel servicePanel;
    private JLabel serviceIcon;
    private JLabel serviceLabel;
    private JPanel exitPanel;
    private JLabel exitIcon;
    private JLabel exitLabel;
    private JPanel sellTablePanel;
    private JPanel paymentInfoPanel;
    private JPanel infoKeyPadPanel;
    private JPanel keyPadPanel;
    private JTable sellTable;
    private KeypadPanel keypadPanel1;
    private JButton button6;
    private JButton button2;
    private JButton button3;

    private GraphicsDevice graphicsDevice;

    public SellWindow() {
        init();
    }

    private void init() {
        // hide cursor from current JFrame
        setCursor(getToolkit().createCustomCursor(
                new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB),
                new Point(),
                null));

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setUndecorated(true);        //need for full screen mode
        setResizable(false);         //need for full screen mode

        // set full screen exclusive mode
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        graphicsDevice = ge.getDefaultScreenDevice();
        graphicsDevice.setFullScreenWindow(this);

        initComponents();
        setContentPane(contentPane);
        setVisible(true);


        // 1.32 - physical scale rate relate to my display
        // 1,9 - font scale for next parameters for debugging
        // next parameters make window for my monitor with physical dimensions like real 14' POS
//        setSize(1050, 618);

        JWindow win2 = new JWindow(this);
        win2.setCursor(getToolkit().createCustomCursor(
                new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB),
                new Point(),
                null));
        button2.addActionListener(actionEvent -> win2.setVisible(true));
        win2.setSize(1920, 1080);
        Button closeButton = new Button("CLOSE");
        closeButton.addActionListener(actionEvent -> win2.setVisible(false));
        win2.add(closeButton);
    }

    private void initComponents() {
        FontProvider fontProvider = new FontProvider();
        discountButton.setFont(fontProvider.getFont(ROBOTO_REGULAR, 30f));
        paymentButton.setFont(fontProvider.getFont(ROBOTO_REGULAR, 30f));

        toPayLabel.setFont(fontProvider.getFont(ROBOTO_REGULAR, 38f));
        toPaySumLabel.setFont(fontProvider.getFont(ROBOTO_REGULAR, 38f));
        discountLabel.setFont(fontProvider.getFont(ROBOTO_REGULAR, 30f));
        discountSumLabel.setFont(fontProvider.getFont(ROBOTO_REGULAR, 30f));

        resposLabel.setFont(fontProvider.getFont(ROBOTO_BOLD, 22f));
        marketLabel.setFont(fontProvider.getFont(ROBOTO_BOLD, 12f));

        Color navPanelColor = new Color(52, 73, 94);
        Color navPanelPressed = new Color(65, 91, 122);
        Color navIconLabelPressed = new Color(172, 194, 215);

        addProductPanel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                addProductPanel.setBackground(navPanelPressed);
                addProductIcon.setForeground(navIconLabelPressed);
                addProductLabel.setForeground(navIconLabelPressed);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                addProductPanel.setBackground(navPanelColor);
                addProductIcon.setForeground(Color.WHITE);
                addProductLabel.setForeground(Color.WHITE);
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {
                mouseReleased(e);
            }
        });
        addProductIcon.setFont(fontProvider.getFont(MENU_ICONS, 58f));
        addProductLabel.setFont(fontProvider.getFont(ROBOTO_REGULAR, 16f));

        workWithReceiptPanel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                workWithReceiptPanel.setBackground(navPanelPressed);
                workWithReceiptIcon.setForeground(navIconLabelPressed);
                workWithReceiptLabel.setForeground(navIconLabelPressed);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                workWithReceiptPanel.setBackground(navPanelColor);
                workWithReceiptIcon.setForeground(Color.WHITE);
                workWithReceiptLabel.setForeground(Color.WHITE);
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {
                mouseReleased(e);
            }
        });
        workWithReceiptIcon.setFont(fontProvider.getFont(MENU_ICONS, 56f));
        workWithReceiptLabel.setFont(fontProvider.getFont(ROBOTO_REGULAR, 16f));

        cashboxPanel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                cashboxPanel.setBackground(navPanelPressed);
                cashboxIcon.setForeground(navIconLabelPressed);
                cashboxLabel.setForeground(navIconLabelPressed);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                cashboxPanel.setBackground(navPanelColor);
                cashboxIcon.setForeground(Color.WHITE);
                cashboxLabel.setForeground(Color.WHITE);
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {
                mouseReleased(e);
            }
        });
        cashboxIcon.setFont(fontProvider.getFont(MENU_ICONS, 58f));
        cashboxLabel.setFont(fontProvider.getFont(ROBOTO_REGULAR, 16f));

        servicePanel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                servicePanel.setBackground(navPanelPressed);
                serviceIcon.setForeground(navIconLabelPressed);
                serviceLabel.setForeground(navIconLabelPressed);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                servicePanel.setBackground(navPanelColor);
                serviceIcon.setForeground(Color.WHITE);
                serviceLabel.setForeground(Color.WHITE);
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {
                mouseReleased(e);
            }
        });
        serviceIcon.setFont(fontProvider.getFont(MENU_ICONS, 58f));
        serviceLabel.setFont(fontProvider.getFont(ROBOTO_REGULAR, 16f));

        exitPanel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                exitPanel.setBackground(navPanelPressed);
                exitIcon.setForeground(navIconLabelPressed);
                exitLabel.setForeground(navIconLabelPressed);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                exitPanel.setBackground(navPanelColor);
                exitIcon.setForeground(Color.WHITE);
                exitLabel.setForeground(Color.WHITE);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
                mouseReleased(e);
            }
        });
        exitIcon.setFont(fontProvider.getFont(MENU_ICONS, 58f));
        exitLabel.setFont(fontProvider.getFont(ROBOTO_REGULAR, 16f));
    }
}