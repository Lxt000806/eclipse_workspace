package com.house.home.dao.basic;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Timestamp;
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
import com.house.home.entity.basic.Gift;

@SuppressWarnings("serial")
@Repository
public class GiftDao extends BaseDao {

	/**
	 * Gift分页信息
	 * 
	 * @param page
	 * @param gift
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Gift gift) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select a.type,a.QuoteModule,a.perfDiscType,a.discAmtType,a.CalcDiscCtrlPer,IsLimitItem,a.issoftToken,a.discType,a.PK,a.Descr,a.Remarks,a.ActName,a.BeginDate,a.EndDate, " 
				+"a.MinArea,a.MaxArea,x1.NOTE TypeDescr,c.Descr QuoteModuleDescr, " 
				+"x2.NOTE DiscTypeDescr,a.DiscPer,b.Descr DiscAmtTypeDescr,a.MaxDiscAmtExpr,x3.NOTE PerfDiscTypeDescr, " 
				+"a.PerfDiscPer,x5.NOTE IsLimitItemDescr,x6.NOTE IsOutSetDescr,x7.NOTE IsServiceDescr, " 
				+"x8.NOTE IsSoftTokenDescr,x9.NOTE IsCupboardDescr,a.DispSeq,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog, "
				+"a.DiscAmtCalcType, x10.NOTE DiscAmtCalcTypeDescr, a.IsAdvance, x11.NOTE IsAdvanceDescr "	//增加字段维护：优惠额度计算方式、额度预支项目 add by zb on 20200427
				+"from tGift a "  
				+"left join tItemType1 b on a.DiscAmtType=b.Code " 
				+"left join tItemType1 c on a.QuoteModule=c.Code " 
				+"left join tXTDM x1 on a.Type=x1.CBM and x1.ID='GIFTTYPE' " 
				+"left join tXTDM x2 on a.DiscType=x2.CBM and x2.ID='GIFTDISCTYPE' "  
				+"left join tXTDM x3 on a.PerfDiscType=x3.CBM and x3.ID='GIFTPERFDSTYPE' "  
				+"left join tXTDM x5 on a.IsLimitItem=x5.CBM and x5.ID='YESNO' "   
				+"left join tXTDM x6 on a.IsOutSet=x6.CBM and x6.ID='YESNO'  " 
				+"left join tXTDM x7 on a.IsService=x7.CBM and x7.ID='YESNO'  " 
				+"left join tXTDM x8 on a.IsSoftToken=x8.CBM and x8.ID='YESNO' " 
				+"left join tXTDM x9 on a.IsCupboard=x9.CBM and x9.ID='YESNO' " 
				+"left join tXTDM x10 on a.DiscAmtCalcType=x10.CBM and x10.ID='GIFTDACALCTYPE' "
				+"left join tXTDM x11 on a.IsAdvance=x11.CBM and x11.ID='YESNO' "
				+"where 1=1 ";

    	if (StringUtils.isNotBlank(gift.getDescr())) {
			sql += " and a.Descr like ? ";
			list.add("%"+gift.getDescr()+"%");
		}
    	if (gift.getDateFrom() != null) {
			sql += " and a.BeginDate>=? ";
			list.add(gift.getDateFrom());
		}
    	if(StringUtils.isNotBlank(gift.getActName())){
    		sql+=" and a.ActName like  ? ";
			list.add("%"+gift.getActName()+"%");
    	}
    	if (gift.getDateTo() != null) {
			sql += " and a.EndDate<dateadd(day,1,?) ";
			list.add(gift.getDateTo());
		}
    	if (gift.getMinArea() != null) {
			sql += " and a.MinArea>=? ";
			list.add(gift.getMinArea());
		}
    	if (gift.getMaxArea() != null) {
			sql += " and a.MaxArea<=? ";
			list.add(gift.getMaxArea());
		}
    	if (StringUtils.isNotBlank(gift.getType())) {
			sql += " and a.Type in('" + gift.getType().replace(",", "', '") + "') ";
		}
    	if (StringUtils.isNotBlank(gift.getDiscType())) {
			sql += " and a.DiscType=? ";
			list.add(gift.getDiscType());
		}
    	if (StringUtils.isNotBlank(gift.getDiscAmtType())) {
			sql += " and a.DiscAmtType=? ";
			list.add(gift.getDiscAmtType());
		}
    	if (StringUtils.isNotBlank(gift.getCustType())) {
			sql += " and exists(select 1 from tGiftCustType gct where gct.GiftPK=a.PK and gct.CustType=?) ";
			list.add(gift.getCustType());
		}
		if (StringUtils.isBlank(gift.getExpired()) || "F".equals(gift.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if(StringUtils.isNotBlank(gift.getCustCode())){
			sql+=" and not exists( select 1 from tCustGift in_a where in_a.custCode = ? and in_a.giftPk = a.Pk ) " +
					" and not exists ( select 1 from tCustomer in_b where in_b.area > a.MaxArea and in_b.Area < a.MinArea and in_b.code = ? )" +
					" and getDate() between a.beginDate and a.endDate " +
					" and ( exists (select 1 from tcustomer in_c " +
					" 				inner join tGiftCustType in_d on in_d.CustType = in_c.custType  where in_c.code = ? and in_d.giftPk = a.Pk)" +
					"		or not exists ( select 1 from tCustomer in_e " +
					"				left join tGiftCustType in_f on in_f.custType <> in_e.custType where in_e.code = ? and in_f.giftpk = a.pk )	" +
					"	)" ;
			list.add(gift.getCustCode());
			list.add(gift.getCustCode());
			list.add(gift.getCustCode());
			list.add(gift.getCustCode());
			
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.DispSeq";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * GiftCustType分页信息
	 * 
	 * @param page
	 * @param gift
	 * @return
	 */
	public Page<Map<String,Object>> goCustTypeJqGrid(Page<Map<String,Object>> page, Gift gift) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select a.PK,a.GiftPK,a.CustType,b.Desc1 CustTypeDescr, "  
				+"a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog "
				+"from tGiftCustType a  "
				+"left join tCusttype b on a.CustType=b.Code where a.GiftPK=? ";
		list.add(gift.getPk());
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.LastUpdate Desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * GiftItem分页信息
	 * 
	 * @param page
	 * @param gift
	 * @return
	 */
	public Page<Map<String,Object>> goItemJqGrid(Page<Map<String,Object>> page, Gift gift) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select a.PK,a.GiftPK,a.BaseItemCode,a.ItemCode,b.Descr BaseItemDescr, "
				+"c.Descr ItemDescr,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog "
				+"from tGiftItem a  "
				+"left join tBaseItem b on a.BaseItemCode=b.Code  "
				+"left join tItem c on a.ItemCode=c.Code where a.GiftPK=?  ";
		list.add(gift.getPk());
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.LastUpdate Desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 保存
	 * 
	 * @param gift
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result doSaveProc(Gift gift) {
		Assert.notNull(gift);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
				
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pGift(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, gift.getM_umState());
			call.setString(2, gift.getLastUpdatedBy());	
			call.setInt(3, gift.getPk()==null?0:gift.getPk());
			call.setString(4, gift.getDescr());
			call.setString(5, gift.getRemarks());
			call.setString(6, gift.getActName());
			call.setTimestamp(7, gift.getBeginDate() == null ? null : new Timestamp(gift.getBeginDate().getTime()));
			call.setTimestamp(8, gift.getEndDate() == null ? null : new Timestamp(gift.getEndDate().getTime()));
			call.setDouble(9, gift.getMinArea());
			call.setDouble(10, gift.getMaxArea());
			call.setString(11, gift.getType());
			call.setString(12, gift.getQuoteModule());
			call.setString(13, gift.getDiscType());
			call.setDouble(14, gift.getDiscPer());
			call.setString(15, gift.getDiscAmtType());
			call.setString(16, gift.getMaxDiscAmtExpr());
			call.setString(17, gift.getPerfDiscType());
			call.setDouble(18, gift.getPerfDiscPer()==null?0:gift.getPerfDiscPer());
			call.setDouble(19, gift.getCalcDiscCtrlPer()==0.0?0:gift.getCalcDiscCtrlPer());
			call.setString(20, gift.getIsService());
			call.setString(21, gift.getIsOutSet());
			call.setString(22, gift.getIsSoftToken());
			call.setString(23, gift.getIsLimitItem());
			call.setInt(24, gift.getDispSeq()==null?0:gift.getDispSeq());
			call.setString(25, gift.getExpired());
			call.setString(26, gift.getCustTypeJson());
			call.setString(27, gift.getItemJson());
			call.registerOutParameter(28, Types.INTEGER);
			call.registerOutParameter(29, Types.NVARCHAR);
			call.setString(30, gift.getIsCupboard());
			call.setString(31, gift.getDiscAmtCalcType());
			call.setString(32, gift.getIsAdvance());
			call.setString(33, gift.getIsProvideDiscToken());
			call.setString(34, gift.getConfirmLevel());
			call.execute();
			result.setCode(String.valueOf(call.getInt(28)));
			result.setInfo(call.getString(29));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
		
	}
	
    public void addCustTypeForGiftIfAbsent(Integer giftPk, String custType, String czybh) {
        String sql = ""
                + "if not exists(select 1 from tGiftCustType where GiftPK = ? and CustType = ?) "
                + System.lineSeparator()
                + "    insert into tGiftCustType (GiftPK, CustType, LastUpdate, LastUpdatedBy, Expired, ActionLog) "
                + "    values  (?, ?, getdate(), ?, N'F', N'ADD') ";
        
        executeUpdateBySql(sql, giftPk, custType, giftPk, custType, czybh);
    }
    
}

