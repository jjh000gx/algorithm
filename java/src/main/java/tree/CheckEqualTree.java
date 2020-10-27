package tree;

import struct.TreeNode;

import java.util.Stack;

/**
 * 给定一棵有 n 个结点的二叉树，你的任务是检查是否可以通过去掉树上的一条边将树分成两棵，且这两棵树结点之和相等。
 *
 * 样例 1:
 *
 * 输入:
 *     5
 *    / \
 *   10 10
 *     /  \
 *    2   3
 *
 * 输出: True
 * 解释:
 *     5
 *    /
 *   10
 *
 * 和: 15
 *
 *    10
 *   /  \
 *  2    3
 *
 * 和: 15
 *  
 *
 * 样例 2:
 *
 * 输入:
 *     1
 *    / \
 *   2  10
 *     /  \
 *    2   20
 *
 * 输出: False
 * 解释: 无法通过移除一条树边将这棵树划分成结点之和相等的两棵子树
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/equal-tree-partition
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class CheckEqualTree {
    private Stack<Integer> seen;
    /**
     * 方法 1：深度优先搜索
     * 想法和算法
     *
     * 通过移除一些 parent 到 child 的边，（其中 child 不能是原始 root）以 root 为根的子树元素之和一定是整棵树和的一般。
     *
     * 记录每个子树的和。我们可以利用深度优先搜索递归解决。之后，检查整棵树的一半权值是否出现（但不能是整棵树之和）。
     *
     * 我们这种特殊的判断是为了避免以下情况的发生。
     *
     * 作者：LeetCode
     * 链接：https://leetcode-cn.com/problems/equal-tree-partition/solution/jun-yun-shu-hua-fen-by-leetcode/
     * 来源：力扣（LeetCode）
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     * @return
     */
    public Boolean checkEqualTree(TreeNode root) {
        if(null == root) {
            return false;
        }
        seen = new Stack<>();
        Integer total = sum(root);
        seen.pop();
        //System.out.println(seen.toString());
        //System.out.println(seen.pop());
        if(total%2 == 0) {
            Integer half = total/2;
            for(Integer t : seen) {
                if(half == t) {
                    return true;
                }
            }
        }
        return false;
    }

    public Integer sum(TreeNode root) {
        if(null == root) {
            return 0;
        }
        seen.push((sum(root.left)+sum(root.right)+root.val));

        return seen.peek();
    }

    public static void main(String[] argv) {
        CheckEqualTree t = new CheckEqualTree();
        TreeNode root = new TreeNode(5);
        root.left  = new TreeNode(10);
        root.right = new TreeNode(10);
        root.right.left = new TreeNode(2);
        root.right.right = new TreeNode(3);
        Boolean b = t.checkEqualTree(root);
        System.out.println(b);
    }
}
