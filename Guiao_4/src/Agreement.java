import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Agreement {

    private final int limite;
    private int n_Threads = 0;

    private final Lock l = new ReentrantLock();
    private final Condition c = l.newCondition();

    private static class Stage{
        int max = Integer.MIN_VALUE;
    }

    Stage stage = new Stage();

    public Agreement(int N){
        this.limite = N;
    }

    public int propose(int choice) throws InterruptedException{
        l.lock();
        try{
            Stage stage = this.stage;
            this.n_Threads++;
            stage.max = Math.max(stage.max, choice);
            if(this.n_Threads < this.limite){
                while(stage == this.stage) {
                    c.await();
                }
            }else{
                c.signalAll();
                this.n_Threads = 0;
                this.stage = new Stage();
            }
            return stage.max;
        }finally {
            l.unlock();
        }
    }
}
