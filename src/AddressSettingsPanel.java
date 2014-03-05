

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;

import com.alee.extended.image.WebImage;
import com.alee.laf.text.WebTextField;

public class AddressSettingsPanel extends JPanel {
	private JLabel firstNameLabel;
	private WebTextField firstNameTextField;
	private JLabel lastNameLabel;
	private JLabel addressLabel;
	private JLabel postCodePostAddressLabel;
	private JLabel phoneNumberLabel;
	private JLabel mobilePhoneNumberLabel;
	private WebTextField lastNameTextField;
	private WebTextField addressTextField;
	private WebTextField postCodeTextField;
	private WebTextField phoneNumberTextField;
	private WebTextField mobilePhoneNumberTextField;
	private JSeparator separator;
	private JSeparator separator_1;
	private WebTextField postAddressTextField;

	public AddressSettingsPanel() {
		initialize();
	}
	private void initialize() {
		setBorder(new TitledBorder(null, "Adressuppgifter", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(new MigLayout("insets 4px", "[92][52][grow]", "[32][32][][32][32][][32px][32]"));
		
		firstNameLabel = new JLabel("Förnamn");
		add(firstNameLabel, "cell 0 0,alignx left");
		
		firstNameTextField = new WebTextField();
		firstNameTextField.setColumns(10);
		add(firstNameTextField, "cell 1 0 2 1,growx");
		
		lastNameLabel = new JLabel("Efternamn");
		add(lastNameLabel, "cell 0 1,alignx left");
		
		lastNameTextField = new WebTextField();
		lastNameTextField.setColumns(10);
		add(lastNameTextField, "cell 1 1 2 1,growx");
		
		separator = new JSeparator();
		add(separator, "cell 0 2 3 1,grow");
		
		addressLabel = new JLabel("Gatuadress");
		add(addressLabel, "cell 0 3,alignx left");
		
		addressTextField = new WebTextField();
		addressTextField.setColumns(10);
		add(addressTextField, "cell 1 3 2 1,growx");
		
		postCodePostAddressLabel = new JLabel("Postnr/Postort");
		add(postCodePostAddressLabel, "cell 0 4,alignx left");
		
		postCodeTextField = new WebTextField();
		postCodeTextField.setColumns(10);
		add(postCodeTextField, "cell 1 4,growx");
		
		postAddressTextField = new WebTextField();
		postAddressTextField.setColumns(10);
		add(postAddressTextField, "cell 2 4,growx");
		
		separator_1 = new JSeparator();
		add(separator_1, "cell 0 5 3 1,grow");
		
		phoneNumberLabel = new JLabel("Tel (inkl riktnr.)");
		add(phoneNumberLabel, "cell 0 6,alignx left");
		
		phoneNumberTextField = new WebTextField();
		phoneNumberTextField.setColumns(10);
		add(phoneNumberTextField, "cell 1 6 2 1,growx");
		
		mobilePhoneNumberLabel = new JLabel("Mobiltelefon");
		add(mobilePhoneNumberLabel, "cell 0 7,alignx left");
		
		mobilePhoneNumberTextField = new WebTextField();
		mobilePhoneNumberTextField.setColumns(10);
		add(mobilePhoneNumberTextField, "cell 1 7 2 1,growx");
	}
	
	public String getFirstName() {
		return firstNameTextField.getText();
	}
	public void setFirstName(String firstName) {
		this.firstNameTextField.setText(firstName);
	}
	public String getLastName() {
		return lastNameTextField.getText();
	}
	public void setLastName(String lastName) {
		this.lastNameTextField.setText(lastName);
		
	}
	public String getAddress() {
		return addressTextField.getText();
	}
	public void setAddress(String streetAddress) {
		this.addressTextField.setText(streetAddress);
	}
	public String getPostCode() {
		return postCodeTextField.getText();
	}
	public void setPostCode(String zipCode) {
		this.postCodeTextField.setText(zipCode);
	}
	public String getPhoneNumber() {
		return phoneNumberTextField.getText();
	}
	public void setPhoneNumber(String phone) {
		this.phoneNumberTextField.setText(phone);
	}
	public String getMobilePhoneNumber() {
		return mobilePhoneNumberTextField.getText();
	}
	public void setMobilePhoneNumber(String cellPhone) {
		this.mobilePhoneNumberTextField.setText(cellPhone);
	}
	public String getPostAddress() {
		return postAddressTextField.getText();
	}
	public void setPostAddress(String city) {
		this.postAddressTextField.setText(city);
	}
	
	public void setFirstNameErros(Color bg, WebImage wi) {
		firstNameTextField.setBackground(bg);
		firstNameTextField.setTrailingComponent(wi);
	}
	
	public void setLastNameErros(Color bg, WebImage wi) {
		lastNameTextField.setBackground(bg);
		lastNameTextField.setTrailingComponent(wi);
	}
	
	public void setAddressErros(Color bg, WebImage wi) {
		addressTextField.setBackground(bg);
		addressTextField.setTrailingComponent(wi);
	}
	
	public void setPostCodeErros(Color bg, WebImage wi) {
		postCodeTextField.setBackground(bg);
		postCodeTextField.setTrailingComponent(wi);
	}
	
	public void setPostAddressErros(Color bg, WebImage wi) {
		postAddressTextField.setBackground(bg);
		postAddressTextField.setTrailingComponent(wi);
	}
	
	public void setMobilePhoneErrors(Color bg, WebImage wi) {
		mobilePhoneNumberTextField.setBackground(bg);
		mobilePhoneNumberTextField.setTrailingComponent(wi);
	}
	
	public void setPhoneErrors(Color bg, WebImage wi) {
		phoneNumberTextField.setBackground(bg);
		phoneNumberTextField.setTrailingComponent(wi);
	}

	
	
}
