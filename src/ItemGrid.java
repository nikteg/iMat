import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import se.chalmers.ait.dat215.project.Product;
import net.miginfocom.swing.MigLayout;

import javax.swing.JSpinner;
import javax.swing.JToggleButton;
import javax.swing.ImageIcon;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ButtonUI;

@SuppressWarnings("serial")
public class ItemGrid extends Item {
	private JLabel lblBild;
	private JButton btnKp;
	private JLabel lblName;
	private JLabel lblPrice;
	private JSpinner spinner;
	private JLabel lblKg;
	public JToggleButton tglFavorite;

	/**
	 * Create the panel.
	 */
	public ItemGrid(Product product, MainWindow parent) {
		super(product, parent);
		initialize();
	}

	private void initialize() {
		setBackground(new Color(248, 248, 248));
		setPreferredSize(new Dimension(180, 240));
		setLayout(new MigLayout("insets 8px", "[grow][grow][][]", "[164px:164px][26px:26px][26px:26px][]"));

		lblBild = new JLabel(parent.getModel().getImageIcon(product, new Dimension(164, 164)));
		lblBild.setPreferredSize(new Dimension(164, 164));
		lblBild.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblBild, "cell 0 0 4 1,growx,aligny top");

		btnKp = new JButton("K\u00F6p");
		btnKp.addActionListener(this);
		
		lblName = new JLabel(product.getName());
		add(lblName, "flowx,cell 0 1 3 1,alignx left,aligny center");
		
		lblPrice = new JLabel(product.getPrice() + ":-");
		add(lblPrice, "cell 0 2,alignx left,aligny center");
		
		spinner = new JSpinner();
		add(spinner, "cell 1 2,alignx right");
		
		lblKg = new JLabel("kg");
		add(lblKg, "cell 2 2");
		btnKp.setActionCommand("add_cart");
		add(btnKp, "cell 3 2,alignx right,aligny center");
		
		tglFavorite = new JToggleButton("");
		tglFavorite.setUI(new javax.swing.plaf.basic.BasicButtonUI());
		tglFavorite.setContentAreaFilled(false);
		tglFavorite.setBorderPainted(false);
		tglFavorite.setRolloverSelectedIcon(new ImageIcon(ItemGrid.class.getResource("/resources/icons/star.png")));
		tglFavorite.setRolloverIcon(new ImageIcon(ItemGrid.class.getResource("/resources/icons/star-inactive.png")));
		tglFavorite.setRolloverEnabled(true);
		tglFavorite.setBorder(null);
		tglFavorite.setSelectedIcon(new ImageIcon(ItemGrid.class.getResource("/resources/icons/star.png")));
		tglFavorite.setIcon(new ImageIcon(ItemGrid.class.getResource("/resources/icons/star-outline.png")));
		tglFavorite.addActionListener(this);
		tglFavorite.setActionCommand("favorite");
		add(tglFavorite, "cell 3 1,alignx right,aligny center");
	}

}
