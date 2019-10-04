/*
 * Copyright (c) RESONANCE JSC, 04.10.2019
 */

package gui.common.dialogs;

import gui.common.MainFrame;
import gui.common.utility_components.GlassPane;
import gui.fonts.FontProvider;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static gui.fonts.FontProvider.ROBOTO_REGULAR;

/**
 * Class for message (alert) dialog window, that contains title, message and one {@link JButton} OK.
 * Bound to message_dialog.form
 */
public class MessageDialog extends AbstractDialog {
    private JPanel mainPanel;
    private JLabel dialogTitle;
    private JLabel dialogMessage;
    private JButton okButton;

    private GlassPane glassPane;
    private MainFrame parentFrame;

    public MessageDialog(Frame owner) {
        super(owner);

        this.setContentPane(mainPanel);

        dialogTitle.setFont(FontProvider.getInstance().getFont(ROBOTO_REGULAR, 40));
        dialogMessage.setFont(FontProvider.getInstance().getFont(ROBOTO_REGULAR, 32));
        okButton.setFont(FontProvider.getInstance().getFont(ROBOTO_REGULAR, 36));
        okButton.addActionListener(this::actionPerformed);

        mainPanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER
                        || e.getKeyCode() == KeyEvent.VK_ESCAPE)
                    okButton.doClick();
            }
        });
    }

    public void setProperties(String title, String message) {
        dialogTitle.setText(title);
        dialogMessage.setText(message);
    }

    /**
     * More detailed description look in superclass.
     * Action commands for buttons have been assigned in bound *.form file.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        switch (e.getActionCommand()) {
            case "okButton":
                close();
                break;
            default:
                break;
        }
    }
}
