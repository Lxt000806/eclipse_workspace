package com.house.home.serviceImpl.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.Exceptions;
import com.house.framework.commons.utils.StringUtils;
import com.house.framework.web.interceptor.DefaultThreadFactory;
import com.house.home.dao.basic.SysLogDao;
import com.house.home.entity.basic.SysLog;
import com.house.home.service.basic.SysLogService;

@SuppressWarnings("serial")
@Service
public class SysLogServiceImpl extends BaseServiceImpl implements SysLogService,InitializingBean{
	
	private ExecutorService executor;
	private ArrayBlockingQueue<SysLog> queue = new ArrayBlockingQueue<SysLog>(2 << 10);
	private Logger logger = LoggerFactory.getLogger(SysLogServiceImpl.class);

	@Autowired
	private SysLogDao sysLogDao;

	public void saveLog(SysLog log, Object handler, Exception ex) {
		// 如果有异常，设置异常信息
		log.setDescription(ex==null?"":StringUtils.substring(Exceptions.getStackTraceAsString(ex), 0, 2000));
		// 如果无标题并无异常日志，则不保存信息
		if (StringUtils.isBlank(log.getTitle()) && StringUtils.isBlank(log.getDescription())) {
			return;
		}
		// 保存日志信息
		queue.add(log);
	}
	
	public void afterPropertiesSet() throws Exception {
		// CPU 核心数
		int coreCpuNum = Runtime.getRuntime().availableProcessors();
		executor = Executors.newFixedThreadPool(coreCpuNum,new DefaultThreadFactory("sysLog"));
		new Thread(new Runnable(){
			public void run() {
				List<SysLog> logs = new ArrayList<SysLog>();
				while (true) {
					try {
						SysLog log = queue.poll(1L, TimeUnit.SECONDS);

						if (log != null) {
							logs.add(log);
						}

						if (log == null || logs.size() > 20) {
							if (!logs.isEmpty()) {
								executor.submit(new LogTask(logs));
								logs = new ArrayList<SysLog>();
							}
						}
					} catch (Exception e) {
						logger.error("", e);
					}
				}				
			}
		}).start();
	}
	
	public void _saveLog(Iterable<SysLog> logs){
		for(SysLog log:logs){
			this.sysLogDao.save(log);
		}
	}
	
	class LogTask implements Runnable{
		
		private List<SysLog> logs = null;
	
		public LogTask(List<SysLog> logs){
			this.logs = logs;
		}
		
		@Override
		public void run() {
			_saveLog(logs);
		}
		
	}
	
	
	//查询日志列表
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, SysLog sysLog){
		return sysLogDao.findPageBySql(page, sysLog);
	}
	
	//查询日志列表
		public Page<Map<String,Object>> findAppPageBySql(Page<Map<String,Object>> page, SysLog sysLog){
			return sysLogDao.findAppPageBySql(page, sysLog);
		}

}
