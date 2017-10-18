package framework.annotation.model;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OneToMany {
    String table() default "";

    String column() default "";
}
