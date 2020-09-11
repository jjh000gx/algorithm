package alibaba.simple;

import struct.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 翻转一棵二叉树。
 *
 * 示例：
 *
 * 输入：
 *
 *      4
 *    /   \
 *   2     7
 *  / \   / \
 * 1   3 6   9
 * 输出：
 *
 *      4
 *    /   \
 *   7     2
 *  / \   / \
 * 9   6 3   1
 * 备注:
 * 这个问题是受到 Max Howell 的 原问题 启发的 ：
 *
 * 谷歌：我们90％的工程师使用您编写的软件(Homebrew)，但是您却无法在面试时在白板上写出翻转二叉树这道题，这太糟糕了。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/invert-binary-tree
 */
public class InvertTree {
    /**
     * 方法一 （递归） 【通过】
     * 这是一个非常经典的树的问题，这个问题很适合用递归方法来解决。
     *
     * 算法
     *
     * 反转一颗空树结果还是一颗空树。对于一颗根为 rr，左子树为 mbox{right}mboxright， 右子树为 mbox{left}mboxleft 的树来说，它的反转树是一颗根为 rr，左子树为 mbox{right}mboxright 的反转树，右子树为 mbox{left}mboxleft 的反转树的树。
     *
     * 作者：LeetCode
     * 链接：https://leetcode-cn.com/problems/invert-binary-tree/solution/fan-zhuan-er-cha-shu-by-leetcode/
     * 来源：力扣（LeetCode）
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     * @param root
     * @return
     */
    public TreeNode invertTree(TreeNode root) {
        if(root == null) {
            return null;
        }
        TreeNode right = invertTree(root.right);
        TreeNode left = invertTree(root.left);
        root.left = right;
        root.right = left;
        return root;
    }

    /**
     *方法二 （迭代） 【通过】
     * 我们也可以用迭代方法来解决这个问题，这种做法和深度优先搜索（Breadth-fist Search, BFS）很接近。
     *
     * 算法
     *
     * 这个方法的思路就是，我们需要交换树中所有节点的左孩子和右孩子。
     * 因此可以创一个队列来存储所有左孩子和右孩子还没有被交换过的节点。
     * 开始的时候，只有根节点在这个队列里面。
     * 只要这个队列不空，就一直从队列中出队节点，然后互换这个节点的左右孩子节点，接着再把孩子节点入队到队列，
     * 对于其中的空节点不需要加入队列。最终队列一定会空，这时候所有节点的孩子节点都被互换过了，
     * 直接返回最初的根节点就可以了。
     *
     * 作者：LeetCode
     * 链接：https://leetcode-cn.com/problems/invert-binary-tree/solution/fan-zhuan-er-cha-shu-by-leetcode/
     * @param root
     * @return
     */
    public TreeNode invertTree1(TreeNode root) {
        if(root == null) return null;
        Queue<TreeNode> list = new LinkedList<TreeNode>();
        list.add(root);
        while (!list.isEmpty()) {
            TreeNode current = list.poll();
            TreeNode temp = current.left;
            current.left = current.right;
            current.right = temp;
            if(current.right != null) list.add(current.right);
            if(current.left != null) list.add(current.left);
        }

        return root;
    }




}
