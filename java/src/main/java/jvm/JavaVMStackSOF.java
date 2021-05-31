package jvm;

/**
 * vm Args: -Xss128k
 */
public class JavaVMStackSOF {
    private int stackLength = 1;
    public  void stackLeak() {
        stackLength++;
        stackLeak();
    }

    public static void main(String[] argvs) throws Throwable {
        JavaVMStackSOF oom = new JavaVMStackSOF();
        try {
            oom.stackLeak();
        } catch (Throwable e) {
            System.out.println("stack lenth:" + oom.stackLength);
            throw e;
        }
    }
}
