import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Aritmetica {

    private int acc;
    private int numbs;
    private Lock l = new ReentrantLock();

    public Aritmetica(){
        this.acc = 0;
        this.numbs = 0;
    }

    public void operator() {

        System.out.println("-------------Menu-------------");
        System.out.println("  (1) => Soma                 ");
        System.out.println("  (2) => Multiplicação        ");
        System.out.println("  (3) => Divisão              ");
        System.out.println("  (-1) => Sair                ");
        System.out.println("------------------------------");

    }

    public int choice(int op, int numb){
        int r = 0;
        if(op == 1){
            r = add(numb);
        } else if(op == 2){
            r = mul(numb);
        } else if(op == 3){
            r = div(numb);
        }

        return r;
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

    public int mul(int numb){
        l.lock();
        this.acc++;
        this.numbs *= numb;
        try{
            return this.numbs;
        } finally {
            l.unlock();
        }
    }

    public int div(int numb){
        l.lock();
        this.acc++;
        this.numbs /= numb;
        try{
            return this.numbs;
        } finally {
            l.unlock();
        }
    }

    public int media(){
        l.lock();
        int media = this.numbs / this.acc;
        try{
            return media;
        } finally {
            l.unlock();
        }
    }
}
