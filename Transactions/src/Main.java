import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Main {
  public static Integer TRANSFERS_QUANTITY = 100_000;
  public static Integer TREADS_QUANTITY = 8;

  public static void main(String[] args) {

    Bank bank = new Bank();
    Random random = new Random();

    accounts();
    bank.setAccounts(accounts());

    List<Thread> threads = Collections.synchronizedList(new ArrayList());

    for (int i = 0; i < TREADS_QUANTITY; i++) {

      threads.add(
          new Thread(
              () -> {
                for (int j = 0; j < TRANSFERS_QUANTITY; j++) {

                  String recipient =
                      new ArrayList<>(accounts().keySet())
                          .get(random.nextInt(accounts().keySet().size()));

                  String sender =
                      new ArrayList<>(accounts().keySet())
                          .get(random.nextInt(accounts().keySet().size()));
                  bank.transfer(sender, recipient, 1);
                }
              }));
          }

    threads.forEach(Thread::start);
    System.out.println("\nStarting balance : " + bank.getTotalMoney() + "\n");
    try {
      Thread.sleep(3000);

    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    for (Thread thread : threads) {
      try {
        thread.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    System.out.println("1467: " + bank.getBalance("1467") + " status: " + !Account.isBlock());
    System.out.println("2931: " + bank.getBalance("2931") + " status: " + !Account.isBlock());
    System.out.println("3098: " + bank.getBalance("3098") + " status: " + !Account.isBlock());
    System.out.println("4782: " + bank.getBalance("4782") + " status: " + !Account.isBlock());

    System.out.println("Balance after all transactions: " + bank.getTotalMoney());
  }

  public static ConcurrentHashMap<String, Account> accounts() {
    ConcurrentHashMap<String, Account> accounts = new ConcurrentHashMap<>();
    Account id1 = new Account("1467", 600000);
    Account id2 = new Account("2931", 500000);
    Account id3 = new Account("3098", 1000000);
    Account id4 = new Account("4782", 100);

    accounts.put(id1.getAccNumber(), id1);
    accounts.put(id2.getAccNumber(), id2);
    accounts.put(id3.getAccNumber(), id3);
    accounts.put(id4.getAccNumber(), id4);

    return accounts;
  }

}
