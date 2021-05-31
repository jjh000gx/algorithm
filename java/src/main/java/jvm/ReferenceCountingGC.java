package jvm;

/**
 * vm: -verbose:gc -XX:+PrintGCDetails -XX:SurvivorRatio=8
 */
public class ReferenceCountingGC {
    public Object instance = null;
    private static final  int _1MB = 1024*1024;

    public static void main(String[] args) {
        ReferenceCountingGC objA = new ReferenceCountingGC();
        ReferenceCountingGC objB = new ReferenceCountingGC();
        objA.instance = objB;
        objB.instance = objA;
        objA = null;
        objB = null;
        //假设在这行发生GC，objA和objB是否能被回收
        System.gc();
    }
}
