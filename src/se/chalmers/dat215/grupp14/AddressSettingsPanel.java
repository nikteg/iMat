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

import se.chalmers.dat215.grupp14.backend.Constants;
import se.chalmers.dat215.grupp14.backend.IMatModel;
import net.miginfocom.swing.MigLayout;

import com.alee.extended.image.WebImage;
import com.alee.laf.text.WebTextField;

/**
 * Address settings panel responsible for showing the customer information
 * @author Niklas Tegnander, Mikael Lönn and Oskar Jönefors
 */
@SuppressWarnings("serial")
public class AddressSettingsPanel extends JPanel implements PropertyChangeListener {
    private WebTextField txtFirstName;
    private WebTextField txtLastName;
    private WebTextField txtAddress;
    private WebTextField txtPostCode;
    private WebTextField txtPhoneNumber;
    private WebTextField txtMobilePhoneNumber;
    private WebTextField txtPostAddress;
    private JLabel lblFirstName;
    private JLabel lblLastName;
    private JLabel lblAddress;
    private JLabel lblPostCode;
    private JLabel lblPhoneNumber;
    private JLabel lblMobilePhoneNumber;
    private JSeparator separator;
    private JSeparator separator_1;

    /**
     * Constructor
     */
    public AddressSettingsPanel() {
        super();
        initializeGUI();
    }

    /**
     * Constructor with a given model
     * @param model
     */
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

        lblFirstName = new JLabel("Förnamn");
        add(lblFirstName, "cell 0 0,alignx left");

        txtFirstName = new WebTextField();
        txtFirstName.setColumns(10);
        add(txtFirstName, "cell 1 0 2 1,growx");

        lblLastName = new JLabel("Efternamn");
        add(lblLastName, "cell 0 1,alignx left");

        txtLastName = new WebTextField();
        txtLastName.setColumns(10);
        add(txtLastName, "cell 1 1 2 1,growx");

        separator = new JSeparator();
        add(separator, "cell 0 2 3 1,grow");

        lblAddress = new JLabel("Gatuadress");
        add(lblAddress, "cell 0 3,alignx left");

        txtAddress = new WebTextField();
        txtAddress.setColumns(10);
        add(txtAddress, "cell 1 3 2 1,growx");

        lblPostCode = new JLabel("Postnr/Postort");
        add(lblPostCode, "cell 0 4,alignx left");

        txtPostCode = new WebTextField();
        txtPostCode.setColumns(10);
        add(txtPostCode, "cell 1 4,growx");

        txtPostAddress = new WebTextField();
        txtPostAddress.setColumns(10);
        add(txtPostAddress, "cell 2 4,growx");

        separator_1 = new JSeparator();
        add(separator_1, "cell 0 5 3 1,grow");

        lblPhoneNumber = new JLabel("Tel (inkl riktnr.)");
        add(lblPhoneNumber, "cell 0 6,alignx left");

        txtPhoneNumber = new WebTextField();
        txtPhoneNumber.setColumns(10);
        add(txtPhoneNumber, "cell 1 6 2 1,growx");

        lblMobilePhoneNumber = new JLabel("Mobiltelefon");
        add(lblMobilePhoneNumber, "cell 0 7,alignx left");

        txtMobilePhoneNumber = new WebTextField();
        txtMobilePhoneNumber.setColumns(10);
        add(txtMobilePhoneNumber, "cell 1 7 2 1,growx");
    }

    /**
     * Get first name
     * 
     * @return
     */
    public String getFirstName() {
        return txtFirstName.getText();
    }

    /**
     * Set first name
     * 
     * @param text
     */
    public void setFirstName(String text) {
        txtFirstName.setText(text);
    }

    /**
     * Get last name
     * 
     * @return
     */
    public String getLastName() {
        return txtLastName.getText();
    }

    /**
     * Set last name
     * 
     * @param text
     */
    public void setLastName(String text) {
        txtLastName.setText(text);
    }

    /**
     * Get address
     * 
     * @return
     */
    public String getAddress() {
        return txtAddress.getText();
    }

    /**
     * Set address
     * 
     * @param text
     */
    public void setAddress(String text) {
        txtAddress.setText(text);
    }

    /**
     * Get post code
     * 
     * @return
     */
    public String getPostCode() {
        return txtPostCode.getText();
    }

    /**
     * Set post code
     * 
     * @param text
     */
    public void setPostCode(String text) {
        txtPostCode.setText(text);
    }

    /**
     * Get phone number
     * 
     * @return
     */
    public String getPhoneNumber() {
        return txtPhoneNumber.getText();
    }

    /**
     * Set phone number
     * 
     * @param text
     */
    public void setPhoneNumber(String text) {
        txtPhoneNumber.setText(text);
    }

    /**
     * Get mobile phone number
     * 
     * @return
     */
    public String getMobilePhoneNumber() {
        return txtMobilePhoneNumber.getText();
    }

    /**
     * Set mobile phone number
     * 
     * @param text
     */
    public void setMobilePhoneNumber(String text) {
        txtMobilePhoneNumber.setText(text);
    }

    /**
     * Get post address
     * 
     * @return
     */
    public String getPostAddress() {
        return txtPostAddress.getText();
    }

    /**
     * Set post address
     * 
     * @param text
     */
    public void setPostAddress(String text) {
        txtPostAddress.setText(text);
    }

    /**
     * Reset text field icon and background
     * 
     * @param wt
     */
    public void resetError(WebTextField wt) {
        wt.setBackground(Color.WHITE);
        wt.setTrailingComponent(null);
    }

    /**
     * Add error icon and background to text field
     * 
     * @param wt
     */
    public void setError(WebTextField wt) {
        wt.setBackground(Constants.ERROR_COLOR);
        wt.setTrailingComponent(new WebImage(AddressSettingsPanel.class
                .getResource("resources/images/icons/warning.png")));
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if (event.getPropertyName() == "account_verify") {
            @SuppressWarnings("unchecked")
            List<String> errors = (ArrayList<String>) event.getNewValue();

            // Reset fields
            resetError(txtFirstName);
            resetError(txtLastName);
            resetError(txtAddress);
            resetError(txtMobilePhoneNumber);
            resetError(txtPhoneNumber);
            resetError(txtPostAddress);
            resetError(txtPostCode);

            if (!errors.isEmpty()) {
                if (errors.contains("firstname_tooshort"))
                    setError(txtFirstName);
                if (errors.contains("lastname_tooshort"))
                    setError(txtLastName);
                if (errors.contains("address_invalid"))
                    setError(txtAddress);
                if (errors.contains("mobilephone_invalid"))
                    setError(txtMobilePhoneNumber);
                if (errors.contains("phone_invalid"))
                    setError(txtPhoneNumber);
                if (errors.contains("postaddress_invalid"))
                    setError(txtPostAddress);
                if (errors.contains("postcode_invalid"))
                    setError(txtPostCode);
            }
        }
    }
}
