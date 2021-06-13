package com.house.home.service.project;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;

public interface CompletionReportService extends BaseService{
	
	public Map<String, Object> getCompletionReportInfo(int custWkPk);
	
	public Map<String, Object> getNowProgress(int custWkPk);
}
