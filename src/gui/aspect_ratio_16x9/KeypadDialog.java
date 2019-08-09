/*
 * Copyright (c) RESONANCE JSC, 09.08.2019
 */

package gui.aspect_ratio_16x9;

import gui.FontProvider;
import gui.custom_components.GlassPane;
import gui.custom_components.KeypadPanel;
import resources.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Класс, содержащий окно ввода пароля и цифровую клавиатуру для входа в систему.
 * Использует форму keypad_dialog.form
 */
public class KeypadDialog extends JWindow {
    private JPanel mainPanel;
    private KeypadPanel keypadPanel;
    private JLabel dialogTitle;
    private JLabel dialogHint;
    private GlassPane glassPane;
    private MainFrame parentFrame;

    public KeypadDialog(Frame owner, String dialogKind) {
        super(owner);
        this.setContentPane(mainPanel);

        dialogTitle.setFont(FontProvider.getInstance().getFont(FontProvider.ROBOTO_REGULAR, 26f));
        dialogHint.setFont(FontProvider.getInstance().getFont(FontProvider.ROBOTO_REGULAR, 20f));

        // hide cursor from current JWindow
        setCursor(getToolkit().createCustomCursor(
                new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB),
                new Point(),
                null));

        if (getParent() instanceof MainFrame)
            parentFrame = (MainFrame) getParent();
        if (parentFrame.getGlassPane() instanceof GlassPane) {
            glassPane = (GlassPane) parentFrame.getGlassPane();
        }

        switch (dialogKind) {
            case "login":
                initLoginDialog();
                break;
            default:
                break;
        }

    }

    private void initLoginDialog() {
        keypadPanel.setActionButtonsAmount(2);
        keypadPanel.getActionButton1().setText(Resources.getInstance().getString("ok"));
        keypadPanel.getActionButton2().setText(Resources.getInstance().getString("exit"));
        keypadPanel.doubleWidthA0Button();
        keypadPanel.switchToPasswordTextField();
        keypadPanel.getActionButton1().addActionListener(e -> {
            // Возвращаем первоначальный mainPanel (следующий метод переопределён). Не влияет на производительность.
            // Нужно из-за того, что было установлено setContentPane(jlayer) для достижения размытого фона.
            parentFrame.setContentPane(null);

            if (parentFrame.getSplashScreenPanel().isVisible()) {
                parentFrame.setCardOfMainPanel("mainSellPanel");
                parentFrame.setCardOfMainSellPanelScreens("sellPanel");
            }
            glassPane.deactivate();

            this.dispose();
        });
        keypadPanel.getActionButton2().addActionListener(e -> {
            this.dispose();
            System.exit(0);
        });

    }
}