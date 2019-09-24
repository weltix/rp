/*
 * Copyright (c) RESONANCE JSC, 24.09.2019
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

    private Robot robot = null;         // object that provides generation of keys presses

    // we define some PlainDocuments for our text field to provide it's different behavior in different contexts
    private final PlainDocument percentTextFieldDocument = new PercentTextFieldDocument(textField);
    private final PlainDocument money72TextFieldDocument = new DigitTextFieldDocument(textField, 7, 2);

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

        // timer for selection text by long mouse pressing
        Timer timerTextFieldLongPress = new Timer(0, this);
        timerTextFieldLongPress.setInitialDelay(1500);
        timerTextFieldLongPress.setActionCommand("timerTextFieldLongPress");
        textField.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                timerTextFieldLongPress.start();
            }
        });

        // timer for generation repeated clicks of numeric keys when a key is in pressed state
        Timer timerRepeatingClicks = new Timer(30, this);
        timerRepeatingClicks.setInitialDelay(500);

        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }

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
                            timerRepeatingClicks.setActionCommand(buttonText);
                            timerRepeatingClicks.start();
                        }

                        @Override
                        public void mouseReleased(MouseEvent e) {
                            timerRepeatingClicks.stop();
                        }

                        @Override
                        public void mouseExited(MouseEvent e) {
                            timerRepeatingClicks.stop();
                        }
                    });
                }
            }
            backSpaceButton.setFont(FontProvider.getInstance().getFont(FONTAWESOME_REGULAR, 54));
        }
    }

    /**
     * Changes the text in text field when digital keys of our programming keyboard are pressing.
     *
     * @param ch symbol identifier of pressed digital key (basically it is symbol represented on the certain button)
     */
    private void changeTextField(char ch) {
        if (ch == '\uf104') {                         // backspace button's char code
            textField.requestFocus();
            robot.keyPress(KeyEvent.VK_BACK_SPACE);
            robot.keyRelease(KeyEvent.VK_BACK_SPACE);
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
        changeTextField(e.getActionCommand().charAt(0));    // don't stop timer for this event here!!! It must work still.

        if ("timerTextFieldLongPress".equals(e.getActionCommand())) {
            ((Timer) e.getSource()).stop();
//            textField.selectAll();
        }
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
     * Method provides some limits and features for {@link KeypadPanel#textField}.
     *
     * @param textFieldDocument type of document.
     */
    public void setTextFieldDocument(String textFieldDocument) {
        switch (textFieldDocument) {
            case "percent":
                textField.setDocument(percentTextFieldDocument);
                break;
            case "money72":
                textField.setDocument(money72TextFieldDocument);
                break;
            default:
                break;
        }
    }

    /**
     * Class provides some limits and features for textField, that use it as document:
     * 1. Max digits amount of integer part (parameter of constructor);
     * 2. Max digits amount of fraction part (parameter of constructor);
     * 3. Only one "." may be entered;
     * 4. Integer part never will be empty. In such case "0" is entered automatically;
     * 5. Any excessive leading "0"s truncated automatically;
     * 6. Fraction part is removed if "." is removed.
     */
    private static class DigitTextFieldDocument extends PlainDocument {

        private final String regex;
        private final String regexEmptyIntegerPart;     // to avoid '' or '.32'. Will be 0 or 0.32
        private final String regexExcessLeadingZero;    // to avoid 02, 003, 0005.43, etc. Will be 2, 3, 5.43
        JTextField textField;
        private StringBuilder strBuilder = new StringBuilder();
        // flag needful in "remove" method to determine if it was called by "replace" method or not and, consequently,
        // will "insertString" method be called after it or not.
        private boolean insertStringExpected = false;

        /**
         * @param integerDigits  number or integer digits before comma, must be >= 1
         * @param fractionDigits number or fraction digits after comma, must be >= 0
         */
        DigitTextFieldDocument(JTextField textField, int integerDigits, int fractionDigits) {
            this.textField = textField;
            regex = String.format("(\\d{1,%d}(\\.\\d{0,%d})?){1}", integerDigits, fractionDigits);
            regexEmptyIntegerPart = String.format("((\\.\\d{0,%d})?){1}", fractionDigits);
            regexExcessLeadingZero = "0+\\d+.*";
        }

        @Override
        public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
            insertStringExpected = false;
            String currentText = this.getText(0, getLength());
            strBuilder.delete(0, strBuilder.length()).append(currentText).insert(offset, str);
            if (strBuilder.toString().matches(regex)) {
                super.insertString(offset, str, attr);
                if (strBuilder.toString().matches(regexExcessLeadingZero)) {  // if result of insert will be "0" + 1 or more digits
                    int leadingZerosAmount = textField.getText().length() - textField.getText().replaceFirst("0+", "").length();
                    remove(0, leadingZerosAmount);                      // than remove all leading zeros (02, 0004 become 2, 4)
                }
            }
        }

        /**
         * This method calls first {@link PlainDocument#remove} method then {@link PlainDocument#insertString} method.
         */
        @Override
        public void replace(int offset, int length, String text, AttributeSet attrs) throws
                BadLocationException {
            String currentText = this.getText(0, getLength()).replaceAll(",", ".");
            strBuilder.delete(0, strBuilder.length()).append(currentText).replace(offset, offset + length, text);
            if (strBuilder.toString().matches(regex)) {
                insertStringExpected = true;
                super.replace(offset, length, text, attrs);
            } else if (strBuilder.toString().matches(regexEmptyIntegerPart)) {  // if result of replace will be ".*" or ""
                insertStringExpected = true;                                    // than
                super.replace(offset, length, "0" + text, attrs);          // we add "0" in front ( result "0.*" or "0")
            }
        }

        @Override
        public void remove(int offs, int len) throws BadLocationException {
            String currentText = this.getText(0, getLength());
            if (textField.getText().substring(offs, offs + len).contains(".")) {
                len = textField.getText().length() - offs;             // fraction part is deleted if "." is deleted
            }
            strBuilder.delete(0, strBuilder.length()).append(currentText).delete(offs, offs + len);
            if (strBuilder.toString().matches(regexExcessLeadingZero)     // if result of remove will be "0" + any digits,
                    && !insertStringExpected) {                           // and if insert method will not follow
                super.remove(offs, len);
                int leadingZerosAmount = textField.getText().length() - textField.getText().replaceFirst("0+", "").length();
                super.remove(0, leadingZerosAmount);                 // than remove all leading zeros (02, 0004 become 2, 4)
                remove(0, 0);       // recycling call of this method if we got result ".*" from "200.*" deleting 2
            } else if (strBuilder.toString().matches(regex)) {
                super.remove(offs, len);
            } else if (strBuilder.toString().matches(regexEmptyIntegerPart)) {    // if result of remove will be  ".*" or ""
                super.remove(offs, len);
                if (!insertStringExpected)                                      // if insertString method will not follows
                    insertString(0, "0", null);                 // than we add manually "0" in front ( result "0.*" or "0")
            }
        }
    }

    /**
     * Class provides some limits and features for textField, that use it as document:
     * 1. Max value (in text form), that may be entered into the field <= 100.00;
     * 2. All other limits derived from super class.
     */
    private static class PercentTextFieldDocument extends DigitTextFieldDocument {

        PercentTextFieldDocument(JTextField textField) {
            super(textField, 3, 2);
        }

        /**
         * Method checks if entered into the text field value satisfies certain conditions and calls appropriate method
         * of superclass if true. Condition: value must be <= 100.00
         */
        @Override
        public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
            String currentText = this.getText(0, getLength()).concat(str);
            Double textToDouble;
            try {
                textToDouble = Double.valueOf(currentText);
            } catch (NumberFormatException e) {
                textToDouble = 101.0;
            }
            if (textToDouble <= 100.0) {
                if (textToDouble == 100.0 && currentText.contains("."))
                    return;
                super.insertString(offset, str, attr);
            }
        }
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

    // TODO: 25.07.2019 Можно сделать выделение текста в текстовом поле по долгому нажатию левой кнопки мыши
    // TODO: 26.07.2019 Подобрать более оптимальный фон кнопки при её нажатии
}