/*
 * Copyright (c) RESONANCE JSC, 26.07.2019
 */

package gui.aspect_ratio_16x9;

import gui.custom_components.DisabledGlassPane;
import gui.custom_components.KeypadPanel;
import resources.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

/**
 * Класс, содержащий окно ввода пароля и цифровую клавиатуру для входа в систему.
 * Использует форму login_window.form
 */
public class LoginWindow extends JWindow {
    private JPanel contentPane;
    private KeypadPanel keypadPanel;
    private DisabledGlassPane disabledGlassPane;

    public LoginWindow(Frame owner) {
        super(owner);
        this.setContentPane(contentPane);
        keypadPanel.setActionButtonsAmount(2);
        keypadPanel.getActionButton1().setText(Resources.getInstance().getString("ok"));
        keypadPanel.getActionButton2().setText(Resources.getInstance().getString("exit"));
        keypadPanel.doubleWidthA0Button();
        keypadPanel.switchToPasswordTextField();

        JFrame parentFrame = null;
        if (getParent() instanceof JFrame) {
            parentFrame = (JFrame) getParent();
        }
        if (parentFrame.getGlassPane() instanceof DisabledGlassPane) {
            disabledGlassPane = (DisabledGlassPane) parentFrame.getGlassPane();
        }

        keypadPanel.getActionButton1().addActionListener(e -> {
            disabledGlassPane.deactivate();
             this.dispose();
        });
        keypadPanel.getActionButton2().addActionListener(e -> {
            this.dispose();
            System.exit(0);
        });

        // hide cursor from current JWindow
        setCursor(getToolkit().createCustomCursor(
                new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB),
                new Point(),
                null));

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                System.out.println("windowOpened");
                Color baseColor = Color.WHITE;
                //alpha originally was 128
                Color backgroundColor = new Color(baseColor.getRed(), baseColor.getGreen(), baseColor.getBlue(), 100);
                disabledGlassPane.activate(null, backgroundColor);
            }

            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("windowClosing");
            }

            @Override
            public void windowClosed(WindowEvent e) {
                System.out.println("windowClosed");
            }

            @Override
            public void windowActivated(WindowEvent e) {
                System.out.println("windowActivated");
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                disabledGlassPane.deactivate();
                System.out.println("windowGainedFocus");
            }

            @Override
            public void windowGainedFocus(WindowEvent e) {
                System.out.println("windowGainedFocus");
            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                System.out.println("windowDeactivated");
            }

            @Override
            public void windowStateChanged(WindowEvent e) {
                System.out.println("windowStateChanged");
            }

            @Override
            public void windowIconified(WindowEvent e) {
                System.out.println("windowIconified");
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
                System.out.println("windowDeiconified");
            }
        });
    }
}