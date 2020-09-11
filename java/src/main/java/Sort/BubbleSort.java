package Sort;

import java.util.Arrays;

/**
 * 冒泡排序是一种简单的排序算法。它重复地走访过要排序的数列，一次比较两个元素，如果它们的顺序错误就把它们交换过来。走访数列的工作是重复地进行直到没有再需要交换，也就是说该数列已经排序完成。这个算法的名字由来是因为越小的元素会经由交换慢慢“浮”到数列的顶端。
 *
 * 1.1 算法描述
 * 1比较相邻的元素。如果第一个比第二个大，就交换它们两个；
 * 2对每一对相邻元素作同样的工作，从开始第一对到结尾的最后一对，这样在最后的元素应该会是最大的数；
 * 3针对所有的元素重复以上的步骤，除了最后一个；
 * 4重复步骤1~3，直到排序完成。
 * @link https://www.cnblogs.com/onepixel/articles/7674659.html
 */
public class BubbleSort {
    public int[] bubbleSort(int[] nums) {
        int l = nums.length;
        A:for(int i=0; i<l; i++) {
            for(int j=0; j<l-i-1; j ++) {
                if(nums[j] > nums[j+1]) {
                    int t = nums[j];
                    nums[j] = nums[j+1];
                    nums[j+1] = t;
                    break A;
                }
            }
        }
        return nums;
    }
    public static void main(String[] argv) {
        BubbleSort t =  new BubbleSort();
        int[] nums = new int[] {11,1,6,3,1,9};
        System.out.println(Arrays.toString(t.bubbleSort(nums)));

    }
}
