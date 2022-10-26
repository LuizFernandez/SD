import java.util.Random;

public class RunAgreement implements Runnable{

    private final Agreement a;

    public RunAgreement(Agreement a){
        this.a = a;
    }

    public void run(){
        Random rand = new Random();
        int i = 0;
        try{
            int vezes = 10;
            while(i < vezes){
                System.out.println(Thread.currentThread().getName() + " valor maximo proposto por todas as threads " + a.propose(rand.nextInt(100)) + " !");
                i++;
                this.a.setValue();
            }
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
