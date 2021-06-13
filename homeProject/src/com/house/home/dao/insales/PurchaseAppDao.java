package com.house.home.dao.insales;
import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseDao;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.house.framework.commons.orm.Page;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.commons.utils.StringUtils;
import com.house.home.entity.commi.DesignCommiFloatRule;
import com.house.home.entity.insales.PurchaseApp;
import com.house.home.entity.project.ProgTempDt;
import com.house.home.entity.salary.SalaryData;
import com.sun.mail.imap.protocol.Status;

@SuppressWarnings("serial")
@Repository
public class PurchaseAppDao extends BaseDao{

	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, PurchaseApp purchaseApp) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select a.No, a.ItemType1,b.Descr itemType1Descr, a.Status,a.WHCode, a.AppCZY, " +
				"	a.AppDate, a.ConfirmCZY, x.note statusdescr,c.desc1 whDescr,e.nameChi ConfirmCZyDescr," +
				"	d.nameCHi appCzyDescr,a.ConfirmDate, a.Remark, a.LastUpdate," +
				"	a.LastUpdatedBy, a.Expired, a.ActionLog " +
				"	from tPurchaseApp a " +
				"	left join tItemType1 b on b.Code = a.ItemType1 " +
				"	left join tXtdm x on x.cbm = a.Status and x.id= 'PURAPPSTAT' " +
				"	left join tWareHouse c on c.Code = a.WhCode " +
				"	left join tEmployee d on d.Number = a.AppCZY " +
				"	left join tEmployee e on e.Number = a.ConfirmCzy" +
				"	where 1=1 " ;
		if(StringUtils.isNotBlank(purchaseApp.getNo())){
			sql+=" and a.no = ? ";
			list.add(purchaseApp.getNo());
		}
		if(StringUtils.isNotBlank(purchaseApp.getItemType1())){
			sql+=" and a.itemType1 = ? ";
			list.add(purchaseApp.getItemType1());
		}
		if(StringUtils.isNotBlank(purchaseApp.getAppCZY())){
			sql+=" and a.AppCzy = ? ";
			list.add(purchaseApp.getAppCZY());
		}
		if(purchaseApp.getDateFrom() != null){
			sql+=" and a.AppDate >= ?";
			list.add(new Timestamp(
					DateUtil.startOfTheDay( purchaseApp.getDateFrom()).getTime()));
		}
		if(purchaseApp.getDateTo() != null){
			sql+=" and a.AppDate <= ?";
			list.add(new Timestamp(
					DateUtil.endOfTheDay(purchaseApp.getDateTo()).getTime()));
		}
		if(StringUtils.isNotBlank(purchaseApp.getStatus())){
			sql+=" and a.status = ? ";
			list.add(purchaseApp.getStatus());
		}
		
		if ("0".equals(purchaseApp.getIncludeAllDetailsPurchased())) {
            sql += "  and exists( "
                    +"    select 1 "
                    +"    from tPurchaseAppDetail in_a "
                    +"    left join tPurchaseDetail in_b on in_b.PurchAppDTPK = in_a.PK "
                    +"    left join tPurchase in_c on in_c.No = in_b.PUNo "
                    +"    where in_a.No = a.No "
                    +"        and (in_b.PK is null or in_c.Status = 'CANCEL') "
                    +") ";
        }
			
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a ";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> findDetailPageBySql(Page<Map<String,Object>> page, PurchaseApp purchaseApp) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (" +
				" select a.PK,a.No,a.ItemCode,a.Qty,a.remark, c.Descr itemdescr, a.Lastupdate, a.Lastupdatedby, a.Expired, a.ActionLog " +
				" ,f.Descr UomDescr,d.Descr ItemType2Descr " +
				" from tPurchaseAppDetail a" +
				" left join tItem c on c.Code = a.ItemCode " +
				" left join tItemType2 d on d.Code = c.ItemType2" +
				" left join tUOM f on f.Code = c.Uom" +
				" where 1=1 " ;
		if(StringUtils.isNotBlank(purchaseApp.getNo())){
			sql+=" and a.no = ? ";
			list.add(purchaseApp.getNo());
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.PK ";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> findPurchConPageBySql(Page<Map<String,Object>> page, PurchaseApp purchaseApp) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (" +
				" select  a.No,a.Status,x.note statusdescr, a.ItemType1, h.Descr itemtype1descr, a.AppDate," +
				" a.AppCZY,e.NameChi appczydescr, a.ConfirmCZY , f.NameChi appConfirmczy,a.ConfirmDate appConfirmDate," +
				" a.Remark appRemark, c.PUNo, x1.NOTE pustatus,d.Date puappdate ,d.ArriveDate puarrivedate,i.preConfirmDate preConfirmDate," +
				" d.confirmDate puConfirmDate,d.Remarks PurchaseRemark " +
				" from tPurchaseApp a " +
				" left join tPurchaseAppDetail b on b.No = a.No" +
				" left join tPurchaseDetail c on c.PurchAppDTPK = b.PK" +
				" left join tPurchase d on d.no = c.PUNo" +
				" left join tEmployee e on e.Number = a.AppCZY" +
				" left join tEmployee f on f.Number = a.ConfirmCZY " +
				" left join tItem g on g.Code = b.ItemCode " +
				" left join tItemType1 h on h.Code = g.ItemType1 " +
				" left join (" +
				"	select max(in_b.PayDate) preConfirmDate, in_a.PUNo from tSupplierPrepayDetail in_a" +
				" 	left join tSupplierPrepay in_b on in_b.No = in_a.No" +
				" 	group by puno " +
				" )i on i.puno = d.no" +
				" left join tXTDM x on x.cbm = a.Status and x.ID = 'PURAPPSTAT' " +
				" left join tXTDM x1 on x1.cbm = d.Status and x1.ID = 'PURCHSTATUS' " +
				" where 1=1 " ;
		if(StringUtils.isNotBlank(purchaseApp.getStatus())){
			sql+=" and a.status = ? ";
			list.add(purchaseApp.getStatus());
		}
		if(StringUtils.isNotBlank(purchaseApp.getNo())){
			sql+=" and a.no = ? ";
			list.add(purchaseApp.getNo());
		}
		if(StringUtils.isNotBlank(purchaseApp.getPuStatus())){
			sql+=" and d.status = ? ";
			list.add(purchaseApp.getPuStatus());
		}
		if(StringUtils.isNotBlank(purchaseApp.getAppCZY())){
			sql+=" and a.AppCzy = ? ";
			list.add(purchaseApp.getAppCZY());
		}
		if(purchaseApp.getDateFrom() != null){
			sql+=" and a.AppDate >= ?";
			list.add(new Timestamp(
					DateUtil.startOfTheDay( purchaseApp.getDateFrom()).getTime()));
		}
		if(purchaseApp.getDateTo() != null){
			sql+=" and a.AppDate <= ?";
			list.add(new Timestamp(
					DateUtil.endOfTheDay(purchaseApp.getDateTo()).getTime()));
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " group by a.No, a.Status, x.NOTE, a.ItemType1, h.Descr, a.AppDate, a.AppCZY," +
					"				e.NameChi, a.ConfirmCZY, f.NameChi, a.ConfirmDate," +
					"				a.Remark, c.PUNo, x1.NOTE , d.Date , d.ArriveDate," +
					"				i.preConfirmDate, d.ConfirmDate, d.Remarks)a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " group by a.No, a.Status, x.NOTE, a.ItemType1, h.Descr, a.AppDate, a.AppCZY," +
					"				e.NameChi, a.ConfirmCZY, f.NameChi, a.ConfirmDate," +
					"				a.Remark, c.PUNo, x1.NOTE , d.Date , d.ArriveDate," +
					"				i.preConfirmDate, d.ConfirmDate, d.Remarks)a ";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	@SuppressWarnings("deprecation")
	public Result doSave(PurchaseApp purchaseApp) {
		Assert.notNull(purchaseApp);
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pPurchaseApp(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, purchaseApp.getM_umState());
			call.setString(2, purchaseApp.getNo());
			call.setString(3, purchaseApp.getItemType1());
			call.setString(4, purchaseApp.getStatus());
			call.setString(5, purchaseApp.getWhCode());
			call.setString(6, purchaseApp.getAppCZY());
			call.setTimestamp(7, purchaseApp.getAppDate() == null ? null
					: new Timestamp(purchaseApp.getAppDate().getTime()));
			call.setString(8, purchaseApp.getConfirmCZY());
			call.setTimestamp(9, purchaseApp.getConfirmDate() == null ? null
					: new Timestamp(purchaseApp.getConfirmDate().getTime()));
			call.setString(10, purchaseApp.getRemark());
			call.setString(11, purchaseApp.getLastUpdatedBy());
			call.setString(12, purchaseApp.getDetailXml());
			call.registerOutParameter(13, Types.INTEGER);
			call.registerOutParameter(14, Types.NVARCHAR); 
			call.execute();
			result.setCode(String.valueOf(call.getInt(13)));
			result.setInfo(call.getString(14));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	
	public boolean checkCanReConfirm(String no){
		String sql=" select * from tPurchaseDetail a " +
				" left join tPurchaseAppDetail b on a.PurchAppDTPK = b.pk" +
				" left join tPurchase c on c.No = a.PUNo" +
				" where a.PurchAppDTPK is not null " +
				" and b.No = ? and c.status <>'CANCEL'";
		List<Map<String, Object>> list=this.findBySql(sql, new Object[]{no});
		if(list!=null && list.size()>0){
			return false;
		}
		return true;
	}

    public Page<Map<String, Object>> exportingPurchaseAppDetails(Page<Map<String, Object>> page,
            PurchaseApp purchaseApp) {
        
        List<Object> params = new ArrayList<Object>();
        
        String sql = "select * from ("
                + "select a.No, b.PK, b.ItemCode, c.Descr ItemDescr, "
                + "    b.Qty, d.Descr UnitDescr, b.Remark, "
                + "    e.Descr SupplierDescr, c.AllQty, c.Price, c.Cost, "
                + "    c.Color, c.ProjectCost, "
                + "    dbo.fGetPurQty(b.ItemCode, '') PurQty, "
                + "    dbo.fGetUseQty(b.ItemCode, '', '') UseQty, "
                + "    f.Descr BrandDescr, c.ItemType2 "
                + "from tPurchaseApp a "
                + "inner join tPurchaseAppDetail b on b.No = a.No "
                + "left join tItem c on c.Code = b.ItemCode "
                + "left join tUOM d on d.Code = c.Uom "
                + "left join tSupplier e on e.Code = c.SupplCode "
                + "left join tBrand f on f.Code = c.SqlCode "
                + "where a.Status = '1' "
                + "    and not exists( "
                + "        select 1 "
                + "        from tPurchase in_a "
                + "        inner join tPurchaseDetail in_b on in_b.PUNo = in_a.No "
                + "        where in_a.Status <> 'CANCEL' "
                + "            and in_b.PurchAppDTPK = b.PK "
                + "    ) ";
        
        if (StringUtils.isNotBlank(purchaseApp.getNo())) {
            sql += "and a.No = ? ";
            params.add(purchaseApp.getNo());
        }
        
        if (StringUtils.isNotBlank(purchaseApp.getItemType1())) {
            sql += "and a.ItemType1 = ? ";
            params.add(purchaseApp.getItemType1());
        }
        
        if (StringUtils.isNotBlank(purchaseApp.getSupplier())) {
            sql += "and c.SupplCode = ? ";
            params.add(purchaseApp.getSupplier());
        }
        
        if (StringUtils.isNotBlank(purchaseApp.getSavedAppDetailPks())) {
            sql += "and b.PK not in(" + purchaseApp.getSavedAppDetailPks() + ")";
        }

        if (StringUtils.isNotBlank(page.getPageOrderBy())) {
            sql += ") a order by a." + page.getPageOrderBy() + " " + page.getPageOrder();
        } else {
            sql += ") a order by a.PK";
        }
        
        return findPageBySql(page, sql, params.toArray());
    }
    
    public List<Map<String, Object>> getPurchItemData(PurchaseApp purchaseApp){
		String sql = " select c.Descr,d.Descr ItemType2Descr,dbo.fGetPurQty(c.Code,'') PurQty,c.Remark, dbo.fGetKcUseQty(c.Code) UseQty," +
				" isnull(tSend7.Send7,0) Send7,isnull(tSend30.Send30,0) Send30,isnull(tSend60.Send60,0)Send60 ," +
				" isnull(e.DdqrwfhQty,0) DdqrwfhQty,f.Descr UomDescr" +
				" from tItem c" +
				" left join tItemType2 d on d.Code = c.ItemType2" +
				" left join (select isnull(ta.ItemCode,tb.ITCode) ItemCode,isnull(ta.Send7,0)+isnull(tb.Send7,0) Send7" +
				"        from ( select b.ItemCode,sum(case when a.Type='S' then b.SendQty else b.SendQty*-1 end) Send7" +
				"        from tItemApp a inner join tItemAppDetail b on a.no=b.No" +
				"        where a.SendDate>=dateadd(day,-7, getdate()) and a.Status in ('SEND','RETURN')" +
				"        group by b.ItemCode) ta full join ( select b.ITCode,sum(case when a.Type='S' then b.Qty else b.Qty*-1 end) Send7" +
				"        from tSalesInvoice a inner join tSalesInvoiceDetail b on a.no=b.SINo" +
				"        where a.GetItemDate>=dateadd(day,-7, getdate()) and a.Status='CONFIRMED'" +
				"        group by b.ITCode) tb on ta.ItemCode=tb.ITCode) tSend7 on c.Code = tSend7.ItemCode" +
				" left join (select isnull(ta.ItemCode,tb.ITCode) ItemCode,isnull(ta.Send30,0)+isnull(tb.Send30,0) Send30" +
				"        from ( select b.ItemCode,sum(case when a.Type='S' then b.SendQty else b.SendQty*-1 end) Send30" +
				"        from tItemApp a inner join tItemAppDetail b on a.no=b.No" +
				"        where a.SendDate>=dateadd(day,-30, getdate()) and a.Status in ('SEND','RETURN')" +
				"        group by b.ItemCode) ta full join ( select b.ITCode,sum(case when a.Type='S' then b.Qty else b.Qty*-1 end) Send30" +
				"        from tSalesInvoice a inner join tSalesInvoiceDetail b on a.no=b.SINo" +
				"        where a.GetItemDate>=dateadd(day,-30, getdate()) and a.Status='CONFIRMED'" +
				"        group by b.ITCode) tb on ta.ItemCode=tb.ITCode) tsend30 on c.Code = tsend30.ItemCode" +
				" left join (select isnull(ta.ItemCode,tb.ITCode) ItemCode,isnull(ta.Send60,0)+isnull(tb.Send60,0) Send60" +
				"        from ( select b.ItemCode,sum(case when a.Type='S' then b.SendQty else b.SendQty*-1 end) Send60" +
				"        from tItemApp a inner join tItemAppDetail b on a.no=b.No" +
				"        where a.SendDate>=dateadd(day,-60, getdate()) and a.Status in ('SEND','RETURN')" +
				"        group by b.ItemCode) ta full join ( select b.ITCode,sum(case when a.Type='S' then b.Qty else b.Qty*-1 end) Send60" +
				"        from tSalesInvoice a inner join tSalesInvoiceDetail b on a.no=b.SINo" +
				"        where a.GetItemDate>=dateadd(day,-60, getdate()) and a.Status='CONFIRMED'" +
				"        group by b.ITCode) tb on ta.ItemCode=tb.ITCode) tSend60 on c.Code=tSend60.ItemCode" +
				" outer apply (" +
				"        select sum(in_a.DdqrwfhQty) DdqrwfhQty" +
				"        from (select case when exists (" +
				"                    select 1 from tConfItemTypeDt in2_a" +
				"                    inner join tCustItemConfirm in2_b on in2_a.ConfItemType = in2_b.ConfItemType" +
				"                    left join tConfItemType in2_c on in2_c.Code = in2_b.ConfItemType" +
				"                    left join tPrjProg in2_d on in2_d.PrjItem = in2_c.PrjItem and in2_d.CustCode = in1_c.Code" +
				"                    where in2_b.CustCode = in1_c.code and in1_a.itemType2 = in2_a.ItemType2" +
				"                        and (in2_a.itemType3 is null or in2_a.itemType3 = ''" +
				"                                or in1_a.itemTYpe3 = in2_a.itemType3)" +
				"                        and in2_b.ItemConfStatus in ('2','3')" +
				"                        and (in2_c.PrjItem is null or in2_c.prjitem = ''" +
				"                                or (in2_c.PrjItem is not null and in2_d.ConfirmDate is not null" +
				"                                        and in2_d.ConfirmDate < in2_b.lastUpdate))" +
				"                ) then isnull(in1_b.qty - in1_b.SendQty, 0) else 0 end DdqrwfhQty   " +
				"            from tItem in1_a" +
				"            inner join tItemReq in1_b on in1_b.ItemCode = in1_a.Code" +
				"            inner join tCustomer in1_c on in1_c.Code = in1_b.CustCode" +
				"            where in1_b.Qty - in1_b.SendQty > 0" +
				"                and in1_c.Status = '4'" +
				"                and in1_a.SendType = '2'" +
				"                and in1_a.Expired = 'F'" +
				"                and in1_a.Code = c.Code" +
				"        ) in_a" +
				"    ) e" +
				" left join tUOM f on f.Code = c.Uom" +
				" where c.Code = ? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{purchaseApp.getItemCode()});
		if(list != null && list.size() > 0){
			return list;
		}
		return new ArrayList<Map<String,Object>>();
	}
    
    @SuppressWarnings("deprecation")
	public List<Map<String, Object>> getItemSendQty(PurchaseApp	purchaseApp) {
		List<Map<String, Object>> res = new ArrayList<Map<String,Object>>();
		Assert.notNull(purchaseApp);
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pPurchaseApp_sendQty(?)}");
			call.setString(1, purchaseApp.getDetailXml());
			call.execute();
			ResultSet rs = call.getResultSet();
			res=BeanConvertUtil.resultSetToList(rs);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		
		return res;
	}

}
