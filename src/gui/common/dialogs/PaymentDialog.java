/*
 * Copyright (c) RESONANCE JSC, 13.08.2019
 */

package gui.common.dialogs;

import gui.common.KeypadPanel;
import gui.common.MainFrame;
import gui.common.utility_components.GlassPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PaymentDialog  extends JWindow implements ActionListener {
    private JPanel mainPanel;
    private KeypadPanel keypadPanel;
    private JButton cashButton;
    private JButton cardButton;
    private JButton cancelButton;
    private JButton payButton;
    private JLabel toPayLabel;
    private JLabel toPaySumLabel;
    private JLabel paymentFormLabel;
    private JLabel paymentFormSumLabel;
    private JLabel mustBePaidLabel;
    private JLabel mustBePaidSumLabel;
    private JPanel centerPanel;

    private GlassPane glassPane;
    private MainFrame parentFrame;

    public PaymentDialog(Frame owner) {
        super(owner);
        this.setContentPane(mainPanel);

        if (getParent() instanceof MainFrame)
            parentFrame = (MainFrame) getParent();
        if (parentFrame.getGlassPane() instanceof GlassPane) {
            glassPane = (GlassPane) parentFrame.getGlassPane();
        }

        cancelButton.addActionListener(e -> {
            glassPane.deactivate();
            this.dispose();
        });

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getActionCommand() + " ");
        if ("cancelButton".equals(e.getActionCommand()))
            this.dispose();

    }
}
