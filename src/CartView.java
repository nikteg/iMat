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
	private JButton btnRensaKassan;
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
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		add(scrollPane, "cell 0 0 2 1,grow");
		
		itemPanel = new JPanel();
		scrollPane.setViewportView(itemPanel);
		itemPanel.setLayout(new MigLayout("insets 0px", "[grow]", "[20px]"));
		
		totalPriceDescriptionLabel = new JLabel("Totalpris:");
		add(totalPriceDescriptionLabel, "flowx,cell 0 1,alignx right");
		
		totalPriceLabel = new JLabel("TOTALPRIS");
		add(totalPriceLabel, "cell 1 1");
		
		btnRensaKassan = new JButton("Rensa varukorg");
		btnRensaKassan.addActionListener(this);
		add(btnRensaKassan, "cell 0 2,growx,aligny center");
		
		checkoutButton = new JButton("Gå till kassan");
		checkoutButton.addActionListener(this);
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
			if (JOptionPane.showConfirmDialog(this, "Är du säker på", "Radera alla favoriter", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == 0) {
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
