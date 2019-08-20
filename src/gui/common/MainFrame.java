/*
 * Copyright (c) RESONANCE JSC, 20.08.2019
 */

package gui.common;

import gui.common.dialogs.DialogType;
import gui.common.dialogs.KeypadDialog;
import gui.common.dialogs.KeypadDialogLogin;
import gui.common.dialogs.PaymentDialog;
import gui.common.utility_components.BackgroundImagePanel;
import gui.common.utility_components.BlurLayerUI;
import gui.common.utility_components.GlassPane;
import gui.fonts.FontProvider;
import resources.Resources;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.LayerUI;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.InputStream;

import static gui.fonts.FontProvider.*;

/**
 * Класс, содержащий описание окна графического интерфейса.
 * JFrame - единственное окно, в котором будет отрисовываться всё приложение.
 * CardLayout будет обеспечивать показ разных экранов (разных cards-окон).
 * JWindow обеспечивают показ диалоговых окон (JWindow - основа-тело всех JFrame и JDialogs).
 * <p>
 * Можно использовать для показа диалогов JDialog (чтобы экран не мерцал при появлении диалога, setModal = true).
 * Но, в Windows придётся отключить FullScreenMode, иначе приложение сворачивается в трей при появлении диалога.
 * В Linux похожая ситуация - при появлении диалога показываются панель управления и пр., их надо скрывать заранее.
 * Также необходимо позаботиться об отсутствии курсора, он всё равно показывается за пределами диалога
 * (использовать glassPane со скрытым курсором во всём приложении).
 */
public class MainFrame extends JFrame implements ActionListener {
    public static final String APP_VERSION = "1.0";

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
    private JLabel versionLabel;

    private GraphicsDevice graphicsDevice;
    private CardLayout mainPanelCardLayout = (CardLayout) (mainPanel.getLayout());
    private CardLayout mainSellPanelScreensLayout = (CardLayout) (mainSellPanelScreens.getLayout());
    private Font robotoRegular16 = FontProvider.getInstance().getFont(ROBOTO_REGULAR, 16);
    private Font menuIcons58 = FontProvider.getInstance().getFont(MENU_ICONS, 58);
    private GlassPane glassPane = new GlassPane();
    private KeypadDialog loginWindow;
    private JWindow paymentWindow;

    // объект класса, который выполняет рисование для JLayer
    private LayerUI<JComponent> layerUI = new BlurLayerUI();
    // компонент-декоратор для других компонентов, при этом не изменяет их, а рисует поверх
    private JLayer<JComponent> jlayer = new JLayer<>();
    public boolean blurBackground = false;

    public MainFrame() {
        init();
    }

    private void init() {
//        // hide cursor from current JFrame
//        setCursor(getToolkit().createCustomCursor(
//                new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB),
//                new Point(),
//                null));

//        setDefaultCloseOperation(EXIT_ON_CLOSE);
//        setUndecorated(true);                       //need for full screen mode
//        setResizable(false);                        //need for full screen mode

        // set full screen exclusive mode
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        graphicsDevice = ge.getDefaultScreenDevice();
        if (graphicsDevice.isFullScreenSupported())
            graphicsDevice.setFullScreenWindow(this);

        setContentPane(mainPanel);
        setGlassPane(glassPane);

        initComponents();
        initNavigationPanel();
        setCardOfMainPanel("mainSellPanel");

        setVisible(true);

        Timer timer = new Timer(0, this);
        timer.setInitialDelay(2000);
        timer.setActionCommand("splashScreenShowingTime");
//        timer.start();

        // 1.32 - physical scale rate relate to my display
        // 1,9 - font scale for next parameters for debugging
        // next parameters make window for my monitor with physical dimensions like real 14' POS
        setSize(1050, 618);
//        setSize(Toolkit.getDefaultToolkit().getScreenSize());
    }

    public void setCardOfMainPanel(String cardName) {
        mainPanelCardLayout.show(mainPanel, cardName);
    }

    public void setCardOfMainSellPanelScreens(String cardName) {
        mainSellPanelScreensLayout.show(mainSellPanelScreens, cardName);
    }

    private void initComponents() {
        Font robotoRegular30 = FontProvider.getInstance().getFont(ROBOTO_REGULAR, 30);

        discountButton.setFont(robotoRegular30);
        paymentButton.setFont(robotoRegular30);
        paymentButton.addActionListener(e -> launchDialog(true, DialogType.PAYMENT));

        toPayLabel.setFont(FontProvider.getInstance().getFont(ROBOTO_REGULAR, 38));
        toPaySumLabel.setFont(FontProvider.getInstance().getFont(ROBOTO_REGULAR, 38));
        discountLabel.setFont(robotoRegular30);
        discountSumLabel.setFont(robotoRegular30);

        resposLabel.setFont(FontProvider.getInstance().getFont(ROBOTO_BOLD, 22));
        marketLabel.setFont(FontProvider.getInstance().getFont(ROBOTO_BOLD, 12));
        searchLabel.setFont(FontProvider.getInstance().getFont(ROBOTO_REGULAR, 26));

        // TODO: 29.07.2019 Решить, где лучше хранить APP_VERSION
        versionLabel.setFont(FontProvider.getInstance().getFont(ROBOTO_REGULAR, 24));
        versionLabel.setText(Resources.getInstance().getString("version:") + "1.0");

        keypadPanel.setActionButtonsAmount(1);     // задаём количество нижних клавиш нашей цифровой клавиатуры
        loginWindow = new KeypadDialogLogin(this);
        paymentWindow = new PaymentDialog(this);
        jlayer.setUI(layerUI);

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
     * Код настраивает look and feel кнопок навигационной панели
     */
    private void initNavigationPanel() {
        Color navPanelColor = new Color(52, 73, 94);
        Color navPanelPressed = new Color(65, 91, 122);
        Color navIconLabelPressed = new Color(230, 238, 243);

        // циклически задаём свойства кнопкам в навигационной панели (getComponents() возвращает компоненты 1-го уровня вложенности)
        if (navigatePanel instanceof Container)
            for (Component child : navigatePanel.getComponents()) {
                if (child.getName() != null &&
                        child instanceof JPanel &&
                        child.getName().contains("Button")) {    // отбираем только панели-кнопки
                    JLabel tempIconLabel = null;
                    JLabel tempTextLabel = null;
                    for (Component innerChild : ((JPanel) child).getComponents()) {
                        if (innerChild.getName().contains("Icon")) {
                            tempIconLabel = (JLabel) innerChild;
                            tempIconLabel.setFont(menuIcons58);
                        }
                        if (innerChild.getName().contains("Label")) {
                            tempTextLabel = (JLabel) innerChild;
                            // html не поддерживает сторонний шрифт, потому используем встроенный
                            tempTextLabel.setFont(FontProvider.getInstance().getFont(null, 16));
                        }
                    }
                    final JLabel iconLabel = tempIconLabel;
                    final JLabel textLabel = tempTextLabel;

                    // ставим слушалки на кнопки навигационной панели
                    child.addMouseListener(new MouseAdapter() {
                        // определяет, активна ли кнопка к выполнению действия (workaround метода mouseClicked).
                        // Причина: в некоторых системах клик не срабатывает при малейшей смене координат курсора во время клика.
                        private boolean isPressed = false;
                        private boolean isMouseOver = false;

                        @Override
                        public void mousePressed(MouseEvent e) {
                            setPressedState();
                            isPressed = true;
                        }

                        @Override
                        public void mouseReleased(MouseEvent e) {
                            setNormalState();
                            if (isPressed && isMouseOver) {
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
                                        launchDialog(true, DialogType.LOGIN);
                                        break;
                                    default:
                                        break;
                                }
                                if (!child.getName().equals("exitButton"))
                                    showNavigationPanelBackButton();
                            }
                            revalidate();
                            repaint();
                            isPressed = false;
                        }

                        @Override
                        public void mouseEntered(MouseEvent e) {
                            if (isPressed)
                                setPressedState();
                            isMouseOver = true;
                        }

                        @Override
                        public void mouseExited(MouseEvent e) {
                            setNormalState();
                            isMouseOver = false;
                        }

                        // задаёт вид кнопок навигационной панели в нормальном состоянии
                        private void setNormalState() {
                            child.setBackground(navPanelColor);
                            iconLabel.setForeground(Color.WHITE);
                            textLabel.setForeground(Color.WHITE);
                        }

                        // задаёт вид кнопок навигационной панели в нажатом состоянии
                        private void setPressedState() {
                            child.setBackground(navPanelPressed);
                            iconLabel.setForeground(navIconLabelPressed);
                            textLabel.setForeground(navIconLabelPressed);
                        }
                    });
                }
            }
    }

    /**
     * Создаёт, настраивает и показывает диалоговое окно определённого типа.
     *
     * @param glassPaneHasBackground определяет, затемнять ли фон вокруг диалогового окна
     * @param dialogType             определяет тип диалога из заранее заданного перечисления {@link DialogType}
     */
    private void launchDialog(boolean glassPaneHasBackground, DialogType dialogType) {
        Color base = UIManager.getColor("inactiveCaptionBorder");
        Color background = new Color(base.getRed(), base.getGreen(), base.getBlue(), 128);   //alpha originally was 128
        if (!glassPaneHasBackground)
            background = null;

        if (!splashScreenPanel.isVisible()) {
            glassPane.activate(background);
            // code will make blurred background around dialog window
            if (blurBackground) {
                jlayer.setView(mainPanel);
                setContentPane(jlayer);
                repaint();
                revalidate();
            }
        }
        // данная задержка - workaround для слабого железа (убирает задержку прорисовки при появлении glassPane)
        Timer timer = new Timer(0, this);
        timer.setInitialDelay(10);
        timer.setActionCommand(dialogType.name());
        timer.start();
    }

    /**
     * Код используется для обработки событий таймера.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // get actual keypad dimensions and location on screen in MainFrame. All another keypads must have the same dimensions.
        Dimension dim = keypadPanel.getSize();
        Point point = keypadPanel.getLocationOnScreen();

        if ("splashScreenShowingTime".equals(e.getActionCommand())) {
            ((Timer) e.getSource()).stop();
            launchDialog(false, DialogType.LOGIN);
        }

        if (DialogType.LOGIN.name().equals(e.getActionCommand())) {
            ((Timer) e.getSource()).stop();
            // keypad height to dialog height ratio. It is impossible to get this value from *.form file.
            double kpHRatio = 86.0 / 100;
            // Next code calculates dimensions and location on screen of dialog.
            // 2 and 3 - correction (dialog borders has absolute width 1px, also dividing lines has absolute width 1px).
            // Dimension.setSize() rounds it's arguments upwards, but when Swing calculates dimensions of components in
            // container using they weights, the sizes of components are rounding to down.
            dim.setSize(dim.getWidth() + 2, dim.getHeight() / kpHRatio + 3);
            point.translate(-1, -(int) ((dim.getHeight() - 3) * (1 - kpHRatio)) - 2);
            loginWindow.setSize(dim);
            loginWindow.setLocation(point);
            loginWindow.setVisible(true);
        }

        if (DialogType.PAYMENT.name().equals(e.getActionCommand())) {
            ((Timer) e.getSource()).stop();
            // 37.3% keypad width to dialog width ratio. It is impossible to get this value from *.form file.
            // 1.01 (или 1.005) - коррекция (необязательно, можно опустить).
            dim.setSize((dim.getWidth() / 37.5) * 100 * 1.005, (dim.getHeight() / 80) * 100 * 1.01);
            paymentWindow.setSize(dim);
            paymentWindow.setLocation(0, 0);
            paymentWindow.setVisible(true);
        }
    }

    /**
     * Код для установки contentPane для нашего главного JFrame из-за пределов данного класса.
     * В основном, используем, чтобы вернуть чёткий фон после закрытия диалогового окна
     * (если использовалось размытие {@link BlurLayerUI}).
     */
    @Override
    public void setContentPane(Container contentPane) {
        if (contentPane == null)
            contentPane = mainPanel;
        super.setContentPane(contentPane);
    }

    /**
     * Код возвращает объект панели-заставки (в основном для того, чтобы проверить показывается ли она в данный момент)
     */
    public JPanel getSplashScreenPanel() {
        return splashScreenPanel;
    }

    /**
     * Код настраивает и показывает кнопку "Назад" в навигационной панели, скрывая при этом остальные ненужные кнопки.
     */
    public void showNavigationPanelBackButton() {
        addGoodIcon.setFont(FontProvider.getInstance().getFont(FONTAWESOME_REGULAR, 58));
        addGoodIcon.setText(Resources.getInstance().getString("back_icon"));
        addGoodLabel.setText(Resources.getInstance().getString("back_html"));

        addGoodButton.removeMouseListener(addGoodButtonMouseListener);
        addGoodButton.addMouseListener(backButtonMouseListener);

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
    }

    public void hideNavigationPanelBackButton() {
        addGoodIcon.setFont(menuIcons58);
        addGoodIcon.setText(Resources.getInstance().getString("add_product_icon"));
        addGoodLabel.setText(Resources.getInstance().getString("add_product_html"));

        addGoodButton.removeMouseListener(backButtonMouseListener);
        addGoodButton.addMouseListener(addGoodButtonMouseListener);

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
    }

    MouseListener addGoodButtonMouseListener = new MouseAdapter() {
        // определяет, активна ли кнопка к выполнению действия (workaround метода mouseClicked).
        // Причина: в некоторых системах клик не срабатывает при малейшей смене координат курсора во время клика.
        private boolean isPressed = false;
        private boolean isMouseOver = false;

        @Override
        public void mousePressed(MouseEvent e) {
            isPressed = true;
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (isPressed && isMouseOver) {
                mainSellPanelScreensLayout.show(mainSellPanelScreens, "addGoodPanel");
                showNavigationPanelBackButton();
            }
            isPressed = false;
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            isMouseOver = true;
        }

        @Override
        public void mouseExited(MouseEvent e) {
            isMouseOver = false;
        }
    };

    MouseListener backButtonMouseListener = new MouseAdapter() {
        // определяет, активна ли кнопка к выполнению действия (workaround метода mouseClicked).
        // Причина: в некоторых системах клик не срабатывает при малейшей смене координат курсора во время клика.
        private boolean isPressed = false;
        private boolean isMouseOver = false;

        @Override
        public void mousePressed(MouseEvent e) {
            isPressed = true;
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (isPressed && isMouseOver) {
                mainSellPanelScreensLayout.show(mainSellPanelScreens, "sellPanel");
                hideNavigationPanelBackButton();
            }
            isPressed = false;
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            isMouseOver = true;
        }

        @Override
        public void mouseExited(MouseEvent e) {
            isMouseOver = false;
        }
    };

    /**
     * Код, в котором инициализируем вручную некоторые выбранные нами компоненты формы, связанной с данным классом.
     */
    private void createUIComponents() {
        splashScreenPanelInit();
    }

    /**
     * Устанавливает изображение из файла в виде фона на JPanel (рисует в кастомной панели BackgroundImagePanel).
     */
    private void splashScreenPanelInit() {
        Image splashScreenImage = null;
        try {
            String imageFile = "../aspect_ratio_16x9/images/splash_screen_1920x1080.png";
            InputStream inputStream = MainFrame.class.getResourceAsStream(imageFile);
            splashScreenImage = ImageIO.read(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        splashScreenPanel = new BackgroundImagePanel(splashScreenImage);
    }

    // TODO: 01.08.2019  Переделать слушалки для кнопки Добавить товар - Назад. Слушалка всегда должна быть в одном экземпляре.
    // TODO: 01.08.2019  Переделать для кнопок look and feel так, чтобы это было прописано в xml файле.
    // TODO: 01.08.2019  Навигационная панель должна показываться синхронно со всем экраном.
    // TODO: 07.08.2019  Как вариант, скрывать курсор во всём приложении с помощью glassPaneю
    // TODO: 13.08.2019  Перелать навигационную панель (алгоритм работы верный, есть ошибки в режиме кнопки назад (работают невидимые кнопки))
}