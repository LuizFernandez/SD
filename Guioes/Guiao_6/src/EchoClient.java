import java.io.*;
import java.net.Socket;

public class EchoClient {

    public static void main(String[] args){
        Aritmetica a = new Aritmetica();
        try{
            Socket sock = new Socket("localhost", 12345);

            BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            PrintWriter out = new PrintWriter(sock.getOutputStream());

            BufferedReader systemin = new BufferedReader(new InputStreamReader(System.in));

            String userInput;
            boolean out_app;
            int op;
            do{
                System.out.printf(a.menu());
                System.out.print("Operador: ");

                userInput = systemin.readLine();
                out.println(userInput);
                out.flush();

                op = Integer.parseInt(userInput);
                out_app = (op == -1);

                if(!out_app) {

                    System.out.println("");
                    System.out.print("Numero: ");
                    userInput = systemin.readLine();
                    out.println(userInput);
                    out.flush();

                    System.out.println("");

                    String response = in.readLine();
                    System.out.println("Server response: " + response);
                }

            } while(!out_app);


            //Termina o ciclo no servidor pois nao quer enviar mais nenhum input para ele
            sock.shutdownOutput();

            /*Mensagem final do Servidor*/
            String response = in.readLine();
            System.out.println("Server response: " + response);

            sock.shutdownInput();
            sock.close();

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
