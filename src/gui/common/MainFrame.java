/*
 * Copyright (c) RESONANCE JSC, 04.09.2019
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static gui.fonts.FontProvider.*;

/**
 * Class describes main window of our graphic interface. Bounded to main_frame.form.
 * This {@link JFrame} is the single window, where all application will be rendering.
 * CardLayout will provide showing of different screens (i.e. different cards-panels).
 * {@link JWindow} will provide showing of dialog windows ({@link JWindow} is parent class for {@link JFrame} and {@link JDialog}).
 * <p>
 * Можно использовать для показа диалогов JDialog (чтобы экран не мерцал при появлении диалога, setModal = true).
 * Но, в Windows придётся отключить FullScreenMode, иначе приложение сворачивается в трей при появлении диалога.
 * В Linux похожая ситуация - при появлении диалога показываются панель управления и пр., их надо скрывать заранее.
 * Также необходимо позаботиться об отсутствии курсора, он всё равно показывается за пределами диалога
 * (glassPane with hidden cursor for whole application may be used).
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
    private JPanel navigatePanelContainer;
    private JPanel mainSellPanel;
    private JPanel sellPanel;
    private JPanel tablePanel;
    private JPanel infoKeyPadPanel;
    private JPanel searchKeyPadPanel;
    private KeypadPanel keypadPanel;
    private JPanel paymentInfoPanel;
    private JPanel sellPanelScreens;
    private JPanel addGoodPanel;
    private JPanel workWithReceiptPanel;
    private JPanel cashboxPanel;
    private JPanel servicePanel;
    private JPanel splashScreenPanel;
    private JLabel searchLabel;
    private JPanel horizontalLine2;
    private JPanel horizontalLine3;
    private JPanel horizontalLine4;
    private JPanel horizontalLine5;
    private JLabel versionLabel;
    private NavigatePanel navPanelMain;
    private NavigatePanel navPanelBack;
    private TiledPanel tiledPanel0;
    private TiledPanel tiledPanel1;
    private TiledPanel tiledPanel2;

    private GraphicsDevice graphicsDevice;
    private CardLayout mainPanelLayout = (CardLayout) mainPanel.getLayout();
    private CardLayout mainSellPanelScreensLayout = (CardLayout) sellPanelScreens.getLayout();
    private CardLayout navigatePanelContainerLayout = (CardLayout) navigatePanelContainer.getLayout();
    private Font robotoRegular16 = FontProvider.getInstance().getFont(ROBOTO_REGULAR, 16);
    private Font menuIcons58 = FontProvider.getInstance().getFont(MENU_ICONS, 58);
    private GlassPane glassPane = new GlassPane();
    private KeypadDialog loginWindow;
    private JWindow paymentWindow;

    // object of the class, that paints for JLayer
    private LayerUI<JComponent> layerUI = new BlurLayerUI();
    // component-decorator for another components. It doesn't change components, but paints over them.
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
//
//        // set full screen exclusive mode
//        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
//        graphicsDevice = ge.getDefaultScreenDevice();
//        if (graphicsDevice.isFullScreenSupported())
//            graphicsDevice.setFullScreenWindow(this);

        setContentPane(mainPanel);
        setGlassPane(glassPane);

        initComponents();

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
        mainPanelLayout.show(mainPanel, cardName);
    }

    public void setCardOfMainSellPanelScreens(String cardName) {
        mainSellPanelScreensLayout.show(sellPanelScreens, cardName);
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

        searchLabel.setFont(FontProvider.getInstance().getFont(ROBOTO_REGULAR, 26));

        // TODO: 29.07.2019 Решить, где лучше хранить APP_VERSION
        versionLabel.setFont(FontProvider.getInstance().getFont(ROBOTO_REGULAR, 24));
        versionLabel.setText(Resources.getInstance().getString("version:") + "1.0");

        keypadPanel.setActionButtonsAmount(1);     // set the amount of action buttons of our keypadPanel
        loginWindow = new KeypadDialogLogin(this);
        paymentWindow = new PaymentDialog(this);
        jlayer.setUI(layerUI);

        setCardOfMainPanel("mainSellPanel");
        navigatePanelContainerLayout.show(navigatePanelContainer, "navPanelMain");

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
     * Creates, tunes and shows a dialog window of certain type.
     *
     * @param glassPaneHasBackground defines, should the background around the dialog window be darker or not
     * @param dialogType             defines dialog type using {@link DialogType} enumeration
     */
    private void launchDialog(boolean glassPaneHasBackground, DialogType dialogType) {
        Color base = UIManager.getColor("inactiveCaptionBorder");
        Color background = new Color(base.getRed(), base.getGreen(), base.getBlue(), 128);   // 128 is original alpha value
        if (!glassPaneHasBackground)
            background = null;

        if (!splashScreenPanel.isVisible()) {
            glassPane.activate(background);
            // code will make background around dialog window blurred
            if (blurBackground) {
                jlayer.setView(mainPanel);
                setContentPane(jlayer);
                revalidate();
                repaint();
            }
        }
        // this delay - workaround for weak hardware (for fast appearance of glassPane without delays)
        Timer timer = new Timer(0, this);
        timer.setInitialDelay(10);
        timer.setActionCommand(dialogType.name());
        timer.start();
    }

    /**
     * Basically is using for timer events handling.
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
            // Next code calculates dimensions and location of dialog on screen.
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
            dim.setSize((dim.getWidth() / 37.5) * 100 * 1.005, (dim.getHeight() / 80) * 100 * 1.01);
            paymentWindow.setSize(dim);
            paymentWindow.setLocation(0, 0);
            paymentWindow.setVisible(true);
        }
    }

    /**
     * Set contentPane for our main JFrame. Basically, we use this method to return a sharp background after closing
     * the dialog box (necessarily if blurring {@link BlurLayerUI} was used).
     */
    @Override
    public void setContentPane(Container contentPane) {
        if (contentPane == null)
            contentPane = mainPanel;
        super.setContentPane(contentPane);
    }

    /**
     * Returns object of splashScreenPanel. Basically used to check is it currently showing.
     */
    public JPanel getSplashScreenPanel() {
        return splashScreenPanel;
    }

    /**
     * Set image from file as background for JPanel (paint in custom JPanel BackgroundImagePanel).
     */
    private void initSplashScreenPanel() {
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

    /**
     * Create {@link List} of icons and {@link List} of texts for buttons of each navigation panel.
     * The size of either of two lists equals the amount of navigation buttons on specified navigation panel.
     * The amount and names of navigation panels specified in main_frame.form. In this method we initialize them.
     */
    private void initNavigatePanel() {
        String[] names0 = {"add_good", "work_with_receipt", "cashbox", "service", "exit"};
        List<String> buttonIcons = new ArrayList<>();
        List<String> buttonTexts = new ArrayList<>();
        for (int i = 0; i < names0.length; i++) {
            buttonIcons.add(Resources.getInstance().getString(names0[i] + "_icon"));
            buttonTexts.add(Resources.getInstance().getString(names0[i] + "_html"));
        }
        Consumer<Integer> actions = buttonAlias -> {
            switch (buttonAlias) {
                case 0:
                    mainSellPanelScreensLayout.show(sellPanelScreens, "addGoodPanel");
                    break;
                case 1:
                    mainSellPanelScreensLayout.show(sellPanelScreens, "workWithReceiptPanel");
                    break;
                case 2:
                    mainSellPanelScreensLayout.show(sellPanelScreens, "cashboxPanel");
                    break;
                case 3:
                    mainSellPanelScreensLayout.show(sellPanelScreens, "servicePanel");
                    break;
                case 4:
                    launchDialog(true, DialogType.LOGIN);
                    return;
                default:
                    break;
            }
            navigatePanelContainerLayout.show(navigatePanelContainer, "navPanelBack");
        };
        navPanelMain = new NavigatePanel(buttonIcons, buttonTexts, null, actions);

        String[] names1 = {"back"};
        buttonIcons.clear();
        buttonTexts.clear();
        for (int i = 0; i < names1.length; i++) {
            buttonIcons.add(Resources.getInstance().getString(names1[i] + "_icon"));
            buttonTexts.add(Resources.getInstance().getString(names1[i] + "_html"));
        }
        Font iconsFont = FontProvider.getInstance().getFont(FONTAWESOME_REGULAR, 58);
        actions = buttonAlias -> {
            navigatePanelContainerLayout.show(navigatePanelContainer, "navPanelMain");
            mainSellPanelScreensLayout.show(sellPanelScreens, "sellPanel");
            revalidate();
            repaint();      // enforced to call to provide synchronous appearance of both panels on weak hardware
        };
        navPanelBack = new NavigatePanel(buttonIcons, buttonTexts, iconsFont, actions);
    }

    /**
     * Create {@link List} of texts for buttons of each tiled panel.
     * The size of list equals the amount of buttons on specified tiled panel.
     * The amount and names of tiled panels specified in main_frame.form. In this method we initialize them.
     */
    private void initTiledPanel() {


        String[] names0 = {"clear_receipt", "return_receipt", "put_off_receipt", "load_receipt", "print_copy",
                "last_receipt", "load_order", "profile_filling", "remove_discount", "particular_discount",
                "set_displayed_columns"};
        List<String> buttonTexts = new ArrayList<>();
        for (int i = 0; i < names0.length; i++) {
            buttonTexts.add(Resources.getInstance().getString(names0[i]));
        }
        Consumer<Integer> actions = buttonNumber -> {
            switch (buttonNumber) {
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    break;
                case 9:
                    break;
                case 10:
                    break;
                default:
                    break;
            }
        };
        tiledPanel0 = new TiledPanel(buttonTexts, actions);

        String[] names1 = {"deposit_withdraw", "open_cashdrawer", "receipt_copy", "payment_terminal", "plas_tech"};
        buttonTexts.clear();
        for (int i = 0; i < names1.length; i++) {
            buttonTexts.add(Resources.getInstance().getString(names1[i]));
        }
        actions = buttonNumber -> {
            switch (buttonNumber) {
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                default:
                    break;
            }
        };
        tiledPanel1 = new TiledPanel(buttonTexts, actions);

        String[] names2 = {"close_shift", "reports", "admin", "stock", "additionally"};
        buttonTexts.clear();
        for (int i = 0; i < names2.length; i++) {
            buttonTexts.add(Resources.getInstance().getString(names2[i]));
        }
        actions = buttonNumber -> {
            switch (buttonNumber) {
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                default:
                    break;
            }
        };
        tiledPanel2 = new TiledPanel(buttonTexts, actions);
    }

    /**
     * Custom initialization of specified components of form, that is bounded to this class.
     */
    private void createUIComponents() {
        initSplashScreenPanel();
        initNavigatePanel();
        initTiledPanel();
    }

    // TODO: 01.08.2019  Переделать для кнопок look and feel так, чтобы это было прописано в xml файле.
    // TODO: 07.08.2019  Как вариант, скрывать курсор во всём приложении с помощью glassPaneю
}