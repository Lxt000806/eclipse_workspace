package com.house.framework.web.servlet;

import javax.servlet.ServletContext;
import org.springframework.web.context.ServletContextAware;
import com.house.framework.commons.conf.SystemConfig;

/**
 * 
 * @ClassName: ResourcePathExposer 
 * @Description: TODO(设置WEB层文件路径) 
 *
 */
public class ResourcePathExposer implements ServletContextAware {
	private ServletContext servletContext;
	private String resourceRoot;
	private String resourceWebRoot;

	public void init() {
		resourceRoot = "/" + getSkin();
		String publicKey = SystemConfig.getProperty("publicKey","","rsaKey");
		getServletContext().setAttribute("resourceRoot", getServletContext().getContextPath() + resourceRoot);
		getServletContext().setAttribute("ctx", getServletContext().getContextPath());
		getServletContext().setAttribute("publicKey", publicKey);
		resourceWebRoot = "resourceWebRoot";
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	public String getResourceRoot() {
		return resourceRoot;
	}

	public ServletContext getServletContext() {
		return servletContext;
	}
	
	public String getVersion(){
		return SystemConfig.getProperty("version", "1.0", "resource");
	}
	
	public String getSkin(){
		return SystemConfig.getProperty("skin", "commons", "resource");
	}

	public String getResourceWebRoot() {
		return resourceWebRoot;
	}

	public void setResourceWebRoot(String resourceWebRoot) {
		this.resourceWebRoot = resourceWebRoot;
	}
}
