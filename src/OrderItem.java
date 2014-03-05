import java.awt.Cursor;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import se.chalmers.ait.dat215.project.Order;
import se.chalmers.ait.dat215.project.ShoppingItem;
import javax.swing.ImageIcon;


public class OrderItem extends JPanel implements  ActionListener, PropertyChangeListener{
	private JLabel lblNameLabel;
	private IMatModel model;
	private Order order;
	private JPanel panel;
	private JLabel label;
	private JLabel lblDatum;
	private JButton btnInfo;
	private HistoryView historyView;

	public OrderItem() {
		super();
		initialize();
	}
	
	/**
	 * Creates a CartItem instance from the given ShoppingItem.
	 * @param shoppingItem - The item to track
	 */
	public OrderItem(HistoryView historyView, Order order, IMatModel model){
		this();
		this.order = order;
		this.model = model;
		this.historyView = historyView;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
		lblDatum.setText(sdf.format(order.getDate()));
		label.setText((totalPrice(order)) + ":-");
		this.model.addPropertyChangeListener(this);
		
		
	}
	


	private void initialize() {
		setLayout(new MigLayout("insets 4px", "[grow][][]", "[60px]"));
		
		
		
		lblDatum = new JLabel("");
		add(lblDatum, "cell 0 0");
		
		label = new JLabel("");
		add(label, "cell 1 0");
		
		btnInfo = new JButton("");
		btnInfo.setToolTipText("Expandera order");
		btnInfo.setUI(new javax.swing.plaf.basic.BasicButtonUI());
		btnInfo.setContentAreaFilled(false);
		btnInfo.setBorderPainted(false);
		btnInfo.setIcon(new ImageIcon(OrderItem.class.getResource("/resources/icons/more.png")));
		btnInfo.addActionListener(this);
		btnInfo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		add(btnInfo, "cell 2 0,alignx right");
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
			historyView.addShoppingItems(order);
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		
	}


	

	



	

}
