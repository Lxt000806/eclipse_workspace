package com.house.home.dao.commi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.commi.CommiCustStakeholderProvide;

@SuppressWarnings("serial")
@Repository
public class CommiCustStakeholderProvideDao extends BaseDao {

	/**
	 * CommiCustStakeholderProvide分页信息
	 * 
	 * @param page
	 * @param commiCustStakeholderProvide
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CommiCustStakeholderProvide commiCustStakeholderProvide) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select a.PK,a.CommiNo,a.CustCode,b.Address,a.Role,c.Descr RoleDescr,a.CommiProvidePer, "
					+"a.SubsidyProvidePer,a.MultipleProvidePer,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog "
					+"from tCommiCustStakeholderProvide a  "
					+"left join tCustomer b on a.CustCode = b.Code "
					+"left join tRoll c on a.Role = c.Code "
					+"left join tCommiCycle d on a.CommiNo = d.No "
					+"where 1=1 ";

    	if (commiCustStakeholderProvide.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(commiCustStakeholderProvide.getPk());
		}
    	if (commiCustStakeholderProvide.getMon() != null) {
			sql += " and d.Mon=? ";
			list.add(commiCustStakeholderProvide.getMon());
		}
    	if (StringUtils.isNotBlank(commiCustStakeholderProvide.getCommiNo())) {
			sql += " and a.CommiNo=? ";
			list.add(commiCustStakeholderProvide.getCommiNo());
		}
    	if (StringUtils.isNotBlank(commiCustStakeholderProvide.getAddress())) {
			sql += " and b.Address like ? ";
			list.add("%"+commiCustStakeholderProvide.getAddress()+"%");
		}
    	if (StringUtils.isNotBlank(commiCustStakeholderProvide.getRole())) {
			sql += " and a.Role=? ";
			list.add(commiCustStakeholderProvide.getRole());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.Pk";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

}

