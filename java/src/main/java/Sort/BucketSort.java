package Sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * 桶排序是计数排序的升级版。它利用了函数的映射关系，高效与否的关键就在于这个映射函数的确定。桶排序 (Bucket sort)的工作的原理：假设输入数据服从均匀分布，将数据分到有限数量的桶里，每个桶再分别排序（有可能再使用别的排序算法或是以递归方式继续使用桶排序进行排）。
 *
 * 9.1 算法描述
 * 1 设置一个定量的数组当作空桶；
 * 2 遍历输入数据，并且把数据一个一个放到对应的桶里去；
 * 3 对每个不是空的桶进行排序；
 * 4 从不是空的桶里把排好序的数据拼接起来。
 */
public class BucketSort {
    public int[] bucketSort(int[] nums,int bsize) {
        if(nums.length < 2) {
            return nums;
        }
        int maxValue,minValue;
        maxValue = minValue = nums[0];
        for(int n: nums) {
            if(n>maxValue) {
                maxValue = n;
            }
            if(n<minValue) {
                minValue = n;
            }
        }

        int bucketCount = (maxValue-minValue)/bsize + 1;
        ArrayList<ArrayList<Integer>> buckets = new ArrayList<ArrayList<Integer>>(bucketCount);
         for(int i=0; i<bucketCount;i++) {
             buckets.add(new ArrayList<Integer>());
         }

        for (int n : nums) {
            int tb = (n-minValue)/bsize;
            buckets.get(tb).add(n);
        }
        int i=0;
        for (ArrayList<Integer> bb : buckets) {
            if(null != bb) {
                Collections.sort(bb);
                for(int b : bb) {
                    nums[i++] = b;
                }
            }
        }

        return nums;
    }

    public static void main(String[] argv) {
        BucketSort t = new BucketSort();
        int[] nums = new int[] {78,1,9,3,0,12,2,55};
        System.out.println(Arrays.toString(nums));

        nums = t.bucketSort(nums,3);

        System.out.println(Arrays.toString(nums));
    }

}
