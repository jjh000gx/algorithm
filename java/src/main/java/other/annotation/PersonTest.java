package other.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class PersonTest {
    public static void main(String[] args) throws Exception {

        Person person = new Person();
        Class pers = person.getClass();
        // 反射拿到方法
        Method method= pers.getDeclaredMethod("getPerson");
        // 获取注解
        PersonAnno ana = method.getAnnotation(PersonAnno.class);
        System.out.println(Arrays.toString(PersonAnno.class.getDeclaredMethods()));

        System.out.println(ana.name() + ":" + ana.age() + ":" + ana.annotationType());


        Annotation[] annotations = method.getAnnotations();
        for(Annotation annotation : annotations){
            System.out.println(annotation.getClass());
            System.out.println(annotation.annotationType());
            System.out.println(Arrays.toString(annotation.getClass().getDeclaredMethods()));
        }
    }
}
