/*
 * Copyright (c) RESONANCE JSC, 17.07.2019
 */

package gui.aspect_ratio_16x9;

import gui.FontProvider;
import gui.custom_components.BackgroundPanel;
import gui.custom_components.KeypadPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
    private JPanel addProductButton;
    private JLabel addProductIcon;
    private JLabel addProductLabel;
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
    private KeypadPanel keypadPanel1;
    private JPanel paymentInfoPanel;
    private JPanel navigatePanelCards;
    private JPanel addGoodPanel;
    private JPanel workWithReceiptPanel;
    private JPanel cashboxPanel;
    private JPanel servicePanel;
    private JPanel splashScreenPanel;

    private GraphicsDevice graphicsDevice;
    private CardLayout cardLayout;
    /**
     * Таймер для отсчёта времени показа splash screen
     */
    private Timer splashScreenTimer;

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

        setSplashScreen();
        initComponents();
        setContentPane(mainPanel);
        setVisible(true);

        // 1.32 - physical scale rate relate to my display
        // 1,9 - font scale for next parameters for debugging
        // next parameters make window for my monitor with physical dimensions like real 14' POS
//        setSize(1050, 618);

        cardLayout = (CardLayout) (mainPanel.getLayout());
        cardLayout.show(mainPanel, "splashScreenPanel");
    }

    /**
     * Устанавливает изображение из файла в виде фона на JPanel (рисует в кастомной панели BackgroundPanel).
     * Запускает таймер, отсчитывающий время показа стартового экрана (splash screen) с данным изображением.
     */
    private void setSplashScreen() {
        splashScreenTimer = new Timer(0, this);
        splashScreenTimer.setInitialDelay(1000);
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
    }

    private void initComponents() {
        FontProvider fontProvider = new FontProvider();
        discountButton.setFont(fontProvider.getFont(ROBOTO_REGULAR, 30f));
        paymentButton.setFont(fontProvider.getFont(ROBOTO_REGULAR, 30f));

        toPayLabel.setFont(fontProvider.getFont(ROBOTO_REGULAR, 38f));
        toPaySumLabel.setFont(fontProvider.getFont(ROBOTO_REGULAR, 38f));
        discountLabel.setFont(fontProvider.getFont(ROBOTO_REGULAR, 30f));
        discountSumLabel.setFont(fontProvider.getFont(ROBOTO_REGULAR, 30f));

        resposLabel.setFont(fontProvider.getFont(ROBOTO_BOLD, 22f));
        marketLabel.setFont(fontProvider.getFont(ROBOTO_BOLD, 12f));

        Color navPanelColor = new Color(52, 73, 94);
        Color navPanelPressed = new Color(65, 91, 122);
        Color navIconLabelPressed = new Color(230, 238, 243);

        keypadPanel1.setContext("searchButtonPanel");           // задаём внешний вид цифровой клавиатуры

        addProductButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                addProductButton.setBackground(navPanelPressed);
                addProductIcon.setForeground(navIconLabelPressed);
                addProductLabel.setForeground(navIconLabelPressed);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                addProductButton.setBackground(navPanelColor);
                addProductIcon.setForeground(Color.WHITE);
                addProductLabel.setForeground(Color.WHITE);
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {
                mouseReleased(e);
            }
        });
        addProductIcon.setFont(fontProvider.getFont(MENU_ICONS, 58f));
        addProductLabel.setFont(fontProvider.getFont(ROBOTO_REGULAR, 16f));

        workWithReceiptButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                workWithReceiptButton.setBackground(navPanelPressed);
                workWithReceiptIcon.setForeground(navIconLabelPressed);
                workWithReceiptLabel.setForeground(navIconLabelPressed);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                workWithReceiptButton.setBackground(navPanelColor);
                workWithReceiptIcon.setForeground(Color.WHITE);
                workWithReceiptLabel.setForeground(Color.WHITE);
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {
                mouseReleased(e);
            }
        });
        workWithReceiptIcon.setFont(fontProvider.getFont(MENU_ICONS, 58f));
        workWithReceiptLabel.setFont(fontProvider.getFont(ROBOTO_REGULAR, 16f));

        cashboxButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                cashboxButton.setBackground(navPanelPressed);
                cashboxIcon.setForeground(navIconLabelPressed);
                cashboxLabel.setForeground(navIconLabelPressed);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                cashboxButton.setBackground(navPanelColor);
                cashboxIcon.setForeground(Color.WHITE);
                cashboxLabel.setForeground(Color.WHITE);
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {
                mouseReleased(e);
            }
        });
        cashboxIcon.setFont(fontProvider.getFont(MENU_ICONS, 58f));
        cashboxLabel.setFont(fontProvider.getFont(ROBOTO_REGULAR, 16f));

        serviceButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                serviceButton.setBackground(navPanelPressed);
                serviceIcon.setForeground(navIconLabelPressed);
                serviceLabel.setForeground(navIconLabelPressed);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                serviceButton.setBackground(navPanelColor);
                serviceIcon.setForeground(Color.WHITE);
                serviceLabel.setForeground(Color.WHITE);
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {
                mouseReleased(e);
            }
        });
        serviceIcon.setFont(fontProvider.getFont(MENU_ICONS, 58f));
        serviceLabel.setFont(fontProvider.getFont(ROBOTO_REGULAR, 16f));

        exitButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                exitButton.setBackground(navPanelPressed);
                exitIcon.setForeground(navIconLabelPressed);
                exitLabel.setForeground(navIconLabelPressed);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                exitButton.setBackground(navPanelColor);
                exitIcon.setForeground(Color.WHITE);
                exitLabel.setForeground(Color.WHITE);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
                mouseReleased(e);
            }
        });
        exitIcon.setFont(fontProvider.getFont(MENU_ICONS, 58f));
        exitLabel.setFont(fontProvider.getFont(ROBOTO_REGULAR, 16f));


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
     * Вызывается по срабатыванию таймера, который отсчитывает время показа начальной заставки (splash screen)
     *
     * @param e событие, произошедшее по срабатыванию таймера
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        splashScreenTimer.stop();

        Dialog dialog = new JDialog(this);
        //Show it.
//        dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//        dialog.setUndecorated(true);
        dialog.setSize(new Dimension(400, 500));
        dialog.setLocationRelativeTo(this);
        JButton okButton = new JButton("OK");
        dialog.add(okButton);
        okButton.addActionListener(q -> dialog.dispose());
        dialog.setVisible(true);

        //        cardLayout.show(mainPanel, "mainSellPanel");

//        JWindow window = new JWindow(this);
//        //Show it.
////        window.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
////        dialog.setUndecorated(true);
//        window.setSize(new Dimension(400, 500));
//        window.setLocationRelativeTo(this);
//
//        JButton okButton = new JButton("OK");
//        okButton.addActionListener(q -> window.dispose());
//        window.add(okButton);
//        window.setVisible(true);

    }
}