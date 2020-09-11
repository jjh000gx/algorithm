package alibaba.simple;

import struct.ListNode;

import java.util.HashMap;
import java.util.Map;

/**
 * 编写一个程序，找到两个单链表相交的起始节点。
 *
 * 如下面的两个链表：
 *
 *
 *
 * 在节点 c1 开始相交。
 */
public class GetIntersectionNode {

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        Map<ListNode,Boolean> hmap = new HashMap<ListNode,Boolean>();
        while (headA!=null) {
            hmap.put(headA,true);
            headA = headA.next;
        }
        while (headB!=null) {
            if(hmap.containsKey(headB)) {
                return headB;
            }
            headB = headB.next;
        }

        return null;
    }


    public static void main(String[] argv) {

    }
}
