package com.house.home.serviceImpl.basic;

import com.house.framework.commons.orm.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.house.home.dao.basic.DocDownloadLogDao;
import com.house.home.service.basic.DocDownloadLogService;

@SuppressWarnings("serial")
@Service 
public class DocDownloadLogServiceImpl extends BaseServiceImpl implements DocDownloadLogService {
	@Autowired
	private  DocDownloadLogDao docDownloadLogDao;

}
