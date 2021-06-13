package com.house.home.dao.workflow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.workflow.ActByteArray;

@SuppressWarnings("serial")
@Repository
public class ActByteArrayDao extends BaseDao {

	/**
	 * ActByteArray分页信息
	 * 
	 * @param page
	 * @param actByteArray
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ActByteArray actByteArray) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from ACT_GE_BYTEARRAY a where 1=1 ";

    	if (StringUtils.isNotBlank(actByteArray.getId())) {
			sql += " and a.ID_=? ";
			list.add(actByteArray.getId());
		}
    	if (actByteArray.getRev() != null) {
			sql += " and a.REV_=? ";
			list.add(actByteArray.getRev());
		}
    	if (StringUtils.isNotBlank(actByteArray.getName())) {
			sql += " and a.NAME_=? ";
			list.add(actByteArray.getName());
		}
    	if (StringUtils.isNotBlank(actByteArray.getDeploymentId())) {
			sql += " and a.DEPLOYMENT_ID_=? ";
			list.add(actByteArray.getDeploymentId());
		}
    	if (actByteArray.getGenerated() != null) {
			sql += " and a.GENERATED_=? ";
			list.add(actByteArray.getGenerated());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Id";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

}

