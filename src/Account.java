import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import se.chalmers.ait.dat215.project.CreditCard;
import se.chalmers.ait.dat215.project.Customer;
import se.chalmers.ait.dat215.project.Product;
import se.chalmers.ait.dat215.project.User;


public class Account {
	private User user;
	private Customer customer;
	
	private Map<String, List<Product>> favoriteLists = new HashMap<String, List<Product>>();
	private List<CreditCard> creditCardList = new ArrayList<CreditCard>();
	
	private boolean anonymous = true;
	
	public Account(User user, Customer customer) {
		setUser(user);
		setCustomer(customer);
	}
	
	/**
	 * Create a new favorite list with the given products and the given name.
	 * 
	 * @param products
	 *            The products to put in the list.
	 * @param name
	 *            The name of the new list.
	 * @throws IllegalArgumentException
	 *             if a favorite list with the given name already exists.
	 */
	public void addFavoriteList(List<Product> products, String name) throws IllegalArgumentException {
		if (favoriteLists.containsKey(name))
			throw new IllegalArgumentException("Favorite list with title " + name + " already exists.");

		favoriteLists.put(name, products);
	}
	
	/**
	 * Remove the favorite list matching the input list.
	 * 
	 * @param list
	 *            - The list to be removed.
	 * @throws IllegalArgumentException
	 *             If no matching favorite lists are found.
	 */
	public void removeFavoriteList(String name) throws IllegalArgumentException {
		if (!favoriteLists.containsKey(name))
			throw new IllegalArgumentException(
					"There is no list matching the list given: " + name);

		favoriteLists.remove(favoriteLists.remove(name));
	}
	
	/**
	 * Returns an ArrayList of all the favorite lists.
	 * 
	 * @return A List<FavoriteList> with all favorite lists.
	 */
	public Map<String, List<Product>> getFavoriteLists() {
		return favoriteLists;
	}
	
	public void setCredentials(String userName, String password, String email, String firstName, String lastName, String address, String mobilePhoneNumber, String phoneNumber, String postAddress, String postCode) {
		setUserName(userName);
		setPassword(password);
		setEmail(email);
		setFirstName(firstName);
		setLastName(lastName);
		setAddress(address);
		setMobilePhoneNumber(mobilePhoneNumber);
		setPhoneNumber(phoneNumber);
		setPostAddress(postAddress);
		setPostCode(postCode);
		
		setAnonymous(false);
	}
	
	public String getUserName() {
		return user.getUserName();
	}

	public void setUserName(String userName) {
		user.setUserName(userName);
	}

	public String getPassword() {
		return user.getPassword();
	}

	public void setPassword(String password) {
		user.setPassword(password);
	}

	public String getEmail() {
		return customer.getEmail();
	}

	public void setEmail(String email) {
		customer.setEmail(email);
	}

	public String getFirstName() {
		return customer.getFirstName();
	}

	public void setFirstName(String firstName) {
		customer.setFirstName(firstName);
	}

	public String getLastName() {
		return customer.getLastName();
	}

	public void setLastName(String lastName) {
		customer.setLastName(lastName);
	}

	public String getAddress() {
		return customer.getAddress();
	}

	public void setAddress(String address) {
		customer.setAddress(address);
	}

	public String getMobilePhoneNumber() {
		return customer.getMobilePhoneNumber();
	}

	public void setMobilePhoneNumber(String mobilePhoneNumber) {
		customer.setMobilePhoneNumber(mobilePhoneNumber);
	}

	public String getPhoneNumber() {
		return customer.getPhoneNumber();
	}

	public void setPhoneNumber(String phoneNumber) {
		customer.setPhoneNumber(phoneNumber);
	}

	public String getPostAddress() {
		return customer.getPostAddress();
	}

	public void setPostAddress(String postAddress) {
		customer.setPostAddress(postAddress);
	}

	public String getPostCode() {
		return customer.getPostCode();
	}

	public void setPostCode(String postCode) {
		customer.setPostCode(postCode);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public boolean isAnonymous() {
		return anonymous;
	}

	public void setAnonymous(boolean anonymous) {
		this.anonymous = anonymous;
	}
}
