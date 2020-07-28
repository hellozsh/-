package Leetcode.链表;

// https://leetcode-cn.com/problems/middle-of-the-linked-list/
public class 链表的中间节点 {
    public ListNode middleNode(ListNode head) {

        ListNode slowNode = head;
        ListNode fastNode = head;

        while (fastNode != null) {

            if (fastNode.next == null) return slowNode;
            if (fastNode.next.next == null) return slowNode.next;
            slowNode = slowNode.next;
            fastNode = fastNode.next.next;
        }
        return slowNode;
    }

    class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
        ListNode(int x, ListNode next) { val = x; this.next = next;}
    }

    // [1,2,3,4,5]
    public ListNode testNode() {

        ListNode one = new ListNode(2);
        ListNode two = new ListNode(1,one);
        ListNode three = new ListNode(1, two);

        return three;
    }


}
