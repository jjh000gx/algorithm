package jvm;

import java.io.InputStream;

/**
 * 类加载器与instanceof 关键词演示
 */
public class ClassLoaderTest {
    public static void main(String[] args) throws Exception {
        ClassLoader myLoader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                try {
                    String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";
                    InputStream is = getClass().getResourceAsStream(fileName);
                    if(is == null) {
                        return super.loadClass(name);
                    }
                    byte[] b = new byte[is.available()];
                    is.read(b);
                    return defineClass(name,b,0,b.length);
                } catch (Exception e) {
                    throw new ClassNotFoundException(name);
                }
            }
        };

        Object obj = myLoader.loadClass("jvm.ClassLoaderTest");
        System.out.println(obj.getClass());
        System.out.println(obj instanceof jvm.ClassLoaderTest);

        ClassLoaderTest a = new ClassLoaderTest();
        System.out.println(a.getClass());
        System.out.println(a instanceof jvm.ClassLoaderTest);
    }
}
