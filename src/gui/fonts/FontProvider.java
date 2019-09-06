/*
 * Copyright (c) RESONANCE JSC, 06.09.2019
 */

package gui.fonts;

import com.sun.istack.internal.Nullable;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

/**
 * Class provides objects of {@link Font} type (font of specified size, created from *.ttf file).
 * Class - Singleton, because it is enough one object of it.
 */

public class FontProvider {
    public static final String ROBOTO_REGULAR = "Roboto-Regular.ttf";
    public static final String ROBOTO_BOLD = "Roboto-Bold.ttf";
    public static final String FONTAWESOME_REGULAR = "FontAwesome-Regular.ttf";
    public static final String MENU_ICONS = "MenuIcons.ttf";

    public static final FontProvider INSTANCE = new FontProvider();
    // we need GraphicsEnvironment here to register certain font in JVM. It is necessary for html text in buttons basically.
    private static GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

    /**
     * Approximate value for text scaling in relation to the template FullHD display.
     * We need DPI value, but it is unreal to get it programmatically. We will rely at least on screen resolution.
     */
    private float scaleValue;

    private FontProvider() {
        double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();    //our screen width in pixels
        scaleValue = (float) (1920 / screenWidth);                                      //1920px screen width - starting point
//        scaleValue = scaleValue * 1.32f;        //scaleValue * 1,32f to test on my display as it is 14' POS, DELETE this later
    }

    public static FontProvider getInstance() {
        return INSTANCE;
    }

    /**
     * Returns an object of specified font with specified size.
     *
     * @param fontName the name of font file. If is {@code null}, then SansSerif font is choosing by default.
     * @param size     the size of font
     */
    public Font getFont(@Nullable String fontName, int size) {
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
        // font registering provides proper font for html text in buttons
        ge.registerFont(font);

        return font;
    }

    // TODO: 04.06.2019 Сделать возможность пользовательской настройки глобального scale всех шрифтов (добавить к уже существующей scaleValue)
}