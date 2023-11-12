package kolejki;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {

        Queue<String> queue = new LinkedList<>();

        queue.add("Piotr");
        // offer – w przypadku niewystarczającej ilości miejsca zwróci false, natomiast:
        // add – zwróci wyjątek: IllegalStateException.
        queue.offer("Ania");
        queue.offer("Tomek");
        queue.offer("Krzys");
        System.out.println(queue);

        //tail  - wskaznik na ostatni element
        //head - wskaznik na pierwszy element

        //peek() pierwszy element
        //element()
        //peek – zwróci NULL w przypadku pustej kolejki, natomiast:
        //element – zwróci wyjątek: NoSuchElementException.
//        queue.clear();
        System.out.println(queue.peek());
        System.out.println(queue.element());

        //poll() zwraca  usuwa pierwszy element
        System.out.println(queue.poll());
        System.out.println(queue.remove());

        // java.util.concurrent.BlockingQueue obsługuje dodatkowe metody wspierające
        // blokowanie operacji, np. poczekanie i przytrzymanie logiki pobierania elementu z kolejki, jeżeli jeszcze nic w niej nie ma.

        //tworzymy kolejke blokujaca o pojemnosci 10
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(10);

        try {
            // nowy watek, poczeka sekunde i doda element do kolejki
            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // metoda offer
                blockingQueue.offer("Hello from another thread");
            }).start();
            System.out.println(blockingQueue.poll()); // null bo jeszcze nic nie ma w kolejce

            // poczekamy max 5 sekund az cos sie pojawi w kolejce:
            System.out.println(blockingQueue.poll(5, TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // TODO: uzupelnij notatke, podaj jakis przyklad o kolejkach priorytetowych
        //kolejka priorytetowa - kolejka, w ktorej pobieranie elementow odbywa sie na podstawie jakiegos kryterium (domyslnie rosnaco)
        //mozna podac komparator do konstruktora z kryterium porowanania
        PriorityQueue<Integer> queue2 = new PriorityQueue<>();
        queue2.offer(1);
        queue2.offer(10);
        queue2.offer(2);
        queue2.offer(20);
        queue2.offer(3);
        queue2.offer(30);

        List<Integer> list = new ArrayList<>();
        while (!queue2.isEmpty()) {
            list.add(queue2.poll());
        }

        System.out.println(list);

        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.reverseOrder());
        priorityQueue.offer(1);
        priorityQueue.offer(10);
        priorityQueue.offer(2);
        priorityQueue.offer(20);
        priorityQueue.offer(3);
        priorityQueue.offer(30);

        List<Integer> list2 = new ArrayList<>();
        while (!priorityQueue.isEmpty()) {
            list2.add(priorityQueue.poll());
        }

        System.out.println(list2);
    }
}
