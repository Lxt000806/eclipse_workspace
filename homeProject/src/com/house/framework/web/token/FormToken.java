package com.house.framework.web.token;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 
 * @ClassName: FormToken 
 * @Description: 代表表单token类
 *
 */
public class FormToken implements Serializable {
	/** 
	 * serialVersionUID 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String FORM_TOKEN_UNIQ_ID = "_form_token_uniq_id";
	
	/** 表单标识*/
	private String token;
	/** 表单创建时间*/
	private Date createTime;

	/** 
	 * 构造函数 
	 */
	public FormToken(String token) {
		this.token = token;
		this.createTime = new Date();
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public String getToken() {
		return token;
	}

	public Date getCreateTime() {
		return createTime;
	}
}
