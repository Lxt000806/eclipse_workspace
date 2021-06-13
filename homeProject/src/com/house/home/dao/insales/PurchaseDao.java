package com.house.home.dao.insales;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Timestamp;
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
import com.house.home.entity.insales.PurchaseFee;

@SuppressWarnings("serial")
@Repository
public class PurchaseDao extends BaseDao {
	
	/**
	 * 采购跟踪 
	 * @param page
	 * @param purchase
	 * @return
	 * 
	 **/
	public Page<Map<String, Object>> findPurchGZPageBySql(
			Page<Map<String, Object>> page, Purchase purchase) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select * from (select ea.namechi applymanDescr,f.maxPk,a.No,a.Status,s1.Note StatusDescr,a.Date,a.Type ,a.ItemType1,it.Descr ItemType1Descr, s2.Note TypeDescr," 
				+" a.WHCode,w.Desc1 WareHouse,a.Supplier,sp.Descr SupplDescr,sp.Phone1,a.Remarks,a.ApplyMan,a.IsCheckOut,i.Note IsCheckOutDescr,"
				+" a.CheckOutNo,a.CustCode,a.DelivType,c.Descr CustDescr,c.Address,p.NOTE DelivTypeDescr,a.LastUpdate,a.LastUpdatedBy,a.Expired,"
				+" a.ActionLog,a.ProjectMan,a.Phone,a.DelivWay,IA.IsService,s3.Note IsServiceDescr,x1.NOTE isCheckOutStatusDescr,a.SINo,a.Amount,"
				+" a.FirstAmount,a.SecondAmount,a.RemainAmount,a.OtherCost,a.OtherCostAdj,"
				+" case when a.Type='R' then -a.Amount else a.Amount end AmountShow,OldPUNo,"
				+" case when a.Type='R' then -a.FirstAmount else a.FirstAmount end FirstAmountShow,"
				+" case when a.Type='R' then -a.SecondAmount else a.SecondAmount end SecondAmountShow,"
				+" case when a.Type='R' then -a.RemainAmount else a.RemainAmount end RemainAmountShow,"
				+" case when a.Type='R' then -a.OtherCost else a.OtherCost end OtherCostShow,"
				+" case when a.Type='R' then -a.OtherCostAdj else a.OtherCostAdj end OtherCostAdjShow,a.ArriveDate,"
				+" DATEDIFF(day,getDate(),a.ArriveDate) ArriveDiff,a.ArriveStatus,x2.Note ArriveStatusDescr,a.ArriveRemark,c.DocumentNo,a.ProjectOtherCost,"
				+" j.AdvancePayDate "
				+" from tPurchase a "
				+" left outer join tCustomer c on a.CustCode=c.Code  "
				+" left outer join tSupplier sp on a.Supplier=sp.Code  "
				+" left outer join tWareHouse w on a.WHCode=w.Code  "
				+" left outer join tItemApp ia on ia.PUNo=a.no  "
				+" left outer join tSplCheckOut sco on sco.no=a.CheckOutNo  "
				+" left outer join tXTDM s3 on s3.IBM=ia.IsService and s3.ID='YESNO'  "
				+" left outer join tXTDM s1 on a.Status=s1.CBM and s1.ID='PURCHSTATUS'  "
				+" left outer join tXTDM s2 on a.Type=s2.CBM and s2.ID='PURCHTYPE'  "
				+" left outer join tXTDM p on a.DelivType=p.CBM and p.ID='PURCHDELIVTYPE'  "
				+" left outer join tXTDM i on a.IsCheckOut=i.CBM and i.ID='YESNO'  "
				+" left outer join tXTDM x1 on sco.Status=x1.CBM and x1.id='SPLCKOTSTATUS' "
				+" left outer join tXTDM x2 on a.ArriveStatus=x2.CBM and x2.id='TPURARVSTATUS' " 
				+" left join (select MAX(PK) maxPk,puno from tPurchaseDelay group by puno ) f on f.PUNo=a.No "	
				+" left outer join tItemType1 it on a.ItemType1=it.Code  "
				+" left join temployee ea on ea.number = a.ApplyMan "
				+" outer apply ( "
				+"     select min(in_a.PayDate) AdvancePayDate "
				+"     from tSupplierPrepay in_a "
				+"     left join tSupplierPrepayDetail in_b on in_b.No = in_a.No "
				+"     where in_a.Type = '2' and in_b.PUNo = a.No "
				+" ) j "
				+" where 1=1" ;
	
		if (StringUtils.isNotBlank(purchase.getNo())) {
			sql += " and a.No=? ";
			list.add(purchase.getNo());
		}
		
		if (StringUtils.isNotBlank(purchase.getRemainTime())) {
			sql += " and DATEDIFF(Day,GETDATE(),a.ArriveDate) <= ? ";
			list.add(purchase.getRemainTime());
		}
		

		if (StringUtils.isNotBlank(purchase.getStatus())) {
			sql += " and a.Status in " + "('"+purchase.getStatus().replaceAll(",", "','")+"')";
		}
		
		if (StringUtils.isNotBlank(purchase.getArriveStatus())) {
			sql += " and a.ArriveStatus in " + "('"+purchase.getArriveStatus().replaceAll(",", "','")+"')";
		}

	if (StringUtils.isNotBlank(purchase.getSplStatusDescr())) {
		sql += " and sc.Status in " + "('"+purchase.getSplStatusDescr().replace(",", "','" )+ "')";
	}

	if (purchase.getDateFrom1() != null) {
		sql += " and a.Date>= ? ";
		list.add(purchase.getDateFrom1());
	}
	if (purchase.getDateTo1() != null) {
		sql += " and a.Date<= ? ";
		list.add(purchase.getDateTo1());
	}
	if (StringUtils.isNotBlank(purchase.getType())) {
		sql += " and a.Type=? ";
		list.add(purchase.getType());
	}
	if (StringUtils.isNotBlank(purchase.getWhcode())) {
		sql += " and a.WHCode=? ";
		list.add(purchase.getWhcode());
	}
	if (StringUtils.isNotBlank(purchase.getSupplier())) {
		sql += " and a.Supplier=? ";
		list.add(purchase.getSupplier());
	}
	if (purchase.getAmount() != null) {
		sql += " and a.Amount=? ";
		list.add(purchase.getAmount());
	}
	if (StringUtils.isNotBlank(purchase.getRemarks())) {
		sql += " and a.Remarks like ? ";
		list.add("%" + purchase.getRemarks() + "%");
	}
	if (StringUtils.isNotBlank(purchase.getIsCheckOut())) {
		sql += " and a.IsCheckOut=? ";
		list.add(purchase.getIsCheckOut());
	}
	
	if (StringUtils.isNotBlank(purchase.getCheckOutNo())) {
		sql += " and a.CheckOutNo=? ";
		list.add(purchase.getCheckOutNo());
	}
	if (purchase.getDateFrom() != null) {
		sql += " and a.LastUpdate>= ? ";
		list.add(purchase.getDateFrom());
	}
	if (purchase.getDateTo() != null) {
		sql += " and a.LastUpdate<= ? ";
		list.add(purchase.getDateTo());
	}
	
	if (StringUtils.isNotBlank(purchase.getLastUpdatedBy())) {
		sql += " and a.LastUpdatedBy=? ";
		list.add(purchase.getLastUpdatedBy());
	}
	if (StringUtils.isBlank(purchase.getExpired())
			|| "F".equals(purchase.getExpired())) {
		sql += " and a.Expired='F' ";
	}
	if (StringUtils.isNotBlank(purchase.getActionLog())) {
		sql += " and a.ActionLog=? ";
		list.add(purchase.getActionLog());
	}
	if (StringUtils.isNotBlank(purchase.getDelivType())) {
		sql += " and a.DelivType=? ";
		list.add(purchase.getDelivType());
	}
	if (StringUtils.isNotBlank(purchase.getCustCode())) {
		sql += " and a.CustCode=? ";
		list.add(purchase.getCustCode());
	}
	if (StringUtils.isNotBlank(purchase.getProjectMan())) {
		sql += " and a.ProjectMan=? ";
		list.add(purchase.getProjectMan());
	}
	if (StringUtils.isNotBlank(purchase.getPhone())) {
		sql += " and a.Phone=? ";
		list.add(purchase.getPhone());
	}
	if (StringUtils.isNotBlank(purchase.getDelivWay())) {
		sql += " and a.DelivWay=? ";
		list.add(purchase.getDelivWay());
	}
	if (StringUtils.isNotBlank(purchase.getItemType1())) {
		sql += " and a.ItemType1=? ";
		list.add(purchase.getItemType1());
	}
	if (StringUtils.isNotBlank(purchase.getItemType2())) {
		sql += "  and exists(select 1 from tPurchaseDetail t left outer join tItem ti on t.ITCode=ti.Code where t.PUNo=a.No and ti.ItemType2=?) ";
		list.add(purchase.getItemType2());
	}
	if (purchase.getFirstAmount() != null) {
		sql += " and a.FirstAmount=? ";
		list.add(purchase.getFirstAmount());
	}
	if (purchase.getSecondAmount() != null) {
		sql += " and a.SecondAmount=? ";
		list.add(purchase.getSecondAmount());
	}
	if (purchase.getRemainAmount() != null) {
		sql += " and a.RemainAmount=? ";
		list.add(purchase.getRemainAmount());
	}
	if (purchase.getOtherCost() != null) {
		sql += " and a.OtherCost=? ";
		list.add(purchase.getOtherCost());
	}
	if (purchase.getOtherCostAdj() != null) {
		sql += " and a.OtherCostAdj=? ";
		list.add(purchase.getOtherCostAdj());
	}
	if (purchase.getCheckSeq() != null) {
		sql += " and a.checkSeq=? ";
		list.add(purchase.getCheckSeq());
	}
	if (purchase.getDateFrom() != null) {
		sql += " and a.ArriveDate>= ? ";
		list.add(purchase.getDateFrom());
	}
	if (purchase.getDateTo() != null) {
		sql += " and a.ArriveDate<= ? ";
		list.add(purchase.getDateTo());
		
	}if (purchase.getArriveDateFrom()!= null) {
		sql += " and a.ArriveDate>= ? ";
		list.add(purchase.getArriveDateFrom());
	}
	if (purchase.getArriveDateTo() != null) {
		sql += " and a.ArriveDate<= ? ";
		list.add(purchase.getArriveDateTo());
	}
	
	if (StringUtils.isNotBlank(purchase.getApplyMan())) {
		sql += " and a.ApplyMan=? ";
		list.add(purchase.getApplyMan());
	}
	if (StringUtils.isNotBlank(purchase.getPayRemark())) {
		sql += " and a.PayRemark=? ";
		list.add(purchase.getPayRemark());
	}
	if (StringUtils.isNotBlank(purchase.getSino())) {
		sql += " and a.SINo=? ";
		list.add(purchase.getSino());
	}
	
	if (StringUtils.isNotBlank(purchase.getAddress())) {
		sql += " and c.Address like ? ";
		list.add("%" + purchase.getAddress() + "%");
	}
	
	if (StringUtils.isNotBlank(purchase.getArriveRemark())) {
		sql += " and a.ArriveRemark=? ";
		list.add(purchase.getArriveRemark());
	}
	if (purchase.getProjectOtherCost() != null) {
		sql += " and a.ProjectOtherCost=? ";
		list.add(purchase.getProjectOtherCost());
	}
	if (purchase.getOverCost() != null) {
		sql += " and a.OverCost=? ";
		list.add(purchase.getOverCost());
	}
	if (purchase.getProjectAmount() != null) {
		sql += " and a.ProjectAmount=? ";
		list.add(purchase.getProjectAmount());
	}
	
	if (StringUtils.isNotBlank(page.getPageOrderBy())) {
		sql += ") a order by a." + page.getPageOrderBy() + " "
				+ page.getPageOrder();
	} else {
		sql += ") a order by a.ArriveDiff ";
	}

	return this.findPageBySql(page, sql, list.toArray());
}
	
	/**
	 * 明细单查询分页信息
	 * 
	 * @param page
	 * @param purchase
	 * @return
	 * dd
	 */
	public Page<Map<String, Object>> findDetailPageBySql(
			Page<Map<String, Object>> page, Purchase purchase) {
		List<Object> list = new ArrayList<Object>();		
		String sql = "select * from (SELECT a.No,a.SINo,p.itcode,a.IsCheckOut,a.checkOutNo,a.ConfirmDate,a.ConfirmCZY,a.Supplier,a.Status,(case when a.type='R' then -1*p.amount else p.amount end)amount " +
				"	 ,a.otherCost,a.otherCostAdj,a.remainamount,a.firstAmount," +
				 	 " a.secondamount,a.projectamount,a.amount+a.otherCost+a.otherCostAdj AllAmount,s1.Note statusdescr,a.payremark, " +
					 "a.Type ,s2.Note typedescr,s3.Note itemtypedescr,s4.Note splStatusDescr,s5.Note yesno1, a.Date,a.ArriveDate,a.WHCode,a.delivType ,b.Desc1 WHCodeDescr ,a.CustCode,a.itemtype1, " +
					 "d.descr SupDescr,a.ApplyMan,c.NameChi applyManDescr,p.Remarks,sc.Status scStatusDescr,a.LastUpdatedBy,a.lastupdate, " +
					 " ct.Descr custDescr ,ct.DocumentNo documentno, ct.Address ,i.Descr itDescr,a.expired,a.actionlog " +
					 " ,s8.Note delivTypedescr,p.UnitPrice,(case when a.type='R' then -1*p.qtycal else p.qtycal end)qtycal,(case when a.type='R' then -1*p.arrivQty else p.arrivQty end)arrivQty  " +
					 " FROM tPurchase a "
					 + " left join tSplCheckOut sc on a.checkOutNo=sc.No "
					 + "LEFT JOIN tWareHouse b ON a.WHCode=b.Code "
					 + "LEFT JOIN tEmployee  c ON a.ApplyMan=c.Number "
					 + "LEFT JOIN tSupplier  d ON a.Supplier=d.Code "
					 + " left join tCustomer ct on ct.Code= a.CustCode "
					 + "left join tPurchaseDetail  p on p.PUNo= a.No "
					 +" left join tItem i on i.Code= p.itcode "
					 + "left outer join tXTDM s1 on a.Status=s1.CBM and s1.ID='PURCHSTATUS'"
					 + "left outer join tXTDM s2 on a.Type = s2.CBM and s2.ID='PURCHTYPE'	"
					 + "left outer join tXTDM s3 on a.itemtype1 = s3.CBM and s3.ID='ITEMRIGHT'	"
					 + " left outer join tXTDM s4 on s4.CBM=sc.Status and s4.id='SPLCKOTSTATUS' "
					 + " left outer join tXTDM s5 on s5.CBM=a.IsCheckOut and s5.id='YESNO' "
					 + " left outer join tXTDM s8 on s8.CBM=a.delivType  and s8.id='PURCHDELIVTYPE' "
					 + "where 1=1";
		
		if (StringUtils.isNotBlank(purchase.getNo())) {
			sql += " and a.No=? ";
			list.add(purchase.getNo());
		}
		
		if (StringUtils.isNotBlank(purchase.getStatus())) {
			sql += " and a.Status in " + "('"+purchase.getStatus().replaceAll(",", "','")+"')";
		}

		if (StringUtils.isNotBlank(purchase.getSplStatusDescr())) {
			sql += " and sc.Status in " + "('"+purchase.getSplStatusDescr().replace(",", "','" )+ "')";
		}

		if (purchase.getDateFrom1() != null) {
			sql += " and a.Date>= ? ";
			list.add(purchase.getDateFrom1());
		}
		
		if (StringUtils.isNotBlank(purchase.getItCode())) {
			sql += " and p.itcode= ? ";
			list.add(purchase.getItCode());
		}
		
		if (purchase.getDateTo1() != null) {
			sql += " and a.Date<= ? ";
			list.add(purchase.getDateTo1());
		}
		if (StringUtils.isNotBlank(purchase.getType())) {
			sql += " and a.Type=? ";
			list.add(purchase.getType());
		}
		if (StringUtils.isNotBlank(purchase.getWhcode())) {
			sql += " and a.WHCode=? ";
			list.add(purchase.getWhcode());
		}
		if (StringUtils.isNotBlank(purchase.getSupplier())) {
			sql += " and a.Supplier=? ";
			list.add(purchase.getSupplier());
		}
		if (purchase.getAmount() != null) {
			sql += " and a.Amount=? ";
			list.add(purchase.getAmount());
		}
		if (StringUtils.isNotBlank(purchase.getRemarks())) {
			sql += " and a.Remarks like ? ";
			list.add("%" + purchase.getRemarks() + "%");
		}
		if (StringUtils.isNotBlank(purchase.getIsCheckOut())) {
			sql += " and a.IsCheckOut=? ";
			list.add(purchase.getIsCheckOut());
		}
		
		if (StringUtils.isNotBlank(purchase.getCheckOutNo())) {
			sql += " and a.CheckOutNo=? ";
			list.add(purchase.getCheckOutNo());
		}
		if (purchase.getDateFrom() != null) {
			sql += " and a.LastUpdate>= ? ";
			list.add(purchase.getDateFrom());
		}
		if (purchase.getDateTo() != null) {
			sql += " and a.LastUpdate<= ? ";
			list.add(purchase.getDateTo());
		}
		
		if (StringUtils.isNotBlank(purchase.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(purchase.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(purchase.getExpired())
				|| "F".equals(purchase.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(purchase.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(purchase.getActionLog());
		}
		if (StringUtils.isNotBlank(purchase.getDelivType())) {
			sql += " and a.DelivType=? ";
			list.add(purchase.getDelivType());
		}
		if (StringUtils.isNotBlank(purchase.getCustCode())) {
			sql += " and a.CustCode=? ";
			list.add(purchase.getCustCode());
		}
		if (StringUtils.isNotBlank(purchase.getProjectMan())) {
			sql += " and a.ProjectMan=? ";
			list.add(purchase.getProjectMan());
		}
		if (StringUtils.isNotBlank(purchase.getPhone())) {
			sql += " and a.Phone=? ";
			list.add(purchase.getPhone());
		}
		if (StringUtils.isNotBlank(purchase.getDelivWay())) {
			sql += " and a.DelivWay=? ";
			list.add(purchase.getDelivWay());
		}
		if (StringUtils.isNotBlank(purchase.getItemType1())) {
			sql += " and a.ItemType1=? ";
			list.add(purchase.getItemType1());
		}
		if (StringUtils.isNotBlank(purchase.getItemType2())) {
			sql += " and i.ItemType2=? ";
			list.add(purchase.getItemType2());
		}
		if (purchase.getFirstAmount() != null) {
			sql += " and a.FirstAmount=? ";
			list.add(purchase.getFirstAmount());
		}
		if (purchase.getSecondAmount() != null) {
			sql += " and a.SecondAmount=? ";
			list.add(purchase.getSecondAmount());
		}
		if (purchase.getRemainAmount() != null) {
			sql += " and a.RemainAmount=? ";
			list.add(purchase.getRemainAmount());
		}
		if (purchase.getOtherCost() != null) {
			sql += " and a.OtherCost=? ";
			list.add(purchase.getOtherCost());
		}
		if (purchase.getOtherCostAdj() != null) {
			sql += " and a.OtherCostAdj=? ";
			list.add(purchase.getOtherCostAdj());
		}
		if (purchase.getCheckSeq() != null) {
			sql += " and a.checkSeq=? ";
			list.add(purchase.getCheckSeq());
		}
		if (purchase.getDateFrom() != null) {
			sql += " and a.ArriveDate>= ? ";
			list.add(purchase.getDateFrom());
		}
		if (purchase.getDateTo() != null) {
			sql += " and a.ArriveDate<= ? ";
			list.add(purchase.getDateTo());
			
		}if (purchase.getArriveDateFrom()!= null) {
			sql += " and a.ArriveDate>= ? ";
			list.add(purchase.getArriveDateFrom());
		}
		if (purchase.getArriveDateTo() != null) {
			sql += " and a.ArriveDate<= ? ";
			list.add(purchase.getArriveDateTo());
		}
		
		if (StringUtils.isNotBlank(purchase.getApplyMan())) {
			sql += " and a.ApplyMan=? ";
			list.add(purchase.getApplyMan());
		}
		if (StringUtils.isNotBlank(purchase.getPayRemark())) {
			sql += " and a.PayRemark=? ";
			list.add(purchase.getPayRemark());
		}
		if (StringUtils.isNotBlank(purchase.getSino())) {
			sql += " and a.SINo=? ";
			list.add(purchase.getSino());
		}
		
		if (StringUtils.isNotBlank(purchase.getAddress())) {
			sql += " and ct.Address like ? ";
			list.add("%" + purchase.getAddress() + "%");
		}
		
		
		if (StringUtils.isNotBlank(purchase.getArriveStatus())) {
			sql += " and a.ArriveStatus=? ";
			list.add(purchase.getArriveStatus());
		}
		if (StringUtils.isNotBlank(purchase.getArriveRemark())) {
			sql += " and a.ArriveRemark=? ";
			list.add(purchase.getArriveRemark());
		}
		if (purchase.getProjectOtherCost() != null) {
			sql += " and a.ProjectOtherCost=? ";
			list.add(purchase.getProjectOtherCost());
		}
		if (purchase.getOverCost() != null) {
			sql += " and a.OverCost=? ";
			list.add(purchase.getOverCost());
		}
		if (purchase.getProjectAmount() != null) {
			sql += " and a.ProjectAmount=? ";
			list.add(purchase.getProjectAmount());
		}
		if(StringUtils.isNotBlank(purchase.getItemRight())){
			sql += " and a.itemType1 in " + "('"+purchase.getItemRight().replace(",", "','" )+ "')";
		}
		if (StringUtils.isNotBlank(purchase.getDepartment2())) {
			sql += " and exists(select 1 from temployee where Department2 = ? " +
					" and (Number = i.buyer1 or Number=i.Buyer2) ) ";
			list.add(purchase.getDepartment2());
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		} else {
			sql += ") a order by a.lastUpdate desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * Purchase分页信息
	 * 
	 * @param page
	 * @param purchase
	 * @return
	 * dd
	 */
	public Page<Map<String, Object>> findPageBySql1(
			Page<Map<String, Object>> page, Purchase purchase) {
		List<Object> list = new ArrayList<Object>();		
		String sql = "select * from (SELECT a.No,a.ConfirmDate,a.ConfirmCZY,a.Supplier,a.Status,a.amount,a.otherCost,a.otherCostAdj,a.firstAmount,a.amount+a.otherCost+a.otherCostAdj AllAmount,s1.Note statusdescr, " +
					 "a.Type ,s2.Note typedescr,s3.Note itemtypedescr, a.Date,a.ArriveDate,a.WHCode,a.delivType ,b.Desc1 WHCodeDescr ,a.CustCode,a.itemtype1, " +
					 "d.descr SupDescr,a.ApplyMan,p.sumArrivQty,c.NameChi applyManDescr,a.Remarks,a.LastUpdatedBy FROM tPurchase a "
					 + "LEFT JOIN tWareHouse b ON a.WHCode=b.Code "
					 + "LEFT JOIN tEmployee  c ON a.ApplyMan=c.Number "
					 + "LEFT JOIN tSupplier  d ON a.Supplier=d.Code "
					 + "LEFT JOIN (select PUNo,isnull(sum(ArrivQty),0) sumArrivQty from tPurchaseDetail where 1=1 group by PUNo) p on p.PUNo=a.No "
					 + "left outer join tXTDM s1 on a.Status=s1.CBM and s1.ID='PURCHSTATUS'"
					 + "left outer join tXTDM s2 on a.Type = s2.CBM and s2.ID='PURCHTYPE'	"
					 + "left outer join tXTDM s3 on a.itemtype1 = s3.CBM and s3.ID='ITEMRIGHT'	";
					
		if(StringUtils.isNotBlank(purchase.getCzybh())){
			sql += "  left join tWareHouseOperater wo on b.code=wo.whCode and wo.czybh=? where 1=1 and wo.whCode is not null  ";
			list.add(purchase.getCzybh().trim());
		}else{
			sql+=" where 1=1 ";
		}

		if (StringUtils.isNotBlank(purchase.getNo())) {
			sql += " and a.No=? ";
			list.add(purchase.getNo());
		}
		
		if (StringUtils.isNotBlank(purchase.getStatus())) {
			sql += " and a.Status in " + "('"+purchase.getStatus().replaceAll(",", "','")+"')";
		}
		
		if (purchase.getDateFrom1() != null) {
			sql += " and a.Date>= ? ";
			list.add(purchase.getDateFrom1());
		}
		if (purchase.getDateTo1() != null) {
			sql += " and a.Date<= ? ";
			list.add(purchase.getDateTo1());
		}
		if (StringUtils.isNotBlank(purchase.getType())) {
			sql += " and a.Type=? ";
			list.add(purchase.getType());
		}
		if (StringUtils.isNotBlank(purchase.getWhcode())) {
			sql += " and a.WHCode=? ";
			list.add(purchase.getWhcode());
		}
		if (StringUtils.isNotBlank(purchase.getSupplier())) {
			sql += " and a.Supplier=? ";
			list.add(purchase.getSupplier());
		}
		if (purchase.getAmount() != null) {
			sql += " and a.Amount=? ";
			list.add(purchase.getAmount());
		}
		if (StringUtils.isNotBlank(purchase.getRemarks())) {
			sql += " and a.Remarks=? ";
			list.add(purchase.getRemarks());
		}
		if (StringUtils.isNotBlank(purchase.getIsCheckOut())) {
			sql += " and a.IsCheckOut=? ";
			list.add(purchase.getIsCheckOut());
		}
		if (StringUtils.isNotBlank(purchase.getCheckOutNo())) {
			sql += " and a.CheckOutNo=? ";
			list.add(purchase.getCheckOutNo());
		}
		if (purchase.getDateFrom() != null) {
			sql += " and a.LastUpdate>= ? ";
			list.add(purchase.getDateFrom());
		}
		if (purchase.getDateTo() != null) {
			sql += " and a.LastUpdate<= ? ";
			list.add(purchase.getDateTo());
		}
		
		if (StringUtils.isNotBlank(purchase.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(purchase.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(purchase.getExpired())
				|| "F".equals(purchase.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(purchase.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(purchase.getActionLog());
		}
		if (StringUtils.isNotBlank(purchase.getDelivType())) {
			sql += " and a.DelivType=? ";
			list.add(purchase.getDelivType());
		}
		if (StringUtils.isNotBlank(purchase.getCustCode())) {
			sql += " and a.CustCode=? ";
			list.add(purchase.getCustCode());
		}
		if (StringUtils.isNotBlank(purchase.getProjectMan())) {
			sql += " and a.ProjectMan=? ";
			list.add(purchase.getProjectMan());
		}
		if (StringUtils.isNotBlank(purchase.getPhone())) {
			sql += " and a.Phone=? ";
			list.add(purchase.getPhone());
		}
		if (StringUtils.isNotBlank(purchase.getDelivWay())) {
			sql += " and a.DelivWay=? ";
			list.add(purchase.getDelivWay());
		}
		if (StringUtils.isNotBlank(purchase.getItemType1())) {
			sql += " and a.ItemType1=? ";
			list.add(purchase.getItemType1());
		}
		if (purchase.getFirstAmount() != null) {
			sql += " and a.FirstAmount=? ";
			list.add(purchase.getFirstAmount());
		}
		if (purchase.getSecondAmount() != null) {
			sql += " and a.SecondAmount=? ";
			list.add(purchase.getSecondAmount());
		}
		if (purchase.getRemainAmount() != null) {
			sql += " and a.RemainAmount=? ";
			list.add(purchase.getRemainAmount());
		}
		if (purchase.getOtherCost() != null) {
			sql += " and a.OtherCost=? ";
			list.add(purchase.getOtherCost());
		}
		if (purchase.getOtherCostAdj() != null) {
			sql += " and a.OtherCostAdj=? ";
			list.add(purchase.getOtherCostAdj());
		}
		if (purchase.getCheckSeq() != null) {
			sql += " and a.checkSeq=? ";
			list.add(purchase.getCheckSeq());
		}
		if (purchase.getDateFrom() != null) {
			sql += " and a.ArriveDate>= ? ";
			list.add(purchase.getDateFrom());
		}
		if (purchase.getDateTo() != null) {
			sql += " and a.ArriveDate<= ? ";
			list.add(purchase.getDateTo());
			
		}if (purchase.getArriveDateFrom()!= null) {
			sql += " and a.ArriveDate>= ? ";
			list.add(purchase.getArriveDateFrom());
		}
		if (purchase.getArriveDateTo() != null) {
			sql += " and a.ArriveDate<= ? ";
			list.add(purchase.getArriveDateTo());
		}
		
		if (StringUtils.isNotBlank(purchase.getApplyMan())) {
			sql += " and a.ApplyMan=? ";
			list.add(purchase.getApplyMan());
		}
		if (StringUtils.isNotBlank(purchase.getPayRemark())) {
			sql += " and a.PayRemark=? ";
			list.add(purchase.getPayRemark());
		}
		if (StringUtils.isNotBlank(purchase.getSino())) {
			sql += " and a.SINo=? ";
			list.add(purchase.getSino());
		}
		if (StringUtils.isNotBlank(purchase.getArriveStatus())) {
			sql += " and a.ArriveStatus=? ";
			list.add(purchase.getArriveStatus());
		}
		if (StringUtils.isNotBlank(purchase.getArriveRemark())) {
			sql += " and a.ArriveRemark=? ";
			list.add(purchase.getArriveRemark());
		}
		if (purchase.getProjectOtherCost() != null) {
			sql += " and a.ProjectOtherCost=? ";
			list.add(purchase.getProjectOtherCost());
		}
		if (purchase.getOverCost() != null) {
			sql += " and a.OverCost=? ";
			list.add(purchase.getOverCost());
		}
		if (purchase.getProjectAmount() != null) {
			sql += " and a.ProjectAmount=? ";
			list.add(purchase.getProjectAmount());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.No";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * Purchase分页信息
	 * 
	 * @param page
	 * @param purchase
	 * @return
	 * dd
	 */
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, Purchase purchase) {
		List<Object> list = new ArrayList<Object>();		
		String sql = "select * from (SELECT f.Desc1 custTypeDescr,sup.no payNo,sup.supStatusDescr,sup.payDate,a.No,a.SINo,a.oldPUNo,a.IsCheckOut,a.checkOutNo,a.ConfirmDate,a.ConfirmCZY,a.Supplier,a.Status,case when a.type='s'then a.Amount else -a.amount end amount,a.otherCost,a.otherCostAdj,a.remainamount,a.firstAmount," +
				 	 " a.secondamount,a.projectamount,a.amount+a.otherCost+a.otherCostAdj AllAmount,s1.Note statusdescr,a.payremark, " +
					 "a.Type ,s2.Note typedescr,s3.Note itemtypedescr,s4.Note splStatusDescr,s5.Note yesno1,s6.Note yesno2,s7.Note yesno3 , a.Date,a.ArriveDate,a.WHCode,a.delivType ,b.Desc1 WHCodeDescr ,a.CustCode,a.itemtype1, " +
					 "d.descr SupDescr,a.ApplyMan,c.NameChi applyManDescr,a.Remarks,sc.Status scStatusDescr,a.LastUpdatedBy,a.lastupdate,a.isAppItem, " +
					 " i1.IsSetItem,i1.IsService,ct.Descr custDescr ,p.sumArrivQty,ct.SaleType,ct.DocumentNo documentno , ct.Address ,a.expired,a.actionlog " +
					 " ,s8.Note delivTypedescr ,d.Phone1,case when a.Type = 'S' then a.Amount else -a.Amount end + a.OtherCost + a.OtherCostAdj SumAmount,a.AppItemDate" +					 
					 "  FROM tPurchase a "
					 + "LEFT JOIN (select PUNo,isnull(sum(ArrivQty),0) sumArrivQty from tPurchaseDetail where 1=1 group by PUNo) p on p.PUNo=a.No "
					 + " left join tSplCheckOut sc on a.checkOutNo=sc.No "
					 + "LEFT JOIN tWareHouse b ON a.WHCode=b.Code "
					 + "LEFT JOIN tEmployee  c ON a.ApplyMan=c.Number "
					 
					 + "LEFT JOIN tSupplier  d ON a.Supplier=d.Code "
					 + " left join tCustomer ct on ct.Code= a.CustCode "
					 + "left join tItemApp  i1 on i1.PUNo= a.No "
					 + "left outer join tXTDM s1 on a.Status=s1.CBM and s1.ID='PURCHSTATUS'"
					 + "left outer join tXTDM s2 on a.Type = s2.CBM and s2.ID='PURCHTYPE'	"
					 + "left outer join tXTDM s3 on a.itemtype1 = s3.CBM and s3.ID='ITEMRIGHT'	"
					 + " left outer join tXTDM s4 on s4.CBM=sc.Status and s4.id='SPLCKOTSTATUS' "
					 + " left outer join tXTDM s5 on s5.CBM=a.IsCheckOut and s5.id='YESNO' "
					 + " left outer join tXTDM s6 on s6.CBM=i1.IsService and s6.id='YESNO' "
					 + " left outer join tXTDM s7 on s7.CBM=i1.IsSetItem and s7.id='YESNO' "
					 + " left outer join tXTDM s8 on s8.CBM=a.delivType  and s8.id='PURCHDELIVTYPE' " +
					 " left join (select a.puno,c.no,x1.NOTE supStatusDescr,c.PayDate from (" +
					 " select max(PK) spdpk,puno  from tSupplierPayDetail group by PUNo ) a " +
					 " left join dbo.tSupplierPayDetail b on a.spdpk=b.PK " +
					 " left join dbo.tSupplierPay c on c.no =b.No" +
					 " left join txtdm x1 on x1.cbm=c.Status and x1.id='SPLPAYSTATUS')sup on sup.puno=a.no " +
					 " left join tCustType f on f.Code = ct.CustType "
					 + " where 1=1 and a.No<>'' and a.ItemType1<>'' " +
					 " and a.itemType1 in " + "('"+purchase.getItemRight().replace(",", "','" )+ "')";

		if(StringUtils.isNotBlank(purchase.getItCode())){
			sql+=" and a.No in(select a.PUNo from  tPurchaseDetail a  where a.ITCode= ? )";
			list.add(purchase.getItCode());

		}//
		
		if (StringUtils.isNotBlank(purchase.getNo())) {
			sql += " and a.No=? ";
			list.add(purchase.getNo());
		}
		
		if("1".equals(purchase.getSaleType())){
			sql+="and exists (select 1 from tItemApp ia where ia.PUNo=a.No) ";
		}else if("2".equals(purchase.getSaleType())) {
			sql+="and not exists (select 1 from tItemApp ia where ia.PUNo=a.No) ";
		}
		
		if (StringUtils.isNotBlank(purchase.getStatus())) {
			sql += " and a.Status in " + "('"+purchase.getStatus().replaceAll(",", "','")+"')";
		}

		if (StringUtils.isNotBlank(purchase.getSplStatusDescr())) {
			sql += " and sc.Status in " + "('"+purchase.getSplStatusDescr().replace(",", "','" )+ "')";
		}

		if (purchase.getDateFrom1() != null) {
			sql += " and a.Date>= ? ";
			list.add(purchase.getDateFrom1());
		}
		if (purchase.getDateTo1() != null) {
			sql += " and a.Date<DATEADD(d,1,?) ";
			list.add(purchase.getDateTo1());
		}
		if (StringUtils.isNotBlank(purchase.getType())) {
			sql += " and a.Type=? ";
			list.add(purchase.getType());
		}
		if (StringUtils.isNotBlank(purchase.getWhcode())) {
			sql += " and a.WHCode=? ";
			list.add(purchase.getWhcode());
		}
		if (StringUtils.isNotBlank(purchase.getSupplier())) {
			sql += " and a.Supplier=? ";
			list.add(purchase.getSupplier());
		}
		if (purchase.getAmount() != null) {
			sql += " and a.Amount=? ";
			list.add(purchase.getAmount());
		}
		if (StringUtils.isNotBlank(purchase.getRemarks())) {
			sql += " and a.Remarks like ? ";
			list.add("%" + purchase.getRemarks() + "%");
		}
		if (StringUtils.isNotBlank(purchase.getIsCheckOut())) {
			sql += " and a.IsCheckOut=? ";
			list.add(purchase.getIsCheckOut());
		}
		if (StringUtils.isNotBlank(purchase.getIsService())) {
			sql += " and i1.IsService =? ";
			list.add(purchase.getIsService());
		}
		if (StringUtils.isNotBlank(purchase.getCheckOutNo())) {
			sql += " and a.CheckOutNo=? ";
			list.add(purchase.getCheckOutNo());
		}
		if (StringUtils.isNotBlank(purchase.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(purchase.getLastUpdatedBy());
		}
	
		if (StringUtils.isNotBlank(purchase.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(purchase.getActionLog());
		}
		if (StringUtils.isNotBlank(purchase.getDelivType())) {
			sql += " and a.DelivType=? ";
			list.add(purchase.getDelivType());
		}
		if (StringUtils.isNotBlank(purchase.getCustCode())) {
			sql += " and a.CustCode=? ";
			list.add(purchase.getCustCode());
		}
		if (StringUtils.isNotBlank(purchase.getProjectMan())) {
			sql += " and a.ProjectMan=? ";
			list.add(purchase.getProjectMan());
		}
		if (StringUtils.isNotBlank(purchase.getPhone())) {
			sql += " and a.Phone=? ";
			list.add(purchase.getPhone());
		}
		if (StringUtils.isNotBlank(purchase.getDelivWay())) {
			sql += " and a.DelivWay=? ";
			list.add(purchase.getDelivWay());
		}
		if (StringUtils.isNotBlank(purchase.getItemType1())) {
			sql += " and a.ItemType1= ? ";
			list.add(purchase.getItemType1());
		}
		if (StringUtils.isNotBlank(purchase.getItemType2())) {
			sql += "  and exists(select 1 from tPurchaseDetail t " +
					" left outer join tItem ti on t.ITCode=ti.Code where t.PUNo=a.No and ti.ItemType2=? )";
			list.add(purchase.getItemType2());
		}
		if (purchase.getFirstAmount() != null) {
			sql += " and a.FirstAmount=? ";
			list.add(purchase.getFirstAmount());
		}
		if (purchase.getSecondAmount() != null) {
			sql += " and a.SecondAmount=? ";
			list.add(purchase.getSecondAmount());
		}
		if (purchase.getRemainAmount() != null) {
			sql += " and a.RemainAmount=? ";
			list.add(purchase.getRemainAmount());
		}
		if (purchase.getOtherCost() != null) {
			sql += " and a.OtherCost=? ";
			list.add(purchase.getOtherCost());
		}
		if (purchase.getOtherCostAdj() != null) {
			sql += " and a.OtherCostAdj=? ";
			list.add(purchase.getOtherCostAdj());
		}
		if (purchase.getCheckSeq() != null) {
			sql += " and a.checkSeq=? ";
			list.add(purchase.getCheckSeq());
		}
		if(purchase.getDateFrom()!=null){
			sql+=" and a.AppItemDate >= ? ";
			list.add(new Timestamp(
					DateUtil.startOfTheDay( purchase.getDateFrom()).getTime()));
		}
		if(purchase.getDateTo()!=null){
			sql+=" and a.AppItemDate <? ";
			list.add(new Timestamp(
					DateUtil.endOfTheDay( purchase.getDateTo()).getTime()));
		}
		if(purchase.getAppItemDateFrom()!=null){
			sql+=" and a.planOrderDate >= ? ";
			list.add(new Timestamp(
					DateUtil.startOfTheDay( purchase.getAppItemDateFrom()).getTime()));
		}
		if(purchase.getAppItemDateTo()!=null){
			sql+=" and a.planOrderDate <? ";
			list.add(new Timestamp(
					DateUtil.endOfTheDay( purchase.getAppItemDateTo()).getTime()));
		}
		if(StringUtils.isNotBlank(purchase.getAppItemMan())){
			sql+=" and a.appItemMan = ?";
			list.add(purchase.getAppItemMan());
		}
		if (purchase.getConfirmDateFrom()!= null) {
			sql += " and a.ConfirmDate>= ? ";
			list.add(purchase.getConfirmDateFrom());
		}
		if (purchase.getConfirmDateTo() != null) {
			sql += " and a.ConfirmDate< DATEADD(d,1,?) ";
			list.add(purchase.getConfirmDateTo());
		}
		
		if (StringUtils.isNotBlank(purchase.getApplyMan())) {
			sql += " and a.ApplyMan=? ";
			list.add(purchase.getApplyMan());
		}
		if (StringUtils.isNotBlank(purchase.getPayRemark())) {
			sql += " and a.PayRemark=? ";
			list.add(purchase.getPayRemark());
		}
		if (StringUtils.isNotBlank(purchase.getSino())) {
			sql += " and a.SINo=? ";
			list.add(purchase.getSino());
		}
		
		if (StringUtils.isNotBlank(purchase.getAddress())) {
			sql += " and ct.Address like ? ";
			list.add("%" + purchase.getAddress() + "%");
		}
		
		if (StringUtils.isNotBlank(purchase.getArriveStatus())) {
			sql += " and a.ArriveStatus=? ";
			list.add(purchase.getArriveStatus());
		}
		if (StringUtils.isNotBlank(purchase.getArriveRemark())) {
			sql += " and a.ArriveRemark=? ";
			list.add(purchase.getArriveRemark());
		}
		if (purchase.getProjectOtherCost() != null) {
			sql += " and a.ProjectOtherCost=? ";
			list.add(purchase.getProjectOtherCost());
		}
		if (purchase.getOverCost() != null) {
			sql += " and a.OverCost=? ";
			list.add(purchase.getOverCost());
		}
		if (purchase.getProjectAmount() != null) {
			sql += " and a.ProjectAmount=? ";
			list.add(purchase.getProjectAmount());
		}
		
		if (StringUtils.isNotBlank(purchase.getApplyMan())) {
            sql += " and a.ApplyMan = ? ";
            list.add(purchase.getApplyMan());
        }
		
		/*if(StringUtils.isNotBlank(purchase.getItemRight())){
		}*/
		if (StringUtils.isNotBlank(purchase.getIsSupplPrepaySelect())){
			if("RZ".equals(purchase.getItemType1()))
				sql += " and a.Status='OPEN' and not exists (select 1 from tPurchArr where tPurchArr.PUNo=a.No) ";
			else if ("LP".equals(purchase.getItemType1())){
				sql += " and a.Status<>'CONFIRMED' ";	
			}else{
				sql += "  and a.Status<>'CANCEL' ";
			}	
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.LastUpdate desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 采购单结算查看
	 * 
	 * @param page
	 * @param purchase
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql_gysjs(
			Page<Map<String, Object>> page, Purchase purchase) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from (select a.IsCheckOut,a.No,d.Address,a.Supplier,b.Descr SupplierDescr,c.NOTE TypeDescr,a.Date,e.No AppNo,"
				+ "a.OtherCost,a.OtherCostAdj,e.IsService,x1.NOTE IsServiceDescr,a.Remarks,a.PayRemark,a.CheckOutNo,"
				+ "a.ProjectOtherCost,a.Status,a.WHCode,a.Amount,a.CustCode,a.CheckSeq,"
				+ "a.ItemType1,a.OverCost,a.FirstAmount,a.SecondAmount,a.type,d.documentNo,"
				+ "e.IsSetItem,x2.NOTE IsSetItemDescr,a.splAmount,a.projectAmount,a.splAmount+a.OtherCost hjAmount " //+parseFloat(rowData.othercostadj)合计金额计算也不加其他费用调整。 byzjf20190624
				+ ",a.SplStatus,x3.note SplStatusDescr,e.ItemType2,it2.Descr ItemType2Descr "	//增加材料类型2取自领料表 add by zb on 20200424
				+ " from tPurchase a "
				+ " left join tSupplier b on b.Code=a.Supplier "
				+ " left join tXTDM c on c.CBM=a.Type and c.ID='PURCHTYPE' "
				+ " left join tCustomer d on d.Code=a.CustCode "
				+ " left join tItemApp e on a.No=e.PUNo "
				+ " left join tXTDM x1 on x1.IBM=e.IsService and x1.id='YESNO' "
				+ " left join tXTDM x2 on x2.CBM=e.IsSetItem and x2.id='YESNO' "
				+ " left join tXTDM x3 on x3.CBM=a.SplStatus and x3.id='PuSplStatus' "
				+ " left join tItemType2 it2 on it2.Code=e.ItemType2 "
				+ " where 1=1 ";

		if (StringUtils.isNotBlank(purchase.getCheckOutNo())) {
			sql += " and a.CheckOutNo=? ";
			list.add(purchase.getCheckOutNo());
		} else {
			return null;
		}
		if (StringUtils.isNotBlank(purchase.getAddress())) {
			sql += " and d.address like ? ";
			list.add("%" + purchase.getAddress() + "%");
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.CheckSeq ";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * 采购单结算查看-该供应商的所有结算单
	 * 
	 * @param page
	 * @param purchase
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql_gysjsAll(
			Page<Map<String, Object>> page, Purchase purchase) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from (select a.IsCheckOut,a.No,d.Address,a.Supplier,b.Descr SupplierDescr,c.NOTE TypeDescr,a.Date,e.No AppNo,"
				+ "a.OtherCost,a.OtherCostAdj,e.IsService,x1.NOTE IsServiceDescr,a.Remarks,a.PayRemark,a.CheckOutNo,"
				+ "a.ProjectOtherCost,a.Status,a.WHCode,a.Amount,a.CustCode,a.CheckSeq,"
				+ "a.ItemType1,a.OverCost,a.FirstAmount,a.SecondAmount,a.type,d.documentNo,"
				+ "e.IsSetItem,x2.NOTE IsSetItemDescr,a.splAmount,a.splAmount+a.OtherCost+a.OtherCostAdj hjAmount,"
				+ "sc.Date checkOutDate,x3.Note checkOutStatusDescr,e.ItemType2,it2.Descr ItemType2Descr "
				+ " from tPurchase a "
				+ " left join tSplCheckOut sc on a.checkOutNo=sc.No "
				+ " left join tSupplier b on b.Code=a.Supplier "
				+ " left join tXTDM c on c.CBM=a.Type and c.ID='PURCHTYPE' "
				+ " left join tCustomer d on d.Code=a.CustCode "
				+ " left join tItemApp e on a.No=e.PUNo "
				+ " left join tXTDM x1 on x1.IBM=e.IsService and x1.id='YESNO' "
				+ " left join tXTDM x2 on x2.CBM=e.IsSetItem and x2.id='YESNO' "
				+ " left join tXTDM x3 on x3.CBM=sc.Status and x3.id='SPLCKOTSTATUS' "
				+ " left join tItemType2 it2 on it2.Code=e.ItemType2 "
				+ " where 1=1 ";

		if (StringUtils.isNotBlank(purchase.getSupplier())) {
			sql += " and a.Supplier=? ";
			list.add(purchase.getSupplier());
		} else {
			return null;
		}
		if (StringUtils.isNotBlank(purchase.getStatus())) {
			sql += " and a.status=? ";
			list.add(purchase.getStatus());
		}
		if (StringUtils.isNotBlank(purchase.getType())) {
			sql += " and a.type=? ";
			list.add(purchase.getType());
		}
		if (purchase.getDateFrom() != null) {
			sql += " and a.date>=? ";
			list.add(purchase.getDateFrom());
		}
		if (purchase.getDateTo() != null) {
			sql += " and a.date<=? ";
			list.add(DateUtil.addDateOneDay(purchase.getDateTo()));
		}
		if (StringUtils.isNotBlank(purchase.getAddress())) {
			sql += " and d.address like ? ";
			list.add("%" + purchase.getAddress() + "%");
		}
		if (StringUtils.isNotBlank(purchase.getIsCheckOut())) {
			sql += " and a.IsCheckOut=? ";
			list.add(purchase.getIsCheckOut());
		}
		if (StringUtils.isNotBlank(purchase.getCheckOutNo())) {
			sql += " and a.CheckOutNo like ? ";
			list.add("%" + purchase.getCheckOutNo() + "%");
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.CheckSeq ";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * 采购单结算新增
	 * 
	 * @param page
	 * @param purchase
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql_gysjsAdd(Page<Map<String,Object>> page, Purchase purchase) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select a.IsCheckOut,a.No,d.DocumentNo,d.Address Address,a.Supplier,b.Descr SupplierDescr,c.NOTE TypeDescr,a.Date,e.No AppNo,"
			    + "case when a.Type='S' then a.Amount else -a.Amount end Amount,a.OtherCost,a.OtherCostAdj,a.CheckOutNo,"
			    + "case when a.Type='S' then a.Amount else -a.Amount end +a.OtherCost+a.OtherCostAdj SumAmount,a.type,"
			    + "a.FirstAmount,a.SecondAmount,round(case when a.Type='S' then a.Amount else -a.Amount end +a.OtherCost+a.OtherCostAdj-a.FirstAmount-a.SecondAmount,0) RemainAmount,"
			    + "a.Remarks,a.PayRemark,e.IsService,x1.NOTE IsServiceDescr,e.IsSetItem,x2.NOTE IsSetItemDescr,a.CheckSeq,"
			    + "a.splAmount,a.projectAmount,a.splAmount+a.OtherCost hjAmount "//+a.OtherCostAdj  合计金额计算也不加其他费用调整byzjf20190624
				+ ",a.SplStatus,x3.note SplStatusDescr,m.ItemType2,it2.Descr ItemType2Descr, "//增加材料类型2取自领料表 add by zb on 20200424
				+ "d.CustType, f.Desc1 CustTypeDescr,a.ProjectOtherCost "
			    + "from tPurchase a "
			    + "inner join tItemApp m on a.No=m.PUNo "
			    + "left join (select count(*) num,IANo from tItemAppSend where ConfirmStatus<>'1' group by IANo) n on m.no=n.IANo "
			    + " left join tSupplier b on b.Code=a.Supplier "
			    + " left join tXTDM c on c.CBM=a.Type and c.ID='PURCHTYPE' "
			    + " left join tCustomer d on d.Code=a.CustCode "
			    + " left join tItemApp e on a.No=e.PUNo "
			    + " left join tXTDM x1 on x1.IBM=e.IsService and x1.id='YESNO' "
			    + " left join tXTDM x2 on x2.CBM=e.IsSetItem and x2.id='YESNO' "
				+ " left join tXTDM x3 on x3.CBM=a.SplStatus and x3.id='PuSplStatus' "
			    + " left join tItemType2 it2 on it2.Code=m.ItemType2 "
			    + " left join tCusttype f on d.CustType = f.Code ";
			    
		if (StringUtils.isNotBlank(purchase.getUnSelected())) {
			sql += " left join (select * from fStrToTable('"+purchase.getUnSelected()+"',',')) t on a.no=t.item ";
//			System.out.print("+purchase.getUnSelected()+");
		}
		sql += " where a.Status = 'CONFIRMED' and a.SecondAmount=0 and (n.num is null or n.num=0) ";
    	if (StringUtils.isNotBlank(purchase.getSupplier())) {
			sql += " and a.supplier=? ";
			list.add(purchase.getSupplier());
		}else{
			return null;
		}
    	// 供应商状态查询 add by zb on 20190529
    	if (StringUtils.isNotBlank(purchase.getSplStatus())) {
			sql += " and a.splStatus in ('"+purchase.getSplStatus().replace(",", "','")+"')";
		}
    	if (StringUtils.isNotBlank(purchase.getUnSelected())) {
			sql += " and t.item is null ";
		}
    	if (StringUtils.isNotBlank(purchase.getCheckOutNo())) {
			sql += " and (a.IsCheckOut='0' or (a.IsCheckOut='1' and a.checkOutNo=?)) ";
			list.add(purchase.getCheckOutNo());
		}else{
			sql += " and a.IsCheckOut='0' ";
		}
    	if (StringUtils.isNotBlank(purchase.getAddress())) {
			sql += " and d.Address like ? ";
			list.add("%"+purchase.getAddress()+"%");
		}
    	if (StringUtils.isNotBlank(purchase.getDocumentNo())) {
			sql += " and d.DocumentNo like ? ";
			list.add("%"+purchase.getDocumentNo()+"%");
		}
    	if (purchase.getDateFrom()!=null) {
			sql += " and a.date>=? ";
			list.add(purchase.getDateFrom());
		}
    	if (purchase.getDateTo()!=null) {
			sql += " and a.date<=? ";
			list.add(purchase.getDateTo());
		}
    	if (StringUtils.isNotBlank(purchase.getType())) {
			sql += " and a.type=? ";
			list.add(purchase.getType());
		}
    	if (StringUtils.isNotBlank(purchase.getItemType2())) {
			sql += " and m.ItemType2 in ('"+purchase.getItemType2().replace(",", "','")+"') ";
		}
    	
    	if (StringUtils.isNotBlank(purchase.getCustType())) {
            sql += " and f.Code in ('" + purchase.getCustType().replace(",", "','") + "') ";
        }
    	
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ") a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ") a order by a.Address";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	public Map<String, Object> getByNo(String id) {
		String sql = "select a.No,a.Type,x1.note TypeDescr,a.Status,x2.NOTE StatusDescr,"
				+ "a.Date,a.OtherCost,a.OtherCostAdj,a.Remarks,b.Address,a.CustCode ,a.ApplyMan," 
				+"c.NameChi applyManDescr,d.Desc1 WHDescr "
				+ "from tPurchase a "
				+ "left join tCustomer b on a.CustCode=b.Code "
				+ "left join tEmployee c on a.ApplyMan= c.Number "
				+ "left join tWareHouse d on a.WHCode=d.Code "
				+ "left join txtdm x1 on a.Type=x1.CBM and x1.ID='PURCHTYPE' "
				+ "left join txtdm x2 on a.Status=x2.CBM and x2.ID='PURCHSTATUS' where a.no=?";
		List<Map<String, Object>> list = this.findBySql(sql,new Object[] { id });
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	public Page<Map<String, Object>> getDetailByNo(
			Page<Map<String, Object>> page, String no) {
		String sql = "select a.ITCode ItemCode,b.Descr ItemDescr,a.QtyCal,a.Remarks,u.Descr UomDescr "
				+ "from tPurchaseDetail a left join titem b on a.ITCode=b.Code "
				+ "left join tUOM u on b.Uom=u.Code where a.PUNo=?";
		return this.findPageBySql(page, sql, new Object[] { no });
	}

	public Page<Map<String, Object>> findPageBySql_khxx(
			Page<Map<String, Object>> page, Purchase purchase) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from (select a.No,a.Status,s1.Note StatusDescr,a.Date,a.Type ,a.ItemType1,it.Descr ItemType1Descr, s2.Note TypeDescr,"
				+ "a.WHCode,w.Desc1 WareHouse,a.Supplier,sp.Descr SupplDescr,a.Remarks, a.AppItemDate, "
				+ "a.IsCheckOut,i.Note IsCheckOutDescr,a.CheckOutNo,a.CustCode,a.DelivType,c.Descr CustDescr,c.Address,p.NOTE DelivTypeDescr,"
				+ "a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,a.ProjectMan,a.Phone,a.DelivWay,"
				+ "IA.IsService,s3.Note IsServiceDescr,"
				+ "a.Amount,a.FirstAmount,a.SecondAmount,a.RemainAmount,a.OtherCost,a.OtherCostAdj,"
				+ "case when a.Type='R' then -a.Amount else a.Amount end AmountShow,"
				+ "case when a.Type='R' then -a.FirstAmount else a.FirstAmount end FirstAmountShow,"
				+ "case when a.Type='R' then -a.SecondAmount else a.SecondAmount end SecondAmountShow,"
				+ "case when a.Type='R' then -a.RemainAmount else a.RemainAmount end RemainAmountShow,"
				+ "case when a.Type='R' then -a.OtherCost else a.OtherCost end OtherCostShow,"
				+ "case when a.Type='R' then -a.OtherCostAdj else a.OtherCostAdj end OtherCostAdjShow"
				+ " from tPurchase a "
				+ " left outer join tCustomer c on a.CustCode=c.Code "
				+ " left outer join tSupplier sp on a.Supplier=sp.Code "
				+ " left outer join tWareHouse w on a.WHCode=w.Code "
				+ " left outer join tItemApp ia on ia.PUNo=a.no "
				+ " left outer join tXTDM s3 on s3.IBM=ia.IsService and s3.ID='YESNO' "
				+ " left outer join tXTDM s1 on a.Status=s1.CBM and s1.ID='PURCHSTATUS' "
				+ " left outer join tXTDM s2 on a.Type=s2.CBM and s2.ID='PURCHTYPE' "
				+ " left outer join tXTDM p on a.DelivType=p.CBM and p.ID='PURCHDELIVTYPE' "
				+ " left outer join tXTDM i on a.IsCheckOut=i.CBM and i.ID='YESNO' "
				+ " left outer join tItemType1 it on a.ItemType1=it.Code "
				+ " where a.CustCode=? and not exists(select 1 from tItemApp b where b.puno=a.no)";
		if (StringUtils.isNotBlank(purchase.getCustCode())) {
			list.add(purchase.getCustCode());
		} else {
			return null;
		}
		if (StringUtils.isNotBlank(purchase.getItemType1())) {
			sql += " and a.itemType1=? ";
			list.add(purchase.getItemType1());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a ";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * 部分到货采购明细单 #param purchase
	 * 
	 * @param xml
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result doPurchaseDetail(Purchase purchase) {
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
			call = conn.prepareCall("{Call pCgddglDH_forXml(?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, purchase.getM_umState());
			call.setString(2, purchase.getNo());
			call.setTimestamp(3, purchase.getArriveDate() == null ? null
					: new Timestamp(purchase.getArriveDate().getTime()));
			call.setString(4, purchase.getWhcode());
			call.setString(5, purchase.getLastUpdatedBy());
			call.setString(6, purchase.getArriveRemark());
			call.registerOutParameter(7, Types.INTEGER);
			call.registerOutParameter(8, Types.NVARCHAR); 
			call.setString(9, purchase.getPurchaseDetailXml());
			call.setString(10, purchase.getPurchFeeNo());
			call.execute();
			result.setCode(String.valueOf(call.getInt(7)));
			result.setInfo(call.getString(8));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
	}

	/**
	 * 采购退回确认部分
	 */
	@SuppressWarnings("deprecation")
	public Result doPurchReturnCheckOut(Purchase purchase){
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
			call = conn.prepareCall("{Call pCgddgl_forXml(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, purchase.getNo());
			call.setTimestamp(2, purchase.getDate()==null?null : new Timestamp(purchase.getDate().getTime()));
			call.setString(3, purchase.getType());
			call.setString(4, purchase.getStatus());
			call.setString(5, purchase.getDelivType());
			call.setString(6, purchase.getWhcode());
			call.setString(7, purchase.getCustCode());
			call.setString(8, purchase.getSupplier());
			call.setString(9, purchase.getItemType1());
			call.setDouble(10, purchase.getAmount()==null?0:purchase.getAmount());
			call.setString(11, purchase.getRemarks());
			call.setInt(12, purchase.getTemp());//用来判断新增还是更改的标志1,新增；2修改，3，付款 4.采购退回确认
			call.setString(13, purchase.getLastUpdatedBy());
			call.setString(14, "F");
			call.setString(15, purchase.getProjectMan());
			call.setString(16, purchase.getPhone());
			call.setString(17, purchase.getDelivWay());
			call.setDouble(18, purchase.getFirstAmount()==null?0:purchase.getFirstAmount());
			call.setDouble(19, purchase.getSecondAmount()==null?0:purchase.getSecondAmount());
			call.setDouble(20, purchase.getRemainAmount()==null?0:purchase.getRemainAmount());
			call.setString(21, purchase.getIsCheckOut());
			call.setDouble(22, purchase.getOtherCostAdj()==null?0:purchase.getOtherCostAdj());
			call.setTimestamp(23,  purchase.getArriveDate()==null?null : new Timestamp(purchase.getArriveDate().getTime()));
			call.setString(24, purchase.getApplyMan());
			call.setString(25, purchase.getSino());
			call.setDouble(26, purchase.getOtherCost()==null?0:purchase.getOtherCost());
			call.setString(27, purchase.getOldNo());
			call.registerOutParameter(28, Types.INTEGER);
			call.registerOutParameter(29, Types.NVARCHAR);
			call.setString(30, purchase.getPurchaseDetailXml());
			call.execute();
			result.setCode(String.valueOf(call.getInt(28)));
			result.setInfo(call.getString(29));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	
	
	public Map<String,Object> ajaxDoReturn(String no){
		String sql=" select a.puno from  tSupplierPrepayDetail a left outer join tSupplierPrepay b on a.no=b.no " +
				" where a.Status in (1,2) and (b.payemp='' or b.PayEmp is null) and a.PuNo=? ";
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{no});
		if (list!=null && list.size()>0){
		 return list.get(0);
		}
		return null;
	}
	
	public Map<String,Object> getAjaxArriveDay(String code){
		String sql=" select ArriveDay from tItemType2 where code=? ";
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{code});
		if (list!=null && list.size()>0){
		 return list.get(0);
		}
		return null;
	}

	public boolean whRight(String whCode,String czybh){
		String sql=" select * from tWareHouseOperater where whCode=? and czybh = ?";
		List<Map<String, Object>> list=this.findBySql(sql, new Object[]{whCode,czybh});
		if(list!=null && list.size()>0){
			return true;
		}
		return false;
	}
	
	
	public double  getProjectCost(String itCode){
		String sql=" select ProjectCost from tItem where code=?";
		List<Map<String, Object>> list=this.findBySql(sql, new Object[]{itCode});
		
		if(list!=null && list.size()>0){
			Map<String, Object> map= list.get(0);
			return   (Double) map.get("ProjectCost");
		}
		return 0.0;
	}
	
	public String  getItemRight(String czybh){
		String sql=" select itemRight from tCzybm where czybh = ? ";
		List<Map<String, Object>> list=this.findBySql(sql, new Object[]{czybh});
		
		if(list!=null && list.size()>0){
			Map<String, Object> map= list.get(0);
			return   (String) map.get("itemRight");
		}
		return null;
	}
	
	public List<Map<String, Object>>  getChengeParameter(String custCode,String itemType2,String itemType1){
		String sql=" select rtrim(code) item2code,descr item2descr from titemType2 where Code in ( " +
				" select i.itemType2 from titemreq a " +
				" left join titem  i on  i.code=a.itemcode  " +
				" where custcode= ? " +
				" and a.itemType1= ? )";
		List<Map<String, Object>> list=this.findBySql(sql, new Object[]{custCode,itemType1});
		
		if(list!=null && list.size()>0){
			Map<String, Object> map= list.get(0);
			return   list;
		}
		return null;
	}
	
	public List<Map<String, Object>>  getChengeParameter2(String custCode,String itemType2,String itemType1){
		String sql=" select rtrim(code) code,descr from tsupplier where Code in (select i.supplCode from titemreq a " +
				" left join titem  i on  i.code=a.itemcode  " +
				" where custcode= ? and a.itemType1= ? " +
				" and( ?='' or i.itemtype2 in " + "('"+itemType2.replace(",", "','" )+ "'))) ";
		List<Map<String, Object>> list=this.findBySql(sql, new Object[]{custCode,itemType1,itemType2});
		
		if(list!=null && list.size()>0){
			Map<String, Object> map= list.get(0);
			return   list;
		}
		return null;
	}
	
	public List<Map<String, Object>>  getChengeParameter3(String custCode,String itemType2,String itemType1,String supplierCode){
		String sql=" select * from ( select  distinct(icd.no )chgcode from titemreq a " +
				" left join titem b on a.ItemCode=b.Code	" +
				" left join (select MAX(no) no,ReqPK from tItemChgDetail a " +
				" left join titemreq b on b.PK=a.ReqPK group by ReqPK)icd on icd.ReqPK=a.PK" +
				" left join titemchg ic on ic.No= icd.no " +
				" left join tSupplier f on f.Code =b.SupplCode" +
				" where a.CustCode=? and a.itemType1= ?  " +
				" and icd.no is not null "  
					+" and  ( ? ='' or b.itemtype2 in " + "('"+itemType2.replace(",", "','" )+ "')) " 
					+" and  ( ? ='' or f.code in " + "('"+supplierCode.replace(",", "','" )+ "'))" 
						+	" ) a ";
		List<Map<String, Object>> list=this.findBySql(sql, new Object[]{custCode,itemType1,itemType2,supplierCode});
		
		if(list!=null && list.size()>0){
			Map<String, Object> map= list.get(0);
			return   list;
		}
		return null;
	}
	
	public Page<Map<String, Object>> findAppItemPageBy(
			Page<Map<String, Object>> page, Purchase purchase) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select  * from    ( select  se.namechi softnamechi,a.custCode,m.Descr appprjDescr,k.ConfirmDate appcomfirmdate,  j.ArriveDay, f.ConfirmDate, g.descr prjDescr, a.no," +
				" b.descr item1Descr, c.descr supplDescr, d.Address, a.date," +
				"   isnull(a.arriveDate,k.ConfirmDate+j.ArriveDay ) arriveDate, " +
				"   case when j.ArriveDay = '' or j.ArriveDay is null or a.arriveDate = '' or a.arriveDate is null  " +
				"				then k.ConfirmDate else a.arriveDate - j.ArriveDay" +
				"	 end planOrderDate," +
				"   case when datediff(d, getdate()," +
				"                      case when a.arriveDate = '' or a.arriveDate is null" +
				"                           then datediff(d, j.arriveday, f.ConfirmDate)" +
				"                           else datediff(d, j.ArriveDay, a.arriveDate)" +
				"                      end) > 3 then 1" +
				"        else 0" +
				"   end threedayslater, a.amount" +
				" from      tPurchase a" +
				" left  join tItemType1 b on b.code = a.ItemType1" +
				" left join tSupplier c on c.code = a.Supplier" +
				" left join tCustomer d on d.code = a.CustCode " +
				" left join  ( select  max(a.PK)pk,a.CustCode from tCustStakeholder a where role='50' " +
				"	group by a.custCode) sjs on sjs.custCode=d.Code" +
				" left join tCustStakeholder cs on cs.pk=sjs.pk " +
				" left join temployee se on se.number = cs.empcode " +
				" left join tCusttype e on e.Code=d.custtype" +
				" left join ( select    max(pk) pk, custcode" +
				"     from      tprjprog a" +
				"     where     a.confirmdate = ( select    max(confirmdate)" +
				"                                 from      tprjprog" +
				"                                 where     custcode = a.custcode" +
				"                               )" +
				"     group by  a.custcode" +
				"   ) cpi on case when e.IsAddAllInfo='1' then d.Code else d.OldCode end =cpi.custcode " +
				" left join tPrjProg f on f.pk = cpi.pk" +
				" left join tPrjItem1 g on g.Code = f.PrjItem" +
				" left join ( select min(PK) pk, a.PUNo from tpurchaseDetail a  group by  a.PUNo  ) pd on pd.PUNo = a.No" +
				" left join tPurchaseDetail h on h.pk = pd.pk" +
				" left join tItem i on i.code = h.ITCode" +
				" left join tItemType2 j on j.Code = i.ItemType2" +
				" left join tItemType12 l on l.code = j.ItemType12 " +
				" left join tPrjProg k on k.PrjItem = j.AppPrjItem" +
				"       and  case when e.IsAddAllInfo='1' then d.Code else d.OldCode end =k.CustCode " +
				" left join tPrjItem1 m on m.Code=j.AppPrjItem " +
				" " +
				" where     a.IsAppItem = '0'" +
				"   and a.status = 'OPEN'" +
				"   and a.type = 'S'";
		
		if(StringUtils.isNotBlank(purchase.getItemRight())){
			sql += " and a.itemType1 in " + "('"+purchase.getItemRight().replace(",", "','" )+ "')";
		}
		if(StringUtils.isNotBlank(purchase.getAddress())){
			sql+=" and d.address like ? ";
			list.add("%"+purchase.getAddress()+"%");
			
		}
		if(StringUtils.isNotBlank(purchase.getItemType1())){
			sql+=" and a.itemType1 = ? ";
			list.add(purchase.getItemType1());
		}
		if(StringUtils.isNotBlank(purchase.getSupplier())){
			sql+=" and a.supplier = ? ";
			list.add(purchase.getSupplier());
		}
		if(StringUtils.isNotBlank(purchase.getItemType12())){
			sql += " and l.code in " + "('"+purchase.getItemType12().replace(",", "','" )+ "')";
		}
		sql+=" ) a where 1=1 ";
		if(purchase.getAppItemDateFrom()!=null){
			sql+=" and a.planOrderDate >= ? ";
			list.add(new Timestamp(
					DateUtil.startOfTheDay( purchase.getAppItemDateFrom()).getTime()));
		}
		if(purchase.getAppItemDateTo()!=null){
			sql+=" and a.planOrderDate <? ";
			list.add(new Timestamp(
					DateUtil.endOfTheDay( purchase.getAppItemDateTo()).getTime()));
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += " ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> getPurchFeeDetail(
			Page<Map<String, Object>> page, PurchaseFee purchaseFee) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select a.pk,a.PUNo, a.SupplFeeType feeType, a.Amount, a.GenerateType, a.LastUpdate, a.LastUpdatedBy, a.Expired, a.ActionLog," +
				" a.Remarks, a.No,x.note generatetypedescr,b.descr feetypeDescr " +
				" from tPurchaseFeeDetail a " +
				" left join tSupplFeeType b on b.Code = a.SupplFeeType " +
				" left join tXtdm x on x.cbm = a.GenerateType and x.id='PuFeeGenType'  " +
				" where 1=1 ";
		
		if(StringUtils.isNotBlank(purchaseFee.getNo())){
			sql+=" and a.no = ? ";
			list.add(purchaseFee.getNo());
		}else {
			sql+=" and 1<>1 ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> getPurchFeeList(
			Page<Map<String, Object>> page, Purchase purchase) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from (select x.note statusDescr,a.no,a.puno,b.desc1 whDescr,a.whCode ,a.lastupdate,a.arriveNo,a.lastUpdatedBy," +
				" c.amount,x1.note billStatusDescr,a.billStatus " +
				" from tPurchaseFee a " +
				" left join tWareHouse b on b.Code=a.WhCode " +
				" left join (select no,sum(amount) amount from tpurchaseFeeDetail group by no ) c on c.no = a.no " +
				" left join tXtdm x on x.cbm = a.status and x.id='PurchFeeStatus' " +
				" left join tXtdm x1 on x1.cbm = a.billStatus and x1.id='BILLSTATUS'" +
				" where 1=1 ";
		
		if(StringUtils.isNotBlank(purchase.getNo())){
			sql+=" and a.puno = ? ";
			list.add(purchase.getNo());
		}
		if(purchase.getDateFrom()!=null){
			sql+=" and exists ( select 1 from tpurchase where No = a.puno and date >= ? ) ";
			list.add(purchase.getDateFrom());
		}
		if(purchase.getDateTo()!=null){
			sql+=" and exists ( select 1 from tpurchase where No = a.puno and date <= dateAdd(d,1,?)) ";
			list.add(purchase.getDateTo());
		}
		if(StringUtils.isNotBlank(purchase.getStatus())){
			sql+=" and a.status = ? ";
			list.add(purchase.getStatus());
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ") a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ") a order by a.lastUpdate ";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	@SuppressWarnings("deprecation")
	public Result doAppItem(Purchase purchase) {
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
			call = conn.prepareCall("{Call pCgddgl_appItem(?,?,?,?,?)}");
			call.setString(1, purchase.getM_umState());
			call.setString(2, purchase.getLastUpdatedBy());
			call.registerOutParameter(3, Types.INTEGER);
			call.registerOutParameter(4, Types.NVARCHAR);
			call.setString(5, purchase.getPurchaseDetailXml());
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
	
	public void cancelAppItem(String no ,String lastUpdatedBy) {
		String sql="update tpurchase set IsAppItem = '0' ,appItemDate = null ,appItemMan = null, lastUpdatedBy= ? ," +
				"lastUpdate = getDate() where no  = ? and isAppItem = '1' ";
	
		this.executeUpdateBySql(sql, new Object[]{lastUpdatedBy,no});

	}
	
	@SuppressWarnings("deprecation")
	public Result doPurchFeeSave(PurchaseFee purchaseFee){
		Assert.notNull(purchaseFee);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pPurchaseFee(?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1,purchaseFee.getPuno());
			call.setDouble(2, purchaseFee.getAmount());
			call.setString(3,purchaseFee.getRemarks());
			call.setString(4,purchaseFee.getWhcode());
			call.setString(5,purchaseFee.getArriveNo());
			call.setString(6, purchaseFee.getLastUpdatedBy());
			call.setString(7, purchaseFee.getM_umState());
			call.setString(8, purchaseFee.getPurchaseFeeDetailXml());
			call.registerOutParameter(9, Types.INTEGER);
			call.registerOutParameter(10, Types.NVARCHAR);
			call.setString(11, purchaseFee.getNo());
			call.setString(12, purchaseFee.getBillStatus());
			call.execute();
			result.setCode(String.valueOf(call.getInt(9)));
			result.setInfo(call.getString(10));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	
	/**
	 * 供应商采购费用明细列表
	 * @param page
	 * @param purchase
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql_purchaseFeeDetail(
			Page<Map<String, Object>> page, Purchase purchase) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from(select  SupplFeeType, b.Descr SupplFeeTypeDescr,a.GenerateType," 
				    + " x1.note  GenerateTypeDescr, a.Amount, a.Remarks,x2.note PurchFeeStatus "
					+ " from tPurchaseFeeDetail a "
					+ " left join tSupplFeeType b on a.SupplFeeType = b.code " 
					+ " left join tXTDM x1 on x1.cbm =a.GenerateType and x1.id='PuFeeGenType' " +
					" left join tPurchaseFee c on c.no = a.no " +
					" left join tXtdm x2 on x2.cbm = c.Status and x2.id = 'PurchFeeStatus'"
					+ " where 1=1 ";
		if (StringUtils.isNotBlank(purchase.getNo())) {
			sql+=" and a.puno = ? ";
			list.add(purchase.getNo());
		}
		if (StringUtils.isNotBlank(purchase.getGenerateType())) {
			if("1".equals(purchase.getGenerateType())){
				sql+=" and (a.GenerateType = ? or  a.GenerateType = '' or a.GenerateType is null ) ";
				list.add(purchase.getGenerateType());
			}else {
				sql+=" and a.GenerateType = ?  ";
				list.add(purchase.getGenerateType());
			}
		}	 
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ") a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ") a order by a.SupplFeeType ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}	

	public String getPurchFeeNo(String no){
		String sql=" select No from tpurchaseFee where PUNo = ?";
		List<Map<String, Object>> list=this.findBySql(sql, new Object[]{no});
		
		if(list!=null && list.size()>0){
			return list.get(0).get("No").toString();
		}
		return "";
	}
	
	public boolean existsPurchFee(String no){
		String sql=" select 1 from tpurchaseFee where PUNo = ? and status ='1' ";
		List<Map<String, Object>> list=this.findBySql(sql, new Object[]{no});
		
		if(list!=null && list.size()>0){
			return true;
		}
		return false;
	}
	
	public String getLogoName(String custCode){
		String sql="select isnull(e.LogoFile,'MyDesignLogo.jpg') LogoName from tCustomer a " +
				" left join tCusttype b on b.Code = a.CustType" +
				" left join tBuilder c on c.Code = a.BuilderCode" +
				" left join tRegion d on d.Code = c.RegionCode" +
				" left join tCmpCustType e on e.CustType = a.CustType and e.CmpCode = d.CmpCode" +
				" where  a.Code = ? and a.CustType in ('2','20')";
		List<Map<String, Object>> list=this.findBySql(sql, new Object[]{custCode});
		if(list!=null && list.size()>0){
			return list.get(0).get("LogoName").toString();
		}
		return "logo.jpg";
	}
	

}
