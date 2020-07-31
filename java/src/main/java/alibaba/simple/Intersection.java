package alibaba.simple;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 *给定两个数组，编写一个函数来计算它们的交集。
 *
 *  
 *
 * 示例 1：
 *
 * 输入：nums1 = [1,2,2,1], nums2 = [2,2]
 * 输出：[2]
 * 示例 2：
 *
 * 输入：nums1 = [4,9,5], nums2 = [9,4,9,8,4]
 * 输出：[9,4]
 *  
 *
 * 说明：
 *
 * 输出结果中的每个元素一定是唯一的。
 * 我们可以不考虑输出结果的顺序。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/intersection-of-two-arrays
 */
public class Intersection {
    public int[] intersection(int[] nums1, int[] nums2) {
        Set<Integer> setNums1 = new HashSet<Integer>();
        for(int n : nums1) {
            setNums1.add(n);
        }
        Set<Integer> setNums2 = new HashSet<Integer>();
        for(int n : nums2) {
            setNums2.add(n);
        }
        if(setNums1.size() > setNums2.size()) {
            return set_intersection(setNums2, setNums1);
        }

        return set_intersection(setNums1, setNums2);

    }
    public int[] set_intersection(Set<Integer> set1, Set<Integer> set2) {
        int [] output = new int[set1.size()];
        int idx = 0;
        for (Integer s : set1)
            if (set2.contains(s)) output[idx++] = s;

        return Arrays.copyOf(output, idx);
    }

    public static void main(String[] argv) {

    }
}
