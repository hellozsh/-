package 结构体实现.链表;

public interface 链表接口<E> {

    static final int ELEMENT_NOT_FOUND = -1; // 未找到元素
    /**
     * 添加元素到尾部
     * @param element
     */
    public void add(E element);
    /**
     * 在index位置插入一个元素
     * @param index
     * @param element
     */
    public void add(int index, E element);
    /**
     * 删除index位置的元素
     * @param index
     * @return
     */
    public E remove(int index);
    /**
     * 删除尾部的元素
     * @return
     */
    public E remove();
    /**
     * 设置index位置的元素
     * @param index
     * @param element
     * @return 原来的元素ֵ
     */
    public E set(int index, E element);
    /**
     * 获取index位置的元素
     * @param index
     * @return
     */
    public E get(int index);
    /**
     * 是否包含某个元素
     * @param element
     * @return
     */
    public boolean contains(E element);
    /**
     * 查看元素的索引
     * @param element
     * @return
     */
    public int indexOf(E element);
    /**
     * 元素的数量
     * @return
     */
    public int size();
    /**
     * 清空元素
     * @return
     */
    public void clear();
    /**
     * 是否为空
     * @return
     */
    public boolean isEmpty();

}
