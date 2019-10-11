/*
 * Copyright (c) RESONANCE JSC, 11.10.2019
 */

package gui.common.dialogs;

import gui.common.components.KeypadPanel;
import gui.fonts.FontProvider;
import resources.Resources;

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

    public KeypadDialogPayment(Frame owner) {
        super(owner);

        paymentPanel.setVisible(true);
        extraPanel.setVisible(true);

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
        keypadPanel.getActionButton1().setText(Resources.getInstance().getString("deposit"));
        keypadPanel.getActionButton1().addActionListener(this::actionPerformed);
        keypadPanel.getActionButton2().setText(Resources.getInstance().getString("cancel"));
        keypadPanel.getActionButton2().addActionListener(this::actionPerformed);
    }

    /**
     * More detailed description look in superclass.
     * Action commands for buttons have been assigned in bound *.form file.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(extraPanel.getSize() + " pay");
        super.actionPerformed(e);
        switch (e.getActionCommand()) {
            case "actionButton1":
                break;
            case "actionButton2":
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
