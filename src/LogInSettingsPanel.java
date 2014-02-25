

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

public class LogInSettingsPanel extends JPanel {
	private JLabel lblEmail;
	private JLabel lblLsenord;
	private JLabel lbligen;
	private JTextField emailTextField;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	
	public LogInSettingsPanel(){
		
		initialize();
	}
	private void initialize() {
		setBorder(new TitledBorder(null, "Inloggningsuppgifter", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(new MigLayout("", "[96][grow]", "[32][32][32]"));
		
		lblEmail = new JLabel("E-mail");
		add(lblEmail, "cell 0 0,alignx left");
		
		emailTextField = new JTextField();
		add(emailTextField, "cell 1 0,growx");
		emailTextField.setColumns(10);
		
		lblLsenord = new JLabel("Lösenord");
		add(lblLsenord, "cell 0 1,alignx left");
		
		passwordField = new JPasswordField();
		add(passwordField, "cell 1 1,growx");
		
		lbligen = new JLabel("(igen)");
		add(lbligen, "cell 0 2,alignx left");
		
		passwordField_1 = new JPasswordField();
		add(passwordField_1, "cell 1 2,growx");
	}
	
	public String getEmail(){
		return emailTextField.getText();
	}
	
	public void setEmail(String email){
		this.emailTextField.setText(email);
	}

}
