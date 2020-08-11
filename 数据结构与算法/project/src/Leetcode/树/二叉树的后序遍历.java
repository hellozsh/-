package Leetcode.树;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/// https://leetcode-cn.com/problems/binary-tree-postorder-traversal/
public class 二叉树的后序遍历 {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    public TreeNode testNode() {

        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(4);
        root.left.right = new TreeNode(2);
        root.left.right.right = new TreeNode(1);

        return root;
    }

    public List<Integer> postorderTraversal(TreeNode root) {

        List<Integer> list = new ArrayList<>();

        if (root == null) {
            return list;
        }
        Stack<TreeNode> stack = new Stack();
        Stack<TreeNode> centerStack = new Stack<>();

        TreeNode node = root;

        while (node != null || !stack.isEmpty()) {

            while (node != null)  {
                stack.push(node);
                node = node.left;
            }

            node = stack.pop();

            if (node.right != null) {
                centerStack.push(node);
            } else {
                list.add(node.val);

                TreeNode curNode = node;
                while (!centerStack.isEmpty()) {
                    TreeNode parentNode = centerStack.peek();
                    if (parentNode != null && parentNode.right == curNode) { // 是父类的右边节点,是父类的右边节点的时候，
                        list.add(parentNode.val);
                        curNode = centerStack.pop();
                    } else {
                        break;
                    }
                }
            }

            node = node.right;
        }

        while (!centerStack.isEmpty()) {
            list.add(centerStack.pop().val);
        }

        return list;
    }
}
