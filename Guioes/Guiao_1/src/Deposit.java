public class Deposit implements Runnable {
    private Bank bank;

    public Deposit(Bank bank) {
        this.bank = bank;
    }

    public void run() {
        int I = 1000;
        int V = 100;

        for(int j = 0; j < I; j++) {
            bank.deposit(V);
        }
    }
}
