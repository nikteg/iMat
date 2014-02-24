import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.JButton;
import javax.swing.JSpinner;

import java.awt.Color;

import javax.swing.ImageIcon;


@SuppressWarnings("serial")
public class ItemList extends JPanel {
	private JLabel lblBild;
	private JSeparator separator;
	private JButton btnKp;
	private JLabel lblNamelabel;
	private JSpinner spinner;
	private JLabel lblPricelabel;

	public ItemList() {
		// TODO Auto-generated constructor stub
		initialize();
	}
	
	public ItemList(String name) {
		this();
		
		lblBild.setText(name);
	}
	
	private void initialize() {
		setPreferredSize(new Dimension(512, 77));
		//setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		setLayout(new MigLayout("", "[64px:64.00][5px:5px][92px:92px][grow][48px:48px][64px:64px]", "[64px]"));
		
		lblBild = new JLabel("");
		lblBild.setIcon(new ImageIcon(ItemList.class.getResource("/resources/icons/zoom.png")));
		lblBild.setHorizontalTextPosition(SwingConstants.RIGHT);
		lblBild.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblBild, "cell 0 0,alignx center,aligny center");
		
		separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		add(separator, "cell 1 0,growy");
		
		lblNamelabel = new JLabel("nameLabel");
		add(lblNamelabel, "cell 2 0,alignx center");
		
		lblPricelabel = new JLabel("priceLabel");
		lblPricelabel.setBackground(Color.RED);
		add(lblPricelabel, "cell 3 0");
		
		spinner = new JSpinner();
		add(spinner, "cell 4 0,alignx right");
		
		btnKp = new JButton("K\u00F6p");
		add(btnKp, "cell 5 0,alignx center");
	}
}
