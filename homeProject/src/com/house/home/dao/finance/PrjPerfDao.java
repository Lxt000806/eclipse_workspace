package com.house.home.dao.finance;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.entity.design.ItemPlanTemp;
import com.house.home.entity.finance.PrjPerf;
import com.house.home.entity.finance.SoftPerf;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
@SuppressWarnings("serial")
@Repository
public class PrjPerfDao extends BaseDao{

	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, PrjPerf prjPerf) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from ( select  No, a.Y, a.M, a.Season, a.BeginDate, a.EndDate, a.Status, b.NOTE StatusDescr," +
				"        a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,a.Remarks " +
				"        from tPrjPerf a" +
				"        left join tXTDM b on b.ID='PRJPERFSTATUS' and b.CBM=a.Status" +
				"        where 1=1 ";
		if(StringUtils.isNotBlank(prjPerf.getNo())){
			sql+=" and  a.no  like ? ";
			list.add("%"+prjPerf.getNo()+"%");
		}
		if(prjPerf.getY() != null){
			sql+=" and a.y = ? ";
			list.add(prjPerf.getY());
		}
		if(prjPerf.getM() != null){
			sql+=" and a.m = ? ";
			list.add(prjPerf.getM());
		}
		if(prjPerf.getSeason() != null){
			sql+=" and a.Season = ? ";
			list.add(prjPerf.getSeason());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.beginDate desc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> getCountPrjPerJqGrid(Page<Map<String,Object>> page, PrjPerf prjPerf) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select * from ( select  g.descr PayeeDescr,a.PerfMarkup,a.PK, a.No, a.CustCode, a.BaseCtrlAmount, a.BasePlan, a.LongFee, a.ManageFee, a.MainSetFee, a.SetAdd, a.SetMinus, a.MainPlan," +
				"           a.ServPlan, a.SoftPlan, a.FurnPlan, a.IntPlan, a.CupPlan, a.BaseDisc, a.ContractFee, a.BaseChg, a.ManageChg, a.DesignChg, a.ChgDisc," +
				"           a.MainChg, a.ServChg, a.SoftChg,a.FurnChg, a.FurnChg+a.FurnPlan FurnCheck, a.IntChg, a.CheckAmount, a.CheckPerf, a.PrjDeptLeader, a.ProjectMan, a.ProvideCard," +
				"           a.ProvideAmount, a.CheckMan, a.DelayDay, a.PerfPerc, a.PerfDisc, a.LastUpdate, a.LastUpdatedBy, a.Expired, a.ActionLog, a.DesignFee," +
				"           c.DocumentNo, c.Descr CustDescr, a.CustCheckDate, c.CustType, ct.Desc1 CustTypeDescr, c.Address, c.Area, " +
				"           a.BasePlan+a.ManageFee+a.MainPlan+a.IntPlan+a.CupPlan+a.SoftPlan+a.ServPlan AllPlan," +
				"           a.BaseChgCnt, a.MainChgCnt, a.IntChgCnt, a.SoftChgCnt, a.ServChgCnt, a.BaseChgCnt+a.MainChgCnt+a.IntChgCnt+a.SoftChgCnt+a.ServChgCnt AllChgCnt," +
				"           e1.NameChi PrjDeptLeaderDescr, e2.NameChi ProjectManDescr, e3.NameChi CheckManDescr, d2.Desc2 Dept2Descr, ct.Type TypeOfCusttype, a.IsModified, x1.NOTE IsModifiedDescr, " +
				"           a.BaseDeduction, a.ItemDeduction, a.PerfExpr, a.PerfExprRemarks, a.SoftTokenAmount, a.Gift, a.Tax, f.Desc2 CmpName, " + // 20200428 c.Tax改为a.Tax
				"           case when a.PerfExpr like '%@PerfMarkup@%' then a.CheckPerf/a.PerfMarkup else a.checkPerf end prePerf, a.TaxChg, " +
				"			c.ManageFee_Base*c.ContainBase ManageFee_Base, c.ManageFee_Main*c.ContainMain ManageFee_Main, c.ManageFee_Int*c.ContainInt ManageFee_Int, " +
				"			c.ManageFee_Cup*c.ContainCup ManageFee_Cup, c.ManageFee_Soft*c.ContainSoft ManageFee_Soft, c.ManageFee_Serv*c.ContainMainServ ManageFee_Serv, " +
				"			isnull(h.ManageChg_Base,0) ManageChg_Base, isnull(h.ManageChg_Main,0) ManageChg_Main, isnull(h.ManageChg_Serv,0) ManageChg_Serv, " +	
				"			isnull(h.ManageChg_Int,0) ManageChg_Int, isnull(h.ManageChg_Cup,0) ManageChg_Cup, isnull(h.ManageChg_Soft,0) ManageChg_Soft, " +
				"			isnull(a.NoSceneDesignerCheck,0) NoSceneDesignerCheck, isnull(a.SceneDesignerCheck,0) SceneDesignerCheck," +
				"           i.NOTE ConstructStatusDescr,a.BasePersonalPlan,a.BasePersonalChg " +	
				"			from tPrjPerfDetail a" +
				"           left join tCustomer c on a.CustCode=c.Code" +
				"           left join tCusttype ct on c.CustType=ct.Code" +
				"           left join tEmployee e1 on e1.Number=a.PrjDeptLeader" +
				"           left join tEmployee e2 on e2.Number=a.ProjectMan" +
				"           left join tEmployee e3 on e3.Number=a.CheckMan" +
				"           left join tDepartment2 d2 on a.PrjDepartment2=d2.Code" +
				"           left join tXTDM x1 on x1.CBM=a.IsModified and x1.ID='YESNO'" +
				"			left join tBuilder d on c.BuilderCode=d.Code " +
				"			left join tRegion e on d.RegionCode=e.Code"	+
				"			left join tCompany f on e.CmpCode=f.Code " +
				"			left join tTaxPayee g on g.Code = a.PayeeCode " +
				"			left join (" +
				"				select cc.CustCode," +
				"				sum(case when cc.ChgType='2' and cc.ItemType1='JZ' then cc.ChgAmount else 0 end) ManageChg_Base, " +
				"				sum(case when cc.ChgType='2' and cc.ItemType1='ZC' and cc.IsService='0' then cc.ChgAmount else 0 end) ManageChg_Main, " +
				"				sum(case when cc.ChgType='2' and cc.ItemType1='ZC' and cc.IsService='1' then cc.ChgAmount else 0 end) ManageChg_Serv, " +
				"				sum(case when cc.ChgType='2' and cc.ItemType1='JC' and cc.IsCupboard='0' then cc.ChgAmount else 0 end) ManageChg_Int, " +
				"				sum(case when cc.ChgType='2' and cc.ItemType1='JC' and cc.IsCupboard='1' then cc.ChgAmount else 0 end) ManageChg_Cup, " +
				"				sum(case when cc.ChgType='2' and cc.ItemType1='RZ' then cc.ChgAmount else 0 end) ManageChg_Soft " +
				"				from tConFeeChg cc where cc.Status='CONFIRMED' group by cc.CustCode " +
				"			) h on c.Code=h.CustCode" +
				"           left join tXTDM i on c.ConstructStatus = i.CBM and i.ID = 'CONSTRUCTSTATUS' " +
				"           where 1=1 ";
		if(StringUtils.isNotBlank(prjPerf.getNo())){
			sql+=" and a.no = ? ";
			list.add(prjPerf.getNo());
		}
		if(StringUtils.isNotBlank(prjPerf.getAddress())){
			sql+=" and c.address like ? ";
			list.add("%"+prjPerf.getAddress()+"%");
		}
		if(StringUtils.isNotBlank(prjPerf.getProjectMan())){
			sql+=" and a.PrjDeptLeader  = ? ";
			list.add(prjPerf.getProjectMan());
		}
		if(StringUtils.isNotBlank(prjPerf.getProjectManDept2())){
			sql+=" and e1.department2 = ? ";
			list.add(prjPerf.getProjectManDept2());
		}
		if(prjPerf.getDateFrom()!=null){
			sql+=" and a.CustCheckDate >= ? ";
			list.add(prjPerf.getDateFrom());
		}
		if(prjPerf.getDateTo() != null){
			sql+=" and a.CustCheckDate <= ?";
			list.add(new Timestamp(
					DateUtil.endOfTheDay( prjPerf.getDateTo()).getTime()));
		}
		if(StringUtils.isNotBlank(prjPerf.getCustType())){
			sql += " and c.custType in " + "('"+prjPerf.getCustType().replace(",", "','" )+ "')";
		}
		if(prjPerf.getPk()!=null){
			sql+=" and a.pk = ? ";
			list.add(prjPerf.getPk());
		}
		if(StringUtils.isNotBlank(prjPerf.getCompany())){
			sql+=" and f.Code = ? ";
			list.add(prjPerf.getCompany());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.lastUpdate desc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> getReportJqGrid(Page<Map<String,Object>> page, PrjPerf prjPerf) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from ( select  a.PerfMarkup,a.PK, a.No, a.CustCode, a.BaseCtrlAmount, a.BasePlan, a.LongFee, a.ManageFee, a.MainSetFee, a.SetAdd, a.SetMinus, a.MainPlan," +
				"           a.ServPlan, a.SoftPlan, a.FurnPlan, a.IntPlan, a.CupPlan, a.BaseDisc, a.ContractFee, a.BaseChg, a.ManageChg, a.DesignChg, a.ChgDisc," +
				"           a.MainChg, a.ServChg, a.SoftChg, a.FurnChg, a.IntChg, a.CheckAmount, a.CheckPerf, a.PrjDeptLeader, a.ProjectMan, a.ProvideCard," +
				"           a.ProvideAmount, a.CheckMan, a.DelayDay, a.PerfPerc, a.PerfDisc, a.LastUpdate, a.LastUpdatedBy, a.Expired, a.ActionLog, a.DesignFee," +
				"           c.DocumentNo, c.Descr CustDescr, a.CustCheckDate, ct.Desc1 CustTypeDescr, c.Address, c.Area, " +
				"           a.BasePlan+a.ManageFee+a.MainPlan+a.IntPlan+a.CupPlan+a.SoftPlan+a.ServPlan AllPlan," +
				"           a.BaseChgCnt, a.MainChgCnt, a.IntChgCnt, a.SoftChgCnt, a.ServChgCnt, a.BaseChgCnt+a.MainChgCnt+a.IntChgCnt+a.SoftChgCnt+a.ServChgCnt AllChgCnt," +
				"           e1.NameChi PrjDeptLeaderDescr, e2.NameChi ProjectManDescr, e3.NameChi CheckManDescr, d2.Desc2 Dept2Descr, " +
				"           a.BaseDeduction, a.ItemDeduction, e4.NameChi DesignManDescr, f.Desc2 DesignManDept2Descr, g.Desc2 DesignManDept1Descr, a.SoftTokenAmount, a.Gift" +
				"           ,a.CheckPerf/a.PerfMarkup prePerf, a.Tax, a.TaxChg,isnull(fr.Amount,0) FreeBaseAmount,i.NOTE ConstructStatusDescr,a.BasePersonalPlan,a.BasePersonalChg  " +
				"           from tPrjPerfDetail a" +
				"			left join tCustomer c on a.CustCode=c.Code" +
				"           left join tCusttype ct on c.CustType=ct.Code" +
				"           left join tEmployee e1 on e1.Number=a.PrjDeptLeader" +
				"           left join tEmployee e2 on e2.Number=a.ProjectMan" +
				"           left join tEmployee e3 on e3.Number=a.CheckMan" +
				"           left join tDepartment2 d2 on e1.Department2=d2.Code" +
				"           left join tEmployee e4 on e4.Number=c.DesignMan" +
				"           left join tDepartment2 f on e4.Department2=f.Code" +
				"           left join tDepartment1 g on e4.Department1=g.Code" +
				"           left join (select sum(LineAmount) Amount,custCode from tBaseItemReq " +
				"           where BaseItemCode in (select * from fStrToTable(?,',')) " +
				"           group by custCode )fr on fr.custCode=a.CustCode" +
				"           left join tXTDM i on c.ConstructStatus = i.CBM and i.ID = 'CONSTRUCTSTATUS' " +
				"           where 1=1";
		list.add(prjPerf.getFreeBaseItem());
		if(StringUtils.isNotBlank(prjPerf.getNo())){
			sql+=" and a.no like ? ";
			list.add("%"+prjPerf.getNo()+"%");
		}
		if(StringUtils.isNotBlank(prjPerf.getAddress())){
			sql+=" and c.address like ? ";
			list.add("%"+prjPerf.getAddress()+"%");
		}
		if(StringUtils.isNotBlank(prjPerf.getProjectMan())){
			sql+=" and a.PrjDeptLeader  = ? ";
			list.add(prjPerf.getProjectMan());
		}
		if(StringUtils.isNotBlank(prjPerf.getProjectManDept2())){
			sql+=" and e1.Department2 = ? ";
			list.add(prjPerf.getProjectManDept2());
		}
		if(prjPerf.getDateFrom()!=null){
			sql+=" and a.CustCheckDate >= ? ";
			list.add(prjPerf.getDateFrom());
		}
		if(prjPerf.getDateTo() != null){
			sql+=" and a.CustCheckDate <= ?";
			list.add(new Timestamp(
					DateUtil.endOfTheDay( prjPerf.getDateTo()).getTime()));
		}
		if(StringUtils.isNotBlank(prjPerf.getCustType())){
			sql += " and c.custType in " + "('"+prjPerf.getCustType().replace(",", "','" )+ "')";
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a  ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public String  getNotify(String beginDate) {
		String sql = " select 1 from tPrjPerf where BeginDate< ? and status = '1' ";
		
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{beginDate});
		if(list != null && list.size() > 0){
			return "之前的统计周期未计算完成，不允许计算本周期业绩!";
		}
		return null;
	}
	
	public void doCalcPerf(String no,String lastUpdatedBy, String calcType) {
		
		String sql = " exec pCalcPrjPerf ?,?,? ";
	
		this.executeUpdateBySql(sql, new Object[]{no, lastUpdatedBy, calcType});
	}
	
	@SuppressWarnings("deprecation")
	public Result savePrjPerf(PrjPerf prjPerf) {
		Assert.notNull(prjPerf);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pPrjPerf(?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, prjPerf.getM_umState());
			call.setString(2, prjPerf.getNo());
			call.setInt(3, prjPerf.getY());
			call.setInt(4, prjPerf.getM());
			call.setInt(5, prjPerf.getSeason());
			call.setTimestamp(6, prjPerf.getBeginDate() == null ? null
					: new Timestamp(prjPerf.getBeginDate().getTime()));
			call.setTimestamp(7, prjPerf.getEndDate() == null ? null
					: new Timestamp(prjPerf.getEndDate().getTime()));
			call.setString(8, prjPerf.getLastUpdatedBy());
			call.registerOutParameter(9, Types.INTEGER);
			call.registerOutParameter(10, Types.NVARCHAR);
			call.setString(11, prjPerf.getRemarks());
			call.execute();
			result.setCode(String.valueOf(call.getInt(9)));
			result.setInfo(call.getString(10));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	
	public void doSaveCount(String no) {
		String sql = " update tprjPerf set Status='2' where No= ?  ";
		this.executeUpdateBySql(sql, new Object[]{no});
	}
	
	public void doSaveCountBack(String no) {
		String sql = " update tprjPerf set Status='1' where No= ?  ";
		this.executeUpdateBySql(sql, new Object[]{no});
	}
	
	public void doPerfChg(String no,String lastUpdatedBy) {
		String sqlItem = "update tItemChg set IscalPerf='1', LastUpdate=getdate(), LastUpdatedBy=  ? , ActionLog='EDIT'" +
				" from tItemChg a " +
				" inner join tCustomer c on a.CustCode=c.Code" +
				" inner join tCusttype ct on c.CustType=ct.Code" +
				" inner join tPrjPerf b on c.CustCheckDate>=b.BeginDate and c.CustCheckDate<dateadd(day, 1, b.EndDate)" +
				" where b.No= ?  and a.IscalPerf='0' and a.Status='2' and ct.IsCalcPerf='1'";
		this.executeUpdateBySql(sqlItem, new Object[]{lastUpdatedBy,no});
		
		String sqlBaseItem = "update tBaseItemChg set IscalPerf='1', LastUpdate=getdate(), LastUpdatedBy=  ? , ActionLog='EDIT'" +
				" from tBaseItemChg a " +
				" inner join tCustomer c on a.CustCode=c.Code" +
				" inner join tCusttype ct on c.CustType=ct.Code" +
				" inner join tPrjPerf b on c.CustCheckDate>=b.BeginDate and c.CustCheckDate<dateadd(day, 1, b.EndDate)" +
				" where b.No= ?  and a.IscalPerf='0' and a.Status='2' and ct.IsCalcPerf='1'";
		this.executeUpdateBySql(sqlBaseItem, new Object[]{lastUpdatedBy,no});
		
		String sqlConFee = "update tConFeeChg set IscalPerf='1', LastUpdate=getdate(), LastUpdatedBy=  ? , ActionLog='EDIT'" +
				" from tConFeeChg a " +
				" inner join tCustomer c on a.CustCode=c.Code" +
				" inner join tCusttype ct on c.CustType=ct.Code" +
				" inner join tPrjPerf b on c.CustCheckDate>=b.BeginDate and c.CustCheckDate<dateadd(day, 1, b.EndDate)" +
				" where b.No= ?  and a.IscalPerf='0' and a.Status='CONFIRMED' and ct.IsCalcPerf='1' and a.ChgType in ('1','2','4') ";
		this.executeUpdateBySql(sqlConFee, new Object[]{lastUpdatedBy,no});
	}

	
	
}
