package se.chalmers.dat215.grupp14;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
 * 
 * @author Niklas Tegnander, Mikael Lönn and Oskar Jönefors
 */
@SuppressWarnings("serial")
public class CardSettingsPanel extends JPanel implements ActionListener, PropertyChangeListener {
    private WebTextField cardNumberTextField;
    private WebTextField cvcTextField;
    private WebComboBox monthWebComboBox;
    private WebComboBox savedCardsWebComboBox;
    private WebComboBox yearWebComboBox;
    private JButton removeButton;
    private JButton saveCardButton;
    private JLabel cardNumberLabel;
    private JLabel cvcLabel;
    private JLabel expireDateLabel;
    private JLabel iconLabel;
    private JLabel slashLabel;
    private JPanel panel;
    private JPanel panel_1;
    private IMatModel model;
    private List<CreditCard> cardList;

    /**
     * Constructor
     */
    public CardSettingsPanel() {
        super();
        initializeGUI();
    }

    /**
     * Constructor with a given model
     * 
     * @param model
     */
    public CardSettingsPanel(IMatModel model) {
        this();
        this.model = model;
        model.addPropertyChangeListener(this);
        
        if (!model.getAccountHandler().getCurrentAccount().isAnonymous()) {
            cardList = model.getCreditCardHandler().getCards(model.getAccountHandler().getCurrentAccount());
        }
        
        removeButton.setEnabled(!model.getAccountHandler().getCurrentAccount().isAnonymous());
        savedCardsWebComboBox.setEnabled(!model.getAccountHandler().getCurrentAccount().isAnonymous());
        
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

        iconLabel = new JLabel("");
        panel.add(iconLabel, "cell 0 0,grow");

        savedCardsWebComboBox = new WebComboBox();
        savedCardsWebComboBox.setModel(new DefaultComboBoxModel(new String[] { "Inga sparade kort" }));
        savedCardsWebComboBox.addActionListener(this);
        panel.add(savedCardsWebComboBox, "cell 1 0,growx");

        removeButton = new JButton("Ta bort");
        removeButton.addActionListener(this);
        panel.add(removeButton, "cell 2 0");

        panel_1 = new JPanel();
        panel_1.setBorder(new TitledBorder(null, "L\u00E4gg till kort", TitledBorder.LEADING, TitledBorder.TOP, null,
                null));
        add(panel_1, "cell 0 1 1 2,grow");
        panel_1.setLayout(new MigLayout("", "[][42,grow,right][12.00,grow][42,grow,left][][48]", "[][][]"));

        cardNumberLabel = new JLabel("Kortnr");
        panel_1.add(cardNumberLabel, "cell 0 0,alignx trailing");

        cardNumberTextField = new WebTextField();
        cardNumberTextField.setColumns(10);
        panel_1.add(cardNumberTextField, "cell 1 0 5 1,growx");

        expireDateLabel = new JLabel("Utg.datum");
        panel_1.add(expireDateLabel, "cell 0 1,alignx trailing");

        monthWebComboBox = new WebComboBox();
        monthWebComboBox.setModel(new DefaultComboBoxModel(new String[] { "MM", "01", "02", "03", "04", "05", "06",
                "07", "08", "09", "10", "11", "12" }));
        panel_1.add(monthWebComboBox, "cell 1 1");

        slashLabel = new JLabel("/");
        panel_1.add(slashLabel, "cell 2 1,alignx center");

        yearWebComboBox = new WebComboBox();
        
        List<String> yearList = new ArrayList<String>();
        yearList.add("ÅÅ");
        
        for (int i = 0; i < 10; i++) {
            yearList.add(("" + (Calendar.getInstance().get(Calendar.YEAR) + i)).substring(2));
        }
        
        yearWebComboBox.setModel(new DefaultComboBoxModel(yearList.toArray(new String[yearList.size()])));
        panel_1.add(yearWebComboBox, "cell 3 1");

        cvcLabel = new JLabel("CVC");
        panel_1.add(cvcLabel, "cell 4 1,alignx trailing");

        cvcTextField = new WebTextField();
        cvcTextField.setColumns(10);
        panel_1.add(cvcTextField, "cell 5 1,growx");

        saveCardButton = new JButton("Spara kort");
        panel_1.add(saveCardButton, "cell 5 2");
        saveCardButton.addActionListener(this);
        saveCardButton.setActionCommand("card_add");
    }

    public String getCardNumber() {
        return cardNumberTextField.getText();
    }

    public String getValidMonth() {
        return (String) monthWebComboBox.getSelectedItem();
    }

    public String getValidYear() {
        return (String) yearWebComboBox.getSelectedItem();
    }

    public String getCVC() {
        return cvcTextField.getText();
    }

    public int getComboboxindex() {
        return savedCardsWebComboBox.getSelectedIndex();
    }

    
    // TODO Change days to years
    // TODO Check if anonymous
    public void updateCards() {
        List<String> cardModelList = new ArrayList<String>();
        
        cardModelList.add(cardList.isEmpty() ? "Inga sparade kort" : "Välj ett kort");
        
        for (CreditCard card : cardList) {
            String censored = "XXXX-";
            
            if (card.getCardType() == "amex") {
                censored += "XXXXXX-" + card.getCardNumber().substring(card.getCardNumber().length() - 5, card.getCardNumber().length());;
            } else {
                censored += "XXXX-XXXX-" + card.getCardNumber().substring(card.getCardNumber().length() - 4, card.getCardNumber().length());;
            }
            
            cardModelList.add(censored);
        }
        
        savedCardsWebComboBox.setModel(new DefaultComboBoxModel(cardModelList.toArray(new String[cardModelList.size()])));
        iconLabel.setIcon(null);
        
        /*
        if (model.getAccountHandler().getCurrentAccount().isAnonymous()) {
            panel.remove(savedCardsWebComboBox);
            savedCardsWebComboBox = new WebComboBox();
            savedCardsWebComboBox.setEnabled(false);
            savedCardsWebComboBox.setModel(new DefaultComboBoxModel(new String[] { "Inga sparade kort" }));
            iconLabel.setIcon(null);
            panel.add(savedCardsWebComboBox, "cell 1 0,growx");
            savedCardsWebComboBox.revalidate();
            savedCardsWebComboBox.repaint();
            removeButton.setEnabled(false);
            saveCardButton.setEnabled(false);

            iconLabel.repaint();
            panel.repaint();
            repaint();

        } else {
            if (cardList != null) {
                panel.remove(savedCardsWebComboBox);
                savedCardsWebComboBox = new WebComboBox();
                savedCardsWebComboBox.addActionListener(this);
                removeButton.setEnabled(true);
                saveCardButton.setEnabled(true);

                String[] cardsfield = new String[cardList.size() + 1];
                cardsfield[0] = "Välj kort";
                for (CreditCard cc : cardList) {
                    String visa = "XXXX-XXXX-XXXX-"
                            + cc.getCardNumber()
                                    .substring(cc.getCardNumber().length() - 4, cc.getCardNumber().length());
                    String mastercard = "XXXX-XXXX-XXXX-"
                            + cc.getCardNumber()
                                    .substring(cc.getCardNumber().length() - 4, cc.getCardNumber().length());
                    String amex = "XXXX-XXXXXX-"
                            + cc.getCardNumber()
                                    .substring(cc.getCardNumber().length() - 5, cc.getCardNumber().length());

                    String cardType = cc.getCardType();
                    if (cardType.equals("mastercard")) {
                        cardsfield[cardList.indexOf(cc) + 1] = mastercard;
                        iconLabel.setIcon(new ImageIcon(CardSettingsPanel.class
                                .getResource("resources/images/mastercard.png")));
                    }
                    if (cardType.equals("visa")) {
                        cardsfield[cardList.indexOf(cc) + 1] = visa;
                        iconLabel.setIcon(new ImageIcon(CardSettingsPanel.class
                                .getResource("resources/images/visa.png")));
                    }
                    if (cardType.equals("amex")) {
                        cardsfield[cardList.indexOf(cc) + 1] = amex;
                        iconLabel.setIcon(new ImageIcon(CardSettingsPanel.class
                                .getResource("resources/images/amex.png")));
                    }
                }
                if (cardsfield.length == 0) {
                    savedCardsWebComboBox.setModel(new DefaultComboBoxModel(new String[] { "Inga sparade kort" }));
                    iconLabel.setIcon(null);
                } else {
                    savedCardsWebComboBox.setModel(new DefaultComboBoxModel(cardsfield));
                }

                if (savedCardsWebComboBox.getSelectedIndex() == 0) {
                    iconLabel.setIcon(null);
                }

                panel.add(savedCardsWebComboBox, "cell 1 0,growx");
                savedCardsWebComboBox.revalidate();
                savedCardsWebComboBox.repaint();

                iconLabel.repaint();
                panel.repaint();
                repaint();

            }

        }*/
    }

    public void resetError(WebTextField wt) {
        wt.setBackground(Color.WHITE);
        wt.setTrailingComponent(null);
    }

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

        if (event.getSource() == removeButton) {
            if (model.getCreditCardHandler().getCards(model.getAccountHandler().getCurrentAccount()).isEmpty())
                return;

            model.removeCard(cardList.get(savedCardsWebComboBox.getSelectedIndex() - 1));
            updateCards();
        }

     // TODO Disable remove button when index 0 is selected
        if (event.getSource() == savedCardsWebComboBox) {
            iconLabel.setIcon(null);

            if (!cardList.isEmpty()) {
                if (savedCardsWebComboBox.getSelectedIndex() > 0) {
                    String cardType = cardList.get(savedCardsWebComboBox.getSelectedIndex() - 1).getCardType();

                    iconLabel.setIcon(new ImageIcon(CardSettingsPanel.class
                            .getResource("resources/images/" + cardType + ".png")));
                }

            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (!isVisible())
            return; // Ugly fix

        if (evt.getPropertyName() == "card_add") {
            @SuppressWarnings("unchecked")
            List<String> errors = (ArrayList<String>) evt.getNewValue();

            resetError(cardNumberTextField);
            resetError(cvcTextField);
            
            if (!errors.isEmpty()) {
                if (errors.contains("cardnumber_invalid"))
                    setError(cardNumberTextField);
                if (errors.contains("cvc_invalid"))
                    setError(cvcTextField);
            }
        }

        if (evt.getPropertyName() == "card_added") {
            cardNumberTextField.setText("");
            monthWebComboBox.setSelectedIndex(0);
            yearWebComboBox.setSelectedIndex(0);
            cvcTextField.setText("");
            updateCards();
        }
        
        if (evt.getPropertyName() == "card_removed") {
            updateCards();
        }

    }

    public CreditCard getSelectedCard() {
        return cardList.get(Math.max(savedCardsWebComboBox.getSelectedIndex() - 1, 0));
    }
}
