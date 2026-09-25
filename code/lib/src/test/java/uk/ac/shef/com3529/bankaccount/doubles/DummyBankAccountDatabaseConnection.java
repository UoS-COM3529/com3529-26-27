package uk.ac.shef.com3529.bankaccount.doubles;

import uk.ac.shef.com3529.bankaccount.BankAccountDatabaseConnection;

public class DummyBankAccountDatabaseConnection implements BankAccountDatabaseConnection {

    @Override
    public int createBankAccount() {
        return 0;
    }

    @Override
    public int getBalance(int bankAccountNo) {
        return 0;
    }

    @Override
    public void setBalance(int bankAccountNo, int amount) {
    }

    @Override
    public int getOverdraft(int bankAccountNo) {
        return 0;
    }

    @Override
    public void setOverdraft(int bankAccountNo, int amount) {
    }
}
