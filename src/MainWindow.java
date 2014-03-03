import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;
import se.chalmers.ait.dat215.project.Product;
import se.chalmers.ait.dat215.project.ShoppingItem;

import com.alee.extended.panel.WebButtonGroup;
import com.alee.laf.StyleConstants;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebToggleButton;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.tabbedpane.WebTabbedPane;
import com.alee.laf.text.WebTextField;
import java.awt.BorderLayout;

public class MainWindow implements ActionListener, PropertyChangeListener {

	private JFrame frame;
	private JLabel lblImat;
	private JScrollPane categoriesScrollPane;
	private WebComboBox userComboBox;
	private JPanel panel;
	private JScrollPane contentScrollPane;
	private JPanel contentPanel;
	private int margin = 10;
	private int numResults;
	private WebTabbedPane sidebarTabbedPane;

	private SearchField searchField;
	private WebToggleButton toggleGridViewButton;
	private WebToggleButton toggleListViewButton;
	private WebButtonGroup toggleViewButtonGroup;
	private JPanel cardPanelList;
	private JPanel cardPanelGrid;

	private Timer searchTimer = new Timer(500, this);
	private JScrollPane cartScrollPane;
	private JPanel varukorgPanel;

	private IMatModel model = IMatModel.getInstance();

	private JPanel confirmPurchasePanel;
	private AddressSettingsPanel addressSettingsPanel_1;
	private JPanel cartConfirmationPanel;
	private CardSettingsPanel cardSettingsPanel_1;
	private JPanel signedInPanel;
	private JPanel userPanel;
	private JPanel signedOutPanel;
	private JButton signInButton;
	private List<Product> searchResults = new ArrayList<Product>();
	private ButtonGroup categoryButtonGroup = new ButtonGroup();
	private List<CategoryToggleButton> categorybuttons = new ArrayList<CategoryToggleButton>();
	private CategoryToggleButton buttonAllCategories;

	
	private final static Logger LOGGER = Logger.getLogger(MainWindow.class.getName());

	private JPanel checkoutPanel;
	private JButton confirmPurchaseButton;
	private SettingsWindow settingsWindow;
	private LogInWindow loginWindow;
	private Constants.Category currentCategory;
	private JPanel favoritePanel;
	private FavoriteView favoriteView;
	private JScrollPane scrollPane;
	private JPanel historyPanel;
	private HistoryView historyView;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
		    public void run() {
				System.out.println("SHUTDOWN EMINENT");
				IMatModel.getInstance().shutDown();
		    }
		}));
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		StyleConstants.drawFocus = false;
		StyleConstants.drawShade = false;
		StyleConstants.smallRound = 0;
		StyleConstants.mediumRound = 0;

		WebLookAndFeel.install();
		
		searchTimer.setRepeats(false);
		searchTimer.setActionCommand("search");

		model.addPropertyChangeListener(this);
		initialize();
	}
	
	public IMatModel getModel() {
		return model;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1120, 772);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(
				new MigLayout("insets 4px", "[192px:n][448.00,grow][72px][300px:300px]", "[][][grow]"));

		lblImat = new JLabel();
		lblImat.setIcon(new ImageIcon(MainWindow.class
				.getResource("/resources/logo.png")));
		lblImat.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(lblImat, "cell 0 0 1 2,growx,aligny center");
		
		searchField = new SearchField(model);
		frame.getContentPane().add(searchField , "cell 1 1,grow");

		toggleGridViewButton = new WebToggleButton(
				new ImageIcon(
						MainWindow.class
								.getResource("/resources/icons/application_view_tile.png")));
		toggleListViewButton = new WebToggleButton(
				new ImageIcon(
						MainWindow.class
								.getResource("/resources/icons/application_view_list.png")));
		toggleViewButtonGroup = new WebButtonGroup(true, toggleGridViewButton,
				toggleListViewButton);
		toggleViewButtonGroup.setButtonsMargin(10);
		toggleViewButtonGroup.setButtonsDrawFocus(false);

		toggleGridViewButton.addActionListener(this);
		toggleGridViewButton.setActionCommand("toggle_grid");
		toggleListViewButton.addActionListener(this);
		toggleListViewButton.setActionCommand("toggle_list");

		toggleGridViewButton.setSelected(true);

		frame.getContentPane().add(toggleViewButtonGroup, "cell 2 1,grow");
		
		userPanel = new JPanel();
		frame.getContentPane().add(userPanel, "cell 3 1,grow");
		userPanel.setLayout(new CardLayout(0, 0));
		
		signedOutPanel = new JPanel();
		userPanel.add(signedOutPanel, "signedOutPanel");
		signedOutPanel.setLayout(new MigLayout("insets 0", "[89px,grow]", "[23px,grow]"));
		
		signInButton = new JButton("Logga in");
		signInButton.addActionListener(this);
		signedOutPanel.add(signInButton, "cell 0 0,grow");
		
		signedInPanel = new JPanel();
		userPanel.add(signedInPanel, "signedInPanel");
		signedInPanel.setLayout(new MigLayout("insets 0", "[89px,grow]", "[23px,grow]"));

		userComboBox = new WebComboBox();
		userComboBox.setAlignmentY(0.0f);
		userComboBox.setAlignmentX(0.0f);
		signedInPanel.add(userComboBox, "cell 0 0,grow");
		userComboBox.setFocusable(false);
		userComboBox.addActionListener(this);

		categoriesScrollPane = new JScrollPane();
		categoriesScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		categoriesScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		categoriesScrollPane.setBorder(null);
		frame.getContentPane().add(categoriesScrollPane, "cell 0 2,grow");

		panel = new JPanel();
		panel.setBorder(null);
		categoriesScrollPane.setViewportView(panel);

		panel.setLayout(new MigLayout("flowy,insets 0", "[grow]", "[28px]"));
		
		contentScrollPane = new JScrollPane();
		contentScrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		contentScrollPane.setViewportBorder(new EtchedBorder(
				EtchedBorder.LOWERED, null, null));
		contentScrollPane.setBackground(Color.WHITE);
		contentScrollPane.setBorder(null);
		contentScrollPane.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent arg0) {
				int width = ((JScrollPane) arg0.getSource()).getWidth();
				int height = ((JScrollPane) arg0.getSource()).getHeight();

				calculateResults(width, height);
			}
		});
		contentScrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		frame.getContentPane().add(contentScrollPane, "cell 1 2 2 1,grow");

		contentPanel = new JPanel();
		contentPanel.setBorder(null);
		contentPanel.setBackground(Color.WHITE);
		contentScrollPane.setViewportView(contentPanel);
		contentPanel.setLayout(new CardLayout(0, 0));

		cardPanelGrid = new JPanel();
		contentPanel.add(cardPanelGrid, "cardPanelGrid");
		cardPanelGrid
				.setLayout(new FlowLayout(FlowLayout.LEFT, margin, margin));

		cardPanelList = new JPanel();
		contentPanel.add(cardPanelList, "cardPanelList");
		cardPanelList.setLayout(new MigLayout("insets 0px, gapy 0px", "[grow]", "[64]"));

		sidebarTabbedPane = new WebTabbedPane();
		sidebarTabbedPane.setFocusable(false);
		
		checkoutPanel = new JPanel();
		sidebarTabbedPane.addTab("Varukorg", null, checkoutPanel, null);
		checkoutPanel.setLayout(new MigLayout("insets 4px", "[grow]", "[2px,grow]"));

		cartScrollPane = new JScrollPane();
		CartView cartView = new CartView(model, frame);
		cartView.setFocusable(false);
		checkoutPanel.add(cartView, "cell 0 0,grow");

		varukorgPanel = new JPanel();
		//cartScrollPane.setViewportView();

		varukorgPanel.setLayout(new GridLayout(100,1));

		frame.getContentPane().add(sidebarTabbedPane, "cell 3 2,grow");

		confirmPurchasePanel = new JPanel();
		contentPanel.add(confirmPurchasePanel, "cardConfrimPanel");
		confirmPurchasePanel.setLayout(new MigLayout("", "[][grow]", "[200.00][][grow]"));
		
		scrollPane = new JScrollPane();
		confirmPurchasePanel.add(scrollPane, "cell 0 0 2 1,grow");
		
		cartConfirmationPanel = new JPanel();
		scrollPane.setViewportView(cartConfirmationPanel);
		cartConfirmationPanel.setBorder(new TitledBorder(null, "Varukorg", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		cartConfirmationPanel.setLayout(new MigLayout("insets 0px", "[grow]", "[64px]"));
		
		addressSettingsPanel_1 = new AddressSettingsPanel();
		confirmPurchasePanel.add(addressSettingsPanel_1, "cell 0 1,grow");
		
		cardSettingsPanel_1 = new CardSettingsPanel();
		confirmPurchasePanel.add(cardSettingsPanel_1, "cell 1 1,grow");
		
		confirmPurchaseButton = new JButton("Bekräfta köp");
		confirmPurchasePanel.add(confirmPurchaseButton, "cell 1 2,alignx right,aligny bottom");
		
		favoritePanel = new JPanel();
		sidebarTabbedPane.addTab("Favoriter", null, favoritePanel, null);
		favoritePanel.setLayout(new MigLayout("insets 4px", "[grow]", "[2px,grow]"));
		
		favoriteView = new FavoriteView(model);
		favoritePanel.add(favoriteView, "cell 0 0,grow");
		
		historyPanel = new JPanel();
		historyPanel.setFocusable(false);
		sidebarTabbedPane.addTab("Historik", null, historyPanel, null);
		historyPanel.setLayout(new MigLayout("insets 4px", "[296px]", "[646px]"));
		
		historyView = new HistoryView(model, frame);
		historyView.setFocusable(false);
		historyPanel.add(historyView, "cell 0 0,grow");
		
		buttonAllCategories = new CategoryToggleButton("Alla kategorier", numResults);
		buttonAllCategories.setSelected(true);
		buttonAllCategories.addActionListener(this);
		buttonAllCategories.setActionCommand("category_change");
		panel.add(buttonAllCategories, "growx");
		categoryButtonGroup.add(buttonAllCategories);
		
		if (model.accountIsAnonymous()) {
			sidebarTabbedPane.setEnabledAt(1, false);
			sidebarTabbedPane.setEnabledAt(2, false);
		}

		for (Constants.Category c : Constants.Category.values()) {
			CategoryToggleButton button = new CategoryToggleButton(c.getName(), 0);
			button.setCategory(c);
			button.setHorizontalAlignment(JButton.LEFT);
			button.addActionListener(this);
			button.setActionCommand("category_change");
			categorybuttons.add(button);
			panel.add(button, "growx");
			categoryButtonGroup.add(button);
		}
	
		//Show all products at startup (for now)
		this.populateResults(model.getProducts());
	}

	private void calculateResults(int width, int height) {
		int num = 0;
		for (Product product : searchResults) {
			if (product.getCategory().equals(currentCategory)) {
				num++;
			}
		}
		
		int totalnum = num == 0 ? searchResults.size() : num;
		
		if (toggleGridViewButton.isSelected()) {
			int cols = Math.max(1, width / (180 + margin));
			int rows = totalnum / cols;
			rows += ((rows * cols < totalnum) ? 1 : 0);

			contentPanel.setPreferredSize(new Dimension(width - 32, (rows * (240 + margin)) + margin));
			contentScrollPane.revalidate();
		} else if (toggleListViewButton.isSelected()) {
			contentPanel.setPreferredSize(new Dimension(width - 32, (totalnum * 64)));
			
			contentScrollPane.revalidate();
		}
	}

	@Override
	public void actionPerformed(ActionEvent action) {
/*
		if (action.getActionCommand() == "favorite") {
			if (!((ItemGrid)action.getSource()).tglFavorite.isSelected()) return;
			Product product = ((Item)action.getSource()).shoppingItem.getProduct();
			System.out.println("DU FAVORISERADE JUST .... BAM BAM BAM: " + product.getName());
		}
		
		if (action.getActionCommand() == "add_cart") {
<<<<<<< HEAD
			Product product = ((Item)action.getSource()).product;
			System.out.println(product.getName());
			model.getShoppingCart().addProduct(product, 1.0);
			if(!model.getShoppingCart().getItems().contains(product)){
				varukorgPanel.add(new CartItem(new ShoppingItem(product, 1.0)));
				varukorgPanel.revalidate();
			}
=======
			ShoppingItem shoppingItem = ((Item)action.getSource()).shoppingItem;
			
						 
				if (model.getShoppingCart().getItems().contains(shoppingItem)){
					
					for(Component c : varukorgPanel.getComponents()){
						System.out.println((((CartItem) c).getShoppingItem().getProduct().getName()));
						if((((CartItem) c).getShoppingItem().getProduct()).equals(shoppingItem.getProduct())){
							((CartItem) c).addItem(((Item)action.getSource()).getAmount());
							break;
						}
					}
				
				}else {
					
					shoppingItem.setAmount(((Item)action.getSource()).getAmount());
					varukorgPanel.add(new CartItem(shoppingItem,this));
					model.getShoppingCart().addItem(shoppingItem);
					varukorgPanel.revalidate();
					
				} 
			
			setTotalPrice(model.getShoppingCart().getTotal()+":-");
		}
		
		if(action.getActionCommand() == "remove_from_cart"){
			CartItem cartItem = (CartItem)action.getSource();
			varukorgPanel.remove(cartItem);
			model.getShoppingCart().removeItem(cartItem.getShoppingItem());
			setTotalPrice(model.getShoppingCart().getTotal()+":-");
			varukorgPanel.revalidate();

		}
		
		if (action.getActionCommand() == "check_out"){
			CardLayout cl = (CardLayout) (contentPanel.getLayout());
			cl.show(contentPanel, "cardConfrimPanel");
			contentPanel.setPreferredSize(new Dimension(cartConfirmationPanel.getWidth(), cartConfirmationPanel.getHeight()));
			
			contentScrollPane.revalidate();
			
>>>>>>> origin/mlonn
		}
		*/
		if (action.getActionCommand() == "toggle_grid") {
			CardLayout cl = (CardLayout) (contentPanel.getLayout());
			cl.show(contentPanel, "cardPanelGrid");
			calculateResults(contentScrollPane.getWidth(), contentScrollPane.getHeight());
		}

		if (action.getActionCommand() == "toggle_list") {
			CardLayout cl = (CardLayout) (contentPanel.getLayout());
			cl.show(contentPanel, "cardPanelList");
			calculateResults(contentScrollPane.getWidth(), contentScrollPane.getHeight());
		}
		/*
		if (action.getActionCommand() == "search") {
			if (action.getSource() == searchTimer) {
				categoryButtonGroup.clearSelection();
				buttonAllCategories.setSelected(true);
			}
			search();
			calculateResults(contentScrollPane.getWidth(), contentScrollPane.getHeight());
			
		}
		*/
		
		if (action.getActionCommand() == "category_change") {
			currentCategory = ((CategoryToggleButton)action.getSource()).getCategory();
			List<Product> results = new ArrayList<Product>();
			
			for (Product product : searchResults) {
				if (buttonAllCategories.isSelected() || ((CategoryToggleButton)action.getSource()).getCategory().equals(model.getCategory(product))) {
					
					results.add(product);
				}
			}
			
			populateResults(results);
		}
		
		
		if (action.getSource() == signInButton) {
			loginWindow = new LogInWindow(frame, model);
			loginWindow.setLocationRelativeTo(frame);
			loginWindow.setVisible(true);
		}
		
		if (action.getSource() == userComboBox) {
			userComboBox.hidePopup();
			
			if (userComboBox.getSelectedIndex() == 1) {
				settingsWindow = new SettingsWindow(frame, model);
				settingsWindow.setLocationRelativeTo(frame);
				settingsWindow.setVisible(true);
			} else if(userComboBox.getSelectedIndex() == 2) {
				model.accountSignOut();
			}
			
			userComboBox.setSelectedIndex(0);

		}
	}
	
	private void updateButtonNumbers() {		
		buttonAllCategories.setNumber(searchResults.size());
		for (int i = 0; i < Constants.Category.values().length; i++) {
			int num = 0;
			
			for (int j = 0; j < searchResults.size(); j++) {
				if (Constants.Category.values()[i].getName().equals(model.getCategory(searchResults.get(j)).getName())) {
					num++;
				}
			}
			
			categorybuttons.get(i).setName(Constants.Category.values()[i].getName());
			categorybuttons.get(i).setNumber(num);
		}		
	}
	
	private void populateResults(List<Product> results) {
		// Clear panel search results
		cardPanelGrid.removeAll();
		cardPanelList.removeAll();
				
		for (int i = 0; i < results.size(); i++) {
			
			Product product = results.get(i);
			
			boolean skipstep = true;
			for (CategoryToggleButton ctb : categorybuttons) {
				if(ctb.isSelected()){
					if(model.getCategory(product).equals(ctb.getCategory())){
						skipstep = false;
					}
				}
			}
			
			if (skipstep && !buttonAllCategories.isSelected()) {
				continue;
			}
			
			ItemList item = new ItemList(new ShoppingItem(product), model);
			
			// Alternate background in list view
			if (i % 2 == 1) item.setBackground(Constants.ALT_COLOR);
			cardPanelGrid.add(new ItemGrid(new ShoppingItem(product), model));
			cardPanelList.add(item, "wrap,growx");
		}
		
		updateButtonNumbers();
		calculateResults(contentScrollPane.getWidth(), contentScrollPane.getHeight());
		cardPanelGrid.revalidate();
		cardPanelList.revalidate();
		cardPanelGrid.repaint();
		cardPanelList.repaint();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName() == "account_signedup") {
			
		}
		
		if (evt.getPropertyName() == "account_signedin") {
			Account account = (Account)evt.getNewValue();
			userComboBox.setModel(new DefaultComboBoxModel(new String[] { account.getUserName(), "Kontoinst\u00E4llningar", "Logga ut" }));
			CardLayout cl = (CardLayout) (userPanel.getLayout());
			cl.show(userPanel, "signedInPanel");
			sidebarTabbedPane.setEnabledAt(1, true);
			sidebarTabbedPane.setEnabledAt(2, true);
		}
		
		if (evt.getPropertyName() == "account_signout") {
			CardLayout cl = (CardLayout) (userPanel.getLayout());
			cl.show(userPanel, "signedOutPanel");
			sidebarTabbedPane.setEnabledAt(1, false);
			sidebarTabbedPane.setEnabledAt(2, false);
			sidebarTabbedPane.setSelectedIndex(0);
		}
		
		if (evt.getPropertyName() == "search") {
			categoryButtonGroup.clearSelection();
			buttonAllCategories.setSelected(true);
			searchResults = (ArrayList<Product>)evt.getNewValue();
			populateResults((ArrayList<Product>)evt.getNewValue());
		}
		
		if (evt.getPropertyName() == "cart_additem" || evt.getPropertyName() == "cart_updateitem") {
			sidebarTabbedPane.setSelectedIndex(0);
		}
		
		if (evt.getPropertyName() == "favorite_add") {
			sidebarTabbedPane.setSelectedIndex(1);
		}
	

	}
}
