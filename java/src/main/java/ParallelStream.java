import java.util.*;
import java.util.stream.Collectors;

public class ParallelStream {
    public static void main(String[] argv) {
        //过滤
        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
        List<String> filtered = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.toList());
        System.out.println(filtered);

        //迭代
        Random random = new Random();
        random.ints().limit(10).forEach(System.out::println);

        //统计
        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
        IntSummaryStatistics stats = numbers.stream().mapToInt((x) -> x).summaryStatistics();

        System.out.println("列表中最大的数 : " + stats.getMax());
        System.out.println("列表中最小的数 : " + stats.getMin());
        System.out.println("所有数之和 : " + stats.getSum());
        System.out.println("平均数 : " + stats.getAverage());

        //map 操作
        List<Integer> numbers1 = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
        // 获取对应的平方数
        List<Integer> squaresList = numbers1.stream().map( i -> i*i).distinct().collect(Collectors.toList());
        //map 操作: [9, 4, 49, 25]
        System.out.println("map 操作: " + squaresList);

        //numbers1.stream().mapToInt(x->x)

        Long count = strings.stream().filter(string->string.isEmpty()).count();
        System.out.println("空字符串数量为: " + count);
        // 并行处理
        count = strings.parallelStream().filter(string -> string.isEmpty()).count();
        System.out.println("空字符串的数量为: " + count);

        //Collectors
        //Collectors 类实现了很多归约操作，例如将流转换成集合和聚合元素。Collectors 可用于返回列表或字符串：

        String st = strings.stream().collect(Collectors.joining(","));
        System.out.println("列表合并为字符串: " + st);
       //"abc", "", "bc", "efg", "abcd","", "jkl"
       Set<String> st1 = strings.stream().collect(Collectors.toSet());
        System.out.println("列表合并为set: " + st1);
        //IntSummaryStatistics stats1 =  strings.stream().mapToInt((x)->Integer.valueOf(x)).summaryStatistics();
    }
}
