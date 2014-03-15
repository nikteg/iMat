package se.chalmers.dat215.grupp14;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import se.chalmers.ait.dat215.project.Order;
import se.chalmers.ait.dat215.project.ShoppingItem;
import se.chalmers.dat215.grupp14.backend.Constants;
import se.chalmers.dat215.grupp14.backend.IMatModel;
import javax.swing.ImageIcon;

/**
 * Item in order view
 * @author Niklas Tegnander, Mikael Lönn and Oskar Jönefors
 */
@SuppressWarnings("serial")
public class OrderItem extends JPanel implements ActionListener, PropertyChangeListener {
    private IMatModel model;
    private Order order;
    private JLabel label;
    private JLabel lblDatum;
    private JButton btnInfo;
    private HistoryView historyView;

    /**
     * Constructor
     */
    public OrderItem() {
        super();
        initializeGUI();
    }

    /**
     * Constructor with given history view, order and model
     * @param historyView
     * @param order
     * @param model
     */
    public OrderItem(HistoryView historyView, Order order, IMatModel model) {
        this();
        this.order = order;
        this.model = model;
        this.historyView = historyView;
        lblDatum.setText(Constants.dateFormat.format(order.getDate()));
        label.setText(Constants.currencyFormat.format(getTotalPrice(order)) + Constants.currencySuffix);
        this.model.addPropertyChangeListener(this);

    }

    /**
     * Initialize GUI
     */
    private void initializeGUI() {
        setLayout(new MigLayout("insets 4px", "[grow][][]", "[60px]"));

        lblDatum = new JLabel("");
        add(lblDatum, "cell 0 0");

        label = new JLabel("");
        add(label, "cell 1 0");

        btnInfo = new JButton("");
        btnInfo.setToolTipText("Expandera order");
        btnInfo.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        btnInfo.setContentAreaFilled(false);
        btnInfo.setBorderPainted(false);
        btnInfo.setIcon(new ImageIcon(OrderItem.class.getResource("resources/images/icons/more.png")));
        btnInfo.addActionListener(this);
        btnInfo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        add(btnInfo, "cell 2 0,alignx right");
    }

    /**
     * Get total price
     * @param order
     * @return
     */
    private double getTotalPrice(Order order) {
        double price = 0;
        for (ShoppingItem si : order.getItems()) {
            price += si.getTotal();
        }
        return price;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == btnInfo) {
            historyView.addShoppingItems(order);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }

}
