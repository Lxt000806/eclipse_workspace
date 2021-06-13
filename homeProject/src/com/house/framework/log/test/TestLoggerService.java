package com.house.framework.log.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.house.framework.log.HouseLogger;
import com.house.framework.log.HouseLoggerFactory;
import com.house.framework.log.LoggerAnnotation;
import com.house.framework.log.LoggerObject;

public class TestLoggerService {
	
	private static final HouseLogger funoLogger = HouseLoggerFactory.getLogger(TestLoggerService.class.toString());
	/**Lo
	 * Using the Annotation to do the operator log
	 * @return
	 */
	@LoggerAnnotation(begin="$user=getUser()", okLog = "$user succ", errLog="$user failed", modelCode="1")
	public int doUpdateOperation() {
		return 0;
	}
	
	/**
	 * Test the Annotation for the exception recording
	 * @return
	 */
	@LoggerAnnotation(begin="$user=getUser()", okLog = "$user succ", errLog="$user failed",  modelCode="1")
	public int doUpdateOperationFailed() {
		int failint = 8/0;
		System.out.println(failint);
		return 0;
	}
	/**
	 * Call the logger directly.
	 * @param args
	 */
	public int doUpdateOperation2(){
		LoggerObject obj = new LoggerObject();
		obj.setLogcontent("succ2");
		funoLogger.dblog(obj);
		return 0;
	}
	
	public static void main(String[] args) {
		ApplicationContext act = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		TestLoggerService service = (TestLoggerService) act.getBean("testService");
		service.doUpdateOperation();
	}
}
