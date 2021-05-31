package other.simple;

/**
 * 一个有名的按摩师会收到源源不断的预约请求，每个预约都可以选择接或不接。在每次预约服务之间要有休息时间，因此她不能接受相邻的预约。给定一个预约请求序列，替按摩师找到最优的预约集合（总预约时间最长），返回总的分钟数。
 *
 * 注意：本题相对原题稍作改动
 *
 *  
 *
 * 示例 1：
 *
 * 输入： [1,2,3,1]
 * 输出： 4
 * 解释： 选择 1 号预约和 3 号预约，总时长 = 1 + 3 = 4。
 * 示例 2：
 *
 * 输入： [2,7,9,3,1]
 * 输出： 12
 * 解释： 选择 1 号预约、 3 号预约和 5 号预约，总时长 = 2 + 9 + 1 = 12。
 * 示例 3：
 *
 * 输入： [2,1,4,5,3,1,1,3]
 * 输出： 12
 * 解释： 选择 1 号预约、 3 号预约、 5 号预约和 8 号预约，总时长 = 2 + 4 + 3 + 3 = 12。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/the-masseuse-lcci
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Massage {
    /**
     * 方法一：动态规划
     * 思路和算法
     *
     * 定义 dp[i][0]dp[i][0] 表示考虑前 ii 个预约，第 ii 个预约不接的最长预约时间，dp[i][1]dp[i][1] 表示考虑前 ii 个预约，第 ii 个预约接的最长预约时间。
     *
     * 从前往后计算 dpdp 值，假设我们已经计算出前 i-1i−1 个 dpdp 值，考虑计算 dp[i][0/1]dp[i][0/1] 的答案。
     *
     * 首先考虑 dp[i][0]dp[i][0] 的转移方程，由于这个状态下第 ii 个预约是不接的，所以第 i-1i−1 个预约接或不接都可以，故可以从 dp[i-1][0]dp[i−1][0] 和 dp[i-1][1]dp[i−1][1] 两个状态转移过来，转移方程即为：
     *
     * dp[i][0]=max(dp[i-1][0],dp[i-1][1])
     * dp[i][0]=max(dp[i−1][0],dp[i−1][1])
     *
     * 对于 dp[i][1]dp[i][1] ，由于这个状态下第 ii 个预约要接，根据题目要求按摩师不能接受相邻的预约，所以第 i-1i−1 个预约不能接受，故我们只能从 dp[i-1][0]dp[i−1][0] 这个状态转移过来，转移方程即为：
     *
     * dp[i][1]=dp[i-1][0]+\textit{nums}_i
     * dp[i][1]=dp[i−1][0]+numsi
     * ​
     * 其中 \textit{nums}_inumsi
     * ​
     *   表示第 ii 个预约的时长。
     *
     * 最后答案即为 max(dp[n][0],dp[n][1])max(dp[n][0],dp[n][1]) ，其中 nn 表示预约的个数。
     *
     * 再回来看转移方程，我们发现计算 dp[i][0/1]dp[i][0/1] 时，只与前一个状态 dp[i-1][0/1]dp[i−1][0/1] 有关，所以我们可以不用开数组，只用两个变量 dp_0dp
     * 0，dp_1dp1
     * ​
     *   分别存储 dp[i-1][0]dp[i−1][0] 和 dp[i-1][1]dp[i−1][1] 的答案，然后去转移更新答案即可。
     *
     * 作者：LeetCode-Solution
     * 链接：https://leetcode-cn.com/problems/the-masseuse-lcci/solution/an-mo-shi-by-leetcode-solution/
     * 来源：力扣（LeetCode）
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     * @param nums
     * @return
     */
    public int massage(int[] nums) {
        int len = nums.length;
        if(0 == len) {
            return 0;
        }
        int dp0 = 0;
        int dp1 = nums[0];
        for(int i =1; i< len;i++) {
            int tdp0 = Math.max(dp0,dp1);
            int tdp1 = dp0 + nums[i];
            dp0 = tdp0;
            dp1 = tdp1;
        }
        return Math.max(dp0,dp1);
    }

    public static void main(String[] argv) {
        Massage tt = new Massage();
        int[] nums = new int[]{2,1,4,5,3,1,1,3};
        int y = tt.massage(nums);
        System.out.println(y);
    }
}
