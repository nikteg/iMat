package se.chalmers.dat215.grupp14;

import java.awt.CardLayout;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import net.miginfocom.swing.MigLayout;
import se.chalmers.ait.dat215.project.Order;
import se.chalmers.ait.dat215.project.ShoppingItem;
import se.chalmers.dat215.grupp14.backend.Constants;
import se.chalmers.dat215.grupp14.backend.IMatModel;
import com.alee.laf.text.WebTextField;
import javax.swing.ImageIcon;
import java.awt.BorderLayout;

/**
 * History view
 * 
 * @author Niklas Tegnander, Mikael Lönn and Oskar Jönefors
 */
@SuppressWarnings("serial")
public class HistoryView extends JPanel implements ActionListener, PropertyChangeListener {
    private IMatModel model;
    private JPanel allOrdersItem;
    private JScrollPane scrollPaneAllOrders;
    private JPanel allOrdersPanel;
    private JPanel oneOrderPanel;
    private JScrollPane scrollPaneOneOrder;
    private JPanel oneOrderItem;
    private JPanel panel_1;
    private JButton backButton;
    private JButton addAllToCartButton;
    private JLabel lblSumma;
    private JPanel panel_2;
    private WebTextField textField;
    private JButton btnSparaLista;
    private JPanel panel_3;
    private JLabel lblOrderName;
    private Order order;

    /**
     * Constructor
     */
    public HistoryView() {
        super();
        initializeGUI();
    }

    /**
     * Constructor with given model
     * 
     * @param model
     */
    public HistoryView(IMatModel model) {
        this();
        this.model = model;
        this.model.addPropertyChangeListener(this);
        updateHistoryView();
    }

    /**
     * Initialize GUI
     */
    private void initializeGUI() {
        setLayout(new CardLayout(0, 0));

        allOrdersPanel = new JPanel();
        add(allOrdersPanel, "allOrdersPanel");
        allOrdersPanel.setLayout(new MigLayout("insets 0px,gapy 0px", "[grow]", "[grow]"));

        scrollPaneAllOrders = new JScrollPane();
        allOrdersPanel.add(scrollPaneAllOrders, "cell 0 0,grow");
        scrollPaneAllOrders.setFocusable(false);
        scrollPaneAllOrders.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPaneAllOrders.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        allOrdersItem = new JPanel();
        scrollPaneAllOrders.setViewportView(allOrdersItem);
        allOrdersItem.setLayout(new MigLayout("insets 0px", "[grow]", "[36px]"));

        oneOrderPanel = new JPanel();
        add(oneOrderPanel, "oneOrderPanel");
        oneOrderPanel.setLayout(new MigLayout("insets 0px", "[50%,grow,left][50%,grow]", "[][][2px,grow][]"));

        panel_3 = new JPanel();
        oneOrderPanel.add(panel_3, "cell 0 0 2 1,grow");
        panel_3.setLayout(new BorderLayout(0, 0));

        backButton = new JButton("Tillbaka");
        panel_3.add(backButton, BorderLayout.WEST);
        backButton.setIcon(new ImageIcon(HistoryView.class.getResource("resources/images/icons/back.png")));
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        lblOrderName = new JLabel("<name>");
        panel_3.add(lblOrderName, BorderLayout.EAST);
        backButton.addActionListener(this);

        panel_2 = new JPanel();
        oneOrderPanel.add(panel_2, "cell 0 1 2 1,grow");
        panel_2.setLayout(new MigLayout("insets 0px", "[86px,grow][]", "[20px]"));

        textField = new WebTextField();
        textField.setInputPrompt("Namn på lista...");
        panel_2.add(textField, "cell 0 0,growx,aligny top");

        btnSparaLista = new JButton("Spara order");
        btnSparaLista.addActionListener(this);
        panel_2.add(btnSparaLista, "cell 1 0");

        panel_1 = new JPanel();
        oneOrderPanel.add(panel_1, "cell 0 2 2 1,grow");
        panel_1.setLayout(new MigLayout("insets 0px,gapy 0px", "[2px,grow]", "[2px,grow]"));

        scrollPaneOneOrder = new JScrollPane();
        panel_1.add(scrollPaneOneOrder, "cell 0 0,grow");
        scrollPaneOneOrder.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPaneOneOrder.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        oneOrderItem = new JPanel();
        scrollPaneOneOrder.setViewportView(oneOrderItem);
        oneOrderItem.setLayout(new MigLayout("insets 0px", "[grow]", "[36px]"));

        addAllToCartButton = new JButton("Lägg till alla varukorg");
        addAllToCartButton.addActionListener(this);
        addAllToCartButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        oneOrderPanel.add(addAllToCartButton, "cell 0 3,growx");

        lblSumma = new JLabel("Summa: ");
        oneOrderPanel.add(lblSumma, "cell 1 3,alignx center");
    }

    public double getOrderTotal(Order order) {
        double total = 0;
        
        for (ShoppingItem si : order.getItems()) {
            total += si.getTotal();
        }
        
        return total;
    }

    public void addShoppingItems(Order order) {
        this.order = order;
        lblOrderName.setText(Constants.dateFormat.format(order.getDate()));
        lblSumma.setText(getOrderTotal(order) + Constants.currencySuffix);
        oneOrderItem.removeAll();
        
        for (ShoppingItem si : order.getItems()) {
            ItemOrderDetailed fi = new ItemOrderDetailed(si, model);
            oneOrderItem.add(fi, "wrap,growx");
        }
        
        updateColors(oneOrderItem);
        allOrdersItem.revalidate();
        repaint();
        CardLayout cl = (CardLayout) (this.getLayout());
        cl.show(this, "oneOrderPanel");
    }

    private void updateColors(JPanel panel) {
        for (int i = 0; i < panel.getComponentCount(); i++) {
            if (i % 2 == 0) {
                panel.getComponents()[i].setBackground(Constants.ALT_COLOR);
            } else {
                panel.getComponents()[i].setBackground(null);
            }
        }
    }

    private void updateHistoryView() {
        allOrdersItem.removeAll();

        for (Order order : model.getOrders()) {
            OrderItem oi = new OrderItem(this, order, model);
            allOrdersItem.add(oi, "wrap,growx");
        }

        updateColors(allOrdersItem);
        allOrdersItem.revalidate();
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == backButton) {
            CardLayout cl = (CardLayout) (this.getLayout());
            cl.show(this, "allOrdersPanel");
        }

        if (event.getSource() == addAllToCartButton) {
            for (ShoppingItem si : order.getItems()) {
                model.cartAddItem(si.getProduct(), si.getAmount());
            }
        }

        if (event.getSource() == btnSparaLista) {
            model.listSave(textField.getText(), order.getItems());
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if (event.getPropertyName() == "order_placed") {
            updateHistoryView();
        }
    }
}
