/*
 * Copyright (c) RESONANCE JSC, 18.09.2019
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
                // Returns initial mainPanel of main_frame.form. Does not affect performance.
                // Need to call, because previously MainFrame#setContentPane(jlayer) possibly was called for blurring of background.
                parentFrame.setContentPane(null);

                if (parentFrame.getSplashScreenPanel().isVisible()) {
                    parentFrame.setCardOfMainPanel("mainSellPanel");
                    parentFrame.setCardOfSellPanelScreens("sellPanel");
                }

                glassPane.deactivate();
                keypadPanel.getTextField().setText("");
                // this delay - workaround for weak hardware (makes rendering faster when glassPane disappears)
                Timer timer = new Timer(0, this);
                timer.setInitialDelay(10);
                timer.setActionCommand("delayBeforeClosingThisWindow");
                timer.start();
                break;
            case "actionButton2":
                this.dispose();
                System.exit(0);
                break;
            default:
                break;
        }


    }
}
