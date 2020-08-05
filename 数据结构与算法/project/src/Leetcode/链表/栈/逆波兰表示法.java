package Leetcode.链表.栈;

import java.util.Stack;
// https://leetcode-cn.com/problems/evaluate-reverse-polish-notation/comments/
public class 逆波兰表示法 {

    public int evalRPN(String[] tokens) {

        Stack leftStack = new Stack();
        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i].equals("+")) {
                int top = (int)leftStack.pop();
                int second = (int)leftStack.pop();
                leftStack.push((top+second));
            } else if (tokens[i]=="-") {
                int top = (int)leftStack.pop();
                int second = (int)leftStack.pop();
                leftStack.push((second-top));
            } else if (tokens[i]=="*") {
                int top = (int)leftStack.pop();
                int second = (int)leftStack.pop();
                leftStack.push((top*second));
            } else if (tokens[i]=="/") {
                int top = (int)leftStack.pop();
                int second = (int)leftStack.pop();
                leftStack.push((second/top));
            } else {
                 String str = tokens[i];

                Integer.valueOf(str).intValue();
                Integer integer = Integer.parseInt(str);

                leftStack.push(integer.intValue());
            }
        }

        return (int)leftStack.pop();
    }
}
