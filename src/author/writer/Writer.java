package author.writer;

import java.util.Queue;

public class Writer extends Thread {

    private final Queue<String> queue;

    public Writer(Queue<String> queue) {
        this.queue = queue;
    }

    public void run() {
        while (true) {
            synchronized (queue) {
                if (queue.isEmpty()) {
                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.println(queue.poll());
            }
        }
    }
}
