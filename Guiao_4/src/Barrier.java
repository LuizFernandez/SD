import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Barrier {

    private final int limite;
    private int n_Threads = 0;
    private int epoca;

    private final Lock l = new ReentrantLock();
    private final Condition c = l.newCondition();

    public Barrier(int N){
        this.limite = N;
        this.epoca = 0;
    }

    public void await() throws InterruptedException{
        l.lock();
        try {
            int epoca = this.epoca;
            this.n_Threads++;
            if (this.n_Threads < this.limite) {
                while (epoca == this.epoca) {
                    System.out.println(Thread.currentThread().getName() + " a espera do fim da " + epoca + " epoca!");
                    c.await();
                    System.out.println(Thread.currentThread().getName() + " fim da epoca!");
                }
            } else {
                System.out.println(Thread.currentThread().getName() + " sou o fim da epoca!");
                this.epoca++;
                this.n_Threads = 0;
                c.signalAll();
            }
        }finally{
                l.unlock();
        }
    }
}

