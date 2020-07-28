package Leetcode.链表;

// https://leetcode-cn.com/problems/shan-chu-lian-biao-de-jie-dian-lcof/
// https://leetcode-cn.com/problems/delete-node-in-a-linked-list/
public class 删除链表的节点 {

    public ListNode deleteNode(ListNode head, int val) {

        if (head.val == val) {
            return head.next;
        } else {
            ListNode preNode = head;
            ListNode node = head.next;
            while (node.val != val) {
                preNode = node;
                node = node.next;
            }
            preNode.next = node.next;
            return head;
        }
    }

    public void deleteNode(ListNode node) {
        node.val = node.next.val;
        node.next = node.next.next;
    }

    class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }
}


