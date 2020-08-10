package 结构体实现.树.重构二叉树;

import java.util.Comparator;

public class AVL树<E> extends 二叉搜索树 {

    public AVL树() { }

    public AVL树(Comparator<E> comparator) {
        super(comparator);
    }
}
