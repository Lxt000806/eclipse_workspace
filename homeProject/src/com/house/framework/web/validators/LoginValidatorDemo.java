package com.house.framework.web.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class LoginValidatorDemo implements Validator {
	public boolean supports(Class<?> aClass) {
		return true;
	}

	public void validate(Object o, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "logname", "用户名不能为空");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "密码不能为空");
		//rejectIfEmptyOrWhitespace不能为空或空白  
		//第一个logname,password为实体的属性名(根据此名验证属性)  
		//第二个logname,password为errorCode(前提根据errorCode获取错误提示信息)  
		//汉字为错误提示信息
	}
}
