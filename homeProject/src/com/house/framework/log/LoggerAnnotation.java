package com.house.framework.log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)

public @interface LoggerAnnotation {
	
	String okLog() default "";
	
	String begin() default "";
	
	String errLog() default "";
	
	String modelCode() default "";
	
	String ip() default "";
	
	String from() default "";
	
	String user() default "";
	
	long modelType() default 0;
	
	String loginModel() default "";
	
}
