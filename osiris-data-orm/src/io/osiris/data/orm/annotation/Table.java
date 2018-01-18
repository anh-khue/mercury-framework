package io.osiris.data.orm.annotation;

import java.lang.annotation.*;

@Deprecated
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Table {
    String table() default "";
}