/*
 * Copyright (c) RESONANCE JSC, 11.10.2019
 */

package gui.common.dialogs;

import gui.common.KeypadPanel;
import gui.fonts.FontProvider;
import resources.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static gui.fonts.FontProvider.ROBOTO_BOLD;
import static gui.fonts.FontProvider.ROBOTO_REGULAR;

/**
 * Class for dialog window, that contains {@link KeypadPanel} and additional payment panel.
 * Class is intended to close receipt by making payment.
 * Parent abstract class {@link KeypadDialog} contains JPanel {@link KeypadDialog#paymentPanel}, which we will make
 * visible in this class.
 */
public class KeypadDialogPayment extends KeypadDialog {
    private JPanel mainPanel;
    private KeypadPanel keypadPanel;
    private JButton cashButton;
    private JButton cardButton;
    private JLabel toPayLabel;
    private JLabel toPaySumLabel;
    private JLabel paymentFormLabel;
    private JLabel paymentFormSumLabel;
    private JLabel mustBePaidLabel;
    private JLabel mustBePaidSumLabel;
    private JPanel centerPanel;
    private JComboBox comboBox1;

    public KeypadDialogPayment(Frame owner) {
        super(owner);
        this.setContentPane(mainPanel);

        paymentPanel.setVisible(true);


        Font robotoRegular38 = FontProvider.getInstance().getFont(ROBOTO_REGULAR, 38);
        cashButton.setFont(robotoRegular38);
        cardButton.setFont(robotoRegular38);
        toPayLabel.setFont(FontProvider.getInstance().getFont(ROBOTO_BOLD, 52));
        toPaySumLabel.setFont(FontProvider.getInstance().getFont(ROBOTO_BOLD, 52));
        paymentFormLabel.setFont(FontProvider.getInstance().getFont(ROBOTO_REGULAR, 40));
        paymentFormSumLabel.setFont(FontProvider.getInstance().getFont(ROBOTO_REGULAR, 40));
        mustBePaidLabel.setFont(FontProvider.getInstance().getFont(ROBOTO_BOLD, 46));
        mustBePaidSumLabel.setFont(FontProvider.getInstance().getFont(ROBOTO_BOLD, 46));

        cashButton.addActionListener(this::actionPerformed);
        cardButton.addActionListener(this::actionPerformed);

        keypadPanel.setActionButtonsAmount(2);
        keypadPanel.getActionButton1().setText(Resources.getInstance().getString("pay_in"));
    }

    /**
     * More detailed description look in superclass.
     * Action commands for buttons have been assigned in bound *.form file.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        switch (e.getActionCommand()) {
            case "cancelButton":
                close();
                keypadPanel.getTextField().setText("");
                break;
            case "cashButton":
                cashButton.setBackground(blueColor);
                cashButton.setForeground(Color.WHITE);
                cardButton.setBackground(beigeColor);
                cardButton.setForeground(Color.BLACK);
                paymentFormLabel.setText(Resources.getInstance().getString("cash"));
                break;
            case "cardButton":
                cashButton.setBackground(beigeColor);
                cashButton.setForeground(Color.BLACK);
                cardButton.setBackground(blueColor);
                cardButton.setForeground(Color.WHITE);
                paymentFormLabel.setText(Resources.getInstance().getString("card"));
                break;
            default:
                break;
        }
    }
}
