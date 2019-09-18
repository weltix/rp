/*
 * Copyright (c) RESONANCE JSC, 18.09.2019
 */

package gui.common.dialogs;

import gui.common.KeypadPanel;
import gui.fonts.FontProvider;
import resources.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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

        GridBagConstraints constraintsExtraPanel = gbLayoutMainPanel.getConstraints(extraPanel);
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
        cashAmountLabel.setText(Resources.getInstance().getString("cash_in_cashbox")
                .concat(String.format(Locale.ROOT, "%.2f", cashInBox)));

        depositButton.addActionListener(this::actionPerformed);
        withdrawButton.addActionListener(this::actionPerformed);

        keypadPanel.setActionButtonsAmount(2);
        keypadPanel.getActionButton1().setText(Resources.getInstance().getString("deposit"));
        keypadPanel.getActionButton1().addActionListener(this::actionPerformed);
        keypadPanel.getActionButton2().setText(Resources.getInstance().getString("cancel"));
        keypadPanel.getActionButton2().addActionListener(this::actionPerformed);

        this.addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                super.windowGainedFocus(e);
                keypadPanel.getTextField().requestFocusInWindow();
                keypadPanel.getTextField().setText(String.format(Locale.ROOT,"%.2f", cashInBox));
                keypadPanel.getTextField().selectAll();
            }
        });
        keypadPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                super.componentShown(e);
                System.out.println("componentShown");
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
                // Returns initial mainPanel of main_frame.form. Does not affect performance.
                // Need to call, because previously MainFrame#setContentPane(jlayer) possibly was called for blurring of background.
                parentFrame.setContentPane(null);
                glassPane.deactivate();
                keypadPanel.getTextField().setText("");
                // this delay - workaround for weak hardware (makes rendering faster when glassPane disappears)
                Timer timer = new Timer(0, this);
                timer.setInitialDelay(10);
                timer.setActionCommand("delayBeforeClosingThisWindow");
                timer.start();
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