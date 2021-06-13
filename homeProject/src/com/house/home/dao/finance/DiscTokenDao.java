package com.house.home.dao.finance;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.SqlUtil;
import com.house.home.entity.finance.DiscToken;

@SuppressWarnings("serial")
@Repository
public class DiscTokenDao extends BaseDao {

	/**
	 * DiscToken分页信息
	 * 
	 * @param page
	 * @param discToken
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, DiscToken discToken) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from(" +
				" select a.CustCode,b.Address,a.ItemType1,it1.Descr ItemType1Descr,a.No,a.Descr," +
				" x3.NOTE CustStatusDescr, a.Amount,a.Status,a.EnableDate,a.CrtDate,x1.NOTE StatusDescr," +
				" c.Address UseAddress, a.UseStep,x2.NOTE UseStepDescr,ChgNo,a.CheckDesAmount,a.CheckDesDate, "+
				" ExpiredDate "+
				" from tDiscToken a "+
				" left join tcustomer b on a.CustCode=b.Code "+
				" left join tItemType1 it1 on it1.Code=a.ItemType1 "+
				" left join tXTDM x1 on x1.ID='DISCTOKENSTATUS' and x1.CBM=a.Status "+
				" left join tXTDM x2 on x2.ID='DISCTOKENUSE' and x2.CBM=a.UseStep "+
				" left join tXTDM x3 on x3.ID='CUSTOMERSTATUS' and x3.CBM = b.Status "+
				" left join tcustomer c on a.UseCustCode=c.Code" +
				" where 1=1 and b.IsInitSign='0' and a.Status<>'1' ";

 
    	if (StringUtils.isNotBlank(discToken.getNo())) {
			sql += " and a.No like ? ";
			list.add("%" + discToken.getNo() + "%");
		}
    	if (StringUtils.isNotBlank(discToken.getAddress())) {
			sql += " and b.Address like ? ";
			list.add("%" + discToken.getAddress() + "%");
		}
    	if (StringUtils.isNotBlank(discToken.getStatus())) {
			String str = SqlUtil.resetStatus(discToken.getStatus());
			sql += " and a.status in (" + str + ")";
		}
    	if (StringUtils.isNotBlank(discToken.getItemType1())) {
			sql += " and a.ItemType1=? ";
			list.add(discToken.getItemType1());
		}
    	if (StringUtils.isNotBlank(discToken.getDateType()) && null != discToken.getDateFrom()){
			if("1".equals(discToken.getDateType())){
				sql += " and a.EnableDate >= ? ";	
			}else if("2".equals(discToken.getDateType())){
				sql += " and a.CrtDate >= ? ";	
			}else if("3".equals(discToken.getDateType())){
				sql += " and a.ExpiredDate >= ? ";	
			}else if("4".equals(discToken.getDateType())){
				sql += " and a.CheckDesDate >= ? ";	
			}else{
				sql += " and a.EnableDate >= ? ";	
			}
			list.add(discToken.getDateFrom());
		}
    	if (StringUtils.isNotBlank(discToken.getDateType()) && null != discToken.getDateTo()){
				if("1".equals(discToken.getDateType())){
					sql += " and a.EnableDate <= ? ";	
				}else if("2".equals(discToken.getDateType())){
					sql += " and a.CrtDate <= ? ";	
				}else if("3".equals(discToken.getDateType())){
					sql += " and a.ExpiredDate <= ? ";	
				}else if("4".equals(discToken.getDateType())){
					sql += " and a.CheckDesDate <= ? ";	
				}else{
					sql += " and a.EnableDate <= ? ";	
				}
				list.add(new Timestamp(DateUtil.endOfTheDay(discToken.getDateTo()).getTime()));
		}
    	if (StringUtils.isNotBlank(discToken.getCustCode())) {
			if (StringUtils.isNotBlank(discToken.getOldCustCode())) {
				sql += " and (a.CustCode=?  or a.CustCode=? ) ";
				list.add(discToken.getCustCode());	
				list.add(discToken.getOldCustCode());	
			}else{
				sql += " and a.CustCode=? ";
				list.add(discToken.getCustCode());
			}
			if("1".equals(discToken.getContainHasSelect())){
				sql += " or (a.useCustCode=? and a.UseStep='1' and a.status='3') ";
				list.add(discToken.getCustCode());
			}
		
		}
    	if (StringUtils.isNotBlank(discToken.getDiscType())) {
			sql += " and a.DiscType=? ";
			list.add(discToken.getDiscType());
		}
    	if (StringUtils.isNotBlank(discToken.getMode())) {
			sql += " and a.Mode=? ";
			list.add(discToken.getMode());
		}
    	if (discToken.getAmount() != null) {
			sql += " and a.Amount=? ";
			list.add(discToken.getAmount());
		}
    	if (discToken.getProvideDate() != null) {
			sql += " and a.ProvideDate=? ";
			list.add(discToken.getProvideDate());
		}
    	if (discToken.getCrtDate() != null) {
			sql += " and a.CrtDate=? ";
			list.add(discToken.getCrtDate());
		}
    	
    	if (StringUtils.isNotBlank(discToken.getChgNo())) {
			sql += " and a.ChgNo=? ";
			list.add(discToken.getChgNo());
		}
    	if (StringUtils.isNotBlank(discToken.getUseCustCode())) {
			sql += " and a.UseCustCode=? ";
			list.add(discToken.getUseCustCode());
		}
    	if (StringUtils.isNotBlank(discToken.getUseStep())) {
			sql += " and a.UseStep=? ";
			list.add(discToken.getUseStep());
		}
		if (StringUtils.isBlank(discToken.getExpired()) || "F".equals(discToken.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    
    	if (StringUtils.isNotBlank(discToken.getUnSelectNo())) {
			String str = SqlUtil.resetStatus(discToken.getUnSelectNo());
			sql += " and a.No not in in (" + str + ")";
		}
    	if (StringUtils.isNotBlank(discToken.getHasSelectNo())) {
			String str = SqlUtil.resetStatus(discToken.getHasSelectNo());
			sql += " or a.No in (" + str + ")";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.No";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Double getDiscTokenAmountTotal(DiscToken discToken) {
		List<Object> list = new ArrayList<Object>();
		String sql = " select isnull(sum(amount),0) amount from tDiscToken a where a.Expired='F'  ";

		if (StringUtils.isNotBlank(discToken.getCustCode())) {
    		sql += " and a.CustCode=? ";
			list.add(discToken.getCustCode());
		}else if(StringUtils.isNotBlank(discToken.getUseCustCode())){
			sql += " and a.UseCustCode=? ";
			list.add(discToken.getUseCustCode());
		}else if(StringUtils.isNotBlank(discToken.getChgNo())){
			sql += " and a.ChgNo=? ";
			list.add(discToken.getChgNo());
		}else{
			return 0.0;
		}
		
		if (StringUtils.isNotBlank(discToken.getStatus())) {
			sql += " and a.Status=? ";
			list.add(discToken.getStatus());
		}
		List<Map<String,Object>> listResult = this.findBySql(sql, list.toArray());
		if (listResult != null && listResult.size() > 0) {
			return Double
					.parseDouble(String.valueOf(listResult.get(0).get("amount")));
		} 
		return 0.00;
	}
	
	/**
	 * DiscToken选中明细信息
	 * @param page
	 * @param discToken
	 * @return
	 */
	public Page<Map<String,Object>> findHasSelectPageBySql(Page<Map<String,Object>> page, DiscToken discToken) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from(" +
				" select a.CustCode,b.Address,a.ItemType1,it1.Descr ItemType1Descr,a.No,a.Descr," +
				" a.Amount,a.Status,a.EnableDate,a.CrtDate,x1.NOTE StatusDescr," +
				" c.Address UseAddress, a.UseStep,x2.NOTE UseStepDescr,ChgNo,a.CheckDesAmount,a.CheckDesDate, "+
				" ExpiredDate "+
				" from tDiscToken a "+
				" left join tcustomer b on a.CustCode=b.Code "+
				" left join tItemType1 it1 on it1.Code=a.ItemType1 "+
				" left join tXTDM x1 on x1.ID='DISCTOKENSTATUS' and x1.CBM=a.Status "+
				" left join tXTDM x2 on x2.ID='DISCTOKENUSE' and x2.CBM=a.UseStep "+
				" left join tcustomer c on a.UseCustCode=c.Code" +
				" where 1=1 ";
    	if (StringUtils.isNotBlank(discToken.getHasSelectNo())) {
			sql += " and a.no in(select * from fStrToTable('"+discToken.getHasSelectNo()+"',',')) ";
		}else{
			return null;
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.No";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public String getDiscTokenNo(DiscToken discToken){
		List<Object> list = new ArrayList<Object>();
		String sql = "select a.No from tDiscToken a where 1=1 ";	
		
    	if (StringUtils.isNotBlank(discToken.getCustCode())) {
    		sql += " and a.CustCode=? ";
			list.add(discToken.getCustCode());
		}else if(StringUtils.isNotBlank(discToken.getUseCustCode())){
			sql += " and a.UseCustCode=? ";
			list.add(discToken.getUseCustCode());
		}else if(StringUtils.isNotBlank(discToken.getChgNo())){
			sql += " and a.ChgNo=? ";
			list.add(discToken.getChgNo());
		}else{
			return "";
		}
    	
    	if (StringUtils.isNotBlank(discToken.getStatus())) {
			sql += " and a.Status=? ";
			list.add(discToken.getStatus());
		}
		List<Map<String,Object>> listResult= this.findBySql(sql, list.toArray());
		if (listResult!=null && listResult.size()>0){
			String discTokenNo="";
			for(Map<String, Object> map:listResult){
				discTokenNo+=map.get("No").toString()+",";
			}
			discTokenNo = discTokenNo.substring(0,discTokenNo.length()-1);
			return discTokenNo;	
		}
		return "";
	}
	
	public void updateDiscTokenStaus(String custCode, String status){
		String sql="update tDiscToken set Status=?, "; 
		if("5".equals(status)){ // 失效
			 sql += " ExpiredDate=getdate() where Status not in('4','5')  ";
		}else{ // 生效
			sql += " ExpiredDate=null where Status='5' ";
		}
		sql += "and custCode=? ";
		this.executeUpdateBySql(sql, new Object[]{status, custCode});
	}
	

}

