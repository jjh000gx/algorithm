package alibaba.simple;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串，判断字符串是否有效。
 *
 * 有效字符串需满足：
 *
 * 左括号必须用相同类型的右括号闭合。
 * 左括号必须以正确的顺序闭合。
 * 注意空字符串可被认为是有效字符串。
 *
 * 示例 1:
 *
 * 输入: "()"
 * 输出: true
 * 示例 2:
 *
 * 输入: "()[]{}"
 * 输出: true
 * 示例 3:
 *
 * 输入: "(]"
 * 输出: false
 * 示例 4:
 *
 * 输入: "([)]"
 * 输出: false
 * 示例 5:
 *
 * 输入: "{[]}"
 * 输出: true
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/valid-parentheses
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class IsValidBrackets {

    private Map<Character, Character> bmap = new HashMap<Character, Character>(){
        {
            put(')', '(');
            put('}','{');
            put(']','[');
        }
    };

    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<Character>();
        for(int i=0; i< s.length(); i++) {
            Character c = s.charAt(i);
            if(bmap.containsKey(c)) {
                if(stack.isEmpty()) {
                    return false;
                }
                if(!bmap.get(c).equals(stack.pop())) {
                    return false;
                }
            } else {
                stack.push(c);
            }
        }
        return stack.isEmpty();
    }
    public static void main(String[] argv) {
        IsValidBrackets t = new IsValidBrackets();
        String s = "()[]{}";
        System.out.println(t.isValid(s));

    }
}
