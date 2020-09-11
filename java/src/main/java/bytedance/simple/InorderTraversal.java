package bytedance.simple;

import struct.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 给定一个二叉树，返回它的中序 遍历。
 *
 * 示例:
 *
 * 输入: [1,null,2,3]
 *    1
 *     \
 *      2
 *     /
 *    3
 *
 * 输出: [1,3,2]
 * 进阶: 递归算法很简单，你可以通过迭代算法完成吗？
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/binary-tree-inorder-traversal
 */
public class InorderTraversal {
    /**
     * 方法一：递归
     * 第一种解决方法是使用递归。这是经典的方法，直截了当。我们可以定义一个辅助函数来实现递归。
     * @param root
     * @return
     */
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<Integer>();
        helper(root,list);
        return list;
    }
    public void helper(TreeNode root, List<Integer> list) {
        if(null != list) {
            if(root.left != null) {
                helper(root.left,list);
            }
            list.add(root.val);
            if(root.right != null) {
                helper(root.right,list);
            }
        }
    }

    /**
     * 方法二：基于栈的遍历
     * 本方法的策略与上衣方法很相似，区别是使用了栈。
     *
     * 下面是示例:
     * @param root
     * @return
     */
    public List<Integer> inorderTraversal1(TreeNode root) {
        List<Integer> list = new ArrayList<Integer>();
        Stack<TreeNode> stack = new Stack<TreeNode>();
        TreeNode curr = root;
        while (curr!=null || !stack.isEmpty()) {
            while (curr!= null) {
                stack.push(curr);
                curr = curr.left;
            }
            curr = stack.pop();
            list.add(curr.val);
            curr = curr.right;
        }

        return list;
    }

    /**
     * 方法三：莫里斯遍历
     * 本方法中，我们使用一种新的数据结构：线索二叉树。方法如下：
     *
     * Step 1: 将当前节点current初始化为根节点
     *
     * Step 2: While current不为空，
     *
     * 若current没有左子节点
     *
     *     a. 将current添加到输出
     *
     *     b. 进入右子树，亦即, current = current.right
     *
     * 否则
     *
     *     a. 在current的左子树中，令current成为最右侧节点的右子节点
     *
     *     b. 进入左子树，亦即，current = current.left
     * 举例而言:
     *
     *
     *
     *           1
     *         /   \
     *        2     3
     *       / \   /
     *      4   5 6
     *
     * 首先，1 是根节点，所以将 current 初始化为 1。1 有左子节点 2，current 的左子树是
     *
     *
     *          2
     *         / \
     *        4   5
     * 在此左子树中最右侧的节点是 5，于是将 current(1) 作为 5 的右子节点。令 current = cuurent.left (current = 2)。
     * 现在二叉树的形状为:
     *
     *
     *          2
     *         / \
     *        4   5
     *             \
     *              1
     *               \
     *                3
     *               /
     *              6
     * 对于 current(2)，其左子节点为4，我们可以继续上述过程
     *
     *
     *         4
     *          \
     *           2
     *            \
     *             5
     *              \
     *               1
     *                \
     *                 3
     *                /
     *               6
     * 由于 4 没有左子节点，添加 4 为输出，接着依次添加 2, 5, 1, 3 。节点 3 有左子节点 6，故重复以上过程。
     * 最终的结果是 [4,2,5,1,6,3]。
     *
     * 作者：LeetCode
     * 链接：https://leetcode-cn.com/problems/binary-tree-inorder-traversal/solution/er-cha-shu-de-zhong-xu-bian-li-by-leetcode/
     * @param root
     * @return
     */
    public List<Integer> inorderTraversal2(TreeNode root) {
        List<Integer> list = new ArrayList<Integer>();

        return list;
    }
}
