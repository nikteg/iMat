import javax.swing.JPanel;
import java.awt.Dimension;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.border.EtchedBorder;
import javax.swing.JSeparator;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class Item extends JPanel {
	private JLabel lblBild;
	private JButton btnKp;
	private JSeparator separator;
	private MainWindow parent;

	/**
	 * Create the panel.
	 */
	public Item() {

		initialize();
	}
	
	public Item(String name) {
		this();
		
		lblBild.setText(name);
	}
	
	public void setParent(MainWindow parent) {
		this.parent = parent;
	}
	
	private void initialize() {
		setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		setPreferredSize(new Dimension(128, 160));
		setLayout(new MigLayout("insets 4px", "[grow]", "[grow][][]"));
		
		lblBild = new JLabel("BILD");
		lblBild.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblBild, "cell 0 0,grow");
		
		separator = new JSeparator();
		add(separator, "cell 0 1,growx,aligny center");
		
		btnKp = new JButton("K\u00F6p");
		btnKp.addActionListener(new BtnKpActionListener());
		add(btnKp, "cell 0 2,alignx right,aligny center");
	}

	private class BtnKpActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			if (arg0.getSource().equals(btnKp)) {
				parent.addToCart();
			}
		}
	}
}
