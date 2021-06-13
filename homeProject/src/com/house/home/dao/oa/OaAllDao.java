package com.house.home.dao.oa;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.oa.OaAll;

@SuppressWarnings("serial")
@Repository
public class OaAllDao extends BaseDao {

	/**
	 * OaAll分页信息
	 * 
	 * @param page
	 * @param oaAll
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, OaAll oaAll) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from OA_ALL a where 1=1 ";

    	if (StringUtils.isNotBlank(oaAll.getProcInstId())) {
			sql += " and a.PROC_INST_ID_=? ";
			list.add(oaAll.getProcInstId());
		}
    	if (StringUtils.isNotBlank(oaAll.getUserId())) {
			sql += " and a.USER_ID=? ";
			list.add(oaAll.getUserId());
		}
    	if (oaAll.getApplyTime() != null) {
			sql += " and a.APPLY_TIME=? ";
			list.add(oaAll.getApplyTime());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.ProcInstId";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

}

