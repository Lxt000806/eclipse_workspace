package com.house.framework.annotation;
 
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
 
@Retention(RetentionPolicy.RUNTIME)
public @interface NoteAnnotation {
     /**
      * 所属表名
      * @return
      */
     String tableName();
    
     /**
      * 数据库：编码字段名,缓存：编码字段值
      * @return
      */
     String code();
     
     /**
      * 中文名称字段名
      * @return
      */
     String note() default "";
}
