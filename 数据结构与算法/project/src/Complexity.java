public class Complexity {

    public static void test1(int n) {

        if (n > 10) {
            System.out.println("n > 10");
        } else if (n > 5) { // 2
            System.out.println("n > 5");
        } else {
            System.out.println("n <= 5");
        }

        //时间复杂度: 1 + 4 + 4 + 4 (i = 0一次，i<4和i++以及打印都是4次)
        for (int i = 0; i < 4; i++) {
            System.out.println("test");
        }

        // 时间复杂度SUM: 14
        // O(1)
        // 空间复杂度: O(1)
    }

    public static void test2(int n) {
        // 时间复杂度SUM: 1 + 3n

        // O(n)
        for (int i = 0; i < n; i++) {
            System.out.println("test");
        }
    }

    public static void test3(int n) {
        //时间复杂度SUM: 1 + 2n + n * (1 + 3n)
        // 1 + 2n + n + 3n^2
        // 3n^2 + 3n + 1

        // O(n^2)
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.println("test");
            }
        }
    }

    public static void test4(int n) {
        //时间复杂度SUM: 1 + 2n + n * (1 + 45)
        // 1 + 2n + 46n
        // 48n + 1

        // O(n)
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 15; j++) {
                System.out.println("test");
            }
        }
    }

    public static void test5(int n) {
        //假设n=8，执行了3次； 8 = 2^3
        //假设n=16，执行了4次； 16 = 2^4

        // 3 = log2(8)
        // 4 = log2(16)

        // 执行次数 = log2(n)
        // 时间复杂度SUM: log2(n)

        // O(logn)
        while ((n = n / 2) > 0) {
            System.out.println("test");
        }
    }

    public static void test6(int n) {
        // log5(n)

        // O(logn)
        while ((n = n / 5) > 0) {
            System.out.println("test");
        }
    }

    public static void test7(int n) {
        // 1 + 2*log2(n) + log2(n) * (1 + 3n)
        // 1 + 3*log2(n) + 2 * nlog2(n)

        // O(nlogn)
        for (int i = 1; i < n; i = i * 2) {
            // 里面这个for； 1 + 3n
            for (int j = 0; j < n; j++) {
                System.out.println("test");
            }
        }
    }

    public static void test8(int n) {
        int a = 10;
        int b = 20;
        int c = a + b;
        int[] array = new int[n];
        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i] + c);
        }
        // 空间复杂度: O(n)
    }

    public static void test9(int n, int k) {
        // O(n+k)
        for (int i = 0; i < n; i++) {
            System.out.println("test");
        }
        for (int i = 0; i < k; i++) {
            System.out.println("test");
        }
    }
}
