package uk.ac.shef.com3529.bankaccount;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class BankAccountTestUsingMockito {

    @Test
    public void shouldAssignABankAccountWhenOpeningAnAccount() {
        // Setup the mock, including to return a bank account number of 1000
        BankAccountDatabaseConnection mock = mock();
        when(mock.createBankAccount()).thenReturn(1000);

        // Given a bank account
        BankAccount bankAccount = new BankAccount(mock, 0, 0);

        // Then it should have a bank account number
        assertThat(bankAccount.getBankAccountNumber(), equalTo(1000));
    }

    @Test
    public void shouldNotAllowNegativeAmountsToBeWithdrawn() {
        // Setup the mock
        BankAccountDatabaseConnection mock = mock();

        // Given a bank account
        BankAccount bankAccount = new BankAccount(mock, 0, 0);

        // When a negative amount is withdrawn, Then an exception is thrown
        assertThrows(BankAccountException.class, () -> {
            bankAccount.withdraw(-1000);
        });
    }

    @Test
    public void shouldCalculateBalanceCorrectlyFollowingAWithdrawal() {
        // Setup the mock, including to return a bank account number of 1000
        BankAccountDatabaseConnection mock = mock();
        when(mock.createBankAccount()).thenReturn(1000);
        
        // Given a bank account with a balance of £500 and an overdraft of £0
        BankAccount bankAccount = new BankAccount(mock, 500, 0);

        // Set the database mock to return a balance of £500 for this account number
        // as would have been instructed to have been set in the database via
        // the constructor
        when(mock.getBalance(1000)).thenReturn(500);
        
        // When £100 is withdrawn
        bankAccount.withdraw(100);

        // Then the balance should be £400
        verify(mock).setBalance(1000, 400);
    }

    @Test
    public void shouldDepositAmount() {
        // Setup the mock, including to return a bank account number of 1000
        BankAccountDatabaseConnection mock = mock();
        when(mock.createBankAccount()).thenReturn(1000);

        // Given a Bank account with an opening balance of £100 and no overdraft
        BankAccount bankAccount = new BankAccount(mock, 100, 0);

        // Set the database mock to return a balance of £100 for this account number
        // as would have been instructed to have been set in the database via
        // the constructor
        when(mock.getBalance(1000)).thenReturn(100);

        // When £100 is deposited
        bankAccount.deposit(100);

        // Then a call should be made to set the balance of the account to £200
        verify(mock).setBalance(1000, 200);
    }

}
