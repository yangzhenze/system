package com.swsk.data.util.generate.anotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target(ElementType.FIELD)
@Retention(RUNTIME)
public @interface Column {

    String name() default "";
    boolean isPK() default false;
}
