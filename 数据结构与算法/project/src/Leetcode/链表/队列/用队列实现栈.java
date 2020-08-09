package Leetcode.链表.队列;

import java.util.LinkedList;

/// https://leetcode-cn.com/problems/implement-stack-using-queues/
public class 用队列实现栈 {


    private LinkedList<Integer> queue;


    /** Initialize your data structure here. */
    public 用队列实现栈() {
       queue = new LinkedList();
    }

    /** Push element x onto stack. */
    public void push(int x) {

        queue.push(x);
    }

    /** Removes the element on top of the stack and returns that element. */
    public int pop() {

        for (int i = 0; i < queue.size(); i++) {
            int o = queue.pop();
            queue.push(o);
        }
        return queue.pop();
    }

    /** Get the top element. */
    public int top() {

        int top = pop();
        queue.push(top);
        return top;
    }

    /** Returns whether the stack is empty. */
    public boolean empty() {

        return queue.isEmpty();
    }
}
