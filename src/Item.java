import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;

import se.chalmers.ait.dat215.project.ShoppingItem;


public abstract class Item extends JPanel implements ActionListener, PropertyChangeListener {
	public ShoppingItem shoppingItem;
	public IMatModel model;
	public FavoriteView favoriteView;
	
	public Item() {
		super();
	}
	
	public Item(ShoppingItem shoppingItem, FavoriteView favoriteView, IMatModel model) {
		this();
		this.favoriteView = favoriteView;
		this.shoppingItem = shoppingItem;
		this.model = model;
		this.model.addPropertyChangeListener(this);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent action) {
		if (action.getActionCommand() == "add_cart") {
			model.cartAddItem(shoppingItem.getProduct(), shoppingItem.getAmount());
		}
		
		if (action.getActionCommand() == "favorite") {
			
			if (model.isFavorite(shoppingItem.getProduct())) {
				model.favoriteRemove(shoppingItem.getProduct());
			} else {
				favoriteView.addFavorite(shoppingItem.getProduct());
				model.favoriteAdd(shoppingItem.getProduct());
			}
 		}
	}



}
