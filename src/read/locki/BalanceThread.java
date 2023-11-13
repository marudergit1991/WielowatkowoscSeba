package read.locki;

public class BalanceThread extends Thread {

    private Balance balance;
    private boolean method;

    public BalanceThread(Balance balance, boolean method) {
        this.balance = balance;
        this.method = method;
        this.start();
    }

    public void run() {
        for (int i = 0; i < 100; i++) {

            System.out.println(getBalance().getData());

            if (method) {
                balance.metodaSync();
            }

            if (!method) {
                balance.metodaLock();
            }
        }
    }

    public Balance getBalance() {
        return balance;
    }
}

