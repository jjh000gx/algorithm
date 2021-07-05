package thread;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class TestHashMapThread extends Thread{
    static HashMap<Integer, Integer> map = new HashMap<Integer, Integer>(2);
    static AtomicInteger at = new AtomicInteger();
    public void run() {
        while(at.get() < 100000) {
             map.put(at.get(),at.get());
             at.incrementAndGet();
        }
    }

    public static void main(String[] argvs) {
        TestHashMapThread t0 = new TestHashMapThread();
        TestHashMapThread t1 = new TestHashMapThread();
        TestHashMapThread t2 = new TestHashMapThread();
        TestHashMapThread t3 = new TestHashMapThread();
        TestHashMapThread t4 = new TestHashMapThread();
        t0.start();
        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
}
