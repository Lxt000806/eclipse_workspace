package com.house.home.dao.insales;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.SqlUtil;
import com.house.home.entity.insales.ItemAppDetail;

@SuppressWarnings("serial")
@Repository
public class ItemAppDetailDao extends BaseDao {
	
	/**
	 *从预算导入ItemAppDetail页面
	 *
	 *@param page
	 *@param itemAppDetail
	 */
	public Page<Map<String,Object>> findImportPageBySql(Page<Map<String,Object>> page, ItemAppDetail itemAppDetail) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from (select a.No,a.Pk,a.FixAreaPK,a.IntProdPK,a.ItemCode,a.Qty,a.SendQty ,b.Descr FixAreaDescr " +
				",c.Descr IntProdDescr ,i.ItemType2,i.ItemType1,i.Descr itemdescr,i.SqlCode,i.Price ,i.Color ,br.Descr sqlcodedescr ,s.Descr suppldescr,t2.Descr itemDescr2 " +
				" ,a.remarks ,i1.CustCode,i.AllQty  ,u.Descr uomdescr From tItemAppDetail a " +
				   "left outer join tFixArea b on a.FixAreaPK = b.PK "
				  +" left outer join tIntProd c on a.IntProdPK = c.PK "
				  +" left outer join tItem i on a.ItemCode = i.Code "
				  +" left outer join tItemApp i1 on a.No = i1.No "
				  +" left outer join tBrand br on br.Code = i.SqlCode "
				  +" left outer join tSupplier s on i.SupplCode=s.Code "
				  +" left outer join tItemType2 t2 on t2.Code = i.ItemType2 "
				  +" left outer join tUom u on u.Code=i.Uom "
			      +" where 1=1 ";
		
		if (itemAppDetail.getPk() != null) {
			sql += " and a.Pk=? ";
			list.add(itemAppDetail.getPk());
		}
		if (itemAppDetail.getCustCode() != null) {
			sql += " and i1.CustCode=? ";
			list.add(itemAppDetail.getCustCode());
		}
		
		if (itemAppDetail.getItemType1() != null) {
			sql += " and i.ItemType1=? ";
			list.add(itemAppDetail.getItemType1());
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ") a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ") a order by a.Pk";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * ItemAppDetail分页信息
	 * 
	 * @param page
	 * @param itemAppDetail
	 * @return
	 */
	public Page<Map<String,Object>> findPurchPageBySql(Page<Map<String,Object>> page, ItemAppDetail itemAppDetail) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from ( select a.no,a.remarks,ir.Markup,ir.UnitPrice,a.itemcode,a.qty,ia.Puno,i.Descr itemDescr,u.Descr UomDescr, " +
				" case when ir.qty <> 0 then round(a.qty*ir.UnitPrice*ir.Markup/100 + ir.ProcessCost*a.qty/ir.qty,0)" +
				" else Round(a.qty*ir.UnitPrice*ir.Markup/100,0) end LineAmount, " +
				"  isnull(case when i.ItemType2 in ('028','029') and ir.Qty<>0 then round(ir.ProcessCost*a.Qty/ir.Qty,2) " +
				" else ir.ProcessCost end, 0) ProcessCost,isnull(a.DeclareQty, 0) DeclareQty, " +
				"  case when a.Qty+isnull(a.ShortQty,0)=0 and a.DeclareQty=0 then 0 when a.Qty+isnull(a.ShortQty,0)<>0 " +
				"and a.DeclareQty=0 then 1 else round((a.DeclareQty-a.Qty-isnull(a.ShortQty,0))/a.DeclareQty,2) end DiffPor "+
				" from tItemAppDetail a " +
				" left join tItemApp ia on ia.No=a.No " +
				" left outer join tItemReq ir on ir.pk=a.ReqPK " +
				" left join tItem i on i.Code=a.itemCode "  +
				" left join tUom u on u.Code=i.Uom " +
				" where 1=1 ";
		
		if(StringUtils.isNotBlank(itemAppDetail.getPuno())){
			sql+=" and ia.PUNo = ?";
			list.add(itemAppDetail.getPuno());

		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ") a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ") a order by a.puno";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * ItemAppDetail分页信息
	 * 
	 * @param page
	 * @param itemAppDetail
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemAppDetail itemAppDetail) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from (Select ia.puno,"
				+ "a.PK,a.No,a.ReqPK,a.FixAreaPK,b.Descr FixAreaDescr,a.IntProdPK,c.Descr IntProdDescr,"
				+ "a.ItemCode,d.Descr ItemDescr,a.Qty,ir.Qty ReqQty,isnull(ir.SendQty,0) SendedQty,a.SendQty,"
				+ "isnull(e.ConfirmedQty,0) ConfirmedQty,a.Remarks,a.Cost,a.ProjectCost,"
				+ "round(a.Cost*a.Qty,2) AllCost,round(a.ProjectCost*a.Qty,2) AllProjectCost, "
				+ "ir.UnitPrice Price,isnull(ir.ProcessCost,0) ProcessCost,"
				+ "d.Cost*a.Qty SumCost,(isnull(e.ConfirmedQty,0)+ir.SendQty+a.Qty-ir.Qty)*d.Cost as Differences, "
				+ "(isnull(e.ConfirmedQty,0)+ir.SendQty+a.Qty-ir.Qty)*d.Price as PriceDifferences, "
				+ "a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,u.Descr UomDescr,d.color,d.SqlCode,sql.Descr SqlcodeDescr,spl.Descr SupplCodeDescr "
				+ "from tItemAppDetail a "
				+ "left outer join tItemApp ia on ia.No =a.No "
				+ "left outer join ( "
				+ "	SELECT i2.ReqPK,sum(Qty) ConfirmedQty  FROM tItemAppDetail i2 "
				+ "	inner join tItemApp i1 on  i2.No=i1.No "
				+ "	WHERE i2.reqpk>0 and i1.CustCode = ? group by i2.ReqPk) e on e.ReqPk = a.ReqPK  "
				+ "	left outer join tFixArea b on a.FixAreaPK = b.PK "
				+ " left outer join tIntProd c on a.IntProdPK = c.PK "
				+ "	left outer join tItemReq ir on ir.Pk=a.ReqPK "
				+ " left outer join tItem d on a.ItemCode = d.Code "
				+ " left outer join tBrand sql on sql.code=d.sqlcode "
				+ " left outer join tSupplier spl on spl.Code=d.SupplCode "
				+ " left outer join tUom u on u.code=d.uom "
				+ "where 1=1 ";
		list.add(itemAppDetail.getCustCode());
    	if (itemAppDetail.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(itemAppDetail.getPk());
		}
    	if (StringUtils.isNotBlank(itemAppDetail.getNo())) {
			sql += " and a.No=? ";
			list.add(itemAppDetail.getNo());
		}else{
			return null;
		}
    	if (itemAppDetail.getReqPk() != null) {
			sql += " and a.ReqPK=? ";
			list.add(itemAppDetail.getReqPk());
		}
    	if (itemAppDetail.getFixAreaPk() != null) {
			sql += " and a.FixAreaPK=? ";
			list.add(itemAppDetail.getFixAreaPk());
		}
    	if (itemAppDetail.getIntProdPk() != null) {
			sql += " and a.IntProdPK=? ";
			list.add(itemAppDetail.getIntProdPk());
		}
    	if (StringUtils.isNotBlank(itemAppDetail.getItemCode())) {
			sql += " and a.ItemCode=? ";
			list.add(itemAppDetail.getItemCode());
		}
    	if (itemAppDetail.getQty() != null) {
			sql += " and a.Qty=? ";
			list.add(itemAppDetail.getQty());
		}
    	if (StringUtils.isNotBlank(itemAppDetail.getRemarks())) {
			sql += " and a.Remarks=? ";
			list.add(itemAppDetail.getRemarks());
		}
    	if (itemAppDetail.getDateFrom() != null){
			sql += " and a.LastUpdate>= ? ";
			list.add(itemAppDetail.getDateFrom());
		}
		if (itemAppDetail.getDateTo() != null){
			sql += " and a.LastUpdate<= ? ";
			list.add(itemAppDetail.getDateTo());
		}
    	if (StringUtils.isNotBlank(itemAppDetail.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(itemAppDetail.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(itemAppDetail.getExpired()) || "F".equals(itemAppDetail.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(itemAppDetail.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(itemAppDetail.getActionLog());
		}
    	if (itemAppDetail.getCost() != null) {
			sql += " and a.Cost=? ";
			list.add(itemAppDetail.getCost());
		}
    	if (itemAppDetail.getAftQty() != null) {
			sql += " and a.AftQty=? ";
			list.add(itemAppDetail.getAftQty());
		}
    	if (itemAppDetail.getAftCost() != null) {
			sql += " and a.AftCost=? ";
			list.add(itemAppDetail.getAftCost());
		}
    	if (itemAppDetail.getSendQty() != null) {
			sql += " and a.SendQty=? ";
			list.add(itemAppDetail.getSendQty());
		}
    	if (itemAppDetail.getProjectCost() != null) {
			sql += " and a.ProjectCost=? ";
			list.add(itemAppDetail.getProjectCost());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ") a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ") a order by a.Pk";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	public Page<Map<String, Object>> findPageByNo(
			Page<Map<String, Object>> page, String id) {
		String sql =" SELECT a.no, a.pk,a.qty,a.sendQty,b.Descr itemCodeDescr,c.descr uom,d.Address,e.NOTE status, "
				   +" t.date,t.confirmDate,t.sendDate,t.arriveDate,t.remarks,CASE WHEN t.SendType='1' THEN f.Descr ELSE g.Desc1 END supplyAddress,t.followRemark,t.IsSetItem, "
				   +" case when t.isSetItem='1' then round(a.ProjectCost*a.Qty,2) else 0 end AllProjectCost, h.ArriveDate ActualArriveDate,t.Status ItemAppStatus "
				   +" FROM tItemAppDetail a "
				   +" inner join tItemApp t on a.no=t.no "
				   +" LEFT JOIN titem b ON a.ItemCode=b.Code left join tuom c on b.uom=c.code "
				   +" INNER JOIN tCustomer d ON t.CustCode=d.Code "
				   +" INNER JOIN tXTDM e ON e.ID='ITEMAPPSTATUS' AND e.CBM=t.Status "
				   +" LEFT JOIN tSupplier f ON f.Code=t.SupplCode "
				   +" LEFT JOIN tWareHouse g ON g.Code=t.WHCode "
				   +" left join (select in_a.IANo, max(in_a.ArriveDate) ArriveDate from tItemAppSend in_a group by in_a.IANo) h on t.No = h.IANo "
				   +" WHERE a.No=?";
		return this.findPageBySql(page, sql, new Object[]{id});
	}
	
	
	public Page<Map<String, Object>> findItemAppDetailExists(Page<Map<String, Object>> page,
			Map<String, Object> param){
		List<Object> list = new ArrayList<Object>();
		String sql = "select "
				+ "	t1.PK,t1.CustCode,t1.FixAreaPK,fa.Descr FixAreaDescr,"
				+ "	t1.IntProdPK,ip.Descr IntProdDescr,t1.ItemCode,sp.Descr SupplDescr,"
				+ "	i.Descr ItemDescr,t1.ItemType1,s.Descr TypeDescr,t1.Qty,"
				+ "	s2.Descr ItemType2Descr,t1.SendQty,t1.Remark,t1.DispSeq,i.Cost  "
				+ "from tItemReq t1 "
				+ "	left outer join tFixArea fa on t1.FixAreaPK=fa.PK "
				+ "	left outer join tIntProd ip on t1.IntProdPK=ip.PK "
				+ "	left outer join tItem i on t1.ItemCode=i.Code "
				+ "	left outer join tSupplier sp on sp.Code=i.SupplCode and i.Code = t1.ItemCode"
				+ "	left outer join tItemType1 s on t1.ItemType1=s.Code"
				+ "	left outer join tItemType2 s2 on i.Itemtype2=s2.Code"
				+ "where t1.qty <> 0 ";
		String reqPK = (String)param.get("reqPK");
		if(StringUtils.isNotEmpty(reqPK)){
			sql += " and t1.pk not in (?)";
			list.add(reqPK);
		}
		String custCode = (String)param.get("custCode");
		if(StringUtils.isNotEmpty(custCode)){
			sql += " and t1.CustCode = ?";
			list.add(custCode);
		}
		String itemType1 = (String)param.get("itemType1");
		if(StringUtils.isNotEmpty(itemType1)){
			sql += " and t1.ItemType1 = ?";
			list.add(itemType1);
		}
		String isService = (String)param.get("isService");
		if(StringUtils.isNotEmpty(isService)){
			sql += " and t1.IsService = ?";
			list.add(isService);
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> findItemAppDetailFast(Page<Map<String, Object>> page, Map<String, Object> param){
		List<Object> list = new ArrayList<Object>();
		String sql = "select "
				+ "a.Code,a.Descr,a.ItemSize,a.SizeDesc,a.BarCode,a.RemCode,a.ItemType1,i1.Descr ItemType1Descr,"
				+ "a.ItemType2,i2.Descr ItemType2Descr,a.ItemType3,i3.Descr ItemType3Descr,"
				+ "a.SqlCode,b.Descr SqlDescr,a.Uom,u.Descr UomDescr,a.SupplCode,s.Descr SplCodeDescr,"
				+ "a.Model,a.Color,a.Remark,a.CommiType,d.NOTE CommiTypeDescr,a.Price,a.MarketPrice,a.IsFixPrice,"
				+ "p.Note IsFixPriceDescr,a.Cost,a.AvgCost,a.AllQty,a.ProjectCost,a.ActionLog "
				+ "from  tItem a "
				+ "	left outer join tItemType1 i1 on a.ItemType1=i1.Code "
				+ "	left outer join tItemType2 i2 on a.ItemType2=i2.Code "
				+ " left outer join tItemtype3 i3 on a.ItemType3=i3.Code "
				+ " left outer join tBrand b on a.SqlCode=b.Code "
				+ " left outer join tXTDM d on a.CommiType=d.CBM and d.ID='COMMITYPE' "
				+ " left outer join tXTDM p on a.IsFixPrice=p.CBM and p.ID='ISFIXPRICE' "
				+ " left outer join tSupplier s on a.SupplCode=s.Code "
				+ " left outer join tUom u on a.Uom = u.Code "
				+ "	where a.Expired='F' ";
		String isSetItem = (String)param.get("isSetItem");
		if(StringUtils.isNotEmpty(isSetItem)){
			sql += "and a.IsSetItem = ?";
			list.add(isSetItem);
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

	public Page<Map<String, Object>> findPageBySql_return(
			Page<Map<String, Object>> page, ItemAppDetail itemAppDetail) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from (Select a.specreqpk,a.PK,a.No,a.ReqPK,a.FixAreaPK,b.Descr FixAreaDescr,a.IntProdPK,c.Descr IntProdDescr, "
            + "a.ItemCode,d.Descr ItemDescr,a.Qty,ir.Qty ReqQty,ir.SendQty,"
            + "ConfirmedQty=ISNULL((SELECT SUM(Qty) FROM tItemAppDetail i2, "
            + " tItemApp i1 WHERE i2.No=i1.No AND i1.Status='CONFIRMED' and i2.ReqPK=a.ReqPK ),0),"
            + "a.Remarks,a.Cost,a.ProjectCost,a.AftQty,a.AftCost,"
            + "round(a.Cost*a.Qty,2) AllCost,round(a.ProjectCost*a.Qty,2) AllProjectCost,u.Descr UomDescr  "
            + " from tItemAppDetail a  "
            + " left outer join tItemReq IR on IR.pk=a.ReqPK "
            + " left outer join tFixArea b on a.FixAreaPK = b.PK "
            + " left outer join tIntProd c on a.IntProdPK = c.PK "
            + " left outer join tItem d on a.ItemCode = d.Code "
            + " left outer join tUom u on u.code=d.uom "
            + " where 1=1 ";
        if (StringUtils.isNotBlank(itemAppDetail.getNo())) {
			sql += " and a.No=? ";
			list.add(itemAppDetail.getNo());
		}else{
			return null;
		}
        if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ") a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ") a ";
		}
        return this.findPageBySql(page, sql, list.toArray());
	}

	public Page<Map<String, Object>> findItemAppDetailExistsReturn(
			Page<Map<String, Object>> page, Map<String, Object> param) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from (Select 0 as IsCheck, t1.PK,t1.no,isnull(t1.ReqPK,0) ReqPK,isnull(t1.specreqpk,0) specreqpk, "
                  +  "isnull(t1.FixAreaPK,0) FixAreaPK,fa.Descr FixAreaDescr,t1.IntProdPK,ip.Descr IntProdDescr,"
                  +  "t1.ItemCode,sp.Descr SupplDescr,i.Descr ItemDescr,t1.Qty,isnull(t2.Qty,0) ReqQty, "
                  +  "isnull(t2.SendQty,0) SendQty,t1.Cost,t1.ProjectCost,t1.AftQty,t1.AftCost,"
                  + "round(t1.Cost*t1.Qty,2) AllCost,round(t1.ProjectCost*t1.Qty,2) AllProjectCost,u.Descr UomDescr,"
                  +  "t1.Remarks from tItemAppDetail t1  "
                  +  " left outer join tItemReq t2 on t2.pk=t1.ReqPK "
                  +  " left outer join tFixArea fa on t1.FixAreaPK=fa.PK "
                  +  " left outer join tIntProd ip on t1.IntProdPK=ip.PK "
                  +  " left outer join tItem i on t1.ItemCode=i.Code "
                  +  " left outer join tUom u on u.code=i.uom "
                  +  " left outer join tSupplier sp on sp.Code=i.SupplCode and i.Code = t1.ItemCode ";
		String no = (String)param.get("no");
		String unSelected = (String)param.get("unSelected");
		unSelected = SqlUtil.resetIntStatus(unSelected);
		if(StringUtils.isNotBlank(no)){
			if (StringUtils.isNotBlank(unSelected)){
				sql += " where t1.no=? and t1.reqpk not in ("+unSelected+") ";
			}else{
				sql += " where t1.no=? ";
			}
			list.add(no);
			sql += " and exists( "
             + " 	select 1 from ( "
             + " 		select sum(in2_a.SendQty) SendQty "
             + " 		from tItemAppDetail in2_a "
             + " 		inner join tItem in2_b on in2_a.ItemCode = in2_b.Code "
             + " 		left join tItem in2_c on in2_b.IsActualItem = '0' and in2_b.WareHouseItemCode = in2_c.Code "
             + " 		left join titemapp in2_d on in2_a.no=in2_d.no "
             + " 		where in2_d.OldNo=? and ((t1.FixAreaPK > 0 and in2_a.FixAreaPK  = t1.FixAreaPK) or t1.FixAreaPK  <= 0 or t1.FixAreaPK  is null) "
             + " 		and (case in2_b.IsActualItem when '1' then in2_b.Code when '0' then in2_c.Code end) = t1.ItemCode "
             + " 	) in_a  "
             + " where  isnull(in_a.SendQty,0) < t1.Qty "
             + " ) ";
			 list.add(no);
		}else{
			return null;
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ") a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ") a ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}


	public Page<Map<String, Object>> findCheckedPageBySql(
			Page<Map<String, Object>> page, ItemAppDetail itemAppDetail) {
		List<Object> list = new ArrayList<Object>();
		String sql ="select a.no,c.Address,a.Date,b.FixAreaPK,f.descr FixAreaDescr,"
				+"case when a.Type='S' then b.Qty-b.SendQty else (b.Qty-b.SendQty)*-1 end Qty,"
				+"case when a.Type='S' then b.SendQty else b.SendQty*-1 end SendQty "
				+"from tItemAppDetail b inner join tItemApp a on a.no=b.no "
				+"left outer join tCustomer c on c.code=a.CustCode " 
				+"left outer join tFixArea f on f.PK=b.FixAreaPK where a.status='CONFIRMED' and a.Expired='F' "
				+"and a.date>=dateadd(day,-365, getdate())";

		if (StringUtils.isNotBlank(itemAppDetail.getItemCode())) {
			sql += "  and b.itemcode=? ";
			list.add(itemAppDetail.getItemCode());
		}
    	sql += " order by a.Date asc ";
		return this.findPageBySql(page, sql, list.toArray());
	}

	public Page<Map<String, Object>> findAppliedPageBySql(
			Page<Map<String, Object>> page, ItemAppDetail itemAppDetail) {
		List<Object> list = new ArrayList<Object>();
		String sql ="select a.no,c.Address,a.Date,b.FixAreaPK,f.descr FixAreaDescr,case when a.Type='S' then b.Qty else b.Qty*-1 end Qty,case when a.Type='S' then b.SendQty else b.SendQty*-1 end SendQty  " +
				" from tItemAppDetail b inner join tItemApp a on a.no=b.no left outer join tCustomer c on c.code=a.CustCode left outer join tFixArea f on f.PK=b.FixAreaPK where (a.status='OPEN' or a.status='CONRETURN' or a.status='WAITCON') and a.Expired='F'  and a.date>=dateadd(day,-365, getdate()) "; 
		if (StringUtils.isNotBlank(itemAppDetail.getItemCode())) {
			sql += "  and b.itemcode=? ";
			list.add(itemAppDetail.getItemCode());
		}
    	
		return this.findPageBySql(page, sql, list.toArray());
	}


	public Page<Map<String, Object>> findPageByNo_khxxcx(
			Page<Map<String, Object>> page, String no, String custCode) {
		List<Object> list = new ArrayList<Object>();
		if(StringUtils.isBlank(no) || StringUtils.isBlank(custCode)){
			return null;
		}
		String sql = "select * from (select  a.PK ,a.No ,a.ReqPK ,a.FixAreaPK ,b.Descr FixAreaDescr ,a.IntProdPK ,c.Descr IntProdDescr ,"
        +"a.ItemCode ,d.Descr ItemDescr ,a.Qty ,a.SendQty ,t.Qty as ReqQty,t.SendQty as SendedQty,"
        +"ConfirmedQty = isnull(( select  sum(Qty) from tItemAppDetail i2 ,tItemApp i1 where   i2.No = i1.No "
        +"and i2.ItemCode = a.ItemCode and i1.Status = 'CONFIRMED' and i1.CustCode =?), 0) ,"
        +"a.Remarks "
        +"from    tItemAppDetail a "
        +"left join tItemReq t on a.ReqPK = t.PK "
        +"left join tFixArea b on a.FixAreaPK = b.PK "
        +"left join tIntProd c on a.IntProdPK = c.PK "
        +"left join tItem d on a.ItemCode = d.Code "
        +"where a.no=? ";
		list.add(custCode);
		list.add(no);
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ") a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ") a ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

	public List<Map<String, Object>> findNotSendItemList(String custCode) {
		String sql="SELECT a.ItemCode,RTRIM(a.no) no,a.ReqPK FROM  tItemAppDetail a " +
                   "WHERE  EXISTS (SELECT 1 FROM  tItemApp b  WHERE b.CustCode=? AND (b.status='OPEN' or b.status='CONFIRMED' or b.status='CONRETURN' or b.status='WAITCON') AND  b.No =a.no)"
                   +" AND abs(a.Qty)>0";
		return this.findBySql(sql,new Object[] { custCode });
		
	}
	/**
	 * 发货申请明细——新增（仓库发货申请用）
	 * @author	created by zb
	 * @date	2019-4-10--下午5:35:56
	 * @param page
	 * @param itemAppDetail
	 * @return
	 */
	public Page<Map<String, Object>> findSendDetailBySql(
			Page<Map<String, Object>> page, ItemAppDetail itemAppDetail) {
		String sql = "select 0 as IsCheck,a.pk,a.no,a.ItemCode,fa.Descr FixAreaDesc,a.Qty,isnull(ir.Qty, 0) ReqQty, " +
					"i.Descr ItemDescr,i.SqlCode,b.Descr SqlDescr,a.SendQty SendQtyDescr,a.Reqpk Refpk, " +
					"a.sendQty,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog  " +
					"from tItemAppDetail a " +
					"left outer join tItem i on a.ItemCode=i.Code " +
					"left outer join tFixArea fa on a.FixAreaPK=fa.PK " +
					"left outer join tItemReq IR on IR.pk=a.ReqPK " +
					"left join tBrand b on b.Code=i.SqlCode " +
					"where a.no=? " +
					"and a.Qty-a.SendQty <> 0 " +
					"and a.PK not in ('"+itemAppDetail.getPks().replace(",", "','")+"') ";
		return this.findPageBySql(page, sql, new Object[]{itemAppDetail.getNo()});
	}

}

