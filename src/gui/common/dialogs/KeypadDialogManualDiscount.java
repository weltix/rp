/*
 * Copyright (c) RESONANCE JSC, 13.09.2019
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

        Font robotoRegular30 = FontProvider.getInstance().getFont(FontProvider.ROBOTO_REGULAR, 30);
        percentButton.setFont(FontProvider.getInstance().getFont(FontProvider.ROBOTO_BOLD, 38));
        moneyButton.setFont(robotoRegular30);
        productButton.setFont(robotoRegular30);
        receiptButton.setFont(robotoRegular30);

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

//                parentFrame.setCardOfMainSellPanelScreens("sellPanel");

                glassPane.deactivate();
                keypadPanel.getTextField().setText("");
                // this delay - workaround for weak hardware (makes rendering faster when glassPane disappears)
                Timer timer = new Timer(0, this);
                timer.setInitialDelay(10);
                timer.setActionCommand("delayBeforeClosingThisWindow");
                timer.start();
                break;
            default:
                break;
        }
    }
}
