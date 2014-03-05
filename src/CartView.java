import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import net.miginfocom.swing.MigLayout;
import se.chalmers.ait.dat215.project.ShoppingItem;


public class CartView extends JPanel implements ActionListener, PropertyChangeListener {

	private IMatModel model;
	private JPanel itemPanel;
	private JLabel totalPriceLabel;
	private JButton btnClearCart;
	private JScrollPane scrollPane;
	private JLabel totalPriceDescriptionLabel;
	private JButton checkoutButton;
	private CheckOutWindow checkoutWindow;
	private JFrame frame;
	
	public CartView() {
		super();
		initialize();
	}

	public CartView(IMatModel model, JFrame frame) {
		this();
		this.model = model;
		this.frame = frame;
		this.model.addPropertyChangeListener(this);
		updateCartView();
	}
	
	private void initialize() {
		setLayout(new MigLayout("insets 2px", "[grow][grow]", "[grow][20.00][24.00]"));
		
		scrollPane = new JScrollPane();
		scrollPane.setFocusable(false);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		add(scrollPane, "cell 0 0 2 1,grow");
		
		itemPanel = new JPanel();
		scrollPane.setViewportView(itemPanel);
		itemPanel.setLayout(new MigLayout("insets 0px, gapy 0", "[grow]", "[20px]"));
		
		totalPriceDescriptionLabel = new JLabel("Totalpris:");
		add(totalPriceDescriptionLabel, "flowx,cell 0 1,alignx right");
		
		totalPriceLabel = new JLabel("TOTALPRIS");
		add(totalPriceLabel, "cell 1 1");
		
		btnClearCart = new JButton("Rensa varukorg");
		btnClearCart.setToolTipText("Ta bort alla artiklar från varukorgen");
		btnClearCart.addActionListener(this);
		btnClearCart.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		add(btnClearCart, "cell 0 2,growx,aligny center");
		
		checkoutButton = new JButton("Gå till kassan");
		checkoutButton.setToolTipText("Gå vidare till kassan");
		checkoutButton.addActionListener(this);
		checkoutButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		add(checkoutButton, "cell 1 2,growx,aligny center");
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
			
			totalPriceLabel.setText(Constants.currencyFormat.format(model.getShoppingCart().getTotal()) + ":-");
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
		if (event.getSource() == btnClearCart) {
			String[] options = new String[2];
			options[0] = new String("Ja");
			options[1] = new String("Nej");
			if (JOptionPane.showOptionDialog(this, "Är du säker på att du vill ta bort alla varor ur varukorgen?", "Rensa varukorg?", 0, JOptionPane.WARNING_MESSAGE,null,options,null) == 0) {
				model.cartClear();
			}
		}
		
		if (event.getSource() == checkoutButton) {
			checkoutWindow = new CheckOutWindow(frame, model);
			checkoutWindow.setLocationRelativeTo(frame);
			checkoutWindow.setVisible(true);
			
		}
	}
}
