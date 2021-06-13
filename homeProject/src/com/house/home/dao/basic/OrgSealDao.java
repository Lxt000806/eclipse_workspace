package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.OrgSeal;

@SuppressWarnings("serial")
@Repository
public class OrgSealDao extends BaseDao {

	/**
	 * OrgSeal分页信息
	 * 
	 * @param page
	 * @param orgSeal
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, OrgSeal orgSeal) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select PK,OrgId,SealId,Htext,Qtext,b.NOTE Color,c.NOTE Central,"
				+"d.NOTE Type,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog "
				+"from tOrgSeal a "
				+"left join tXTDM b on a.Color = b.CBM and b.ID = 'SEALCOLOR' "
				+"left join tXTDM c on a.Central = c.CBM and c.ID = 'SEALCENTRAL' "
				+"left join tXTDM d on a.Type = d.CBM and d.ID = 'SEALTYPE' "
				+"where 1=1 ";

    	if (orgSeal.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(orgSeal.getPk());
		}
    	if (StringUtils.isNotBlank(orgSeal.getOrgId())) {
			sql += " and a.OrgId=? ";
			list.add(orgSeal.getOrgId());
		}
   
    	if (StringUtils.isNotBlank(orgSeal.getSealId())) {
			sql += " and a.SealId=? ";
			list.add(orgSeal.getSealId());
		}
    	
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.Pk";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 更新所有已启用的印章Id为新的印章Id
	 * @param oldSealId
	 * @param newSealId
	 */
	public void updateSealId(String oldSealId,String newSealId) {

		String sql = "update tTaxPayeeESign set SealId=? where SealId=? ";

		this.executeUpdateBySql(sql, newSealId,oldSealId);
	}
}

