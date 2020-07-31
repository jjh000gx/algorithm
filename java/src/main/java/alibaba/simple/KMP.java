package alibaba.simple;

import java.util.Arrays;

/**
 *KMP算法是一种改进的字符串匹配算法，由D.E.Knuth，J.H.Morris和V.R.Pratt提出的，
 * 因此人们称它为克努特—莫里斯—普拉特操作（简称KMP算法）。
 * KMP算法的核心是利用匹配失败后的信息，尽量减少模式串与主串的匹配次数以达到快速匹配的目的。
 * 具体实现就是通过一个next()函数实现，函数本身包含了模式串的局部匹配信息。KMP算法的时间复杂度O(m+n)
 * 链接：https://baijiahao.baidu.com/s?id=1659735837100760934&wfr=spider&for=pc
 */
public class KMP {
    public static int kmp(String str, String pattern) {
            //预处理，生成next数组
        int[] next = getNexts(pattern);
        int j = 0;
        //主循环，遍历主串字符
        for (int i = 0; i < str.length(); i++) {
            while (j > 0 && str.charAt(i) != pattern.charAt(j)) {
                //遇到坏字符时，查询next数组并改变模式串的起点
                j = next[j];
            }
            if (str.charAt(i) == pattern.charAt(j)) {
                j++;
            }
            if (j == pattern.length()) {
                //匹配成功，返回下标
                return i - pattern.length() + 1;
            }
        }
        return -1;
    }
    //生成next数组
    private static int[] getNexts(String pattern) {
        int[] next = new int[pattern.length()];
        int j = 0;
        for (int i=2; i<pattern.length(); i++) {
            while (j != 0 && pattern.charAt(j) != pattern.charAt(i-1)) {
                //从next[i+1]的求解回溯到next[j]
                j = next[j];
            }
            if (pattern.charAt(j) == pattern.charAt(i-1)) {
                j++;
            }
            next[i] = j;
        }
        return next;
    }
    public static void main(String[] args) {
        String str = "ATGTGAGCTGGTGTGTGCFAA";
        String pattern = "GTGTGTF";
        System.out.println(Arrays.toString(getNexts(pattern)));
        int index = kmp(str, pattern);
        System.out.println("首次出现位置：" + index);
    }
}
