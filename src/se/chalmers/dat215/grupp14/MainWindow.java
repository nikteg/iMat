package se.chalmers.dat215.grupp14;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import net.miginfocom.swing.MigLayout;
import se.chalmers.ait.dat215.project.Product;
import se.chalmers.ait.dat215.project.ShoppingItem;
import se.chalmers.dat215.grupp14.backend.Account;
import se.chalmers.dat215.grupp14.backend.Constants;
import se.chalmers.dat215.grupp14.backend.IMatModel;
import com.alee.extended.panel.WebButtonGroup;
import com.alee.laf.StyleConstants;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebToggleButton;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.tabbedpane.WebTabbedPane;

@SuppressWarnings("serial")
public class MainWindow extends JFrame implements ActionListener, PropertyChangeListener, ChangeListener {
    private JLabel lblLogo;
    private JScrollPane scrollPaneCategories;
    private WebComboBox userComboBox;
    private JPanel panelCategories;
    private JScrollPane scrollPaneContent;
    private JPanel panelContent;
    private WebTabbedPane tabbedPaneSidebar;

    private SearchField searchField;
    private WebToggleButton tglBtnGrid;
    private WebToggleButton tglBtnList;
    private WebButtonGroup groupGridList;
    private JPanel cardPanelList;
    private JPanel cardPanelGrid;

    private JPanel signedInPanel;
    private JPanel userPanel;
    private JPanel signedOutPanel;
    private JButton signInButton;
    private List<Product> searchResults = new ArrayList<Product>();
    private ButtonGroup groupCategories = new ButtonGroup();
    private List<CategoryButton> categorybuttons = new ArrayList<CategoryButton>();
    private CategoryButton tglBtnAllCategories;

    private JPanel panelCheckout;
    private SettingsDialog settingsWindow;
    private SignInDialog loginWindow;
    private Constants.Category currentCategory;
    private JPanel favoritePanel;
    private FavoriteView favoriteView;
    private JPanel historyPanel;
    private HistoryView historyView;
    private boolean logoChanged = true;
    private JPanel listPanel;
    private ListView listView;

    private IMatModel model;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        MainWindow window = new MainWindow(new IMatModel());
        window.setVisible(true);
    }

    /**
     * Create the application with given model
     */
    public MainWindow(IMatModel model) {
        super("iMat - Effektiva matinköp för folk i farten");
        this.model = model;

        StyleConstants.drawFocus = false;
        StyleConstants.drawShade = false;
        StyleConstants.smallRound = 0;
        StyleConstants.mediumRound = 0;

        WebLookAndFeel.install();

        model.addPropertyChangeListener(this);
        initializeGUI();
        
        // Perform a search on startup
        model.search("");
    }

    /**
     * Get model
     * @return
     */
    public IMatModel getModel() {
        return model;
    }

    /* KONAMI STUFF */
    public void changeLogo() {
        if (logoChanged) {
            lblLogo.setIcon(new ImageIcon(MainWindow.class.getResource("resources/images/logo2.png")));
            setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        } else {
            lblLogo.setIcon(new ImageIcon(MainWindow.class.getResource("resources/images/logo.png")));
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }

        setBounds(getX(), getY(), getWidth() - (int) (Math.random() * 100), getHeight() - (int) (Math.random() * 100));

        logoChanged = !logoChanged;
    }

    /* END KONAMI STUFF */

    /**
     * Initialize GUI
     */
    private void initializeGUI() {
        if (System.getProperty("os.name").contains("Windows")) {
            setBounds(100, 100, 1120, 772);
            setMinimumSize(new Dimension(740, 360));
        } else {
            setBounds(100, 100, 1110, 772);
            setMinimumSize(new Dimension(720, 360));
        }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(
                new MigLayout("insets 4px", "[192px:n][448.00,grow][72px][300px:300px]", "[][][grow]"));

        // Logotype
        lblLogo = new JLabel();
        lblLogo.setToolTipText("KONAMI?");
        lblLogo.setIcon(new ImageIcon(MainWindow.class.getResource("resources/images/logo.png")));
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
        getContentPane().add(lblLogo, "cell 0 0 1 2,growx,aligny center");

        // Search field
        searchField = new SearchField(model, this);
        searchField.setToolTipText("Skriv här för att söka. Resultaten visas i realtid");
        getContentPane().add(searchField, "cell 1 1,grow");

        // Change display mode buttons
        tglBtnGrid = new WebToggleButton(new ImageIcon(
                MainWindow.class.getResource("resources/images/icons/application_view_tile.png")));
        tglBtnGrid.setToolTipText("Visa produkter i standardvy");
        tglBtnList = new WebToggleButton(new ImageIcon(
                MainWindow.class.getResource("resources/images/icons/application_view_list.png")));
        tglBtnList.setToolTipText("Visa produkter i listvy");
        groupGridList = new WebButtonGroup(true, tglBtnGrid, tglBtnList);
        groupGridList.setButtonsMargin(10);
        groupGridList.setButtonsDrawFocus(false);

        tglBtnGrid.addActionListener(this);
        tglBtnGrid.setActionCommand("toggle_grid");
        tglBtnGrid.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        tglBtnList.addActionListener(this);
        tglBtnList.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        tglBtnList.setActionCommand("toggle_list");

        // Pre-select the grid button
        tglBtnGrid.setSelected(true);

        getContentPane().add(groupGridList, "cell 2 1,grow");

        userPanel = new JPanel();
        getContentPane().add(userPanel, "cell 3 1,grow");
        userPanel.setLayout(new CardLayout(0, 0));

        signedOutPanel = new JPanel();
        signedOutPanel.setLayout(new MigLayout("insets 0px", "[89px,grow]", "[23px,grow]"));
        userPanel.add(signedOutPanel, "signedOutPanel");

        signInButton = new JButton("Logga in");
        signInButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        signInButton.addActionListener(this);
        signedOutPanel.add(signInButton, "cell 0 0,grow");

        signedInPanel = new JPanel();
        signedInPanel.setLayout(new MigLayout("insets 0px", "[89px,grow]", "[23px,grow]"));
        userPanel.add(signedInPanel, "signedInPanel");

        userComboBox = new WebComboBox();
        signedInPanel.add(userComboBox, "cell 0 0,grow");
        userComboBox.addActionListener(this);
        userComboBox.setFocusable(false);

        panelCategories = new JPanel();
        panelCategories.setLayout(new MigLayout("flowy,insets 0", "[grow]", "[28px]"));
        panelCategories.setBorder(null);

        scrollPaneCategories = new JScrollPane();
        scrollPaneCategories.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPaneCategories.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPaneCategories.setBorder(null);
        scrollPaneCategories.setViewportView(panelCategories);
        getContentPane().add(scrollPaneCategories, "cell 0 2,grow");

        scrollPaneContent = new JScrollPane();
        scrollPaneContent.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPaneContent.setViewportBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        scrollPaneContent.setBackground(Color.WHITE);
        scrollPaneContent.setBorder(null);
        scrollPaneContent.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent event) {
                calculateResults(((JScrollPane) event.getSource()).getWidth(),
                        ((JScrollPane) event.getSource()).getHeight());
            }
        });
        scrollPaneContent.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        getContentPane().add(scrollPaneContent, "cell 1 2 2 1,grow");

        panelContent = new JPanel();
        panelContent.setBorder(null);
        panelContent.setBackground(Color.WHITE);
        scrollPaneContent.setViewportView(panelContent);
        panelContent.setLayout(new CardLayout(0, 0));

        cardPanelGrid = new JPanel();
        panelContent.add(cardPanelGrid, "cardPanelGrid");
        cardPanelGrid.setLayout(new FlowLayout(FlowLayout.LEFT, Constants.MARGIN, Constants.MARGIN));

        cardPanelList = new JPanel();
        panelContent.add(cardPanelList, "cardPanelList");
        cardPanelList.setLayout(new MigLayout("insets 0px, gapy 0px", "[grow]", "[]"));

        tabbedPaneSidebar = new WebTabbedPane();
        tabbedPaneSidebar.setFocusable(false);

        panelCheckout = new JPanel();
        tabbedPaneSidebar.addTab("Varukorg", null, panelCheckout, null);
        tabbedPaneSidebar.setToolTipTextAt(0, "Visa din varukorg");
        tabbedPaneSidebar.addChangeListener(this);
        panelCheckout.setLayout(new MigLayout("insets 4px", "[grow]", "[2px,grow]"));

        CartView cartView = new CartView(this, model);
        cartView.setFocusable(false);
        panelCheckout.add(cartView, "cell 0 0,grow");

        getContentPane().add(tabbedPaneSidebar, "cell 3 2,grow");

        favoritePanel = new JPanel();
        tabbedPaneSidebar.addTab("Favoriter", null, favoritePanel, null);
        tabbedPaneSidebar.setToolTipTextAt(1, "Visa dina favoritmärkta varor");
        favoritePanel.setLayout(new MigLayout("insets 4px", "[grow]", "[2px,grow]"));

        favoriteView = new FavoriteView(model);
        favoritePanel.add(favoriteView, "cell 0 0,grow");

        listPanel = new JPanel();
        tabbedPaneSidebar.addTab("Listor", null, listPanel, null);
        listPanel.setLayout(new MigLayout("insets 6px", "[grow]", "[grow]"));

        listView = new ListView(model);
        listPanel.add(listView, "cell 0 0,grow");

        historyPanel = new JPanel();
        historyPanel.setFocusable(false);
        tabbedPaneSidebar.addTab("Historik", null, historyPanel, null);
        tabbedPaneSidebar.setToolTipTextAt(3, "Visa dina tidigare beställningar");
        historyPanel.setLayout(new MigLayout("insets 6px", "[296px]", "[646px]"));

        historyView = new HistoryView(model);
        historyView.setFocusable(false);
        historyPanel.add(historyView, "cell 0 0,grow");

        tglBtnAllCategories = new CategoryButton("Alla kategorier", searchResults.size());
        tglBtnAllCategories.setSelected(true);
        tglBtnAllCategories.addActionListener(this);
        tglBtnAllCategories.setActionCommand("category_change");
        panelCategories.add(tglBtnAllCategories, "growx");
        groupCategories.add(tglBtnAllCategories);

        if (model.getAccountHandler().getCurrentAccount().isAnonymous()) {
            tabbedPaneSidebar.setEnabledAt(1, false);
            tabbedPaneSidebar.setToolTipTextAt(1, "Logga in för att se dina favoriter");
            tabbedPaneSidebar.setEnabledAt(2, false);
            tabbedPaneSidebar.setToolTipTextAt(2, "Logga in för att se dina listor");
            tabbedPaneSidebar.setEnabledAt(3, false);
            tabbedPaneSidebar.setToolTipTextAt(3, "Logga in för att se din orderhistorik");
        }

        for (Constants.Category c : Constants.Category.values()) {
            CategoryButton button = new CategoryButton(c.getName(), 0);
            button.setCategory(c);
            button.setHorizontalAlignment(SwingConstants.LEFT);
            button.addActionListener(this);
            button.setActionCommand("category_change");
            categorybuttons.add(button);
            panelCategories.add(button, "growx");
            groupCategories.add(button);
        }
    }
    
    /**
     * Update category button amount
     */
    private void updateButtonNumbers() {
        tglBtnAllCategories.setAmount(searchResults.size());
        for (int i = 0; i < Constants.Category.values().length; i++) {
            int num = 0;
            
            for (int j = 0; j < searchResults.size(); j++) {
                if (Constants.Category.values()[i].getName().equals(model.getCategory(searchResults.get(j)).getName())) {
                    num++;
                }
            }
            
            categorybuttons.get(i).setName(Constants.Category.values()[i].getName());
            categorybuttons.get(i).setAmount(num);
        }
    }
    
    /**
     * Populate main view with search results
     * @param results
     */
    private void populateResults(List<Product> results) {
        // Clear panel search results
        cardPanelGrid.removeAll();
        cardPanelList.removeAll();
        
        for (int i = 0; i < results.size(); i++) {
            Product product = results.get(i);
            ItemList item = new ItemList(new ShoppingItem(product), favoriteView, model);
            
            // Alternate background in list view
            if (i % 2 == 1)
                item.setBackground(Constants.ALT_COLOR);
            
            cardPanelGrid.add(new ItemGrid(new ShoppingItem(product), favoriteView, model));
            cardPanelList.add(item, "wrap,growx");
        }
        
        updateButtonNumbers();
        calculateResults(scrollPaneContent.getWidth(), scrollPaneContent.getHeight());
        cardPanelGrid.revalidate();
        cardPanelList.revalidate();
        cardPanelGrid.repaint();
        cardPanelList.repaint();
    }

    /**
     * Resize the main panel according to the amount of items in the view and window size
     * @param width
     * @param height
     */
    private void calculateResults(int width, int height) {
        int totalnum = cardPanelList.getComponentCount();

        if (tglBtnGrid.isSelected()) {
            int cols = Math.max(1, width / (Constants.GRID_WIDTH + Constants.MARGIN));
            int rows = totalnum / cols;
            rows += ((rows * cols < totalnum) ? 1 : 0);

            panelContent
                    .setPreferredSize(new Dimension(width - 0, (rows * (Constants.GRID_HEIGHT + Constants.MARGIN)) + Constants.MARGIN));
            scrollPaneContent.revalidate();
        } else if (tglBtnList.isSelected()) {
            panelContent.setPreferredSize(new Dimension(width - 32, (totalnum * Constants.LIST_HEIGHT)));

            scrollPaneContent.revalidate();
        }
    }

    @Override
    public void actionPerformed(ActionEvent action) {

        if (action.getActionCommand() == "toggle_grid") {
            CardLayout cl = (CardLayout) (panelContent.getLayout());
            cl.show(panelContent, "cardPanelGrid");
            calculateResults(scrollPaneContent.getWidth(), scrollPaneContent.getHeight());
        }

        if (action.getActionCommand() == "toggle_list") {
            CardLayout cl = (CardLayout) (panelContent.getLayout());
            cl.show(panelContent, "cardPanelList");
            calculateResults(scrollPaneContent.getWidth(), scrollPaneContent.getHeight());
        }

        if (action.getActionCommand() == "category_change") {
            if (action.getSource() == tglBtnAllCategories) {
                populateResults(searchResults);

                return;
            }

            currentCategory = ((CategoryButton) action.getSource()).getCategory();
            List<Product> results = new ArrayList<Product>();

            for (Product product : searchResults) {
                if (currentCategory.equals(model.getCategory(product))) {
                    results.add(product);
                }
            }

            populateResults(results);
        }

        if (action.getSource() == signInButton) {
            loginWindow = new SignInDialog(this, model);
            loginWindow.setLocationRelativeTo(this);
            loginWindow.setVisible(true);
        }

        if (action.getSource() == userComboBox) {
            userComboBox.hidePopup();

            if (userComboBox.getSelectedIndex() == 1) {
                settingsWindow = new SettingsDialog(this, model);
                settingsWindow.setLocationRelativeTo(this);
                settingsWindow.setVisible(true);
            } else if (userComboBox.getSelectedIndex() == 2) {
                model.accountSignOut();
            }

            userComboBox.setSelectedIndex(0);

        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName() == "account_signedin") {
            Account account = (Account) evt.getNewValue();
            userComboBox.setModel(new DefaultComboBoxModel(new String[] { account.getUserName(),
                    "Kontoinst\u00E4llningar", "Logga ut" }));
            CardLayout cl = (CardLayout) (userPanel.getLayout());
            cl.show(userPanel, "signedInPanel");

            tabbedPaneSidebar.setEnabledAt(1, true);
            tabbedPaneSidebar.setToolTipTextAt(1, "Favoritmärkta varor");
            tabbedPaneSidebar.setEnabledAt(2, true);
            tabbedPaneSidebar.setToolTipTextAt(2, "Sparade listor");
            tabbedPaneSidebar.setEnabledAt(3, true);
            tabbedPaneSidebar.setToolTipTextAt(3, "Tidigare beställningar");
        }

        if (evt.getPropertyName() == "account_signout") {
            CardLayout cl = (CardLayout) (userPanel.getLayout());
            cl.show(userPanel, "signedOutPanel");

            tabbedPaneSidebar.setEnabledAt(1, false);
            tabbedPaneSidebar.setToolTipTextAt(1, "Logga in för att se dina favoriter");
            tabbedPaneSidebar.setEnabledAt(2, false);
            tabbedPaneSidebar.setToolTipTextAt(2, "Logga in för att se dina listor");
            tabbedPaneSidebar.setEnabledAt(3, false);
            tabbedPaneSidebar.setToolTipTextAt(3, "Logga in för att se din orderhistorik");
            tabbedPaneSidebar.setSelectedIndex(0);
        }

        if (evt.getPropertyName() == "search") {
            groupCategories.clearSelection();
            tglBtnAllCategories.setSelected(true);
            searchResults = (ArrayList<Product>) evt.getNewValue();
            populateResults((ArrayList<Product>) evt.getNewValue());
            scrollPaneContent.getVerticalScrollBar().setMaximum(panelContent.getHeight());
            scrollPaneContent.getVerticalScrollBar().setValue(scrollPaneContent.getVerticalScrollBar().getMinimum());
        }

    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (((WebTabbedPane) e.getSource()).getSelectedIndex() == 0
                || ((WebTabbedPane) e.getSource()).getSelectedIndex() == 2) {
            favoriteView.updateFavoriteView();
        }

    }
}
