import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import net.miginfocom.swing.MigLayout;
import se.chalmers.ait.dat215.project.ShoppingItem;


public class CartView extends JPanel implements ActionListener, PropertyChangeListener {

	private IMatModel model;
	private JPanel itemPanel;
	private JLabel totalPriceLabel;
	private JButton btnRensaKassan;
	
	public CartView() {
		super();
	}

	public CartView(IMatModel model) {
		this.model = model;
		this.model.addPropertyChangeListener(this);
		initialize();
		updateCartView();
	}
	
	private void initialize() {
		setLayout(new MigLayout("insets 2px", "[][grow][grow]", "[grow][][]"));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		add(scrollPane, "cell 0 0 3 1,grow");
		
		itemPanel = new JPanel();
		scrollPane.setViewportView(itemPanel);
		itemPanel.setLayout(new MigLayout("insets 0px", "[grow]", "[20px]"));
		
		JLabel totalPriceDescriptionLabel = new JLabel("Totalpris:");
		add(totalPriceDescriptionLabel, "flowx,cell 1 1,alignx right");
		
		totalPriceLabel = new JLabel("TOTALPRIS");
		add(totalPriceLabel, "cell 2 1");
		
		btnRensaKassan = new JButton("Rensa varukorg");
		btnRensaKassan.addActionListener(this);
		add(btnRensaKassan, "cell 1 2,growx,aligny center");
		
		JButton checkoutButton = new JButton("Gå till kassan");
		add(checkoutButton, "cell 2 2,growx,aligny center");
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName() == "cart_additem") {
			itemPanel.add(new CartItem((ShoppingItem)evt.getNewValue(), model), "wrap,growx");
			totalPriceLabel.setText(model.getShoppingCart().getTotal() + ":-");
			updateColors();
			revalidate();
			repaint();
		}
		
		if (evt.getPropertyName() == "cart_removeitem") {
			
			for (int i = 0; i < itemPanel.getComponentCount(); i++) {
				if (((CartItem)itemPanel.getComponent(i)).getShoppingItem() == (ShoppingItem)evt.getNewValue()) {
					itemPanel.remove(i);
					break;
				}
			}
			
			totalPriceLabel.setText(model.getShoppingCart().getTotal() + ":-");
			updateColors();
			itemPanel.revalidate();
			repaint();
		}
		
		if (evt.getPropertyName() == "cart_updateitem") {
			
			for (int i = 0; i < itemPanel.getComponentCount(); i++) {
				if (((CartItem)itemPanel.getComponent(i)).getShoppingItem() == (ShoppingItem)evt.getNewValue()) {
					((CartItem)itemPanel.getComponent(i)).getShoppingItem().setAmount(((ShoppingItem)evt.getNewValue()).getAmount());
					break;
				}
			}
			
			totalPriceLabel.setText(model.getShoppingCart().getTotal() + ":-");
			itemPanel.revalidate();
			repaint();
		}
		
		if (evt.getPropertyName() == "cart_clear") {
			itemPanel.removeAll();
			totalPriceLabel.setText(model.getShoppingCart().getTotal() + ":-");
			itemPanel.revalidate();
			repaint();
		}
	}
	
	private void updateColors() {
		for (int i = 0; i < itemPanel.getComponentCount(); i++) {
			if (i % 2 == 0) {
				itemPanel.getComponents()[i].setBackground(Constants.ALT_COLOR);
			} else {
				itemPanel.getComponents()[i].setBackground(null);
			}
		}
	}
	
	private void updateCartView() {
		for (int i = 0; i < model.getShoppingCart().getItems().size(); i++) {
			CartItem ci = new CartItem(model.getShoppingCart().getItems().get(i), model);
			itemPanel.add(ci, "wrap,growx");
		}
		
		totalPriceLabel.setText(model.getShoppingCart().getTotal() + ":-");
		updateColors();
		itemPanel.revalidate();
		repaint();
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == btnRensaKassan) {
			model.cartClear();
		}
	}
}
