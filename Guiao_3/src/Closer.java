import java.util.Random;

public class Closer implements  Runnable{

    Bank b;

    public Closer(Bank b){
        this.b = b;
    }

    public void run(){
        Random rand = new Random();
        int id = rand.nextInt(b.getNextId());

        int balance = b.closeAccount(id);
        if(balance != 0)
            System.out.println("Conta " + id + " fechada com sucesso! Continha uma quantia de " + balance);
        else
            System.out.println("Conta " + id + " ja fechada ou nunca existiu");
    }
}
