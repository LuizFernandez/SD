import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Somador {

    //Variaveis de Instancia
    private int n;
    private int acc;

    private final Lock l = new ReentrantLock();

    public Somador(){
        this.n = 0;
        this.acc = 0;
    }

    public void add(int number){
        try{
            l.lock();
            this.n++;
            this.acc += number;
        }finally {
            l.unlock();
        }
    }

    public int mean(){
        try{
            l.lock();
            return this.acc / this.n;
        }finally {
            l.unlock();
        }
    }
}
