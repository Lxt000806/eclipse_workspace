package com.house.home.dao.design;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
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
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.entity.design.BaseItemPlan;
import com.house.home.entity.design.Customer;
import com.house.home.entity.project.BaseItemChg;

@SuppressWarnings("serial")
@Repository
public class BaseItemPlanDao extends BaseDao {

	/**
	 * BaseItemPlan分页信息
	 * 
	 * @param page
	 * @param baseItemPlan
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, BaseItemPlan baseItemPlan) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from tBaseItemPlan a where 1=1 ";

    	if (baseItemPlan.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(baseItemPlan.getPk());
		}
    	if (StringUtils.isNotBlank(baseItemPlan.getCustCode())) {
			sql += " and a.CustCode=? ";
			list.add(baseItemPlan.getCustCode());
		}
    	if (baseItemPlan.getFixAreaPk() != null) {
			sql += " and a.FixAreaPK=? ";
			list.add(baseItemPlan.getFixAreaPk());
		}
    	if (StringUtils.isNotBlank(baseItemPlan.getBaseItemCode())) {
			sql += " and a.BaseItemCode=? ";
			list.add(baseItemPlan.getBaseItemCode());
		}
    	if (baseItemPlan.getQty() != null) {
			sql += " and a.Qty=? ";
			list.add(baseItemPlan.getQty());
		}
    	if (baseItemPlan.getCost() != null) {
			sql += " and a.Cost=? ";
			list.add(baseItemPlan.getCost());
		}
    	if (baseItemPlan.getUnitPrice() != null) {
			sql += " and a.UnitPrice=? ";
			list.add(baseItemPlan.getUnitPrice());
		}
    	if (baseItemPlan.getLineAmount() != null) {
			sql += " and a.LineAmount=? ";
			list.add(baseItemPlan.getLineAmount());
		}
    	if (StringUtils.isNotBlank(baseItemPlan.getRemark())) {
			sql += " and a.Remark=? ";
			list.add(baseItemPlan.getRemark());
		}
    	if (baseItemPlan.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(baseItemPlan.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(baseItemPlan.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(baseItemPlan.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(baseItemPlan.getExpired()) || "F".equals(baseItemPlan.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(baseItemPlan.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(baseItemPlan.getActionLog());
		}
    	if (baseItemPlan.getDispSeq() != null) {
			sql += " and a.DispSeq=? ";
			list.add(baseItemPlan.getDispSeq());
		}
    	if (baseItemPlan.getMaterial() != null) {
			sql += " and a.Material=? ";
			list.add(baseItemPlan.getMaterial());
		}
    	if (baseItemPlan.getIsCheck() != null) {
			sql += " and a.IsCheck=? ";
			list.add(baseItemPlan.getIsCheck());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Pk";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	public Page<Map<String, Object>> findPageBySql_jzys(
			Page<Map<String, Object>> page, BaseItemPlan baseItemPlan) {
		List<Object> list = new ArrayList<Object>();
		if (StringUtils.isBlank(baseItemPlan.getCustCode())) {
			return null;
		}
		String sql = "select * from (select a.*,c.Descr FixAreaDescr,CASE WHEN RTRIM(c.Descr) in ('全房','水电项目','土建项目','安装项目','综合项目') THEN 1 ELSE 0 END isgroup,b.Descr BaseItemDescr,b.OfferPri tempunitprice,b.Material tempmaterial,b.BaseItemType1,d.Descr Uom,"
		   +" c.Descr ordername,b.Category,x1.note CategoryDescr,x2.NOTE IsOutSetDescr,g.count,b.IsFixPrice,b.IsCalMangeFee,b.allowpricerise, " 
		   +" ba.Descr BaseAlgorithmDescr,b.PrjType,c.PrePlanAreaPK, "
		   +" x3.NOTE IsRequireddescr,x4.NOTE CanReplaceDescr,x5.NOTE CanModiQtydescr, "
		   +" case when isnull(a.BaseAlgorithm,'')='' then 0 else isnull(dbo.fGetBaseItemPlanAutoQty(a.custcode,a.BaseAlgorithm,a.BaseItemCode,c.preplanareapk,isnull(a.tempdtpk,0)),0)  end AutoQty, "
		   +" gf.Descr GiftDescr,x6.note IsMainItemDescr "
           +"from (select a.lineAmount beflineamount,a.giftPK,a.PK,a.CustCode,a.FixAreaPK,a.BaseItemCode,a.Qty,a.isoutset,"
           +"a.Cost,a.UnitPrice,a.Material,a.LineAmount,a.DispSeq,a.isCheck,"
           +"a.Remark,a.LastUpdate,a.LastUpdatedBy,a.ActionLog,a.Expired,Round(a.Qty*a.UnitPrice,0) sumUnitPrice,Round(a.Qty*a.Material,0) sumMaterial, "
           +" a.IsRequired,a.CanReplace,a.CanModiQty,a.BaseAlgorithm,a.tempdtpk,IsGift,IsMainItem,BaseItemSetNo  "
           +"from tBaseItemPlan a "
           +"where exists(select 1 from tCustomer where a.Custcode=Code and ContainBase=1) "
           +"and a.CustCode=?) a "
           +"left join tBaseItem b on b.Code=a.BaseItemCode "
           +"left join tFixArea c on c.Pk=a.FixAreaPK "
           +"left join tUom d on b.Uom=d.Code "
           +"left join txtdm  x1 on x1.cbm=b.Category and x1.id='BASEITEMCAT' "
           +"left outer join tXTDM x2 on x2.ID='YESNO' and x2.CBM=a.IsOutSet "
           +"LEFT JOIN(select  count(UnitPrice) COUNT,e.pk from tBaseItemPlan  e left outer join tBaseItem h on e.BaseItemCode=h.Code  WHERE  e.CustCode=? AND (  e.UnitPrice<>h.OfferPri or e.Material<>h.Material) group by e.pk) g on g.pk =a.pk "
           +"left outer join tBaseAlgorithm ba on ba.code=a.BaseAlgorithm "
           +"left outer join tXTDM x3 on x3.ID='YESNO' and x3.CBM=a.IsRequired "
           +"left outer join tXTDM x4 on x4.ID='YESNO' and x4.CBM=a.CanReplace "
       	   +"left outer join tXTDM x5 on x5.ID='YESNO' and x5.CBM=a.CanModiQty "
       	   +"left outer join tXTDM x6 on x6.ID='YESNO' and x6.CBM=a.IsMainItem "
           +"left outer join tgift gf on gf.PK=a.GiftPK "
           +"where a.CustCode=? ";
		list.add(baseItemPlan.getCustCode());
		list.add(baseItemPlan.getCustCode());
		list.add(baseItemPlan.getCustCode());
		if(baseItemPlan.getGiftPk()!= null){
			sql+=" and (a.giftPK = ? or a.giftPK is null or a.giftPK = '') " +
				" and (exists( select 1 from tGiftItem in_a where in_a.giftpk = ? and in_a.BaseItemCode = a.BaseItemCode " +
				" ) or not exists( select 1 from tGiftItem in_b where in_b.giftpk = ? )" +
				") ";
			list.add(baseItemPlan.getGiftPk());
			list.add(baseItemPlan.getGiftPk());
			list.add(baseItemPlan.getGiftPk());
		}
        if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ") a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ") a order by a.FixAreaPk";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

	public boolean hasBaseItemPlan(String custCode) {
		String sql = "select top 1 Custcode from tBaseItemPlan where Custcode=?";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{custCode});
		if (list!=null && list.size()>0){
			return true;
		}
		return false;
	}

	public Map<String, Object> getBaseFeeComp(String custCode, String descr) {
		String sql="select baseFeeComp=isnull(sum(a.LineAmount),0) from tBaseItemPlan a "
		           +" left outer join tFixArea b on b.PK=a.FixAreaPK "
		          +"  where b.Descr =? AND  a.CustCode=? ";
	List<Map<String,Object>> list=this.findBySql(sql, new Object[]{descr,custCode});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	public Result doBaseItemForProc(Customer customer) {
		Assert.notNull(customer);
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pJcys_forXml(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			String FromStatus="";
			call.setString(1,customer.getCode());
			call.setDouble(2, customer.getManageFee());
			call.setDouble(3, customer.getBaseFeeDirct());
			call.setDouble(4, customer.getBaseFeeComp());
			call.setDouble(5, customer.getBaseFee());
			call.setDouble(6, customer.getArea());
			call.setString(7,customer.getLastUpdatedBy());
			call.setString(8,customer.getCustType());
			call.setString(9,customer.getDescr());
			call.setDouble(10,customer.getManageFeeBase());
			call.registerOutParameter(11, Types.INTEGER);
			call.registerOutParameter(12, Types.NVARCHAR);
			call.setString(13,customer.getDetailXml());
			call.setString(14,customer.getBaseTempNo());
			call.execute();
			result.setCode(String.valueOf(call.getInt(11)));
			result.setInfo(call.getString(12));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	public Result doBaseItemTCForProc(Customer customer) {
		Assert.notNull(customer);
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pJcys_TC_forXml(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			String FromStatus="";
			call.setString(1,customer.getCode());
			call.setDouble(2, customer.getManageFee());
			call.setDouble(3, customer.getBaseFeeDirct());
			call.setDouble(4, customer.getBaseFeeComp());
			call.setDouble(5, customer.getBaseFee());
			call.setDouble(6, customer.getArea());
			call.setString(7,customer.getLastUpdatedBy());
			call.setDouble(8, customer.getMainSetFee());
			call.setDouble(9, customer.getSetMinus());
			call.setDouble(10, customer.getSetAdd());
			call.setDouble(11, customer.getLongFee());
			call.setDouble(12, customer.getManageFeeBase());
			call.setString(13,customer.getCustType());
			call.setString(14,customer.getDescr());
			call.registerOutParameter(15, Types.INTEGER);
			call.registerOutParameter(16, Types.NVARCHAR);
			call.setString(17,customer.getDetailXml());
			call.setString(18,customer.getBaseTempNo());
			call.execute();
			result.setCode(String.valueOf(call.getInt(15)));
			result.setInfo(call.getString(16));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return result;
	}

	public long getBaseItemPlanCount(BaseItemPlan baseItemPlan) {
		List<Object> list = new ArrayList<Object>();
		String sql=" select *  from tBaseItemPlan a where 1=1 ";
		if(StringUtils.isNotBlank(baseItemPlan.getCustCode())){
			sql+=" and  a.custCode=? ";
			list.add(baseItemPlan.getCustCode());
		}
		return this.countSqlResult(sql, "", list.toArray());
	}
	
	/**
	 * 计算客户砌墙面积（tBaseItem.PrjType = '1'）
	 * 
	 * @param custCode
	 * @return
	 */
	public double calculateWallArea(String custCode) {
        String sql = "select isnull(sum(a.Qty), 0.00) WallArea "
                + "from tBaseItemPlan a "
                + "inner join tBaseItem b on a.BaseItemCode = b.Code and b.PrjType = '1' "
                + "where a.CustCode = ? ";
        
        List<Map<String, Object>> result = findBySql(sql, custCode);
        
        return (Double) result.get(0).get("WallArea");
	}
	
	public String getBaseItemPlanAutoQty(BaseItemPlan baseItemPlan){
		String sql = " select isnull(dbo.fGetBaseItemPlanAutoQty(?,?,?,?,?),0) qty";
        List<Map<String, Object>> result = 
        		findBySql(sql, baseItemPlan.getCustCode(),baseItemPlan.getBaseAlgorithm(),baseItemPlan.getBaseItemCode(),
        				baseItemPlan.getPrePlanAreaPK(),baseItemPlan.getTempDtPk());
        if(result != null && result.size()>0){
        	return result.get(0).get("qty").toString();
        }
        return "0.0";
	}

}

