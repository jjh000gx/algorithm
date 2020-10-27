package alibaba.medium;

/**
 * 给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为 1000。
 *
 * 示例 1：
 *
 * 输入: "babad"
 * 输出: "bab"
 * 注意: "aba" 也是一个有效答案。
 * 示例 2：
 *
 * 输入: "cbbd"
 * 输出: "bb"
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/longest-palindromic-substring
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class LongestPalindrome {
    /**
     * 方法一：动态规划
     * 思路与算法
     *
     * 对于一个子串而言，如果它是回文串，并且长度大于 22，那么将它首尾的两个字母去除之后，它仍然是个回文串。例如对于字符串 \textrm{``ababa''}“ababa”，如果我们已经知道 \textrm{``bab''}“bab” 是回文串，那么 \textrm{``ababa''}“ababa” 一定是回文串，这是因为它的首尾两个字母都是 \textrm{``a''}“a”。
     *
     * 根据这样的思路，我们就可以用动态规划的方法解决本题。我们用 P(i,j)P(i,j) 表示字符串 ss 的第 ii 到 jj 个字母组成的串（下文表示成 s[i:j]s[i:j]）是否为回文串：
     *
     * P(i,j) = \begin{cases} \text{true,} &\quad\text{如果子串~} S_i \dots S_j \text{~是回文串}\\ \text{false,} &\quad\text{其它情况} \end{cases}
     * P(i,j)={
     * true,
     * false,
     * ​
     *
     * 如果子串 S
     * i
     * ​
     *  …S
     * j
     * ​
     *   是回文串
     * 其它情况
     * ​
     *
     *
     * 这里的「其它情况」包含两种可能性：
     *
     * s[i, j]s[i,j] 本身不是一个回文串；
     *
     * i > ji>j，此时 s[i, j]s[i,j] 本身不合法。
     *
     * 那么我们就可以写出动态规划的状态转移方程：
     *
     * P(i, j) = P(i+1, j-1) \wedge (S_i == S_j)
     * P(i,j)=P(i+1,j−1)∧(S
     * i
     * ​
     *  ==S
     * j
     * ​
     *  )
     *
     * 也就是说，只有 s[i+1:j-1]s[i+1:j−1] 是回文串，并且 ss 的第 ii 和 jj 个字母相同时，s[i:j]s[i:j] 才会是回文串。
     *
     * 上文的所有讨论是建立在子串长度大于 22 的前提之上的，我们还需要考虑动态规划中的边界条件，即子串的长度为 11 或 22。对于长度为 11 的子串，它显然是个回文串；对于长度为 22 的子串，只要它的两个字母相同，它就是一个回文串。因此我们就可以写出动态规划的边界条件：
     *
     * \begin{cases} P(i, i) = \text{true} \\ P(i, i+1) = ( S_i == S_{i+1} ) \end{cases}
     * {
     * P(i,i)=true
     * P(i,i+1)=(S
     * i==S
     * i+1
     * ​
     *  )
     * ​
     *
     *
     * 根据这个思路，我们就可以完成动态规划了，最终的答案即为所有 P(i, j) = \text{true}P(i,j)=true 中 j-i+1j−i+1（即子串长度）的最大值。注意：在状态转移方程中，我们是从长度较短的字符串向长度较长的字符串进行转移的，因此一定要注意动态规划的循环顺序。
     *
     * 作者：LeetCode-Solution
     * 链接：https://leetcode-cn.com/problems/longest-palindromic-substring/solution/zui-chang-hui-wen-zi-chuan-by-leetcode-solution/
     * 来源：力扣（LeetCode）
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     * @param s
     * @return
     */
    public String longestPalindrome(String s) {
        int n = s.length();
        String anx = "";
        boolean[][] dp = new boolean[n][n];
        for(int l=0; l<n; l++) {
            for(int i=0;i+l<n; i++) {
                int j = i+l;
                if(0 == l) {
                    dp[i][j] = true;
                } else if(1 == l) {
                    dp[i][j] = s.charAt(i) == s.charAt(j);
                } else {
                    dp[i][j] = dp[i+1][j-1] && s.charAt(i) == s.charAt(j);
                }
                if(dp[i][j] && anx.length() < j-i +1) {
                    anx = s.substring(i,j+1);
                }
            }
        }
        return anx;
    }

    /**
     * 方法二：中心扩展算法
     * 思路与算法
     *
     * 我们仔细观察一下方法一中的状态转移方程：
     *
     * 可以发现，所有的状态在转移的时候的可能性都是唯一的。也就是说，我们可以从每一种边界情况开始「扩展」，也可以得出所有的状态对应的答案。
     *
     * 边界情况即为子串长度为 11 或 22 的情况。我们枚举每一种边界情况，并从对应的子串开始不断地向两边扩展。如果两边的字母相同，我们就可以继续扩展，例如从 P(i+1,j-1)P(i+1,j−1) 扩展到 P(i,j)P(i,j)；如果两边的字母不同，我们就可以停止扩展，因为在这之后的子串都不能是回文串了。
     *
     * 聪明的读者此时应该可以发现，「边界情况」对应的子串实际上就是我们「扩展」出的回文串的「回文中心」。方法二的本质即为：我们枚举所有的「回文中心」并尝试「扩展」，直到无法扩展为止，此时的回文串长度即为此「回文中心」下的最长回文串长度。我们对所有的长度求出最大值，即可得到最终的答案。
     *
     * 作者：LeetCode-Solution
     * 链接：https://leetcode-cn.com/problems/longest-palindromic-substring/solution/zui-chang-hui-wen-zi-chuan-by-leetcode-solution/
     * 来源：力扣（LeetCode）
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     * @param s
     * @return
     */
    public String longestPalindrome1(String s) {
        if (s == null || s.length() < 1) {
            return "";
        }
        int start=0 ,end = 0;
        int n = s.length();
        for(int i = 0; i<n; i++) {
           int l1 = expandAroundCenter(s,i,i,n);
           int l2 = expandAroundCenter(s,i,i+1,n);
           int l = Math.max(l1,l2);
           if(l > end-start) {
               start = i-(l-1)/2;
               end   = i+l/2;
           }

        }
        return s.substring(start,end+1);
    }

    private int expandAroundCenter(String s,int left,int right, int n) {
        while (right<n && left>=0 && s.charAt(right) == s.charAt(left)) {
            left--;
            right++;
        }
        return right-left -1;
    }

    public static void main(String[] argv) {
        LongestPalindrome t = new LongestPalindrome();
        System.out.println(t.longestPalindrome("babad"));
    }
}
