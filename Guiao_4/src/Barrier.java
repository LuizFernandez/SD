
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Barrier {

    private int limite = 0;
    private int n_Therds;
    private int epoca;

    private Lock l = new ReentrantLock();
    Condition c = l.newCondition();

    public Barrier (int N) {
        this.limite = N;
        epoca = 0;
    }

    public void await() throws InterruptedException {
        l.lock();
        try {
            int epoca = this.epoca;
            this.n_Therds++;
            if (this.n_Therds < this.limite) {
                while (epoca == this.epoca) {
                    System.out.println(Thread.currentThread().getName() + " à espera do fim da " + epoca + "ª época!!");
                    c.await();
                    System.out.println(Thread.currentThread().getName() + " fim da época!");
                }
            } else {
                System.out.println(Thread.currentThread().getName() + " sou o fim da época!");
                this.epoca++;
                this.n_Therds = 0;
                c.signalAll();
            }
        } finally {
            l.unlock();
        }
    }
}