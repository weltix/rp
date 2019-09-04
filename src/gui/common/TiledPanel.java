/*
 * Copyright (c) RESONANCE JSC, 04.09.2019
 */

package gui.common;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.function.Consumer;

/**
 * Class describes tiled panel. Bounded to tiled_panel.form.
 * This panel is used as common panel that holds buttons with different functions for selling.
 * We create from start all buttons, and then remove unnecessary of them.
 * Such approach is more easy, because all used buttons
 */
public class TiledPanel {
    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel leftPanel;
    private JPanel bottomPanel;

    public static int topPanelHeight;
    // list of texts for buttons-tiles of TiledPanel
    private List<String> navButtonTexts;
    private Consumer<Integer> navButtonActions;    // object of functional interface Consumer, hold actions for buttons of TiledPanel
    private int buttonsAmount;

    public TiledPanel(List<String> texts, Consumer<Integer> actions) {
        navButtonTexts = texts;
        navButtonActions = actions;
        buttonsAmount = texts.size();
        initComponents();
    }

    private void initComponents() {
        // Cycle sets properties for buttons of TiledPanel (getComponents() returns only components of 1-st level of nesting).
        // Name of component is entered in *.form file in "name" field.
        int buttonNum = 0;
        if (mainPanel instanceof Container)
            for (Component child : mainPanel.getComponents()) {
                if (child.getName() != null
                        && child instanceof JPanel
                        && child.getName().contains("NavigateButton.mainPanel")) {

                    JPanel naviButton = (JPanel) ((JPanel) child).getComponent(0);
                    JLabel naviButtonIcon = (JLabel) naviButton.getComponent(0);
                    JLabel naviButtonText = (JLabel) naviButton.getComponent(1);
                    JPanel delimiterLine = (JPanel) ((JPanel) child).getComponent(1);

                    if (buttonNum < buttonsAmount) {
                        naviButtonText.setText(navButtonTexts.get(buttonNum));
                        final int buttonNumFinal = buttonNum;
                        // set listeners for each button. Depending on event we perform specified action.
                        naviButton.addMouseListener(new MouseAdapter() {
                            /* isPressed and isMouseOver - workaround of mouseClicked method.
                               Reason: in some systems click doesn't work properly when mouse cursor coordinates changes at
                               least for 1px during click */
                            private boolean isPressed = false;
                            private boolean isMouseOver = false;

                            @Override
                            public void mousePressed(MouseEvent e) {
                                isPressed = true;
                            }

                            @Override
                            public void mouseReleased(MouseEvent e) {
                                if (isPressed && isMouseOver) {
                                    navButtonActions.accept(buttonNumFinal);
                                }
                                isPressed = false;
                            }

                            @Override
                            public void mouseEntered(MouseEvent e) {
                                if (isPressed)
                                    isMouseOver = true;
                            }

                            @Override
                            public void mouseExited(MouseEvent e) {
                                isMouseOver = false;
                            }
                        });
                    } else {
                        naviButton.setOpaque(false);
                        naviButtonIcon.setVisible(false);
                        naviButtonText.setVisible(false);
                        delimiterLine.setOpaque(false);
                    }
                    buttonNum++;
                }
            }
    }
}