/*
 * Copyright (c) RESONANCE JSC, 14.08.2019
 */

package gui.common.dialogs;

import gui.common.KeypadPanel;
import gui.common.MainFrame;
import gui.common.utility_components.GlassPane;
import gui.fonts.FontProvider;
import resources.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static gui.fonts.FontProvider.ROBOTO_BOLD;
import static gui.fonts.FontProvider.ROBOTO_REGULAR;

/**
 * Код, описывающий диалог оплаты товаров.
 * Это отдельно стоящий диалог, не наследующий и не имеющий производных кастомных классов.
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
    private JButton clearButton;

    private GlassPane glassPane;
    private MainFrame parentFrame;

    private Color blueColor = new Color(53, 152, 219);
    private Color beigeColor = new Color(235, 235, 235);

//    private int

    public PaymentDialog(Frame owner) {
        super(owner);
        this.setContentPane(mainPanel);

        Font robotoRegular38 = FontProvider.getInstance().getFont(ROBOTO_REGULAR, 38);
        cashButton.setFont(robotoRegular38);
        cardButton.setFont(robotoRegular38);
        cancelButton.setFont(FontProvider.getInstance().getFont(ROBOTO_REGULAR, 32));
        payButton.setFont(FontProvider.getInstance().getFont(ROBOTO_REGULAR, 42));
        clearButton.setFont(FontProvider.getInstance().getFont(ROBOTO_REGULAR, 32));
        toPayLabel.setFont(FontProvider.getInstance().getFont(ROBOTO_BOLD, 52));
        toPaySumLabel.setFont(FontProvider.getInstance().getFont(ROBOTO_BOLD, 52));
        paymentFormLabel.setFont(FontProvider.getInstance().getFont(ROBOTO_REGULAR, 40));
        paymentFormSumLabel.setFont(FontProvider.getInstance().getFont(ROBOTO_REGULAR, 40));
        mustBePaidLabel.setFont(FontProvider.getInstance().getFont(ROBOTO_BOLD, 46));
        mustBePaidSumLabel.setFont(FontProvider.getInstance().getFont(ROBOTO_BOLD, 46));


        if (getParent() instanceof MainFrame)
            parentFrame = (MainFrame) getParent();
        if (parentFrame.getGlassPane() instanceof GlassPane) {
            glassPane = (GlassPane) parentFrame.getGlassPane();
        }

        cashButton.addActionListener(this);
        cardButton.addActionListener(this);

        cancelButton.addActionListener(e -> {
            glassPane.deactivate();
            keypadPanel.getTextField().setText("");
            // данная задержка - workaround для слабого железа (убирает задержку прорисовки при исчезновении glassPane)
            Timer timer = new Timer(0, this);
            timer.setInitialDelay(10);
            timer.setActionCommand("delayBeforeClosingThisWindow");
            timer.start();
        });

        keypadPanel.setActionButtonsAmount(1);
        keypadPanel.getActionButton1().setText(Resources.getInstance().getString("pay_in"));
    }

    /**
     * Код используется для обработки событий таймера и нажатий кнопок.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if ("delayBeforeClosingThisWindow".equals(e.getActionCommand())) {
            ((Timer) e.getSource()).stop();
            this.dispose();
        }
        if ("cashButton".equals(e.getActionCommand())) {
            cashButton.setBackground(blueColor);
            cashButton.setForeground(Color.WHITE);
            cardButton.setBackground(beigeColor);
            cardButton.setForeground(Color.BLACK);
            paymentFormLabel.setText(Resources.getInstance().getString("cash"));
        }
        if ("cardButton".equals(e.getActionCommand())) {
            cashButton.setBackground(beigeColor);
            cashButton.setForeground(Color.BLACK);
            cardButton.setBackground(blueColor);
            cardButton.setForeground(Color.WHITE);
            paymentFormLabel.setText(Resources.getInstance().getString("card"));
        }
    }
}
