public class WarehouseTest {
    public static void main(String[] args) throws InterruptedException{

        //Warehouse w = new Warehouse();
        //WarehouseCoop w = new WarehouseCoop();
        WarehouseFair w = new WarehouseFair();

        int N = 5;
        Thread[] clients = new Thread[N];

        for(int i = 0; i < N; i++){
            clients[i] = new Thread(new RunClient(w));
            clients[i].setName("Client #" + (i+1));
        }

        Thread sp1 = new Thread(new RunSupply(w));
        sp1.setName("Supply #1");
        Thread sp2 = new Thread(new RunSupply(w));
        sp2.setName("Supply #2");

        for(int i = 0; i < N; i++){
            clients[i].start();
        }

        sp1.start();
        sp2.start();

        for(int i = 0; i < N; i++){
            clients[i].join();
        }

        sp1.join();
        sp2.join();
    }
}