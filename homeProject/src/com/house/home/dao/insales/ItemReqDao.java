package com.house.home.dao.insales;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.design.Customer;
import com.house.home.entity.insales.ItemReq;

@SuppressWarnings("serial")
@Repository
public class ItemReqDao extends BaseDao {

	/**
	 * 领料需求列表接口
	 * 
	 * @param page
	 * @param itemReqim
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemReq itemReq) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select a.*,b.descr itemCodeDescr,c.descr itemType2Descr,b.itemType2,fa.Descr FixAreaDescr,fb.Descr uom,"
				+"(a.Qty-a.sendQty) remainQty "
				+"from tItemReq a "
				+"left join tItem b on a.itemCode=b.code "
				+"left join tFixArea fa on a.FixAreaPK=fa.PK "
				+"left join tuom fb on b.uom=fb.code "
				+"left join tItemType2 c on b.itemType2=c.code where 1=1 and a.qty>a.sendQty ";
		
		if (StringUtils.isNotBlank(itemReq.getCustCode())) {
			sql += " and a.CustCode=? ";
			list.add(itemReq.getCustCode());
		}
		if (StringUtils.isNotBlank(itemReq.getItemType2())) {
			sql += " and c.code=? ";
			list.add(itemReq.getItemType2());
		}
		if (StringUtils.isNotBlank(itemReq.getItemCodeDescr())) {
			sql += " and b.descr like ? ";
			list.add("%"+itemReq.getItemCodeDescr()+"%");
		}
    	if (itemReq.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(itemReq.getPk());
		}
    	
    	if (itemReq.getFixAreaPk() != null) {
			sql += " and a.FixAreaPK=? ";
			list.add(itemReq.getFixAreaPk());
		}
    	if (itemReq.getIntProdPk() != null) {
			sql += " and a.IntProdPK=? ";
			list.add(itemReq.getIntProdPk());
		}
    	if (StringUtils.isNotBlank(itemReq.getItemCode())) {
			sql += " and a.ItemCode=? ";
			list.add(itemReq.getItemCode());
		}
    	if (StringUtils.isNotBlank(itemReq.getItemType1())) {
			sql += " and a.ItemType1=? ";
			list.add(itemReq.getItemType1());
		}
    	if (itemReq.getQty() != null) {
			sql += " and a.Qty=? ";
			list.add(itemReq.getQty());
		}
    	if (itemReq.getSendQty() != null) {
			sql += " and a.SendQty=? ";
			list.add(itemReq.getSendQty());
		}
    	if (itemReq.getCost() != null) {
			sql += " and a.Cost=? ";
			list.add(itemReq.getCost());
		}
    	if (itemReq.getUnitPrice() != null) {
			sql += " and a.UnitPrice=? ";
			list.add(itemReq.getUnitPrice());
		}
    	if (itemReq.getBefLineAmount() != null) {
			sql += " and a.BefLineAmount=? ";
			list.add(itemReq.getBefLineAmount());
		}
    	if (itemReq.getMarkup() != null) {
			sql += " and a.Markup=? ";
			list.add(itemReq.getMarkup());
		}
    	if (itemReq.getLineAmount() != null) {
			sql += " and a.LineAmount=? ";
			list.add(itemReq.getLineAmount());
		}
    	if (StringUtils.isNotBlank(itemReq.getRemark())) {
			sql += " and a.Remark=? ";
			list.add(itemReq.getRemark());
		}
    	if (itemReq.getDateFrom() != null){
			sql += " and a.LastUpdate>= ? ";
			list.add(itemReq.getDateFrom());
		}
		if (itemReq.getDateTo() != null){
			sql += " and a.LastUpdate<= ? ";
			list.add(itemReq.getDateTo());
		}
    	if (StringUtils.isNotBlank(itemReq.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(itemReq.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(itemReq.getExpired()) || "F".equals(itemReq.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(itemReq.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(itemReq.getActionLog());
		}
    	if (itemReq.getProcessCost() != null) {
			sql += " and a.ProcessCost=? ";
			list.add(itemReq.getProcessCost());
		}
    	if (itemReq.getDispSeq() != null) {
			sql += " and a.DispSeq=? ";
			list.add(itemReq.getDispSeq());
		}
    	if (itemReq.getIsService() != null) {
			sql += " and a.IsService=? ";
			list.add(itemReq.getIsService());
		}
    	if (itemReq.getIsCommi() != null) {
			sql += " and a.IsCommi=? ";
			list.add(itemReq.getIsCommi());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.IsService,a.DispSeq";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 获取客户材料需求列表接口
	 * 
	 * @param page
	 * @param itemReq
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySqlForClient(Page<Map<String,Object>> page, ItemReq itemReq) {
		List<Object> list = new ArrayList<Object>();

		String sql ="select  a.*, b.descr itemCodeDescr, c.descr itemType2Descr, b.itemType2, fa.Descr FixAreaDescr, fb.Descr uom, "
		        +"( a.Qty - a.sendQty - isnull(d.qty,0) - isnull(e.qty,0) ) remainQty, ( a.sendQty + isnull(d.qty,0) + isnull(e.qty,0) ) applyQty, a.remark remarks, "
		        +" case when f.CustCode is null then '0' else '1' end hasChange, "
		        +" case when g.confirmFlag='1' or h.confirmFlag='1' then '1' else '0' end isConfirm, "
		        +" case when xt.qz = '2' and isnull(g.confirmFlag,'') <> '1' and isnull(h.confirmFlag ,'')<> '1' "
				+" and isnull(i.ItemType2 ,'')<>'' then '0' else '1' end canOrderFlag "
		        +" from    tItemReq a "
		        +" left join ( select  m.CustCode, n.FixAreaPK, n.ItemCode from     tItemChg m, tItemChgDetail n "
		        +" where m.CustCode =? and  m.status = '1' and m.no = n.no group by m.CustCode, n.FixAreaPK, n.ItemCode  "
		        +" ) f on f.Custcode=a.Custcode and f.FixAreaPK=a.FixAreaPK and f.ItemCode=a.ItemCode "
				+"left join tItem b on a.itemCode = b.code "
				+"left join tFixArea fa on a.FixAreaPK = fa.PK "
				+"left join tuom fb on b.uom = fb.code "
				+"left join tItemType2 c on b.itemType2 = c.code "
				+"left join ( select  isnull(sum(case when b.type='S' then a.Qty-isnull(a.SendQty, 0) else 0 end ), 0) qty, a.ReqPK "
		        +" from    tItemAppDetail a , tItemApp b  where   b.Status in ( 'OPEN', 'CONFIRMED','CONRETURN') and b.No = a.No "
		        +" group by a.ReqPk) d on d.ReqPK = a.pk "
		        +"left join ( select  isnull(sum( a.Qty), 0) qty, a.ReqPK "
		        +" from    tItemPreAppDetail a , tItemPreApp b " 
		        +"where  a.no=b.no and  b.Status  in ( '2', '3', '4' )  and not exists ( select 1 from  tItemAppDetail where PreAppDTPK=a.pk) "
		        +"group by a.ReqPk) e on e.ReqPK = a.pk "
		        +"left join ( "
		        +"	select tcitd.ItemType2 itemType2Code,'1' confirmFlag "
				+"	from tCustItemConfirm tcic "
				+"	left join tConfItemTypeDt tcitd on tcitd.ConfItemType = tcic.ConfItemType  "
				+"	where CustCode=? and ItemConfStatus IN ('2','3') and (tcitd.ItemType3 is null or tcitd.ItemType3='') "
				+"	group by tcitd.ItemType2 "
				+") g on g.itemType2Code = c.Code  "
				+"left join (  "
				+"	select tcitd.ItemType2 itemType2Code,tcitd.ItemType3 itemType3Code,'1' confirmFlag  "
				+"	from tCustItemConfirm tcic  "
				+"	left join tConfItemTypeDt tcitd on tcitd.ConfItemType = tcic.ConfItemType  "
				+"	where CustCode=? and ItemConfStatus IN ('2','3')  "
				+"	group by tcitd.ItemType2,tcitd.ItemType3 "
				+") h on h.itemType2Code = c.Code and h.itemType3Code = b.ItemType3 " 
				+" left join tXtcs xt on xt.id = 'ItemConfCtrl'"
				+" left join ( "
				+"		select a.ItemType2 "
				+"		from tConfItemTypeDt a "
				+"		inner join tConfItemType b on a.ConfItemType = b.Code "
				+"		group by a.ItemType2 "
				+" ) i on i.ItemType2 = c.Code "
		        + " where   1 = 1 and a.qty > 0 ";

				//+"left join tItemType2 c on b.itemType2=c.code where 1=1 and a.sendQty<a.qty ";
		//领料申请，有需求领料，发货数量>=需求数量的也允许选择，用于补货。
		//发货数量>=需求数量的摆在最后面(通过增加排序解决，加个计算字段来排序，当发货数量>=需求数量为0，否则为1)。修改有需求材料查询接口

		list.add(itemReq.getCustCode());
		list.add(itemReq.getCustCode());
		list.add(itemReq.getCustCode());
	
		if (StringUtils.isNotBlank(itemReq.getCustCode())) {
			sql += " and a.CustCode=? ";
			list.add(itemReq.getCustCode());
		}
		if (StringUtils.isNotBlank(itemReq.getItemType2())) {
			sql += " and c.code=? ";
			list.add(itemReq.getItemType2());
		}
		if (StringUtils.isNotBlank(itemReq.getItemCodeDescr())) {
			sql += " and b.descr like ? ";
			list.add("%"+itemReq.getItemCodeDescr()+"%");
		}
    	if (itemReq.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(itemReq.getPk());
		}
    	
    	if (itemReq.getFixAreaPk() != null) {
			sql += " and a.FixAreaPK=? ";
			list.add(itemReq.getFixAreaPk());
		}
    	if (itemReq.getIntProdPk() != null) {
			sql += " and a.IntProdPK=? ";
			list.add(itemReq.getIntProdPk());
		}
    	if (StringUtils.isNotBlank(itemReq.getItemCode())) {
			sql += " and a.ItemCode=? ";
			list.add(itemReq.getItemCode());
		}
    	if (StringUtils.isNotBlank(itemReq.getItemType1())) {
			sql += " and a.ItemType1=? ";
			list.add(itemReq.getItemType1());
		}
    	if (itemReq.getQty() != null) {
			sql += " and a.Qty=? ";
			list.add(itemReq.getQty());
		}
    	if (itemReq.getSendQty() != null) {
			sql += " and a.SendQty=? ";
			list.add(itemReq.getSendQty());
		}
    	if (itemReq.getCost() != null) {
			sql += " and a.Cost=? ";
			list.add(itemReq.getCost());
		}
    	if (itemReq.getUnitPrice() != null) {
			sql += " and a.UnitPrice=? ";
			list.add(itemReq.getUnitPrice());
		}
    	if (itemReq.getBefLineAmount() != null) {
			sql += " and a.BefLineAmount=? ";
			list.add(itemReq.getBefLineAmount());
		}
    	if (itemReq.getMarkup() != null) {
			sql += " and a.Markup=? ";
			list.add(itemReq.getMarkup());
		}
    	if (itemReq.getLineAmount() != null) {
			sql += " and a.LineAmount=? ";
			list.add(itemReq.getLineAmount());
		}
    	if (StringUtils.isNotBlank(itemReq.getRemark())) {
			sql += " and a.Remark=? ";
			list.add(itemReq.getRemark());
		}
    	if (itemReq.getDateFrom() != null){
			sql += " and a.LastUpdate>= ? ";
			list.add(itemReq.getDateFrom());
		}
		if (itemReq.getDateTo() != null){
			sql += " and a.LastUpdate<= ? ";
			list.add(itemReq.getDateTo());
		}
    	if (StringUtils.isNotBlank(itemReq.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(itemReq.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(itemReq.getExpired()) || "F".equals(itemReq.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(itemReq.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(itemReq.getActionLog());
		}
    	if (itemReq.getProcessCost() != null) {
			sql += " and a.ProcessCost=? ";
			list.add(itemReq.getProcessCost());
		}
    	if (itemReq.getDispSeq() != null) {
			sql += " and a.DispSeq=? ";
			list.add(itemReq.getDispSeq());
		}
    	if (itemReq.getIsService() != null) {
			sql += " and a.IsService=? ";
			list.add(itemReq.getIsService());
		}
    	if (itemReq.getIsCommi() != null) {
			sql += " and a.IsCommi=? ";
			list.add(itemReq.getIsCommi());
		}
    	if (StringUtils.isNotBlank(itemReq.getIsSetItem())) {
			sql += " and a.IsOutSet=? ";
			list.add(itemReq.getIsSetItem());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by case when a.sendQty<a.qty then 1 else 0 end desc,a.IsService,a.DispSeq";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> findItemReqList(Page<Map<String,Object>> page, ItemReq itemReq) {
		List<Object> list = new ArrayList<Object>();
		String sql = "Select "
				+ "t1.PK  reqpk,t1.CustCode,t1.FixAreaPK,fa.Descr FixAreaDescr,"
				+ "t1.IntProdPK,ip.Descr IntProdDescr,t1.ItemCode,sp.Descr SupplDescr,t1.IsOutSet, "
				+ "i.Descr ItemDescr,t1.ItemType1,s.Descr TypeDescr,rtrim(i.Itemtype2) Itemtype2,t1.Qty, "
				+ "s2.Descr ItemType2Descr,s3.descr itemType3Descr,t1.SendQty,t1.Remark,t1.DispSeq,t1.Cost,t1.unitprice,t1.markup,(t1.LineAmount-isnull(t1.ProcessCost,0)) TmpLineAmount,a.descr uomdescr,  "
				+ " isnull(t1.ProcessCost,0) ProcessCost,t1.LineAmount,b.NOTE IsOutSetDescr,t1.LastUpdate,t1.LastUpdatedBy,i.Expired,t1.ActionLog,i.Uom, "
				+ " (t1.SendQty-t1.Qty)*t1.UnitPrice beflineamount,t1.itemSetNo,its.descr itemSetDescr,t1.CustTypeItemPk, " 
				+ " t1.Algorithm,t1.DoorPK,t1.CutType,fa.PrePlanAreaPK,al.descr AlgorithmDescr,pra.descr PrePlanAreaDescr,x1.note cuttypedescr, "
				+ " t1.SupplPromItemPK,isnull(t1.algorithmPer,1.0) algorithmPer,isnull(t1.algorithmDeduct,0.0) algorithmDeduct "
				+ " from tItemReq t1 "
				+ " left outer join tFixArea fa on t1.FixAreaPK=fa.PK "
				+ " left outer join tIntProd ip on t1.IntProdPK=ip.PK "
				+ " left outer join tItem i on t1.ItemCode=i.Code "
				+ " left outer join tSupplier sp on sp.Code=i.SupplCode and i.Code = t1.ItemCode "
				+ " left outer join tItemType1 s on t1.ItemType1=s.Code "
				+ " left outer join tItemType2 s2 on i.Itemtype2=s2.Code "
				+ " left outer join tItemType3 s3 on i.Itemtype3=s3.Code "
				+ " left outer join tUom a on i.Uom=a.Code"
				+ " left outer join tXTDM b on b.ID='YESNO' and b.CBM=t1.IsOutSet"
				+ " left outer join titemSet its on its.no=t1.itemSetNo "
				+ " left outer join tAlgorithm al on al.code=t1.Algorithm "
				+ " left outer join tPrePlanArea pra on pra.pk=fa.PrePlanAreaPK "
				+ " left outer join txtdm x1 on x1.id='CUTTYPE' and x1.cbm=t1.cuttype "
				+ " where t1.qty <> 0 AND  t1.expired='F'   ";
		if("1".equals(itemReq.getOnlyShowNotEqual())){
			sql+=" and t1.qty<> t1.SendQty ";
			
		}
    	if (StringUtils.isNotBlank(itemReq.getCustCode())) {
			sql += " and t1.CustCode=? ";
			list.add(itemReq.getCustCode());
		}
    	if (StringUtils.isNotBlank(itemReq.getItemType1())) {
			sql += " and t1.ItemType1=? ";
			list.add(itemReq.getItemType1());
			if("JC".equals(itemReq.getItemType1().trim())){
				sql+=" and ip.IsCupboard=? ";
				list.add(itemReq.getIsCupboard());
				
			}
		}	
    	if(StringUtils.isNotBlank(itemReq.getItemType2())){
			sql += " and i.itemtype2 in " + "('"+itemReq.getItemType2().replace(",", "','" )+ "')";
		}
    	if (itemReq.getIsService()!= null) {
			sql += " and t1.IsService=? ";
			list.add(itemReq.getIsService());
		}	    	
    	if (StringUtils.isNotBlank(itemReq.getNo())){
    		sql += " and t1.pk in (select reqPk from tItemAppDetail where no=?) ";
			list.add(itemReq.getNo());
    	}
    	if (StringUtils.isNotBlank(itemReq.getUnSelected())){
    		sql += " and t1.pk not in ("+itemReq.getUnSelected()+") ";
    	}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by t1.IsService,t1.DispSeq";
		}		
		return this.findPageBySql(page, sql, list.toArray());
	}

	public Page<Map<String, Object>> findPageBySql_khxxcx(Page<Map<String, Object>> page,ItemReq itemReq) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from (select a.PK,a.CustCode,a.FixAreaPK,a.BaseItemCode,a.Qty,a.Cost,a.UnitPrice,a.LineAmount,a.Remark,"
			+"a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,a.Material,a.DispSeq,"
			+"c.Descr FixAreaDescr,b.Descr BaseItemDescr,d.Descr Uom,x.note isOutSetDescr "
            +"from tBaseItemReq a "
            +"left join tBaseItem b on b.Code=a.BaseItemCode "
            +"left join tFixArea c on c.Pk=a.FixAreaPK "
            +"left join tUom d on b.Uom=d.Code "
            +"left join tBaseItemType2 e on e.Code = b.BaseItemType2 " //TODO 增加工种分类1、项目名称查询条件 add by zb
            +"left join tWorkType2 f1 on f1.Code = e.OfferWorkType2 " 
            +"left join tWorkType2 f2 on f2.Code = e.MaterWorkType2 "
            +"left join tXTDM x on x.ID='YESNO' and a.isOutSet=x.IBM "
            +"where a.Qty<> 0 ";
    	if (StringUtils.isNotBlank(itemReq.getCustCode())) {
			sql += " and a.CustCode=? ";
			list.add(itemReq.getCustCode());
		}else{
			return null;
		}
    	if (StringUtils.isNotBlank(itemReq.getWorkType1())) {
			sql += " and (f1.WorkType1 = ? or f2.WorkType1 = ?) ";
			list.add(itemReq.getWorkType1());
			list.add(itemReq.getWorkType1());
		}
    	if (StringUtils.isNotBlank(itemReq.getBaseItemDescr())) {
			sql += " and b.Descr like ?";
			list.add("%"+itemReq.getBaseItemDescr()+"%");
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ") a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ") a order by a.FixAreaPk";
		}		
		return this.findPageBySql(page, sql, list.toArray());
	}

	public Page<Map<String, Object>> findPageBySql_zcxq(
			Page<Map<String, Object>> page, ItemReq itemReq) {
		List<Object> list = new ArrayList<Object>();
		if (StringUtils.isBlank(itemReq.getCustCode()) || StringUtils.isBlank(itemReq.getItemType1())) {
			return null;
		}
		String sql = "select * from (select a.*,st.Descr ItemSetDescr,xt.Note isOutSetDescr,"
	        +"c.Descr FixAreaDescr,b.Descr ItemDescr,d.Descr IntProdDescr,f.Descr Uom, "
	        +"e.Descr ItemType1Descr,g.Descr ItemType2Descr,sql.Descr SqlCodeDescr, "
	        +"case when a.LineAmount <> 0 then round(round(a.LineAmount-a.Qty*a.Cost-a.ProcessCost,0)/ a.LineAmount,4)*100 else 0 end ProfitPer, "
	        +"tx.NOTE CommiTypeDescr,h.descr SupplPromDescr "
	        +"from ( "
	        +"select a.pk,a.CustCode,a.FixAreaPK,a.IntProdPK,a.ItemCode,a.ItemType1,a.Qty,a.SendQty,a.Cost,a.UnitPrice,a.SupplPromItemPK,"
	        +"a.BefLineAmount,a.Markup,a.LineAmount,a.Remark,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,a.ProcessCost,"
	        +"a.DispSeq,a.IsService,a.IsCommi,a.DiscCost,a.LineAmount-a.DiscCost aftdiscamount,a.IsOutSet,(a.Qty*a.UnitPrice*a.Markup/100) TmpLineAmount,"
		    +"b.SendQtyByWH,c.SendQtyBySpl,isnull(d.IsCheckOutQty,0) IsCheckOutQty,a.Cost*a.Qty CostAll,a.ItemSetNo,a.PurchaseCost, " ;
		if (!"1".equals(itemReq.getCostRight())) { //主材预算增加提成类型、提成比例，服务性产品、软装、集成预算不用加，删掉b.CommiType modify by zb
        	sql += " '没有权限查看' CommiType,'没有权限查看' CommiPerc " ;
		} else {
			sql += "a.CommiType,a.CommiPerc " ; 
		}
		sql += "from tItemReq a "
			+"left join "
			+"(select sum(case when bb.type='S' then aa.SendQty else -aa.SendQty end) SendQtyByWH,aa.ReqPK "
			+"from  tItemAppDetail aa "
			+"left join tItemApp bb on bb.No=aa.No "
			+"where bb.SendType='2' group by aa.ReqPk) b "
			+"on b.ReqPK=a.PK "
			
			+"left join "
			+"(select sum(case when bb.type='S' then aa.SendQty else -aa.SendQty end) SendQtyBySpl,aa.ReqPK "
			+"from  tItemAppDetail aa "
			+"left join tItemApp bb on bb.No=aa.No "
			+"where bb.SendType='1' group by aa.ReqPk) c "
			+"on c.ReqPK=a.PK "
			
			+"left join "
			+"(select sum(case when bb.Type='S' then aa.Qty else -aa.Qty end) IsCheckOutQty,aa.ReqPK from  tItemAppDetail aa "
			+"left join tItemApp bb on bb.No=aa.No "
			+"left join tPurchase cc on cc.No=bb.PUNo "
			+"where cc.IsCheckOut='1' group by aa.ReqPk) d "
			+"on d.ReqPK=a.PK "
			+"where (a.qty<>0 or a.ProcessCost<>0 or a.SendQty<>0 or a.LineAmount<>0) ";
		if (itemReq.getIsService()!=null) {
			sql += "and a.IsService=? ";
			list.add(itemReq.getIsService());
		}
		sql += "and a.CustCode=? "
			+"and a.ItemType1=? "
            +") a "
            +"left join tItem b on b.Code=a.ItemCode "
            +"left join tBrand sql on sql.code=b.sqlcode "
            +"left join tItemType1 e on e.Code=a.ItemType1 "
            +"left join tItemType2 g on g.Code=b.ItemType2 "
            +"left join tFixArea c on c.Pk=a.FixAreaPK "
            +"left join tIntProd d on d.PK=a.IntProdPK "
            +"left join tUom f on b.Uom=f.Code "
            +"left join tItemSet st on a.itemSetNo=st.no "
            +"left join txtdm xt on a.isoutset=xt.cbm and xt.id='YESNO' "
            +"left join tXTDM tx on tx.CBM=a.CommiType and tx.ID='COMMITYPE' " +
            " left join tSupplPromItem i on i.Pk = a.SupplPromItemPK " +
            " left join tSupplProm h on h.no = i.no "
            +"where a.CustCode=? and a.ItemType1=? ";
	    	list.add(itemReq.getCustCode());
	    	list.add(itemReq.getItemType1());
	    	list.add(itemReq.getCustCode());
	    	list.add(itemReq.getItemType1());
	    	if (StringUtils.isNotBlank(itemReq.getFixareadescr())) {
				sql += "and c.Descr like ? ";
				list.add("%"+itemReq.getFixareadescr()+"%");
			}
			if (StringUtils.isNotBlank(itemReq.getItemdescr())) {
				sql += "and b.Descr like ? ";
				list.add("%"+itemReq.getItemdescr()+"%");
			}
			if (StringUtils.isNotBlank(itemReq.getIntproddescr())) {
				sql += "and d.Descr like ? ";
				list.add("%"+itemReq.getIntproddescr()+"%");
			}
			if (StringUtils.isNotBlank(itemReq.getItemtype2descr())) {
				sql += "and g.Descr like ? ";
				list.add("%"+itemReq.getItemtype2descr()+"%");
			}
			if (StringUtils.isNotBlank(itemReq.getItemType2())) {
				sql += "and b.itemType2=? ";
				list.add(itemReq.getItemType2());
			}
			
			if (StringUtils.isNotBlank(itemReq.getIsCupboard())) {
                sql += "and d.IsCupboard = ? ";
                list.add(itemReq.getIsCupboard());
            }
			
			if (StringUtils.isNotBlank(page.getPageOrderBy())){
				sql += ") a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
			}else{
				sql += ") a order by a.DispSeq";
				if("JC".equals(itemReq.getItemType1())){
					sql+=",a.IntProdPK";
				}else{
					sql+=",a.FixAreaPk";
				}
			}
			return this.findPageBySql(page, sql, list.toArray());
	}

	public Page<Map<String, Object>> findPageBySql_dhfx(
			Page<Map<String, Object>> page, ItemReq itemReq) {
		List<Object> list = new ArrayList<Object>();
		if (StringUtils.isBlank(itemReq.getCustCode())){
			return null;
		}
		String sql = "select * from (select aa.*,i.Descr ITDescr,sql.descr SqlDescr,it1.descr itemtype1descr,"
				+"isnull(bb.QtyCal,0) QtyCal,isnull(bb.ArrivQty,0) ArrivQty,i.itemType1,i.code,"
				+"dbo.fGetKcUseQty(aa.ItemCode) useQty from "
			    +" (select a.CustCode,a.itemcode,sum(a.qty) Qty,sum(a.sendqty) SendQty,sum(a.qty-a.sendqty) Qty_SendQty  from titemreq a"
			    +" where a.CustCode=? ";
		list.add(itemReq.getCustCode());
		if (StringUtils.isNotBlank(itemReq.getItemType1())){
			sql += "and a.itemType1=? ";
			list.add(itemReq.getItemType1());
		}
		sql+=" group by a.CustCode,a.ItemCode"
		    +" ) aa"
		    +" left outer join"
		    +" (select b.ITCode,sum(case when c.Type='R' then -b.QtyCal else b.QtyCal end) QtyCal,sum(case when c.Type='R' then -b.ArrivQty else b.ArrivQty end) ArrivQty from tPurchaseDetail b"
		    +" inner join tPurchase c on b.PUno=c.no"
		    +" where c.Custcode=?"
		    +" group by b.ITCode) bb"
		    +" on aa.itemcode=bb.itcode"
		    +" left outer join tItem i on aa.ItemCode=i.code"
		    +" left outer join tBrand sql on sql.code=i.Sqlcode"
		    +" left outer join tItemType1 it1 on it1.code=i.itemtype1";
		list.add(itemReq.getCustCode());
		if (!"F".equals(itemReq.getExpired())){
			sql += " where aa.Qty_SendQty>0 ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ") a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ") a order by a.itemtype1,a.code";
		}
		System.out.println(sql);
		System.out.println(list);
		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 *从预算导入ItemAppDetail页面
	 *
	 *@param page
	 *@param itemAppDetail
	 */
	public Page<Map<String,Object>> findImportPageBySql(Page<Map<String,Object>> page, ItemReq itemReq,String arr) {
		List<Object> list = new ArrayList<Object>(); 
		String sql = "select * from (select s.code supcode,ic.status itemChgStatus,a.Pk,a.BefLineAmount,a.DispSeq,a.FixAreaPK," +
				"a.IntProdPK,a.ItemCode,a.Qty,a.SendQty ,b.Descr FixAreaDescr " +
				",c.Descr IntProdDescr ,i.ItemType2,i.ItemType1,i.Descr itemdescr,i.SqlCode," +
				" i.cost price ,i.Color ,br.Descr sqlcodedescr ,s.Descr suppldescr,t2.Descr itemDescr2 " +
				" ,a.remark remarks,a.CustCode, k.PurchaseQty, " ;
				if(StringUtils.isNotBlank(itemReq.getPuno())){
					sql+=" p1.unitPrice cost,p1.markup pumarkup,p1.beflineprice pubeflineprice, ";
				}else {
					sql+="i.Cost,  " ;
				}
				sql+="i.AllQty," +
				"icd.no itemchgno,u.Descr uomdescr ," +
				"dbo.fGetPurQty(i.Code,'') PurQty,dbo.fGetUseQty(i.Code,'','') UseQty  " +
				",i.projectCost " +
				"From tItemReq a " +
				   "left outer join tFixArea b on a.FixAreaPK = b.PK "
				  +" left outer join tIntProd c on a.IntProdPK = c.PK "
				  +" left outer join tItem i on a.ItemCode = i.Code "
				  +" left outer join tBrand br on br.Code = i.SqlCode "
				  +" left outer join tSupplier s on i.SupplCode=s.Code "
				  +" left outer join tItemType2 t2 on t2.Code = i.ItemType2 "
				  +" left outer join tUom u on u.Code=i.Uom "
				  + " left join (select MAX(no) no,ReqPK from tItemChgDetail a " +
			     	" left join titemreq b on b.PK=a.ReqPK group by ReqPK)icd on icd.ReqPK=a.PK  " +
			     " left join titemchg ic on ic.No= icd.no   " +
			     	
			     // 增加业务逻辑，只显示需求数量大于采购数量的材料，避免重复下单
			     // 张海洋 20200605
                  "left join (select in_a.CustCode, in_a.ItemCode, " +
                  "           sum(in_a.Qty) ItemReqQty " +
                  "           from tItemReq in_a group by in_a.CustCode, in_a.ItemCode) j " +
                  "    on a.CustCode = j.CustCode and a.ItemCode = j.ItemCode " +
                  "left join (select in_a.CustCode, in_b.ITCode, " +
                  "           sum(case when in_a.type = 'S' then 1 else -1 end * in_b.QtyCal) PurchaseQty " +
                  "           from tPurchase in_a left join tPurchaseDetail in_b " +
                  "           on in_a.No = in_b.PUNo where in_a.Status in('OPEN', 'CONFIRMED') " +
                  "           group by in_a.CustCode, in_b.ITCode) k " +
                  "    on a.CustCode = k.CustCode and a.ItemCode = k.ITCode ";
			  
		
		if (itemReq.getPuno() != "") {
			sql += " LEFT JOIN (select unitPrice,PUNo,ITCode,markup,beflineprice  from tPurchaseDetail group by puno,itcode,unitPrice,markup,beflineprice" +
					" 		) p1 on p1.ITCode=a.ItemCode where 1=1 and a.Qty <> 0" +
			        " and isnull(j.ItemReqQty, 0) > isnull(k.PurchaseQty, 0)" +
					" and p1.puno=? ";
			list.add(itemReq.getPuno());
		}else {
			sql+=" where 1=1 and a.Qty <> 0 " +
					" and isnull(j.ItemReqQty, 0) > isnull(k.PurchaseQty, 0) ";
		}
		if(StringUtils.isNotBlank(itemReq.getItemChgNo())){
			sql += " and icd.no in " + "('"+itemReq.getItemChgNo().replace(",", "','" )+ "')";
		}
		if (itemReq.getCustCode()!=null) {
			sql += " and a.CustCode = ? ";
			list.add(itemReq.getCustCode());
		}
		
		if (itemReq.getItemType1() != null) {
			sql += " and i.ItemType1=? ";
			list.add(itemReq.getItemType1());
		}
		if (StringUtils.isNotBlank(itemReq.getItemType2())) {
			sql += " and i.itemtype2 in " + "('"+itemReq.getItemType2().replace(",", "','" )+ "')";
		}
		
		if(StringUtils.isNotBlank(itemReq.getSupplierCode())){
			sql+=" ) a where " +
					" a.supCode in " + "('"+itemReq.getSupplierCode().replace(",", "','" )+ "')";
		}else{
			sql+=" ) a ";
		}
		if(StringUtils.isNotBlank(arr)){
			sql += " where ( a.itemchgstatus is null or  a.itemchgstatus = '' or a.itemchgstatus='2') and a.pk not in " + "('"+arr.replaceAll(",", "','")+"')";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.DispSeq ";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}

	@SuppressWarnings("unchecked")
	public List<ItemReq> getReqList(String custCode,
			String itemType1) {
		String hql="from ItemReq where custCode=? and itemType1=? and isoutset='0' and qty>0 ";
		List<ItemReq> list=this.find(hql, new Object[]{custCode,itemType1});
		return list;
	}
	
	/**
	 *软装未下单页面
	 *
	 *@param page
	 *@param itemAppDetail notAppQty
	 */
	public Page<Map<String,Object>> findSoftNotAppQueryPageBySql(Page<Map<String,Object>> page, ItemReq itemReq) {
		List<Object> list = new ArrayList<Object>(); 
		String sql = " select it1.descr ItemType1Descr,e.isFee,e.itemtype2,d.descr supDescr,fa.descr FixAreaDescr,f.Descr uomDescr,e.Descr itemDescr ," +
					"a.FixAreaPK,a.ItemCode,a.ItemType1,a.Remark,a.CustCode,a.Qty,a.SendQty,it2.Descr itemtype2descr," +
					"isnull(sum(case when b.Type='S' then c.qty else 0 end),0) AppQty,(a.Qty-a.SendQty-isnull(sum(case when b.Type='S' then c.qty else 0 end),0)) as notAppQty from titemreq  a " +
					" left join tItemAppDetail c on a.PK=c.ReqPK  " +
					" left join tItem e on e.Code = a.ItemCode " +
					" left join tBrand d on d.code=e.sqlCode " +
					" left join tUom f on f.code= e.uom " +
					" left join tFixArea fa on fa.pk = a.FixAreaPK " +
					" left join tItemType1 it1 on it1.code = a.itemtype1 " +
					" left join titemType2 it2 on it2.code = e.ItemType2 " +
					" left join tItemApp b on c.No=b.no and b.Status in  ('CONFIRMED','CONRETURN','OPEN') " +
					" where a.ItemType1='rz' and a.Qty>0 and e.isfee<>'1'   ";
		
		if(StringUtils.isNotBlank(itemReq.getCustCode())){
			sql+=" and a.CustCode=?";
			list.add(itemReq.getCustCode());
		}
		if(StringUtils.isNotBlank(itemReq.getItemType2())){
			sql+=" and e.itemType2 = ?";
			list.add(itemReq.getItemType2());
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " group by  it1.descr,fa.descr ,d.descr,e.descr,f.descr,a.FixAreaPK,a.ItemCode,it2.descr," +
					"a.ItemType1,a.Remark,a.Qty,a.SendQty ,a.CustCode,e.isFee  order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " group by it1.descr,fa.descr ,d.descr,e.descr,f.descr,a.FixAreaPK,a.ItemCode,it2.descr," +
					"a.ItemType1,a.Remark,a.Qty,a.SendQty ,a.CustCode,e.itemtype2,e.isFee," +
					"a.pk having (a.Qty-a.SendQty-isnull(sum(case when b.Type='S' then c.qty else 0 end),0))>0  order by a.pk";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}

	public String getHintString(String custCode) {
		StringBuffer sb = new StringBuffer();
		String sql = "select distinct ItemType1, it1.Descr ItemType1Descr from tItemReq iq"
               + " left join tItemType1 it1 on it1.Code=iq.ItemType1"
               + " where CustCode=? and ItemType1<>'JZ' and iq.Qty>0"
               + " and not exists(select 1 from tItemCheck ic where ic.CustCode=iq.CustCode and ic.ItemType1=iq.ItemType1 and ic.Status='2')";
		List<Map<String,Object>> list = this.findBySql(sql.toLowerCase(), new Object[]{custCode});
		if (list!=null && list.size()>0){
			for (Map<String,Object> map : list){
				sb.append("[");
				sb.append(String.valueOf(map.get("itemtype1descr")));
				sb.append("]");
			}
		}
		if (sb.length() > 0) {
			sb.append("还未进行材料结算！");
		}
		return sb.toString();
	}

	public int getCountNum(String custCode) {
		String sql = "select count(1) ret from tItemReq t where 1=1 and t.Expired='F' and t.qty <> t.sendQty "
                + "and (t.itemtype1='ZC' or t.itemtype1='RZ') and t.CustCode=?";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{custCode});
		if (list!=null && list.size()>0){
			return Integer.parseInt(String.valueOf(list.get(0).get("ret")));
		}
		return 0;
	}

	public Page<Map<String, Object>> findPageBySql_gcwg(
			Page<Map<String, Object>> page, String custCode) {
		String sql = "select a.ItemCode,b.ItemType1,t1.Descr ItemType1Descr,b.ItemType2,t2.Descr ItemType2Descr,a.FixAreaPK,"
             + "t3.Descr FixAreaDescr,b.Descr ItemDescr,b.Uom,u.Descr UnitDescr,"
             + "a.Qty,a.SendQty,a.Qty-a.SendQty as lessQty,a.UnitPrice,a.Cost,a.Remark"
             + " from tItemReq a "
             + " left outer join tItem b on a.ItemCode=b.Code "
             + " left outer join tUOM u on u.Code = b.UOM "
             + " left outer join tItemType1 t1 on b.ItemType1=t1.Code "
             + " left outer join tItemType2 t2 on b.ItemType2=t2.Code "
             + " left outer join tFixArea t3 on a.FixAreaPK=t3.PK "
             + " where 1=1 and a.Expired='F' and a.qty <> a.sendQty and (a.itemtype1='ZC' or a.itemtype1='RZ') and a.CustCode=?"
             + " order by b.ItemType1,a.ItemCode";
		return this.findPageBySql(page, sql, new Object[]{custCode});
	}
	
	public Page<Map<String, Object>> getCountDiscDetail(
			Page<Map<String, Object>> page, Customer customer) {
		String sql = "select CustCode, it1.Descr ItemType1Descr, ip.ItemCode, i.Descr ItemDescr, ip.Qty, u.Descr UomDescr," +
				" ip.Cost, ip.Cost*ip.Qty CostAmount, ip.UnitPrice, ip.BefLineAmount, ip.Markup," +
				" round(ip.BefLineAmount*ip.MarkUp/100,0) TmpLineAmount, ip.ProcessCost, ip.LineAmount, " +
				" ip.ProjectCost*ip.Qty ProjectCostTotal " +
				" from tItemPlan ip" +
				" left join tItem i on ip.ItemCode=i.Code" +
				" left join tItemType1 it1 on i.ItemType1=it1.Code" +
				" left join tUOM u on i.Uom=u.Code" +
				" where (ip.Markup<=50 or (ip.IsService='1' and ip.ProcessCost<0)) and ip.CustCode= ? " +
				" order by i.ItemType1, ip.DispSeq";
		return this.findPageBySql(page, sql, new Object[]{customer.getCode()});
	}
	
}

