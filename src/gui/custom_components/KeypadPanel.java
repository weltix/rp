/*
 * Copyright (c) RESONANCE JSC, 01.07.2019
 */

package gui.custom_components;

import gui.FontProvider;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static gui.FontProvider.FONTAWESOME_REGULAR;
import static gui.FontProvider.ROBOTO_REGULAR;
import static javax.swing.JLayeredPane.DRAG_LAYER;

public class KeypadPanel extends JFrame {
    private JLabel searchLabel;
    private JButton searchButton;
    private JButton a0Button;
    private JButton dotButton;
    private JButton cButton;
    private JButton a7Button;
    private JButton a8Button;
    private JButton a9Button;
    private JButton a4Button;
    private JButton a5Button;
    private JButton a6Button;
    private JButton a1Button;
    private JButton a2Button;
    private JButton a3Button;
    private JTextField barcodeTextField;
    private JButton backSpaceButton;
    private JPanel keyPadPanel;
    private JPanel parentPanel;
    private JPanel actionButtonPanel;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JPanel centerPanel;

    public KeypadPanel() {
        FontProvider fontProvider = new FontProvider();
        Font robotoRegular50 = fontProvider.getFont(ROBOTO_REGULAR, 50f);

        searchButton.setFont(fontProvider.getFont(ROBOTO_REGULAR, 34f));

        a1Button.setFont(robotoRegular50);
        a2Button.setFont(robotoRegular50);
        a3Button.setFont(robotoRegular50);
        a4Button.setFont(robotoRegular50);
        a5Button.setFont(robotoRegular50);
        a6Button.setFont(robotoRegular50);
        a7Button.setFont(robotoRegular50);
        a8Button.setFont(robotoRegular50);
        a9Button.setFont(robotoRegular50);
        a0Button.setFont(robotoRegular50);
        dotButton.setFont(robotoRegular50);
        cButton.setFont(robotoRegular50);

        searchLabel.setFont(fontProvider.getFont(ROBOTO_REGULAR, 22f));

        barcodeTextField.setFont(fontProvider.getFont(ROBOTO_REGULAR, 40f));
        barcodeTextField.setBorder(BorderFactory.createEmptyBorder());

        backSpaceButton.setFont(fontProvider.getFont(FONTAWESOME_REGULAR, 54f));

        a1Button.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                a1Button.setBackground(new Color(255, 255, 255));
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

//        GridBagLayout centerPanelLayout = (GridBagLayout) centerPanel.getLayout();
//        GridBagConstraints constraintsForA0Button = centerPanelLayout.getConstraints(a0Button);
//        GridBagConstraints constraintsForDotButton = centerPanelLayout.getConstraints(dotButton);
//
//        constraintsForA0Button.gridwidth = 3;
//        centerPanel.remove(dotButton);
//        centerPanel.add(a0Button, constraintsForA0Button);
//
//        constraintsForA0Button.gridwidth = 1;
//        centerPanel.add(dotButton, constraintsForDotButton);
//        centerPanel.add(a0Button, constraintsForA0Button);

        JLayeredPane layeredPane = getRootPane().getLayeredPane();
        layeredPane.add(new Label("2135412354234"), DRAG_LAYER);
        setContentPane(parentPanel);
//        setVisible(true);
    }
}
