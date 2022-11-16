import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Aritmetica {

    //Variaveis de Instancia
    private int n;
    private int acc;

    private final Lock l = new ReentrantLock();

    public Aritmetica(){
        this.n = 0;
        this.acc = 0;
    }

    public String menu(){

        StringBuffer sb = new StringBuffer();
        sb.append("-------------Menu-------------\n");
        sb.append("  (1) => Soma                 \n");
        sb.append("  (2) => Subtração            \n");
        sb.append("  (3) => Multiplicação        \n");
        sb.append("  (4) => Divisão              \n");
        sb.append("  (-1)=> Sair                 \n");
        sb.append("------------------------------\n");

        return sb.toString();
    }

    public int choice(int op, int numb){
        int r;
        switch (op) {
            case 1:
                r = this.add(numb);
                break;
            case 2:
                r = this.sub(numb);
                break;
            case 3:
                r = this.product(numb);
                break;
            case 4:
                r = this.div(numb);
                break;
            default:
                r = this.acc;
                break;
        };

        return r;
    }

    private int add(int numb){
        try{
            l.lock();
            this.acc += numb;
            this.n++;
            return this.acc;
        }finally {
            l.unlock();
        }
    }

    private int sub(int numb){
        try{
            l.lock();
            this.acc -= numb;
            this.n++;
            return this.acc;
        }finally {
            l.unlock();
        }
    }

    private int product(int numb){
        try{
            l.lock();
            this.acc *= numb;
            this.n++;
            return this.acc;
        }finally {
            l.unlock();
        }
    }

    private int div(int numb){
        try{
            l.lock();
            this.acc /= numb;
            this.n++;
            return this.acc;
        }finally {
            l.unlock();
        }
    }

    public int mean(){
        try{
            l.lock();
            return this.acc / this.n;
        } finally {
            l.unlock();
        }
    }


}
