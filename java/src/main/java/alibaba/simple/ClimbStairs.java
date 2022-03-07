package alibaba.simple;

import java.math.BigDecimal;

/**
 * 假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
 *
 * 每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
 *
 * 注意：给定 n 是一个正整数。
 *
 * 示例 1：
 *
 * 输入： 2
 * 输出： 2
 * 解释： 有两种方法可以爬到楼顶。
 * 1.  1 阶 + 1 阶
 * 2.  2 阶
 * 示例 2：
 *
 * 输入： 3
 * 输出： 3
 * 解释： 有三种方法可以爬到楼顶。
 * 1.  1 阶 + 1 阶 + 1 阶
 * 2.  1 阶 + 2 阶
 * 3.  2 阶 + 1 阶
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/climbing-stairs
 */
public class ClimbStairs {
    /**
     * 太费时间，leetcode 不通过，不能使用递归
     * @param n
     * @return
     */
    public int climbStairs1(int n) {
        if(n<1) {
            return 0;
        }
        if(1 == n) {
            return 1;
        }
        if(2 == n) {
            return 2;
        }
        return climbStairs1(n-1) + climbStairs1(n-2);
    }

    /**
     * 方法一：动态规划
     * 思路和算法
     *
     * 我们用 f(x)f(x) 表示爬到第 xx 级台阶的方案数，考虑最后一步可能跨了一级台阶，也可能跨了两级台阶，所以我们可以列出如下式子：
     *
     * f(x) = f(x - 1) + f(x - 2)
     * f(x)=f(x−1)+f(x−2)
     *
     * 它意味着爬到第 xx 级台阶的方案数是爬到第 x - 1x−1 级台阶的方案数和爬到第 x - 2x−2 级台阶的方案数的和。很好理解，因为每次只能爬 11 级或 22 级，所以 f(x)f(x) 只能从 f(x - 1)f(x−1) 和 f(x - 2)f(x−2) 转移过来，而这里要统计方案总数，我们就需要对这两项的贡献求和。
     *
     * 以上是动态规划的转移方程，下面我们来讨论边界条件。我们是从第 00 级开始爬的，所以从第 00 级爬到第 00 级我们可以看作只有一种方案，即 f(0) = 1f(0)=1；从第 00 级到第 11 级也只有一种方案，即爬一级，f(1) = 1f(1)=1。这两个作为边界条件就可以继续向后推导出第 nn 级的正确结果。我们不妨写几项来验证一下，根据转移方程得到 f(2) = 2f(2)=2，f(3) = 3f(3)=3，f(4) = 5f(4)=5......我们把这些情况都枚举出来，发现计算的结果是正确的。
     *
     * 我们不难通过转移方程和边界条件给出一个时间复杂度和空间复杂度都是 O(n)O(n) 的实现，但是由于这里的 f(x)f(x) 只和 f(x - 1)f(x−1) 与 f(x - 2)f(x−2) 有关，所以我们可以用「滚动数组思想」把空间复杂度优化成 O(1)O(1)。下面的代码中给出的就是这种实现。
     *
     * 作者：LeetCode-Solution
     * 链接：https://leetcode-cn.com/problems/climbing-stairs/solution/pa-lou-ti-by-leetcode-solution/
     * @param n
     * @return
     */
    public int climbStairs(int n) {
        if(n<1) {
            return 0;
        }
        int p=0, q = 0, r = 1;
        for(int i=1; i<= n; i++) {
            p=q;
            q=r;
            r = p+q;
        }
        return r;
    }
    public static void main(String[] argv) {
        ClimbStairs t = new ClimbStairs();
        System.out.println(t.climbStairs(30));
        t.getClass().getClassLoader();

    }
}
