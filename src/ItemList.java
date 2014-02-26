import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.miginfocom.swing.MigLayout;
import se.chalmers.ait.dat215.project.Product;
import se.chalmers.ait.dat215.project.ShoppingItem;


@SuppressWarnings("serial")
public class ItemList extends Item implements ChangeListener {
	private JLabel lblBild;
	private JSeparator separator;
	private JButton btnKp;
	private JLabel lblNamelabel;
	private JSpinner spinner;
	private JLabel lblPricelabel;

	public ItemList(ShoppingItem shoppingItem , MainWindow parent) {
		super(shoppingItem, parent);
		initialize();
	}
	
	private void initialize() {
		setPreferredSize(new Dimension(512, 77));
		//setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		setLayout(new MigLayout("", "[64px:64.00][5px:5px][92px:92px][grow][48px:48px][64px:64px]", "[64px]"));
		
		lblBild = new JLabel(parent.getModel().getImageIcon(shoppingItem.getProduct(), new Dimension(48, 48)));
		lblBild.setHorizontalTextPosition(SwingConstants.RIGHT);
		lblBild.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblBild, "cell 0 0,alignx center,aligny center");
		
		separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		add(separator, "cell 1 0,growy");
		
		lblNamelabel = new JLabel(shoppingItem.getProduct().getName());
		add(lblNamelabel, "cell 2 0,alignx center");
		
		lblPricelabel = new JLabel(shoppingItem.getProduct().getPrice() + ":-");
		lblPricelabel.setBackground(Color.RED);
		add(lblPricelabel, "cell 3 0");
		
		spinner = new JSpinner();
		spinner.addChangeListener(this);
		add(spinner, "cell 4 0,alignx right");
		
		btnKp = new JButton("K\u00F6p");
		btnKp.addActionListener(this);
		btnKp.setActionCommand("add_cart");
		add(btnKp, "cell 5 0,alignx center");
	}

	@Override
	public void stateChanged(ChangeEvent event) {
		if (event.getSource() == spinner) {
			lblPricelabel.setText(shoppingItem.getProduct().getPrice() * ((Integer)spinner.getValue()).doubleValue() + ":-");
		}
	}
	
	@Override
	public double getAmount() {
		return ((Integer)spinner.getValue()).doubleValue();
	}

}
