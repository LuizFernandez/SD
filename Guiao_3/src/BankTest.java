
public class BankTest {
    public static void main(String[] args) throws InterruptedException {
        final int N=10;

        Bank b = new Bank();

        Thread tc1 = new Thread(new Creator(b,5));
        Thread tc2 = new Thread(new Creator(b,7));

        tc1.start(); tc2.start();tc1.join(); tc2.join();

        int[] ids = new int[b.getNextId()];

        Thread tcl1 = new Thread(new Closer(b));
        Thread tcl2 = new Thread(new Closer(b));

        for(int i = 0; i < b.getNextId(); i++)
            ids[i] = i;

        System.out.println(b.totalBalance(ids));

        tcl1.start();tcl2.start();

        Thread t1 = new Thread(new Mover(b,10));
        Thread t2 = new Thread(new Mover(b,10));

        t1.start(); t2.start();
        tcl1.join();tcl2.join();

        t1.join(); t2.join();

        System.out.println(b.totalBalance(ids));
    }
}
