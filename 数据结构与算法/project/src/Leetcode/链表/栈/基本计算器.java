package Leetcode.链表.栈;

import java.util.Stack;

public class 基本计算器 {

    public int calculate(String s) {

        Stack leftStack = new Stack();
        Stack scoreStack = new Stack();

        for (int i = 0; i < s.length(); i++) {

            char ch = s.charAt(i);
            if (ch == '(' || ch == '+' || ch == '-')  {

                leftStack.push(ch);
            } else if (ch == ')'){ // 右括号，做计算

                char leftch = (char)leftStack.pop();
                while (leftch != '(') { // 拿出栈里的2个元素做加法再入栈

                    int top = (int)scoreStack.pop();
                    int second = (int)scoreStack.pop();

                    if (leftch == '+') {
                        scoreStack.push((top+second));
                    } else if (leftch == '-') {
                        scoreStack.push((second - top));
                    }
                    leftch = (char)leftStack.pop();
                }
            } else if (ch == ' ') {

            } else {


                scoreStack.push((int)(ch-48));
            }
        }


        while (!leftStack.isEmpty()) { // 拿出栈里的2个元素做加法再入栈

            char leftch = (char)leftStack.pop();

            int top = (int)scoreStack.pop();
            int second = (int)scoreStack.pop();

            if (leftch == '+') {
                scoreStack.push((top+second));
            } else if (leftch == '-') {
                scoreStack.push((second - top));
            }
        }

        return (int)scoreStack.pop();
    }
}
