package com.house.home.dao.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.SqlUtil;
import com.house.home.entity.design.Customer;
import com.sun.org.apache.xpath.internal.operations.And;
@Repository
@SuppressWarnings("serial")
public class IntMeasureProgDao extends BaseDao {
	
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from (select a.Code,a.Address 楼盘,c.Desc1 客户类型,e.NOTE 实际进度,cast(dbo.fGetEmpNameChi(a.Code,'61') as nvarchar(1000)) 橱柜设计师, "
				+"l.NameChi 集成设计师,f.Date 申报集成初测时间,f.DealDate 集成初测实际处理时间, "
				+"g.Date 申报橱柜精测时间,g.DealDate 橱柜精测实际处理时间,h.Date 申报衣柜精测时间,h.DealDate 衣柜精测实际处理时间, "
				+"dateadd(day,7,g.Date) 橱柜预计下单时间,dateadd(day,7,h.Date) 衣柜预计下单时间,i.CupAppDate 橱柜实际下单时间,i.IntAppDate 衣柜实际下单时间 "
				+"from tCustomer a  "
				+"left join (  "
				+"	select max(PK) PK,CustCode "
				+"	from tPrjProg a "
				+"	where a.begindate = (select max (begindate) from tprjprog where custcode= a.custcode) "
				+"	group by  a.CustCode "
				+")b on b.CustCode=a.Code "
				+"left join tCustType c on a.CustType=c.Code "
				+"left join tPrjProg d on b.PK=d.PK "
				+"left join tXTDM e on e.CBM = d.PrjItem and e.ID = 'prjitem' "
				+"left join (select max(No)No,CustCode from tPrjJob where JobType='06' and Status not in ('1','5') group by CustCode )f0 on a.Code=f0.CustCode "
				+"left join tPrjJob f on f0.No=f.No "
				+"left join (select max(No)No,CustCode from tPrjJob where JobType='07' and Status not in ('1','5') group by CustCode )g0 on a.Code=g0.CustCode "
				+"left join tPrjJob g on g0.No=g.No "
				+"left join (select max(No)No,CustCode from tPrjJob where JobType='01' and Status not in ('1','5') group by CustCode )h0 on a.Code=h0.CustCode "
				+"left join tPrjJob h on h0.No=h.No "
				+"left join tCustIntProg i on a.Code=i.CustCode "
				+"left join(select max(pk)Pk,CustCode from tCustStakeholder where Role='11' group by CustCode)j on a.Code=j.CustCode "
				+"left join tCustStakeholder k on j.Pk=k.PK  "
				+"left join tEmployee l on k.EmpCode=l.Number "
				+"where c.IsAddAllInfo='1' and a.Status='4' "
				+"and (exists( "
				+"	select 1 from tItemReq ir "
				+"  left join tIntProd ip on ir.IntProdPK=ip.PK "
				+"	where ir.CustCode=a.Code and ir.ItemType1='JC'"
				+"	and ((ip.IsCupboard='1' and i.CupAppDate is null and c.Type='1') or (ip.IsCupboard='0' and i.IntAppDate is null)) "
				+") or i.CupAppDate is null and c.Type='2' )"; 
		if(StringUtils.isNotBlank(customer.getAddress())){
			sql+=" and a.Address like ? ";
			list.add("%"+customer.getAddress()+"%");
		}
		if(StringUtils.isNotBlank(customer.getEmpCode())){
			sql+=" and k.EmpCode=? ";
			list.add(customer.getEmpCode());
		}
		if (StringUtils.isNotBlank(customer.getDepartment2())) {
			sql += " and l.Department2=? ";
			list.add(customer.getDepartment2());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ")a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ")a order by a.Code asc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	} 
}
