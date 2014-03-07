package se.chalmers.dat215.grupp14;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class SettingsWindow extends JDialog implements ActionListener, PropertyChangeListener {
	private IMatModel model;
	private JPanel settingsPanel;
	private JLabel lblSettings;
	private CardSettingsPanel cardSettingsPanel;
	private AddressSettingsPanel addressSettingsPanel;
	private LogInSettingsPanel logInSettingsPanel;
	private JButton btnSave;

	public SettingsWindow(JFrame frame, IMatModel model) {
		super(frame, true);
		this.model = model;
		initializeGUI();
	}
	
	private void initializeGUI() {
		setSize(660, 600);
		setResizable(false);
		
		settingsPanel = new JPanel();
		getContentPane().add(settingsPanel, BorderLayout.CENTER);
		settingsPanel.setLayout(new MigLayout("", "[grow][330px]", "[64][195.00,grow][][28]"));
		
		lblSettings = new JLabel("Profilinställningar");
		lblSettings.setFont(new Font("Tahoma", Font.PLAIN, 16));
		settingsPanel.add(lblSettings, "cell 0 0");
		
		logInSettingsPanel = new LogInSettingsPanel(model);
		settingsPanel.add(logInSettingsPanel, "cell 0 1,grow");
		
		cardSettingsPanel = new CardSettingsPanel(model);
		settingsPanel.add(cardSettingsPanel, "cell 1 1,grow");
		
		addressSettingsPanel = new AddressSettingsPanel(model);
		settingsPanel.add(addressSettingsPanel, "cell 0 2,grow");
		
		btnSave = new JButton("Spara");
		btnSave.addActionListener(this);
		btnSave.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		settingsPanel.add(btnSave, "cell 1 3,alignx right,growy");
		
		logInSettingsPanel.setEmail(model.getAccount().getEmail());
		
		model.addPropertyChangeListener(this);
	}
	

	
	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == btnSave) {
			model.accountUpdate(
			        model.getAccount().getUserName(),
			        logInSettingsPanel.getPassword(),
					logInSettingsPanel.getPasswordRepeat(),
					logInSettingsPanel.getEmail(),
					addressSettingsPanel.getFirstName(),
					addressSettingsPanel.getLastName(),
					addressSettingsPanel.getAddress(),
					addressSettingsPanel.getMobilePhoneNumber(),
					addressSettingsPanel.getPhoneNumber(), 
					addressSettingsPanel.getPostAddress(), 
					addressSettingsPanel.getPostCode()
			);
		}
	}
	
	@SuppressWarnings("unchecked")
    @Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (!isVisible()) return; // TODO
		
		if (evt.getPropertyName() == "account_update") {
			List<String> errors = (ArrayList<String>)evt.getNewValue();
			
			if (!errors.isEmpty()) {
				String msg = "";
				
				if (errors.contains("email_invalid")) {
					msg += "Fel format på epostadress\n";
				}
				
				if (errors.contains("password_mismatch")) {
					msg += "Lösenorden stämmer inte överens\n";
				}
				
				if (errors.contains("firstname_tooshort")) {
					msg += "Förnamn saknas\n";
				}
				
				if (errors.contains("lastname_tooshort")) {
					msg += "Efternamn saknas\n";
				}
				
				if (errors.contains("address_invalid")) {
					msg += "Address saknas\n";
				}
				
				if (errors.contains("mobilephone_invalid")) {
					msg += "Fel format på mobilnummer\n";
				}
				
				if (errors.contains("phone_invalid")) {
					msg += "Fel format på telefonnummer\n";
				}
				
				if (errors.contains("postaddress_invalid")) {
					msg += "Postadress saknas\n";
				}
				
				if (errors.contains("postcode_invalid")) {
					msg += "Fel format på postnummer\n";
				}
				
				JOptionPane.showMessageDialog(this, msg, "Uppdatering misslyckades", JOptionPane.WARNING_MESSAGE);
			}
			
		}
		
		if (evt.getPropertyName() == "account_updated") {
			JOptionPane.showMessageDialog(this, "Dina uppgifter sparades", "Uppgifter sparade", JOptionPane.INFORMATION_MESSAGE);
			dispose();
		}
	}

}
