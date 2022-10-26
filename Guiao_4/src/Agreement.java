import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Agreement {

    private final int limite;
    private int n_Threads = 0;
    private int epoca;
    private int value = Integer.MIN_VALUE;

    private final Lock l = new ReentrantLock();
    private final Condition c = l.newCondition();

    public Agreement(int N){
        this.limite = N;
        this.epoca = 0;
    }
    
    public int propose(int choice) throws InterruptedException{
        l.lock();
        try{
            int epoca = this.epoca;
            this.n_Threads++;
            int value;
            if(this.n_Threads < this.limite){
                if(this.value < choice)
                    this.value = choice;
                while(this.epoca == epoca) {
                    System.out.println(Thread.currentThread().getName() + " a espera do fim da epoca " + this.epoca + "!!");
                    c.await();
                    System.out.println(Thread.currentThread().getName() + " fim da epoca!!");
                }
                value = this.value;
            }else{
                System.out.println(Thread.currentThread().getName() + " sou o fim da epoca!");
                this.epoca++;
                this.n_Threads = 0;
                value = this.value;
                c.signalAll();
            }
            return value;
        }finally {
            l.unlock();
        }
    }
}
