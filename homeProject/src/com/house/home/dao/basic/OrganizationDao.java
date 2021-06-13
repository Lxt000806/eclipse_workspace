package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.Organization;

@SuppressWarnings("serial")
@Repository
public class OrganizationDao extends BaseDao {

	/**
	 * Organization分页信息
	 * 
	 * @param page
	 * @param organization
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Organization organization) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from ( "
				+"select a.PK,a.OrgId,a.FlowId,a.IdentifyUrl,b.NOTE IsIdentifiedDescr,c.NOTE IsSilenceSignDescr,"
				+"a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,a.Name,a.IdNumber,a.IdType,a.ThirdPartyUserId, "
				+"a.OrgLegalName,a.OrgLegalIdNumber "
				+"from tOrganization a "
				+"left join tXTDM b on a.IsIdentified = b.CBM and b.ID = 'YESNO' "
				+"left join tXTDM c on a.IsSilenceSign = c.CBM and c.ID = 'YESNO' "
				+"where 1=1 ";

		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.Pk";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 是否存在机构
	 * 
	 * @param orgId
	 * @return
	 */
	public boolean isExistsOrg(String orgId) {

		String sql = "select 1 from tOrganization where OrgId = ? ";
		
		List<Map<String, Object>> list = this.findBySql(sql, orgId);
		if (list.size() > 0 && list != null) {
			return true;
		}
		return false;
	}
	
	/**
	 * 删除机构所有印章
	 * @param orgId
	 */
	public void delSealByOrgId(String orgId) {

		String sql = "delete from tOrgSeal where OrgId = ? ";

		this.executeUpdateBySql(sql, orgId);
	}
}

