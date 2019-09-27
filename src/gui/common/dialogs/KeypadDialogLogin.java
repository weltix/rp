/*
 * Copyright (c) RESONANCE JSC, 27.09.2019
 */

package gui.common.dialogs;

import gui.common.KeypadPanel;
import gui.common.utility_components.GlassPane;
import resources.Resources;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Class for dialog window, that contains {@link JPasswordField} and {@link KeypadPanel} for login to system.
 */
public class KeypadDialogLogin extends KeypadDialog {
    /**
     * Constructor tunes the look of this dialog, and sets actions for action buttons.
     * Constructor of parent is called initially.
     *
     */
    public KeypadDialogLogin(GlassPane glassPane) {
        super(glassPane);

        keypadPanel.switchToPasswordTextField();
        keypadPanel.setActionButtonsAmount(2);
        keypadPanel.getActionButton1().setText(Resources.getInstance().getString("ok"));
        keypadPanel.getActionButton2().setText(Resources.getInstance().getString("exit"));
        keypadPanel.doubleWidthA0Button();

        keypadPanel.getActionButton1().addActionListener(this::actionPerformed);
        keypadPanel.getActionButton2().addActionListener(this::actionPerformed);
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
                prepareToDispose();
                if (parentFrame.getSplashScreenPanel().isVisible()) {
                    parentFrame.setCardOfMainPanel("mainSellPanel");
                    parentFrame.setCardOfSellPanelScreens("sellPanel");
                }
                break;
            case "actionButton2":
//                this.dispose();
                System.exit(0);
                break;
            default:
                break;
        }


    }
}
