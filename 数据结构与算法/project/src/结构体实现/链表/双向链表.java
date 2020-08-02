package 结构体实现.链表;

public class 双向链表<E> extends 抽象类<E> {

    private static  class Node<E> {
        E element;
        Node<E> next;
        Node<E> prev;

        public  Node(Node<E> prev, E element, Node<E> next) {
            this.prev = prev;
            this.element = element;
            this.next = next;
        }
    }

    private Node<E> first;
    private Node<E> last; // 双向链表也可以从后往前找

    @Override
    public void add(int index, E element) {

        rangeCheckForAdd(index);
        // 拿到前一个元素
        if (index == size) { // 往最后一个位置放值,

            Node newNode = new Node(last ,element, null);
            if (last != null) {
                last.next = newNode;
            }
            last = newNode;
            if (index == 0) {
                first = newNode;
            }
        } else {

           Node<E> node = node(index);
           Node newNode = new Node(node.prev ,element, node);
           if (index != 0) {
               node.prev.next = newNode;
           } else {// 往位置0插入数据，
               first = newNode;
           }

           node.prev = newNode;
        }
        size++;
    }

    @Override
    public E remove(int index) {

        rangeCheck(index);
        Node<E> oldNode = first;
        // 拿到前一个元素
        if (index == 0) {
            first = first.next;
            if (first != null) {
                first.prev = null;
            }
        } else {
            Node oldPreNode = node(index-1);
            oldNode = oldPreNode.next;
            oldPreNode.next = oldNode.next;
            if (oldNode.next != null) { // 不是最后一位
                oldNode.next.prev = oldPreNode;
            } else {
                last = oldPreNode;
            }
        }
        size--;
        return oldNode.element;
    }

    @Override
    public E set(int index, E element) {

        Node<E> node = node(index);
        E old = node.element;
        node(index).element = element;
        return old;
    }

    @Override
    public E get(int index) {
        return node(index).element;
    }

    @Override
    public int indexOf(E element) {

        Node<E> node = first;
        for (int i = 0; i < size; i++) {
            if (node.element == null && element == null) return i;
            if (node.element.equals(element)) return i;
            node = node.next;
        }
        return ELEMENT_NOT_FOUND;
    }

    @Override
    public void clear() {

        first = null;
        size = 0;
    }

    private Node<E> node(int index) {

        // rangcheck
        rangeCheck(index);

        Node<E> node;
        if (index < (size >> 1)) {
            node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = last;
            for (int i = size-1; i > index; i--) {
                node = node.prev;
            }
        }
        return node;
    }


    @Override
    public String toString() {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("size=");
        stringBuilder.append(size);
        stringBuilder.append(", [");

        Node<E> node = first;
        while (node != null) {


            if (node.prev != null) {
                stringBuilder.append(node.prev.element);
            } else {
                stringBuilder.append("null");
            }
            stringBuilder.append("_");
            stringBuilder.append(node.element);
            stringBuilder.append("_");
            if (node.next != null) {
                stringBuilder.append(node.next.element);
            } else {
                stringBuilder.append("null");
            }
            stringBuilder.append(",");
            node = node.next;
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
