package 结构体实现.树;

import 结构体实现.树.printer.BinaryTreeInfo;
import 结构体实现.树.重构二叉树.AVL树;
import 结构体实现.树.重构二叉树.树;

import java.util.Comparator;

public class 二叉搜索树复习<E> implements BinaryTreeInfo {

    protected   int size;
    protected   Node<E> root;

    @Override
    public Object root() {
        return root;
    }

    @Override
    public Object left(Object node) {
        return ((Node<E> )node).left;
    }

    @Override
    public Object right(Object node) {
        return ((Node<E> )node).right;
    }

    @Override
    public Object string(Object node) {

        String parentStr = "null)";
        if (((Node<E> )node).parent != null) {
            parentStr = ((Node<E> )node).parent.element+")";
        }

        return ((Node<E> )node).element+"_P("+parentStr;
    }

    protected static class Node<E> {
        E element;
        Node<E> left;
        Node<E> right;
        Node<E> parent;
        public Node(E element, Node<E> parent) {
            this.element = element;
            this.parent = parent;
        }
        /// 是否是叶子节点
        public boolean isLeaf() {
            return left == null && right == null;
        }
        public boolean hasTwoChildren() {
            return left != null && right != null;
        }

        public boolean isLeftChild() {
            return parent != null && this == parent.left;
        }
        public boolean isRightChild() {
            return parent != null && this == parent.right;
        }
    }

    public 二叉搜索树复习() {
        this(null);
    }

    public 二叉搜索树复习(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    private Comparator<E> comparator;

    public int size() {
        return size;
    }

   public boolean isEmpty() {
       return size == 0;
    }

    public void clear() {
        root = null;
        size = 0;
    }

    protected Node<E> createNode(E element, Node<E> parent) {
        return new Node<>(element, parent);
    }

    public void add(E element) {

        if (root == null) {
            root = createNode(element, null);
            size++;
            afterAdd(root);
            return;
        }

        Node<E> node = root;
        int cmp = 0;
        Node<E> parent = root;
        while (node != null) {
            cmp = compare(element,node.element);
            parent = node;
            if (cmp > 0) { // 在右边
                node = node.right;
            } else if (cmp == 0) { // 相等
                node.element = element;
                return;
            } else {
                node = node.left;
            }
        }
        Node<E> newNode = createNode(element, parent);
        if (cmp > 0) {
            parent.right = newNode;
        } else if (cmp < 0) {
            parent.left = newNode;
        }
        size++;
        afterAdd(newNode);
    }

    protected void afterAdd(Node<E> node) {

    }

    public void remove(E element) {

        Node<E> node = node(element);
        if (node == null) return;

        if (node.isLeaf()) {
             removeNode(node);
             afterRemove(node);
        } else {
            // 需要找到前驱节点
            Node<E> replaceNode = predecessorNode(node);
            if (replaceNode == null) {
                // 找到后继节点
                replaceNode = successor(node);
            }
            node.element = replaceNode.element;
            removeNode(replaceNode);
            afterRemove(node);
        }
    }

    protected void afterRemove(Node<E> node) {

    }

    private Node<E> predecessorNode(Node<E> node) {
        if (node == null) return null;
        Node<E> preNode = node.left;
        while (preNode.right != null) {
            preNode = node.right;
        }
        return preNode;
    }

    private Node<E> successor(Node<E> node) {
        if (node == null) return null;
        Node<E> succNode = node.right;
        while (succNode.left != null) {
            succNode = node.left;
        }
        return succNode;
    }

    private void removeNode(Node<E> node) {

        int cmp = compare(node.element,node.parent.element);
        if (cmp > 0) { // 在右边
            node.parent.right = null;
        } else {
            node.parent.left = null;
        }
    }

    public boolean contains(E element) {
       return node(element) != null;
    }

    private Node<E> node(E element) {

        Node<E> node = root;
        int cmp = 0;
        while (node != null) {
            cmp = compare(element,node.element);
            if (cmp > 0) { // 在右边
                node = node.right;
            } else if (cmp == 0) { // 相等
                return node;
            } else {
                node = node.left;
            }
        }
        return null;
    }

    private int compare(E e1, E e2) {

        if (comparator != null) {
            return comparator.compare(e1,e2);
        }
        return ((Comparable<E>)e1).compareTo(e2);
    }




}
