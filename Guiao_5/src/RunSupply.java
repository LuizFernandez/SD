public class RunSupply implements Runnable{

    //private Warehouse h;
    private WarehouseCoop h;

    public RunSupply(WarehouseCoop h){
        this.h = h;
    }

    public void run(){
        String[] list = {"Leite", "Ovo", "Chocolate", "Manteiga", "Ovo", "Farinha"};
        for (String s : list) {
            this.h.supply(s, 1);
            System.out.println("Supply sucedido de " + s);
        }
    }
}
