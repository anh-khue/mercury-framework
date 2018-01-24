package io.osiris.data.orm.annotation;

import java.lang.annotation.*;

@Deprecated
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ManyToOne {
    String column() default "";

    String referencedTable() default "";
}
