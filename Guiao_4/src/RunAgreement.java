import java.util.Random;

public class RunAgreement implements Runnable{

    private Agreement a;
    private Random rnd;
    private int maxNumber;

    public RunAgreement(Agreement a) {
        this.a = a;
        this.rnd = new Random();
        this.maxNumber = 1000;
    }

    public void run() {
        int n = rnd.nextInt(maxNumber);
        try {
            System.out.println(a.propose(n));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
