package 结构体实现.队列;

import java.util.Arrays;

public class 循环队列<E> {

    private int front;
    private int size;
    private E[] elements = (E[]) new Object[10];
    private static final int DEFAULT_CAPACITY = 10;


    public 循环队列() {

    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void enQueue(E element) {

        ensureCapcity(size+1);

        int index = (front + size) % elements.length;
        elements[index] = element;

        size++;
    }

    public E deQueue() {
        E frontElement = elements[front];
        elements[front] = null;
        front = (front + 1) % elements.length;
        size--;
        return frontElement;
    }
    public E front() {
        return elements[front];
    }

    @Override
    public String toString() {
        return "循环队列{" +
                "front=" + front +
                ", size=" + size +
                ", elements=" + Arrays.toString(elements) +
                '}';
    }

    private void ensureCapcity(int capacity) {

        int oldCapacity = elements.length;
        if (oldCapacity >= capacity) return;

        int newCapacity = oldCapacity + (oldCapacity >> 1);
        E[] newElements = (E[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newElements[i] = elements[(front+i) % oldCapacity];
        }
        elements = newElements;
        front = 0;
        System.out.println(oldCapacity + "扩容为" + newCapacity);
    }
}
