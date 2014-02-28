import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import net.miginfocom.swing.MigLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSeparator;

import se.chalmers.ait.dat215.project.ShoppingItem;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;


public class CartView extends JPanel implements PropertyChangeListener {

	private IMatModel model;
	private JPanel itemPanel;
	private JLabel totalPriceLabel;
	private List<ShoppingItem> itemList;
	
	/**
	 * 
	 * @param cart The cart to monitor;
	 */
	public CartView(IMatModel model) {
		this.model = model;
		itemList = new ArrayList<ShoppingItem>(model.getShoppingCart().getItems());
		setLayout(new MigLayout("", "[126px,grow]", "[539.00,grow][][47px,fill]"));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		add(scrollPane, "cell 0 0,grow");
		
		JPanel itemPanel = new JPanel();
		scrollPane.setViewportView(itemPanel);
		itemPanel.setLayout(new MigLayout("", "[grow,fill]", "[]"));
		
		JPanel summaryPanel = new JPanel();
		add(summaryPanel, "cell 0 2,grow");
		summaryPanel.setLayout(new MigLayout("", "[][][][][][][][][][]", "[][]"));
		
		JLabel totalPriceDescriptionLabel = new JLabel("Totalpris:");
		summaryPanel.add(totalPriceDescriptionLabel, "cell 8 0");
		
		JLabel totalPriceLabel = new JLabel("TOTALPRIS");
		summaryPanel.add(totalPriceLabel, "cell 9 0");
		
		JButton checkoutButton = new JButton("Gå till kassan");
		summaryPanel.add(checkoutButton, "cell 9 1");
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String command = evt.getPropertyName();
		switch (command) {
			case "cart_add_item": updateCartView();
			break;
			case "cart_remove_item": updateCartView();
			break;
			case "cart_clear": updateCartView();
			break;
			case "cart_updateitem": updateCartView();
			default: break;
		}
	}

	private void addItem(ShoppingItem item) {
		itemPanel.add(new CartItem(item, model));
	}
	
	private void clearCart(){
		itemPanel.removeAll();
	}
	
	
	
	private void removeItem(ShoppingItem item) {
	}
	
	private void updateCartView(){
		itemList = model.getShoppingCart().getItems();
		itemPanel.removeAll();
		for(int i=0; i < itemList.size(); i++){
			itemPanel.add(new CartItem(itemList.get(i), model), "wrap");
		}
		totalPriceLabel.setText("" + model.getShoppingCart().getTotal());
	}
}
