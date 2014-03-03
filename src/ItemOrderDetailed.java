import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.miginfocom.swing.MigLayout;
import se.chalmers.ait.dat215.project.Product;
import se.chalmers.ait.dat215.project.ShoppingItem;

import com.alee.laf.spinner.WebSpinner;


public class ItemOrderDetailed extends JPanel implements ActionListener, ChangeListener{
	private JLabel lblNameLabel;
	private ShoppingItem shoppingItem;
	private IMatModel model;
	private JLabel lblPriceLabel;
	private JToggleButton tglFavorite;
	private JButton btnAddToCart;
	private JLabel labelAmount;

	public ItemOrderDetailed() {
		super();
		initialize();
	}
	
	/**
	 * Creates a CartItem instance from the given ShoppingItem.
	 * @param shoppingItem - The item to track
	 */
	public ItemOrderDetailed(ShoppingItem shoppingItem, IMatModel model){
		this();
		this.shoppingItem = shoppingItem;
		this.model = model;
		lblNameLabel.setText(shoppingItem.getProduct().getName());
		lblPriceLabel.setText(shoppingItem.getProduct().getPrice() + shoppingItem.getProduct().getUnit());
		labelAmount.setText((int)shoppingItem.getAmount() + " " + shoppingItem.getProduct().getUnitSuffix());
		tglFavorite.setSelected(model.isFavorite(shoppingItem.getProduct()));

	}
	
	private void initialize() {
		setLayout(new MigLayout("insets 4px", "[75.00,grow][][][pref:18.00px:pref]", "[60px]"));
		
		lblNameLabel = new JLabel("nameLabel");
		add(lblNameLabel, "cell 0 0,alignx left,aligny center");
		
		labelAmount = new JLabel();
		labelAmount.setPreferredSize(new Dimension(48, 10));
		add(labelAmount, "cell 1 0,alignx left,aligny center");
		
		
		lblPriceLabel = new JLabel("20:-");
		add(lblPriceLabel, "cell 2 0,alignx right,aligny center");
		
		btnAddToCart = new JButton("");
		btnAddToCart.setPreferredSize(new Dimension(18, 18));
		btnAddToCart.setUI(new javax.swing.plaf.basic.BasicButtonUI());
		btnAddToCart.setContentAreaFilled(false);
		btnAddToCart.setBorderPainted(false);
		btnAddToCart.setIcon(new ImageIcon(CartItem.class.getResource("/resources/icons/cart.png")));
		btnAddToCart.addActionListener(this);
		btnAddToCart.setMinimumSize(new Dimension(0, 0));
		add(btnAddToCart, "cell 2 0");
				
		
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
		
		add(tglFavorite, "cell 3 0,alignx right,aligny center");
	}
	
	

	@Override
	public void actionPerformed(ActionEvent action) {
		if (action.getActionCommand() == "favorite") {
			
			if (model.isFavorite(shoppingItem.getProduct())) {
				model.favoriteRemove(shoppingItem.getProduct());
			} else {
				model.favoriteAdd(shoppingItem.getProduct());
			}
 		}
		
		if (action.getSource() == btnAddToCart) {
			model.cartAddItem(shoppingItem.getProduct(), shoppingItem.getAmount());
		}
		
	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	





	

	



	

}
