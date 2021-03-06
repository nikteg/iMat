package se.chalmers.dat215.grupp14;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import javax.swing.JPanel;
import se.chalmers.ait.dat215.project.ShoppingItem;
import se.chalmers.dat215.grupp14.backend.IMatModel;

/**
 * Abstract item class
 * @author Niklas Tegnander, Mikael Lönn and Oskar Jönefors
 */
@SuppressWarnings("serial")
public abstract class Item extends JPanel implements ActionListener, PropertyChangeListener {
    public ShoppingItem shoppingItem;
    public IMatModel model;
    public FavoriteView favoriteView;

    /**
     * Constructor
     */
    public Item() {
        super();
    }

    /**
     * Constructor with given shopping item, favorite view and model
     * @param shoppingItem
     * @param favoriteView
     * @param model
     */
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
