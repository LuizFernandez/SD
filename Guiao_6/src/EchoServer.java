import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;



public class EchoServer {

    public static void main(String[] args) throws IOException {
            ServerSocket ss = new ServerSocket(12345);
            SomaNumb soma = new SomaNumb();// Exercicio 4
            Aritmetica a = new Aritmetica();

            while (true) {
                Socket socket = ss.accept();
                Thread t = new Thread(new EchoServerRun(socket, soma, a));
                t.start();
            }
    }
}