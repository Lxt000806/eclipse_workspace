package com.house.home.serviceImpl.basic;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.PrjRoleDao;
import com.house.home.entity.basic.PrjRole;
import com.house.home.entity.basic.PrjRolePrjItem;
import com.house.home.entity.basic.PrjRoleWorkType12;
import com.house.home.service.basic.PrjRoleService;

@SuppressWarnings("serial")
@Service
public class PrjRoleServiceImpl extends BaseServiceImpl implements PrjRoleService {

	@Autowired
	private PrjRoleDao prjRoleDao;
	// add by hc  2017/11/22  begin 
	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, PrjRole prjRole) {
		return prjRoleDao.findPageBySql(page, prjRole);
	}
	
	@Override
	public Page<Map<String, Object>> findPageByksxzSql(
			Page<Map<String, Object>> page, PrjRole prjRole) {
		// TODO Auto-generated method stub
		return prjRoleDao.findPageBykuxzSql(page, prjRole);
	}
	
	@Override
	public Page<Map<String, Object>> findPageByksworkSql(
			Page<Map<String, Object>> page, PrjRole prjRole) {
		// TODO Auto-generated method stub
		return prjRoleDao.findPageBykuworkSql(page, prjRole);
	}
	
	@Override
	public PrjRole getByCode(String code) {
		
		return prjRoleDao.getByCode(code);
	}
	
	@Override
	public PrjRole getByDescr(String descr) {
		return prjRoleDao.getByDescr(descr);
	}
	
	@Override
	public PrjRole getByDescr1(String descr, String descr1) {
		return prjRoleDao.getByDescr1(descr,descr1);
	}
	
	@Override
	public Result doPrjRoleSave(PrjRole prjRole) {
		
		return prjRoleDao.doPrjRoleCheckOut(prjRole);
	}

	@Override
	public Page<Map<String, Object>> findPageBySqlPrjItem(
			Page<Map<String, Object>> page, PrjRolePrjItem prjRolePrjItem) {
		return prjRoleDao.findPageBySqlItem(page, prjRolePrjItem);
	}

	@Override
	public Page<Map<String, Object>> findPageBySqlPrjWork(
			Page<Map<String, Object>> page, PrjRoleWorkType12 prjRoleWorkType12) {
		return prjRoleDao.findPageBySqlWork(page, prjRoleWorkType12);
	}

	@Override
	public Result deleteForProc(PrjRole prjRole) {
		
		return prjRoleDao.doPrjRoleCheckOut(prjRole);
	}

	
	// add by hc  2017/11/22  end 

	

}
 
