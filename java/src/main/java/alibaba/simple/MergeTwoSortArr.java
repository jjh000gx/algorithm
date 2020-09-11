package alibaba.simple;

import java.util.Arrays;

/**
 * 给你两个有序整数数组?nums1 和 nums2，请你将 nums2 合并到?nums1?中，使 nums1 成为一个有序数组。
 *
 *
 * 说明:
 *
 * 初始化?nums1 和 nums2 的元素数量分别为?m 和 n 。
 * 你可以假设?nums1?有足够的空间（空间大小大于或等于?m + n）来保存 nums2 中的元素。
 *
 * 示例:
 *
 * 输入:
 * nums1 = [1,2,3,0,0,0], m = 3
 * nums2 = [2,5,6],       n = 3
 *
 * 输出:?[1,2,2,3,5,6]
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/merge-sorted-array
 */
public class MergeTwoSortArr {
    /**
     * 方法二已经取得了最优的时间复杂度O(n + m)O(n+m)，但需要使用额外空间。这是由于在从头改变nums1的值时，需要把nums1中的元素存放在其他位置。
     *
     * 如果我们从结尾开始改写 nums1 的值又会如何呢？这里没有信息，因此不需要额外空间。
     *
     * 这里的指针 p 用于追踪添加元素的位置。
     *
     * 作者：LeetCode
     * 链接：https://leetcode-cn.com/problems/merge-sorted-array/solution/he-bing-liang-ge-you-xu-shu-zu-by-leetcode/
     * 来源：力扣（LeetCode）
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     * @param nums1
     * @param m
     * @param nums2
     * @param n
     */
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int p1 = m-1;
        int p2 = n-1;
        int p = m+n-1;
        while (p2>=0 && p1>= 0) {
            nums1[p--] = (nums1[p1] > nums2[p2]) ? nums1[p1--] : nums2[p2--];
        }

        System.arraycopy(nums2,0,nums1,0,p2+1);
    }

    public static void main(String[] argv) {
        int[] num1 = new int[]{1,2,3,0,0,0};
        int[] num2 =  new int[]{2,5,6};

        MergeTwoSortArr t = new MergeTwoSortArr();
        t.merge(num1,3,num2,3);

        System.out.println(Arrays.toString(num1));

    }
}
