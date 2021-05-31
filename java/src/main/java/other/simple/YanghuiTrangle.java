package other.simple;

import java.util.ArrayList;
import java.util.List;

/**
 * 在杨辉三角中，每个数是它左上方和右上方的数的和。
 * https://leetcode-cn.com/problems/pascals-triangle/submissions/
 */
public class YanghuiTrangle {
    public List<List<Integer>> generate(int numRows) {
         List<List<Integer>> yanhuiList = new ArrayList<>();
         if(numRows <= 0) {
            return  yanhuiList;
         }
        yanhuiList.add(new ArrayList<>());
        yanhuiList.get(0).add(1);
         for(int i=1; i< numRows; i++) {
             yanhuiList.add(new ArrayList<>());
             yanhuiList.get(i).add(1);
             for(int j=1;j<yanhuiList.get(i-1).size(); j++) {
                int t = yanhuiList.get(i-1).get(j-1) + yanhuiList.get(i-1).get(j);
                 yanhuiList.get(i).add(t);
             }
             yanhuiList.get(i).add(1);
         }
        return yanhuiList;
    }

    public static void main(String[] argv) {
        YanghuiTrangle tt = new YanghuiTrangle();
        List<List<Integer>> yanhui = tt.generate(5);
        System.out.println(yanhui);

    }
}
