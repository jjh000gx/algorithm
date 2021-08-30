package thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * https://blog.csdn.net/weixin_44416026/article/details/106789119
 */
public class ReentrantlockDemo implements Runnable {

    private static ReentrantLock reentrantLock = new ReentrantLock(true); //创建ReentrantLock对象
    private static Condition condition = reentrantLock.newCondition(); //通过Reentrantlock获取Condition对象
    int flag;

    public ReentrantlockDemo(int flag) {
        this.flag = flag;
    }

    @Override
    public void run() {
        System.out.println("线程" + Thread.currentThread().getName() + "进入");
        reentrantLock.lock();
        try {
            System.out.println("线程" + Thread.currentThread().getName() + "获取锁");
            //flag为1表示当前线程等待，为2表示唤醒等待的线程
            if(flag == 1){
                System.out.println("线程" + Thread.currentThread().getName() + "等待");
                condition.await();
                System.out.println("线程" + Thread.currentThread().getName() + "被唤醒");
            }
            else{
                System.out.println("线程" + Thread.currentThread().getName() + "唤醒等待线程");
                condition.signal();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            reentrantLock.unlock();
            System.out.println("线程" + Thread.currentThread().getName() + "结束");
        }
    }
    //@SuppressWarnings("unchecked")
    public void addItems(String item){

       // @SuppressWarnings("rawtypes")
        List items = new ArrayList();

        items.add(item);

    }

    public static void main(String[] args) throws InterruptedException {
        //这里虽然创建了两个Runnable实例，但锁是被static修饰的，所以两个实例使用的同一把锁
        ReentrantlockDemo r1 = new ReentrantlockDemo(1);
        ReentrantlockDemo r2 = new ReentrantlockDemo(2);
        Thread t1 = new Thread(r1,"thread1");
        Thread t2 = new Thread(r2,"thread2");
        t1.start();
        Thread.sleep(1000);
        t2.start();
        //new PriorityBlockingQueue();
        //new ThreadPoolExecutor();
    }
}
