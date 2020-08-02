package 结构体实现.链表;

public class 双向循环链表<E> extends 抽象类<E> {

    private static  class Node<E> {
        E element;
        Node<E> next;
        Node<E> prev;

        public  Node(Node<E> prev, E element, Node<E> next) {
            this.prev = prev;
            this.element = element;
            this.next = next;
        }

        @Override
        public String toString() {
            StringBuilder string = new StringBuilder();

            if (prev != null) {
                string.append(prev.element);
            } else {
                string.append("null");
            }
            string.append("_").append(element).append("_");

            if (next != null) {
                string.append(next.element);
            } else {
                string.append("null");
            }
            return  string.toString();
        }
    }

    private Node<E> first;
    private Node<E> last; // 双向链表也可以从后往前找


    @Override
    public void add(int index, E element) {

        rangeCheckForAdd(index);
        // 拿到前一个元素
        if (index == size) { // 往最后一个位置放值,

            Node newNode = new Node(last ,element, first);
            if (last != null) {
                last.next = newNode;

            }
            last = newNode;
            last.next = first;
            if (index == 0) {
                first = newNode;
                first.prev = last;
                last.next = first;

                last = first;
            }
            first.prev = last;
        } else {

            Node<E> node = node(index);
            Node newNode = new Node(node.prev ,element, node);
            if (index != 0) {
                node.prev.next = newNode;
            } else {// 往位置0插入数据，
                first = newNode;
                last.next = first;
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
            if (size == 1) {
                first = null;
                last = null;
            } else {
                first = first.next;
                first.prev = last;
                last.next = first;
            }
        } else {
            Node oldPreNode = node(index-1);
            oldNode = oldPreNode.next;
            oldPreNode.next = oldNode.next;
            if (index != size-1) { // 不是最后一位
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

    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("size=").append(this.size).append(", [");
        Node<E> node = this.first;

        for(int i = 0; i < this.size; ++i) {
            if (i != 0) {
                string.append(", ");
            }

            string.append(node);
            node = node.next;
        }

        string.append("]");
        return string.toString();
    }
}
