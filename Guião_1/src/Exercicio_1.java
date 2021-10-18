public class Exercicio_1 {

    public static void main(String[] args) throws InterruptedException {

        int N = 10;

        Thread[] thread = new Thread[N];
        Increment inc = new Increment();

        for(int i = 0; i < N; i++)
            thread[i] = new Thread(inc);

        for(int i = 0; i < N; i++)
            thread[i].start();
        for(int i = 0; i < N; i++)
            thread[i].join();

        System.out.println("Fim!!");
    }
}
