package Leetcode.链表.栈;

import java.util.Stack;

/// https://leetcode-cn.com/problems/score-of-parentheses/
public class 括号的分数 {

    public int scoreOfParentheses(String S) {

        Stack leftStack = new Stack();
        Stack scoreStack = new Stack();

        char peforch = ' ';
        for (int i = 0; i < S.length(); i++) {

           char ch = S.charAt(i);
           if (ch == '(') { // 左括号，入栈
               if (peforch == ')') { // 需要做加法
                   leftStack.push('+');
               }
               leftStack.push(ch);
           } else { // 右括号，做计算

               // 拿一个左边顶部
               if (peforch == '(') { // 是1
                   leftStack.pop();
                   scoreStack.push(1);
               } else if (peforch == ')') { // 做*2

                   char leftch = (char)leftStack.pop();
                   while (leftch == '+') { // 拿出栈里的2个元素做加法再入栈

                      int top = (int)scoreStack.pop();
                      int second = (int)scoreStack.pop();
                      scoreStack.push((top+second));

                      leftch = (char)leftStack.pop();
                   }
                   int top = (int)scoreStack.pop();
                   top = top*2;
                   scoreStack.push(top);
               }

           }

           peforch = ch;
        }

        int score = 0;
        while (!scoreStack.isEmpty()) {
            score = score + (int)scoreStack.pop();
        }

        return score;
    }

}
