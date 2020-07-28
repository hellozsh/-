package Leetcode.链表;

// https://leetcode-cn.com/problems/remove-duplicates-from-sorted-list/
public class 删除重复元素 {

    public ListNode deleteDuplicates(ListNode head) {

        ListNode node = head;
        while (node != null && node.next != null) {
            if (node.val == node.next.val) {
                node.next = node.next.next;
            } else {
                node = node.next;
            }
        }
        return head;
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
