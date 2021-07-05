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



    public int maxSubArray1(int[] nums) {
        int pre = 0, maxAns = nums[0];
        for (int x : nums) {
            pre = Math.max(pre + x, x);
            maxAns = Math.max(maxAns, pre);
        }
        return maxAns;
    }


    public static void main(String[] argv) {
        GetNoSpaceSumOfArray t = new GetNoSpaceSumOfArray();
        int[] nums = new int[]{-2,1,-3,4,-1,2,1,-5,4};
        System.out.println(t.maxSubArray(nums));

    }
}
