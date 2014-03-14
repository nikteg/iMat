package se.chalmers.dat215.grupp14.backend;

/**
 * Class responsible for combining the backend classes User and Customer
 * 
 * @author Niklas Tegnander, Mikael Lönn and Oskar Jönefors
 */
public class Account {
    private String userName;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String address;
    private String mobilePhoneNumber;
    private String phoneNumber;
    private String postAddress;
    private String postCode;
    private boolean anonymous = true;

    @Override
    public String toString() {
        return String.format("%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;", getUserName(), getPassword(), getEmail(),
                getFirstName(), getLastName(), getAddress(), getMobilePhoneNumber(), getPhoneNumber(),
                getPostAddress(), getPostCode());
    }

    public Account(String userName, String password, String email) {
        this(userName, password, email, "", "", "", "", "", "", "");
    }

    public Account(String userName, String password, String email, String firstName, String lastName, String address,
            String mobilePhoneNumber, String phoneNumber, String postAddress, String postCode) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.mobilePhoneNumber = mobilePhoneNumber;
        this.phoneNumber = phoneNumber;
        this.postAddress = postAddress;
        this.postCode = postCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPostAddress() {
        return postAddress;
    }

    public void setPostAddress(String postAddress) {
        this.postAddress = postAddress;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }

    @Override
    public int hashCode() {
        return userName.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (((Account) other).getUserName().equals(userName)) {
            return true;
        }

        return false;
    }
}
