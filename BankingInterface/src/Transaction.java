import java.util.Date;

public class Transaction {

    /**
     * Value of transaction
     */
    private double amount;

    /**
     * Time transaction was conducted
     */
    private Date timeStamp;

    /**
     * Memo for this transaction
     */
    private String memo;

    /**
     * The account in which transaction was performed
     */
    private Account inAccount;

    /**
     * create a transaction
     *
     * @param theAmount
     *            value of Transaction
     * @param newAccount
     *            Account interacted with
     */
    public Transaction(double theAmount, Account newAccount) {
        this.amount = theAmount;
        //automatically creates new Date object with current time of transaction
        this.timeStamp = new Date();
        this.inAccount = newAccount;

    }

    /**
     * create a transaction
     *
     * @param theAmount
     *            value of transaction
     * @param newMemo
     *            Reason for transaction
     * @param newAccount
     *            account interacted with
     */
    public Transaction(double theAmount, String newMemo, Account newAccount) {

        this(theAmount, newAccount);
        this.memo = newMemo;

    }

    public String getSummaryLine() {
        if (this.amount >= 0) {
            return String.format("%s: $%.02f: %s", this.timeStamp.toString(),
                    this.amount, this.memo);
        } else {
            return String.format("%s: $(%.02f): %s", this.timeStamp.toString(),
                    -this.amount, this.memo);

        }
    }

    //getters and setters

    /**
     * Get the amount of the transaction
     *
     * @return amount of transaction
     */
    public double getAmount() {
        return this.amount;
    }

    /**
     *
     * @return the date
     */
    public Date getTimeStamp() {
        return this.timeStamp;
    }

    /**
     *
     * @return the memo
     */
    public String getMemo() {
        return this.memo;
    }

    /**
     *
     * @return account associated with transaction
     */
    public Account getInAccount() {
        return this.inAccount;
    }

}