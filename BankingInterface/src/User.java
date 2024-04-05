import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Creating the User interface of a banking application
 */

public class User {

    /**
     * User's first name
     */
    private String firstName;

    /**
     * User's last name
     */
    private String lastName;

    /**
     * User's account identification
     */
    private String uuid;

    /**
     * MD5 hash of user's pin number
     */
    private byte pinHash[];

    /**
     * the list of accounts for this user
     */
    private ArrayList<Account> accounts;

    public User(String firstName, String lastName, String pin, Bank theBank) {
//        create the name of the account
        this.firstName = firstName;
        this.lastName = lastName;

//        create a hash for the pin
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            this.pinHash = md.digest(pin.getBytes());
        } catch (NoSuchAlgorithmException e) {
            System.err.println("error, caughtNoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }

        // create uuid
        this.uuid = theBank.getNewUserUUID();

        // empty accounts
        this.accounts = new ArrayList<Account>();

        // account created message
        System.out.printf(
                "New account created! First name: %s Last name: %s UUID: %s \n",
                firstName, lastName, this.uuid);
    }

    /**
     * Update User information
     *
     * @param newFirstName
     *            Update first name of user
     */
    public void updateUserInformation(String newFirstName, String newLastName,
            String newPin) {
        this.firstName = newFirstName;
        this.lastName = newLastName;
//      create a hash for the pin
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            this.pinHash = md.digest(newPin.getBytes());
        } catch (NoSuchAlgorithmException e) {
            System.err.println("error, caughtNoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }

    }

    /**
     * Add a new Account
     *
     * @param newAccount
     *            Account to be added to User Object
     */
    public void addAccount(Account newAccount) {
        this.accounts.add(newAccount);
    }

    /**
     * Checks whether a pin is valid
     *
     * @param pin
     *            input pin
     * @return whether pin is valid
     */
    public boolean validatePin(String pin) {
        byte prospectivePinHash[] = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(pin.getBytes()),
                    this.pinHash);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("error, caughtNoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }
        return false;
    }

    /**
     *
     */
    public void printAccountsSummary(User theUser, Scanner sc) {
        System.out.printf("\n\n%s's accounts summary\n", this.firstName);
        for (int a = 0; a < this.accounts.size(); a++) {
            System.out.printf("  %d %s\n", a + 1,
                    this.accounts.get(a).getSummaryLine());
        }
    }

    /**
     * print transaction history for certain account
     *
     * @param acctIdx
     *            the index of account to use
     */
    public void printAcctTransHistory(int acctIdx) {
        this.accounts.get(acctIdx).printTransHistory();
    }

    /**
     * get account balance for a specific account
     *
     * @param acctIdx
     *            index of account to use
     * @return account balance
     */
    public double getAccountBalance(int acctIdx) {
        return this.accounts.get(acctIdx).getBalance();
    }

    /**
     * get the UUID of a particular account
     *
     * @param acctIdx
     *            the index of the account to use
     * @return UUID of the account
     */
    public String getAccountUUID(int acctIdx) {
        return this.accounts.get(acctIdx).getUUID();
    }

    public void addAcctTransaction(int acctIdx, double amount, String memo) {
        this.accounts.get(acctIdx).addTransaction(amount, memo);
    }

    // getters and setters
    /**
     * get User First Name
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * get User Last Name
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * get UUID
     *
     * @return user's UUID
     */
    public String getUUID() {
        return this.uuid;
    }

    /**
     * number of accounts user has with the bank
     *
     * @return number of accounts
     */
    public int numAccounts() {
        return this.accounts.size();
    }

}
