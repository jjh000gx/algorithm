package queue.block;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {

    public static void main(String[] args) {

        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(10);
        queue.poll();

        new Thread(new Producer(queue)).start();
        new Thread(new Consumer(queue)).start();

        System.out.println("main end");
        List<String> tt = new LinkedList<String>();
    }

}
