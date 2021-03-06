
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Bank {
    private static class Account {
        public Lock l = new ReentrantLock();
        private int balance;
        Account(int balance) { this.balance = balance; }
        int balance() {
            return balance;
        }
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

    private Map<Integer, Account> map = new HashMap<Integer, Account>();
    private int nextId = 0;

    private ReentrantReadWriteLock l = new ReentrantReadWriteLock();
    private Lock rl = l.readLock();
    private Lock wl = l.writeLock();

    public int getNextId(){
        return this.nextId;
    }

    public Map<Integer, Account> getmap(){
        return map;
    }

    // create account and return account id
    public int createAccount(int balance) {
        Account c = new Account(balance);
        wl.lock();
        int id = nextId;
        nextId += 1;
        map.put(id, c);
        wl.unlock();
        return id;
    }

    // close account and return balance, or 0 if no such account
    public int closeAccount(int id) {
        wl.lock();
        Account c = map.remove(id);
        if (c == null) {
            wl.unlock();
            return 0;
        }
        try{
            c.l.lock();
            wl.unlock();
            return c.balance();
        } finally {
            c.l.unlock();
        }
    }

    // account balance; 0 if no such account
    public int balance(int id) {
        rl.lock();
        Account c = map.get(id);
        if(c == null){
            rl.unlock();
            return 0;
        }
        try{
            c.l.lock();
            rl.unlock();
            return c.balance();
        } finally {
            c.l.unlock();
        }
    }

    // deposit; fails if no such account
    public boolean deposit(int id, int value) {
        rl.lock();
        Account c = map.get(id);
        if (c == null) {
            rl.unlock();
            return false;
        }
        try{
            c.l.lock();
            rl.unlock();
            return c.deposit(value);
        } finally {
            c.l.unlock();
        }

    }

    // withdraw; fails if no such account or insufficient balance
    public boolean withdraw(int id, int value) {
        rl.lock();
        Account c = map.get(id);
        if (c == null) {
            rl.unlock();
            return false;
        }
        try{
            c.l.lock();
            rl.unlock();
            return c.withdraw(value);
        } finally {
            c.l.unlock();
        }

    }

    // transfer value between accounts;
    // fails if either account does not exist or insufficient balance
    public boolean transfer(int from, int to, int value) {
        Account cfrom, cto;
        rl.lock();
        try {
            cfrom = map.get(from);
            cto = map.get(to);
            if (cfrom == null || cto == null)
                return false;
            if(from < to){
                cfrom.l.lock();
                cto.l.lock();
            } else {
                cto.l.lock();
                cfrom.l.lock();
            }
        } finally {
            rl.unlock();
        }
        try{
            try{
                if(!cfrom.withdraw(value))
                    return false;
            } finally {
                cfrom.l.unlock();
            }
            return cto.deposit(value);
        } finally {
            cto.l.unlock();
        }
    }

    // sum of balances in set of accounts; 0 if some does not exist
    public int totalBalance(int[] ids) {
        Account[] acs = new Account[ids.length];
        rl.lock();
        try {
            for(int i = 0; i < nextId; i++){
                acs[i] = map.get(ids[i]);

            }
            for(Account c : acs)
                if(c != null)
                    c.l.lock();
        } finally {
            rl.unlock();
        }
        int total = 0;
        for (Account c : acs) {
            if(c != null) {
                total += c.balance();
                c.l.unlock();
            }
        }
        return total;
    }
}
