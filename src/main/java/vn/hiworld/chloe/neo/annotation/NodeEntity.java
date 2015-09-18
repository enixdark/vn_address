package vn.hiworld.chloe.neo.annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE})
@Inherited
public @interface NodeEntity {
	static final String CLASS = "vn.hiworld.chloe.neo.annotation.NodeEntity";
    static final String LABEL = "label";
    static final String PARENT = "parent";
    static final String CHILD = "child";

	String label() default "";
	String parent() default "";
	String child() default "";

}
