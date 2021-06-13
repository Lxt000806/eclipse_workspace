package com.house.home.serviceImpl.commi;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.commi.CommiCustStakeholderDao;
import com.house.home.entity.commi.CommiCustStakeholder;
import com.house.home.service.commi.CommiCustStakeholderService;

@SuppressWarnings("serial")
@Service
public class CommiCustStakeholderServiceImpl extends BaseServiceImpl implements CommiCustStakeholderService {

	@Autowired
	private CommiCustStakeholderDao commiCustStakeholderDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CommiCustStakeholder commiCustStakeholder){
		return commiCustStakeholderDao.findPageBySql(page, commiCustStakeholder);
	}

	@Override
	public void adjustAmount(CommiCustStakeholder commiCustStakeholder) {
		//如果没有可以更新的记录，则插入一条记录
		if(commiCustStakeholderDao.modifyAdjustAmount(commiCustStakeholder) == 0){
			commiCustStakeholderDao.insertAdjustAmount(commiCustStakeholder);
		}
		commiCustStakeholderDao.updateAdjustAmount(commiCustStakeholder);
	}

	@Override
	public Page<Map<String, Object>> goDesignFeeJqGrid(Page<Map<String, Object>> page,CommiCustStakeholder commiCustStakeholder) {
		return commiCustStakeholderDao.goDesignFeeJqGrid(page, commiCustStakeholder);
	}

	@Override
	public Page<Map<String, Object>> goHisJqGrid(Page<Map<String, Object>> page,CommiCustStakeholder commiCustStakeholder) {
		return commiCustStakeholderDao.goHisJqGrid(page, commiCustStakeholder);
	}

	@Override
	public Page<Map<String, Object>> goBasePersonalJqGrid(Page<Map<String, Object>> page,CommiCustStakeholder commiCustStakeholder) {
		return commiCustStakeholderDao.goBasePersonalJqGrid(page, commiCustStakeholder);
	}

	@Override
	public Page<Map<String, Object>> goStakeholderJqGrid(Page<Map<String, Object>> page,CommiCustStakeholder commiCustStakeholder) {
		return commiCustStakeholderDao.goStakeholderJqGrid(page, commiCustStakeholder);
	}

}
