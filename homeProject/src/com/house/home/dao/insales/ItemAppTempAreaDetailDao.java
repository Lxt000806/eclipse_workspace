package com.house.home.dao.insales;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.insales.ItemAppTempAreaDetail;

@SuppressWarnings("serial")
@Repository
public class ItemAppTempAreaDetailDao extends BaseDao {

	/**
	 * ItemAppTempAreaDetail分页信息
	 * 
	 * @param page
	 * @param itemAppTempAreaDetail
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemAppTempAreaDetail itemAppTempAreaDetail) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from tItemAppTempAreaDetail a where 1=1 ";

    	if (itemAppTempAreaDetail.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(itemAppTempAreaDetail.getPk());
		}
    	if (StringUtils.isNotBlank(itemAppTempAreaDetail.getAtano())) {
			sql += " and a.ATANo=? ";
			list.add(itemAppTempAreaDetail.getAtano());
		}
    	if (StringUtils.isNotBlank(itemAppTempAreaDetail.getItcode())) {
			sql += " and a.ITCode=? ";
			list.add(itemAppTempAreaDetail.getItcode());
		}
    	if (itemAppTempAreaDetail.getQty() != null) {
			sql += " and a.Qty=? ";
			list.add(itemAppTempAreaDetail.getQty());
		}
    	if (itemAppTempAreaDetail.getDispSeq() != null) {
			sql += " and a.DispSeq=? ";
			list.add(itemAppTempAreaDetail.getDispSeq());
		}
    	if (itemAppTempAreaDetail.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(itemAppTempAreaDetail.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(itemAppTempAreaDetail.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(itemAppTempAreaDetail.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(itemAppTempAreaDetail.getExpired()) || "F".equals(itemAppTempAreaDetail.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(itemAppTempAreaDetail.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(itemAppTempAreaDetail.getActionLog());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Pk";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, String iatNo, String custCode) {
		String sql = "SELECT a.*,c.Descr,d.Descr uomDescr,c.remark remarks,c.ItemType2, "
			    	+" cast( ROUND(CASE WHEN a.calType != '2' THEN a.Qty ELSE ISNULL(a.Qty,0) * ISNULL(b.Area,0)*b.InnerareaPer END ,1)  as   numeric(20,1)) sugQty "
			    	+" FROM  dbo.tItemAppTempAreaDetail a "
				    +" LEFT JOIN dbo.tItem c ON c.Code=a.itCode "
				    +" LEFT JOIN  dbo.tUOM d ON d.Code=c.Uom "
					+"LEFT  JOIN  (SELECT a.no,b.Area,c.InnerareaPer InnerAreaPer FROM  dbo.tItemAppTempArea a,dbo.tCustomer b " +
					" left join tcustType c on c.Code=b.CustType "
				    +" WHERE a.IATNo=? AND  b.Code=? AND b.Area*c.InnerAreaPer BETWEEN a.FromArea AND ToArea) b ON 1=1" 
				    +"  where a.ATANo=b.no and c.Expired='F' order by a.dispSeq";
		return this.findPageBySql(page, sql, new Object[]{iatNo,custCode});
	}
	
	public Page<Map<String,Object>> findConfItemTypePageBySql(Page<Map<String,Object>> page, String batchCode, String custCode) {
		String sql = " select a.code confCode,a.descr," +
				" case when isnull(" +
				"				(select COUNT(1) isConfirm  from tCustItemConfirm cdt " +
				"					where cdt.ConfItemType=a.Code"+
				"    				and CustCode= ? " +
				"				)" +
				"			,0) >0 then '1' else '0' end isConfirm 	" +
				" from tConfItemType a " +
				" left join tAppItemTypeBatchDetail c on c.ConfItemType = a.Code" +
				" where c.AppItemTypeBatch = ? " +
				" and exists ( " +
				" 		select 1 from tConfItemTypeDt in_a " +
				" 		where in_a.ConfItemType=a.Code " +
				"		and exists (" +
				"				select in_c.itemType2,in_c.ItemType3 from tItemReq in_b " +
				"				left join tItem in_c on in_c.Code=in_b.ItemCode" +
				"				where in_b.CustCode= ? and in_b.ItemType1='ZC'" +
				"			 	and in_c.ItemType2=in_a.ItemType2 " +
				"			 	and ( in_a.ItemType3='' or isnull(in_a.ItemType3,in_c.itemType3) =in_c.itemType3)" +
				"		) and (" +
				"				not exists (select 1 from tXTCS where id = 'ItemConfCtrl' and qz = '2') or " +
				"				exists (select 1 from tCustItemConfirm in_in_a where in_in_a.ConfItemType = in_a.ConfItemType and in_in_a.ItemConfStatus = '2'" +
				"					and in_in_a.CustCode = ?" +
				"				)" +
				"			)" +
				" )";
		return this.findPageBySql(page, sql, new Object[]{custCode, batchCode, custCode, custCode});
	}
	
	public Page<Map<String,Object>> findConfItemCodePageBySql(Page<Map<String,Object>> page, 
			String confCode, String custCode,String flag,String m_umStatus) {
		String sql = " select b.code itCode,b.descr,c.descr uomDescr,a.qty-case when e.preQty='' then 0" +
				"  else isnull( e.preQty,0) end needQty,d.descr FixAreaDescr,a.pk from tItemReq a " +
				" left join tFixArea d on d.pk=a.FixAreaPk " +
				" left join tItem b on b.Code=a.ItemCode " +
				" left join tUom c on c.code=b.uom " +
				" left join (" +
				"	select a.CustCode,b.itemcode,sum(b.Qty) preQty from tItemPreApp a" +
				" 	left join tItemPreAppDetail b on b.no=a.No" +
				" 	where 1=1 " ;
				if(StringUtils.isNotBlank(m_umStatus)){
					if("A".equals(m_umStatus)){
						sql+=" and a.status <> '6'  ";
					}else{
						sql+=" and a.status not in ('1','6')  ";
					}
				}else{
					sql+=" and a.status not in ('6')  ";
				}
				sql += " and not exists (select 1 from tItemApp in_a " +
						" where  in_a.PreAppNo=a.No " +
						" and in_a.Status = 'CANCEL')" +
				" 	group by b.itemcode,a.CustCode " +
				" ) e on e.custCode= ? and a.itemCode=e.ItemCode" +
				" where a.ItemType1='ZC' and a.CustCode= ?  and exists (" +
				" 			select 1 from tConfItemType in_a" +
				" 			left join tConfItemTypeDt in_b on in_b.ConfItemType=in_a.Code" +
				" 			where in_a.code in ('"+confCode.replaceAll(",", "','")+"') " +
				" 			and in_b.ItemType2=b.ItemType2 " +
				" 			and (in_b.ItemType3='' or isnull(in_b.ItemType3,b.ItemType3) = b.ItemType3)" +
				" ) and not exists (" +
				"		select 1 from tItemAppDetail in_c " +
				" 		left join titemApp in_d on in_d.no =in_c.no " +
				"		where in_c.ReqPK=a.PK and in_d.Status <> 'CANCEL'" +
				" ) ";
				if(StringUtils.isNotBlank(flag)){
					if("1".equals(flag)){
						sql+=" and a.isOutSet = '0'";
					}else{
						sql+=" and a.isOutSet = '1'";
					}
				}
		return this.findPageBySql(page, sql, new Object[]{custCode,custCode});
	}
	
	public Page<Map<String,Object>> findCheckAppItemAllPageBySql(Page<Map<String,Object>> page
		 	, String itemAppCodes, String itemCode ,String custCode, String isSetItem) {
		String sql = "select f.descr from tAppItemTypeBatch a " +
			" inner join tAppItemTypeBatchDetail b on a.Code=b.AppItemTypeBatch" +
			" inner join tConfItemType c on c.Code=b.ConfItemType" +
			" inner join tconfitemtypedt d on d.ConfItemType=c.Code" +
			" inner join titem f on f.ItemType2=d.ItemType2 and (d.itemType3='' or isnull(d.ItemType3,f.ItemType3)=f.ItemType3)  " +
			" where a.Code= (select top 1 in_a.Code from tAppItemTypeBatch in_a " +
			" inner join tAppItemTypeBatchDetail in_b on in_a.Code=in_b.AppItemTypeBatch" +
			" inner join tConfItemType in_c on in_c.Code=in_b.ConfItemType" +
			" inner join tconfitemtypedt in_d on in_d.ConfItemType=in_c.Code" +
			" inner join titem in_f on in_f.ItemType2=in_d.ItemType2 and (in_d.itemType3='' or isnull(in_d.ItemType3,in_f.ItemType3)=in_f.ItemType3)" +
			" where  in_a.InfoAppAll='1' and in_f.code= ?  and in_a.isSetItem = ? )" +
			" and f.Code not in ('"+itemAppCodes.replaceAll(",", "','")+"') " +
			" and  exists (select in_g.ItemCode,in_i.Descr from tItemreq in_g " +
			"		left join tItem in_i on in_i.code=in_g.ItemCode" +
			" where in_g.CustCode= ?  " +
			"			 and in_g.ItemType1='zc' and f.Code=in_g.ItemCode and " +
			" in_g.IsOutSet <> ?  and in_g.SendQty=0 and in_g.Qty<>0 )" ;

		return this.findPageBySql(page, sql, new Object[]{itemCode, isSetItem, custCode, isSetItem});
	}
	
	
	
}

