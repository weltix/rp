/*
 * Copyright (c) RESONANCE JSC, 22.08.2019
 */

package gui.common;

import gui.fonts.FontProvider;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import static gui.fonts.FontProvider.ROBOTO_BOLD;

/**
 * Class describes navigation panel. Bounded to navigate_panel.form.
 */
public class NavigatePanel extends JPanel {
    private JPanel mainPanel;
    private JPanel navigatePanel;
    private JLabel headerLabel1;
    private JLabel headerLabel2;
    private JPanel headerPanel;
    private NavigateButton navigateButton0;
    private NavigateButton navigateButton1;
    private NavigateButton navigateButton2;
    private NavigateButton navigateButton3;
    private NavigateButton navigateButton4;
    private NavigateButton navigateButton5;
    private NavigateButton navigateButton6;
    private NavigateButton navigateButton7;
    private NavigateButton navigateButton8;
    private JPanel paddingPanel;

    // lists of icons and texts for navigate buttons. Icon is a text actually, but with special font, where no letters, but icons are.
    private List<String> navButtonIcons;
    private List<String> navButtonTexts;
    private int navButtonsAmount;

    public NavigatePanel(List<String> icons, List<String> texts) {
        navButtonIcons = icons;
        navButtonTexts = texts;
        navButtonsAmount = icons.size();
        initComponents();
    }

    private void initComponents() {
        headerLabel1.setFont(FontProvider.getInstance().getFont(ROBOTO_BOLD, 22));
        headerLabel2.setFont(FontProvider.getInstance().getFont(ROBOTO_BOLD, 12));

        Color backgroundColor = new Color(52, 73, 94);
        Color backgroundPressedColor = new Color(65, 91, 122);
        Color foregroundPressedColor = new Color(230, 238, 243);

        // Cycle sets properties for buttons of navigation panel (getComponents() returns only components of 1-st level of nesting).
        // Name of component is entered in *.form file in "name" field.
        int buttonNum = 0;
        int removedButtonsAmount = 0;
        if (navigatePanel instanceof Container)
            for (Component child : navigatePanel.getComponents()) {
                if (child.getName() != null &&
                        child instanceof JPanel &&
                        child.getName().contains("NavigateButton.mainPanel")) {

                    if (buttonNum < navButtonsAmount) {
                        JPanel naviButton = (JPanel) ((JPanel) child).getComponent(0);
                        JLabel naviButtonIcon = (JLabel) naviButton.getComponent(1);
                        JLabel naviButtonText = (JLabel) naviButton.getComponent(0);
                        naviButtonIcon.setText(navButtonIcons.get(buttonNum));
                        naviButtonText.setText(navButtonTexts.get(buttonNum));

                        // set listeners for each button. Depending on event we change look of button, or perform specified action.
                        child.addMouseListener(new MouseAdapter() {
                            /* isPressed and isMouseOver - workaround of mouseClicked method.
                               Reason: in some systems click doesn't work properly when mouse cursor coordinates changes at
                               least for 1px during click */
                            private boolean isPressed = false;
                            private boolean isMouseOver = false;

                            @Override
                            public void mousePressed(MouseEvent e) {
                                setPressedState();
                                isPressed = true;
                            }

                            @Override
                            public void mouseReleased(MouseEvent e) {
                                setNormalState();
                                if (isPressed && isMouseOver) {
                                    switch (naviButton.getName()) {
                                        case "navButton0":
                                            navButton0Clicked();
                                            break;
                                        case "navButton1":
                                            navButton1Clicked();
                                            break;
                                        case "navButton2":
                                            navButton2Clicked();
                                            break;
                                        case "navButton3":
                                            navButton3Clicked();
                                            break;
                                        case "navButton4":
                                            navButton4Clicked();
                                            break;
                                        default:
                                            break;
                                    }
                                }
                                isPressed = false;
                            }

                            @Override
                            public void mouseEntered(MouseEvent e) {
                                if (isPressed)
                                    setPressedState();
                                isMouseOver = true;
                            }

                            @Override
                            public void mouseExited(MouseEvent e) {
                                setNormalState();
                                isMouseOver = false;
                            }

                            // set look of navigation panel buttons in normal state
                            private void setNormalState() {
                                naviButton.setBackground(backgroundColor);
                                naviButtonIcon.setForeground(Color.WHITE);
                                naviButtonText.setForeground(Color.WHITE);
                            }

                            // set look of navigation panel buttons in pressed state
                            private void setPressedState() {
                                naviButton.setBackground(backgroundPressedColor);
                                naviButtonIcon.setForeground(foregroundPressedColor);
                                naviButtonText.setForeground(foregroundPressedColor);
                            }
                        });
                    } else {
                        navigatePanel.remove(child);
                        removedButtonsAmount++;
                    }
                    buttonNum++;
                }
            }

        GridBagLayout gbLayout = (GridBagLayout) navigatePanel.getLayout();
        GridBagConstraints constraintsPaddingPanel = gbLayout.getConstraints(paddingPanel);
        constraintsPaddingPanel.weighty = 10.4 * removedButtonsAmount;  // 10.4% - weight of navigating button by Y axis
        navigatePanel.remove(paddingPanel);
        navigatePanel.add(paddingPanel, constraintsPaddingPanel);
    }

    public void navButton0Clicked() {

    }

    public void navButton1Clicked() {

    }

    public void navButton2Clicked() {

    }

    public void navButton3Clicked() {

    }

    public void navButton4Clicked() {

    }
}
