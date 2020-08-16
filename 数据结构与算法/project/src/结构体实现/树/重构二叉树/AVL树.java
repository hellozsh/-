package 结构体实现.树.重构二叉树;
import java.util.Comparator;

public class AVL树<E> extends 二叉搜索树<E> {

    private class AVLNode<E> extends Node<E> {
        int height = 1;

        public AVLNode(E element, Node<E> parent) {
            super(element, parent);
        }

        public int balanceFactor() {
            int leftHeight = left == null ? 0: ((AVLNode<E>)left).height;
            int rightHeight = right == null ? 0: ((AVLNode<E>)right).height;

            return leftHeight - rightHeight;
        }

        public void updateHeight() {
            int leftHeight = left == null ? 0: ((AVLNode<E>)left).height;
            int rightHeight = right == null ? 0: ((AVLNode<E>)right).height;

            height = 1 + Math.max(leftHeight, rightHeight);
        }

        public Node<E> tallerChild() {
            int leftHeight = left == null ? 0: ((AVLNode<E>)left).height;
            int rightHeight = right == null ? 0: ((AVLNode<E>)right).height;
            if (leftHeight > rightHeight) return left;
            if (leftHeight < rightHeight) return right;
            return isLeftChild() ? left : right;
        }

    }

    public AVL树() { }

    public AVL树(Comparator<E> comparator) {
        super(comparator);
    }


    @Override
    protected void afterAdd(Node<E> node) {
        while ((node = node.parent) != null) {
            if (isBalanced(node)) {
                // 更新高度
                updateHeight(node);
            } else {
                rebalance(node);
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


    private void rotateAllRotation(Node<E> r, // 这个子树的根节点
                                   Node<E> b, Node<E> c, Node<E> d, Node<E> e, Node<E> f) {

        d.parent = r.parent;
        if (r.isLeftChild()) {
            r.parent.left = d;
        } else if (r.isRightChild()) {
            r.parent.right = d;
        } else {
            root = d;
        }

        // b-c
        b.right = c;
        if (c != null) c.parent = b;
        updateHeight(b);

        // e-f
        f.left = e;
        if (e != null) e.parent = f;
        updateHeight(f);

        // b-d-f
        d.left = b;
        d.right = f;
        b.parent = d;
        f.parent = d;
        updateHeight(d);
    }


    private void rebalance(Node<E> grand) {
        Node<E> parent = ((AVLNode<E>)grand).tallerChild();
        Node<E> node = ((AVLNode<E>)parent).tallerChild();
        if (parent.isLeftChild()) {
            if (node.isLeftChild()) { // LL
                rotateRight(grand);
            } else { // LR
                rotateLeft(parent);
                rotateRight(grand);
            }
        } else {
            if (node.isLeftChild()) { // RL
                rotateRight(parent);
                rotateLeft(grand);
            } else { // RR
                rotateLeft(grand);
            }
        }
    }

    private void rotateLeft(Node<E> grand) {
        Node<E> parent = grand.right;
        Node<E> child = parent.left;
        grand.right = child;
        parent.left = grand;

        afterRotation(grand, parent, child);
    }

    private void rotateRight(Node<E> grand) {

        Node<E> parent = grand.left;
        Node<E> child = parent.right;
        grand.left = child;
        parent.right = grand;

        afterRotation(grand, parent, child);
    }

    private void afterRotation(Node<E> grand, Node<E> parent, Node<E> child) {
        // 让parent成为子树的根节点
        parent.parent = grand.parent;
        if (grand.isLeftChild()) {
            grand.parent.left = parent;
        } else if (grand.isRightChild()) {
            grand.parent.right = parent;
        } else { // grand是root节点
            root = parent;
        }

        // 更新child的parent
        if (child != null) {
            child.parent = grand;
        }

        // 更新grand的parent
        grand.parent = parent;

        // 更新高度
        updateHeight(grand);
        updateHeight(parent);
    }

    private boolean isBalanced(Node<E> node) {
        return Math.abs(((AVLNode<E>)node).balanceFactor()) <= 1;
    }
    private void updateHeight(Node<E> node) {
        ((AVLNode<E>)node).updateHeight();
    }

    @Override
    protected Node<E> createNode(E element, Node<E> parent) {
        return new AVLNode<E>(element, parent);
    }

}














