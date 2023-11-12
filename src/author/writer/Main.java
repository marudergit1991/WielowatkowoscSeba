package author.writer;

import java.util.LinkedList;
import java.util.Queue;

public class Main {
    public static void main(String[] args) {

        //napisz program author - writer. Author co sekunde generuje napisy i program je dostaje jako argumenty
        //writer ma je wypisac na konsoli

        Queue<String> queue = new LinkedList<>();
        Author author = new Author(queue);
        Writer writer = new Writer(queue);
        author.start();
        writer.start();
    }


}
