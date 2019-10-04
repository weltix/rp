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
import java.util.function.Consumer;

import static gui.fonts.FontProvider.ROBOTO_REGULAR;

/**
 * Class for confirm dialog window, that contains title, question and two option {@link JButton}s - Yes or No.
 * Bound to confirm_dialog.form
 */
public class ConfirmDialog extends AbstractDialog{
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
        yesButton.addActionListener(this::actionPerformed);
        noButton.addActionListener(this::actionPerformed);

        mainPanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_ENTER:
                            yesButton.doClick();
                        break;
                    case KeyEvent.VK_ESCAPE:
                            noButton.doClick();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    public void setProperties (String title, String question, Consumer<Integer> action){
        dialogTitle.setText(title);
        dialogQuestion.setText(question);
        this.yesButtonAction = action;
    }

    /**
     * More detailed description look in superclass.
     * Action commands for buttons have been assigned in bound *.form file.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        switch (e.getActionCommand()) {
            case "yesButton":
                yesButtonAction.accept(0);
                break;
            case "noButton":
                close();
                break;
            default:
                break;
        }
    }


}
