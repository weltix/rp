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

public class KeypadPanel implements ActionListener {
    private JLabel searchLabel;
    private JButton searchButton;
    private JButton a0Button;
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
    private JTextField textField;
    private JButton backSpaceButton;
    private JPanel keyPadPanel;
    private JPanel parentPanel;
    private JPanel actionButtonPanel;
    private JButton actionButton1;
    private JButton actionButton2;
    private JPanel mainPanel;

    public KeypadPanel() {
        initComponents();
    }

    private void initComponents() {
        FontProvider fontProvider = new FontProvider();
        Font robotoRegular34 = fontProvider.getFont(ROBOTO_REGULAR, 34f);
        Font robotoRegular50 = fontProvider.getFont(ROBOTO_REGULAR, 50f);

        searchButton.setFont(robotoRegular34);
        actionButton1.setFont(robotoRegular34);
        actionButton2.setFont(robotoRegular34);

        searchLabel.setFont(fontProvider.getFont(ROBOTO_REGULAR, 22f));
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
     * Обеспечивает определение названия-идентификатора окна (диалога), содержащего данную клавиатуру.
     * Это необходимо для того, чтобы определить какие кнопки показывать внизу
     * (например, одну кнопку "Поиск", или же 2 кнопки "ОК" и "Отмена" и т.д.).
     *
     * @param context произвольное название-идентификатор окна (диалога), содержащего данную клавиатуру
     */
    public void setContext(String context) {
        CardLayout cardLayout = (CardLayout) (actionButtonPanel.getLayout());
        if (context.equals("searchButtonPanel"))
            cardLayout.show(actionButtonPanel, "searchButtonPanel");
        else
            cardLayout.show(actionButtonPanel, "twoButtonPanel");

        // TODO: 25.07.2019 доработать данный метод (когда будут известны все варианты самой нижней(-их) кнопок)
//        GridBagLayout centerPanelLayout = (GridBagLayout) mainPanel.getLayout();
//        GridBagConstraints constraintsForA0Button = centerPanelLayout.getConstraints(a0Button);
//        GridBagConstraints constraintsForDotButton = centerPanelLayout.getConstraints(dotButton);
//
//        constraintsForA0Button.gridwidth = 3;
//        mainPanel.remove(dotButton);
//        mainPanel.add(a0Button, constraintsForA0Button);
//
//        constraintsForA0Button.gridwidth = 1;
//        mainPanel.add(dotButton, constraintsForDotButton);
//        mainPanel.add(a0Button, constraintsForA0Button);
    }

    // TODO: 25.07.2019 Можно сделать выделение текста в текстовом поле по долгому нажатию левой кнопки мыши, а также по двойному щелчку
    // TODO: 26.07.2019 Подобрать более оптимальный фон кнопки при её нажатии
}
