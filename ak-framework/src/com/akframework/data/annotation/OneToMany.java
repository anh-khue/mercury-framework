package com.akframework.data.annotation;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OneToMany {
    String table() default "";

    String column() default "";
}