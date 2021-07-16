package other.simple;

import java.util.UUID;

/**
 * 给定一个整数 n，返回 n! 结果尾数中零的数量。
 *
 * 示例 1:
 *
 * 输入: 3
 * 输出: 0
 * 解释: 3! = 6, 尾数中没有零。
 * 示例 2:
 *
 * 输入: 5
 * 输出: 1
 * 解释: 5! = 120, 尾数中有 1 个零.
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/factorial-trailing-zeroes
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class TrailingZeroes {
    public int trailingZeroes(int n) {
        if(n <= 4) {
            return 0;
        }
        int trail = 1;
        for(int i =n ; i >1 ; i--) {
            //乘法运算时间复杂度高
            trail *= i;
        }
        int count = 0;
        System.out.println(trail);
        while (0 == trail%10) {
            count++;
            trail = trail/10;
        }
        return count;
    }

    public static void main(String[] argv) {
        TrailingZeroes tt = new TrailingZeroes();
        System.out.println(tt.trailingZeroes(10));
        String uuid1 = UUID.randomUUID().toString();

        System.out.println(uuid1);

        UUID uuid = UUID.randomUUID();
        int version = uuid.version();
        int variant = uuid.variant();
        System.out.println(String.format("version:%d,variant:%d", version, variant));
        uuid = UUID.nameUUIDFromBytes(new byte[0]);
        version = uuid.version();
        variant = uuid.variant();
        System.out.println(String.format("version:%d,variant:%d", version, variant));

    }
}
