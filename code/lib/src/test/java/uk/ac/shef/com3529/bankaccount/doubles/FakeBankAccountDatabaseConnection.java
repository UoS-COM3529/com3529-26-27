package uk.ac.shef.com3529.bankaccount.doubles;

import uk.ac.shef.com3529.bankaccount.BankAccountDatabaseConnection;

public class FakeBankAccountDatabaseConnection implements BankAccountDatabaseConnection {

    private final int bankAccountNumber;
    private int balance;
    private int overdraft;

    public FakeBankAccountDatabaseConnection(int bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    @Override
    public int createBankAccount() {
        return bankAccountNumber;
    }

    @Override
    public int getBalance(int bankAccountNo) {
        return balance;
    }

    @Override
    public void setBalance(int bankAccountNo, int amount) {
        this.balance = amount;
    }

    @Override
    public int getOverdraft(int bankAccountNo) {
        return overdraft;
    }

    @Override
    public void setOverdraft(int bankAccountNo, int amount) {
        this.overdraft = amount;
    }
}
