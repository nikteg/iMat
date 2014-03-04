import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;


public class SettingsWindow extends JDialog implements ActionListener, PropertyChangeListener {
	private JFrame frame;
	private IMatModel model;
	private JPanel settingsPanel;
	private JLabel lblSettings;
	private CardSettingsPanel cardSettingsPanel;
	private AddressSettingsPanel addressSettingsPanel;
	private LogInSettingsPanel logInSettingsPanel;
	private JButton btnSave;

	public SettingsWindow(JFrame frame, IMatModel model) {
		super(frame,true);
		this.frame = frame;
		this.model = model;
		initialize();
		
	}
	
	private void initialize() {
		setSize(660, 600);
		setResizable(false);
		
		settingsPanel = new JPanel();
		getContentPane().add(settingsPanel, BorderLayout.CENTER);
		settingsPanel.setLayout(new MigLayout("", "[grow][grow]", "[64][195.00,grow][][28]"));
		
		lblSettings = new JLabel("Profilinställningar");
		lblSettings.setFont(new Font("Tahoma", Font.PLAIN, 16));
		settingsPanel.add(lblSettings, "cell 0 0");
		
		logInSettingsPanel = new LogInSettingsPanel();
		settingsPanel.add(logInSettingsPanel, "cell 0 1,grow");
		
		cardSettingsPanel = new CardSettingsPanel();
		settingsPanel.add(cardSettingsPanel, "cell 1 1,grow");
		
		addressSettingsPanel = new AddressSettingsPanel();
		settingsPanel.add(addressSettingsPanel, "cell 0 2,grow");
		
		btnSave = new JButton("Spara");
		btnSave.addActionListener(this);
		
		
		settingsPanel.add(btnSave, "cell 1 3,alignx right,growy");
		
		addressSettingsPanel.setFirstName(model.getAccount().getFirstName());
		addressSettingsPanel.setLastName(model.getAccount().getLastName());
		addressSettingsPanel.setPhoneNumber(model.getAccount().getPhoneNumber());
		addressSettingsPanel.setMobilePhoneNumber(model.getAccount().getMobilePhoneNumber());
		addressSettingsPanel.setAddress(model.getAccount().getAddress());
		addressSettingsPanel.setPostAddress(model.getAccount().getPostAddress());
		addressSettingsPanel.setPostCode(model.getAccount().getPostCode());
		
		logInSettingsPanel.setEmail(model.getAccount().getEmail());
		
		
		
	}
	

	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnSave){
			
			model.getAccount().setFirstName(addressSettingsPanel.getFirstName());
			model.getAccount().setLastName(addressSettingsPanel.getLastName());
			model.getAccount().setPhoneNumber(addressSettingsPanel.getPhoneNumber());
			model.getAccount().setMobilePhoneNumber(addressSettingsPanel.getMobilePhoneNumber());
			model.getAccount().setAddress(addressSettingsPanel.getAddress());
			model.getAccount().setPostAddress(addressSettingsPanel.getPostAddress());
			model.getAccount().setPostCode(addressSettingsPanel.getPostCode());
			
			model.getAccount().setEmail(logInSettingsPanel.getEmail());
			
			
			
			dispose();
		}
		
		
	}
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		
	}

}
