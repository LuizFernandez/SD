public class Creator implements Runnable{

    private Bank b;
    private int n_account;

    public Creator(Bank b, int n_account){
        this.b = b;
        this.n_account = n_account;
    }

    public void run(){

        final int balance = 1000;

        for(int i = 0; i < this.n_account; i++){
            b.createAccount(balance);
        }
    }
}
