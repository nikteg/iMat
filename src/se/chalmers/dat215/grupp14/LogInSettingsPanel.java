package se.chalmers.dat215.grupp14;

import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import se.chalmers.dat215.grupp14.backend.Constants;
import se.chalmers.dat215.grupp14.backend.IMatModel;
import net.miginfocom.swing.MigLayout;

import com.alee.extended.image.WebImage;
import com.alee.laf.text.WebPasswordField;
import com.alee.laf.text.WebTextField;

@SuppressWarnings("serial")
public class LogInSettingsPanel extends JPanel implements PropertyChangeListener {
    private JLabel lblEmail;
    private JLabel lblLsenord;
    private WebPasswordField passwordField;
    private WebTextField emailTextField;
    
    public LogInSettingsPanel() {
        super();
        initializeGUI();
    }

    public LogInSettingsPanel(IMatModel model) {
        this();
        model.addPropertyChangeListener(this);
        emailTextField.setText(model.getAccountHandler().getCurrentAccount().getEmail());
        passwordField.setText(model.getAccountHandler().getCurrentAccount().getPassword());
    }

    private void initializeGUI() {
        setBorder(new TitledBorder(null, "Inloggningsuppgifter", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        setLayout(new MigLayout("", "[96][grow]", "[32][32]"));

        lblEmail = new JLabel("E-mail");
        add(lblEmail, "cell 0 0,alignx left");

        emailTextField = new WebTextField();
        add(emailTextField, "cell 1 0,growx");
        emailTextField.setColumns(10);

        lblLsenord = new JLabel("Lösenord");
        add(lblLsenord, "cell 0 1,alignx left");

        passwordField = new WebPasswordField();
        add(passwordField, "cell 1 1,growx");
    }

    public String getEmail() {
        return emailTextField.getText();
    }

    public String getPassword() {
        return new String(this.passwordField.getPassword());
    }
    
    public void resetError(WebPasswordField wt) {
        wt.setBackground(Color.WHITE);
        wt.setTrailingComponent(null);
    }

    public void resetError(WebTextField wt) {
        wt.setBackground(Color.WHITE);
        wt.setTrailingComponent(null);
    }

    public void setError(WebPasswordField wt) {
        wt.setBackground(Constants.ERROR_COLOR);
        wt.setTrailingComponent(new WebImage(AddressSettingsPanel.class
                .getResource("resources/images/icons/warning.png")));
    }
    
    public void setError(WebTextField wt) {
        wt.setBackground(Constants.ERROR_COLOR);
        wt.setTrailingComponent(new WebImage(AddressSettingsPanel.class
                .getResource("resources/images/icons/warning.png")));
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName() == "account_update") {
            @SuppressWarnings("unchecked")
            List<String> errors = (ArrayList<String>) evt.getNewValue();
            
            if (!errors.isEmpty()) {
                resetError(emailTextField);
                resetError(passwordField);
                
                if (errors.contains("email_invalid"))
                    setError(emailTextField);
                
                if (errors.contains("password_tooshort"))
                    setError(passwordField);
            }
        }
    }

}
