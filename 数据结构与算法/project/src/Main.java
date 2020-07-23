import Second.DynamicArray;

import java.util.ArrayList;

class Solution {
    public int maxCoins(int[] nums) {

        int left = 1;
        int right = 1;
        int sum = 0;
        for (int i = 1; i < nums.length; i++) {

            if (i < nums.length-1) {
                right = nums[i+1];
            } else  {
                right = 1;
            }

            if (left>=nums[i]) {

            } else {

            }
            if (left >= nums[i] && right >= nums[i]) {
                sum += left *nums[i]*right;
            } else {

            }

        }
        return 0;
    }

}

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

        ArrayList
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


}
