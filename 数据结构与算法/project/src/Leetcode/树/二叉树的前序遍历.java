package Leetcode.树;


import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/// https://leetcode-cn.com/problems/binary-tree-preorder-traversal/
public class 二叉树的前序遍历 {


    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    public TreeNode testNode() {

        TreeNode root = new TreeNode(1);
        root.right = new TreeNode(2);
        root.right.left = new TreeNode(3);

        return root;
    }

    public List<Integer> preorderTraversal(TreeNode root) {

        List<Integer> list = new ArrayList<>();

        if (root == null) {
            return list;
        }
        Stack<TreeNode> stack = new Stack();
        stack.push(root);

        TreeNode node;
        while (!stack.isEmpty()) {
            node = stack.pop();

            list.add(node.val);
            System.out.println(node.val);
            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
        }

        return list;
    }
}
