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

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

import net.miginfocom.swing.MigLayout;

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
	private JToggleButton tglbtnBageri;
	private JToggleButton tglbtnBarn;
	private JToggleButton tglbtnBlommor;
	private JToggleButton tglbtnDryck;
	private JToggleButton tglbtnFiskSkaldjur;
	private JToggleButton tglbtnAllaKategorier;
	private JScrollPane contentScrollPane;
	private JPanel contentPanel;
	private int margin = 8;
	private WebTabbedPane sidebarTabbedPane;

	private WebToggleButton toggleGridViewButton;
	private WebToggleButton toggleListViewButton;
	private WebButtonGroup toggleViewButtonGroup;
	private JPanel cardPanelList;
	private JPanel cardPanelGrid;
	private JPanel cardPanelSettings;
	private JLabel lblInstllningarFr;
	private JPanel SignInSettingsPanel;
	private JPanel PaymentSettingsPanel;
	private JPanel addresSettingsPanel;
	private JTextField textField;
	private JLabel lblEmail;
	private JLabel lblNewPassword;
	private JLabel lblRepeatPassword;
	private JTextField FirstNameTextField;
	private JTextField LastNameTextField;
	private JTextField streetAddressTextField;
	private JTextField cityTextField;
	private JTextField phoneTextField;
	private JTextField cellPhoneTextField;
	private JLabel lblFirstName;
	private JLabel lblLastName;
	private JLabel lblStreetAddress;
	private JLabel lblPostNr;
	private JTextField zipCodeTextField;
	private JPanel savedCardsPanel;
	private JLabel lblPhone;
	private JLabel lblCellPhone;
	private JSeparator separator;
	private JSeparator separator_1;
	private JPanel addCardsPanel;
	private JTextField cvcTextField;
	private JLabel lblCvc;
	private WebComboBox yearComboBox;
	private WebComboBox monthComboBox;
	private JLabel dividerLabel;
	private JLabel lblUtgdatum;
	private JLabel lblCardNum;
	private JTextField textField_11;
	private JPasswordField passwordField;
	private JPasswordField passwordFieldRepeat;
	private WebComboBox webComboBox;
	private JButton btnTaBort;
	private JButton btnNewButton;
	private JScrollPane varuorgScrollPane;
	private JPanel varukorgPanel;

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
		txtSearchBox.setText("Potatisgrat\u00E4ng");
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
		panel.setPreferredSize(new Dimension(64, 28));
		categoriesScrollPane.setViewportView(panel);
		panel.setLayout(new MigLayout("insets 0", "[grow]",
				"[bottom][28px][28px][28px][28px][28px]"));

		tglbtnAllaKategorier = new JToggleButton("Alla kategorier");
		tglbtnAllaKategorier.setPreferredSize(new Dimension(64, 28));
		tglbtnAllaKategorier.setSelected(true);
		panel.add(tglbtnAllaKategorier, "flowy,cell 0 0,growx");

		tglbtnBageri = new JToggleButton("Bageri");
		tglbtnBageri.setPreferredSize(new Dimension(64, 28));
		panel.add(tglbtnBageri, "cell 0 1,growx");

		tglbtnBarn = new JToggleButton("Barn");
		tglbtnBarn.setPreferredSize(new Dimension(64, 28));
		panel.add(tglbtnBarn, "cell 0 2,growx");

		tglbtnBlommor = new JToggleButton("Blommor");
		tglbtnBlommor.setPreferredSize(new Dimension(64, 28));
		panel.add(tglbtnBlommor, "cell 0 3,growx");

		tglbtnDryck = new JToggleButton("Dryck");
		tglbtnDryck.setPreferredSize(new Dimension(64, 28));
		panel.add(tglbtnDryck, "cell 0 4,growx");

		tglbtnFiskSkaldjur = new JToggleButton("Fisk & Skaldjur");
		tglbtnFiskSkaldjur.setPreferredSize(new Dimension(64, 28));
		panel.add(tglbtnFiskSkaldjur, "cell 0 5,growx");

		ButtonGroup group = new ButtonGroup();
		group.add(tglbtnAllaKategorier);
		group.add(tglbtnBageri);
		group.add(tglbtnBarn);
		group.add(tglbtnBlommor);
		group.add(tglbtnDryck);
		group.add(tglbtnFiskSkaldjur);

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
		cardPanelList.setLayout(new BoxLayout(cardPanelList, BoxLayout.Y_AXIS));
		contentPanel.add(cardPanelList, "cardPanelList");

		cardPanelSettings = new JPanel();
		contentPanel.add(cardPanelSettings, "cardPanelSettings");
		cardPanelSettings
				.setLayout(new MigLayout("", "[287.00,grow][287.00,grow]",
						"[49.00][260.00][205.00,grow][]"));

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

		PaymentSettingsPanel = new JPanel();
		PaymentSettingsPanel.setBorder(new TitledBorder(null,
				"Betalningsuppgifter", TitledBorder.LEADING, TitledBorder.TOP,
				null, null));
		cardPanelSettings.add(PaymentSettingsPanel, "cell 1 1,grow");
		PaymentSettingsPanel.setLayout(new MigLayout("", "[grow]",
				"[83.00][197.00,grow]"));

		savedCardsPanel = new JPanel();
		savedCardsPanel.setBorder(new TitledBorder(null, "Sparade kort",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		PaymentSettingsPanel.add(savedCardsPanel, "cell 0 0,grow");
		savedCardsPanel.setLayout(new MigLayout("", "[grow][]", "[grow]"));

		webComboBox = new WebComboBox();
		savedCardsPanel.add(webComboBox, "cell 0 0,growx");

		btnTaBort = new JButton("Ta bort");
		savedCardsPanel.add(btnTaBort, "cell 1 0");

		addCardsPanel = new JPanel();
		addCardsPanel.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "L\u00E4gg till kort",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		PaymentSettingsPanel.add(addCardsPanel, "cell 0 1,grow");
		addCardsPanel.setLayout(new MigLayout("",
				"[][42px][][42px][grow][48px]", "[32px][32px]"));

		lblCardNum = new JLabel("Kortnr");
		addCardsPanel.add(lblCardNum, "cell 0 0,alignx left");

		textField_11 = new JTextField();
		addCardsPanel.add(textField_11, "cell 1 0 5 1,growx");
		textField_11.setColumns(10);

		lblUtgdatum = new JLabel("Utg.datum");
		addCardsPanel.add(lblUtgdatum, "cell 0 1,alignx left");

		monthComboBox = new WebComboBox();
		monthComboBox.setModel(new DefaultComboBoxModel(new String[] { "MM",
				"01", "02", "03", "04", "05", "06", "07", "08", "09", "10",
				"11", "12" }));
		addCardsPanel.add(monthComboBox, "cell 1 1,growx");

		dividerLabel = new JLabel("/");
		addCardsPanel.add(dividerLabel, "cell 2 1,alignx trailing");

		yearComboBox = new WebComboBox();
		yearComboBox.setModel(new DefaultComboBoxModel(new String[] {
				"\u00C5\u00C5", "14", "15", "16", "17", "18", "19", "20", "21",
				"22", "23", "24", "25", "26", "27", "28", "29" }));
		addCardsPanel.add(yearComboBox, "cell 3 1,growx");

		lblCvc = new JLabel("CVC");
		addCardsPanel.add(lblCvc, "cell 4 1,alignx trailing");

		cvcTextField = new JTextField();
		addCardsPanel.add(cvcTextField, "cell 5 1,growx");
		cvcTextField.setColumns(10);

		addresSettingsPanel = new JPanel();
		addresSettingsPanel.setBorder(new TitledBorder(null, "Adressuppgifter",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		cardPanelSettings.add(addresSettingsPanel, "cell 0 2,grow");
		addresSettingsPanel.setLayout(new MigLayout("", "[92px][52px][grow]",
				"[32px][32px][][32px][32px][][32px][32px]"));

		lblFirstName = new JLabel("F\u00F6rnamn");
		addresSettingsPanel.add(lblFirstName, "cell 0 0,alignx left");

		FirstNameTextField = new JTextField();
		addresSettingsPanel.add(FirstNameTextField, "cell 1 0 2 1,growx");
		FirstNameTextField.setColumns(10);

		lblLastName = new JLabel("Efternamn");
		addresSettingsPanel.add(lblLastName, "cell 0 1,alignx left");

		LastNameTextField = new JTextField();
		addresSettingsPanel.add(LastNameTextField, "cell 1 1 2 1,growx");
		LastNameTextField.setColumns(10);

		separator = new JSeparator();
		addresSettingsPanel.add(separator, "cell 0 2 3 1,grow");

		lblStreetAddress = new JLabel("Gatuadress");
		addresSettingsPanel.add(lblStreetAddress, "cell 0 3,alignx left");

		streetAddressTextField = new JTextField();
		addresSettingsPanel.add(streetAddressTextField, "cell 1 3 2 1,growx");
		streetAddressTextField.setColumns(10);

		lblPostNr = new JLabel("Postnr/Postort");
		addresSettingsPanel.add(lblPostNr, "cell 0 4,alignx left");

		zipCodeTextField = new JTextField();
		addresSettingsPanel.add(zipCodeTextField, "cell 1 4,growx");
		zipCodeTextField.setColumns(10);

		cityTextField = new JTextField();
		addresSettingsPanel.add(cityTextField, "cell 2 4,growx");
		cityTextField.setColumns(10);

		separator_1 = new JSeparator();
		addresSettingsPanel.add(separator_1, "cell 0 5 3 1,grow");

		lblPhone = new JLabel("Tel (inkl riktnr.)");
		addresSettingsPanel.add(lblPhone, "cell 0 6");

		phoneTextField = new JTextField();
		addresSettingsPanel.add(phoneTextField, "cell 2 6,growx");
		phoneTextField.setColumns(10);

		lblCellPhone = new JLabel("Mobiltelefon");
		addresSettingsPanel.add(lblCellPhone, "cell 0 7");

		cellPhoneTextField = new JTextField();
		addresSettingsPanel.add(cellPhoneTextField, "cell 2 7,growx");
		cellPhoneTextField.setColumns(10);

		btnNewButton = new JButton("New button");
		cardPanelSettings.add(btnNewButton,
				"cell 1 3,alignx right,aligny bottom");

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

	private void calculateResults(int width, int height) {
		if (toggleGridViewButton.isSelected()) {
			int cols = width / (128 + margin);
			System.out.print("cols:" + cols);
			int rows = 25 / cols;
			rows += ((rows * cols < 25) ? 1 : 0);
			System.out.println("rows:" + rows);

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
					contentScrollPane.getHeight());
		}

		if (action.getSource() == toggleListViewButton) {
			CardLayout cl = (CardLayout) (contentPanel.getLayout());
			cl.show(contentPanel, "cardPanelList");
			contentPanel.setPreferredSize(cardPanelList.getPreferredSize());
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
		String text = txtSearchBox.getText();

		cardPanelGrid.removeAll();
		cardPanelList.removeAll();

		for (int i = 0; i < 25; i++) {

			cardPanelGrid.add(new ItemGrid(text + " " + i));
			ItemList item = new ItemList(text + " " + i);

			if (i % 2 == 1) {
				item.setBackground(new Color(248, 248, 248));
			}

			cardPanelList.add(item);
		}

		cardPanelGrid.revalidate();
		cardPanelList.revalidate();

	}

	private JPanel getCart() {
		return varukorgPanel;
	}

	private class TxtSearchBoxKeyListener extends KeyAdapter {
		@Override
		public void keyReleased(KeyEvent keyEvent) {
			search();
		}
	}
}
