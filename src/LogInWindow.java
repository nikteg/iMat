
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

import net.miginfocom.swing.MigLayout;

import com.alee.extended.image.WebImage;
import com.alee.laf.text.WebPasswordField;
import com.alee.laf.text.WebTextField;

public class LogInWindow extends JDialog implements ActionListener, PropertyChangeListener {
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
	private JFrame frame;
	private IMatModel model;

	public LogInWindow(JFrame frame, IMatModel model) {
		super(frame, true);
		this.frame = frame;
		this.model = model;
		this.model.addPropertyChangeListener(this);
		initialize();
	}

	private void initialize() {
		setSize(360, 250);
		setResizable(false);

		signInTabbedPane = new JTabbedPane(JTabbedPane.TOP);
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
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});

		btnLogIn = new JButton("Logga in");
		btnLogIn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		signInPanel.add(btnLogIn, "cell 2 3,aligny top");
		btnLogIn.addActionListener(this);

		newCustomerPanel = new JPanel();
		signInTabbedPane.addTab("Ny kund", null, newCustomerPanel, null);
		newCustomerPanel.setLayout(new MigLayout("insets 4px 4px 4px 16px", "[96px][grow]", "[grow][32][32][32][32][32]"));

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
			userNameTextField.setBackground(Color.WHITE);
			passwordField.setBackground(Color.WHITE);
			model.accountSignIn(userNameTextField.getText(), new String(passwordField.getPassword()));
		}
		
		if (event.getSource() == btnRegister) {
			newUserNameTextField.setBackground(Color.WHITE);
			newUserPassword.setBackground(Color.WHITE);
			newUserEmailTextField.setBackground(Color.WHITE);
			newUserPasswordRepeat.setBackground(Color.WHITE);
			
			model.accountSignUp(newUserNameTextField.getText(), new String(newUserPassword.getPassword()), newUserEmailTextField.getText());
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (!isVisible()) return; // TODO
		if (evt.getPropertyName() == "account_signin") {
			List<String> errors = (ArrayList<String>)evt.getNewValue();
			if (!errors.isEmpty()) {
				userNameTextField.setBackground(Constants.ERROR_COLOR);
				userNameTextField.setTrailingComponent(new WebImage(LogInWindow.class.getResource("/resources/icons/warning.png")));
				passwordField.setBackground(Constants.ERROR_COLOR);
				passwordField.setTrailingComponent(new WebImage(LogInWindow.class.getResource("/resources/icons/warning.png")));
				JOptionPane.showMessageDialog(this, "Felaktigt användarnamn eller lösenord", "Fel vid inloggning", JOptionPane.WARNING_MESSAGE);
			}
		}
		
		if (evt.getPropertyName() == "account_signedin") {
			dispose();
		}
		
		if (evt.getPropertyName() == "account_signup") {
			List<String> errors = (ArrayList<String>)evt.getNewValue();
			if (!errors.isEmpty()) {
				String msg = "";
				
				if (errors.contains("username_too_short")) {
					newUserNameTextField.setBackground(Constants.ERROR_COLOR);
					newUserNameTextField.setTrailingComponent(new WebImage(LogInWindow.class.getResource("/resources/icons/warning.png")));
					msg += "För kort användarnamn\n";
				}
				
				if (errors.contains("password_too_short")) {
					newUserPassword.setBackground(Constants.ERROR_COLOR);
					newUserPassword.setTrailingComponent(new WebImage(LogInWindow.class.getResource("/resources/icons/warning.png")));
					newUserPasswordRepeat.setBackground(Constants.ERROR_COLOR);
					newUserPasswordRepeat.setTrailingComponent(new WebImage(LogInWindow.class.getResource("/resources/icons/warning.png")));
					msg += "För kort lösenord\n";
				}
				
				if (errors.contains("email_invalid")) {
					newUserEmailTextField.setBackground(Constants.ERROR_COLOR);
					newUserEmailTextField.setTrailingComponent(new WebImage(LogInWindow.class.getResource("/resources/icons/warning.png")));
					msg += "Felaktig epostadress\n";
				}
				
				if (!new String(newUserPassword.getPassword()).equals(new String(newUserPasswordRepeat.getPassword()))) {
					newUserPassword.setBackground(Constants.ERROR_COLOR);
					newUserPassword.setTrailingComponent(new WebImage(LogInWindow.class.getResource("/resources/icons/warning.png")));
					newUserPasswordRepeat.setBackground(Constants.ERROR_COLOR);
					newUserPasswordRepeat.setTrailingComponent(new WebImage(LogInWindow.class.getResource("/resources/icons/warning.png")));
					msg += "Lösenorden stämmer inte överens\n";
				}
				
				JOptionPane.showMessageDialog(this, msg, "Fel vid registering", JOptionPane.WARNING_MESSAGE);
			}
		}
		
		if (evt.getPropertyName() == "account_signedup") {
			Account account = (Account)evt.getNewValue();
			System.out.println("DET GICK! Du har ett konto, din jävel...");
			model.accountSignIn(account.getUserName(), account.getPassword());
		}
	}
}
