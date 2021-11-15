import java.util.Random;

public class Agreement_Teste {

    public static void main(String[] args) throws InterruptedException {

        Agreement b = new Agreement(5);
        Thread[] t = new Thread[10];

        Random rand = new Random();

        for(int i = 0; i < 10; i++) {
            t[i] = new Thread(new RunAgreement(b));
        }
        for(int i = 0; i < 10; i++)
            t[i].start();

        for(int i = 0; i < 10; i++)
            t[i].join();

    }
}
