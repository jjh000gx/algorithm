package alibaba.simple;

/**
 * 反转一个单链表。
 *
 * 示例:
 *
 * 输入: 1->2->3->4->5->NULL
 * 输出: 5->4->3->2->1->NULL
 * @link https://leetcode-cn.com/problems/reverse-linked-list/
 */
public class ReveLinkList {
    public static class ListNode{
        int val;
        ListNode next;
        ListNode(int val) {
            this.val = val;
        }
    }
    public ListNode head,current;

    public  ReveLinkList(int val) {
        head    = new ListNode(val);
        current = head;
    }
    public void insert(int val) {
        current.next = new ListNode(val);
        current = current.next;
    }

    public ListNode rever1 (ListNode curr) {
        ListNode prev = null;
        //ListNode curr = head;
        while (null != curr) {
            ListNode temp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = temp;
        }
        return  prev;
    }

    public void print(ListNode p) {
        ListNode  l =  p;
        while (null != l) {
            System.out.println(l.val);
            l = l.next;
        }
    }



    public  static void main(String[] argv) {
        ReveLinkList t = new ReveLinkList(1);

        t.insert(2);
        t.insert(3);
        t.print(t.head);
        ListNode curr = t.rever1(t.head);
        t.print(curr);
    }

}



