/*
 * Copyright (c) RESONANCE JSC, 26.07.2019
 */

package gui.aspect_ratio_16x9;

import javax.swing.*;

public class LoginDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;

    public LoginDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
    }
}
