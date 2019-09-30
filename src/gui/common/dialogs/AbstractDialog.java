/*
 * Copyright (c) RESONANCE JSC, 30.09.2019
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
    }

    /**
     * Returns initial mainPanel of main_frame.form. Does not affect performance.
     * Needful if previously MainFrame#setContentPane(jlayer) possibly was called for blurring of background.
     * Then deactivates glass pane and calls dispose method.
     */
    protected void close() {
        parentFrame.setContentPane(null);
        glassPane.deactivate();
        SwingUtilities.invokeLater(() -> this.dispose());
    }
}
