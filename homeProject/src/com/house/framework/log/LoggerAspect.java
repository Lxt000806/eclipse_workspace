package com.house.framework.log;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;

public class LoggerAspect {

	@Autowired
	private LoggerService loggerservice;

	public Object simpleAdvice(ProceedingJoinPoint joinPoint) throws Throwable {

		Object[] args = joinPoint.getArgs();
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		if (method.getDeclaringClass().isInterface()) {
			method = joinPoint.getTarget().getClass().getDeclaredMethod(
					joinPoint.getSignature().getName(),
					method.getParameterTypes());
		}

		LoggerAnnotation testnotation = method
				.getAnnotation(LoggerAnnotation.class);
		Object returnObj = joinPoint.proceed(args);
		try {
			if (null == testnotation) return returnObj;
			// determine whether use the dblog, annotation
			// 获取调用的代理方法
			if (null != testnotation) {
				Map<String, Object> trailContext = new HashMap<String, Object>();
				// 先执行begin脚本解析
				trailContext.put("args", args);
				String begin = testnotation.begin();
				loggerservice.begin(begin, joinPoint.getTarget(), args,
						trailContext);
				// doing oklog
				LoggerObject lo = getLoggerObject(testnotation);
				lo.setLogcontent(testnotation.okLog());
				lo.setClazz(joinPoint.getTarget().getClass().getName());
				lo.setJavaFun(method.getName());
				lo.setLoginMode(testnotation.loginModel());
				loggerservice.eval(lo, joinPoint.getTarget(), args,
						trailContext);
			}
		} catch (Exception exception) {
			if (null != testnotation) {
				Map<String, Object> trailContext = new HashMap<String, Object>();
				// 先执行begin脚本解析
				trailContext.put("args", args);
				String begin = testnotation.begin();
				loggerservice.begin(begin, joinPoint.getTarget(), args,
						trailContext);
				// doing oklog
				LoggerObject lo = getLoggerObject(testnotation);
				lo.setLogcontent(testnotation.errLog());
				lo.setClazz(joinPoint.getTarget().getClass().getName());
				lo.setJavaFun(method.getName());
				lo.setLoginMode(testnotation.loginModel());
				loggerservice.eval(lo, joinPoint.getTarget(), args,
						trailContext);
			}
			exception.printStackTrace();
			throw exception;
		}
		return returnObj;
	}

	private LoggerObject getLoggerObject(LoggerAnnotation testnotation) {
		LoggerObject lo = new LoggerObject();
		lo.setModelCode(testnotation.modelCode());
		lo.setIp(testnotation.ip());
		lo.setCzybh(testnotation.user());
		lo.setTime(new Date());
		lo.setModelType(testnotation.modelType());
		return lo;
	}
}
