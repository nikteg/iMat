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

import com.alee.laf.rootpane.WebDialog;

@SuppressWarnings("serial")
public class CheckOutWindow extends WebDialog implements ActionListener, PropertyChangeListener {
    private IMatModel model;
    private JPanel settingsPanel;
    private CardSettingsPanel cardSettingsPanel;
    private AddressSettingsPanel addressSettingsPanel;
    private JButton btnConfirm;
    private JPanel panel;
    private JScrollPane scrollPane;
    private JPanel cartPanel;
    private JLabel lblAmountValue;
    private JPanel amountPanel;

    public CheckOutWindow(JFrame frame, IMatModel model) {
        super(frame, "Checkout", true);
        this.model = model;
        this.model.addPropertyChangeListener(this);

        initializeGUI();

        for (int i = 0; i < model.getShoppingCart().getItems().size(); i++) {
            ItemCheckOut ci = new ItemCheckOut(model.getShoppingCart().getItems().get(i), model);
            cartPanel.add(ci, "wrap,growx");
            lblAmountValue.setText(model.getShoppingCart().getTotal() + Constants.currencySuffix);

            updateColors();
        }
    }

    private void updateColors() {
        for (int i = 0; i < cartPanel.getComponentCount(); i++) {
            cartPanel.getComponents()[i].setBackground((i % 2 == 0) ? Constants.ALT_COLOR : null);
        }

        repaint();
    }

    private void initializeGUI() {
        setSize(660, 600);
        setResizable(false);
        requestFocusInWindow();

        settingsPanel = new JPanel();
        getContentPane().add(settingsPanel, BorderLayout.CENTER);
        settingsPanel.setLayout(new MigLayout("", "[320px,grow][330px]", "[195.00,grow][][grow][28]"));

        panel = new JPanel();
        panel.setBorder(new TitledBorder(null, "Varukorg", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        settingsPanel.add(panel, "cell 0 0 1 3,grow");
        panel.setLayout(new BorderLayout(0, 0));

        scrollPane = new JScrollPane();
        panel.add(scrollPane);

        cartPanel = new JPanel();
        scrollPane.setViewportView(cartPanel);
        cartPanel.setLayout(new MigLayout("insets 0px, gapy 0px", "[grow]", "[32px]"));

        addressSettingsPanel = new AddressSettingsPanel(model);
        settingsPanel.add(addressSettingsPanel, "cell 1 0,grow");
        if (!model.getAccount().isAnonymous()) {
            addressSettingsPanel.setFirstName(model.getAccount().getFirstName());
            addressSettingsPanel.setLastName(model.getAccount().getLastName());
            addressSettingsPanel.setPhoneNumber(model.getAccount().getPhoneNumber());
            addressSettingsPanel.setMobilePhoneNumber(model.getAccount().getMobilePhoneNumber());
            addressSettingsPanel.setAddress(model.getAccount().getAddress());
            addressSettingsPanel.setPostAddress(model.getAccount().getPostAddress());
            addressSettingsPanel.setPostCode(model.getAccount().getPostCode());
        }

        btnConfirm = new JButton("Bekräfta");
        btnConfirm.addActionListener(this);
        btnConfirm.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        cardSettingsPanel = new CardSettingsPanel(model);
        settingsPanel.add(cardSettingsPanel, "cell 1 1,grow");

        amountPanel = new JPanel();
        amountPanel.setBorder(new TitledBorder(null, "Summa", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        settingsPanel.add(amountPanel, "cell 1 2,grow");
        amountPanel.setLayout(new MigLayout("insets 0px 0px 8px 0px", "[282.00,grow]", "[grow]"));

        lblAmountValue = new JLabel("0:-");
        lblAmountValue.setHorizontalAlignment(SwingConstants.CENTER);
        lblAmountValue.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        amountPanel.add(lblAmountValue, "cell 0 0,grow");

        settingsPanel.add(btnConfirm, "cell 1 3,alignx right,growy");

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnConfirm) {
            CCard card = cardSettingsPanel.getSelectedCard();

            if (cardSettingsPanel.getComboboxindex() == 0) {
                card = new CCard(cardSettingsPanel.getCardNumber(), cardSettingsPanel.getValidMonth(),
                        cardSettingsPanel.getValidYear(), cardSettingsPanel.getCVC(), model.getAccount());
            }


            if (model.accountVerify(model.getAccount().getUserName(), model.getAccount().getPassword(),
                    model.getAccount().getEmail(), addressSettingsPanel.getFirstName(),
                    addressSettingsPanel.getLastName(), addressSettingsPanel.getAddress(),
                    addressSettingsPanel.getMobilePhoneNumber(), addressSettingsPanel.getPhoneNumber(),
                    addressSettingsPanel.getPostAddress(), addressSettingsPanel.getPostCode()).isEmpty()) {
                model.orderPlace(card);
            }
                
        }

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (!isVisible())
            return; // TODO
        if (evt.getPropertyName() == "cart_removeitem") {

            for (int i = 0; i < cartPanel.getComponentCount(); i++) {
                if (((ItemCheckOut) cartPanel.getComponent(i)).getShoppingItem() == (ShoppingItem) evt.getNewValue()) {
                    cartPanel.remove(i);
                    break;
                }
            }

            updateColors();
            cartPanel.revalidate();
            repaint();
        }

        if (evt.getPropertyName() == "order_place") {
            @SuppressWarnings("unchecked")
            List<String> errors = (ArrayList<String>) evt.getNewValue();
            
            if (!errors.isEmpty()) {
                String msg = "";

                if (errors.contains("firstname_invalid"))
                    msg += "Förnamn saknas\n";
                if (errors.contains("lastname_invalid"))
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
                    msg += "Postadress saknas\n";
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
