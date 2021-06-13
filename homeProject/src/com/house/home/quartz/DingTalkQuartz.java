package com.house.home.quartz;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.house.framework.commons.conf.SystemConfig;
import com.house.framework.commons.utils.DingTalkUtils;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.commons.utils.StringUtils;
import com.house.home.service.workflow.WfProcInstService;

/**
 * 钉钉消息推送定时任务
 */
public class DingTalkQuartz {
	private static Logger logger = LoggerFactory.getLogger(DingTalkQuartz.class);
	
	public void execute(){
		try{
			logger.debug("推送钉钉消息定时任务开始");
			
			Long start = System.currentTimeMillis();
			
			String isSendDingTalkMsg = SystemConfig.getProperty("value","","isSendDingTalkMsg");
			if (!"1".equals(isSendDingTalkMsg)) return; //system_config未配置推送钉钉消息
			
			WfProcInstService wfProcInstService = (WfProcInstService) SpringContextHolder
					.getBean("wfProcInstServiceImpl");
			List<Map<String, Object>> list = wfProcInstService.findTodoTaskGroupByUserId();
			if (list == null || list.size() <= 0) return; //没有待审批的记录
			
			Calendar calendar = Calendar.getInstance(); //获取当前的系统时间
			String hourAndMinute = calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);
			for (Map<String, Object> map : list) {
				String phone = (String) map.get("Phone"); 
				Integer cnt = (Integer) map.get("Cnt");
				String content = "你有" + cnt + "条待审批信息，请前往易居通进行审批【" + hourAndMinute + "】";
				String userId = DingTalkUtils.getUserIdByMobile(phone);
				if (StringUtils.isBlank(userId)) {
					logger.error("钉钉消息推送-未找到userId，电话号码：" + phone);
					continue;
				}
				DingTalkUtils.sendMessage(userId, content);
			}
			
			Long end  = System.currentTimeMillis();
			logger.debug("推送钉钉消息定时任务结束，用时："+(end - start)+"ms");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
