package alibaba.medium;

import java.util.HashMap;
import java.util.Map;

/**
 * 请你来实现一个 atoi 函数，使其能将字符串转换成整数。
 *
 * 首先，该函数会根据需要丢弃无用的开头空格字符，直到寻找到第一个非空格的字符为止。接下来的转化规则如下：
 *
 * 如果第一个非空字符为正或者负号时，则将该符号与之后面尽可能多的连续数字字符组合起来，形成一个有符号整数。
 * 假如第一个非空字符是数字，则直接将其与之后连续的数字字符组合起来，形成一个整数。
 * 该字符串在有效的整数部分之后也可能会存在多余的字符，那么这些字符可以被忽略，它们对函数不应该造成影响。
 * 注意：假如该字符串中的第一个非空格字符不是一个有效整数字符、字符串为空或字符串仅包含空白字符时，则你的函数不需要进行转换，即无法进行有效转换。
 *
 * 在任何情况下，若函数不能进行有效的转换时，请返回 0 。
 *
 * 提示：
 *
 * 本题中的空白字符只包括空格字符 ' ' 。
 * 假设我们的环境只能存储 32 位大小的有符号整数，那么其数值范围为 [−231,  231 − 1]。如果数值超过这个范围，请返回  INT_MAX (231 − 1) 或 INT_MIN (−231) 。
 *  
 *
 * 示例 1:
 *
 * 输入: "42"
 * 输出: 42
 * 示例 2:
 *
 * 输入: "   -42"
 * 输出: -42
 * 解释: 第一个非空白字符为 '-', 它是一个负号。
 *      我们尽可能将负号与后面所有连续出现的数字组合起来，最后得到 -42 。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/string-to-integer-atoi
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class MyAtoi {
    private  int    sign = 1;
    private  long   ans  = 0;
    private  String state = "start";
    private Map<String,String[]> table = new HashMap<String,String[]>(){
        {
            put("start", new String[]{"start", "signed", "in_number", "end"});
            put("signed", new String[]{"end", "end", "in_number", "end"});
            put("in_number",new String[]{"end", "end", "in_number", "end"});
            put("end",new String[]{"end", "end", "end", "end"});
        }
    };


    public int get_state(Character c) {
        if(' ' == c) {
            return 0;
        }
        if('+' == c || '-'==c) {
            return 1;
        }
        if(Character.isDigit(c)) {
            return 2;
        }

        return 3;
    }
    public void get(Character c) {
        state = table.get(state)[get_state(c)];
        if("in_number".equals(state)) {
            ans = ans * 10 + c - '0';
            if(sign == 1) {
                if(ans > (long)Integer.MAX_VALUE) {
                    ans = (long)Integer.MAX_VALUE;
                    state = "end";
                }
            } else {
                if(ans > -(long)Integer.MIN_VALUE) {
                    ans = -(long)Integer.MIN_VALUE;
                    state = "end";
                }
            }
        } else if("signed".equals(state)) {
            sign = '+' == c ? 1 : -1;
        }
    }
    public int myAtoi(String str) {
        int l = str.length();
        for(int i =0; i<l; i++) {
            if("end".equals(state)) {
                break;
            }
            get(str.charAt(i));
        }
        return (int) (sign * ans);
    }

    public static void main(String[] argvs) {
        MyAtoi t = new MyAtoi();
        String str = "-989237708838734747";
        System.out.println(t.myAtoi(str));
    }
}
