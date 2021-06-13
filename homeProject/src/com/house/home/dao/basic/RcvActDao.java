package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.StringUtils;
import com.house.home.entity.basic.RcvAct;

@SuppressWarnings("serial")
@Repository
public class RcvActDao extends BaseDao {

	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, RcvAct rcvAct) {
		List<Object> list = new ArrayList<Object>();
		
		String sql = "select a.*,b.Descr PayeeDescr,c.Descr BankDescr, " +
		        "e.NOTE AllowTransDescr, d.NameChi AdminName " +
				"from tRcvAct a " +
				"left join tTaxPayee b on a.PayeeCode=b.Code " +
				"left join tBank c on c.Code=a.BankCode " +
				"left join tEmployee d on a.Admin = d.Number " +
				"left join tXTDM e on a.AllowTrans = e.CBM and e.ID = 'YESNO' " +
				"where 1=1 ";
		
		if (StringUtils.isNotBlank(rcvAct.getCode())) {
			sql += " and a.Code=? ";
			list.add(rcvAct.getCode());
		}
		
		if (StringUtils.isNotBlank(rcvAct.getDescr())) {
			sql += " and a.Descr like ? ";
			list.add("%" + rcvAct.getDescr() + "%");
		}
		
		if (StringUtils.isBlank(rcvAct.getExpired()) 
				|| "F".equals(rcvAct.getExpired())) {
			sql += " and a.expired='F' ";
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " order by a." + page.getPageOrderBy() + " " + page.getPageOrder();
		} else {
			sql += " order by a.LastUpdate desc";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	@SuppressWarnings("unchecked")
	public RcvAct getByCode(String code) {
		String hql = "from RcvAct a where a.code=? ";
		List<RcvAct> list = this.find(hql, new Object[]{code});
		if (list != null && list.size() > 0) 
			return list.get(0);
		else
			return null;
	}

	@SuppressWarnings("unchecked")
	public RcvAct getByDescr(String descr, String code) {
		String hql = "from RcvAct a where a.descr=? and a.code <> ?";
		List<RcvAct> list = this.find(hql, new Object[]{descr,code});
		if (list != null && list.size() > 0) 
			return list.get(0);
		else
			return null;
	}
}
