package 结构体实现.树;


import 结构体实现.树.重构二叉树.AVL树;
import 结构体实现.树.重构二叉树.树;

public class AVL树复习<E> extends 二叉搜索树复习<E> {


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

                rotateAllRotation(grand,grand,node.left, node, node.right, parent);
            } else { // RR
                rotateAllRotation(grand,grand,parent.left, parent, node.left, node);
            }
        }
    }


    private void rotateAllRotation( Node<E> r, // 这个子树的根节点
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

    private void rotateLeft(Node<E> needDeclineNode) { // 需要左边下降的node

        Node<E> rightChild = needDeclineNode.right;
        Node<E> leftChild_rightChild = rightChild.left;

        needDeclineNode.right = leftChild_rightChild;
        rightChild.left = needDeclineNode;

        // 维护needDeclineNode子树的parent和height
        afterRotate(needDeclineNode, rightChild, leftChild_rightChild);
    }

    private void rotateRight(Node<E> needDeclineNode) { // 需要右边下降的node

        Node<E> leftChild = needDeclineNode.left;
        Node<E> rightChild_leftChild = leftChild.right;

        needDeclineNode.left = rightChild_leftChild;
        leftChild.right = needDeclineNode;

        // 维护needDeclineNode子树的parent和height
        afterRotate(needDeclineNode, leftChild, rightChild_leftChild);
    }

    // 原先的grand、parent、child， grand下降，parent变成最高的点，原来是parent的child变成grand的child
    // 后，到这里更新parent属性、更新高度
    protected void afterRotate(Node<E> grand, Node<E> parent, Node<E> child) {

        if (grand.isLeftChild()) {
            grand.parent.left = parent;
        } else if (grand.isRightChild()) {
            grand.parent.right = parent;
        } else {
            root = parent;
        }
        if (child != null) {
            child.parent = grand;
        }
        parent.parent = grand.parent;
        grand.parent = parent;

        // 更新高度
        updateHeight(grand);
        updateHeight(parent);
    }


    private void updateHeight(Node<E> node) {
        ((AVLNode<E>)node).updateHeight();
    }

    private boolean isBalanced(Node<E> node) {

        return Math.abs(((AVLNode<E>)node).balanceFactor()) <= 1;
    }


    @Override
    protected Node createNode(Object element, Node parent) {
        return new AVLNode(element, parent);
    }
}
