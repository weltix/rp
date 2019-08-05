/*
 * Copyright (c) RESONANCE JSC, 30.07.2019
 */

package gui.custom_components;

import gui.FontProvider;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.LayerUI;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

import static gui.FontProvider.ROBOTO_REGULAR;

/*
 *  Simple implementation of a Glass Pane that will capture and ignore all
 *  events as well paint the glass pane to give the frame a "disabled" look.
 *
 *  The background color of the glass pane should use a color with an
 *  alpha value to create the disabled look.
 *
 * P.S. Позволяет отлавливать и отменять все нажатия кнопок мыши и клавиатуры.
 * Заменяет JFrame.setEnable(false), которое вызывает мерцание экрана.
 */
public class GlassPane extends JComponent
        implements KeyListener {
    private final static Border MESSAGE_BORDER = new EmptyBorder(10, 10, 10, 10);
    private JLabel message = new JLabel();

   public LayerUI<JComponent> layerUI = new BlurLayerUI();
    public JLayer<JComponent> jlayer = new JLayer<JComponent>(this, layerUI);


    public GlassPane() {
        //  Set glass pane properties


        setDoubleBuffered(true);
        setOpaque(false);
        setLayout(new GridBagLayout());

        Color base = UIManager.getColor("inactiveCaptionBorder");
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

    /*
     *  Make the glass pane visible and change the cursor
     *
     *  A message can be displayed and it will be centered on the frame.
     *
     * @param text текст в центре экрана
     * @param backgroundColor цвет стеклянной панели (можно использовать полупрозрачный)
     */
    public void activate(String text, Color backgroundColor) {
        if (text != null && text.length() > 0) {
            message.setVisible(true);
            message.setText(text);
            message.setForeground(Color.WHITE);
            message.setFont(FontProvider.getInstance().getFont(ROBOTO_REGULAR, 50f));
        } else
            message.setVisible(false);

        if (backgroundColor != null)
            setBackground(backgroundColor);

        // hide cursor from current JFrame
        setCursor(getToolkit().createCustomCursor(
                new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB),
                new Point(),
                null));

        setVisible(true);
//        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        requestFocusInWindow();
    }

    /*
     *  Hide the glass pane and restore the cursor
     */
    public void deactivate() {
        setCursor(null);
        setVisible(false);
    }




    class BlurLayerUI extends LayerUI<JComponent> {
        private BufferedImage mOffscreenImage;
        private BufferedImageOp mOperation;

        public BlurLayerUI() {
            float ninth = 1.0f / 9.0f;
            float[] blurKernel = {
                    ninth, ninth, ninth,
                    ninth, ninth, ninth,
                    ninth, ninth, ninth
            };
            mOperation = new ConvolveOp(
                    new Kernel(3, 3, blurKernel),
                    ConvolveOp.EDGE_NO_OP, null);
        }

        @Override
        public void paint(Graphics g, JComponent c) {
            int w = c.getWidth();
            int h = c.getHeight();

            if (w == 0 || h == 0) {
                return;
            }

            // Only create the offscreen image if the one we have
            // is the wrong size.
            if (mOffscreenImage == null ||
                    mOffscreenImage.getWidth() != w ||
                    mOffscreenImage.getHeight() != h) {
                mOffscreenImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            }

            Graphics2D ig2 = mOffscreenImage.createGraphics();
            ig2.setClip(g.getClip());
            super.paint(ig2, c);
            ig2.dispose();

            Graphics2D g2 = (Graphics2D) g;
            g2.drawImage(mOffscreenImage, mOperation, 0, 0);
        }
    }
}