import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import se.chalmers.ait.dat215.project.Product;
import se.chalmers.ait.dat215.project.ShoppingItem;


public abstract class Item extends JPanel implements ActionListener {
	public ShoppingItem shoppingItem;
	public MainWindow parent;
	
	public Item() {
		super();
	}
	
	public Item(ShoppingItem shoppingItem, MainWindow parent) {
		this();
		this.shoppingItem = shoppingItem;
		this.parent = parent;
	}
	
	@Override
	public void actionPerformed(ActionEvent action) {
		if (action.getActionCommand() == "add_cart" || action.getActionCommand() == "favorite") {
			
			action.setSource(this);
			parent.actionPerformed(action);
		}
	}

	public abstract double getAmount();

}
