/*
 * Copyright (c) RESONANCE JSC, 26.07.2019
 */

package gui.aspect_ratio_16x9;

import gui.custom_components.DisabledGlassPane;
import gui.custom_components.KeypadPanel;
import resources.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Класс, содержащий окно ввода пароля и цифровую клавиатуру для входа в систему.
 * Использует форму login_window.form
 */
public class LoginWindow extends JWindow {
    private JPanel contentPane;
    private KeypadPanel keypadPanel;
    private Resources resources = new Resources();

    public LoginWindow(Frame owner) {
        super(owner);
        this.setContentPane(contentPane);
        keypadPanel.setActionButtons("twoActionButtons");
        keypadPanel.actionButton1.setText(resources.getString("ok"));
        keypadPanel.actionButton2.setText(resources.getString("exit_html"));
        keypadPanel.doubleWidthA0Button();
        keypadPanel.switchToPasswordTextField();
        keypadPanel.actionButton1.addActionListener(e -> {
            if (getParent() instanceof JFrame)
                if (((JFrame) getParent()).getGlassPane() instanceof DisabledGlassPane)
                    ((DisabledGlassPane) ((JFrame) getParent()).getGlassPane()).deactivate();
//            getParent().setEnabled(true);
            this.dispose();
        });
        keypadPanel.actionButton2.addActionListener(e -> {
//            getParent().setEnabled(true);
            this.dispose();
            System.exit(0);
        });


//        getParent().setEnabled(false);

        // hide cursor from current JWindow
        setCursor(getToolkit().createCustomCursor(
                new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB),
                new Point(),
                null));
    }

}