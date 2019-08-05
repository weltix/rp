/*
 * Copyright (c) RESONANCE JSC, 17.07.2019
 */

package gui.aspect_ratio_16x9;

import gui.FontProvider;
import gui.custom_components.BackgroundPanel;
import gui.custom_components.GlassPane;
import gui.custom_components.KeypadPanel;
import resources.Resources;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static gui.FontProvider.*;

/**
 * Класс, содержащий описание окна графического интерфейса.
 * JFrame - единственное окно, в котором будет отрисовываться всё приложение.
 * CardLayout будет обеспечивать показ разных экранов (разных cards-окон).
 * Window обеспечивают показ диалоговых окон (Window - основа-тело всех JFrame и JDialogs).
 */
public class MainFrame extends JFrame implements ActionListener {
    private JLabel toPayLabel;
    private JLabel toPaySumLabel;
    private JLabel discountLabel;
    private JLabel discountSumLabel;
    private JButton discountButton;
    private JButton paymentButton;

    private JLabel resposLabel;
    private JLabel marketLabel;
    private JPanel addGoodButton;
    private JLabel addGoodIcon;
    private JLabel addGoodLabel;
    private JPanel workWithReceiptButton;
    private JLabel workWithReceiptIcon;
    private JLabel workWithReceiptLabel;
    private JPanel cashboxButton;
    private JLabel cashboxIcon;
    private JLabel cashboxLabel;
    private JPanel serviceButton;
    private JLabel serviceIcon;
    private JLabel serviceLabel;
    private JPanel exitButton;
    private JLabel exitIcon;
    private JLabel exitLabel;
    private JButton button2;
    private JButton button3;
    private JTable sellTable;
    private JPanel mainPanel;
    private JPanel navigatePanel;
    private JPanel mainSellPanel;
    private JPanel sellPanel;
    private JPanel tablePanel;
    private JPanel infoKeyPadPanel;
    private JPanel searchKeyPadPanel;
    private KeypadPanel keypadPanel;
    private JPanel paymentInfoPanel;
    private JPanel mainSellPanelScreens;
    private JPanel addGoodPanel;
    private JPanel workWithReceiptPanel;
    private JPanel cashboxPanel;
    private JPanel servicePanel;
    private JPanel splashScreenPanel;
    private JLabel searchLabel;
    private JPanel horizontalLine1;
    private JPanel horizontalLine2;
    private JPanel horizontalLine3;
    private JPanel horizontalLine4;
    private JPanel horizontalLine5;

    private Timer splashScreenTimer;
    private CardLayout mainPanelCardLayout = (CardLayout) (mainPanel.getLayout());
    private CardLayout mainSellPanelScreensLayout = (CardLayout) (mainSellPanelScreens.getLayout());
    private Font robotoRegular16 = FontProvider.getInstance().getFont(ROBOTO_REGULAR, 16f);
    private Font menuIcons58 = FontProvider.getInstance().getFont(MENU_ICONS, 58f);
    // Таймер для отсчёта времени показа splash screen
    private GraphicsDevice graphicsDevice;
    // GlassPane для эмуляции неактивного (disabled) фонового окна (аналог modality в JDialog)
    private GlassPane glassPane = new GlassPane();
    private JWindow loginWindow;

    public MainFrame() {
        init();
    }

    private void init() {
        // hide cursor from current JFrame
        setCursor(getToolkit().createCustomCursor(
                new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB),
                new Point(),
                null));

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setUndecorated(true);                       //need for full screen mode
        setResizable(false);                        //need for full screen mode

        // set full screen exclusive mode
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        graphicsDevice = ge.getDefaultScreenDevice();
        if (graphicsDevice.isFullScreenSupported())
            graphicsDevice.setFullScreenWindow(this);


        setContentPane(mainPanel);
        setGlassPane(glassPane);

        initComponents();
        initSplashScreen();
        initNavigationPanel();
        setCardOfMainPanel("splashScreenPanel");

        setVisible(true);

        // 1.32 - physical scale rate relate to my display
        // 1,9 - font scale for next parameters for debugging
        // next parameters make window for my monitor with physical dimensions like real 14' POS
//        setSize(1050, 618);
    }

    public void setCardOfMainPanel(String cardName) {
        mainPanelCardLayout.show(mainPanel, cardName);
    }

    public void setCardOfMainSellPanelScreens(String cardName) {
        mainSellPanelScreensLayout.show(mainSellPanelScreens, cardName);
    }

    private void initComponents() {
        Font robotoRegular30 = FontProvider.getInstance().getFont(ROBOTO_REGULAR, 30f);

        discountButton.setFont(robotoRegular30);
        paymentButton.setFont(robotoRegular30);

        toPayLabel.setFont(FontProvider.getInstance().getFont(ROBOTO_REGULAR, 38f));
        toPaySumLabel.setFont(FontProvider.getInstance().getFont(ROBOTO_REGULAR, 38f));
        discountLabel.setFont(robotoRegular30);
        discountSumLabel.setFont(robotoRegular30);

        resposLabel.setFont(FontProvider.getInstance().getFont(ROBOTO_BOLD, 22f));
        marketLabel.setFont(FontProvider.getInstance().getFont(ROBOTO_BOLD, 12f));
        searchLabel.setFont(FontProvider.getInstance().getFont(ROBOTO_REGULAR, 26f));

        keypadPanel.setActionButtonsAmount(1);     // задаём количество нижних клавиш нашей цифровой клавиатуры
        loginWindow = new LoginWindow(this);

        String[] columnNames = {"First Name",
                "Last Name",
                "Sport",
                "# of Years",
                "Vegetarian"};

        Object[][] data = {
                {"Kathy", "Smith3w4t3q4vqw4tgv4wgt4gt34gt4evtegvw4t",
                        "Snowboarding", new Integer(5), new Boolean(false)},
                {"John", "Doe",
                        "Rowing", new Integer(3), new Boolean(true)},
                {"Sue", "Black",
                        "Knitting", new Integer(2), new Boolean(false)},
                {"Jane", "White",
                        "Speed reading", new Integer(20), new Boolean(true)},
                {"Joe", "Brown",
                        "Pool", new Integer(10), new Boolean(false)},
                {"Kathy", "Smith3w4t3q4vqw4tgv4wgt4gt34gt4evtegvw4t",
                        "Snowboarding", new Integer(5), new Boolean(false)},
                {"John", "Doe",
                        "Rowing", new Integer(3), new Boolean(true)},
                {"Sue", "Black",
                        "Knitting", new Integer(2), new Boolean(false)},
                {"Jane", "White",
                        "Speed reading", new Integer(20), new Boolean(true)},
                {"Joe", "Brown",
                        "Pool", new Integer(10), new Boolean(false)},
                {"Kathy", "Smith3w4t3q4vqw4tgv4wgt4gt34gt4evtegvw4t",
                        "Snowboarding", new Integer(5), new Boolean(false)},
                {"John", "Doe",
                        "Rowing", new Integer(3), new Boolean(true)},
                {"Sue", "Black",
                        "Knitting", new Integer(2), new Boolean(false)},
                {"Jane", "White",
                        "Speed reading", new Integer(20), new Boolean(true)},
                {"Joe", "Brown",
                        "Pool", new Integer(10), new Boolean(false)}
        };


        String data1 = "Kathy32465312542346243523524 45745y45herghesrgerg";
        String data2 = "John";
        String data3 = "Sue";
        String data4 = "Jane";
        String data5 = "Jane";

        Object[] row = {data1, data2, data3, data4, data5};

//        sellTable.setPreferredScrollableViewportSize(new Dimension(500, 70));
//        sellTable.setFillsViewportHeight(true);

        DefaultTableModel sellTableModel = (DefaultTableModel) sellTable.getModel();
        sellTableModel.addColumn("1");
        sellTableModel.addColumn("2");
        sellTableModel.addColumn("3");
        sellTableModel.addColumn("4");
        sellTableModel.addColumn("5");
        sellTableModel.addRow(row);
        sellTableModel.setDataVector(data, row);

        sellTableModel.setRowCount(25);
    }

    /**
     * Устанавливает изображение из файла в виде фона на JPanel (рисует в кастомной панели BackgroundPanel).
     * Запускает таймер, отсчитывающий время показа стартового экрана (splash screen) с данным изображением.
     */
    private void initSplashScreen() {
        splashScreenTimer = new Timer(0, this);
        splashScreenTimer.setInitialDelay(2000);
        splashScreenTimer.start();
        Image splashScreenImage = null;
        try {
            String imageFile = "images/splash_screen_1920x1080.png";
            InputStream inputStream = MainFrame.class.getResourceAsStream(imageFile);
            splashScreenImage = ImageIO.read(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;                         // заполняем всего родителя по обеим осям
        constraints.weightx = 1;
        constraints.weighty = 1;
        JPanel backgroundPanel = new BackgroundPanel(splashScreenImage);    // получаем панель с картинкой в фоне
        splashScreenPanel.add(backgroundPanel, constraints);                // устанавливаем полученную панель в родителя

        Color backgroundColor = new Color(0, 0, 0, 0);
        glassPane.activate("Подождите, выполняется загрузка программы", backgroundColor);

//        JComponent glassPane = this.getLayeredPane();
//        this.setGlassPane(glassPane);
//        Label label = new Label("2134123412341234");
//        glassPane.add(label);
//
//        label.setForeground(Color.WHITE);
////        layeredPane.add(label, 300);

//        JLayeredPane layeredPane = this.getLayeredPane();
//        Label label = new Label("2134123412341234");
//        label.setForeground(Color.WHITE);
//        splashScreenPanel.add(label);
//        splashScreenPanel.repaint();
        // TODO: 29.07.2019 Сделать Label на главной заставке
    }

    /**
     * Код настраивает look and feel кнопок навигационной панели
     */
    private void initNavigationPanel() {
        Color navPanelColor = new Color(52, 73, 94);
        Color navPanelPressed = new Color(65, 91, 122);
        Color navIconLabelPressed = new Color(230, 238, 243);

        // циклически задаём свойства кнопкам в навигационной панели (getComponents() возвращает компоненты 1-го уровня вложенности)
        for (Component child : navigatePanel.getComponents()) {
            if ((child.getName() != null) && (child.getName().contains("Button"))) {    // отбираем только панели-кнопки
                JLabel tempIconLabel = null;
                JLabel tempTextLabel = null;
                for (Component innerChild : ((JPanel) child).getComponents()) {
                    if (innerChild.getName().contains("Icon")) {
                        tempIconLabel = (JLabel) innerChild;
                        tempIconLabel.setFont(menuIcons58);
                    }
                    if (innerChild.getName().contains("Label")) {
                        tempTextLabel = (JLabel) innerChild;
                        tempTextLabel.setFont(robotoRegular16);
                    }
                }
                final JLabel iconLabel = tempIconLabel;
                final JLabel textLabel = tempTextLabel;

                // ставим слушалки на кнопки навигационной панели
                child.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        switch (child.getName()) {
                            case "addGoodButton":       // имя кнопок навигационной панели (задано в .form в поле name)
                                mainSellPanelScreensLayout.show(mainSellPanelScreens, "addGoodPanel");
                                break;
                            case "workWithReceiptButton":
                                mainSellPanelScreensLayout.show(mainSellPanelScreens, "workWithReceiptPanel");
                                break;
                            case "cashboxButton":
                                mainSellPanelScreensLayout.show(mainSellPanelScreens, "cashboxPanel");
                                break;
                            case "serviceButton":
                                mainSellPanelScreensLayout.show(mainSellPanelScreens, "servicePanel");
                                break;
                            case "exitButton":
                                launchLoginWindow();
                                break;
                            default:
                                break;
                        }
                        if (!child.getName().equals("exitButton"))
                            showNavigationPanelBackButton();
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                        child.setBackground(navPanelPressed);
                        iconLabel.setForeground(navIconLabelPressed);
                        textLabel.setForeground(navIconLabelPressed);
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        child.setBackground(navPanelColor);
                        iconLabel.setForeground(Color.WHITE);
                        textLabel.setForeground(Color.WHITE);
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        mouseReleased(e);
                    }
                });
            }
        }
    }

    /**
     * Вызывается по срабатыванию таймера, который отсчитывает время показа начальной заставки (splash screen)
     *
     * @param e событие, произошедшее по срабатыванию таймера
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        splashScreenTimer.stop();
        if (splashScreenPanel.isVisible() == true)
            glassPane.deactivate();
        launchLoginWindow();
    }

    /**
     * Создаёт, настраивает и показывает окно входа в систему по паролю
     */
    private void launchLoginWindow() {
        loginWindow.setSize(keypadPanel.getSize());
        loginWindow.setLocationRelativeTo(this);
        loginWindow.setVisible(true);
    }

    /**
     * Код настраивает и показывает кнопку "Назад" в навигационной панели, скрывая при этом остальные ненужные кнопки.
     */
    public void showNavigationPanelBackButton() {
        addGoodIcon.setFont(FontProvider.getInstance().getFont(FONTAWESOME_REGULAR, 58f));
        addGoodIcon.setText(Resources.getInstance().getString("back_icon"));
        addGoodLabel.setText(Resources.getInstance().getString("back_html"));

        addGoodButton.removeMouseListener(addGoodButtonMouseListener);
        addGoodButton.addMouseListener(backButtonMouseListener);

        workWithReceiptButton.setEnabled(false);
        cashboxButton.setEnabled(false);
        serviceButton.setEnabled(false);
        exitButton.setEnabled(false);

        workWithReceiptButton.setOpaque(false);
        cashboxButton.setOpaque(false);
        serviceButton.setOpaque(false);
        exitButton.setOpaque(false);

        workWithReceiptIcon.setVisible(false);
        cashboxIcon.setVisible(false);
        serviceIcon.setVisible(false);
        exitIcon.setVisible(false);

        workWithReceiptLabel.setVisible(false);
        cashboxLabel.setVisible(false);
        serviceLabel.setVisible(false);
        exitLabel.setVisible(false);

        horizontalLine2.setOpaque(false);
        horizontalLine3.setOpaque(false);
        horizontalLine4.setOpaque(false);
        horizontalLine5.setOpaque(false);

        navigatePanel.repaint();
        navigatePanel.revalidate();
//        GridBagLayout textFieldPanelLayout = (GridBagLayout) textFieldPanel.getLayout();
//        GridBagConstraints constraintsTextField = textFieldPanelLayout.getConstraints(textField);
//        textFieldPanel.remove(textField);
//        textField = new JPasswordField();
//        textField.setFont(fontProvider.getFont(ROBOTO_REGULAR, 60f));
//        textField.setBorder(BorderFactory.createEmptyBorder());
//        textFieldPanel.add(textField, constraintsTextField);
    }

    public void hideNavigationPanelBackButton() {
        addGoodIcon.setFont(menuIcons58);
        addGoodIcon.setText(Resources.getInstance().getString("add_product_icon"));
        addGoodLabel.setText(Resources.getInstance().getString("add_product_html"));

        addGoodButton.removeMouseListener(backButtonMouseListener);
        addGoodButton.addMouseListener(addGoodButtonMouseListener);

        workWithReceiptButton.setEnabled(true);
        cashboxButton.setEnabled(true);
        serviceButton.setEnabled(true);
        exitButton.setEnabled(true);

        workWithReceiptButton.setOpaque(true);
        cashboxButton.setOpaque(true);
        serviceButton.setOpaque(true);
        exitButton.setOpaque(true);

        workWithReceiptIcon.setVisible(true);
        cashboxIcon.setVisible(true);
        serviceIcon.setVisible(true);
        exitIcon.setVisible(true);

        workWithReceiptLabel.setVisible(true);
        cashboxLabel.setVisible(true);
        serviceLabel.setVisible(true);
        exitLabel.setVisible(true);

        horizontalLine2.setOpaque(true);
        horizontalLine3.setOpaque(true);
        horizontalLine4.setOpaque(true);
        horizontalLine5.setOpaque(true);

        navigatePanel.repaint();
        navigatePanel.revalidate();

//        GridBagLayout textFieldPanelLayout = (GridBagLayout) textFieldPanel.getLayout();
//        GridBagConstraints constraintsTextField = textFieldPanelLayout.getConstraints(textField);
//        textFieldPanel.remove(textField);
//        textField = new JPasswordField();
//        textField.setFont(fontProvider.getFont(ROBOTO_REGULAR, 60f));
//        textField.setBorder(BorderFactory.createEmptyBorder());
//        textFieldPanel.add(textField, constraintsTextField);
    }

    MouseListener addGoodButtonMouseListener = new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {
            mainSellPanelScreensLayout.show(mainSellPanelScreens, "addGoodPanel");
            showNavigationPanelBackButton();
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    };

    MouseListener backButtonMouseListener = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            mainSellPanelScreensLayout.show(mainSellPanelScreens, "sellPanel");
            hideNavigationPanelBackButton();
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    };

    // TODO: 01.08.2019  Переделать слушалки для кнопки Добавить товар - Назад. Слушалка всегда должна быть в одном экземпляре.
    // TODO: 01.08.2019  Переделать для кнопок look and feel так, чтобы это было прописано в xml файле.
    // TODO: 01.08.2019  Навигационная панель должна показываться синхронно со всем экраном.
}