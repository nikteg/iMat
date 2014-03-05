import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import javax.swing.JLabel;
import javax.swing.JToggleButton;
import javax.swing.ImageIcon;





import javax.swing.SpinnerNumberModel;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.alee.laf.spinner.WebSpinner;

import javax.swing.JButton;

import se.chalmers.ait.dat215.project.Product;

import java.awt.Color;


public class FavoriteItem extends JPanel implements ActionListener, PropertyChangeListener{
	private JLabel lblNameLabel;
	private Product product;
	private IMatModel model;
	private JLabel lblPriceLabel;
	private JToggleButton tglFavorite;
	private JButton btnAddToCart;

	public FavoriteItem() {
		super();
		initialize();
	}
	
	/**
	 * Creates a CartItem instance from the given ShoppingItem.
	 * @param shoppingItem - The item to track
	 */
	public FavoriteItem(Product product, IMatModel model){
		this();
		this.product = product;
		this.model = model;
		model.addPropertyChangeListener(this);
		lblNameLabel.setText(product.getName());
		lblPriceLabel.setText(product.getPrice() + product.getUnit());
	}
	
	private void initialize() {
		setLayout(new MigLayout("insets 4px", "[grow][64px][][pref:18.00px:pref]", "[60px]"));
		
		lblNameLabel = new JLabel("nameLabel");
		add(lblNameLabel, "cell 0 0,alignx left,aligny center");
		

		
		lblPriceLabel = new JLabel("20:-");
		add(lblPriceLabel, "cell 1 0,alignx right,aligny center");
		btnAddToCart = new JButton("");

		btnAddToCart.setUI(new javax.swing.plaf.basic.BasicButtonUI());
		btnAddToCart.setContentAreaFilled(false);
		btnAddToCart.setBorderPainted(false);
		btnAddToCart.setIcon(new ImageIcon(CartItem.class.getResource("/resources/icons/cart.png")));
		btnAddToCart.addActionListener(this);
		btnAddToCart.setMinimumSize(new Dimension(0, 0));
		btnAddToCart.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		add(btnAddToCart, "cell 2 0");
				
		
		tglFavorite = new JToggleButton("");
		tglFavorite.setPressedIcon(new ImageIcon(FavoriteItem.class.getResource("/resources/icons/star2.png")));
		tglFavorite.setUI(new javax.swing.plaf.basic.BasicButtonUI());
		tglFavorite.setContentAreaFilled(false);
		tglFavorite.setBorderPainted(false);
		tglFavorite.setRolloverSelectedIcon(new ImageIcon(FavoriteItem.class.getResource("/resources/icons/star.png")));
		tglFavorite.setRolloverIcon(new ImageIcon(ItemGrid.class.getResource("/resources/icons/star-inactive.png")));
		tglFavorite.setRolloverEnabled(true);
		tglFavorite.setBorder(null);
		tglFavorite.setSelectedIcon(new ImageIcon(FavoriteItem.class.getResource("/resources/icons/star.png")));
		tglFavorite.setIcon(new ImageIcon(ItemGrid.class.getResource("/resources/icons/star-outline.png")));
		tglFavorite.addActionListener(this);
		tglFavorite.setSelected(true);
		tglFavorite.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tglFavorite.setActionCommand("favorite");
		
		add(tglFavorite, "cell 3 0,alignx right,aligny center");
	}
	
	

	@Override
	public void actionPerformed(ActionEvent action) {
		if (action.getActionCommand() == "favorite") {
			
			if (model.isFavorite(product)) {
				model.favoriteRemove(product);
			} else {
				model.favoriteAdd(product);
			}
 		}
		
		if (action.getSource() == btnAddToCart) {
			model.cartAddItem(product);
		}
		
	}
	
	public Product getProduct() {
		return product;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName() == "favorite_add") {
			if(((Product)evt.getNewValue()).equals(product)) {
				tglFavorite.setSelected(true);
			}
		}
		if (evt.getPropertyName() == "favorite_remove") {
			if(((Product)evt.getNewValue()).equals(product)) {
				tglFavorite.setSelected(false);
			}
		}
	}
	
	





	

	



	

}
