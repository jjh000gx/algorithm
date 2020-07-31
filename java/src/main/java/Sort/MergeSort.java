package Sort;

import java.util.Arrays;

/**
 * 归并排序是建立在归并操作上的一种有效的排序算法。该算法是采用分治法（Divide and Conquer）的一个非常典型的应用。将已有序的子序列合并，得到完全有序的序列；即先使每个子序列有序，再使子序列段间有序。若将两个有序表合并成一个有序表，称为2-路归并。
 *
 * 5.1 算法描述
 * 把长度为n的输入序列分成两个长度为n/2的子序列；
 * 对这两个子序列分别采用归并排序；
 * 将两个排序好的子序列合并成一个最终的排序序列。
 *
 * 归并排序是一种稳定的排序方法。和选择排序一样，归并排序的性能不受输入数据的影响，
 * 但表现比选择排序好的多，因为始终都是O(nlogn）的时间复杂度。代价是需要额外的内存空间。
 */
public class MergeSort {
    public int[] mergeSort(int[] nums) {
        int len = nums.length;
        if(len < 2) {
            return nums;
        }
        int mid = len/2;
        int[] left  = getSubArr(nums,0,mid-1);
        System.out.println(Arrays.toString(left));
        int[] right = getSubArr(nums,mid,len-1);
        System.out.println(Arrays.toString(right));
         //mergeSort(left);
        return merge(mergeSort(left), mergeSort(right));
    }

    public int[] merge(int[] nums1, int[] nums2) {
        int n1l = nums1.length;
        int n2l = nums2.length;
        int[] nnums = new int[n1l + n2l];
        int n1 = 0;
        int n2 = 0;
        int n   = 0;
        while (n1 < n1l && n2 < n2l) {
            if(nums1[n1] < nums2[n2]) {
                nnums[n++] = nums1[n1++];
            } else {
                nnums[n++] = nums2[n2++];
            }
        }
        while (n1<n1l) {
            nnums[n++] = nums1[n1++];
        }
        while (n2<n2l) {
            nnums[n++] = nums2[n2++];
        }

        return nnums;
    }

    public int[] getSubArr(int[] nums,int start,int end) {
        int[] subArr = new int[end-start+1];
        int j = 0;
        while (start<=end) {
            subArr[j++] = nums[start++];
        }
        return subArr;
    }

    public static void main(String[] argv) {
        MergeSort t = new MergeSort();
        int[] nums = new int[] {78,1,9,3,0,12,2,55};
        System.out.println(Arrays.toString(nums));

        nums = t.mergeSort(nums);

        System.out.println(Arrays.toString(nums));
    }
}
