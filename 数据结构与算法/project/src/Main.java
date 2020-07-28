import Leetcode.链表.删除重复元素;
import Leetcode.链表.环形链表;
import Leetcode.链表.移除链表元素;
import Link.DynamicArray;
import Link.LinkedList;
import Link.List;
import sun.awt.image.ImageWatched;
import Leetcode.链表.反转链表;


public class Main {

    public static int fib1(int n) {

        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        return fib1(n-1) + fib1(n-2);
    }

    public static int fib2(int n) {

        int first = 0;
        int second = 1;
        int temp = 0;
        for (int i = 0; i< n-1; i++) {
            temp = first+second;
            first = second;
            second = temp;
        }
        return second;
    }

    // 线性代数解法
    public static int fib3(int n) {
        double c = Math.sqrt(5);
        return (int)((Math.pow((1+c)/2,n) - Math.pow((1-c)/2,n))/c);
    }

    // 0 1 1 2 3 5 8
    public static  void main(String[] args) {
        System.out.println("hello world!");

//        int n = 20;
//        Times.test("fib1", new Times.Task() {
//            @Override
//            public void execute() {
//                System.out.println(fib1(n));
//            }
//        });
//        Times.test("fib2", new Times.Task() {
//            @Override
//            public void execute() {
//                System.out.println(fib2(n));
//            }
//        });


        //
//        testDynamicArray();
//        testLinkedList();

        testLeetcode();

    }

    static void testLeetcode(){

        移除链表元素 value = new 移除链表元素();
        value.removeElements(value.testNode(), 1);
    }

    static void testDynamicArray(){

        DynamicArray<Integer> arr = new DynamicArray<>();

        arr.add(1);
        arr.add(2);
        arr.add(3);
        arr.add(5);

        arr.add(2,4);

        System.out.println(arr.toString());

        // 提醒JVM进行垃圾回收
        System.gc();
    }

   static void testLinkedList(){

        List<Integer> list = new LinkedList<>();
        list.add(20);
        list.add(0, 10);
        list.add(30);
        list.add(list.size(), 40);

        System.out.println(list);

        list.remove(1);
        System.out.println(list);
    }


}
