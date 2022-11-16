
public class Exercicio_2 {

    public static void main(String[] args) throws InterruptedException {

        Bank bank = new Bank();
        int N = 10;
        Deposit dep = new Deposit(bank);

        Thread[] thread = new Thread[N];

        for(int i = 0; i < N; i++)
            thread[i] = new Thread(dep);

        for(int i = 0; i < N; i++)
            thread[i].start();
        for(int i = 0; i < N; i++)
            thread[i].join();

        System.out.println("Account balance: " + bank.balance());
    }
}
