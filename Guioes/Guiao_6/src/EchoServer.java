import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {

    public static void main(String[] args) throws IOException{
        ServerSocket ss = new ServerSocket(12345);
        Somador s = new Somador();
        Aritmetica a = new Aritmetica();

        while(true){
            Socket sock = ss.accept();
            System.out.println("Clinte estabeleceu Ligaçao!!");
            //Thread client = new Thread(new EchoServerRun(sock, new Somador())); //Estado unico a cada Cliente
            //Thread client = new Thread(new EchoServerRun(sock, s));             //Estado partilhado por todos os clientes
            Thread client = new Thread(new EchoServerRun(sock, a));
            client.start();
        }

    }
}

