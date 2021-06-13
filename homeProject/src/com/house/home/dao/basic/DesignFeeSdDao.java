package com.house.home.dao.basic;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.StringUtils;
import com.house.home.entity.basic.DesignFeeSd;
import org.springframework.stereotype.Repository;
@SuppressWarnings("serial")
@Repository
public class DesignFeeSdDao extends BaseDao{

	/**
	 * 设计费标准设置分页查询
	 * @author	created by zb
	 * @date	2018-12-21--下午3:24:28
	 * @param page
	 * @param designFeeSd
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, DesignFeeSd designFeeSd) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from(select a.PK,a.Position,b.desc2 Positiondescr ,a.DesignFee,a.DispSeq,a.LastUpdatedBy," +
					" a.LastUpdate,a.Expired,a.ActionLog,a.CustType,c.Desc1 CustTypeDescr " +	//增加客户类型 add by zb on 20200330
					" from tDesignFeeSd a " +
					" left outer join tPosition b on a.Position=b.code and b.type='4' " +
					" left join tCusttype c on c.Code=a.CustType " +
					" where 1=1";
		if (StringUtils.isBlank(designFeeSd.getExpired()) || "F".equals(designFeeSd.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(designFeeSd.getPosition())) {
			sql += " and a.Position =? ";
			list.add(designFeeSd.getPosition());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " ) a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		} else {
			sql += " ) a order by a.LastUpdate desc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 根据设计费获取DesignFeeSd
	 * @param DesignFee
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public DesignFeeSd getDesignFeeSdByDesignFee(Double DesignFee){
		String hql = "from DesignFeeSd where Expired='F' and DesignFee=?";
		List<DesignFeeSd> list = this.find(hql, new Object[] {DesignFee});
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	/**
	 * 根据PK获取设计费标准
	 * @author	created by zb
	 * @date	2020-4-1--上午11:35:11
	 */
	@SuppressWarnings("unchecked")
	public DesignFeeSd getDesignFByPositCustT(DesignFeeSd designFeeSd) {
		String hql = "from DesignFeeSd where position=? and custType=? ";
		List<DesignFeeSd> list = this.find(hql,new Object[] {designFeeSd.getPosition(), designFeeSd.getCustType()});
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

}
