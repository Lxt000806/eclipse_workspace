package com.house.home.serviceImpl.design;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.oss.OssConfigure;
import com.house.framework.web.login.UserContext;
import com.house.home.dao.basic.CzybmDao;
import com.house.home.dao.design.CustConDao;
import com.house.home.entity.design.CustCon;
import com.house.home.service.design.CustConService;

@SuppressWarnings("serial")
@Service
public class CustConServiceImpl extends BaseServiceImpl implements CustConService {

	@Autowired
	private CustConDao custConDao;
	@Autowired
	private CzybmDao czybmDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CustCon custCon,UserContext uc){
		
		custCon.setHasAllConAuth(czybmDao.hasGNQXByCzybh(uc.getCzybh(), "0826", "查看所有接触记录"));
		
		Page<Map<String, Object>> returnPage = custConDao.findPageBySql(page, custCon,uc);
		
		if (StringUtils.isNotBlank(custCon.getIsGetCallRecord()) && "1".equals(custCon.getIsGetCallRecord())) {
			return returnPage;
		}

		for (int i = 0; page.getResult() != null && i < page.getResult().size(); i++) {
			page.getResult().get(i).put("callRecord", "");
			page.getResult().get(i).put("mobileFilePath", "");
			page.getResult().get(i).put("callrecord", "");
			page.getResult().get(i).put("mobilefilepath", "");
		}
		return returnPage;
	}
	
	@Override
	public Page<Map<String, Object>> findPageBySql_bs(
			Page<Map<String, Object>> page, CustCon custCon, UserContext uc) {
		Page<Map<String, Object>> returnPage = custConDao.findPageBySql_bs(page, custCon,uc);
		
		if (StringUtils.isNotBlank(custCon.getIsGetCallRecord()) && "1".equals(custCon.getIsGetCallRecord())) {
			return returnPage;
		}

		for (int i = 0; page.getResult() != null && i < page.getResult().size(); i++) {
			page.getResult().get(i).put("callRecord", "");
			page.getResult().get(i).put("mobileFilePath", "");
			page.getResult().get(i).put("callrecord", "");
			page.getResult().get(i).put("mobilefilepath", "");
		}
		return returnPage;
	}

	@Override
	public Page<Map<String, Object>> findPageBySql_khxx(
			Page<Map<String, Object>> page, CustCon custCon) {
		return custConDao.findPageBySql_khxx(page,custCon);
	}


	

}
