package 结构体实现.链表;

public abstract class 抽象类<E> implements 链表接口<E> {

    protected int size;
    /**
     * 添加元素到尾部
     * @param element
     */
    public void add(E element) {
        add(size, element);
    }
    /**
     * 删除尾部的元素
     * @return
     */
    public E remove() {
       return remove(size-1);
    }
    /**
     * 元素的数量
     * @return
     */
    public int size() {
        return size;
    }
    /**
     * 是否为空
     * @return
     */
    public boolean isEmpty() {
        return size == 0;
    }
    /**
     * 是否包含某个元素
     * @param element
     * @return
     */
    public boolean contains(E element) {

        return indexOf(element) != ELEMENT_NOT_FOUND;
    }

    protected void rangeCheckForAdd(int index) {
        if (index < 0 || index > size) {
            outOfBounds(index);
        }
    }

    protected void outOfBounds(int index) {
        throw new IndexOutOfBoundsException("Index:" + index + ", Size:" + size);
    }

    protected void rangeCheck(int index) {
        if (index < 0 || index >= size) {
            outOfBounds(index);
        }
    }

}
