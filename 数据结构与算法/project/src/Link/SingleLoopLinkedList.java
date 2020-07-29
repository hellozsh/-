package Link;

// 单向循环链表---即最后一个的next指向第一个
public class SingleLoopLinkedList<E> extends AbstractList<E> {

    private Node<E> first;

    private static  class Node<E> {
        E element;
        Node<E> next;

        public  Node(E element, Node<E> next) {
            this.element = element;
            this.next = next;
        }
    }

    @Override
    public void clear() {

        size = 0;
        first = null;
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

        /*
          最好情况是  O(1)
          最好情况是  O(n)
          平均情况是  O(n)
         */
        rangeCheckForAdd(index);

        if (index == 0) {
            Node<E> newFirst = new Node<E>(element, first.next);
           // 拿到最后一个节点
            Node<E> last = (size == 0) ? newFirst : node(size-1);

            first = newFirst;
            last.next = first;
        }  else {
            Node<E> pre = node(index-1);
            pre.next = new Node<E>(element, pre.next);
        }

        size++;
    }

    @Override
    public E remove(int index) {

        /*
          最好情况是  O(1)
          最好情况是  O(n)
          平均情况是  O(n)
         */
        rangeCheck(index);

        Node<E> node = first;
        if (index == 0) {
            if (size == 1) {
                first = null;
            } else {
                Node<E> last = node(size -1);
                last.next = first.next;
                first = first.next;
            }
        } else {
            Node<E> pre = node(index-1);
            node = pre.next;
            pre.next = pre.next.next;
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
        /*
          最好情况是  O(1)
          最好情况是  O(n)
          平均情况是  O(n)
         */
        rangeCheck(index);

        Node<E> node = first;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("size=").append(size).append(", [");
        Node<E> node = first;
        for (int i = 0; i < size; i++) {
            if (i != 0) {
                string.append(", ");
            }

            string.append(node.element);
            node = node.next;

//			if (i != size - 1) {
//				string.append(", ");
//			}
        }
        string.append("]");
        return string.toString();
    }
}
