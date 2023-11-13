package read.locki;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        //Pokazać zastosowanie read/write locków i porównać ich efektywność w stosunku do zwykłej synchronizacji.
        //Stworz klase BalanceThread która przyjmuje obiekt Balance i go startuje w konstruktorze
        //metoda run niech ma petle ktora sie wykona 100 razy i dopoki balanace nie jest rowny 0 to robim syso

        //Balance posiada zmienna intowa oraz locka i ma dwie metody jedna z uzyciem synchro a druga z lockami

        long startSynchro = System.currentTimeMillis();
        int iloscWatkow = 100;


        BalanceThread[] balanceThreads = new BalanceThread[iloscWatkow];
        Balance balance = new Balance(100);
        for (int i = 0; i < iloscWatkow; i++) {
            balanceThreads[i] = new BalanceThread(balance, true);
        }

        for (int i = 0; i < iloscWatkow; i++) {
            balanceThreads[i].join();
        }

        long stopSynchro = System.currentTimeMillis();


        long startLock = System.currentTimeMillis();

        for (int i = 0; i < iloscWatkow; i++) {
            balanceThreads[i] = new BalanceThread(balance, false);
        }

        for (int i = 0; i < iloscWatkow; i++) {
            balanceThreads[i].join();
        }

        long stopLock = System.currentTimeMillis();
        System.out.println("czas Synchro: " + (stopLock - startLock));
        System.out.println("czas Lock: " + (stopSynchro - startSynchro));

    }
}
