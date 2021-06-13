package com.house.framework.tag;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.commons.lang3.StringUtils;
import com.house.framework.commons.conf.CommonConstant;
import com.house.framework.web.login.UserContext;
import com.house.framework.web.login.UserContextHolder;

public class AuthorizeTag extends TagSupport {
	private static final long serialVersionUID = 1L;
	
	/**权限码，开发人员提供与相应的操作的权限码，系统根据此权限码进行过滤 */
	private String authCode;
	
	private boolean hasAuth = true;
	
	private String custStatus;
	
	public int doStartTag() throws JspException {
		if(StringUtils.isBlank(authCode)){
			if(hasAuth){
				return SKIP_BODY;
			}else{
				return EVAL_BODY_INCLUDE;
			}
		}
		
		UserContext uc = UserContextHolder.getUserContext();
		HttpServletRequest request =null;
		if(uc == null){
			request = (HttpServletRequest) pageContext.getRequest();
			uc = (UserContext)request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
		}
		if(uc == null){
			JspWriter out = pageContext.getOut();
			try {
				StringBuffer strb = new StringBuffer();
				strb.append("<SCRIPT type=\"text/javascript\">")
					.append("alert(\"用户未登入，请重新登入\");")
					.append("window.top.location.href=\"")
					.append(request.getContextPath())
					.append("\";")
					.append("</SCRIPT>");
				out.print(strb.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				try {
					if(out != null)
						out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		if(uc != null){
			boolean judgeAuth = false; 
			if(StringUtils.isNotBlank(uc.getAuthStr())){
				String[] auths=authCode.trim().split("\\|");
				for(int i=0;i<auths.length;i++){
					if(uc.getAuthStr().indexOf(","+auths[i]+",") != -1){
						if("CUSTOMERXX_PHONE_CON".equals(auths[i])||"CUSTOMERXX_PHONE_COMPLETE".equals(auths[i])){
							if("CUSTOMERXX_PHONE_CON".equals(auths[i])){
								if("4".equals(custStatus)){
									judgeAuth = true;
								}
							} 
							if("CUSTOMERXX_PHONE_COMPLETE".equals(auths[i])){
								if("5".equals(custStatus)){
									judgeAuth = true;
								}
							}
						}else{
							judgeAuth = true;
						}
					}
				}
			}
			if(uc.isSuperAdmin() || judgeAuth){
				if(hasAuth){
					return EVAL_BODY_INCLUDE;
				}else{
					return SKIP_BODY;
				}
			}
		}
		if(!hasAuth){
			return EVAL_BODY_INCLUDE;
		}
		return SKIP_BODY;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public boolean isHasAuth() {
		return hasAuth;
	}

	public void setHasAuth(boolean hasAuth) {
		this.hasAuth = hasAuth;
	}

	public String getCustStatus() {
		return custStatus;
	}

	public void setCustStatus(String custStatus) {
		this.custStatus = custStatus;
	}
}
