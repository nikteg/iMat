package se.chalmers.dat215.grupp14;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import net.miginfocom.swing.MigLayout;
import com.alee.extended.image.WebImage;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.text.WebTextField;
import java.awt.Dimension;

/**
 * Credit card panel responsible for showing the credit card information
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
    private CCardHandler cardHandler;
    private List<CCard> cardList;
    
    /**
     * Constructor
     */
    public CardSettingsPanel() {
        super();
        initialize();
    }
    
    /**
     * Constructor with a given model
     * @param model
     */
    public CardSettingsPanel(IMatModel model) {
        this();
        this.model = model;
        cardHandler = model.getCCardHandler();
        cardList = cardHandler.getCCards(model.getAccountHandler().getCurrentAccount());
        model.addPropertyChangeListener(this);
        updateCards();
    }

    /**
     * Initialize GUI
     */
    private void initialize() {
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
        yearWebComboBox.setModel(new DefaultComboBoxModel(new String[] { "ÅÅ", "14", "15", "16", "17", "18", "19",
                "20", "21", "22", "23", "24", "25", "26", "27", "28", "29" }));
        panel_1.add(yearWebComboBox, "cell 3 1");

        cvcLabel = new JLabel("CVC");
        panel_1.add(cvcLabel, "cell 4 1,alignx trailing");

        cvcTextField = new WebTextField();
        cvcTextField.setColumns(10);
        panel_1.add(cvcTextField, "cell 5 1,growx");

        saveCardButton = new JButton("Spara kort");
        panel_1.add(saveCardButton, "cell 5 2");
        saveCardButton.addActionListener(this);
        saveCardButton.setActionCommand("card_save");
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

    public void updateCards() {

        if (model.getAccount().isAnonymous()) {
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
                for (CCard cc : cardList) {
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
                                .getResource("resources/images/icons/mastercard.png")));
                    }
                    if (cardType.equals("visa")) {
                        cardsfield[cardList.indexOf(cc) + 1] = visa;
                        iconLabel.setIcon(new ImageIcon(CardSettingsPanel.class
                                .getResource("resources/images/icons/visa.png")));
                    }
                    if (cardType.equals("amex")) {
                        cardsfield[cardList.indexOf(cc) + 1] = amex;
                        iconLabel.setIcon(new ImageIcon(CardSettingsPanel.class
                                .getResource("resources/images/icons/amex.png")));
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

        }
    }

    public void resetError(WebTextField wt) {
        wt.setBackground(Color.WHITE);
    }

    public void setError(WebTextField wt) {
        wt.setBackground(Constants.ERROR_COLOR);
        wt.setTrailingComponent(new WebImage(AddressSettingsPanel.class.getResource("resources/images/warning.png")));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == "card_save") {
            model.cardAdd(new CCard(getCardNumber(), getValidMonth(), getValidYear(), getCVC(), model.getAccountHandler().getCurrentAccount()));
        }

        if (e.getSource() == removeButton) {
            model.removeCard(cardList.get(savedCardsWebComboBox.getSelectedIndex() - 1));
            updateCards();
        }

        if (e.getSource() == savedCardsWebComboBox) {
            iconLabel.setIcon(null);
            if (cardList.size() != 0) {
                if (savedCardsWebComboBox.getSelectedIndex() != 0) {
                    String cardType = cardList.get(savedCardsWebComboBox.getSelectedIndex() - 1).getCardType();
                    if (cardType.equalsIgnoreCase("mastercard")) {
                        iconLabel.setIcon(new ImageIcon(CardSettingsPanel.class
                                .getResource("resources/images/icons/mastercard.png")));
                    }
                    if (cardType.equalsIgnoreCase("visa")) {
                        iconLabel.setIcon(new ImageIcon(CardSettingsPanel.class
                                .getResource("resources/images/icons/visa.png")));
                    }
                    if (cardType.equalsIgnoreCase("amex")) {
                        iconLabel.setIcon(new ImageIcon(CardSettingsPanel.class
                                .getResource("resources/images/icons/amex.png")));
                    }
                    iconLabel.repaint();
                    panel.repaint();
                    repaint();
                }

            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (!isVisible())
            return; // TODO

        if (evt.getPropertyName() == "card_save") {
            @SuppressWarnings("unchecked")
            List<String> errors = (ArrayList<String>) evt.getNewValue();

            if (!errors.isEmpty()) {
                if (errors.contains("cardnumber_invalid"))
                    setError(cardNumberTextField);
                if (errors.contains("cvc_invalid"))
                    setError(cardNumberTextField);
            }
        }

        if (evt.getPropertyName() == "card_saved") {
            updateCards();
        }

    }

    public CCard getSelectedCard() {
        return cardList.get(savedCardsWebComboBox.getSelectedIndex() - 1);
    }
}
