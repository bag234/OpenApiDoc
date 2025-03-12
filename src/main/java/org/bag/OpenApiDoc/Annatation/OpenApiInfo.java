package org.bag.OpenApiDoc.Annatation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(SOURCE)
@Target(TYPE)
public @interface OpenApiInfo {

	String title() default "Un Named";
	
	String version() default "Un ver";
	
}
