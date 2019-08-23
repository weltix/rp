/*
 * Copyright (c) RESONANCE JSC, 23.08.2019
 */

package gui.common;

import gui.fonts.FontProvider;

import javax.swing.*;

import static gui.fonts.FontProvider.MENU_ICONS;

/**
 * Class represents one button of navigation panel. Bounded to navigate_button.form.
 */
public class NavigateButton{
    private JPanel button;
    private JLabel icon;
    private JLabel text;
    private JPanel line;
    private JPanel mainPanel;

    public NavigateButton() {
        icon.setFont(FontProvider.getInstance().getFont(MENU_ICONS, 58));
        // html doesn't support external fonts, so will use built-in (null as argument)
        text.setFont(FontProvider.getInstance().getFont(null, 16));
    }
}
