import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import se.chalmers.ait.dat215.project.Product;


public abstract class Item extends JPanel implements ActionListener {
	public Product product;
	public MainWindow parent;
	
	public Item() {
		super();
	}
	
	public Item(Product product, MainWindow parent) {
		this();
		this.product = product;
		this.parent = parent;
	}
	
	@Override
	public void actionPerformed(ActionEvent action) {
		if (action.getActionCommand() == "add_cart") {
			action.setSource(this);
			parent.actionPerformed(action);
		}
	}
}
