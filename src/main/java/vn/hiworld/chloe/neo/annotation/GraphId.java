package vn.hiworld.chloe.neo.annotation;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@Inherited
public @interface GraphId {

    static final String CLASS = "vn.hiworld.chloe.neo.annotation.GraphId";
    static final String NAME = "name";

    String name() default "";

}

