package com.house.framework.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.web.token.FormToken;
import com.house.framework.web.token.FormTokenManager;
import com.house.framework.web.token.impl.FormTokenManagerImpl;

public class FormTokenTag extends TagSupport {
	private static final long serialVersionUID = 1L;

	
	public int doStartTag() {
		FormTokenManager formTokenManager = SpringContextHolder.getBean("formTokenManagerImpl", FormTokenManagerImpl.class);
		HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
		FormToken formToken = formTokenManager.newFormToken(request);
		
		//jspContext.setAttribute("formToken", formToken);
		StringBuffer strb = new StringBuffer();
		
		strb.append("<input type=\"hidden\" name=\"_form_token_uniq_id\" id=\"_form_token_uniq_id\" value=\"" + formToken.getToken() + "\" />");
		
		JspWriter out = pageContext.getOut();
		try{
			out.print(strb.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return SKIP_BODY;
	}
}
