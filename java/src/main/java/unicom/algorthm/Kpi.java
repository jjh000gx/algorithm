package unicom.algorthm;

/**
 * @description kpi计算
 * @author jiangjianhe
 * @createDate 2022-03-05
 */
public class Kpi {
    /**
     * 最少年终总额计算
     * @param data
     * @return
     */
    public int lestSumBonus(int[] data) {

        if (null == data || 0 == data.length) {
            return 0;
        }
        int len          = data.length;
        if (1 == len ) {
            return 1;
        }

        int tempd        = data[0];
        int sum          = 1;
        int currentBonus =  1;
        //int[] sumData = new int[len];
        for (int i=1;i<len;i++) {
            if(data[i] > tempd) {
                currentBonus++;
            }
            if(data[i] < tempd) {
                int j = i;
                //遍历到小于上一个数据时，用于初始奖金为1
                currentBonus = 1;
                //需要回溯之前的数据
                int ttempd = data[i];
                while (--j >= 0) {
                    if(data[j] > ttempd) {
                        ttempd = data[j];
                        //如果不是第一个元素，需要判断 上上个元素需要大于上个元素才可以加1
                        if((j-1>0 && data[j-1] > data[j]) || j==0 ) {
                            sum += 1;
                        }
                        continue;
                    }
                    break;
                }
                //sum += i-j-1;
            }
            tempd = data[i];
            sum += currentBonus;
            //sumData[i] = currentBonus;
        }

        return sum;
    }


    public static void main(String[] argvs) {
        Kpi tt = new Kpi();

        int[] data = new int[]{8,6,7};
        System.out.println(tt.lestSumBonus(data));
    }
}
