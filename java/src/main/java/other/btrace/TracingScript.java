package other.btrace;

import com.sun.btrace.annotations.*;
import static com.sun.btrace.BTraceUtils.*;

/**
 * https://www.cnblogs.com/wei-zw/p/9502274.html
 * https://www.cnblogs.com/serendipity/archive/2012/05/14/2499840.html
 * cmd  btrace  pid TracingScript.java
 */
@BTrace
public class TracingScript {
    /* put your code here */
    /*指明要查看的方法，类*/
    @OnMethod(
            clazz="other.btrace.CaseObject",
            method="execute",
            location=@Location(Kind.RETURN)
    )
    /*主要两个参数是对象自己的引用 和 返回值，其它参数都是方法调用时传入的参数*/
    public static void traceExecute(@Self Object object,int sleepTime, @Return boolean result) {
        println("调用堆栈！！");
        println(strcat("返回结果是：", str(result)));
        jstack();
        println(strcat("时间是：", str(sleepTime)));
    }

}
