package se.chalmers.dat215.grupp14;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import se.chalmers.ait.dat215.project.ShoppingItem;

@SuppressWarnings("serial")
public class ItemCheckOut extends JPanel {
    private ShoppingItem shoppingItem;
    private JLabel lblName;
    private JLabel lblPrice;
    private JLabel lblAmount;

    public ItemCheckOut() {
        super();
        initializeGUI();
    }

    public ItemCheckOut(ShoppingItem shoppingItem, IMatModel model) {
        this();
        this.shoppingItem = shoppingItem;
        lblName.setText(shoppingItem.getProduct().getName());
        lblAmount.setText(((Double) shoppingItem.getAmount()).intValue() + " "
                + shoppingItem.getProduct().getUnitSuffix());
        lblPrice.setText(shoppingItem.getTotal() + Constants.currencySuffix);
    }

    private void initializeGUI() {
        setPreferredSize(new Dimension(256, 64));
        setLayout(new MigLayout("insets 0px  8px 0px  8px", "[grow][72px][72px]", "[64px]"));

        lblName = new JLabel("<name>");
        add(lblName, "cell 0 0,alignx left");

        lblAmount = new JLabel("<amount>");
        add(lblAmount, "cell 1 0,alignx left");

        lblPrice = new JLabel("<price>");
        lblPrice.setBackground(Color.RED);
        add(lblPrice, "cell 2 0,alignx right");
    }

    public ShoppingItem getShoppingItem() {
        return shoppingItem;
    }

}
