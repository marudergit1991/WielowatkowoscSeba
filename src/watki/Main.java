package watki;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread();

        //dziedzicenie po klasie Thread
        Thread thread2 = new MyThread();
        thread2.start();

        //implementacja interfejsu Runnable
        Thread t22 = new Thread(new MyRunnable());
        t22.start();

        //watek za pomoca klasy anonimowej
        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("I'm inside runnable!");
            }
        });
        thread3.start();

        Thread thread4 = new Thread(() -> System.out.println("I'm inside runnable!"));
        thread4.start();
        System.out.println();

		/*cykl zycia watku https://www.samouczekprogramisty.pl/assets/images/2019/02/05_thread_states.svg
		  NEW – nowy wątek, który nie został jeszcze uruchomiony,
		  RUNNABLE – wątek, który może wykonywać swój kod,
		  TERMINATED – wątek, który zakończył swoje działanie,
		  BLOCKED – wątek zablokowany, oczekujący na zwolnienie współdzielonego zasobu,
		  WAITING – wątek uśpiony,
		  TIMED_WAITING – wątek uśpiony na określony czas.
		 */

        System.out.println("Ruszamy z watkiem");
        Thread threadd = new Thread(() -> {
            System.out.println("Thread start");
            for (int i = 0; i < 5; i++) {
                System.out.println("Thread " + i);
            }
            System.out.println("Thread stop");
        });
        threadd.start();

        System.out.println("Main start");
        for (int i = 0; i < 5; i++) {
            System.out.println("Main " + i);
        }
        System.out.println("Main stop");


        // blocked watek w stanie blocked oczekuje na pewien zablokowany zasob W języku Java blokowanie odbywa
        // się przy pomocy tak zwanych monitorów, które służą do synchronizacji wątków.

        Licznik licznik = new Licznik();

        Runnable r = () -> { // obiekt r ktory implementuje runnable
            for (int i = 0; i < 100000; i++) {
                licznik.zwieksz();
            }
        };
        // tworzymy 3 watki i odpalamy je
        Thread t1 = new Thread(r);
        Thread t2 = new Thread(r);
        Thread t3 = new Thread(r);

        t1.start();
        t2.start();
        t3.start();


        //metoda join zapewnia ze aktualny watek czeka na zakonczenie sie watku an ktorym join zostalo wywolane
        //main czeka na zakonczenie sie watku t1, jak ten sie skoncyz to czeka na zakonczenie t2 a potem t3
        //watki moga sie skonczyc w kolejnosci dowolnej

        t1.join();
        t2.join();
        t3.join();

        System.out.println(licznik.getWartosc());

        		/*
		 * To co udało Ci się zaobserwować wyżej to tak zwany wyścig (ang. race condition). Taka sytuacja zachodzi jeśli
		 * kilka wątków jednocześnie
		 *  modyfikuje zmienną, która do takiej równoległej zmiany nie jest przystosowana. Tylko dlaczego wartość atrybutu wartosc
		 *  miała tak różne wartości? Działo się tak dlatego, że operacja value += 1 nie jest operacją atomową.
		 *  Operacje atomowe to operacje niepodzielne, a i+=1 pobiera wartosc, dodaje 1, przypisuje
		 *
		 *  Aby uniknąć takich sytuacji, uniknąć wyścigów, niezbędna jest synchronizacja pracy wątków.
		 *
		 *  Każdy obiekt w języku Java powiązany jest z tak zwanym monitorem. Każdy monitor może być w jednym z dwóch stanów: odblokowany albo zablokowany.
		 *   Monitor może być zablokowany wyłącznie przez jeden wątek w danym momencie. Dzięki tej właściwości to obiekty używane są do tego,
		 *   żeby synchronizować wątki ze sobą. Służy do tego słowo kluczowe synchronized.
		 *
		 *   synchronized (obiekt) {
    			// synchronizowany kod
			}
			Masz pewność, że wszystko co znajduje się wewnątrz bloku w każdym momencie uruchomione jest przez maksymalnie jeden wątek.
		 */

    }
}
