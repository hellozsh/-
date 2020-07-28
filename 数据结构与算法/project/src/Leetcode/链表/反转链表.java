package Leetcode.链表;

// https://leetcode-cn.com/problems/fan-zhuan-lian-biao-lcof/
public class 反转链表 {


    public ListNode diguiReverseList(ListNode head) {

        if (head == null || head.next == null) return head;

        ListNode newHead = diguiReverseList(head.next);
        head.next.next = head;
        head.next = null;
        return newHead;
    }

    public ListNode reverseList(ListNode head) {

        ListNode oldHead = head;
        ListNode newHead = null;

        while (oldHead != null) {

            ListNode tempNode = oldHead;
            oldHead = oldHead.next;

            tempNode.next = newHead;
            newHead = tempNode;
        }
        return newHead;
    }


    class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
        ListNode(int x, ListNode next) { val = x; this.next = next;}
    }

    // [1,2,3,4,5]
    public ListNode testNode() {

      ListNode five = new ListNode(5);
      ListNode four = new ListNode(4, five);
      ListNode three = new ListNode(3, four);
      ListNode two = new ListNode(2, three);
      ListNode one = new ListNode(1, two);

      return one;
    }
}

