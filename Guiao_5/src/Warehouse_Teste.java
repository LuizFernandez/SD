public class Warehouse_Teste {

    public static void main(String[] args) throws InterruptedException {

        /* Alinea A
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

        //Aliena B e Exercicio 2
        //WarehouseCoop h = new WarehouseCoop();

        //Exercicio Extra

        WareHouseFair h = new WareHouseFair();

        Thread[] clientes = new Thread[10];
        Thread[] trabalhadores = new Thread[10];

        for(int i = 0 ; i < 10; i++){
            clientes[i] = new Thread(new RunCliente(h));
            clientes[i].setName("Cliente #"+(i+1));
            trabalhadores[i] = new Thread(new RunSupply(h));
        }

        for(int i = 0; i < 10; i++){
            clientes[i].start();
            trabalhadores[i].start();
        }

        for(int i = 0;i < 10; i++){
            clientes[i].join();
            trabalhadores[i].join();
        }


    }



}
