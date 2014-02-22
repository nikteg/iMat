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
import com.alee.laf.button.WebButton;
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
		frame.getContentPane().setLayout(new MigLayout("insets 4px", "[192px:n][grow][72px][212px:212px]", "[][][grow]"));
		
		lblImat = new JLabel();
		lblImat.setIcon(new ImageIcon(MainWindow.class.getResource("/resources/logo.png")));
		lblImat.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(lblImat, "cell 0 0 1 2,growx,aligny center");
		
		txtSearchBox = new JTextField();
		txtSearchBox.addKeyListener(new TxtSearchBoxKeyListener());
		txtSearchBox.setText("Potatisgrat\u00E4ng");
		txtSearchBox.setFont(new Font("Lucida Grande", Font.PLAIN, 24));
		frame.getContentPane().add(txtSearchBox, "cell 1 1,grow");
		txtSearchBox.setColumns(10);

        toggleGridViewButton = new WebToggleButton(new ImageIcon(MainWindow.class.getResource("/resources/icons/application_view_tile.png")));
        toggleListViewButton = new WebToggleButton(new ImageIcon(MainWindow.class.getResource("/resources/icons/application_view_list.png")));
        toggleViewButtonGroup = new WebButtonGroup(true, toggleGridViewButton, toggleListViewButton);
        toggleViewButtonGroup.setButtonsMargin(10);
        toggleViewButtonGroup.setButtonsDrawFocus(false);
        
        toggleGridViewButton.addActionListener(this);
        toggleListViewButton.addActionListener(this);
        
        toggleGridViewButton.setSelected(true);
		
		frame.getContentPane().add(toggleViewButtonGroup, "cell 2 1,grow");
		
		userComboBox = new WebComboBox();
		userComboBox.setModel(new DefaultComboBoxModel(new String[] {"Mikael L\u00F6nn", "Kontoinst\u00E4llningar", "Orderhistorik", "Logga ut"}));
		userComboBox.setFocusable(false);
		frame.getContentPane().add(userComboBox, "cell 3 1,grow");
		
		categoriesScrollPane = new JScrollPane();
		categoriesScrollPane.setBorder(null);
		frame.getContentPane().add(categoriesScrollPane, "cell 0 2,grow");
		
		panel = new JPanel();
		categoriesScrollPane.setViewportView(panel);
		panel.setLayout(new MigLayout("flowy,insets 0", "[grow]", "[28px]"));
		
		ButtonGroup group = new ButtonGroup();
		
		WebToggleButton buttonAllCategories = new WebToggleButton("Alla kategorier");
		panel.add(buttonAllCategories, "growx");
		group.add(buttonAllCategories);
		
		for (Constants.Category c : Constants.Category.values()) {
			WebToggleButton button = new WebToggleButton(c.getName());
			panel.add(button, "growx");
			group.add(button);
		}
		
		contentScrollPane = new JScrollPane();
		contentScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		contentScrollPane.setViewportBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		contentScrollPane.setBackground(Color.WHITE);
		contentScrollPane.setBorder(null);
		contentScrollPane.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent arg0) {
				int width = ((JScrollPane)arg0.getSource()).getWidth();
				int height = ((JScrollPane)arg0.getSource()).getHeight();
				
				calculateResults(width, height);
			}
		});
		contentScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		frame.getContentPane().add(contentScrollPane, "cell 1 2 2 1,grow");
		
		contentPanel = new JPanel();
		contentPanel.setBorder(null);
		contentPanel.setBackground(Color.WHITE);
		contentScrollPane.setViewportView(contentPanel);
		contentPanel.setLayout(new CardLayout(0, 0));
		
		cardPanelGrid = new JPanel();
		contentPanel.add(cardPanelGrid, "cardPanelGrid");
		cardPanelGrid.setLayout(new FlowLayout(FlowLayout.LEFT, margin, margin));
		
		cardPanelList = new JPanel();
		cardPanelList.setLayout(new BoxLayout(cardPanelList, BoxLayout.Y_AXIS));
		contentPanel.add(cardPanelList, "cardPanelList");
		
		sidebarTabbedPane = new WebTabbedPane();
		sidebarTabbedPane.setFocusable(false);
		sidebarTabbedPane.addTab("Varukorg", new WebLabel());
		sidebarTabbedPane.addTab("Favoriter", new WebLabel());
		sidebarTabbedPane.addTab("Historik", new WebLabel());
		frame.getContentPane().add(sidebarTabbedPane, "cell 3 2,grow");
	
		search();
	}
	
	private void calculateResults(int width, int height) {
		if (toggleGridViewButton.isSelected()) {
			int cols = width/(128+margin); System.out.print("cols:" + cols);
			int rows = 25 / cols;
			rows += ((rows * cols < 25) ? 1 : 0); System.out.println("rows:" + rows);
			
			contentPanel.setPreferredSize(new Dimension(width - 32, (rows * (160 + margin)) + margin));
			contentScrollPane.revalidate();
		}
	}

	@Override
	public void actionPerformed(ActionEvent action) {
		if (action.getSource() == toggleGridViewButton) {
			CardLayout cl = (CardLayout)(contentPanel.getLayout());
			cl.show(contentPanel, "cardPanelGrid");
			calculateResults(contentScrollPane.getWidth(), contentScrollPane.getHeight());
		}
		
		if (action.getSource() == toggleListViewButton) {
			CardLayout cl = (CardLayout)(contentPanel.getLayout());
			cl.show(contentPanel, "cardPanelList");
			contentPanel.setPreferredSize(cardPanelList.getPreferredSize());
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
	
	private class TxtSearchBoxKeyListener extends KeyAdapter {
		@Override
		public void keyReleased(KeyEvent keyEvent) {
			search();
		}
	}
}
