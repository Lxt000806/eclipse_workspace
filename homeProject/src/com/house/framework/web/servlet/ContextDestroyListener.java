package com.house.framework.web.servlet;

import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContextDestroyListener implements ServletContextListener {

	private static Logger logger = LoggerFactory.getLogger(ContextDestroyListener.class);

	public static final List<String> MANUAL_DESTROY_THREAD_IDENTIFIERS = Arrays
			.asList("QuartzScheduler", "scheduler_Worker", "sendMessageQuartz", "localQuartzScheduler");

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// Ignore
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		try {
			destroyJDBCDrivers();
		    immolate();
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
			logger.error("Destroy error", e);
		}
	}

	@SuppressWarnings("unused")
	private void destroySpecifyThreads() {
		final Set<Thread> threads = Thread.getAllStackTraces().keySet();
		for (Thread thread : threads) {
			if (needManualDestroy(thread)) {
				synchronized (this) {
					try {
						if (thread.isInterrupted()) {
							System.out.println("停止了!");
							logger.debug(String.format("Destroy  %s successful", thread));
							return;
						}
					} catch (Exception e) {
						logger.error(String.format("Destroy %s error", thread), e);
					}
				}
			}
		}
	}

	private boolean needManualDestroy(Thread thread) {
		final String threadName = thread.getName();
		for (String manualDestroyThreadIdentifier : MANUAL_DESTROY_THREAD_IDENTIFIERS) {
			if (threadName.contains(manualDestroyThreadIdentifier)) {
				return true;
			}
		}
		return false;
	}

	private void destroyJDBCDrivers() {
		final Enumeration<Driver> drivers = DriverManager.getDrivers();
		Driver driver;
		while (drivers.hasMoreElements()) {
			driver = drivers.nextElement();
			try {
				DriverManager.deregisterDriver(driver);
				logger.debug(String.format("Deregister JDBC driver %s successful", driver));
			} catch (SQLException e) {
				logger.error(String.format("Deregister JDBC driver %s error",driver), e);
			}
		}
	}
	
	private Integer immolate() {
        int count = 0;
        try {
            final Field threadLocalsField = Thread.class.getDeclaredField("threadLocals");
            threadLocalsField.setAccessible(true);
            final Field inheritableThreadLocalsField = Thread.class.getDeclaredField("inheritableThreadLocals");
            inheritableThreadLocalsField.setAccessible(true);
            for (final Thread thread : Thread.getAllStackTraces().keySet()) {
                    count += clear(threadLocalsField.get(thread));
                    count += clear(inheritableThreadLocalsField.get(thread));
                    if (thread != null) {  
                        thread.setContextClassLoader(null);  
                    } 
            }
            logger.info("immolated " + count + " values in ThreadLocals");
        } catch (Exception e) {
            throw new Error("ThreadLocalImmolater.immolate()", e);
        }
        return count;
    }

    @SuppressWarnings("rawtypes")
	private int clear(final Object threadLocalMap) throws Exception {
        if (threadLocalMap == null)
                return 0;
        int count = 0;
        final Field tableField = threadLocalMap.getClass().getDeclaredField("table");
        tableField.setAccessible(true);
        final Object table = tableField.get(threadLocalMap);
        for (int i = 0, length = Array.getLength(table); i < length; ++i) {
            final Object entry = Array.get(table, i);
            if (entry != null) {
                final Object threadLocal = ((WeakReference)entry).get();
                if (threadLocal != null) {
                    Array.set(table, i, null);
                    ++count;
                }
            }
        }
        return count;
    }
}
