package Leetcode.链表.栈;


import java.util.Stack;

public class 有效的括号 {

    public boolean isValid(String s) {

        Stack leftStack = new Stack();
        for (int i = 0; i < s.length(); i++) {
           char ch = s.charAt(i);
           if (ch == '(' || ch == '[' || ch == '{') {
               leftStack.push(ch);
           } else {

               if (leftStack.empty()) return false;
               char left = (char)leftStack.pop();
               if ((left == '(') && (ch != ')')) return false;
               if ((left == '[') && (ch != ']')) return false;
               if ((left == '{') && (ch != '}')) return false;
           }
        }
        if (leftStack.empty()) {
            return true;
        } else {
            return false;
        }
    }
}
