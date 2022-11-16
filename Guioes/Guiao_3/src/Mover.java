import java.util.Random;

public class Mover implements Runnable{

    Bank b;
    int s; //Number of accounts;

    public Mover(Bank b, int s){
        this.b = b;
        this.s = s;
    }

    public void run(){
        final int moves = 10;
        int from, to;
        Random rand = new Random();

        for(int i = 0; i < moves; i++){
            from = rand.nextInt(s); //Get One
            while((to = rand.nextInt(s)) == from); //Slow way to get distinct
            boolean transfer = b.transfer(from, to, 10);
            if(transfer)
                System.out.println("Transfer " + 10 + " value between " + from + " account  and " + to + " account.");
            else
                System.out.println("Transfer " + 10 + " value between " + from + " account  and " + to + " account not successfully.");
        }

    }
}
