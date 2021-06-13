package com.house.framework.web.filter;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import com.house.framework.commons.conf.CommonConstant;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.commons.utils.StringUtil;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.SysLog;
import com.house.home.service.basic.SysLogService;

/**
 * App用户日志过滤器
 *
 */
public class AppLogFilter implements Filter {

	public void destroy() {

	}

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
		String uri=request.getRequestURI();
		if (uri.startsWith(request.getContextPath()+"/client")
				&& !uri.startsWith(request.getContextPath()+"/client/prjProg/prjProgUpload")
				&& !uri.startsWith(request.getContextPath()+"/client/prjProg/prjProgUploadNew")
				&& !uri.startsWith(request.getContextPath()+"/client/prjProg/prjProgUploadApp")
//				&& !uri.startsWith(request.getContextPath()+"/client/itemPreApp/addItemPreApp")
//				&& !uri.startsWith(request.getContextPath()+"/client/itemPreApp/updateItemPreApp")
				&& !uri.startsWith(request.getContextPath()+"/client/itemPreApp/uploadItemPreAppPhoto")){
			HttpSession session = request.getSession();
			UserContext uc =(UserContext) session.getAttribute(CommonConstant.USER_APP_KEY);
			
			if(null == uc || StringUtils.isBlank(uc.getCzybh())){
				if (uri.startsWith(request.getContextPath()+"/client/employee/getEmployeeDetail")
						|| uri.startsWith(request.getContextPath()+"/client/version/checkVersion")
						|| uri.startsWith(request.getContextPath()+"/client/basic/getAppServerUrlList")
						|| uri.startsWith(request.getContextPath()+"/client/prjProg/getPrjProgPhotoList")){
					//写日志
					saveLog(request,"");
				}
			}else{
				//写日志
				saveLog(request,uc.getCzybh());
			}
		}
		chain.doFilter(req, res);
	}

	public void init(FilterConfig arg0) throws ServletException {

	}
	
	private void saveLog(HttpServletRequest request,String czybh){
		try{
			SysLogService sysLogService = (SysLogService)SpringContextHolder.getBean("sysLogServiceImpl");
			String uri = request.getRequestURI();
			if (uri.lastIndexOf("/")!=uri.lastIndexOf("/getItemAppTempAreaDetail")){
				return;
			}
			String method = request.getMethod();
			JSONObject json = new JSONObject();
			String queryString = "";
			if ("post".equalsIgnoreCase(method)){
				//json = this.getJson(request);
			}else{
				queryString = StringUtil.formatQueryString(request);
				json = StringUtil.queryStringToJSONObject(request);
			}
			String jsonString = json.toString();
			SysLog sysLog = new SysLog();
			sysLog.setTitle("");
			sysLog.setType("1");//1:业务日志;2:异常日志
			sysLog.setAppType("2");//1：b/s，2：项目经理app 3：客户App
			sysLog.setRequestUrl(uri);
			sysLog.setUserAgent(request.getHeader("user-agent"));
//			sysLog.setMethod(uri.substring(uri.lastIndexOf("/")+1));
			sysLog.setMethod(method);
			if (StringUtils.isNotBlank(queryString)){
				sysLog.setParams(queryString.substring(0, queryString.length()>4000?4000:queryString.length()));
			}else{
				sysLog.setParams(queryString);
			}
			if (StringUtils.isNotBlank(jsonString)){
				sysLog.setDescription(jsonString.substring(0, jsonString.length()>4000?4000:jsonString.length()));
			}else{
				sysLog.setDescription(jsonString);
			}
			sysLog.setRemoteAddr(request.getRemoteAddr());
			sysLog.setOperId(czybh);
			sysLog.setOperDate(new Date());
			sysLog.setClazz("");
			sysLog.setResponseContent("");
			sysLogService.save(sysLog);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 取客服端json数据流
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	private JSONObject getJson(HttpServletRequest request) {
		BufferedReader br = null;
		JSONObject json = new JSONObject();
		try {
			ServletInputStream ris = request.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(ris);
			InputStreamReader r = new InputStreamReader(bis, "UTF-8");
			br = new BufferedReader(r);
			StringBuilder msg = new StringBuilder();
			bis.mark(0);
			String x = null;
			while (null != (x = br.readLine())) {
				msg.append(x);
			}
			if (msg.length() > 0) {
				json = JSONObject.fromObject(msg.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if (br!=null){
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return json;
	}
}
