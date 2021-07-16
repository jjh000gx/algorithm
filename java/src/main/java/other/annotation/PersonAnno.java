package other.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PersonAnno {

    String name() default "";
    int age() default 0;

    Class<?>[] groups() default { };


}
