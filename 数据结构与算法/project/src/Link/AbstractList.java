package Link;

public abstract class AbstractList<E> implements List<E> {

    protected int size;


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

    /**
     * 添加元素到尾部
     * @param element
     */

    // 复杂度 最好 O(1)  最坏是扩容的时候，扩容方法 O(n)  平均 O(1)+O(1)+...O(1)+O(n) == O(1)
    /*
      均摊复杂度: O(n)平均到之前的每个O(1)上，那么之前的O(1)其实是O(2),O(2)也是用O(1)来表示
      均摊复杂度: 经过连续的多次复杂度比较低的情况后，出现个别复杂度高的情况
     */
    public void add(E element) {
        add(size, element);
    }

    /**
     * 元素的数量
     * @return
     */
    public int size() {
        return size;
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
