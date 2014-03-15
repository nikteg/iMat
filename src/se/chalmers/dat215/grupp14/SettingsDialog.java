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

import se.chalmers.dat215.grupp14.backend.IMatModel;
import net.miginfocom.swing.MigLayout;

/**
 * Settings dialog
 * @author Niklas Tegnander, Mikael Lönn and Oskar Jönefors
 */
@SuppressWarnings("serial")
public class SettingsDialog extends JDialog implements ActionListener, PropertyChangeListener {
    private IMatModel model;
    private JPanel settingsPanel;
    private JLabel lblSettings;
    private CardSettingsPanel cardSettingsPanel;
    private AddressSettingsPanel addressSettingsPanel;
    private SignInSettingsPanel logInSettingsPanel;
    private JButton btnSave;
    private JButton btnCancel;

    /**
     * Constructor with given frame and model
     * @param frame
     * @param model
     */
    public SettingsDialog(JFrame frame, IMatModel model) {
        super(frame, true);
        this.model = model;
        this.model.addPropertyChangeListener(this);
        initializeGUI();
    }

    /**
     * Initialize GUI
     */
    private void initializeGUI() {
        setSize(660, 600);
        setResizable(false);

        settingsPanel = new JPanel();
        getContentPane().add(settingsPanel, BorderLayout.CENTER);
        settingsPanel.setLayout(new MigLayout("", "[grow][330px]", "[64][grow][][28]"));

        lblSettings = new JLabel("Profilinställningar");
        lblSettings.setFont(new Font("Tahoma", Font.PLAIN, 16));
        settingsPanel.add(lblSettings, "cell 0 0");

        logInSettingsPanel = new SignInSettingsPanel(model);
        settingsPanel.add(logInSettingsPanel, "cell 0 1,grow");

        cardSettingsPanel = new CardSettingsPanel(model);
        settingsPanel.add(cardSettingsPanel, "cell 1 1,grow");

        addressSettingsPanel = new AddressSettingsPanel(model);
        settingsPanel.add(addressSettingsPanel, "cell 0 2,grow");

        btnSave = new JButton("Spara");
        btnSave.addActionListener(this);
        btnSave.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        btnCancel = new JButton("Avbryt");
        btnCancel.addActionListener(this);
        btnCancel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        settingsPanel.add(btnCancel, "flowx,cell 1 3,alignx right");

        settingsPanel.add(btnSave, "cell 1 3,alignx right,growy");
        pack();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == btnSave) {
            model.accountUpdate(model.getAccountHandler().getCurrentAccount().getUserName(), logInSettingsPanel.getPassword(),
                    logInSettingsPanel.getEmail(),
                    addressSettingsPanel.getFirstName(), addressSettingsPanel.getLastName(),
                    addressSettingsPanel.getAddress(), addressSettingsPanel.getMobilePhoneNumber(),
                    addressSettingsPanel.getPhoneNumber(), addressSettingsPanel.getPostAddress(),
                    addressSettingsPanel.getPostCode());
        }
        
        if (event.getSource() == btnCancel) {
            dispose();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (!isVisible())
            return; // Ugly fix

        if (evt.getPropertyName() == "account_update") {
            List<String> errors = (ArrayList<String>) evt.getNewValue();

            if (!errors.isEmpty()) {
                String msg = "";

                if (errors.contains("email_invalid")) {
                    msg += "Fel format på epostadress\n";
                }

                if (errors.contains("password_tooshort")) {
                    msg += "Lösenordet är för kort\n";
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
            JOptionPane.showMessageDialog(this, "Dina uppgifter sparades", "Uppgifter sparade",
                    JOptionPane.INFORMATION_MESSAGE);
            dispose();
        }
    }

}
