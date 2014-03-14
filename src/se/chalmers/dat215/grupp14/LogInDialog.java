package se.chalmers.dat215.grupp14;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
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
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import net.miginfocom.swing.MigLayout;
import se.chalmers.dat215.grupp14.backend.Account;
import se.chalmers.dat215.grupp14.backend.Constants;
import se.chalmers.dat215.grupp14.backend.IMatModel;

import com.alee.extended.image.WebImage;
import com.alee.laf.text.WebPasswordField;
import com.alee.laf.text.WebTextField;

@SuppressWarnings("serial")
public class LogInDialog extends JDialog implements ActionListener, PropertyChangeListener {
    private JTabbedPane signInTabbedPane;
    private JPanel newCustomerPanel;
    private JPanel signInPanel;
    private JLabel userNameLabel;
    private WebTextField userNameTextField;
    private JLabel lblNewLabel_1;
    private JButton btnCancel;
    private JButton btnLogIn;
    private WebPasswordField passwordField;
    private JLabel lblAnvndarnamn;
    private JLabel lblEpost;
    private JLabel lblNyttLsenord;
    private JLabel lbligen;
    private WebTextField newUserNameTextField;
    private WebTextField newUserEmailTextField;
    private WebPasswordField newUserPassword;
    private WebPasswordField newUserPasswordRepeat;
    private JButton btnRegister;
    private IMatModel model;

    public LogInDialog(JFrame frame, IMatModel model) {
        super(frame, true);
        this.model = model;
        this.model.addPropertyChangeListener(this);
        initializeGUI();
    }

    private void initializeGUI() {
        setSize(360, 250);
        setResizable(false);

        signInTabbedPane = new JTabbedPane(SwingConstants.TOP);
        signInTabbedPane.setFocusable(false);
        getContentPane().add(signInTabbedPane, BorderLayout.CENTER);

        signInPanel = new JPanel();
        signInTabbedPane.addTab("Logga in", null, signInPanel, null);
        signInPanel.setLayout(new MigLayout("insets 4px 4px 4px 16px", "[96][grow][]", "[32][32][32][grow]"));

        userNameLabel = new JLabel("Användarnamn");
        signInPanel.add(userNameLabel, "cell 0 1,alignx trailing");

        userNameTextField = new WebTextField();
        signInPanel.add(userNameTextField, "cell 1 1 2 1,growx");
        userNameTextField.setColumns(10);

        lblNewLabel_1 = new JLabel("Lösenord");
        signInPanel.add(lblNewLabel_1, "cell 0 2,alignx trailing");

        passwordField = new WebPasswordField();
        signInPanel.add(passwordField, "cell 1 2 2 1,growx");

        btnCancel = new JButton("Avbryt");
        btnCancel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        signInPanel.add(btnCancel, "cell 1 3,alignx right,aligny top");
        btnCancel.addActionListener(this);

        btnLogIn = new JButton("Logga in");
        btnLogIn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        signInPanel.add(btnLogIn, "cell 2 3,aligny top");
        btnLogIn.addActionListener(this);

        newCustomerPanel = new JPanel();
        signInTabbedPane.addTab("Ny kund", null, newCustomerPanel, null);
        newCustomerPanel.setLayout(new MigLayout("insets 4px 4px 4px 16px", "[96px][grow]",
                "[grow][32][32][32][32][32]"));

        lblAnvndarnamn = new JLabel("Användarnamn");
        newCustomerPanel.add(lblAnvndarnamn, "cell 0 1,alignx trailing");

        newUserNameTextField = new WebTextField();
        newCustomerPanel.add(newUserNameTextField, "cell 1 1,growx");
        newUserNameTextField.setColumns(10);

        lblEpost = new JLabel("E-post");
        newCustomerPanel.add(lblEpost, "cell 0 2,alignx trailing");

        newUserEmailTextField = new WebTextField();
        newCustomerPanel.add(newUserEmailTextField, "cell 1 2,growx");
        newUserEmailTextField.setColumns(10);

        lblNyttLsenord = new JLabel("Nytt lösenord");
        newCustomerPanel.add(lblNyttLsenord, "cell 0 3,alignx trailing");

        newUserPassword = new WebPasswordField();
        newCustomerPanel.add(newUserPassword, "cell 1 3,growx");

        lbligen = new JLabel("(igen)");
        newCustomerPanel.add(lbligen, "cell 0 4,alignx trailing");

        newUserPasswordRepeat = new WebPasswordField();
        newCustomerPanel.add(newUserPasswordRepeat, "cell 1 4,growx");

        btnRegister = new JButton("Registrera");
        btnRegister.addActionListener(this);
        btnRegister.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        newCustomerPanel.add(btnRegister, "cell 1 5,alignx right,aligny top");

        userNameLabel.requestFocusInWindow();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == btnLogIn) {
            model.accountSignIn(userNameTextField.getText(), new String(passwordField.getPassword()));
        }

        if (event.getSource() == btnRegister) {
            model.accountSignUp(newUserNameTextField.getText(), new String(newUserPassword.getPassword()),
                    newUserEmailTextField.getText());
        }
        
        if (event.getSource() == btnCancel) {
            dispose();
        }
    }

    public void resetError(WebPasswordField wt) {
        wt.setBackground(Color.WHITE);
        wt.setTrailingComponent(null);
    }

    public void resetError(WebTextField wt) {
        wt.setBackground(Color.WHITE);
        wt.setTrailingComponent(null);
    }

    public void setError(WebTextField wt) {
        wt.setBackground(Constants.ERROR_COLOR);
        wt.setTrailingComponent(new WebImage(AddressSettingsPanel.class
                .getResource("resources/images/icons/warning.png")));
    }

    public void setError(WebPasswordField wt) {
        wt.setBackground(Constants.ERROR_COLOR);
        wt.setTrailingComponent(new WebImage(AddressSettingsPanel.class
                .getResource("resources/images/icons/warning.png")));
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (!isVisible())
            return; // Ugly fix

        if (evt.getPropertyName() == "account_signin") {
            @SuppressWarnings("unchecked")
            List<String> errors = (ArrayList<String>) evt.getNewValue();

            resetError(userNameTextField);
            resetError(passwordField);

            if (!errors.isEmpty()) {
                if (errors.contains("username_wrong"))
                    setError(userNameTextField);

                if (errors.contains("password_wrong"))
                    setError(passwordField);

                JOptionPane.showMessageDialog(this, "Felaktigt användarnamn eller lösenord", "Fel vid inloggning",
                        JOptionPane.WARNING_MESSAGE);
            }
        }

        if (evt.getPropertyName() == "account_signedin") {
            dispose();
        }

        if (evt.getPropertyName() == "account_signup") {
            @SuppressWarnings("unchecked")
            List<String> errors = (ArrayList<String>) evt.getNewValue();

            resetError(newUserNameTextField);
            resetError(newUserEmailTextField);
            resetError(newUserPassword);
            resetError(newUserPasswordRepeat);

            if (!errors.isEmpty()) {

                String msg = "";

                if (errors.contains("username_taken")) {
                    setError(newUserNameTextField);
                    msg += "Användarnamn upptaget\n";
                } else {
                    if (errors.contains("username_too_short")) {
                        setError(newUserNameTextField);
                        msg += "För kort användarnamn\n";
                    }

                    if (errors.contains("password_too_short")) {
                        setError(newUserPassword);
                        msg += "För kort lösenord\n";
                    }

                    if (errors.contains("email_invalid")) {
                        setError(newUserEmailTextField);
                        msg += "Felaktig epostadress\n";
                    }

                    if (!new String(newUserPassword.getPassword()).equals(new String(newUserPasswordRepeat
                            .getPassword()))) {
                        setError(newUserPasswordRepeat);
                        msg += "Lösenorden stämmer inte överens\n";
                    }
                }

                JOptionPane.showMessageDialog(this, msg, "Fel vid registering", JOptionPane.WARNING_MESSAGE);
            }
        }

        if (evt.getPropertyName() == "account_signedup") {
            Account account = (Account) evt.getNewValue();
            model.accountSignIn(account.getUserName(), account.getPassword());
        }
    }
}
