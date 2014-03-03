import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import com.alee.laf.rootpane.WebDialog;

import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import se.chalmers.ait.dat215.project.ShoppingItem;
import javax.swing.SwingConstants;


public class CheckOutWindow extends WebDialog implements ActionListener, PropertyChangeListener {
	private JFrame frame;
	private IMatModel model;
	private JPanel settingsPanel;
	private CardSettingsPanel cardSettingsPanel;
	private AddressSettingsPanel addressSettingsPanel;
	private JButton btnConfirm;
	private JPanel panel;
	private JScrollPane scrollPane;
	private JPanel cartPanel;
	private JLabel lblAmountValue;
	private JPanel amountPanel;

	public CheckOutWindow(JFrame frame, IMatModel model) {
		super(frame,"Checkout",true);
		this.frame = frame;
		this.model = model;
		this.model.addPropertyChangeListener(this);
		init();
		setSummaryCart();
		
	}
	private void setSummaryCart() {
		for (int i = 0; i < model.getShoppingCart().getItems().size(); i++) {
			ItemCheckOut ci = new ItemCheckOut(model.getShoppingCart().getItems().get(i), model);
			cartPanel.add(ci, "wrap,growx");
			lblAmountValue.setText(model.getShoppingCart().getTotal() + ":-");

			updateColors();
			cartPanel.revalidate();
			repaint();
			
		}
		
	}
	
	private void updateColors() {
		for (int i = 0; i < cartPanel.getComponentCount(); i++) {
			if (i % 2 == 0) {
				cartPanel.getComponents()[i].setBackground(Constants.ALT_COLOR);
			} else {
				cartPanel.getComponents()[i].setBackground(null);
			}
		}
	}
	
	private void init() {
		setSize(660, 600);
		setResizable(false);
		requestFocusInWindow();
		
		settingsPanel = new JPanel();
		getContentPane().add(settingsPanel, BorderLayout.CENTER);
		settingsPanel.setLayout(new MigLayout("", "[320px,grow][grow]", "[195.00,grow][][grow][28]"));
		
		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Varukorg", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		settingsPanel.add(panel, "cell 0 0 1 3,grow");
		panel.setLayout(new BorderLayout(0, 0));
		
		scrollPane = new JScrollPane();
		panel.add(scrollPane);
		
		cartPanel = new JPanel();
		scrollPane.setViewportView(cartPanel);
		cartPanel.setLayout(new MigLayout("insets 0px, gapy 0px", "[grow]", "[32px]"));
		
		addressSettingsPanel = new AddressSettingsPanel();
		settingsPanel.add(addressSettingsPanel, "cell 1 0,grow");
		
		addressSettingsPanel.setFirstName(model.getAccount().getFirstName());
		addressSettingsPanel.setLastName(model.getAccount().getLastName());
		addressSettingsPanel.setPhoneNumber(model.getAccount().getPhoneNumber());
		addressSettingsPanel.setMobilePhoneNumber(model.getAccount().getMobilePhoneNumber());
		addressSettingsPanel.setAddress(model.getAccount().getAddress());
		addressSettingsPanel.setPostAddress(model.getAccount().getPostAddress());
		addressSettingsPanel.setPostCode(model.getAccount().getPostCode());
		
		btnConfirm = new JButton("Bekräfta");
		btnConfirm.addActionListener(this);
		
		cardSettingsPanel = new CardSettingsPanel();
		settingsPanel.add(cardSettingsPanel, "cell 1 1,grow");
		
		amountPanel = new JPanel();
		amountPanel.setBorder(new TitledBorder(null, "Summa", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		settingsPanel.add(amountPanel, "cell 1 2,grow");
		amountPanel.setLayout(new MigLayout("insets 0px 0px 8px 0px", "[282.00,grow]", "[grow]"));
		
		lblAmountValue = new JLabel("0:-");
		lblAmountValue.setHorizontalAlignment(SwingConstants.CENTER);
		lblAmountValue.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		amountPanel.add(lblAmountValue, "cell 0 0,grow");
		
		
		settingsPanel.add(btnConfirm, "cell 1 3,alignx right,growy");
		
	}
	

	

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnConfirm){
			JOptionPane.showMessageDialog(this, "Tack för din beställning!", "Order bekräftad", JOptionPane.INFORMATION_MESSAGE);
			
			model.orderPlace();
			model.cartClear();
			dispose();
		}
		
		
	}
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName() == "cart_removeitem") {
			
			for (int i = 0; i < cartPanel.getComponentCount(); i++) {
				if (((ItemCheckOut)cartPanel.getComponent(i)).getShoppingItem() == (ShoppingItem)evt.getNewValue()) {
					System.out.println("hej");
					cartPanel.remove(i);
					break;
				}
			}
			
			updateColors();
			cartPanel.revalidate();
			repaint();
		}
		
	}

}
