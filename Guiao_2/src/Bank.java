import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Bank {
    private static class Account {
        public Lock lock = new ReentrantLock();
        private int balance;
        Account(int balance) { this.balance = balance; }
        int balance() { return balance; }
        boolean deposit(int value) {
            balance += value;
            return true;
        }
        boolean withdraw(int value) {
            if (value > balance)
                return false;
            balance -= value;
            return true;

        }
    }

    // Bank slots and vector of accounts
    private int slots;
    private Account[] av;

    //private Lock lock = new ReentrantLock(); //Uma Theard com este tipo de lock pode "acumular" locks

    public Bank(int n)
    {
        slots=n;
        av=new Account[slots];
        for (int i=0; i<slots; i++) av[i]=new Account(0);
    }

    // Account balance
    public int balance(int id) {
        if (id < 0 || id >= slots)
            return 0;
        try{
            this.av[id].lock.lock();
            return av[id].balance();
        } finally {
            this.av[id].lock.unlock();
        }
    }

    // Deposit
    boolean deposit(int id, int value) {
        if (id < 0 || id >= slots)
            return false;
        try{
            this.av[id].lock.lock();
            return av[id].deposit(value);
        } finally {
            this.av[id].lock.unlock();
        }
    }

    // Withdraw; fails if no such account or insufficient balance
    public boolean withdraw(int id, int value) {
        if (id < 0 || id >= slots)
            return false;
        try{
            this.av[id].lock.lock();
            return av[id].withdraw(value);
        } finally {
            this.av[id].lock.unlock();
        }
    }

    //Tranfer;operações de levantamento e depósito de um valor sobre duas conta
    public boolean transfer (int from, int to, int value) {
        int MinID = Math.min(from, to);
        int MaxID = Math.max(from,to);
        try {
            this.av[MinID].lock.lock();
            this.av[MaxID].lock.lock();
            if (this.withdraw(from, value)) {
                this.deposit(to, value);
                return true;
            } else
                return false;
        } finally {
            this.av[MinID].lock.unlock();
            this.av[MaxID].lock.unlock();
        }
    }

    //Total Balance
    public int totalBalance(){
        int total = 0;

        for(int i = 0; i < this.slots; i++)
            this.av[i].lock.lock();
        for(int i = 0; i < this.slots; i++) {
            total += this.balance(i);
            this.av[i].lock.unlock();
        }

        return total;
    }
}
