package author.writer;

import java.time.Duration;
import java.util.Queue;

public class Author extends Thread {
    private final Queue<String> queue;

    public Author(Queue<String> queue) {
        this.queue = queue;
    }

    public void run() {
        int licznik = 0;
        while (true) {
            try {
                Thread.sleep(Duration.ofSeconds(1));
                synchronized (queue) {
                    queue.offer("String nr: " + licznik);
                    licznik++;
                    queue.notify();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
