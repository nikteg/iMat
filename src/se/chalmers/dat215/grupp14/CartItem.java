package se.chalmers.dat215.grupp14;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import net.miginfocom.swing.MigLayout;
import se.chalmers.ait.dat215.project.ShoppingItem;
import com.alee.laf.spinner.WebSpinner;

@SuppressWarnings("serial")
public class CartItem extends JPanel implements ChangeListener, ActionListener, PropertyChangeListener {
    private JLabel lblName;
    private WebSpinner spinnerAmount;
    private ShoppingItem shoppingItem;
    private IMatModel model;
    private JLabel lblTotalPrice;
    private JButton btnRemove;
    private JLabel lblUnitSuffix;

    /**
     * Constructor
     */
    public CartItem() {
        initializeGUI();
    }

    /**
     * Constructor with shoppingItem to track
     * 
     * @param shoppingItem
     */
    public CartItem(ShoppingItem shoppingItem, IMatModel model) {
        this();
        this.shoppingItem = shoppingItem;
        this.model = model;
        this.model.addPropertyChangeListener(this);

        lblName.setText(shoppingItem.getProduct().getName());
        lblTotalPrice.setText(shoppingItem.getTotal() + Constants.currencySuffix);
        lblUnitSuffix.setText(shoppingItem.getProduct().getUnitSuffix());
        spinnerAmount.setValue(((Double) (shoppingItem.getAmount())).intValue());
    }

    /**
     * Initialize GUI
     */
    private void initializeGUI() {
        setLayout(new MigLayout("insets 4px", "[grow][52][28][48px][pref:18.00px:pref]", "[60px]"));

        lblName = new JLabel("nameLabel");
        add(lblName, "cell 0 0,alignx left,aligny center");

        spinnerAmount = new WebSpinner();
        spinnerAmount.addChangeListener(this);
        spinnerAmount.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
        spinnerAmount.setPreferredSize(new Dimension(48, 10));
        add(spinnerAmount, "cell 1 0,alignx right,aligny baseline");

        lblUnitSuffix = new JLabel("<unit>");
        add(lblUnitSuffix, "cell 2 0,alignx left,aligny center");

        lblTotalPrice = new JLabel("<price>");
        add(lblTotalPrice, "cell 3 0,alignx right,aligny center");

        btnRemove = new JButton();
        btnRemove.setToolTipText("Ta bort artikel från varukorg");
        btnRemove.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        btnRemove.setContentAreaFilled(false);
        btnRemove.setBorderPainted(false);
        btnRemove.setIcon(new ImageIcon(CartItem.class.getResource("resources/images/icons/delete.png")));
        btnRemove.addActionListener(this);
        btnRemove.setMinimumSize(new Dimension(0, 0));
        btnRemove.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(btnRemove, "cell 4 0");
    }

    public ShoppingItem getShoppingItem() {
        return shoppingItem;
    }

    @Override
    public void stateChanged(ChangeEvent event) {
        if (event.getSource() == spinnerAmount) {
            model.cartUpdateItem(shoppingItem.getProduct(), ((Integer) spinnerAmount.getValue()).doubleValue());
        }
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        // When remove button is clicked
        if (event.getSource() == btnRemove) {
            model.cartRemoveItem(shoppingItem.getProduct());
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName() == "cart_updateitem") {
            if (shoppingItem.getProduct().equals(((ShoppingItem) evt.getNewValue()).getProduct())) {
                ShoppingItem item = (ShoppingItem) evt.getNewValue();
                // if (shoppingItem.getAmount() == item.getAmount()) return;

                spinnerAmount.setValue(((Double) item.getAmount()).intValue());
                lblTotalPrice.setText(Constants.currencyFormat.format(item.getTotal()) + Constants.currencySuffix);
                shoppingItem = item;
                repaint();
            }
        }
    }
}
