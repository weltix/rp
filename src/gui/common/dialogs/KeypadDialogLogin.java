/*
 * Copyright (c) RESONANCE JSC, 20.08.2019
 */

package gui.common.dialogs;

import gui.common.KeypadPanel;
import resources.Resources;

import javax.swing.*;
import java.awt.*;

/**
 * Class for dialog window, that contains {@link JPasswordField} and {@link KeypadPanel} for login to system.
 */
public class KeypadDialogLogin extends KeypadDialog {
    /**
     * Constructor sets the look of login dialog, and sets actions for action buttons.
     * Constructor of parent is called initially.
     *
     * @param owner {@link Frame} object, from which this window was called
     */
    public KeypadDialogLogin(Frame owner) {
        super(owner);

        keypadPanel.setActionButtonsAmount(2);
        keypadPanel.getActionButton1().setText(Resources.getInstance().getString("ok"));
        keypadPanel.getActionButton2().setText(Resources.getInstance().getString("exit"));
        keypadPanel.doubleWidthA0Button();
        keypadPanel.switchToPasswordTextField();

        keypadPanel.getActionButton1().addActionListener(e -> {
            // Returns initial mainPanel of main_frame.form. Does not affect performance.
            // Need to call, because previously MainFrame#setContentPane(jlayer) possibly was called for blurring of background.
            parentFrame.setContentPane(null);
            if (parentFrame.getSplashScreenPanel().isVisible()) {
                parentFrame.setCardOfMainPanel("mainSellPanel");
                parentFrame.setCardOfMainSellPanelScreens("sellPanel");
            }
            glassPane.deactivate();
            keypadPanel.getTextField().setText("");
            // this delay - workaround for weak hardware (makes rendering faster when glassPane disappears)
            Timer timer = new Timer(0, this);
            timer.setInitialDelay(10);
            timer.setActionCommand("delayBeforeClosingThisWindow");
            timer.start();
        });

        keypadPanel.getActionButton2().addActionListener(e -> {
            this.dispose();
            System.exit(0);
        });
    }
}
