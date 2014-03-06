import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;

import com.alee.extended.image.WebImage;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.text.WebTextField;
import java.awt.Dimension;


public class CardSettingsPanel extends JPanel implements ActionListener, PropertyChangeListener {
	private JPanel panel;
	private JPanel panel_1;
	private JButton removeButton;
	private JLabel expireDateLabel;
	private WebComboBox monthWebComboBox;
	private JLabel slashLabel;
	private WebComboBox yearWebComboBox;
	private JLabel cvcLabel;
	private WebTextField cvcTextField;
	private WebTextField cardNumberTextField;
	private JLabel cardNumberLabel;
	private JButton saveCardButton;
	private CCardHandler cardHandler;
	private List<CCard> cardList;
	private IMatModel model;
	private JLabel iconLabel;
	private WebComboBox savedCardsWebComboBox;
	
	public CardSettingsPanel() {
		super();
		initialize();
	}

	public CardSettingsPanel(IMatModel model) {
		this();
		this.model = model;
		cardHandler = model.getCCardHandler();
		cardList = cardHandler.getCCards(model.getAccount().getUserName());
		model.addPropertyChangeListener(this);
		
		updateCards();
		}
	

	
	private void initialize() {
		setBorder(new TitledBorder(null, "Betalningsuppgifter", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(new MigLayout("", "[grow]", "[72px:n][][112.00]"));
		
		panel = new JPanel();
		panel.setPreferredSize(new Dimension(64, 40));
		panel.setBorder(new TitledBorder(null, "Sparade kort", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(panel, "cell 0 0,grow");
		panel.setLayout(new MigLayout("", "[64px][140px,grow][grow]", "[40px,grow]"));
		
		iconLabel = new JLabel("");
		panel.add(iconLabel, "cell 0 0,grow");
		
		savedCardsWebComboBox = new WebComboBox();
		savedCardsWebComboBox.setModel(new DefaultComboBoxModel(new String[] {"Inga sparade kort"}));
		savedCardsWebComboBox.addActionListener(this);
		panel.add(savedCardsWebComboBox, "cell 1 0,growx");


		
		removeButton = new JButton("Ta bort");
		removeButton.addActionListener(this);
		panel.add(removeButton, "cell 2 0");
		
		panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "L\u00E4gg till kort", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(panel_1, "cell 0 1 1 2,grow");
		panel_1.setLayout(new MigLayout("", "[][42,grow,right][12.00,grow][42,grow,left][][48]", "[][][]"));
		
		cardNumberLabel = new JLabel("Kortnr");
		panel_1.add(cardNumberLabel, "cell 0 0,alignx trailing");
		
		cardNumberTextField = new WebTextField();
		cardNumberTextField.setColumns(10);
		panel_1.add(cardNumberTextField, "cell 1 0 5 1,growx");
		
		expireDateLabel = new JLabel("Utg.datum");
		panel_1.add(expireDateLabel, "cell 0 1,alignx trailing");
		
		monthWebComboBox = new WebComboBox();
		monthWebComboBox.setModel(new DefaultComboBoxModel(new String[] {"MM", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}));
		panel_1.add(monthWebComboBox, "cell 1 1");
		
		slashLabel = new JLabel("/");
		panel_1.add(slashLabel, "cell 2 1,alignx center");
		
		yearWebComboBox = new WebComboBox();
		yearWebComboBox.setModel(new DefaultComboBoxModel(new String[] {"ÅÅ", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29"}));
		panel_1.add(yearWebComboBox, "cell 3 1");
		
		cvcLabel = new JLabel("CVC");
		panel_1.add(cvcLabel, "cell 4 1,alignx trailing");
		
		cvcTextField = new WebTextField();
		cvcTextField.setColumns(10);
		panel_1.add(cvcTextField, "cell 5 1,growx");
		
		saveCardButton = new JButton("Spara kort");
		panel_1.add(saveCardButton, "cell 5 2");
		saveCardButton.addActionListener(this);
		saveCardButton.setActionCommand("card_save");
	}

	public String getCardNumber() {
		return cardNumberTextField.getText();
	}
	
	public String getValidMonth() {
		return (String)monthWebComboBox.getSelectedItem();
	}
	
	public String getValidYear() {
		return (String)yearWebComboBox.getSelectedItem();
	}
	
	public String getCVC(){
		return cvcTextField.getText();
	}
	
	public void updateCards(){
		
		if (cardList != null) {
			panel.remove(savedCardsWebComboBox);
			savedCardsWebComboBox = new WebComboBox();
			savedCardsWebComboBox.addActionListener(this);
			
			String[] cardsfield = new String[cardList.size()];
			for (CCard cc : cardList) {
				cardsfield[cardList.indexOf(cc)] = cc.getCardNumber();
			}
			savedCardsWebComboBox.setModel(new DefaultComboBoxModel(cardsfield));
			panel.add(savedCardsWebComboBox, "cell 1 0,growx");
			savedCardsWebComboBox.revalidate();
			savedCardsWebComboBox.repaint();
			String cardType = cardList.get(savedCardsWebComboBox.getSelectedIndex()).getCardType();
			if (cardType.equalsIgnoreCase("Mastercard")) {
				System.out.println("mastercard");
				iconLabel.setIcon(new ImageIcon(CardSettingsPanel.class.getResource("/resources/icons/mastercard.png")));
			}
			if (cardType.equalsIgnoreCase("Visa")) {
				iconLabel.setIcon(new ImageIcon(CardSettingsPanel.class.getResource("/resources/icons/visa.png")));
				System.out.println("visa");
			}
			if (cardType.equalsIgnoreCase("American_Express")) {
				iconLabel.setIcon(new ImageIcon(CardSettingsPanel.class.getResource("/resources/icons/amex.png")));
				System.out.println("amex");
			}
			savedCardsWebComboBox.setSelectedIndex(savedCardsWebComboBox.getItemCount()-1);
			iconLabel.repaint();
			panel.repaint();
			repaint();

		
		}
	}

	
	public void setCardNumberErros(Color bg, WebImage wi) {
		cardNumberTextField.setBackground(bg);
		cardNumberTextField.setTrailingComponent(wi);
	}
	
	public void setCvcErros(Color bg, WebImage wi) {
		cvcTextField.setBackground(bg);
		cvcTextField.setTrailingComponent(wi);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "card_save") {
			
			setCardNumberErros(Color.WHITE, null);
			setCvcErros(Color.WHITE, null);
			
			model.cardSave(getCardNumber(),getValidMonth(),getValidYear(),getCVC());

		}
		
		if (e.getSource() == removeButton) {
			model.removeCard(cardList.get(savedCardsWebComboBox.getSelectedIndex()));
			updateCards();
		}
		
		if (e.getSource() == savedCardsWebComboBox) {
			String cardType = cardList.get(savedCardsWebComboBox.getSelectedIndex()).getCardType();
			System.out.println(cardType);
			if (cardType.equalsIgnoreCase("Mastercard")) {
				System.out.println("mastercard");
				iconLabel.setIcon(new ImageIcon(CardSettingsPanel.class.getResource("/resources/icons/mastercard.png")));
			}
			if (cardType.equalsIgnoreCase("Visa")) {
				iconLabel.setIcon(new ImageIcon(CardSettingsPanel.class.getResource("/resources/icons/visa.png")));
				System.out.println("visa");
			}
			if (cardType.equalsIgnoreCase("American_Express")) {
				iconLabel.setIcon(new ImageIcon(CardSettingsPanel.class.getResource("/resources/icons/amex.png")));
				System.out.println("amex");
			}
			iconLabel.repaint();
			panel.repaint();
			repaint();
			
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (!isVisible()) return; // TODO
		if (evt.getPropertyName() == "card_save") {
			List<String> errors = (ArrayList<String>)evt.getNewValue();
			if (!errors.isEmpty()) {
				String msg = "";
				
				if (errors.contains("cardnumber_invalid")) {
					setCardNumberErros(Constants.ERROR_COLOR, new WebImage(LogInWindow.class.getResource("/resources/icons/warning.png")));
					msg += "Fel format på kortnummer\n";
				}
				
				if (errors.contains("cvc_invalid")) {
					setCvcErros(Constants.ERROR_COLOR, new WebImage(LogInWindow.class.getResource("/resources/icons/warning.png")));
					msg += "Fel format på cvc\n";
				}
				
				if (errors.contains("month_invalid")) {
					msg += "Utgångsmånad saknas\n";
				}
				
				if (errors.contains("year_invalid")) {
					msg += "Utgångsår saknas\n";
				}
				JOptionPane.showMessageDialog(this, msg, "Fel vid sparning av uppgifter", JOptionPane.WARNING_MESSAGE);
			}
			
		}
		
		if (evt.getPropertyName() == "card_saved") {
			updateCards();
		}
		
	}
}
