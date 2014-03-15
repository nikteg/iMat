package se.chalmers.dat215.grupp14;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JToggleButton;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.miginfocom.swing.MigLayout;
import se.chalmers.ait.dat215.project.Product;
import se.chalmers.ait.dat215.project.ShoppingItem;
import se.chalmers.dat215.grupp14.backend.Constants;
import se.chalmers.dat215.grupp14.backend.IMatModel;

import com.alee.laf.spinner.WebSpinner;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Font;

/**
 * Grid item
 * @author Niklas Tegnander, Mikael Lönn and Oskar Jönefors
 */
@SuppressWarnings("serial")
public class ItemGrid extends Item implements ChangeListener {
    private JLabel lblBild;
    private JButton btnKp;
    private JLabel lblName;
    private JLabel lblPrice;
    private WebSpinner spinner;
    public JToggleButton tglFavorite;
    private JLabel lblSuffix;
    private JPanel panel;

    /**
     * Constructor
     */
    public ItemGrid() {
        super();
        initializeGUI();
    }

    /**
     * Constructor with given shopping item, favorite view and model
     * @param shoppingItem
     * @param favoriteView
     * @param model
     */
    public ItemGrid(ShoppingItem shoppingItem, FavoriteView favoriteView, IMatModel model) {
        super(shoppingItem, favoriteView, model);
        initializeGUI();
    }

    private void initializeGUI() {
        setBackground(new Color(248, 248, 248));
        setPreferredSize(new Dimension(180, 240));
        setLayout(new MigLayout("insets 8px", "[48px][grow][][grow]", "[164px:164px][grow][26px:26px]"));

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(164, 164));

        lblBild = new JLabel(model.getImageIcon(shoppingItem.getProduct(), new Dimension(164, 164)));
        lblBild.setPreferredSize(new Dimension(164, 164));
        lblBild.setHorizontalAlignment(SwingConstants.CENTER);

        btnKp = new JButton("Lägg till");
        btnKp.setToolTipText("Lägg till produkt i varukorgen");
        btnKp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnKp.addActionListener(this);
        
        panel = new JPanel();
        panel.setBackground(new Color(248, 248, 248));
        add(panel, "cell 0 1 4 1,grow");
        panel.setLayout(new BorderLayout(0, 0));

        lblName = new JLabel(shoppingItem.getProduct().getName());
        lblName.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
        panel.add(lblName, BorderLayout.WEST);
        
        lblPrice = new JLabel(Constants.currencyFormat.format(shoppingItem.getProduct().getPrice()) + Constants.currencySuffix + "/" + shoppingItem.getProduct().getUnitSuffix());
        lblPrice.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
        panel.add(lblPrice, BorderLayout.EAST);

        spinner = new WebSpinner();
        spinner.setDrawFocus(false);
        spinner.setPreferredSize(new Dimension(32, 20));
        spinner.addChangeListener(this);
        spinner.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), new Integer(99), new Integer(1)));
        add(spinner, "flowx,cell 0 2,grow");

        tglFavorite = new JToggleButton("");
        tglFavorite.setPressedIcon(new ImageIcon(ItemGrid.class.getResource("resources/images/icons/star2.png")));
        tglFavorite.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        tglFavorite.setContentAreaFilled(false);
        tglFavorite.setBorderPainted(false);
        tglFavorite
                .setRolloverSelectedIcon(new ImageIcon(ItemGrid.class.getResource("resources/images/icons/star.png")));
        tglFavorite.setRolloverIcon(new ImageIcon(ItemGrid.class
                .getResource("resources/images/icons/star-inactive.png")));
        tglFavorite.setRolloverEnabled(true);
        tglFavorite.setBorder(null);
        tglFavorite.setSelectedIcon(new ImageIcon(ItemGrid.class.getResource("resources/images/icons/star.png")));
        tglFavorite.setIcon(new ImageIcon(ItemGrid.class.getResource("resources/images/icons/star-outline.png")));
        tglFavorite.setVisible(!model.getAccountHandler().getCurrentAccount().isAnonymous());
        tglFavorite.addActionListener(this);
        tglFavorite.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        tglFavorite.setBounds(136, 4, 24, 24);
        lblBild.setBounds(0, 0, 164, 164);

        layeredPane.add(lblBild, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(tglFavorite, JLayeredPane.MODAL_LAYER);
        add(layeredPane, "cell 0 0 5 1,aligny top,grow");
        tglFavorite.setActionCommand("favorite");
        
        lblSuffix = new JLabel(shoppingItem.getProduct().getUnitSuffix());
        add(lblSuffix, "cell 1 2,alignx left");

        // add(tglFavorite, "cell 3 2,alignx center,aligny center");
        btnKp.setActionCommand("add_cart");
        add(btnKp, "cell 3 2,alignx right,aligny center");

        if (model.isFavorite(shoppingItem.getProduct()))
            tglFavorite.setSelected(true);

    }

    @Override
    public void stateChanged(ChangeEvent event) {
        if (event.getSource() == spinner) {
            shoppingItem.setAmount(((Integer) spinner.getValue()).doubleValue());
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName() == "favorite_add") {
            if (((Product) evt.getNewValue()).equals(shoppingItem.getProduct())) {
                tglFavorite.setSelected(true);
            }
        }
        if (evt.getPropertyName() == "favorite_remove") {
            if (((Product) evt.getNewValue()).equals(shoppingItem.getProduct())) {
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
