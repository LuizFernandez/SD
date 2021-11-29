import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class EchoServerRun implements Runnable{

    private Socket s;
    //private SomaNumb soma;
    private Aritmetica a;

    public EchoServerRun(Socket s, /*SomaNumb soma,*/ Aritmetica a){
        this.s = s;
        //this.soma  = soma;
        this.a = a;
    }

    /* Exercicio 4
    public void run() {
        try {

            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter out = new PrintWriter(s.getOutputStream());

            String line;
            int soma = 0;
            while ((line = in.readLine()) != null) {
                int num = Integer.parseInt(line);
                soma += num;
                this.soma.add(num);
                out.println(soma);
                out.flush();

                System.out.println("Client message: " + num);
            }

            out.println(this.soma.media());
            out.flush();

            s.shutdownOutput();
            s.shutdownInput();
            s.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    */

    public void run() {//ESTA INCOMPLETO => EM VEZ DE IMPRIMIR NO CLIENTE O VALOR TOTAL GLOBAL, IMPRIMIR O VALOR TOTAL DO CLIENTE
        try {

            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter out = new PrintWriter(s.getOutputStream());

            String line;
            boolean valid = true;
            while ((line = in.readLine()) != null && valid) {
                int op = Integer.parseInt(line);
                if((line = in.readLine()) != null) {
                    int num = Integer.parseInt(line);
                    int resultado = this.a.choice(op, num);
                    out.println(resultado);
                    out.flush();
                    System.out.println("Client message: " + num);
                }
                else valid = false;
            }

            out.println(this.a.media());
            out.flush();


            s.shutdownOutput();
            s.shutdownInput();
            s.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
