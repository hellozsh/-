package Leetcode.树;
import java.util.LinkedList;
import java.util.List;

/// https://leetcode-cn.com/problems/maximum-depth-of-binary-tree/
public class 二叉树的最大深度 {

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



    /// 迭代方法
    public int maxDepth(TreeNode root) {

        List<List<Integer>> list = new LinkedList<>();

        if(root == null) {
            return 0;
        }

        LinkedList<TreeNode> queue = new LinkedList<>();

        int levelSize = 1;
        int levelheight = 0;

        queue.add(root);  // Queue

        while (!queue.isEmpty()) {

            TreeNode node = queue.pop();
            levelSize--;

            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
            if (levelSize == 0) {

                levelheight ++;
                levelSize = queue.size();
            }
        }

        return levelheight;
    }

    /// 递归方法
    public int maxDepthDiGui(TreeNode root) {
        if(root == null){
            return 0;
        }
        int left = maxDepthDiGui(root.left);
        int right = maxDepthDiGui(root.right);
        return Math.max(left,right) + 1;
    }
}
