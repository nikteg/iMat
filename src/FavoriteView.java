import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import net.miginfocom.swing.MigLayout;
import se.chalmers.ait.dat215.project.Product;
import se.chalmers.ait.dat215.project.ShoppingItem;


public class FavoriteView extends JPanel implements ActionListener, PropertyChangeListener {

	private IMatModel model;
	private JButton clearFavoritesButton;
	private JPanel favoritePanel;
	private JScrollPane scrollPane;
	private List<Product> favItemList = new ArrayList<Product>();
	
	public FavoriteView() {
		super();
		initialize();
	}
	
	public FavoriteView(IMatModel model) {
		this.model = model;
		this.model.addPropertyChangeListener(this);
		initialize();
		updateFavoriteView();
	}
	
	private void initialize() {
		setLayout(new MigLayout("insets 2px", "[grow]", "[grow][20][24]"));
		
		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		add(scrollPane, "cell 0 0,grow");
		
		favoritePanel = new JPanel();
		scrollPane.setViewportView(favoritePanel);
		favoritePanel.setLayout(new MigLayout("insets 0px", "[grow]", "[36px]"));
		
		clearFavoritesButton = new JButton("Rensa favoriter");
		clearFavoritesButton.addActionListener(this);
		add(clearFavoritesButton, "cell 0 2,alignx center");
	}



	private void updateColors() {
		for (int i = 0; i < favoritePanel.getComponentCount(); i++) {
			if (i % 2 == 0) {
				favoritePanel.getComponents()[i].setBackground(Constants.ALT_COLOR);
			} else {
				favoritePanel.getComponents()[i].setBackground(null);
			}
		}
	}
	
	public void updateFavoriteView() {
		
		favoritePanel.removeAll();
		favItemList.clear();
		for (int i = 0; i < model.getFavorites().size(); i++) {
			FavoriteItem fi = new FavoriteItem(model.getFavorites().get(i),model);
			favoritePanel.add(fi, "wrap,growx");
			favItemList.add(fi.getProduct());
		}
		
		updateColors();
		favoritePanel.revalidate();
		repaint();
	}
	
	public void addFavorite(Product product) {
		System.out.println(favItemList.size());
		if (!favItemList.contains(product)) {
			FavoriteItem fi = new FavoriteItem(product,model);
			favoritePanel.add(fi, "wrap,growx");
			favItemList.add(product);
			updateColors();
			favoritePanel.revalidate();
			repaint();
		}
		

		
		
	}

	
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName() == "favorite_clear") {
			updateFavoriteView();
		}
	
	}
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == clearFavoritesButton) {
			if (JOptionPane.showConfirmDialog(this, "Är du säker", "Radera alla favoriter", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == 0) {
				model.favoriteClear();
				repaint();
			}

		}
		
	}


}
