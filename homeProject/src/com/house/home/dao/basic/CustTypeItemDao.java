package com.house.home.dao.basic;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
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
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.commons.utils.XmlConverUtil;
import com.house.home.entity.basic.CustTypeItem;
import com.house.home.entity.project.ProgCheckPlan;
import com.house.home.entity.basic.Item;


@SuppressWarnings("serial")
@Repository
public class CustTypeItemDao extends BaseDao{
	
	/**
	 * 主页面
	 * @param page
	 * @param custTypeItem
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, CustTypeItem custTypeItem) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select * from (select a.*,b.descr itemDescr,c.descr itemType1Descr,x1.note custtypedescr,x2.NOTE discamtcalctypedescr,x3.NOTE FixAreaTypeDescr " +
				" from tCustTypeItem a  " +
				" left join tItem b on b.code=a.itemCode " +
				" left join tItemType1 c on c.code=b.itemType1 " +
				" left join tXtdm x1 on x1.cbm=a.CustType and x1.Id='CustType' " +
				" left join tXTDM x2 on x2.CBM=a.DiscAmtCalcType and x2.ID='GIFTDACALCTYPE' " +
				" left join tXTDM x3 on x3.CBM=a.FixAreaType and x3.ID='SetItemFixArea' " +
				" where 1=1  ";
		
		if(StringUtils.isNotBlank(custTypeItem.getItemCode())){
			sql+=" and a.itemcode= ? ";
			list.add(custTypeItem.getItemCode());
		}
		if(StringUtils.isNotBlank(custTypeItem.getCustType())){
			sql += " and a.custType in " + "('"+custTypeItem.getCustType().replaceAll(",", "','")+"')";
		}
		if(StringUtils.isNotBlank(custTypeItem.getSupplCode())){
			sql+=" and b.supplCode= ? ";
			list.add(custTypeItem.getSupplCode());
		}
		if(StringUtils.isNotBlank(custTypeItem.getItemType1())){
			sql+=" and c.code = ? ";
			list.add(custTypeItem.getItemType1());
		}
		if(StringUtils.isNotBlank(custTypeItem.getItemRight())){
				sql += " and c.Code in " + "('"+custTypeItem.getItemRight().replaceAll(",", "','")+"')";
		}
		if (StringUtils.isBlank(custTypeItem.getExpired())
				|| "F".equals(custTypeItem.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ")a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ")a order by a.pk desc";
		}
		System.out.println(sql+list);
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 新增
	 * @param custTypeItem
	 */
	public void doSaveCustTypeItem(CustTypeItem custTypeItem) {
		String sql = "insert into dbo.tCustTypeItem " +
				"( CustType ,ItemCode ,Price ,ProjectCost ,Remark ,LastUpdate ,LastUpdatedBy ,Expired ,ActionLog ,DiscAmtCalcType,FixAreaType ) " +
				"  VALUES (?,?,?,?,?,getDate(),?,'F','ADD',?,?) ";
		this.executeUpdateBySql(sql, new Object[]{custTypeItem.getCustType(),custTypeItem.getItemCode(),custTypeItem.getPrice(),
				custTypeItem.getProjectCost(),custTypeItem.getRemark(),custTypeItem.getLastUpdatedBy(),custTypeItem.getDiscAmtCalcType(),custTypeItem.getFixAreaType()});
	}
	
	/**
	 * 根据客户类型获取客户类型编码
	 * @param custTypeDescr
	 * @return
	 */
	public String getCustTypeDescr(String custType){
		String sql=" select Desc1 from tcusttype where Code=? ";
		
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{custType});
		
		if (list!=null && list.size()>0){
			 return list.get(0).get("Desc1").toString();
		}
		return "";
	}
	
	/**
	 * 批量导入调过程
	 * @param custTypeItem
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result doSaveBatch (CustTypeItem custTypeItem) {
		Assert.notNull(custTypeItem);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pCustTypeItem(?,?,?)}");
			call.registerOutParameter(1, Types.INTEGER);
			call.registerOutParameter(2, Types.NVARCHAR);
			call.setString(3, custTypeItem.getCustTypeItemDetailXml());
			call.execute();
			result.setCode(String.valueOf(call.getInt(1)));
			result.setInfo(call.getString(2));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	
	public boolean hasExist(String  itemCode, String custType, String remark){ 
		String sql=" select 1 from tCustTypeItem where itemcode= ? and custType= ? and remark= ? and expired = 'F' ";
		
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{itemCode,custType,remark==null?"":remark});
		
		if (list!=null && list.size()>0){
			 return true;
		}
		return false;
	}
	
	public boolean hasExists(String  itemCode, String custType, String remark,Integer pk){ 
		String sql=" select 1 from tCustTypeItem where itemcode= ? and custType= ? and remark= ? and expired = 'F' and pk <> ? ";
		
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{itemCode,custType,remark,pk});
		
		if (list!=null && list.size()>0){
			 return true;
		}
		return false;
	}

	public Page<Map<String, Object>> getItemBySqlCode(
			Page<Map<String, Object>> page, Item item) {
		List<Object> list = new ArrayList<Object>();

		String sql="SELECT ct.pk custtypeitempk,ct.custType, a.Expired,ltrim(rtrim(a.ItemType2)) ItemType2,ltrim(rtrim(a.SqlCode)) ItemType3, " 
				+" a.Code,a.Descr,b.Descr SqlCodeDescr,f.Descr ItemType3Descr,c.Descr SupplCodeDescr,a.SizeDesc, " 
				+" a.Color,d.Descr Uomdescr,a.Cost,a.MarketPrice,ct.Price,a.IsFixPrice,e.Note IsFixPricedescr," 
				+" a.Remark itemRemark ,ct.Remark,g.Descr ItemType2Descr,a.CommiType,a.model,isnull(FixAreaType,'1')FixAreaType,  " 
				+" h.note FixAreaTypeDescr " 
				+" FROM  dbo.tCustTypeItem ct "
				+" LEFT JOIN dbo.tItem a ON ct.ItemCode=a.Code "
				+" left outer join tBrand b on b.Code=a.SqlCode " 
				+" left outer join tSupplier c on c.Code=a.SupplCode " 
				+" left outer join tUom d on a.Uom=d.Code " 
				+" left outer join tXTDM e on a.IsFixPrice=e.CBM and e.ID='YESNO' "
				+" left outer join tItemType3 f on a.ItemType3=f.Code " 
				+" left outer join tItemType2 g on a.ItemType2=g.Code " 
				+" left outer join tXTDM h on ct.FixAreaType=h.CBM and h.ID='SetItemFixArea' "
				+" where ct.Expired='F' and a.Expired='F' ";  
		if(StringUtils.isNotBlank(item.getCustType())){
	    	sql+=" and ct.custType=? ";
	    	list.add(item.getCustType());
	    }     
		if (item.getPrePlanAreaPk()!=null && item.getPrePlanAreaPk()>0 && StringUtils.isNotBlank(item.getCustType())) {
			  sql += " and ((case (select FixAreaType from tPrePlanArea where PK=?) " +
			  		" when '1'  then '2' else '3' end = ct.FixAreaType ) or (ct.FixAreaType='1' and ct.custType= ? )) ";
			list.add(item.getPrePlanAreaPk());
			list.add(item.getCustType());
		  }
	    if(StringUtils.isNotBlank(item.getItemType1())){
	    	sql+=" and ltrim(rtrim(a.ItemType1))=? ";
	    	list.add(item.getItemType1());
	    }
	    if(StringUtils.isNotBlank(item.getItemType2())){
	    	sql+=" and ltrim(rtrim(a.ItemType2))=? ";
	    	list.add(item.getItemType2());
	    }
	    if(StringUtils.isNotBlank(item.getSqlCode())){
	    	sql+=" and a.SqlCode=? ";
	    	list.add(item.getSqlCode().trim());
	    //	sql+=" order by a.DispSeq,a.Descr DESC ";
	    //	return this.findPageBySql(page, sql, list.toArray());
	    }
	    if(StringUtils.isNotBlank(item.getCode())){
		  sql+="and a.Code=? ";
		  list.add(item.getCode().trim());
	  }
	   if(StringUtils.isNotBlank(item.getDescr())){
		  sql+="and a.Descr like ? ";
		  list.add("%"+item.getDescr()+"%");
	  }
	  if(StringUtils.isNotBlank(item.getCustCode())){
			sql+=" and (c.isGroup = '1' or exists (select 1 from tCustomer in_a " +
					"			left join tBuilder in_b on in_b.code = in_a.BuilderCode " +
					" 			left Join tRegion in_c on in_c.code= in_b.regionCode " +
					"			where exists ( select 1 from tSupplCmp in_d where in_d.SupplCode = c.Code and in_d.CmpCode = in_c.CmpCode ) " +
					" and in_a.Code = ?  " +
					"  ))";
			list.add(item.getCustCode());
	  }
	  if (item.getPrePlanAreaPk()!=null && item.getPrePlanAreaPk()>0 ) {
		  sql += " and ((case (select FixAreaType from tPrePlanArea where PK=?) when '1'  then '2' else '3' end = ct.FixAreaType ) or ct.FixAreaType='1' ) ";
		list.add(item.getPrePlanAreaPk());
	  }
	   sql+=" order by a.Descr  ";
       return this.findPageBySql(page, sql, list.toArray());
	}

	public Page<Map<String, Object>> findItemPageBySql(
			Page<Map<String, Object>> page, Item item) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select *from( ";
		if("1".equals(item.getIsDescrWithPrice())){
			sql+="select a.descr+','+cast(ct.price as varchar(20))+'元' descr, ";
		}else{
			sql+="select a.descr, ";
		}
		sql+=	"  ct.pk, cast(ct.pk as varchar(20)) custtypeitempk,ct.price, ct.ProjectCost,ct.Remark,a.Code,a.ItemSize,a.SizeDesc,a.BarCode,a.RemCode,a.ItemType1,i1.Descr ItemType1Descr,"
				+ "a.ItemType2,i2.Descr ItemType2Descr,a.ItemType3,i3.Descr ItemType3Descr,"
				+ "a.SqlCode,b.Descr SqlDescr,a.Uom,u.Descr UomDescr,a.SupplCode,s.Descr SplCodeDescr,"
				+ "a.Model,a.Color,a.CommiType,d.NOTE CommiTypeDescr,a.MarketPrice,a.IsFixPrice,"
				+ "p.Note IsFixPriceDescr,a.Cost,a.AvgCost,a.AllQty,a.ActionLog,dbo.fGetPurQty(a.Code,'') PurQty,dbo.fGetUseQty(a.Code,'','') UseQty,"
				+ " a.ProjectCost oldProjectCost "
				+ " FROM  dbo.tCustTypeItem ct "
				+ " LEFT JOIN dbo.tItem a ON ct.ItemCode=a.Code "
				+ " left outer join tItemType1 i1 on a.ItemType1=i1.Code "
				+ " left outer join tItemType2 i2 on a.ItemType2=i2.Code "
				+ " left outer join tItemtype3 i3 on a.ItemType3=i3.Code "
				+ " left outer join tBrand b on a.SqlCode=b.Code "
				+ " left outer join tXTDM d on a.CommiType=d.CBM and d.ID='COMMITYPE' "
				+ " left outer join tXTDM p on a.IsFixPrice=p.CBM and p.ID='ISFIXPRICE' "
				+ " left outer join tSupplier s on a.SupplCode=s.Code "
				+ " left outer join tUom u on a.Uom = u.Code "
				+ " where  a.Expired='F' ";
		if(item.getCustTypeItemPk()!=null){
			sql += " and ct.pk=? ";
			list.add(item.getCustTypeItemPk());
		}
		if(StringUtils.isNotBlank(item.getPk())){
			sql += " and (cast(ct.pk as varchar(20)) like ? or a.Descr like ?)   ";
			list.add("%" + item.getPk() + "%");
			list.add("%" + item.getPk() + "%");
		}
		if(StringUtils.isNotBlank(item.getCustType())){
			sql += " and ct.custType=? ";
			list.add(item.getCustType());
		}
    	if (StringUtils.isNotBlank(item.getCode())) {
			sql += " and a.Code=? ";
			list.add(item.getCode());
		}
    	if (StringUtils.isNotBlank(item.getDescr())) {
			sql += " and a.Descr like ? ";
			list.add("%"+item.getDescr()+"%");
		}
    	if (StringUtils.isNotBlank(item.getRemCode())) {
			sql += " and a.RemCode=? ";
			list.add(item.getRemCode());
		}
    	if (StringUtils.isNotBlank(item.getItemType1())) {
			sql += " and a.ItemType1=? ";
			list.add(item.getItemType1().trim());
		}
    	if (StringUtils.isNotBlank(item.getItemType2())) {
			sql += " and a.ItemType2=? ";
			list.add(item.getItemType2());
		}
    	if (StringUtils.isNotBlank(item.getItemType3())) {
			sql += " and a.ItemType3=? ";
			list.add(item.getItemType3());
		}
    	if (StringUtils.isNotBlank(item.getBarCode())) {
			sql += " and a.BarCode=? ";
			list.add(item.getBarCode());
		}
    	if (StringUtils.isNotBlank(item.getSqlCode())) {
			sql += " and a.SqlCode=? ";
			list.add(item.getSqlCode());
		}
    	if (StringUtils.isNotBlank(item.getSupplCode())) {
			sql += " and a.SupplCode=? ";
			list.add(item.getSupplCode());
		}
    	if (StringUtils.isNotBlank(item.getModel())) {
			sql += " and a.Model like ? ";
			list.add("%"+item.getModel()+"%");
		}

    	if (item.getItemSize() != null) {
			sql += " and a.ItemSize=? ";
			list.add(item.getItemSize());
		}
    	if (StringUtils.isNotBlank(item.getSizeDesc())) {
			sql += " and a.SizeDesc like ? ";
			list.add("%"+item.getSizeDesc()+"%");
		}
    	if (item.getPrePlanAreaPk()!=null && item.getPrePlanAreaPk()>0 ) {
			sql += " and( (case (select FixAreaType from tPrePlanArea where PK=?) when '1'  then '2' else '3' end = ct.FixAreaType ) or ct.FixAreaType='1' ) ";
			list.add(item.getPrePlanAreaPk());
		}
    	if(StringUtils.isNotBlank(item.getCustCode())){
			sql+=" and (s.isGroup = '1' or exists (select 1 from tCustomer in_a " +
					"			left join tBuilder in_b on in_b.code = in_a.BuilderCode " +
					" 			left Join tRegion in_c on in_c.code= in_b.regionCode " +
					"			where exists ( select 1 from tSupplCmp in_d where in_d.SupplCode = s.Code and in_d.CmpCode = in_c.CmpCode ) " +
					" and in_a.Code = ?  " +
					"  ))";
			list.add(item.getCustCode());
		}
    	
		if (StringUtils.isBlank(item.getExpired()) || "F".equals(item.getExpired())) {
			sql += " and ct.Expired='F' ";
		}
		sql+=") a ";
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.pk desc";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

	public boolean hasItem(Integer pk,String custType, String itemType1) {
		String sql=" select 1 from tCustTypeItem a  "+
				   " left join tItem b on a.itemCode=b.code " +
				   " where a.pk=?  and a.custType=? and b.itemType1=? and a.expired = 'F' ";
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{pk,custType,itemType1});
		
		if (list!=null && list.size()>0){
			 return true;
		}
		return false;
	}

	public Integer getUniquePk(String itemCode, String custType, String itemType1,String remarks) {
		String sql=" select a.pk from tCustTypeItem a  "+
				   " left join tItem b on a.itemCode=b.code " +
				   " where a.itemCode=? and a.custType=? and b.itemType1=? and a.expired = 'F' ";
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{itemCode,custType,itemType1});
		if (list!=null && list.size()==1){
			 return Integer.parseInt(list.get(0).get("pk").toString());
			
		}
		
		sql = " select a.pk from tCustTypeItem a  "+
		      " left join tItem b on a.itemCode=b.code " +
		      " where a.itemCode=? and a.custType=? and b.itemType1=? and a.remark = ? and a.expired = 'F' ";
		list = this.findBySql(sql, new Object[]{itemCode,custType,itemType1,remarks});
		if (list!=null && list.size()==1){
			 return Integer.parseInt(list.get(0).get("pk").toString());
			
		}
		
		return null;
	}
	
	public String getDiscAmtCalcTypeDescr(String discAmtCalcType){
		String sql=" select NOTE from tXTDM where CBM = ? and ID='GIFTDACALCTYPE' ";
		
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{discAmtCalcType});
		
		if (list!=null && list.size()>0){
			 return list.get(0).get("NOTE").toString();
		}
		return "";
	}
	
	/**
	 * 批量新增调过程
	 * @param custTypeItem
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result doSaveBatchAddNoExcelForProc(CustTypeItem custTypeItem) {
		Assert.notNull(custTypeItem);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pCustTypeItemBatchAddNoExcel(?,?,?,?,?,?,?)}");
			call.registerOutParameter(1, Types.INTEGER);
			call.registerOutParameter(2, Types.NVARCHAR);
			call.setString(3, custTypeItem.getCustType());
			call.setString(4, custTypeItem.getDiscAmtCalcType());
			call.setString(5, XmlConverUtil.jsonToXmlNoHead(custTypeItem.getDetailJson()));
			call.setString(6, custTypeItem.getLastUpdatedBy());
			call.setString(7, custTypeItem.getFixAreaType());
			call.execute();
			result.setCode(String.valueOf(call.getInt(1)));
			result.setInfo(call.getString(2));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	
	public boolean hasExistsICP(String  itemCode, String custType, Double price,Integer pk){ 
		String sql=" select 1 from tCustTypeItem where itemcode= ? and custType= ? and price= ? and expired = 'F' and pk <> ? ";
		
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{itemCode,custType,price,pk});
		
		if (list!=null && list.size()>0){
			 return true;
		}
		return false;
	}
	
	public boolean hasExistsICP(String  itemCode, String custType, Double price){ 
		String sql=" select 1 from tCustTypeItem where itemcode= ? and custType= ? and price= ? and expired = 'F' ";
		
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{itemCode,custType,price});
		
		if (list!=null && list.size()>0){
			 return true;
		}
		return false;
	}
	
	public boolean checkFixAreaType(CustTypeItem custTypeItem){ 
		
		String sql="select 1 from tCustTypeItem a " +
				" where a.ItemCode = ? and a.CustType = ? and " +
				" (" +
				"	(exists " +
				"		(select 1 from tPrePlanArea b " +
				"		where b.pk = ? and " +
				" 			(a.FixAreaType = '1' or (a.FixAreaType = '2' and b.FixAreaType = '1') " +
				" 				or (a.FixAreaType = '3' and b.FixAreaType <>'1' ) " +
				"			) " +
				"		) " +
				" 	) or not exists (select 1 from tPrePlanArea where pk = ?) " +
				" ) ";
		List<Map<String,Object>> list=this.findBySql(sql, 
				new Object[]{custTypeItem.getItemCode(),custTypeItem.getCustType(),
							custTypeItem.getPrePlanAreaPk(),custTypeItem.getPrePlanAreaPk()
							});
		if (list!=null && list.size()>0){
			 return true;
		}
		return false;
	}
	
	public void doBatchDeal(CustTypeItem custTypeItem) {
		String sql = " update tCustTypeItem set Expired ='T' " +
				" where pk in ("+custTypeItem.getPks()+") ";						
		this.executeUpdateBySql(sql, new Object[]{});
	}
}
