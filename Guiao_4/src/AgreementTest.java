public class AgreementTest {

    public static void main(String[] args) throws InterruptedException{

        int N = 4;

        Agreement a = new Agreement(N);
        Thread[] t = new Thread[N];

        for(int i = 0; i < N; i++){
            t[i] = new Thread(new RunAgreement(a));
            t[i].setName("Thread #" + (i+1));
            t[i].start();
        }

        for(int i = 0; i < N; i++){
            t[i].join();
        }
    }
}
