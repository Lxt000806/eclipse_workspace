package com.house.framework.web.interceptor;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang.StringUtils;


/**
 * 默认线程池,增加名称设置
 *@author fb
 *@version 
 *@since 2015年12月18日
 */
public class DefaultThreadFactory implements ThreadFactory{
    private static final AtomicInteger poolNumber = new AtomicInteger(1);
    private final ThreadGroup group;
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final String namePrefix;

    public DefaultThreadFactory(){
    	this("");
    }
    
	public DefaultThreadFactory(String poolName) {
		SecurityManager s = System.getSecurityManager();
		group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();

		if (StringUtils.isNotEmpty(poolName)) {
			namePrefix = poolName + "-" + "pool-" + poolNumber.getAndIncrement() + "-thread-";
		} else {
			namePrefix = "pool-" + poolNumber.getAndIncrement() + "-thread-";
		}
	}

    public Thread newThread(Runnable r) {
        Thread t = new Thread(group, r,
                              namePrefix + threadNumber.getAndIncrement(),
                              0);
        if (t.isDaemon())
            t.setDaemon(false);
        if (t.getPriority() != Thread.NORM_PRIORITY)
            t.setPriority(Thread.NORM_PRIORITY);
        return t;
    }
}
