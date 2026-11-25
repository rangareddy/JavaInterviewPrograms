# Simple Bank System

Implement a bank with accounts numbered from 1 to `n` and starting balances `balance[0..n-1]`. `transfer(a, b, money)`, `deposit(account, money)` and `withdraw(account, money)` each return `true` if the transaction is valid and has been executed, and `false` otherwise. A transaction is invalid if an account number is outside `1..n` or if it would leave an account with a negative balance.

## Algorithm Strategy (Validate Then Mutate)

1. Keep the balances in a `long[]` (values can be large).
2. A helper `valid(account)` checks `1 <= account <= n`.
3. `withdraw` needs a valid account and a sufficient balance; `deposit` needs only a valid account.
4. `transfer` validates both accounts and the source balance first, then withdraws and deposits, so nothing changes on failure.

## Example walkthrough

- With balances `[10, 100, 20, 50, 30]`: withdraw 10 from account 3 succeeds (20 -> 10), transfer 15 from 5 to 1 succeeds, deposit 20 to 5 succeeds.
- Transferring 15 from account 3 (balance 10) to account 4 fails.

## Complexity Analysis

- **Time Complexity:** `O(1) per operation` — Array access only.
- **Space Complexity:** `O(n)` — The balances.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: HashMap of Accounts

Store balances in a map keyed by account number. Same logic, more overhead.

```java
public static class Mapped {
    private final java.util.Map<Integer, Long> b = new java.util.HashMap<>();
    public Mapped(long[] init) { for (int i = 0; i < init.length; i++) b.put(i + 1, init[i]); }
    public boolean transfer(int a, int c, long m) { if (!b.containsKey(a) || !b.containsKey(c) || b.get(a) < m) return false; b.put(a, b.get(a) - m); b.put(c, b.get(c) + m); return true; }
    public boolean deposit(int a, long m) { if (!b.containsKey(a)) return false; b.put(a, b.get(a) + m); return true; }
    public boolean withdraw(int a, long m) { if (!b.containsKey(a) || b.get(a) < m) return false; b.put(a, b.get(a) - m); return true; }
}
```

- **Time Complexity:** O(1)
- **Space Complexity:** O(n)

### Approach 2: Array of Balances (Optimized)

A plain `long[]` indexed by account number minus one. This is the version implemented in the repository.

```java
public static class Arr {
    private final long[] b;
    public Arr(long[] init) { b = init.clone(); }
    private boolean ok(int a) { return a >= 1 && a <= b.length; }
    public boolean transfer(int a, int c, long m) { if (!ok(a) || !ok(c) || b[a - 1] < m) return false; b[a - 1] -= m; b[c - 1] += m; return true; }
    public boolean deposit(int a, long m) { if (!ok(a)) return false; b[a - 1] += m; return true; }
    public boolean withdraw(int a, long m) { if (!ok(a) || b[a - 1] < m) return false; b[a - 1] -= m; return true; }
}
```

- **Time Complexity:** O(1)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | HashMap of Accounts | O(1) | O(n) |
| 2 | Array of Balances (Optimized) | O(1) | O(n) |

## Source Code

### SimpleBankSystem.java

```java
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
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: every operation is validated before anything changes, so a failed transaction never leaves a half-applied transfer.

**Sequence** on balances `[10, 100, 20, 50, 30]`:

| Call | Check | Balances afterwards | Result |
|------|-------|---------------------|--------|
| withdraw(3, 10) | 20 >= 10 | [10, 100, 10, 50, 30] | true |
| transfer(5, 1, 20) | 30 >= 20 | [30, 100, 10, 50, 10] | true |
| deposit(5, 20) | valid account | [30, 100, 10, 50, 30] | true |
| transfer(3, 4, 15) | 10 < 15 | unchanged | false |
| withdraw(10, 50) | account 10 does not exist | unchanged | false |

**First output in `main`:** `true`.
