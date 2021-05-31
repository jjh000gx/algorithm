import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Generic {
    public static void main(String[] argv) {
        int[] a = new int[1];
        List<String> value1 = new ArrayList<String>();

        value1.add("1");
        /**
         * 2. 这里编译器不会报错
         */
        List<? extends String> value2 = value1;
        System.out.println(value2.toString());
        //value2.add("1");
        new LinkedList();
    }
}
