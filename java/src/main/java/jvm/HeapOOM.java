package jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * VM Args: -Xms20m 堆的最小值 -Xmx20m 堆的最大值 -XX:+HeapDumpOnOutOf-MemoryError  -XX:HeapDumpPath=D:\
 */
public class HeapOOM {
    static class OOMObject {}
    public static void main(String[] args) {
        List<OOMObject> list = new ArrayList<OOMObject>();

        while (true) {
            list.add(new OOMObject());
        }
    }
}
