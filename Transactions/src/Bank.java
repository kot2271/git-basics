import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class Bank {
  public static Double BIG_TRANSFERS_PERCENT = 0.05;

  private ConcurrentHashMap<String, Account> accounts;
  private final Random random = new Random();

 public Bank() {
    accounts = new ConcurrentHashMap<>();
  }

  final double numRandom = Math.random();

  public ConcurrentHashMap<String, Account> getAccounts() {
    return accounts;
  }

  public long getTotalMoney() {
    return accounts.values().stream().mapToLong(Account::getMoney).sum();
  }

  public void setAccounts(ConcurrentHashMap<String, Account> accounts) {
    this.accounts = accounts;
  }

  public synchronized boolean isFraud(String fromAccountNum, String toAccountNum, long amount)
      throws InterruptedException {
    System.out.printf(
        "....Verification in progress (%s -> %s, %d)", fromAccountNum, toAccountNum, amount);

    Thread.sleep(1000);
    return random.nextBoolean();
  }

  /**
   * TODO: реализовать метод. Метод переводит деньги между счетами. Если сумма транзакции > 50000,
   * то после совершения транзакции, она отправляется на проверку Службе Безопасности – вызывается
   * метод isFraud. Если возвращается true, то делается блокировка счетов (как – на ваше усмотрение)
   */
  public void transfer(String fromAccountNum, String toAccountNum, long amount) {
    Account fromAccount = accounts.get(fromAccountNum);
    Account toAccount = accounts.get(toAccountNum);

      Account lowSyncAccount = fromAccountNum.compareTo(toAccountNum) > 0 ? fromAccount : toAccount;
      Account topSyncAccount = lowSyncAccount == fromAccount ? toAccount : fromAccount;

    synchronized (lowSyncAccount) {
      synchronized (topSyncAccount) {
        System.out.printf(
            "%s >>> %s (%d)", fromAccount.getAccNumber(), toAccount.getAccNumber(), amount);
        if (!isChecked(fromAccount, toAccount, amount)) {
          return;
        }
        lowerMoney(fromAccount, amount);
        enlargeMoney(toAccount, amount);

        try {
          if (amount > 50000 && isFraud(fromAccountNum, toAccountNum, amount)) {
            fromAccount.setBlock(true);
            toAccount.setBlock(true);
            System.out.printf(
                "...Failed operation" + "\nAccounts %s and %s blocked Security Service\n",
                fromAccount.getAccNumber(), toAccount.getAccNumber());
          } else {
            System.out.println("...Operation was successfully completed\t");
          }
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }

  /** TODO: реализовать метод. Возвращает остаток на счёте. */
  public long getBalance(String accountNum) {
    return accounts.get(accountNum).getMoney();
  }

  private boolean isChecked(Account from, Account to, long value) {
    if (from.isBlock() || to.isBlock()) {
      System.out.println("...One of the accounts is blocked");
      return false;
    }
    if (numRandom == Math.random() * BIG_TRANSFERS_PERCENT) {
      final boolean b = (from.isBlock() && to.isBlock());
      return b;
    }

    if (!(from.getMoney() >= value && value > 0)) {
      System.out.println("...Not enough money or wrong amount");
      return false;
    }
    return true;
  }

  private void lowerMoney(Account account, long amount) {
    account.setMoney(account.getMoney() - amount);
  }

  private void enlargeMoney(Account account, long amount) {
    account.setMoney(account.getMoney() + amount);
  }
}
