package alibaba.medium;

import java.util.HashMap;
import java.util.Map;

/** Least recently used
 * 运用你所掌握的数据结构，设计和实现一个  LRU (最近最少使用) 缓存机制。它应该支持以下操作： 获取数据 get 和 写入数据 put 。
 *
 * 获取数据 get(key) - 如果关键字 (key) 存在于缓存中，则获取关键字的值（总是正数），否则返回 -1。
 * 写入数据 put(key, value) - 如果关键字已经存在，则变更其数据值；如果关键字不存在，则插入该组「关键字/值」。当缓存容量达到上限时，它应该在写入新数据之前删除最久未使用的数据值，从而为新的数据值留出空间。
 *
 *  
 *
 * 进阶:
 *
 * 你是否可以在 O(1) 时间复杂度内完成这两种操作？
 *
 *  
 *
 * 示例:
 *
 * LRUCache cache = new LRUCache( 2 缓存容量  );
 *
 * cache.put(1,1);
*cache.put(2,2);
*cache.get(1);       // 返回  1
*cache.put(3,3);    // 该操作会使得关键字 2 作废
*cache.get(2);       // 返回 -1 (未找到)
*cache.put(4,4);    // 该操作会使得关键字 1 作废
*cache.get(1);       // 返回 -1 (未找到)
*cache.get(3);       // 返回  3
*cache.get(4);       // 返回  4
*
*链接：https://leetcode-cn.com/problems/lru-cache
 * * */

public class LRUCache {
    class DLinkedNode {
        int key;
        int val;
        DLinkedNode pre;
        DLinkedNode next;
        public DLinkedNode() {}
        public DLinkedNode(int key,int val) {
            this.key = key;
            this.val = val;
        }
    }
    private DLinkedNode head,tail;
    private Map<Integer,DLinkedNode>  cache = new HashMap<Integer, DLinkedNode>();
    private int size,capacity;
    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        head = new DLinkedNode();
        tail = new DLinkedNode();
        head.next = tail;
        tail.pre = head;
    }
    public int get(int key) {
        DLinkedNode node = cache.get(key);
        if(null == node) {
            return -1;
        }
        moveToHead(node);

        return node.val;
    }

    public void put(int key, int val) {
        DLinkedNode node = cache.get(key);
        if(null == node) {
            node = new DLinkedNode(key,val);
            addToHead(node);
            cache.put(key,node);
            size++;
            if(size > capacity) {
                removeTail();
            }
        } else {
            node.val = val;
            moveToHead(node);
        }

    }

    private void removeTail() {
        DLinkedNode tnode = tail.pre;
        removeNode(tnode);
        cache.remove(tnode.key);
        size--;

    }

    private void moveToHead(DLinkedNode node) {
        removeNode(node);
        addToHead(node);
    }

    private void addToHead(DLinkedNode node) {
          node.next = head.next;
          node.pre  = head;
          head.next.pre = node;
          head.next = node;
    }

    private void removeNode(DLinkedNode node) {
        node.pre.next = node.next;
        node.next.pre = node.pre;
    }

    private void print() {
        DLinkedNode thead = head.next;
        System.out.println("=======");
        while (thead.val > 0) {
            System.out.println(thead.val);
            thead = thead.next;
        }
    }



    public static void main(String[] argv) {
        LRUCache t = new LRUCache(3);
        t.put(1,1);
        t.put(2,2);
        System.out.println(t.get(1));
        t.put(3,3);
        System.out.println(t.get(2));
        t.print();

    }

}
