package se.chalmers.dat215.grupp14.backend;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Simple class to represent a credit card. Necessary for the iMat project since
 * the backend's CreditCard class doesn't support creating new instances.
 */
public class CreditCard {
    private String cardNumber;
    private Account holder;
    private String month;
    private String year;
    private String cvc;

    public CreditCard(String cardNumber, String validMonth, String validYear, String cvc, Account holder) {
        this.cardNumber = cardNumber;
        this.month = validMonth;
        this.year = validYear;
        this.cvc = cvc;
        this.holder = holder;
    }

    public String getCardType() {
        Pattern masterCardPattern = Pattern.compile("^5[1-5]{3}(-| )?[0-9]{4}(-| )?[0-9]{4}(-| )?[0-9]{4}$");
        Pattern visaCardPattern = Pattern.compile("^4[1-9]{3}(-| )?[0-9]{4}(-| )?[0-9]{4}(-| )?[0-9]{4}$");
        Pattern amexCardPattern = Pattern.compile("^(34|37)[1-9]{2}(-| )?[0-9]{6}(-| )?[0-9]{5}$");

        Matcher masterCardMatcher = masterCardPattern.matcher(getCardNumber());
        Matcher visaCardMatcher = visaCardPattern.matcher(getCardNumber());
        Matcher amexCardMatcher = amexCardPattern.matcher(getCardNumber());

        if (masterCardMatcher.matches())
            return "mastercard";

        if (visaCardMatcher.matches())
            return "visa";

        if (amexCardMatcher.matches())
            return "amex";

        return "";
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public Account getHolder() {
        return holder;
    }

    public String getValidMonth() {
        return month;
    }

    public String getValidYear() {
        return year;
    }

    public String getCVC() {
        return cvc;
    }
    
    @Override
    public int hashCode() {
        return cardNumber.hashCode();
    }
    
    @Override
    public boolean equals(Object other) {
        if (((CreditCard)other).getCardNumber().equals(cardNumber)) {
            return true;
        }
        
        return false;
    }
}
