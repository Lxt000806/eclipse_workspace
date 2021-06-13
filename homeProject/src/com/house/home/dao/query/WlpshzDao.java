package com.house.home.dao.query;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.home.entity.basic.Item;
import com.house.home.entity.insales.Purchase;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.org.apache.xpath.internal.operations.And;

@Repository
@SuppressWarnings("serial")
public class WlpshzDao extends BaseDao{
	
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page,Item item) {
		List<Object> list = new ArrayList<Object>();
		String sql = "  exec pWlpshz ?,? ";
		list.add(new Timestamp(
				DateUtil.startOfTheDay(item.getDateFrom()).getTime()));
		list.add(new Timestamp(
				DateUtil.endOfTheDay(item.getDateTo()).getTime()));
		//List<Map<String , Object>> list2=new ArrayList<Map<String ,Object>>();
		//list2=findListBySql(sql, "2018-05-01","2018-05-02");
		//System.out.println(list2);
		page.setResult(findListBySql(sql, list.toArray())); 			
		page.setTotalCount(page.getResult().size());
		return page;
	}
	
	public Page<Map<String, Object>> findTilePageBySql(
			Page<Map<String, Object>> page, String  date) {
		List<Object> list = new ArrayList<Object>();
							   
		String sql = " select * from ( select ia.Remarks,ct.Address,ia.ConfirmDate,u.descr uomdescr,a.No iasno,ia.No iano,c.ItemCode,d.descr itemDescr " +
				" ,c.qty,c.Qty*d.PerWeight allWeight,a.Date sendDate ,ia.CustCode,a.ArriveAddress ,e.descr item2descr,f.descr item3descr," +
				" e.code item2Code,f.Code item3Code," +
				" dateDiff(hour,ia.ConfirmDate,a.Date) SendDay" +
				" from tItemSendBatch isb" +
				" inner join tItemAppSend a on a.SendBatchNo =isb.No" +
				" inner join titemapp ia on ia.no=a.IANo and ia.SendType='2' " +
				" inner join tItemAppDetail c on c.No=ia.No" +
				" inner join titem d on d.Code=c.ItemCode " +
				" inner join titemtype2 e on e.code =d.ItemType2 " +
				" inner join titemType3 f on f.Code=d.ItemType3" +
				" left join tUom u on u.code = d.Uom " +
				" left join tCustomer ct on ct.code =ia.CustCode" +
				" where e.ItemType12='11' ";
		if(date !=null){
			sql+=" and a.date>='"+date+"' and a.date<=dateAdd(d,1,'"+date+"') ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ") a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ") a ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> findToiletPageBySql(
			Page<Map<String, Object>> page, String date) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select  * from(select ia.Remarks,ct.address,ia.ConfirmDate ,ias.No iasno,ia.no iano,iad.ItemCode,iad.Qty,ias.Date sendDate,d.descr itemDescr ," +
				" iad.Qty*d.PerWeight allWeight,u.descr uomdescr,ia.CustCode,ias.ArriveAddress,it2.descr item2descr,it3.descr item3descr," +
				" dateDiff(hour,ia.ConfirmDate,ias.Date) SendDay" +
				" from tItemSendBatch isb" +
				" inner join tItemAppSend ias on ias.SendBatchNo=isb.No" +
				" inner join tItemAppSendDetail iasd on iasd.No=ias.No" +
				" inner join tItemAppDetail iad on iad.PK=iasd.RefPk" +
				" inner join tItemApp ia on ia.No=iad.No and ia.SendType='2' " +
				" inner join tItem d on d.Code=iad.ItemCode" +
				" inner join tItemType2 it2 on it2.Code=d.ItemType2 " +
				" inner join titemtype3 it3 on it3.code=d.ItemType3 " +
				" left join tUom u on u.code=d.uom " +
				" left join tCustomer ct on ct.code=ia.CUstCode " +
				" where ia.ItemType1='ZC'  " +
				" and ( ( select  ','+QZ+',' from    tXTCS where   id = 'cabinet') like '%,'+rtrim(it3.Code)+',%'  " +
				" or  ( select  ','+QZ+',' from    tXTCS where   id = 'toilet') like'%,'+rtrim(it3.Code)+',%' "+
                "    )";
			if(date !=null){
				sql+=" and ias.date>='"+date+"' and ias.date<=dateAdd(d,1,'"+date+"') ";
			}
			if (StringUtils.isNotBlank(page.getPageOrderBy())){
				sql += ") a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
			}else{
				sql += ") a ";
			}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> findFloorPageBySql(
			Page<Map<String, Object>> page, String date) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select  * from (select ct.address,，ia.ConfirmDate,u.descr uomDescr,a.No iasno,ia.No iano,c.ItemCode,d.descr itemDescr ," +
				" c.Qty,c.Qty*d.PerWeight allweight,a.Date sendDate ,f.descr item3descr," +
				" e.code item2Code,f.Code item3Code ,ia.CustCode,a.ArriveAddress,e.descr item2descr,ia.Remarks, " +
				" dateDiff(hour,ia.ConfirmDate,a.Date) SendDay" +
				" from tItemSendBatch isb" +
				" inner join tItemAppSend a on a.SendBatchNo =isb.No" +
				" inner join titemapp ia on ia.no=a.IANo and ia.SendType='2' " +
				" inner join tItemAppDetail c on c.No=ia.No" +
				" inner join titem d on d.Code=c.ItemCode" +
				" inner join titemtype2 e on e.code =d.ItemType2 " +
				" inner join titemType3 f on f.Code=d.ItemType3 " +
				" left join tUom u on u.code=d.uom " +
				" left join tCustomer ct on ct.code=ia.CustCode " +
				" where  ( select  ','+QZ+',' from    tXTCS where   id = 'floor') like'%,'+rtrim(f.Code)+',%'";
			if(date !=null){
				sql+=" and a.date>='"+date+"' and a.date<=dateAdd(d,1,'"+date+"') ";
			}
			if (StringUtils.isNotBlank(page.getPageOrderBy())){
				sql += ") a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
			}else{
				sql += ") a ";
			}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	
}
