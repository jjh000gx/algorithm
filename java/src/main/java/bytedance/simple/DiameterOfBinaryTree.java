package bytedance.simple;

import struct.TreeNode;

/**
 * 给定一棵二叉树，你需要计算它的直径长度。一棵二叉树的直径长度是任意两个结点路径长度中的最大值。这条路径可能穿过也可能不穿过根结点。
 *
 *  
 *
 * 示例 :
 * 给定二叉树
 *
 *           1
 *          / \
 *         2   3
 *        / \
 *       4   5
 * 返回 3, 它的长度是路径 [4,2,1,3] 或者 [5,2,1,3]。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/diameter-of-binary-tree
 */
public class DiameterOfBinaryTree {
    private int ans;
    public int diameterOfBinaryTree(TreeNode root) {
        ans = 1;
        depth(root);
        return ans-1;
    }

    public int depth(TreeNode root) {
        if(root == null) {
            return 0;
        }
        int R = depth(root.right);
        int L = depth(root.left);
        ans = Math.max(ans,L+R+1);
        return Math.max(R,L) + 1;
    }

    public static void main(String[] argv) {
        TreeNode node = new TreeNode();
        int[] data = {1,2,2,3,4,4,1};
        node.CompleteBinaryTree(data);
        DiameterOfBinaryTree t = new DiameterOfBinaryTree();
        int d = t.diameterOfBinaryTree(node.getRoot());
        System.out.println(d);
    }
}
