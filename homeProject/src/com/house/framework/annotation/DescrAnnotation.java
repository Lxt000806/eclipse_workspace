package com.house.framework.annotation;
 
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
 
@Retention(RetentionPolicy.RUNTIME)
public @interface DescrAnnotation {
    //字段的描述注解
     String descr();
    
}
