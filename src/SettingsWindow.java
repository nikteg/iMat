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

import com.alee.extended.image.WebImage;


public class SettingsWindow extends JDialog implements ActionListener, PropertyChangeListener {
	private JFrame frame;
	private IMatModel model;
	private JPanel settingsPanel;
	private JLabel lblSettings;
	private CardSettingsPanel cardSettingsPanel;
	private AddressSettingsPanel addressSettingsPanel;
	private LogInSettingsPanel logInSettingsPanel;
	private JButton btnSave;

	public SettingsWindow(JFrame frame, IMatModel model) {
		super(frame,true);
		this.frame = frame;
		this.model = model;
		initialize();
		
	}
	
	private void initialize() {
		setSize(660, 600);
		setResizable(false);
		
		settingsPanel = new JPanel();
		getContentPane().add(settingsPanel, BorderLayout.CENTER);
		settingsPanel.setLayout(new MigLayout("", "[grow][grow]", "[64][195.00,grow][][28]"));
		
		lblSettings = new JLabel("Profilinställningar");
		lblSettings.setFont(new Font("Tahoma", Font.PLAIN, 16));
		settingsPanel.add(lblSettings, "cell 0 0");
		
		logInSettingsPanel = new LogInSettingsPanel();
		settingsPanel.add(logInSettingsPanel, "cell 0 1,grow");
		
		cardSettingsPanel = new CardSettingsPanel(model);
		settingsPanel.add(cardSettingsPanel, "cell 1 1,grow");
		
		addressSettingsPanel = new AddressSettingsPanel();
		settingsPanel.add(addressSettingsPanel, "cell 0 2,grow");
		
		btnSave = new JButton("Spara");
		btnSave.addActionListener(this);
		btnSave.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		
		settingsPanel.add(btnSave, "cell 1 3,alignx right,growy");
		
		addressSettingsPanel.setFirstName(model.getAccount().getFirstName());
		addressSettingsPanel.setLastName(model.getAccount().getLastName());
		addressSettingsPanel.setPhoneNumber(model.getAccount().getPhoneNumber());
		addressSettingsPanel.setMobilePhoneNumber(model.getAccount().getMobilePhoneNumber());
		addressSettingsPanel.setAddress(model.getAccount().getAddress());
		addressSettingsPanel.setPostAddress(model.getAccount().getPostAddress());
		addressSettingsPanel.setPostCode(model.getAccount().getPostCode());
		
		logInSettingsPanel.setEmail(model.getAccount().getEmail());
		
		model.addPropertyChangeListener(this);
		
		
		
	}
	

	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnSave){
			
			model.setCredentials(model.getAccount().getUserName(),
									logInSettingsPanel.getPassword(),
									logInSettingsPanel.getPasswordRepeat(),
									logInSettingsPanel.getEmail(),
									addressSettingsPanel.getFirstName(),
									addressSettingsPanel.getLastName(),
									addressSettingsPanel.getAddress(),
									addressSettingsPanel.getMobilePhoneNumber(),
									addressSettingsPanel.getPhoneNumber(), 
									addressSettingsPanel.getPostAddress(), 
									addressSettingsPanel.getPostCode());
			
			
		}
		
		
	}
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (!isVisible()) return; // TODO
		if (evt.getPropertyName() == "account_save") {
			List<String> errors = (ArrayList<String>)evt.getNewValue();
			if (!errors.isEmpty()) {
				String msg = "";
				
				if (errors.contains("email_invalid")) {
					logInSettingsPanel.setEmailErros(Constants.ERROR_COLOR, new WebImage(LogInWindow.class.getResource("/resources/icons/warning.png")));
					msg += "Fel format på Email\n";
				}
				
				if (errors.contains("password_invalid")) {
					logInSettingsPanel.setPasswordErros(Constants.ERROR_COLOR, new WebImage(LogInWindow.class.getResource("/resources/icons/warning.png")));
					logInSettingsPanel.setPasswordRepeatErrors(Constants.ERROR_COLOR, new WebImage(LogInWindow.class.getResource("/resources/icons/warning.png")));
					msg += "Lösenorden stämmer inte överens\n";
				}
				
				if (errors.contains("firstname_invalid")) {
					addressSettingsPanel.setFirstNameErros(Constants.ERROR_COLOR, new WebImage(LogInWindow.class.getResource("/resources/icons/warning.png")));
					msg += "Förnamn saknas\n";
				}
				
				if (errors.contains("lastname_invalid")) {
					addressSettingsPanel.setLastNameErros(Constants.ERROR_COLOR, new WebImage(LogInWindow.class.getResource("/resources/icons/warning.png")));
					msg += "Efternamn saknas\n";
				}
				
				if (errors.contains("address_invalid")) {
					addressSettingsPanel.setAddressErros(Constants.ERROR_COLOR, new WebImage(LogInWindow.class.getResource("/resources/icons/warning.png")));
					msg += "Address saknas\n";
				}
				
				if (errors.contains("mobilephone_invalid")) {
					addressSettingsPanel.setMobilePhoneErrors(Constants.ERROR_COLOR, new WebImage(LogInWindow.class.getResource("/resources/icons/warning.png")));
					msg += "Fel format på mobilnummer\n";
				}
				
				if (errors.contains("phone_invalid")) {
					addressSettingsPanel.setPhoneErrors(Constants.ERROR_COLOR, new WebImage(LogInWindow.class.getResource("/resources/icons/warning.png")));
					msg += "Fel format på telefonnummer\n";
				}
				
				if (errors.contains("postaddress_invalid")) {
					addressSettingsPanel.setPostAddressErros(Constants.ERROR_COLOR, new WebImage(LogInWindow.class.getResource("/resources/icons/warning.png")));
					msg += "Postadress saknas\n";
				}
				
				if (errors.contains("postcode_invalid")) {
					addressSettingsPanel.setPostCodeErros(Constants.ERROR_COLOR, new WebImage(LogInWindow.class.getResource("/resources/icons/warning.png")));
					msg += "Fel format på postnummer\n";
				}
				
				JOptionPane.showMessageDialog(this, msg, "Fel vid sparning av uppgifter", JOptionPane.WARNING_MESSAGE);
			}
			
		}
		
		if (evt.getPropertyName() == "account_saved") {
			JOptionPane.showMessageDialog(this, "Uppgifter sparade", "Intällningar", JOptionPane.INFORMATION_MESSAGE);
			dispose();
		}
		

	

		
	}

}
