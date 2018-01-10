package com.akframework.data.annotation;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ManyToOne {
    String column() default "";

    String referencedTable() default "";
}
