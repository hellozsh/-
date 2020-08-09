package 结构体实现.链表.队列;

import java.util.Arrays;

public class 循环双端队列<E> {

    private int front;
    private int size;
    private E[] elements = (E[]) new Object[10];
    private static final int DEFAULT_CAPACITY = 10;


    public 循环双端队列() {

    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void enQueueRear(E element) {

        ensureCapcity(size+1);

        int index = rearIndex();
        elements[index] = element;

        size++;
    }

    public void enQueueFront(E element) {

        ensureCapcity(size+1);
        // 拿到front前面位置
        int index = (front + elements.length - 1) % elements.length;
        elements[index] = element;

        front = index;
        size++;
    }

    public E deQueueFront() {
        E frontElement = elements[front];
        elements[front] = null;
        front = (front + 1) % elements.length;
        size--;
        return frontElement;
    }

    public E deQueueRear() {
        E rearElement = elements[rearIndex()];
        elements[rearIndex()] = null;
        size--;
        return rearElement;
    }

    public void clear() {

        for (int i = 0; i < elements.length; i++){
            elements[i] = null;
        }
        front = 0;
        size = 0;
    }

    public E front() {
        return elements[front];
    }

    public E Rear() {
        return elements[rearIndex()];
    }

    /*
      尽量避免使用乘*、除/、摸%、浮点数运算，效率低下
     */
    public int rearIndex(){

        // 因为front + size最大不会是大到elements.length的2倍，所以用这种方式效率会比取模高
        if (front + size < elements.length) {
            return front+size;
        } else {
           return front + size - elements.length;
        }
//        return ((front+size) % elements.length);
    }

    @Override
    public String toString() {
        return "循环双端队列{" +
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
