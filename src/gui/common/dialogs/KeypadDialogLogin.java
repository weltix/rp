/*
 * Copyright (c) RESONANCE JSC, 04.10.2019
 */

package gui.common.dialogs;

import gui.common.KeypadPanel;
import resources.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Class for dialog window, that contains {@link JPasswordField} and {@link KeypadPanel} for login to system.
 */
public class KeypadDialogLogin extends KeypadDialog {
    /**
     * Constructor tunes the look of this dialog, and sets actions for action buttons.
     * Constructor of parent is called initially.
     *
     * @param owner {@link Frame} object, from which this window was called
     */
    public KeypadDialogLogin(Frame owner) {
        super(owner);

        keypadPanel.switchToPasswordTextField();
        keypadPanel.setActionButtonsAmount(2);
        keypadPanel.getActionButton1().setText(Resources.getInstance().getString("ok"));
        keypadPanel.getActionButton2().setText(Resources.getInstance().getString("exit"));
        keypadPanel.doubleWidthA0Button();

        keypadPanel.getActionButton1().addActionListener(this::actionPerformed);
        keypadPanel.getActionButton2().addActionListener(this::actionPerformed);
    }

    /**
     * More detailed description look in superclass.
     * Action commands for buttons have been assigned in bound *.form file.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        switch (e.getActionCommand()) {
            case "actionButton1":
                close();
                if (parentFrame.getSplashScreenPanel().isVisible()) {
                    parentFrame.setCardOfMainPanel("mainSellPanel");
                    parentFrame.setCardOfSellPanelScreens("sellPanel");
                }
                keypadPanel.getTextField().setText("");
                break;
            case "actionButton2":
                System.exit(0);
                break;
            default:
                break;
        }
    }
}
