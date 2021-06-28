package alibaba.medium;
import struct.TreeNode;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @name 通过前序遍历和中序遍历 数组 构建 二叉树
 * https://leetcode-cn.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/solution/cong-qian-xu-yu-zhong-xu-bian-li-xu-lie-gou-zao-9/
 */
public class BuildTree {
    private Map<Integer,Integer> indexMap = new HashMap<Integer,Integer>();

    /**
     * @name 递归
     *  实现思路如下：
     *  前序遍历的规则为：
     *   [根节点，[左子树前序遍历]，[右子树前序遍历]]
     *  中序遍历的规则为：
     *    [[左子树中序遍历]，根节点，[右子树中序遍历]]
     *
     *    根据规则，我们可以知道 前序遍历 第一元素为根节点， 然后通过 中序遍历的数组 确定根节点元素
     *    在中序遍历数组的下标位置。就可以确定 左右子树的位置和长度了
     *
     *
     * @param preOrder
     * @param inorder
     * @param pre_left
     * @param pre_right
     * @param in_left
     * @param in_right
     * @return
     */
    public TreeNode buildRecurSionTree(int[] preOrder,int[] inorder,int pre_left,int pre_right,int in_left,int in_right)
    {
        if(pre_left>pre_right) {
            return  null;
        }
        //获取中序遍历数组根节点的位置
        int in_root= indexMap.get(preOrder[pre_left]);
        TreeNode root = new TreeNode(preOrder[pre_left]);
        //左子树的节点数组
        int size_m = in_root - in_left;

        root.left = buildRecurSionTree(preOrder,inorder,pre_left+1,pre_left+size_m,in_left,in_root-1);
        root.right = buildRecurSionTree(preOrder,inorder,pre_left+size_m+1,pre_right,in_root+1,in_right);
        return root;
    }

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        int n = inorder.length;
        for(int i=0; i<n; i++) {
            indexMap.put(inorder[i], i);
        }
        return  buildRecurSionTree(preorder,inorder,0,n-1,0,n-1);

    }
    public static void main(String[] argvs) {
        int[] preOrder = new int[]{3,9,20,15,7};
        int[] inOrder = new int[]{9,3,15,20,7};

        TreeNode t = new BuildTree().buildTree(preOrder,inOrder);
        System.out.println(t);

    }

    /**
     * @name 迭代 实现  利用栈的数据结构辅助
     *
     * 
     *
     * @param preorder
     * @param inorder
     * @return
     */
    public TreeNode buildInteraTree(int[] preorder, int[] inorder) {
        if (preorder == null || preorder.length == 0) {
            return null;
        }
        TreeNode root = new TreeNode(preorder[0]);
        Deque<TreeNode> stack = new LinkedList<TreeNode>();
        stack.push(root);
        int inorderIndex = 0;
        for (int i = 1; i < preorder.length; i++) {
            int preorderVal = preorder[i];
            TreeNode node = stack.peek();
            if (node.val != inorder[inorderIndex]) {
                node.left = new TreeNode(preorderVal);
                stack.push(node.left);
            } else {
                while (!stack.isEmpty() && stack.peek().val == inorder[inorderIndex]) {
                    node = stack.pop();
                    inorderIndex++;
                }
                node.right = new TreeNode(preorderVal);
                stack.push(node.right);
            }
        }
        return root;
    }

}
