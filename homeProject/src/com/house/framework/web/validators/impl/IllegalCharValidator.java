package com.house.framework.web.validators.impl;

import java.util.regex.Matcher;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import com.house.framework.commons.conf.SystemConfig;
import com.house.framework.web.validators.IllegalChar;

/**
 * 非法字符校验实现类
 *
 */
public class IllegalCharValidator implements ConstraintValidator<IllegalChar, String> {
	
	//String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";

	public void initialize(IllegalChar illegalChar) {
	}

	public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
		
		if ( value == null || value.length() == 0 ) {
			return true;
		}
		
		java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(
				"<[^<|^>]*>",
				java.util.regex.Pattern.CASE_INSENSITIVE
		);
		
		Matcher m = pattern.matcher( value );
		 if(m.find()){return false;}
		 
		 
		 String illegalChar = SystemConfig.getProperty("illegal_char", "", "ILLEGAL_CHAR");
		 String[] illegalChars = illegalChar.split(",");
		 
		 for(String tmp : illegalChars){
			 if(value.indexOf(tmp) > -1){return false;}
		 }
		 
		 return true;
	}

}
