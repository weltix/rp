/*
 * Copyright (c) RESONANCE JSC, 28.05.2019
 */

import gui.aspect_ratio_16x9.MainFrame;
import resources.Resources;

import javax.swing.*;

/**
 * Главный класс, с которого начинается работа приложения.
 * Resources.getInstance() вызываем для нормального отображения русскоязычных символов.
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