package se.chalmers.dat215.grupp14;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AccountHandler {
    private List<Account> accounts = new ArrayList<Account>();
    private Account currentAccount;
    private IMatModel model;
    private final Account anonymousAccount = new Account("", "", "");

    public AccountHandler(IMatModel model) {
        this.model = model;
        this.anonymousAccount.setAnonymous(true);
        loadState();
    }

    public void removeAccount(Account account) {
        if (accounts.contains(account)) {
            accounts.remove(account);
        }
        
        saveState();
    }
    
    public void addAccount(Account account, boolean signIn) {
        accounts.add(account);
        
        if (signIn)
            setCurrentAccount(account);
        
        saveState();
    }
    
    public Account findAccount(String userName) {
        for (Account account : getAccounts()) {
            if (account.getUserName().equalsIgnoreCase((userName))) {
                return account;
            }
        }
        
        return null;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void saveState() {
        Writer writer = null;

        try {
            for (Account account : accounts) {
                if (account.isAnonymous())
                    continue;
                System.out.println(account.getUserName());
                File writeFile = new File(model.getDataDirectory() + "/accounts/" + account.getUserName());

                if (!writeFile.exists())
                    writeFile.createNewFile();

                writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(writeFile), "UTF-8"));
                writer.write(String.format("%s;%s;%s;%s;%s;%s;%s;%s;%s;%s", account.getUserName(), account.getPassword(),
                        account.getEmail(), account.getFirstName(), account.getLastName(), account.getAddress(),
                        account.getMobilePhoneNumber(), account.getPhoneNumber(), account.getPostAddress(),
                        account.getPostCode()));
            }

            writer.close();
        } catch (IOException e) {
            throw new RuntimeException("I/O error. Changes could not be saved to file.", e);
        }
    }

    private Account readAccount(File file) {
        Account account = null;
        Scanner sc = null;
        
        try {
            sc = new Scanner(new FileReader(file));
            sc.useDelimiter(";");
            
            account = new Account(sc.next(), sc.next(), sc.next(), sc.next(), sc.next(), sc.next(), sc.next(),
                    sc.next(), sc.next(), sc.next());
            account.setAnonymous(false);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("I/O error. Could not read from file.", e);
        }
        
        return account;
    }

    public void loadState() {
        File folder = new File(model.getDataDirectory() + "/accounts/");

        if (!folder.exists())
            folder.mkdir();

        for (File file : folder.listFiles()) {
            if (file.getName().startsWith(".")) continue;
            accounts.add(readAccount(file));
        }
    }
    
    public Account getCurrentAccount() {
        if (currentAccount == null) {
            return anonymousAccount;
        }
        return currentAccount;
    }

    public void setCurrentAccount(Account currentAccount) {
        this.currentAccount = currentAccount;
    }
}
