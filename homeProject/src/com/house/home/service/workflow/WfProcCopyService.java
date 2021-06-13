package com.house.home.service.workflow;

import java.util.List;

import com.house.framework.commons.orm.BaseService;
import com.house.home.entity.workflow.WfProcCopy;

public interface WfProcCopyService extends BaseService {
	public List<WfProcCopy> findListByWfProcNo(String wfProcNo, String taskKey);
	
	public void doSaveCopyByGroup(String wfProcInstNo,String wfProcNo,String taskKey,String lastUpdatedBy);
}
