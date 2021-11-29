import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SomaNumb {//Exercicio 4

    private int acc;
    private int numbs;
    private final Lock l = new ReentrantLock();

    public SomaNumb(){
        this.acc = 0;
        this.numbs = 0;
    }

    public int add(int numb){
        l.lock();
        this.acc++;
        this.numbs += numb;
        try{
            return this.numbs;
        } finally {
            l.unlock();
        }
    }

    public int media(){
        l.lock();
        try{
            if(this.acc < 1)
                return 0;
            else {
                return this.numbs / this.acc;
            }
        } finally {
            l.unlock();
        }
    }
}
