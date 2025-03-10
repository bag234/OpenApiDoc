package org.bag.OpenApiDoc.Annatation;

import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target(TYPE)
public @interface OpenApiDoc {

	String path();
	
	String summary() default "";
	
}
