/*
 * Copyright (c) RESONANCE JSC, 07.10.2019
 */

package gui.common.ui;

import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

/**
 * Class set UI for ScrollBar.
 */
public class DefaultScrollBarUI extends BasicScrollBarUI {

    private final Dimension buttonSize;

    public DefaultScrollBarUI(int buttonSize) {
        super();
        this.buttonSize = new Dimension(buttonSize, buttonSize);
    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
        return new BasicArrowButton(orientation,
                new Color(235, 235, 235),
                new Color(200, 200, 200),
                new Color(128, 128, 128),
                new Color(200, 200, 200)) {
            @Override
            public Dimension getPreferredSize() {
                return buttonSize;
            }
        };
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        return new BasicArrowButton(orientation,
                new Color(235, 235, 235),
                new Color(200, 200, 200),
                new Color(128, 128, 128),
                new Color(200, 200, 200)) {
            @Override
            public Dimension getPreferredSize() {
                return buttonSize;
            }
        };
    }

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle r) {
        c.setBackground(new Color(250, 250, 250));
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle r) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        Color color = null;
        JScrollBar sb = (JScrollBar) c;
        if (!sb.isEnabled() || r.width > r.height) {
            return;
        } else if (isDragging) {
            color = new Color(246, 246, 246);
        } else {
            color = new Color(235, 235, 235);
        }
        g2.setPaint(color);
        g2.fillRect(r.x, r.y, r.width, r.height);
        g2.setPaint(new Color(200, 200, 200));
        g2.drawRect(r.x, r.y, r.width - 1, r.height);
        g2.dispose();
    }

    @Override
    protected void setThumbBounds(int x, int y, int width, int height) {
        super.setThumbBounds(x, y, width, height);
        scrollbar.repaint();
    }
}

 //TODO: 07.10.2019 Настройка scrollBar кнопок и ползунка (оптимизировать)