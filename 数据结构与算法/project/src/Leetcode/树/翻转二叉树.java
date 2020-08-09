package Leetcode.树;


import javax.xml.soap.Node;

/// https://leetcode-cn.com/problems/invert-binary-tree/
public class 翻转二叉树 {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }


    public TreeNode invertTree(TreeNode root) {

        if (root == null) return root;

        TreeNode tmp = root.left;
        root.left = root.right;
        root.right = tmp;

        invertTree(root.left);
        invertTree(root.right);

        return root;
    }
}


