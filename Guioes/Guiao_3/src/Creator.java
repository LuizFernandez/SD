public class Creator implements Runnable{

    Bank b;
    int n_account;

    public Creator(Bank b, int n_account){
        this.b = b;
        this.n_account = n_account;
    }

    public void run() {
        final int balance = 1000;
        int id;

        for (int i = 0; i < n_account; i++){
            id = b.createAccount(balance);
            System.out.println("Conta criada. ID => " + id);
        }
    }
}
