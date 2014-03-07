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

/**
 * Address settings panel responsible for showing the customer information
 * @author Niklas Tegnander, Mikael Lönn and Oskar Jönefors
 */
@SuppressWarnings("serial")
public class AddressSettingsPanel extends JPanel implements PropertyChangeListener {
    private WebTextField firstNameTextField;
    private WebTextField lastNameTextField;
    private WebTextField addressTextField;
    private WebTextField postCodeTextField;
    private WebTextField phoneNumberTextField;
    private WebTextField mobilePhoneNumberTextField;
    private WebTextField postAddressTextField;
    private JSeparator separator;
    private JSeparator separator_1;
    private JLabel firstNameLabel;
    private JLabel lastNameLabel;
    private JLabel addressLabel;
    private JLabel postCodePostAddressLabel;
    private JLabel phoneNumberLabel;
    private JLabel mobilePhoneNumberLabel;

    public AddressSettingsPanel() {
        super();
        initializeGUI();
    }

    public AddressSettingsPanel(IMatModel model) {
        this();
        model.addPropertyChangeListener(this);
        setFirstName(model.getAccountHandler().getCurrentAccount().getFirstName());
        setLastName(model.getAccountHandler().getCurrentAccount().getLastName());
        setAddress(model.getAccountHandler().getCurrentAccount().getAddress());
        setMobilePhoneNumber(model.getAccountHandler().getCurrentAccount().getMobilePhoneNumber());
        setPhoneNumber(model.getAccountHandler().getCurrentAccount().getPhoneNumber());
        setPostAddress(model.getAccountHandler().getCurrentAccount().getPostAddress());
        setPostCode(model.getAccountHandler().getCurrentAccount().getPostCode());
    }

    /**
     * Initialize GUI
     */
    private void initializeGUI() {
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

    /**
     * Get first name
     * @return
     */
    public String getFirstName() {
        return firstNameTextField.getText();
    }

    /**
     * Set first name
     * @param text
     */
    public void setFirstName(String text) {
        firstNameTextField.setText(text);
    }

    /**
     * Get last name
     * @return
     */
    public String getLastName() {
        return lastNameTextField.getText();
    }

    /**
     * Set last name
     * @param text
     */
    public void setLastName(String text) {
        lastNameTextField.setText(text);
    }

    /**
     * Get address
     * @return
     */
    public String getAddress() {
        return addressTextField.getText();
    }

    /**
     * Set address
     * @param text
     */
    public void setAddress(String text) {
        addressTextField.setText(text);
    }

    /**
     * Get post code
     * @return
     */
    public String getPostCode() {
        return postCodeTextField.getText();
    }

    /**
     * Set post code
     * @param text
     */
    public void setPostCode(String text) {
        postCodeTextField.setText(text);
    }

    /**
     * Get phone number
     * @return
     */
    public String getPhoneNumber() {
        return phoneNumberTextField.getText();
    }

    /**
     * Set phone number
     * @param text
     */
    public void setPhoneNumber(String text) {
        phoneNumberTextField.setText(text);
    }

    /**
     * Get mobile phone number
     * @return
     */
    public String getMobilePhoneNumber() {
        return mobilePhoneNumberTextField.getText();
    }

    /**
     * Set mobile phone number
     * @param text
     */
    public void setMobilePhoneNumber(String text) {
        mobilePhoneNumberTextField.setText(text);
    }

    /**
     * Get post address
     * @return
     */
    public String getPostAddress() {
        return postAddressTextField.getText();
    }

    /**
     * Set post address
     * @param text
     */
    public void setPostAddress(String text) {
        postAddressTextField.setText(text);
    }

    /**
     * Reset text field icon and background
     * @param wt text field
     */
    public void resetError(WebTextField wt) {
        wt.setBackground(Color.WHITE);
        wt.setTrailingComponent(null);
    }

    /**
     * Add error icon and background to text field
     * @param wt text field
     */
    public void setError(WebTextField wt) {
        wt.setBackground(Constants.ERROR_COLOR);
        wt.setTrailingComponent(new WebImage(AddressSettingsPanel.class.getResource("resources/images/icons/warning.png")));
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if (event.getPropertyName() == "account_update") {
            @SuppressWarnings("unchecked")
            List<String> errors = (ArrayList<String>) event.getNewValue();

            // Reset fields
            resetError(firstNameTextField);
            resetError(lastNameTextField);
            resetError(addressTextField);
            resetError(mobilePhoneNumberTextField);
            resetError(phoneNumberTextField);
            resetError(postAddressTextField);
            resetError(postCodeTextField);

            if (!errors.isEmpty()) {
                if (errors.contains("firstname_tooshort"))
                    setError(firstNameTextField);
                if (errors.contains("lastname_tooshort"))
                    setError(lastNameTextField);
                if (errors.contains("address_invalid"))
                    setError(addressTextField);
                if (errors.contains("mobilephone_invalid"))
                    setError(mobilePhoneNumberTextField);
                if (errors.contains("phone_invalid"))
                    setError(phoneNumberTextField);
                if (errors.contains("postaddress_invalid"))
                    setError(postAddressTextField);
                if (errors.contains("postcode_invalid"))
                    setError(postCodeTextField);
            }
        }
    }
}
