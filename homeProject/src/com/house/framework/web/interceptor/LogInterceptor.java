package com.house.framework.web.interceptor;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.house.framework.commons.utils.DateUtil;
/**
 * 
* @ClassName: LogInterceptor 
* @Description: 日志拦截器
* @author liuxiaobin
* @date 2015年8月25日 下午4:27:48 
*
 */
public class LogInterceptor implements HandlerInterceptor {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());

	private static final ThreadLocal<Long> startTimeThreadLocal =
			new NamedThreadLocal<Long>("ThreadLocal StartTime");
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, 
			Object handler) throws Exception {
		//if (logger.isDebugEnabled()){ // 20190226 mark by xzp 
		long beginTime = System.currentTimeMillis();//1、开始时间  
        startTimeThreadLocal.set(beginTime);		//线程绑定变量（该数据只有当前请求的线程可见）  
        logger.info("开始计时: {}  URI: {}", new SimpleDateFormat("hh:mm:ss.SSS") //20190226 modi by xzp logger.debug -> logger.info
        	.format(beginTime), request.getRequestURI());
		//}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, 
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, 
			Object handler, Exception ex) throws Exception {

		// 保存日志
		LogUtils.saveLog(request, handler, ex, "页面访问日志");
		
		// 打印JVM信息。
		//if (logger.isDebugEnabled()){ // 20190226 mark by xzp 
		long beginTime = startTimeThreadLocal.get();//得到线程绑定的局部变量（开始时间）  
		long endTime = System.currentTimeMillis(); 	//2、结束时间  
		logger.info("计时结束："+new SimpleDateFormat("hh:mm:ss.SSS").format(endTime)+"  耗时：" //20190226 modi by xzp logger.debug -> logger.info
				+DateUtil.formatDateTime(endTime - beginTime)+"  URI: "+request.getRequestURI()
				+"  最大内存: "+Runtime.getRuntime().maxMemory()/1024/1024
				+"m  已分配内存: "+Runtime.getRuntime().totalMemory()/1024/1024
				+"m  已分配内存中的剩余空间: "+Runtime.getRuntime().freeMemory()/1024/1024
				+"m  最大可用内存: "+(Runtime.getRuntime().maxMemory()-Runtime.getRuntime().totalMemory()+Runtime.getRuntime().freeMemory())/1024/1024+"m");	
	
		//}
	}

}
