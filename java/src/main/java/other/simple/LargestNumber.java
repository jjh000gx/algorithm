package other.simple;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 给定一组非负整数，重新排列它们的顺序使之组成一个最大的整数。
 *
 * 示例 1:
 *
 * 输入: [10,2]
 * 输出: 210
 * 示例 2:
 *
 * 输入: [3,30,34,5,9]
 * 输出: 9534330
 * 说明: 输出结果可能非常大，所以你需要返回一个字符串而不是整数
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/largest-number
 */
public class LargestNumber {
    private class LargestNumberComparator implements Comparator<String> {

        @Override
        public int compare(String o1, String o2) {
            String or1 = o1 + o2;
            String or2 = o2 + o1;
            return or2.compareTo(or1);
        }
    }

    /**
     * 自定义排序：
     * 想法
     *
     * 为了构建最大数字，我们希望越高位的数字越大越好。
     *
     * 算法
     *
     * 首先，我们将每个整数变成字符串。然后进行排序。
     *
     * 如果仅按降序排序，有相同的开头数字的时候会出现问题。比方说，样例 2 按降序排序得到的数字是 9534330395343303 ，然而交换 33 和 3030 的位置可以得到正确答案 95343309534330 。因此，每一对数在排序的比较过程中，我们比较两种连接顺序哪一种更好。我们可以证明这样的做法是正确的：
     *
     * 假设（不是一般性），某一对整数 aa 和 bb ，我们的比较结果是 aa 应该在 bb 前面，这意味着 a\frown b > b\frown aa⌢b>b⌢a ，其中 \frown⌢ 表示连接。如果排序结果是错的，说明存在一个 cc ， bb 在 cc 前面且 cc 在 aa 的前面。这产生了矛盾，因为 a\frown b > b\frown aa⌢b>b⌢a 和 b\frown c > c\frown bb⌢c>c⌢b 意味着 a\frown c > c\frown aa⌢c>c⌢a 。换言之，我们的自定义比较方法保证了传递性，所以这样子排序是对的。
     *
     * 一旦数组排好了序，最“重要”的数字会在最前面。有一个需要注意的情况是如果数组只包含 0 ，我们直接返回结果 00 即可。否则，我们用排好序的数组形成一个字符串并返回。
     *
     * 作者：LeetCode
     * 链接：https://leetcode-cn.com/problems/largest-number/solution/zui-da-shu-by-leetcode/
     * @return
     */
    public String largestNumber(int nums[]) {
        String larStr = "";
        String[] sortArr = new String[nums.length];
        for (int i=0; i< nums.length; i++) {
            sortArr[i] = String.valueOf(nums[i]);
        }
        Arrays.sort(sortArr,new LargestNumberComparator());
        if(sortArr[0].equals("0")) {
            return  "0";
        }
        for (String l: sortArr) {
            larStr += l;
        }
        return larStr;
    }

    public static void main(String[] argv) {
        LargestNumber t = new LargestNumber();
        int[] nums = new int[]{1, 2, 30,3};
        System.out.println(t.largestNumber(nums));
    }
}
