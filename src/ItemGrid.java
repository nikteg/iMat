import java.awt.Color;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;

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
import com.alee.laf.tabbedpane.WebTabbedPane;

@SuppressWarnings("serial")
public class ItemGrid extends Item implements ChangeListener{
	private JLabel lblBild;
	private JButton btnKp;
	private JLabel lblName;
	private JLabel lblPrice;
	private WebSpinner spinner;
	public JToggleButton tglFavorite;
	private JLabel lblSuffix;
	private WebTabbedPane tabbedpane;
	private FavoriteView favoriteView;

	public ItemGrid() {
		super();
		initialize();
	}
	
	public ItemGrid(ShoppingItem shoppingItem, FavoriteView favoriteView, IMatModel model) {
		super(shoppingItem, model);
		this.tabbedpane = tabbedpane;
		this.favoriteView = favoriteView;
		initialize();
	}

	private void initialize() {
		setBackground(new Color(248, 248, 248));
		setPreferredSize(new Dimension(180, 240));
		setLayout(new MigLayout("insets 8px", "[48][48,grow][grow][][]", "[164px:164px][26px:26px][26px:26px][]"));

		lblBild = new JLabel(model.getImageIcon(shoppingItem.getProduct(), new Dimension(164, 164)));
		lblBild.setPreferredSize(new Dimension(164, 164));
		lblBild.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblBild, "cell 0 0 5 1,growx,aligny top");

		btnKp = new JButton("Lägg till");
		btnKp.setToolTipText("Lägg till produkt i varukorgen");
		btnKp.addActionListener(this);
		
		lblName = new JLabel(shoppingItem.getProduct().getName());
		add(lblName, "flowx,cell 0 1 3 1,alignx left,aligny center");
		
		lblPrice = new JLabel(shoppingItem.getProduct().getPrice() + shoppingItem.getProduct().getUnit());
		add(lblPrice, "cell 3 1 2 1,alignx right,aligny center");
		
		spinner = new WebSpinner();
		spinner.setDrawFocus(false);
		spinner.setPreferredSize(new Dimension(32, 20));
		spinner.addChangeListener(this);
		spinner.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		add(spinner, "cell 0 2,grow");
		
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
		tglFavorite.setVisible((!model.getAccount().isAnonymous()));
		tglFavorite.addActionListener(this);
		
		lblSuffix = new JLabel(shoppingItem.getProduct().getUnitSuffix());
		add(lblSuffix, "cell 1 2,alignx left");
		tglFavorite.setActionCommand("favorite");
		
		add(tglFavorite, "cell 3 2,alignx center,aligny center");
		btnKp.setActionCommand("add_cart");
		add(btnKp, "cell 4 2,alignx right,aligny center");
		
		if (model.isFavorite(shoppingItem.getProduct())) tglFavorite.setSelected(true);
		
	}
	
	@Override
	public void stateChanged(ChangeEvent event) {
		if (event.getSource() == spinner) {
			shoppingItem.setAmount(((Integer)spinner.getValue()).doubleValue());
		}
	}
	

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName() == "favorite_add") {
			if(((Product)evt.getNewValue()).equals(shoppingItem.getProduct())) {
				tglFavorite.setSelected(true);
				favoriteView.updateFavoriteView();
			}
		}
		if (evt.getPropertyName() == "favorite_remove") {
			if(((Product)evt.getNewValue()).equals(shoppingItem.getProduct())) {
				tglFavorite.setSelected(false);
				favoriteView.updateFavoriteView();
			}
		}
		
		if (evt.getPropertyName() == "account_signedin") {
			tglFavorite.setVisible(true);
		}
		
		if (evt.getPropertyName() == "account_signedup") {
			tglFavorite.setVisible(true);
		}
		
		if (evt.getPropertyName() == "account_signout") {
			tglFavorite.setVisible(false);
		}
		
		
		
	}


}
