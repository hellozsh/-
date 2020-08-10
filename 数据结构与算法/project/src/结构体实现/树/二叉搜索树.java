package 结构体实现.树;

import 结构体实现.树.printer.BinaryTreeInfo;

import java.lang.Comparable;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;


public class 二叉搜索树<E> implements BinaryTreeInfo {

    private static class Node<E> {
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
    }

    public static interface Visitor<E> {
        void visit(E element);
    }

    private int size;
    private Node<E> root;

    // 写一个比较器来实现是因为很可能有需求2个二叉树里都放着同一种类型对象，但是排序规则不同，
    // 所以这个时候就可以外界来传入一个比较器，我们根据比较器的规则，来排序二叉树

    // 另外又还有大部分二叉树不存在存放同一个类型不同比较规则的，所以需要给一个默认规则
    private Comparator<E> comparator;

    public 二叉搜索树() {
        this(null);
    }

    public 二叉搜索树(Comparator<E> comparator) {
        this.comparator = comparator;
    }

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
            Node<E> pre = predecessorNode(node);
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

    private void elementNotNullCheck(E element) {
        if (element == null) {
            throw new IllegalArgumentException("element must not be null");
        }
    }

    /// 前驱节点
    private Node<E> predecessorNode(Node<E> node) {

        // 前驱节点在左子树当中(left.right.right.right....)
        Node<E> p = node.left;
        if (p != null) {

            while (p.right != null) {
                p = p.right;
            }
            return p;
        }
        // 从父节点、祖父节点中存找前驱节点
        while (node.parent != null && node == node.parent.left) {
            node = node.parent;
        }
        return node.parent;
    }

    /// 计算树的高度----递归方法
    public int heightDiGui() {
        return heightDiGui(root);
    }

    private int heightDiGui(Node<E> node) {

        if (node == null) {
            return 0;
        }
        return 1+Math.max(heightDiGui(node.left), heightDiGui(node.right));
    }

    /// 计算树的高度----迭代方法
    public int heightDieDai() {

        int height = 0;
        int levelSize = 1; // 存储着每一层的元素数量
        LinkedList<Node<E>> queue = new LinkedList();
        queue.push(root);
        Node<E> node;
        while (!queue.isEmpty()) {

            node = queue.poll();
            levelSize --;
            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
            if (levelSize == 0) {
                levelSize = queue.size();
                height++;
            }
        }
        return height;
    }

    /// 判断是否是完全二叉树
    public boolean isComplete() {

        LinkedList<Node<E>> queue = new LinkedList();
        queue.offer(root);
        boolean leaf = false;
        while (!queue.isEmpty()) {
            Node<E> node = queue.poll();
            if (leaf && !node.isLeaf()) { // 要求是叶子节点，但是不是叶子节点
                return false;
            }
            if (node.left != null && node.right != null) {
                queue.offer(node.left);
                queue.offer(node.right);
            } else if (node.left == null && node.right != null) {
                return false;
            } else {  // 说明遇到了左子树为null、右子树为null或者左子树有值，右子树为null的情况

                leaf = true; // 后面的都需要是叶子节点
            }
        }
        return true;
    }




    /// 遍历
    public void preorderTraversal() {
        preorderTraversal(root);
    }

   private void preorderTraversal(Node<E> node) {

        if (node == null) {
            return;
        }
       System.out.println(node.element);
       preorderTraversal(node.left);
       preorderTraversal(node.right);
   }

   public void inorderTraversalASC() {
       inorderTraversalASC(root);
   }

    private void inorderTraversalASC(Node<E> node) {
        if (node == null) {
            return;
        }
        inorderTraversalASC(node.left);
        System.out.println(node.element);
        inorderTraversalASC(node.right);
    }

    public void inorderTraversalDSC() {
        inorderTraversalDSC(root);
    }

    private void inorderTraversalDSC(Node<E> node) {
        if (node == null) {
            return;
        }
        inorderTraversalDSC(node.right);
        System.out.println(node.element);
        inorderTraversalDSC(node.left);
    }

    public void postorderTraversal() {
        postorderTraversal(root);
    }

    private void postorderTraversal(Node<E> node) {
        if (node == null) {
            return;
        }
        postorderTraversal(node.left);
        postorderTraversal(node.right);
        System.out.println(node.element);
    }

    public void postorderTraversal(Visitor<E> visitor) {
        postorderTraversal(root,visitor);
    }

    private void postorderTraversal(Node<E> node,Visitor<E> visitor) {
        if (node == null || visitor == null) {
            return;
        }
        postorderTraversal(node.left, visitor);
        postorderTraversal(node.right, visitor);
        visitor.visit(node.element);
    }


    public void levelorderTraversal() {

        LinkedList<Node<E>> queue = new LinkedList();
        queue.push(root);
        Node<E> node;
        while (!queue.isEmpty()) {
            node = queue.poll();
            System.out.println(node.element);
            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
        }
    }


    public void levelorderTraversal(Visitor<E> visitor) {

        if (root == null || visitor == null) return;

        LinkedList<Node<E>> queue = new LinkedList();
        queue.push(root);
        Node<E> node;
        while (!queue.isEmpty()) {
            node = queue.poll();
            visitor.visit(node.element);
            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
        }
    }



    @Override
    public Object root() {
        return root;
    }

    @Override
    public Object left(Object node) {
        return ((Node<E>)node).left;
    }

    @Override
    public Object right(Object node) {
        return ((Node<E>)node).right;
    }

    @Override
    public Object string(Object node) {
        String str = ((Node<E>)node).parent != null ? ((Node<E>)node).parent.element.toString() :"null";

        return ((Node<E>)node).element+"_"+"P("+str+")";
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        toString(root, sb,"");

        return "二叉搜索树{" +
                "size=" + size +
                ", tree=" +  sb.toString() +
                '}';
    }



    private void toString(Node<E> node, StringBuilder sb, String prefix) {


        if (node == null) {
            return;
        }
        sb.append(prefix).append(node.element).append("\n");
        toString(node.left, sb, prefix+"[L]");
        toString(node.right, sb, prefix+"[R]");
    }

}

