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
	private JLabel lblNamelabel;
	private JLabel lblPricelabel;
	private JLabel lblAmountLabel;

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
		setPreferredSize(new Dimension(256, 64));
		setLayout(new MigLayout("insets 0px  8px 0px  8px", "[grow][72px][72px]", "[64px]"));
		
		lblNamelabel = new JLabel(shoppingItem.getProduct().getName());
		add(lblNamelabel, "cell 0 0,alignx left");
		
		lblAmountLabel = new JLabel(((Double)shoppingItem.getAmount()).intValue() + " " + shoppingItem.getProduct().getUnitSuffix());
		add(lblAmountLabel, "cell 1 0,alignx left");
		
		lblPricelabel = new JLabel(shoppingItem.getTotal() + ":-");
		lblPricelabel.setBackground(Color.RED);
		add(lblPricelabel, "cell 2 0,alignx right");
		
	}

	public ShoppingItem getShoppingItem() {
		return shoppingItem;
	}



}
