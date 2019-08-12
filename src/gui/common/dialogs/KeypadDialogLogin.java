/*
 * Copyright (c) RESONANCE JSC, 12.08.2019
 */

package gui.common.dialogs;

import resources.Resources;

import javax.swing.*;
import java.awt.*;

/**
 * Класс, содержащий окно ввода пароля и цифровую клавиатуру для входа в систему.
 */
public class KeypadDialogLogin extends KeypadDialog {
    /**
     * Конструктор настраивает вид логин-диалога, задаёт действия для кнопок действия.
     * При этом, первоначально вызывается конструктор родителя {@link KeypadDialog}
     *
     * @param owner объект, из которого было вызвано данное окно
     */
    public KeypadDialogLogin(Frame owner) {
        super(owner);

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
            // данная задержка - workaround для слабого железа (убирает задержку прорисовки при исчезновении glassPane)
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
