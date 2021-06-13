package com.house.framework.log;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;

public class HouseLoggerFactory implements LoggerFactory{
	
	private static HouseLoggerFactory instance= new HouseLoggerFactory();
	
	public Logger makeNewLoggerInstance(String name) {
		return (Logger)new HouseLogger(name);
	}
	
	public static HouseLogger getLogger(String name){
		return (HouseLogger)HouseLogger.getLogger(name, HouseLoggerFactory.instance);
	}
}
	
