package com.house.home.serviceImpl.basic;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.dao.RoleUserDao;
import com.house.framework.entity.RoleUser;
import com.house.home.dao.basic.CzybmAuthorityDao;
import com.house.home.dao.basic.CzybmDao;
import com.house.home.entity.basic.Czybm;
import com.house.home.service.basic.CzybmService;

@SuppressWarnings("serial")
@Service
public class CzybmServiceImpl extends BaseServiceImpl implements CzybmService {

	@Autowired
	private CzybmDao czybmDao;
	@Autowired
	private RoleUserDao roleUserDao;
	@Autowired
	private CzybmAuthorityDao czybmAuthorityDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Czybm czybm){
		return czybmDao.findPageBySql(page, czybm);
	}
	
	public Result updateForProc(Czybm czybm, String xml, String xmlCk) {
		Result result = czybmDao.updateForProc(czybm, xml, xmlCk);
		//设置用户角色
//		if (result.isSuccess()){
//			assignRole(czybm);
//		}
		return result;
	}

	public Czybm getByCzybhAndMm(String czybh, String mm) {
		return czybmDao.getByCzybhAndMm(czybh, mm);
	}

	public List<String> getAllSuperAdminID() {
		return czybmDao.getAllSuperAdminID();
	}

	public List<Czybm> getSuperAdminList() {
		return czybmDao.getSuperAdminList();
	}

	public List<Czybm> getByRoleId(Long roleId) {
		return czybmDao.getByRoleId(roleId);
	}

	public List<Czybm> getByRoleCode(String roleCode) {
		return czybmDao.getByRoleCode(roleCode);
	}

	public boolean isSuperAdmin(String czybh) {
		return czybmDao.isSuperAdmin(czybh);
	}

	public Czybm getByEmnum(String number) {
		return czybmDao.getByEmnum(number);
	}

	@Override
	public void assignRole(Czybm czybm) {
		if (czybm!=null){
			if (StringUtils.isNotBlank(czybm.getUserRole())){
				RoleUser roleUser = null;
				List<Long> roleIdList = IdUtil.splitIds(czybm.getUserRole());
				roleUserDao.deleteByCzybh(czybm.getCzybh());
				for(Long roleId : roleIdList){
					if(roleId != null){
						roleUser = new RoleUser();
						roleUser.setGenTime(new Date());
						roleUser.setRoleId(roleId);
						roleUser.setCzybh(czybm.getCzybh());
						this.roleUserDao.save(roleUser);
						//czybmDao.doCZYQXByRole(roleId, czybm.getCzybh());
						//czybmDao.doCZYGNQXByRole(roleId, czybm.getCzybh());
					}
				}
			}else{
				roleUserDao.deleteByCzybh(czybm.getCzybh());
			}
		}
	}

	@Override
	public List<Map<String, Object>> getPermission(String id) {
		return czybmDao.getPermission(id);
	}

	@Override
	public Result setCzybmAuths(String czybh, List<Long> addList,
			List<Long> delList) {
		Assert.notNull(czybh,"操作员编号不能为空");
		return this.czybmAuthorityDao.doSetCzybmAuths(czybh, delList, addList);
		/*if(delList != null && delList.size()>0){
			this.czybmAuthorityDao.delByCzybhAndAuthIds(czybh, delList);
		}
		if(addList != null && addList.size()>0){
			this.czybmAuthorityDao.addByCzybhAndAuthIds(czybh, addList);
		}*/
	}

	@Override
	public List<Long> getAuthIdsByCzybh(String czybh) {
		return czybmDao.getAuthIdsByCzybh(czybh);
	}

	@Override
	public boolean hasAuthByCzybh(String czybh, int authId) {
		return czybmDao.hasAuthByCzybh(czybh, authId);
	}

	@Override
	public String getHasAgainMan() {
		// TODO Auto-generated method stub
		return czybmDao.getHasAgainMan();
	}

	@Override
	public boolean hasGNQXByCzybh(String czybh, String mkdm, String gnmc) {
		// TODO Auto-generated method stub
		return czybmDao.hasGNQXByCzybh(czybh, mkdm, gnmc);
	}
	
	@Override
	public List<Map<String, Object>> findGNQXByCzybhAndMkdm(String czybh, String mkdm) {
		return czybmDao.findGNQXByCzybhAndMkdm(czybh, mkdm);
	}

	@Override
	public List<Map<String, Object>> getCZYList(String czybh) {
		// TODO Auto-generated method stub
		return czybmDao.getCZYList(czybh);
	}

	@Override
	public Result doCopyRight(Czybm czybm) {
		// TODO Auto-generated method stub
		return czybmDao.doCopyRight(czybm);
	}

	@Override
	public List<Map<String, Object>> getQX_ALLMK(int ptbh) {
		// TODO Auto-generated method stub
		return czybmDao.getQX_ALLMK(ptbh);
	}

	@Override
	public List<Map<String, Object>> getQX_CZYQX(String czybh, int ptbh, String containRoleAuth) {
		
		return czybmDao.getQX_CZYQX(czybh, ptbh, containRoleAuth);
	}

	@Override
	public Result doAppRight(Czybm czybm) {
		// TODO Auto-generated method stub
		return czybmDao.doAppRight(czybm);
	}

	@Override
	public boolean isHasEMNum(Czybm czybm) {
		// TODO Auto-generated method stub
		return czybmDao.isHasEMNum(czybm);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql_mtRegion(
			Page<Map<String, Object>> page, Czybm czybm) {
		// TODO Auto-generated method stub
		return czybmDao.findPageBySql_mtRegion(page, czybm);
	}

	@Override
	public boolean isPasswordExpired(String czybh) {
		return czybmDao.isPasswordExpired(czybh);
	}

    @Override
    public Czybm findByCzybhOrPhone(String loginName) {
        
        if (StringUtils.isNotBlank(loginName)) {
            return czybmDao.findByCzybhOrPhone(loginName);
        }
        
        return null;
    }

	@Override
	public List<Long> getPersonalAuthIdsByCzybh(String czybh) {
		return czybmDao.getPersonalAuthIdsByCzybh(czybh);
	}

	@Override
	public Page<Map<String, Object>> findPageBySqlAuthDetail(
			Page<Map<String, Object>> page, Czybm czybm) {
		return czybmDao.findPageBySqlAuthDetail(page, czybm);
	}

	@Override
	public Page<Map<String, Object>> findPageBySqlPlatformAuth(
			Page<Map<String, Object>> page, String ptbh, String czybh) {
		return czybmDao.findPageBySqlPlatformAuth(page, ptbh, czybh );
	}

	@Override
	public List<Map<String, Object>> getCzyByIds(String czybhs) {
		return  czybmDao.getCzyByIds(czybhs);
	}

	@Override
	public boolean checkIsWhiteIp(Long ip) {

		return czybmDao.checkIsWhiteIp(ip);
	}
	

}
