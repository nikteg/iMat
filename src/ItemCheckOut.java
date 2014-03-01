import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.miginfocom.swing.MigLayout;
import se.chalmers.ait.dat215.project.Product;
import se.chalmers.ait.dat215.project.ShoppingItem;

import javax.swing.SpinnerNumberModel;

import com.alee.laf.spinner.WebSpinner;


@SuppressWarnings("serial")
public class ItemCheckOut extends JPanel {
	private ShoppingItem shoppingItem;
	private IMatModel model;
	private JLabel lblBild;
	private JSeparator separator;
	private JLabel lblNamelabel;
	private JLabel lblPricelabel;

	public ItemCheckOut() {
		super();
		initialize();
	}
	
	public ItemCheckOut(ShoppingItem shoppingItem , IMatModel model) {
		super();
		this.model = model;
		this.shoppingItem = shoppingItem;
		initialize();
	}
	
	private void initialize() {
		setPreferredSize(new Dimension(512, 64));
		setLayout(new MigLayout("insets 0px", "[64px:64.00][5px:5px][92px:92px,grow][64px]", "[64px]"));
		
		lblBild = new JLabel(model.getImageIcon(shoppingItem.getProduct(), new Dimension(48, 48)));
		lblBild.setHorizontalTextPosition(SwingConstants.RIGHT);
		lblBild.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblBild, "cell 0 0,alignx center,aligny center");
		
		separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		add(separator, "cell 1 0,growy");
		
		lblNamelabel = new JLabel(shoppingItem.getProduct().getName());
		add(lblNamelabel, "cell 2 0,alignx left");
		
		lblPricelabel = new JLabel(shoppingItem.getAmount() + " á " + shoppingItem.getProduct().getPrice() + " " + shoppingItem.getProduct().getUnit());
		lblPricelabel.setBackground(Color.RED);
		add(lblPricelabel, "cell 3 0");
		
	}

	public ShoppingItem getShoppingItem() {
		return shoppingItem;
	}



}
