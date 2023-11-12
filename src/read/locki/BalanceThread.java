package read.locki;

import java.time.Duration;

public class BalanceThread extends Thread {

    private Balance balance;

    public BalanceThread(Balance balance) {
        this.balance = balance;
        this.start();
    }

    public void run() {
        for (int i = 0; i < 100; i++) {

            System.out.println(getBalance().getData());
            //balance.metodaSync();

            balance.metodaLock();


        }
    }

    public Balance getBalance() {
        return balance;
    }
}

