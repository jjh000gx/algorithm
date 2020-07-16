package alibaba.simple;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/*给定一个整数数组 nums?和一个目标值 target，请你在该数组中找出和为目标值的那?两个?整数，并返回他们的数组下标。

你可以假设每种输入只会对应一个答案。但是，数组中同一个元素不能使用两遍。

?

示例:

给定 nums = [2, 7, 11, 15], target = 9

因为 nums[0] + nums[1] = 2 + 7 = 9
所以返回 [0, 1]
通过次数1,213,088提交次数2,475,202

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/two-sum
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。*/
public class SumOfTwoNums {
    public int[] sumofTwoNum(int[] nums, int target) {
        Map<Integer,Integer> map = new HashMap<Integer, Integer>();
        for(int i=0; i<nums.length; i++) {
            int lift = target - nums[i];
            if(map.containsKey(lift)) {
                return new int[] {map.get(lift),i};
            }
            map.put(nums[i], i);
        }

        return null;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{2,7,9,10};
        int target = 11;
        SumOfTwoNums t = new SumOfTwoNums();
        int[] b = t.sumofTwoNum(nums,target);
        System.out.println(Arrays.toString(b));
        System.out.print(b);

    }

}
