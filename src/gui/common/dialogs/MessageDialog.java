/*
 * Copyright (c) RESONANCE JSC, 20.09.2019
 */

package gui.common.dialogs;

import gui.common.MainFrame;
import gui.common.utility_components.GlassPane;
import gui.fonts.FontProvider;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static gui.fonts.FontProvider.ROBOTO_REGULAR;

/**
 * Class for message (alert) dialog window, that contains title, message and one {@link JButton} OK.
 * Bounded to message_dialog.form
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

        // get glass pane of MainFrame
        if (getParent() instanceof MainFrame)
            parentFrame = (MainFrame) getParent();
        if (parentFrame.getGlassPane() instanceof GlassPane) {
            glassPane = (GlassPane) parentFrame.getGlassPane();
        }

        okButton.addActionListener(this::actionPerformed);
    }

    public void setProperties(String title, String message) {
        dialogTitle.setText(title);
        dialogMessage.setText(message);
    }

    /**
     * Method is called when action occurs (i.e. button pressed or timer triggers).
     *
     * @param e event, that occurs.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        // actionCommands for buttons assigned in bounded *.form file
        switch (e.getActionCommand()) {
            case "okButton":
                prepareToDispose();
                break;
            default:
                break;
        }
    }
}
