import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;

import se.chalmers.ait.dat215.project.CartEvent;
import se.chalmers.ait.dat215.project.ShoppingCartListener;
import se.chalmers.ait.dat215.project.ShoppingItem;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.SpinnerNumberModel;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.alee.laf.spinner.WebSpinner;

import javax.swing.JButton;


public class CartItem extends JPanel implements ChangeListener, ActionListener, PropertyChangeListener{
	private JLabel lblNameLabel;
	private WebSpinner spinner;
	private JLabel lblPrice;
	private ShoppingItem shoppingItem;
	private IMatModel model;
	private JLabel lblTotalPriceLabel;
	private JButton btnX;

	public CartItem() {
		// TODO Auto-generated constructor stub
		initialize();
	}
	
	public CartItem(String name){
		this();
		lblNameLabel.setText(name);
		
	}
	
	/**
	 * Creates a CartItem instance from the given ShoppingItem.
	 * @param shoppingItem - The item to track
	 */
	public CartItem(ShoppingItem shoppingItem, IMatModel model){
		this();
		this.shoppingItem = shoppingItem;
		this.model = model;
		lblNameLabel.setText(shoppingItem.getProduct().getName());
		lblPrice.setText(shoppingItem.getProduct().getPrice() + ":-");
		lblTotalPriceLabel.setText(shoppingItem.getTotal() + ":-");
		spinner.setValue(((Double)(shoppingItem.getAmount())).intValue());
	}
	
	private void initialize() {
		
		
		setPreferredSize(new Dimension(260, 60));
		setLayout(new MigLayout("", "[grow][48px][60px][23.00px][pref:18.00px:pref]", "[60px]"));
		
		lblNameLabel = new JLabel("nameLabel");
		add(lblNameLabel, "cell 0 0,alignx left,aligny center");
		
		spinner = new WebSpinner();
		spinner.addChangeListener(this);
		spinner.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		
		lblPrice = new JLabel("priceLabel");
		add(lblPrice, "cell 1 0,alignx right,aligny center");
		spinner.setPreferredSize(new Dimension(48, 10));
		add(spinner, "cell 2 0,alignx right,aligny baseline");
		
		lblTotalPriceLabel = new JLabel("X");
		add(lblTotalPriceLabel, "cell 3 0,alignx center,aligny center");
		
		btnX = new JButton("");
		btnX.setPreferredSize(new Dimension(18, 18));
		btnX.setUI(new javax.swing.plaf.basic.BasicButtonUI());
		btnX.setIcon(new ImageIcon(CartItem.class.getResource("/resources/icons/delete.png")));
		btnX.addActionListener(this);
		btnX.setActionCommand("remove_from_cart");
		btnX.setMinimumSize(new Dimension(0, 0));
		add(btnX, "cell 4 0");
	}
	
	public ShoppingItem getShoppingItem(){
		return shoppingItem;
	}
	
	@Override
	public void stateChanged(ChangeEvent event) {
		if (event.getSource() == spinner) {
			System.out.println("RULLAR");
			shoppingItem.setAmount(((Integer)spinner.getValue()).doubleValue());
			lblTotalPriceLabel.setText(shoppingItem.getTotal() + ":-");
			repaint();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnX) {
			model.cartRemoveItem(shoppingItem);
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName() == "cart_updateitem") {
			ShoppingItem item = (ShoppingItem)evt.getNewValue();
			if (shoppingItem.getProduct().equals(item.getProduct())) {
				spinner.setValue(((Double)(item).getAmount()).intValue());
			}
		}
	}


	

	



	

}
