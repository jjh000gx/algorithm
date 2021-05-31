package jdk8;

import java.util.function.BiFunction;
import java.util.function.Function;

public class BiFunctionTest {
    public static void main(String[] args){
        BiFunctionTest test = new BiFunctionTest();
        //实现四则运算
        System.out.println(test.compute(4,2,(value1,value2)->value1+value2));
        System.out.println(test.compute(4,2,(v1,v2)->v1-v2));
        System.out.println(test.compute(1,2,(v1,v2)->v1*v2));
        System.out.println(test.compute(3,2,(v1,v2)->v1/v2));
        System.out.println(test.calcute(3,4,(v1,v2)->v1+v2,v->v + v));
    }

    public  int compute(int num1, int num2, BiFunction<Integer,Integer,Integer> biFunction){
        return  biFunction.apply(num1,num2);
    }

    public int calcute(int num1, int num2, BiFunction<Integer,Integer,Integer> biFunction, Function<Integer,Integer> function){
        //调用addThen首先对接收的两个参数进行bifunction的apply，然后在进行function的apply
        return biFunction.andThen(function).apply(num1,num2);
    }
}
