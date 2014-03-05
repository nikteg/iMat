import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CCardHandler {
	private IMatModel model;
	private Map<String, List<CCard>> cardMap;
	
	/**
	 * Creates an instance of the CCardHandler working with the given model.
	 * @param model
	 */
	public CCardHandler (IMatModel model) {
		this.model = model;
		this.cardMap = null;
		try {
			this.cardMap = this.getCardMap();
		} catch (IOException e) {
			this.cardMap = null;
		}
	}
	
	/**
	 * Get the CCards for the given user.
	 * @param userName
	 * @return a List<CCard> belonging to the given userName.
	 * 		    returns null if user has no CCards.
	 */
	public List<CCard> getCCards(String userName){
		if (cardMap.containsKey(userName)){
			return cardMap.get(userName);
		} else {
			return null;
		}
	}
	
	/**
	 * Removes the given card belonging to the given user, if it exists.
	 * Only checks for matching card numbers.
	 * @param card CCard to remove
	 * @param userName Owner of the card.
	 */
	public void removeCard(CCard card, String userName) {
		if (cardMap.containsKey(userName)) {
			List<CCard> cards = cardMap.get(userName);
			for (CCard cc : cards) {
				if (cc.getCardNumber().equals(card.getCardNumber())) {
					cards.remove(cc);
				}
			}
		}
	}
	
	/**
	 * Saves the given card to the given user.
	 * @param card
	 * @param userName
	 */
	public void saveCard(CCard card, String userName){
		if (cardMap.containsKey(userName)){
			List<CCard> cards = cardMap.get(userName);
			cards.add(card);
		} else {
			List<CCard> cards = new ArrayList<CCard>();
			cards.add(card);
			cardMap.put(userName, cards);
		}
			this.saveState();
	}
	
	/**
	 * Save the current cards to file.
	 */
	public void saveState(){
		Writer writer = null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(model.getDataDirectory() + "/cclists.txt"), "utf-8"));
			
			for (String userName : cardMap.keySet()) {
				writer.write("uid" + ";" + userName + ";");
				List<CCard> cards = cardMap.get(userName);
				for (CCard card : cards){
					String cardNumber = card.getCardNumber();
					String cardType = card.getCardType();
					String holdersName = card.getHoldersName();
					int validMonth = card.getValidMonth();
					int validYear = card.getValidYear();
					int cvc = card.getCvc();
					writer.write("ccard;" + cardNumber + ";" + cardType + ";" + holdersName + ";" + 
					validMonth + ";" + validYear + ";" + cvc + ";");
				}
				writer.write("enduser;");
			}
			writer.close();
		} catch (IOException e) {
			throw new RuntimeException("I/O error. Changes could not be saved to file.");
		}
	}
	
	private Map<String, List<CCard>> getCardMap() throws IOException {
		Scanner sc = null;
		Map<String, List<CCard>> dbmap = new HashMap<String, List<CCard>>();
		try {
			sc = new Scanner(new FileReader(model.getDataDirectory() + "/cclists.txt"));
			sc.useDelimiter(";");
			
			String userName = "";
			String cardNumber = "";
			String cardType = "";
			String holdersName = "";
			int validMonth;
			int validYear;
			int cvc;
			
			List<CCard> cards = new ArrayList<CCard>();
			
			if (sc.hasNext()) {
				while(sc.hasNext()){
					if (sc.hasNext("uid")) {
						sc.next();
						userName = sc.next();
						cards = new ArrayList<CCard>();
					}
					if (sc.hasNext("ccard")) {
						sc.next();
						cardNumber = sc.next();
						cardType = sc.next();
						holdersName = sc.next();
						validMonth = Integer.parseInt(sc.next());
						validYear = Integer.parseInt(sc.next());
						cvc = Integer.parseInt(sc.next());
						cards.add(new CCard(cardNumber, cardType, holdersName, validMonth, validYear, cvc));
					}
					if(sc.hasNext("enduser")){
						sc.next();
						List<CCard> cardsClone = new ArrayList<CCard>();
						cardsClone.addAll(cards);
						dbmap.put(userName, cardsClone);
						cards.clear();
					}
				}
				sc.close();
				return dbmap;
			} else { //empty file
				sc.close();
				return dbmap;
			}
		} catch (FileNotFoundException e) {
			return dbmap;
		}
	}
}
