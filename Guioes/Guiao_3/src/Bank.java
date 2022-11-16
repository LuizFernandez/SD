import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Bank {
    private static class Account {
        private int balance;
        private final Lock l = new ReentrantLock();
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

    private final Map<Integer, Account> map = new HashMap<Integer, Account>();
    private final ReentrantReadWriteLock l = new ReentrantReadWriteLock();
    private final Lock wl = l.writeLock();
    private final Lock rl = l.readLock();
    private int nextId = 0;

    public int getNextId(){
        return this.nextId;
    }

    // create account and return account id
    public int createAccount(int balance) {
        Account c = new Account(balance);
        int id;
        wl.lock();
        id = nextId;
        nextId += 1;
        map.put(id, c);
        wl.unlock();
        return id;
    }

    // close account and return balance, or 0 if no such account
    public int closeAccount(int id) {
        Account c;
        wl.lock();
        c = map.remove(id);
        if (c == null) {
            wl.unlock();
            return 0;
        }
        try {
            c.l.lock();
            wl.unlock();
            return c.balance();
        }finally {
            c.l.unlock();
        }
    }

    // account balance; 0 if no such account
    public int balance(int id) {
        Account c;
        rl.lock();
        c = map.get(id);
        if (c == null) {
            rl.unlock();
            return 0;
        }
        try{
            c.l.lock();
            rl.unlock();
            return c.balance();
        }finally {
            c.l.unlock();
        }
    }

    // deposit; fails if no such account
    public boolean deposit(int id, int value) {
        Account c;
        rl.lock();
        c = map.get(id);
        if (c == null) {
            rl.unlock();
            return false;
        }
        try{
            c.l.lock();
            rl.unlock();
            return c.deposit(value);
        }finally {
            c.l.unlock();
        }
    }

    // withdraw; fails if no such account or insufficient balance
    public boolean withdraw(int id, int value) {
        Account c;
        rl.lock();
        c = map.get(id);
        if (c == null) {
            rl.unlock();
            return false;
        }
        try{
            c.l.lock();
            rl.unlock();
            return c.withdraw(value);
        }finally {
            c.l.unlock();
        }
    }

    // transfer value between accounts;
    // fails if either account does not exist or insufficient balance
    public boolean transfer(int from, int to, int value) {
        Account cfrom, cto;
        rl.lock();
        cfrom = map.get(from);
        cto = map.get(to);
        if (cfrom == null || cto ==  null) {
            rl.unlock();
            return false;
        }
        try{
            if(from > to){
                cfrom.l.lock();
                cto.l.lock();
            }else{
                cto.l.lock();
                cfrom.l.lock();
            }
            rl.unlock();
            try {
                boolean b = cfrom.withdraw(value);
                if (!b) return false;
            } finally {
                cfrom.l.unlock();
            }
            return cto.deposit(value);
        }finally {
            cto.l.unlock();
        }

    }

    // sum of balances in set of accounts; 0 if some does not exist
    public int totalBalance(int[] ids) {
        Account[] accounts = new Account[ids.length];
        try {
            rl.lock();
            for (int i : ids) {
                accounts[i] = map.get(i);
                if (accounts[i] != null) {
                    accounts[i].l.lock();
                }
            }
        }finally {
            rl.unlock();
        }
        int total = 0;
        for(Account c : accounts){
            if(c != null) {
                total += c.balance();
                c.l.unlock();
            }
        }
        return total;
    }
}
