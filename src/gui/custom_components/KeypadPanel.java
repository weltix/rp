/*
 * Copyright (c) RESONANCE JSC, 09.08.2019
 */

package gui.custom_components;

import gui.FontProvider;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static gui.FontProvider.FONTAWESOME_REGULAR;
import static gui.FontProvider.ROBOTO_REGULAR;

/**
 * Класс, содержащий в себе блок цифровой клавиатуры.
 * Использует форму keypad_panel.form
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
        Font robotoRegular34 = FontProvider.getInstance().getFont(ROBOTO_REGULAR, 34f);
        Font robotoRegular50 = FontProvider.getInstance().getFont(ROBOTO_REGULAR, 50f);

        actionButton0.setFont(robotoRegular34);
        actionButton1.setFont(robotoRegular34);
        actionButton2.setFont(robotoRegular34);

        textField.setFont(FontProvider.getInstance().getFont(ROBOTO_REGULAR, 40f));
        textField.setBorder(BorderFactory.createEmptyBorder());

        // таймер для генерации повторных срабатываний цифровых клавиш при их удержании в нажатом состоянии
        Timer timer = new Timer(30, this);
        timer.setInitialDelay(500);

        // циклически задаём свойства цифровым клавишам (12 шт.) (getComponents() возвращает компоненты 1-го уровня вложенности)
        if (centerPanel instanceof Container) {
            // добавим кнопку backSpaceButton к обрабатываемому списку кнопок, так как она не входит в centerPanel
            List<Component> mainPanelComponentsList = Arrays.asList(centerPanel.getComponents());
            List<Component> processingComponents = new ArrayList<>(mainPanelComponentsList);
            processingComponents.add(backSpaceButton);

            for (Component child : processingComponents) {
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
            backSpaceButton.setFont(FontProvider.getInstance().getFont(FONTAWESOME_REGULAR, 54f));
        }
    }

    /**
     * Обеспечивает изменение текстового поля при нажатии на цифровые клавиши
     *
     * @param ch символ-идентификатор нажатой цифровой клавиши (в основном, это - символ на самой кнопке)
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
     * Метод определяет сколько кнопок показывать внизу цифровой клавиатуры
     * (например, одну кнопку "Поиск", или же 2 кнопки "ОК" и "Отмена" и т.д.).
     * Детальная настройка вида кнопок (цвет, текст)
     *
     * @param amount количество кнопок.
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
     * Клавиша ноль получает двойную ширину. Клавиша "точка" и вертикальная полоса-делитель удаляются.
     */
    public void doubleWidthA0Button() {
        GridBagLayout gbLayout = (GridBagLayout) centerPanel.getLayout();
        GridBagConstraints constraintsA0Button = gbLayout.getConstraints(a0Button);
        constraintsA0Button.gridwidth = 3;
        centerPanel.remove(dotButton);
        centerPanel.remove(verticalLine);
        centerPanel.add(a0Button, constraintsA0Button);
    }

    /**
     * Код заменяет обычное текстовое поле полем для ввода паролей.
     */
    public void switchToPasswordTextField() {
        GridBagLayout textFieldPanelLayout = (GridBagLayout) textFieldPanel.getLayout();
        GridBagConstraints constraintsTextField = textFieldPanelLayout.getConstraints(textField);
        textFieldPanel.remove(textField);
        textField = new JPasswordField();
        textField.setFont(FontProvider.getInstance().getFont(ROBOTO_REGULAR, 60f));
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
