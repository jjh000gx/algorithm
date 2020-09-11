package alibaba.simple;

/**
 * 设计一个支持 push ，pop ，top 操作，并能在常数时间内检索到最小元素的栈。
 *
 * push(x) —— 将元素 x 推入栈中。
 * pop() —— 删除栈顶的元素。
 * top() —— 获取栈顶元素。
 * getMin() —— 检索栈中的最小元素。
 *  
 *
 * 示例:
 *
 * 输入：
 * ["MinStack","push","push","push","getMin","pop","top","getMin"]
 * [[],[-2],[0],[-3],[],[],[],[]]
 *
 * 输出：
 * [null,null,null,null,-3,null,0,-2]
 *
 * 解释：
 * MinStack minStack = new MinStack();
 * minStack.push(-2);
 * minStack.push(0);
 * minStack.push(-3);
 * minStack.getMin();   --> 返回 -3.
 * minStack.pop();
 * minStack.top();      --> 返回 0.
 * minStack.getMin();   --> 返回 -2.
 *  
 *
 * 提示：
 *
 * pop、top 和 getMin 操作总是在 非空栈 上调用。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/min-stack
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class MinStack {
    private int[] stack;
    private int[] miniStack;
    private int size;
    private int current;

    /** initialize your data structure here. */
    public MinStack() {
        stack     = new int[1];
        miniStack = new int[1];
        size    = 1;
        current = -1;
    }
    public void resize(int n) {
        int[] temp = new int[n];
        int[] temp1 = new int[n];
        for(int i=0; i < size; i++) {
            temp[i]  = stack[i];
            temp1[i] = miniStack[i];
        }
        size      = n;
        stack     = temp;
        miniStack = temp1;
    }
    public void push(int x) {
        if((current+1) == size) {
            resize(2*size);
        }
        int min   = x;
        if(current >=0) {
            if(miniStack[current] < x) {
                min = miniStack[current];
            }
        }
        stack[++current]    = x;
        miniStack[current] = min;
    }


    public void pop() {
        if(current > -1) {
            stack[current]    = 0;
            miniStack[current] = 0;
            current--;
        }
    }

    public int top() {
        return stack[current];
    }

    public int getMin() {
        return miniStack[current];
    }

    public static void main(String[] argv) {
        MinStack minStack = new MinStack();
        minStack.push(-2);
        minStack.push(0);
        minStack.push(-1);
        System.out.println(minStack.getMin());
        minStack.top();
        minStack.pop();

        System.out.println(minStack.getMin());

    }
}
