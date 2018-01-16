package com.osiris.data.common.annotation;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OneToMany {
    String table() default "";

    String referenceColumn() default "";
}
