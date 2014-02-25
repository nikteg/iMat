import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;
import se.chalmers.ait.dat215.project.Product;
import com.alee.extended.panel.WebButtonGroup;
import com.alee.laf.StyleConstants;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebToggleButton;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.label.WebLabel;
import com.alee.laf.tabbedpane.WebTabbedPane;



public class MainWindow implements ActionListener {

	private JFrame frame;
	private JTextField txtSearchBox;
	private JLabel lblImat;
	private JScrollPane categoriesScrollPane;
	private WebComboBox userComboBox;
	private JPanel panel;
	private JScrollPane contentScrollPane;
	private JPanel contentPanel;
	private int margin = 8;
	private int numResults;
	private WebTabbedPane sidebarTabbedPane;

	private WebToggleButton toggleGridViewButton;
	private WebToggleButton toggleListViewButton;
	private WebButtonGroup toggleViewButtonGroup;
	private JPanel cardPanelList;
	private JPanel cardPanelGrid;

	private Timer searchTimer = new Timer(500, this);
	private JScrollPane varukorgScrollPane;
	private JPanel varukorgPanel;

	private IMatModel model = IMatModel.getInstance();
	private JList cartList;
	private DefaultListModel cartListModel = new DefaultListModel();

	private JPanel confirmPurchasePanel;
	private AddressSettingsPanel addressSettingsPanel_1;
	private JPanel cartConfirmationPanel;
	private CardSettingsPanel cardSettingsPanel_1;
	private JPanel signedInPanel;
	private JPanel userPanel;
	private JPanel signedOutPanel;
	private JButton signInButton;
	private LogInWindow loginWindow;
	private SettingsWindow settingsWindow;
	private List<Product> searchResults;
	private ButtonGroup categoryButtonGroup = new ButtonGroup();
	private List<CategoryToggleButton> categorybuttons = new ArrayList<CategoryToggleButton>();
	private CategoryToggleButton buttonAllCategories;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
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
		frame.setBounds(100, 100, 1050, 772);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(
				new MigLayout("insets 4px", "[192px:n][grow][72px][212px:212px]", "[][][grow]"));

		lblImat = new JLabel();
		lblImat.setIcon(new ImageIcon(MainWindow.class
				.getResource("/resources/logo.png")));
		lblImat.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(lblImat, "cell 0 0 1 2,growx,aligny center");

		txtSearchBox = new JTextField();
		txtSearchBox.addActionListener(this);
		txtSearchBox.addKeyListener(new TxtSearchBoxKeyListener());
		txtSearchBox.setFont(new Font("Lucida Grande", Font.PLAIN, 24));
		frame.getContentPane().add(txtSearchBox, "cell 1 1,grow");
		txtSearchBox.setColumns(10);

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
				signedInPanel.setLayout(new MigLayout("insets 0", "[212px]", "[48px]"));
		
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

				calculateResults(width, height,numResults);
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
		cardPanelList.setLayout(new BoxLayout(cardPanelList, BoxLayout.Y_AXIS));
		contentPanel.add(cardPanelList, "cardPanelList");

		sidebarTabbedPane = new WebTabbedPane();
		sidebarTabbedPane.setFocusable(false);

		varukorgScrollPane = new JScrollPane();
		sidebarTabbedPane.addTab("Varukorg", null, varukorgScrollPane, null);

		varukorgPanel = new JPanel();
		varukorgScrollPane.setViewportView(varukorgPanel);
		varukorgPanel.setLayout(new BorderLayout(0, 0));
		
		cartList = new JList();
		varukorgPanel.add(cartList);
		sidebarTabbedPane.addTab("Favoriter", new WebLabel());
		sidebarTabbedPane.addTab("Historik", new WebLabel());
		frame.getContentPane().add(sidebarTabbedPane, "cell 3 2,grow");
		
		cartList.setModel(cartListModel);
		
		confirmPurchasePanel = new JPanel();
		contentPanel.add(confirmPurchasePanel, "cardConfrimPanel");
		confirmPurchasePanel.setLayout(new MigLayout("", "[][grow]", "[grow][grow][]"));
		
		cartConfirmationPanel = new JPanel();
		confirmPurchasePanel.add(cartConfirmationPanel, "cell 0 0 2 1,grow");
		
		addressSettingsPanel_1 = new AddressSettingsPanel();
		confirmPurchasePanel.add(addressSettingsPanel_1, "cell 0 1,grow");
		
		cardSettingsPanel_1 = new CardSettingsPanel();
		confirmPurchasePanel.add(cardSettingsPanel_1, "cell 1 1,grow");
		
		buttonAllCategories = new CategoryToggleButton("Alla kategorier",numResults);
		buttonAllCategories.setSelected(true);
		buttonAllCategories.addActionListener(this);
		buttonAllCategories.setActionCommand("search");
		panel.add(buttonAllCategories, "growx");
		categoryButtonGroup.add(buttonAllCategories);

		for (Constants.Category c : Constants.Category.values()) {
			CategoryToggleButton button = new CategoryToggleButton(c.getName(),0);
			button.setCategory(c);
			button.setHorizontalAlignment(JButton.LEFT);
			button.addActionListener(this);
			button.setActionCommand("search");
			categorybuttons.add(button);
			panel.add(button, "growx");
			categoryButtonGroup.add(button);
		}
		txtSearchBox.setText("");
		search();
		
		
	}

	private void calculateResults(int width, int height, int num) {
		if (toggleGridViewButton.isSelected() && width != 0) {
			int cols = width / (128 + margin);
			int rows = num / cols;
			rows += ((rows * cols < num) ? 1 : 0);

			contentPanel.setPreferredSize(new Dimension(width - 32,
					(rows * (160 + margin)) + margin));
			
			contentScrollPane.revalidate();
		}
	}

	@Override
	public void actionPerformed(ActionEvent action) {

		if (action.getActionCommand() == "add_cart") {
			Product product = ((Item)action.getSource()).product;
			cartListModel.addElement(product.getName());
			System.out.println(product.getName());
		}
		
		if (action.getActionCommand() == "toggle_grid") {
			CardLayout cl = (CardLayout) (contentPanel.getLayout());
			cl.show(contentPanel, "cardPanelGrid");
			calculateResults(contentScrollPane.getWidth(),
					contentScrollPane.getHeight(), numResults);
		}
		
		if (action.getSource() == toggleGridViewButton) {
			CardLayout cl = (CardLayout) (contentPanel.getLayout());
			cl.show(contentPanel, "cardPanelGrid");
			calculateResults(contentScrollPane.getWidth(),
					contentScrollPane.getHeight(),numResults);
		}

		if (action.getActionCommand() == "toggle_list") {
			CardLayout cl = (CardLayout) (contentPanel.getLayout());
			cl.show(contentPanel, "cardPanelList");
			contentPanel.setPreferredSize(cardPanelList.getPreferredSize());
		}

		if (action.getActionCommand() == "search") {
			if (action.getSource() == txtSearchBox) {
				CardLayout cl = (CardLayout) (contentPanel.getLayout());
				cl.show(contentPanel, toggleGridViewButton.isSelected() ? "cardPanelGrid" : "cardPanelList");
			}
			
			search();
		}
		
		if(action.getSource() == signInButton){
			loginWindow = new LogInWindow(frame, this);
			loginWindow.setLocationRelativeTo(frame);
			loginWindow.setVisible(true);
		}

		if (action.getSource() == userComboBox) {
			userComboBox.hidePopup();
			userComboBox.setSelectedIndex(0);
			if (userComboBox.getSelectedIndex() == 1) {
				settingsWindow = new SettingsWindow(frame, this);
				settingsWindow.setLocationRelativeTo(frame);
				settingsWindow.setVisible(true);
			}else if(userComboBox.getSelectedIndex() == 2){
				logOut();
			}

		}
	}

	private void search() {
		String text = txtSearchBox.getText();
		
		// Skip search
		if (text.length() < 2) return;
		
		searchResults = model.getSearchResults(text);

		// Clear panel search results
		cardPanelGrid.removeAll();
		cardPanelList.removeAll();
	
		for (int i = 0; i < searchResults.size(); i++) {
			Product p = searchResults.get(i);
			boolean skipstep = true;
			for (CategoryToggleButton ctb : categorybuttons) {
				if(ctb.isSelected()){
					if(model.getCategory(p).equals(ctb.getCategory())){
						skipstep = false;
					}
				}
			}
			if(skipstep && !buttonAllCategories.isSelected()){
				continue;
			}
			
			ItemList item = new ItemList(p, this);
			
			// Alternate background in list view
			if (i % 2 == 1) item.setBackground(new Color(248, 248, 248));
			cardPanelGrid.add(new ItemGrid(p, this));
			cardPanelList.add(item);
		}
		
		updateButtonNumbers();
		calculateResults(contentScrollPane.getWidth(), contentScrollPane.getHeight(),numResults);
		cardPanelGrid.revalidate();
		cardPanelList.revalidate();
		cardPanelGrid.repaint();
		cardPanelList.repaint();
	}
	
	private void updateButtonNumbers(){
		
		buttonAllCategories.SetNumber(searchResults.size());
		for (int i = 0; i < Constants.Category.values().length; i++) {
			int num = 0;
			
			for (int j = 0; j < searchResults.size(); j++) {
				if (Constants.Category.values()[i].getName().equals(model.getCategory(searchResults.get(j)).getName())) {
					num++;
				}
			}
			
			categorybuttons.get(i).setName(Constants.Category.values()[i].getName());
			categorybuttons.get(i).SetNumber(num);
		}		
	}

	private JPanel getCart() {
		return varukorgPanel;
	}

	private class TxtSearchBoxKeyListener extends KeyAdapter {
		@Override
		public void keyReleased(KeyEvent keyEvent) {
			searchTimer.restart();
		}
	}
	
	public void logOut(){
		//TODO Figure out logout method
		//model.logOut();
		System.out.println("Du har nu loggat ut.");
		CardLayout cl = (CardLayout) (userPanel.getLayout());
		cl.show(userPanel, "signedOutPanel");
				
	}
	
	public void logIn(String userName, String password) {
		//TODO Figure out login method
		//model.logIn();
		
		System.out.println("du har loggat in som " + userName);
		userComboBox.setModel(new DefaultComboBoxModel(new String[] {
				userName, "Kontoinst\u00E4llningar", "Logga ut" }));
		loginWindow.setVisible(false);
		CardLayout cl = (CardLayout) (userPanel.getLayout());
		cl.show(userPanel, "signedInPanel");
		
		
	}

	public void createUser(String userName, String email, String password) {
		//TODO Figure out create user method
		//model.createUser(userName,email,password);
		System.out.println("du har registerat dig som " + userName);
		logIn(userName,password);
		
		
	}
}
