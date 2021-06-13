package com.house.home.dao.insales;

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

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.entity.insales.BaseItem;

@SuppressWarnings("serial")
@Repository
public class BaseItemDao extends BaseDao {

	/**
	 * BaseItem分页信息
	 * 
	 * @param page
	 * @param baseItem
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, BaseItem baseItem) {
		List<Object> list = new ArrayList<Object>();

		String sql ="select a.Code,a.Descr,b.Descr BaseItemType1Descr,c.Descr BaseItemType2Descr,d.descr uom,x1.note CategoryDescr, "
			       +"Cost,MarketPrice,OfferPri,Material,Remark,a.Category,rtrim(a.BaseItemType1) BaseItemType1,a.iscalmangefee,a.allowpricerise,a.IsFixPrice "
			       +"from tBaseItem a "
			       +" left outer join tBaseItemType1 b on b.Code=a.BaseItemType1 "
			       +" left outer join tBaseItemType2 c on c.Code=a.BaseItemType2 "
			       +" LEFT JOIN dbo.tUOM d ON a.Uom=d.Code "
			       +" left join txtdm  x1 on x1.cbm=a.Category and x1.id='BASEITEMCAT' "
			       +" left join (" 
			       +"	select BaseItemCode,count(*) num from tBaseItemToCheckItem a group by a.BaseItemCode " 
			       +" )e on e.BaseItemCode = a.Code "// 增加查询条件：未设置基础结算项目 --add by zb
			       +" where a.Expired='F' ";
		
		if (StringUtils.isNotBlank(baseItem.getNoSetBaseCheckItem())&&"T".equals(baseItem.getNoSetBaseCheckItem())) {
			sql += " and isnull(e.num, 0) = 0 ";
		}
		if(StringUtils.isNotBlank(baseItem.getCustomerType())){
			if(StringUtils.isNotBlank(baseItem.getCanUseComBaseItem())){
				if("0".equals(baseItem.getCanUseComBaseItem())){
					sql+=" and ( a.custType = ? or a.CustTypeGroupNo in (select No from tCustTypeGroupDt where CustType = ?) ) ";
				}else{
					sql+=" and ( (a.custType = ? or a.CustTypeGroupNo in (select No from tCustTypeGroupDt where CustType = ?))" +
							" or ((a.custType = '' or a.custType is null) and (a.CustTypeGroupNo is null or a.CustTypeGroupNo = '')) ) ";
				}
				list.add(baseItem.getCustomerType());
				list.add(baseItem.getCustomerType());
			}
		}
		if (StringUtils.isNotBlank(baseItem.getCode())) {
			sql += " and a.Code=? ";
			list.add(baseItem.getCode());
		}
    	if ("1".equals(baseItem.getCustTypeType())){
    		sql += " and a.Category='1' ";
    	}
    	if (StringUtils.isNotBlank(baseItem.getDescr())) {
			sql += " and a.Descr like ? ";
			list.add("%"+baseItem.getDescr()+"%");
		}
    	if (StringUtils.isNotBlank(baseItem.getRemCode())) {
			sql += " and a.RemCode=? ";
			list.add(baseItem.getRemCode());
		}
    	if (StringUtils.isNotBlank(baseItem.getBaseItemType1())) {
			sql += " and a.BaseItemType1=? ";
			list.add(baseItem.getBaseItemType1());
		}
    	if (StringUtils.isNotBlank(baseItem.getBaseItemType2())) {
			sql += " and a.BaseItemType2=? ";
			list.add(baseItem.getBaseItemType2());
		}
    	if (baseItem.getCost() != null) {
			sql += " and a.Cost=? ";
			list.add(baseItem.getCost());
		}
    	if (baseItem.getMarketPrice() != null) {
			sql += " and a.MarketPrice=? ";
			list.add(baseItem.getMarketPrice());
		}
    	if (baseItem.getOfferPri() != null) {
			sql += " and a.OfferPri=? ";
			list.add(baseItem.getOfferPri());
		}
    	if (StringUtils.isNotBlank(baseItem.getRemark())) {
			sql += " and a.Remark=? ";
			list.add(baseItem.getRemark());
		}
    	if (baseItem.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(baseItem.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(baseItem.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(baseItem.getLastUpdatedBy());
		}
    	if (StringUtils.isNotBlank(baseItem.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(baseItem.getActionLog());
		}
    	if (StringUtils.isNotBlank(baseItem.getUom())) {
			sql += " and a.Uom=? ";
			list.add(baseItem.getUom());
		}
    	if (baseItem.getDispSeq() != null) {
			sql += " and a.DispSeq=? ";
			list.add(baseItem.getDispSeq());
		}
    	if (baseItem.getMaterial() != null) {
			sql += " and a.Material=? ";
			list.add(baseItem.getMaterial());
		}
    	if (StringUtils.isNotBlank(baseItem.getCategory())) {
			sql += " and a.Category=? ";
			list.add(baseItem.getCategory());
		}
    	if (StringUtils.isNotBlank(baseItem.getCtrlType())) {
			sql += " and a.CtrlType=? ";
			list.add(baseItem.getCtrlType());
		}
    	if (baseItem.getCtrlOfferCost() != null) {
			sql += " and a.CtrlOfferCost=? ";
			list.add(baseItem.getCtrlOfferCost());
		}
    	if (StringUtils.isNotBlank(baseItem.getPrjType())) {
			sql += " and a.PrjType=? ";
			list.add(baseItem.getPrjType());
		}
    	if (StringUtils.isNotBlank(baseItem.getCustType())) {
    	    if(StringUtils.isNotBlank(baseItem.getCanUseComBaseItem())){
                if("0".equals(baseItem.getCanUseComBaseItem())){
                    sql+=" and ( a.custType = ? or a.CustTypeGroupNo in (select No from tCustTypeGroupDt where CustType = ?) ) ";
                }else{
                    sql+=" and ( (a.custType = ? or a.CustTypeGroupNo in (select No from tCustTypeGroupDt where CustType = ?))" +
                            " or ((a.custType = '' or a.custType is null) and (a.CustTypeGroupNo is null or a.CustTypeGroupNo = '')) ) ";
                }
                list.add(baseItem.getCustType());
                list.add(baseItem.getCustType());
            }
		}
    	if (StringUtils.isNotBlank(baseItem.getIsOutSet())) {
			if("0".equals(baseItem.getIsOutSet())){
				sql+="and a.Category  in ('5', '6', '7') ";
			}else{
				sql+="and a.Category not in ('5', '6', '7') ";
			}
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Code";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> findListPageBySql(Page<Map<String,Object>> page, BaseItem baseItem) {
		List<Object> list = new ArrayList<Object>();

		String sql =" select * from (select x6.note IsOutSetDescr,x5.note AllowPriceRiseDescr,ct.desc1 custTypeDescr,a.Code,a.Descr,isnull(a.RemCode,SPACE(1)) RemCode,isnull(a.DispSeq,0) DispSeq,a.Category,a.BaseItemType1,i1.Descr BaseItemType1Descr,a.BaseItemType2,isnull(i2.Descr,SPACE(1)) BaseItemType2Descr,a.Cost,a.MarketPrice," +
				" a.OfferPri,a.Material,a.Uom,b.Descr UomDescr,isnull(a.Remark,SPACE(1)) Remark,a.LastUpdate,a.LastUpdatedBy,a.Expired, " +
				" a.ActionLog,t.Note CategoryDescr,ProjectPrice, " +
				" a.prjtype,x1.Note PrjTypeDescr,a.IsFixPrice,x2.Note IsFixPriceDescr,a.PerfPer, " +
				" a.PrjCtrlType,x3.Note PrjCtrlTypeDescr,a.OfferCtrl,a.MaterialCtrl,x4.note IsCalMangeFeeDescr,IsCalMangeFee " +
				" from tBaseItem a left outer join tBaseItemType1 i1 on RTrim(a.BaseItemType1)=RTrim(i1.Code) " +
				" left outer join tBaseItemType2 i2 on RTrim(a.BaseItemType2)=RTrim(i2.Code) " +
				" left outer join tUom b on a.Uom=b.Code " +
				" left join tXTDM t on a.Category=t.Cbm and t.Id='BASEITEMCAT' " +
				" left join tXTDM x1 on a.prjtype=x1.cbm and x1.id='BASEITEMPRJTYPE' " +
				" left join tXTDM x2 on a.IsFixPrice=x2.cbm and x2.id='YESNO' " +
				" left join tXTDM x3 on a.PrjCtrlType=x3.cbm and x3.id='PrjCtrlType' " +
				" left join tXTDM x4 on a.IsCalMangeFee=x4.cbm and x4.id='YESNO' " +
				" left join tcustType ct on ct.code=a.custtype " +
				" left join tXTDM x5 on a.AllowPriceRise=x5.cbm and x5.id='YESNO'" +
				" left join tXTDM x6 on a.IsOutSet=x6.cbm and x6.id='YESNO'" +
				" where 1=1 ";
		
		if (StringUtils.isNotBlank(baseItem.getNoSetBaseCheckItem())&&"T".equals(baseItem.getNoSetBaseCheckItem())) {
			sql += " and isnull(e.num, 0) = 0 ";
		}
		if (StringUtils.isBlank(baseItem.getExpired())
				|| "F".equals(baseItem.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if(StringUtils.isNotBlank(baseItem.getCustomerType())){
			sql+=" and a.custType = ? ";
			list.add(baseItem.getCustomerType());
		}
		if (StringUtils.isNotBlank(baseItem.getCode())) {
			sql += " and a.Code=? ";
			list.add(baseItem.getCode());
		}
    	if ("1".equals(baseItem.getCustTypeType())){
    		sql += " and a.Category='1' ";
    	}
    	if(StringUtils.isNotBlank(baseItem.getIsPrefpre())){
    		if("1".equals(baseItem.getIsPrefpre())){
    			sql+=" and a.PerfPer < 1 ";
    		}else {
				sql+=" and a.PerfPer >= 1 ";
			}
    	}
    	if (StringUtils.isNotBlank(baseItem.getDescr())) {
			sql += " and a.Descr like ? ";
			list.add("%"+baseItem.getDescr()+"%");
		}
    	if (StringUtils.isNotBlank(baseItem.getRemCode())) {
			sql += " and a.RemCode=? ";
			list.add(baseItem.getRemCode());
		}
    	if (StringUtils.isNotBlank(baseItem.getBaseItemType1())) {
			sql += " and a.BaseItemType1=? ";
			list.add(baseItem.getBaseItemType1());
		}
    	if (StringUtils.isNotBlank(baseItem.getBaseItemType2())) {
			sql += " and a.BaseItemType2=? ";
			list.add(baseItem.getBaseItemType2());
		}
    	if (baseItem.getCost() != null) {
			sql += " and a.Cost=? ";
			list.add(baseItem.getCost());
		}
    	if (baseItem.getMarketPrice() != null) {
			sql += " and a.MarketPrice=? ";
			list.add(baseItem.getMarketPrice());
		}
    	if (baseItem.getOfferPri() != null) {
			sql += " and a.OfferPri=? ";
			list.add(baseItem.getOfferPri());
		}
    	if (StringUtils.isNotBlank(baseItem.getRemark())) {
			sql += " and a.Remark like ? ";
			list.add("%"+baseItem.getRemark()+"%");
		}
    	if (baseItem.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(baseItem.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(baseItem.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(baseItem.getLastUpdatedBy());
		}
    	if (StringUtils.isNotBlank(baseItem.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(baseItem.getActionLog());
		}
    	if (StringUtils.isNotBlank(baseItem.getUom())) {
			sql += " and a.Uom=? ";
			list.add(baseItem.getUom());
		}
    	if (baseItem.getDispSeq() != null) {
			sql += " and a.DispSeq=? ";
			list.add(baseItem.getDispSeq());
		}
    	if (baseItem.getMaterial() != null) {
			sql += " and a.Material=? ";
			list.add(baseItem.getMaterial());
		}
    	if (StringUtils.isNotBlank(baseItem.getCategory())) {
			sql += " and a.Category=? ";
			list.add(baseItem.getCategory());
		}
    	if (StringUtils.isNotBlank(baseItem.getCtrlType())) {
			sql += " and a.CtrlType=? ";
			list.add(baseItem.getCtrlType());
		}
    	if (baseItem.getCtrlOfferCost() != null) {
			sql += " and a.CtrlOfferCost=? ";
			list.add(baseItem.getCtrlOfferCost());
		}
    	if (StringUtils.isNotBlank(baseItem.getPrjType())) {
			sql += " and a.PrjType=? ";
			list.add(baseItem.getPrjType());
		}
    	
    	if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.dispSeq,a.lastupdate desc ";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> findDetailPageBySql(Page<Map<String,Object>> page, BaseItem baseItem) {
		List<Object> list = new ArrayList<Object>();

		String sql ="select a.Address,b.qty ,b.unitprice,b.material,b.lineamount,b.remark,c.Descr FixAreaDescr from tBaseItemPlan b " +
				" inner join tCustomer a  " +
				" on a.Code=b.CustCode and b.BaseItemCode= ? " +
				" left outer join tFixArea c on c.pk=b.FixAreaPK " +
				" where a.Status in ('1','2','3') " +
				"	or  (a.Status='5' and  a.EndCode in('1','2','5','6')) " +
				" order by b.CustCode,b.DispSeq  ";

		list.add(baseItem.getCode());
		
		return this.findPageBySql(page, sql, list.toArray());
	}

	public Page<Map<String, Object>> getItemBaseType(
			Page<Map<String, Object>> page, BaseItem baseItem) {
		List<Object> list=new ArrayList<Object>();
		String sql="select a.isoutset,a.DispSeq,a.Code,a.Descr,a.Cost,a.MarketPrice,a.OfferPri,a.Material,b.Descr Uom,a.Remark,a.IsCalMangeFee,"
                + "rtrim(a.BaseItemType1) BaseItemType1,rtrim(a.BaseItemType2) BaseItemType2,a.Category,x1.note CategoryDescr,"
				+ " a.PrjCtrlType,a.OfferCtrl,a.MaterialCtrl,allowpricerise,a.IsBaseItemSet,x2.note IsBaseItemSetDescr,a.FixAreaType, c.descr FixAreaTypeDescr,a.IsFixPrice "
                + " from tBaseItem a "
                + " left outer join tUom b on a.Uom=b.Code "
                + " left outer join txtdm x1 on x1.id='BASEITEMCAT'and x1.cbm=a.Category "
                + " left outer join txtdm x2 on x2.id='YESNO' and x2.cbm=a.IsBaseItemSet "
                + " left outer join tFixAreaType c on c.code=a.FixAreaType "
                
                + " where a.Expired='F'   ";
        if("2".equals(baseItem.getIsOutSet())){
        	sql+=" and a.Category not in ('5','6') ";
        }else if ("1".equals(baseItem.getIsOutSet())){
        	sql+=" and a.Category  in ('1') ";
        }
        if(StringUtils.isNotBlank(baseItem.getCustomerType())){
        	 if(StringUtils.isNotBlank(baseItem.getCanUseComBaseItem())){
        	     if("0".equals(baseItem.getCanUseComBaseItem())){
                     sql += " and  (a.CustType = ? or a.CustTypeGroupNo in (select in_a.No from tCustTypeGroupDt in_a where in_a.CustType = ? ))";
                 }else{
                     sql += " and (a.CustType = ?  or a.CustTypeGroupNo in (select in_a.No from tCustTypeGroupDt in_a where in_a.CustType = ? ) "
                         +"or ((a.CustType is null or a.CustType = '') and (a.CustTypeGroupNo is null or a.CustTypeGroupNo = '')) ) ";
                 }
        	     list.add(baseItem.getCustomerType());
        	     list.add(baseItem.getCustomerType());
     		 }
        }
   
        if ("1".equals(baseItem.getCustTypeType())){
        	sql+=" and a.Category='1' ";
        }
		if(StringUtils.isNotBlank(baseItem.getBaseItemType1())){
			sql+="and BaseItemType1=? ";
			list.add(baseItem.getBaseItemType1());
		}
		if(StringUtils.isNotBlank(baseItem.getBaseItemType2())){
			sql+="and BaseItemType2=? ";
			list.add(baseItem.getBaseItemType2());
		}
		if(StringUtils.isNotBlank(baseItem.getDescr())){
			sql+=" and a.descr like ?";
			list.add("%"+baseItem.getDescr()+"%");
		}
		if (StringUtils.isNotBlank(baseItem.getCustType())) {
			if(StringUtils.isNotBlank(baseItem.getCanUseComBaseItem())){
			    if("0".equals(baseItem.getCanUseComBaseItem())){
                    sql += " and  (a.CustType = ? or a.CustTypeGroupNo in (select in_a.No from tCustTypeGroupDt in_a where in_a.CustType = ? ))";
                }else{
                    sql += " and (a.CustType = ?  or a.CustTypeGroupNo in (select in_a.No from tCustTypeGroupDt in_a where in_a.CustType = ? ) "
                        +"or ((a.CustType is null or a.CustType = '') and (a.CustTypeGroupNo is null or a.CustTypeGroupNo = '')) ) ";
                }
			    list.add(baseItem.getCustType());
			    list.add(baseItem.getCustType());
     		}
		}	
		sql+="order by a.DispSeq,a.Descr desc";
		return this.findPageBySql(page, sql, list.toArray());
	}

	public void importDetail(Map<String, Object> data) {
		Assert.notNull(data);
		Connection conn=null;
		CallableStatement call=null;
		try {
			HibernateTemplate hibernateTemplate=SpringContextHolder.getBean("hibernateTemplate");
			Session session=SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pJcys_Import_BS(?,?,?,?,?,?,?,?,?)}");
			call.setString(1,data.get("custCode").toString());
			call.setString(2,data.get("fixareadescr").toString());
			call.setString(3,data.get("baseitemcode").toString());
			call.setDouble(4, Double.parseDouble(data.get("qty").toString()));
			call.setString(5,data.get("lastupdatedby").toString());
			call.registerOutParameter(6, Types.INTEGER);
			call.registerOutParameter(7, Types.INTEGER);
			call.registerOutParameter(8, Types.INTEGER);
			call.registerOutParameter(9, Types.NVARCHAR);
			call.execute();
			if(call.getInt(8)==1){
				data.put("fixareapk", call.getInt(6));
				data.put("preplanareapk", call.getInt(7));
			}else{
				data.put("isinvalid", 1);
				data.put("isinvaliddescr", call.getString(8));
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		
	}

	public String getUomByBaseItemCode(String baseItemCode) {
		String sql = "select b.descr from tBaseItem a,tUom b where a.uom=b.code and a.code=?";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{baseItemCode});
		if (list!=null && list.size()>0){
			return (String) list.get(0).get("descr");
		}
		return null;
	}

	public List<Map<String, Object>> getBaseAlgorithmByCode(BaseItem baseItem) {
		String sql = "SELECT distinct c.Code,c.Code+' '+c.Descr fd,c.Descr FROM tBaseItem a "
				+ "inner join tBaseAlgorithmPrjType b on a.PrjType=b.PrjType "
				+ "inner join tBaseAlgorithm c on b.Code=c.Code WHERE a.Code=? and c.Expired='F'";
		List<Map<String, Object>> list = this.findListBySql(sql,
				new Object[] { baseItem.getCode() });
		return list;
	}
	
	@SuppressWarnings("deprecation")
	public Result doSave(BaseItem baseItem) {
		Assert.notNull(baseItem);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pInserttBaseItem(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, baseItem.getDescr());
			call.setString(2, baseItem.getRemCode());
			call.setString(3, baseItem.getCategory());
			call.setString(4, baseItem.getBaseItemType1());
			call.setString(5, baseItem.getBaseItemType2());
			call.setDouble(6, baseItem.getCost()==null?0:baseItem.getCost());
			call.setDouble(7, baseItem.getMarketPrice()==null?0:baseItem.getMarketPrice());
			call.setDouble(8, baseItem.getOfferPri()==null?0:baseItem.getOfferPri());
			call.setDouble(9, baseItem.getMaterial()==null?0:baseItem.getMaterial());
			call.setString(10, baseItem.getUom());
			call.setInt(11, baseItem.getDispSeq()==null?0:baseItem.getDispSeq());
			call.setString(12, baseItem.getRemark());
			call.setString(13, baseItem.getLastUpdatedBy());
			call.setDouble(14, baseItem.getProjectPrice()==null?0:baseItem.getProjectPrice());
			call.setString(15, baseItem.getPrjType());
			call.setString(16, baseItem.getIsFixPrice());
			call.setString(17, baseItem.getPrjCtrlType());
			call.setString(18, baseItem.getOfferCtrl());
			call.setString(19, baseItem.getMaterialCtrl());
			call.setString(20, baseItem.getIsCalMangeFee());
			call.setString(21, baseItem.getCustType());
			call.setString(22, baseItem.getAllowPriceRise());
			call.setString(23, baseItem.getIsOutSet());
			call.registerOutParameter(24, Types.INTEGER);
			call.registerOutParameter(25, Types.NVARCHAR);
			call.setString(26, baseItem.getIsBaseItemSet());
			call.setString(27, baseItem.getBaseTempNo());
			call.setString(28, baseItem.getFixAreaType());
			call.setString(29, baseItem.getCustTypeGroupNo());
			call.setString(30, baseItem.getItemType2());
			call.execute();
			result.setCode(String.valueOf(call.getInt(24)));
			result.setInfo(call.getString(25));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	
	@SuppressWarnings("deprecation")
	public Result doUpdate(BaseItem baseItem) {
		Assert.notNull(baseItem);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pUpdatetBaseItem(?,?,?,?,?,?,?,?,?,? ,?,?,?,?,?,?,?,?,?,? ,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, baseItem.getCode());
			call.setString(2, baseItem.getDescr());
			call.setString(3, baseItem.getRemCode());
			call.setString(4, baseItem.getCategory());
			call.setString(5, baseItem.getBaseItemType1());
			call.setString(6, baseItem.getBaseItemType2());
			call.setDouble(7, baseItem.getCost()==null?0:baseItem.getCost());
			call.setDouble(8, baseItem.getMarketPrice()==null?0:baseItem.getMarketPrice());
			call.setDouble(9, baseItem.getOfferPri()==null?0:baseItem.getOfferPri());
			call.setDouble(10, baseItem.getMaterial()==null?0:baseItem.getMaterial());
			call.setString(11, baseItem.getUom());
			call.setInt(12, baseItem.getDispSeq()==null?0:baseItem.getDispSeq());
			call.setString(13, baseItem.getRemark());
			call.setString(14, baseItem.getExpired());
			call.setString(15, baseItem.getLastUpdatedBy());
			call.setDouble(16, baseItem.getProjectPrice()==null?0:baseItem.getProjectPrice());
			call.setString(17, baseItem.getPrjType());
			call.setString(18, baseItem.getIsFixPrice());
			call.setString(19, baseItem.getPrjCtrlType());
			call.setString(20, baseItem.getOfferCtrl());
			call.setString(21, baseItem.getMaterialCtrl());
			call.setString(22, baseItem.getIsCalMangeFee());
			call.setString(23, baseItem.getCustType());
			call.setString(24, baseItem.getAllowPriceRise());
			call.setString(25, baseItem.getIsOutSet());
			call.registerOutParameter(26, Types.INTEGER);
			call.registerOutParameter(27, Types.NVARCHAR);
			call.setString(28, baseItem.getIsBaseItemSet());
			call.setString(29, baseItem.getBaseTempNo());
			call.setString(30, baseItem.getFixAreaType());
			call.setString(31, baseItem.getCustTypeGroupNo());
			call.setString(32, baseItem.getItemType2());
			call.execute();
			result.setCode(String.valueOf(call.getInt(26)));
			result.setInfo(call.getString(27));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	
	@SuppressWarnings("deprecation")
	public Result doUpdatePrice(BaseItem baseItem) {
		Assert.notNull(baseItem);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pUpBaseItemPrice(?,?,?,?,?,?)}");
			call.setString(1, baseItem.getCode());
			call.setDouble(2, baseItem.getOfferPri()==null?0:baseItem.getOfferPri());
			call.setDouble(3, baseItem.getMaterial()==null?0:baseItem.getMaterial());
			call.setString(4, baseItem.getLastUpdatedBy());
			call.registerOutParameter(5, Types.INTEGER);
			call.registerOutParameter(6, Types.NVARCHAR);
			call.execute();
			result.setCode(String.valueOf(call.getInt(5)));
			result.setInfo(call.getString(6));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	
}

