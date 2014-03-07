package se.chalmers.dat215.grupp14;

import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import net.miginfocom.swing.MigLayout;
import com.alee.extended.image.WebImage;
import com.alee.laf.text.WebPasswordField;
import com.alee.laf.text.WebTextField;

@SuppressWarnings("serial")
public class LogInSettingsPanel extends JPanel implements PropertyChangeListener {
    private JLabel lblEmail;
    private JLabel lblLsenord;
    private JLabel lbligen;
    private WebPasswordField passwordField;
    private WebPasswordField passwordFieldRepeat;
    private WebTextField emailTextField;

    public LogInSettingsPanel(IMatModel model) {
        super();
        model.addPropertyChangeListener(this);
        initializeGUI();
    }

    private void initializeGUI() {
        setBorder(new TitledBorder(null, "Inloggningsuppgifter", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        setLayout(new MigLayout("", "[96][grow]", "[32][32][32]"));

        lblEmail = new JLabel("E-mail");
        add(lblEmail, "cell 0 0,alignx left");

        emailTextField = new WebTextField();
        add(emailTextField, "cell 1 0,growx");
        emailTextField.setColumns(10);

        lblLsenord = new JLabel("Lösenord");
        add(lblLsenord, "cell 0 1,alignx left");

        passwordField = new WebPasswordField();
        add(passwordField, "cell 1 1,growx");

        lbligen = new JLabel("(igen)");
        add(lbligen, "cell 0 2,alignx left");

        passwordFieldRepeat = new WebPasswordField();
        add(passwordFieldRepeat, "cell 1 2,growx");
    }

    public String getEmail() {
        return emailTextField.getText();
    }

    public void setEmail(String email) {
        this.emailTextField.setText(email);
    }

    public String getPassword() {
        return new String(this.passwordField.getPassword());
    }

    public String getPasswordRepeat() {
        return new String(this.passwordFieldRepeat.getPassword());
    }

    // TODO setErrors & resetErrors
    public void setEmailErros(Color errorColor, WebImage webImage) {
        emailTextField.setBackground(errorColor);
        emailTextField.setTrailingComponent(webImage);
    }

    public void setPasswordErros(Color errorColor, WebImage webImage) {
        passwordField.setBackground(errorColor);
        passwordField.setTrailingComponent(webImage);
    }

    public void setPasswordRepeatErrors(Color errorColor, WebImage webImage) {
        passwordFieldRepeat.setBackground(errorColor);
        passwordFieldRepeat.setTrailingComponent(webImage);
    }

    @Override
    public void propertyChange(PropertyChangeEvent arg0) {
        // TODO Auto-generated method stub
    }

}
