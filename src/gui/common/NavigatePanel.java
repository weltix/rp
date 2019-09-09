/*
 * Copyright (c) RESONANCE JSC, 09.09.2019
 */

package gui.common;

import com.sun.istack.internal.Nullable;
import gui.fonts.FontProvider;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.function.Consumer;

import static gui.fonts.FontProvider.ROBOTO_BOLD;

/**
 * Class describes navigation panel. Bounded to navigate_panel.form.
 * We enforced to create from start all navigate buttons, and then make unnecessary buttons invisible or transparent,
 * because approach with adding buttons is not work properly (division inaccuracy when height is calculated).
 */
public class NavigatePanel extends JPanel {
    private JPanel mainPanel;
    private JPanel navigatePanel;
    private JLabel headerLabel1;
    private JLabel headerLabel2;
    private JPanel headerPanel;
    private JPanel paddingPanel;

    // lists of icons and texts for navigate buttons. Icon is a text actually, but with special font, where no letters, but icons are.
    private List<String> navButtonIcons;
    private List<String> navButtonTexts;
    // default icon font is set in NavigationButton.class, but it can be changed for certain NavigatePanel object.
    private Font iconsFont;
    private Consumer<Integer> navButtonActions;    // object of functional interface Consumer, hold actions for navigation buttons
    private int navButtonsAmount;

    public NavigatePanel(List<String> icons, List<String> texts, @Nullable Font font, Consumer<Integer> actions) {
        navButtonIcons = icons;
        navButtonTexts = texts;
        iconsFont = font;
        navButtonActions = actions;
        navButtonsAmount = icons.size();
        initComponents();
    }

    private void initComponents() {
        headerLabel1.setFont(FontProvider.getInstance().getFont(ROBOTO_BOLD, 22));
        headerLabel2.setFont(FontProvider.getInstance().getFont(ROBOTO_BOLD, 14));

        Color backgroundColor = new Color(52, 73, 94);
        Color backgroundPressedColor = new Color(65, 91, 122);
        Color foregroundPressedColor = new Color(230, 238, 243);

        // Cycle sets properties for buttons of navigation panel (getComponents() returns only components of 1-st level of nesting).
        // Name of component is entered in *.form file in "name" field.
        int buttonNum = 0;
        if (navigatePanel instanceof Container)
            for (Component child : navigatePanel.getComponents()) {
                if (child.getName() != null
                        && child instanceof JPanel
                        && child.getName().contains("NavigateButton.mainPanel")) {

                    JPanel naviButton = (JPanel) ((JPanel) child).getComponent(0);
                    JLabel naviButtonIcon = (JLabel) naviButton.getComponent(0);
                    JLabel naviButtonText = (JLabel) naviButton.getComponent(1);
                    JPanel delimiterLine = (JPanel) ((JPanel) child).getComponent(1);

                    if (iconsFont != null)
                        naviButtonIcon.setFont(iconsFont);

                    if (buttonNum < navButtonsAmount) {
                        naviButtonIcon.setText(navButtonIcons.get(buttonNum));
                        naviButtonText.setText(navButtonTexts.get(buttonNum));
                        final int buttonNumFinal = buttonNum;
                        // set listeners for each button. Depending on event we change look of button, or perform specified action.
                        naviButton.addMouseListener(new MouseAdapter() {
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
                                    navButtonActions.accept(buttonNumFinal);
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
                        naviButton.setOpaque(false);
                        naviButtonIcon.setVisible(false);
                        naviButtonText.setVisible(false);
                        delimiterLine.setOpaque(false);
                    }
                    buttonNum++;
                }
            }
    }

    /**
     * Header panel is panel with title like "RESPOS MARKET" in top left corner of screen.
     */
    public int getHeaderPanelHeight() {
        return headerPanel.getHeight();
    }
}