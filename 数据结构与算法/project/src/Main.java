import Leetcode.树.二叉树的前序遍历;
import Leetcode.树.从前序和中序遍历序列构造二叉树;
import Leetcode.链表.栈.有效的括号;
import Leetcode.链表.栈.括号的分数;
import Leetcode.链表.栈.逆波兰表示法;
import Leetcode.链表.栈.基本计算器;
import Link.DynamicArray;
import Link.SingleLinkedList;
import Link.List;
import Link.LinkedList;
import com.sun.source.tree.BinaryTree;
import sun.tools.jconsole.inspector.XObject;
import 结构体实现.树.Person;
import 结构体实现.树.printer.BinaryTrees;
import 结构体实现.链表.单向链表;
import 结构体实现.链表.双向循环链表;
import 结构体实现.链表.双向链表;
import 结构体实现.链表.抽象类;
import 结构体实现.链表.队列.循环双端队列;
import 结构体实现.链表.队列.循环队列;
import 结构体实现.链表.队列.队列;
import 结构体实现.链表.队列.双端队列;
import 结构体实现.树.二叉搜索树;

import java.util.Comparator;

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
//        test结构体实现();

    }

    static void testLeetcode(){

        有效的括号 value = new 有效的括号();
//        value.isValid("()");

        括号的分数 value1 = new 括号的分数();
//        value1.scoreOfParentheses("(()(()))");

        逆波兰表示法 value2 = new 逆波兰表示法();
        String[] list = {"4","13","5","/","+"};
//        value2.evalRPN(list);

        基本计算器 value3 = new 基本计算器();
//        System.out.println(value3.stackCalculate("1-11"));

        从前序和中序遍历序列构造二叉树 value4 = new 从前序和中序遍历序列构造二叉树();
//        从前序和中序遍历序列构造二叉树.TreeNode node =value4.buildTree(new int[]{3, 9, 20, 15, 7}, new int[]{9, 3, 15, 20, 7});
//        System.out.println(node);




        二叉树的前序遍历 value5 = new 二叉树的前序遍历();
        二叉树的前序遍历.TreeNode root = value5.testNode();

        value5.preorderTraversal(root);
    }

    static void test结构体实现(){

        单向链表<Integer> arr1 = new 单向链表<>();

        双向链表<Integer> arr2 = new 双向链表<>();

        双向循环链表<Integer> arr3 = new 双向循环链表<>();

//        test链表(arr3);
//        test队列();
//        test双端队列();
//        test循环队列();
//        test循环双端队列();
        test搜索二叉树();
    }

    static void test搜索二叉树() {
        Integer data[] = new Integer[] {
                7,4,9,2,5,8,11,1,3,10,12
        };
        二叉搜索树 bst = new 二叉搜索树();
        for (int i = 0; i < data.length; i++) {
            bst.add(data[i]);
        }
        bst.add(12);
        bst.add(11);
        BinaryTrees.println(bst);

        System.out.println("--------前序--------");
        bst.preorderTraversal();
        System.out.println("--------中序--生序--------");
        bst.inorderTraversalASC();
        System.out.println("--------中序--降序--------");
        bst.inorderTraversalDSC();
        System.out.println("--------后序--------");
        bst.postorderTraversal();
        System.out.println("--------层序--------");
        bst.levelorderTraversal();

        bst.levelorderTraversal(new 二叉搜索树.Visitor() {
            @Override
            public void visit(Object element) {
                System.out.print("_"+element+"_");
            }
        });

        System.out.println(bst);

        System.out.println(bst.heightDieDai());
        System.out.println(bst.heightDiGui());

        System.out.println(bst.isComplete());
//        二叉搜索树 bst1 = new 二叉搜索树(new Comparator<Person>() {
//            @Override
//            public int compare(Person o1, Person o2) {
//                return o2.getAge()-o1.getAge();
//            }
//        });
//        bst1.add(new Person(12));
//        bst1.add(new Person(15));
//
//        二叉搜索树 bst2 = new 二叉搜索树();
//        bst2.add(new Person(12));
//        bst2.add(new Person(15));
    }

    static void test队列() {

        队列<Integer> queue = new 队列<>();

        queue.enQueue(11);
        queue.enQueue(22);
        queue.enQueue(33);
        queue.enQueue(44);

        while (!queue.isEmpty()) {
            System.out.println(queue.deQueue());
        }
    }

    static void test双端队列() {

        双端队列<Integer> queue = new 双端队列<>();

        queue.enQueueFront(11);
        queue.enQueueFront(22);
        queue.enQueueRear(33);
        queue.enQueueRear(44);

        System.out.println(queue); // 尾 44 33 11 22 头

        while (!queue.isEmpty()) {
            System.out.println(queue.deQueueFront());
        }
    }

    static void test循环队列() {

        循环队列<Integer> queue = new 循环队列<>();
        for (int i = 0; i < 10; i++) {
            queue.enQueue(i);
        }
        for (int i = 0; i < 5; i++) {
            queue.deQueue();
        }
        for (int i = 15; i < 20; i++) {
            queue.enQueue(i);
        }
        System.out.println(queue);
        queue.enQueue(20);
        System.out.println(queue);
        while (!queue.isEmpty()) {
            System.out.println(queue.deQueue());
        }
    }

    static void test循环双端队列() {

        循环双端队列<Integer> queue = new 循环双端队列<>();
        for (int i = 0; i < 10; i++) {
            queue.enQueueFront(i+1);
            queue.enQueueRear(i+100);
        }
        for (int i = 0; i < 3; i++) {
            queue.deQueueFront();
            queue.deQueueRear();
        }

        System.out.println(queue);
        queue.enQueueFront(11);
        queue.enQueueFront(12);
        System.out.println(queue);
        while (!queue.isEmpty()) {
            System.out.println(queue.deQueueFront());
        }
    }

    static void test链表(抽象类 arr){

        arr.add(1);
        arr.add(3);
        arr.add(1,2);
        arr.add(4);  // 1, 2, 3, 4
        arr.remove(1); // 1, 3, 4
        arr.remove();  // 1,3
        arr.set(0, 6);  // 6,3
        arr.add(arr.size(), 7); // 6,3,7
        System.out.println(arr.indexOf(3) == 1);  // true
        System.out.println(arr.contains(3));  // true
        System.out.println(arr.get(0));  // 6

        System.out.println(arr);
    }


    static void testDynamicArray(){

        DynamicArray<Integer> arr = new DynamicArray<>();

        arr.add(1);
        arr.add(3);
        arr.add(1,2);
        arr.add(4);

        arr.remove(1);


        System.out.println(arr.toString());

        // 提醒JVM进行垃圾回收
        System.gc();
    }

   static void testSingleLinkedList(){

        List<Integer> list = new SingleLinkedList<>();
        list.add(20);
        list.add(0, 10);
        list.add(30);
        list.add(list.size(), 40);

        System.out.println(list);

        list.remove(1);
        System.out.println(list);
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
