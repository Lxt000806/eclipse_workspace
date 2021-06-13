package com.house.home.serviceImpl.workflow;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.home.dao.workflow.WfProcCopyDao;
import com.house.home.entity.workflow.WfProcCopy;
import com.house.home.service.workflow.WfProcCopyService;

@SuppressWarnings("serial")
@Service
public class WfProcCopyServiceImpl extends BaseServiceImpl implements WfProcCopyService {

	@Autowired
	private WfProcCopyDao wfProcCopyDao;
	
	@Override
	public List<WfProcCopy> findListByWfProcNo(String wfProcNo, String taskKey) {
		return wfProcCopyDao.findListByWfProcNo(wfProcNo, taskKey);
	}

	@Override
	public void doSaveCopyByGroup(String wfProcInstNo, String wfProcNo,
			String taskKey, String lastUpdatedBy) {
		wfProcCopyDao.doSaveCopyByGroup(wfProcInstNo, wfProcNo, taskKey, lastUpdatedBy);
	}
	
	

}
