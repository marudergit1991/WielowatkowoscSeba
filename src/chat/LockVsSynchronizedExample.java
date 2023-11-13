package chat;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockVsSynchronizedExample {

    private static final int NUM_THREADS = 100;
    private static final int ITERATIONS = 100000;

    private static int sharedCounter = 0;
    private static Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        runWithSynchronized();
        runWithLock();
    }

    private static void runWithSynchronized() {
        long startTime = System.currentTimeMillis();

        Thread[] threads = new Thread[NUM_THREADS];

        for (int i = 0; i < NUM_THREADS; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < ITERATIONS; j++) {
                    synchronized (LockVsSynchronizedExample.class) {
                        sharedCounter++;
                    }
                }
            });
        }

        startAndJoinThreads(threads);

        long endTime = System.currentTimeMillis();
        System.out.println("Synchronized Time: " + (endTime - startTime) + "ms");
        System.out.println("Shared Counter (Synchronized): " + sharedCounter);
    }

    private static void runWithLock() {
        sharedCounter = 0; // Resetowanie licznika

        long startTime = System.currentTimeMillis();

        Thread[] threads = new Thread[NUM_THREADS];

        for (int i = 0; i < NUM_THREADS; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < ITERATIONS; j++) {
                    lock.lock();
                    try {
                        sharedCounter++;
                    } finally {
                        lock.unlock();
                    }
                }
            });
        }

        startAndJoinThreads(threads);

        long endTime = System.currentTimeMillis();
        System.out.println("Lock Time: " + (endTime - startTime) + "ms");
        System.out.println("Shared Counter (Lock): " + sharedCounter);
    }

    private static void startAndJoinThreads(Thread[] threads) {
        for (Thread thread : threads) {
            thread.start();
        }

        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
