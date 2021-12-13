import java.io.*;
import java.net.Socket;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TaggedConnection implements AutoCloseable{
    private final Socket s;
    private final DataInputStream dis;
    private final DataOutputStream dos;
    private final Lock wl = new ReentrantLock();
    private final Lock rl = new ReentrantLock();

    public static class Frame {
        public final int tag;
        public final byte[] data;
        public Frame(int tag, byte[] data) {
            this.tag = tag;
            this.data = data;
        }
    }

    public TaggedConnection(Socket socket) throws IOException{
        this.s = socket;
        this.dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        this.dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
    }

    public void send(Frame frame) throws IOException {
        send(frame.tag, frame.data);
    }

    public void send(int tag, byte[] data) throws IOException {
        try{
            wl.lock();
            dos.writeInt(4 + data.length);
            dos.writeInt(tag);
            dos.write(data);
            dos.flush();
        } finally {
            wl.unlock();
        }
    }

    public Frame receive() throws IOException {
        try{
            rl.lock();
            byte[] data = new byte[(dis.readInt() - 4)];
            int tag = dis.readInt();
            dis.readFully(data);
            return new Frame(tag, data);
        }finally {
            rl.unlock();
        }
    }

    public void close() throws IOException {
        s.close();
    }
}
