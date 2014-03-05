import java.awt.Cursor;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import se.chalmers.ait.dat215.project.Order;
import se.chalmers.ait.dat215.project.ShoppingItem;


public class ListItem extends JPanel implements  ActionListener, PropertyChangeListener{
	private JLabel lblNameLabel;
	private IMatModel model;
	private String title;
	private List<ShoppingItem> shoppingItems;
	private JPanel panel;
	private JLabel label;
	private JLabel lblDatum;
	private JButton btnInfo;
	private ListView listView;

	public ListItem() {
		super();
		initialize();
	}
	
	/**
	 * Creates a CartItem instance from the given ShoppingItem.
	 * @param shoppingItem - The item to track
	 */
	public ListItem(ListView listView, String title, List<ShoppingItem> shoppingItems, IMatModel model){
		this();
		this.title = title;
		this.shoppingItems = shoppingItems;
		this.model = model;
		this.listView = listView;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
		lblDatum.setText(title);
		label.setText((totalPrice(shoppingItems)) + ":-");
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
		btnInfo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		add(btnInfo, "cell 2 0");
	}
	
	private double totalPrice(List<ShoppingItem> shoppingItems) {
		double price = 0;
		for (ShoppingItem si : shoppingItems) {
			price += si.getTotal();
		}
		return price;
	}
	
	



	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == btnInfo) {
			listView.addShoppingItems(shoppingItems);
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		
	}


	

	



	

}
