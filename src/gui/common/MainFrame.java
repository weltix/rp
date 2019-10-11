/*
 * Copyright (c) RESONANCE JSC, 11.10.2019
 */

package gui.common;

import gui.common.components.*;
import gui.common.dialogs.*;
import gui.common.ui.BlurLayerUI;
import gui.common.ui.DefaultScrollBarUI;
import gui.fonts.FontProvider;
import resources.Resources;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.LayerUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

import static gui.fonts.FontProvider.FONTAWESOME_REGULAR;
import static gui.fonts.FontProvider.ROBOTO_REGULAR;
import static javax.swing.ListSelectionModel.SINGLE_SELECTION;

/**
 * Class describes main window of our graphic interface. Bound to main_frame.form.
 * This {@link JFrame} is the single window, where all application will be rendering.
 * CardLayout will provide showing of different screens (i.e. different cards-panels).
 * <p>
 * We use special Full Screen mode to paint directly to screen without using windows system of operation system.
 * <p>
 * Using Synth look & feel *.xml is unsuitable because of lagging of interface (rendering is 2 or 3 times longer than usually).
 * The reasong is loading images (more images = lower performance) and weak hardware.
 * <p>
 * {@link JWindow} is more appropriate way for showing of dialog windows
 * ({@link JWindow} is parent class for {@link JFrame} and {@link JDialog}).
 * Using of JPanels located on glass pane instead of {@link JWindow} consumes the same amount of RAM and CPU.
 * Only difference - {@link JWindow} appears with slight single blink (the reason is glass pane appearing first).
 * So, we will use JPanels on glass pane for greater performance.
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
    private JScrollPane scrollPaneOfSellTable;

    private GraphicsDevice graphicsDevice;      // used to set full screen mode
    private CardLayout mainPanelLayout = (CardLayout) mainPanel.getLayout();
    private CardLayout sellPanelScreensLayout = (CardLayout) sellPanelScreens.getLayout();
    private CardLayout navigatePanelContainerLayout = (CardLayout) navigatePanelContainer.getLayout();
    private GlassPane glassPane = new GlassPane();
    private KeypadDialog loginDialog;
    private KeypadDialog changeAmountDialog;
    private KeypadDialog manualDiscountDialog;
    private KeypadDialog depositWithdrawDialog;
    private KeypadDialog paymentDialog;
    private ConfirmDialog confirmDialog;
    private MessageDialog messageDialog;
    private Dimension kpSize;   // size of this MainFrame's keypadPanel in px
    private Point kpPoint;      // location of this MainFrame's keypadPanel on screen

    // object of the class, that paints for JLayer
    private LayerUI<JComponent> layerUI = new BlurLayerUI();
    // component-decorator for another components. It doesn't change components, but paints over them.
    private JLayer<JComponent> jlayer = new JLayer<>();
    // screen resolution
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private boolean areDialogsInitialized = false;

    private static final int SELL_TABLE_ROW_COUNT = 14;
    public boolean blurBackground = false;                      // if true, than background under glass panel will be blurred
    public static final boolean IS_CURSOR_INVISIBLE = false;    // value for whole application

    public MainFrame() {
        init();
    }

    private void init() {
        if (IS_CURSOR_INVISIBLE) {
            setCursor(getToolkit().createCustomCursor(
                    new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB),
                    new Point(),
                    null));
        }

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

        // Timer makes delay before showing of login dialog.
        // This place also is suitable for initialization of some components of mainSellPanel (it is hidden behind
        // splash screen yet, but we can get sizes of it's components already, location of them will became available only when
        // mainSellPanel will become visible).
        Timer timer = new Timer(1000, e -> {
            ((Timer) e.getSource()).stop();     // stop after first triggering

            initDialogWindows();                // this call need to get size and location of MainFrame's keyboard to tune up loginDialog
            launchDialog(false, loginDialog);

            int navPanelHeaderHeight = initSellTable(SELL_TABLE_ROW_COUNT);
            // next we can set missing sizes of some components (it is good that this components still behind splash screen)
            navPanelMain.setHeaderPanelHeight(navPanelHeaderHeight - 1);        // 1 - correction (Swing inaccuracy)
            navPanelBack.setHeaderPanelHeight(navPanelHeaderHeight - 1);        // 1 - correction (Swing inaccuracy)
            tiledPanel0.setTopPanelHeight(navPanelHeaderHeight);
            tiledPanel1.setTopPanelHeight(navPanelHeaderHeight);
            tiledPanel2.setTopPanelHeight(navPanelHeaderHeight);

            sellPanel.setVisible(false);    // need here to trigger listener of sellPanel when it will be shown (visible) first time
        });
        timer.setInitialDelay(10);
        timer.start();

        // 1.32 - physical scale rate relate to my display
        // 1,9 - font scale for next parameters for debugging
//        setSize(1033, 580);        // makes window for my monitor with physical dimensions like real 14' POS
//        setSize(1280, 720);
//        setSize(1920, 1080);
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
        discountButton.addActionListener(e -> {
            changeAmountDialog.setTextFieldText(String.format(Locale.ROOT, "%.3f", 5.4));
            launchDialog(true, changeAmountDialog);
        });
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
        changeAmountDialog = new KeypadDialogChangeProductAmount(this);
        manualDiscountDialog = new KeypadDialogManualDiscount(this);
        depositWithdrawDialog = new KeypadDialogDepositWithdraw(this);
        paymentDialog = new KeypadDialogPayment(this);
        confirmDialog = new ConfirmDialog(this);
        messageDialog = new MessageDialog(this);

        sellPanel.setVisible(false);    // we need invisible sellPanel to trigger next listener when sellPanel will become visible
        sellPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                /**
                 * Method is called when sellPanel becomes visible, and here we can complete initializations of some components
                 */
                super.componentShown(e);
                if (!splashScreenPanel.isVisible()) {
                    initDialogWindows();


                }
            }
        });
    }

    /**
     * Method provides getting of size and location of {@link MainFrame}'s keypadPanel.
     * Getting of location is possible only when appropriate container becomes visible ({@link MainFrame#sellPanel}).
     * In another case we set location manually (first time only, when {@link MainFrame#splashScreenPanel} appears).
     */
    private void initDialogWindows() {
        if (areDialogsInitialized)
            return;

        int loginDlgXMargin = (int) (0.012 * getWidth());      // (default) need for login dialog location on splash screen
        int loginDlgYMargin = (int) (0.02 * getHeight());      // (default) need for login dialog location on splash screen
        if (screenSize.getWidth() == 1920 && screenSize.getHeight() == 1080) {
            loginDlgXMargin = (int) (0.0115 * getWidth());
            loginDlgYMargin = (int) (0.019 * getHeight());
        } else if ((screenSize.getWidth() == 1366 && screenSize.getHeight() == 768)
                || (screenSize.getWidth() == 1280 && screenSize.getHeight() == 720)) {
            loginDlgXMargin = (int) (0.013 * getWidth());
            loginDlgYMargin = (int) (0.02 * getHeight());
        }
        // get actual keypad size on screen in MainFrame. All another dialog's keypads must have the same dimensions.
        kpSize = keypadPanel.getSize();
        // get actual keypad location on screen in MainFrame. We will use it as relational location.
        try {
            kpPoint = keypadPanel.getLocationOnScreen();
            areDialogsInitialized = true;
        } catch (IllegalComponentStateException e) {
            // Exception occurs only if keypadPanel is in invisible state.
            // Use approximately assuming location of keypad on screen in MainFrame. Need for SplashScreen keypadPanel basically.
            kpPoint = new Point(getWidth() - (int) kpSize.getWidth() - loginDlgXMargin,
                    getHeight() - (int) kpSize.getHeight() - loginDlgYMargin);
        }

        // utility variables
        Dimension size = new Dimension();
        Point location = new Point();
        double kpWRatio;
        double kpHRatio;

        /** {@link KeypadDialogLogin} customization */
        // keypad height to dialog height ratio. It is impossible to get this value from *.form file programmatically.
        kpHRatio = 86.0 / 100;
        // 1, 2 and 3 - correction (dialog borders has absolute width 1px, also dividing lines has absolute width 1px).
        // Dimension.setSize() rounds it's arguments upwards, but when Swing calculates dimensions of components in
        // container using they weights, the sizes of components are rounding to down.
        size.setSize(kpSize.getWidth() + 2, kpSize.getHeight() / kpHRatio + 3);
        location.setLocation(kpPoint.getX() - 1, kpPoint.getY() - ((size.getHeight() - 3) * (1 - kpHRatio)) - 2);
        loginDialog.setSize(size);
        loginDialog.setLocation(location);

        // everything is the same like for previous dialog
        changeAmountDialog.setSize(size);
        changeAmountDialog.setLocation(location);

        /** {@link KeypadDialogManualDiscount} customization */
        // keypad height to dialog height ratio. 113.4% = 100% + 13.4% of KeypadDialog.extraPanel, which is visible in this dialog.
        kpHRatio = 86.0 / 113.4;    // 13.4% is set in KeypadDialogManualDiscount class.
        // 1, 2 and 3 - correction (dialog borders has absolute width 1px, also dividing lines has absolute width 1px).
        // Dimension.setSize() rounds it's arguments upwards, but when Swing calculates dimensions of components in
        // container using they weights, the sizes of components are rounding to down.
        size.setSize(kpSize.getWidth() + 2, kpSize.getHeight() / kpHRatio + 3);
        location.setLocation(kpPoint.getX() - 1, kpPoint.getY() - ((size.getHeight() - 3) * (1 - kpHRatio)) - 2);
        manualDiscountDialog.setSize(size);
        manualDiscountDialog.setLocation(location);

        /** {@link KeypadDialogDepositWithdraw} customization */
        // keypad height to dialog height ratio. 119% = 100% + 19% of KeypadDialog.extraPanel1, which is visible in this dialog
        kpHRatio = 86.0 / 119;     // 19% is set in KeypadDialogDepositWithdraw class.
        // 1, 2 and 3 - correction (dialog borders has absolute width 1px, also dividing lines has absolute width 1px).
        // Dimension.setSize() rounds it's arguments upwards, but when Swing calculates dimensions of components in
        // container using they weights, the sizes of components are rounding to down.
        size.setSize(kpSize.getWidth() + 2, kpSize.getHeight() / kpHRatio + 3);
        location.setLocation(kpPoint.getX() - 1, kpPoint.getY() - ((size.getHeight() - 3) * (1 - kpHRatio)) - 2);
        depositWithdrawDialog.setSize(size);
        depositWithdrawDialog.setLocation(location);

        /** {@link KeypadDialogPayment} customization */
        // 37.3% keypad width to dialog width ratio. It is impossible to get this value from *.form file programmatically.
        size.setSize((kpSize.getWidth() / 37.5) * 100 * 1.005, (kpSize.getHeight() / 80) * 100 * 1.01);
        location.setLocation(0, 1);
        paymentDialog.setSize(size);
        paymentDialog.setLocation(location);

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
     * Provides glassPane showing, allowing optional blurring of it, then shows specified dialog window.
     * Simple this.setEnabled(false) is not suitable because of blinking of screen when all elements are becoming disabled.
     *
     * @param glassPaneHasBackground defines, should the background around the dialog window be darker or not
     * @param dialogWindow           object of dialog window
     */
    private void launchDialog(boolean glassPaneHasBackground, JWindow dialogWindow) {
        Color base = new Color(184, 207, 229);
        Color background = new Color(base.getRed(), base.getGreen(), base.getBlue(), 128);   // 128 is original alpha value
        if (!glassPaneHasBackground)
            background = null;   // glassPane will be transparent, but in visible state (to cover rest part of screen from clicks)

        if (!splashScreenPanel.isVisible()) {
            glassPane.activate(background);
            // code will make background around dialog window blurred (optional using of blurring feature)
            if (blurBackground) {
                jlayer.setView(mainPanel);
                setContentPane(jlayer);
                revalidate();
                repaint();
            }
        }

        switch (dialogWindow.getClass().getSimpleName()) {
            case "KeypadDialogLogin":
//                SwingUtilities.invokeLater(() -> loginDialog.setVisible(true));
                loginDialog.setVisible(true);
                break;
            case "KeypadDialogChangeProductAmount":
//                SwingUtilities.invokeLater(() -> changeAmountDialog.setVisible(true));
                changeAmountDialog.setVisible(true);
                break;
            case "KeypadDialogManualDiscount":
//                SwingUtilities.invokeLater(() -> manualDiscountDialog.setVisible(true));
                manualDiscountDialog.setVisible(true);
                break;
            case "KeypadDialogDepositWithdraw":
//                SwingUtilities.invokeLater(() -> depositWithdrawDialog.setVisible(true));
                depositWithdrawDialog.setVisible(true);
                break;
            case "KeypadDialogPayment":
//                SwingUtilities.invokeLater(() -> paymentDialog.setVisible(true));
                paymentDialog.setVisible(true);
                break;
            case "ConfirmDialog":
//                SwingUtilities.invokeLater(() -> confirmDialog.setVisible(true));
                confirmDialog.setVisible(true);
                break;
            case "MessageDialog":
//                SwingUtilities.invokeLater(() -> messageDialog.setVisible(true));
                messageDialog.setVisible(true);
                break;
            default:
                break;
        }
    }

    /**
     * Code makes all settings for sell table (fonts, colors, dimensions, behavior).
     * ScrollPane for this table also is setting up here.
     * Also we use some received here dimensions for tune up of NavigatePanel header and TiledPanels.
     * <p>
     * Table will have {@code rowCount} rows, and height of single one is calculated below.
     *
     * @param rowCount amount of rows of our table.
     * @return value of {@link NavigatePanel#headerPanel} height, that is possible to get on this step
     * when table header's height calculated.
     */
    private int initSellTable(int rowCount) {
        // First we will find height of table's header and row.
        // Example of calculations: 14.5x = 14x + 0.5x, where x is one row height, 14 - rows count, 0.5 - prefHeaderToRowRatio.
        // If received table header has too small height, than round height of one row not to ceil, but already to floor.
        double prefHeaderToRowRatio = 0.5;  // Header height to row height ratio is nearly this value,
        double minHeaderToRowRatio = 0.4;   // but no less then this value (custom defined values)
        double scrollBarButtonToRowHeightRatio = 0.8;   // custom defined value
// TODO: 07.10.2019 0.8 сделать глобальным параметром?
        int tablePanelHeight = scrollPaneOfSellTable.getHeight();
        double rawTableHeaderHeight = tablePanelHeight / (rowCount + prefHeaderToRowRatio);
        int tableRowHeight = (int) Math.ceil(rawTableHeaderHeight);
        int tableHeaderHeight = tablePanelHeight - (tableRowHeight * rowCount);
        if ((double) tableHeaderHeight / (double) tableRowHeight < minHeaderToRowRatio) {
            tableRowHeight = (int) Math.floor(rawTableHeaderHeight);
            tableHeaderHeight = tablePanelHeight - (tableRowHeight * rowCount);
        }

        sellTable.setRowHeight(tableRowHeight);

        /**
         * Local class for customizing of table's header (sets font, colors, borders, size and alignment).
         */
        class DefaultHeaderRenderer extends JLabel implements TableCellRenderer {

            private DefaultHeaderRenderer(int height) {
                setFont(FontProvider.getInstance().getFont(ROBOTO_REGULAR, 26));
                setOpaque(true);
                setForeground(Color.WHITE);
                setBackground(new Color(52, 73, 94));
                setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.WHITE));
                setPreferredSize(new Dimension(0, height));
                setHorizontalAlignment(JLabel.CENTER);
            }

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                setText(value.toString());
                return this;
            }
        }
        sellTable.getTableHeader().setDefaultRenderer(new DefaultHeaderRenderer(tableHeaderHeight));

        /**
         * Local class for customizing of table's cell (sets border absence of selected cell).
         */
        class DefaultCellRenderer extends DefaultTableCellRenderer {

            Border padding = BorderFactory.createEmptyBorder(0, 5, 0, 5);

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setBorder(noFocusBorder);
                setBorder(BorderFactory.createCompoundBorder(getBorder(), padding));
                return this;
            }
        }
        sellTable.setDefaultRenderer(Object.class, new DefaultCellRenderer());

        sellTable.setSelectionMode(SINGLE_SELECTION);
        sellTable.setFont(FontProvider.getInstance().getFont(ROBOTO_REGULAR, 36));
        sellTable.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, new Color(200, 200, 200)));
        sellTable.getTableHeader().setReorderingAllowed(false);     // don't allow reorder columns by hands

        int scrollBarButtonSize = (int) (tableRowHeight * scrollBarButtonToRowHeightRatio);
        UIManager.put("ScrollBar.width", scrollBarButtonSize);
        scrollPaneOfSellTable.getVerticalScrollBar().setUI(new DefaultScrollBarUI(scrollBarButtonSize));
        scrollPaneOfSellTable.setViewportView(sellTable);       // method is actual for TouchScroll.class
        scrollPaneOfSellTable.getVerticalScrollBar().setUnitIncrement(tableRowHeight);
        scrollPaneOfSellTable.getVerticalScrollBar().setBlockIncrement(tableRowHeight * SELL_TABLE_ROW_COUNT);

        String[] columnNames = {"", "№",
                Resources.getInstance().getString("name"),
                Resources.getInstance().getString("am_nt"),
                Resources.getInstance().getString("sum")};

        Object[][] data = {
                {" ", "Лосьон для тела Dove Чай матча и экстракт сакуры, 250 мл   4539448\n",
                        String.format(Locale.ROOT, "%.3f", 999.9),
                        String.format(Locale.ROOT, "%.2f", 999.9)},
                {" ", "Лосьон для тела Dove Экстракт лотоса и рисовое молочко, 250 мл   4539449\n",
                        String.format(Locale.ROOT, "%.3f", 10.9),
                        String.format(Locale.ROOT, "%.2f", 10000.9)},
                {" ", "Лосьон для тела Love Beauty and Planet «Бархатное масло ши», 400 мл   4539461\n",
                        new Integer(2), new Double(248)},
                {" ", "Лосьон для тела Love Beauty and Planet «Восхитительно сияние», 400 мл   4539462\n",
                        new Integer(20), new Double(1030)},
                {" ", "Лосьон для тела Love Beauty and Planet «Соблазнительное увлажнение», 400 мл   4539463\n",
                        new Integer(10), new Double(400.80)},
                {" ", "Молочко для тела Villaphyta, с алоэ, 200 мл   4545913\n",
                        new Integer(5), new Double(500)},
                {" ", "Регенерирующее масло для тела Cosnature «Гранат», 100 мл   4545931\n",
                        new Integer(3), new Double(700)},
                {" ", "Бальзам для тела Alkmene Sensitiv «Био Мальва», 250 мл   4545947\n",
                        new Integer(2), new Double(300.40)},
                {" ", "Молочко для тела Alkmene «Био Нероли», живительное, 250 мл   4545951\n",
                        new Integer(20), new Double(170.50)},
                {" ", "Интенсивный бальзам для тела Alkmene «Био Олива», 250 мл   4545953\n",
                        new Integer(10), new Double(200)},
                {" ", "Бальзам для тела Alkmene «Био Каштан», расслабляющий, 150 мл   4545955\n",
                        new Integer(5), new Double(340)},
                {" ", "Молочко для тела Alkmene «Био Алоэ», ухаживающее, 250 мл   4545957\n",
                        new Integer(3), new Double(124)},
                {" ", "Масло для тела Ayluna «Лунный восход», 100 мл   4545993\n",
                        new Integer(2), new Double(800)},
                {" ", "Масло для тела Ayluna «Чудесное дерево», 100 мл   4545994\n",
                        new Integer(20), new Double(650)},
                {" ", "Сливки для тела Ayluna «Лунный восход», 250 мл   4545999\n",
                        new Integer(10), new Double(320)},
                {" ", "Сливки для тела Ayluna «Утренняя заря», 250 мл   4546000\n",
                        new Integer(5), new Double(174)},
                {" ", "Лосьон для тела Eunyul, с маслом черного тмина, 500 мл   4639063\n",
                        new Integer(3), new Double(100)},
                {" ", "Гель для тела Lebelage, увлажняющий успокаивающий, с экстрактом алоэ, 300 мл   4639098\n",
                        new Integer(2), new Double(540)},
                {" ", "Гель для тела Lebelage, увлажняющий успокаивающий, с экстрактом огурца, 300 мл   4639099\n",
                        new Integer(20), new Double(140)},
                {" ", "Гель для тела Lebelage, увлажняющий успокаивающий, с муцином улитки, 300 мл   4639100\n",
                        new Integer(10), new Double(220)},
                {" ", "Масло для массажа, 150 мл 40336 609906\n",
                        new Integer(5), new Double(120.50)},
                {" ", "Крем для тела Organic Shop \"Ванильный взбитый крем\" увлажняющий 450 мл 1155253\n",
                        new Integer(3), new Double(140.50)},
                {" ", "Крем для тела Organic Shop \"Банановый молочный коктейль\" восстанавливающий 450 мл 1155254\n",
                        new Integer(2), new Double(900)},
                {" ", "Крем для тела Organic Shop \"Карамельный каппуччино\" подтягивающий 450 мл 1155255\n",
                        new Integer(20), new Double(300.80)},
                {" ", "Масло для тела Банька Агафьи  густое мускатное, 300 мл 1155332\n",
                        new Integer(10), new Double(500.60)},
                {" ", "Маска для тела Банька Агафьи \"Черная торфяная\", 100 мл 1224853\n",
                        new Integer(5), new Double(400.80)},
                {" ", "Обертывание-крио водорослевое, Compliment Body, 475 мл   2925152\n",
                        new Integer(3), new Double(120)},
                {" ", "Успокаивающий арома-спрей для здорового сна Французская лаванда и Магический ирис, 75 мл   3604211\n",
                        new Integer(2), new Double(100)},
                {" ", "Мусс для тела Zeitun «Ночной обновляющий» сандал и амбра, 200 мл   4321997\n",
                        new Integer(20), new Double(70)},
                {" ", "Мусс для тела Nivea «Огуречный лимонад», 200 мл   4349312\n",
                        new Integer(10), new Double(50)},
                {" ", "Лосьон для тела Dove Чай матча и экстракт сакуры, 250 мл   4539448\n",
                        new Integer(5), new Double(250.50)},
                {" ", "Лосьон для тела Dove Экстракт лотоса и рисовое молочко, 250 мл   4539449\n",
                        new Integer(3), new Double(124)},
                {" ", "Лосьон для тела Love Beauty and Planet «Бархатное масло ши», 400 мл   4539461\n",
                        new Integer(2), new Double(248)},
                {" ", "Лосьон для тела Love Beauty and Planet «Восхитительно сияние», 400 мл   4539462\n",
                        new Integer(20), new Double(1030)},
                {" ", "Лосьон для тела Love Beauty and Planet «Соблазнительное увлажнение», 400 мл   4539463\n",
                        new Integer(10), new Double(400.80)},
                {" ", "Молочко для тела Villaphyta, с алоэ, 200 мл   4545913\n",
                        new Integer(5), new Double(500)},
                {" ", "Регенерирующее масло для тела Cosnature «Гранат», 100 мл   4545931\n",
                        new Integer(3), new Double(700)},
                {" ", "Бальзам для тела Alkmene Sensitiv «Био Мальва», 250 мл   4545947\n",
                        new Integer(2), new Double(300.40)},
                {" ", "Молочко для тела Alkmene «Био Нероли», живительное, 250 мл   4545951\n",
                        new Integer(20), new Double(170.50)},
                {" ", "Интенсивный бальзам для тела Alkmene «Био Олива», 250 мл   4545953\n",
                        new Integer(10), new Double(200)},
                {" ", "Бальзам для тела Alkmene «Био Каштан», расслабляющий, 150 мл   4545955\n",
                        new Integer(5), new Double(340)},
                {" ", "Молочко для тела Alkmene «Био Алоэ», ухаживающее, 250 мл   4545957\n",
                        new Integer(3), new Double(124)},
                {" ", "Масло для тела Ayluna «Лунный восход», 100 мл   4545993\n",
                        new Integer(2), new Double(800)},
                {" ", "Масло для тела Ayluna «Чудесное дерево», 100 мл   4545994\n",
                        new Integer(20), new Double(650)},
                {" ", "Сливки для тела Ayluna «Лунный восход», 250 мл   4545999\n",
                        new Integer(10), new Double(320)},
                {" ", "Сливки для тела Ayluna «Утренняя заря», 250 мл   4546000\n",
                        new Integer(5), new Double(174)},
                {" ", "Лосьон для тела Eunyul, с маслом черного тмина, 500 мл   4639063\n",
                        new Integer(3), new Double(100)},
                {" ", "Гель для тела Lebelage, увлажняющий успокаивающий, с экстрактом алоэ, 300 мл   4639098\n",
                        new Integer(2), new Double(540)},
                {" ", "Гель для тела Lebelage, увлажняющий успокаивающий, с экстрактом огурца, 300 мл   4639099\n",
                        new Integer(20), new Double(140)},
                {" ", "Гель для тела Lebelage, увлажняющий успокаивающий, с муцином улитки, 300 мл   4639100\n",
                        new Integer(10), new Double(220)},
                {" ", "Масло для массажа, 150 мл 40336 609906\n",
                        new Integer(5), new Double(120.50)},
                {" ", "Крем для тела Organic Shop \"Ванильный взбитый крем\" увлажняющий 450 мл 1155253\n",
                        new Integer(3), new Double(140.50)},
                {" ", "Крем для тела Organic Shop \"Банановый молочный коктейль\" восстанавливающий 450 мл 1155254\n",
                        new Integer(2), new Double(900)},
                {" ", "Крем для тела Organic Shop \"Карамельный каппуччино\" подтягивающий 450 мл 1155255\n",
                        new Integer(20), new Double(300.80)},
                {" ", "Масло для тела Банька Агафьи  густое мускатное, 300 мл 1155332\n",
                        new Integer(10), new Double(500.60)},
                {" ", "Маска для тела Банька Агафьи \"Черная торфяная\", 100 мл 1224853\n",
                        new Integer(5), new Double(400.80)},
                {" ", "Обертывание-крио водорослевое, Compliment Body, 475 мл   2925152\n",
                        new Integer(3), new Double(120)},
                {" ", "Успокаивающий арома-спрей для здорового сна Французская лаванда и Магический ирис, 75 мл   3604211\n",
                        new Integer(2), new Double(100)},
                {" ", "Мусс для тела Zeitun «Ночной обновляющий» сандал и амбра, 200 мл   4321997\n",
                        new Integer(20), new Double(70)},
                {" ", "Мусс для тела Nivea «Огуречный лимонад», 200 мл   4349312\n",
                        new Integer(10), new Double(706)}
        };
        // add numeration to array of objects above
        List<Object[]> dataList = new ArrayList<>(Arrays.asList(data));
        for (int i = 0; i < dataList.size(); i++) {
            List<Object> dataListItem = new ArrayList<>(Arrays.asList(dataList.get(i)));
            dataListItem.add(1, Integer.valueOf(i + 1));
            data[i] = dataListItem.toArray();
        }

        DefaultTableModel sellTableModel = (DefaultTableModel) sellTable.getModel();
//        sellTableModel.setColumnIdentifiers(columnNames);   // names of columns

//        sellTableModel.addColumn("1");
//        sellTableModel.addColumn("2");
//        sellTableModel.addColumn("3");
//        sellTableModel.addColumn("4");
//        sellTableModel.addColumn("5");
//        sellTableModel.addRow(columnNames);
        sellTableModel.setDataVector(data, columnNames);

        if (sellTable.getRowCount() < SELL_TABLE_ROW_COUNT)
            sellTableModel.setRowCount(SELL_TABLE_ROW_COUNT);

        DefaultTableCellRenderer centerRenderer = new DefaultCellRenderer();
        centerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        List<String> columnAliases = new ArrayList<>(Arrays.asList("", "№", "am_nt", "un_of_measure", "balance"));
        for (String columnAlias : columnAliases) {
            try {
                sellTable.getColumn(columnAlias).setCellRenderer(centerRenderer);
            } catch (IllegalArgumentException iae) {
                // exception occurs if there is no column with such alias in table at present moment
            }
        }
        DefaultTableCellRenderer rightRenderer = new DefaultCellRenderer();
        rightRenderer.setHorizontalAlignment(DefaultTableCellRenderer.RIGHT);
        columnAliases = new ArrayList<>(Arrays.asList("sum", "price", "discount"));
        for (String columnAlias : columnAliases) {
            try {
                sellTable.getColumn(columnAlias).setCellRenderer(rightRenderer);
            } catch (IllegalArgumentException iae) {
                // exception occurs if there is no column with such alias in table at present moment
            }
        }

//        for (int column = 0; column < sellTable.getColumnCount(); column++) {
//            if (column == 2 ) continue;
//            TableColumn tableColumn = sellTable.getColumnModel().getColumn(column);
//            int preferredWidth = tableColumn.getMinWidth();
//            int maxWidth = tableColumn.getMaxWidth();
//            System.out.println("column " + column + " preferredWidth = " + preferredWidth + " maxWidth = " + maxWidth);
//
//            for (int row = 0; row < sellTable.getRowCount(); row++) {
//                TableCellRenderer cellRenderer = sellTable.getCellRenderer(row, column);
//                Component c = sellTable.prepareRenderer(cellRenderer, row, column);
//                int width = c.getPreferredSize().width + sellTable.getIntercellSpacing().width;
//                preferredWidth = Math.max(preferredWidth, width);
//
//                //  We've exceeded the maximum width, no need to check other rows
//
//                if (preferredWidth >= maxWidth) {
//                    preferredWidth = maxWidth;
//                    break;
//                }
//            }
//
//            tableColumn.setPreferredWidth(preferredWidth);
//        }

        setJTableColumnsWidth(sellTable, sellTable.getWidth(), 5, 7, 65, 10, 13);

//        sellTable.getColumn(Resources.getInstance().getString("name")).setPreferredWidth(300);
        System.out.println(sellTable.getWidth() + " ");
        System.out.println(sellTable.getColumn("").getPreferredWidth() + " ");
        System.out.println(sellTable.getColumn("№").getPreferredWidth() + " ");
        System.out.println(sellTable.getColumn(Resources.getInstance().getString("name")).getPreferredWidth() + " ");
        System.out.println(sellTable.getColumn(Resources.getInstance().getString("am_nt")).getPreferredWidth() + " ");
        System.out.println(sellTable.getColumn(Resources.getInstance().getString("sum")).getPreferredWidth() + " ");


        int tablePanelLocationY = (int) tablePanel.getLocation().getY();    // actually, determines height of panel under table
        int navPanelHeaderHeight = tablePanelLocationY + tableHeaderHeight;

        return navPanelHeaderHeight;
    }

    /**
     * Method sets table's columns width.
     */
    public static void setJTableColumnsWidth(JTable table, int tablePreferredWidth,
                                             double... percentages) {
        double total = 0;
        for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
            total += percentages[i];
        }

        for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
            TableColumn column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth((int)
                    (tablePreferredWidth * (percentages[i] / total)));
        }
    }

    /**
     * Method is called when action occurs (i.e. button pressed or timer triggers).
     *
     * @param e event, that occurs.
     */
    @Override
    public void actionPerformed(ActionEvent e) {

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
                    loginDialog.setTextFieldText("");
                    launchDialog(true, loginDialog);
                    return;
                default:
                    break;
            }
            navigatePanelContainerLayout.show(navigatePanelContainer, "navPanelBack");
            revalidate();
            repaint();                  // enforced to call to provide synchronous appearance of both panels on weak hardware
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

        final Consumer<Integer> actionBackToSellPanel = actions;
        navPanelBack.getMainPanel().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
                    actionBackToSellPanel.accept(0);    // parameter does not matter in this context
            }
        });
    }

    /**
     * Create {@link List} of texts for buttons of each tiled panel.
     * The size of list equals the amount of buttons on specified tiled panel.
     * The amount and names of tiled panels specified in main_frame.form. In this method we initialize them.
     */
    private void initTiledPanel() {
        String[] names0 = {"receipt_cleaning", "return_receipt", "put_off_receipt", "load_receipt", "print_copy",
                "last_receipt", "load_order", "profile_filling", "remove_discounts", "manual_discount",
                "displayed_columns_of_table"};
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
                    manualDiscountDialog.setTextFieldText(null);   // null - only selects text in text field
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
                    double cashAmount = 10.43;
                    depositWithdrawDialog.setTextFieldText(String.format(Locale.ROOT, "%.2f", cashAmount));
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
     * Custom initialization of specified components of form, that is bound to this class.
     * This method is mandatory if "Custom Create" option is marked for at least one component of bound to this
     * class *.form file. Method is intended to initialize this specified component(s).
     */
    private void createUIComponents() {
        initSplashScreenPanel();
        initNavigatePanel();
        initTiledPanel();
        scrollPaneOfSellTable = new TouchScroll();      // for scrolling of sell table
    }

    // TODO: 11.09.2019 Установить действия на все кнопки TiledPanel
    // TODO: 26.09.2019 Окно оплаты
    // TODO: 26.09.2019 Окно со списком
    // TODO: 26.09.2019 Окно клавиатуры
    // TODO: 26.09.2019 Окно с плиткой товаров
    // TODO: 27.09.2019 Проверить Analyzer ом.
    // TODO: 27.09.2019 invokeLater glasspanel
    // TODO: 27.09.2019 вид кнопок при нажатии, рисование компонентов
    // TODO: 30.09.2019 Папку с картинками для synth удалить после того, как нарисую цвет кнопки
    // TODO: 26.07.2019 Подобрать более оптимальный фон кнопки при её нажатии
    // TODO: 01.10.2019 Установку всех цветов и шрифтов собрать в одном месте 
    // TODO: 01.10.2019 Диалоговые окна реализовать с помощью JPanels
    // TODO: 02.10.2019 Scrolling of JTable
    // TODO: 08.10.2019 Заменить html переносы на TextPanes во всех местах, где может возникнуть перенос
}