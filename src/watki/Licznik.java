package watki;

public class Licznik {

    private int wartosc;

//    public void zwieksz() {
//        synchronized (this) {
//            wartosc++;
//        }
//    }

    public synchronized void zwieksz() {
        wartosc++;
    }

    public int getWartosc() {
        return wartosc;
    }
}
