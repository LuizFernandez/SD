class BankTest {
    public static void main(String[] args) throws InterruptedException {
        final int N=10;

        Bank b = new Bank(N);

        for (int i=0; i<N; i++)
            b.deposit(i,1000);

        System.out.println(b.totalBalance());

        Thread t1 = new Thread(new Mover(b,10));
        Thread t2 = new Thread(new Mover(b,10));

        t1.start(); t2.start(); t1.join(); t2.join();

        System.out.println(b.totalBalance());
    }
}