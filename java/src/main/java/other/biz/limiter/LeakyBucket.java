package other.biz.limiter;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 1.漏桶算法
 * 漏桶作为计量工具（The Leaky Bucket Algorithm as a Meter）时，可以用于流量整形（Traffic Shaping）和流量控制（TrafficPolicing），漏桶算法的描述如下：
 * 一个固定容量的漏桶，按照常量固定速率流出水滴；
 * 如果桶是空的，则不需流出水滴；
 * 可以以任意速率流入水滴到漏桶；
 * 如果流入水滴超出了桶的容量，则流入的水滴溢出了（被丢弃），而漏桶容量是不变的。
 *
 * 2.桶的容量代表最大并发量，如果桶满了，则请求被丢弃
 *  固定速率流出
 *  随意速率流入，流入代表请求，如果流入速率很快，将桶装满，则溢出的请求被放弃，以达到限流的效果。
 *
 *  这种算法，在使用过后也存在弊端：无法应对短时间的突发流量。
 */
public class    LeakyBucket {
    //桶的容量
    private int capacity = 100;
    //木桶剩余的水滴的量(初始化的时候的空的桶)
    private AtomicInteger water = new AtomicInteger();

    // 水滴的流出的速率 每1000毫秒流出1滴
    private int leakRate;
    // 第一次请求之后,木桶在这个时间点开始漏水
    private long leakTimeStamp;

    public LeakyBucket(int leakRate) {
        this.leakRate = leakRate;
    }

    public boolean acquire() {
        Long current = System.currentTimeMillis();
        if(water.get() == 0) {
            leakTimeStamp = current;
            water.addAndGet(1);
            return true;
        }
        // 先执行漏水，计算剩余水量
        int hrun = (int)((current - leakTimeStamp)* leakRate /1000 );
        int waterLeft = water.get() - hrun;
        water.set(Math.max(waterLeft,0));
        //水量没有满，则可以继续倒水
        if(water.get() < capacity) {
            //当并发很大，水流速比较慢的时候。如果反正if语句外包,会造成没有水流出来,造成请求会一直拒绝
            leakTimeStamp = current;
            water.addAndGet(1);
            return true;
        }

        return false;
    }



}
