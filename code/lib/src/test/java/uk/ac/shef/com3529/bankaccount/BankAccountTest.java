package uk.ac.shef.com3529.bankaccount;

import org.junit.jupiter.api.Test;
import uk.ac.shef.com3529.bankaccount.doubles.DummyBankAccountDatabaseConnection;
import uk.ac.shef.com3529.bankaccount.doubles.FakeBankAccountDatabaseConnection;
import uk.ac.shef.com3529.bankaccount.doubles.MockedBankAccountDatabaseConnection;
import uk.ac.shef.com3529.bankaccount.doubles.StubbedBankAccountDatabaseConnection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BankAccountTest {

    @Test
    public void shouldAssignABankAccountWhenOpeningAnAccount() {
        StubbedBankAccountDatabaseConnection stub = new StubbedBankAccountDatabaseConnection();

        // Given a bank account
        BankAccount bankAccount = new BankAccount(stub, 0, 0);

        // Then it should have a bank account number
        assertThat(bankAccount.getBankAccountNumber(), equalTo(1000));
    }

    @Test
    public void shouldNotAllowNegativeAmountsToBeWithdrawn() {
        DummyBankAccountDatabaseConnection dummy = new DummyBankAccountDatabaseConnection();

        // Given a bank account
        BankAccount bankAccount = new BankAccount(dummy, 0, 0);

        // When a negative amount is withdrawn, Then an exception is thrown
        assertThrows(BankAccountException.class, () -> {
            bankAccount.withdraw(-1000);
        });
    }

    @Test
    public void shouldCalculateBalanceCorrectlyFollowingAWithdrawal() {
        FakeBankAccountDatabaseConnection fake = new FakeBankAccountDatabaseConnection(0);

        // Given a bank account with a balance of £500 and an overdraft of £0
        BankAccount bankAccount = new BankAccount(fake, 500, 0);

        // When £100 is withdrawn
        bankAccount.withdraw(100);

        // Then the balance should be £400
        assertThat(bankAccount.getBalance(), equalTo(400));
    }

    @Test
    public void shouldDepositAmount() {
        MockedBankAccountDatabaseConnection mock = new MockedBankAccountDatabaseConnection();

        // Given a Bank account with an opening balance of £100 and no overdraft
        BankAccount bankAccount = new BankAccount(mock, 100, 0);

        // When £100 is deposited
        bankAccount.deposit(100);

        // Then a call should be made to set the balance of the account to £200
        // (as signalled by a flag - mock.verify - being set to true)
        assertThat(mock.verify, equalTo(true));
    }
}
