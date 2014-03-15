package se.chalmers.dat215.grupp14;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import se.chalmers.ait.dat215.project.ShoppingItem;
import se.chalmers.dat215.grupp14.backend.Constants;
import se.chalmers.dat215.grupp14.backend.IMatModel;

/**
 * Item in list view
 * @author Niklas Tegnander, Mikael Lönn and Oskar Jönefors
 */
@SuppressWarnings("serial")
public class ListItem extends JPanel implements ActionListener, PropertyChangeListener {
    private IMatModel model;
    private String title;
    private List<ShoppingItem> shoppingItems;
    private JLabel label;
    private JLabel lblDatum;
    private JButton btnInfo;
    private ListView listView;
    private JButton btnX;

    /**
     * Constructor
     */
    public ListItem() {
        super();
        initializeGUI();
    }

    /**
     * Constructor with given list view, title, shopping items and model
     * @param listView
     * @param title
     * @param shoppingItems
     * @param model
     */
    public ListItem(ListView listView, String title, List<ShoppingItem> shoppingItems, IMatModel model) {
        this();
        this.title = title;
        this.shoppingItems = shoppingItems;
        this.model = model;
        this.listView = listView;
        lblDatum.setText(title);
        label.setText((getTotalPrice(shoppingItems)) + Constants.currencySuffix);
        this.model.addPropertyChangeListener(this);

    }

    /**
     * Initialize GUI
     */
    private void initializeGUI() {
        setLayout(new MigLayout("insets 4px", "[grow][][][]", "[60px]"));

        lblDatum = new JLabel("");
        add(lblDatum, "cell 0 0");

        label = new JLabel("");
        add(label, "cell 1 0");

        btnInfo = new JButton("");
        btnInfo.setToolTipText("Expandera lista");
        btnInfo.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        btnInfo.setContentAreaFilled(false);
        btnInfo.setBorderPainted(false);
        btnInfo.setIcon(new ImageIcon(OrderItem.class.getResource("resources/images/icons/more.png")));
        btnInfo.addActionListener(this);
        btnInfo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        add(btnInfo, "cell 2 0,alignx right");

        btnX = new JButton("");
        btnX.setToolTipText("Ta bort artikel från varukorg");
        btnX.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        btnX.setContentAreaFilled(false);
        btnX.setBorderPainted(false);
        btnX.setIcon(new ImageIcon(CartItem.class.getResource("resources/images/icons/delete.png")));
        btnX.addActionListener(this);
        btnX.setActionCommand("remove_from_cart");
        btnX.setMinimumSize(new Dimension(0, 0));
        btnX.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(btnX, "cell 3 0");
    }

    /**
     * Get total price
     * @param shoppingItems
     * @return
     */
    private double getTotalPrice(List<ShoppingItem> shoppingItems) {
        double price = 0;
        for (ShoppingItem si : shoppingItems) {
            price += si.getTotal();
        }
        return price;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == btnInfo) {
            listView.addShoppingItems(shoppingItems);
        }

        if (event.getSource() == btnX) {
            model.listRemove(title);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }

}
