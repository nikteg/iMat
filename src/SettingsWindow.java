import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.Window;

import javax.swing.JFrame;

import com.alee.laf.rootpane.WebDialog;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class SettingsWindow extends WebDialog implements ActionListener {
	private JFrame frame;
	private MainWindow parent;
	private JPanel settingsPanel;
	private JLabel lblSettings;
	private CardSettingsPanel cardSettingsPanel;
	private AddressSettingsPanel addressSettingsPanel;
	private LogInSettingsPanel logInSettingsPanel;
	private JButton btnSave;

	public SettingsWindow(JFrame frame, MainWindow parent) {
		super(frame,true);
		this.frame = frame;
		this.parent = parent;
		init();
	}
	private void init() {
		setSize(660, 600);
		setResizable(false);
		requestFocusInWindow();
		
		settingsPanel = new JPanel();
		getContentPane().add(settingsPanel, BorderLayout.CENTER);
		settingsPanel.setLayout(new MigLayout("", "[grow][grow]", "[64][195.00,grow][][28]"));
		
		lblSettings = new JLabel("settings");
		lblSettings.setFont(new Font("Tahoma", Font.PLAIN, 16));
		settingsPanel.add(lblSettings, "cell 0 0");
		
		logInSettingsPanel = new LogInSettingsPanel();
		settingsPanel.add(logInSettingsPanel, "cell 0 1,grow");
		
		cardSettingsPanel = new CardSettingsPanel();
		settingsPanel.add(cardSettingsPanel, "cell 1 1,grow");
		
		addressSettingsPanel = new AddressSettingsPanel();
		settingsPanel.add(addressSettingsPanel, "cell 0 2,grow");
		
		btnSave = new JButton("Spara");
		btnSave.addActionListener(this);
		
		
		settingsPanel.add(btnSave, "cell 1 3,alignx right,growy");
	}
	
	public String getFirstName() {
		return addressSettingsPanel.getFirstName();
	}
	
	public void setFirstName(String firstName) {

		addressSettingsPanel.setFirstName(firstName);
	}
	
	public String getStreetAddress() {

		return addressSettingsPanel.getStreetAddress();
	}
	
	public void setStreetAddress(String streetAddress) {

		addressSettingsPanel.setStreetAddress(streetAddress);
	}
	
	public String getZipCode() {

		return addressSettingsPanel.getZipCode();
	}
	
	public void setZipCode(String zipCode) {

		addressSettingsPanel.setZipCode(zipCode);
	}
	
	public String getPhone() {

		return addressSettingsPanel.getPhone();
	}
	
	public void setPhone(String phone) {

		addressSettingsPanel.setPhone(phone);
	}
	
	public String getCellPhone() {
		
		return addressSettingsPanel.getCellPhone();
	}
	
	public void setCellPhone(String cellPhone) {

		addressSettingsPanel.setCellPhone(cellPhone);
	}
	
	public String getCity() {

		return addressSettingsPanel.getCity();
	}
	
	public void setCity(String city) {

		addressSettingsPanel.setCity(city);
	}
	
	public String getEmail(){
		
		return logInSettingsPanel.getEmail();
	}
	
	public void setEmail(String email){
		
		this.logInSettingsPanel.setEmail(email);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnSave){
			//TODO save user settings
			//parent.getModel().saveUserSettings();
			setVisible(false);
		}
		
		
	}

}
