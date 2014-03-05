/**
 * Simple class to represent a credit card.
 * Necessary for the iMat project since the backend's CreditCard class doesn't support creating new instances.
 */
public class CCard {
	private String cardNumber;
	private String cardType;
	private String holdersName;
	private int validMonth;
	private int validYear;
	private int cvc;
	
	public CCard (String cardNumber, String cardType, String holdersName, int validMonth, int validYear, int cvc) {
		this.cardNumber = cardNumber;
		this.cardType = cardType;
		this.holdersName = holdersName;
		this.validMonth = validMonth;
		this.validYear = validYear;
	}
	
	public String getCardNumber() {
		return cardNumber;
	}
	
	public String getCardType() {
		return cardType;
	}
	
	public String getHoldersName() {
		return holdersName;
	}
	
	public int getValidMonth() {
		return validMonth;
	}
	
	public int getValidYear() {
		return validYear;
	}
	
	public int getCvc() {
		return cvc;
	}
}
