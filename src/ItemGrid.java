import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import net.miginfocom.swing.MigLayout;


@SuppressWarnings("serial")
public class ItemGrid extends JPanel {
	private JLabel lblBild;
	private JButton btnKp;
	private JSeparator separator;
	private MainWindow parent;

	/**
	 * Create the panel.
	 */
	public ItemGrid() {
		initialize();
	}
	
	public ItemGrid(String name) {
		this();
		
		lblBild.setText(name);
	}
	
	public void setParent(MainWindow parent) {
		this.parent = parent;
	}
	
	private void initialize() {
		setBackground(new Color(248, 248, 248));
		setPreferredSize(new Dimension(128, 160));
		setLayout(new MigLayout("insets 4px", "[grow]", "[grow][][]"));
		
		lblBild = new JLabel("BILD");
		lblBild.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblBild, "cell 0 0,grow");
		
		separator = new JSeparator();
		add(separator, "cell 0 1,growx,aligny center");
		
		btnKp = new JButton("K\u00F6p");
		add(btnKp, "cell 0 2,alignx right,aligny center");
	}

	
}
