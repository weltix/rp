/*
 * Copyright (c) RESONANCE JSC, 20.09.2019
 */

package gui.common.dialogs;

import gui.common.MainFrame;
import gui.common.utility_components.GlassPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * General abstract class for all dialog windows of this app. Used for inheritance by another descent classes.
 */
public abstract class AbstractDialog extends JWindow implements ActionListener {

    protected MainFrame parentFrame;        // it is our MainFrame object (parent of any our dialog)
    protected GlassPane glassPane;          // it is glass pane that is set in MainFrame's object

    public AbstractDialog(Frame owner) {
        super(owner);

        if (getParent() instanceof MainFrame)
            parentFrame = (MainFrame) getParent();
        if (parentFrame.getGlassPane() instanceof GlassPane) {
            glassPane = (GlassPane) parentFrame.getGlassPane();
        }
//        // hide cursor from current JWindow
//        setCursor(getToolkit().createCustomCursor(
//                new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB),
//                new Point(),
//                null));
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

    /**
     * Method prepares dialog for disposing.
     * Timer is used as workaround for weak hardware (it makes rendering faster when glassPane disappears).
     * Actually timer makes little delay between glass pane deactivating and disposing of dialog.
     */
    protected void prepareToDispose() {
        // Returns initial mainPanel of main_frame.form. Does not affect performance.
        // Need to call, because previously MainFrame#setContentPane(jlayer) possibly was called for blurring of background.
        parentFrame.setContentPane(null);
        glassPane.deactivate();
        Timer timer = new Timer(0, this);
        timer.setInitialDelay(10);
        timer.setActionCommand("delayBeforeClosingThisWindow");
        timer.start();
    }
}
