import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import se.chalmers.ait.dat215.project.CreditCard;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Order;
import se.chalmers.ait.dat215.project.Product;
import se.chalmers.ait.dat215.project.ProductCategory;
import se.chalmers.ait.dat215.project.ShoppingCart;
import se.chalmers.ait.dat215.project.User;
/**
 * A model class to handle communication with the backend IMatDataHandler
 * and present data to the view.
 * 
 * @author Oskar Jönefors
 *
 */

public class IMatModel {
	
	public enum Category {BREAD, SNACKS, DRINKS, DAIRIES, MEAT_FISH, VEGETABLES,
		FRUIT_BERRIES, PASTA_POTATO_RICE, SPICES, ROOT_VEGETABLES, PANTRY};
	
	private static IMatModel instance = null;
	private IMatDataHandler backend;
	private List<FavoriteList> favoriteLists;
	private List<CreditCard> creditCardList;//TODO Make use of this list.
	private List<Product> categoryList;
	
	private IMatModel() {
		backend = IMatDataHandler.getInstance();
		creditCardList = new ArrayList<CreditCard>();
		favoriteLists = new ArrayList<FavoriteList>();
		categoryList = new ArrayList<Product>();
	}
	
	/**
	 * Adds gives a product to favorites. Does not affect favorite lists.
	 * @param p The product to give favorite status.
	 */
	public void addFavorite(Product p){
		backend.addFavorite(p);
	}

	/**
	 * Create a new favorite list with the given products and the given name.
	 * 
	 * @param products The products to put in the list.
	 * @param name The name of the new list.
	 * @throws IllegalArgumentException if a favorite list with the given name already exists.
	 */
	public void createFavoriteList(List<Product> products, String name) throws IllegalArgumentException {
		for(int i = 0; i < favoriteLists.size(); i++){
			if(favoriteLists.get(i).getName().equals(name)){
				throw new IllegalArgumentException("Favorite list with title " + name + " already exists.");
			}
		}
		favoriteLists.add(new FavoriteList(products, name));
	}

	/**
	 * Get a shared instance of the IMatModel
	 * @return An instance of IMatModel.
	 */
	public static synchronized IMatModel getInstance(){
		if(instance == null){
			return new IMatModel();
		}
		return instance;
	}
	
	/**
	 * Returns the single creditcard object holding information about the customer's creditcard.
	 * @return a CreditCard object.
	 */
	public CreditCard getCreditCard(){
		return backend.getCreditCard();
		//TODO Implement multiple card support
	}

	/**
	 * Returns the path to the directory where the backend stores it's data.
	 * @return Path in String format.
	 */
	public String getDataDirectory(){
		return backend.imatDirectory();
	}

	/**
	 * Returns an ArrayList of all the favorite lists.
	 * @return A List<FavoriteList> with all favorite lists.
	 */
	public List<FavoriteList> getFavoriteLists(){
		return favoriteLists;
	}
	
	/**
	 * 
	 * @return A list of all favorite-marked products. Not those in favorite lists.
	 */
	public List<Product> getFavorites(){
		return backend.favorites();
	}

	/**
	 * Returns an ImageIcon with a full-sized image for the product P.
	 * @param p
	 * @return an ImageIcon or null if no image exists for the specified product.
	 */
	public ImageIcon getImageIcon(Product p){
		return backend.getImageIcon(p);
	}

	/**
	 *  Returns an ImageIcon with the specified Dimension for the product p.
	 * @param p Product
	 * @param d Desired dimension of the requested ImageIcon.
	 * @return an ImageIcon or null if no image exists for the specified product.
	 */
	public ImageIcon getImageIcon(Product p, Dimension d){
		return backend.getImageIcon(p, d);
	}

	/**
	 * Returns an ImageIcon with the specified width and height for the product P.
	 * All images have the ratio 4:3. The given width and height can be anything,
	 * but if they do not have the ratio 4:3 the result might not look good.
	 * @param p The product
	 * @param width Requested icon width
	 * @param height Requested icon height
	 * @return an ImageIcon or null if no image exists for the specified product.
	 */
	public ImageIcon getImageIcon(Product p, int width, int height){
		return backend.getImageIcon(p, width, height);
	}

	/**
	 * Return a list of all previous orders
	 * @return A list of previous orders.
	 */
	public List<Order> getOrders(){
		return backend.getOrders();
	}

	/**
	 * Returns the product with the given idNbr.
	 * @param idNbr
	 * @return the product with the given id number or null if no such product exists.
	 */
	public Product getProduct(int idNbr){
		return backend.getProduct(idNbr);
	}

	/**
	 * Returns a list with all the store's products.
	 * @return a list with all the store's products.
	 */
	public List<Product> getProducts(){
		return backend.getProducts();
	}

	/**
	 * Returns a list of the products in the given category.
	 * @param cat Product category
	 * @return List of products in the given category.
	 */
	public List<Product> getProducts(Category cat){
		
		
		switch (cat) {
			case BREAD:			categoryList.addAll(backend.getProducts(ProductCategory.BREAD));
								break;
			case DAIRIES: 		categoryList.addAll(backend.getProducts(ProductCategory.DAIRIES));
								break;
			case DRINKS: 		categoryList.addAll(backend.getProducts(ProductCategory.COLD_DRINKS));
								categoryList.addAll(backend.getProducts(ProductCategory.HOT_DRINKS));
								break;
			case FRUIT_BERRIES: categoryList.addAll(backend.getProducts(ProductCategory.BERRY));
								categoryList.addAll(backend.getProducts(ProductCategory.CITRUS_FRUIT));
								categoryList.addAll(backend.getProducts(ProductCategory.EXOTIC_FRUIT));
								categoryList.addAll(backend.getProducts(ProductCategory.FRUIT));
								categoryList.addAll(backend.getProducts(ProductCategory.MELONS));
								break;
			case MEAT_FISH:		categoryList.addAll(backend.getProducts(ProductCategory.MEAT));
								categoryList.addAll(backend.getProducts(ProductCategory.FISH));
								break;
			case PANTRY:		categoryList.addAll(backend.getProducts(ProductCategory.FLOUR_SUGAR_SALT));
								break;
			case PASTA_POTATO_RICE: categoryList.addAll(backend.getProducts(ProductCategory.PASTA));
									categoryList.addAll(backend.getProducts(ProductCategory.POTATO_RICE));
									break;
			case ROOT_VEGETABLES:	categoryList.addAll(backend.getProducts(ProductCategory.ROOT_VEGETABLE));
									break;
			case SNACKS:		categoryList.addAll(backend.getProducts(ProductCategory.NUTS_AND_SEEDS));
								categoryList.addAll(backend.getProducts(ProductCategory.SWEET));
								break;
			case SPICES:		categoryList.addAll(backend.getProducts(ProductCategory.HERB));
								break;
			case VEGETABLES:	categoryList.addAll(backend.getProducts(ProductCategory.CABBAGE));
								categoryList.addAll(backend.getProducts(ProductCategory.POD));
								categoryList.addAll(backend.getProducts(ProductCategory.VEGETABLE_FRUIT));
								break;
			default:	break;
		}
		return categoryList;
	}
	
	public Constants.Category getCategory(Product p){
		
		switch (p.getCategory()){
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
	 * @param s Search expression
	 * @return List<Product> with search results
	 */
	public List<Product> getSearchResults(String s){
		return backend.findProducts(s);
	}

	/**
	 * Returns the single ShoppingCart object. Changes to the ShoppingCart are done using methods in the ShoppingCart class.
	 * The backend saves the state of the shopping cart when it is shutdown and restores it again the next time it is initialized.
	 * @return a ShoppingCart object.
	 */
	public ShoppingCart getShoppingCart(){
		return backend.getShoppingCart();
	}

	/**
	 * Returns the single user object holding information about the (optional) user.
	 * @return a User object.
	 */
	public User getUser(){
		return backend.getUser();
	}

	/**
	 * Returns true the given Product object p has an image associated with. Should normally be true for all products.
	 * @param p Product to check
	 */
	public boolean hasImage(Product p){
		return backend.hasImage(p);
	}

	/**
	 * Returns true if all information about the customer has been given. All is interpreted as name, adress and at least one phone number.
	 */
	public boolean isCustomerComplete(){
		return backend.isCustomerComplete();
	}
	
	/**
	 * Returns true if the product p is a favorite, false otherwise.
	 * @param p - The product to check.
	 */
	public boolean isFavorite(Product p){
		return backend.isFavorite(p);
	}
	
	/**
	 * Returns true if this is the first run, false otherwise.
	 */
	public boolean isFirstRun(){
		return backend.isFirstRun();
	}
	
	/**
	 * Creates an order from the current contents of the shoppingcart. Also removes the items currently in the ShoppingCart.
	 * All orders are remembered by the IMatDataHandler. This method is shorthand for placeOrder(true).
	 * @return An Order object containing information about the order.
	 */
	public Order placeOrder(){
		return backend.placeOrder();
	}
	
	/**
	 * Creates an order from the current contents of the shoppingcart. All orders are remembered by the backend.
	 * @param clearShoppingCart - indicates whether the shopping cart is cleared or not.
	 * @return An Order object containing information about the order.
	 */
	public Order placeOrder(boolean clearShoppingCart){
		return backend.placeOrder(clearShoppingCart);
	}
	
	/**
	 * Removes favorite status from a product. Does not affect favorite lists.
	 * @param p The product to de-favoritize. I make up words.
	 */
	public void removeFavorite(Product p){
		backend.removeFavorite(p);
	}
	
	/**
	 * Remove the favorite list matching the input list.
	 * @param list - The list to be removed.
	 * @throws IllegalArgumentException If no matching favorite lists are found.
	 */
	public void removeFavoriteList(FavoriteList list) throws IllegalArgumentException {
		if(favoriteLists.contains(list)){
			favoriteLists.remove(favoriteLists.indexOf(list));
		}else{
			throw new IllegalArgumentException("There is no list matching the list given: " + list.getName());
		}
	}
	
	/**
	 * Resets the backend to it's initial state. Mostly useful during development.
	 */
	public void reset(){
		backend.reset();
	}
	
	/**
	 * Inner class to handle favorite lists.
	 *
	 */
	public class FavoriteList{
		private String name;
		private List<Product> products;
		
		public FavoriteList(List<Product> products, String name){
			this.products = products;
			this.name = name;
		}
		/**
		 * Returns the name of the favorite list. 
		 * @return The name of this favorite list.
		 */
		public String getName(){
			return name;
		}
		/**
		 * Returns the list of products in the favorite list.
		 * @return The products in this favorite list.
		 */
		public List<Product> getProducts(){
			return products;
		}
	}
}
