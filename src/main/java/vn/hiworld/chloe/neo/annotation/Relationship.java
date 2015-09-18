package vn.hiworld.chloe.neo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@Inherited
public @interface Relationship {

    static final String CLASS = "vn.hiworld.chloe.neo.annotation.Relationship";
    static final String TYPE = "type";
//    static final String PARENT = "parent";
//    static final String CHILD = "child";
    static final String DIRECTION = "direction";

    static final String INCOMING = "INCOMING";
    static final String OUTGOING = "OUTGOING";
    static final String UNDIRECTED = "UNDIRECTED";

    String type() default "";
    String direction() default OUTGOING;
//    boolean child() default false;
//    boolean parent() default false;

}
