import java.awt.Dimension;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
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
import se.chalmers.ait.dat215.project.ShoppingItem;
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
	
	private ListHandler listHandler = new ListHandler(this);
	
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
	
	public boolean accountSignIn(String userName, String password) {
		List<String> errors = new ArrayList<String>();
		
		if (!userName.equalsIgnoreCase(account.getUserName())) {
			errors.add("username_wrong");
		}
		
		if (!password.equals(account.getPassword())) {
			errors.add("password_wrong");
		}
		
		pcs.firePropertyChange("account_signin", null, errors);
		LOGGER.log(Level.INFO, "account_signin");
		
		if (errors.isEmpty()) {
			account.setAnonymous(false);
			pcs.firePropertyChange("account_signedin", null, account);
			LOGGER.log(Level.INFO, "account_signedin");
			
			return true;
		}
		
		return false;
	}
	
	public void setCredentials(String userName, String password, String passwordRepeat, String email, String firstName, String lastName, String address, String mobilePhoneNumber, String phoneNumber, String postAddress, String postCode) {
		List<String> errors = new ArrayList<String>();
		
		Pattern emailPattern = Pattern.compile("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$");
		Matcher m = emailPattern.matcher(email);
		
		if (!m.matches()) errors.add("email_invalid");
		
		if (!password.equals(passwordRepeat)) errors.add("passoword_invalid");
		
		if (firstName.length() < 1) errors.add("firstname_invalid");
		
		if (lastName.length() < 1) errors.add("lastname_invalid");
		
		if (address.length() < 1) errors.add("address_invalid");
		
		Pattern numberPattern = Pattern.compile("^[0-9\\+ ]+$");
		Matcher mpm = numberPattern.matcher(mobilePhoneNumber);
		
		if (!mpm.matches() && mobilePhoneNumber.length() != 0) errors.add("mobilephone_invalid");
		
		Matcher pm = numberPattern.matcher(phoneNumber);
		
		if (!pm.matches() && phoneNumber.length() != 0) errors.add("phone_invalid");
		
		if (postAddress.length() < 1) errors.add("postaddress_invalid");
		
		Pattern postCodePattern = Pattern.compile("^[0-9 ]{5,6}$");
		Matcher pcm = postCodePattern.matcher(postCode);
		
		if (!pcm.matches()) errors.add("postcode_invalid");
		
		pcs.firePropertyChange("account_save", null, errors);
		
		if (errors.isEmpty()) {
			if (!(password.length() == 0)) {
				account.setPassword(password);
			}
			account.setEmail(email);
			account.setFirstName(firstName);
			account.setLastName(lastName);
			account.setAddress(postAddress);
			account.setMobilePhoneNumber(mobilePhoneNumber);
			account.setPhoneNumber(phoneNumber);
			account.setPostAddress(postAddress);
			account.setPostCode(postCode);
			
			pcs.firePropertyChange("account_saved", null, account);
			
		}
		


	}
	
	
	public boolean accountSignUp(String userName, String password, String email) {
		List<String> errors = new ArrayList<String>();
		
		if (userName.length() < 1) errors.add("username_too_short");
		
		if (password.length() < 1) errors.add("password_too_short");
		
		Pattern emailPattern = Pattern.compile("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$");
		Matcher m = emailPattern.matcher(email);
		
		if (!m.matches()) errors.add("email_invalid");
		
		pcs.firePropertyChange("account_signup", null, errors);
		
		if (errors.isEmpty()) {
			account.setUserName(userName);
			account.setPassword(password);
			account.setEmail(email);
			
			account.setAnonymous(false);
			
			pcs.firePropertyChange("account_signedup", null, account);
			LOGGER.log(Level.INFO, "account_signedup");
			
			return true;
		}

		return false;
	}
	
	public void accountSignOut() {
		account.setAnonymous(true);
		
		pcs.firePropertyChange("account_signout", null, account);
		LOGGER.log(Level.INFO, "account_signout");
	}
	
	public boolean accountIsAnonymous() {
		return account.isAnonymous();
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
	public void favoriteAdd(Product product) {
		backend.addFavorite(product);
		
		pcs.firePropertyChange("favorite_add", null, product);
		LOGGER.log(Level.INFO, "favorite_add");
	}
	
	public void favoriteRemove(Product product) {
		backend.removeFavorite(product);
		
		pcs.firePropertyChange("favorite_remove", null, product);
		LOGGER.log(Level.INFO, "favorite_remove");
	}
	
	public void favoriteClear() {
		backend.favorites().clear();
		
		pcs.firePropertyChange("favorite_clear", null, backend.favorites());
		LOGGER.log(Level.INFO, "favorite_clear");
	}
	
	public void cartAddItem(Product product) {
		cartAddItem(product, 1.0);
	}
	
	public void cartAddItem(Product product, double amount) {
		for (ShoppingItem si : backend.getShoppingCart().getItems()) {
			if (si.getProduct().equals(product)) {
				cartUpdateItem(product, si.getAmount() + amount);
				
				return;
			}
		}
		ShoppingItem item = new ShoppingItem(product, amount);
		
		backend.getShoppingCart().addItem(item);
		pcs.firePropertyChange("cart_additem", null, item);
		LOGGER.log(Level.INFO, "cart_additem");
	}
	
	public void cartRemoveItem(Product product) {
		for (ShoppingItem item : backend.getShoppingCart().getItems()) {
			if (item.getProduct().equals(product)) {
				backend.getShoppingCart().removeItem(item);

				pcs.firePropertyChange("cart_removeitem", null, item);
				LOGGER.log(Level.INFO, "cart_removeitem");
				
				return;
			}
		}
	}
	
	public void cartUpdateItem(Product product, double amount) {
		for (ShoppingItem item : backend.getShoppingCart().getItems()) {
			if (item.getProduct().equals(product)) {
				item.setAmount(amount);
				
				pcs.firePropertyChange("cart_updateitem", null, item);
				LOGGER.log(Level.INFO, "cart_updateitem");
				
				return;
			}
		}
	}
	
	public void cartClear() {
		backend.getShoppingCart().clear();
		
		pcs.firePropertyChange("cart_clear", null, backend.getShoppingCart());
		LOGGER.log(Level.INFO, "cart_clear");
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
	public void getSearchResults(String query) {
		
		List<Product> results = backend.findProducts(query);
		
		pcs.firePropertyChange("search", null, results);
		LOGGER.log(Level.INFO, "search");
	}
	
	/**
	 * Get a string of the working directory.
	 * @return String of the directory where the data files are stored.
	 */
	public String getWorkingDirectory(){
		return backend.imatDirectory();
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
	public Order orderPlace (String firstName,
								String lastName, 
								String address, 
								String mobilePhoneNumber, 
								String phoneNumber, 
								String postAddress, 
								String postCode) {
		
		return orderPlace(true, firstName, lastName, address, mobilePhoneNumber, phoneNumber, postAddress, postCode);
	}

	/**
	 * Creates an order from the current contents of the shoppingcart. All
	 * orders are remembered by the backend.
	 * 
	 * @param clearShoppingCart
	 *            - indicates whether the shopping cart is cleared or not.
	 * @return An Order object containing information about the order.
	 */
	public Order orderPlace(boolean clearShoppingCart,String firstName, String lastName, String address, String mobilePhoneNumber, String phoneNumber, String postAddress, String postCode) {
		List<String> errors = new ArrayList<String>();
		
		
		if (firstName.length() < 1) errors.add("firstname_invalid");
		
		if (lastName.length() < 1) errors.add("lastname_invalid");
		
		if (address.length() < 1) errors.add("address_invalid");
		
		Pattern numberPattern = Pattern.compile("^[0-9\\+ ]+$");
		Matcher mpm = numberPattern.matcher(mobilePhoneNumber);
		
		if (!mpm.matches() && mobilePhoneNumber.length() != 0) errors.add("mobilephone_invalid");
		
		Matcher pm = numberPattern.matcher(phoneNumber);
		
		if (!pm.matches() && phoneNumber.length() != 0) errors.add("phone_invalid");
		
		if (postAddress.length() < 1) errors.add("postaddress_invalid");
		
		Pattern postCodePattern = Pattern.compile("^[0-9 ]{5,6}$");
		Matcher pcm = postCodePattern.matcher(postCode);
		
		if (!pcm.matches()) errors.add("postcode_invalid");
			
		pcs.firePropertyChange("order_place", null, errors);
		LOGGER.log(Level.INFO, "order_place");
		
		if (errors.isEmpty()) {
			Order order = backend.placeOrder(clearShoppingCart);
			pcs.firePropertyChange("order_placed", null, order);
			LOGGER.log(Level.INFO, "order_placed");
			return order;
		}

		
		return null;
		

	}
	
	
	public void orderRemove(Order order) {
		backend.getOrders().remove(order);
		pcs.firePropertyChange("order_remove", order, null);
		LOGGER.log(Level.INFO, "order_remove");
	}
	
	public void listSave(String listName, List<ShoppingItem> shoppingItems) {
		listHandler.saveFavoriteList(shoppingItems, listName, account.getUserName());
		pcs.firePropertyChange("list_saved", null, null);
		LOGGER.log(Level.INFO, "list_saved");
	}
	
	public ListHandler getListHandler() {
		return listHandler;
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
	 * Set model to show all products.
	 */
	public void showAllProducts(){
		List<Product> results = backend.getProducts();
		
		pcs.firePropertyChange("search", null, results);
		LOGGER.log(Level.INFO, "search");
	}

	/**
	 * Resets the backend to it's initial state. Mostly useful during
	 * development.
	 */
	public void reset() {
		backend.reset();
	}
	
	public void shutDown() {
		listHandler.saveState();
		backend.shutDown();
	}











}
