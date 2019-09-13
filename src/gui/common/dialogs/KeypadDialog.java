/*
 * Copyright (c) RESONANCE JSC, 13.09.2019
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

/**
 * General abstract class for dialog window, that contains {@link JTextField} and {@link KeypadPanel}.
 * Used for inheritance by another descent classes. Bounded to keypad_dialog.form
 */
public abstract class KeypadDialog extends JWindow implements ActionListener {
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
    protected GlassPane glassPane;
    protected MainFrame parentFrame;
    protected GridBagLayout gbLayoutMainPanel = (GridBagLayout) mainPanel.getLayout();
    // get layout to operate with cards-JPanels, that contained in appropriate container
    protected CardLayout cardPanelLayout = (CardLayout) cardPanel.getLayout();

    public KeypadDialog(Frame owner) {
        super(owner);
        this.setContentPane(mainPanel);

        extraPanel.setVisible(false);       // default value

        // type and size of fonts in heading
        dialogTitle.setFont(FontProvider.getInstance().getFont(FontProvider.ROBOTO_REGULAR, 26));
        dialogHint.setFont(FontProvider.getInstance().getFont(FontProvider.ROBOTO_REGULAR, 20));

//        // hide cursor from current JWindow
//        setCursor(getToolkit().createCustomCursor(
//                new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB),
//                new Point(),
//                null));

        if (getParent() instanceof MainFrame)
            parentFrame = (MainFrame) getParent();
        if (parentFrame.getGlassPane() instanceof GlassPane) {
            glassPane = (GlassPane) parentFrame.getGlassPane();
        }
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
    }

    public KeypadPanel getKeypadPanel() {
        return keypadPanel;
    }

    public JTextField getTextField() {
        return keypadPanel.getTextField();
    }
}