package com.house.home.serviceImpl.basic;

import com.house.framework.commons.orm.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.house.home.dao.basic.DocAttachmentDao;
import com.house.home.service.basic.DocAttachmentService;

@SuppressWarnings("serial")
@Service 
public class DocAttachmentServiceImpl extends BaseServiceImpl implements DocAttachmentService {
	@Autowired
	private  DocAttachmentDao docAttachmentDao;

}
