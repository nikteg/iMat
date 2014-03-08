package se.chalmers.dat215.grupp14;

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
     * 
     * @param model
     */
    public CCardHandler(IMatModel model) {
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
     * 
     * @param userName
     * @return a List<CCard> belonging to the given userName. returns null if
     *         user has no CCards.
     */
    public List<CCard> getCCards(Account account) {
        if (cardMap.containsKey(account.getUserName())) {
            return cardMap.get(account.getUserName());
        } else {
            return null;
        }
    }

    /**
     * Removes the given card belonging to the given user, if it exists. Only
     * checks for matching card numbers.
     * 
     * @param card
     *            CCard to remove
     * @param userName
     *            Owner of the card.
     */
    public void removeCard(CCard card, Account account) {
        if (cardMap.containsKey(account.getUserName())) {
            List<CCard> cards = cardMap.get(account.getUserName());
            CCard deleteCard = null;
            for (CCard cc : cards) {
                if (cc.getCardNumber().equals(card.getCardNumber())) {
                    deleteCard = cc;
                }
            }
            cards.remove(deleteCard);
        }
        this.saveState();
    }

    /**
     * Saves the given card to the given user.
     * 
     * @param card
     * @param userName
     */
    public void saveCard(CCard card, Account account) {
        if (cardMap.containsKey(account.getUserName())) {
            List<CCard> cards = cardMap.get(account.getUserName());
            cards.add(card);
        } else {
            List<CCard> cards = new ArrayList<CCard>();
            cards.add(card);
            cardMap.put(account.getUserName(), cards);
        }
        this.saveState();
    }

    /**
     * Save the current cards to file.
     */
    public void saveState() {
        Writer writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(model.getDataDirectory()
                    + "/cclists.txt"), "utf-8"));

            for (String userName : cardMap.keySet()) {
                writer.write("uid" + ";" + userName + ";");
                List<CCard> cards = cardMap.get(userName);
                for (CCard card : cards) {
                    String cardNumber = card.getCardNumber();
                    String holdersName = card.getHolder().getUserName();
                    String validMonth = card.getValidMonth();
                    String validYear = card.getValidYear();
                    String cvc = card.getCVC();
                    writer.write("ccard;" + cardNumber + ";" + holdersName + ";" + validMonth + ";" + validYear + ";"
                            + cvc + ";");
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
            String holder = "";
            String validMonth;
            String validYear;
            String cvc;

            List<CCard> cards = new ArrayList<CCard>();

            if (sc.hasNext()) {
                while (sc.hasNext()) {
                    if (sc.hasNext("uid")) {
                        sc.next();
                        userName = sc.next();
                        cards = new ArrayList<CCard>();
                    }
                    if (sc.hasNext("ccard")) {
                        sc.next();
                        cardNumber = sc.next();
                        holder = sc.next();
                        validMonth = sc.next();
                        validYear = sc.next();
                        cvc = sc.next();
                        cards.add(new CCard(cardNumber, validMonth, validYear, cvc, model.getAccountHandler()
                                .findAccount(holder)));
                    }
                    if (sc.hasNext("enduser")) {
                        sc.next();
                        List<CCard> cardsClone = new ArrayList<CCard>();
                        cardsClone.addAll(cards);
                        dbmap.put(userName, cardsClone);
                        cards.clear();
                    }
                }
                sc.close();
                return dbmap;
            } else { // empty file
                sc.close();
                return dbmap;
            }
        } catch (FileNotFoundException e) {
            return dbmap;
        }
    }
}
