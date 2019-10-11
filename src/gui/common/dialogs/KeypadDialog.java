/*
 * Copyright (c) RESONANCE JSC, 11.10.2019
 */

package gui.common.dialogs;

import gui.common.components.KeypadPanel;
import gui.fonts.FontProvider;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * General abstract class for dialog window, that contains {@link JTextField} and {@link KeypadPanel}.
 * Used for inheritance by another descent classes. Bound to keypad_dialog.form
 */
public abstract class KeypadDialog extends AbstractDialog {
    protected JPanel mainPanel;
    protected KeypadPanel keypadPanel;
    public JLabel dialogTitle;
    public JLabel dialogHint;
    protected JPanel extraPanel;
    protected JPanel cardPanel;
    protected JButton percentButton;
    protected JButton moneyButton;
    protected JButton productButton;
    protected JButton receiptButton;
    private JPanel discountPanel;
    private JPanel depositWithdrawPanel;
    protected JButton depositButton;
    protected JButton withdrawButton;
    protected JLabel cashAmountLabel;
    private JPanel topPanel;
    // next components are from payment panel
    protected JPanel paymentPanel;
    protected JPanel centerPanel;
    protected JButton cashButton;
    protected JButton cardButton;
    protected JComboBox comboBox;
    protected JLabel toPayLabel;
    protected JLabel toPaySumLabel;
    protected JLabel paymentFormLabel;
    protected JLabel paymentFormSumLabel;
    protected JLabel mustBePaidLabel;
    protected JLabel mustBePaidSumLabel;

    private final GridBagLayout gbLayoutMainPanel = (GridBagLayout) mainPanel.getLayout();
    protected GridBagConstraints constraintsExtraPanel = gbLayoutMainPanel.getConstraints(extraPanel);
    // get layout to operate with cards-JPanels, that contained in appropriate container
    protected CardLayout cardPanelLayout = (CardLayout) cardPanel.getLayout();

    // used for toggle buttons in some subclasses
    protected Color blueColor = new Color(53, 152, 219);
    protected Color beigeColor = new Color(235, 235, 235);

    public KeypadDialog(Frame owner) {
        super(owner);
        this.setContentPane(mainPanel);

        paymentPanel.setVisible(false);     // default value, need only for some subclasses
        extraPanel.setVisible(false);       // default value, need only for some subclasses

        // type and size of fonts in heading
        dialogTitle.setFont(FontProvider.getInstance().getFont(FontProvider.ROBOTO_REGULAR, 26));
        dialogHint.setFont(FontProvider.getInstance().getFont(FontProvider.ROBOTO_REGULAR, 20));

        mainPanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
                    keypadPanel.getActionButton2().doClick();
            }
        });
    }

    /**
     * More detailed description look in superclass.
     * Action commands for buttons have been assigned in bound *.form file.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        switch (e.getActionCommand()) {
            case "actionButton0":
            case "actionButton1":
            case "actionButton2":
                close();
            default:
                break;
        }
    }

    /**
     * Method sets the text in text field of dialog. Must be called every time before launching dialog window.
     *
     * @param extraData text for text field of dialog. If equals {@code null}, than text in text field will not change.
     */
    public void setTextFieldText(String extraData) {
        if (extraData != null)
            keypadPanel.getTextField().setText(extraData);
        keypadPanel.getTextField().requestFocus();
        keypadPanel.getTextField().selectAll();
    }
}
// TODO: 08.10.2019 Вынести операцию нажатия на actionButton2 в этот суперкласс из всех наследников?