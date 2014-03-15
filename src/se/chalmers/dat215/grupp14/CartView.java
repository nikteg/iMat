package se.chalmers.dat215.grupp14;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import net.miginfocom.swing.MigLayout;
import se.chalmers.ait.dat215.project.ShoppingItem;
import se.chalmers.dat215.grupp14.backend.Constants;
import se.chalmers.dat215.grupp14.backend.IMatModel;

import com.alee.extended.image.WebImage;
import com.alee.laf.text.WebTextField;

/**
 * Cart view
 * @author Niklas Tegnander, Mikael Lönn and Oskar Jönefors
 */
@SuppressWarnings("serial")
public class CartView extends JPanel implements ActionListener, PropertyChangeListener {
    private CheckOutDialog checkoutWindow;
    private WebTextField txtListName;
    private JScrollPane scrollPane;
    private JButton btnClearCart;
    private JButton btnCheckout;
    private JButton btnSave;
    private JPanel pnlItem;
    private JPanel panel;
    private JLabel lblTotalPrice;
    private JLabel lblTotalPriceDescription;
    private JFrame frame;
    private IMatModel model;

    /**
     * Constructor
     */
    public CartView() {
        super();
        initializeGUI();
    }

    /**
     * Constructor with a given model and parent frame
     * @param model
     * @param frame
     */
    public CartView(JFrame frame, IMatModel model) {
        this();
        this.model = model;
        this.frame = frame;
        this.model.addPropertyChangeListener(this);

        txtListName.setEnabled(!model.getAccountHandler().getCurrentAccount().isAnonymous());
        btnSave.setEnabled(!model.getAccountHandler().getCurrentAccount().isAnonymous());
        btnCheckout.setEnabled(!model.getShoppingCart().getItems().isEmpty());
        btnClearCart.setEnabled(!model.getShoppingCart().getItems().isEmpty());

        for (int i = 0; i < model.getShoppingCart().getItems().size(); i++) {
            CartItem ci = new CartItem(model.getShoppingCart().getItems().get(i), model);
            pnlItem.add(ci, "wrap,growx");
        }

        lblTotalPrice.setText(model.getShoppingCart().getTotal() + Constants.currencySuffix);
        updateColors();
    }

    /**
     * Initialize GUI
     */
    private void initializeGUI() {
        setLayout(new MigLayout("insets 2px", "[grow][grow]", "[][grow][20.00][24.00]"));

        panel = new JPanel();
        add(panel, "cell 0 0 2 1,grow");
        panel.setLayout(new MigLayout("insets 0px", "[grow][]", "[]"));

        txtListName = new WebTextField();
        txtListName.setInputPrompt("Namn på lista...");

        panel.add(txtListName, "cell 0 0,growx");

        btnSave = new JButton("Spara varukorg");
        btnSave.addActionListener(this);
        btnSave.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        panel.add(btnSave, "cell 1 0");

        scrollPane = new JScrollPane();
        scrollPane.setFocusable(false);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, "cell 0 1 2 1,grow");

        pnlItem = new JPanel();
        scrollPane.setViewportView(pnlItem);
        pnlItem.setLayout(new MigLayout("insets 0px, gapy 0", "[grow]", "[36px]"));

        lblTotalPriceDescription = new JLabel("Totalpris:");
        add(lblTotalPriceDescription, "flowx,cell 0 2,alignx right");

        lblTotalPrice = new JLabel("TOTALPRIS");
        add(lblTotalPrice, "cell 1 2");

        btnClearCart = new JButton("Rensa varukorg");
        btnClearCart.setToolTipText("Ta bort alla artiklar från varukorgen");
        btnClearCart.addActionListener(this);
        btnClearCart.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(btnClearCart, "cell 0 3,growx,aligny center");

        btnCheckout = new JButton("Gå till kassan");
        btnCheckout.setToolTipText("Gå vidare till kassan");
        btnCheckout.addActionListener(this);
        btnCheckout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(btnCheckout, "cell 1 3,growx,aligny center");

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // Enable list saving
        if (evt.getPropertyName() == "account_signedin") {
            txtListName.setEnabled(!model.getAccountHandler().getCurrentAccount().isAnonymous());
            btnSave.setEnabled(!model.getAccountHandler().getCurrentAccount().isAnonymous());
        }

        // Disable list saving
        if (evt.getPropertyName() == "account_signout") {
            txtListName.setEnabled(!model.getAccountHandler().getCurrentAccount().isAnonymous());
            btnSave.setEnabled(!model.getAccountHandler().getCurrentAccount().isAnonymous());
        }

        // Add item to cart
        if (evt.getPropertyName() == "cart_additem") {
            btnCheckout.setEnabled(true);
            btnClearCart.setEnabled(true);
            pnlItem.add(new CartItem((ShoppingItem) evt.getNewValue(), model), "wrap,growx");

            lblTotalPrice.setText(Constants.currencyFormat.format(model.getShoppingCart().getTotal()) + Constants.currencySuffix);
            updateColors();
        }

        // Remove item from cart
        if (evt.getPropertyName() == "cart_removeitem") {
            btnCheckout.setEnabled(!model.getShoppingCart().getItems().isEmpty());
            btnClearCart.setEnabled(!model.getShoppingCart().getItems().isEmpty());

            for (Component component : pnlItem.getComponents()) {
                if (((CartItem) component).getShoppingItem() == (ShoppingItem) evt.getNewValue()) {
                    pnlItem.remove(component);
                    break;
                }
            }

            lblTotalPrice.setText(Constants.currencyFormat.format(model.getShoppingCart().getTotal()) + Constants.currencySuffix);
            updateColors();
        }

        // Update cart
        if (evt.getPropertyName() == "cart_updateitem") {
            for (Component component : pnlItem.getComponents()) {
                if (((CartItem) component).getShoppingItem() == (ShoppingItem) evt.getNewValue()) {
                    ((CartItem) component).getShoppingItem().setAmount(((ShoppingItem) evt.getNewValue()).getAmount());
                    break;
                }
            }

            lblTotalPrice.setText(Constants.currencyFormat.format(model.getShoppingCart().getTotal())
                    + Constants.currencySuffix);
        }

        // Clear cart
        if (evt.getPropertyName() == "cart_clear") {
            pnlItem.removeAll();
            pnlItem.revalidate();
            repaint();
            lblTotalPrice.setText(Constants.currencyFormat.format(model.getShoppingCart().getTotal())
                    + Constants.currencySuffix);
        }
        
        if (evt.getPropertyName() == "list_save") {
            @SuppressWarnings("unchecked")
            List<String> errors = (List<String>)evt.getNewValue();
            
            resetError(txtListName);
            
            if (!errors.isEmpty()) {
                if (errors.contains("name_tooshort")) {
                    setError(txtListName);
                }
            }
        }
    }

    /**
     * Reset text field icon and background
     * @param wt
     */
    public void resetError(WebTextField wt) {
        wt.setBackground(Color.WHITE);
        wt.setTrailingComponent(null);
    }

    /**
     * Add error icon and background to text field
     * @param wt
     */
    public void setError(WebTextField wt) {
        wt.setBackground(Constants.ERROR_COLOR);
        wt.setTrailingComponent(new WebImage(AddressSettingsPanel.class
                .getResource("resources/images/icons/warning.png")));
    }

    /**
     * Row striping
     */
    private void updateColors() {
        for (int i = 0; i < pnlItem.getComponentCount(); i++) {
            pnlItem.getComponents()[i].setBackground((i % 2 == 0) ? Constants.ALT_COLOR : null);
        }

        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == btnClearCart) {
            String[] options = new String[2];
            options[0] = new String("Ja");
            options[1] = new String("Nej");
            if (JOptionPane.showOptionDialog(this, "Är du säker på att du vill ta bort alla varor ur varukorgen?",
                    "Rensa varukorg?", 0, JOptionPane.WARNING_MESSAGE, null, options, null) == 0) {
                model.cartClear();
            }
        }

        if (event.getSource() == btnCheckout) {
            if (model.getShoppingCart().getItems().size() != 0) {
                checkoutWindow = new CheckOutDialog(frame, model);
                checkoutWindow.setLocationRelativeTo(frame);
                checkoutWindow.setVisible(true);
            }
        }

        if (event.getSource() == btnSave) {
            model.listSave(txtListName.getText(), model.getShoppingCart().getItems());
        }
    }
}
