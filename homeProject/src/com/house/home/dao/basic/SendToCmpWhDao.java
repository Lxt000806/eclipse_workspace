package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.SendToCmpWh;

@SuppressWarnings("serial")
@Repository
public class SendToCmpWhDao extends BaseDao {

	/**
	 * SendToCmpWh分页信息
	 * 
	 * @param page
	 * @param sendToCmpWh
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, SendToCmpWh sendToCmpWh) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select a.PK,a.AmountFrom,a.AmountTo,a.ActionLog,a.Expired,a.LastUpdate,a.LastUpdatedBy, "
				+"b.Desc1 CustTypeDescr,c.Descr supplDescr,d.Descr RegionDescr,a.ItemAppType,tx.NOTE ItemAppTypeDescr " 
				+"from tSendToCmpWh a " 
				+"left join tCustType b on a.CustType=b.Code " 
				+"left join tSupplier c on a.SupplCode=c.Code "
				+"left join tRegion d on a.RegionCode=d.Code "
				+"left join tXTDM tx on tx.ID='TOCMITEMAPPTYPE' and tx.CBM=a.ItemAppType "
				+"where 1=1 ";

		if (StringUtils.isNotBlank(sendToCmpWh.getCustType())) {
			sql += " and a.CustType in " + "('"+sendToCmpWh.getCustType().replace(",", "','" )+ "')";
		}
    	if (StringUtils.isNotBlank(sendToCmpWh.getSupplCode())) {
			sql += " and a.SupplCode=? ";
			list.add(sendToCmpWh.getSupplCode());
		}
    	if (StringUtils.isNotBlank(sendToCmpWh.getRegionCode())) {
			sql += " and a.RegionCode=? ";
			list.add(sendToCmpWh.getRegionCode());
		}
		if (StringUtils.isBlank(sendToCmpWh.getExpired()) || "F".equals(sendToCmpWh.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.LastUpdate desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

}

