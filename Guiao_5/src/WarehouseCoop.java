import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class WarehouseCoop {

    private Map<String, Product> map =  new HashMap<String, Product>();
    private Lock l = new ReentrantLock();
    private Condition c = l.newCondition();
    private int turn = 0;
    private int ticket = 0;

    private class Product {
        Condition isEmpty = l.newCondition();
        int quantity = 0;
    }

    private Product get(String item) {
        Product p;
        p = map.get(item);
        if (p != null) return p;
        p = new Product();
        map.put(item, p);
        return p;
    }

    public void supply(String item, int quantity) {
        l.lock();
        Product p = get(item);
        p.quantity += quantity;
        p.isEmpty.signalAll();
        l.unlock();
    }

    public void consume(String[] items) throws InterruptedException{
        l.lock();
        int ticket = this.ticket++;
        for(int i = 0; i < items.length; i++){
            Product p = get(items[i]);

            while(p.quantity == 0){
                p.isEmpty.await();
                i = 0;
            }
        }
        while(ticket > this.turn)
            c.await();
        this.turn++;
        c.signalAll();
        for (String s : items){
            Product p = get(s);
            p.quantity--;
        }
        l.unlock();
    }
}
