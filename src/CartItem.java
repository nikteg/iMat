import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;

import se.chalmers.ait.dat215.project.CartEvent;
import se.chalmers.ait.dat215.project.ShoppingCartListener;
import se.chalmers.ait.dat215.project.ShoppingItem;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.SpinnerNumberModel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class CartItem extends JPanel{
	private JLabel lblNameLabel;
	private JSpinner spinner;
	private JLabel lblPricelabel;
	private JLabel lblRemoveLabel;
	private ShoppingItem item;
	private IMatModel model;
	private JLabel lblTotalPriceLabel;

	public CartItem() {
		// TODO Auto-generated constructor stub
		initialize();
	}
	
	public CartItem(String name){
		this();
		lblNameLabel.setText(name);
		
	}
	
	/**
	 * Creates a CartItem instance from the given ShoppingItem.
	 * @param item - The item to track
	 */
	public CartItem(ShoppingItem item){
		this();
		this.item = item;
		model = IMatModel.getInstance();
		lblNameLabel.setText(item.getProduct().getName());
		lblPricelabel.setText(""+item.getProduct().getPrice() + ":-");
		lblTotalPriceLabel.setText(""+item.getTotal() + ":-");
		spinner.setValue((item.getAmount()));
	}
	
	private void initialize() {
		
		
		setPreferredSize(new Dimension(280, 60));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{100, 50, 50, 25, 25, 0};
		gridBagLayout.rowHeights = new int[]{40, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		lblNameLabel = new JLabel("nameLabel");
		GridBagConstraints gbc_lblNameLabel = new GridBagConstraints();
		gbc_lblNameLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNameLabel.insets = new Insets(0, 0, 0, 5);
		gbc_lblNameLabel.gridx = 0;
		gbc_lblNameLabel.gridy = 0;
		add(lblNameLabel, gbc_lblNameLabel);
		
		spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		spinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				//item.setAmount((double)spinner.getValue());
				System.out.println(spinner.getValue());
			}
		});
		
		lblPricelabel = new JLabel("priceLabel");
		GridBagConstraints gbc_lblPricelabel = new GridBagConstraints();
		gbc_lblPricelabel.anchor = GridBagConstraints.EAST;
		gbc_lblPricelabel.insets = new Insets(0, 0, 0, 5);
		gbc_lblPricelabel.gridx = 1;
		gbc_lblPricelabel.gridy = 0;
		add(lblPricelabel, gbc_lblPricelabel);
		spinner.setPreferredSize(new Dimension(60, 60));
		GridBagConstraints gbc_spinner = new GridBagConstraints();
		gbc_spinner.anchor = GridBagConstraints.EAST;
		gbc_spinner.insets = new Insets(0, 0, 0, 5);
		gbc_spinner.gridx = 2;
		gbc_spinner.gridy = 0;
		add(spinner, gbc_spinner);
		
		lblTotalPriceLabel = new JLabel("X");
		GridBagConstraints gbc_lblTotalPriceLabel = new GridBagConstraints();
		gbc_lblTotalPriceLabel.anchor = GridBagConstraints.WEST;
		gbc_lblTotalPriceLabel.insets = new Insets(0, 0, 0, 5);
		gbc_lblTotalPriceLabel.gridx = 3;
		gbc_lblTotalPriceLabel.gridy = 0;
		add(lblTotalPriceLabel, gbc_lblTotalPriceLabel);
		
		lblRemoveLabel = new JLabel("");
		lblRemoveLabel.addMouseListener(new MouseAdapter() {
		});
		lblRemoveLabel.setIcon(new ImageIcon(CartItem.class.getResource("/resources/icons/delete.png")));
		GridBagConstraints gbc_lblRemoveLabel = new GridBagConstraints();
		gbc_lblRemoveLabel.anchor = GridBagConstraints.EAST;
		gbc_lblRemoveLabel.gridx = 4;
		gbc_lblRemoveLabel.gridy = 0;
		add(lblRemoveLabel, gbc_lblRemoveLabel);
	}



	

}
