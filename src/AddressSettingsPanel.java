

import java.awt.LayoutManager;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JSeparator;

public class AddressSettingsPanel extends JPanel {
	private JLabel firstNameLabel;
	private JTextField firstNameTextField;
	private JLabel lastNameLabel;
	private JLabel streetAddressLabel;
	private JLabel zipCodeCityLabel;
	private JLabel phoneLabel;
	private JLabel cellPhoneLabel;
	private JTextField lastNameTextField;
	private JTextField streetAddressTextField;
	private JTextField zipCodeTextField;
	private JTextField phoneTextField;
	private JTextField cellPhoneTextField;
	private JSeparator separator;
	private JSeparator separator_1;
	private JTextField cityTextField;

	public AddressSettingsPanel() {
		initialize();
	}
	private void initialize() {
		setBorder(new TitledBorder(null, "Adressuppgifter", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(new MigLayout("insets 4px", "[92][52][grow]", "[32][32][][32][32][][32px][32]"));
		
		firstNameLabel = new JLabel("Förnamn");
		add(firstNameLabel, "cell 0 0,alignx left");
		
		firstNameTextField = new JTextField();
		firstNameTextField.setColumns(10);
		add(firstNameTextField, "cell 1 0 2 1,growx");
		
		lastNameLabel = new JLabel("Efternamn");
		add(lastNameLabel, "cell 0 1,alignx left");
		
		lastNameTextField = new JTextField();
		lastNameTextField.setColumns(10);
		add(lastNameTextField, "cell 1 1 2 1,growx");
		
		separator = new JSeparator();
		add(separator, "cell 0 2 3 1,grow");
		
		streetAddressLabel = new JLabel("Gatuadress");
		add(streetAddressLabel, "cell 0 3,alignx left");
		
		streetAddressTextField = new JTextField();
		streetAddressTextField.setColumns(10);
		add(streetAddressTextField, "cell 1 3 2 1,growx");
		
		zipCodeCityLabel = new JLabel("Postnr/Postort");
		add(zipCodeCityLabel, "cell 0 4,alignx left");
		
		zipCodeTextField = new JTextField();
		zipCodeTextField.setColumns(10);
		add(zipCodeTextField, "cell 1 4,growx");
		
		cityTextField = new JTextField();
		cityTextField.setColumns(10);
		add(cityTextField, "cell 2 4,growx");
		
		separator_1 = new JSeparator();
		add(separator_1, "cell 0 5 3 1,grow");
		
		phoneLabel = new JLabel("Tel (inkl riktnr.)");
		add(phoneLabel, "cell 0 6,alignx left");
		
		phoneTextField = new JTextField();
		phoneTextField.setColumns(10);
		add(phoneTextField, "cell 1 6 2 1,growx");
		
		cellPhoneLabel = new JLabel("Mobiltelefon");
		add(cellPhoneLabel, "cell 0 7,alignx left");
		
		cellPhoneTextField = new JTextField();
		cellPhoneTextField.setColumns(10);
		add(cellPhoneTextField, "cell 1 7 2 1,growx");
	}
	
	public String getFirstName() {
		return firstNameTextField.getText();
	}
	public void setFirstName(String firstName) {
		this.firstNameTextField.setText(firstName);
	}
	public String getStreetAddress() {
		return streetAddressTextField.getText();
	}
	public void setStreetAddress(String streetAddress) {
		this.streetAddressTextField.setText(streetAddress);
	}
	public String getZipCode() {
		return zipCodeTextField.getText();
	}
	public void setZipCode(String zipCode) {
		this.zipCodeTextField.setText(zipCode);
	}
	public String getPhone() {
		return phoneTextField.getText();
	}
	public void setPhone(String phone) {
		this.phoneTextField.setText(phone);
	}
	public String getCellPhone() {
		return cellPhoneTextField.getText();
	}
	public void setCellPhone(String cellPhone) {
		this.cellPhoneTextField.setText(cellPhone);
	}
	public String getCity() {
		return cityTextField.getText();
	}
	public void setCity(String city) {
		this.cityTextField.setText(city);
	}
	
	
}
