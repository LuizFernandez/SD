import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class RunCliente implements Runnable{

    //private Warehouse h;
    //private WarehouseCoop h;
    private WareHouseFair h;

    public RunCliente(WareHouseFair h){
        this.h = h;
    }

    public void run(){
        String[] list = {"Ovo", "Ovo", "Leite", "Farinha", "Chocolate", "Manteiga"};
        try{
            h.consume(list);
            System.out.println("Compra concluida do " + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
