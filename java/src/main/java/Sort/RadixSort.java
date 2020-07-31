package Sort;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 基数排序是按照低位先排序，然后收集；再按照高位排序，然后再收集；依次类推，直到最高位。有时候有些属性是有优先级顺序的，先按低优先级排序，再按高优先级排序。最后的次序就是高优先级高的在前，高优先级相同的低优先级高的在前。
 *
 * 10.1 算法描述
 *1. 取得数组中的最大数，并取得位数；
 *2. arr为原始数组，从最低位开始取每个位组成radix数组；
 *3. 对radix进行计数排序（利用计数排序适用于小范围数的特点）；
 *
 * 10.4 算法分析
 * 基数排序基于分别排序，分别收集，所以是稳定的。
 * 但基数排序的性能比桶排序要略差，每一次关键字的桶分配都需要O(n)的时间复杂度，而且分配之后得到新的关键字序列又需要O(n)的时间复杂度。
 * 假如待排数据可以分为d个关键字，则基数排序的时间复杂度将是O(d*2n) ，当然d要远远小于n，因此基本上还是线性级别的。
 *
 * 基数排序的空间复杂度为O(n+k)，其中k为桶的数量。一般来说n>>k，因此额外空间需要大概n个左右。
 */
public class RadixSort {
    public int[] radixSort(int[] nums) {
        int maxValue = nums[0];
        for(int n:nums) {
            if(n>maxValue) {
                maxValue = n;
            }
        }
        maxValue *= 10;
        int dev = 1;
        int mod = 10;
        ArrayList<ArrayList<Integer>> buckets = new ArrayList<ArrayList<Integer>>(10);
        for(int i =0;i<10; i++) {
            buckets.add(new ArrayList<Integer>());
        }
        System.out.println(buckets.size());
        do{
            for(int n:nums) {
                int b = (n%mod)/dev;
                System.out.println(buckets.get(b).size());
                buckets.get(b).add(n);
            }
            System.out.println(buckets.toString());
            int i =0 ;
            for (ArrayList<Integer> bucket: buckets) {
                System.out.println(bucket.toString());
                while (!bucket.isEmpty()) {
                    nums[i++] = bucket.remove(0);
                }
            }
            dev *= 10;
            mod *= 10;
        } while (mod < maxValue);
        return nums;
    }

    public static void main(String[] argv) {
        RadixSort t = new RadixSort();
        int[] nums = new int[] {78,1,9,3,0,12,2,55,11,68,355};
        System.out.println(Arrays.toString(nums));

        nums = t.radixSort(nums);

        System.out.println(Arrays.toString(nums));
    }
}
