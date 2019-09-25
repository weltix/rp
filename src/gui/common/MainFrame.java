/*
 * Copyright (c) RESONANCE JSC, 25.09.2019
 */

package gui.common;

import gui.common.dialogs.*;
import gui.common.utility_components.BackgroundImagePanel;
import gui.common.utility_components.BlurLayerUI;
import gui.common.utility_components.GlassPane;
import gui.fonts.FontProvider;
import resources.Resources;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.LayerUI;
import javax.swing.plaf.synth.SynthLookAndFeel;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static gui.fonts.FontProvider.FONTAWESOME_REGULAR;
import static gui.fonts.FontProvider.ROBOTO_REGULAR;

/**
 * Class describes main window of our graphic interface. Bounded to main_frame.form.
 * This {@link JFrame} is the single window, where all application will be rendering.
 * CardLayout will provide showing of different screens (i.e. different cards-panels).
 * {@link JWindow} will provide showing of dialog windows ({@link JWindow} is parent class for {@link JFrame} and {@link JDialog}).
 * <p>
 * We use special Full Screen mode to paint directly to screen without using windows system of operation system.
 * <p>
 * JDialog may be used for dialog windows showing (set {@code setModal=true} to prevent screen blinking when dialog appears).
 * But it is necessary to switch off FullScreenMode, because in Windows systems app minimizes to tray when dialog appears.
 * In Linux the same situation - task palel is showing when dialog appears, so it must be hidden in advance.
 * Another one issue when JDialog is used is cursor visibility outside dialog window
 * (glassPane with hidden cursor for whole application is solution).
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

    private GraphicsDevice graphicsDevice;      // used to set full screen mode
    private CardLayout mainPanelLayout = (CardLayout) mainPanel.getLayout();
    private CardLayout sellPanelScreensLayout = (CardLayout) sellPanelScreens.getLayout();
    private CardLayout navigatePanelContainerLayout = (CardLayout) navigatePanelContainer.getLayout();
    private GlassPane glassPane = new GlassPane();
    private KeypadDialog loginDialog;
    private KeypadDialog manualDiscountDialog;
    private KeypadDialog depositWithdrawDialog;
    private AbstractDialog paymentDialog;
    private ConfirmDialog confirmDialog;
    private MessageDialog messageDialog;
    private Dimension kpSize;   // size of this MainFrame's keypadPanel in px
    private Point kpPoint;      // location of this MainFrame's keypadPanel on screen

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

        setVisible(true);

        Timer timer = new Timer(0, this);
        timer.setInitialDelay(2000);
        timer.setActionCommand("splashScreenShowingTime");
        timer.start();

        // 1.32 - physical scale rate relate to my display
        // 1,9 - font scale for next parameters for debugging
        // next parameters make window for my monitor with physical dimensions like real 14' POS
//        setSize(1050, 618);
//        setSize(Toolkit.getDefaultToolkit().getScreenSize());
    }

    public void setCardOfMainPanel(String cardName) {
        mainPanelLayout.show(mainPanel, cardName);
    }

    public void setCardOfSellPanelScreens(String cardName) {
        sellPanelScreensLayout.show(sellPanelScreens, cardName);
    }

    private void initComponents() {
        Font robotoRegular30 = FontProvider.getInstance().getFont(ROBOTO_REGULAR, 30);

        discountButton.setFont(robotoRegular30);
        paymentButton.setFont(robotoRegular30);
        paymentButton.addActionListener(e -> launchDialog(true, paymentDialog));

        toPayLabel.setFont(FontProvider.getInstance().getFont(ROBOTO_REGULAR, 38));
        toPaySumLabel.setFont(FontProvider.getInstance().getFont(ROBOTO_REGULAR, 38));
        discountLabel.setFont(robotoRegular30);
        discountSumLabel.setFont(robotoRegular30);

        searchLabel.setFont(FontProvider.getInstance().getFont(ROBOTO_REGULAR, 26));

        // TODO: 29.07.2019 Решить, где лучше хранить APP_VERSION
        versionLabel.setFont(FontProvider.getInstance().getFont(ROBOTO_REGULAR, 24));
        versionLabel.setText(Resources.getInstance().getString("version:") + "1.0");

        keypadPanel.setActionButtonsAmount(1);     // set the amount of action buttons of our keypadPanel
        jlayer.setUI(layerUI);

        setCardOfMainPanel("splashScreenPanel");
        navigatePanelContainerLayout.show(navigatePanelContainer, "navPanelMain");

        // instantiation of dialogs
        loginDialog = new KeypadDialogLogin(this);
        manualDiscountDialog = new KeypadDialogManualDiscount(this);
        depositWithdrawDialog = new KeypadDialogDepositWithdraw(this);
        paymentDialog = new PaymentDialog(this);
        confirmDialog = new ConfirmDialog(this);
        messageDialog = new MessageDialog(this);

        // When sellPanel become visible we can get MainFrame's keypadPanel location, that some dialogs may use.
        sellPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                super.componentShown(e);
                // Next condition checks if sellPanel become visible after iconify-deiconify of app.
                // We need invisible sellPanel when splashScreenPanel is visible to ensure that current method will
                // be surely called when sellPanel really appears on screen.
                if (!splashScreenPanel.isVisible()) {
                    initDialogWindows();
                } else {
                    sellPanel.setVisible(false);
                }
            }
        });

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
     * Provides glassPane showing, then runs timer, that after triggering will show specified dialog window.
     * All dialogs are launched using timer with very small delay, that make dialog appearance faster on weak hardware.
     *
     * @param glassPaneHasBackground defines, should the background around the dialog window be darker or not
     * @param dialogWindow           object of dialog window
     */
    private void launchDialog(boolean glassPaneHasBackground, JWindow dialogWindow) {
        Color base = UIManager.getColor("inactiveCaptionBorder");
        Color background = new Color(base.getRed(), base.getGreen(), base.getBlue(), 128);   // 128 is original alpha value
        if (!glassPaneHasBackground)
            background = null;

        // optional using of blurring feature
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

        // determines DialogType of current dialog window object
        DialogType dialogType = null;
        switch (dialogWindow.getClass().getSimpleName()) {
            case "KeypadDialogLogin":
                dialogType = DialogType.LOGIN;
                break;
            case "KeypadDialogManualDiscount":
                dialogType = DialogType.MANUAL_DISCOUNT;
                break;
            case "KeypadDialogDepositWithdraw":
                dialogType = DialogType.DEPOSIT_WITHDRAW;
                break;
            case "PaymentDialog":
                dialogType = DialogType.PAYMENT;
                break;
            case "ConfirmDialog":
                dialogType = DialogType.CONFIRM;
                break;
            case "MessageDialog":
                dialogType = DialogType.MESSAGE;
                break;
            default:
                break;
        }
        // this delay - workaround for weak hardware (for fast appearance of glassPane without delays)
        Timer timer = new Timer(0, this);
        timer.setInitialDelay(10);
        timer.setActionCommand(dialogType.name());
        timer.start();
    }

    /**
     * Method provides getting of size and location of {@link MainFrame}'s keypadPanel.
     * Getting of location is possible only when appropriate container becomes visible ({@link MainFrame#sellPanel}).
     * In another case we set location manually (first time only, when {@link MainFrame#splashScreenPanel} appears).
     */
    public void initDialogWindows() {
        // get actual keypad size on screen in MainFrame. All another dialog's keypads must have the same dimensions.
        kpSize = keypadPanel.getSize();
        // get actual keypad location on screen in MainFrame. We will use it as relational location.
        try {
            kpPoint = keypadPanel.getLocationOnScreen();
        } catch (IllegalComponentStateException e) {
            // Exception occurs only if keypadPanel is in invisible state.
            // Use approximately assuming location of keypad on screen in MainFrame. Need for SplashScreen keypadPanel basically.
            kpPoint = new Point(getWidth() - (int) kpSize.getWidth() - 20, getHeight() - (int) kpSize.getHeight() - 20);
        }

        // utility variables
        Dimension size = new Dimension();
        Point location = new Point();
        double kpWRatio;
        double kpHRatio;

        /** {@link KeypadDialogLogin} customization */
        // keypad height to dialog height ratio. It is impossible to get this value from *.form file programmatically.
        kpHRatio = 86.0 / 100;
        // Next code calculates dimensions and location of dialog on screen.
        // 2 and 3 - correction (dialog borders has absolute width 1px, also dividing lines has absolute width 1px).
        // Dimension.setSize() rounds it's arguments upwards, but when Swing calculates dimensions of components in
        // container using they weights, the sizes of components are rounding to down.
        size.setSize(kpSize.getWidth() + 2, kpSize.getHeight() / kpHRatio + 3);
        location.setLocation(kpPoint.getX() - 1, kpPoint.getY() - ((size.getHeight() - 3) * (1 - kpHRatio)) - 2);
        loginDialog.setSize(size);
        loginDialog.setLocation(location);

        /** {@link KeypadDialogManualDiscount} customization */
        // keypad height to dialog height ratio. It is impossible to get this value from *.form file programmatically.
        kpHRatio = 86.0 / 114;     /** 114% = 100% + 14% of {@link KeypadDialog#extraPanel1}, which is visible in this dialog */
        // Next code calculates dimensions and location of dialog on screen.
        // 2 and 3 - correction (dialog borders has absolute width 1px, also dividing lines has absolute width 1px).
        // Dimension.setSize() rounds it's arguments upwards, but when Swing calculates dimensions of components in
        // container using they weights, the sizes of components are rounding to down.
        size.setSize(kpSize.getWidth() + 2, kpSize.getHeight() / kpHRatio + 3);
        location.setLocation(kpPoint.getX() - 1, kpPoint.getY() - ((size.getHeight() - 3) * (1 - kpHRatio)) - 2);
        manualDiscountDialog.setSize(size);
        manualDiscountDialog.setLocation(location);

        /** {@link KeypadDialogDepositWithdraw} customization */
        // keypad height to dialog height ratio. It is impossible to get this value from *.form file programmatically.
        kpHRatio = 86.0 / 119;     /** 119% = 100% + 19% of {@link KeypadDialog#extraPanel1}, which is visible in this dialog */
        // Next code calculates dimensions and location of dialog on screen.
        // 2 and 3 - correction (dialog borders has absolute width 1px, also dividing lines has absolute width 1px).
        // Dimension.setSize() rounds it's arguments upwards, but when Swing calculates dimensions of components in
        // container using they weights, the sizes of components are rounding to down.
        size.setSize(kpSize.getWidth() + 2, kpSize.getHeight() / kpHRatio + 3);
        location.setLocation(kpPoint.getX() - 1, kpPoint.getY() - ((size.getHeight() - 3) * (1 - kpHRatio)) - 2);
        depositWithdrawDialog.setSize(size);
        depositWithdrawDialog.setLocation(location);

        /** {@link PaymentDialog} customization */
        // 37.3% keypad width to dialog width ratio. It is impossible to get this value from *.form file programmatically.
        size.setSize((kpSize.getWidth() / 37.5) * 100 * 1.005, (kpSize.getHeight() / 80) * 100 * 1.01);
        paymentDialog.setSize(size);
        paymentDialog.setLocation(0, 0);

        /** {@link ConfirmDialog} customization */
        // 36,5% is dialog width to screen width ratio. 28% is dialog height to screen height ratio.
        // It is impossible to get this value from *.form file programmatically.
        kpWRatio = 36.5 / 100;
        kpHRatio = 28.0 / 100;
        size.setSize(this.getWidth() * kpWRatio, this.getHeight() * kpHRatio);
        confirmDialog.setSize(size);
        confirmDialog.setLocationRelativeTo(this);

        /** {@link MessageDialog} customization */
        messageDialog.setSize(size);
        messageDialog.setLocationRelativeTo(this);
    }

    /**
     * Basically is using for timer events handling.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof Timer) {
            ((Timer) e.getSource()).stop();
        }

        // if timer for splashScreen appearing triggered
        if ("splashScreenShowingTime".equals(e.getActionCommand())) {
            initDialogWindows();        // this call need to get size and location of MainFrame's keyboard to tune up loginDialog
            launchDialog(false, loginDialog);
            sellPanel.setVisible(false);    // need here to trigger listener of sellPanel when it will be shown (visible) first time
            return;
        }

        // show appropriate dialog after timer triggering (timer, that makes very small delay, see method launchDialog)
        switch (e.getActionCommand()) {
            case "LOGIN":
                loginDialog.setVisible(true);
                break;
            case "MANUAL_DISCOUNT":
                manualDiscountDialog.setVisible(true);
                break;
            case "DEPOSIT_WITHDRAW":
                depositWithdrawDialog.setVisible(true);
                break;
            case "PAYMENT":
                paymentDialog.setVisible(true);
                break;
            case "CONFIRM":
                confirmDialog.setVisible(true);
                break;
            case "MESSAGE":
                messageDialog.setVisible(true);
                break;
            default:
                break;
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
        String[] names0 = {"add_product", "work_with_receipt", "cashbox", "service", "exit"};
        List<String> buttonIcons = new ArrayList<>();
        List<String> buttonTexts = new ArrayList<>();
        for (int i = 0; i < names0.length; i++) {
            buttonIcons.add(Resources.getInstance().getString(names0[i] + "_icon"));
            buttonTexts.add(Resources.getInstance().getString(names0[i] + "_html"));
        }
        Consumer<Integer> actions = buttonNumber -> {
            switch (buttonNumber) {
                case 0:
                    sellPanelScreensLayout.show(sellPanelScreens, "addGoodPanel");
                    break;
                case 1:
                    sellPanelScreensLayout.show(sellPanelScreens, "workWithReceiptPanel");
                    break;
                case 2:
                    sellPanelScreensLayout.show(sellPanelScreens, "cashboxPanel");
                    break;
                case 3:
                    sellPanelScreensLayout.show(sellPanelScreens, "servicePanel");
                    break;
                case 4:
                    launchDialog(true, loginDialog);
                    return;
                default:
                    break;
            }
            navigatePanelContainerLayout.show(navigatePanelContainer, "navPanelBack");

            // Get height of header panel with title in top left corner of screen, then use this height in tilesPanels.
            // Here is only place where height of header panel may be determined
            // (even after this JFrame setVisible(true) it return 0 instead real value)
            int height = navPanelBack.getHeaderPanelHeight();
            tiledPanel0.setTopPanelHeight(height);
            tiledPanel1.setTopPanelHeight(height);
            tiledPanel2.setTopPanelHeight(height);
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
        actions = buttonNumber -> {
            navigatePanelContainerLayout.show(navigatePanelContainer, "navPanelMain");
            sellPanelScreensLayout.show(sellPanelScreens, "sellPanel");
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
        String[] names0 = {"receipt_cleaning", "return_receipt", "put_off_receipt", "load_receipt", "print_copy",
                "last_receipt", "load_order", "profile_filling", "remove_discounts", "manual_discount",
                "set_displayed_columns"};
        List<String> buttonTexts = new ArrayList<>();
        for (int i = 0; i < names0.length; i++) {
            buttonTexts.add(Resources.getInstance().getString(names0[i]));
        }
        Consumer<Integer> actions = buttonNumber -> {
            switch (buttonNumber) {
                case 0:
                    Consumer<Integer> action = e -> this.dispose();
                    confirmDialog.setProperties(Resources.getInstance().getString("receipt_cleaning"),
                            Resources.getInstance().getString("question_clear_receipt"),
                            action);
                    launchDialog(true, confirmDialog);
                    break;
                case 1:
                    action = e -> this.dispose();
                    confirmDialog.setProperties(Resources.getInstance().getString("return_receipt"),
                            Resources.getInstance().getString("question_return_receipt"),
                            action);
                    launchDialog(true, confirmDialog);
                    break;
                case 2:
                    messageDialog.setProperties(Resources.getInstance().getString("put_off_receipt"),
                            Resources.getInstance().getString("msg_receipt_should_not_be_empty"));
                    launchDialog(true, messageDialog);
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
                    messageDialog.setProperties(Resources.getInstance().getString("profile_filling"),
                            Resources.getInstance().getString("msg_not_found_profiles"));
                    launchDialog(true, messageDialog);
                    break;
                case 8:
                    action = e -> this.dispose();
                    confirmDialog.setProperties(Resources.getInstance().getString("remove_discounts"),
                            Resources.getInstance().getString("msg_remove_discounts"),
                            action);
                    launchDialog(true, confirmDialog);
                    break;
                case 9:
                    action = e -> this.dispose();
                    launchDialog(true, manualDiscountDialog);
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
                    Consumer<Integer> action = e -> this.dispose();
                    launchDialog(true, depositWithdrawDialog);
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
     * This method is mandatory if "Custom Create" option is marked for at least one component of bounded to this
     * class *.form file. Method is intended to initialize this specified component(s).
     */
    private void createUIComponents() {
        initSplashScreenPanel();
        initNavigatePanel();
        initTiledPanel();
        initLookAndFeel();
    }

    /**
     * Loads the .xml file with appropriate look and feel (button, panel, label colors and fonts).
     */
    private void initLookAndFeel() {
        SynthLookAndFeel lookAndFeel = new SynthLookAndFeel();
        try {
            lookAndFeel.load(MainFrame.class.getResourceAsStream("style.xml"),
                    MainFrame.class);
            UIManager.setLookAndFeel(lookAndFeel);
        } catch (Exception e) {
            System.err.println("Couldn't get specified look and feel ("
                    + lookAndFeel
                    + "), for some reason.");
            System.err.println("Using the default look and feel.");
            e.printStackTrace();
        }
    }

    // TODO: 01.08.2019  Переделать для кнопок look and feel так, чтобы это было прописано в xml файле.
    // TODO: 07.08.2019  Как вариант, скрывать курсор во всём приложении с помощью glassPaneю
    // TODO: 11.09.2019 Установить действия на все кнопки TiledPanel 
}