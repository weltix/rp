/*
 * Copyright (c) RESONANCE JSC, 08.10.2019
 */

package gui.common.dialogs;

import gui.common.KeypadPanel;
import gui.fonts.FontProvider;

import javax.swing.*;
import java.awt.*;

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
    private final GridBagLayout gbLayoutMainPanel = (GridBagLayout) mainPanel.getLayout();
    protected GridBagConstraints constraintsExtraPanel = gbLayoutMainPanel.getConstraints(extraPanel);
    // get layout to operate with cards-JPanels, that contained in appropriate container
    protected CardLayout cardPanelLayout = (CardLayout) cardPanel.getLayout();

    public KeypadDialog(Frame owner) {
        super(owner);
        this.setContentPane(mainPanel);

        extraPanel.setVisible(false);       // default value

        // type and size of fonts in heading
        dialogTitle.setFont(FontProvider.getInstance().getFont(FontProvider.ROBOTO_REGULAR, 26));
        dialogHint.setFont(FontProvider.getInstance().getFont(FontProvider.ROBOTO_REGULAR, 20));
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