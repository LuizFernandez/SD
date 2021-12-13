import java.io.*;
import java.net.Socket;
import java.util.concurrent.locks.ReentrantLock;

public class FramedConnection implements AutoCloseable{
    private final Socket s;
    private final DataInputStream dis;
    private final DataOutputStream dos;
    private final ReentrantLock wl = new ReentrantLock();
    private final ReentrantLock rl = new ReentrantLock();

    public FramedConnection(Socket socket) throws IOException {
        this.s = socket;
        this.dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        this.dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
    }

    public void send(byte[] data) throws IOException {
        try{
            wl.lock();
            dos.writeInt(data.length);//Delimitar o tamanho de leitura para não ler a mais nem a menos do socket
            dos.write(data);
            dos.flush();
        }finally {
            wl.unlock();
        }

    }

    public byte[] receive() throws IOException {
        try{
            rl.lock();
            int size = dis.readInt();
            byte[] data = new byte[size];
            dis.readFully(data);
            return data;
        } finally {
            rl.unlock();
        }
    }

    public void close() throws IOException {
        s.close();
    }
}
