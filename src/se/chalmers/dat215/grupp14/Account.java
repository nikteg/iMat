package se.chalmers.dat215.grupp14;
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
	
	private boolean anonymous = true;
	
	public Account(User user, Customer customer) {
		setUser(user);
		setCustomer(customer);
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
