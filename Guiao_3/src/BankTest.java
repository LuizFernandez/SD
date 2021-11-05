
public class BankTest {

    public static void runCreateAccount(Bank b, int n) {
        int balance = 1000;

        for(int i = 0; i < n; i++)
            b.createAccount(balance);

    }

    public static void main(String[] args) throws InterruptedException {

        Bank b = new Bank();

        Thread t1 = new Thread(() -> runCreateAccount(b, 3));
        Thread t2 = new Thread(() -> runCreateAccount(b, 4));
        Thread t3 = new Thread(() -> runCreateAccount(b, 2));

        t1.start();t2.start();t3.start();
        t1.join();t2.join();t3.join();


    }
}
