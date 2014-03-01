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


public class CheckOutWindow extends WebDialog implements ActionListener, PropertyChangeListener {
	private JFrame frame;
	private IMatModel model;
	private JPanel settingsPanel;
	private JLabel lblSettings;
	private CardSettingsPanel cardSettingsPanel;
	private AddressSettingsPanel addressSettingsPanel;
	private JButton btnConfirm;
	private JPanel panel;
	private JScrollPane scrollPane;
	private JPanel cartPanel;
	private JLabel lblAmountValue;
	private JLabel lblMoms;
	private JLabel lblNetto;
	private JLabel lblAmount;
	private JLabel lblNettoValue;
	private JLabel lblMomsValue;

	public CheckOutWindow(JFrame frame, IMatModel model) {
		super(frame,true);
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
			lblNettoValue.setText(model.getShoppingCart().getTotal() - (model.getShoppingCart().getTotal() * 0.06) + ":-");
			lblMomsValue.setText(model.getShoppingCart().getTotal()  * 0.06 +  ":-");

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
		settingsPanel.setLayout(new MigLayout("", "[grow][][grow]", "[64][195.00,grow][][][][][][28]"));
		
		lblSettings = new JLabel("Checkout");
		settingsPanel.add(lblSettings, "cell 0 0");
		
		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Varukorg", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		settingsPanel.add(panel, "cell 0 1 3 1,grow");
		panel.setLayout(new BorderLayout(0, 0));
		
		scrollPane = new JScrollPane();
		panel.add(scrollPane);
		
		cartPanel = new JPanel();
		scrollPane.setViewportView(cartPanel);
		cartPanel.setLayout(new MigLayout("", "[grow]", "[64px]"));
		
		lblNetto = new JLabel("Netto:");
		lblNetto.setFont(new Font("Tahoma", Font.PLAIN, 11));
		settingsPanel.add(lblNetto, "cell 1 4,alignx right");
		
		lblNettoValue = new JLabel("Brutto");
		settingsPanel.add(lblNettoValue, "cell 2 4");
		
		lblMoms = new JLabel("Varav moms:");
		settingsPanel.add(lblMoms, "cell 1 5,alignx right");
		
		lblMomsValue = new JLabel("Moms");
		settingsPanel.add(lblMomsValue, "cell 2 5");
		
		lblAmount = new JLabel("Att Betala:");
		settingsPanel.add(lblAmount, "cell 1 6,alignx right");
		
		lblAmountValue = new JLabel("Summa");
		settingsPanel.add(lblAmountValue, "cell 2 6,alignx left");
		
		addressSettingsPanel = new AddressSettingsPanel();
		settingsPanel.add(addressSettingsPanel, "cell 0 3,grow");
		
		btnConfirm = new JButton("Bekräfta");
		btnConfirm.addActionListener(this);
		
		cardSettingsPanel = new CardSettingsPanel();
		settingsPanel.add(cardSettingsPanel, "cell 1 3 2 1,grow");
		
		
		settingsPanel.add(btnConfirm, "cell 2 7,alignx right,growy");
	}
	

	

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnConfirm){
			JOptionPane.showMessageDialog(this, "Tack för din beställning!", "Order bekräftad", JOptionPane.INFORMATION_MESSAGE);
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
