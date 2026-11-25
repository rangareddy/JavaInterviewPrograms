package com.ranga.design;

/**
 * Implement a bank with accounts numbered from 1 to {@code n} and starting balances {@code balance[0..n-1]}. {@code transfer(a, b, money)}, {@code deposit(account, money)} and {@code withdraw(account, money)} each return {@code true} if the transaction is valid and has been executed, and {@code false} otherwise. A transaction is invalid if an account number is outside {@code 1..n} or if it would leave an account with a negative balance.
 *
 * <p><b>Algorithm Strategy (Validate Then Mutate):</b>
 * <ol>
 *   <li>Keep the balances in a {@code long[]} (values can be large).</li>
 *   <li>A helper {@code valid(account)} checks {@code 1 <= account <= n}.</li>
 *   <li>{@code withdraw} needs a valid account and a sufficient balance; {@code deposit} needs only a valid account.</li>
 *   <li>{@code transfer} validates both accounts and the source balance first, then withdraws and deposits, so nothing changes on failure.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>With balances {@code [10, 100, 20, 50, 30]}: withdraw 10 from account 3 succeeds (20 -> 10), transfer 15 from 5 to 1 succeeds, deposit 20 to 5 succeeds.</li>
 *   <li>Transferring 15 from account 3 (balance 10) to account 4 fails.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(1) per operation} — Array access only.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The balances.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class SimpleBankSystem {

    public static void main(String[] args) {
        SimpleBankSystem bank = new SimpleBankSystem(new long[]{10, 100, 20, 50, 30});
        System.out.println("Input  : withdraw(3, 10)");
        System.out.println("Output : " + bank.withdraw(3, 10));
        System.out.println();
        System.out.println("Input  : transfer(5, 1, 20), deposit(5, 20), transfer(3, 4, 15), withdraw(10, 50)");
        System.out.println("Output : " + bank.transfer(5, 1, 20) + ", " + bank.deposit(5, 20) + ", " + bank.transfer(3, 4, 15) + ", " + bank.withdraw(10, 50));
    }

    private final long[] balance;

    public SimpleBankSystem(long[] balance) {
        this.balance = balance.clone();
    }

    public boolean transfer(int account1, int account2, long money) {
        if (!valid(account1) || !valid(account2) || balance[account1 - 1] < money) {
            return false;
        }
        balance[account1 - 1] -= money;
        balance[account2 - 1] += money;
        return true;
    }

    public boolean deposit(int account, long money) {
        if (!valid(account)) {
            return false;
        }
        balance[account - 1] += money;
        return true;
    }

    public boolean withdraw(int account, long money) {
        if (!valid(account) || balance[account - 1] < money) {
            return false;
        }
        balance[account - 1] -= money;
        return true;
    }

    private boolean valid(int account) {
        return account >= 1 && account <= balance.length;
    }
}
