package com.house.home.serviceImpl.basic;

import java.util.ArrayList;
import java.util.List;
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
import com.house.framework.web.interceptor.DefaultThreadFactory;
import com.house.home.dao.basic.LogoDao;
import com.house.home.entity.basic.Logo;
import com.house.home.service.basic.LogoService;

@SuppressWarnings("serial")
@Service
public class LogoServiceImpl extends BaseServiceImpl implements LogoService, InitializingBean{
	
	private ExecutorService executor;
	private ArrayBlockingQueue<Logo> queue = new ArrayBlockingQueue<Logo>(2 << 10);
	private Logger logger = LoggerFactory.getLogger(LogoServiceImpl.class);

	@Autowired
	private LogoDao logoDao;
	
	public void saveLog(Logo logo, Object handler, Exception ex) {
		// 保存日志信息
		queue.add(logo);
	}
	
	public void afterPropertiesSet() throws Exception {
		// CPU 核心数
		int coreCpuNum = Runtime.getRuntime().availableProcessors();
		executor = Executors.newFixedThreadPool(coreCpuNum,new DefaultThreadFactory("logo"));
		new Thread(new Runnable(){
			public void run() {
				List<Logo> logos = new ArrayList<Logo>();
				while (true) {
					try {
						Logo logo = queue.poll(1L, TimeUnit.SECONDS);

						if (logo != null) {
							logos.add(logo);
						}

						if (logo == null || logos.size() > 20) {
							if (!logos.isEmpty()) {
								executor.submit(new LogoTask(logos));
								logos = new ArrayList<Logo>();
							}
						}
					} catch (Exception e) {
						logger.error("", e);
					}
				}				
			}
		}).start();
	}
	
	public void _saveLog(Iterable<Logo> logos){
		for(Logo log: logos){
			this.logoDao.save(log);
		}
	}
	
	class LogoTask implements Runnable{
		
		private List<Logo> logos = null;
	
		public LogoTask(List<Logo> logos){
			this.logos = logos;
		}
		
		@Override
		public void run() {
			_saveLog(logos);
		}
	}

}
