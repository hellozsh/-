package Leetcode.链表.栈;

import java.util.ArrayList;
import java.util.Stack;

/// https://leetcode-cn.com/problems/basic-calculator/submissions/
public class 基本计算器 {

    public int calculate(String s) {

        Stack leftStack = new Stack();
        Stack scoreStack = new Stack();

        char perch = ' ';
        for (int i = 0; i < s.length(); i++) {


            char ch = s.charAt(i);

            if (ch == ')' || ch == '+' || ch == '-' ){ // 右括号，做计算

                if (leftStack.isEmpty()) {

                } else {
                    char leftch = (char)leftStack.pop();

                    while (leftch != '(') { // 拿出栈里的2个元素做加法再入栈

                        int top = (int)scoreStack.pop();
                        int second = (int)scoreStack.pop();

                        if (leftch == '+') {
                            scoreStack.push((top+second));
                        } else if (leftch == '-') {
                            scoreStack.push((second - top));
                        }
                        if (!leftStack.isEmpty()) {
                            leftch = (char)leftStack.pop();
                        } else {
                            break;
                        }
                    }
                    if (leftch == '(' && ch != ')' ) {
                        leftStack.push(leftch);
                    }
                }

            }

            if (ch == '(' || ch == '+' || ch == '-')  {

                leftStack.push(ch);
            } else if (ch == ' ' || ch == ')') {

            } else {
                if (perch != '(' && perch != '+' && perch != '-' && perch != ' ') {

                   int scroe = (int)scoreStack.pop()*10+(int)(ch-48);
                    scoreStack.push(scroe);
                } else {
                    scoreStack.push((int)(ch-48));
                }
            }
            perch = ch;
        }


        while (!leftStack.isEmpty()) {

            char leftch = (char)leftStack.pop();

            int top = (int)scoreStack.pop();
            int second = (int)scoreStack.pop();

            if (leftch == '+') {
                scoreStack.push((top+second));
            } else if (leftch == '-') {
                scoreStack.push((second - top));
            }
        }

        int score = 0;
        int i = 1;
        while (!scoreStack.isEmpty()) {
            score = (int)scoreStack.pop()*i+score;
            i=i*10;
        }

        return score;
    }

    // 一个计算器实现+-*/()功能
    // 传入 1+2*3-(1+1)
    // 第一个for得出 123*+11+-
    // 第二个for得出 5
    public Double stackCalculate(String s) {

        ArrayList<String> array = new ArrayList();

        Stack<Character> yunsuanStack = new Stack();

        char perch = ' ';
        for (int i = 0; i < s.length(); i++) {

            char ch = s.charAt(i);

            if (ch == '(') {
                yunsuanStack.push(ch);
            } else if (ch == ')')  {

                char pop;
                while (!yunsuanStack.isEmpty()) {
                    pop = yunsuanStack.pop();
                    if (pop != '(' && pop != ')') {
                        array.add(Character.toString(pop));
                    } else {
                        perch = ch;
                        break;
                    }
                }
            } else if (ch == '+' || ch == '-' || ch == '*' || ch == '/') {
                char pop;
                if (yunsuanStack.isEmpty()) {
                    yunsuanStack.push(ch);
                } else {
                    while (!yunsuanStack.isEmpty()) {
                        pop = yunsuanStack.peek();
                        if ((pop == '+' || pop == '-') && (ch == '*' || ch == '/')) {
                            break;
                        } else if (pop == '(' || pop == ')') {
                            break;
                        } else {
                            array.add(Character.toString(yunsuanStack.pop()));
                        }
                    }
                    yunsuanStack.push(ch);
                }
            } else if (ch == ' ') {

            } else {
                if (perch != '/' && perch != '*' && perch != '(' && perch != '+' && perch != '-' && perch != ' ') {

                    String score =  array.get(array.size()-1) + (int)(ch-48);
                    array.set(array.size()-1, score);
                } else {
                    array.add(Character.toString(ch));
                }
            }
            perch = ch;
        }

        while (!yunsuanStack.isEmpty()) {
            array.add(Character.toString(yunsuanStack.pop()));
        }

        System.out.println(array);
        yunsuanStack.clear();
        Stack<Double> scoreStack = new Stack();

        for (int i = 0; i < array.size(); i++) {

            String num = array.get(i);
            if (num.equals("+") || num.equals("-") || num.equals("*") || num.equals("/")) {

                Double top = scoreStack.pop();
                Double second = scoreStack.pop();
                if (num.equals("+")) {
                    scoreStack.push((second+top));
                } else if (num.equals("-")) {
                    scoreStack.push((second-top));
                } else if (num.equals("*")) {
                    scoreStack.push((second*top));
                } else if (num.equals("/")) {
                    scoreStack.push((second/top));
                }
            } else {
                scoreStack.push(Double.parseDouble(num));
            }
        }
        return scoreStack.pop();
    }
}
