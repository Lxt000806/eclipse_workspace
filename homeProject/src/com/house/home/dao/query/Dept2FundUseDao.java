package com.house.home.dao.query;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.entity.insales.Purchase;
@Repository
@SuppressWarnings("serial")
public class Dept2FundUseDao extends BaseDao {
	public Page<Map<String,Object>> findPageBySql_pdept2FundUse(Page<Map<String,Object>> page, Purchase purchase) {
		Assert.notNull(purchase);

		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pdept2FundUse(?,?,?,?)}");
			call.setTimestamp(1, purchase.getDateFrom1() == null ? null : new Timestamp(
					purchase.getDateFrom1().getTime()));
			call.setTimestamp(2, purchase.getDateTo1() == null ? null : new Timestamp(
					DateUtil.endOfTheDay(purchase.getDateTo1()).getTime()));
			call.setInt(3, page.getPageNo());
			call.setInt(4,page.getPageSize());
			call.execute();
			ResultSet rs = call.getResultSet(); 
			List<Map<String, Object>> list=BeanConvertUtil.resultSetToList(rs);
			page.setResult(list); 			
			if (!list.isEmpty()) {
				page.setTotalCount((Integer)list.get(0).get("totalcount"));
			} else {
				page.setTotalCount(0);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return page;
	} 
	public Page<Map<String, Object>> findPrepayFeePageBySql(
		Page<Map<String, Object>> page, Purchase purchase) {
		List<Object> list = new ArrayList<Object>();
		String sql ="select * from"; 
			 sql+=" (select a.No, a.ItemType1, a.Type, x1.NOTE TypeDescr, it1.Descr ItemType1Descr,"
			    + " a.AppDate, d.Descr SupplierDescr, b.PUNo, b.Status,"              
                + " x2.NOTE StatusDescr, b.Amount, b.AftAmount, a.PayDate, b.Remarks,d.Department2 "
                + " from   tSupplierPrepay a " 
                + " inner join tSupplierPrepayDetail b on a.No = b.No "
                + " left join tXTDM x1 on x1.ID = 'SPLPREPAYTYPE' and a.Type = x1.CBM "
                + " left join tItemType1 it1 on a.ItemType1 = it1.Code "
                + " left join tSupplier d on b.Supplier = d.Code "
                + " left join tXTDM x2 on x2.ID = 'SPLPDSTATUS' and b.Status = x2.CBM "
                + " left join tPurchase e on b.PUNo = e.No "
			    + " left join tDepartment2  dpt2 on dpt2.Code=d.Department2 "
	            + " where a.ItemType1='RZ'  " 
	            + " and a.PayDate>=? and  a.PayDate<=? " ;
		list.add(purchase.getDateFrom1());
		list.add(new Timestamp(
					DateUtil.endOfTheDay(purchase.getDateTo1()).getTime()));
		if (StringUtils.isNotBlank(purchase.getSupplierDepartment2())) {
			sql += " and d.Department2=? ";
			list.add(purchase.getSupplierDepartment2());
		};
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " )a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " )a order by a.No";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> findPurArrFeePageBySql(
			Page<Map<String, Object>> page, Purchase purchase) {
		List<Object> list = new ArrayList<Object>();
		String sql="select * from( select   sum(pad.ArrivQty * pud.UnitPrice) ItemAmount,pa.PUNo,pu.CheckOutNo, dtp2.Desc1 Department2Descr, x1.note purchTypeDescr,C.Descr SupplierDescr, " 
				+ "    case when   pu.type='S' then ("
				+ "              case  when  sum(pad.ArrivQty * pud.UnitPrice)- isnull(b.RemainFirstAmount,pu.FirstAmount) >=0 and isnull(b.RemainFirstAmount,pu.FirstAmount)>0"
                + "              then sum(pad.ArrivQty * pud.UnitPrice)- isnull(b.RemainFirstAmount,pu.FirstAmount) "
                + "              else  sum(pad.ArrivQty * pud.UnitPrice) end)"
                + "       else (case when sum(pad.ArrivQty * pud.UnitPrice)<= isnull(b.RemainFirstAmount,pu.FirstAmount) and isnull(b.RemainFirstAmount,pu.FirstAmount)<0   "  
                + "             then sum(pad.ArrivQty * pud.UnitPrice)- isnull(b.RemainFirstAmount,pu.FirstAmount)"
                + "              else  sum(pad.ArrivQty * pud.UnitPrice) end)"
                + "       end  PurArrFee, "    
                + "     case when   pu.type='S' then (  "
                + "                 case when  sum(pad.ArrivQty * pud.UnitPrice)- isnull(b.RemainFirstAmount,pu.FirstAmount) >0  then isnull(b.RemainFirstAmount,pu.FirstAmount) else 0  end  ) "
                + "         else  ( case when  sum(pad.ArrivQty * pud.UnitPrice)- isnull(b.RemainFirstAmount,pu.FirstAmount) <0  then isnull(b.RemainFirstAmount,pu.FirstAmount) else 0  end  ) "
                + "    end    RemainFirstAmount   "   
                + "   from   tPurchArr pa  " 
                + "   inner join tPurchArrDetail pad on pad.PANo = pa.no "
                + "   inner join  tPurchaseDetail pud on pud.pk=pad.RefPk  "
                + "   inner join tPurchase pu on pu.no = pud.PUNo " 
                + "   left join tSupplier c on c.code = pu.Supplier   " 
                + "   left  outer join tDepartment2 dtp2 on dtp2.Code = c.Department2"
                + "   left outer join tXTDM x1 on pu.Type = x1.CBM  and x1.id='PurchType'  "
                + "   left outer join (  "
				+ "	  select  isnull(PU.FirstAmount,0)-sum(pad.ArrivQty*pud.UnitPrice) RemainFirstAmount ,pa.PUNO  from   tPurchArr pa "
				+ "	  inner join tPurchArrDetail pad on  pad.PANo=pa.no"
				+ "	  inner join tPurchaseDetail pud  on pud.pk=pad.RefPk " 
				+ "	  inner join  tPurchase pu on  pu.no=pud.PUNo"
				+ "	 where    pa.date<? "
				+ "	 group by pa.puno,pu.FirstAmount "
				+ "   )b on  b.puno=pa.puno "
				+ "   where pa.Date>=? and   pa.Date<=? ";
		       list.add(purchase.getDateFrom1());
		       list.add(purchase.getDateFrom1());
		       list.add(new Timestamp(
						DateUtil.endOfTheDay(purchase.getDateTo1()).getTime()));
		       if (StringUtils.isNotBlank(purchase.getSupplierDepartment2())) {
					sql += " and c.Department2=? ";
					list.add(purchase.getSupplierDepartment2());
				};
			  sql+=" group by   pa.PUNo , isnull(b.RemainFirstAmount,pu.FirstAmount), "
                + "  pu.CheckOutNo, dtp2.Desc1 , x1.note,C.Descr ,pu.Type " ;   
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " )a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " )a order by a.CheckOutNo";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 结算差价（采购其他费用部分）
	 * 
	 */
	public Page<Map<String, Object>> findOtherFeePageBySql(
			Page<Map<String, Object>> page, Purchase purchase) {
		List<Object> list = new ArrayList<Object>();
		String sql ="select * from( "; 
		sql+="select min(a.PayDate) PayDate ,c.OtherCost+c.OtherCostAdj  OtherFee,c.OtherCost,c.OtherCostAdj,PUNo,d.Department2,d.Descr  SupplierDescr,c.CheckOutNo   "
		   + " from    tSupplierPay a  "
		   + " left  outer join  tSupplierPayDetail b  on a.no=b.No "
		   + " left  outer  join tPurchase c  on c.no=PUNo "
		   + " left join tSupplier d on c.Supplier = d.Code   "
		   + " where   c.ItemType1='RZ' and  a.PayDate>=? and  a.PayDate<=? "
		   + " and not exists(select 1 from  tSupplierPay sp  left  outer join  tSupplierPayDetail spd on sp.no=spd.No  where sp.PayDate< ?  and spd.PUNo=b.PUNo ) "; 
		list.add(purchase.getDateFrom1());
		list.add(new Timestamp(
					DateUtil.endOfTheDay(purchase.getDateTo1()).getTime()));
		list.add(purchase.getDateFrom1());
	    if (StringUtils.isNotBlank(purchase.getSupplierDepartment2())) {
				sql += " and d.Department2=? ";
				list.add(purchase.getSupplierDepartment2());
			};
	  
			sql+=" group by C.OtherCost,c.OtherCostAdj,b.puno,d.Department2,d.Descr,c.CheckOutNo   ";	
			
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " )a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " )a order by  a.Department2";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 结算差价（采购其他费用部分）
	 * 
	 */
	public Page<Map<String, Object>> findPreAmountPageBySql(
			Page<Map<String, Object>> page, Purchase purchase) {
		List<Object> list = new ArrayList<Object>();
		String sql ="select * from( "; 
		 sql+=" select   a.No,a.CheckOutNo,a.PreAmount ,d.Department2,A.PayDate,d.Descr SupplierDescr "
			+ " from    tSupplierPay a "
			+ " left join tSplCheckOut c on a.CheckOutNo = c.No "
	        + " left join tSupplier d on c.SplCode = d.Code  "
		    + " where   d.ItemType1='RZ' and  a.PayDate>=? and  a.PayDate<=? " ;
		list.add(purchase.getDateFrom1());
		 list.add(new Timestamp(
					DateUtil.endOfTheDay(purchase.getDateTo1()).getTime()));
	    if (StringUtils.isNotBlank(purchase.getSupplierDepartment2())) {
				sql += " and d.Department2=? ";
				list.add(purchase.getSupplierDepartment2());
			};
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " )a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " )a order by  a.Department2";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	
	/**
	 * 人工费用 明细
	 */
	public Page<Map<String, Object>> findLaborFeePageBySql(
			Page<Map<String, Object>> page, Purchase purchase) {
		List<Object> list = new ArrayList<Object>();
		String sql ="select * from("; 	
		  sql+= " select  a.No, a.ItemType1,a.ActName, a.CardID, a.Remarks ShRemarks, "
			  + " a.AppCZY, a.Date, a.ConfirmCZY, a.ConfirmDate, a.PayCZY, a.PayDate, a.DocumentNo,"
			  + " b.ActName DetailActName, b.CardID DetailCardID, b.FeeType, f.CustCode, "
			  + " b.AppSendNo, b.Amount, b.Remarks LaborFeeDetailRemarks, c.Address, "
			  + " c.DocumentNo CustomerDocumentNo, d.Descr FeeTypeDescr, "
			  + " (case when c.CustType='2' then dept2I.Desc1 else dept2II.Desc1 end )Department2Descr  "
			  + " from  tLaborFee a "
			  + " left outer join tLaborFeeDetail b on b.no = a.No  "
			  + " left outer join tCustomer c on c.code = b.CustCode "
			  + " left outer join tLaborFeeType d on d.code = b.FeeType "
			  + " left  outer join tCustStakeholder e on e.Role='00' and c.code=e.CustCode " 
			  + " left  outer join tCustStakeholder f on f.Role='50' and c.code=f.CustCode " 
			  + " left outer  join tEmployee e1  on e1.Number=e.EmpCode "
			  + " left outer join tEmployee e2  on e2.Number=f.EmpCode "
			  + " left outer join tDepartment2 dept2I on  dept2I.code=e1.Department2 "
			  + " left outer join tDepartment2 dept2II on  dept2II.code=e2.Department2 "
		      + " where  a.ItemType1='RZ' "
		      + " and a.payDate>=? and  a.payDate<=? " ;
		    list.add(purchase.getDateFrom1());
		    list.add(new Timestamp(
					DateUtil.endOfTheDay(purchase.getDateTo1()).getTime()));
		    if (StringUtils.isNotBlank(purchase.getSupplierDepartment2())) {
					sql += " and ( e1.Department2=? or e2.Department2=? )";
					list.add(purchase.getSupplierDepartment2());
					list.add(purchase.getSupplierDepartment2());
				};
			if (StringUtils.isNotBlank(page.getPageOrderBy())){
				sql += " )a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
			}else{
				sql += " )a order by a.No";
			}
			return this.findPageBySql(page, sql, list.toArray());
	}
	
		
}
