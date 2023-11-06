package consumer.producer;

import java.time.Duration;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Main {
    private static final Random generator = new Random();
    private static final Queue<String> queue = new LinkedList<>();

    public static void main(String[] args) {

        int itemCount = 5;

//        Thread producer = new Thread(() -> {
//            // produkujemy zadana liczbe elementow:
//            for (int i = 0; i < itemCount; i++) {
//                try {
//                    //metoda sleep sluzy do uspienia wątku, przekazany parametro mowi o min czasie
//                    //przez ktory dany watek bedzie uspiony - nie bedzie zajmowal czasu procesora
//                    //czyli symulacja zwiazana z produkcja elementow
//                    Thread.sleep(Duration.ofSeconds(generator.nextInt(5)).toMillis());
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//                synchronized (queue) {
//                    queue.offer("Item no " + i);
//                }
//            }
//        });
//
//        // watek konsumujacy
//        Thread consumer = new Thread(() -> {
//            int itemsLeft = itemCount;
//            int licznik = 0;
//            // wykonuje sie dopoki zadana liczba elementow nie bedzie odebrana z kolejki
//            while (itemsLeft > 0) {
//                String item;
//                licznik++;
//                //uzywamy bloku synchronized w który sprawdza czy elementy są w kolejce i do ewentualnego ich pobrania.
//                synchronized (queue) {
//                    if (queue.isEmpty()) {
//                        continue;
//                    }
//                    item = queue.poll();
//                }
//                itemsLeft--;
//                System.out.println("Consumer got item: " + item);
//                System.out.println(licznik);
//            }
//        });
//
//        consumer.start();
//        producer.start();

        		/*
		 * Program działa. Ma jednak pewien subtelny błąd. Zwróć uwagę na wątek
		 * konsumenta. Wątek ten działa bez przerwy. Bez przerwy zajmuje czas
		 * procesora. Co więcej, przez większość swojego czasu kręci się wewnątrz pętli
		 * sprawdzając czy kolejka jest pusta. Jako drobne ćwiczenie dla Ciebie
		 * zostawiam dodanie licznika iteracji – ile razy pętla wykonała się w Twoim
		 * przypadku?
         *
         * Jednym ze sposobów może być usypianie wątku konsumenta używając metody
         * Thread.sleep(), którą już znasz. To także byłoby marnowanie zasobów – skąd
         * możesz wiedzieć jak długo zajmie produkowanie kolejnego elementu?
         *
         * Wiesz już, że każdy obiekt powiązany jest z monitorem używamy w trakcie
         * synchronizacji. Podobnie wygląda sprawa w przypadku mechanizmu powiadomień.
         * Każdy obiekt w języku Java posiada zbiór powiadamianych wątków (ang. waiting
         * set).
         *
         * Wewnątrz tego zbioru znajdują się wątki, które czekają na powiadomienie
         * dotyczące danego obiektu. Jedynym sposobem, żeby modyfikować zawartość tego
         * zbioru jest używanie metod dostępnych w klasie Object:
         *
         * Object.wait() – dodanie aktualnego wątku do zbioru powiadamianych wątków,
         * Object.notify() – powiadomienie i wybudzenie jednego z oczekujących wątków,
         * Object.notifyAll() – powiadomienie i wybudzenie wszystkich oczekujących wątków.
         */

        Thread producer = new Thread(() -> {
            // produkujemy zadana liczbe elementow:
            for (int i = 0; i < itemCount; i++) {
                try {
                    //metoda sleep sluzy do uspienia wątku, przekazany parametro mowi o min czasie
                    //przez ktory dany watek bedzie uspiony - nie bedzie zajmowal czasu procesora
                    //czyli symulacja zwiazana z produkcja elementow
                    Thread.sleep(Duration.ofSeconds(generator.nextInt(5)).toMillis());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (queue) {
                    queue.offer("Item no " + i);
                    queue.notify();
                }
            }
        });

        // watek konsumujacy
        Thread consumer = new Thread(() -> {
            int itemsLeft = itemCount;
            int licznik = 0;
            // wykonuje sie dopoki zadana liczba elementow nie bedzie odebrana z kolejki
            while (itemsLeft > 0) {
                String item;
                licznik++;
                //uzywamy bloku synchronized w który sprawdza czy elementy są w kolejce i do ewentualnego ich pobrania.
                synchronized (queue) {
                    if (queue.isEmpty()) {
                        try {
                            queue.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    item = queue.poll();
                }
                itemsLeft--;
                System.out.println("Consumer got item: " + item);
                System.out.println(licznik);
            }
        });

        consumer.start();
        producer.start();

    }

}
