import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.miginfocom.swing.MigLayout;
import se.chalmers.ait.dat215.project.Product;
import se.chalmers.ait.dat215.project.ShoppingItem;

import javax.swing.SpinnerNumberModel;

import com.alee.laf.spinner.WebSpinner;


@SuppressWarnings("serial")
public class ItemList extends Item implements ChangeListener {
	private JLabel lblBild;
	private JSeparator separator;
	private JButton btnKp;
	private JLabel lblNamelabel;
	private WebSpinner spinner;
	private JLabel lblPricelabel;
	public JToggleButton tglFavorite;

	public ItemList() {
		super();
		initialize();
	}
	
	public ItemList(ShoppingItem shoppingItem , IMatModel model) {
		super(shoppingItem, model);
		initialize();
	}
	
	private void initialize() {
		setPreferredSize(new Dimension(512, 77));
		//setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		setLayout(new MigLayout("", "[64px:64.00][5px:5px][92px:92px,grow][64px][][48px:48px][64px:64px]", "[64px]"));
		
		lblBild = new JLabel(model.getImageIcon(shoppingItem.getProduct(), new Dimension(48, 48)));
		lblBild.setHorizontalTextPosition(SwingConstants.RIGHT);
		lblBild.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblBild, "cell 0 0,alignx center,aligny center");
		
		separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		add(separator, "cell 1 0,growy");
		
		lblNamelabel = new JLabel(shoppingItem.getProduct().getName());
		add(lblNamelabel, "cell 2 0,alignx left");
		
		lblPricelabel = new JLabel(shoppingItem.getProduct().getPrice() + ":-");
		lblPricelabel.setBackground(Color.RED);
		add(lblPricelabel, "cell 3 0");
		
		spinner = new WebSpinner();
		spinner.addChangeListener(this);
		spinner.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		add(spinner, "cell 5 0,growx");
		
		btnKp = new JButton("K\u00F6p");
		btnKp.addActionListener(this);
		btnKp.setActionCommand("add_cart");
		add(btnKp, "cell 6 0,alignx center");
		
		tglFavorite = new JToggleButton("");
		tglFavorite.setUI(new javax.swing.plaf.basic.BasicButtonUI());
		tglFavorite.setContentAreaFilled(false);
		tglFavorite.setBorderPainted(false);
		tglFavorite.setRolloverSelectedIcon(new ImageIcon(ItemGrid.class.getResource("/resources/icons/star.png")));
		tglFavorite.setRolloverIcon(new ImageIcon(ItemGrid.class.getResource("/resources/icons/star-inactive.png")));
		tglFavorite.setRolloverEnabled(true);
		tglFavorite.setBorder(null);
		tglFavorite.setSelectedIcon(new ImageIcon(ItemGrid.class.getResource("/resources/icons/star.png")));
		tglFavorite.setIcon(new ImageIcon(ItemGrid.class.getResource("/resources/icons/star-outline.png")));
		tglFavorite.addActionListener(this);
		tglFavorite.setActionCommand("favorite");
		
		if (model.isFavorite(shoppingItem.getProduct())) tglFavorite.setSelected(true);
		
		add(tglFavorite, "cell 4 0,alignx center");
	}

	@Override
	public void stateChanged(ChangeEvent event) {
		if (event.getSource() == spinner) {
			shoppingItem.setAmount(((Integer)spinner.getValue()).doubleValue());
			lblPricelabel.setText(shoppingItem.getProduct().getPrice() * ((Integer)spinner.getValue()).doubleValue() + ":-");
		}
	}
	


	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName() == "favorite_add") {
			if(((Product)evt.getNewValue()).equals(shoppingItem.getProduct())) {
				tglFavorite.setSelected(true);
			}
		}
		if (evt.getPropertyName() == "favorite_remove") {
			if(((Product)evt.getNewValue()).equals(shoppingItem.getProduct())) {
				tglFavorite.setSelected(false);
			}
		}
		
	}

}
