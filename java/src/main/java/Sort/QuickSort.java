package Sort;

import java.util.Arrays;

/**
 * 快速排序的基本思想：通过一趟排序将待排记录分隔成独立的两部分，其中一部分记录的关键字均比另一部分的关键字小，则可分别对这两部分记录继续进行排序，以达到整个序列有序。
 *
 * 6.1 算法描述
 * 快速排序使用分治法来把一个串（list）分为两个子串（sub-lists）。具体算法描述如下：
 *
 * 从数列中挑出一个元素，称为 “基准”（pivot）；
 * 重新排序数列，所有元素比基准值小的摆放在基准前面，所有元素比基准值大的摆在基准的后面（相同的数可以到任一边）。在这个分区退出之后，该基准就处于数列的中间位置。这个称为分区（partition）操作；
 * 递归地（recursive）把小于基准值元素的子数列和大于基准值元素的子数列排序。
 */
public class QuickSort {
    public int[] quickSort(int[] nums, int left, int right) {

       if(left < right) {
           int pivot = partion(nums,left,right);
           quickSort(nums,left,pivot -1);
           quickSort(nums,pivot+1,right);
       }

        return nums;
    }
    public int partion(int[] nums,int left, int right) {
        int pkey   = nums[left]; //取基准数据
        int privot = left;
        int pindex = left;
        while (left <= right) {
            //小于基准数据的放在基准的左边，否则放在右边

            left++;if(nums[left] < pkey) {
                swap(nums, left, pindex);
                pindex++;
                privot = left;
            }
        }
        swap(nums,privot,pindex);
        return pindex;

    }

    public void swap(int[] nums,int i, int j) {
        int temp = nums[i];
        nums[i]  = nums[j];
        nums[j] = temp;
    }
    public static void main(String[] argv) {
        QuickSort t = new QuickSort();
        int[] nums = new int[] {1,9,3,0,12,2,55};
        System.out.println(Arrays.toString(nums));

        t.quickSort(nums,0,nums.length-1);

        System.out.println(Arrays.toString(nums));

    }
}
