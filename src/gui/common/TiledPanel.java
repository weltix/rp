/*
 * Copyright (c) RESONANCE JSC, 07.10.2019
 */

package gui.common;

import gui.fonts.FontProvider;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.function.Consumer;

import static gui.fonts.FontProvider.ROBOTO_REGULAR;

/**
 * Class describes tiled panel. Bound to tiled_panel.form.
 * This panel is used as common panel that holds buttons with different functions for selling.
 * We create all buttons from start, and then remove unnecessary of them.
 * Such approach is more easy for tune-up of buttons, also reduce the amount of code, because all used buttons already
 * configured appropriately.
 * Only flaw is redundant work for garbage collector.
 * <p>
 * Top, right and bottom panels are customizing programmatically.
 * Left panel's width and button's insets are adjusting in bound *.form file.
 */
public class TiledPanel {
    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel leftPanel;
    private JPanel bottomPanel;
    private JPanel rightPanel;

    private GridBagLayout gbLayoutMainPanel = (GridBagLayout) mainPanel.getLayout();
    // list of texts for buttons-tiles of TiledPanel
    private List<String> buttonsTexts;
    private Consumer<Integer> buttonsActions;    // object of functional interface Consumer, hold actions for buttons of TiledPanel
    private int buttonsAmount;

    public TiledPanel(List<String> texts, Consumer<Integer> actions) {
        buttonsTexts = texts;
        buttonsActions = actions;
        buttonsAmount = texts.size();
        initComponents();
    }

    private void initComponents() {
        Font robotoRegular30 = FontProvider.getInstance().getFont(ROBOTO_REGULAR, 30);
        // Cycle sets properties for buttons of TiledPanel (getComponents() returns only components of 1-st level of nesting).
        // Name of component is entered in *.form file in "name" field.
        int buttonNum = 0;
        if (mainPanel instanceof Container)
            for (Component child : mainPanel.getComponents()) {
                if (child instanceof JButton) {
                    JButton button = (JButton) child;

                    if (buttonNum < buttonsAmount) {
                        button.setText(buttonsTexts.get(buttonNum));
                        button.setFont(robotoRegular30);

                        final int buttonNumFinal = buttonNum;
                        // set listeners for each button. Depending on event we perform specified action.
                        button.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseReleased(MouseEvent e) {
                                buttonsActions.accept(buttonNumFinal);
                            }
                        });
                    } else {
                        mainPanel.remove(button);
                    }
                    buttonNum++;
                }
            }

        setBottomPanelHeight();
        if (buttonsAmount < 5)
            setRightPanelWidth();
    }

    /**
     * Set the height of top panel. The width is not important and here may get any value.
     */
    public void setTopPanelHeight(int height) {
        topPanel.setPreferredSize(new Dimension(10, height));
    }

    /**
     * Sets the height of bottomPanel (removes and then adds bottomPanel with new constraints).
     */
    private void setBottomPanelHeight() {
        int buttonRowsAmount = (int) Math.ceil((double) buttonsAmount / 5);
        GridBagConstraints constraintsBottomPanel = gbLayoutMainPanel.getConstraints(bottomPanel);
        // 6 - initial amount of button rows (look in *.form file).
        // 15% - weight in Y axis of 1 button, 10% - weight in Y axis of space that is free from buttons.
        // It is impossible to get weights of elements directly from *.form file.
        constraintsBottomPanel.weighty = (6 - buttonRowsAmount) * 15 + 10;
        mainPanel.remove(bottomPanel);
        mainPanel.add(bottomPanel, constraintsBottomPanel);
    }

    /**
     * Sets the width of rightPanel (removes and then adds rightPanel with new constraints).
     * Method is actual only if total amount of buttons is less than 5.
     */
    private void setRightPanelWidth() {
        GridBagConstraints constraintsRightPanel = gbLayoutMainPanel.getConstraints(rightPanel);
        // 5 - amount of buttons in single row. 20% - weight in X axis of 1 button.
        // It is impossible to get weights of elements from *.form file programmatically.
        constraintsRightPanel.weightx = (5 - buttonsAmount) * 20;
        mainPanel.remove(rightPanel);
        mainPanel.add(rightPanel, constraintsRightPanel);
    }
}