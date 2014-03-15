package se.chalmers.dat215.grupp14;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import net.miginfocom.swing.MigLayout;
import se.chalmers.ait.dat215.project.Product;
import se.chalmers.dat215.grupp14.backend.Constants;
import se.chalmers.dat215.grupp14.backend.IMatModel;

/**
 * Favorite view
 * @author Niklas Tegnander, Mikael Lönn and Oskar Jönefors
 */
@SuppressWarnings("serial")
public class FavoriteView extends JPanel implements ActionListener, PropertyChangeListener {
    private IMatModel model;
    private JButton btnClearFavorites;
    private JPanel pnlFavorite;
    private JScrollPane scrollPane;
    private List<Product> favItemList = new ArrayList<Product>();

    /**
     * Constructor
     */
    public FavoriteView() {
        super();
        initializeGUI();
    }

    /**
     * Constructor with given model
     * @param model
     */
    public FavoriteView(IMatModel model) {
        this.model = model;
        this.model.addPropertyChangeListener(this);
        initializeGUI();
        updateFavoriteView();
    }

    /**
     * Initialize GUI
     */
    private void initializeGUI() {
        setLayout(new MigLayout("insets 2px", "[grow]", "[grow][20][24]"));

        scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, "cell 0 0,grow");

        pnlFavorite = new JPanel();
        scrollPane.setViewportView(pnlFavorite);
        pnlFavorite.setLayout(new MigLayout("insets 0px,gapy 0px", "[grow]", "[36px]"));

        btnClearFavorites = new JButton("Rensa favoriter");
        btnClearFavorites.addActionListener(this);
        btnClearFavorites.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(btnClearFavorites, "cell 0 2,alignx center");
    }

    /**
     * Row striping
     */
    private void updateColors() {
        for (int i = 0; i < pnlFavorite.getComponentCount(); i++) {
            pnlFavorite.getComponents()[i].setBackground((i % 2 == 0) ? Constants.ALT_COLOR : null);
        }

        repaint();
    }

    /**
     * Update favorite view
     */
    public void updateFavoriteView() {

        pnlFavorite.removeAll();
        favItemList.clear();
        for (int i = 0; i < model.getFavorites().size(); i++) {
            FavoriteItem fi = new FavoriteItem(model.getFavorites().get(i), model);
            pnlFavorite.add(fi, "wrap,growx");
            favItemList.add(fi.getProduct());
        }

        updateColors();
        pnlFavorite.revalidate();
    }

    /**
     * Add favorite
     * @param product
     */
    public void addFavorite(Product product) {
        if (!favItemList.contains(product)) {
            FavoriteItem fi = new FavoriteItem(product, model);
            pnlFavorite.add(fi, "wrap,growx");
            favItemList.add(product);
            updateColors();
            pnlFavorite.revalidate();
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
        if (event.getSource() == btnClearFavorites) {
            if (JOptionPane.showConfirmDialog(this, "Är du säker på att du vill radera alla favoriter?",
                    "Radera alla favoriter", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == 0) {
                model.favoriteClear();
                repaint();
            }
        }
    }
}
