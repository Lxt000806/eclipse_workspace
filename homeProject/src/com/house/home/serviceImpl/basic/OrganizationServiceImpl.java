package com.house.home.serviceImpl.basic;

import java.util.Date;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.esign.ESignUtils;
import com.house.framework.commons.utils.esign.JSONHelper;
import com.house.home.dao.basic.OrganizationDao;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.OrgSeal;
import com.house.home.entity.basic.Organization;
import com.house.home.service.basic.OrganizationService;

@SuppressWarnings("serial")
@Service
public class OrganizationServiceImpl extends BaseServiceImpl implements OrganizationService {

	@Autowired
	private OrganizationDao organizationDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Organization organization){
		return organizationDao.findPageBySql(page, organization);
	}

	@Override
	public Result doSave(Organization organization) {
		ESignUtils.generateESignToken(true);
		Employee employee = organizationDao.get(Employee.class, organization.getLastUpdatedBy());

		//查询当前操作员账号
		JSONObject accJson = ESignUtils.createPersonAcct(employee.getIdnum(), employee.getNameChi(), 
				"CRED_PSN_CH_IDCARD",employee.getIdnum(), employee.getPhone(), null);
		accJson = JSONHelper.castDataJson(accJson, JSONObject.class);
		
		//调用e签宝接口创建机构
		JSONObject orgJson = ESignUtils.createOrgAcct(organization.getIdNumber(),
				accJson.getString("accountId"), organization.getName(), null, 
				organization.getIdNumber());
		
		if(orgJson.getInteger("code") != 0 ){
			//机构在e签宝已存在，且在数据库不存在，则在数据库创建机构
			if(orgJson.getInteger("code") == 53000000 
					&& !organizationDao.isExistsOrg(JSONHelper.castDataJson(orgJson, JSONObject.class).getString("orgId")) ){
				
				orgJson = JSONHelper.castDataJson(orgJson, JSONObject.class);
				doSaveData(orgJson, accJson, organization);
				
				return new Result(Result.SUCCESS_CODE, "机构创建成功");
			}else {
				return new Result(Result.FAIL_CODE, orgJson.getString("message")); 
			}
		}
		orgJson = JSONHelper.castDataJson(orgJson, JSONObject.class);
		
		//设置为静默签署
		ESignUtils.setSignAuth(orgJson.getString("orgId"));
		
		doSaveData(orgJson, accJson, organization);
		
		return new Result(Result.SUCCESS_CODE, "机构创建成功");
	}

	@Override
	public void doUpdate(Organization organization) {
		ESignUtils.generateESignToken(true);
		
		//调用e签宝接口更新机构
		ESignUtils.modifyOrgAcct(organization.getOrgId(), organization.getName(),
				organization.getOrgLegalName(),organization.getOrgLegalIdNumber());
		
		//更新系统机构表
		organizationDao.update(organization);
	}

	@Override
	public void doDelete(Organization organization) {
		ESignUtils.generateESignToken(true);
		
		//调用e签宝接口更新机构
		ESignUtils.logoutOrgAcc(organization.getOrgId());
		
		//删除系统机构表
		organizationDao.delete(organization);
		
		//删除机构的所有印章
		organizationDao.delSealByOrgId(organization.getOrgId());
	}

	@Override
	public void doIdentity(Organization organization) {
		ESignUtils.generateESignToken(true);
		Employee employee = organizationDao.get(Employee.class, organization.getLastUpdatedBy());

		//查询当前操作员账号
		JSONObject accJson = ESignUtils.createPersonAcct(employee.getIdnum(), employee.getNameChi(), 
				"CRED_PSN_CH_IDCARD",employee.getIdnum(), employee.getPhone(), null);
		accJson = JSONHelper.castDataJson(accJson, JSONObject.class);
		
		//调用e签宝接口更新机构
		JSONObject json = ESignUtils.orgIdentity(organization.getOrgId(), accJson.getString("accountId"));
		
		//更新认证url和flowId
		organization.setIdentifyUrl(json.getString("url"));
		organization.setFlowId(json.getString("flowId"));
		organizationDao.update(organization);
	}

	@Override
	public void doRefreshIdentity(Organization organization) {
		ESignUtils.generateESignToken(true);
		
		JSONObject json = ESignUtils.queryOrgAccById(organization.getOrgId());
		if(json.getBoolean("status")){
			organization.setIsIdentified("1");
		}else {
			organization.setIsIdentified("0");
		}
		organizationDao.update(organization);
		
	}
	
	/**
	 * 机构、印章表保存
	 * @param orgJson
	 * @param accJson
	 * @param organization
	 */
	public void doSaveData(JSONObject orgJson,JSONObject accJson,Organization organization){
		//保存机构信息
		organization.setThirdPartyUserId(organization.getIdNumber());
		organization.setCreatorId(accJson.getString("accountId"));
		organization.setOrgId(orgJson.getString("orgId"));
		organization.setIdType("CRED_ORG_USCC");
		organization.setIsIdentified("0");
		organization.setIsSilenceSign("1");
		organizationDao.save(organization);

		//保存默认印章信息
		OrgSeal orgSeal = new OrgSeal();
		JSONArray jsonArray = ESignUtils.queryOrgSeal(orgJson.getString("orgId"));
		JSONObject sealJson = jsonArray.getJSONObject(0);
		orgSeal.setSealId(sealJson.getString("sealId"));
		orgSeal.setOrgId(orgJson.getString("orgId"));
		orgSeal.setColor("RED");
		orgSeal.setCentral("STAR");
		orgSeal.setType("TEMPLATE_ROUND");
		orgSeal.setLastUpdate(new Date());
		orgSeal.setLastUpdatedBy(organization.getLastUpdatedBy());
		orgSeal.setExpired("F");
		orgSeal.setActionLog("ADD");
		organizationDao.save(orgSeal);
		
	}
}
