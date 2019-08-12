/*
 * Copyright (c) RESONANCE JSC, 13.08.2019
 */

package gui.common.utility_components;

import gui.fonts.FontProvider;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;

import static gui.fonts.FontProvider.ROBOTO_REGULAR;

/*
 *  Simple implementation of a Glass Pane that will capture and ignore all
 *  events as well paint the glass pane to give the frame a "disabled" look.
 *
 *  The background color of the glass pane should use a color with an
 *  alpha value to create the disabled look.
 *
 * P.S. Позволяет отлавливать и отменять все нажатия кнопок мыши и клавиатуры.
 * Нам, в основном, нужен как замена JFrame.setEnable(false), которое вызывает мерцание экрана.
 */
public class GlassPane extends JComponent
        implements KeyListener {
    private final static Border MESSAGE_BORDER = new EmptyBorder(10, 10, 10, 10);
    private JLabel
            message = new JLabel();

    public GlassPane() {
        //  Set glass pane properties

        setOpaque(false);
        setLayout(new GridBagLayout());

//        Color base = UIManager.getColor("inactiveCaptionBorder");
        Color base = new Color(184, 207, 229);
        Color background = new Color(base.getRed(), base.getGreen(), base.getBlue(), 128);   //alpha originally was 128
        setBackground(background);

        //  Add a message label to the glass pane

        add(message, new GridBagConstraints());
        message.setOpaque(false);
        message.setBorder(MESSAGE_BORDER);

        //  Disable Mouse, Key and Focus events for the glass pane

        addMouseListener(new MouseAdapter() {
        });
        addMouseMotionListener(new MouseMotionAdapter() {
        });

        addKeyListener(this);

        setFocusTraversalKeysEnabled(false);
    }

    /*
     *  The component is transparent but we want to paint the background
     *  to give it the disabled look.
     */
    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRect(0, 0, getSize().width, getSize().height);
    }

    /*
     *  The	background color of the message label will be the same as the
     *  background of the glass pane without the alpha value
     */
    @Override
    public void setBackground(Color background) {
        super.setBackground(background);
    }

    /*
     *  Implement the KeyListener to consume events
     */
    public void keyPressed(KeyEvent e) {
        e.consume();
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
        e.consume();
    }

    /**
     * Make the glass pane visible and change the cursor
     *
     * @param backgroundColor цвет стеклянной панели (можно использовать полупрозрачный)
     */
    public void activate(Color backgroundColor) {
        if (backgroundColor != null)
            setBackground(backgroundColor);

        // hide cursor from current JFrame
        setCursor(getToolkit().createCustomCursor(
                new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB),
                new Point(),
                null));

        setVisible(true);
        requestFocusInWindow();
    }

    /*
     *  Hide the glass pane and restore the cursor
     */
    public void deactivate() {
        setCursor(null);
        setVisible(false);
    }

    /**
     * Выводит текст на glassPanel.
     *
     * @param text текст, который надо вывести на glassPanel
     */
    public void showText(String text) {
        Color background = new Color(0, 0, 0, 0);   //alpha originally was 128
        setBackground(background);
        if (text != null && text.length() > 0) {
            message.setVisible(true);
            message.setText(text);
            message.setForeground(Color.WHITE);
            message.setFont(FontProvider.getInstance().getFont(ROBOTO_REGULAR, 50));
            activate(background);
        } else {
            message.setVisible(false);
            deactivate();
        }
    }
}

// TODO: 08.08.2019 Надо ли в данном классе поле message?