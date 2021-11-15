public class Warehouse_Teste {

    public static void main(String[] args) throws InterruptedException {
        /*
        Warehouse h = new Warehouse();

        Thread cliente1 = new Thread(new RunCliente(h));
        cliente1.setName("Cliente #1");
        Thread cliente2 = new Thread(new RunCliente(h));
        cliente2.setName("Cliente #2");

        Thread t1 = new Thread(new RunSupply(h));
        t1.setName("Supply #1");
        Thread t2 = new Thread(new RunSupply(h));
        t2.setName("Supply #2");
        */

        WarehouseCoop h = new WarehouseCoop();

        Thread cliente1 = new Thread(new RunCliente(h));
        cliente1.setName("Cliente #1");
        Thread cliente2 = new Thread(new RunCliente(h));
        cliente2.setName("Cliente #2");

        Thread t1 = new Thread(new RunSupply(h));
        t1.setName("Supply #1");
        Thread t2 = new Thread(new RunSupply(h));
        t2.setName("Supply #2");

        t1.start();cliente1.start();cliente2.start();t2.start();

        t1.join();t2.join();cliente1.join();cliente2.join();


    }



}
