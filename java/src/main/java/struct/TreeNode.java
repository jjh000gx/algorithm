package struct;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {
    public int val;
    public TreeNode left;
    public TreeNode right;
    TreeNode root;
    public TreeNode(int x) {
        val = x;
    }
    public TreeNode() {}

    public TreeNode getRoot() {
        return root;
    }
    public void setRoot(TreeNode root) {
        this.root = root;
    }

    /**
     * 构造完全二叉树
     * @param data
     */
    public void CompleteBinaryTree(int[] data) {
        List<TreeNode> list =  new ArrayList<TreeNode>();
        for(int d : data) {
            list.add(new TreeNode(d));
        }
        root = list.get(0);
        int size = list.size();
        for(int i=0;i<size/2;i++) {
            if(i*2 +1 < size) {
                list.get(i).left = list.get(2*i+1);
            }
            if(i*2 +2 < size) {
                list.get(i).right = list.get(2*i+2);
            }
        }
    }

    /**
     * 前序遍历
     */
    public List<Integer> preOrder(TreeNode root, List<Integer> list) {
        if(null != root) {
            list.add(root.val);
            System.out.print("  " + root.val);
            preOrder(root.left, list);
            preOrder(root.right,list);
        }
        return list;
    }

    public static void main(String[] argv) {
        int[] data = {1,2,3,4,5,6,7};
        TreeNode t = new TreeNode();
        t.CompleteBinaryTree(data);
        List<Integer> list = new ArrayList<Integer>();
        t.preOrder(t.root,list);
        System.out.println(list.toString());
        list.toArray();
    }
}
