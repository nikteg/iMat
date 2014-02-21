import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;


public class CartItem extends JPanel{
	private JLabel lblNameLabel;
	private JSpinner spinner;
	private JSeparator separator;
	private JLabel lblPricelabel;
	private JLabel label;

	public CartItem() {
		// TODO Auto-generated constructor stub
		initialize();
	}
	
	public CartItem(String name){
		this();
		lblNameLabel.setText(name);
		
	}
	
	
	
	
	private void initialize() {
		
		
		setPreferredSize(new Dimension(212, 36));
		setLayout(new MigLayout("insets 2px", "[][][74.00][32px:n:32px][24px:n:24px,center]", "[28px,grow]"));
		
		lblNameLabel = new JLabel("nameLabel");
		add(lblNameLabel, "cell 0 0");
		
		separator = new JSeparator();
		separator.setPreferredSize(new Dimension(2, 0));
		separator.setOrientation(SwingConstants.VERTICAL);
		add(separator, "cell 1 0,growy");
		
		lblPricelabel = new JLabel("priceLabel");
		add(lblPricelabel, "cell 2 0");
		
		spinner = new JSpinner();
		spinner.setPreferredSize(new Dimension(36, 20));
		add(spinner, "cell 3 0,alignx right");
		
		label = new JLabel("");
		label.setIcon(new ImageIcon(CartItem.class.getResource("/resources/icons/delete.png")));
		add(label, "cell 4 0");
	}


	

}
