/*
 * Copyright (c) RESONANCE JSC, 14.08.2019
 */

package gui.common.dialogs;

import gui.common.KeypadPanel;
import gui.common.MainFrame;
import gui.common.utility_components.GlassPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Код, описывающий диалог оплаты товаров.
 * Использует форму payment_dialog.form
 */
public class PaymentDialog extends JWindow implements ActionListener {
    private JPanel mainPanel;
    private KeypadPanel keypadPanel;
    private JButton cashButton;
    private JButton cardButton;
    private JButton cancelButton;
    private JButton payButton;
    private JLabel toPayLabel;
    private JLabel toPaySumLabel;
    private JLabel paymentFormLabel;
    private JLabel paymentFormSumLabel;
    private JLabel mustBePaidLabel;
    private JLabel mustBePaidSumLabel;
    private JPanel centerPanel;

    private GlassPane glassPane;
    private MainFrame parentFrame;

    public PaymentDialog(Frame owner) {
        super(owner);
        this.setContentPane(mainPanel);

        if (getParent() instanceof MainFrame)
            parentFrame = (MainFrame) getParent();
        if (parentFrame.getGlassPane() instanceof GlassPane) {
            glassPane = (GlassPane) parentFrame.getGlassPane();
        }

        cancelButton.addActionListener(e -> {
            glassPane.deactivate();
            keypadPanel.getTextField().setText("");
            // данная задержка - workaround для слабого железа (убирает задержку прорисовки при исчезновении glassPane)
            Timer timer = new Timer(0, this);
            timer.setInitialDelay(10);
            timer.setActionCommand("delayBeforeClosingThisWindow");
            timer.start();
        });

//        keypadPanel.setActionButtonsAmount(2);
//        keypadPanel.getActionButton1().setText(Resources.getInstance().getString("ok"));
//        keypadPanel.getActionButton2().setText(Resources.getInstance().getString("exit"));
//        keypadPanel.doubleWidthA0Button();
//        keypadPanel.switchToPasswordTextField();

    }

    /**
     * Код используется для обработки событий таймера
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if ("delayBeforeClosingThisWindow".equals(e.getActionCommand())) {
            ((Timer) e.getSource()).stop();
            this.dispose();
        }
    }
}
