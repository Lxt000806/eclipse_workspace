package com.house.home.dao.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.CustAccount;

@SuppressWarnings("serial")
@Repository
public class GiftCodeConfirmDao extends BaseDao{

	public Page<Map<String, Object>> getGiftCodeConfirmList(
			Page<Map<String, Object>> page, String phone) {
		String sql ="select b.NameChi ConfirmCzy,a.LastUpdate from tGiftCodeConfirm a "
				+" left join tEmployee b on a.LastUpdatedBy=b.Number "
				+" where a.Phone=? order by a.LastUpdate desc";
		return this.findPageBySql(page,sql,new Object[]{phone});
	}
	
	public Page<Map<String, Object>> getGiftAppList(
			Page<Map<String, Object>> page, String custCode) {
		String sql ="select c.Descr itemDescr,case when b.returnqtyed is null then a.sendqty else a.sendqty-b.returnqtyed end qty,d.Descr uomDescr from ( "
				+" select sum(gad.qty) sendqty, gad.itemcode,ga.CustCode from  tgiftappdetail gad "
				+" inner join tgiftapp ga on ga.no = gad.no "
				+" where   status <>'cancel'  and  OutType='1' and ga.CustCode<>''  and CustCode=? "
				+" group by  gad.itemcode ,ga.CustCode "
				+" ) a "
				+" left  outer join ( " 
				+" select   sum(gad.qty) returnqtyed, gad.itemcode,ga.CustCode from tgiftappdetail gad "   
				+" inner join tgiftapp ga on ga.no = gad.no where  status = 'return' and ga.CustCode<>'' "   
				+" group by gad.itemcode,ga.CustCode "
				+" ) b on b.itemcode = a.itemcode and b.CustCode=a.CustCode "   
				+" left join tItem c on a.ItemCode=c.Code "
				+" left join tUOM d on c.Uom=d.Code ";
		return this.findPageBySql(page,sql,new Object[]{custCode});
	}
	
	public Page<Map<String, Object>> getCustomerList(
			Page<Map<String, Object>> page, String address) {
		List<Object> list = new ArrayList<Object>();
		
		String sql = "select Address,Code CustCode from tCustomer where Expired='F' ";
		if(StringUtils.isNotBlank(address)){
			sql+=" and Address like ? ";
			list.add("%"+address+"%");
		}
		return this.findPageBySql(page,sql,list.toArray());
	}
	
	@SuppressWarnings("unchecked")
	public CustAccount getCustAccount(String phone){
		String hql = "from CustAccount where mobile1=?";
		List<CustAccount> list = this.find(hql, new Object[] { phone });
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
