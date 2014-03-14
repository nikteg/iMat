package se.chalmers.dat215.grupp14;

import java.awt.CardLayout;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import net.miginfocom.swing.MigLayout;
import se.chalmers.ait.dat215.project.ShoppingItem;
import se.chalmers.dat215.grupp14.backend.Constants;
import se.chalmers.dat215.grupp14.backend.IMatModel;

import javax.swing.ImageIcon;

// TODO LÄGG TILL EN SÅNDÄR RUBRIKJÄVEL
@SuppressWarnings("serial")
public class ListView extends JPanel implements ActionListener, PropertyChangeListener {
    private IMatModel model;
    private JPanel allListsItem;
    private JScrollPane scrollPaneAllOrders;
    private JPanel allListPanel;
    private JPanel oneListPanel;
    private JScrollPane scrollPaneOneOrder;
    private JPanel oneListItem;
    private JPanel panel_1;
    private JButton backButton;
    private JButton addAllToCartButton;
    private JLabel lblSumma;
    private List<ShoppingItem> shoppingItems;

    public ListView() {
        super();
        initializeGUI();
    }

    public ListView(IMatModel model) {
        this();
        this.model = model;
        this.model.addPropertyChangeListener(this);
        updateListView();
    }

    private void initializeGUI() {
        setLayout(new CardLayout(0, 0));

        allListPanel = new JPanel();
        add(allListPanel, "allOrdersPanel");
        allListPanel.setLayout(new MigLayout("insets 0px,gapy 0px", "[grow]", "[grow]"));

        scrollPaneAllOrders = new JScrollPane();
        allListPanel.add(scrollPaneAllOrders, "cell 0 0,grow");
        scrollPaneAllOrders.setFocusable(false);
        scrollPaneAllOrders.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPaneAllOrders.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        allListsItem = new JPanel();
        scrollPaneAllOrders.setViewportView(allListsItem);
        allListsItem.setLayout(new MigLayout("insets 0px", "[grow]", "[36px]"));

        oneListPanel = new JPanel();
        add(oneListPanel, "oneOrderPanel");
        oneListPanel.setLayout(new MigLayout("insets 0px", "[50%,grow,left][50%,grow]", "[][2px,grow][]"));

        backButton = new JButton("Tillbaka");
        backButton.setIcon(new ImageIcon(ListView.class.getResource("resources/images/icons/back.png")));
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(this);
        oneListPanel.add(backButton, "cell 0 0,alignx left,aligny center");

        panel_1 = new JPanel();
        oneListPanel.add(panel_1, "cell 0 1 2 1,grow");
        panel_1.setLayout(new MigLayout("insets 0px,gapy 0px", "[2px,grow]", "[2px,grow]"));

        scrollPaneOneOrder = new JScrollPane();
        panel_1.add(scrollPaneOneOrder, "cell 0 0,grow");
        scrollPaneOneOrder.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPaneOneOrder.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        oneListItem = new JPanel();
        scrollPaneOneOrder.setViewportView(oneListItem);
        oneListItem.setLayout(new MigLayout("insets 0px,gapy 0px", "[grow]", "[36px]"));

        addAllToCartButton = new JButton("Lägg till alla varukorg");
        addAllToCartButton.addActionListener(this);
        addAllToCartButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        oneListPanel.add(addAllToCartButton, "cell 0 2,growx");

        lblSumma = new JLabel("Summa: ");
        oneListPanel.add(lblSumma, "cell 1 2,alignx center");
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {

        if (event.getPropertyName() == "list_saved" || event.getPropertyName() == "list_removed"
                || event.getPropertyName() == "account_signedin") {
            updateListView();
        }
    }

    public double getOrderTotal(List<ShoppingItem> shoppingItems) {
        double total = 0;
        for (ShoppingItem si : shoppingItems) {
            total += si.getTotal();
        }
        return total;
    }

    public void addShoppingItems(List<ShoppingItem> shoppingItems) {

        this.shoppingItems = shoppingItems;
        lblSumma.setText(getOrderTotal(shoppingItems) + Constants.currencySuffix);
        oneListItem.removeAll();
        for (ShoppingItem si : shoppingItems) {
            ItemListDetailed fi = new ItemListDetailed(si, model);
            oneListItem.add(fi, "wrap,growx");
        }
        updateColors(oneListItem);
        allListsItem.revalidate();
        repaint();
        CardLayout cl = (CardLayout) (this.getLayout());
        cl.show(this, "oneOrderPanel");
    }

    private void updateColors(JPanel panel) {
        for (int i = 0; i < panel.getComponentCount(); i++) {
            panel.getComponents()[i].setBackground((i % 2 == 0) ? Constants.ALT_COLOR : null);
        }

        repaint();
    }

    private void updateListView() {
        allListsItem.removeAll();
        Map<String, List<ShoppingItem>> listMap = model.getListHandler().getLists(model.getAccountHandler().getCurrentAccount());

        if (listMap != null) {
            for (String s : listMap.keySet()) {

                ListItem li = new ListItem(this, s, listMap.get(s), model);
                allListsItem.add(li, "wrap,growx");
            }

            updateColors(allListsItem);
            allListsItem.revalidate();
            repaint();
        }

    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == backButton) {
            CardLayout cl = (CardLayout) (this.getLayout());
            cl.show(this, "allOrdersPanel");
        }

        if (event.getSource() == addAllToCartButton) {

            for (ShoppingItem si : shoppingItems) {
                model.cartAddItem(si.getProduct(), si.getAmount());
            }
        }

    }
}
