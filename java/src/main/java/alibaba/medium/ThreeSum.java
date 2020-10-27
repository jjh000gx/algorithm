package alibaba.medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 给你一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？请你找出所有满足条件且不重复的三元组。
 *
 * 注意：答案中不可以包含重复的三元组。
 *
 *  
 *
 * 示例：
 *
 * 给定数组 nums = [-1, 0, 1, 2, -1, -4]，
 *
 * 满足要求的三元组集合为：
 * [
 *   [-1, 0, 1],
 *   [-1, -1, 2]
 * ]
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/3sum
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 */
public class ThreeSum {
    /**
     * 方法一：排序 + 双指针
     * 题目中要求找到所有「不重复」且和为 00 的三元组，这个「不重复」的要求使得我们无法简单地使用三重循环枚举所有的三元组。这是因为在最坏的情况下，数组中的元素全部为 00，即
     *
     *
     * [0, 0, 0, 0, 0, ..., 0, 0, 0]
     * 任意一个三元组的和都为 00。如果我们直接使用三重循环枚举三元组，会得到 O(N^3)O(N
     * 3
     *  ) 个满足题目要求的三元组（其中 NN 是数组的长度）时间复杂度至少为 O(N^3)O(N
     * 3
     *  )。在这之后，我们还需要使用哈希表进行去重操作，得到不包含重复三元组的最终答案，又消耗了大量的空间。这个做法的时间复杂度和空间复杂度都很高，因此我们要换一种思路来考虑这个问题。
     *
     * 「不重复」的本质是什么？我们保持三重循环的大框架不变，只需要保证：
     *
     * 第二重循环枚举到的元素不小于当前第一重循环枚举到的元素；
     *
     * 第三重循环枚举到的元素不小于当前第二重循环枚举到的元素。
     *
     * 也就是说，我们枚举的三元组 (a, b, c)(a,b,c) 满足 a \leq b \leq ca≤b≤c，保证了只有 (a, b, c)(a,b,c) 这个顺序会被枚举到，而 (b, a, c)(b,a,c)、(c, b, a)(c,b,a) 等等这些不会，这样就减少了重复。要实现这一点，我们可以将数组中的元素从小到大进行排序，随后使用普通的三重循环就可以满足上面的要求。
     *
     * 同时，对于每一重循环而言，相邻两次枚举的元素不能相同，否则也会造成重复。举个例子，如果排完序的数组为
     *
     *
     * [0, 1, 2, 2, 2, 3]
     *  ^  ^  ^
     * 我们使用三重循环枚举到的第一个三元组为 (0, 1, 2)(0,1,2)，如果第三重循环继续枚举下一个元素，那么仍然是三元组 (0, 1, 2)(0,1,2)，产生了重复。因此我们需要将第三重循环「跳到」下一个不相同的元素，即数组中的最后一个元素 33，枚举三元组 (0, 1, 3)(0,1,3)。
     *
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> threeSum(int[] nums) {
        int n = nums.length;
        Arrays.sort(nums);
        List<List<Integer>> ans = new ArrayList<List<Integer>>();
        for(int first=0; first < n; first ++) {
            if(first > 0 && nums[first] == nums[first-1]) {
                continue;
            }
            int target = -nums[first];
            int third  = n-1;
            for(int second=first+1; second < n-1; second++) {
                // 需要和上一次枚举的数不相同
                if(second > first+1 && nums[second] == nums[second-1]) {
                    continue;
                }
                while (second < third && nums[second] + nums[third] > target) {
                    third--;
                }
                if(second == third) {
                    break;
                }
                if(nums[second] + nums[third] == target) {
                    List<Integer> temp  = new ArrayList<Integer>();
                    temp.add(nums[first]);
                    temp.add(nums[second]);
                    temp.add(nums[third]);
                    ans.add(temp);
                }
            }
        }

        return ans;
    }

    public static void main(String[] argvs) {
        ThreeSum t = new ThreeSum();
        int[] nums = new int[]{-1, 0, 1, 2, -1, -4};
         System.out.println(t.threeSum(nums));
    }
}
