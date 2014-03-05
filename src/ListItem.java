import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
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
	private JButton btnX;

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
		setLayout(new MigLayout("insets 4px", "[grow][][48][]", "[60px]"));
		
		
		
		lblDatum = new JLabel("");
		add(lblDatum, "cell 0 0");
		
		label = new JLabel("");
		add(label, "cell 1 0");
		
		btnInfo = new JButton("Mer info");
		btnInfo.setMargin(new Insets(2, 5, 2, 5));
		btnInfo.addActionListener(this);
		btnInfo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		add(btnInfo, "cell 2 0");
		
		btnX = new JButton("");
		btnX.setToolTipText("Ta bort artikel från varukorg");
		btnX.setUI(new javax.swing.plaf.basic.BasicButtonUI());
		btnX.setContentAreaFilled(false);
		btnX.setBorderPainted(false);
		btnX.setIcon(new ImageIcon(CartItem.class.getResource("/resources/icons/delete.png")));
		btnX.addActionListener(this);
		btnX.setActionCommand("remove_from_cart");
		btnX.setMinimumSize(new Dimension(0, 0));
		btnX.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		add(btnX, "cell 3 0");
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
		
		if (event.getSource() == btnX) {
			model.listRemove(title);
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		
	}


	

	



	

}
