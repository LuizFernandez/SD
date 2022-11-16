import java.util.Arrays;
import java.util.stream.Collectors;

public class RunClient implements Runnable{

    //private Warehouse h;
    //private WarehouseCoop h;
    private WarehouseFair h;

    public RunClient(WarehouseFair h){
        this.h = h;
    }

    public void run(){
        String[] list = {"Ovo", "Ovo", "Farinha", "Leite", "Ovo", "Chocolate", "Farinha", "Manteiga"};
        try{
            //h.consume(Arrays.stream(list).collect(Collectors.toSet()));
            h.consume(list);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
