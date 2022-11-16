import java.util.Random;

public class RunSupply implements Runnable{

    //private Warehouse h;
    //private WarehouseCoop h;
    private WarehouseFair h;

    public RunSupply(WarehouseFair h){
        this.h = h;
    }

    public void run(){
        Random rand = new Random();
        String[] list = {"Leite", "Ovo", "Farinha", "Manteiga", "Chocolate", "Ovo", "Farinha", "Farinha"};
        int quantidade = rand.nextInt(10);
        for(String s : list){
            h.supply(s, quantidade);
            System.out.println(Thread.currentThread().getName() + " supply " + s + " with more " + quantidade + " quantities");
        }
    }
}
