/*
 * Copyright (c) RESONANCE JSC, 14.08.2019
 */

package gui.fonts;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

/**
 * Класс, предоставляющий объекты типа Font (шрифт заданного размера, созданный из файла *.ttf).
 * Класс - Singleton, так как нам достаточно единственного его объекта.
 */

public class FontProvider {
    public static final String ROBOTO_REGULAR = "Roboto-Regular.ttf";
    public static final String ROBOTO_BOLD = "Roboto-Bold.ttf";
    public static final String FONTAWESOME_REGULAR = "FontAwesome-Regular.ttf";
    public static final String MENU_ICONS = "MenuIcons.ttf";

    public static final FontProvider INSTANCE = new FontProvider();

    /**
     * Приблизительное значение масштабирования текста по отношению к шаблонному дисплею FullHD.
     * Нам необходимо значение DPI, но определить его программно нереально. Будет опираться хоть на разрешение экрана.
     */
    private float scaleValue;

    private FontProvider() {
        double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();    //our screen width in pixels
        scaleValue = (float) (1920 / screenWidth);                                      //1920px screen width - starting point
        scaleValue = scaleValue * 1.32f;        //scaleValue * 1,32f to test on my display as it is 14' POS, DELETE this later
    }

    public static FontProvider getInstance() {
        return INSTANCE;
    }

    /**
     * Возвращает объект заданного шрифта c заданным размером
     *
     * @param fontName название файла шрифта
     * @param size     размер шрифта
     */
    public Font getFont(String fontName, int size) {
        Font font = null;
        float actualSize = size / scaleValue;

        if (fontName == null) {
            font = new Font(Font.SANS_SERIF, Font.PLAIN, Math.round(actualSize));
            return font;
        }

        InputStream inputStream = FontProvider.class.getResourceAsStream(fontName);
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, inputStream).deriveFont(actualSize);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return font;
    }

    // TODO: 04.06.2019 Сделать возможность пользовательской настройки глобального scale всех шрифтов (добавить к уже существующей scaleValue)
}