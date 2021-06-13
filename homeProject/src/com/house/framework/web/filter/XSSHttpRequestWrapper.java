package com.house.framework.web.filter;

import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.house.framework.commons.utils.StringUtil;

/**
 * @describe request信息封装类，用于判断、处理request请求中特殊字符
 */
public class XSSHttpRequestWrapper extends HttpServletRequestWrapper {

	protected Log logger = LogFactory.getLog(this.getClass());

	private String charset = "UTF-8";

	private Map<String, Object> params = new ConcurrentHashMap<String, Object>();

	/**
	 * 封装http请求
	 * 
	 * @param request
	 */
	public XSSHttpRequestWrapper(HttpServletRequest request)
			throws UnsupportedEncodingException {
		super(request);
		if (super.getCharacterEncoding() == null) {
			super.setCharacterEncoding(charset);
		}
	}

	public XSSHttpRequestWrapper(HttpServletRequest request, String charset)
			throws UnsupportedEncodingException {
		super(request);
		if (super.getCharacterEncoding() == null) {
			super.setCharacterEncoding(charset);
		}
	}

	/**
	 * 过滤用户请求XSS内容 void
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void validateXssFilter() {
		Enumeration<String> nameEnums = super.getParameterNames();
		while (nameEnums.hasMoreElements()) {
			parameterFilter(nameEnums.nextElement());
		}
	}

	/**
	 * 过滤变量的特殊字符
	 * 
	 * @param name
	 */
	private void parameterFilter(String name) {
		String values[] = super.getParameterValues(name);
		params.put(name, parameterXss(values));
	}

	/**
	 * 过滤变量
	 * 
	 * @param values
	 * @return String[]
	 */
	private String[] parameterXss(String... values) {
		if (values == null || values.length < 1)
			return values;
		for (int i = 0; i < values.length; i++) {
			if (StringUtil.isEmpty(values[i])) {
				continue;
			}
			values[i] = XSSSecurityManager.filter(getRequestURI(),values[i]);
		}
		return values;
	}

	public String[] getParameterValues(String name) {
		Object v = params.get(name);
		if (v == null) {
			return super.getParameterValues(name);
		} else if (v instanceof String[]) {
			return (String[]) v;
		} else if (v instanceof String) {
			return new String[] { (String) v };
		} else {
			return new String[] { v.toString() };
		}
	}

	
	public String getParameter(String name) {
		Object v = params.get(name);
		if (v == null) {
			return super.getParameter(name);
		} else if (v instanceof String[]) {
			String[] strArr = (String[]) v;
			if (strArr.length > 0) {
				return strArr[0];
			} else {
				return super.getParameter(name);
			}
		} else if (v instanceof String) {
			return (String) v;
		} else {
			return v.toString();
		}
	}
	
	
	public String getQueryString(){
		String queryString = super.getQueryString();
		if(!StringUtil.isEmpty(queryString)){
			queryString = XSSSecurityManager.filter(queryString);
			if(queryString != null) queryString = queryString.replaceAll("&amp;", "&");
		}
		return queryString;
	}
}
