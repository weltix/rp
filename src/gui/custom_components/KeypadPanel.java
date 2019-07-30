/*
 * Copyright (c) RESONANCE JSC, 01.07.2019
 */

package gui.custom_components;

import gui.FontProvider;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static gui.FontProvider.FONTAWESOME_REGULAR;
import static gui.FontProvider.ROBOTO_REGULAR;

/**
 * Класс, содержащий в себе блок цифровой клавиатуры.
 * Использует форму keypad_panel.form
 */
public class KeypadPanel extends JComponent implements ActionListener {
    public JButton actionButton0;
    public JButton actionButton1;
    public JButton actionButton2;
    public JTextField textField;

    private JButton a0Button;
    private JPanel mainPanel;
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
    private JPanel parentPanel;
    private JPanel actionButtonPanel;
    private JPanel verticalLine;
    private JPanel textFieldPanel;

    private FontProvider fontProvider = new FontProvider();

    public KeypadPanel() {
        initComponents();
    }

    private void initComponents() {
        Font robotoRegular34 = fontProvider.getFont(ROBOTO_REGULAR, 34f);
        Font robotoRegular50 = fontProvider.getFont(ROBOTO_REGULAR, 50f);

        actionButton0.setFont(robotoRegular34);
        actionButton1.setFont(robotoRegular34);
        actionButton2.setFont(robotoRegular34);

        backSpaceButton.setFont(fontProvider.getFont(FONTAWESOME_REGULAR, 54f));
        textField.setFont(fontProvider.getFont(ROBOTO_REGULAR, 40f));
        textField.setBorder(BorderFactory.createEmptyBorder());

        // таймер для генерации повторных срабатываний цифровых клавиш при их удержании в нажатом состоянии
        Timer timer = new Timer(30, this);
        timer.setInitialDelay(500);

        // циклически задаём свойства цифровым клавишам (12 шт.) (getComponents() возвращает компоненты 1-го уровня вложенности)
        if (mainPanel instanceof Container) {
            for (Component child : mainPanel.getComponents()) {
                if (child instanceof JButton) {
                    child.setFont(robotoRegular50);
                    child.addMouseListener(new MouseListener() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                        }

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
                        public void mouseEntered(MouseEvent e) {
                        }

                        @Override
                        public void mouseExited(MouseEvent e) {
                            timer.stop();
                        }
                    });
                }
            }
        }

        backSpaceButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                String buttonText = String.valueOf('\b');
                changeTextField(buttonText.charAt(0));
                timer.setActionCommand(buttonText);
                timer.start();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                timer.stop();
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {
                timer.stop();
            }
        });
    }

    /**
     * Обеспечивает изменение текстового поля при нажатии на цифровые клавиши
     *
     * @param ch символ-идентификатор нажатой цифровой клавиши (в основном, это - символ на самой кнопке)
     */
    private void changeTextField(char ch) {
        int caretPosition = textField.getCaretPosition();
        if (ch == '\b') {
            textField.select(caretPosition - 1, caretPosition);
            textField.replaceSelection("");
        } else if (ch == 'C')
            textField.setText("");
        else
            textField.replaceSelection(String.valueOf(ch));
    }

    /**
     * Метод обеспечивает генерацию повторных срабатываний при длительном нажатии на цифровую клавишу.
     * Выполняется с заданной в таймере частотой после его срабатыванию.
     *
     * @param e событие, произошедшее по срабатыванию таймера
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        changeTextField(e.getActionCommand().charAt(0));
    }

    /**
     * Обеспечивает выбор количества кнопок действий внизу клавиатуры.
     * Метод определяет какие кнопки показывать внизу
     * (например, одну кнопку "Поиск", или же 2 кнопки "ОК" и "Отмена" и т.д.).
     *
     * @param mode название-идентификатор используемого режима.
     *             Заранее определены режимы {@code oneActionButton} и {@code twoActionButtons}
     */
    public void setActionButtons(String mode) {
        CardLayout cardLayout = (CardLayout) (actionButtonPanel.getLayout());
        if (mode.equals("oneActionButton"))
            cardLayout.show(actionButtonPanel, "oneActionButton");
        else if (mode.equals("twoActionButtons"))
            cardLayout.show(actionButtonPanel, "twoActionButtons");
        else System.err.println("Unknown mode for choosing amount of action buttons.");
    }

    /**
     * Клавиша ноль получает двойную ширину. Клавиша "точка" и вертикальная полоса-делитель удаляются.
     */
    public void doubleWidthA0Button() {
        GridBagLayout mainPanelLayout = (GridBagLayout) mainPanel.getLayout();
        GridBagConstraints constraintsA0Button = mainPanelLayout.getConstraints(a0Button);
        constraintsA0Button.gridwidth = 3;
        mainPanel.remove(dotButton);
        mainPanel.remove(verticalLine);
        mainPanel.add(a0Button, constraintsA0Button);
    }

    /**
     * Код делает из обычного текстового поля поле для ввода паролей.
     */
    public void switchToPasswordTextField() {
        GridBagLayout textFieldPanelLayout = (GridBagLayout) textFieldPanel.getLayout();
        GridBagConstraints constraintsTextField = textFieldPanelLayout.getConstraints(textField);
        textFieldPanel.remove(textField);
        textField = new JPasswordField();
        textField.setFont(fontProvider.getFont(ROBOTO_REGULAR, 60f));
        textField.setBorder(BorderFactory.createEmptyBorder());
        textFieldPanel.add(textField, constraintsTextField);
    }

    /**
     * Код переопределяет возвращаемый размер цифровой клавиатуры.
     */
    @Override
    public Dimension getSize() {
        return keyPadPanel.getSize();
    }

    // TODO: 25.07.2019 Можно сделать выделение текста в текстовом поле по долгому нажатию левой кнопки мыши, а также по двойному щелчку
    // TODO: 26.07.2019 Подобрать более оптимальный фон кнопки при её нажатии
}
