package 结构体实现.链表;

public class 单向链表<E> extends 抽象类<E> {
    private static  class Node<E> {
        E element;
        Node<E> next;

        public  Node(E element, Node<E> next) {
            this.element = element;
            this.next = next;
        }
    }

    private Node<E> first;

    @Override
    public void add(int index, E element) {

        rangeCheckForAdd(index);
        // 拿到前一个元素
        if (index == 0) {

            Node newNode = new Node(element, first);
            first = newNode;
        } else {
            Node oldPreNode = node(index-1);
            Node newNode = new Node(element, oldPreNode.next);
            oldPreNode.next = newNode;
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
        } else {
            Node oldPreNode = node(index-1);
            oldNode = oldPreNode.next;
            oldPreNode.next = oldPreNode.next.next;
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

        Node node = first;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }


    @Override
    public String toString() {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("size=");
        stringBuilder.append(size);
        stringBuilder.append(", [");

        Node node = first;
        while (node != null) {

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
