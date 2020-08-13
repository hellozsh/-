package Leetcode.树;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class N叉树的前序遍历 {

    class Node {
        public int val;
        public List<Node> children;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    };


    public List<Integer> preorder(Node root) {

        List<Integer> list = new LinkedList<>();

        Stack<Node> stack = new Stack();

        if (root == null) {
            return list;
        } else {
            stack.push(root);
        }

        while (!stack.isEmpty()) {

            Node node = stack.pop();
            list.add(node.val);
            if (node.children != null) {
               for (int i = node.children.size()-1; i >= 0; i--) {
                   stack.add(node.children.get(i));
               }
            }
        }
        return list;
    }

    public List<Integer> preorderDiGui(Node root) {

        List<Integer> list = new LinkedList<>();

        if (root == null) {
            return list;
        }

        bianli(root, list);
        return list;
    }

    public void bianli(Node root, List<Integer> list) {

        list.add(root.val);
        if (root.children != null) {
            for (int i = 0; i < root.children.size(); i++) {
                bianli(root.children.get(i), list);
            }
        }
    }

}
