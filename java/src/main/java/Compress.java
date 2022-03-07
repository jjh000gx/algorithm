import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Compress {
    private static Map<Integer,Integer> cdata = new HashMap<>();

    public static void main(String[] argvs) throws Exception{
        BufferedReader in = new BufferedReader(new FileReader("D:\\web\\jjh000gx\\algorithm\\java\\src\\main\\java\\input.txt"));
        //BufferedReader in = new BufferedReader(new FileReader("input.txt"));

        String str = in.readLine();
        int     n = Integer.parseInt(in.readLine());
        in.close();
        int tt = getLengthOfOptimalCompression(str,n);
        System.out.println(tt);
        Character cchar = str.charAt(0);
        int       tcount = 1;
        for (int i=1; i< str.length(); i++) {
            if(cchar.equals(str.charAt(i))) {
                tcount ++;
            } else {
                setCdata(tcount);
                cchar = str.charAt(i);
                tcount = 1;
            }
        }
        setCdata(tcount);
        Integer b = countCompressLength(n);
        BufferedWriter out = new BufferedWriter(new FileWriter("output.txt"));
        out.write(String.valueOf(b));
        out.close();
    }

    public static Integer countCompressLength(Integer n) {
        Integer c = 0;
        for(Integer key :cdata.keySet()) {
            Integer tcount = cdata.get(key);
            //字符串倍数
            Integer b = key == 1 ? 1 : 2;
            c        +=  tcount * b;
            if(n > 0) {
                Integer ttcount = key * tcount;
                //减去多少个字符
                if(n-ttcount < 0) {
                    ttcount = n;
                }
                //求模
                Integer m = ttcount%key;
                if( 0 == m) {
                    c -= b*tcount;
                } else {
                    Integer mm = ttcount / key;
                    c -= mm * b;
                    if (key - m == 1) {
                        c -= 1;
                    }
                }
                n -= ttcount;
             }

            //System.out.println(key);
        }
        return  c;
    }

    public static void setCdata(Integer tcount) {
        Integer v = cdata.get(tcount);
        v = null == v ? 1 : v+1;
        cdata.put(tcount,v);
    }

    /**
     * https://leetcode-cn.com/problems/string-compression-ii/solution/ya-suo-zi-fu-chuan-ii-by-leetcode-solution/
     * @param s
     * @param k
     * @return
     */
    public static int getLengthOfOptimalCompression(String s, int k) {
        int n = s.length();
        int[][] f = new int[n + 1][k + 1];
        for (int i = 0; i <= n; i++) {
            Arrays.fill(f[i], Integer.MAX_VALUE >> 1);
        }
        f[0][0] = 0;
        for (int i = 1; i <= n; ++i) {
            for (int j = 0; j <= k && j <= i; ++j) {
                if (j > 0) {
                    f[i][j] = f[i - 1][j - 1];
                }
                int same = 0, diff = 0;
                for (int i0 = i; i0 >= 1 && diff <= j; --i0) {
                    if (s.charAt(i0 - 1) == s.charAt(i - 1)) {
                        ++same;
                        f[i][j] = Math.min(f[i][j], f[i0 - 1][j - diff] + calc(same));
                    } else {
                        ++diff;
                    }
                }
            }
        }

        return f[n][k];
    }

    public static int calc(int x) {
        if (x == 1) {
            return 1;
        }
        if (x < 10) {
            return 2;
        }
        if (x < 100) {
            return 3;
        }
        return 4;
    }
}
