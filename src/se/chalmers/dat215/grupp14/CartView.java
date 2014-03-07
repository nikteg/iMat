package se.chalmers.dat215.grupp14;
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

import com.alee.laf.text.WebTextField;


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
	private JPanel panel;
	private WebTextField listNameTextField;
	private JButton btnSparaLista;
	
	public CartView() {
		super();
		initialize();
	}

	public CartView(IMatModel model, JFrame frame) {
		this();
		this.model = model;
		this.frame = frame;
		listNameTextField.setEnabled(!model.getAccount().isAnonymous());
		btnSparaLista.setEnabled(!model.getAccount().isAnonymous());
		this.model.addPropertyChangeListener(this);
		checkoutButton.setEnabled(!model.getShoppingCart().getItems().isEmpty());
		btnClearCart.setEnabled(!model.getShoppingCart().getItems().isEmpty());
		
		updateCartView();
	}
	
	private void initialize() {
		setLayout(new MigLayout("insets 2px", "[grow][grow]", "[][grow][20.00][24.00]"));
		
		panel = new JPanel();
		add(panel, "cell 0 0 2 1,grow");
		panel.setLayout(new MigLayout("insets 0px", "[grow][]", "[]"));
		
		listNameTextField = new WebTextField();
		listNameTextField.setInputPrompt("Namn på lista...");
		
		panel.add(listNameTextField, "cell 0 0,growx");
		
		btnSparaLista = new JButton("Spara varukorg");
		btnSparaLista.addActionListener(this);
		btnSparaLista.setCursor(Cursor
				.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		panel.add(btnSparaLista, "cell 1 0");
		
		scrollPane = new JScrollPane();
		scrollPane.setFocusable(false);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		add(scrollPane, "cell 0 1 2 1,grow");
		
		itemPanel = new JPanel();
		scrollPane.setViewportView(itemPanel);
		itemPanel.setLayout(new MigLayout("insets 0px, gapy 0", "[grow]", "[36px]"));
		
		totalPriceDescriptionLabel = new JLabel("Totalpris:");
		add(totalPriceDescriptionLabel, "flowx,cell 0 2,alignx right");
		
		totalPriceLabel = new JLabel("TOTALPRIS");
		add(totalPriceLabel, "cell 1 2");
		
		btnClearCart = new JButton("Rensa varukorg");
		btnClearCart.setToolTipText("Ta bort alla artiklar från varukorgen");
		btnClearCart.addActionListener(this);
		btnClearCart.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		add(btnClearCart, "cell 0 3,growx,aligny center");
		
		checkoutButton = new JButton("Gå till kassan");
		checkoutButton.setToolTipText("Gå vidare till kassan");
		checkoutButton.addActionListener(this);
		checkoutButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		add(checkoutButton, "cell 1 3,growx,aligny center");
		

	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
	
		
		if (evt.getPropertyName() == "account_signedin") {
			listNameTextField.setEnabled(!model.getAccount().isAnonymous());
			btnSparaLista.setEnabled(!model.getAccount().isAnonymous());
		}
		
		if (evt.getPropertyName() == "account_signout") {
			listNameTextField.setEnabled(!model.getAccount().isAnonymous());
			btnSparaLista.setEnabled(!model.getAccount().isAnonymous());
		}
		
		if (evt.getPropertyName() == "cart_additem") {
			checkoutButton.setEnabled(true);
			btnClearCart.setEnabled(true);
			itemPanel.add(new CartItem((ShoppingItem)evt.getNewValue(), model), "wrap,growx");
			totalPriceLabel.setText(model.getShoppingCart().getTotal() + ":-");
			updateColors();
			revalidate();
			repaint();
		}
		
		if (evt.getPropertyName() == "cart_removeitem") {
			
			checkoutButton.setEnabled(!model.getShoppingCart().getItems().isEmpty());
			btnClearCart.setEnabled(!model.getShoppingCart().getItems().isEmpty());
			
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
			if (model.getShoppingCart().getItems().size() != 0) {
				checkoutWindow = new CheckOutWindow(frame, model);
				checkoutWindow.setLocationRelativeTo(frame);
				checkoutWindow.setVisible(true);
			}
			
			
		}
		
		if (event.getSource() == btnSparaLista) {
			
			model.listSave(listNameTextField.getText(), model.getShoppingCart().getItems());
			
		}
	}
}
