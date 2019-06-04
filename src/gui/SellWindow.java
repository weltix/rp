/*
 * Copyright (c) RESONANCE JSC, 28.05.2019
 */

package gui;

import resources.fonts.FontProvider;

import javax.swing.*;
import java.awt.*;

import static resources.fonts.FontProvider.FONTAWESOME_REGULAR;
import static resources.fonts.FontProvider.ROBOTO_REGULAR;

/**
 * @author Dmitriy Bludov
 */

public class SellWindow extends JFrame {
    private JPanel contentPane;
    private JPanel navigationPanel;
    private JPanel sellTablePanel;
    private JPanel infoKeyPadPanel;
    private JPanel keyPadPanel;
    private JPanel paymentInfoPanel;
    private JButton discountButton;
    private JButton paymentButton;
    private JButton a4Button;
    private JButton a7Button;
    private JButton a0Button;
    private JButton searchButton;
    private JButton a1Button;
    private JButton a2Button;
    private JButton a5Button;
    private JButton a8Button;
    private JButton dotButton;
    private JButton a3Button;
    private JButton a6Button;
    private JButton a9Button;
    private JButton cButton;
    private JLabel searchLabel;
    private JTextField searchGoodsTextField;
    private JButton backSpaceButton;
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
        discountButton.setFont(fontProvider.getFont(ROBOTO_REGULAR, 28f));
        paymentButton.setFont(fontProvider.getFont(ROBOTO_REGULAR, 28f));
        searchButton.setFont(fontProvider.getFont(ROBOTO_REGULAR, 32f));

        keyPadPanel.setFont(fontProvider.getFont(ROBOTO_REGULAR, 50f));
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
        searchLabel.setFont(fontProvider.getFont(ROBOTO_REGULAR, 20f));

        searchGoodsTextField.setFont(fontProvider.getFont(ROBOTO_REGULAR, 40f));
        searchGoodsTextField.setBorder(BorderFactory.createEmptyBorder());

        backSpaceButton.setFont(fontProvider.getFont(FONTAWESOME_REGULAR, 54f));
        backSpaceButton.setText("\uf104");
    }



}
