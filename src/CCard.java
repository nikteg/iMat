/**
 * Simple class to represent a credit card.
 * Necessary for the iMat project since the backend's CreditCard class doesn't support creating new instances.
 */
public class CCard {
	private String cardNumber;
	private String cardType;
	private String holdersName;
	private String validMonth;
	private String validYear;
	private String cvc;
	
	public CCard (String cardNumber, String cardType, String holdersName, String validMonth, String validYear, String cvc) {
		this.cardNumber = cardNumber;
		this.cardType = cardType;
		this.holdersName = holdersName;
		this.validMonth = validMonth;
		this.validYear = validYear;
		this.cvc = cvc;
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
	
	public String getValidMonth() {
		return validMonth;
	}
	
	public String getValidYear() {
		return validYear;
	}
	
	public String getCvc() {
		return cvc;
	}
}
