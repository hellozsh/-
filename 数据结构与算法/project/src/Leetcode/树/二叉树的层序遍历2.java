package Leetcode.树;

import java.util.LinkedList;
import java.util.List;


/// https://leetcode-cn.com/problems/binary-tree-level-order-traversal-ii/
public class 二叉树的层序遍历2 {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    public TreeNode testNode() {

        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);

        return root;
    }


    public List<List<Integer>> levelOrderBottom(TreeNode root) {

        List<List<Integer>> list = new LinkedList<>();

        if(root == null) {
            return list;
        }

        LinkedList<TreeNode> queue = new LinkedList<>();

        int levelSize = 1;
        TreeNode lastNode = root;
        queue.add(root);  // Queue

        List<Integer> _lastList = new LinkedList<>();

        while (!queue.isEmpty()) {

            TreeNode node = queue.pop();
            _lastList.add(node.val);
            levelSize--;

            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
            if (levelSize == 0) {

                list.add(0,_lastList);
                if (!queue.isEmpty()) {
                    _lastList = new LinkedList<>();
                    levelSize = queue.size();
                }
            }
        }

        return list;
    }

}
