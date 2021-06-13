package com.house.framework.log;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

public class HouseLogger extends Logger {
	
	protected HouseLogger(String name) {
		super(name);
	}

	protected void forcedLog( Priority level, Object message,
			Throwable t) {
		if (message instanceof LoggerObject) {
			LoggerObject msgObj = (LoggerObject) message;
			LogEvent event = new LogEvent(msgObj.clazz, this, level,
					message, t);
			event.setMsgObj(msgObj);
			OperAppender operAppender = new OperAppender();
			operAppender.append(event);
		}
	}

	public void dblog(Object message) {
		if (repository.isDisabled(LoggerLevel.DBLOG_INT)) {
			return;
		}
		if (LoggerLevel.DBLOG_LEVEL.isGreaterOrEqual(this.getEffectiveLevel())) {
			forcedLog( LoggerLevel.DBLOG_LEVEL, message, null);
		}
	}
}
