package io.osiris.data.common.annotation;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ManyToOne {

    String column() default "";

    String table() default "";

    String target() default "";
}
