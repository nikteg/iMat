package se.chalmers.dat215.grupp14;

import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;

import com.alee.extended.image.WebImage;
import com.alee.laf.text.WebTextField;

public class AddressSettingsPanel extends JPanel implements PropertyChangeListener {
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

    public AddressSettingsPanel(IMatModel model) {
        super();
        model.addPropertyChangeListener(this);
        initialize();
    }

    private void initialize() {
        setBorder(new TitledBorder(null, "Adressuppgifter", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        setLayout(new MigLayout("insets 4px", "[92][72][grow]", "[32][32][][32][32][][32px][32]"));

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
    
    public void setFirstName(String text) {
        firstNameTextField.setText(text);
    }

    public String getLastName() {
        return lastNameTextField.getText();
    }
    
    public void setLastName(String text) {
        lastNameTextField.setText(text);
    }

    public String getAddress() {
        return addressTextField.getText();
    }
    
    public void setAddress(String text) {
        addressTextField.setText(text);
    }

    public String getPostCode() {
        return postCodeTextField.getText();
    }
    
    public void setPostCode(String text) {
        postCodeTextField.setText(text);
    }
    
    public String getPhoneNumber() {
        return phoneNumberTextField.getText();
    }
    
    public void setPhoneNumber(String text) {
        phoneNumberTextField.setText(text);
    }

    public String getMobilePhoneNumber() {
        return mobilePhoneNumberTextField.getText();
    }
    
    public void setMobilePhoneNumber(String text) {
        mobilePhoneNumberTextField.setText(text);
    }

    public String getPostAddress() {
        return postAddressTextField.getText();
    }
    
    public void setPostAddress(String text) {
        mobilePhoneNumberTextField.setText(text);
    }
    
    public void resetError(WebTextField wt) {
        wt.setBackground(Color.WHITE);
    }

    public void setError(WebTextField wt) {
        wt.setBackground(Constants.ERROR_COLOR);
        wt.setTrailingComponent(new WebImage(AddressSettingsPanel.class.getResource("/resources/images/warning.png")));
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName() == "account_update") {
            List<String> errors = (ArrayList)evt.getNewValue();
            
            resetError(firstNameTextField);
            resetError(lastNameTextField);
            resetError(addressTextField);
            resetError(mobilePhoneNumberTextField);
            resetError(phoneNumberTextField);
            resetError(postAddressTextField);
            resetError(postCodeTextField);

            if (errors.contains("account_update")) {
                if (errors.contains("firstname_tooshort"))  setError(firstNameTextField);
                if (errors.contains("lastname_tooshort"))   setError(lastNameTextField);
                if (errors.contains("address_invalid"))     setError(addressTextField);
                if (errors.contains("mobilephone_invalid")) setError(mobilePhoneNumberTextField);
                if (errors.contains("phone_invalid"))       setError(phoneNumberTextField);
                if (errors.contains("postaddress_invalid")) setError(postAddressTextField);
                if (errors.contains("postcode_invalid"))    setError(postCodeTextField);
            }
        }
    }
}
