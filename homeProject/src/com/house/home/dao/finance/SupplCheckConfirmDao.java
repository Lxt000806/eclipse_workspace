package com.house.home.dao.finance;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.entity.insales.Purchase;

@SuppressWarnings("serial")
@Repository
public class SupplCheckConfirmDao extends BaseDao {

	/**
	 * 主列表
	 * @author	created by zb
	 * @date	2019-4-4--上午9:50:56
	 * @param page
	 * @param purchase
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, Purchase purchase) {
		List<Object> params = new ArrayList<Object>();
		String sql = "select * from ( " +
		   			"select a.No,a.Status,a.Type,b.No IANo,c.Address,c.Area,a.ItemType1,d.Descr ItemType1Descr,b.IsCupboard,g3.NOTE IsCupboardDescr," +
		   			"a.Supplier,f.Descr SupplierDescr,a.AppCheckDate,case when b.Type='S' then a.Amount else -a.Amount end Amount, a.SplAmount, " +
		   			"case when b.type='S' then (case when a.Type = 'S' then a.Amount - a.SplAmount else -a.Amount - a.SplAmount end) " +
		   			"else (-a.Amount-a.SplAmount) end DiffAmount, " +
					"case when a.Type='S' then a.ProjectAmount else -a.ProjectAmount end ProjectAmount," +
					"(case when b.Type='S' then a.Amount else -a.Amount end)+a.OtherCost+a.OtherCostAdj" +
					"-a.FirstAmount-a.SecondAmount RemainAmount, " +
					"a.ProjectOtherCost,a.OtherCost, isnull(e.ErpOtherCost, 0) ErpOtherCost, " +
					"case when b.type='S' then (case when a.Type = 'S' then a.OtherCost - isnull(e.ErpOtherCost, 0) else -a.OtherCost - isnull(e.ErpOtherCost, 0) end) " +
					"else (a.OtherCost-isnull(e.ErpOtherCost, 0)) end DiffCost, " +
					"a.OtherCostAdj,a.CheckConfirmDate,a.CheckConfirmCZY,i.zwxm CheckConfirmCZYDescr,a.CheckConfirmRemarks,a.LastUpdate, " +
					"a.SplStatus,g.NOTE SplStatusDescr, " +
					"h.status CheckOutStatus,c.CheckStatus,g2.NOTE CheckOutStatusDescr,b.Type ItemAppType,isnull(a.FirstAmount,0) FirstAmount, " +
					"isnull(a.SecondAmount,0) SecondAmount,a.CustCode,c.carryFloor,c.CustType,j.Desc1 CustTypeDescr, " +
					"c.Layout, g4.NOTE LayoutDescr, k.RegionCode, l.Descr RegionDescr,b.sendDate,a.PayRemark,a.ToCheckConfirmDate,a.SYSConfirmRemarks, "+
					"refc.Address refAddress, b.Remarks ItemAppRemarks, "+
					" case when a.ItemType1<>'ZC' or isnull(sl.SalasAmount,0)=0 then 0  " +
					" else round((sl.SalasAmount-a.splamount)/sl.SalasAmount,2) end ProfitPer  "+                      
					"from tPurchase a " +
					"left join tItemApp b on b.PUNo=a.No " +
					"left join tCustomer c on c.Code=a.CustCode " +
					"left join tItemType1 d on d.Code=a.ItemType1 " +
					"left join ( " +
					"	select in_a.PUNo,sum(in_a.Amount) ErpOtherCost " +
					"	from tPurchaseFeeDetail in_a " +
					"	left join tSupplFeeType in_b on in_b.code=in_a.SupplFeeType " +
					"	where in_a.GenerateType='2' " +
					"	group by in_a.PUNo " +
					")e on e.PUNo=a.No " +
					"left join tSupplier f on f.Code=a.Supplier " +
					"left join tXTDM g on g.ID='PuSplStatus' and g.CBM=a.SplStatus " +
					"left join tSplCheckOut h on h.No=a.CheckOutNo " +
					"left join tXTDM g2 on g2.ID='SPLCKOTSTATUS' and g2.CBM=h.status " +
					//"left join tEmployee i on i.Number=a.CheckConfirmCZY " +
					"left join tczybm i on i.CZYBH=a.CheckConfirmCZY " +
					"left join tXTDM g3 on g3.CBM=b.IsCupboard and g3.ID='YESNO' " + //是否橱柜
					"left join tCusttype j on j.Code=c.CustType " +
                	"left join tXTDM g4 on g4.CBM=c.Layout and g4.ID='LAYOUT' " +
                	"left join tBuilder k on k.Code=c.BuilderCode " +
                	"left join tRegion l on l.Code=k.RegionCode " +
            		"left join tCustomer refc on refc.Code=b.RefCustCode " +
            		" left join (select a.No, sum(case when b.type='S' then a.SendQty else - a.SendQty end * ir.unitprice * ir.markup / 100) SalasAmount " +
            		"   from titemappdetail a " +
            		"   left join titemapp b on b.no = a.no " +
            		"   left outer join titemreq ir on ir.pk = a.reqpk  group by a.No ) sl on sl.No=b.No " +
	   				" where a.Expired='F' and a.Status<>'CANCEL' ";
		if (StringUtils.isNotBlank(purchase.getCheckOutStatus())) {
			sql += "and h.status in ('"+purchase.getCheckOutStatus().trim().replace(",", "','")+"')";
		}
		if ("0".equals(purchase.getIsIncludeSetComp())) {
			sql += "and isnull(h.status, '0') <> '2' ";
		}
		if (StringUtils.isNotBlank(purchase.getNo())) {
			sql += " and a.No = ? ";
			params.add(purchase.getNo());
		}
		if(StringUtils.isNotBlank(purchase.getItemType1())){
			sql += " and a.ItemType1 in ('"+purchase.getItemType1().trim().replace(",", "','")+"')";
		}
		if(StringUtils.isNotBlank(purchase.getSupplier())){
			sql += " and a.Supplier=? ";
			params.add(purchase.getSupplier());
		}
		if (StringUtils.isNotBlank(purchase.getSplStatus())) {
			sql += " and a.SplStatus in ('"+purchase.getSplStatus().trim().replace(",", "','")+"')";
		}
		if(null != purchase.getAppCheckDateFrom()){
			sql += " and a.AppCheckDate >=? ";
			params.add(DateUtil.startOfTheDay(purchase.getAppCheckDateFrom()));
		}
		if(null != purchase.getAppCheckDateTo()){
			sql += " and a.AppCheckDate < ? ";
			params.add(DateUtil.addDate(purchase.getAppCheckDateTo(), 1));
		}
		if (StringUtils.isNotBlank(purchase.getAddress())) {
			sql += " and c.Address like ?";
			params.add("%"+purchase.getAddress()+"%");
		}
		if(purchase.getSendDateFrom() != null){
			sql+=" and exists(select 1 from tItemAppSend ina where ina.IANO = b.no and ina.Date >= ? )";
			params.add(DateUtil.startOfTheDay(purchase.getSendDateFrom()));
		}
		if(purchase.getSendDateTo() != null){
			sql+=" and exists(select 1 from tItemAppSend ina where ina.IANO = b.no and ina.Date < ? )";
			params.add(DateUtil.addDate(purchase.getSendDateTo(), 1));
		}
		
		if (StringUtils.isNotBlank(purchase.getIANo())) {
            sql += " and b.No = ? ";
            params.add(purchase.getIANo());
        }
		
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a order by a.No desc ";
		}
		return this.findPageBySql(page, sql, params.toArray());
	}
	/**
	 * 获取采购费用明细
	 * @author	created by zb
	 * @date	2019-4-4--下午3:35:34
	 * @param page
	 * @param purchase
	 * @return
	 */
	public Page<Map<String, Object>> findPurFeeDetailPageBySql(
			Page<Map<String, Object>> page, Purchase purchase) {
		String sql = "select case when a.SupplFeeType is null then d.SupplFeeType else a.SupplFeeType end SupplFeeType, " +
					"case when a.SupplFeeType is null then f.Descr else b.Descr end supplFeeTypeDescr, " +
					"a.GenerateType,c.NOTE GenerateTypeDescr,a.Amount,d.GenerateType GenerateType2,e.NOTE GenerateType2Descr, " +
					"d.Amount Amount2,a.Remarks,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,g.No,h.Code,a.CarryFloor " +
					"from ( " +
					"	select * " +
					"	from tPurchaseFeeDetail " +
					"	where PUNo=? and GenerateType='1' " +
					")a " +
					"left join tSupplFeeType b on b.code=a.SupplFeeType " +
					"left join tXTDM c on c.ID='PuFeeGenType' and c.CBM=a.GenerateType " +
					"full join ( " +
					"	select * " +
					"	from tPurchaseFeeDetail " +
					"	where PUNo=? and GenerateType='2' " +
					") d on d.SupplFeeType=a.SupplFeeType " +
					"left join tXTDM e on e.ID='PuFeeGenType' and e.CBM=d.GenerateType " +
					"left join tSupplFeeType f on f.code=d.SupplFeeType " +
					"left join tPurchase g on g.No=d.PUNo " +
					"left join tCustomer h on h.Code=g.CustCode " +
					"order by a.SupplFeeType ";
		return this.findPageBySql(page, sql, new Object[]{purchase.getNo(), purchase.getNo()});
	}
	/**
	 * 系统计算其它费用
	 * @author	created by zb
	 * @date	2019-5-29--下午5:14:13
	 * @param purchase
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result genSupplOtherFee(Purchase purchase) {
		Assert.notNull(purchase);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call PGenSupplOtherFee(?,?,?,?)}");
			call.setString(1, purchase.getNo());
			call.setString(2, purchase.getLastUpdatedBy());
			call.registerOutParameter(3, Types.INTEGER);
			call.registerOutParameter(4, Types.NVARCHAR);
			call.execute();
			result.setCode(String.valueOf(call.getInt(3)));
			result.setInfo(call.getString(4));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
	}

	/**
	 * 是否供应商结算已审核
	 * 
	 * @param purchaseNo
	 * @return
	 */
	public List<Map<String, Object>> isSupplierChecked(String purchaseNo) {
		String sql = "select 1 from tPurchase a " +
					 "left join tSplCheckOut b on a.CheckOutNo = b.No " + 
					 "where b.status='2' and a.No=?";
		return this.findBySql(sql, new Object[] {purchaseNo});
	}

}

