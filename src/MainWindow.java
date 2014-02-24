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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EtchedBorder;

import net.miginfocom.swing.MigLayout;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Product;

import com.alee.extended.panel.WebButtonGroup;
import com.alee.laf.StyleConstants;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebToggleButton;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.label.WebLabel;
import com.alee.laf.tabbedpane.WebTabbedPane;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;
import javax.swing.JSeparator;
import javax.swing.UIManager;
import javax.swing.JPasswordField;
import java.awt.BorderLayout;

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
	
	private IMatDataHandler db = IMatDataHandler.getInstance();

	private JPanel cardPanelSettings;
	private JLabel lblInstllningarFr;
	private JPanel SignInSettingsPanel;
	private JTextField textField;
	private JLabel lblEmail;
	private JLabel lblNewPassword;
	private JLabel lblRepeatPassword;
	private JPasswordField passwordField;
	private JPasswordField passwordFieldRepeat;
	private JButton btnNewButton;
	private JScrollPane varuorgScrollPane;
	private JPanel varukorgPanel;
	private JPanel confirmPurchasePanel;
	private AddressSettingsPanel addressSettingsPanel;
	private AddressSettingsPanel addressSettingsPanel_1;
	private JPanel cartConfirmationPanel;
	private CardSettingsPanel cardSettingsPanel;
	private CardSettingsPanel cardSettingsPanel_1;

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

		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1050, 772);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(
				new MigLayout("insets 4px",
						"[192px:n][grow][72px][212px:212px]", "[][][grow]"));

		lblImat = new JLabel();
		lblImat.setIcon(new ImageIcon(MainWindow.class
				.getResource("/resources/logo.png")));
		lblImat.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(lblImat, "cell 0 0 1 2,growx,aligny center");

		txtSearchBox = new JTextField();
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
		toggleListViewButton.addActionListener(this);

		toggleGridViewButton.setSelected(true);

		frame.getContentPane().add(toggleViewButtonGroup, "cell 2 1,grow");

		userComboBox = new WebComboBox();
		userComboBox.setModel(new DefaultComboBoxModel(new String[] {
				"Mikael L\u00F6nn", "Kontoinst\u00E4llningar", "Logga ut" }));
		userComboBox.setFocusable(false);
		userComboBox.addActionListener(this);
		frame.getContentPane().add(userComboBox, "cell 3 1,grow");

		categoriesScrollPane = new JScrollPane();
		categoriesScrollPane.setBorder(null);
		frame.getContentPane().add(categoriesScrollPane, "cell 0 2,grow");

		panel = new JPanel();
		categoriesScrollPane.setViewportView(panel);

		panel.setLayout(new MigLayout("flowy,insets 0", "[grow]", "[28px]"));
		
		ButtonGroup group = new ButtonGroup();
		
		WebToggleButton buttonAllCategories = new WebToggleButton("<html><table cellpadding=0 cellspacing=0 style='width: 134px'><tr><td>Alla kategorier</td><td style='text-align: right; color: rgb(150, 150, 150)'>" + (int)(1 + Math.random() * 10)*10 + "</td></tr></table></html>");
		buttonAllCategories.setHorizontalAlignment(JButton.LEFT);
		buttonAllCategories.setSelected(true);
		panel.add(buttonAllCategories, "growx");
		group.add(buttonAllCategories);
		
		for (Constants.Category c : Constants.Category.values()) {
			WebToggleButton button = new WebToggleButton("<html><table cellpadding=0 cellspacing=0 style='width: 134px'><tr><td>" + c.getName() + "</td><td style='text-align: right; color: rgb(150, 150, 150)'>" + (int)(1 + Math.random() * 10) + "</td></tr></table></html>");
			button.setHorizontalAlignment(JButton.LEFT);
			panel.add(button, "growx");
			group.add(button);
		}
		
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

		initializeSettingsView();
		
		
		sidebarTabbedPane = new WebTabbedPane();
		sidebarTabbedPane.setFocusable(false);

		varuorgScrollPane = new JScrollPane();
		sidebarTabbedPane.addTab("Varukorg", null, varuorgScrollPane, null);

		varukorgPanel = new JPanel();
		varuorgScrollPane.setViewportView(varukorgPanel);
		sidebarTabbedPane.addTab("Favoriter", new WebLabel());
		sidebarTabbedPane.addTab("Historik", new WebLabel());
		frame.getContentPane().add(sidebarTabbedPane, "cell 3 2,grow");

		search();
	}

	private void initializeSettingsView() {
		cardPanelSettings = new JPanel();
		contentPanel.add(cardPanelSettings, "cardPanelSettings");
		cardPanelSettings
				.setLayout(new MigLayout("", "[287.00,grow][287.00,grow]", "[49.00][200.00,grow][205.00,grow][]"));

		lblInstllningarFr = new JLabel("Inst\u00E4llningar f\u00F6r ");
		lblInstllningarFr.setFont(new Font("Tahoma", Font.PLAIN, 16));
		cardPanelSettings.add(lblInstllningarFr, "cell 0 0");

		SignInSettingsPanel = new JPanel();
		SignInSettingsPanel.setBorder(new TitledBorder(null,
				"Inloggningsuppgifter", TitledBorder.LEADING, TitledBorder.TOP,
				null, null));
		cardPanelSettings.add(SignInSettingsPanel, "cell 0 1,grow");
		SignInSettingsPanel.setLayout(new MigLayout("", "[96px][grow]",
				"[32px][32px][32px]"));

		lblEmail = new JLabel("E-post");
		SignInSettingsPanel.add(lblEmail, "cell 0 0,alignx left");

		textField = new JTextField();
		SignInSettingsPanel.add(textField, "cell 1 0,growx");
		textField.setColumns(10);

		lblNewPassword = new JLabel("Nytt L\u00F6senord");
		SignInSettingsPanel.add(lblNewPassword, "cell 0 1,alignx left");

		passwordField = new JPasswordField();
		SignInSettingsPanel.add(passwordField, "cell 1 1,growx");

		lblRepeatPassword = new JLabel("(igen)");
		SignInSettingsPanel.add(lblRepeatPassword, "cell 0 2,alignx left");

		passwordFieldRepeat = new JPasswordField();
		SignInSettingsPanel.add(passwordFieldRepeat, "cell 1 2,growx");
		
		cardSettingsPanel = new CardSettingsPanel();
		cardPanelSettings.add(cardSettingsPanel, "cell 1 1,grow");
		
		addressSettingsPanel = new AddressSettingsPanel();
		cardPanelSettings.add(addressSettingsPanel, "cell 0 2,grow");

		btnNewButton = new JButton("New button");
		cardPanelSettings.add(btnNewButton,
				"cell 1 3,alignx right,aligny bottom");
		
		confirmPurchasePanel = new JPanel();
		contentPanel.add(confirmPurchasePanel, "cardConfrimPanel");
		confirmPurchasePanel.setLayout(new MigLayout("", "[][grow]", "[grow][grow][]"));
		
		cartConfirmationPanel = new JPanel();
		confirmPurchasePanel.add(cartConfirmationPanel, "cell 0 0 2 1,grow");
		
		addressSettingsPanel_1 = new AddressSettingsPanel();
		confirmPurchasePanel.add(addressSettingsPanel_1, "cell 0 1,grow");
		
		cardSettingsPanel_1 = new CardSettingsPanel();
		confirmPurchasePanel.add(cardSettingsPanel_1, "cell 1 1,grow");
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

		if (action.getSource() == toggleGridViewButton) {
			CardLayout cl = (CardLayout) (contentPanel.getLayout());
			cl.show(contentPanel, "cardPanelGrid");
			calculateResults(contentScrollPane.getWidth(),
					contentScrollPane.getHeight(),numResults);
		}

		if (action.getSource() == toggleListViewButton) {
			CardLayout cl = (CardLayout) (contentPanel.getLayout());
			cl.show(contentPanel, "cardPanelList");
			contentPanel.setPreferredSize(cardPanelList.getPreferredSize());
		}

		if (action.getSource() == searchTimer) {
			search();
		}

		if (action.getSource() == userComboBox) {
			if (userComboBox.getSelectedIndex() == 1) {
				CardLayout cl = (CardLayout) (contentPanel.getLayout());
				cl.show(contentPanel, "cardPanelSettings");
				contentPanel.setPreferredSize(cardPanelSettings
						.getPreferredSize());
			}

		}
	}

	private void search() {
		cardPanelGrid.removeAll();
		cardPanelList.removeAll();
		
		
		String text = txtSearchBox.getText();

		List<Product> results = db.findProducts(text);
		numResults = results.size();
		for (int i = 0; i < results.size(); i++) {
			ItemGrid itemGrid = new ItemGrid();
			itemGrid.setName(results.get(i).getName());
			//itemGrid.setIcon(results)
			cardPanelGrid.add(itemGrid);
			
			ItemList item = new ItemList();
			
			if (i % 2 == 1) {
				item.setBackground(new Color(248, 248, 248));
			}

			cardPanelList.add(item);
		}

		calculateResults(contentScrollPane.getWidth(), contentScrollPane.getHeight(),numResults);
		cardPanelGrid.revalidate();
		cardPanelList.revalidate();
		cardPanelGrid.repaint();
		cardPanelList.repaint();
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
}
