package alibaba.simple;

/**
 * 实现 strStr() 函数。
 *
 * 给定一个 haystack 字符串和一个 needle 字符串，在 haystack 字符串中找出 needle 字符串出现的第一个位置 (从0开始)。如果不存在，则返回  -1。
 *
 * 示例 1:
 *
 * 输入: haystack = "hello", needle = "ll"
 * 输出: 2
 * 示例 2:
 *
 * 输入: haystack = "aaaaa", needle = "bba"
 * 输出: -1
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/implement-strstr
 */
public class StrStr {
    private int a;
    private int b;

    public void setA(int a) {
        this.a = a;
    }

    public int getA() {
        return  this.a;
    }
    public void setB(int b) {
        this.b = b;
    }

    public int strStr(String haystack, String needle) {
        if(needle.length() == 0) {
            return 0;
        }
        int[] next = getNexts(needle);

        int j = 0;
        for(int i= 0; i<haystack.length(); i++) {
            while (j>0 && haystack.charAt(i) != needle.charAt(j)) {
                //出现坏字符的时候，回溯
                j = next[j];
            }
            if(haystack.charAt(i) == needle.charAt(j)) {
                j++;
            }
            if(j == needle.length()) {
                return i-needle.length() +1;
            }
        }

        return -1;
    }

    public int[] getNexts(String needle) {
        int length = needle.length();
        int[] next = new int[length];
        int j = 0;
        for (int i=2; i< length; i++) {
            while (j>0 && needle.charAt(j) != needle.charAt(i-1)) {
                j = next[j];
            }
            if(needle.charAt(j) == needle.charAt(i-1)) {
                j++;
            }
            next[i] = j;
        }

        return next;
    }

    public int bfStrStr(String haystack, String needle) {
        int hl = haystack.length();
        int nl = needle.length();
        if(nl == 0) {
            return 0;
        }
        if(nl > hl) {
            return -1;
        }
        for(int i=0; i<hl-nl+1; i++) {
            int t = i;
            for(int j=0;j<nl;j++) {
                if(haystack.charAt(t) != needle.charAt(j)) {
                    break;
                }
                t++;
            }
            if(t-i == nl) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] argv) {
        StrStr t = new StrStr();
        System.out.println(t.bfStrStr("mississipi", "issipi"));

        StrStr s1 = new StrStr();
        s1.setA(1);
        s1.setB(2);

        StrStr s2 = new StrStr();
        s2.setA(s1.getA());
        s2.setA(10);

        System.out.println(s1.getA());

        Integer a = 10;
        Integer b = a;
        b = 8;
        System.out.println(a);
        System.out.println(b);
        System.out.println("ewrew".substring(1,2));
    }

}
