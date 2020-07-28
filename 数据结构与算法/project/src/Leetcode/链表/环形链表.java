package Leetcode.链表;

// https://leetcode-cn.com/problems/linked-list-cycle/
public class 环形链表 {

    public boolean hasCycle(ListNode head) {

        if (head == null || head.next == null) {
            return false;
        }

        ListNode slowNode = head;
        ListNode fastNode = head.next;
        while (slowNode != null && fastNode != null && fastNode.next != null && fastNode.next.next != null) {

            if (slowNode == fastNode){

                return true;
            }
            slowNode = slowNode.next;
            fastNode = fastNode.next.next;
        }
        return false;
    }

    class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
        ListNode(int x, ListNode next) { val = x; this.next = next;}
    }

    // [1,2,3,4,5]
    public ListNode testNode() {

        ListNode five = new ListNode(1);
        ListNode four = new ListNode(2, five);

        return four;
    }
}
