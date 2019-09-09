/*
 * Copyright (c) RESONANCE JSC, 09.09.2019
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
 * Class for payment dialog window. Bounded to payment_dialog.form
 * It is separately standing dialog window that nor inherit any classes nor have derivative classes.
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
            // this delay - workaround for weak hardware (makes rendering faster when glassPane disappears)
            Timer timer = new Timer(0, this);
            timer.setInitialDelay(10);
            timer.setActionCommand("delayBeforeClosingThisWindow");
            timer.start();
        });

        keypadPanel.setActionButtonsAmount(1);
        keypadPanel.getActionButton1().setText(Resources.getInstance().getString("pay_in"));
    }

    /**
     * Method is called when action occurs (i.e. button pressed or timer triggers).
     *
     * @param e event, that occurs.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // if timer for closing this dialog triggered
        if ("delayBeforeClosingThisWindow".equals(e.getActionCommand())) {
            ((Timer) e.getSource()).stop();
            this.dispose();
        }
        // actionCommands for buttons assigned in appropriate *.form file
        switch (e.getActionCommand()) {
            case "cashButton":
                cashButton.setBackground(blueColor);
                cashButton.setForeground(Color.WHITE);
                cardButton.setBackground(beigeColor);
                cardButton.setForeground(Color.BLACK);
                paymentFormLabel.setText(Resources.getInstance().getString("cash"));
                break;
            case "cardButton":
                cashButton.setBackground(beigeColor);
                cashButton.setForeground(Color.BLACK);
                cardButton.setBackground(blueColor);
                cardButton.setForeground(Color.WHITE);
                paymentFormLabel.setText(Resources.getInstance().getString("card"));
                break;
            default:
                break;
        }
    }
}
// TODO: 20.08.2019 Format 1000000.00 max (no less)
