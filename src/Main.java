/*
 * Copyright (c) RESONANCE JSC, 20.08.2019
 */

import gui.common.MainFrame;
import resources.Resources;

import javax.swing.*;

/**
 * The main class from which the application starts.
 * Resources.getInstance() is called for normal displaying of cyrillic symbols.
 */

public class Main {

    public static void main(String[] args) {
        Resources.getInstance();
        SwingUtilities.invokeLater(Main::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        new MainFrame();
    }
}