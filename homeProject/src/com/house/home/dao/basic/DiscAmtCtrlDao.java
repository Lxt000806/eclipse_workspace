package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.DiscAmtCtrl;

@SuppressWarnings("serial")
@Repository
public class DiscAmtCtrlDao extends BaseDao {

	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, DiscAmtCtrl discAmtCtrl) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from (select a.PK,a.CustType,b.Desc1 CustTypeDescr,a.DiscAmtType,c.Descr DiscAmtTypeDescr, "
					+ "a.MaxDiscAmtExpr,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,a.IsCupboard,d.NOTE isCupboardDescr,"
					+ "a.IsService,d2.NOTE isServiceDescr "
					+ "from tDiscAmtCtrl a "
					+ "left join tCusttype b on b.Code=a.CustType "
					+ "left join tItemType1 c on c.Code=a.DiscAmtType "
					+ "left join tXTDM d on d.ID='YESNO' and d.CBM=a.isCupboard "
					+ "left join tXTDM d2 on d2.ID='YESNO' and d2.CBM=a.isService "
					+ "where 1=1 ";

		if (StringUtils.isBlank(discAmtCtrl.getExpired()) || "F".equals(discAmtCtrl.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(discAmtCtrl.getCustType())) {
			sql += " and a.CustType in ('"+discAmtCtrl.getCustType().replace(",", "','")+"') ";
		}
		if (StringUtils.isNotBlank(discAmtCtrl.getDiscAmtType())) {
			sql += " and a.DiscAmtType in ('"+discAmtCtrl.getDiscAmtType().replace(",", "','")+"')";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " )a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " )a order by a.LastUpdate desc";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
}

