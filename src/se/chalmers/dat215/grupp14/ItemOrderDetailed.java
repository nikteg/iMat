package se.chalmers.dat215.grupp14;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import net.miginfocom.swing.MigLayout;
import se.chalmers.ait.dat215.project.ShoppingItem;
import se.chalmers.dat215.grupp14.backend.IMatModel;

@SuppressWarnings("serial")
public class ItemOrderDetailed extends JPanel implements ActionListener {
    private JLabel lblNameLabel;
    private ShoppingItem shoppingItem;
    private IMatModel model;
    private JToggleButton tglFavorite;
    private JButton btnAddToCart;
    private JLabel labelAmount;

    public ItemOrderDetailed() {
        super();
        initializeGUI();
    }

    /**
     * Creates a CartItem instance from the given ShoppingItem.
     * 
     * @param shoppingItem
     *            - The item to track
     */
    public ItemOrderDetailed(ShoppingItem shoppingItem, IMatModel model) {
        this();
        this.shoppingItem = shoppingItem;
        this.model = model;
        lblNameLabel.setText(shoppingItem.getProduct().getName());
        labelAmount.setText((int) shoppingItem.getAmount() + " " + shoppingItem.getProduct().getUnitSuffix());
        tglFavorite.setSelected(model.isFavorite(shoppingItem.getProduct()));
    }

    private void initializeGUI() {
        setLayout(new MigLayout("insets 4px", "[75.00,grow][][][pref:18.00px:pref]", "[60px]"));

        lblNameLabel = new JLabel("nameLabel");
        add(lblNameLabel, "cell 0 0,alignx left,aligny center");

        labelAmount = new JLabel();
        labelAmount.setPreferredSize(new Dimension(48, 10));
        add(labelAmount, "cell 1 0,alignx left,aligny center");

        btnAddToCart = new JButton("");
        btnAddToCart.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        btnAddToCart.setContentAreaFilled(false);
        btnAddToCart.setBorderPainted(false);
        btnAddToCart.setIcon(new ImageIcon(CartItem.class.getResource("resources/images/icons/cart.png")));
        btnAddToCart.addActionListener(this);
        btnAddToCart.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnAddToCart.setMinimumSize(new Dimension(0, 0));
        add(btnAddToCart, "cell 2 0");

        tglFavorite = new JToggleButton("");
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
        tglFavorite.addActionListener(this);
        tglFavorite.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        tglFavorite.setActionCommand("favorite");

        add(tglFavorite, "cell 3 0,alignx right,aligny center");
    }

    @Override
    public void actionPerformed(ActionEvent action) {
        if (action.getActionCommand() == "favorite") {

            if (model.isFavorite(shoppingItem.getProduct())) {
                model.favoriteRemove(shoppingItem.getProduct());
            } else {
                model.favoriteAdd(shoppingItem.getProduct());
            }
        }

        if (action.getSource() == btnAddToCart) {
            model.cartAddItem(shoppingItem.getProduct(), shoppingItem.getAmount());
        }
    }
}
