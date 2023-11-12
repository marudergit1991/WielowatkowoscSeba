package test;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Balance {

    private int number = 0;
    private Lock lock = new ReentrantLock();


    private boolean useLock;   // której metody synchronizacji użyć?

    public Balance(boolean ul) {
        useLock = ul;
    }

    public int balance(boolean print) { // parametr print mówi czy wypisać gwiazdkę
        if (!useLock) return balanceSynchro(print);
        else return balanceLocked(print);
    }

    // Synchronizacja za pomocą bezposrednich rygli
    public int balanceLocked(boolean print) {
        try {
            lock.lock();
            number++;
            if (print) System.out.print("*");
            number--;
            return number;
        } finally {
            lock.unlock();
        }
    }

    // Użycie synchronized
    public synchronized int  balanceSynchro(boolean print) {
        number++;
        if (print) System.out.print("*");
        number--;
        return number;
    }


}

class BalanceThread extends Thread {

    private Balance b;  // referencja do obiektu klasy Balance
    private int count;  // liczba pwotórzeń pętli w metodzie run

    public BalanceThread(String name, Balance b, int count) {
        super(name);
        this.b = b;
        this.count = count;
        start();
    }

    public void run() {
        int wynik = 0;
        for (int i = 0; i < count; i++) {
            boolean print;
            if (i%20 == 0) print = true;  // gwiazdkę będziemy drukować co 20 iterację
            else print = false;
            wynik = b.balance(print);
            if (wynik != 0) break;
        }


        System.out.println("\n"+ Thread.currentThread().getName() +
                " konczy z wynikiem  " + wynik);
    }
}

class BalanceTest {

    static ArrayList<String> czasy = new ArrayList<String>(); // wyniki czasowe

    public static void test(int tnum, boolean lock) {

        // jesli lock jest true będziemy używać bezp. rygli
        Balance b = new Balance(lock);
        String wynik = lock ? "Lock " : "Synchro ";
        String id = lock ? "L" : "S";
        wynik += tnum;
        // Tworzymy i uruchamiamy wątki
        Thread[] thread = new Thread[tnum];

        long start = System.currentTimeMillis();

        for (int i = 0; i < tnum; i++)
            thread[i] = new BalanceThread(id +(i+1), b, 100);

        // czekaj na zakończenie wszystkich wątków
        try {
            for (int i = 0; i < tnum; i++) thread[i].join();
        } catch (InterruptedException exc) {
            System.exit(1);
        }
        wynik += " Czas: " + (System.currentTimeMillis() - start);
        System.out.println(wynik);
        czasy.add(wynik);
        // Uduwamy niepotrzebne obiekty-wątki
        for (int i = 0; i < thread.length; i++) { thread[i] = null; }
        System.gc();
    }

    public static void main(String[] args) {
        //Test synchro
        for (int i=100; i<=1000; i+=100) {
            test(i, false);
        }
        // Test Lock
        for (int i=100; i<=1000; i+=100) {
            test(i, true);
        }
        for (String msg : czasy) { System.out.println(msg);

        }

    }


} 