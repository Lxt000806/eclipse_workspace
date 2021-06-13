package com.house.home.dao.insales;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.Types;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.entity.insales.PurchaseDetail;

@SuppressWarnings("serial")
@Repository
public class PurchaseDetailDao extends BaseDao {
	
	public Page<Map<String,Object>> findPurchPageBySql(Page<Map<String,Object>> page, PurchaseDetail purchaseDetail) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from  (select a.lastUpdatedby,a.PUNo,a.PK,a.ITCode," +
				" case when p.type='R' and p.OldPUNo is null then i.Cost else a.unitPrice end unitPrice" +
				" ,a.QtyCal,a.ArrivQty,a.Remarks,i.Color," +
				" i.Descr ItDescr," ;
			if(StringUtils.isNotBlank(purchaseDetail.getWhCode()) && StringUtils.isNotBlank(purchaseDetail.getM_umState())){
				sql+="isnull(iwh.Qtycal,'0') allqty,";
			}	else{
				sql+="i.allqty," ;
			}
				sql+=" " +
				"i.ItemType1,b.Descr SqlCodeDescr,u.Descr UniDescr,a.Amount, " +
				" a.QtyCal-a.ArrivQty TheArrivQty ,p.Type,p.DelivType,p.Status,dbo.fGetPurQty(i.Code,a.puno) PurQty,dbo.fGetUseQty(i.Code,a.puno,'') UseQty From tPurchaseDetail a " +
				" LEFT JOIN tItem i ON a.ITCode=i.Code " +
				" LEFT JOIN tBrand b ON i.SqlCode=b.Code " + 
				" LEFT JOIN tPurchase p ON a.PUNo=p.No" + 
				" LEFT JOIN tUoM u ON i.Uom=u.Code " ;
			if(StringUtils.isNotBlank(purchaseDetail.getWhCode()) && StringUtils.isNotBlank(purchaseDetail.getM_umState())){
				sql+=" left join tItemWHBal iwh on iwh.whCode= ? and iwh.itCode=a.ITCode ";
				list.add(purchaseDetail.getWhCode());
			}	
		sql+=" where 1=1 and (a.QtyCal-a.ArrivQty)<> 0 ";
		
		if (purchaseDetail.getPk() != null) { 
			sql += " and a.PK=? ";
			list.add(purchaseDetail.getPk());
		}
    	if (StringUtils.isNotBlank(purchaseDetail.getPuno())) {
			sql += " and a.PUNo=? ";
			list.add(purchaseDetail.getPuno());
		}
    	if (StringUtils.isNotBlank(purchaseDetail.getItcode())) {
			sql += " and a.ITCode=? ";
			list.add(purchaseDetail.getItcode());
		}
    	if (StringUtils.isNotBlank(purchaseDetail.getRemarks())) {
			sql += " and a.Remarks=? ";
			list.add(purchaseDetail.getRemarks());
		}
    	if (purchaseDetail.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(purchaseDetail.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(purchaseDetail.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(purchaseDetail.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(purchaseDetail.getExpired()) || "F".equals(purchaseDetail.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ") a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ") a order by a.Pk";
		}
		return this.findPageBySql(page, sql, list.toArray());

	}
	
	/**
	 * PurchaseDetail分页信息
	 * 
	 * @param page
	 * @param purchaseDetail
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, PurchaseDetail purchaseDetail) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from  (select a.markup,case when a.markup = 0 then 0 else a.befLinePrice*a.QtyCal end  beflineamount,a.beflinePrice,a.lastupdate,a.lastupdatedby,a.PK,a.PUNo,a.unitPrice,a.ProjectCost,a.Amount,a.ITCode,a.QtyCal,a.ArrivQty,a.Remarks,i.Color," +
				"i.Descr ItDescr,i.allqty,b.Descr SqlCodeDescr, w.Code whcode,w.Desc1 whcodedescr ,u.Descr UniDescr," +
				"a.QtyCal-a.ArrivQty TheArrivQty,p.DelivType,p.status,dbo.fGetPurQty(i.Code,a.puno) PurQty,dbo.fGetUseQty(i.Code,a.puno,'') UseQty, " +
				" a.PurchAppDTPK " +
				" From tPurchaseDetail a " +
				" LEFT JOIN tItem i ON a.ITCode=i.Code " +
				" LEFT JOIN tPurchase p ON a.PUNo=p.No " +
				" left join tWareHouse w ON p.WHCode=w.Code "+
				" LEFT JOIN tBrand b ON i.SqlCode=b.Code " + 
				" LEFT JOIN tUoM u ON i.Uom=u.Code " +
				" where 1=1 ";

    	if (purchaseDetail.getPk() != null) { 
			sql += " and a.PK=? ";
			list.add(purchaseDetail.getPk());
		}
    	if (StringUtils.isNotBlank(purchaseDetail.getPuno())) {
			sql += " and a.PUNo=? ";
			list.add(purchaseDetail.getPuno());
		}
    	if (StringUtils.isNotBlank(purchaseDetail.getItcode())) {
			sql += " and a.ITCode=? ";
			list.add(purchaseDetail.getItcode());
		}
    	if (purchaseDetail.getQtyCal() != null) {
			sql += " and a.QtyCal=? ";
			list.add(purchaseDetail.getQtyCal());
		}
    	if (purchaseDetail.getUnitPrice() != null) {
			sql += " and a.UnitPrice=? ";
			list.add(purchaseDetail.getUnitPrice());
		}
    	if (purchaseDetail.getAmount() != null) {
			sql += " and a.Amount=? ";
			list.add(purchaseDetail.getAmount());
		}
    	if (StringUtils.isNotBlank(purchaseDetail.getRemarks())) {
			sql += " and a.Remarks=? ";
			list.add(purchaseDetail.getRemarks());
		}
    	if (purchaseDetail.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(purchaseDetail.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(purchaseDetail.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(purchaseDetail.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(purchaseDetail.getExpired()) || "F".equals(purchaseDetail.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(purchaseDetail.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(purchaseDetail.getActionLog());
		}
    	if (purchaseDetail.getArrivQty() != null) {
			sql += " and a.ArrivQty=? ";
			list.add(purchaseDetail.getArrivQty());
		}
    	if (purchaseDetail.getProjectCost() != null) {
			sql += " and a.ProjectCost=? ";
			list.add(purchaseDetail.getProjectCost());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ") a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ") a order by a.Pk";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> findViewCPageBySql(Page<Map<String,Object>> page, PurchaseDetail purchaseDetail) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select * from  ( select a.markup,case when a.markup = 0 then 0 else befLinePrice*a.QtyCal end  beflineamount,a.beflinePrice,a.PK,a.PUNo,a.unitPrice,a.ProjectCost,a.Amount,a.ITCode,a.QtyCal,a.ArrivQty,a.Remarks,i.Color," +
				" i.Descr ItDescr,i.allqty,b.Descr SqlCodeDescr, w.Code whcode,w.Desc1 whcodedescr ,u.Descr UniDescr," +
				" a.QtyCal-a.ArrivQty TheArrivQty,p.DelivType,p.status " +
				//",dbo.fGetPurQty(i.Code,a.puno) PurQty,dbo.fGetUseQty(i.Code,'') UseQty  " +
				" From tPurchaseDetail a " +
				" LEFT JOIN tItem i ON a.ITCode=i.Code " +
				" LEFT JOIN tPurchase p ON a.PUNo=p.No " +
				" left join tWareHouse w ON p.WHCode=w.Code "+
				" LEFT JOIN tBrand b ON i.SqlCode=b.Code " + 
				" LEFT JOIN tUoM u ON i.Uom=u.Code " +
				" where 1=1 ";

    	if (purchaseDetail.getPk() != null) { 
			sql += " and a.PK=? ";
			list.add(purchaseDetail.getPk());
		}
    	if (StringUtils.isNotBlank(purchaseDetail.getPuno())) {
			sql += " and a.PUNo=? ";
			list.add(purchaseDetail.getPuno());
		}
    	if (StringUtils.isNotBlank(purchaseDetail.getItcode())) {
			sql += " and a.ITCode=? ";
			list.add(purchaseDetail.getItcode());
		}
    	if (purchaseDetail.getQtyCal() != null) {
			sql += " and a.QtyCal=? ";
			list.add(purchaseDetail.getQtyCal());
		}
    	if (purchaseDetail.getUnitPrice() != null) {
			sql += " and a.UnitPrice=? ";
			list.add(purchaseDetail.getUnitPrice());
		}
    	if (purchaseDetail.getAmount() != null) {
			sql += " and a.Amount=? ";
			list.add(purchaseDetail.getAmount());
		}
    	if (StringUtils.isNotBlank(purchaseDetail.getRemarks())) {
			sql += " and a.Remarks=? ";
			list.add(purchaseDetail.getRemarks());
		}
    	if (purchaseDetail.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(purchaseDetail.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(purchaseDetail.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(purchaseDetail.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(purchaseDetail.getExpired()) || "F".equals(purchaseDetail.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(purchaseDetail.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(purchaseDetail.getActionLog());
		}
    	if (purchaseDetail.getArrivQty() != null) {
			sql += " and a.ArrivQty=? ";
			list.add(purchaseDetail.getArrivQty());
		}
    	if (purchaseDetail.getProjectCost() != null) {
			sql += " and a.ProjectCost=? ";
			list.add(purchaseDetail.getProjectCost());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ") a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ") a order by a.Pk";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> findViewOPageBySql(Page<Map<String,Object>> page, PurchaseDetail purchaseDetail) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from  (select a.markup,a.befLinePrice,case when a.markup= 0 then 0 else a.befLinePrice*a.QtyCal end befLineAmount" +
				",a.PK,a.PUNo,a.unitPrice,a.ProjectCost,a.Amount,a.ITCode,a.QtyCal,a.ArrivQty,a.Remarks,i.Color," +
				"i.Descr ItDescr,i.allqty,b.Descr SqlCodeDescr, w.Code whcode,w.Desc1 whcodedescr ,u.Descr UniDescr," +
				"a.QtyCal-a.ArrivQty TheArrivQty,p.DelivType,p.status" +
				//"(dbo.fGetPurQty(i.Code,'')-a.QtyCal+a.ArrivQty) PurQty" +
				//" ,(dbo.fGetUseQty(i.Code,'')-a.QtyCal+a.ArrivQty) UseQty" +
				"  From tPurchaseDetail a " +
				" LEFT JOIN tItem i ON a.ITCode=i.Code " +
				" LEFT JOIN tPurchase p ON a.PUNo=p.No " +
				" left join tWareHouse w ON p.WHCode=w.Code "+
				" LEFT JOIN tBrand b ON i.SqlCode=b.Code " + 
				" LEFT JOIN tUoM u ON i.Uom=u.Code " +
				" where 1=1 ";

    	if (purchaseDetail.getPk() != null) { 
			sql += " and a.PK=? ";
			list.add(purchaseDetail.getPk());
		}
    	if (StringUtils.isNotBlank(purchaseDetail.getPuno())) {
			sql += " and a.PUNo=? ";
			list.add(purchaseDetail.getPuno());
		}
    	if (StringUtils.isNotBlank(purchaseDetail.getItcode())) {
			sql += " and a.ITCode=? ";
			list.add(purchaseDetail.getItcode());
		}
    	if (purchaseDetail.getQtyCal() != null) {
			sql += " and a.QtyCal=? ";
			list.add(purchaseDetail.getQtyCal());
		}
    	if (purchaseDetail.getUnitPrice() != null) {
			sql += " and a.UnitPrice=? ";
			list.add(purchaseDetail.getUnitPrice());
		}
    	if (purchaseDetail.getAmount() != null) {
			sql += " and a.Amount=? ";
			list.add(purchaseDetail.getAmount());
		}
    	if (StringUtils.isNotBlank(purchaseDetail.getRemarks())) {
			sql += " and a.Remarks=? ";
			list.add(purchaseDetail.getRemarks());
		}
    	if (purchaseDetail.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(purchaseDetail.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(purchaseDetail.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(purchaseDetail.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(purchaseDetail.getExpired()) || "F".equals(purchaseDetail.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(purchaseDetail.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(purchaseDetail.getActionLog());
		}
    	if (purchaseDetail.getArrivQty() != null) {
			sql += " and a.ArrivQty=? ";
			list.add(purchaseDetail.getArrivQty());
		}
    	if (purchaseDetail.getProjectCost() != null) {
			sql += " and a.ProjectCost=? ";
			list.add(purchaseDetail.getProjectCost());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ") a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ") a order by a.Pk";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	public Page<Map<String, Object>> findNotArriPageBySql(
			Page<Map<String, Object>> page, PurchaseDetail purchaseDetail) {
		List<Object> list=new ArrayList<Object>();
		String sql="select a.no,c.Address,a.date,a.ArriveDate,b.ArrivQty,case when a.Type='S' then b.QtyCal-b.ArrivQty else b.QtyCal*-1 end QtyCal " +
				" from tPurchaseDetail b inner join tPurchase a on a.no=b.puno left outer join tCustomer c on c.code=a.CustCode  where a.status='OPEN' and a.Expired='F'  and a.date>=dateadd(day,-365, getdate()) " ;
		if(StringUtils.isNotBlank(purchaseDetail.getItcode())){
			sql+=" and b.itCode=? ";
			list.add(purchaseDetail.getItcode());
		}
		return this.findPageBySql(page, sql,list.toArray());
	}

	public Page<Map<String, Object>> findPageByNo(
			Page<Map<String, Object>> page, String no) {
		String sql = "select a.PK,a.PUNo,a.ITCode,d.Descr ITDescr,d.AllQty,a.QtyCal,a.ArrivQty,d.UOM,u.Descr UnitDescr,a.UnitPrice,a.Amount,"
		          + "a.Remarks,a.LastUpdate,a.LastUpdatedBy,sql.Descr SqlCodeDescr from tPurchaseDetail a  "
		          + "left outer join tItem d on a.ITCode = d.Code "
		          + "left outer join tUOM u on d.UOM = u.Code "
		          + " left outer join tBrand sql on sql.code=d.SqlCode "
		          + " where a.puno=? ";
		return this.findPageBySql(page, sql, new Object[]{no});
	}

	public Page<Map<String, Object>> findPageBySql_dhfx(
			Page<Map<String, Object>> page, PurchaseDetail purchaseDetail) {
		List<Object> list=new ArrayList<Object>();
		if (StringUtils.isBlank(purchaseDetail.getCustCode()) || StringUtils.isBlank(purchaseDetail.getItcode())){
			return null;
		}
		String sql = "select * from (select c.no,c.Date,c.ArriveDate,c.Status,x1.NOTE StatusDescr,case when c.Type='R' then -b.QtyCal else b.QtyCal end QtyCal," 
				+" case when c.Type='R' then -b.ArrivQty else b.ArrivQty end ArrivQty from tPurchaseDetail b"
			    +" inner join tPurchase c on b.PUno=c.no"
			    +" left outer join tXTDM x1 on x1.CBM=c.Status and x1.id='PURCHSTATUS'"
			    +" where c.Custcode=? and b.ITCode=? ";
		list.add(purchaseDetail.getCustCode());
		list.add(purchaseDetail.getItcode());
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ") a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ") a order by a.Date";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	
	@SuppressWarnings("deprecation")
	public PurchaseDetail getPurchaseDetail(PurchaseDetail purchaseDetail){
		Assert.notNull(purchaseDetail);
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pGetItemQty(?,?,?,?,?,?,?,?)}");
			call.setString(1, purchaseDetail.getItcode());
			call.setString(2, purchaseDetail.getPuno());
			call.registerOutParameter(3,Types.DOUBLE);
			call.registerOutParameter(4,Types.DOUBLE);
			call.registerOutParameter(5,Types.DOUBLE);
			call.registerOutParameter(6,Types.DOUBLE);
			call.registerOutParameter(7,Types.DOUBLE);
			call.registerOutParameter(8,Types.DOUBLE);
			call.execute();
			purchaseDetail.setAllqty(Double.valueOf(call.getDouble(3)));
			purchaseDetail.setPurqty(Double.valueOf(call.getDouble(4)));
			purchaseDetail.setSaleqty(Double.valueOf(call.getDouble(5)));
			purchaseDetail.setAppqty(Double.valueOf(call.getDouble(6)));
			purchaseDetail.setApplyqty(Double.valueOf(call.getDouble(7)));
			purchaseDetail.setYpqty(Double.valueOf(call.getDouble(8)));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return purchaseDetail;
	}
	
	public PurchaseDetail getQtyCal(PurchaseDetail purchaseDetail){
		String sql=" select sum(QtyCal) QtyCal From  tPurchaseDetail where itcode=? and puno=? ";
		
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{purchaseDetail.getItcode(),purchaseDetail.getPuno()});
		if (list!=null && list.size()>0){
			Map<String, Object> map = new HashMap<String, Object>();
			map=list.get(0);
			purchaseDetail.setQtyCal((Double) map.get("QtyCal"));
			 return purchaseDetail;
		}
		return null;
	}
	
}

