package 结构体实现.栈;
import 结构体实现.链表.双向链表;

public class 栈<E> {

    private 双向链表<E> list = new 双向链表<>();

    public void clear() {
        list.clear();
    }

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public void push(E element) {
        list.add(element);
    }

    public void pop() {
        list.remove();
    }
}
