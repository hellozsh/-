package Leetcode.树;

import java.lang.reflect.Array;
import java.util.Arrays;

/// https://leetcode-cn.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/submissions/
public class 从前序和中序遍历序列构造二叉树 {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    public TreeNode buildTree(int[] preorder, int[] inorder) {

        System.out.println(preorder.toString() + "---- " + inorder.toString());

        if (preorder.length == 0 && inorder.length == 0) return null;

        TreeNode node = new TreeNode(preorder[0]);

        int rootIndex = 0;
        for (int i = 0; i < inorder.length; i++) {

            if (inorder[i] == node.val) { // 找到根节点了
                rootIndex = i;
                break;
            }
        }

        int[] leftPreorder = Arrays.copyOfRange(preorder, 1, rootIndex+1);
        int[] leftInorder = Arrays.copyOfRange(inorder, 0, rootIndex);

        int[] rightPreorder = Arrays.copyOfRange(preorder, rootIndex+1, preorder.length);
        int[] rightInorder = Arrays.copyOfRange(inorder, rootIndex+1, inorder.length);

        // 拿到左子树
        node.left = buildTree(leftPreorder, leftInorder);
        // 拿到右子树

        node.right = buildTree(rightPreorder, rightInorder);

        return node;
    }


}
