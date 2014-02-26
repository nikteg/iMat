import java.awt.Dimension;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;

import se.chalmers.ait.dat215.project.CreditCard;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Order;
import se.chalmers.ait.dat215.project.Product;
import se.chalmers.ait.dat215.project.ProductCategory;
import se.chalmers.ait.dat215.project.ShoppingCart;
import se.chalmers.ait.dat215.project.User;

/**
 * A model class to handle communication with the backend IMatDataHandler and
 * present data to the view.
 * 
 * @author Oskar Jönefors
 * 
 */

public class IMatModel {
	private static IMatModel instance = null;
	
	private IMatDataHandler backend = IMatDataHandler.getInstance();
	private List<Product> categoryList = new ArrayList<Product>();
	
	private Account account = new Account(backend.getUser(), backend.getCustomer());
	
	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	
	private final static Logger LOGGER = Logger.getLogger(IMatModel.class.getName());

	private IMatModel() {
		
	}
	
	/**
	 * Get a shared instance of the IMatModel
	 * 
	 * @return An instance of IMatModel.
	 */
	public static synchronized IMatModel getInstance() {
		if (instance == null) {
			return new IMatModel();
		}
		
		return instance;
	}
	
	public void addPropertyChangeListener(PropertyChangeListener pcl) {
		pcs.addPropertyChangeListener(pcl);
	}

	public void removePropertyChangeListener(PropertyChangeListener pcl) {
		pcs.removePropertyChangeListener(pcl);
	}
	
	public Map<String, String> signIn(String userName, String password) {
		HashMap<String, String> errors = new HashMap<String, String>();
		
		if (!userName.equals(account.getUserName()) || !password.equals(account.getPassword())) {
			System.out.println("Vad du matar in: ");
			System.out.println(userName);
			System.out.println(password);
			System.out.println("Vad backend säger: ");
			System.out.println(account.getUserName());
			System.out.println(account.getPassword());
			
			errors.put("signin", "Felaktiga inloggningsuppgifter");
		}
		
		LOGGER.log(Level.INFO, errors.get("signin"));
		
		if (errors.isEmpty()) {
			pcs.firePropertyChange("signedin", null, account);
			
			LOGGER.log(Level.INFO, "User (" + userName + ") has signed in");
		}
		
		return errors;
	}
	
	public void setCredentials(String userName, String password, String email, String firstName, String lastName, String address, String mobilePhoneNumber, String phoneNumber, String postAddress, String postCode) {
		account.setUserName(userName);
		account.setPassword(password);
		account.setEmail(email);
		account.setFirstName(firstName);
		account.setLastName(lastName);
		account.setAddress(postAddress);
		account.setMobilePhoneNumber(mobilePhoneNumber);
		account.setPhoneNumber(phoneNumber);
		account.setPostAddress(postAddress);
		account.setPostCode(postCode);
	}
	
	public Map<String, String> signUp(String userName, String password, String email) {
		HashMap<String, String> errors = new HashMap<String, String>();
		
		if (userName.length() < 1) errors.put("username", "För kort användarnamn");
		
		if (password.length() < 1) errors.put("password", "För kort lösenord");
		
		Pattern emailPattern = Pattern.compile("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$");
		Matcher m = emailPattern.matcher(email);
		
		if (!m.matches()) errors.put("email", "Felaktig e-postadress");
		
		if (errors.isEmpty()) {
			account.setUserName(userName);
			account.setPassword(password);
			account.setEmail(email);
			
			account.setAnonymous(false);
			
			pcs.firePropertyChange("signedup", null, account);
			
			LOGGER.log(Level.INFO, "User (" + userName + ") has signed up");
			
			signIn(account.getUserName(), account.getPassword());
		}

		return errors;
	}
	
	public void signOut() {
		backend.reset();
		account.setAnonymous(true);
		
		pcs.firePropertyChange("signedout", null, account);
		
		LOGGER.log(Level.INFO, "User (" + account.getUserName() + ") has signed out");
	}
	
	public Account getAccount() {
		return account;
	}

	/**
	 * Adds gives a product to favorites. Does not affect favorite lists.
	 * 
	 * @param p
	 *            The product to give favorite status.
	 */
	public void addFavorite(Product p) {
		backend.addFavorite(p);
	}

	/**
	 * Returns the single creditcard object holding information about the
	 * customer's creditcard.
	 * 
	 * @return a CreditCard object.
	 */
	public CreditCard getCreditCard() {
		return backend.getCreditCard();
		// TODO Implement multiple card support
	}

	/**
	 * Returns the path to the directory where the backend stores it's data.
	 * 
	 * @return Path in String format.
	 */
	public String getDataDirectory() {
		return backend.imatDirectory();
	}

	/**
	 * 
	 * @return A list of all favorite-marked products. Not those in favorite
	 *         lists.
	 */
	public List<Product> getFavorites() {
		return backend.favorites();
	}

	/**
	 * Returns an ImageIcon with a full-sized image for the product P.
	 * 
	 * @param p
	 * @return an ImageIcon or null if no image exists for the specified
	 *         product.
	 */
	public ImageIcon getImageIcon(Product p) {
		return backend.getImageIcon(p);
	}

	/**
	 * Returns an ImageIcon with the specified Dimension for the product p.
	 * 
	 * @param p
	 *            Product
	 * @param d
	 *            Desired dimension of the requested ImageIcon.
	 * @return an ImageIcon or null if no image exists for the specified
	 *         product.
	 */
	public ImageIcon getImageIcon(Product p, Dimension d) {
		return backend.getImageIcon(p, d);
	}

	/**
	 * Returns an ImageIcon with the specified width and height for the product
	 * P. All images have the ratio 4:3. The given width and height can be
	 * anything, but if they do not have the ratio 4:3 the result might not look
	 * good.
	 * 
	 * @param p
	 *            The product
	 * @param width
	 *            Requested icon width
	 * @param height
	 *            Requested icon height
	 * @return an ImageIcon or null if no image exists for the specified
	 *         product.
	 */
	public ImageIcon getImageIcon(Product p, int width, int height) {
		return backend.getImageIcon(p, width, height);
	}

	/**
	 * Return a list of all previous orders
	 * 
	 * @return A list of previous orders.
	 */
	public List<Order> getOrders() {
		return backend.getOrders();
	}

	/**
	 * Returns the product with the given idNbr.
	 * 
	 * @param idNbr
	 * @return the product with the given id number or null if no such product
	 *         exists.
	 */
	public Product getProduct(int idNbr) {
		return backend.getProduct(idNbr);
	}

	/**
	 * Returns a list with all the store's products.
	 * 
	 * @return a list with all the store's products.
	 */
	public List<Product> getProducts() {
		return backend.getProducts();
	}

	/**
	 * Returns a list of the products in the given category.
	 * 
	 * @param cat
	 *            Product category
	 * @return List of products in the given category.
	 */
	public List<Product> getProducts(Constants.Category cat) {

		switch (cat) {
		case BREAD:
			categoryList.addAll(backend.getProducts(ProductCategory.BREAD));
			break;
		case DAIRIES:
			categoryList.addAll(backend.getProducts(ProductCategory.DAIRIES));
			break;
		case DRINKS:
			categoryList.addAll(backend
					.getProducts(ProductCategory.COLD_DRINKS));
			categoryList
					.addAll(backend.getProducts(ProductCategory.HOT_DRINKS));
			break;
		case FRUIT_BERRIES:
			categoryList.addAll(backend.getProducts(ProductCategory.BERRY));
			categoryList.addAll(backend
					.getProducts(ProductCategory.CITRUS_FRUIT));
			categoryList.addAll(backend
					.getProducts(ProductCategory.EXOTIC_FRUIT));
			categoryList.addAll(backend.getProducts(ProductCategory.FRUIT));
			categoryList.addAll(backend.getProducts(ProductCategory.MELONS));
			break;
		case MEAT_FISH:
			categoryList.addAll(backend.getProducts(ProductCategory.MEAT));
			categoryList.addAll(backend.getProducts(ProductCategory.FISH));
			break;
		case PANTRY:
			categoryList.addAll(backend
					.getProducts(ProductCategory.FLOUR_SUGAR_SALT));
			break;
		case PASTA_POTATO_RICE:
			categoryList.addAll(backend.getProducts(ProductCategory.PASTA));
			categoryList.addAll(backend
					.getProducts(ProductCategory.POTATO_RICE));
			break;
		case ROOT_VEGETABLES:
			categoryList.addAll(backend
					.getProducts(ProductCategory.ROOT_VEGETABLE));
			break;
		case SNACKS:
			categoryList.addAll(backend
					.getProducts(ProductCategory.NUTS_AND_SEEDS));
			categoryList.addAll(backend.getProducts(ProductCategory.SWEET));
			break;
		case SPICES:
			categoryList.addAll(backend.getProducts(ProductCategory.HERB));
			break;
		case VEGETABLES:
			categoryList.addAll(backend.getProducts(ProductCategory.CABBAGE));
			categoryList.addAll(backend.getProducts(ProductCategory.POD));
			categoryList.addAll(backend
					.getProducts(ProductCategory.VEGETABLE_FRUIT));
			break;
		default:
			break;
		}
		return categoryList;
	}

	public Constants.Category getCategory(Product p) {

		switch (p.getCategory()) {
		case BREAD:
			return Constants.Category.BREAD;
		case DAIRIES:
			return Constants.Category.DAIRIES;
		case COLD_DRINKS:
		case HOT_DRINKS:
			return Constants.Category.DRINKS;
		case BERRY:
		case CITRUS_FRUIT:
		case EXOTIC_FRUIT:
		case FRUIT:
		case MELONS:
			return Constants.Category.FRUIT_BERRIES;
		case MEAT:
		case FISH:
			return Constants.Category.MEAT_FISH;
		case FLOUR_SUGAR_SALT:
			return Constants.Category.PANTRY;
		case PASTA:
		case POTATO_RICE:
			return Constants.Category.PASTA_POTATO_RICE;
		case ROOT_VEGETABLE:
			return Constants.Category.ROOT_VEGETABLES;
		case NUTS_AND_SEEDS:
		case SWEET:
			return Constants.Category.SNACKS;
		case HERB:
			return Constants.Category.SPICES;
		case CABBAGE:
		case POD:
		case VEGETABLE_FRUIT:
			return Constants.Category.VEGETABLES;
		default:
			return null;
		}

	}

	/**
	 * 
	 * @param s
	 *            Search expression
	 * @return List<Product> with search results
	 */
	public List<Product> getSearchResults(String s) {
		return backend.findProducts(s);
	}

	/**
	 * Returns the single ShoppingCart object. Changes to the ShoppingCart are
	 * done using methods in the ShoppingCart class. The backend saves the state
	 * of the shopping cart when it is shutdown and restores it again the next
	 * time it is initialized.
	 * 
	 * @return a ShoppingCart object.
	 */
	public ShoppingCart getShoppingCart() {
		return backend.getShoppingCart();
	}

	/**
	 * Returns the single user object holding information about the (optional)
	 * user.
	 * 
	 * @return a User object.
	 */
	public User getUser() {
		return backend.getUser();
	}

	/**
	 * Returns true the given Product object p has an image associated with.
	 * Should normally be true for all products.
	 * 
	 * @param p
	 *            Product to check
	 */
	public boolean hasImage(Product p) {
		return backend.hasImage(p);
	}

	/**
	 * Returns true if all information about the customer has been given. All is
	 * interpreted as name, adress and at least one phone number.
	 */
	public boolean isCustomerComplete() {
		return backend.isCustomerComplete();
	}

	/**
	 * Returns true if the product p is a favorite, false otherwise.
	 * 
	 * @param p
	 *            - The product to check.
	 */
	public boolean isFavorite(Product p) {
		return backend.isFavorite(p);
	}

	/**
	 * Returns true if this is the first run, false otherwise.
	 */
	public boolean isFirstRun() {
		return backend.isFirstRun();
	}

	/**
	 * Creates an order from the current contents of the shoppingcart. Also
	 * removes the items currently in the ShoppingCart. All orders are
	 * remembered by the IMatDataHandler. This method is shorthand for
	 * placeOrder(true).
	 * 
	 * @return An Order object containing information about the order.
	 */
	public Order placeOrder() {
		return backend.placeOrder();
	}

	/**
	 * Creates an order from the current contents of the shoppingcart. All
	 * orders are remembered by the backend.
	 * 
	 * @param clearShoppingCart
	 *            - indicates whether the shopping cart is cleared or not.
	 * @return An Order object containing information about the order.
	 */
	public Order placeOrder(boolean clearShoppingCart) {
		return backend.placeOrder(clearShoppingCart);
	}

	/**
	 * Removes favorite status from a product. Does not affect favorite lists.
	 * 
	 * @param p
	 *            The product to de-favoritize. I make up words.
	 */
	public void removeFavorite(Product p) {
		backend.removeFavorite(p);
	}

	/**
	 * Resets the backend to it's initial state. Mostly useful during
	 * development.
	 */
	public void reset() {
		backend.reset();
	}
	
	public void shutDown() {
		backend.shutDown();
	}
}
