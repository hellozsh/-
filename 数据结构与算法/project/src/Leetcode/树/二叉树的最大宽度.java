package Leetcode.树;
import java.util.Deque;
import java.util.LinkedList;

public class 二叉树的最大宽度 {


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
    public class ValueTreeNode {
        int val;
        TreeNode node;
        ValueTreeNode(int x, TreeNode nodeVla) { val = x ; node = nodeVla;}
    }


//    public int widthOfBinaryTree(TreeNode root) {
//
//        if(root == null) {
//            return 0;
//        }
//
//        Deque<ValueTreeNode> queue = new LinkedList<>();
//        // 根节点编号为 0
////        root.val = 0;
//        ValueTreeNode valueNode = new ValueTreeNode(0, root);
//
//        queue.add(valueNode);
//
//        int sum;
//        int ans = 0;
//        while(!queue.isEmpty()) {
//            sum = queue.size();
//            // 队头和队尾的编号值求差用来更新宽度
//            ans = Math.max(ans, queue.getLast().val - queue.getFirst().val + 1);
//            // 一次处理一层，进入这个循环前队列中是一层的所有非空节点
//            while(sum > 0) {
//                ValueTreeNode temp = queue.remove();
//
//                // 子节点入队前修改 val, val = 满二叉树中节点编号
//                if(temp.node.left != null) {
//                    valueNode = new ValueTreeNode(temp.val * 2 + 1, temp.node.left);
//                    queue.add(valueNode);
//                }
//                if(temp.node.right != null) {
//                    valueNode = new ValueTreeNode(temp.val * 2 + 1, temp.node.right);
//                    queue.add(valueNode);
//                }
//                sum--;
//            }
//        }
//
//        return ans;
//    }

    /// 迭代方法
    public int widthOfBinaryTree(TreeNode root) {

        if(root == null) {
            return 0;
        }

        Deque<ValueTreeNode> queue = new LinkedList<>();

        int levelSize = 1;
        int levelhWidth = 0;

        ValueTreeNode valueNode = new ValueTreeNode(0, root);
        queue.add(valueNode);  // Queue

        while (!queue.isEmpty()) {

            ValueTreeNode node = queue.pop();
            levelSize--;

            if (node.node.left != null) {
                valueNode = new ValueTreeNode(node.val * 2 + 1, node.node.left);
                queue.add(valueNode);
            }
            if (node.node.right != null) {
                valueNode = new ValueTreeNode(node.val * 2 + 2, node.node.right);
                queue.add(valueNode);
            }
            if (levelSize == 0) {

                int val = queue.getLast().val - queue.getFirst().val + 1;
                levelhWidth = Math.max(levelhWidth, val);

                levelSize = queue.size();
            }
        }

        return levelhWidth;
    }

}
