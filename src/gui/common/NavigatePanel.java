/*
 * Copyright (c) RESONANCE JSC, 21.08.2019
 */

package gui.common;

import gui.fonts.FontProvider;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import static gui.fonts.FontProvider.MENU_ICONS;
import static gui.fonts.FontProvider.ROBOTO_BOLD;

/**
 * Class contains block of navigation panel. Bounded to navigate_panel.form.
 * All buttons made of JPanels. One such {@link JPanel} contains 2 {@link JLabel}.
 * One JLabel is button icon, and another JLabel is button text.
 * At present moment this panel has maximum 5 buttons, but this value can be simply increased in future.
 */
public class NavigatePanel extends JPanel {
    private JPanel mainPanel;
    private JPanel navigatePanel;
    private JLabel headerLabel1;
    private JLabel headerLabel2;
    private JPanel naviButton0;
    private JLabel naviText0;
    private JLabel naviIcon0;
    private JPanel horizontalLine0;
    private JPanel naviButton1;
    private JLabel naviText1;
    private JLabel naviIcon1;
    private JPanel horizontalLine1;
    private JPanel naviButton2;
    private JLabel naviIcon2;
    private JLabel naviText2;
    private JPanel horizontalLine2;
    private JPanel naviButton3;
    private JLabel naviIcon3;
    private JLabel naviText3;
    private JPanel horizontalLine3;
    private JPanel naviButton4;
    private JLabel naviIcon4;
    private JLabel naviText4;
    private JPanel horizontalLine4;
    private JPanel headerPanel;

    private int navButtonsAmount;
    // lists of icons and texts for navigate buttons. Icon is a text actually, but with special font, where no letters, but icons are.
    private List<String> navButtonIcons;
    private List<String> navButtonTexts;

    public NavigatePanel(int buttonsAmount, List<String> icons, List<String> texts) {
        navButtonsAmount = buttonsAmount;
        navButtonIcons = icons;
        navButtonTexts = texts;
//        setNavButtonsAmount(buttonsAmount);
        initComponents();
    }

    private void initComponents() {
        Font menuIcons58 = FontProvider.getInstance().getFont(MENU_ICONS, 58);

        headerLabel1.setFont(FontProvider.getInstance().getFont(ROBOTO_BOLD, 22));
        headerLabel2.setFont(FontProvider.getInstance().getFont(ROBOTO_BOLD, 12));

        Color backgroundColor = new Color(52, 73, 94);
        Color backgroundPressedColor = new Color(65, 91, 122);
        Color foregroundPressedColor = new Color(230, 238, 243);

        // Cycle sets properties for buttons of navigation panel (getComponents() returns only components of 1-st level of nesting).
        // Name of component is entered in *.form file in "name" field.
        int buttonNum = 0;
        if (navigatePanel instanceof Container)
            for (Component child : navigatePanel.getComponents()) {
                if (child.getName() != null
                        && child instanceof JPanel && child.getName().contains("Button")) {

                    if (buttonNum < navButtonsAmount) {
                        // set icon (symbol) and text, also font type and size for them on specified button
                        JLabel tempIconLabel = null;
                        JLabel tempTextLabel = null;
                        for (Component innerChild : ((JPanel) child).getComponents()) {
                            if (innerChild.getName().contains("Icon")) {
                                tempIconLabel = (JLabel) innerChild;
                                tempIconLabel.setFont(menuIcons58);
                                String buttonIcon = navButtonIcons.get(buttonNum);
                                if (buttonIcon == null)
                                    buttonIcon = "";
                                tempIconLabel.setText(buttonIcon);
                            }
                            if (innerChild.getName().contains("Text")) {
                                tempTextLabel = (JLabel) innerChild;
                                // html doesn't support external fonts, so will use built-in
                                tempTextLabel.setFont(FontProvider.getInstance().getFont(null, 16));
                                String buttonText = navButtonTexts.get(buttonNum);
                                if (buttonText == null)
                                    buttonText = "";
                                tempTextLabel.setText(buttonText);
                            }
                        }
                        // icon and text JLabels on current button
                        final JLabel iconLabel = tempIconLabel;
                        final JLabel textLabel = tempTextLabel;

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
                                    switch (child.getName()) {
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
                                child.setBackground(backgroundColor);
                                iconLabel.setForeground(Color.WHITE);
                                textLabel.setForeground(Color.WHITE);
                            }

                            // set look of navigation panel buttons in pressed state
                            private void setPressedState() {
                                child.setBackground(backgroundPressedColor);
                                iconLabel.setForeground(foregroundPressedColor);
                                textLabel.setForeground(foregroundPressedColor);
                            }
                        });
                    } else {

                    }
                    buttonNum++;
                }
            }
    }

    private void setNavButtonsAmount(int amount) {
        switch (amount) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
            default:
                break;
        }


        naviButton0.setEnabled(false);
        naviButton1.setEnabled(false);
        naviButton2.setEnabled(false);
        naviButton3.setEnabled(false);
        naviButton4.setEnabled(false);

        naviIcon0.setVisible(false);
        naviIcon1.setVisible(false);
        naviIcon2.setVisible(false);
        naviIcon3.setVisible(false);
        naviIcon4.setVisible(false);

        naviText0.setVisible(false);
        naviText1.setVisible(false);
        naviText2.setVisible(false);
        naviText3.setVisible(false);
        naviText4.setVisible(false);

        horizontalLine0.setOpaque(false);
        horizontalLine1.setOpaque(false);
        horizontalLine2.setOpaque(false);
        horizontalLine3.setOpaque(false);
        horizontalLine4.setOpaque(false);
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
