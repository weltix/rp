/*
 * Copyright (c) RESONANCE JSC, 09.09.2019
 */

package gui.common.dialogs;

import gui.fonts.FontProvider;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static gui.fonts.FontProvider.ROBOTO_REGULAR;

/**
 * Class for confirm dialog window, that contains title, question and two option {@link JButton}s - Yes or No.
 * Bounded to confirm_dialog.form
 */
public class ConfirmDialog extends JWindow implements ActionListener {
    private JButton yesButton;
    private JPanel mainPanel;
    private JLabel dialogTitle;
    private JLabel dialogQuestion;
    private JButton noButton;

    public ConfirmDialog(Frame owner) {
        super(owner);
        this.setContentPane(mainPanel);

        Font robotoRegular38 = FontProvider.getInstance().getFont(ROBOTO_REGULAR, 38);
        Font robotoRegular30 = FontProvider.getInstance().getFont(ROBOTO_REGULAR, 30);
        dialogTitle.setFont(robotoRegular38);
        dialogQuestion.setFont(robotoRegular30);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
