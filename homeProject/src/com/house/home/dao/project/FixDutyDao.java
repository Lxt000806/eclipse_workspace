package com.house.home.dao.project;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.commons.utils.SqlUtil;
import com.house.framework.web.login.UserContext;
import com.house.home.client.service.evt.FixDutyEvt;
import com.house.home.entity.project.FixDuty;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

@SuppressWarnings("serial")
@Repository
public class FixDutyDao extends BaseDao{
	
	public Page<Map<String, Object>> getFixDutyList(
		Page<Map<String, Object>> page, FixDutyEvt evt) {
		List<Object> list = new ArrayList<Object>();
		String sql =  " select a.no,a.AppDate,a.OfferPrj,a.Material,(a.OfferPrj+a.Material) Price, (select isnull(sum(Amount),0) from tFixDutyMan where No=a.no and WorkerCode<>?)CfmPrice ,a.Remarks, "
				+ " a.Status,b.NOTE StatusDescr,a.CancelRemark from tFixDuty a "
				+ " left join tXTDM b on a.Status = b.CBM "
				+ " where b.ID='FIXSTATUS' and a.CustWkPk= ? ";
		list.add(evt.getWorkCode());
		list.add(evt.getCustWkPk());
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> getFixDutyListForPrjMan(
			Page<Map<String, Object>> page, FixDutyEvt evt) {
			List<Object> list = new ArrayList<Object>();
			String sql =  " select a.no,a.AppDate,b.Address,a.OfferPrj,a.Material,(a.OfferPrj+a.Material) Price,a.Remarks,a.PrjRemark,a.Status,d.NOTE StatusDescr , " 
					+ " (select isnull(sum(Amount),0) from tFixDutyMan fm left join tFixDuty fd on fd.No=fm.No where fm.No=a.no and EmpCode= b.ProjectMan and fd.Status in('5','6','7') )CfmPrice, "
					+ " a.IsPrjAllDuty,e.Note AppManTypeDescr,case when a.AppManType='1' then c.NameChi else f.NameChi end NameChi,g.Remark FixDutyManRemark "
					+ " from tFixDuty a  "
					+ " left join tCustomer b on a.CustCode = b.Code "
					+ " left join tWorker c on a.AppWorkerCode=c.Code "
					+ " left join tXTDM d on a.Status = d.CBM "
					+ " left join tXTDM e on a.AppManType = e.CBM and e.ID = 'APPMANTYPE' "
					+ " left join tEmployee f on a.AppCzy = f.Number "
					+ " left join tFixDutyMan g on g.No = a.No and g.EmpCode = a.PrjMan and g.FaultType = '1' "
					+ " where d.ID='FIXSTATUS' and b.ProjectMan=?  ";
			list.add(evt.getPrjMan());
			if("3".equals(evt.getStatus())){
				sql +=" and a.status in ('3','4','5','6','7','8') ";
			}else{
				sql +=" and a.status = ? ";
				list.add(evt.getStatus());
			}
			if(StringUtils.isNotBlank(evt.getAddress())){
				sql+=" and b.address like ? ";
				list.add("%"+evt.getAddress()+"%");
			}
			sql +=" order by a.AppDate desc ";
			
			return this.findPageBySql(page, sql, list.toArray());
		}

	public Page<Map<String, Object>> getFixDutyList(Page<Map<String, Object>> page, FixDuty fixDuty, UserContext uc){
		List<Object> list = new ArrayList<Object>();
		String sql = "select a.No,a.CustCode,a.CustWkPk,a.AppDate,a.AppCZY,a.AppWorkerCode,a.OfferPrj," +
				" a.Material,a.Remarks,a.Status,a.PrjConfirmDate,a.PrjMan,a.PrjRemark,a.CfmDate,a.CfmMan," +
				" a.CfmRemark,a.CfmOfferPrj,a.CfmMaterial,a.DutyMan,a.DutyDate,a. ManageCfmDate,a.ManageCfmMan," +
				" a.ProvideMan,a.ProvideDate,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,x1.note statusDescr,c.Code WorkType12," +
				" c.descr workType12Descr ,case when a.appManType = '2' then e.nameChi else f.nameChi end appManDescr,  " +
				" x2.note appManTypeDescr,g.address,a.CfmReturnRemark " +
				" from tFixDuty a " +
				" left join tCustWorker b on b.pk=a.CustWkPk " +
				" left join tWorkType12 c on c.code= b.workType12 " +
				" left join tEmployee d on d.number = a.PrjMan " +
				" left join tEmployee e on e.number = a.appCzy and a.appManType = '2' " +
				" left join tWorker f on f.code = a.appCzy and a.appManTYpe ='1' " +
				" left join tCustomer g on g.code=a.CUstCode " +
				" left join tXTDM x1 on x1.id='FIXSTATUS' and x1.cbm = a.status " +
				" left Join tXtdm x2 on x2.id = 'APPMANTYPE' and x2.cbm = a.AppmanType " +
				" where 1=1 and " + SqlUtil.getCustRight(uc, "g", 0);;
		
		if(StringUtils.isNotBlank(fixDuty.getCzybh())){
				sql+=" and exists (" +
						" select *  from tczybm in_a" +
						" left join tPrjRole in_b on in_a.PrjRole=in_b.Code" +
						" left join tPrjRoleWorkType12 in_c on in_b.Code=in_c.PrjRole" +
						" where  in_a.CZYBH=? and (in_a.PrjRole='' or in_a.PrjRole is null or b.WorkType12=in_c.WorkType12)  " +
						") ";
				list.add(fixDuty.getCzybh());
		}
		if(StringUtils.isNotBlank(fixDuty.getWorkType12())){
			sql+=" and b.worktype12 = ? ";
			list.add(fixDuty.getWorkType12());
		}
		if(StringUtils.isNotBlank(fixDuty.getAddress())){
			sql+=" and g.address like ? ";
			list.add("%"+fixDuty.getAddress()+"%");
		}
		if(StringUtils.isNotBlank(fixDuty.getStatus())){
			if("3".equals(fixDuty.getStatus())){
				sql+=" and a.status = ?";
				list.add(fixDuty.getStatus());
			}else{
				sql+=" and a.status <> '3' and a.cfmMan = ? ";
				list.add(fixDuty.getCzybh());
			}
		}
		if(StringUtils.isNotBlank(fixDuty.getNo())){
			sql+=" and a.No = ? ";
			list.add(fixDuty.getNo());
		}
		if(StringUtils.isNotBlank(fixDuty.getDepartment2s())){
			sql += " and d.Department2 in ('"+fixDuty.getDepartment2s().replaceAll(",", "','") + "')";
		}
		sql+=" order by a.AppDate desc ";
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> getFixDutyDetail(Page<Map<String, Object>> page, FixDuty fixDuty){
		List<Object> list = new ArrayList<Object>();
		String sql = " select a.Pk,c.custCode,b.descr baseCheckItemDescr, a.baseCheckItemCode," +
				" a.Qty,case when c.status ='3' and a.CfmQty =0 then a.Qty else a.CfmQty end CfmQty," +
				" a.OfferPri,a.Material, a.Remark,c.remarks " +
				" from tFixDutyDetail a " +
				" left join tBaseCheckItem b on b.code = a.BaseCheckItemCode  " +
				" left join tFixDuty c on c.no =a.no " +
				" where 1=1 and a.expired  = 'F' ";
		
		if(StringUtils.isNotBlank(fixDuty.getNo())){
			sql+=" and a.No = ? ";
			list.add(fixDuty.getNo());
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> getBaseCheckItemList(Page<Map<String, Object>> page, String baseCheckItemCode,String custCode){
		List<Object> list = new ArrayList<Object>();
		String sql = " select c.Descr fixAreaDescr,b.Descr baseCheckItemDescr,a.Qty,a.OfferPri,a.Material " +
				" from tBaseCheckItemPlan a" +
				" left join tBaseCheckItem b on b.code = a.BaseCheckItemCode" +
				" left join tFixArea c on c.pk=a.FixAreaPK " +
				" where 1=1 ";
		
		if(StringUtils.isNotBlank(baseCheckItemCode)){
			sql+=" and a.baseCheckItemCode = ? ";
			list.add(baseCheckItemCode);
		}
		
		if(StringUtils.isNotBlank(custCode)){
			sql+=" and a.CustCode = ? ";
			list.add(custCode);
		}
		sql +=" order by a.BasecheckItemCode ";
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	@SuppressWarnings("deprecation")
	public Result doUpdateFixDutyDetail(FixDuty fixDuty,String xml) {
		Assert.notNull(fixDuty);
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pUpdateFixDuty_forXml(?,?,?,?)}");
			call.setString(1, fixDuty.getCfmMan());
			call.setString(2, xml);
			call.registerOutParameter(3, Types.INTEGER);
			call.registerOutParameter(4, Types.NVARCHAR);
			call.execute();
			result.setCode(String.valueOf(call.getInt(3)));
			result.setInfo(call.getString(4));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	/**
	 * FixDuty分页信息
	 * 
	 * @param page
	 * @param fixDuty
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, FixDuty fixDuty) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from(select a.No,a.CustCode,b.Address,x3.NOTE cwStatusDescr,x1.NOTE appManTypeDescr,"
				+ "case when a.AppManType='1' then d.NameChi else e6.NameChi end appManDescr,a.OfferPrj,a.Material,"
				+ "a.PrjConfirmDate,e1.NameChi prjManDescr,dbo.fGetDept2Descr(a.custCode,'20')  Department2,a.PrjRemark,a.CfmDate,e2.NameChi cfmManDescr,"
				+ "e3.NameChi dutyManDescr,a.DutyDate,a.ManageCfmDate,e4.NameChi manageCfmManDescr,"
				+ "a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,a.AppDate,a.Remarks,a.Status,a.CfmRemark, "
				+ "e5.NameChi provideManDescr,a.ProvideDate,a.CfmOfferPrj,a.CfmMaterial,x2.NOTE statusDescr,a.CancelRemark,b.ProjectMan,e7.nameChi projectManDescr, "
				+ "a.AppManType,a.AppWorkerCode,c.WorkType12,w.NameChi WorkerName,w.Code WorkerCode,wt12.Descr workType12Descr,isnull(fm.FixAmount,0)FixAmount,isnull(fm.payAmount,0)payAmount,  "
				+ "a.IsPrjAllDuty,a.CfmReturnRemark,b.DesignMan,isnull(b.DesignRiskFund,0)DesignRiskFund,isnull(b.PrjManRiskFund,0)PrjManRiskFund, "
				+ "b.CustType, f.Desc1 CustTypeDescr, a.Type, "
				+ "e8.NameChi DesignManName, e8.Phone DesignManPhone "
				+ "from tFixDuty a "
				+ "left join tCustomer b on a.CustCode=b.Code "
				+ "left join tCustWorker c on a.CustWkPk=c.PK "
				+ "left join tWorker d on a.AppWorkerCode=d.Code "
				+ "left join tEmployee e1 on a.PrjMan=e1.Number "
				+ "left join tDepartment2 d21 on d21.Code=e1.Department2 "
				+ "left join tEmployee e2 on a.CfmMan=e2.Number "
				+ "left join tEmployee e3 on a.DutyMan=e3.Number "
				+ "left join tEmployee e4 on a.ManageCfmMan=e4.Number "
				+ "left join tEmployee e5 on a.ProvideMan=e5.Number "
				+ "left join tEmployee e6 on a.AppCZY=e6.Number "
				+ "left join tXTDM x1 on a.AppManType=x1.IBM and x1.ID='APPMANTYPE' "
				+ "left join tXTDM x2 on a.Status=x2.IBM and x2.ID='FIXSTATUS' "
				+ "left join tXTDM x3 on c.Status=x3.IBM and x3.ID='CUSTWKSTATUS' "
				+ "left join tEmployee e7 on b.ProjectMan = e7.Number "
				+ "left join tEmployee e8 on e8.Number = b.DesignMan "
				+ "left join tWorker w on c.WorkerCode=w.code " 
				+ "left join tWorkType12 wt12 on c.WorkType12=wt12.code "
				+ "left join ( select no, sum(case when faulttype<>'2' then Amount else 0 end) payAmount, sum(Amount)FixAmount " 
				+ "from tFixDutyMan where Expired='F' group by No " 
				+ ")fm on fm.no=a.No "
				+ "left join tCusttype f on b.CustType = f.Code "
				+ "where 1=1 ";

    	if (StringUtils.isNotBlank(fixDuty.getNo())) {
			sql += " and a.No like ? ";
			list.add("%"+fixDuty.getNo()+"%");
		}
    	if (StringUtils.isNotBlank(fixDuty.getAddress())) {
			sql += " and b.address like ? ";
			list.add("%"+fixDuty.getAddress()+"%");
		}
    	if (StringUtils.isNotBlank(fixDuty.getCustCode())) {
			sql += " and a.CustCode=? ";
			list.add(fixDuty.getCustCode());
		}
		if (fixDuty.getDateFrom() != null) {
			sql += " and a.appdate>= ? ";
			list.add(fixDuty.getDateFrom() );
		}
		if (fixDuty.getDateTo()!= null) {
			sql += " and a.appdate< ? ";
			list.add(DateUtil.addInteger(fixDuty.getDateTo(),
					Calendar.DATE, 1));
		}

		if (fixDuty.getDutyDateFrom() != null) {
			sql += " and a.dutyDate>= ? ";
			list.add(fixDuty.getDutyDateFrom() );
		}
		if (fixDuty.getDutyDateTo()!= null) {
			sql += " and a.dutyDate< ? ";
			list.add(DateUtil.addInteger(fixDuty.getDutyDateTo(), Calendar.DATE, 1));
		}
		if (fixDuty.getManageCfmDateFrom() != null) {
			sql += " and a.ManageCfmDate>= ? ";
			list.add(fixDuty.getManageCfmDateFrom() );
		}
		if (fixDuty.getManageCfmDateTo()!= null) {
			sql += " and a.ManageCfmDate< ? ";
			list.add(DateUtil.addInteger(fixDuty.getManageCfmDateTo(), Calendar.DATE, 1));
		}
		
    	if (StringUtils.isNotBlank(fixDuty.getStatus())) {
			sql += " and a.Status in ('"+fixDuty.getStatus().replaceAll(",", "','") + "')";
		}
    	if (StringUtils.isNotBlank(fixDuty.getAppManType())) {
			sql += " and a.AppManType=? ";
			list.add(fixDuty.getAppManType());
		}
    	if (StringUtils.isNotBlank(fixDuty.getAppCzy())) {
			sql += " and a.AppCZY=? ";
			list.add(fixDuty.getAppCzy());
		}
    	if (StringUtils.isNotBlank(fixDuty.getAppWorkerCode())) {
			sql += " and a.AppWorkerCode=? ";
			list.add(fixDuty.getAppWorkerCode());
		}
		if (StringUtils.isBlank(fixDuty.getExpired())
				|| "F".equals(fixDuty.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if(StringUtils.isNotBlank(fixDuty.getWorkType12())){
			sql += " and  c.WorkType12 in " + "('"+fixDuty.getWorkType12().replace(",", "','" )+ "')";
		}
		if (StringUtils.isNotBlank(fixDuty.getDepartment2())) {
			sql += " and e1.Department2 in " + "('"+fixDuty.getDepartment2().replace(",", "','" )+ "')";
		}
    	if (StringUtils.isNotBlank(fixDuty.getType())) {
			sql += " and a.Type=? ";
			list.add(fixDuty.getType());
		}
    	
    	// 增加客户类型筛选条件
    	// 张海洋 20200617
    	if (StringUtils.isNotBlank(fixDuty.getCustType())) {
            String[] custTypes = fixDuty.getCustType().split(",");
            
            String temporary = "";
            for (String type : custTypes) {
                temporary += "?,";
                list.add(type);
            }
            
            sql += "and b.CustType in (" + temporary.substring(0, temporary.length() - 1) + ") ";
        }
    	
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.LastUpdate desc";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 明细
	 * 
	 * @param page
	 * @param fixDuty
	 * @return
	 */
	public Page<Map<String, Object>> goDetailJqGrid(Page<Map<String, Object>> page, FixDuty fixDuty) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from ("
		        + "select a.PK,a.No,a.BaseCheckItemCode,b.Descr baseCheckItemDescr,a.Qty,a.CfmQty,a.OfferPri, "
		        + "    a.Material,a.Remark,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog, "
		        + "    isnull(a.Qty, 0) * isnull(a.OfferPri, 0) allOfferPri, "
		        + "    isnull(a.Qty, 0) * isnull(a.Material, 0) allMaterial, a.BaseItemCode, c.Descr BaseItemDescr, "
		        + "    case when d.Type='1' then a.BaseCheckItemCode else a.BaseItemCode end ItemCode, "
		        + "    case when d.Type='1' then b.Descr else c.Descr end ItemDescr,d.DutyDate,e.NameChi DutyManDescr, "
		        + "    d.AppDate,d.Remarks,f.NOTE StatusDescr,g.Address,d.CustCode,d.ProvideDate, "
		        + "    case d.Type when '1' then (a.OfferPri + a.Material) * a.CfmQty "
		        + "                when '2' then (a.OfferPri + a.Material) * a.Qty end TotalAmount, "
		        + "    dbo.fGetEmpNameChi(g.Code, '00') DesignManName, "
		        + "    isnull(h.DesignerBearAmount, 0) DesignerBearAmount, "
		        + "    isnull(h.DesignerRiskFund, 0) DesignerRiskFund, "
		        + "    g.DesignerMaxDiscAmount + g.DirectorMaxDiscAmount + isnull(g.DesignRiskFund, 0) "
		        + "        + isnull(i.DiscTranBalance, 0) TotalDiscBalance, m.Desc2 DesignManDepartment1Descr, "
		        + "    case when d.Type='1' then bu.Descr else cu.Descr end UomDescr "
		        + "from tFixDutyDetail a "
		        + "left join tBaseCheckItem b on a.BaseCheckItemCode=b.Code "
		        + "left join tBaseItem c on a.BaseItemCode = c.Code "
		        + "inner join tFixDuty d on a.No = d.No "
		        + "left join tEmployee e on d.DutyMan = e.Number "
		        + "left join tXTDM f on d.Status = f.CBM and f.ID='FIXSTATUS' "
		        + "left join tCustomer g on d.CustCode = g.Code "
		        + "outer apply ( "
		        + "    select sum(in_a.Amount) DesignerBearAmount, "
		        + "           sum(in_a.RiskFund) DesignerRiskFund "
		        + "    from tFixDutyMan in_a "
		        + "    where in_a.No = a.No "
		        + "        and exists( "
		        + "            select 1 "
		        + "            from tCustStakeholder in1_a "
		        + "            where in1_a.CustCode = d.CustCode "
		        + "                and in1_a.Role = '00' "
		        + "                and in1_a.EmpCode = in_a.EmpCode "
		        + "        ) "
		        + ") h "
		        + "outer apply ( "
		        + "    select sum(in_a.Amount) DiscTranBalance "
		        + "    from tDiscAmtTran in_a "
		        + "    where ((in_a.Type = '1' and in_a.IsExtra = '1' and in_a.IsRiskFund = '0') " // 额外赠送
		        + "            or (in_a.Type = '2' and in_a.IsRiskFund = '0') " // 优惠额度支出 
		        + "            or (in_a.Type = '2' and in_a.IsRiskFund = '1')) " // 前端风险金支出
		        + "        and in_a.CustCode = d.CustCode "
		        + ") i "
		        + "left join tEmployee k on k.Number = g.DesignMan "
		        + "left join tDepartment1 m on m.Code = k.Department1 "
		        + "left join tUom cu on cu.Code = c.Uom "
		        + "left join tUom bu on bu.Code = b.Uom "
		        + "where 1=1 ";
		
		if(StringUtils.isNotBlank(fixDuty.getNo())){
			sql+=" and a.No=? ";
			list.add(fixDuty.getNo());
		}
		if(StringUtils.isNotBlank(fixDuty.getType())){
			sql+=" and d.Type=? ";
			list.add(fixDuty.getType());
		}
		if(StringUtils.isNotBlank(fixDuty.getDutyMan())){
			sql+=" and d.DutyMan=? ";
			list.add(fixDuty.getDutyMan());
		}
		if(StringUtils.isNotBlank(fixDuty.getAddress())){
			sql+=" and g.Address like ? ";
			list.add("%"+fixDuty.getAddress()+"%");
		}
		if (fixDuty.getDateFrom() != null) {
			sql += " and d.appdate>= ? ";
			list.add(fixDuty.getDateFrom() );
		}
		if (fixDuty.getDateTo()!= null) {
			sql += " and d.appdate< ? ";
			list.add(DateUtil.addInteger(fixDuty.getDateTo(),
					Calendar.DATE, 1));
		}
		if (fixDuty.getDutyDateFrom() != null) {
			sql += " and d.dutyDate>= ? ";
			list.add(fixDuty.getDutyDateFrom() );
		}
		if (fixDuty.getDutyDateTo()!= null) {
			sql += " and d.dutyDate< ? ";
			list.add(DateUtil.addInteger(fixDuty.getDutyDateTo(), Calendar.DATE, 1));
		}
		if (fixDuty.getProvideDateFrom() != null) {
			sql += " and d.ProvideDate>= ? ";
			list.add(fixDuty.getProvideDateFrom() );
		}
		if (fixDuty.getProvideDateTo()!= null) {
			sql += " and d.ProvideDate< ? ";
			list.add(DateUtil.addInteger(fixDuty.getProvideDateTo(), Calendar.DATE, 1));
		}
		if (StringUtils.isNotBlank(fixDuty.getStatus())) {
			sql += " and d.Status in ('"+fixDuty.getStatus().replaceAll(",", "','") + "')";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.No";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> getFixDutyDetailList(
		Page<Map<String, Object>> page, FixDutyEvt evt) {
		List<Object> list = new ArrayList<Object>();
		String sql =  " select a.BaseCheckItemCode,b.Descr baseCheckItemDescr,a.Qty,a.OfferPri,a.Material,a.Remark,c.Descr uomDescr "
				+ " from tFixDutyDetail a "
				+ " left join tBaseCheckItem b on a.BaseCheckItemCode=b.Code "
				+ " left join tUOM c on b.Uom=c.Code "
				+ " where a.No=  ? ";
		list.add(evt.getNo());
		return this.findPageBySql(page, sql, list.toArray());
	}
	
public Page<Map<String, Object>> getBaseCheckItemList(
		Page<Map<String, Object>> page, FixDutyEvt evt) {
		List<Object> list = new ArrayList<Object>();
		String sql =  "select a.Code baseCheckItemCode,a.Descr baseCheckItemDescr,a.OfferPri,a.Material,b.Descr uomDescr, "
				+ " a.IsFixItem,a.DispSeq"
				+ " from tBaseCheckItem a "
				+ " left join tUOM b on a.Uom=b.Code "
				+ " where a.Expired='F' and  a.WorkType12= ?"
				+ " and IsFixItem='1' ";
		list.add(evt.getWorkType12());
		if (StringUtils.isNotBlank(evt.getBaseCheckItemDescr())) {
			sql += " and a.Descr like ? ";	
			list.add("%"+evt.getBaseCheckItemDescr()+"%");
		}
		if(StringUtils.isNotBlank(evt.getWorkerClassify())){
			sql += " and exists (select BaseCheckItemCode from tBaseCheckItemWorkerClassify c where c.BaseCheckItemCode = a.Code and c.WorkerClassify = ?) ";
			list.add(evt.getWorkerClassify());
		}
		sql += " order by a.DispSeq ";
		return this.findPageBySql(page, sql, list.toArray());
	}
	 
	@SuppressWarnings("deprecation")
	public Result saveForProc(FixDuty fixDuty, String xml) {
		Assert.notNull(fixDuty);
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pDzdgl_forXml(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, fixDuty.getM_umState());
			call.setString(2, fixDuty.getCustCode());
			call.setInt(3, fixDuty.getCustWkPk());
			call.setString(4, fixDuty.getAppManType());
			call.setString(5, fixDuty.getAppCzy());
			call.setString(6, fixDuty.getAppWorkerCode());
			call.setDouble(7, fixDuty.getOfferPrj());
			call.setDouble(8, fixDuty.getMaterial());
			call.setString(9, fixDuty.getRemarks());
			call.setString(10, fixDuty.getStatus());
			call.setString(11, xml);
			call.registerOutParameter(12, Types.INTEGER);
			call.registerOutParameter(13, Types.NVARCHAR);
			call.setString(14, fixDuty.getNo());
			call.setString(15, fixDuty.getFromWays());
			call.execute();
			result.setCode(String.valueOf(call.getInt(12)));
			result.setInfo(call.getString(13));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	/**
	 * 责任人 
	 * 
	 * @param page
	 * @param fixDuty
	 * @return
	 */
	public Page<Map<String, Object>> goManJqGrid(Page<Map<String, Object>> page, FixDuty fixDuty) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select a.PK,a.No,a.FaultType,a.EmpCode,a.WorkerCode,a.SupplCode,a.Amount,a.IsSalary, "
				+ "a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,f.NOTE isSalaryDescr, "
				+ "b.NameChi empNameChi,c.NameChi workerNameChi,d.Descr supplDescr,e.NOTE faultTypeDescr,a.Remark " +
				" ,isnull(tt.ConfirmAmount,0) ConfirmAmount,a.RiskFund, a.PaidAmount "
				+ "from tFixDutyMan a  "
				+ "left join tEmployee b on a.EmpCode=b.Number  "
				+ "left join tWorker c on a.WorkerCode=c.Code  "
				+ "left join tSupplier d on a.SupplCode=d.Code  "
				+ "left join tXTDM e on a.FaultType=e.CBM and e.ID='FAULTTYPE' "
				+ "left join tXTDM f on a.IsSalary=f.CBM and f.ID='YESNO' " +
				" left join tFixDuty g on g.no = a.no "
				+ " left join (select bb.WorkerCode,bb.CustCode,sum(isnull(bb.ConfirmAmount,0)) ConfirmAmount "
			    + " from tWorkCost aa left join tWorkCostDetail bb on aa.No=bb.No "
			    + " where aa.Status='2' and aa.PayCZY is not null "
			    + " group by bb.WorkerCode,bb.CustCode) tt on a.WorkerCode=tt.WorkerCode and g.CustCode=tt.CustCode "
				+ "where a.No=?";
		list.add(fixDuty.getNo());
		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 取消
	 * 
	 * @param page
	 * @param fixDuty
	 * @return
	 */
	public String cancel(FixDuty fixDuty) {
		List<Object> list = new ArrayList<Object>();

		String sql = "update tFixDuty set status='8',lastupdate=getdate(),lastupdatedby=?,cancelremark=?,actionlog='EDIT' where status<>'7' and no=?";
		list.add(fixDuty.getLastUpdatedBy());
		list.add(fixDuty.getCancelRemark());
		list.add(fixDuty.getNo());
		return executeUpdateBySql(sql, list.toArray()).toString();
	}
	/**
	 * 更新状态
	 * 
	 * @param page
	 * @param fixDuty
	 * @return
	 */
	public Long updateOneStatus(FixDuty fixDuty) {
		List<Object> list = new ArrayList<Object>();
		String addSql="";
		Boolean flag=false;
		if("5".equals(fixDuty.getStatus())&&"4".equals(fixDuty.getOldStatus())){
			addSql+=",dutyMan=?,dutyDate=getdate()";
			flag=true;
		}else if("6".equals(fixDuty.getStatus())){
			addSql+=",manageCfmMan=?,manageCfmDate=getdate()";
			flag=true;
		}else if("4".equals(fixDuty.getStatus())){
			addSql+=",dutyMan='',dutyDate=null";
		}else if("5".equals(fixDuty.getStatus())&&"6".equals(fixDuty.getOldStatus())){
			addSql+=",manageCfmMan='',manageCfmDate=null";
		}
		String sql = "update tFixDuty set status=?,lastupdate=getdate(),lastupdatedby=?,actionlog='EDIT'"
					+addSql+" where status=? and no=?";
		list.add(fixDuty.getStatus());
		list.add(fixDuty.getLastUpdatedBy());
		if(flag){
			list.add(fixDuty.getEmpCode());
		}
		list.add(fixDuty.getOldStatus());
		list.add(fixDuty.getNo());
		return executeUpdateBySql(sql, list.toArray());
	}
	/**
	 * 批量更新状态
	 * 
	 * @param page
	 * @param fixDuty
	 * @return
	 */
	public String updateMultyStatus(FixDuty fixDuty) {
		List<Object> list = new ArrayList<Object>();

		String sql = "update tFixDuty set status=?,lastupdate=getdate(),lastupdatedby=?,actionlog='EDIT',"
				+"provideMan=?,provideDate=getdate() " 
				+"where status=? and no in "+ "('"+fixDuty.getNos().replaceAll(",", "','")+"')";
		list.add(fixDuty.getStatus());
		list.add(fixDuty.getLastUpdatedBy());
		list.add(fixDuty.getEmpCode());
		list.add(fixDuty.getOldStatus());
		return executeUpdateBySql(sql, list.toArray()).toString();
		
	}
	/**
	 * 保存
	 * 
	 * @param fixDuty
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result doSaveProc(FixDuty fixDuty) {
		Assert.notNull(fixDuty);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pDzry_forXml(?,?,?,?,?,?)}");
			call.setString(1, fixDuty.getNo());
			call.setString(2, fixDuty.getLastUpdatedBy());	
			call.setString(3, fixDuty.getFixDutyManJson());
			call.registerOutParameter(4, Types.INTEGER);
			call.registerOutParameter(5, Types.NVARCHAR);
			call.setString(6, fixDuty.getCfmReturnRemark());
			call.execute();
			result.setCode(String.valueOf(call.getInt(4)));
			result.setInfo(call.getString(5));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
		
	}
	/**
	 * 明细查询
	 * 
	 * @param page
	 * @param fixDuty
	 * @return
	 */
	public Page<Map<String,Object>> goDetailQueryJqGrid(Page<Map<String,Object>> page, FixDuty fixDuty) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from(select a.PK,a.No,a.FaultType,a.EmpCode,a.WorkerCode,a.SupplCode," 
			+"a.Amount,a.Amount+a.RiskFund TotalAmount,a.IsSalary,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,"
			+"b.NameChi empNameChi,c.NameChi workerNameChi,d.Descr supplDescr,e.NOTE faultTypeDescr,f.Status,l.NOTE StatusDescr, " 
			+"f.CustCode,g.Address,f.DutyDate,f.DutyMan,h.NameChi dutyManNamechi,i.note isSalaryDescr,k.NOTE positionTypeDescr "
			+",wt12.descr worktype12descr,w.namechi workername,w.CardID,f.Remarks, a.RiskFund, isnull(a.PaidAmount, 0) PaidAmount "
		    +"from tFixDutyMan a "
		    +"left join tEmployee b on a.EmpCode=b.Number  "
			+"left join tWorker c on a.WorkerCode=c.Code " 
			+"left join tSupplier d on a.SupplCode=d.Code  "
			+"left join tXTDM e on a.FaultType=e.CBM and e.ID='FAULTTYPE' "
		    +"inner join tFixDuty f on a.No=f.No "
		    +"left join tCustomer g on f.CustCode=g.Code "
		    +"left join tEmployee h on f.DutyMan=h.Number "
		    +"left join tPosition j on b.Position=j.Code "
		    +"left join tXTDM i on a.isSalary=i.CBM and i.ID='YESNO' "
		    +"left join tXTDM k on j.type=k.CBM and k.ID='POSTIONTYPE' "
		    +"left join tcustworker cw on f.custwkpk = cw.pk "
		    +"left join tworker w on f.appworkercode = w.code "
		    +"left join tworktype12 wt12 on cw.worktype12 = wt12.code "
		    +"left join tXTDM l on f.Status=l.CBM and l.ID='FIXSTATUS' "
			+"where 1=1  ";

		if (StringUtils.isNotBlank(fixDuty.getStatus())) {
			sql += " and f.Status=? ";
			list.add(fixDuty.getStatus());
		}
		if (fixDuty.getDutyDateFrom() != null) {
			sql += " and f.dutydate>= ? ";
			list.add(fixDuty.getDutyDateFrom() );
		}
		if (fixDuty.getDutyDateTo()!= null) {
			sql += " and f.dutydate< ? ";
			list.add(DateUtil.addInteger(fixDuty.getDutyDateTo(),
					Calendar.DATE, 1));
		}
    	if (StringUtils.isNotBlank(fixDuty.getDutyMan())) {
			sql += " and f.dutyman=? ";
			list.add(fixDuty.getDutyMan());
		}
		if(StringUtils.isNotBlank(fixDuty.getFaultType())){
			sql += " and  a.FaultType in " + "('"+fixDuty.getFaultType().replace(",", "','" )+ "')";
		}
    	if (StringUtils.isNotBlank(fixDuty.getWorkerCode())) {
			sql += " and a.workercode=? ";
			list.add(fixDuty.getWorkerCode());
		}
    	if (StringUtils.isNotBlank(fixDuty.getEmpCode())) {
			sql += " and a.empcode=? ";
			list.add(fixDuty.getEmpCode());
		}
    	if (StringUtils.isNotBlank(fixDuty.getSupplCode())) {
			sql += " and a.supplcode=? ";
			list.add(fixDuty.getSupplCode());
		}
    	if (StringUtils.isNotBlank(fixDuty.getType())) {
			sql += " and f.Type=? ";
			list.add(fixDuty.getType());
		}
    	if(StringUtils.isNotBlank(fixDuty.getAddress())){
			sql+=" and g.address like ? ";
			list.add("%"+fixDuty.getAddress()+"%");
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.No";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> getPrjItemDescr(
			Page<Map<String, Object>> page, String czybh) {
		// TODO Auto-generated method stub
		List<Object> params = new ArrayList<Object>();
		String sql= "select * from (select a.code no,descr workType12Descr from tWorkType12 a where  (" +
				" (select PrjRole from tCZYBM where CZYBH=? ) is null " +
				"			or( select PrjRole from tCZYBM where CZYBH=? ) ='' ) or  Code in(" +
				"				select WorkType12 From tprjroleworktype12 pr where pr.prjrole = " +
				"				(select PrjRole from tCZYBM where CZYBH=? ) or pr.prjrole = ''" +
				"				 )" +
				"  ) a   ";
		params.add(czybh);
		params.add(czybh);
		params.add(czybh);
		return this.findPageBySql(page, sql,params.toArray());
	}
	
	public Page<Map<String, Object>> getBaseCheckItemDetailList(Page<Map<String, Object>> page,String custCode,String workType12){
		List<Object> list = new ArrayList<Object>();
		String sql = " select b.Descr BaseCheckItemDescr,c.Descr FixAreaDescr,a.Qty,b.WorkType12 from tBaseCheckItemPlan a "
				+" left join tBaseCheckItem b on a.BaseCheckItemCode=b.Code "
				+" left join tFixArea c  on a.FixAreaPK=c.PK "
				+" where a.CustCode = ? and b.WorkType12= ? ";
		list.add(custCode);
		list.add(workType12);
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public List<Map<String, Object>> checkHasFixDuty(FixDuty fixDuty) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from tFixDuty where Status NOT IN ('8','1') and CustWkPk=? ";
		list.add(fixDuty.getCustWkPk());
		return this.findBySql(sql, list.toArray());
	}
	
	public String updateFixDutyDetail(FixDuty fixDuty) {
		List<Object> list = new ArrayList<Object>();
		String sql = "update tFixDutyDetail set CfmQty=Qty,LastUpdate=getdate(),LastUpdatedBy=?  "
				+" where No=? ";
		list.add(fixDuty.getLastUpdatedBy());
		list.add(fixDuty.getNo());
		return executeUpdateBySql(sql, list.toArray()).toString();
	}
	
	
	public Map<String, Object> getFixDutyByCustCode(String custCode) {
		String sql = "select * from tFixDuty where CustCode=? and Status='2' ";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[] { custCode });
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	public double getConfirmAmount(String custCode,String workerCode){
		String sql = "select bb.WorkerCode,bb.CustCode,sum(isnull(bb.ConfirmAmount,0)) ConfirmAmount "
				    + " from tWorkCost aa left join tWorkCostDetail bb on aa.No=bb.No "
				    + " where aa.Status='2' and aa.PayCZY is not null " +
				    " and bb.custCode = ? and bb.workerCode = ? "
				    + " group by bb.WorkerCode,bb.CustCode ";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[] { custCode,workerCode });
		if (list != null && list.size() > 0) {
			return Double.parseDouble(list.get(0).get("ConfirmAmount").toString());
		}
		
		return 0.0;
	}
	/**
	 * 已存在工资单的定责单不能反审批。
	 * @author	created by zb
	 * @date	2019-7-25--下午5:39:55
	 * @param no
	 * @return
	 */
	public Boolean isWorkCostDetail(String no) {
		String sql = "select 1 "
					+"from tWorkCostDetail a "
					+"where a.Expired='F' and a.RefFixDutyManPk<>0 "
					+"and exists ( "
					+"	select 1 "
					+"	from tFixDutyMan in_a "
					+"	where in_a.Expired='F' and in_a.PK=a.RefFixDutyManPk "
					+"	and in_a.No=? "
					+") ";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[] { no });
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;
	}
	/**
	 * 新增设计定责保存
	 * 
	 * @param fixDuty
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result doSaveDesignDuty(FixDuty fixDuty) {
		Assert.notNull(fixDuty);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pSjdz_forXml(?,?,?,?,?)}");
			call.setString(1, fixDuty.getNo());
			call.setString(2, fixDuty.getLastUpdatedBy());	
			call.setString(3, fixDuty.getFixDutyManJson());
			call.registerOutParameter(4, Types.INTEGER);
			call.registerOutParameter(5, Types.NVARCHAR);
			call.execute();
			result.setCode(String.valueOf(call.getInt(4)));
			result.setInfo(call.getString(5));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
		
	}
	/**
	 * 获取其他单累计风控金额
	 * @author cjg
	 * @date 2019-10-24
	 * @param custCode
	 * @return
	 */
	public Map<String, Object> getOtherRiskFund(String custCode,String no) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select  isnull(sum(case when a.FaultType='1' and a.EmpCode =c.DesignMan then a.RiskFund else 0 end),0) DesignRiskFund, "
				+"isnull(sum(case when a.FaultType='1' and a.EmpCode =c.DesignMan then 0 else a.RiskFund end),0) PrjManRiskFund "
				+"from    tFixDutyMan a "
				+"inner join tFixDuty b on a.No = b.No "
				+"inner join tCustomer c on b.CustCode=c.Code "
				+"where   CustCode = ? and b.status in ('4','5','6','7') ";
		list.add(custCode);
		if(StringUtils.isNotBlank(no)){
			sql+=" and a.No<>?";
			list.add(no);
		}
		return this.findBySql(sql, list.toArray()).get(0);
	}
	/**
	 * 新增设计定责保存
	 * @author cjg
	 * @date 2019-10-25
	 * @param fixDuty
	 * @param xml
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result doSaveDeignDuty(FixDuty fixDuty) {
		Assert.notNull(fixDuty);
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pAddDesignDuty(?,?,?,?,?,?,?,?,?)}");
			call.setString(1, fixDuty.getCustCode());
			call.setString(2, fixDuty.getAppCzy());
			call.setString(3, fixDuty.getRemarks());
			call.setString(4, fixDuty.getFixDutyManJson());
			call.registerOutParameter(5, Types.INTEGER);
			call.registerOutParameter(6, Types.NVARCHAR);
			call.setString(7, fixDuty.getDetailJson());
	        call.setDouble(8, fixDuty.getOfferPrj() == null ? 0 : fixDuty.getOfferPrj());
            call.setDouble(9, fixDuty.getMaterial() == null ? 0 : fixDuty.getMaterial());
			call.execute();
			result.setCode(String.valueOf(call.getInt(5)));
			result.setInfo(call.getString(6));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	/**
	 * 发放的同时在项目经理app上提醒项目经理
	 * @author	created by zb
	 * @date	2020-3-28--上午11:53:37
	 * @param fixDuty
	 * @return
	 */
	public void doRemindProMan(FixDuty fixDuty) {
		String sql = "insert into tPersonMessage (MsgType, MsgText, MsgRelNo, MsgRelCustCode, CrtDate,  "
					+"	SendDate, RcvType, RcvCZY, RcvStatus, IsPush, PushStatus) "
					+"select   N'19', c.Address+',定责单处理完成,您承担'+cast(b.Amount as nvarchar(100)), b.PK, a.CustCode, getdate(), "
					+"  getdate(), N'1', b.EmpCode, N'0', N'1', N'0' "
					+"from tFixDuty a "
					+"left join tFixDutyMan b on b.No=a.No "
					+"left join tCustomer c on c.Code=a.CustCode "
					+"where b.FaultType='1' and a.status=? and a.No in ('"+fixDuty.getNos().replaceAll(",", "','")+"') ";
		this.executeUpdateBySql(sql, new Object[]{fixDuty.getOldStatus()});
	}
	
	/**
	 * 发放   新方法
	 * @param fixDuty
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result doUpdateMultyStatus(FixDuty fixDuty) {
		Assert.notNull(fixDuty);
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null; 
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pFixDuty_updateStatus(?,?,?,?,?)}");
			call.setString(1, fixDuty.getEmpCode());
			call.setString(2, fixDuty.getLastUpdatedBy());
			call.setString(3, fixDuty.getDetailJson());
			call.registerOutParameter(4, Types.INTEGER);
			call.registerOutParameter(5, Types.NVARCHAR);
			call.execute();
			result.setCode(String.valueOf(call.getInt(4)));
			result.setInfo(call.getString(5));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	
	public Map<String, Object> getFixDutyDetailInfo(String no){
		String sql = "select  b.Address, a.Remarks, a.PrjRemark ,a.IsPrjAllDuty, a.OfferPrj, a.Material, a.Status, d.NOTE StatusDescr, "
				+" case when a.AppManType='1' then c.NameChi else f.NameChi end NameChi, e.Note AppManTypeDescr, "
				+" (select isnull(sum(Amount),0) from tFixDutyMan fm left join tFixDuty fd on fd.No=fm.No where fm.No=a.no and EmpCode= b.ProjectMan and fd.Status in('5','6','7') )CfmPrice "
				+" from tFixDuty a "
				+" left join tCustomer b on a.CustCode = b.Code "
				+" left join tWorker c on a.AppWorkerCode = c.Code "
				+" left join tXTDM d on a.Status = d.CBM and d.ID='FIXSTATUS' "
				+" left join tXTDM e on a.AppManType = e.CBM and e.ID='APPMANTYPE' "
				+" left join tEmployee f on a.AppCZY = f.Number "
				+" where a.No = ?";
		return this.findBySql(sql, new Object[] { no }).get(0);
	}
	
	public Page<Map<String, Object>> getDepartment2List(Page<Map<String, Object>> page) {
		List<Object> params = new ArrayList<Object>();
		String sql= " select code,Desc1 Descr  from tDepartment2 where Department1='03' and Expired = 'F' ";
		return this.findPageBySql(page, sql,params.toArray());
	}
	
	
	public long countDesignDuties(String custCode) {
	    String sql = "select * from tFixDuty where Type = '2' and Status <> '8' and CustCode = ? ";
	    
	    return countSqlResult(sql, null, custCode);
	}
	
    public Map<String, Object> summarizeDiscounts(String custCode, String fixDutyNo) {

        String sql = "select a.Code, a.Address, "
                + "a.DesignerMaxDiscAmount + a.DirectorMaxDiscAmount + isnull(b.ExtraGiftAmount, 0) + "
                + "    isnull(c.DiscAmountExpense, 0) DiscBalance, "
                + "a.DesignerMaxDiscAmount + a.DirectorMaxDiscAmount + isnull(b.ExtraGiftAmount, 0) + "
                + "    isnull(c.DiscAmountExpense, 0) + "
                + "    (isnull(a.DesignRiskFund, 0) + isnull(d.FrontendRiskFundExpense, 0)) TotalDiscBalance, "
                + "isnull(a.DesignRiskFund, 0) + isnull(d.FrontendRiskFundExpense, 0) FrontendRiskBalance, "
                + "isnull(a.PrjManRiskFund, 0) - isnull(e.PrjRiskFundExpense, 0) PrjRiskBalance, "
                + "a.DesignerMaxDiscAmount + a.DirectorMaxDiscAmount MaxDiscAmount, "
                + "isnull(b.ExtraGiftAmount, 0) ExtraGiftAmount, "
                + "isnull(a.DesignRiskFund, 0) DesignRiskFund, "
                + "isnull(a.PrjManRiskFund, 0) PrjManRiskFund, "
                + "isnull(c.DiscAmountExpense, 0) DiscAmountExpense, "
                + "isnull(d.FrontendRiskFundExpense, 0) FrontendRiskFundExpense, "
                + "isnull(e.PrjRiskFundExpense, 0) PrjRiskFundExpense "
                + "from tCustomer a "
                + "left join ( "
                + "    select CustCode, sum(Amount) ExtraGiftAmount "
                + "    from tDiscAmtTran "
                + "    where Type = '1' and IsExtra = '1' and IsRiskFund = '0' "
                + "    group by CustCode "
                + ") b on a.Code = b.CustCode "
                + "left join ( "
                + "    select CustCode, sum(Amount) DiscAmountExpense "
                + "    from tDiscAmtTran "
                + "    where Type = '2' and IsRiskFund = '0' "
                + "    group by CustCode "
                + ") c on a.Code = c.CustCode "
                + "left join ( "
                + "    select a.CustCode, sum(a.Amount) FrontendRiskFundExpense "
                + "    from tDiscAmtTran a "
                + "    left join tFixDutyMan b on a.FixDutyManPK = b.PK "
                + "    left join tFixDuty c on b.No = c.No "
                + "    where a.Type = '2' and a.IsRiskFund = '1' and isnull(c.No, '') <> ? "
                + "    group by a.CustCode "
                + ") d on a.Code = d.CustCode "
                + "left join ( "
                + "    select in_a.Code, sum(in_c.RiskFund) PrjRiskFundExpense "
                + "    from tCustomer in_a "
                + "    left join tFixDuty in_b on in_a.Code = in_b.CustCode "
                + "    left join tFixDutyMan in_c on in_b.No = in_c.No "
                + "    where in_b.Status in ('5', '6', '7') "
                + "        and in_a.DesignMan <> in_c.EmpCode "
                + "    group by in_a.Code "
                + ") e on a.Code = e.Code "
                + "where a.Code = ? ";
        
        List<Map<String, Object>> results = findBySql(sql, fixDutyNo, custCode);
        
        return results.size() > 0 ? results.get(0) : new HashMap<String, Object>();
    }
	
}
