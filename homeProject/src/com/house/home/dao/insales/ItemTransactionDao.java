package com.house.home.dao.insales;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.insales.ItemTransaction;
@SuppressWarnings("serial")
@Repository
public class ItemTransactionDao extends BaseDao {

	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, ItemTransaction itemTransaction,UserContext uc) {
		List<Object> list = new ArrayList<Object>();
		String sql ="select *from(select a.PK,a.ITCode,b.Descr,c.Desc1,a.TrsQty,a.Qty,a.Date,a.PrefixCode,e.Desc2 PrefixDesc," 
				  +	"a.Document,a.RefDocument,a.Remarks,a.LastUpdate,d.ZWXM,a.Expired,a.ActionLog,a.LastUpdatedBy ,a.Cost,a.AftAllQty," 
				  +"a.AftCost ,case when a.TrsQty>0 then a.TrsQty else 0 end LkQty,case when a.TrsQty>0 then a.Cost else 0 end LkCost,case when a.TrsQty>0 then round(a.TrsQty*a.Cost,2) else 0 end LkAmount," 
				  +"case when a.TrsQty<=0 then abs(a.TrsQty) else 0 end CkQty,case when a.TrsQty<=0 then a.Cost else 0 end CkCost,case when a.TrsQty<=0 then round(abs(a.TrsQty)*a.Cost,2) else 0 end CkAmount,it2.descr itemType2Descr,sp.Descr SupplCodeDescr, "
				  + "case a.PrefixCode when 'IAS' then wh.No "
				  + "when 'SI' then wh2.No "
				  + "when 'PR' then spl.No "
				  + "when 'BA' then x4.No "
				  + "when 'BT' then x5.No "
				  + "when 'GA' then tgco.No end CheckOutNo,"
				  + "case a.PrefixCode when 'IAS' then t1.Note "
				  + "when 'SI' then t2.Note "
				  + "when 'PR' then t3.Note "
				  + "when 'BA' then t4.Note "
				  + "when 'BT' then t5.Note "
				  + "when 'GA' then t6.Note end CheckOutStatus,"
				  + "case a.PrefixCode when 'IAS' then wh.DocumentNo "
				  + "when 'SI' then wh2.DocumentNo "
				  + "when 'PR' then spl.DocumentNo "
				  + "when 'BA' then x4.DocumentNo "
				  + "when 'BT' then x5.DocumentNo "
				  + "when 'GA' then tgco.DocumentNo end DocumentNo,it3.Descr itemType3Descr " 
				  +"from tItemTransaction a inner join tItem b on a.ITCode=b.Code " 
				  + "inner join tWareHouse c on a.WHCode=c.Code " 
				  + "inner join tCZYBM d on a.LastUpdatedBy=d.CZYBH " 
				  + "left join tItemAppSend x1 on a.PrefixCode='IAS' and a.document=x1.no "
				  + " left join tWHCheckOut wh on x1.WhCheckOutNo=wh.no "
				  + "left join txtdm t1 on t1.id='WHChkOutStatus' and t1.cbm=wh.status "
				  + "left join tSalesInvoice x2 on a.PrefixCode='SI' and a.document=x2.no "
				  + "left join tWHCheckOut wh2 on x2.WhCheckOutNo=wh2.no "
				  + "left join txtdm t2 on t2.id='WHChkOutStatus' and t2.cbm=wh2.status "
				  + "left join tPurchase x3 on a.PrefixCode='PR' and a.document=x3.no "
				  + "left join TSPLCHECKOUT spl on x3.checkOutNo=spl.no "
				  + "left join txtdm t3 on t3.id='SPLCKOTSTATUS' and t3.cbm=spl.status "
				  + "left join tItemBalAdjHeader x4 on a.PrefixCode='BA' and a.document=x4.no "
				  + "left join txtdm t4 on t4.id='BALADJSTATUS' and t4.cbm=x4.status "
				  + "left join tItemTransferHeader x5 on a.PrefixCode='BT' and a.document=x5.no "
				  + "left join txtdm t5 on t5.id='ITEMTRANSTATUS' and t5.cbm=x5.status "
				  + "left join tItemType2 it2 on it2.code=b.itemType2 "
				  +" left outer join tSupplier sp on sp.Code=b.SupplCode " 
				  + " left join tGiftApp x6 on a.PrefixCode='GA' and a.document=x6.No "
				  + " left join tGiftCheckOut tgco on tgco.No = x6.CheckOutNo "
				  + " left join txtdm t6 on t6.id='WHChkOutStatus' and t6.cbm = tgco.status "
				  + "left join tPrefix e on a.PrefixCode=e.Prefix "
				  + " left join tItemType3 it3 on it3.Code = b.ItemType3 "
				  + " where 1=1     ";
		sql+=" and b.ItemType1 in"+ "('"+uc.getItemRight().replaceAll(",", "','")+"')"; 
		if (StringUtils.isNotBlank(itemTransaction.getWhCode())) {
			sql += " and a.whCode=? ";
			list.add(itemTransaction.getWhCode());
		}
    	if (StringUtils.isNotBlank(itemTransaction.getItCode())) {
			sql += " and a.ITCode=? ";
			list.add(itemTransaction.getItCode());
		}
    	if (StringUtils.isNotBlank(itemTransaction.getDescr())) {
			sql += " and b.Descr like ? ";
			list.add("%"+itemTransaction.getDescr()+"%");
		}
    	if (StringUtils.isNotBlank(itemTransaction.getDocument())) {
			sql += " and a.document=? ";
			list.add(itemTransaction.getDocument());
		}
    	if (StringUtils.isNotBlank(itemTransaction.getItemType1())) {
			sql += " and b.itemType1=? ";
			list.add(itemTransaction.getItemType1());
		}
    	if (StringUtils.isNotBlank(itemTransaction.getItemType2())) {
			sql += " and b.itemType2=? ";
			list.add(itemTransaction.getItemType2());
		}
    	if (itemTransaction.getDateFrom()!=null) {
    		sql += " and a.date>= ? ";
    		list.add(itemTransaction.getDateFrom());
		}
    	if (itemTransaction.getDateTo()!=null) {
    		sql += " and a.date< DATEADD(d,1,?) ";
    		list.add(itemTransaction.getDateTo());
		}
    	if (StringUtils.isNotBlank(itemTransaction.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(itemTransaction.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(itemTransaction.getExpired()) || "F".equals(itemTransaction.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(itemTransaction.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(itemTransaction.getActionLog());
		}
    	if(StringUtils.isNotBlank(itemTransaction.getItemType3())){
    		sql += " and it3.Code=? ";
    		list.add(itemTransaction.getItemType3());
    	}
    	sql+=")a";
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.pk desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

}
