package Link;

public class LinkedList<E> extends AbstractList<E> {

    private Node<E> first;
    private Node<E> last;

    private static  class Node<E> {
        E element;
        Node<E> next;
        Node<E> prev;

        public  Node(E element, Node<E> next, Node<E> prev) {
            this.element = element;
            this.next = next;
            this.prev = prev;
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

    /*
    虽然双向链表里的内容你指向我，我指向你，只是first和last置空，貌似双向链表里的元素由于互相引用，无法达到释放
    但是java的内存管理知识: 只有gc root对象的指向才会让一个对象不会释放，
       gc root对象:
         1>被局部变量指向的对象
         2>...
    所以java里最终都会释放
     */
    @Override
    public void clear() {  // java的内存管理知识：

        size = 0;
        first = null;
        last = null;
    }

    @Override
    public E get(int index) {
        return node(index).element;
    }

    @Override
    public E set(int index, E element) {

       Node<E> node = node(index);
       E old = node.element;
        node(index).element = element;
        return old;
    }

    @Override
    public void add(int index, E element) {

        rangeCheckForAdd(index);

        if (index == size) { // 往最后面添加元素

            Node oldLast = last;
            last = new Node<>(element, null, last);
            if (oldLast == null) {

                first = last ;
            } else {
                oldLast.next = last;
            }
            last.next = new Node<>(element, null, last);
            last = last.next;
        } else {
            Node next = node(index);
            Node prev = next.prev;
            Node node = new Node(element,next,prev);

            next.prev = node;
            if (prev != null) {
                prev.next = node;
            } else {
                first = node;
            }
        }


        size++;
    }

    @Override
    public E remove(int index) {

        rangeCheck(index);

        Node<E> node = node(index);
        if (node.prev == null) { // index == 0

            first = node.next;
        } else {
            node.prev.next = node.next;
        }

        if (node.next == null) { // index = size-1

            last = node.prev;
        } else {
            node.next.prev = node.prev;
        }

        size--;
        return node.element;
    }

    @Override
    public int indexOf(E element) {
        Node<E> node = first;
        if (element == null) {
            for (int i = 0; i < size; i++) {
                if (node.element == null) return i;
                node = node.next;
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (element.equals(node.element)) return i; // n
                node = node.next;
            }
        }
        return ELEMENT_NOT_FOUND;
    }

    private Node<E> node(int index) {

        rangeCheck(index);

        Node<E> node;
        if (index < (size >> 1)) {
            node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = last;
            for (int i = 0; i < (size-index); i++) {
                node = node.prev;
            }
        }

        return node;
    }


}
