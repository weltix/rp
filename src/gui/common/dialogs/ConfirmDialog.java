/*
 * Copyright (c) RESONANCE JSC, 11.09.2019
 */

package gui.common.dialogs;

import gui.common.MainFrame;
import gui.common.utility_components.GlassPane;
import gui.fonts.FontProvider;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

import static gui.fonts.FontProvider.ROBOTO_REGULAR;

/**
 * Class for confirm dialog window, that contains title, question and two option {@link JButton}s - Yes or No.
 * Bounded to confirm_dialog.form
 */
public class ConfirmDialog extends JWindow implements ActionListener {
    private JPanel mainPanel;
    private JLabel dialogTitle;
    private JLabel dialogQuestion;
    private JButton yesButton;
    private JButton noButton;

    private GlassPane glassPane;
    private MainFrame parentFrame;

    private Consumer<Integer> yesButtonAction;     // object of functional interface Consumer, hold action for yesButton

    public ConfirmDialog(Frame owner) {
        super(owner);

        this.setContentPane(mainPanel);

        dialogTitle.setFont(FontProvider.getInstance().getFont(ROBOTO_REGULAR, 40));
        dialogQuestion.setFont(FontProvider.getInstance().getFont(ROBOTO_REGULAR, 32));
        yesButton.setFont(FontProvider.getInstance().getFont(ROBOTO_REGULAR, 36));
        noButton.setFont(FontProvider.getInstance().getFont(ROBOTO_REGULAR, 36));

        // get glass pane of MainFrame
        if (getParent() instanceof MainFrame)
            parentFrame = (MainFrame) getParent();
        if (parentFrame.getGlassPane() instanceof GlassPane) {
            glassPane = (GlassPane) parentFrame.getGlassPane();
        }

        yesButton.addActionListener(this::actionPerformed);
        noButton.addActionListener(this::actionPerformed);
    }

    public void setProperties (String title, String question, Consumer<Integer> action){
        dialogTitle.setText(title);
        dialogQuestion.setText(question);
        this.yesButtonAction = action;
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
        // actionCommands for buttons assigned in bounded *.form file
        switch (e.getActionCommand()) {
            case "yesButton":
                yesButtonAction.accept(0);      // in is no matter what
                break;
            case "noButton":
                glassPane.deactivate();
                // this delay - workaround for weak hardware (makes rendering faster when glassPane disappears)
                Timer timer = new Timer(0, this);
                timer.setInitialDelay(10);
                timer.setActionCommand("delayBeforeClosingThisWindow");
                timer.start();
                break;
            default:
                break;
        }
    }


}
