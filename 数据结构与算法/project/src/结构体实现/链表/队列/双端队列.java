package 结构体实现.链表.队列;

import 结构体实现.链表.双向链表;

// Deque
public class 双端队列<E> {

    private 双向链表<E> list = new 双向链表<>();

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public void enQueueRear(E element) {
        list.add(element);
    }

    public void enQueueFront(E element) {
        list.add(0, element);
    }

    public E deQueueFront() {

        return list.remove(0);
    }

    public E deQueueRear() {

        return list.remove();
    }

    public E front() {

        return list.get(0);
    }

    public E rear() {

        return list.get(list.size()-1);
    }

    public void clear() {

        list.clear();
    }

    @Override
    public String toString() {

        return "双端队列{" +
                "list=" + list +
                '}';
    }
}
