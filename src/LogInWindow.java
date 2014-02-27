
import java.awt.BorderLayout;
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
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

public class LogInWindow extends JDialog implements ActionListener, PropertyChangeListener {
	private JTabbedPane signInTabbedPane;
	private JPanel newCustomerPanel;
	private JPanel signInPanel;
	private JLabel userNameLabel;
	private JTextField userNameTextField;
	private JLabel lblNewLabel_1;
	private JPanel buttonPanel;
	private JButton btnNewCostumer;
	private JButton btnLogIn;
	private JPasswordField passwordField;
	private JLabel lblAnvndarnamn;
	private JLabel lblEpost;
	private JLabel lblNyttLsenord;
	private JLabel lbligen;
	private JTextField newUserNameTextField;
	private JTextField newUserEmailTextField;
	private JPasswordField newUserPassword;
	private JPasswordField newUserPasswordRepeat;
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
		signInPanel.setLayout(new MigLayout("", "[96][216.00]",
				"[32][32][32][grow]"));

		userNameLabel = new JLabel("Användarnamn");
		signInPanel.add(userNameLabel, "cell 0 1,alignx trailing");

		userNameTextField = new JTextField();
		signInPanel.add(userNameTextField, "cell 1 1,growx");
		userNameTextField.setColumns(10);

		lblNewLabel_1 = new JLabel("Lösenord");
		signInPanel.add(lblNewLabel_1, "cell 0 2,alignx trailing");

		passwordField = new JPasswordField();
		signInPanel.add(passwordField, "cell 1 2,growx");

		buttonPanel = new JPanel();
		signInPanel.add(buttonPanel, "cell 1 3,grow");
		buttonPanel.setLayout(new MigLayout("", "[135.00,right][left]", "[]"));

		btnNewCostumer = new JButton("Ny kund");
		btnNewCostumer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				signInTabbedPane.setSelectedIndex(1);
			}
		});
		buttonPanel.add(btnNewCostumer, "cell 0 0,alignx left");

		btnLogIn = new JButton("Logga in");
		btnLogIn.addActionListener(this);
		buttonPanel.add(btnLogIn, "cell 1 0,alignx right");

		newCustomerPanel = new JPanel();
		signInTabbedPane.addTab("Ny kund", null, newCustomerPanel, null);
		newCustomerPanel.setLayout(new MigLayout("", "[][grow]",
				"[32][32][32][32][32]"));

		lblAnvndarnamn = new JLabel("Användarnamn");
		newCustomerPanel.add(lblAnvndarnamn, "cell 0 0,alignx trailing");

		newUserNameTextField = new JTextField();
		newCustomerPanel.add(newUserNameTextField, "cell 1 0,growx");
		newUserNameTextField.setColumns(10);

		lblEpost = new JLabel("E-post");
		newCustomerPanel.add(lblEpost, "cell 0 1,alignx trailing");

		newUserEmailTextField = new JTextField();
		newCustomerPanel.add(newUserEmailTextField, "cell 1 1,growx");
		newUserEmailTextField.setColumns(10);

		lblNyttLsenord = new JLabel("Nytt lösenord");
		newCustomerPanel.add(lblNyttLsenord, "cell 0 2,alignx trailing");

		newUserPassword = new JPasswordField();
		newCustomerPanel.add(newUserPassword, "cell 1 2,growx");

		lbligen = new JLabel("(igen)");
		newCustomerPanel.add(lbligen, "cell 0 3,alignx trailing");

		newUserPasswordRepeat = new JPasswordField();
		newCustomerPanel.add(newUserPasswordRepeat, "cell 1 3,growx");

		btnRegister = new JButton("Registrera");
		btnRegister.addActionListener(this);
		newCustomerPanel.add(btnRegister, "cell 1 4,alignx right");
		
		userNameLabel.requestFocusInWindow();
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == btnLogIn) {
			model.accountSignIn(userNameTextField.getText(), new String(passwordField.getPassword()));
/*			userNameTextField.setBackground(Color.WHITE);
			passwordField.setBackground(Color.WHITE);
			
			model.accountSignIn(userNameTextField.getText(), new String(passwordField.getPassword()));
			
			if (!errors.isEmpty()) {
				userNameTextField.setBackground(Constants.ERROR_COLOR);
				passwordField.setBackground(Constants.ERROR_COLOR);
				JOptionPane.showMessageDialog(this, errors.get("signin"), "Fel vid inloggning", JOptionPane.WARNING_MESSAGE);
			} else {
				dispose();
			}*/
		}
		
		if (event.getSource() == btnRegister) {
			model.accountSignUp(newUserNameTextField.getText(), new String(newUserPassword.getPassword()), newUserEmailTextField.getText());
/*			newUserNameTextField.setBackground(Color.WHITE);
			newUserPassword.setBackground(Color.WHITE);
			newUserEmailTextField.setBackground(Color.WHITE);
			newUserPasswordRepeat.setBackground(Color.WHITE);
			
			model.accountSignUp(newUserNameTextField.getText(), new String(newUserPassword.getPassword()), newUserEmailTextField.getText());
			
			if (errors.containsKey("username")) newUserNameTextField.setBackground(Constants.ERROR_COLOR);
			if (errors.containsKey("password")) newUserPassword.setBackground(Constants.ERROR_COLOR);
			if (errors.containsKey("email")) newUserEmailTextField.setBackground(Constants.ERROR_COLOR);
			
			if (!new String(newUserPassword.getPassword()).equals(new String(newUserPasswordRepeat.getPassword()))) {
				errors.put("passwordrepeat", "Upprepning av lösenord stämmer inte");
				newUserPassword.setBackground(Constants.ERROR_COLOR);
				newUserPasswordRepeat.setBackground(Constants.ERROR_COLOR);
			}
			
			if (!errors.isEmpty()) {
				String msg = "";
				for (String error : errors.values()) {
					msg += error + "\n";
				}
				
				JOptionPane.showMessageDialog(this, msg, "Fel vid registering", JOptionPane.WARNING_MESSAGE);
			} else {
				dispose();
			}*/
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName() == "account_signin") {
			List<String> errors = (ArrayList<String>)evt.getNewValue();
			if (!errors.isEmpty()) {
				for (String error : errors) {
					System.out.println(error);
				}
			}
		}
		
		if (evt.getPropertyName() == "account_signedin") {
			System.out.println("DET GICK! Du är inloggad nu...");
			dispose();
		}
		
		if (evt.getPropertyName() == "account_signup") {
			List<String> errors = (ArrayList<String>)evt.getNewValue();
			if (!errors.isEmpty()) {
				for (String error : errors) {
					System.out.println(error);
				}
			}
		}
		
		if (evt.getPropertyName() == "account_signedup") {
			Account account = (Account)evt.getNewValue();
			System.out.println("DET GICK! Du har ett konto, din jävel...");
			model.accountSignIn(account.getUserName(), account.getPassword());
		}
	}
}
