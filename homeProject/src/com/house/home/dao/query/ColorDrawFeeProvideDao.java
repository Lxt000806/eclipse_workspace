package com.house.home.dao.query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.design.Customer;
import com.sun.org.apache.bcel.internal.generic.NEW;

@SuppressWarnings("serial")
@Repository
public class ColorDrawFeeProvideDao extends BaseDao {
	
	/**
	 * 分页信息
	 * 
	 * @param page
	 * @param customer
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();
		                                                                                                        
		String sql = "select * from (select b.Address,c.NOTE IsFullColorDrawDescr , DrawNoChg, f.NOTE DrawNoChgDescr, " 
				+"case when a.DrawNoChg='1' then DrawNoChgDate else d.ConfirmDate end ConfirmDate,e.qqConfirmDate, "
		        +"isnull(a.DrawQty,0)DrawQty,isnull(a.Draw3DQty,0)Draw3DQty  "
				+"from tCustomer b  "
				+"left join tDesignPicPrg a on a.CustCode=b.Code  "
				+"left join (select min(ConfirmDate) ConfirmDate,custCode from tCustDoc where Type='2' group by custCode)d on d.custCode=b.code "
				+"left join tXTDM c on a.IsFullColorDraw=c.CBM and c.ID='DRAWTYPE' "
				+"left join (select max(ConfirmDate)qqConfirmDate,in_a.CustCode from tPrjProg in_a where in_a.PrjItem='3' group by in_a.CustCode) e on b.Code=e.CustCode "
				+"left join tXTDM f on a.DrawNoChg = f.CBM and f.ID = 'YESNO' "
				+"where 1=1 "; 
		if(customer.getDateFrom()!=null && customer.getDateTo()!=null){
			sql+="and ( (a.DrawNoChg='1' and a.DrawNoChgDate>=? and a.DrawNoChgDate<dateadd(day,1,?)) "
				+" or (isnull(a.DrawNoChg,'')<>'1' and d.ConfirmDate>=? and d.ConfirmDate<dateadd(day,1,?)) )"; 
			list.add(customer.getDateFrom());
			list.add(customer.getDateTo());
			list.add(customer.getDateFrom());
			list.add(customer.getDateTo());
		}else if(customer.getDateFrom()!=null && customer.getDateTo()==null){
			sql+="and ( (a.DrawNoChg='1' and a.DrawNoChgDate>=? ) "
				+" or (isnull(a.DrawNoChg,'')<>'1' and d.ConfirmDate>=? ) )"; 
			list.add(customer.getDateFrom());
			list.add(customer.getDateFrom());
		}else if(customer.getDateFrom()==null && customer.getDateTo()!=null){
			sql+="and ( (a.DrawNoChg='1' and a.DrawNoChgDate<dateadd(day,1,?)) "
				+" or (isnull(a.DrawNoChg,'')<>'1' and  d.ConfirmDate<dateadd(day,1,?))  )"; 
			list.add(customer.getDateTo());
			list.add(customer.getDateTo());
		}
		
		if (customer.getConfirmWallDateFrom() != null
		        && customer.getConfirmWallDateTo() != null) {
		    
		    sql += "and e.qqConfirmDate >= ? and e.qqConfirmDate < dateadd(day, 1, ?) ";
		    
		    list.add(customer.getConfirmWallDateFrom());
		    list.add(customer.getConfirmWallDateTo());
        } else if (customer.getConfirmWallDateFrom() != null) {
            sql += "and e.qqConfirmDate >= ? ";
            
            list.add(customer.getConfirmWallDateFrom());
        } else if (customer.getConfirmWallDateTo() != null) {
            sql += "and e.qqConfirmDate <= dateadd(day, 1, ?) ";
            
            list.add(customer.getConfirmWallDateTo());
        }
		
		if (StringUtils.isNotBlank(customer.getAddress())) {
			sql += " and b.address like ? ";
			list.add("%"+customer.getAddress().trim()+"%");
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a where isnull(ConfirmDate,'')<>'' order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a where isnull(ConfirmDate,'')<>'' order by a.Address ";
		}
		System.out.println(sql);
		return this.findPageBySql(page, sql, list.toArray());
	}
}

