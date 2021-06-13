package com.house.home.dao.insales;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
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

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.entity.insales.ItemBatchDetail;

@SuppressWarnings("serial")
@Repository
public class ItemBatchDetailDao extends BaseDao {

	/**
	 * ItemBatchDetail分页信息
	 * 
	 * @param page
	 * @param itemBatchDetail
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemBatchDetail itemBatchDetail) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select a.*,b.Descr itCodeDescr,b.IsSetItem from tItemBatchDetail a left join tItem b on a.itCode=b.code where 1=1 ";
		
		if (StringUtils.isNotBlank(itemBatchDetail.getItCodeDescr())) {
			sql += " and b.Descr like ? ";
			list.add("%"+itemBatchDetail.getItCodeDescr()+"%");
		}
		if (StringUtils.isNotBlank(itemBatchDetail.getIsSetItem())) {
			sql += " and b.isSetItem=? ";
			list.add(itemBatchDetail.getIsSetItem());
		}
    	if (itemBatchDetail.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(itemBatchDetail.getPk());
		}
    	if (StringUtils.isNotBlank(itemBatchDetail.getIbdno())) {
			sql += " and a.IBDNo=? ";
			list.add(itemBatchDetail.getIbdno());
		}
    	if (StringUtils.isNotBlank(itemBatchDetail.getItcode())) {
			sql += " and a.ITCode=? ";
			list.add(itemBatchDetail.getItcode());
		}
    	if (itemBatchDetail.getQty() != null) {
			sql += " and a.Qty=? ";
			list.add(itemBatchDetail.getQty());
		}
    	if (StringUtils.isNotBlank(itemBatchDetail.getRemarks())) {
			sql += " and a.Remarks=? ";
			list.add(itemBatchDetail.getRemarks());
		}
    	if (itemBatchDetail.getDateFrom() != null){
			sql += " and a.LastUpdate>= ? ";
			list.add(itemBatchDetail.getDateFrom());
		}
		if (itemBatchDetail.getDateTo() != null){
			sql += " and a.LastUpdate<= ? ";
			list.add(itemBatchDetail.getDateTo());
		}
    	if (StringUtils.isNotBlank(itemBatchDetail.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(itemBatchDetail.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(itemBatchDetail.getExpired()) || "F".equals(itemBatchDetail.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(itemBatchDetail.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(itemBatchDetail.getActionLog());
		}
    	if (itemBatchDetail.getDispSeq() != null) {
			sql += " and a.DispSeq=? ";
			list.add(itemBatchDetail.getDispSeq());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by b.Code,b.LastUpdate DESC";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	public Page<Map<String, Object>> findPageByIbdNo(
			Page<Map<String, Object>> page, String ibdNo) {
		List<Object> list = new ArrayList<Object>();
		String sql=" select a.Remarks,a.ItemType1 itemType, t1.pk, t1.IBDNo,t1.ITCode code,t1.Qty,t1.Remarks itemRemark,t1.DispSeq, " 
               +" case when t1.OperType='3' then oi.Descr else i.Descr end Descr,i.SqlCode,sql.Descr brand,i.SupplCode,spl.Descr SupplCodeDescr,ct.Address,a.CustCode,  "
               +"i.Model,i.ItemSize,i.SizeDesc,i.Color,i.Uom uomCode,tUom.Descr Uom,isnull(i.Price,0) Price,a.OtherRemarks, " 
               +"i.Remark ,isnull(i.AllQty,0) AllQty,t1.OperType,ppa.Descr PrePlanAreaDescr,t1.AreaAttr,t1.oldReqPK,t1.OldItemCode,  "
               +"oi.Descr OldItemDescr,t1.prePlanAreaPK, ppa.FixAreaType,case when it2.ItemType12 is not null then it2.ItemType12 else it3.ItemType12 end ItemType12, "
               +"i.ItemType1,it1.Descr ItemType1Descr,i.ItemType2,i.ItemType3 "
               +"from tItemBatchDetail t1 " 
               +"left outer join tItem i on i.code=t1.ITCode  "
               +"left outer join tBrand sql on sql.Code=i.SqlCode " 
               +"left outer join tUom on tUom.Code=i.Uom  "
               +"left outer join tSupplier spl on spl.code=i.SupplCode " 
               +"left outer join tItemType1 it1 on it1.Code=i.ItemType1  "
               +"left outer join tItemBatchHeader a on t1.IBDNo=a.No " 
               +"left outer join tPrePlanArea ppa on t1.PrePlanAreaPK = ppa.PK "
               +"left outer join tCustomer ct on a.CustCode = ct.Code "
               +"left outer join tItem oi on t1.OldItemCode = oi.Code "
               +"left outer join tItemType2 it2 on it2.Code=i.ItemType2 "
               +"left outer join tItemType2 it3 on it3.code=oi.ItemType2 "
               +"where t1.IBDNo=? ";
		list.add(ibdNo);
		return this.findPageBySql(page, sql, list.toArray());
	}

	public List<ItemBatchDetail> getItemBatchDetailByIbdNo(String ibdNo) {
		String hql="FROM ItemBatchDetail WHERE IBDNo=?";
		return this.find(hql, new Object[]{ibdNo});
	}

	public Page<Map<String, Object>> getItemBatchDetailByIbdNo(
			Page<Map<String, Object>> page, ItemBatchDetail itemBatchDetail) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select  i.itemType1,a.pk,a.ITCode,i.Descr ITCodeDescr,a.Qty, a.Remarks,i.remark itemRemark " +
				",a.DispSeq, "
				+ "a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,rtrim(i.ItemType2) ItemType2,h.descr itemType3Descr, "
				+ "i.Uom,i.isfixprice,d.Descr UomDescr,case when '1'= ? then isnull(g.price,0) else i.Price end price,round(case when '1'= ? then isnull(g.price,0) else i.Price end*a.Qty,0) BefLineAmount,i.Cost,i.CommiType, "
				+ "a.OldReqPK,a.OldItemCode,i2.descr OldItemDescr, a.PrePlanAreaPK,pa.Descr PrePlanAreaDescr, a.AreaAttr,a.OperType, x1.note OperTypeDescr,i.ProjectCost,a.IBDNo "
				+ "from tItemBatchDetail a "
				+ "left outer join tItem i on i.code=a.ITCode "
				+ "left outer join tUom d on i.Uom=d.Code "
				+ "left outer join tItemType3 h on i.itemType3=h.Code " 
				+ "left outer join tSupplier s on s.code=i.SupplCode "
				+ "left join tCustTypeItem g on g.itemCode = a.ITCode and g.CustType = ? "
				+ "left join tPrePlanArea pa on pa.pk=PrePlanAreaPK "
				+ "left join txtdm x1 on x1.cbm=a.OperType  and x1.id='IBDTOPERTYPE' "
				+ "left join titem i2 on i2.code=a.OldItemCode  "
				+ " where 1=1   ";
		list.add(itemBatchDetail.getIsLevel());
		list.add(itemBatchDetail.getIsLevel());
		list.add(itemBatchDetail.getCustType());
		if("1".equals(itemBatchDetail.getIsLevel())){
			sql+=" and g.Pk is not null";
		}
		if (StringUtils.isBlank(itemBatchDetail.getItemExpired())) {// 不是材料批次时（增减时）过滤掉过期的材料
			sql += " and i.expired='F' ";
		}
		if (StringUtils.isNotBlank(itemBatchDetail.getIbdno())) {
			sql += " and a.IBDno=? ";
			list.add(itemBatchDetail.getIbdno().trim());
		}
		if(StringUtils.isNotBlank(itemBatchDetail.getCustCode())){
			sql+=" and (s.isGroup = '1' or exists (select 1 from tCustomer in_a " +
					"			left join tBuilder in_b on in_b.code = in_a.BuilderCode " +
					" 			left Join tRegion in_c on in_c.code= in_b.regionCode " +
					"			where exists ( select 1 from tSupplCmp in_d where in_d.SupplCode = s.Code and in_d.CmpCode = in_c.CmpCode ) " +
					" and in_a.Code = ?  " +
					"  ))";
			list.add(itemBatchDetail.getCustCode());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += " order by a.DispSeq";
		}

		return this.findPageBySql(page, sql, list.toArray());

	}
	
	public Page<Map<String, Object>> getItemBatchDetailJqGrid(
			Page<Map<String, Object>> page, ItemBatchDetail itemBatchDetail) {
		List<Object> list = new ArrayList<Object>();
		String sql = " select * from (select f.Descr FixAreaDescr,a.pk, a.itcode, i.descr itcodedescr, a.qty, a.remarks, a.dispseq,i.itemtype2, "+
				    " it2.descr itemtype2descr, i.uom, i.isfixprice, ou.descr olduomdescr, i.cost, "+
					" a.oldreqpk, a.olditemcode, i2.descr olditemdescr, "+
					" a.preplanareapk, pa.descr preplanareadescr, a.areaattr, a.opertype,i.price,"+
					" x1.note opertypedescr,x2.note oldIsServiceDescr,x3.note oldIsOutSetDescr,oit2.descr olditemtype2descr,req.qty oldqty "+
					" from titembatchdetail a "+ 
					" left outer join titem i on i.code = a.itcode "+ 
					" left outer join titemtype2 it2 on i.itemtype2 =it2.code "+
					" left join tpreplanarea pa on pa.pk = preplanareapk "+
					" left join titem i2 on i2.code = a.olditemcode "+
					" left join tItemReq req on req.PK=a.OldReqPK "+
					" left join tFixArea f on f.pk=req.FixAreaPK "+
					" left join txtdm x1 on x1.cbm=a.opertype and x1.id ='ibdtopertype' "+
					" left join txtdm x2 on X2.ibm=req.IsService and x2.id ='YESNO' "+
					" left join txtdm x3 on X3.cbm=req.IsOutSet and x3.id ='YESNO' "+
					" left outer join titemtype2 oit2 on i2.itemtype2 =oit2.code "+
					" left outer join tuom ou on i2.uom =ou.code "+
					" where 1 = 1 " ;
		if (StringUtils.isNotBlank(itemBatchDetail.getIbdno())) {
			sql += " and a.IBDno=? ";
			list.add(itemBatchDetail.getIbdno().trim());
		}
		if (StringUtils.isNotBlank(itemBatchDetail.getOperType())) {
			sql += " and a.OperType=? ";
			list.add(itemBatchDetail.getOperType());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " )a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += " ) a order by a.DispSeq";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

    @SuppressWarnings("deprecation")
    public Page<Map<String, Object>> getItemChgImportingJqGrid(Page<Map<String, Object>> page,
            ItemBatchDetail itemBatchDetail) {
        
        Assert.notNull(itemBatchDetail);
        Connection conn = null;
        CallableStatement call = null;
        try {
            HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
            Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
            conn = session.connection();
            call = conn.prepareCall("{Call pItemBatchImportingQuery(?,?,?,?,?,?,?,?)}");
            call.setString(1, itemBatchDetail.getIbdno());
            call.setString(2, itemBatchDetail.getOperType());
            call.setString(3, itemBatchDetail.getExpired());
            call.setInt(4, itemBatchDetail.getPrePlanAreaPk() != null ? itemBatchDetail.getPrePlanAreaPk() : 0);
            call.setString(5, itemBatchDetail.getAutoImport());
            call.setString(6, itemBatchDetail.getExcludedReqPks());
            call.setString(7, itemBatchDetail.getItemType12());
            call.setString(8, itemBatchDetail.getMatchType());
            call.execute();
            
            List<Map<String, Object>> list = BeanConvertUtil.resultSetToList(call.getResultSet());
            page.setResult(list);
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(null, call, conn);
        }
        
        return page;
    }
    
}

