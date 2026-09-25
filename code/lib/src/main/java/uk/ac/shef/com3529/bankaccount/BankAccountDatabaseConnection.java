package uk.ac.shef.com3529.bankaccount;

public interface BankAccountDatabaseConnection {

    public int createBankAccount();

    public int getBalance(int bankAccountNo);

    public void setBalance(int bankAccountNo, int amount);

    public int getOverdraft(int bankAccountNo);

    public void setOverdraft(int bankAccountNo, int amount);

}
