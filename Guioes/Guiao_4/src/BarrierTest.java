public class BarrierTest {

    public static void main(String[] args) throws InterruptedException{

        int N = 4;

        Barrier b = new Barrier(N);
        Thread[] t = new Thread[N];

        for(int i = 0; i < N; i++){
            t[i] = new Thread(new RunBarrier(b));
            t[i].setName("Thread #" + (i+1) );
            t[i].start();
        }

        for(int i = 0; i < N; i++){
            t[i].join();
        }
    }
}