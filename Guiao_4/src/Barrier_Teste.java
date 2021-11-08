public class Barrier_Teste {

    public static void main(String[] args) throws InterruptedException {

        Barrier b = new Barrier(5);
        Thread[] t = new Thread[10];

        for(int i = 0; i < 10; i++) {
            t[i] = new Thread(new RunBarrier(b));
            t[i].setName("Theard #" + i);
        }
        for(int i = 0; i < 10; i++)
            t[i].start();

        for(int i = 0; i < 10; i++)
            t[i].join();

    }
}
