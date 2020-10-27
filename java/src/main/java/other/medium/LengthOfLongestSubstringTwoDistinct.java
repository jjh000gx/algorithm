package other.medium;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 给定一个字符串 s ，找出 至多 包含两个不同字符的最长子串 t ，并返回该子串的长度。
 *
 * 示例 1:
 *
 * 输入: "eceba"
 * 输出: 3
 * 解释: t 是 "ece"，长度为3。
 * 示例 2:
 *
 * 输入: "ccaabbb"
 * 输出: 5
 * 解释: t 是 "aabbb"，长度为5。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/longest-substring-with-at-most-two-distinct-characters
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class LengthOfLongestSubstringTwoDistinct {
    public int lengthOfLongestSubstringTwoDistinct(String s) {
        int n = s.length();
        if(n < 3) {
            return n;
        }
        int left = 0;
        int right = 0;
        int max_len = 0;
        Map<Character,Integer> hmap = new HashMap<Character,Integer>();

        while (right < n) {
            if(hmap.size() < 3) {
                hmap.put(s.charAt(right), right++);
            }
            if(hmap.size() == 3) {
                left = Collections.min(hmap.values());
                hmap.remove(s.charAt(left));
                left += 1;
            }
            max_len = Math.max(max_len,right-left);
        }
        return max_len;
    }

    public static void main(String[] argvs) {
        LengthOfLongestSubstringTwoDistinct t = new LengthOfLongestSubstringTwoDistinct();
        int m = t.lengthOfLongestSubstringTwoDistinct("ccaabbb");
        System.out.println(m);
    }
}
