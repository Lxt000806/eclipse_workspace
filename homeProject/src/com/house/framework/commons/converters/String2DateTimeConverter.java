package com.house.framework.commons.converters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;
import org.springframework.core.convert.converter.Converter;

/**
 * 
 * @ClassName: CustomerConverter 
 * @Description: 测试校验器, 优先级低于定义注解的@DateTimeFormat
 *
 */
public class String2DateTimeConverter implements Converter<String, Date>{
	
    public  Date convert(String source) {
    	if (source == null || source.equals("")){
        	return null;
        }
    	SimpleDateFormat dateFormat = null;
    	if(Pattern.compile("\\d{4}-\\d{2}-\\d{2}").matcher(source).matches()){
    		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	}else if(Pattern.compile("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}").matcher(source).matches()){
    		dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	}else{
    		return null;
    	}
        dateFormat.setLenient(false);
        
        try {
            return dateFormat.parse(source);
        } catch (ParseException e) {
            e.printStackTrace();
        }        
        return null;
    }
    
}
