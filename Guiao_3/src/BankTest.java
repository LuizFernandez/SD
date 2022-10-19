public class BankTest {
    public static void main(String[] args) throws InterruptedException {

        final int N=10;

        Bank b = new Bank();

        Thread[] thcreate = new Thread[N];

        for(int i = 0; i < N; i++){
            thcreate[i] = new Thread(new Creator(b, 4));
            thcreate[i].start();
        }

        for(int i = 0; i < N; i++){
            thcreate[i].join();
        }

        int nextID = b.getNextId();
        System.out.println("No Banco existem " + nextID + " contas!!");

        Thread[] thclose = new Thread[N];
        Thread[] thMove = new Thread[N-3];

        for(int i = 0; i < (N-3); i++){
            thMove[i] = new Thread(new Mover(b, nextID));
            thMove[i].start();
        }

        for(int i = 0; i < N; i++){
            thclose[i] = new Thread(new Closer(b));
            thclose[i].start();
        }

        for(int i = 0; i < N; i++){
            thclose[i].join();
        }

        for(int i = 0; i < (N-3); i++){
            thMove[i].join();
        }

        int[] ids = new int[b.getNextId()];

        for(int i = 0; i < b.getNextId(); i++)
            ids[i] = i;

        System.out.println(b.totalBalance(ids));

    }
}