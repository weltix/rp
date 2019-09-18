/*
 * Copyright (c) RESONANCE JSC, 18.09.2019
 */

package gui.common;

import gui.fonts.FontProvider;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static gui.fonts.FontProvider.FONTAWESOME_REGULAR;
import static gui.fonts.FontProvider.ROBOTO_REGULAR;

/**
 * Class contains numeric keyboard block. Bounded to keypad_panel.form
 */
public class KeypadPanel extends JComponent implements ActionListener {
    private JButton actionButton0;
    private JButton actionButton1;
    private JButton actionButton2;
    private JTextField textField;
    private JButton a0Button;
    private JPanel centerPanel;
    private JButton dotButton;
    private JButton cButton;
    private JButton a7Button;
    private JButton a8Button;
    private JButton a9Button;
    private JButton a4Button;
    private JButton a5Button;
    private JButton a6Button;
    private JButton a1Button;
    private JButton a2Button;
    private JButton a3Button;
    private JButton backSpaceButton;
    private JPanel keyPadPanel;
    private JPanel mainPanel;
    private JPanel actionButtonPanel;
    private JPanel verticalLine;
    private JPanel textFieldPanel;

    public KeypadPanel() {
        initComponents();
    }

    private void initComponents() {
        Font robotoRegular34 = FontProvider.getInstance().getFont(ROBOTO_REGULAR, 34);
        Font robotoRegular50 = FontProvider.getInstance().getFont(ROBOTO_REGULAR, 50);

        actionButton0.setFont(robotoRegular34);
        actionButton1.setFont(robotoRegular34);
        actionButton2.setFont(robotoRegular34);

        textField.setFont(FontProvider.getInstance().getFont(ROBOTO_REGULAR, 40));
        textField.setBorder(BorderFactory.createEmptyBorder());

        textField.setDocument(new PlainDocument() {
            @Override
            public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
                String text = this.getText(0, getLength());
//                System.out.println("text = " + text + " textLength = " + getLength());
//                if ((str == null)
//                        || (str.equals(".") && text.contains("."))
//                        || (str.equals(".") && offset == 0)
//                        || !str.matches("\\d")) {
//                    System.out.println("return");
//                    return;
//                }
//                int dotPosition = text.indexOf('.');    // if '.' is already in textField, than get it position, else get -1
//                System.out.println("dotPosition = " + dotPosition + " offset = " + offset + " getLength() = " + getLength());
//
//                if (!text.contains(".")) {
//                    if ((text.length() < 7) || (text.length() < 8 && str.equals(".")))
//                        super.insertString(offset, str, attr);
//                } else if ((offset <= dotPosition && dotPosition < 7)
//                        || (offset > dotPosition && text.length() <= dotPosition + 2))
//                    super.insertString(offset, str, attr);
                System.out.println("insertString");
                String regex = "\\d{1,7}(\\.\\d{0,2})?";
                StringBuilder builder = new StringBuilder(text).insert(offset, str);
                if (builder.toString().matches(regex))
                    super.insertString(offset, str, attr);
            }

            @Override
            public void remove(int offs, int len) throws BadLocationException {
                String text = this.getText(0, getLength());
                System.out.println("removeString");
                String regex = "\\d{1,7}(\\.\\d{0,2})?";
                StringBuilder builder = new StringBuilder(text).delete(offs, len);
                if (builder.toString().matches(regex))
                    super.remove(offs, len);
            }
        });

        // timer for generation repeated clicks of numeric keys when a key is in pressed state
        Timer timer = new Timer(30, this);
        timer.setInitialDelay(500);

        // cycle sets properties for numeric keys (12 buttons) (getComponents() returns only components of 1-st level of nesting)
        if (centerPanel instanceof Container) {
            // add backSpaceButton to processing list of buttons, because it is not direct child of centerPanel (not included in component list)
            List<Component> componentsList = Arrays.asList(centerPanel.getComponents());
            List<Component> processingComponents = new ArrayList<>(componentsList);
            processingComponents.add(backSpaceButton);

            for (Component child : processingComponents) {
                if (child instanceof JButton) {
                    child.setFont(robotoRegular50);
                    child.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mousePressed(MouseEvent e) {
                            String buttonText = ((JButton) e.getSource()).getText();
                            changeTextField(buttonText.charAt(0));
                            timer.setActionCommand(buttonText);
                            timer.start();
                        }

                        @Override
                        public void mouseReleased(MouseEvent e) {
                            timer.stop();
                        }

                        @Override
                        public void mouseExited(MouseEvent e) {
                            timer.stop();
                        }
                    });
                }
            }
            backSpaceButton.setFont(FontProvider.getInstance().getFont(FONTAWESOME_REGULAR, 54));
        }
    }

    /**
     * Changes the text in text field when digital keys are pressing.
     *
     * @param ch symbol identifier of pressed digital key (basically it is symbol represented on the certain button)
     */
    private void changeTextField(char ch) {
        int caretPosition = textField.getCaretPosition();
        if (ch == '\uf104') {       // backspace button's char code
            textField.select(caretPosition - 1, caretPosition);
            textField.replaceSelection("");
        } else if (ch == 'C')
            textField.setText("");
        else
            textField.replaceSelection(String.valueOf(ch));
    }

    /**
     * Method is called when action occurs (button pressed or timer triggers).
     *
     * @param e event, that occurs.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        changeTextField(e.getActionCommand().charAt(0));
    }

    /**
     * Method defines how many keys to show in bottom part of numeric keyboard.
     * (for example, 1 key "Search", or 2 keys "OK" or "Cancel")
     */
    public void setActionButtonsAmount(int amount) {
        CardLayout cardLayout = (CardLayout) (actionButtonPanel.getLayout());
        switch (amount) {
            case 2:
                cardLayout.show(actionButtonPanel, "twoActionButtons");
                break;
            case 1:
            default:
                cardLayout.show(actionButtonPanel, "oneActionButton");
                break;
        }
    }

    /**
     * Key "0" gets double width. Key "." and vertical line-delimiter deleted.
     */
    public void doubleWidthA0Button() {
        GridBagLayout gbLayout = (GridBagLayout) centerPanel.getLayout();
        GridBagConstraints constraintsA0Button = gbLayout.getConstraints(a0Button);
        constraintsA0Button.gridwidth = 3;
        centerPanel.remove(a0Button);
        centerPanel.remove(dotButton);
        centerPanel.remove(verticalLine);
        centerPanel.add(a0Button, constraintsA0Button);
    }

    /**
     * Method changes {@link JTextField} with {@link JPasswordField}.
     */
    public void switchToPasswordTextField() {
        GridBagLayout textFieldPanelLayout = (GridBagLayout) textFieldPanel.getLayout();
        GridBagConstraints constraintsTextField = textFieldPanelLayout.getConstraints(textField);
        textFieldPanel.remove(textField);
        textField = new JPasswordField();
        textField.setFont(FontProvider.getInstance().getFont(ROBOTO_REGULAR, 60));
        textField.setBorder(BorderFactory.createEmptyBorder());
        textFieldPanel.add(textField, constraintsTextField);
    }

    @Override
    public Dimension getSize() {
        return mainPanel.getSize();
    }

    @Override
    public Point getLocationOnScreen() throws IllegalComponentStateException {
        return mainPanel.getLocationOnScreen();
    }

    public JButton getActionButton0() {
        return actionButton0;
    }

    public JButton getActionButton1() {
        return actionButton1;
    }

    public JButton getActionButton2() {
        return actionButton2;
    }

    public JTextField getTextField() {
        return textField;
    }

    // TODO: 25.07.2019 Можно сделать выделение текста в текстовом поле по долгому нажатию левой кнопки мыши, а также по двойному щелчку
    // TODO: 26.07.2019 Подобрать более оптимальный фон кнопки при её нажатии
}