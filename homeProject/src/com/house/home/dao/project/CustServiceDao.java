package com.house.home.dao.project;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.project.CustService;

@SuppressWarnings("serial")
@Repository
public class CustServiceDao extends BaseDao{

	/**
	 * @Description: 主页分页查询
	 * @author	created by zb
	 * @date	2018-8-6--下午3:02:01
	 * @param page
	 * @param custService
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, CustService custService) {
		List<Object> list = new ArrayList<Object>();
		
		String sql = " select * from ( " +
				" select a.No, a.Status, tx2.NOTE StatusDescr, a.Type, tx1.NOTE TypeDescr, a.Address, b.ConsEndDate, " +
				" b.Descr CustName, b.Mobile1, b.Mobile2, a.Remarks, b.ProjectMan, e1.NameChi ProjectManDescr, e1.Phone, " +
				" e1.Department2, d.Desc1 Department2Descr, a.RepMan, e2.NameChi RepManDescr, a.RepDate, a.ServiceMan, " +
				" e3.NameChi ServiceManDescr, a.DealMan, a.DealDate, a.LastUpdate, " +
				" a.LastUpdatedBy, a.Expired, a.ActionLog, a.Undertaker, tx3.NOTE UndertakerDescr " +
				" from tCustService a " +
				" left join tCustomer b on b.Code = a.CustCode " +
				" left join tEmployee e1 on e1.Number = b.ProjectMan " +
				" left join tEmployee e2 on e2.Number = a.RepMan " +
				" left join tEmployee e3 on e3.Number = a.ServiceMan " +
				" left join tDepartment2 d on d.Code = e1.Department2 " +
				" left join tXTDM tx1 on tx1.CBM = a.Type and tx1.ID = 'CUSTSRVTYPE' " +
				" left join tXTDM tx2 on tx2.CBM = a.Status and tx2.ID = 'CUSTSRVSTATUS' " +
				" left join tXTDM tx3 on tx3.CBM = a.Undertaker and tx3.ID = 'CUSTSRVUNTKER' " +
				" where 1=1 ";
		
		if(StringUtils.isNotBlank(custService.getCustAccountMobile1())){
			sql += " and exists ( "
				 + " 	select 1 from tCustAccount in_tca "
				 + " 	left join tCustomer in_tc on in_tca.PK = in_tc.CustAccountPk "
				 + " 	where in_tca.Mobile1=? and in_tc.Code=a.CustCode "
				 + " ) ";
			list.add(custService.getCustAccountMobile1());
		}
		
		if (StringUtils.isBlank(custService.getExpired()) || "F".equals(custService.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(custService.getAddress())) {
			sql += " and a.Address like ? ";
			list.add("%"+custService.getAddress()+"%");
		}
		if (StringUtils.isNotBlank(custService.getCustCode())) {
			sql += " and a.CustCode = ? ";
			list.add(custService.getCustCode());
		}
		if (StringUtils.isNotBlank(custService.getStatus())) {
			sql += " and a.Status in " + "('"+custService.getStatus().replaceAll(",", "','")+"')";
		}
		if (StringUtils.isNotBlank(custService.getType())) {
			sql += " and a.Type = ? ";
			list.add(custService.getType());
		}
		if (custService.getRepDateFrom() != null) {
			sql += " and a.RepDate >= ? ";
			list.add(custService.getRepDateFrom());
		}
		if (custService.getRepDateTo() != null) {
			sql += " and a.RepDate <= ?";
			list.add(new Timestamp(DateUtil.endOfTheDay(custService.getRepDateTo()).getTime()));
		}
		if (custService.getDealDateFrom() != null) {
			sql += " and a.DealDate >= ? ";
			list.add(custService.getDealDateFrom());
		}
		if (custService.getDealDateTo() != null) {
			sql += " and a.DealDate <= ?";
			list.add(new Timestamp(DateUtil.endOfTheDay(custService.getDealDateTo()).getTime()));
		}
		if (StringUtils.isNotBlank(custService.getServiceMan())) {
			sql += " and a.ServiceMan = ? ";
			list.add(custService.getServiceMan());
		}
		if (StringUtils.isNotBlank(custService.getAppStatus())) {
			if("1".equals(custService.getAppStatus())){
				sql += " and a.Status in('0','1') ";
			}else{
				sql += " and a.Status = '2' ";
			}
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " ) a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		} else {
			sql += " ) a order by a.LastUpdate desc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * @Description: 根据code获取相关客户信息（暂且客户售后（custService）、税务信息登记（custTax）在用。）
	 * @author	created by zb
	 * @date	2018-9-5--下午5:17:51
	 * @param code
	 * @return
	 */
	public Map<String, Object> getCustDetailByCode(String code) {
		String sql = " select a.custCheckDate,a.Code, a.Descr, a.Address, a.Mobile1, a.ConsEndDate, d21.Desc1 Department2Descr, " +
				" a.Status, a.SetDate, a.Mobile2, " +
				" a.SignDate, a.CustCheckDate, e1.Status ProManStatus, a.ProjectMan, e1.NameChi ProjectManDescr, tx1.NOTE ProManStatusDescr " +
				" from tCustomer a " +
				" left join tEmployee e1 on e1.Number = a.ProjectMan " +
				" left join tDepartment2 d21 on d21.Code = e1.Department2 " +
				" left join tXTDM tx1 on tx1.CBM = e1.Status and tx1.ID = 'EMPSTS' " +
				" where a.code = ? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{code});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	public Map<String, Object> getCustServiceDetail(String no){
		String sql = " select tc.Address,tx1.NOTE statusDescr,tx2.NOTE typeDescr,tcs.Remarks,tcs.RepDate,tc.EndDate, "
				   + " te1.NameChi serviceManDescr,tcs.DealMan dealManDescr,tcs.dealDate,tcs.type,tcs.Status, "
				   + " tc.Descr CustName,tc.Mobile1,tc.ProjectMan, te2.NameChi ProjectManDescr, te2.Phone, tx3.NOTE UndertakerDescr, "
				   + " tcs.FeedBackRemark,tx4.note DealStatusDescr,tcs.No,tx5.NOTE addressStatus "
				   + " from tCustService tcs "
				   + " left join tCustomer tc on tcs.CustCode = tc.Code "
				   + " left join tXTDM tx1 on tx1.ID='CUSTSRVSTATUS' and tx1.CBM = tcs.Status "
				   + " left join tXTDM tx2 on tx2.ID='CUSTSRVTYPE' and tx2.CBM = tcs.Type "
				   + " left join tEmployee te1 on te1.Number = tcs.ServiceMan "
				   + " left join tEmployee te2 on te2.Number = tc.ProjectMan "
				   + " left join tXTDM tx3 on tx3.CBM = tcs.Undertaker and tx3.ID = 'CUSTSRVUNTKER' "
				   + " left join tCustProblem tcu on tcu.PK=tcs.CustProblemPK"
				   + " left join tXTDM tx4 on tx4.cbm = tcu.status and tx4.id='PROMSTATUS' "
				   + " left join tXTDM tx5 on tc.Status=tx5.CBM and tx5.ID='CUSTOMERSTATUS' "
				   + " where tcs.No=? ";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[]{no});
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	public Map<String, Object> goComplete(String no){
		String sql = " select tcs.DealMan,tcs.dealDate,tcs.No from tCustService tcs "
				   + " where tcs.No=? ";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[]{no});
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	public Page<Map<String, Object>> AppfindBySql(
			Page<Map<String, Object>> page, CustService custService) {
		List<Object> list = new ArrayList<Object>();
		
		String sql = "select * from ( select a.Remarks,a.RepDate,a.No,a.Address from tCustService a "
				   + "left join tEmployee b on a.ServiceMan = b.Number "
				   + "where 1=1 ";
		
		if (StringUtils.isBlank(custService.getExpired()) || "F".equals(custService.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(custService.getAddress())) {
			sql += " and (a.Address like ? or b.nameChi like ?) ";
			list.add("%"+custService.getAddress()+"%");
			list.add("%"+custService.getAddress()+"%");
		}
		if (StringUtils.isNotBlank(custService.getAppStatus())) {
			if("0".equals(custService.getAppStatus())) {
				sql += " and a.Status in('0','1') and (a.FeedBackRemark is null or a.FeedBackRemark = '') ";
			}else if("1".equals(custService.getAppStatus())){
				sql += " and a.Status in('0','1') ";
			}else{
				sql += " and a.Status = '2' ";
			}
		}
		if (StringUtils.isNotBlank(custService.getServiceMan())) {
			sql += " and a.ServiceMan = ? ";
			list.add(custService.getServiceMan());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " ) a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		} else {
			sql += " ) a order by a.RepDate desc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
}
