/*
 * Copyright (c) RESONANCE JSC, 16.09.2019
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
 * ("percents" or "money") and {"product" or "receipt"}. Class is intended to set particular discount.
 * Parent abstract class {@link KeypadDialog} contains JPanel {@link KeypadDialog#extraPanel}, which has
 * CardLayout as layout manager, and contains another JPanels different for different subclasses.
 */
public class KeypadDialogManualDiscount extends KeypadDialog {

    private Color blueColor = new Color(53, 152, 219);
    private Color beigeColor = new Color(235, 235, 235);

    /**
     * Constructor tunes the look of this dialog, and sets action for action button.
     * Constructor of parent is called initially.
     *
     * @param owner {@link Frame} object, from which this window was called
     */
    public KeypadDialogManualDiscount(Frame owner) {
        super(owner);

        extraPanel.setVisible(true);
        cardPanelLayout.show(cardPanel, "discountPanel");

        dialogTitle.setText(Resources.getInstance().getString("manual_discount"));
        dialogHint.setText(Resources.getInstance().getString("hint_set_discount"));

        Font robotoRegular30 = FontProvider.getInstance().getFont(FontProvider.ROBOTO_REGULAR, 30);
        percentButton.setFont(FontProvider.getInstance().getFont(FontProvider.ROBOTO_BOLD, 38));
        moneyButton.setFont(robotoRegular30);
        productButton.setFont(robotoRegular30);
        receiptButton.setFont(robotoRegular30);

        percentButton.addActionListener(this::actionPerformed);
        moneyButton.addActionListener(this::actionPerformed);
        productButton.addActionListener(this::actionPerformed);
        receiptButton.addActionListener(this::actionPerformed);

        keypadPanel.setActionButtonsAmount(1);
        keypadPanel.getActionButton0().setText(Resources.getInstance().getString("set_discount"));
        keypadPanel.getActionButton0().addActionListener(this::actionPerformed);
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
            case "actionButton0":
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
            case "percentButton":
                percentButton.setBackground(blueColor);
                percentButton.setForeground(Color.WHITE);
                moneyButton.setBackground(beigeColor);
                moneyButton.setForeground(Color.BLACK);
                break;
            case "moneyButton":
                percentButton.setBackground(beigeColor);
                percentButton.setForeground(Color.BLACK);
                moneyButton.setBackground(blueColor);
                moneyButton.setForeground(Color.WHITE);
                break;
            case "productButton":
                productButton.setBackground(blueColor);
                productButton.setForeground(Color.WHITE);
                receiptButton.setBackground(beigeColor);
                receiptButton.setForeground(Color.BLACK);
                break;
            case "receiptButton":
                productButton.setBackground(beigeColor);
                productButton.setForeground(Color.BLACK);
                receiptButton.setBackground(blueColor);
                receiptButton.setForeground(Color.WHITE);
                break;
            default:
                break;
        }
    }
}
