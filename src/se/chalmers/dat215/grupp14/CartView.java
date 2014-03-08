package se.chalmers.dat215.grupp14;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import net.miginfocom.swing.MigLayout;
import se.chalmers.ait.dat215.project.ShoppingItem;
import com.alee.laf.text.WebTextField;

@SuppressWarnings("serial")
public class CartView extends JPanel implements ActionListener, PropertyChangeListener {
    private CheckOutWindow checkoutWindow;
    private WebTextField listNameTextField;
    private JScrollPane scrollPane;
    private JButton btnClearCart;
    private JButton checkoutButton;
    private JButton btnSparaLista;
    private JPanel itemPanel;
    private JPanel panel;
    private JLabel totalPriceLabel;
    private JLabel totalPriceDescriptionLabel;
    private JFrame frame;
    private IMatModel model;

    public CartView() {
        super();
        initializeGUI();
    }

    public CartView(IMatModel model, JFrame frame) {
        this();
        this.model = model;
        this.frame = frame;
        this.model.addPropertyChangeListener(this);

        listNameTextField.setEnabled(!model.getAccount().isAnonymous());
        btnSparaLista.setEnabled(!model.getAccount().isAnonymous());
        checkoutButton.setEnabled(!model.getShoppingCart().getItems().isEmpty());
        btnClearCart.setEnabled(!model.getShoppingCart().getItems().isEmpty());

        for (int i = 0; i < model.getShoppingCart().getItems().size(); i++) {
            CartItem ci = new CartItem(model.getShoppingCart().getItems().get(i), model);
            itemPanel.add(ci, "wrap,growx");
        }

        totalPriceLabel.setText(model.getShoppingCart().getTotal() + Constants.currencySuffix);
        updateColors();
    }

    private void initializeGUI() {
        setLayout(new MigLayout("insets 2px", "[grow][grow]", "[][grow][20.00][24.00]"));

        panel = new JPanel();
        add(panel, "cell 0 0 2 1,grow");
        panel.setLayout(new MigLayout("insets 0px", "[grow][]", "[]"));

        listNameTextField = new WebTextField();
        listNameTextField.setInputPrompt("Namn på lista...");

        panel.add(listNameTextField, "cell 0 0,growx");

        btnSparaLista = new JButton("Spara varukorg");
        btnSparaLista.addActionListener(this);
        btnSparaLista.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        panel.add(btnSparaLista, "cell 1 0");

        scrollPane = new JScrollPane();
        scrollPane.setFocusable(false);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, "cell 0 1 2 1,grow");

        itemPanel = new JPanel();
        scrollPane.setViewportView(itemPanel);
        itemPanel.setLayout(new MigLayout("insets 0px, gapy 0", "[grow]", "[36px]"));

        totalPriceDescriptionLabel = new JLabel("Totalpris:");
        add(totalPriceDescriptionLabel, "flowx,cell 0 2,alignx right");

        totalPriceLabel = new JLabel("TOTALPRIS");
        add(totalPriceLabel, "cell 1 2");

        btnClearCart = new JButton("Rensa varukorg");
        btnClearCart.setToolTipText("Ta bort alla artiklar från varukorgen");
        btnClearCart.addActionListener(this);
        btnClearCart.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(btnClearCart, "cell 0 3,growx,aligny center");

        checkoutButton = new JButton("Gå till kassan");
        checkoutButton.setToolTipText("Gå vidare till kassan");
        checkoutButton.addActionListener(this);
        checkoutButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(checkoutButton, "cell 1 3,growx,aligny center");

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // Enable list saving
        if (evt.getPropertyName() == "account_signedin") {
            listNameTextField.setEnabled(!model.getAccount().isAnonymous());
            btnSparaLista.setEnabled(!model.getAccount().isAnonymous());
        }

        // Disable list saving
        if (evt.getPropertyName() == "account_signout") {
            listNameTextField.setEnabled(!model.getAccount().isAnonymous());
            btnSparaLista.setEnabled(!model.getAccount().isAnonymous());
        }

        // Add item to cart
        if (evt.getPropertyName() == "cart_additem") {
            checkoutButton.setEnabled(true);
            btnClearCart.setEnabled(true);
            itemPanel.add(new CartItem((ShoppingItem) evt.getNewValue(), model), "wrap,growx");

            totalPriceLabel.setText(model.getShoppingCart().getTotal() + Constants.currencySuffix);
            updateColors();
        }

        // Remove item from cart
        if (evt.getPropertyName() == "cart_removeitem") {
            checkoutButton.setEnabled(!model.getShoppingCart().getItems().isEmpty());
            btnClearCart.setEnabled(!model.getShoppingCart().getItems().isEmpty());

            for (Component component : itemPanel.getComponents()) {
                if (((CartItem) component).getShoppingItem() == (ShoppingItem) evt.getNewValue()) {
                    itemPanel.remove(component);
                    break;
                }
            }

            totalPriceLabel.setText(model.getShoppingCart().getTotal() + Constants.currencySuffix);
            updateColors();
        }

        // Update cart
        if (evt.getPropertyName() == "cart_updateitem") {
            for (Component component : itemPanel.getComponents()) {
                if (((CartItem) component).getShoppingItem() == (ShoppingItem) evt.getNewValue()) {
                    ((CartItem) component).getShoppingItem().setAmount(((ShoppingItem) evt.getNewValue()).getAmount());
                    break;
                }
            }

            totalPriceLabel.setText(Constants.currencyFormat.format(model.getShoppingCart().getTotal())
                    + Constants.currencySuffix);
        }

        // Clear cart
        if (evt.getPropertyName() == "cart_clear") {
            itemPanel.removeAll();
            itemPanel.revalidate();
            repaint();
            totalPriceLabel.setText(Constants.currencyFormat.format(model.getShoppingCart().getTotal())
                    + Constants.currencySuffix);
        }
    }

    private void updateColors() {
        for (int i = 0; i < itemPanel.getComponentCount(); i++) {
            itemPanel.getComponents()[i].setBackground((i % 2 == 0) ? Constants.ALT_COLOR : null);
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

        if (event.getSource() == checkoutButton) {
            if (model.getShoppingCart().getItems().size() != 0) {
                checkoutWindow = new CheckOutWindow(frame, model);
                checkoutWindow.setLocationRelativeTo(frame);
                checkoutWindow.setVisible(true);
            }

        }

        if (event.getSource() == btnSparaLista) {

            model.listSave(listNameTextField.getText(), model.getShoppingCart().getItems());

        }
    }
}
