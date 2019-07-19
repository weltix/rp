/*
 * Copyright (c) RESONANCE JSC, 28.05.2019
 */

import gui.aspect_ratio_16x9.SellWindow;
import resources.Resources;

import javax.swing.*;

/**
 * @author Dmitriy Bludov
 */

public class Main {
    public static Resources resources;

    public static void main(String[] args) {
        resources = new Resources();
        SwingUtilities.invokeLater(Main::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        new SellWindow();
    }
}
