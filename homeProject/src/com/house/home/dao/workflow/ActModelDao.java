package com.house.home.dao.workflow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.workflow.ActModel;

@SuppressWarnings("serial")
@Repository
public class ActModelDao extends BaseDao {

	/**
	 * ActModel分页信息
	 * 
	 * @param page
	 * @param actModel
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ActModel actModel) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select ID_,REV_,NAME_,a.KEY_,CREATE_TIME_,LAST_UPDATE_TIME_,VERSION_,META_INFO_ , " 
				+ " case when isnull(b.num,0)=0 then '未发布' else '已发布' end status "
				+ " from ACT_RE_MODEL a " 
				+ " left join (select KEY_, count(1) num from ACT_RE_PROCDEF group by KEY_ )b on b.key_=a.KEY_"
				+ " where a.VERSION_=(select max(b.VERSION_) from ACT_RE_MODEL b  where b.KEY_=a.key_) ";

    	if (StringUtils.isNotBlank(actModel.getId())) {
			sql += " and a.ID_=? ";
			list.add(actModel.getId());
		}
    	if (actModel.getRev() != null) {
			sql += " and a.REV_=? ";
			list.add(actModel.getRev());
		}
    	if (StringUtils.isNotBlank(actModel.getName())) {
			sql += " and a.NAME_ like ? ";
			list.add("%"+actModel.getName()+"%");
		}
    	if (StringUtils.isNotBlank(actModel.getKey())) {
			sql += " and a.KEY_=? ";
			list.add(actModel.getKey());
		}
    	if (StringUtils.isNotBlank(actModel.getCategory())) {
			sql += " and a.CATEGORY_=? ";
			list.add(actModel.getCategory());
		}
    	if (actModel.getCreateTime() != null) {
			sql += " and a.CREATE_TIME_=? ";
			list.add(actModel.getCreateTime());
		}
    	if (actModel.getLastUpdateTime() != null) {
			sql += " and a.LAST_UPDATE_TIME_=? ";
			list.add(actModel.getLastUpdateTime());
		}
    	if (actModel.getVersion() != null) {
			sql += " and a.VERSION_=? ";
			list.add(actModel.getVersion());
		}
    	if (StringUtils.isNotBlank(actModel.getMetaInfo())) {
			sql += " and a.META_INFO_=? ";
			list.add(actModel.getMetaInfo());
		}
    	if (StringUtils.isNotBlank(actModel.getDeploymentId())) {
			sql += " and a.DEPLOYMENT_ID_=? ";
			list.add(actModel.getDeploymentId());
		}
    	if (StringUtils.isNotBlank(actModel.getEditorSourceValueId())) {
			sql += " and a.EDITOR_SOURCE_VALUE_ID_=? ";
			list.add(actModel.getEditorSourceValueId());
		}
    	if (StringUtils.isNotBlank(actModel.getEditorSourceExtraValueId())) {
			sql += " and a.EDITOR_SOURCE_EXTRA_VALUE_ID_=? ";
			list.add(actModel.getEditorSourceExtraValueId());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by LAST_UPDATE_TIME_";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

}

