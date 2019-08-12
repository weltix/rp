/*
 * Copyright (c) RESONANCE JSC, 12.08.2019
 */

package gui.common.dialogs;

import gui.common.KeypadPanel;
import gui.common.MainFrame;
import gui.common.utility_components.GlassPane;
import gui.fonts.FontProvider;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

/**
 * Класс, содержащий окно ввода пароля и цифровую клавиатуру для входа в систему.
 * Использует форму keypad_dialog.form
 */
public class KeypadDialog extends JWindow implements ActionListener {
    private JPanel mainPanel;
    protected KeypadPanel keypadPanel;
    private JLabel dialogTitle;
    private JLabel dialogHint;
    protected GlassPane glassPane;
    protected MainFrame parentFrame;

    public KeypadDialog(Frame owner) {
        super(owner);
        this.setContentPane(mainPanel);

        // тип и размер шрифтов в заголовке
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
    }

    /**
     * Код используется для обработки событий таймера
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if ("delayBeforeClosingThisWindow".equals(e.getActionCommand())) {
            ((Timer) e.getSource()).stop();
            keypadPanel.getTextField().setText("");
            this.dispose();
        }
    }

    public JTextField getTextField() {
        return keypadPanel.getTextField();
    }
}