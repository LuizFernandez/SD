import java.util.Random;

public class Closer implements Runnable{

    private Bank b;


    public Closer(Bank b){
        this.b = b;
    }

    public void run(){
        Random rand = new Random();
        int id = rand.nextInt(b.getNextId());

        int balance = b.closeAccount(id);
        System.out.println("Account #" + id + " closed! => Balance " + balance);
    }
}
