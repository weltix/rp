/*
 * Copyright (c) RESONANCE JSC, 11.10.2019
 */

package gui.common.dialogs;

import gui.common.components.KeypadPanel;
import gui.fonts.FontProvider;
import resources.Resources;

import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Class for dialog window, that contains {@link KeypadPanel}.
 * Class is intended to change amount of selected product in receipt.
 */
public class KeypadDialogChangeProductAmount extends KeypadDialog {

    /**
     * Constructor tunes up the look of this dialog, and sets actions for action buttons.
     * Constructor of parent is called initially.
     *
     * @param owner {@link Frame} object, from which this window was called
     */
    public KeypadDialogChangeProductAmount(Frame owner) {
        super(owner);

        dialogTitle.setText(Resources.getInstance().getString("change_product_amount"));
        dialogHint.setText(Resources.getInstance().getString("hint_set_necessary_value"));

        Font robotoRegular30 = FontProvider.getInstance().getFont(FontProvider.ROBOTO_REGULAR, 30);

        keypadPanel.setTextFieldDocument("money63");
        keypadPanel.setActionButtonsAmount(2);
        keypadPanel.getActionButton1().setText(Resources.getInstance().getString("set"));
        keypadPanel.getActionButton1().addActionListener(this::actionPerformed);
        keypadPanel.getActionButton2().setText(Resources.getInstance().getString("cancel"));
        keypadPanel.getActionButton2().addActionListener(this::actionPerformed);
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
                break;
            default:
                break;
        }
    }

}