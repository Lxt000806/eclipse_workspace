package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import sun.net.www.content.image.gif;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.GiftToken;

@SuppressWarnings("serial")
@Repository
public class GiftTokenDao extends BaseDao{
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, GiftToken giftToken){
		List<Object> list = new ArrayList<Object>();
		String sql = " select * from ( SELECT a.PK,a.CustCode,c.Descr CustDescr,c.Address,a.TokenNo,a.ItemCode,b.Descr ItemDescr,a.Qty,a.Status,x1.NOTE StatusDescr,"
					+" a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,ca.Descr cmpActivityDescr,c.SetDate "
					+" FROM dbo.tGiftToken a "
					+" LEFT JOIN dbo.tItem b ON a.ItemCode = b.Code "
					+" LEFT JOIN dbo.tCustomer c ON c.Code = a.CustCode "
					+" LEFT JOIN dbo.tXTDM x1 ON x1.CBM = a.Status AND x1.ID='GIFTTOKENSTATUS' "
					+" LEFT JOIN dbo.tCmpActivity ca ON ca.No = a.No "
					+" WHERE 1=1 ";
		if(StringUtils.isNotBlank(giftToken.getAddress())){
			sql += " AND c.Address like ? ";
			list.add("%"+giftToken.getAddress()+"%");
		}
		if(StringUtils.isNotBlank(giftToken.getCustCode())){
			sql += " AND a.CustCode=? ";
			list.add(giftToken.getCustCode());
		}
		if(StringUtils.isNotBlank(giftToken.getTokenNo())){
			sql += " AND a.TokenNo like ? ";
			list.add("%"+giftToken.getTokenNo()+"%");
		}
		if(StringUtils.isNotBlank(giftToken.getItemCode())){
			sql += " AND a.ItemCode=? ";
			list.add(giftToken.getItemCode());
		}
		if(StringUtils.isNotBlank(giftToken.getItemDescr())){
			sql += " AND b.Descr like ? ";
			list.add("%"+giftToken.getItemDescr()+"%");
		}
		if(StringUtils.isNotBlank(giftToken.getStatus())){
			sql += " AND a.Status=? ";
			list.add(giftToken.getStatus());
		}
		if(StringUtils.isNotBlank(giftToken.getNo())){
			sql += " AND a.No=? ";
			list.add(giftToken.getNo());
		}
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a order by a.LastUpdate desc";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Map<String,Object> existTokenNo(String tokenNo){
		String sql = " SELECT * FROM dbo.tGiftToken WHERE TokenNo=? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{tokenNo});
		if(list != null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
}
