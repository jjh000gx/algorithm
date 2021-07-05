package utils;

public class KMP {
    public static int kmp(String str,String pattern) {
        int[] next = getNext(pattern);
        int j = 0;
        for(int i =0; i< str.length(); i++) {
            while (j!=0 && str.charAt(i) != pattern.charAt(j)) {
                //遇到坏字符时，查询next数组并改变模式串的起点
                j = next[j];
            }
            if(str.charAt(i) == pattern.charAt(j)) {
                j++;
            }
            if(j == pattern.length()) {
                return i - j + 1;
            }
        }
        return -1;
    }

    public static int[] getNext(String pattern) {
        int[] next = new int[pattern.length()];
        int j = 0;
        next[0] = 0;
         for(int i=1; i<pattern.length(); i++) {
             //从next[i+1]的求解回溯到next[j]
             while (j>0 && pattern.charAt(i-1) != pattern.charAt(j)) {
                 j = next[j];
             }
             if(pattern.charAt(i-1) == pattern.charAt(j)) {
                 j++;
             }
             next[i] = j;
         }

         return next;
    }
}
