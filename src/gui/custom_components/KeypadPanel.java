/*
 * Copyright (c) RESONANCE JSC, 01.07.2019
 */

package gui.custom_components;

import gui.FontProvider;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static gui.FontProvider.FONTAWESOME_REGULAR;
import static gui.FontProvider.ROBOTO_REGULAR;

public class KeypadPanel implements ActionListener {
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
    private JButton actionButton1;
    private JButton actionButton2;
    private JPanel centerPanel;

    StringBuilder tempStr = new StringBuilder();

    public KeypadPanel() {

        FontProvider fontProvider = new FontProvider();
        Font robotoRegular34 = fontProvider.getFont(ROBOTO_REGULAR, 34f);
        Font robotoRegular50 = fontProvider.getFont(ROBOTO_REGULAR, 50f);

        searchButton.setFont(robotoRegular34);
        actionButton1.setFont(robotoRegular34);
        actionButton2.setFont(robotoRegular34);

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

        a0Button.addActionListener(e -> changeBarcodeTextField('0'));
        a1Button.addActionListener(e -> changeBarcodeTextField('1'));
        a2Button.addActionListener(e -> changeBarcodeTextField('2'));
        a3Button.addActionListener(e -> changeBarcodeTextField('3'));
        a4Button.addActionListener(e -> changeBarcodeTextField('4'));
        a5Button.addActionListener(e -> changeBarcodeTextField('5'));
        a6Button.addActionListener(e -> changeBarcodeTextField('6'));
        a7Button.addActionListener(e -> changeBarcodeTextField('7'));
        a8Button.addActionListener(e -> changeBarcodeTextField('8'));
        a9Button.addActionListener(e -> changeBarcodeTextField('9'));
        dotButton.addActionListener(e -> changeBarcodeTextField('.'));
        cButton.addActionListener(e -> changeBarcodeTextField('C'));
        backSpaceButton.addActionListener(e -> changeBarcodeTextField('\b'));


        Timer timer = new Timer(0, this);
        timer.setInitialDelay(250);

        if (centerPanel instanceof Container) {
            for (Component child : centerPanel.getComponents()) {
                if (child instanceof JButton) {

                    child.addMouseListener(new MouseListener() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            System.out.println(e.getSource().getText);
                        }

                        @Override
                        public void mousePressed(MouseEvent e) {
//                            timer.setActionCommand(ch);
                            timer.start();
                        }

                        @Override
                        public void mouseReleased(MouseEvent e) {
                            timer.stop();
                        }

                        @Override
                        public void mouseEntered(MouseEvent e) {
                        }

                        @Override
                        public void mouseExited(MouseEvent e) {
                        }
                    });
                }
            }
        }


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
    }

    private void changeBarcodeTextField(char ch) {
        tempStr = new StringBuilder(barcodeTextField.getText());
        if (ch == '\b') {
            if (tempStr.length() > 0)
                tempStr.delete(tempStr.length() - 1, tempStr.length());
        } else if (ch == 'C')
            tempStr.delete(0, tempStr.length());
        else
            tempStr.append(ch);
        barcodeTextField.setText(tempStr.toString());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        backSpaceButton.doClick();
    }
}
