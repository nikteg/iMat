import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;

import se.chalmers.ait.dat215.project.CartEvent;
import se.chalmers.ait.dat215.project.Order;
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
import java.text.SimpleDateFormat;

import com.alee.laf.spinner.WebSpinner;

import javax.swing.JButton;

import java.awt.Color;


public class OrderItem extends JPanel implements  ActionListener, PropertyChangeListener{
	private JLabel lblNameLabel;
	private IMatModel model;
	private Order order;
	private JPanel panel;
	private JLabel label;
	private JLabel lblDatum;
	private JButton btnInfo;

	public OrderItem() {
		super();
		initialize();
	}
	
	/**
	 * Creates a CartItem instance from the given ShoppingItem.
	 * @param shoppingItem - The item to track
	 */
	public OrderItem(Order order, IMatModel model){
		this();
		this.order = order;
		this.model = model;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
		lblDatum.setText(sdf.format(order.getDate()));
		label.setText((totalPrice(order)) + ":-");
		this.model.addPropertyChangeListener(this);
		
		
	}
	


	private void initialize() {
		setLayout(new MigLayout("insets 4px", "[grow][][48]", "[60px]"));
		
		
		
		lblDatum = new JLabel("");
		add(lblDatum, "cell 0 0");
		
		label = new JLabel("");
		add(label, "cell 1 0");
		
		btnInfo = new JButton("Mer info");
		btnInfo.setMargin(new Insets(2, 5, 2, 5));
		btnInfo.addActionListener(this);
		
		add(btnInfo, "cell 2 0");
	}
	
	private double totalPrice(Order order) {
		double price = 0;
		for (ShoppingItem si : order.getItems()) {
			price += si.getTotal();
		}
		return price;
	}
	
	



	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == btnInfo) {
			model.orderMoreInfo(order);
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		
	}


	

	



	

}
