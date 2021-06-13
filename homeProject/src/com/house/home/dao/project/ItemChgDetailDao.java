package com.house.home.dao.project;

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
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.entity.design.PrePlanArea;
import com.house.home.entity.insales.ItemReq;
import com.house.home.entity.project.ItemChgDetail;

@SuppressWarnings("serial")
@Repository
public class ItemChgDetailDao extends BaseDao {

	/**
	 * ItemChgDetail分页信息
	 * 
	 * @param page
	 * @param itemChgDetail
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemChgDetail itemChgDetail) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from(select a.SupplPromItemPK,a.IsService,a.IsCommi,a.PK,a.No,a.ReqPK,a.FixAreaPK,a.IntProdPK,rtrim(c.ItemType2) ItemType2, " +
				" b.Descr  FixAreaDescr,c.Descr itemDescr,c.Uom,d.Descr uomDescr,c.isFixPrice, isnull(e.Qty,0) PreQty, f.Descr IntProdDescr,g.Descr ItemType2Descr,h.descr itemType3Descr, "+
				" a.ItemCode,a.Qty,a.Cost,round(a.Cost*a.Qty,4) AllCost,a.UnitPrice,a.BefLineAmount,a.Markup,a.itemSetNo,i.descr itemSetDescr,a.custTypeItemPk, " +
				" round(a.Qty*a.UnitPrice*a.Markup/100,0) TmpLineAmount,isnull(a.ProcessCost,0) ProcessCost,j.descr sqlcodedescr,c.MarketPrice, " +
				" a.LineAmount,a.Remarks,a.LastUpdate,a.LastUpdatedBy,a.ActionLog,c.Expired,a.DispSeq,a.DiscCost,a.IsOutSet,case when a.IsOutSet=0 then '否' else '是' end isoutsetdescr, " +
				" a.Algorithm,a.DoorPK,a.CutType,a.RefChgReqPK,al.descr AlgorithmDescr,pra.descr PrePlanAreaDescr,x1.note cuttypedescr,b.PrePlanAreaPK ," +
				" m.descr supplpromDescr,a.AlgorithmPer,a.AlgorithmDeduct,g.itemTYpe12 "+
				" from tItemChgDetail a" +
				" left join  tFixArea b ON a.FixAreaPK=b.PK " +
				" left join  titem c ON a.ItemCode=c.Code " +
				" left join  tUOM d ON c.Uom=d.Code  " +
				" left join  tItemReq e on e.PK=a.ReqPK "+
				" left join  tIntProd f on f.Pk=a.IntProdPK "+
				" left join  tItemType2 g on g.Code=c.ItemType2 "+
				" left join  tItemType3 h on h.Code=c.ItemType3 "+
				" left outer join titemSet i on i.no=a.itemSetNo "+
				" left join tBrand j on c.SqlCode=j.Code "+
			    " left outer join tAlgorithm al on al.code=a.Algorithm " +
				" left outer join tPrePlanArea pra on pra.pk=b.PrePlanAreaPK "+
			    " left outer join txtdm x1 on x1.id='CUTTYPE' and x1.cbm=a.cuttype " +
			    " left join tSupplPromItem k on k.pk = a.SupplPromItemPK " +
			    " left join tSupplProm m on m.no = k.no "+
				" where 1=1 ";
    	if (itemChgDetail.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(itemChgDetail.getPk());
		}
    	if (StringUtils.isNotBlank(itemChgDetail.getNo())) {
			sql += " and a.No=? ";
			list.add(itemChgDetail.getNo());
		}
    	if (itemChgDetail.getReqPk() != null) {
			sql += " and a.ReqPK=? ";
			list.add(itemChgDetail.getReqPk());
		}
    	if (itemChgDetail.getFixAreaPk() != null) {
			sql += " and a.FixAreaPK=? ";
			list.add(itemChgDetail.getFixAreaPk());
		}
    	if (itemChgDetail.getIntProdPk() != null) {
			sql += " and a.IntProdPK=? ";
			list.add(itemChgDetail.getIntProdPk());
		}
    	if (StringUtils.isNotBlank(itemChgDetail.getItemCode())) {
			sql += " and a.ItemCode=? ";
			list.add(itemChgDetail.getItemCode());
		}
    	if (itemChgDetail.getQty() != null) {
			sql += " and a.Qty=? ";
			list.add(itemChgDetail.getQty());
		}
    	if (itemChgDetail.getCost() != null) {
			sql += " and a.Cost=? ";
			list.add(itemChgDetail.getCost());
		}
    	if (itemChgDetail.getUnitPrice() != null) {
			sql += " and a.UnitPrice=? ";
			list.add(itemChgDetail.getUnitPrice());
		}
    	if (itemChgDetail.getBefLineAmount() != null) {
			sql += " and a.BefLineAmount=? ";
			list.add(itemChgDetail.getBefLineAmount());
		}
    	if (itemChgDetail.getMarkup() != null) {
			sql += " and a.Markup=? ";
			list.add(itemChgDetail.getMarkup());
		}
    	if (itemChgDetail.getLineAmount() != null) {
			sql += " and a.LineAmount=? ";
			list.add(itemChgDetail.getLineAmount());
		}
    	if (StringUtils.isNotBlank(itemChgDetail.getRemarks())) {
			sql += " and a.Remarks=? ";
			list.add(itemChgDetail.getRemarks());
		}
    	if (itemChgDetail.getDateFrom() != null){
			sql += " and a.LastUpdate>= ? ";
			list.add(itemChgDetail.getDateFrom());
		}
		if (itemChgDetail.getDateTo() != null){
			sql += " and a.LastUpdate<= ? ";
			list.add(itemChgDetail.getDateTo());
		}
    	if (StringUtils.isNotBlank(itemChgDetail.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(itemChgDetail.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(itemChgDetail.getExpired()) || "F".equals(itemChgDetail.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(itemChgDetail.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(itemChgDetail.getActionLog());
		}
    	if (itemChgDetail.getProcessCost() != null) {
			sql += " and a.ProcessCost=? ";
			list.add(itemChgDetail.getProcessCost());
		}
    	if (itemChgDetail.getDispSeq() != null) {
			sql += " and a.DispSeq=? ";
			list.add(itemChgDetail.getDispSeq());
		}
    	if (itemChgDetail.getIsService() != null) {
			sql += " and a.IsService=? ";
			list.add(itemChgDetail.getIsService());
		}
    	if (itemChgDetail.getIsCommi() != null) {
			sql += " and a.IsCommi=? ";
			list.add(itemChgDetail.getIsCommi());
		}
    	sql+=")a";
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder()+",a.DispSeq ";
		}else{
			sql += "  order by a.DispSeq ";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	public Page<Map<String,Object>> findPageByNo(Page<Map<String,Object>> page,String id) {
		String sql = "SELECT a.*,b.Descr itemCodeDescr,c.Descr itemType2Descr,e.Descr Uom,f.descr fixAreaDescr FROM tItemChgDetail a "
			+"LEFT JOIN tItem b ON a.ItemCode=b.Code "
			+"left join tUom e on b.Uom=e.Code "
			+"left join tFixArea f on a.fixareapk=f.pk "
			+"LEFT JOIN tItemType2 c ON b.ItemType2=c.Code where a.no=? order by a.lastUpdate desc, a.dispseq ";
		return this.findPageBySql(page, sql, new Object[]{id});
	}

	public Page<Map<String, Object>> findPageByNo_khxx(
			Page<Map<String, Object>> page, String no, String costRight) {
		String sql = "select * from (select a.IsCommi,a.PK,a.No,a.ReqPK,a.FixAreaPK,b.Descr FixAreaDescr,a.IntProdPK,"
		  + "c.Descr IntProdDescr,a.ItemCode,d.Descr ItemDescr,f.Descr ItemType2Descr,"
          + "a.Qty,e.Descr Uom,a.Cost,a.UnitPrice,a.BefLineAmount,a.Markup,a.LineAmount,"
          + "a.ProcessCost,(a.Qty*a.UnitPrice*a.Markup/100) TmpLineAmount,sql.Descr SqlCodeDescr,"
          + "a.Remarks,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,st.Descr ItemSetDescr, " ;
      	if (!"1".equals(costRight)) { //主材预算增加提成类型、提成比例，服务性产品、软装、集成预算不用加，删掉b.CommiType modify by zb
          	sql += " '没有权限查看' CommiTypeDescr,'没有权限查看' CommiPerc " ;
  		} else {
  			sql += "tx.NOTE CommiTypeDescr,a.CommiPerc " ; 
  		}
        sql += "from tItemChgDetail a "
          + "left join tFixArea b on b.PK=a.FixAreaPK "
          + "left join tIntProd c on c.Pk=a.IntProdPK "
          + "left join tItem d on d.Code=a.ItemCode "
          + "left join tBrand sql on sql.code=d.SqlCode "
          + "left join tUom e on d.Uom=e.Code "
          + "left join tItemType2 f on f.Code=d.ItemType2 "
          + "left join tItemSet st on a.itemSetNo=st.no "
          +"left join tXTDM tx on tx.CBM=a.CommiType and tx.ID='COMMITYPE' "
          + " where a.No=? ";
        if (StringUtils.isNotBlank(page.getPageOrderBy())){
 			sql += ") a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
 		}else{
 			sql += ") a ";
 		}
 		return this.findPageBySql(page, sql, new Object[]{no});
	}

	@SuppressWarnings("deprecation")
	public void importDetail(Map<String, Object> data) {
		Assert.notNull(data);
		Connection conn = null; 
		CallableStatement call = null;
		ItemReq itemReq=null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pClzj_Import_BS(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setObject(1,data.get("custCode"));
			call.setObject(2,data.get("itemType1"));
			call.setObject(3,data.get("isservice"));
			call.setInt(4, 1);
			if(data.get("reqpk")!=null){
				call.setInt(5,Integer.parseInt(data.get("reqpk").toString()));
				itemReq=this.get(ItemReq.class,Integer.parseInt(data.get("reqpk").toString()));
			}else{
				call.setObject(5,null);
			}
			call.setObject(6,data.get("fixareadescr"));
			call.setObject(7,data.get("intproddescr"));
			call.setObject(8,data.get("iscupboard"));
			call.setObject(9,data.get("itemcode"));
			call.setDouble(10, Double.parseDouble(data.get("qty").toString()));
			call.setDouble(11, Double.parseDouble(data.get("unitprice").toString()));
			call.setDouble(12, Double.parseDouble(data.get("markup").toString()));
			call.setDouble(13, Double.parseDouble(data.get("processcost").toString()));
			call.setObject(14,data.get("remarks"));
			call.setObject(15,data.get("isoutset"));
			call.setObject(16,data.get("lastupdatedby"));
			call.setInt(17, Integer.parseInt(data.get("dispseq").toString()));
			call.setString(18,data.get("itemsetdescr").toString());
			call.registerOutParameter(19, Types.INTEGER);
			call.registerOutParameter(20, Types.INTEGER);
			call.registerOutParameter(21, Types.NVARCHAR);
			call.registerOutParameter(22, Types.INTEGER);
			call.registerOutParameter(23, Types.NVARCHAR);
			call.registerOutParameter(24, Types.NVARCHAR);
			call.execute();
			if(call.getInt(22)==1){
				if(itemReq!=null){
					data.put("fixareapk", itemReq.getFixAreaPk());
					data.put("intprodpk", itemReq.getIntProdPk());
					data.put("itemsetno", itemReq.getItemSetNo());
					if(itemReq.getPrePlanAreaPk() != null){
						PrePlanArea prePlanArea = this.get(PrePlanArea.class, itemReq.getPrePlanAreaPk());
						data.put("preplanareapk", itemReq.getPrePlanAreaPk());
						data.put("preplanareadescr", prePlanArea.getDescr());
					}
					
				}else{
					data.put("fixareapk", call.getInt(19));
					data.put("intprodpk", call.getInt(20));
					data.put("itemsetno", call.getString(21));
					System.out.println(call.getInt(24));
					if(call.getInt(24) != 0){
						PrePlanArea prePlanArea = this.get(PrePlanArea.class, call.getInt(24));
						data.put("preplanareapk", prePlanArea.getPk());
						data.put("preplanareadescr", prePlanArea.getDescr());
					}
				}
			}else{
				data.put("isinvalid", 1);
				data.put("isinvaliddescr", call.getString(23));
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		
		
	}

}

