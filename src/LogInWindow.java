


import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.Cursor;

import javax.swing.JPasswordField;

import com.alee.laf.rootpane.WebDialog;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LogInWindow extends WebDialog implements ActionListener {
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
	private MainWindow parent;
	public LogInWindow(JFrame frame, MainWindow parent) {
		super(frame, true);
		this.frame = frame;
		this.parent = parent;
		initialize2();
	}
	private void initialize2() {
		setSize(360, 250);
		setResizable(false);
		
		signInTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(signInTabbedPane, BorderLayout.CENTER);
		
		signInPanel = new JPanel();
		signInTabbedPane.addTab("Logga in", null, signInPanel, null);
		signInPanel.setLayout(new MigLayout("", "[96][216.00]", "[32][32][32][grow]"));
		
		userNameLabel = new JLabel("Användarnamn");
		userNameLabel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
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
		newCustomerPanel.setLayout(new MigLayout("", "[][grow]", "[32][32][32][32][32]"));
		
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
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()  == btnLogIn){
			parent.logIn(userNameTextField.getText(), new String(passwordField.getPassword()));
		}
		if(e.getSource() == btnRegister){
			
			if (new String(newUserPassword.getPassword()).equals(new String(newUserPasswordRepeat.getPassword()))){
				parent.createUser(newUserNameTextField.getText(), newUserEmailTextField.getText(), new String(newUserPassword.getPassword()));
				signInTabbedPane.setSelectedIndex(0);
			}else{
				JOptionPane.showMessageDialog(this, "du är dum i huvudet!", "Fel vid registering", JOptionPane.WARNING_MESSAGE);
			}
			
		}
		
	}



}
