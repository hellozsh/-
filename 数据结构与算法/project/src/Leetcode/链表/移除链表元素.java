package Leetcode.链表;

// https://leetcode-cn.com/problems/remove-linked-list-elements/
public class 移除链表元素 {

    public ListNode removeElements(ListNode head, int val) {

        if (head == null) return head;
        ListNode node = head;
        while (node != null && node.next != null) {
            if (node.next.val == val) {
                node.next = node.next.next;
            } else {
                node = node.next;
            }
        }

        if (head.val == val) {
            return head.next;
        } else {
            return head;
        }
    }


    class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
        ListNode(int x, ListNode next) { val = x; this.next = next;}
    }

    //
    public ListNode testNode() {

        ListNode seven = new ListNode(6);
        ListNode six = new ListNode(5,seven);
        ListNode five = new ListNode(4, six);
        ListNode four = new ListNode(3, five);
        ListNode three = new ListNode(1, four);
        ListNode two = new ListNode(1, three);
        ListNode one = new ListNode(1, two);

        return one;
    }

}
