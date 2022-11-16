import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class EchoServerRun implements Runnable{

    private Socket sock;
    //private Somador s;
    private Aritmetica a_global;
    private Aritmetica a_propria;

    public EchoServerRun(Socket sock, /*Somador s*/Aritmetica a){
        this.sock = sock;
        //this.s = s;
        this.a_global = a;
        this.a_propria = new Aritmetica();
    }

    public void run(){
        /*Exercicios Propostos
        try{

            BufferedReader in = new BufferedReader(new InputStreamReader(this.sock.getInputStream()));
            PrintWriter out = new PrintWriter(this.sock.getOutputStream());

            String line;
            int soma = 0;
            while((line = in.readLine()) != null){
                int numb = Integer.parseInt(line);
                this.s.add(numb);
                soma += numb;
                out.println(soma);
                out.flush();

                System.out.println("Client Message: " + numb);
            }

            int mean = this.s.mean();
            out.println(mean);
            out.flush();

            sock.shutdownOutput();
            sock.shutdownInput();
            sock.close();

        }catch(IOException e){
            e.printStackTrace();
        }*/
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(this.sock.getInputStream()));
            PrintWriter out = new PrintWriter(this.sock.getOutputStream());

            String line;
            while((line = in.readLine()) != null){
                int op = Integer.parseInt(line);
                if((line = in.readLine()) != null){
                    int numb = Integer.parseInt(line);
                    this.a_global.choice(op, numb);
                    out.println(this.a_propria.choice(op, numb));
                    out.flush();
                    System.out.println("Client Message: " + numb);
                }
            }

            out.println(this.a_global.mean());
            out.flush();

            sock.shutdownOutput();
            sock.shutdownInput();
            sock.close();

        }catch(IOException e){
            e.printStackTrace();
        }
    }

}
