package com.house.home.dao.finance;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
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
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.driver.ItemAppSend;
import com.house.home.entity.finance.SplCheckOut;
import com.house.home.entity.finance.WHCheckOut;
import com.house.home.entity.insales.SalesInvoice;

@SuppressWarnings("serial")
@Repository
public class WHCheckOutDao extends BaseDao {

	/**
	 * WHCheckOut分页信息
	 * 
	 * @param page
	 * @param whCheckOut
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, WHCheckOut whCheckOut,UserContext uc) {
		List<Object> list = new ArrayList<Object>();
		
		String sql = "select * from (select "
				+ " a.No,a.Status,d.NOTE StatusDescr,a.DocumentNo,a.CheckDate,a.Remarks,a.WHCode,g.Desc1 WHDescr,"
				+ "	a.AppCZY,e.ZWXM AppCZYDescr,a.Date,a.ConfirmCZY,f.ZWXM ConfirmCZYDescr,a.ConfirmDate,"
			    + "	a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog"
			    + "	from tWHCheckOut a"
			    + "	left outer join tXTDM d on a.Status=d.CBM and d.ID='WHChkOutStatus' "
			    + " left outer join tCZYBM e on e.CZYBH=a.AppCZY "
			    + " left outer join tCZYBM f on f.CZYBH=a.ConfirmCZY "
                + " left outer join tWareHouse g on g.code=a.WHCode ";
		if(StringUtils.isNotBlank(uc.getCzybh())){
			if ("2".equals(uc.getCzylb())){
				sql += "  left join tWareHouseOperater who on a.WHCode=who.whCode and who.czybh=? where 1=1 and who.whCode is not null  ";
				list.add(uc.getCzybh().trim());
			}else{
				sql+=" where 1=1 ";
			}
		}else{
			sql+=" where 1=1 ";
		}
		if(StringUtils.isNotBlank(uc.getItemRight())){
			sql+=" and ( g.itemType1 in "+"('"+uc.getItemRight().trim().replace(",", "','")+"') or g.itemType1 is null or g.itemType1 = '') ";
		}else {
			sql+=" and ( g.itemType1 is null or g.itemType1 = '' ) ";
		}
    	if (StringUtils.isNotBlank(whCheckOut.getNo())) {
			sql += " and a.No=? ";
			list.add(whCheckOut.getNo());
		}
    	if (whCheckOut.getDateFrom() != null){
			sql += " and a.Date>= ? ";
			list.add(whCheckOut.getDateFrom());
		}
		if (whCheckOut.getDateTo() != null){
			sql += " and a.Date<= ? ";
			list.add(DateUtil.addDateOneDay(whCheckOut.getDateTo()));
		}
    	if (StringUtils.isNotBlank(whCheckOut.getWhCode())) {
			sql += " and a.WHCode=? ";
			list.add(whCheckOut.getWhCode());
		}
    	if (whCheckOut.getCheckDateFrom() != null){
			sql += " and a.checkDate>= ? ";
			list.add(whCheckOut.getCheckDateFrom());
		}
		if (whCheckOut.getCheckDateTo() != null){
			sql += " and a.checkDate<= ? ";
			list.add(DateUtil.addDateOneDay(whCheckOut.getCheckDateTo()));
		}
    	if (StringUtils.isNotBlank(whCheckOut.getDocumentNo())) {
    		sql += " and a.documentNo=? ";
    		list.add(whCheckOut.getDocumentNo());
    	}
    	if (StringUtils.isNotBlank(whCheckOut.getStatus())) {
			sql += " and a.Status in " + "('"+whCheckOut.getStatus().replaceAll(",", "','")+"')";
		}

		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ") a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ") a order by a.LastUpdate desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	

	/**
	 * WHCheckOut 明细分页信息
	 * 
	 * @param page
	 * @param whCheckOut
	 * @return
	 */
	public Page<Map<String, Object>> findDetailPageBySql(Page<Map<String, Object>> page,WHCheckOut whCheckOut,UserContext uc){
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select a.No, a.IANo, a.WHCode, y.Remarks ItemAppRemarks, a.IsCheckOut, a.WHCheckOutNo, "
	               + " a.CheckSeq, d.Address, d.DocumentNo CustDocumentNo, wco.DocumentNo wcoDocumentNo,a.Remarks, "
	               + " y.SupplCode, c.NOTE TypeDescr, y.SendDate, y.ItemType1,"
	               + " g.Descr ItemType1Descr,x1.NOTE SendType, round(isnull(f.itemSumCost,0), 2) itemSumCost,"
	               + " round(isnull(h.ProjectAmount,0), 2) ProjectAmount, x2.Note IsSetItemDescr,"
 	               + " ( select top 1  it2.Descr from tItemAppDetail iad , tItem i , tItemType2 it2"
                   + "  where  iad.ItemCode = i.Code  and i.ItemType2 = it2.Code   and iad.No = a.IANo ) ItemType2Descr, "
                   + "  wco.No wcoNo, wco.date , wco.Status, "
	               + "  x3.NOTE StatusDescr,  wco.CheckDate,  "
	               + " wh.Desc1  WHDescr,  wco.ConfirmDate,a.SendFee, a.WHFee,a.ProjectOtherCost, "
	               +  " round(isnull(f.ItemSumCost,0)+isnull(a.SendFee,0)+isnull(a.WHFee,0)*(case when wh.WHFeeCostType='2' or wh.WHFeeCostType='3' then 1 else 0 end)+isnull(a.CarryFee,0),2) SumCost ,  " //+isnull( a.WHFee,0)
                   +  " round(isnull(h.ProjectAmount,0)+isnull(a.SendFee,0)+isnull(a.ProjectOtherCost,0)+isnull(a.WHFee,0)*(case when wh.WHFeeCostType='2' or wh.WHFeeCostType='3' then 1 else 0 end)+isnull(a.CarryFee,0),2) SumProjectAmount  "//+isnull( a.WHFee,0)
	               + " ,a.CarryFee "
                   + " from  tWHCheckOut wco "
	               + " left outer join tItemAppSend a on wco.no = a.WHCheckOutNo "
	               + " left outer join tItemApp y on y.no = a.IAno "
	               + " left outer join tSupplier b on b.Code = y.SupplCode "
	               + " left outer join tXTDM c on c.CBM = y.Type  and c.ID = 'ITEMAPPTYPE' "
                   + " left outer join tCustomer d on d.Code = y.CustCode "
                   + " left outer join tXTDM x1 on x1.IBM = y.SendType and x1.id = 'ITEMAPPSENDTYPE' " // and y.SendType = 2 "
                   + " left outer join tItemType1 g on g.code = y.ItemType1 "
                   //+ " left outer join tItemAppDetail e on e.no = y.no "
                   //+ " left outer join tItemtype2 i on i.code = y.itemtype2 "
                   + " left outer join tXTDM x2 on y.IsSetItem = x2.CBM and x2.ID = 'YESNO' "
                   + " left outer join ( select    No, sum(SendQty * Cost) ItemSumCost "
                   + " from  tItemAppSendDetail "
                   + "       group by  No "
                   + "     ) f on f.no = a.no "
                   + " left outer join ( select    a.No, "
                   + "                 sum(a.SendQty * b.ProjectCost) ProjectAmount "
                   + "       from      tItemAppSendDetail a , "
                   + "                 tItemAppDetail b "
                   + "       where     a.RefPk = b.PK "
                   + "       group by  a.No "
                   + "     ) h on h.no = a.no "
                   + " left outer join tXTDM x3 on wco.Status = x3.CBM "
                   + " and x3.ID = 'WHChkOutStatus' "
                   + " left outer join tWareHouse wh on wh.code = wco.WHCode ";
                  
		if(StringUtils.isNotBlank(uc.getCzybh())){
			sql += "  left join tWareHouseOperater who on wco.WHCode=who.whCode and who.czybh=? where 1=1 and who.whCode is not null  ";
			list.add(uc.getCzybh().trim());
		}else{
			sql+=" where 1=1 ";
		}
		sql+=" and y.ItemType1 in"+ "('"+uc.getItemRight().replaceAll(",", "','")+"')"; 
    	if (StringUtils.isNotBlank(whCheckOut.getNo())) {
			sql += " and wco.No=? ";
			list.add(whCheckOut.getNo());
		}
    	if (StringUtils.isNotBlank(whCheckOut.getIaNo())) {
			sql += " and a.IaNo=? ";
			list.add(whCheckOut.getIaNo());
		}
    	if (StringUtils.isNotBlank(whCheckOut.getItemAppSendNo())) {
			sql += " and a.No=? ";
			list.add(whCheckOut.getItemAppSendNo());
		}
    	if (StringUtils.isNotBlank(whCheckOut.getAddress())) {
			sql += " and d.Address like ?";
			list.add("%" + whCheckOut.getAddress()+ "%");
		}
    	if (StringUtils.isNotBlank(whCheckOut.getItemType1())) {
			sql += " and y.ItemType1=? ";
			list.add(whCheckOut.getItemType1());
		}
    	if (whCheckOut.getDateFrom() != null){
			sql += " and wco.Date>= ? ";
			list.add(whCheckOut.getDateFrom());
		}
		if (whCheckOut.getDateTo() != null){
			sql += " and wco.Date<? ";
			list.add(DateUtil.addDateOneDay(whCheckOut.getDateTo()));
		}
    	if (StringUtils.isNotBlank(whCheckOut.getWhCode())) {
			sql += " and wco.WHCode=? ";
			list.add(whCheckOut.getWhCode());
		}
    	if (whCheckOut.getCheckDateFrom() != null){
			sql += " and wco.checkDate>= ? ";
			list.add(whCheckOut.getCheckDateFrom());
		}
		if (whCheckOut.getCheckDateTo() != null){
			sql += " and wco.checkDate< ? ";
			list.add(DateUtil.addDateOneDay(whCheckOut.getCheckDateTo()));
		}
    	if (StringUtils.isNotBlank(whCheckOut.getDocumentNo())) {
    		sql += " and wco.documentNo=? ";
    		list.add(whCheckOut.getDocumentNo());
    	}
    	if (StringUtils.isNotBlank(whCheckOut.getStatus())) {
			sql += " and wco.Status in " + "('"+whCheckOut.getStatus().replaceAll(",", "','")+"')";
		}
    	 /*sql += " group by a.No, a.IANo, a.WHCode, y.Remarks, a.IsCheckOut, "
    		 + " a.WHCheckOutNo, a.CheckSeq, d.Address, d.DocumentNo, y.SupplCode,"
    		 + " c.NOTE, y.SendDate, x1.NOTE, y.Type, y.ProjectAmount, y.ItemType1,"
    		 + " g.Descr, i.Descr, f.ItemSumCost, h.ProjectAmount, x2.Note, wco.No, "
    		 + " wco.Status, x3.NOTE, wco.DocumentNo, wco.CheckDate,a.Remarks, "
    		 + " wco.WHCode, wh.Desc1, wco.Date, wco.ConfirmDate,a.SendFee, a.WHFee,a.ProjectOtherCost " ; */
    		
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ") a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ") a order by a.CheckSeq";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 操作出库记账（使用存储过程）
	 * @param whCheckOut
	 * @param xml
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result doWHCheckOutForProc(WHCheckOut whCheckOut) {
		Assert.notNull(whCheckOut);
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pCkjz_Add_forXml(?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, whCheckOut.getM_umState()); //whCheckOut.getM_umState()
			call.setString(2, whCheckOut.getNo());
			call.setString(3, whCheckOut.getRemarks());
			call.setTimestamp(4, whCheckOut.getCheckDate() == null ? null : new Timestamp(
					whCheckOut.getCheckDate().getTime()));
			call.setString(5, whCheckOut.getAppCZY());
			call.setString(6, whCheckOut.getLastUpdatedBy());
			call.setString(7, whCheckOut.getDocumentNo());
			call.setString(8, whCheckOut.getWhCode());
			call.registerOutParameter(9, Types.INTEGER);
			call.registerOutParameter(10, Types.NVARCHAR);
			call.setString(11, whCheckOut.getItemAppsendDetailJson());
			call.setString(12, whCheckOut.getSalesInvoiceDetailJson());
			call.execute();
			result.setCode(String.valueOf(call.getInt(9)));
			result.setInfo(call.getString(10));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	 @SuppressWarnings("deprecation")
		public Result doWHCheckOutCheckForProc(WHCheckOut whCheckOut) {
			Assert.notNull(whCheckOut);
			Result result = new Result();
			Connection conn = null; 
			CallableStatement call = null;
			try{
				HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
				Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
				conn = session.connection();
				call = conn.prepareCall("{Call pCkjz_Sh_forXml(?,?,?,?,?,?,?,?,?,?)}");
				call.setString(1, whCheckOut.getNo());
				call.setString(2, whCheckOut.getStatus());
				call.setString(3, whCheckOut.getRemarks());
				call.setString(4, whCheckOut.getLastUpdatedBy());
				call.setString(5, whCheckOut.getConfirmCZY());
				call.setString(6, whCheckOut.getDocumentNo());
				call.registerOutParameter(7, Types.INTEGER);
				call.registerOutParameter(8, Types.NVARCHAR);
				call.setString(9, whCheckOut.getItemAppsendDetailJson());
				call.setString(10, whCheckOut.getSalesInvoiceDetailJson());
				call.execute();
				result.setCode(String.valueOf(call.getInt(7)));
				result.setInfo(call.getString(8));
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				DbUtil.close(null, call, conn);
			}
			return result;
		}
	/**
	 * 送货单明细
	 * 
	 * @param page
	 * @param purchase
	 * @return
	 */
	public Page<Map<String,Object>> findItemAppSendDetailBySql(Page<Map<String,Object>> page, WHCheckOut whCheckOut) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select * from (select a.No, a.IANo, a.date, a.WHCode, y.Remarks ItemAppRemarks, a.IsCheckOut, " 
                   + " a.WHCheckOutNo, a.CheckSeq, d.Address Address, d.DocumentNo, "
                   + " y.SupplCode, y.Type ,c.NOTE TypeDescr, y.SendDate, y.ItemType1,"
                   + " g.Descr ItemType1Descr, x1.NOTE SendType, "
                   + " round(isnull(f.ItemSumCost,0), 2) ItemSumCost,"
                   + " round(isnull(h.ProjectAmount,0), 2) ProjectAmount, x2.Note IsSetItemDescr,"
                   + " ( select top 1 it2.Descr from   tItemAppDetail iad ,tItem i ,tItemType2 it2  "
                   + "        where  iad.ItemCode = i.Code and i.ItemType2 = it2.Code and iad.No = a.IANo   " 
                   + "         ) ItemType2Descr," 
                   + " a.SendFee,a.ProjectOtherCost, a.WHFee,a.Remarks ,x3.Note CheckStatusDescr,"
                   + " round(isnull(f.ItemSumCost,0)+isnull(a.SendFee,0)*(case when wh.SendFeeCostType='2' or wh.SendFeeCostType='3' then 1 else 0 end)" 
                   + "  +isnull(a.WHFee,0)*(case when wh.WHFeeCostType='2' or wh.WHFeeCostType='3' then 1 else 0 end)+isnull(a.CarryFee,0)+isnull(a.LongFee,0),2) SumCost ,  " //+isnull( a.WHFee,0)
                   + " round(isnull(h.ProjectAmount,0)+isnull(a.SendFee,0) *(case when wh.SendFeeCostType='2' or wh.SendFeeCostType='3' then 1 else 0 end)" 
                   + " +isnull(a.ProjectOtherCost,0) + isnull(a.WHFee,0)*(case when wh.WHFeeCostType='2' or wh.WHFeeCostType='3' then 1 else 0 end)+isnull(a.CarryFee,0)+isnull(a.LongFee,0),2) SumProjectAmount  "//+isnull( a.WHFee,0)
                   + " ,wh.WHFeeCostType,y.ProjectMan ProjectManDescr,a.carryfee, " // 改为取领料单的项目经理 em.NameChi ProjectManDescr
                   + " d.PayeeCode,i.Descr PayeeDescr,wh.SendFeeCostType,"	//增加签约公司 add by zb on 20200423
                   + " a.LongFee,a.AutoSendFee,a.AutoLongFee,a.AutoCarryFee"
                   + " from  tItemAppSend a "
                   + " left outer join tItemApp y on y.no = a.IAno "
                   + " left outer join tSupplier b on b.Code = y.SupplCode "
                   + " left outer join tXTDM c on c.CBM = y.Type  and c.ID = 'ITEMAPPTYPE' "
                   + " left outer join tCustomer d on d.Code = y.CustCode "
                   + " left outer join tXTDM x1 on x1.IBM = y.SendType and x1.id = 'ITEMAPPSENDTYPE' "   //and y.SendType = 2 "
                   + " left outer join tItemType1 g on g.code = y.ItemType1 "
                   + " left  outer join tWareHouse wh on a.WHCode=wh.Code "
                   //+ " left outer join tItemAppDetail e on e.no = y.no "
                   //+ " left outer join tItemtype2 i on i.code = y.itemtype2 "
                   + " left outer join tXTDM x2 on y.IsSetItem = x2.CBM  and x2.ID = 'YESNO' "
                   + " left outer join tXTDM x3 on d.CheckStatus=x3.IBM and x3.id='CheckStatus'  "
                   + " left outer join tEmployee em on em.Number=d.ProjectMan "
                   + " left outer join ( select  No, sum(SendQty * Cost) ItemSumCost "
                   + " 				from      tItemAppSendDetail  group by  No "
                   + " 			   ) f on f.no = a.no "
                   + " left outer join ( select    a.No, "
                   + " sum(a.SendQty * b.ProjectCost) ProjectAmount "
                   + " from      tItemAppSendDetail a , "
                   + " tItemAppDetail b "
                   + " where     a.RefPk = b.PK "
                   + " group by  a.No "
                   + " ) h on h.no = a.no "
                   + " left join tTaxPayee i on i.Code=d.PayeeCode "
                   + " where 1=1 " ;

	    	if (StringUtils.isNotBlank(whCheckOut.getNo())) {
				sql += " and a.WHCheckOutNo=? ";
				list.add(whCheckOut.getNo());
			}else{
				return null;
			}
	    	/*sql += " group by  a.No, a.IANo, a.date, a.WHCode, y.Remarks, a.IsCheckOut, "
	    		+ " a.WHCheckOutNo, a.CheckSeq, d.Address, d.DocumentNo, y.SupplCode, "
	    		+ " c.NOTE, y.SendDate, x1.NOTE, y.Type, y.ProjectAmount, y.ItemType1, "
	    		+ " g.Descr, i.Descr, f.ItemSumCost, h.ProjectAmount, x2.Note,a.SendFee,a.ProjectOtherCost, a.WHFee,a.Remarks " ;*/
	    	/*if (StringUtils.isNotBlank(page.getPageOrderBy())) {
				sql += ") a order by a." + page.getPageOrderBy() + " "
						+ page.getPageOrder();
			} else {
				sql += ") a order by a.CheckSeq";
			}*/
	    	sql += ") a order by a.CheckSeq asc";
	    	return this.findPageBySql(page, sql, list.toArray());
			}
	
	/**
	 * 销售单明细
	 * 
	 * @param page
	 * @param purchase
	 * @return
	 */
	public Page<Map<String,Object>> findSalesInvoiceDetailBySql(Page<Map<String,Object>> page, WHCheckOut whCheckOut) {
		List<Object> list = new ArrayList<Object>();
		String sql = " select a.IsCheckOut, a.No, c.note SalesTypeDescr, a.CustCode, a.custName, a.WHCode, "
                   + "    b.Address, a.Type, a.GetItemDate, a.Remarks, a.CheckSeq, "
		           + "    case when a.Type = 'R' then a.Amount * -1 else a.Amount end Amount, "
		           + "    case when a.Type = 'R' then a.TotalAdditionalCost * -1 else a.TotalAdditionalCost end TotalAdditionalCost, "
                   + "    a.Date, d.TotalMaterialCost "
                   + " from tSalesInvoice a  "
                   + " left join tsaleCust b on b.Code = a.CustCode "
                   + " left join tXTDM c on c.CBM = a.Type and c.ID = 'SALESINVTYPE' "
                   + " outer apply ( "
                   + "     select "
                   + "         round(sum(case when in_a.Type = 'R' then in_b.Qty * in_b.BCost * -1 "
                   + "                        else in_b.Qty * in_b.BCost end), 2) TotalMaterialCost "
                   + "     from tSalesInvoice in_a "
                   + "     left join tSalesInvoiceDetail in_b on in_b.SINo = in_a.no "
                   + "     where in_a.No = a.No "
                   + " ) d "
                   + " where 1=1 " ;
                 
    	if (StringUtils.isNotBlank(whCheckOut.getNo())) {
			sql += " and a.WHCheckOutNo=? ";
			list.add(whCheckOut.getNo());
		}else{
			return null;
		}
    	
		sql += " order by a.No desc " ;
		
        return this.findPageBySql(page, sql, list.toArray());
    }
	
	/**
	 * 送货单新增
	 * 
	 * @param page
	 * @param purchase
	 * @return
	 */
	public Page<Map<String, Object>> findWHCheckOutItemAppSendAdd(Page<Map<String, Object>> page,ItemAppSend itemAppSend){
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select a.IsCheckOut,a.no, a.IANo,a.date,a.WHCode,a.Remarks,t1.Remarks ItemAppRemarks,a.WHCheckOutNo,a.CheckSeq, "
	               + " x2.NOTE ItemType1Descr,d.Address Address, "
	               + " t1.CustCode,d.Descr,d.DocumentNo,t1.SupplCode,c.NOTE TypeDescr,t1.Type,t1.SendDate,"
	               + " x1.NOTE SendType,t1.OtherCost,t1.OtherCostAdj,round(isnull(f.ItemSumCost,0),2) ItemSumCost, "
	               + " x3.Note IsSetItemDescr,round(isnull(h.ProjectAmount,0),2) ProjectAmount ,x4.Note CheckStatusDescr,"
	               + " ( select top 1 it2.Descr from   tItemAppDetail iad ,tItem i ,tItemType2 it2  "
                   + "        where  iad.ItemCode = i.Code and i.ItemType2 = it2.Code and iad.No = a.IANo   " 
                   + "         ) ItemType2Descr,isnull(a.SendFee,0) SendFee,0.0 as ProjectOtherCost, " //a.ProjectOtherCost
	               + " case when wh.isWHFee = '1' then round(case when wh.WHFeeType='1' then isnull(h.ProjectAmount * wh.WHFeePer, 0) " 
	               + " else isnull(h.WHFee,0) end ,2)  else 0 end WHFee,isnull(a.carryfee,0) carryfee, " 
	               + " wh.WHFeeCostType,t1.ProjectMan ProjectManDescr,d.PayeeCode,i.Descr PayeeDescr,wh.SendFeeCostType, " 
	               + " isnull(a.LongFee,0) LongFee,isnull(a.AutoLongFee,0) AutoLongFee,isnull(a.AutoCarryFee,0) AutoCarryFee,isnull(a.AutoSendFee,0)AutoSendFee "
                   + " from tItemAppSend a "
	               + " left outer join tItemApp t1 on t1.no=a.IANo "
	               + " left outer join tSupplier b on b.Code=t1.SupplCode"
	               + " left outer join tXTDM c on c.CBM=t1.Type and c.ID='ITEMAPPTYPE' "
	               + " left outer join tCustomer d on d.Code=t1.CustCode  "
	               + " left outer join tXTDM x1 on x1.IBM=t1.SendType and x1.id='ITEMAPPSENDTYPE' "
	               + " left outer join tXTDM x2 on x2.CBM=t1.ItemType1 and x2.id='ITEMRIGHT' "
	               + " left outer join tXTDM x3 on t1.IsSetItem=x3.CBM and x3.ID='YESNO' "
	               + " left outer join tXTDM x4 on d.CheckStatus=x4.IBM and x4.id='CheckStatus'  "
	               + " left  outer join tWareHouse wh on a.WHCode=wh.Code "
	               + " left outer join (select No,sum(SendQty*Cost) ItemSumCost from tItemAppSendDetail group by No) f on f.no=a.no "
	               + " left outer join tEmployee em on em.Number=d.ProjectMan "
	               + " left outer join (select a.No, sum(a.SendQty * b.ProjectCost) ProjectAmount," 
	               + " sum(case when c.WhFeeType='1' then a.SendQty*b.ProjectCost*c.WHFee else a.SendQty*c.WHFee end) WHFee "
	               + " 		from tItemAppSendDetail a "
	               + " 		left outer join tItemAppDetail b on a.RefPK = b.pk "
	               + "      left outer join tItem c on b.Itemcode = c.Code "
	               + "      group by a.No  ) h on h.no = a.no "
	               + " left join tTaxPayee i on i.Code=d.PayeeCode "
	               + " where 1=1 ";
	    if(StringUtils.isNotBlank(itemAppSend.getItemRight())){
	    	sql+=" and t1.ItemType1 in  "+"('"+itemAppSend.getItemRight().trim().replace(",","','")+"')";
	    }else {
			sql+=" and 1<>1";
		} 
	    
		if (StringUtils.isNotBlank(itemAppSend.getNo())) {
			sql += " and a.No=? ";
			list.add(itemAppSend.getNo());
		}
		if (StringUtils.isNotBlank(itemAppSend.getIaNo())) {
    		sql += " and a.IaNo=? ";
    		list.add(itemAppSend.getIaNo());
    	}
    	if (itemAppSend.getDateFrom() != null){
			sql += " and a.Date>= ? ";
			list.add(itemAppSend.getDateFrom());
		}
		if (itemAppSend.getDateTo() != null){
			sql += " and a.Date<= ? ";
			list.add(DateUtil.addDateOneDay(itemAppSend.getDateTo()));
		}
    	if (StringUtils.isNotBlank(itemAppSend.getWhCode())) {
			sql += " and a.WHCode=? ";
			list.add(itemAppSend.getWhCode());
		}
    	if (StringUtils.isNotBlank(itemAppSend.getCustCode())) {
			sql += " and t1.CustCode=? ";
			list.add(itemAppSend.getCustCode());
		}
    	if (StringUtils.isNotBlank(itemAppSend.getAddress())) {
			sql += " and d.Address like =? ";
			list.add("%" + itemAppSend.getAddress() + "%");
		}
    	if (StringUtils.isNotBlank(itemAppSend.getRemarks())) {
			sql += " and a.Remarks=? ";
			list.add(itemAppSend.getRemarks());
		}
    	if (StringUtils.isNotBlank(itemAppSend.getItemType1())) {
			sql += " and t1.ItemType1=? ";
			list.add(itemAppSend.getItemType1());
		}
    	if (StringUtils.isNotBlank(itemAppSend.getItemType2())) {
			sql += " and t1.ItemType2=? ";
			list.add(itemAppSend.getItemType2());
		}
    	if (StringUtils.isNotBlank(itemAppSend.getWhCheckOutNo())) {
			sql += " and (a.IsCheckOut='0' or (a.IsCheckOut='1' and a.WHCheckOutNo=?)) ";
			list.add(itemAppSend.getWhCheckOutNo());
		}else{
			sql += " and a.IsCheckOut='0' ";
		}
    	if (StringUtils.isNotBlank(itemAppSend.getUnSelected())) {
			sql += " and a.No not in(select * from fStrToTable('"+itemAppSend.getUnSelected()+"',',')) ";
		}
    	/* sql += " group by a.IsCheckOut,t1.No,x2.NOTE ,i2.Descr ,d.Address ,"
         + " t1.CustCode,d.Descr,d.DocumentNo,t1.SupplCode,c.NOTE,t1.SendDate,"
         + " x1.NOTE ,t1.Remarks,t1.OtherCost,t1.OtherCostAdj,t1.Date, a.IsCheckOut,"
         + "a.no, a.IANo,a.date,a.WHCode,a.WHCheckOutNo,a.CheckSeq,f.ItemSumCost,h.ProjectAmount,x3.NOTE,a.SendFee,a.ProjectOtherCost,h.ProjectAmount,wh.WHFeePer,a.Remarks ";*/
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ") a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ") a order by a.no desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> findWHCheckOutSalesInvoiceAdd(Page<Map<String, Object>> page,SalesInvoice salesInvoice){
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from ( "
				   + " select a.IsCheckOut,a.No,a.type,f.note SalesTypeDescr,a.WHcode,b.Desc1 as WHCodeDescr,c.Address,a.CustName, "
	               + "     a.ItemType1,d.descr ItemType1descr,a.Date,a.GetItemDate,a.remarks,a.WHCheckOutNo,a.Status,a.CheckSeq, "
	               + "     case when a.Type = 'R' then a.Amount * -1 else a.Amount end Amount, "
	               + "     g.TotalMaterialCost, g.TotalAdditionalCost "
	               + " from tSalesInvoice a "
	               + " left outer join tWareHouse b on a.WHCode=b.Code "
	               + " left outer join tsaleCust c on c.Code=a.CustCode "
	               + " left outer join titemtype1 d on d.code=a.ItemType1 "
	               + " left outer join tXTDM f on f.CBM=a.Type and f.ID='SALESINVTYPE'  "
                   + " outer apply ( "
                   + "     select "
                   + "         round(sum(case when in_a.Type = 'R' then in_b.Qty * in_b.BCost * -1 "
                   + "                        else in_b.Qty * in_b.BCost end), 2) TotalMaterialCost, "
                   + "         round(sum(case when in_a.Type = 'R' then in_b.Qty * in_c.AdditionalCost * -1 "
                   + "                        else in_b.Qty * in_c.AdditionalCost end), 2) TotalAdditionalCost "
                   + "     from tSalesInvoice in_a "
                   + "     left join tSalesInvoiceDetail in_b on in_b.SINo = in_a.no "
                   + "     left join tItem in_c on in_c.Code = in_b.ITCode "
                   + "     where in_a.No = a.No "
                   + " ) g "
	               + " where 1=1";
		if (StringUtils.isNotBlank(salesInvoice.getNo())) {
			sql += " and a.No=? ";
			list.add(salesInvoice.getNo());
		}
		
    	if (salesInvoice.getDateFrom() != null){
			sql += " and a.Date>= ? ";
			list.add(salesInvoice.getDateFrom());
		}
		if (salesInvoice.getDateTo() != null){
			sql += " and a.Date<= ? ";
			list.add(DateUtil.addDateOneDay(salesInvoice.getDateTo()));
		}
    	if (StringUtils.isNotBlank(salesInvoice.getWhcode())) {
			sql += " and a.WHCode=? ";
			list.add(salesInvoice.getWhcode());
		}
    	if (StringUtils.isNotBlank(salesInvoice.getItemType1())) {
			sql += " and a.ItemType1=? ";
			list.add(salesInvoice.getItemType1());
		}
    	if (StringUtils.isNotBlank(salesInvoice.getCustCode())) {
			sql += " and a.CustCode=? ";
			list.add(salesInvoice.getCustCode());
		}
    	
    	if (StringUtils.isNotBlank(salesInvoice.getWhcheckOutNo())) {
			sql += " and (a.IsCheckOut='0' or (a.IsCheckOut='1' and a.WHCheckOutNo=?)) and a.Status = 'CONFIRMED' ";
			list.add(salesInvoice.getWhcheckOutNo());
		}else{
			sql += " and a.IsCheckOut='0' and a.Status = 'CONFIRMED' ";
		}
    	if (StringUtils.isNotBlank(salesInvoice.getUnSelected())) {
			sql += " and a.No not in(select * from fStrToTable('"+salesInvoice.getUnSelected()+"',',')) ";
		}

		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ") a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ") a order by a.no desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> findTotalByItemType2BySql(Page<Map<String,Object>> page, WHCheckOut whCheckOut) {
		List<Object> list = new ArrayList<Object>();
		String sql =" select  Itemtype2descr,round(sum(projectCost),2) sumprojectCost,round(sum(SumCost2),2) SumCost2  from "
		          + " ( select e.descr Itemtype2descr,sum(b.SendQty*c.projectCost) projectCost,Sum(b.SendQty*b.Cost) SumCost2 "
		          + " from tItemAppSend a "
		          + " left outer join tItemAppSendDetail b on a.No=b.No"
		          + " left outer join tItemAppDetail c on b.RefPK=c.PK"
		          + " left outer join tItem d on c.ItemCode=d.Code"
		          + " left outer join tItemType2 e on d.ItemType2=e.Code ";
		 if (StringUtils.isNotBlank(whCheckOut.getNo())) {
		  	sql += " where a.WHCheckOutNo=? ";
		  	list.add(whCheckOut.getNo());
		  }else{
		  	 return null;
		  }
		  sql += " group by e.Descr "
		          + " "
		          + " union all "
		          + " select  e.descr Itemtype2descr,0 projectCost, SUM(case when type='R' then t1.Qty*t1.BCost*-1 else  t1.Qty*t1.BCost end) SumCost2 "
		          + " from tSalesInvoice c "
		          + " left outer join tSalesInvoiceDetail t1 on t1.sino= c.no  left outer join  titem f on f.code=t1.itcode "
		          + " left outer join  tItemtype2 e on e.code=f.itemtype2  ";
          if (StringUtils.isNotBlank(whCheckOut.getNo())) {
  		  	sql += " where c.WHCheckOutNo=? ";
  		  	list.add(whCheckOut.getNo());
  		  }else{
  		  	 return null;
  		  }
		  sql += "group by  e.descr,projectCost )k "
		      + " group by k.Itemtype2descr "  ;

		  return this.findPageBySql(page, sql, list.toArray());
			}
	
	public Page<Map<String,Object>> findTotalByBrandBySql(Page<Map<String,Object>> page, WHCheckOut whCheckOut) {
		List<Object> list = new ArrayList<Object>();
		String sql =" select  SplCodeDescr,round(sum(projectCost),2) sumprojectCost,round(sum(SumCost2),2) SumCost2  from "
		          + " ( select e.descr SplCodeDescr,sum(b.SendQty*c.projectCost) projectCost,Sum(b.SendQty*b.Cost) SumCost2 "
		          + " from tItemAppSend a "
		          + " left outer join tItemAppSendDetail b on a.No=b.No"
		          + " left outer join tItemAppDetail c on b.RefPK=c.PK"
		          + " left outer join tItem d on c.ItemCode=d.Code"
		          + " left outer join tBrand e on d.SqlCode = e.Code ";
		 if (StringUtils.isNotBlank(whCheckOut.getNo())) {
		  	sql += " where a.WHCheckOutNo=? ";
		  	list.add(whCheckOut.getNo());
		  }else{
		  	 return null;
		  }
		  sql += " group by e.Descr "
		          + " "
		          + " union all "
		          + " select e.descr SplCodeDescr,0 projectCost, SUM(case when type='R' then t1.Qty*t1.BCost*-1 else  t1.Qty*t1.BCost end) SumCost2 "
		          + " from tSalesInvoice c "
		          + " left outer join tSalesInvoiceDetail t1 on t1.sino= c.no  left outer join  titem f on f.code=t1.itcode "
		          + " left outer join tBrand e on f.SqlCode = e.Code ";
          if (StringUtils.isNotBlank(whCheckOut.getNo())) {
  		  	sql += " where c.WHCheckOutNo=? ";
  		  	list.add(whCheckOut.getNo());
  		  }else{
  		  	 return null;
  		  }
		  sql += "group by  e.descr,projectCost )k "
		      + " group by SplCodeDescr "  ;

		  return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> findTotalByCompanyBySql(Page<Map<String,Object>> page, WHCheckOut whCheckOut) {
		List<Object> list = new ArrayList<Object>();
		String sql =" select  cmpname,round(sum(projectCost),2) sumprojectCost,round(sum(SumCost2),2) SumCost2  from "
		          + " ( select n.descr cmpname,sum(b.SendQty*c.projectCost) projectCost,Sum(b.SendQty*b.Cost) SumCost2 "
		          + " from tItemAppSend a "
		          + " left outer join tItemAppSendDetail b on a.No=b.No"
		          + " left outer join tItemAppDetail c on b.RefPK=c.PK"
		          + " left outer join tItem d on c.ItemCode=d.Code"
		          + " left outer join titemapp t1 on t1.no=a.iano" 
		          + " left outer join tcustomer e on e.code = t1.custcode" 
		          + " left join ttaxpayee n on n.code = e.payeecode";
		 if (StringUtils.isNotBlank(whCheckOut.getNo())) {
		  	sql += " where a.WHCheckOutNo=? ";
		  	list.add(whCheckOut.getNo());
		  }else{
		  	 return null;
		  }
		  sql += " group by n.Descr "
		          + " "
		          + " union all "
		          + " select n.descr cmpname,0 projectCost, SUM(case when type='R' then t1.Qty*t1.BCost*-1 else  t1.Qty*t1.BCost end) SumCost2 "
		          + " from tSalesInvoice c "
		          + " left outer join tSalesInvoiceDetail t1 on t1.sino= c.no  left outer join  titem f on f.code=t1.itcode "
		          + " left outer join tcustomer e on e.code = c.custcode" 
		          + " left join ttaxpayee n on n.code = e.payeecode ";
          if (StringUtils.isNotBlank(whCheckOut.getNo())) {
  		  	sql += " where c.WHCheckOutNo=? ";
  		  	list.add(whCheckOut.getNo());
  		  }else{
  		  	 return null;
  		  }
		  sql += "group by n.descr,projectCost )k "
		      + " group by cmpname "  ;
		  return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 自动生成其他费用计算
	 * @param itemAppSend
	 * @return
	 */
	public Map<String, Object> doGenWHCheckOutSendFee(
			WHCheckOut whCheckOut) {
		Connection conn = null;
		CallableStatement call = null;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call PGenWHCheckOutSendFee_forXml(?,?,?,?)}");
			call.setString(1, whCheckOut.getWhCode());
			call.setString(2, whCheckOut.getItemAppsendDetailJson());
			call.registerOutParameter(3, Types.INTEGER);
			call.registerOutParameter(4, Types.NVARCHAR);
			call.execute();
			ResultSet rs = call.getResultSet();	
			List<Map<String, Object>> sendFeeList = BeanConvertUtil
					.resultSetToList(rs);
			map.put("sendFeeList", sendFeeList);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return null;
	}
		
}

