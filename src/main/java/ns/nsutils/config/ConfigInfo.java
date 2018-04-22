package ns.nsutils.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface ConfigInfo
{
	String category();
	
	String key();
	
	String info() default "";
	
	boolean clientOnly() default false;
	
	boolean serverOnly() default false;
}
