package 结构体实现.树.重构二叉树;

public class AVL树<E> extends 二叉平衡树<E> {


    private class AVLNode<E> extends Node<E> {
        int height = 1;
        public AVLNode(E element, Node<E> parent) {
            super(element, parent);
        }

        public int balanceFactor() {
            int leftHeight = left == null ? 0 : ((AVLNode<E>)left).height;
            int rightHeight = right == null ? 0 : ((AVLNode<E>)right).height;
            return leftHeight - rightHeight;
        }

        public void updateHeight() {
            int leftHeight = left == null ? 0 : ((AVLNode<E>)left).height;
            int rightHeight = right == null ? 0 : ((AVLNode<E>)right).height;

            height = 1 + Math.max(leftHeight, rightHeight);
        }

        public Node<E> tallerChild() {

            int leftHeight = left == null ? 0 : ((AVLNode<E>)left).height;
            int rightHeight = right == null ? 0 : ((AVLNode<E>)right).height;
            if (leftHeight > rightHeight) {
                return left;
            } else {
                return right;
            }
        }

    }


    @Override
    protected void afterAdd(Node<E> newNode) {

        while ((newNode = newNode.parent) != null) {
            if (isBalanced(newNode)) {
                // 更新高度
                updateHeight(newNode);
            } else {
//                rebalance(newNode);
                rebalanceAllRotation(newNode);
            }
        }
    }

    protected void afterRemove(Node<E> node) {

        while ((node = node.parent) != null) {
            if (isBalanced(node)) {
                // 更新高度
                updateHeight(node);
            } else {
                rebalance(node);
            }
        }
    }

    // 统一所有旋转操作
    private void rebalanceAllRotation(Node<E> grand) {

        Node<E> parent = ((AVLNode<E>)grand).tallerChild();
        Node<E> node = ((AVLNode<E>)parent).tallerChild();
        if (grand.left == parent) {
            if (parent.left == node) { // LL
                rotateAllRotation(grand,node,node.right, parent, parent.right, grand);
            } else { // LR
                rotateAllRotation(grand,parent,node.left, node, node.right, grand);
            }
        } else {
            if (parent.left == node) { // RL

                rotateAllRotation(grand,grand,node.left, node, node.right, grand);
            } else { // RR
                rotateAllRotation(grand,grand,parent.left, parent, node.left, node);
            }
        }
    }



    private void rebalance(Node<E> grand) {

        Node<E> parent = ((AVLNode<E>)grand).tallerChild();
        Node<E> node = ((AVLNode<E>)parent).tallerChild();
        if (grand.left == parent) {
            if (parent.left == node) { // LL
                rotateRight(grand);
            } else { // LR

                rotateLeft(parent);
                rotateRight(grand);
            }
        } else {
            if (parent.left == node) { // RL

                rotateRight(parent);
                rotateLeft(grand);
            } else { // RR

                rotateLeft(grand);
            }
        }
    }

    private void updateHeight(Node<E> node) {
        ((AVLNode<E>)node).updateHeight();
    }

    private boolean isBalanced(Node<E> node) {

        return Math.abs(((AVLNode<E>)node).balanceFactor()) <= 1;
    }


    @Override
    protected Node<E> createNode(E element, Node<E> parent) {
        return new AVLNode(element, parent);
    }

    @Override
    protected void afterRotation(Node<E> grand, Node<E> parent, Node<E> child) {
        super.afterRotation(grand, parent, child);
        // 更新高度
        updateHeight(grand);
        updateHeight(parent);
    }

    @Override
    protected void rotateAllRotation(Node<E> r, Node<E> b, Node<E> c, Node<E> d, Node<E> e, Node<E> f) {
        super.rotateAllRotation(r, b, c, d, e, f);
        // 更新高度
        updateHeight(b);
        updateHeight(f);
        updateHeight(d);
    }
}














