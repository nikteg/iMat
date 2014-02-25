import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import net.miginfocom.swing.MigLayout;
import com.alee.laf.combobox.WebComboBox;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;


public class CardSettingsPanel extends JPanel{
	private JPanel panel;
	private JPanel panel_1;
	private WebComboBox savedCardsWebComboBox;
	private JButton removeButton;
	private JLabel expireDateLabel;
	private WebComboBox monthWebComboBox;
	private JLabel slashLabel;
	private WebComboBox yearWebComboBox;
	private JLabel cvcLabel;
	private JTextField cvcTextField;
	private JTextField cardNumberTextField;
	private JLabel cardNumberLabel;

	public CardSettingsPanel() {
		// TODO Auto-generated constructor stub
		initialize();
	}
	private void initialize() {
		setBorder(new TitledBorder(null, "Betalningsuppgifter", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(new MigLayout("", "[grow]", "[83][193,grow]"));
		
		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Sparade kort", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(panel, "cell 0 0,grow");
		panel.setLayout(new MigLayout("", "[grow][]", "[]"));
		
		savedCardsWebComboBox = new WebComboBox();
		panel.add(savedCardsWebComboBox, "cell 0 0,growx");
		
		removeButton = new JButton("Ta bort");
		panel.add(removeButton, "cell 1 0");
		
		panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "L\u00E4gg till kort", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(panel_1, "cell 0 1,grow");
		panel_1.setLayout(new MigLayout("", "[][42,grow,right][12.00,grow][42,grow,left][][48]", "[][]"));
		
		cardNumberLabel = new JLabel("Kortnr");
		panel_1.add(cardNumberLabel, "cell 0 0,alignx trailing");
		
		cardNumberTextField = new JTextField();
		cardNumberTextField.setColumns(10);
		panel_1.add(cardNumberTextField, "cell 1 0 5 1,growx");
		
		expireDateLabel = new JLabel("Utg.datum");
		panel_1.add(expireDateLabel, "cell 0 1,alignx trailing");
		
		monthWebComboBox = new WebComboBox();
		monthWebComboBox.setModel(new DefaultComboBoxModel(new String[] {"MM", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}));
		panel_1.add(monthWebComboBox, "cell 1 1");
		
		slashLabel = new JLabel("/");
		panel_1.add(slashLabel, "cell 2 1,alignx center");
		
		yearWebComboBox = new WebComboBox();
		yearWebComboBox.setModel(new DefaultComboBoxModel(new String[] {"ÅÅ", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29"}));
		panel_1.add(yearWebComboBox, "cell 3 1");
		
		cvcLabel = new JLabel("CVC");
		panel_1.add(cvcLabel, "cell 4 1,alignx trailing");
		
		cvcTextField = new JTextField();
		cvcTextField.setColumns(10);
		panel_1.add(cvcTextField, "cell 5 1,growx");
	}

}
