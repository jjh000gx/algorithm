package alibaba.simple;

import javafx.util.Pair;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 给定一个二叉树，找出其最大深度。
 *
 * 二叉树的深度为根节点到最远叶子节点的最长路径上的节点数。
 *
 * 说明: 叶子节点是指没有子节点的节点。
 *
 * 示例：
 * 给定二叉树 [3,9,20,null,null,15,7]，
 *
 *     3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 * 返回它的最大深度 3
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/maximum-depth-of-binary-tree
 */
public class MaxDepth {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) {
            val = x;
        }
    }

    /**
     * 直观的方法是通过递归来解决问题。在这里，我们演示了 DFS（深度优先搜索）策略的示例。
     * @param root
     * @return
     */
    public int maxDepth(TreeNode root) {
        if(null == root) {
            return 0;
        } else {
            int left_depth = maxDepth(root.left);
            int right_depth = maxDepth(root.right);
            return  Math.max(left_depth,right_depth) + 1;
        }
    }

    /**
     * 广度优先算法
     * @param root
     * @return
     */
    public int maxDepth1(TreeNode root) {
        Queue<Pair<TreeNode,Integer>> stack = new LinkedList<Pair<TreeNode, Integer>>();
        if(root != null) {
            stack.add(new Pair(root, 1));
        }
        int depth = 0;
        while (!stack.isEmpty()) {
            Pair<TreeNode,Integer> current = stack.poll();
            root = current.getKey();
            int current_depth = current.getValue();
            if(null != root) {
                depth = Math.max(depth,current_depth);
                stack.add(new Pair(root.left,current_depth+1));
                stack.add(new Pair(root.right,current_depth+1));
            }
        }

        return depth;

    }

    public static void main(String[] argv) {

    }
}
