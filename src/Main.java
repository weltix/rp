/*
 * Copyright (c) RESONANCE JSC, 28.05.2019
 */

import gui.SellWindow;
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
