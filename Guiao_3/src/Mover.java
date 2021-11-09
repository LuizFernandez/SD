import java.util.Random;

public class Mover implements Runnable {
    private Bank b;
    private int s; // Number of accounts

    public Mover(Bank b, int s) { this.b=b; this.s=s; }

    public void run() {
        final int moves=10;
        int from, to;
        Random rand = new Random();

        for (int m=0; m<moves; m++)
        {
            from=rand.nextInt(s); // Get one
            while ((to=rand.nextInt(s))==from); // Slow way to get distinct
            boolean tranfer = b.transfer(from,to,1);
            if(tranfer)
                System.out.println("Transfer " + 1 + " value between " + from + " account  and " + to + " account.");
            else
                System.out.println("Transfer " + 1 + " value between " + from + " account  and " + to + " account not successfully.");
        }
    }
}