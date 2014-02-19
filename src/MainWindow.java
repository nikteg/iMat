import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import net.miginfocom.swing.MigLayout;

import com.alee.laf.WebLookAndFeel;
import javax.swing.JTabbedPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class MainWindow {

	private JFrame frame;
	private JTextField txtPotatisgrattng;
	private JLabel lblImat;
	private JScrollPane scrollPane;
	private JComboBox comboBox;
	private JPanel panel;
	private JToggleButton tglbtnBageri;
	private JToggleButton tglbtnBarn;
	private JToggleButton tglbtnBlommor;
	private JToggleButton tglbtnDryck;
	private JToggleButton tglbtnFiskSkaldjur;
	private JToggleButton tglbtnAllaKategorier;
	private JScrollPane scrollPane_1;
	private JPanel panel_1;
	private JPanel panelItem;
	private JLabel lblBild;
	private JButton btnNewButton_1;
	private JSeparator separator;
	private int margin = 24;
	private int cartItems = 0;
	private JScrollPane scrollPane_2;
	private JTabbedPane tabbedPane;
	private JLabel lblGridList;
	private boolean gridOrList;

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
		WebLookAndFeel.install();
		
		initialize();
	}
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1050, 768);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("insets 4px", "[192px:n][grow][72px][192px:192px]", "[][][grow]"));
		
		lblImat = new JLabel();
		lblImat.setIcon(new ImageIcon(MainWindow.class.getResource("/resources/logo.png")));
		lblImat.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(lblImat, "cell 0 0 1 2,growx,aligny center");
		
		txtPotatisgrattng = new JTextField();
		txtPotatisgrattng.setText("Potatisgrat\u00E4ng");
		txtPotatisgrattng.setFont(new Font("Lucida Grande", Font.PLAIN, 24));
		frame.getContentPane().add(txtPotatisgrattng, "cell 1 1,grow");
		txtPotatisgrattng.setColumns(10);
		
		lblGridList = new JLabel("");
		lblGridList.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
				panel_1.removeAll();
				if (gridOrList) {
					for (int i = 0; i < 100; i++) {
						ItemGrid item =  new ItemGrid("" + (i+1));
						panel_1.add(item);
						
					}
					int width = scrollPane_1.getWidth();
					int height = scrollPane_1.getHeight();
					calculateResults(width, height);
					panel_1.revalidate();
					gridOrList = false;
					lblGridList.setIcon(new ImageIcon(MainWindow.class.getResource("/resources/icons/list-icon.png")));
				} else if (!gridOrList){
					for (int i = 0; i < 100; i++) {
						ItemList item =  new ItemList("" + (i+1));
						panel_1.add(item);
					}
					int width = scrollPane_1.getWidth();
					int height = scrollPane_1.getHeight();
					calculateResults(width, height);
					panel_1.revalidate();
					gridOrList = true;
					lblGridList.setIcon(new ImageIcon(MainWindow.class.getResource("/resources/icons/grid-icon.png")));

				}
				
				
			}
		});
		lblGridList.setIcon(new ImageIcon(MainWindow.class.getResource("/resources/icons/list-icon.png")));
		frame.getContentPane().add(lblGridList, "cell 2 1,alignx trailing");
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Mikael L\u00F6nn", "Kontoinst\u00E4llningar", "Orderhistorik", "Logga ut"}));
		frame.getContentPane().add(comboBox, "cell 3 1,grow");
		
		scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		frame.getContentPane().add(scrollPane, "cell 0 2,grow");
		
		panel = new JPanel();
		panel.setPreferredSize(new Dimension(64, 28));
		scrollPane.setViewportView(panel);
		panel.setLayout(new MigLayout("insets 0", "[grow]", "[52px,bottom][28px][28px][28px][28px][28px]"));
		
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
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBorder(null);
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_1.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent arg0) {
				int width = ((JScrollPane)arg0.getSource()).getWidth();
				int height = ((JScrollPane)arg0.getSource()).getHeight();
				
				calculateResults(width, height);
			}
		});
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		frame.getContentPane().add(scrollPane_1, "cell 1 2 2 1,grow");
		
		panel_1 = new JPanel();
		scrollPane_1.setViewportView(panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, margin, margin));
		
		scrollPane_2 = new JScrollPane();
		frame.getContentPane().add(scrollPane_2, "cell 3 2,grow");
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		scrollPane_2.setViewportView(tabbedPane);
		
		

		
	}
	
	private void calculateResults(int width, int height) {
		if (gridOrList) {
			int cols = width/(128+margin);
			int rows = 100 / cols;
			rows += ((rows * cols < 100) ? 1 : 0);
			
			panel_1.setPreferredSize(new Dimension(width, (rows * (160 + margin)) + margin));
			scrollPane_1.revalidate();
		} else if (!gridOrList){
			panel_1.setPreferredSize(new Dimension(width, (100 * (64 + margin)) + margin));
			scrollPane_1.revalidate();
		}

	}
}
