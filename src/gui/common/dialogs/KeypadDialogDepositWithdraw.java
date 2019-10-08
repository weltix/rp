/*
 * Copyright (c) RESONANCE JSC, 08.10.2019
 */

package gui.common.dialogs;

import gui.common.KeypadPanel;
import gui.fonts.FontProvider;
import resources.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Class for dialog window, that contains {@link KeypadPanel} and additional toggle buttons for choosing
 * ("deposit" or "withdraw"). Class is intended to deposit or withdraw cash.
 * Parent abstract class {@link KeypadDialog} contains JPanel {@link KeypadDialog#extraPanel}, which has
 * CardLayout as layout manager, and contains another JPanels different for different subclasses.
 */
public class KeypadDialogDepositWithdraw extends KeypadDialog {

    // used for toggle buttons
    private Color blueColor = new Color(53, 152, 219);
    private Color beigeColor = new Color(235, 235, 235);

    /**
     * Constructor tunes up the look of this dialog, and sets actions for action buttons.
     * Constructor of parent is called initially.
     *
     * @param owner {@link Frame} object, from which this window was called
     */
    public KeypadDialogDepositWithdraw(Frame owner) {
        super(owner);

        extraPanel.setVisible(true);
        cardPanelLayout.show(cardPanel, "depositWithdrawPanel");

        // 19% - weight in Y axis of extraPanel (100% - original height, and we add 19% of extraPanel. Totally 119%)
        constraintsExtraPanel.weighty = 19;
        mainPanel.remove(extraPanel);
        mainPanel.add(extraPanel, constraintsExtraPanel);

        dialogTitle.setText(Resources.getInstance().getString("deposit_withdraw"));
        dialogHint.setText(Resources.getInstance().getString("hint_deposit_withdraw"));

        Font robotoRegular30 = FontProvider.getInstance().getFont(FontProvider.ROBOTO_REGULAR, 30);
        depositButton.setFont(robotoRegular30);
        withdrawButton.setFont(robotoRegular30);
        cashAmountLabel.setFont(robotoRegular30);

        depositButton.addActionListener(this::actionPerformed);
        withdrawButton.addActionListener(this::actionPerformed);

        keypadPanel.setTextFieldDocument("money72");
        keypadPanel.setActionButtonsAmount(2);
        keypadPanel.getActionButton1().setText(Resources.getInstance().getString("deposit"));
        keypadPanel.getActionButton1().addActionListener(this::actionPerformed);
        keypadPanel.getActionButton2().setText(Resources.getInstance().getString("cancel"));
        keypadPanel.getActionButton2().addActionListener(this::actionPerformed);
    }

    /**
     * @param extraData text for text field and label of dialog.
     *
     * @see {@link KeypadDialog#setTextFieldText(String)}
     */
    @Override
    public void setTextFieldText(String extraData) {
        cashAmountLabel.setText(Resources.getInstance().getString("cash_in_cashbox").concat(extraData));
        SwingUtilities.invokeLater(() -> super.setTextFieldText(extraData));    // use invokeLater to let execute string above first
    }

    /**
     * Action commands for buttons have been assigned in bound *.form file.
     *
     * @see {@link AbstractDialog#actionPerformed(ActionEvent)}
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        switch (e.getActionCommand()) {
            case "actionButton1":
                break;
            case "actionButton2":
                close();
                break;
            case "depositButton":
                depositButton.setBackground(blueColor);
                depositButton.setForeground(Color.WHITE);
                withdrawButton.setBackground(beigeColor);
                withdrawButton.setForeground(Color.BLACK);
                keypadPanel.getActionButton1().setText(Resources.getInstance().getString("deposit_action"));
                keypadPanel.getTextField().requestFocus();      // keeps text selection if it was selected already
                break;
            case "withdrawButton":
                depositButton.setBackground(beigeColor);
                depositButton.setForeground(Color.BLACK);
                withdrawButton.setBackground(blueColor);
                withdrawButton.setForeground(Color.WHITE);
                keypadPanel.getActionButton1().setText(Resources.getInstance().getString("withdraw_action"));
                keypadPanel.getTextField().requestFocus();      // keeps text selection if it was selected already
                break;
            default:
                break;
        }
    }

}