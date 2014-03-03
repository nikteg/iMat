import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import net.miginfocom.swing.MigLayout;
import se.chalmers.ait.dat215.project.Order;
import se.chalmers.ait.dat215.project.ShoppingItem;

import com.alee.extended.panel.WebAccordion;
import com.alee.extended.panel.WebAccordionStyle;
import com.alee.laf.scroll.WebScrollPane;

import javax.swing.BoxLayout;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import javax.swing.JButton;


public class HistoryView extends JPanel implements ActionListener, PropertyChangeListener {

	private IMatModel model;
	private JPanel allOrdersItem;
	private JScrollPane scrollPaneAllOrders;
	private CheckOutWindow checkoutWindow;
	private JFrame frame;
	private JPanel allOrdersPanel;
	private JPanel oneOrderPanel;
	private JScrollPane scrollPaneOneOrder;
	private JPanel oneOrderItem;
	private JPanel panel_1;
	private JButton backButton;
	
	public HistoryView() {
		super();
		initialize();
	}

	public HistoryView(IMatModel model, JFrame frame) {
		this();
		this.model = model;
		this.frame = frame;
		this.model.addPropertyChangeListener(this);
		updateHistoryView();
	}
	
	private void initialize() {
		setLayout(new CardLayout(0, 0));
		
		allOrdersPanel = new JPanel();
		add(allOrdersPanel, "allOrdersPanel");
		allOrdersPanel.setLayout(new MigLayout("insets 0px", "[grow]", "[grow]"));
		
		scrollPaneAllOrders = new JScrollPane();
		allOrdersPanel.add(scrollPaneAllOrders, "cell 0 0,grow");
		scrollPaneAllOrders.setFocusable(false);
		scrollPaneAllOrders.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPaneAllOrders.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		allOrdersItem = new JPanel();
		scrollPaneAllOrders.setViewportView(allOrdersItem);
		allOrdersItem.setLayout(new MigLayout("insets 0px", "[grow]", "[48]"));
		
		
		
		oneOrderPanel = new JPanel();
		add(oneOrderPanel, "oneOrderPanel");
		oneOrderPanel.setLayout(new MigLayout("insets 0px", "[2px,grow]", "[][2px,grow]"));
		
		backButton = new JButton("<-- Bakåt");
		backButton.addActionListener(this);
		oneOrderPanel.add(backButton, "cell 0 0");
		
		panel_1 = new JPanel();
		oneOrderPanel.add(panel_1, "cell 0 1,grow");
		panel_1.setLayout(new MigLayout("insets 0px", "[2px,grow]", "[2px,grow]"));
		
		scrollPaneOneOrder = new JScrollPane();
		panel_1.add(scrollPaneOneOrder, "cell 0 0,grow");
		scrollPaneOneOrder.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPaneOneOrder.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		oneOrderItem = new JPanel();
		scrollPaneOneOrder.setViewportView(oneOrderItem);
		oneOrderItem.setLayout(new MigLayout("insets 0px", "[grow]", "[36px]"));
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {

		if (event.getPropertyName() == "order_moreinfo") {
			addShoppingItems((Order)event.getNewValue());
		}
	}
	
	private void addShoppingItems(Order order) {
		for (ShoppingItem si : order.getItems()) {
			FavoriteItem fi = new FavoriteItem(si.getProduct(), model);
			System.out.println("hej");
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
		
		
		
		for (Order order : model.getOrders()) {
			OrderItem oi = new OrderItem(order, model);
			allOrdersItem.add(oi, "wrap,growx");
		}
		
		
		updateColors(allOrdersItem);
		allOrdersItem.revalidate();
		repaint();
		


	}
	
	

	private WebScrollPane createScrollPane(Order order) {
				
		
		JPanel panel = new JPanel();
		panel.setLayout(new MigLayout("insets 4px", "[grow]", "[60px]"));
		panel.add(new OrderItem(order,model), "growx");
		
		WebScrollPane scrollPane = new WebScrollPane ( panel, false );
        scrollPane.setPreferredSize ( new Dimension ( 200, 200 ) );

        return scrollPane;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == backButton) {
			CardLayout cl = (CardLayout) (this.getLayout());
			cl.show(this, "allOrdersPanel");
		}
		
	}
}
