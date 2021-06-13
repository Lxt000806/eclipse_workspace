package com.house.framework.web.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisConnection;

import com.house.framework.commons.conf.CommonConstant;
import com.house.framework.commons.converters.BeanUtilsEx;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.RedisUtil;
import com.house.framework.commons.utils.SerializeUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.entity.basic.APIKey;
import com.house.home.service.basic.ShowService;
import com.house.home.service.project.PrjProgConfirmService;

/**
 * App用户登入过滤器
 *
 */
public class AppLoginFilter implements Filter {

	private static final Logger logger = LoggerFactory.getLogger(AppLoginFilter.class);

	public void destroy() {

	}

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse) res;
		String origin = request.getHeader("Origin");
		if(origin != null && origin.length() > 0){
			response.addHeader("Access-Control-Allow-Origin", origin);
			response.addHeader("Access-Control-Allow-Methods", "GET, POST, HEAD, OPTIONS, PUT, DELETE, TRACE, PATCH");
			response.addHeader("Access-Control-Allow-Headers","Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, Authorization, AppVersion,App-TokenId,UserPhone,AppType,UserToken");
			response.addHeader("Access-Control-Allow-Credentials", "true");

			if ("OPTIONS".equals(request.getMethod())) {
				return;
			}
		}

		response.setContentType("application/json;charset=utf-8");
		String uri=request.getRequestURI();
		if (uri.startsWith(request.getContextPath()+"/client")
				&& !uri.startsWith(request.getContextPath()+"/client/prjProg/prjProgUploadApp")
				&& !uri.startsWith(request.getContextPath()+"/client/employee/getEmployeeDetail")
				&& !uri.startsWith(request.getContextPath()+"/client/driver/getDriverDetail")
				&& !uri.startsWith(request.getContextPath()+"/client/worker/getWorkerDetail")
				&& !uri.startsWith(request.getContextPath()+"/client/driver/resetDriverPwd")
				&& !uri.startsWith(request.getContextPath()+"/client/driverItemAppReturn/deleteItemAppReturnPhoto")
				&& !uri.startsWith(request.getContextPath()+"/client/driverItemAppSend/deleteItemAppSendPhoto")
				&& !uri.startsWith(request.getContextPath()+"/client/custAccount/getCustAccountDetail")
				&& !uri.startsWith(request.getContextPath()+"/client/custAccount/registerCustAccount")
				&& !uri.startsWith(request.getContextPath()+"/client/custAccount/resetCustAccountPwd")
				&& !uri.startsWith(request.getContextPath()+"/client/custAccount/wxCustAccount")
				&& !uri.startsWith(request.getContextPath()+"/client/employee/registerEmployee")
				&& !uri.startsWith(request.getContextPath()+"/client/employee/resetEmployeePwd")
				&& !uri.startsWith(request.getContextPath()+"/client/worker/resetWorkerPwd")
				&& !uri.startsWith(request.getContextPath()+"/client/sms/getSmsPassword")
				&& !uri.startsWith(request.getContextPath()+"/client/sms/getWxCustAccountSmsPassword")
				&& !uri.startsWith(request.getContextPath()+"/client/sms/getWorkerSmsPassword")
				&& !uri.startsWith(request.getContextPath()+"/client/sms/getDriverSmsPassword")
				&& !uri.startsWith(request.getContextPath()+"/client/sms/getCustAccountSmsPassword")
				&& !uri.startsWith(request.getContextPath()+"/client/version/checkVersion")
				&& !uri.startsWith(request.getContextPath()+"/client/version/updateVersion")
				&& !uri.startsWith(request.getContextPath()+"/client/version/copyPictures")
				&& !uri.startsWith(request.getContextPath()+"/client/basic/getAppServerUrlList")
				&& !uri.startsWith(request.getContextPath()+"/client/prjProg/getPrjProgPhotoList")
				&& !uri.startsWith(request.getContextPath()+"/client/prjProg/getPrjProgPhoto")
				&& !uri.startsWith(request.getContextPath()+"/client/prjJob/getPrjJobPhotoList")
				&& !uri.startsWith(request.getContextPath()+"/client/prjJob/getPrjJobPhoto")
				&& !uri.startsWith(request.getContextPath()+"/client/prjProgCheck/getPrjProgCheckPhotoList")
				&& !uri.startsWith(request.getContextPath()+"/client/prjProgCheck/getPrjProgCheckPhoto")
				&& !uri.startsWith(request.getContextPath()+"/client/driverItemAppSend/getItemAppSendPhotoList")
				&& !uri.startsWith(request.getContextPath()+"/client/driverItemAppReturn/getItemReturnArrivedPhotoList")
				&& !uri.startsWith(request.getContextPath()+"/client/prjProgConfirm/prjProgPhotoDelete")
				&& !uri.startsWith(request.getContextPath()+"/client/itemPreApp/getItemPreAppPhotoList")
				&& !uri.startsWith(request.getContextPath()+"/client/signIn/updatePosiInfo")
				&& !uri.startsWith(request.getContextPath()+"/client/signIn/getPosiInfo")
				&& !uri.startsWith(request.getContextPath()+"/client/basic/refreshCache")
				&& !uri.startsWith(request.getContextPath()+"/client/signIn/getPosiDistance")
				&& !uri.startsWith(request.getContextPath()+"/client/basic/getTokenId")
				&& !uri.startsWith(request.getContextPath()+"/client/weChat/wxCustFastAccount")
				&& !uri.startsWith(request.getContextPath()+"/client/show/")
				&& !uri.startsWith(request.getContextPath()+"/client/api/")
				&& !uri.startsWith(request.getContextPath()+"/client/verify/")
				&& !uri.startsWith(request.getContextPath()+"/client/farbell/callback")
				&& !uri.startsWith(request.getContextPath()+"/client/WxApplet/getAccessTokenFromRedise")
				&& !uri.startsWith(request.getContextPath()+"/client/mallBase/")
				&& !uri.startsWith(request.getContextPath()+"/client/mallApplets/")
				&& !uri.startsWith(request.getContextPath()+"/client/mallBackstage/")
				&& !uri.startsWith(request.getContextPath()+"/client/employee/getEmployeeDetailByPhone")){
			HttpSession session = request.getSession();
			System.out.println(session.getId());
			UserContext uc=null;
			/**
			 * 使用Token在头部设置IsToken为1
			 * 2019/07/04 新版易居通app采用redis判断
			 */
			if("1".equals(request.getHeader("AppType"))||"1".equals(request.getHeader("IsToken")) || "3".equals(request.getHeader("AppType"))
					|| "2".equals(request.getHeader("AppType")) || "5".equals(request.getHeader("AppType")) || "6".equals(request.getHeader("AppType"))){
				int redisDbIndex = "5".equals(request.getHeader("AppType")) ? 8 : 6;
				redisDbIndex = "6".equals(request.getHeader("AppType")) ? 9 : redisDbIndex;
				RedisConnection redisConnection = RedisUtil.getRedisConnection();
				redisConnection.select(redisDbIndex);
				System.out.println(request.getHeader("UserToken"));
				byte[] userContextBtyes = RedisUtil.get(redisConnection, request.getHeader("UserToken"));
				if(userContextBtyes != null && userContextBtyes.length > 0){
					uc = (UserContext) SerializeUtil.unSerialize(userContextBtyes);
				}
			}else{
				/**
				else if(uri.startsWith(request.getContextPath()+"/client/worker/") || uri.startsWith(request.getContextPath()+"//client/")){
	
					uc =(UserContext) session.getAttribute(CommonConstant.WORKER_APP_KEY);
				}
				 * 由于最初工人APP的相关接口除了ClientWorkerController和ClientWorkerAppController的所有接口有采用/client/worker前缀,
				 * 解决这一问题废除WORKER_APP_KEY,把工人APP的Session标识改为与项目经理相同
				 * remove by zzr 2018/05/07
				 * 
				 */
				if (uri.startsWith(request.getContextPath()+"/client/driver")){
					uc =(UserContext) session.getAttribute(CommonConstant.DRIVER_APP_KEY);
				}else if(uri.startsWith(request.getContextPath()+"/client/custAccount")){
					uc =(UserContext) session.getAttribute(CommonConstant.CLIENT_APP_KEY);
				}else{
					uc =(UserContext) session.getAttribute(CommonConstant.USER_APP_KEY);
					if(uc==null){
						//多的权限没有判断少的是否有
						uc =(UserContext) session.getAttribute(CommonConstant.CLIENT_APP_KEY);
					}
				}

			}
			if(null == uc){
				logger.debug("过滤器发现用户未登入或用户Session超时");
				//HttpServletResponse response = (HttpServletResponse)res;
				String json = "{\"success\":false,\"returnInfo\":\"用户未登录\"}";
				String callback = request.getParameter("callback");
				if (StringUtils.isNotBlank(callback)){
					json = callback + "(" + json + ")";
					response.setHeader("Content-type", "application/x-javascript;charset=utf-8");
				}else{
					response.setContentType("text/html;charset=utf-8");
				}
				PrintWriter out = response.getWriter();
				out.print(json);
				out.flush();
				out.close();
				return ;
			}
			try {
				UserContextHolder.set(uc);
			} catch (RuntimeException e) {
				logger.error("绑定Session中用户信息到ThreadLocal 出现异常: "+e.getMessage(), e);
			}
		}
		boolean checkApiKey = true;
		if(uri.startsWith(request.getContextPath()+"/client/api/mt")){
			checkApiKey = false;
		}
		if(uri.startsWith(request.getContextPath()+"/client/api/") && checkApiKey == true){
			String returnMsg = "";
			try{
				String apiKeyStr = request.getParameter("apiKey");
				if(StringUtils.isBlank(apiKeyStr)){
					returnMsg = "密钥不能为空";
				}else{
					ShowService showService=(ShowService) SpringContextHolder.getBean("showServiceImpl");
					Map<String, Object> apiKeyMap = showService.getAPIKey(apiKeyStr);
					APIKey apiKey = new APIKey();
					BeanConvertUtil.mapToBean(apiKeyMap, apiKey);
					if(apiKey == null || StringUtils.isBlank(apiKey.getApiKey())){
						returnMsg = "密钥不存在或已失效";
					}
				}
			}catch (Exception e) {
				// TODO: handle exception
				logger.error("判断APIKey出现异常: "+e.getMessage(), e);
				returnMsg = "服务执行异常";
			}finally{
				if(!"".equals(returnMsg)){
					logger.debug("过滤器发现API请求存在问题");
					String json = "{\"success\":false,\"returnInfo\":\""+returnMsg+"\"}";
					String callback = request.getParameter("callback");
					if (StringUtils.isNotBlank(callback)){
						json = callback + "(" + json + ")";
						response.setHeader("Content-type", "application/x-javascript;charset=utf-8");
					}else{
						response.setContentType("text/html;charset=utf-8");
					}
					PrintWriter out = response.getWriter();
					out.print(json);
					out.flush();
					out.close();
					return ;
				}
			}
		}
		chain.doFilter(req, res);
	}

	public void init(FilterConfig arg0) throws ServletException {

	}

}
