import java.io.IOException;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Demultiplexer implements AutoCloseable{

    private final TaggedConnection conn;
    private final Lock l = new ReentrantLock();
    private final Map<Integer, Entry> buffer = new HashMap<>();
    private IOException exception = null;

    private class Entry{
        int waiters = 0;
        Queue<byte[]> queue = new ArrayDeque<>();
        Condition notEmpty = l.newCondition();
    }

    private Entry get(int tag){
        Entry entry = buffer.get(tag);
        if(entry == null){
            entry = new Entry();
            buffer.put(tag,entry);
        }
        return entry;
    }

    public Demultiplexer(TaggedConnection conn) {
        this.conn = conn;
    }

    public void start() {
        new Thread(() -> {
                try{
                    while(true){
                        TaggedConnection.Frame frame = conn.receive();
                        l.lock();
                        try{
                            Entry entry = get(frame.tag);
                            entry.queue.add(frame.data);
                            entry.notEmpty.signalAll();
                        } finally {
                            l.unlock();
                        }
                    }
                } catch (IOException e){//Caso ocorra um erro(exceção), para o programa não "morrer" acordar as threads adormecidas
                    l.lock();
                    try{
                        exception = e;
                        buffer.forEach((k,v) -> v.notEmpty.signalAll());
                    } finally {
                        l.unlock();
                    }
                }
        }).start();
    }

    public void send(TaggedConnection.Frame frame) throws IOException {
            this.conn.send(frame);
    }

    public void send(int tag, byte[] data) throws IOException {
            this.conn.send(tag,data);
    }

    public byte[] receive(int tag) throws IOException, InterruptedException {
            l.lock();
            Entry entry;
            try{
                entry = get(tag);
                entry.waiters++;
                while(true){
                    if(!entry.queue.isEmpty()){
                        entry.waiters--;
                        byte[] data = entry.queue.poll();
                        if(entry.waiters == 0 && entry.queue.isEmpty())
                            buffer.remove(tag);
                        return data;
                    }
                    if(exception != null)//Se foi acordada por um erro, lançar a exceção
                        throw exception;
                    entry.notEmpty.await();
                }
            }finally {
                l.unlock();
            }
    }

    public void close() throws IOException {
        conn.close();
    }
}
