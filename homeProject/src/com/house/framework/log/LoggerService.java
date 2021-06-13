package com.house.framework.log;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.house.framework.log.script.ScriptUtil;
import com.house.framework.log.text.TextRender;
import com.house.framework.log.text.VelocityTextRender;
import com.house.framework.log.util.StringUtil;
import com.house.framework.log.util.TrailConstants;
import com.house.framework.web.login.UserContext;
import com.house.framework.web.login.UserContextHolder;

@Service
public class LoggerService {

	private  TextRender textRender = new VelocityTextRender();

	public  void begin(String beginScript, Object target,
			Object[] arguments, Map<String, Object> context) {
		try {
			context.put(TrailConstants.ARG_STRING, arguments);
			ScriptUtil.execute(beginScript, target, arguments, context);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	public  void eval(LoggerObject obj, Object target,
			Object[] arguments, Map<String, Object> context) {
		try {
			context.put(TrailConstants.ARG_STRING, arguments);
			String evaledText = StringUtil.EMPTY;
			String toEvalText = obj.getLogcontent();
			if (!StringUtil.isEmpty(toEvalText)) {
				evaledText = textRender.render(toEvalText, context);
			}
			obj.setLogcontent(evaledText);
			//if default is null, using the default method
			if (StringUtil.isEmpty(obj.getIp())){
				obj.setIp(this.getRemoteIp());
			}
			// calling logger factory
			HouseLoggerFactory factory = new HouseLoggerFactory();
			Logger logger = HouseLogger.getLogger(obj.getClazz(), factory);
			HouseLogger flogger =new HouseLogger(logger.getName());
			if (LoggerLevel.DBLOG_LEVEL.isGreaterOrEqual(logger.getEffectiveLevel())) {
				flogger.forcedLog( LoggerLevel.DBLOG_LEVEL, obj, null);
			}
			
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	protected String getRemoteIp() {
		UserContext uc = UserContextHolder.getUserContext();
		if(uc == null){
			ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
			if (null != attr){
				HttpServletRequest request = attr.getRequest();
				return request.getRemoteAddr();
			}else{
				return "127.0.0.0";
			}
		}
		return uc.getIp();
	}
	
	@SuppressWarnings("unused")
	protected long getWapUser() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if (null != attr){
			HttpServletRequest request = attr.getRequest();
			HttpSession session = request.getSession();
			return 0L;
		 }else{
			return 0L;
		} 
	}
}
