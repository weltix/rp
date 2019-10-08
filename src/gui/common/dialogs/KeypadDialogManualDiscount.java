/*
 * Copyright (c) RESONANCE JSC, 08.10.2019
 */

package gui.common.dialogs;

import gui.common.KeypadPanel;
import gui.fonts.FontProvider;
import resources.Resources;

import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Class for dialog window, that contains {@link KeypadPanel} and additional toggle buttons for choosing
 * ("percents" or "money") and {"product" or "receipt"}. Class is intended to set particular discount.
 * Parent abstract class {@link KeypadDialog} contains JPanel {@link KeypadDialog#extraPanel}, which has
 * CardLayout as layout manager, and contains another JPanels different for different subclasses.
 */
public class KeypadDialogManualDiscount extends KeypadDialog {

    // used for toggle buttons
    private Color blueColor = new Color(53, 152, 219);
    private Color beigeColor = new Color(235, 235, 235);

    /**
     * Constructor tunes up the look of this dialog, and sets action for action button.
     * Constructor of parent is called initially.
     *
     * @param owner {@link Frame} object, from which this window was called
     */
    public KeypadDialogManualDiscount(Frame owner) {
        super(owner);

        extraPanel.setVisible(true);
        cardPanelLayout.show(cardPanel, "discountPanel");

        dialogTitle.setText(Resources.getInstance().getString("manual_discount"));
        dialogHint.setText(Resources.getInstance().getString("hint_set_discount"));

        // 14% - weight in Y axis of extraPanel (100% - original height, and we add 14% of extraPanel. Totally 114%)
        constraintsExtraPanel.weighty = 14;
        mainPanel.remove(extraPanel);
        mainPanel.add(extraPanel, constraintsExtraPanel);

        Font robotoRegular30 = FontProvider.getInstance().getFont(FontProvider.ROBOTO_REGULAR, 30);
        percentButton.setFont(FontProvider.getInstance().getFont(FontProvider.ROBOTO_BOLD, 38));
        moneyButton.setFont(robotoRegular30);
        productButton.setFont(robotoRegular30);
        receiptButton.setFont(robotoRegular30);

        percentButton.addActionListener(this::actionPerformed);
        moneyButton.addActionListener(this::actionPerformed);
        productButton.addActionListener(this::actionPerformed);
        receiptButton.addActionListener(this::actionPerformed);

        keypadPanel.setActionButtonsAmount(2);
        keypadPanel.getActionButton1().setText(Resources.getInstance().getString("set"));
        keypadPanel.getActionButton1().addActionListener(this::actionPerformed);
        keypadPanel.getActionButton2().setText(Resources.getInstance().getString("cancel"));
        keypadPanel.getActionButton2().addActionListener(this::actionPerformed);

        keypadPanel.setTextFieldDocument("money72");
        keypadPanel.getTextField().setText("0");
        keypadPanel.setTextFieldDocument("percent");
        keypadPanel.getTextField().setText("0");
    }

    /**
     * Action commands for buttons have been assigned in bound *.form file.
     *
     * @see {@link AbstractDialog#actionPerformed(ActionEvent)}
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        switch (e.getActionCommand()) {
            case "actionButton1":
                System.out.println("yes");
                break;
            case "actionButton2":
                close();
                break;
            case "percentButton":
                percentButton.setBackground(blueColor);
                percentButton.setForeground(Color.WHITE);
                moneyButton.setBackground(beigeColor);
                moneyButton.setForeground(Color.BLACK);
                keypadPanel.setTextFieldDocument("percent");
                setTextFieldText(null);    // only selects text in text field
                break;
            case "moneyButton":
                percentButton.setBackground(beigeColor);
                percentButton.setForeground(Color.BLACK);
                moneyButton.setBackground(blueColor);
                moneyButton.setForeground(Color.WHITE);
                keypadPanel.setTextFieldDocument("money72");
                setTextFieldText(null);    // only selects text in text field
                break;
            case "productButton":
                productButton.setBackground(blueColor);
                productButton.setForeground(Color.WHITE);
                receiptButton.setBackground(beigeColor);
                receiptButton.setForeground(Color.BLACK);
                break;
            case "receiptButton":
                productButton.setBackground(beigeColor);
                productButton.setForeground(Color.BLACK);
                receiptButton.setBackground(blueColor);
                receiptButton.setForeground(Color.WHITE);
                break;
            default:
                break;
        }
    }
}