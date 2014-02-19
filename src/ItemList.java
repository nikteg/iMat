import java.awt.LayoutManager;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.border.EtchedBorder;
import javax.swing.JButton;
import javax.swing.JSpinner;


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
		setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		setPreferredSize(new Dimension(582, 64));
		setLayout(new MigLayout("", "[64px:64.00][5px:5px][92px:92px][240.00px,grow][48][64px]", "[64px]"));
		
		lblBild = new JLabel("BILD");
		lblBild.setHorizontalTextPosition(SwingConstants.RIGHT);
		lblBild.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblBild, "cell 0 0,alignx center");
		
		separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		add(separator, "cell 1 0,growy");
		
		lblNamelabel = new JLabel("nameLabel");
		add(lblNamelabel, "cell 2 0,alignx center");
		
		lblPricelabel = new JLabel("priceLabel");
		lblPricelabel.setMaximumSize(new Dimension(13337, 14));
		add(lblPricelabel, "cell 3 0");
		
		spinner = new JSpinner();
		spinner.setPreferredSize(new Dimension(48, 20));
		add(spinner, "cell 4 0,alignx right");
		
		btnKp = new JButton("K\u00F6p");
		add(btnKp, "cell 5 0,alignx center");
	}
}
