package com.house.home.serviceImpl.basic;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.esign.ESignUtils;
import com.house.home.dao.basic.OrgSealDao;
import com.house.home.entity.basic.OrgSeal;
import com.house.home.service.basic.OrgSealService;

@SuppressWarnings("serial")
@Service
public class OrgSealServiceImpl extends BaseServiceImpl implements OrgSealService {

	@Autowired
	private OrgSealDao orgSealDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, OrgSeal orgSeal){
		return orgSealDao.findPageBySql(page, orgSeal);
	}

	@Override
	public void doSave(OrgSeal orgSeal) {
		ESignUtils.generateESignToken(true);
		
		JSONObject json = ESignUtils.createOrgSeal(orgSeal.getOrgId(), orgSeal.getHtext(), 
				orgSeal.getQtext(), orgSeal.getColor(),
				orgSeal.getCentral(),orgSeal.getType());
		
		orgSeal.setSealId(json.getString("sealId"));
		orgSealDao.save(orgSeal);
	}

	@Override
	public void doUpdate(OrgSeal orgSeal) {
		
		String oldSealId = orgSeal.getSealId();
		
		ESignUtils.generateESignToken(true);
		
		//先删除印章
		ESignUtils.deleteSealByOrgId(orgSeal.getOrgId(), orgSeal.getSealId());
		
		//再创建印章
		JSONObject json = ESignUtils.createOrgSeal(orgSeal.getOrgId(), orgSeal.getHtext(), 
				orgSeal.getQtext(), orgSeal.getColor(),
				orgSeal.getCentral(),orgSeal.getType());
		
		//更新已启用的印章Id为新的印章Id
		orgSealDao.updateSealId(oldSealId, json.getString("sealId"));
		
		//更新机构印章表
		orgSeal.setSealId(json.getString("sealId"));
		orgSealDao.update(orgSeal);
	}

	@Override
	public void doDelete(OrgSeal orgSeal) {
		//先删除印章
		ESignUtils.deleteSealByOrgId(orgSeal.getOrgId(), orgSeal.getSealId());
		
		//删除机构印章表数据
		orgSealDao.delete(orgSeal);
	}

}
