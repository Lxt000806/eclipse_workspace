package com.house.home.dao.workflow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.workflow.ActProcdef;

@SuppressWarnings("serial")
@Repository
public class ActProcdefDao extends BaseDao {

	/**
	 * ActProcdef分页信息
	 * 
	 * @param page
	 * @param actProcdef
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ActProcdef actProcdef) {
		
		List<Object> list = new ArrayList<Object>();

		String sql = " select a.CATEGORY_ category, a.ID_ id, a.DEPLOYMENT_ID_ deploymentId, a.KEY_ [key], a.NAME_ name,"
				+ " a.VERSION_ [version], a.RESOURCE_NAME_ resourceName, a.DGRM_RESOURCE_NAME_ dgrmResourceName,"
				+ " b.DEPLOY_TIME_ deployTime, a.SUSPENSION_STATE_ suspensionState,"
				+ " case when a.SUSPENSION_STATE_='1' then '已激活' else '已挂起' end suspensionStateDescr"
				+ " from ACT_RE_PROCDEF a"
				+ " left join ACT_RE_DEPLOYMENT b on a.DEPLOYMENT_ID_=b.ID_"
				+ " where 1=1 ";

    	if (StringUtils.isNotBlank(actProcdef.getId())) {
			sql += " and a.ID_=? ";
			list.add(actProcdef.getId());
		}
    	if (actProcdef.getRev() != null) {
			sql += " and a.REV_=? ";
			list.add(actProcdef.getRev());
		}
    	if (StringUtils.isNotBlank(actProcdef.getCategory())) {
			sql += " and a.CATEGORY_=? ";
			list.add(actProcdef.getCategory());
		}
    	if (StringUtils.isNotBlank(actProcdef.getName())) {
    		sql += "  and a.NAME_ like ?";
			list.add("%" + actProcdef.getName()+ "%");
		
		}
    	if (StringUtils.isNotBlank(actProcdef.getKey())) {
			sql += " and a.KEY_=? ";
			list.add(actProcdef.getKey());
		}
    	if (actProcdef.getVersion() != null) {
			sql += " and a.VERSION_=? ";
			list.add(actProcdef.getVersion());
		}
    	if (StringUtils.isNotBlank(actProcdef.getDeploymentId())) {
			sql += " and a.DEPLOYMENT_ID_=? ";
			list.add(actProcdef.getDeploymentId());
		}
    	if (StringUtils.isNotBlank(actProcdef.getResourceName())) {
			sql += " and a.RESOURCE_NAME_=? ";
			list.add(actProcdef.getResourceName());
		}
    	if (StringUtils.isNotBlank(actProcdef.getDgrmResourceName())) {
			sql += " and a.DGRM_RESOURCE_NAME_=? ";
			list.add(actProcdef.getDgrmResourceName());
		}
    	if (StringUtils.isNotBlank(actProcdef.getDescription())) {
			sql += " and a.DESCRIPTION_=? ";
			list.add(actProcdef.getDescription());
		}
    	if (actProcdef.getHasStartFormKey() != null) {
			sql += " and a.HAS_START_FORM_KEY_=? ";
			list.add(actProcdef.getHasStartFormKey());
		}
    	if (actProcdef.getSuspensionState() != null) {
			sql += " and a.SUSPENSION_STATE_=? ";
			list.add(actProcdef.getSuspensionState());
		}
    	if (actProcdef.getOnlyLatestVersion() != null) { 
    		if (actProcdef.getOnlyLatestVersion()) { // 同一个流程标识，只显示最后一个版本
    			sql += " and a.VERSION_=(select max(VERSION_) from ACT_RE_PROCDEF where KEY_=a.KEY_) ";
    		}
    	}
    	
    	sql = "select * from (" + sql;
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ") a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ") a order by a.deployTime desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public void updateProcVersion(String wfProcInNo){
		String sql = " update tWfProcess set Version = a.Version+1,lastUpdate = getDate() from tWfProcess a where a.ProcKey = ? ";
		this.executeUpdateBySql(sql, new Object[]{wfProcInNo.split("[.]")[0]});
	}

}

