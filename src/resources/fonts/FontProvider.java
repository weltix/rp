/*
 * Copyright (c) RESONANCE JSC, 03.06.2019
 */

package resources.fonts;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class FontProvider {
    public static final String ROBOTO_REGULAR = "Roboto-Regular.ttf";
    public static final String ROBOTO_BOLD = "Roboto-Bold.ttf";
    public static final String FONTAWESOME_REGULAR = "FontAwesome-Regular.ttf";

    private float scaleValue;

    // TODO: 04.06.2019 Сделать возможность пользовательской настройки scale (добавить к уже существующей scaleValue)

    public FontProvider() {
        // We need DPI, but it's unreal to determine it. We will use resolution in pixels at least.
        double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();    //our screen width in pixels
        scaleValue = (float) (1920 / screenWidth);                                //1920px screen width - starting point
        scaleValue = scaleValue * 1.32f;        //scaleValue * 1,32f to test on my display as it is 14' POS DELETE
    }

    public Font getFont(String fontName, float size) {
        InputStream inputStream = FontProvider.class.getResourceAsStream(fontName);
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
}


