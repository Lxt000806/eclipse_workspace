package com.house.home.serviceImpl.project;

import java.util.Map;

import com.house.framework.commons.orm.BaseServiceImpl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.house.home.dao.project.CompletionReportDao;
import com.house.home.service.project.CompletionReportService;

@SuppressWarnings("serial")
@Service 
public class CompletionReportServiceImpl extends BaseServiceImpl implements CompletionReportService {
	@Autowired
	private CompletionReportDao completionReportDao;
	
	@Override
	public Map<String, Object> getCompletionReportInfo(int custWkPk) {
		return completionReportDao.getCompletionReportInfo(custWkPk);
	}

	@Override
	public Map<String, Object> getNowProgress(int custWkPk) {
		return completionReportDao.getNowProgress(custWkPk);
	}
	
}
