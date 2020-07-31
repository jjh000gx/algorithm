package alibaba.simple;

/**
 * 给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
 *
 * 示例:
 *
 * 输入: [-2,1,-3,4,-1,2,1,-5,4],
 * 输出: 6
 * 解释: 连续子数组 [4,-1,2,1] 的和最大，为 6。
 * 进阶:
 *
 * 如果你已经实现复杂度为 O(n) 的解法，尝试使用更为精妙的分治法求解。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/maximum-subarray
 */
public class GetNoSpaceSumOfArray {
    /**
     * 方法一：动态规划
     * 思路和算法
     *
     * 假设 nums 数组的长度是 nn，下标从 00 到 n - 1n−1。
     *
     * 我们用 a_iai
     * ​
     *   代表 nums[i]，用 f(i)f(i) 代表以第 ii 个数结尾的「连续子数组的最大和」，那么很显然我们要求的答案就是：
     *
     * \max_{0 \leq i \leq n - 1} \{ f(i) \}
     * 0≤i≤n−1
     * max
     * ​
     *  {f(i)}
     *
     * 因此我们只需要求出每个位置的 f(i)f(i)，然后返回 f 数组中的最大值即可。那么我们如何求 f(i)f(i) 呢？我们可以考虑 a_ia
     * i
     * ​
     *   单独成为一段还是加入 f(i - 1)f(i−1) 对应的那一段，这取决于 a_ia
     * i
     * ​
     *   和 f(i - 1) + a_if(i−1)+a
     * i
     * ​
     *   的大小，我们希望获得一个比较大的，于是可以写出这样的动态规划转移方程：
     *
     * f(i) = \max \{ f(i - 1) + a_i, a_i \}
     * f(i)=max{f(i−1)+a
     * i
     * ​
     *  ,a
     * i
     * ​
     *  }
     *
     * 不难给出一个时间复杂度 O(n)O(n)、空间复杂度 O(n)O(n) 的实现，即用一个 f 数组来保存 f(i)f(i) 的值，用一个循环求出所有 f(i)f(i)。考虑到 f(i)f(i) 只和 f(i - 1)f(i−1) 相关，于是我们可以只用一个变量 pre 来维护对于当前 f(i)f(i) 的 f(i - 1)f(i−1) 的值是多少，从而让空间复杂度降低到 O(1)O(1)，这有点类似「滚动数组」的思想。
     *
     * 作者：LeetCode-Solution
     * 链接：https://leetcode-cn.com/problems/maximum-subarray/solution/zui-da-zi-xu-he-by-leetcode-solution/
     * @param nums
     * @return
     */
    public int maxSubArray(int[] nums) {
        int max = nums[0];
        for(int i = 1; i< nums.length; i++ ) {
            if(nums[i] + nums[i-1] > nums[i]) {
                nums[i] += nums[i-1];
            }
            if(nums[i] > max) {
                max = nums[i];
            }
        }
        return max;
    }

    /**
     * 方法二：分治
     * 思路和算法
     *
     * 这个分治方法类似于「线段树求解 LCIS 问题」的 pushUp 操作。 也许读者还没有接触过线段树，没有关系，方法二的内容假设你没有任何线段树的基础。当然，如果读者有兴趣的话，推荐看一看线段树区间合并法解决 多次询问 的「区间最长连续上升序列问题」和「区间最大子段和问题」，还是非常有趣的。
     *
     * 我们定义一个操作 get(a, l, r) 表示查询 aa 序列 [l, r][l,r] 区间内的最大子段和，那么最终我们要求的答案就是 get(nums, 0, nums.size() - 1)。如何分治实现这个操作呢？对于一个区间 [l, r][l,r]，我们取 m = \lfloor \frac{l + r}{2} \rfloorm=⌊
     * 2
     * l+r
     * ​
     *  ⌋，对区间 [l, m][l,m] 和 [m + 1, r][m+1,r] 分治求解。当递归逐层深入直到区间长度缩小为 11 的时候，递归「开始回升」。这个时候我们考虑如何通过 [l, m][l,m] 区间的信息和 [m + 1, r][m+1,r] 区间的信息合并成区间 [l, r][l,r] 的信息。最关键的两个问题是：
     *
     * 我们要维护区间的哪些信息呢？
     * 我们如何合并这些信息呢？
     * 对于一个区间 [l, r][l,r]，我们可以维护四个量：
     *
     * lSum 表示 [l, r][l,r] 内以 ll 为左端点的最大子段和
     * rSum 表示 [l, r][l,r] 内以 rr 为右端点的最大子段和
     * mSum 表示 [l, r][l,r] 内的最大子段和
     * iSum 表示 [l, r][l,r] 的区间和
     * 以下简称 [l, m][l,m] 为 [l, r][l,r] 的「左子区间」，[m + 1, r][m+1,r] 为 [l, r][l,r] 的「右子区间」。我们考虑如何维护这些量呢（如何通过左右子区间的信息合并得到 [l, r][l,r] 的信息）？对于长度为 11 的区间 [i, i][i,i]，四个量的值都和 a_ia
     * i
     * ​
     *   相等。对于长度大于 11 的区间：
     *
     * 首先最好维护的是 iSum，区间 [l, r][l,r] 的 iSum 就等于「左子区间」的 iSum 加上「右子区间」的 iSum。
     * 对于 [l, r][l,r] 的 lSum，存在两种可能，它要么等于「左子区间」的 lSum，要么等于「左子区间」的 iSum 加上「右子区间」的 lSum，二者取大。
     * 对于 [l, r][l,r] 的 rSum，同理，它要么等于「右子区间」的 rSum，要么等于「右子区间」的 iSum 加上「左子区间」的 rSum，二者取大。
     * 当计算好上面的三个量之后，就很好计算 [l, r][l,r] 的 mSum 了。我们可以考虑 [l, r][l,r] 的 mSum 对应的区间是否跨越 mm——它可能不跨越 mm，也就是说 [l, r][l,r] 的 mSum 可能是「左子区间」的 mSum 和 「右子区间」的 mSum 中的一个；它也可能跨越 mm，可能是「左子区间」的 rSum 和 「右子区间」的 lSum 求和。三者取大。
     * 这样问题就得到了解决。
     *
     * 作者：LeetCode-Solution
     * 链接：https://leetcode-cn.com/problems/maximum-subarray/solution/zui-da-zi-xu-he-by-leetcode-solution/
     * 来源：力扣（LeetCode）
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     * @param argv
     */

    public static void main(String[] argv) {
        GetNoSpaceSumOfArray t = new GetNoSpaceSumOfArray();
        int[] nums = new int[]{-2,1,-3,4,-1,2,1,-5,4};
        System.out.println(t.maxSubArray(nums));

    }
}
