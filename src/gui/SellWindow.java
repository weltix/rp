/*
 * Copyright (c) RESONANCE JSC, 28.05.2019
 */

package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static gui.FontProvider.*;

/**
 * @author Dmitriy Bludov
 */

public class SellWindow extends JFrame {
    private JPanel contentPane;

    private JPanel sellTablePanel;

    private JPanel infoKeyPadPanel;

    private JPanel paymentInfoPanel;
    private JLabel toPayLabel;
    private JLabel toPaySumLabel;
    private JLabel discountLabel;
    private JLabel discountSumLabel;
    private JButton discountButton;
    private JButton paymentButton;

    private JPanel keyPadPanel;
    private JLabel searchLabel;
    private JTextField barcodeTextField;
    private JButton backSpaceButton;
    private JButton a1Button;
    private JButton a2Button;
    private JButton a3Button;
    private JButton a4Button;
    private JButton a5Button;
    private JButton a6Button;
    private JButton a7Button;
    private JButton a8Button;
    private JButton a9Button;
    private JButton a0Button;
    private JButton dotButton;
    private JButton cButton;
    private JButton searchButton;


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

    private GraphicsDevice graphicsDevice;

    public SellWindow() {
        init();
    }

    private void init() {
//        // hide cursor from current JFrame
//        setCursor(getToolkit().createCustomCursor(
//                new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB),
//                new Point(),
//                null));
//
//        setDefaultCloseOperation(EXIT_ON_CLOSE);
//        setUndecorated(true);        //need for full screen mode
//        setResizable(false);         //need for full screen mode
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
        setSize(1050, 618);
    }

    private void initComponents() {
        FontProvider fontProvider = new FontProvider();
        discountButton.setFont(fontProvider.getFont(ROBOTO_REGULAR, 30f));
        paymentButton.setFont(fontProvider.getFont(ROBOTO_REGULAR, 30f));
        searchButton.setFont(fontProvider.getFont(ROBOTO_REGULAR, 34f));

        a1Button.setFont(fontProvider.getFont(ROBOTO_REGULAR, 50f));
        a2Button.setFont(fontProvider.getFont(ROBOTO_REGULAR, 50f));
        a3Button.setFont(fontProvider.getFont(ROBOTO_REGULAR, 50f));
        a4Button.setFont(fontProvider.getFont(ROBOTO_REGULAR, 50f));
        a5Button.setFont(fontProvider.getFont(ROBOTO_REGULAR, 50f));
        a6Button.setFont(fontProvider.getFont(ROBOTO_REGULAR, 50f));
        a7Button.setFont(fontProvider.getFont(ROBOTO_REGULAR, 50f));
        a8Button.setFont(fontProvider.getFont(ROBOTO_REGULAR, 50f));
        a9Button.setFont(fontProvider.getFont(ROBOTO_REGULAR, 50f));
        a0Button.setFont(fontProvider.getFont(ROBOTO_REGULAR, 50f));
        dotButton.setFont(fontProvider.getFont(ROBOTO_REGULAR, 50f));
        cButton.setFont(fontProvider.getFont(ROBOTO_REGULAR, 50f));

        searchLabel.setFont(fontProvider.getFont(ROBOTO_REGULAR, 22f));

        barcodeTextField.setFont(fontProvider.getFont(ROBOTO_REGULAR, 40f));
        barcodeTextField.setBorder(BorderFactory.createEmptyBorder());

        backSpaceButton.setFont(fontProvider.getFont(FONTAWESOME_REGULAR, 54f));

        toPayLabel.setFont(fontProvider.getFont(ROBOTO_REGULAR, 38f));
        toPaySumLabel.setFont(fontProvider.getFont(ROBOTO_REGULAR, 38f));
        discountLabel.setFont(fontProvider.getFont(ROBOTO_REGULAR, 30f));
        discountSumLabel.setFont(fontProvider.getFont(ROBOTO_REGULAR, 30f));

        resposLabel.setFont(fontProvider.getFont(ROBOTO_BOLD, 22f));
        marketLabel.setFont(fontProvider.getFont(ROBOTO_BOLD, 12f));

        addProductPanel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                addProductPanel.setBackground(new Color(42, 58, 84));
                addProductIcon.setForeground(new Color(194, 208, 215));
                addProductLabel.setForeground(new Color(194, 208, 215));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                addProductPanel.setBackground(new Color(52, 73, 94));
                addProductIcon.setForeground(new Color(255, 255, 255));
                addProductLabel.setForeground(new Color(255, 255, 255));
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
                workWithReceiptPanel.setBackground(new Color(65, 91, 122));
                workWithReceiptIcon.setForeground(new Color(222, 234, 240));
                workWithReceiptLabel.setForeground(new Color(222, 234, 240));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                workWithReceiptPanel.setBackground(new Color(52, 73, 94));
                workWithReceiptIcon.setForeground(new Color(255, 255, 255));
                workWithReceiptLabel.setForeground(new Color(255, 255, 255));
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {
                mouseReleased(e);
            }
        });
        workWithReceiptIcon.setFont(fontProvider.getFont(MENU_ICONS, 58f));
        workWithReceiptLabel.setFont(fontProvider.getFont(ROBOTO_REGULAR, 16f));
        cashboxPanel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                cashboxPanel.setBackground(new Color(65, 91, 122));
                cashboxIcon.setForeground(new Color(222, 234, 240));
                cashboxLabel.setForeground(new Color(222, 234, 240));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                cashboxPanel.setBackground(new Color(52, 73, 94));
                cashboxIcon.setForeground(new Color(255, 255, 255));
                cashboxLabel.setForeground(new Color(255, 255, 255));
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
                servicePanel.setBackground(new Color(65, 91, 122));
                serviceIcon.setForeground(new Color(222, 234, 240));
                serviceLabel.setForeground(new Color(222, 234, 240));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                servicePanel.setBackground(new Color(52, 73, 94));
                serviceIcon.setForeground(new Color(255, 255, 255));
                serviceLabel.setForeground(new Color(255, 255, 255));
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
                exitPanel.setBackground(new Color(42, 58, 84));
                exitIcon.setForeground(new Color(194, 208, 215));
                exitLabel.setForeground(new Color(194, 208, 215));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                exitPanel.setBackground(new Color(52, 73, 94));
                exitIcon.setForeground(new Color(255, 255, 255));
                exitLabel.setForeground(new Color(255, 255, 255));
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