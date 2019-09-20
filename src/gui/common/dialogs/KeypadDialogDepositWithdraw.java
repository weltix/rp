/*
 * Copyright (c) RESONANCE JSC, 20.09.2019
 */

package gui.common.dialogs;

import gui.common.KeypadPanel;
import gui.fonts.FontProvider;
import resources.Resources;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Locale;

/**
 * Class for dialog window, that contains {@link KeypadPanel} and additional toggle buttons for choosing
 * ("deposit" or "withdraw"). Class is intended to deposit or withdraw cash.
 * Parent abstract class {@link KeypadDialog} contains JPanel {@link KeypadDialog#extraPanel}, which has
 * CardLayout as layout manager, and contains another JPanels different for different subclasses.
 */
public class KeypadDialogDepositWithdraw extends KeypadDialog {

    private Color blueColor = new Color(53, 152, 219);
    private Color beigeColor = new Color(235, 235, 235);

    private Double cashInBox = 5000000.00;
    private String cashInBoxString = String.format(Locale.ROOT, "%.2f", cashInBox);

    /**
     * Constructor tunes the look of this dialog, and sets actions for action buttons.
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

        keypadPanel.setFormattedTextField(7, 2);
        keypadPanel.setActionButtonsAmount(2);
        keypadPanel.getActionButton1().setText(Resources.getInstance().getString("deposit"));
        keypadPanel.getActionButton1().addActionListener(this::actionPerformed);
        keypadPanel.getActionButton2().setText(Resources.getInstance().getString("cancel"));
        keypadPanel.getActionButton2().addActionListener(this::actionPerformed);

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                cashAmountLabel.setText(Resources.getInstance().getString("cash_in_cashbox").concat(cashInBoxString));
                keypadPanel.getTextField().setText(cashInBoxString);
                keypadPanel.getTextField().requestFocus();
                keypadPanel.getTextField().selectAll();
            }
        });
    }

    /**
     * Method is called when action occurs (i.e. button pressed or timer triggers).
     *
     * @param e event, that occurs.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        // actionCommands for buttons assigned in bounded *.form file
        switch (e.getActionCommand()) {
            case "actionButton1":
                break;
            case "actionButton2":
                prepareToDispose();
                break;
            case "depositButton":
                depositButton.setBackground(blueColor);
                depositButton.setForeground(Color.WHITE);
                withdrawButton.setBackground(beigeColor);
                withdrawButton.setForeground(Color.BLACK);
                keypadPanel.getActionButton1().setText(Resources.getInstance().getString("deposit_action"));
                keypadPanel.getTextField().requestFocusInWindow();
                break;
            case "withdrawButton":
                depositButton.setBackground(beigeColor);
                depositButton.setForeground(Color.BLACK);
                withdrawButton.setBackground(blueColor);
                withdrawButton.setForeground(Color.WHITE);
                keypadPanel.getActionButton1().setText(Resources.getInstance().getString("withdraw_action"));
                keypadPanel.getTextField().requestFocusInWindow();
                break;
            default:
                break;
        }
    }

}