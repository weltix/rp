/*
 * Copyright (c) RESONANCE JSC, 19.09.2019
 */

package gui.common;

import gui.fonts.FontProvider;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.*;
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
        if (ch == '\uf104') {                         // backspace button's char code
//            if (textField.getSelectedText() == null)  // if there are no any selected text to delete, one char before caret will be deleted
//                textField.select(caretPosition - 1, caretPosition);
////            textField.replaceSelection("");
//            try {
//                int selectedLength = textField.getSelectionEnd() - textField.getSelectionStart();
//                textField.getDocument().remove(textField.getSelectionStart(), selectedLength);
//            } catch (BadLocationException e) {
//                e.printStackTrace();
//            }
            Robot robot;
            try {
                robot = new Robot();
                robot.keyPress(KeyEvent.VK_BACK_SPACE);
                robot.keyRelease(KeyEvent.VK_BACK_SPACE);
            } catch (AWTException e) {
                e.printStackTrace();
            }


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

    /**
     * Method defines some limits for {@link KeypadPanel#textField}.
     *
     * @param integerDigits  number or integer digits before comma
     * @param fractionDigits number or fraction digits after comma
     */
    public void setFormattedTextField(int integerDigits, int fractionDigits) {
        textField.setDocument(new PlainDocument() {
            private String regex = String.format("(\\d{1,%d}(\\.\\d{0,%d})?)?", integerDigits, fractionDigits);
            private String regexEmptyIntegerPart = String.format("((\\.\\d{0,%d})?)?", fractionDigits);
            private String currentText;
            private StringBuilder strBuilder = new StringBuilder();

            @Override
            public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
                System.out.println("insertString");
                currentText = this.getText(0, getLength());
                strBuilder.delete(0, strBuilder.length()).append(currentText).insert(offset, str);
                System.out.println("currentText = " + currentText + " strBuilder = " + strBuilder.toString());
                if (strBuilder.toString().matches(regex))
                    super.insertString(offset, str, attr);
            }

            @Override
            public void replace(int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                System.out.println("replace");
                currentText = this.getText(0, getLength());
                strBuilder.delete(0, strBuilder.length()).append(currentText).replace(offset, offset + length, text);
                System.out.println("currentText = " + currentText + " strBuilder = " + strBuilder.toString());
                if (strBuilder.toString().matches(regex))
                    super.replace(offset, length, text, attrs);
//                else if ((strBuilder.toString().matches(regexEmptyIntegerPart)) && (textField.getSelectionEnd() == 1)) {
//                    insertString(offset, "0", null);
//                    super.remove(offs, len);
//                    textField.select(0, 1);
//                }
            }

            @Override
            public void remove(int offs, int len) throws BadLocationException {
                System.out.println("remove");
                currentText = this.getText(0, getLength());
                strBuilder.delete(0, strBuilder.length()).append(currentText).delete(offs, offs + len);
                System.out.println("currentText = " + currentText + " strBuilder = " + strBuilder.toString());
                if (strBuilder.toString().matches(regex))
                    super.remove(offs, len);
                else if (strBuilder.toString().matches(regexEmptyIntegerPart)) {
                    super.remove(0, 1);
                    insertString(0, "0", null);
                    textField.select(0, 1);
                }
            }
        });
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