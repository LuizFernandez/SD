import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Warehouse {
    private Map<String, Product> map =  new HashMap<String, Product>();
    private Lock l = new ReentrantLock();

    private class Product {
        Condition c = l.newCondition();
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
        p.c.signalAll();
        l.unlock();
    }

    public void consume(ArrayList<String> items) throws InterruptedException{
        l.lock();
        for (String s : items){
            Product p = get(s);
            while(p.quantity == 0)
                p.c.await();
            p.quantity--;
        }
        l.unlock();
    }

}