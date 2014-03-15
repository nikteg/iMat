package se.chalmers.dat215.grupp14;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;
import se.chalmers.dat215.grupp14.backend.Constants;
import se.chalmers.dat215.grupp14.backend.CreditCard;
import se.chalmers.dat215.grupp14.backend.IMatModel;

import com.alee.extended.image.WebImage;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.text.WebTextField;

/**
 * Credit card panel responsible for showing the credit card information
 * @author Niklas Tegnander, Mikael Lönn and Oskar Jönefors
 */
@SuppressWarnings("serial")
public class CardSettingsPanel extends JPanel implements ActionListener, PropertyChangeListener {
    private WebTextField txtCardNumber;
    private WebTextField txtCVC;
    private WebComboBox cBoxMonth;
    private WebComboBox cBoxSavedCards;
    private WebComboBox cBoxYear;
    private JButton btnRemove;
    private JButton btnSave;
    private JLabel lblCardNumber;
    private JLabel lblCVC;
    private JLabel lblDateExpire;
    private JLabel lblIcon;
    private JLabel lblDivider;
    private JPanel panel;
    private JPanel panel_1;
    private IMatModel model;
    private List<CreditCard> cardList = new ArrayList<CreditCard>();

    /**
     * Constructor
     */
    public CardSettingsPanel() {
        super();
        initializeGUI();
    }

    /**
     * Constructor with a given model
     * @param model
     */
    public CardSettingsPanel(IMatModel model) {
        this();
        this.model = model;
        model.addPropertyChangeListener(this);

        if (!model.getAccountHandler().getCurrentAccount().isAnonymous()) {
            cardList = model.getCreditCardHandler().getCards(model.getAccountHandler().getCurrentAccount());
        }

        if (!model.getAccountHandler().getCurrentAccount().isAnonymous()) {
            btnSave = new JButton("Spara kort");
            panel_1.add(btnSave, "cell 5 2");
            btnSave.addActionListener(this);
            btnSave.setActionCommand("card_add");
        }

        cBoxSavedCards.setEnabled(!model.getAccountHandler().getCurrentAccount().isAnonymous());
        updateCards();
    }

    /**
     * Initialize GUI
     */
    private void initializeGUI() {
        setBorder(new TitledBorder(null, "Betalningsuppgifter", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        setLayout(new MigLayout("", "[grow]", "[72px:n][][112.00]"));

        panel = new JPanel();
        panel.setPreferredSize(new Dimension(64, 40));
        panel.setBorder(new TitledBorder(null, "Sparade kort", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        add(panel, "cell 0 0,grow");
        panel.setLayout(new MigLayout("", "[64px][140px,grow][grow]", "[40px,grow]"));

        lblIcon = new JLabel("");
        panel.add(lblIcon, "cell 0 0,grow");

        cBoxSavedCards = new WebComboBox();
        cBoxSavedCards.setModel(new DefaultComboBoxModel(new String[] { "Inga sparade kort" }));
        cBoxSavedCards.addActionListener(this);
        panel.add(cBoxSavedCards, "cell 1 0,growx");

        btnRemove = new JButton("Ta bort");
        btnRemove.addActionListener(this);
        btnRemove.setEnabled(false);
        panel.add(btnRemove, "cell 2 0");

        panel_1 = new JPanel();
        panel_1.setBorder(new TitledBorder(null, "L\u00E4gg till kort", TitledBorder.LEADING, TitledBorder.TOP, null,
                null));
        add(panel_1, "cell 0 1 1 2,grow");
        panel_1.setLayout(new MigLayout("", "[][42,grow,right][12.00,grow][42,grow,left][][48]", "[][][]"));

        lblCardNumber = new JLabel("Kortnr");
        panel_1.add(lblCardNumber, "cell 0 0,alignx trailing");

        txtCardNumber = new WebTextField();
        txtCardNumber.setColumns(10);
        panel_1.add(txtCardNumber, "cell 1 0 5 1,growx");

        lblDateExpire = new JLabel("Utg.datum");
        panel_1.add(lblDateExpire, "cell 0 1,alignx trailing");

        cBoxMonth = new WebComboBox();
        cBoxMonth.setModel(new DefaultComboBoxModel(new String[] { "MM", "01", "02", "03", "04", "05", "06", "07",
                "08", "09", "10", "11", "12" }));
        panel_1.add(cBoxMonth, "cell 1 1");

        lblDivider = new JLabel("/");
        panel_1.add(lblDivider, "cell 2 1,alignx center");

        cBoxYear = new WebComboBox();

        List<String> yearList = new ArrayList<String>();
        yearList.add("ÅÅ");

        for (int i = 0; i < 10; i++) {
            yearList.add(("" + (Calendar.getInstance().get(Calendar.YEAR) + i)).substring(2));
        }

        cBoxYear.setModel(new DefaultComboBoxModel(yearList.toArray(new String[yearList.size()])));
        panel_1.add(cBoxYear, "cell 3 1");

        lblCVC = new JLabel("CVC");
        panel_1.add(lblCVC, "cell 4 1,alignx trailing");

        txtCVC = new WebTextField();
        txtCVC.setColumns(10);
        panel_1.add(txtCVC, "cell 5 1,growx");
    }

    /**
     * Get card number
     * @return
     */
    public String getCardNumber() {
        return txtCardNumber.getText();
    }

    /**
     * Get valid month
     * @return
     */
    public String getValidMonth() {
        return (String) cBoxMonth.getSelectedItem();
    }

    /**
     * Get valid year
     * @return
     */
    public String getValidYear() {
        return (String) cBoxYear.getSelectedItem();
    }

    /**
     * Get CVC
     * @return
     */
    public String getCVC() {
        return txtCVC.getText();
    }

    /**
     * Get saved cards combo box index
     * @return
     */
    public int getSavedCardsIndex() {
        return cBoxSavedCards.getSelectedIndex();
    }

    /**
     * Update cards
     */
    public void updateCards() {
        List<String> cardModelList = new ArrayList<String>();

        cardModelList.add(cardList.isEmpty() ? "Inga sparade kort" : "Välj ett kort");

        for (CreditCard card : cardList) {
            String censored = "XXXX-";

            if (card.getCardType() == "amex") {
                censored += "XXXXXX-"
                        + card.getCardNumber().substring(card.getCardNumber().length() - 5,
                                card.getCardNumber().length());
                ;
            } else {
                censored += "XXXX-XXXX-"
                        + card.getCardNumber().substring(card.getCardNumber().length() - 4,
                                card.getCardNumber().length());
                ;
            }

            cardModelList.add(censored);
        }

        cBoxSavedCards.setModel(new DefaultComboBoxModel(cardModelList.toArray(new String[cardModelList.size()])));
        lblIcon.setIcon(null);

        btnRemove.setEnabled(!model.getAccountHandler().getCurrentAccount().isAnonymous() && !cardList.isEmpty()
                && (cBoxSavedCards.getSelectedIndex() > 0));
    }
    
    /**
     * Get selected card
     * @return
     */
    public CreditCard getSelectedCard() {
        return cardList.get(Math.max(cBoxSavedCards.getSelectedIndex() - 1, 0));
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

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getActionCommand() == "card_add") {
            model.cardAdd(new CreditCard(getCardNumber(), getValidMonth(), getValidYear(), getCVC(), model
                    .getAccountHandler().getCurrentAccount()));
        }

        if (event.getSource() == btnRemove) {
            if (model.getCreditCardHandler().getCards(model.getAccountHandler().getCurrentAccount()).isEmpty())
                return;

            model.removeCard(cardList.get(cBoxSavedCards.getSelectedIndex() - 1));
            updateCards();
        }

        if (event.getSource() == cBoxSavedCards) {
            btnRemove.setEnabled((cBoxSavedCards.getSelectedIndex() > 0));

            lblIcon.setIcon(null);

            if (!cardList.isEmpty()) {
                if (cBoxSavedCards.getSelectedIndex() > 0) {
                    String cardType = cardList.get(cBoxSavedCards.getSelectedIndex() - 1).getCardType();

                    lblIcon.setIcon(new ImageIcon(CardSettingsPanel.class.getResource("resources/images/" + cardType
                            + ".png")));
                }

            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (!isVisible())
            return; // Ugly fix

        if (evt.getPropertyName() == "card_add" || evt.getPropertyName() == "order_place") {
            @SuppressWarnings("unchecked")
            List<String> errors = (ArrayList<String>) evt.getNewValue();

            resetError(txtCardNumber);
            resetError(txtCVC);

            if (!errors.isEmpty()) {
                if (errors.contains("cardnumber_invalid"))
                    setError(txtCardNumber);
                if (errors.contains("cvc_invalid"))
                    setError(txtCVC);
            }
        }

        if (evt.getPropertyName() == "card_added") {
            txtCardNumber.setText("");
            cBoxMonth.setSelectedIndex(0);
            cBoxYear.setSelectedIndex(0);
            txtCVC.setText("");
            updateCards();
        }

        if (evt.getPropertyName() == "card_removed") {
            updateCards();
        }

    }
}
