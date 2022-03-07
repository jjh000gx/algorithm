public class Tests {
    public static void main(String[] argvs) {
        String a = "abc";
        String b = "abc";
        String c = new String("abc").intern();
        if(a == c) {
            System.out.println("a=c");
        }
    }
}
