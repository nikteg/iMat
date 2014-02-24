import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import se.chalmers.ait.dat215.project.Product;
import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class ItemGrid extends Item {
	private JLabel lblBild;
	private JButton btnKp;
	private JSeparator separator;
	private JLabel lblNameLabel;

	/**
	 * Create the panel.
	 */
	public ItemGrid(Product product, MainWindow parent) {
		super(product, parent);
		initialize();
	}

	private void initialize() {
		setBackground(new Color(248, 248, 248));
		setPreferredSize(new Dimension(128, 160));
		setLayout(new MigLayout("insets 4px", "[grow][]", "[87.00,grow][][]"));

		lblBild = new JLabel(parent.getModel().getImageIcon(product, new Dimension(120, 120)));
		lblBild.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblBild, "cell 0 0 2 1,grow");

		separator = new JSeparator();
		add(separator, "cell 0 1 2 1,growx,aligny center");

		lblNameLabel = new JLabel(product.getName());
		add(lblNameLabel, "cell 0 2,alignx center");

		btnKp = new JButton("K\u00F6p");
		btnKp.addActionListener(this);
		btnKp.setActionCommand("add_cart");
		add(btnKp, "cell 1 2,alignx right,aligny center");
	}

}
