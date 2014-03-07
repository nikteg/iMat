package se.chalmers.dat215.grupp14;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JToggleButton;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.miginfocom.swing.MigLayout;
import se.chalmers.ait.dat215.project.Product;
import se.chalmers.ait.dat215.project.ShoppingItem;

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
	private JLabel lblUnitsuffix;
	public ItemList() {
		super();
		initializeGUI();
	}
	
	public ItemList(ShoppingItem shoppingItem, FavoriteView favoriteView, IMatModel model) {
		super(shoppingItem, favoriteView, model);
		initializeGUI();
	}
	
	private void initializeGUI() {
		setPreferredSize(new Dimension(512, 64));
		//setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		setLayout(new MigLayout("insets 0px 0px 0px 8px", "[64px:64.00][5px:5px][92px:92px,grow][64px][][48px:48px][48][]", "[64px]"));
		
		lblBild = new JLabel(model.getImageIcon(shoppingItem.getProduct(), new Dimension(48, 48)));
		lblBild.setHorizontalTextPosition(SwingConstants.RIGHT);
		lblBild.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblBild, "cell 0 0,alignx center,aligny center");
		
		separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		add(separator, "cell 1 0,growy");
		
		lblNamelabel = new JLabel(shoppingItem.getProduct().getName());
		add(lblNamelabel, "cell 2 0,alignx left");
		
		lblPricelabel = new JLabel(shoppingItem.getProduct().getPrice() + shoppingItem.getProduct().getUnit());
		lblPricelabel.setBackground(Color.RED);
		add(lblPricelabel, "cell 3 0");
		
		spinner = new WebSpinner();
		spinner.addChangeListener(this);
		spinner.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		add(spinner, "cell 5 0,growx");
		
		tglFavorite = new JToggleButton("");
		tglFavorite.setPressedIcon(new ImageIcon(ItemList.class.getResource("resources/images/icons/star2.png")));
		tglFavorite.setUI(new javax.swing.plaf.basic.BasicButtonUI());
		tglFavorite.setContentAreaFilled(false);
		tglFavorite.setBorderPainted(false);
		tglFavorite.setRolloverSelectedIcon(new ImageIcon(ItemList.class.getResource("resources/images/icons/star.png")));
		tglFavorite.setRolloverIcon(new ImageIcon(ItemList.class.getResource("resources/images/icons/star-inactive.png")));
		tglFavorite.setRolloverEnabled(true);
		tglFavorite.setBorder(null);
		tglFavorite.setSelectedIcon(new ImageIcon(ItemList.class.getResource("resources/images/icons/star.png")));
		tglFavorite.setIcon(new ImageIcon(ItemList.class.getResource("resources/images/icons/star-outline.png")));
		tglFavorite.addActionListener(this);
		tglFavorite.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tglFavorite.setActionCommand("favorite");
		tglFavorite.setVisible(!model.getAccount().isAnonymous());

		
		if (model.isFavorite(shoppingItem.getProduct())) tglFavorite.setSelected(true);
		
		add(tglFavorite, "cell 4 0,alignx center");
		
		btnKp = new JButton("Lägg till");
		btnKp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnKp.addActionListener(this);
		
		lblUnitsuffix = new JLabel(shoppingItem.getProduct().getUnitSuffix());
		add(lblUnitsuffix, "cell 6 0");
		btnKp.setActionCommand("add_cart");
		add(btnKp, "cell 7 0");
		

	}

	@Override
	public void stateChanged(ChangeEvent event) {
		if (event.getSource() == spinner) {
			shoppingItem.setAmount(((Integer)spinner.getValue()).doubleValue());
			lblPricelabel.setText(shoppingItem.getProduct().getPrice() * ((Integer)spinner.getValue()).doubleValue() + Constants.currencySuffix);
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
		
		if (evt.getPropertyName() == "favorite_clear") {
			tglFavorite.setSelected(false);
		}
		
		if (evt.getPropertyName() == "account_signedin") {
			tglFavorite.setVisible(true);
		}
		
		if (evt.getPropertyName() == "account_signedup") {
			tglFavorite.setVisible(true);
		}
		
		if (evt.getPropertyName() == "account_signout") {
			tglFavorite.setVisible(false);
		}
		
	}

}