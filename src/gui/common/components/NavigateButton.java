/*
 * Copyright (c) RESONANCE JSC, 11.10.2019
 */

package gui.common.components;

import gui.fonts.FontProvider;

import javax.swing.*;

import static gui.fonts.FontProvider.MENU_ICONS;
import static gui.fonts.FontProvider.ROBOTO_REGULAR;

/**
 * Class represents one button of navigation panel. Bound to navigate_button.form.
 */
public class NavigateButton{
    private JPanel button;
    private JLabel icon;
    private JLabel text;
    private JPanel line;
    private JPanel mainPanel;

    public NavigateButton() {
        icon.setFont(FontProvider.getInstance().getFont(MENU_ICONS, 58));
        text.setFont(FontProvider.getInstance().getFont(ROBOTO_REGULAR, 16));
    }
}

// TODO: 09.09.2019 Переделать панельки на кнопки?