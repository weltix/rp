/*
 * Copyright (c) RESONANCE JSC, 17.07.2019
 */

package gui;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

/**
 * Класс, предоставляющий объекты типа Font (шрифт заданного размера, созданный из файла *.ttf).
 */

public class FontProvider {
    public static final String ROBOTO_REGULAR = "Roboto-Regular.ttf";
    public static final String ROBOTO_BOLD = "Roboto-Bold.ttf";
    public static final String FONTAWESOME_REGULAR = "FontAwesome-Regular.ttf";
    public static final String MENU_ICONS = "MenuIcons.ttf";

    /**
     * Приблизительное значение масштабирования текста по отношению к шаблонному дисплею FullHD.
     * Нам необходимо значение DPI, но определить его нереально. Поэтому будет опираться хоть на разрешение экрана.
     */
    private float scaleValue;

    public FontProvider() {
        double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();    //our screen width in pixels
        scaleValue = (float) (1920 / screenWidth);                                      //1920px screen width - starting point
//        scaleValue = scaleValue * 1.32f;        //scaleValue * 1,32f to test on my display as it is 14' POS, DELETE this later
    }

    /**
     * Возвращает объект заданного шрифта, заданного размера
     *
     * @param fontName название файла шрифта
     * @param size размер шрифта
     */
    public Font getFont(String fontName, float size) {
        InputStream inputStream = FontProvider.class.getResourceAsStream("fonts/" + fontName);
        Font font = null;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, inputStream).deriveFont(size / scaleValue);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return font;
    }

    // TODO: 04.06.2019 Сделать возможность пользовательской настройки глобального scale всех шрифтов (добавить к уже существующей scaleValue)
}