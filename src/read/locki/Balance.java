package read.locki;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Balance {

    private int data;
    private final Lock lock = new ReentrantLock();

    public Balance(int data) {
        this.data = data;
    }

    public synchronized void metodaSync() {

        //Thread.sleep(Duration.ofMillis(10000));
        //System.out.println(getData());
        this.setData(getData() - 1);
    }

    public void metodaLock() {

        try {
            lock.lock();
            //Thread.sleep(Duration.ofMillis(10000));
            //System.out.println(getData());
            this.setData(getData() - 1);
        } finally {
            lock.unlock();
        }
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
}
