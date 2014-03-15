package se.chalmers.dat215.grupp14;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;
import se.chalmers.ait.dat215.project.ShoppingItem;
import se.chalmers.dat215.grupp14.backend.CreditCard;
import se.chalmers.dat215.grupp14.backend.Constants;
import se.chalmers.dat215.grupp14.backend.IMatModel;

import com.alee.laf.rootpane.WebDialog;

/**
 * Checkout dialog
 * @author Niklas Tegnander, Mikael Lönn and Oskar Jönefors
 */
@SuppressWarnings("serial")
public class CheckOutDialog extends WebDialog implements ActionListener, PropertyChangeListener {
    private AddressSettingsPanel pnlAddressSettings;
    private CardSettingsPanel pnlCardSettings;
    private JScrollPane scrollPane;
    private JButton btnConfirm;
    private JLabel lblAmountValue;
    private JPanel pnlSettings;
    private JPanel pnlAmount;
    private JPanel pnlCart;
    private JPanel panel;
    private IMatModel model;

    /**
     * Constructor with given model and parent
     * @param frame
     * @param model
     */
    public CheckOutDialog(JFrame frame, IMatModel model) {
        super(frame, "Checkout", true);
        this.model = model;
        this.model.addPropertyChangeListener(this);

        initializeGUI();

        for (int i = 0; i < model.getShoppingCart().getItems().size(); i++) {
            ItemCheckOut ci = new ItemCheckOut(model.getShoppingCart().getItems().get(i), model);
            pnlCart.add(ci, "wrap,growx");
            lblAmountValue.setText(model.getShoppingCart().getTotal() + Constants.currencySuffix);

            updateColors();
        }
    }

    /**
     * Initialize GUI
     */
    private void initializeGUI() {
        setSize(660, 600);
        setResizable(false);
        requestFocusInWindow();

        pnlSettings = new JPanel();
        getContentPane().add(pnlSettings, BorderLayout.CENTER);
        pnlSettings.setLayout(new MigLayout("", "[320px,grow][330px]", "[195.00,grow][][grow][28]"));

        panel = new JPanel();
        panel.setBorder(new TitledBorder(null, "Varukorg", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        pnlSettings.add(panel, "cell 0 0 1 3,grow");
        panel.setLayout(new BorderLayout(0, 0));

        scrollPane = new JScrollPane();
        panel.add(scrollPane);

        pnlCart = new JPanel();
        scrollPane.setViewportView(pnlCart);
        pnlCart.setLayout(new MigLayout("insets 0px, gapy 0px", "[grow]", "[32px]"));

        pnlAddressSettings = new AddressSettingsPanel(model);
        pnlSettings.add(pnlAddressSettings, "cell 1 0,grow");
        if (!model.getAccountHandler().getCurrentAccount().isAnonymous()) {
            pnlAddressSettings.setFirstName(model.getAccountHandler().getCurrentAccount().getFirstName());
            pnlAddressSettings.setLastName(model.getAccountHandler().getCurrentAccount().getLastName());
            pnlAddressSettings.setPhoneNumber(model.getAccountHandler().getCurrentAccount().getPhoneNumber());
            pnlAddressSettings.setMobilePhoneNumber(model.getAccountHandler().getCurrentAccount()
                    .getMobilePhoneNumber());
            pnlAddressSettings.setAddress(model.getAccountHandler().getCurrentAccount().getAddress());
            pnlAddressSettings.setPostAddress(model.getAccountHandler().getCurrentAccount().getPostAddress());
            pnlAddressSettings.setPostCode(model.getAccountHandler().getCurrentAccount().getPostCode());
        }

        btnConfirm = new JButton("Bekräfta");
        btnConfirm.addActionListener(this);
        btnConfirm.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        pnlCardSettings = new CardSettingsPanel(model);
        pnlSettings.add(pnlCardSettings, "cell 1 1,grow");

        pnlAmount = new JPanel();
        pnlAmount.setBorder(new TitledBorder(null, "Summa", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        pnlSettings.add(pnlAmount, "cell 1 2,grow");
        pnlAmount.setLayout(new MigLayout("insets 0px 0px 8px 0px", "[282.00,grow]", "[grow]"));

        lblAmountValue = new JLabel("0:-");
        lblAmountValue.setHorizontalAlignment(SwingConstants.CENTER);
        lblAmountValue.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        pnlAmount.add(lblAmountValue, "cell 0 0,grow");

        pnlSettings.add(btnConfirm, "cell 1 3,alignx right,growy");

        pack();
    }
    
    /**
     * Row striping
     */
    private void updateColors() {
        for (int i = 0; i < pnlCart.getComponentCount(); i++) {
            pnlCart.getComponents()[i].setBackground((i % 2 == 0) ? Constants.ALT_COLOR : null);
        }

        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnConfirm) {
            CreditCard card = null;

            if (pnlCardSettings.getSavedCardsIndex() == 0) {
                card = new CreditCard(pnlCardSettings.getCardNumber(), pnlCardSettings.getValidMonth(),
                        pnlCardSettings.getValidYear(), pnlCardSettings.getCVC(), model.getAccountHandler()
                                .getCurrentAccount());
            } else {
                card = pnlCardSettings.getSelectedCard();
            }

            if (model.getAccountHandler().getCurrentAccount().isAnonymous()) {
                System.out.println("Försöker handla ej inloggad");
                if (model.accountVerify("Anonymous", "password", "anonymous@mail.com",
                        pnlAddressSettings.getFirstName(), pnlAddressSettings.getLastName(),
                        pnlAddressSettings.getAddress(), pnlAddressSettings.getMobilePhoneNumber(),
                        pnlAddressSettings.getPhoneNumber(), pnlAddressSettings.getPostAddress(),
                        pnlAddressSettings.getPostCode()).isEmpty()) {
                    model.orderPlace(card);
                }
            } else {
                if (model.accountVerify(model.getAccountHandler().getCurrentAccount().getUserName(),
                        model.getAccountHandler().getCurrentAccount().getPassword(),
                        model.getAccountHandler().getCurrentAccount().getEmail(), pnlAddressSettings.getFirstName(),
                        pnlAddressSettings.getLastName(), pnlAddressSettings.getAddress(),
                        pnlAddressSettings.getMobilePhoneNumber(), pnlAddressSettings.getPhoneNumber(),
                        pnlAddressSettings.getPostAddress(), pnlAddressSettings.getPostCode()).isEmpty()) {
                    model.orderPlace(card);
                }
            }

        }

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (!isVisible())
            return; // Ugly fix

        if (evt.getPropertyName() == "cart_removeitem") {

            for (int i = 0; i < pnlCart.getComponentCount(); i++) {
                if (((ItemCheckOut) pnlCart.getComponent(i)).getShoppingItem() == (ShoppingItem) evt.getNewValue()) {
                    pnlCart.remove(i);
                    break;
                }
            }

            updateColors();
            pnlCart.revalidate();
            repaint();
        }

        if (evt.getPropertyName() == "account_verify") {
            @SuppressWarnings("unchecked")
            List<String> errors = (ArrayList<String>) evt.getNewValue();

            if (!errors.isEmpty()) {
                String msg = "";
                
                if (errors.contains("firstname_tooshort"))
                    msg += "Förnamn saknas\n";
                if (errors.contains("lastname_tooshort"))
                    msg += "Efternamn saknas\n";
                if (errors.contains("address_invalid"))
                    msg += "Address saknas\n";
                if (errors.contains("mobilephone_invalid"))
                    msg += "Fel format på mobilnummer\n";
                if (errors.contains("phone_invalid"))
                    msg += "Fel format på telefonnummer\n";
                if (errors.contains("postaddress_invalid"))
                    msg += "Postadress saknas\n";
                if (errors.contains("postcode_invalid"))
                    msg += "Postnummer saknas\n";
                
                JOptionPane.showMessageDialog(this, msg, "Fel vid utcheckning", JOptionPane.WARNING_MESSAGE);
            }
        }

        if (evt.getPropertyName() == "order_place") {
            @SuppressWarnings("unchecked")
            List<String> errors = (ArrayList<String>) evt.getNewValue();

            if (!errors.isEmpty()) {
                String msg = "";
                
                if (errors.contains("cardnumber_invalid"))
                    msg += "Fel format på kortnummer\n";
                if (errors.contains("cvc_invalid"))
                    msg += "Fel format på cvc\n";
                if (errors.contains("month_invalid"))
                    msg += "Utgångsmånad saknas\n";
                if (errors.contains("year_invalid"))
                    msg += "Utgångsår saknas\n";
                
                JOptionPane.showMessageDialog(this, msg, "Fel vid utcheckning", JOptionPane.WARNING_MESSAGE);
            }
        }

        if (evt.getPropertyName() == "order_placed") {
            JOptionPane.showMessageDialog(this, "Tack för din beställning!", "Order bekräftad",
                    JOptionPane.INFORMATION_MESSAGE);
            dispose();
        }

    }

}
