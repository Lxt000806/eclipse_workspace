package com.house.home.dao.commi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.commi.CommiNotProvideCustStakeholder;

@SuppressWarnings("serial")
@Repository
public class CommiNotProvideCustStakeholderDao extends BaseDao {

	/**
	 * CommiNotProvideCustStakeholder分页信息
	 * 
	 * @param page
	 * @param commiNotProvideCustStakeholder
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CommiNotProvideCustStakeholder commiNotProvideCustStakeholder) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select a.PK,a.CustCode,a.Role,a.Remarks,a.ReProvideMon, " +
				"a.Expired,a.LastUpdate,a.LastUpdatedBy,a.ActionLog,b.NOTE TypeDescr,c.Address,d.Descr RoleDescr "+
				"from tCommiNotProvideCustStakeholder a " +
				"left join tXTDM b on a.Type = b.CBM and b.ID = 'COMMINPCSTYPE' " +
				"left join tCustomer c on a.CustCode = c.Code "+
				"left join tRoll d on a.Role = d.Code "+
				"where 1=1 ";

    	if (commiNotProvideCustStakeholder.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(commiNotProvideCustStakeholder.getPk());
		}
    	if (StringUtils.isNotBlank(commiNotProvideCustStakeholder.getAddress())) {
			sql += " and c.Address like ? ";
			list.add("%"+commiNotProvideCustStakeholder.getAddress()+"%");
		}
    	if (StringUtils.isNotBlank(commiNotProvideCustStakeholder.getCustCode())) {
			sql += " and a.CustCode=? ";
			list.add(commiNotProvideCustStakeholder.getCustCode());
		}
    	if (StringUtils.isNotBlank(commiNotProvideCustStakeholder.getRole())) {
			sql += " and a.Role=? ";
			list.add(commiNotProvideCustStakeholder.getRole());
		}
    	if (StringUtils.isNotBlank(commiNotProvideCustStakeholder.getType())) {
			sql += " and a.Type=? ";
			list.add(commiNotProvideCustStakeholder.getType());
		}
    	if (commiNotProvideCustStakeholder.getReProvideMon() != null) {
			sql += " and a.ReProvideMon=? ";
			list.add(commiNotProvideCustStakeholder.getReProvideMon());
		}
    	
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.Pk";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

}

