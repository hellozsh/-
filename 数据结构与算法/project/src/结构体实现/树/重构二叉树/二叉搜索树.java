package 结构体实现.树.重构二叉树;

import java.util.Comparator;

public class 二叉搜索树<E> extends 树 {

     private Comparator<E> comparator;

    public 二叉搜索树() {
        this(null);
    }

    public 二叉搜索树(Comparator<E> comparator) {
        this.comparator = comparator;
    }


    public void add(E element) {

        elementNotNullCheck(element);
        if (root == null) {
            root = new Node<>(element, null);
            size++;
            return;
        }

        Node<E> node = root;
        Node<E> parent = null;
        int cmp = 0;
        while (node != null) {

            cmp = compare(element, node.element);
            parent = node;
            if (cmp > 0) {
                node = node.right;
            } else if (cmp < 0) {
                node = node.left;
            } else { // 相等
                node.element = element;
                return;
            }
        }
        Node<E> newNode = new Node<>(element, parent);
        if (cmp > 0) {
            parent.right = newNode;
        } else {
            parent.left = newNode;
        }
        size++;
    }

    private int compare(E e1, E e2) {

        if (comparator != null) {
            return comparator.compare(e1,e2);
        }
        return ((Comparable<E>)e1).compareTo(e2);
    }
    public void remove(E element) {

        remove(node(element));
    }

    private void remove(Node<E> node) {

        if (node == null) return;

        // 度为2的节点, 找到前驱节点，用前驱节点的值覆盖当前node的值
        // 再将那个前驱节点删掉
        // 也可以用后继节点值来当下一个node的值
        // 一个node的前驱后继节点肯定是度为1或0的节点
        if (node.hasTwoChildren()) {
            Node<E> pre = predecessor(node);
            node.element = pre.element;
            node = pre;
        }

        // 删除node节点(走到这里node的度肯定为0或者1)
        Node<E> replacementNode = node.left != null ? node.left : node.right;
        if (replacementNode != null) { // node是度为1的节点
            replacementNode.parent = node.parent;
            if (node.parent == null) {
                root = replacementNode;
            } else if (node == node.parent.left) {
                node.parent.left = replacementNode;
            } else if (node == node.parent.right) {
                node.parent.right = replacementNode;
            }
        } else if (node.parent == null){ // node是叶子节点并且是根节点
            root = null;
        } else { // node是叶子节点、但不是根节点
            if (node == node.parent.right) {
                node.parent.right = null;
            } else {
                node.parent.left = null;
            }
        }


        size--;
    }
    private Node<E> node(E element) {
        Node<E> node = root;
        while (node != null) {
            int cmp = compare(element, node.element);
            if(cmp == 0) return node;
            if (cmp > 0) {
                node = node.right;
            } else {
                node = node.left;
            }
        }
        return null;
    }

    public boolean contains(E element) {
        return node(element) != null;
    }
}
