package queue.block;


import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable {

    private final BlockingQueue<Integer> queue;

    @Override
    public void run() {

        try {
            int i = 0;
            while (i < 21) {
                Integer take = queue.take(); //获取不到值的时候会被堵塞
                process(take);
                i++;
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }

    private void process(Integer take) throws InterruptedException {
        System.out.println("[Consumer] Take : " + take);
        Thread.sleep(500);
    }

    public Consumer(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }
}