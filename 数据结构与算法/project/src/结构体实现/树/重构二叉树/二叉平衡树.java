package 结构体实现.树.重构二叉树;
import java.util.Comparator;

public class 二叉平衡树<E> extends 二叉搜索树<E> {

    public 二叉平衡树() {
        this(null);
    }

    public 二叉平衡树(Comparator<E> comparator) {
        super(comparator);
    }

    protected void rotateAllRotation(Node<E> r, // 这个子树的根节点
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

        // e-f
        f.left = e;
        if (e != null) e.parent = f;

        // b-d-f
        d.left = b;
        d.right = f;
        b.parent = d;
        f.parent = d;
    }


    protected void rotateLeft(Node<E> grand) {
        Node<E> parent = grand.right;
        Node<E> child = parent.left;
        grand.right = child;
        parent.left = grand;

        afterRotation(grand, parent, child);
    }

    protected void rotateRight(Node<E> grand) {

        Node<E> parent = grand.left;
        Node<E> child = parent.right;
        grand.left = child;
        parent.right = grand;

        afterRotation(grand, parent, child);
    }

    protected void afterRotation(Node<E> grand, Node<E> parent, Node<E> child) {
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
    }

}
