package se.chalmers.dat215.grupp14.backend;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CreditCardHandler {
    private IMatModel model;
    private Map<Account, List<CreditCard>> cardMap = new HashMap<Account, List<CreditCard>>();

    /**
     * Creates an instance of the CCardHandler working with the given model.
     * @param model
     */
    public CreditCardHandler(IMatModel model) {
        this.model = model;
        loadState();
    }

    /**
     * Get the credit cards for the given user.
     * @param account
     * @return
     */
    public List<CreditCard> getCards(Account account) {
        if (cardMap.containsKey(account)) {
            return cardMap.get(account);
        }

        return new ArrayList<CreditCard>();
    }

    /**
     * Removes the given card belonging to the given user, if it exists. Only
     * checks for matching card numbers.
     * @param card
     * @param account
     */
    public void removeCard(CreditCard card, Account account) {
        if (cardMap.containsKey(account)) {
            List<CreditCard> cards = cardMap.get(account);
            for (CreditCard cc : cards) {
                if (cc.getCardNumber().equals(card.getCardNumber())) {
                    cards.remove(cc);
                    break;
                }
            }
            
        }
    }

    /**
     * Saves the given card to the given user
     * @param card
     * @param userName
     */
    public void saveCard(CreditCard card, Account account) {
        if (cardMap.containsKey(account)) {
            List<CreditCard> cards = cardMap.get(account);
            cards.add(card);
        } else {
            List<CreditCard> cards = new ArrayList<CreditCard>();
            cards.add(card);
            cardMap.put(account, cards);
        }
    }

    /**
     * Save the current cards to file
     */
    public void saveState() {
        Writer writer = null;
        
        try {
            for (Account account : cardMap.keySet()) {
                File writeFile = new File(model.getDataDirectory() + "/ccards/" + account.getUserName() + ".txt");
                
                if (!writeFile.exists())
                    writeFile.createNewFile();
                
                writer = new PrintWriter(writeFile, "UTF-8");
                
                List<CreditCard> cards = getCards(account);
                for (CreditCard card : cards) {
                    writer.write(String.format("%s;%s;%s;%s;", card.getCardNumber(), card.getValidMonth(), card.getValidYear(), card.getCVC()));
                }
                
                writer.close();
            }
        } catch (IOException e) {
            throw new RuntimeException("I/O error. Changes could not be saved to file.", e);
        }
    }
    
    /**
     * Load state from file
     */
    public void loadState() {
        File folder = new File(model.getDataDirectory() + "/ccards/");
        
        if (!folder.exists())
            folder.mkdir();

        for (File file : folder.listFiles()) {
            if (file.getName().endsWith(".txt")) {
                cardMap.put(model.getAccountHandler().findAccount(file.getName().replace(".txt", "")), readCards(file));
            }
        }
    }
    
    /**
     * Add cards from file
     * @param file
     * @return
     */
    private List<CreditCard> readCards(File file) {
        List<CreditCard> cards = new ArrayList<CreditCard>();
        Scanner sc = null;
        
        try {
            sc = new Scanner(new FileReader(file));
            sc.useDelimiter(";");
            
            while (sc.hasNext()) {
                cards.add(new CreditCard(sc.next(), sc.next(), sc.next(), sc.next(), model.getAccountHandler().findAccount(file.getName().replace(".txt", ""))));
            }
            
        } catch (FileNotFoundException e) {
            throw new RuntimeException("I/O error. Could not read from file.", e);
        } finally {
            sc.close();
        }
        
        return cards;
    }
}
