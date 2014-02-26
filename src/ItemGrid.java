import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import se.chalmers.ait.dat215.project.Product;
import se.chalmers.ait.dat215.project.ShoppingItem;
import net.miginfocom.swing.MigLayout;

import javax.swing.JSpinner;
import javax.swing.JToggleButton;
import javax.swing.ImageIcon;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.ButtonUI;
import javax.swing.SpinnerNumberModel;

import com.alee.laf.spinner.WebSpinner;

@SuppressWarnings("serial")
public class ItemGrid extends Item implements ChangeListener{
	private JLabel lblBild;
	private JButton btnKp;
	private JLabel lblName;
	private JLabel lblPrice;
	private WebSpinner spinner;
	private JLabel lblKg;
	public JToggleButton tglFavorite;

	/**
	 * Create the panel.
	 */
	public ItemGrid(ShoppingItem shoppingItem, MainWindow parent) {
		super(shoppingItem, parent);
		initialize();
	}

	private void initialize() {
		setBackground(new Color(248, 248, 248));
		setPreferredSize(new Dimension(180, 240));
		setLayout(new MigLayout("insets 8px", "[grow][grow][][]", "[164px:164px][26px:26px][26px:26px][]"));

		lblBild = new JLabel(parent.getModel().getImageIcon(shoppingItem.getProduct(), new Dimension(164, 164)));
		lblBild.setPreferredSize(new Dimension(164, 164));
		lblBild.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblBild, "cell 0 0 4 1,growx,aligny top");

		btnKp = new JButton("K\u00F6p");
		btnKp.addActionListener(this);
		
		lblName = new JLabel(shoppingItem.getProduct().getName());
		add(lblName, "flowx,cell 0 1 3 1,alignx left,aligny center");
		
		lblPrice = new JLabel(shoppingItem.getProduct().getPrice() + ":-");
		add(lblPrice, "cell 0 2,alignx left,aligny center");
		
		spinner = new WebSpinner();
		spinner.setDrawFocus(false);
		spinner.setPreferredSize(new Dimension(32, 20));
		spinner.addChangeListener(this);
		spinner.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		add(spinner, "cell 1 2,grow");
		
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
	
	@Override
	public void stateChanged(ChangeEvent event) {
		if (event.getSource() == spinner) {
			shoppingItem.setAmount(((Integer)spinner.getValue()).doubleValue());
			lblPrice.setText(shoppingItem.getTotal() + ":-");
		}
	}
	

	@Override
	public double getAmount() {
		return ((Integer)spinner.getValue()).doubleValue();
	}


}
