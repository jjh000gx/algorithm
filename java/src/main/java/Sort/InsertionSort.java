package Sort;

import java.util.Arrays;

/**
 * 插入排序（Insertion-Sort）的算法描述是一种简单直观的排序算法。它的工作原理是通过构建有序序列，对于未排序数据，在已排序序列中从后向前扫描，找到相应位置并插入。
 *
 * 3.1 算法描述
 * 一般来说，插入排序都采用in-place在数组上实现。具体算法描述如下：
 *
 * 1从第一个元素开始，该元素可以认为已经被排序；
 * 2取出下一个元素，在已经排序的元素序列中从后向前扫描；
 * 3如果该元素（已排序）大于新元素，将该元素移到下一位置；
 * 4重复步骤3，直到找到已排序的元素小于或者等于新元素的位置；
 * 5将新元素插入到该位置后；
 * 重复步骤2~5。
 *
 * 算法分析：
 * 插入排序在实现上，通常采用in-place排序（即只需用到O(1)的额外空间的排序），
 * 因而在从后向前扫描过程中，需要反复把已排序元素逐步向后挪位，为最新元素提供插入空间。
 */
public class InsertionSort {

    public int[] insertionSort(int[] nums) {
        int len = nums.length;
        for(int i=0; i<len; i++) {
            int j = i;
            int current = nums[j];
            while (j>=1 && nums[j-1] > current) {
                //swap(nums,j,j+1);
                nums[j] = nums[j-1];
                j--;
            }
            nums[j] = current;
        }
        return nums;
    }
    public void swap(int[] nums,int i, int j) {
        int temp = nums[i];
        nums[i]  = nums[j];
        nums[j] = temp;
    }
    public static void main(String[] argv) {
        InsertionSort t = new InsertionSort();
        int[] nums = new int[] {78,1,9,3,0,12,2,55};
        System.out.println(Arrays.toString(nums));

        t.insertionSort(nums);

        System.out.println(Arrays.toString(nums));

    }
}
