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
import com.house.home.entity.basic.Department2;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.finance.PerfCycle;
import com.house.home.entity.finance.PrjPerf;
import com.house.home.entity.project.WorkCost;

@SuppressWarnings("serial")
@Repository
public class PerfCycleDao extends BaseDao {
	/** 公用的sql,相当于cs里面的公用临时表 **/
	String basicSql = "";

	/**
	 * PerfCycle分页信息
	 * 
	 * @param page
	 * @param perfCycle
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, PerfCycle perfCycle) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from(select No, a.Y , a.M , a.Season, a.BeginDate, a.EndDate, a.Status, b.NOTE StatusDescr, "
				+ " a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,isnull(c.ContractFee,0)ContractFee,"
				+ " isnull(c.ContractAndDesignFee,0)ContractAndDesignFee,isnull(c.RecalPerf,0)RecalPerf,a.Remarks "
				+ " from tPerfCycle a "
				+ " left join tXTDM b on b.ID='CYCLESTATUS' and b.CBM=a.Status "
				+ " left join (	select d.PerfCycleNo,sum(d.ContractFee+d.Tax)ContractFee,sum(d.ContractFee+d.DesignFee+d.Tax) "
				+ " ContractAndDesignFee,sum(d.RecalPerf)RecalPerf from tPerformance d group by d.PerfCycleNo)c "
				+ " on a.No=c.PerfCycleNo"
				+ " where 1=1";

		if (StringUtils.isNotBlank(perfCycle.getNo())) {
			sql += " and a.No=? ";
			list.add(perfCycle.getNo());
		}
		if (perfCycle.getY() != null) {
			sql += " and a.Y=? ";
			list.add(perfCycle.getY());
		}
		if (perfCycle.getM() != null) {
			sql += " and a.M=? ";
			list.add(perfCycle.getM());
		}
		if (perfCycle.getSeason() != null) {
			sql += " and a.Season=? ";
			list.add(perfCycle.getSeason());
		}
		if (perfCycle.getBeginDate() != null) {
			sql += " and a.BeginDate=? ";
			list.add(perfCycle.getBeginDate());
		}
		if (perfCycle.getEndDate() != null) {
			sql += " and a.EndDate=? ";
			list.add(perfCycle.getEndDate());
		}
		if (StringUtils.isNotBlank(perfCycle.getStatus())) {
			sql += " and a.Status=? ";
			list.add(perfCycle.getStatus());
		}
		if (perfCycle.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(perfCycle.getLastUpdate());
		}
		if (StringUtils.isNotBlank(perfCycle.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(perfCycle.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(perfCycle.getExpired())
				|| "F".equals(perfCycle.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(perfCycle.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(perfCycle.getActionLog());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ")a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ")a order by a.BeginDate desc";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * 查询计算状态
	 * 
	 * @param no
	 * @return
	 */
	public String checkStatus(String no) {
		String sql = "select status from tPerfCycle where no = ? ";
		return this.findBySql(sql, new Object[] { no }).get(0).get("status")
				.toString();
	}

	/**
	 * 检查周期
	 * 
	 * @param no
	 * @param beginDate
	 * @return
	 */
	public String isExistsPeriod(String no, String beginDate) {
		String sql = " select BeginDate from tPerfCycle where No= ?  and Status='1'";
		List<Map<String, Object>> list = this.findBySql(sql,
				new Object[] { no });
		if (list != null && list.size() > 0) {
			String sqlDate = " select 1 from tPerfCycle where BeginDate< ? and Status='1' ";
			List<Map<String, Object>> listDate = this.findBySql(sqlDate,
					new Object[] { list.get(0).get("BeginDate") });
			if (listDate != null && listDate.size() > 0) {
				return "2";// 之前的统计周期未计算完成，不允许计算本周期业绩!
			} else {
				return null;
			}
		} else {
			return "1";// 没有找到相应的业绩统计周期或该统计周期已计算完成!
		}
	}

	/**
	 * 检查业绩计算员工信息是否与当前员工信息一致
	 * 
	 * @return
	 */
	public List<Map<String, Object>> checkEmployeeInfo() {
		String sql = "select 1 from tEmployee a left join tEmpForPerf b on a.Number=b.Number "
				+ "where (a.Department1<>b.Department1 or a.LeadLevel<>b.LeadLevel or a.IsLead<>b.IsLead "
				+ "or isnull(a.Department2,'')<>isnull(b.Department2,'')  "
				+ "or isnull(a.ManagerRegularDate,'')<>isnull(b.ManagerRegularDate,'') "
				+ "or isnull(a.PosiChgDate,'')<>isnull(b.PosiChgDate,'') "
				+ "or isnull(a.OldDept,'')<>isnull(b.OldDept,'') "
				+ "or isnull(a.OldDeptDate,'')<>isnull(b.OldDeptDate,'') "
				+ "or isnull(a.PerfBelongMode,'')<>isnull(b.PerfBelongMode,'')) and a.Expired='F' ";
		return this.findBySql(sql, new Object[] {});
	}

	/**
	 * 计算完成
	 * 
	 * @param no
	 */
	public void doComplete(String no) {
		String sql = " update tPerfCycle set Status='2' where No= ?  ";
		this.executeUpdateBySql(sql, new Object[] { no });
	}

	/**
	 * 退回
	 * 
	 * @param no
	 */
	public void doReturn(String no) {
		String sql = " update tPerfCycle set Status='1' where No= ?  ";
		this.executeUpdateBySql(sql, new Object[] { no });
	}

	/**
	 * 员工信息同步列表
	 * 
	 * @param page
	 * @param perfCycle
	 * @return
	 */
	public Page<Map<String, Object>> findEmployeePageBySql(
			Page<Map<String, Object>> page, PerfCycle perfCycle) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from ( select 0 as IsCheck,a.Number,a.NameChi,a.Department1,d1.Desc1 Department1Descr,a.Department2,d2.desc1 Department2Descr, "
				+ "a.LeadLevel,x1.NOTE LeadLevelDescr,a.IsLead,x2.NOTE IsLeadDescr,a.ManagerRegularDate,a.PosiChgDate, "
				+ "a.OldDept,d3.Desc1 OldDeptDescr,a.OldDeptDate,a.PerfBelongMode,d4.Desc1 OldDepartment1Descr, "
				+ "p1.Desc2 PosiDescr,p2.Desc2 OldPosiDescr,d5.Desc2 Dept3Descr,d6.Desc2 OldDept3Descr,a.Expired EmpExpired,b.Expired EmpForPerfExpired, "
				+ "a.PersonChgDate,d7.Desc2 OldDepartment2Descr  "
				+ "from tEmployee a left join tEmpForPerf b on a.Number=b.Number "
				+ "left join tDepartment1 d1 on a.Department1=d1.Code "
				+ "left join tDepartment2 d2 on a.Department2=d2.Code  "
				+ "left join tDepartment2 d3 on a.oldDept=d3.Code  "
				+ "left join tDepartment1 d4 on b.Department1=d4.Code  "
				+ "left join txtdm x1 on a.LeadLevel=x1.CBM and x1.ID='LEADLEVEL' "
				+ "left join txtdm x2 on a.IsLead=x2.CBM and x2.ID='YESNO' "
				+ "left join tPosition p1 on p1.Code=a.Position  "
				+ "left join tPosition p2 on p2.Code=b.Position "
				+ "left join tDepartment3 d5 on d5.Code=a.Department3 "
				+ "left join tDepartment3 d6 on d6.Code=b.Department3 "
				+ "left join tDepartment2 d7 on b.Department2=d7.Code "
				+ "where (a.Department1<>b.Department1 or a.LeadLevel<>b.LeadLevel or a.IsLead<>b.IsLead "
				+ "or isnull(a.Department2,'')<>isnull(b.Department2,'') "
				+ "or isnull(a.ManagerRegularDate,'')<>isnull(b.ManagerRegularDate,'') "
				+ "or isnull(a.PosiChgDate,'')<>isnull(b.PosiChgDate,'') "
				+ "or isnull(a.OldDept,'')<>isnull(b.OldDept,'') "
				+ "or isnull(a.OldDeptDate,'')<>isnull(b.OldDeptDate,'') "
				+ "or isnull(a.Department3,'')<>isnull(b.Department3,'') "
				+ "or isnull(a.Position,'')<>isnull(b.Position,'') "
				+ "or isnull(a.Expired,'')<>isnull(b.Expired,'') "
				+ "or isnull(a.PerfBelongMode,'')<>isnull(b.PerfBelongMode,''))";
		if (StringUtils.isNotBlank(perfCycle.getNumber())) {
			sql += " and a.Number=? ";
			list.add(perfCycle.getNumber());
		}
		if (StringUtils.isBlank(perfCycle.getExpired())
				|| "F".equals(perfCycle.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isBlank(perfCycle.getExpired_empForPerf())
				|| "F".equals(perfCycle.getExpired_empForPerf())) {
			sql += " and b.Expired='F' ";
		}
		
		if (perfCycle.getPersonChgDateFrom()!= null) {
			sql += " and a.PersonChgDate >= ?";
			list.add(perfCycle.getPersonChgDateFrom());
		}
		if (perfCycle.getPersonChgDateTo() != null) {
			sql += " and a.PersonChgDate <DATEADD(d,1,?)";
			list.add(perfCycle.getPersonChgDateTo());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ")a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ")a order by a.PersonChgDate ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 员工信息同步
	 * 
	 * @param numbers
	 */
	public void doSyncEmployee(String numbers) {
		String sql = " delete from tEmpForPerf where number in ( '"
				+ numbers.replaceAll(",", "','") + "')";
		sql += " INSERT INTO tEmpForPerf(Number,Department1,Department2,LeadLevel,IsLead,ManagerRegularDate,PosiChgDate,"
                +" OldDept,OldDeptDate,PerfBelongMode,LastUpdate,ActionLog,Department3,Position,Expired) "
				+ " select Number,Department1,Department2,LeadLevel,IsLead,ManagerRegularDate,PosiChgDate,"
				+ " OldDept,OldDeptDate,PerfBelongMode,getdate(),'ADD',Department3,Position,Expired  "
				+ " from tEmployee where number in ( '"
				+ numbers.replaceAll(",", "','") + "')";
		this.executeUpdateBySql(sql, new Object[] {});
	}

	/**
	 * 部门领导信息同步列表
	 * 
	 * @param page
	 * @param perfCycle
	 * @return
	 */
	public Page<Map<String, Object>> findLeaderPageBySql(
			Page<Map<String, Object>> page, PerfCycle perfCycle) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from ( select a.Code,a.Desc2,a.LeaderCode,b.LeaderCode OldLeaderCode, "
				+"c.NameChi LeaderDescr,d.NameChi OldLeaderDescr "
				+"from tDepartment a " 
				+"left join tDepForPerf b on a.Code=b.Code " 
				+"left join tEmployee c on a.LeaderCode=c.Number "
				+"left join tEmployee d on b.LeaderCode=d.Number "
				+"where a.LeaderCode<>b.LeaderCode and a.IsActual='1' ";
		if (StringUtils.isNotBlank(perfCycle.getDepartmentDescr())) {
			sql += " and a.Desc2 like ? ";
			list.add("%"+perfCycle.getDepartmentDescr()+"%");
		}
		if (StringUtils.isBlank(perfCycle.getExpired_dept())
				|| "F".equals(perfCycle.getExpired_dept())) {
			sql += " and a.Expired='F' ";
		}if (StringUtils.isNotBlank(perfCycle.getLevel())) {
			sql += " and len(a.Path)-len(replace(a.Path,'/',''))+1=? ";
			list.add(perfCycle.getLevel());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ")a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ")a order by a.Code ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 部门领导信息同步
	 * 
	 * @param codes
	 */
	public void doSyncLeader(String codes) {
		String sql = " delete from tDepForPerf where code in ( '"
				+ codes.replaceAll(",", "','") + "')";
		sql += " INSERT INTO tDepForPerf(Code,LeaderCode,LastUpdate,LastUpdatedBy,Expired,ActionLog) "
				+ " select Code,LeaderCode,LastUpdate,LastUpdatedBy,Expired,ActionLog  "
				+ " from tDepartment where code in ( '"
				+ codes.replaceAll(",", "','") + "')";
		this.executeUpdateBySql(sql, new Object[] {});
	}
	
	/**
	 * 报表默认统计周期
	 * 
	 * @return
	 */
	public List<Map<String, Object>> defaultCycle() {
		String sql = "select no from tPerfCycle where cast(getdate() as date) between BeginDate and EndDate";
		return this.findBySql(sql, new Object[] {});
	}

	public String baseSql() {
		return " select a.PK PerfPK, c.PK SPK,pc.Y,pc.M "
				+ "  from tPerformance a "
				+ "  inner join tPerfCycle pc on pc.No=a.PerfCycleNo "
				+ "  inner join tCustomer b on a.CustCode=b.Code "
				+ "  left join tPerfStakeholder c on a.PK=c.PerfPk "
				+ "  left join tDepartment1 d1 on c.Department1=d1.Code "
				+ "  left join tDepartment2 d2 on c.Department2=d2.Code "
				+ "  inner join (select c.Code CustCode,r.Code RegionCode from tCustomer c "
				+ "              left join tBuilder b on  c.BuilderCode=b.Code "
				+ "              left join tRegion r on b.RegionCode=r.Code  "
				+ "              )r on b.Code=r.CustCode  where 1=1  ";
	}

	/**
	 * 公用sql的查询参数集合
	 * 
	 * @param perfCycle
	 * @return
	 */
	public List<Object> paramList(PerfCycle perfCycle, List<Object> list) {
		String baseSql = baseSql();
		if (perfCycle.getCrtDateFrom() != null) {
			baseSql += " and a.CrtDate >= ?";
			list.add(perfCycle.getCrtDateFrom());
		}
		if (perfCycle.getCrtDateTo() != null) {
			baseSql += " and a.CrtDate <DATEADD(d,1,?)";
			list.add(perfCycle.getCrtDateTo());
		}
		if (perfCycle.getAchieveDateFrom() != null) {
			baseSql += " and a.AchieveDate >= ?";
			list.add(perfCycle.getAchieveDateFrom());
		}
		if (perfCycle.getAchieveDateTo() != null) {
			baseSql += " and a.AchieveDate <DATEADD(d,1,?)";
			list.add(perfCycle.getAchieveDateTo());
		}
		if (StringUtils.isNotBlank(perfCycle.getDepartment1())) {
			baseSql += " and c.department1 in " + "('"
					+ perfCycle.getDepartment1().replaceAll(",", "','") + "')";
		}
		if (StringUtils.isNotBlank(perfCycle.getDepartment2())) {
			baseSql += " and c.department2 in " + "('"
					+ perfCycle.getDepartment2().replaceAll(",", "','") + "')";
		}
		if (StringUtils.isNotBlank(perfCycle.getDepartment3())) {
			baseSql += " and c.department3 in " + "('"
					+ perfCycle.getDepartment3().replaceAll(",", "','") + "')";
		}
		if (StringUtils.isNotBlank(perfCycle.getEmpCode())) {
			baseSql += " and c.empCode =?";
			list.add(perfCycle.getEmpCode());
		}
		if (StringUtils.isNotBlank(perfCycle.getNo())) {
			baseSql += " and a.PerfCycleNo =?";
			list.add(perfCycle.getNo());
		}
		if (StringUtils.isNotBlank(perfCycle.getCustType())) {
			baseSql += " and b.CustType in " + "('"
					+ perfCycle.getCustType().replaceAll(",", "','") + "')";
		}
		if (StringUtils.isNotBlank(perfCycle.getAddress())) {
			baseSql += " and b.address like ?";
			list.add("%" + perfCycle.getAddress() + "%");
		}
		if (StringUtils.isNotBlank(perfCycle.getIsChecked())) {
			baseSql += " and a.IsChecked =?";
			list.add(perfCycle.getIsChecked());
		}
		if (perfCycle.getY() != null) {
			baseSql += " and pc.Y =?";
			list.add(perfCycle.getY());
		}
		if (perfCycle.getSeason() != null) {
			baseSql += " and pc.season =?";
			list.add(perfCycle.getSeason());
		}
		if (StringUtils.isNotBlank(perfCycle.getDocumentNo())) {
			baseSql += " and a.DocumentNo =?";
			list.add(perfCycle.getDocumentNo());
		}
		if (StringUtils.isNotBlank(perfCycle.getDeptType())) {
			baseSql += " and (d1.DepType =? or d2.DepType=?)";
			list.add(perfCycle.getDeptType());
			list.add(perfCycle.getDeptType());
		}
		if (StringUtils.isNotBlank(perfCycle.getRegion())) {
			baseSql += " and r.RegionCode in " + "('"
					+ perfCycle.getRegion().replaceAll(",", "','") + "')";
		}
		if (StringUtils.isNotBlank(perfCycle.getPerfType())) {
			baseSql += " and a.Type in " + "('"
					+ perfCycle.getPerfType().replaceAll(",", "','") + "')";
		}
		if (StringUtils.isNotBlank(perfCycle.getSource())) {
			baseSql += " and b.Source=?";
			list.add(perfCycle.getSource());
		}
		if(StringUtils.isNotBlank(perfCycle.getCheckStatus())){
			baseSql += " and b.checkStatus in " + "('"+perfCycle.getCheckStatus().replace(",", "','" )+ "')";
		}
		basicSql = baseSql;
		return list;
	}

	/**
	 * 报表--明细
	 * 
	 * @param page
	 * @param perfCycle
	 * @return
	 */
	public Page<Map<String, Object>> findReportDetailBySql(
			Page<Map<String, Object>> page, PerfCycle perfCycle) {
		List<Object> list = paramList(perfCycle, new ArrayList<Object>());
		String sql = "select * from(select a.pk,a.perfcycleno,a.type,x1.NOTE typedescr,a.custcode,b.Descr custdescr,b.address, "
				+ "a.quantity,a.area,a.baseplan, a.mainplan,a.integrateplan,a.cupboardplan,a.softplan,a.mainservplan,a.designfee , " 
				+ "a.basedisc,a.contractfee,a.longfee,a.softfee_furniture,a.perfamount, " 
				+ "a.firstpay,a.mustreceive,a.realreceive,a.achievedate, a.mainproper, "
				+ "a.signdate,a.setdate,a.setminus,a.managefee_base,a.managefee_inset,a.managefee_base-a.managefee_inset managefee_outset,a.managefee_main,a.managefee_int,a.managefee_serv, "
				+ "a.managefee_soft,a.managefee_cup,a.remarks,a.ismodified,x2.NOTE ismodifieddescr, "
				+ "a.datatype,x3.NOTE datatypedescr,a.lastupdate,a.lastupdatedby,a.expired,a.actionlog, "
				+ "cast(dbo.fGetPerfEmpNameChi(a.PK,'01') as nvarchar(100)) businessmandescr,  "
				+ "cast(dbo.fGetPerfLeaderNameChi(a.PK, '01') as nvarchar(100)) businessmanleader, "
				+ "cast(dbo.fGetPerfEmpNameChi(a.PK,'00') as nvarchar(100)) designmandescr, "
				+ "cast(dbo.fGetPerfLeaderNameChi(a.PK, '00') as nvarchar(100)) designmanleader, "
				+ "cast(dbo.fGetPerfEmpNameChi(a.PK,'24') as nvarchar(100)) againmandescr,  "
				+ "a.crtdate,b.custtype,c.Desc1 custtypedescr, a.contractfee+a.designfee+a.tax contractanddesignfee,a.contractfee+a.tax contractandtax, " 
				+ "a.ManageFee_Base+a.ManageFee_Main+a.ManageFee_Serv+a.ManageFee_Soft+a.ManageFee_Int+a.ManageFee_Cup managefee_sum,a.regperfpk, "
				+ "a.tilestatus,x4.NOTE tilestatusdescr,a.bathstatus,x5.NOTE bathstatusdescr,a.tilededuction,a.bathdeduction,a.maindeduction,a.recalperf,a.perfperc,a.perfdisc,  "
				+ "a.ischgholder,X6.NOTE ischgholderdescr,a.documentno,a.ischecked,x7.Note ischeckeddescr,a.iscalpkperf, "
				+ "x8.NOTE iscalpkperfdescr,b.discremark,a.markup,x11.NOTE isinitsigndescr,  "
				//+ "(case when a.type in ('3','4','5') and a.RegPerfPk is not null  then cast(dbo.fGetPerfEmpNameChi(a.RegPerfPk,'01') as nvarchar(100)) else cast(dbo.fGetPerfEmpNameChi(b.PerfPk,'01')as nvarchar(100)) end) oldbusinessmandescr, "
				//+ "(case when a.type in ('3','4','5') and a.RegPerfPk is not null  then cast(dbo.fGetPerfEmpNameChi(a.RegPerfPk,'00') as nvarchar(100)) else cast(dbo.fGetPerfEmpNameChi(b.PerfPk,'00')as nvarchar(100)) end) olddesignmandescr, "
				//+ "(case when a.type in ('3','4','5') and a.RegPerfPk is not null  then cast(dbo.fGetPerfDept2Descr(a.RegPerfPk,'01') as nvarchar(100)) else cast(dbo.fGetPerfDept2Descr(b.PerfPk,'01')as nvarchar(100)) end) oldbusinessmandescrdep, "
				//+ "(case when a.type in ('3','4','5') and a.RegPerfPk is not null  then cast(dbo.fGetPerfDept2Descr(a.RegPerfPk,'00') as nvarchar(100)) else cast(dbo.fGetPerfDept2Descr(b.PerfPk,'00')as nvarchar(100)) end) olddesignmandescrdep, "
				//+ "(case when a.type in ('3','4','5') and a.RegPerfPk is not null  then cast(dbo.fGetPerfDept1Descr(a.RegPerfPk,'01') as nvarchar(100)) else cast(dbo.fGetPerfDept1Descr(b.PerfPk,'01')as nvarchar(100)) end) oldbusidept1descr,  "
				//+ "(case when a.type in ('3','4','5') and a.RegPerfPk is not null  then cast(dbo.fGetPerfDept1Descr(a.RegPerfPk,'00') as nvarchar(100)) else cast(dbo.fGetPerfDept1Descr(b.PerfPk,'00') as nvarchar(100)) end) olddesidept1descr, "
				+ "(case when a.RegPerfPk is not null  then cast(dbo.fGetPerfEmpNameChi(a.RegPerfPk,'01') as nvarchar(100)) else cast(dbo.fGetPerfEmpNameChi(b.PerfPk,'01')as nvarchar(100)) end) oldbusinessmandescr, "
				+ "(case when a.RegPerfPk is not null  then cast(dbo.fGetPerfEmpNameChi(a.RegPerfPk,'00') as nvarchar(100)) else cast(dbo.fGetPerfEmpNameChi(b.PerfPk,'00')as nvarchar(100)) end) olddesignmandescr, "
				+ "(case when a.RegPerfPk is not null  then cast(dbo.fGetPerfDept2Descr(a.RegPerfPk,'01') as nvarchar(100)) else cast(dbo.fGetPerfDept2Descr(b.PerfPk,'01')as nvarchar(100)) end) oldbusinessmandescrdep, "
				+ "(case when a.RegPerfPk is not null  then cast(dbo.fGetPerfDept2Descr(a.RegPerfPk,'00') as nvarchar(100)) else cast(dbo.fGetPerfDept2Descr(b.PerfPk,'00')as nvarchar(100)) end) olddesignmandescrdep, "
				+ "(case when a.RegPerfPk is not null  then cast(dbo.fGetPerfDept1Descr(a.RegPerfPk,'01') as nvarchar(100)) else cast(dbo.fGetPerfDept1Descr(b.PerfPk,'01')as nvarchar(100)) end) oldbusidept1descr,  "
				+ "(case when a.RegPerfPk is not null  then cast(dbo.fGetPerfDept1Descr(a.RegPerfPk,'00') as nvarchar(100)) else cast(dbo.fGetPerfDept1Descr(b.PerfPk,'00') as nvarchar(100)) end) olddesidept1descr, "
				+ "cast(dbo.fGetPerfDept2Descr(a.PK,'01') as nvarchar(100)) businessmandeptdescr, "
				+ "cast(dbo.fGetPerfDept2Descr(a.PK,'00') as nvarchar(100)) designmandeptdescr, "
				+ "cast(dbo.fGetPerfLeaderNameChi(a.RegPerfPk, '01') as nvarchar(100)) oldbusinessmanleader, "
				+ "cast(dbo.fGetPerfLeaderNameChi(a.RegPerfPk, '00') as nvarchar(100)) olddesignmanleader, "
				+ "a.softtokenamount,a.basededuction,a.itemdeduction,cast(dbo.fGetPerfBusiDrcNameChi(a.PK) as nvarchar(100)) busidrcdescr,dbo.fGetPerfBusiDrcNameChi(a.RegPerfPk) oldbusidrcdescr, "
				+ "a.baseperfper,round(a.PerfAmount-a.BaseDeduction-a.ItemDeduction-a.SoftTokenAmount,2) realperfamount, "
				+ "cast(dbo.fGetEmpNameChi(a.CustCode,'63') as nvarchar(100)) scenedesigndescr, cast(dbo.fGetEmpNameChi(a.CustCode,'64') as nvarchar(100)) deependesigndescr, "
				+ "a.marketfund,a.BasePlan-a.BaseDisc-a.MarketFund baseperfradix,  "
				+ "case when exists(select 1 from tPerfStakeholder in_a where a.pk=in_a.PerfPK and in_a.Role='01' and in_a.IsCalcPersonPerf='1') and "
				+ "          not exists(select 1 from tPerfStakeholder in_a where a.pk=in_a.PerfPK and in_a.Role='01' and in_a.IsCalcPersonPerf='0') then '是' "
				+ "     when exists(select 1 from tPerfStakeholder in_a where a.pk=in_a.PerfPK and in_a.Role='01' and in_a.IsCalcPersonPerf='0') and "
				+ "          not exists(select 1 from tPerfStakeholder in_a where a.pk=in_a.PerfPK and in_a.Role='01' and in_a.IsCalcPersonPerf='1') then '否' "
				+ "     else '' end iscalbusimanperfdescr, "
				+ "case when exists(select 1 from tPerfStakeholder in_a where a.pk=in_a.PerfPK and in_a.Role='00' and in_a.IsCalcPersonPerf='1') and "
				+ "          not exists(select 1 from tPerfStakeholder in_a where a.pk=in_a.PerfPK and in_a.Role='00' and in_a.IsCalcPersonPerf='0') then '是' "
				+ "     when exists(select 1 from tPerfStakeholder in_a where a.pk=in_a.PerfPK and in_a.Role='00' and in_a.IsCalcPersonPerf='0') and "
				+ "          not exists(select 1 from tPerfStakeholder in_a where a.pk=in_a.PerfPK and in_a.Role='00' and in_a.IsCalcPersonPerf='1') then '否' "
				+ "     else '' end iscaldesimanperfdescr, "
				+ "case when exists(select 1 from tPerfStakeholder in_a where a.pk=in_a.PerfPK and in_a.Role='24' and in_a.IsCalcPersonPerf='1') and "
				+ "          not exists(select 1 from tPerfStakeholder in_a where a.pk=in_a.PerfPK and in_a.Role='24' and in_a.IsCalcPersonPerf='0') then '是' "
				+ "     when exists(select 1 from tPerfStakeholder in_a where a.pk=in_a.PerfPK and in_a.Role='24' and in_a.IsCalcPersonPerf='0') and "
				+ "          not exists(select 1 from tPerfStakeholder in_a where a.pk=in_a.PerfPK and in_a.Role='24' and in_a.IsCalcPersonPerf='1') then '否' "
				+ "     else '' end iscalagainmanperfdescr, "
				+ "case when exists(select 1 from tPerfStakeholder in_a where a.pk=in_a.PerfPK and in_a.Role='01' and in_a.IsCalcDeptPerf='1') and "
				+ "          not exists(select 1 from tPerfStakeholder in_a where a.pk=in_a.PerfPK and in_a.Role='01' and in_a.IsCalcDeptPerf='0') then '是' "
				+ "     when exists(select 1 from tPerfStakeholder in_a where a.pk=in_a.PerfPK and in_a.Role='01' and in_a.IsCalcDeptPerf='0') and "
				+ "          not exists(select 1 from tPerfStakeholder in_a where a.pk=in_a.PerfPK and in_a.Role='01' and in_a.IsCalcDeptPerf='1') then '否' "
				+ "     else '' end iscalbusideptperfdescr, "
				+ " case when exists(select 1 from tPerfStakeholder in_a where a.pk=in_a.PerfPK and in_a.Role='00' and in_a.IsCalcDeptPerf='1') and "
				+ "           not exists(select 1 from tPerfStakeholder in_a where a.pk=in_a.PerfPK and in_a.Role='00' and in_a.IsCalcDeptPerf='0') then '是' "
				+ "      when exists(select 1 from tPerfStakeholder in_a where a.pk=in_a.PerfPK and in_a.Role='00' and in_a.IsCalcDeptPerf='0') and "
				+ "           not exists(select 1 from tPerfStakeholder in_a where a.pk=in_a.PerfPK and in_a.Role='00' and in_a.IsCalcDeptPerf='1') then '否' "
				+ "      else '' end iscaldesideptperfdescr, "
				+ "case when exists(select 1 from tPerfStakeholder in_a where a.pk=in_a.PerfPK and in_a.Role='24' and in_a.IsCalcDeptPerf='1') and "
				+ "          not exists(select 1 from tPerfStakeholder in_a where a.pk=in_a.PerfPK and in_a.Role='24' and in_a.IsCalcDeptPerf='0') then '是' "
				+ "     when exists(select 1 from tPerfStakeholder in_a where a.pk=in_a.PerfPK and in_a.Role='24' and in_a.IsCalcDeptPerf='0') and "
				+ "          not exists(select 1 from tPerfStakeholder in_a where a.pk=in_a.PerfPK and in_a.Role='24' and in_a.IsCalcDeptPerf='1') then '否' "
				+ "     else '' end iscalagaindeptperfdescr, "
				+ "a.realmaterperf,a.maxmaterperf,a.alreadymaterperf,a.materperf,x9.NOTE paytypedescr,b.paytype,c.maxmaterperfper,a.setadd,a.gift, "
				+ "isnull(e.Y,l.Y) oldyear, isnull(e.M,l.M) oldmonth, a.perfexpr, a.perfexprremarks, "
				+ "a.BasePlan-a.LongFee baseplanwithoutlongfee,a.ManageFee_Base+a.LongFee managefee_basewithlongfee, "
				+ "a.ManageFee_Base+a.ManageFee_Main+a.ManageFee_Serv+a.ManageFee_Soft+a.ManageFee_Int+a.ManageFee_Cup+a.LongFee managefee_sumwithlongfee, "
				+ "b.basefee_dirct,isnull(f.LineAmount,0) nomanageamount,b.secondpay, "
				+ "a.perfmarkup,case when CHARINDEX('@PerfMarkup@',a.PerfExpr)>0 then case when isnull(a.perfMarkup,'')<>'' and a.perfMarkup<>0 then a.recalperf/a.perfMarkup else a.recalperf end else a.recalperf end befmarkupperf,"
				+ "cast(dbo.fGetPerfDeptDescr(a.PK,'00',1) as nvarchar(100)) desidept1descr,cast(dbo.fGetPerfDeptDescr(a.PK,'00',2) as nvarchar(100)) desidept2descr, "
				+ "cast(dbo.fGetPerfDeptDescr(a.PK,'01',1) as nvarchar(100)) busidept1descr,cast(dbo.fGetPerfDeptDescr(a.PK,'01',2) as nvarchar(100)) busidept2descr, "
				+ "cast(dbo.fGetPerfDeptDescr(a.PK,'24',2) as nvarchar(100)) prjdept2descr,x10.NOTE constructtypedescr,a.PerfAmount-a.SoftTokenAmount perfamountwithoutsofttoken, "
				+ "case when isnull(a.PerfExpr,'')<>'' then a.BaseDeduction-a.LongFee else a.BaseDeduction end basedeductionwithoutlongfee,a.tax,x12.note isaddallinfodescr,a.basePersonalplan,a.managefee_basePersonal, a.woodplan, a.managefee_wood "
   				+ "from ( select distinct PerfPK from ("
				+ basicSql
				+ ")yjjs) main "
				+ "inner join tPerformance a on main.PerfPK=a.PK "
				+ "inner join tCustomer b on a.CustCode=b.Code "
				//+ "left join tCusttype c on b.CustType=c.Code "正常业绩、重签扣业绩  先从重签表取原客户类型
				+ "left join tAgainSign asn on asn.CustCode=a.CustCode and case when a.Type='5' then asn.BackPerfPK else asn.PerfPK end=a.PK " 
				+ "left join tCusttype c on c.Code=isnull(asn.CustType,b.CustType) " 
				+ "left join tXTDM x1 on a.Type=x1.CBM and x1.ID='PERFTYPE' "
				+ "left join tXTDM x2 on a.IsModified=x2.CBM and x2.ID='YESNO' "
				+ "left join tXTDM x3 on a.DataType=x3.CBM and x3.ID='PERFDATATYPE' "
				+ "left join tXTDM x4 on a.TileStatus=x4.CBM and x4.ID='MaterialStatus' "
				+ "left join tXTDM x5 on a.BathStatus=x5.CBM and x5.ID='MaterialStatus' "
				+ "left join tXTDM x6 on a.IsChgHolder=x6.CBM and x6.ID='YESNO' "
				+ "left join tXTDM x7 on a.IsChecked=x7.CBM and x7.ID='YESNO' "
				+ "left join tXTDM x8 on a.IsCalPkPerf=x8.CBM and x8.ID='YESNO' "
				+ "left join tXTDM x9 on b.PayType=x9.CBM and x9.ID='TIMEPAYTYPE' "
				+ "left join tXTDM x10 on b.ConstructType=x10.CBM and x10.ID='CONSTRUCTTYPE' "
				+ "left join tXTDM x11 on a.IsInitSign=x11.CBM and x11.ID='YESNO' "
				+ "left join tXTDM x12 on a.IsAddAllInfo=x12.CBM and x12.ID='YESNO' "
				+ "left join tPerformance d on a.RegPerfPK=d.PK "
				+ "left join tPerfCycle e on d.PerfCycleNo=e.No "
				+ "left join ( " // 计算不计管理费的预算金额
				+ " select a.CustCode, sum(a.LineAmount) LineAmount from tBaseItemPlan a "
				+ " inner join tBaseItem b on a.BaseItemCode=b.Code "
				+ " where b.IsCalMangeFee='0' "
				+ " group by a.CustCode "
				+ ") f on b.Code=f.CustCode "+
				" left join ( " +
				"		select min(in_a.PerfCycleNo) PerfCycleNo,in_a.CustCode " +
				"		from  tPerformance in_a " +
				"		group by CustCode  " +
				" )k on k.CustCode=a.CustCode " +
				" left join tPerfCycle l on l.no=k.PerfCycleNo " ;

		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ")a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ")a order by a.CrtDate desc,a.DocumentNo desc,a.CustCode desc";
		}
		page.setResult(findListBySql(sql, list.toArray()));
		page.setTotalCount(page.getResult().size());
		return page;
	}

	/**
	 * 报表--业务部
	 * 
	 * @param page
	 * @param perfCycle
	 * @return
	 */
	public Page<Map<String, Object>> findReportYwbBySql(
			Page<Map<String, Object>> page, PerfCycle perfCycle) {
		List<Object> list = paramList(perfCycle, new ArrayList<Object>());
		String sql = " with tGroup(Department1, Department2, sumRecalPerf, sumContractFee, num, PKPerf,contractFee,DesignFee) as "
				+ "(select b.Department1, b.Department2, sum(a.RecalPerf * b.PerfPer) sumRecalPerf, sum((a.ContractFee+a.DesignFee+isnull(a.Tax,0)) * b.PerfPer) sumContractFee, "
				+ "rank() over (order by  sum(case when a.IsCalPkPerf='1' then a.RecalPerf * b.PerfPer else 0 end) desc), sum(case when a.IsCalPkPerf='1' then a.RecalPerf * b.PerfPer else 0 end) PKPerf, "
				+ " sum((a.contractFee+isnull(a.Tax,0)) * b.PerfPer) contractFee,sum(a.DesignFee * b.PerfPer) designFee "
				+ " from  (select * from("
				+ basicSql
				+ ")yjjs ) main "
				+ "inner join tPerformance a on main.PerfPK = a.PK "
				+ "left join (  "
				+ "  select case when b.code is not null or b.code='' then b.Department1 else a.Department1 end Department1,a.Department2, "
				+ " a.PerfPer, a.PK, a.Role "
				+ " from tPerfStakeholder a "
				+ " left join tDepartment2 b on a.Department2=b.code "
				+ "where a.IsCalcDeptPerf='1' "
				+ ") b on main.SPK=b.PK  "
				+ "left join tDepartment1 d1 on b.Department1=d1.Code "
				+ "left join tDepartment2 d2 on b.Department2=d2.Code "
				+ "where b.Role = '01' and (b.Department1 is null or b.Department1='' or d1.DepType not in ('3','9','10')) and (b.Department2 is null or b.Department2='' or d2.DepType not  in ('3','9','10')) group by  b.Department1, b.Department2 "
				+ ") "
				+ "select d1.Desc2 dept1descr, d2.Desc2 dept2descr, "
				+ "a.sumrecalperf, a.sumcontractfee, '第'+cast(a.num as nvarchar(10))+'名' num, pkperf,e2.NameChi laderdescr,e2.posichgdate, "
				+ " a.contractfee,a.designfee "
				+ "from tGroup a "
				+ "left join tDepartment1 d1 on a.Department1 = d1.Code "
				+ "left join tDepartment2 d2 on a.Department2 = d2.Code "
				+ "left join (select min(Number) EmpCode, Department1, Department2  "
				+ "	  from   tEmployee  "
				+ "	  where  IsLead = '1' "
				+ "and Expired = 'F' "
				+ "	  group by Department1, Department2  "
				+ "	  ) g on g.Department1 = a.Department1  "
				+ "and g.Department2 = a.Department2  "
				+ "		  left outer join tEmployee e2 on e2.Number=g.EmpCode  "
				+ " order by a.Department1,d2.DispSeq";
		page.setResult(findListBySql(sql, list.toArray()));
		page.setTotalCount(page.getResult().size());
		return page;
	}

	/**
	 * 报表--设计部
	 * 
	 * @param page
	 * @param perfCycle
	 * @return
	 */
	public Page<Map<String, Object>> findReportSjbBySql(
			Page<Map<String, Object>> page, PerfCycle perfCycle) {
		List<Object> list = paramList(perfCycle, new ArrayList<Object>());
		String sql = "select d1.Desc2 dept1descr, d2.Desc2 dept2descr, "
           +"a.sumrecalperf, a.sumcontractfee, '第'+cast(a.num as nvarchar(10))+'名' num, a.pkperf ,e2.NameChi laderdescr,e2.posichgdate,a.contractfee,a.designfee  "
           +"from (select b.Department1, b.Department2, sum(a.RecalPerf * b.PerfPer) sumRecalPerf, sum((a.ContractFee+a.DesignFee+isnull(a.Tax,0)) * b.PerfPer) sumContractFee, "
           +"rank() over (order by  sum(case when a.IsCalPkPerf='1' then a.RecalPerf * b.PerfPer else 0 end) desc)num, "
           + " sum(case when a.IsCalPkPerf='1' then a.RecalPerf * b.PerfPer else 0 end) PKPerf,"
           + " sum((a.ContractFee+isnull(a.Tax,0)) * b.PerfPer) ContractFee, sum(a.DesignFee* b.PerfPer) DesignFee"
	       + " from  (select * from("
	       + basicSql
	   	   + ")yjjs ) main "
           +"inner join tPerformance a on main.PerfPK = a.PK "
           +"left join (  "
           +"  select case when b.code is not null or b.code='' then b.Department1 else a.Department1 end Department1,a.Department2, "
           +"  a.PerfPer, a.PK, a.Role "
           +"  from tPerfStakeholder a "
           +"  left join tDepartment2 b on a.Department2=b.code "
           +"  where a.IsCalcDeptPerf='1' "
           +") b on main.SPK=b.PK "
           +"left join tDepartment1 d1 on b.Department1=d1.Code "
           +"left join tDepartment2 d2 on b.Department2=d2.Code "
           +"where b.Role = '00' group by  b.Department1, b.Department2) a "
           +"left join tDepartment1 d1 on a.Department1 = d1.Code "
           +"left join tDepartment2 d2 on a.Department2 = d2.Code "
           +"left outer join (select min(Number) EmpCode, Department1, Department2  "
           +"from   tEmployee  "
           +"where  IsLead = '1' "
           +"and Expired = 'F' "
           +"group by Department1, Department2  "
           +") g on g.Department1 = a.Department1  "
           +"and g.Department2 = a.Department2  "
           +"left outer join tEmployee e2 on e2.Number = g.EmpCode  "
           +"order by a.Department1,d2.DispSeq";
		page.setResult(findListBySql(sql, list.toArray()));
		page.setTotalCount(page.getResult().size());
		return page;
	}

	/**
	 * 报表--事业部
	 * 
	 * @param page
	 * @param perfCycle
	 * @return
	 */
	public Page<Map<String, Object>> findReportSybBySql(
			Page<Map<String, Object>> page, PerfCycle perfCycle) {
		List<Object> list = paramList(perfCycle, new ArrayList<Object>());
		String sql = "select * from( select  b.Department1,d1.Desc2 Dept1Descr, "
				+ "sum(a.RecalPerf * b.PerfPer * case when b.Role = '00' then i.DesignAmtPer else h.BusiAmtPer end) sumRecalPerf,"
				+ "sum((a.ContractFee+a.DesignFee+isnull(a.Tax,0)) * b.PerfPer * case when b.Role = '00' then i.DesignAmtPer else h.BusiAmtPer end) sumContractFee," 
				+ "sum((a.ContractFee+isnull(a.Tax,0)) * b.PerfPer * case when b.Role = '00' then i.DesignAmtPer else h.BusiAmtPer end) ContractFee,"
				+ "sum(a.DesignFee* b.PerfPer * case when b.Role = '00' then i.DesignAmtPer else h.BusiAmtPer end) DesignFee, "
				+ "sum(case when a.IsCalPkPerf='1' then a.RecalPerf * b.PerfPer else 0 end * case when b.Role = '00' then i.DesignAmtPer else h.BusiAmtPer end) PKPerf,"
				+ "(isnull(MainIndPerf,0)+isnull(IntIndPerf,0)+isnull(SoftIndPerf,0))*0.94 sumDlxxContractFee,"
				+ "e2.NameChi laderdescr,e2.PosiChgDate "
				+ " from  (select * from("
				+ basicSql
				+ ")yjjs ) main "
				+ "inner join tPerformance a on main.PerfPK=a.PK "
				+ "left join tPerfStakeholder b on main.SPK=b.PK and b.IsCalcDeptPerf='1' "
				+ "left join tDepartment1 d1 on b.Department1 = d1.Code "
				+ "left join tDepartment2 d2 on b.Department2 = d2.Code "
				+ "left outer join ("
				+ "	select min(Number) EmpCode, Department1  "
				+ "	from   tEmployee  "
				+ "	where  IsLead = '1' and Expired = 'F' and Department2 = '' "
				+ " group by Department1  "
				+ ")g on g.Department1 = b.Department1 "
				+ "left outer join tEmployee e2 on e2.Number = g.EmpCode "
				+ "outer apply(select cast(QZ as money) BusiAmtPer from tXTCS where ID='BusiAmtPer') h "
				+ "outer apply(select cast(QZ as money) DesignAmtPer from tXTCS where ID='DesignAmtPer') i "
				+ "left join (" 
				+ "	select sum(in_a.ContractFee*in_b.ProvidePer*in_b.WeightPer)MainIndPerf,in_b.Department1,in_c.Mon "
				+ "	from tMainBusiCommi in_a  "
				+ "	left join tMainBusiStakeholder in_b on in_a.PK = in_b.CommiPK "
				+ "	inner join tItemCommiCycle in_c on in_a.CommiNo = in_c.No "
				+ "	where in_b.IsCalcDeptPerf = '1'  "
				+ "	group by in_b.Department1,Mon "
				+ ") j on main.Y*100+main.M = j.Mon and b.Department1 = j.Department1 "
				+ "left join (" 
				+ "	select sum(in_a.ContractFee*in_b.ProvidePer*in_b.WeightPer)SoftIndPerf,in_b.Department1,in_c.Mon "
				+ "	from tSoftBusiCommi in_a  "
				+ "	left join tSoftBusiStakeholder in_b on in_a.PK = in_b.CommiPK "
				+ "	inner join tItemCommiCycle in_c on in_a.CommiNo = in_c.No "
				+ "	where in_b.IsCalcDeptPerf = '1'  "
				+ "	group by in_b.Department1,Mon "
				+ ") k on main.Y*100+main.M = k.Mon and b.Department1 = k.Department1 "
				+ "left join (" 
				+ "	select sum(in_a.ContractFee*in_b.ProvidePer*in_b.WeightPer)IntIndPerf,in_b.Department1,in_c.Mon "
				+ "	from tIntBusiCommi in_a  "
				+ "	left join tIntBusiStakeholder in_b on in_a.PK = in_b.CommiPK "
				+ "	inner join tItemCommiCycle in_c on in_a.CommiNo = in_c.No "
				+ "	where in_b.IsCalcDeptPerf = '1'  "
				+ "	group by in_b.Department1,Mon "
				+ ") l on main.Y*100+main.M = l.Mon and b.Department1 = l.Department1 ";
		if ("true".equals(perfCycle.getRoleBox())) {
			sql += "where b.Role in ('00','01')";
		} else {
			sql += "where (b.Role='00' or (b.Role='01' and isnull(d2.DepType,'')<>'2' "
					+ "and not exists (select 1 from tPerfStakeholder in_a where in_a.PerfPK=a.PK and in_a.Role='00' and in_a.EmpCode=b.EmpCode)))";
		}
		sql += " group by b.Department1,d1.Desc2,e2.NameChi,e2.PosiChgDate,MainIndPerf,SoftIndPerf,IntIndPerf ";
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ")a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ")a ";
		}
		System.out.println(sql);
		System.out.println(list);
		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * 报表--工程部
	 * 
	 * @param page
	 * @param perfCycle
	 * @return
	 */
	public Page<Map<String, Object>> findReportGcbBySql(
			Page<Map<String, Object>> page, PerfCycle perfCycle) {
		List<Object> list = paramList(perfCycle, new ArrayList<Object>());
		String sql = "select * from(select  b.Department1,d1.Desc2 Dept1Descr,b.Department2,d2.Desc2 Dept2Descr, "
				+ "sum(a.RecalPerf * b.PerfPer) sumRecalPerf,sum((a.ContractFee+a.DesignFee+isnull(a.Tax,0)) * b.PerfPer) sumContractFee, "
				+ "sum(case when a.IsCalPkPerf='1' then a.RecalPerf * b.PerfPer else 0 end) PKPerf,sum(a.Quantity) AgainQty ,"
				+ " sum((a.ContractFee+isnull(a.Tax,0)) * b.PerfPer) contractFee, sum(a.designFee * b.PerfPer) designFee "
				+ " from  (select * from("
				+ basicSql
				+ ")yjjs ) main "
				+ "inner join tPerformance a on main.PerfPK=a.PK "
				+ "left join ( "
				+ " select case when b.code is not null or b.code <> '' then b.Department1 else a.Department1 end Department1,a.Department2, "
				+ "a.PerfPer, a.PK, a.Role "
				+ "from tPerfStakeholder a "
				+ "left join tDepartment2 b on a.Department2=b.code "
				+ "where a.IsCalcDeptPerf='1' "
				+ ") b on main.SPK=b.PK  "
				+ "left join tDepartment1 d1 on b.Department1 = d1.Code "
				+ "left join tDepartment2 d2 on b.Department2 = d2.Code "
				+ "where (b.Role='01' or b.Role='24') and (d1.DepType in ('3','9','10') or d2.DepType in ('3','9','10')) "
				+ "group by b.Department1,d1.Desc2,b.Department2,d2.Desc2";
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ")a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ")a ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * 报表--业务员
	 * 
	 * @param page
	 * @param perfCycle
	 * @return
	 */
	public Page<Map<String, Object>> findReportYwyBySql(
			Page<Map<String, Object>> page, PerfCycle perfCycle) {
		List<Object> list = paramList(perfCycle, new ArrayList<Object>());
		String sql = "select * from(select b.EmpCode,e.NameChi,d1.Desc2 Dept1Descr,d2.Desc2 Dept2Descr, "
				+ "sum(a.RecalPerf * b.PerfPer) sumRecalPerf,sum((a.ContractFee+a.DesignFee+isnull(a.Tax,0)) * b.PerfPer) sumContractFee, "
				+ "sum(case when c.NetChanel='1' then a.RecalPerf * b.PerfPer else 0 end) FollowPerf, "// 商务通客户算到跟单业绩
				+ "sum(case when c.NetChanel is null or c.NetChanel<>'1' then a.RecalPerf * b.PerfPer else 0 end) NoFollowPerf, "// 非商务通客户算到非跟单业绩
				+ "sum(case when a.IsCalPkPerf='1' then a.RecalPerf * b.PerfPer else 0 end) PKPerf, "
				+ "sum((a.ContractFee+isnull(a.Tax,0)) * b.PerfPer) contractfee, sum(a.DesignFee* b.PerfPer) designfee"
				+ " from  (select * from("
				+ basicSql
				+ ")yjjs ) main "
				+ "inner join tPerformance a on main.PerfPK=a.PK "
				+ "inner join tCustomer c on a.CustCode=c.Code "
				+ "left join tPerfStakeholder b on main.SPK = b.PK "
				+ "left join tEmployee e on b.EmpCode=e.Number "
				+ "left join tDepartment1 d1 on e.Department1=d1.Code "
				+ "left join tDepartment2 d2 on e.Department2=d2.Code "
				+ "left join tDepartment1 d3 on b.Department1=d3.Code "
				+ "left join tDepartment2 d4 on b.Department2=d4.Code "
				+ "where b.Role='01' and (d3.Code is null or d3.DepType not in ('3','9','10')) and (d4.Code is null or d4.DepType not in ('3','9','10')) and b.IsCalcPersonPerf='1' ";
		if (StringUtils.isNotBlank(perfCycle.getBusiType())) {
			sql += "and ((d4.Code is not null and d4.BusiType=?) or (d4.Code is null and d3.BusiType=?))";
			list.add(perfCycle.getBusiType());
			list.add(perfCycle.getBusiType());
		}
		sql += "group by b.EmpCode,e.NameChi,d1.Desc2,d2.Desc2";
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ")a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ")a order by a.PKPerf desc";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * 报表--设计师
	 * 
	 * @param page
	 * @param perfCycle
	 * @return
	 */
	public Page<Map<String, Object>> findReportSjsBySql(
			Page<Map<String, Object>> page, PerfCycle perfCycle) {
		List<Object> list = paramList(perfCycle, new ArrayList<Object>());
		String sql = "select * from(select b.EmpCode,e.NameChi,d1.Desc2 Dept1Descr,d2.Desc2 Dept2Descr, "
				+ "sum(a.RecalPerf * b.PerfPer) sumRecalPerf,sum((a.ContractFee+a.DesignFee+isnull(a.Tax,0)) * b.PerfPer) sumContractFee, "
				+ "sum(case when a.IsCalPkPerf='1' then a.RecalPerf * b.PerfPer else 0 end) PKPerf, "
				+ " sum((a.ContractFee+isnull(a.Tax,0)) * b.PerfPer) contractfee ,sum(a.Designfee * b.PerfPer) designfee"
				+ " from  (select * from("
				+ basicSql
				+ ")yjjs ) main "
				+ " inner join tPerformance a on main.PerfPK=a.PK "
				+ " left join tPerfStakeholder b on main.SPK = b.PK "
				+ "left join tEmployee e on b.EmpCode=e.Number "
				+ "left join tDepartment1 d1 on e.Department1=d1.Code "
				+ "left join tDepartment2 d2 on e.Department2=d2.Code "
				+ "where b.Role='00' and b.IsCalcPersonPerf='1' "
				+ "group by b.EmpCode,e.NameChi,d1.Desc2,d2.Desc2";
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ")a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ")a order by a.PKPerf desc";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * 报表--翻单员
	 * 
	 * @param page
	 * @param perfCycle
	 * @return
	 */
	public Page<Map<String, Object>> findReportFdyBySql(
			Page<Map<String, Object>> page, PerfCycle perfCycle) {
		List<Object> list = paramList(perfCycle, new ArrayList<Object>());
		String sql = "select * from(select b.EmpCode,e.NameChi,d1.Desc2 Dept1Descr,d2.Desc2 Dept2Descr, "
				+ "sum(a.RecalPerf * b.PerfPer) sumRecalPerf,sum((a.ContractFee+a.DesignFee+isnull(a.Tax,0)) * b.PerfPer) sumContractFee, "
				+ "sum(a.Quantity) AgainQty, "
				+ "sum(case when a.IsCalPkPerf='1' then a.RecalPerf * b.PerfPer else 0 end) PKPerf ,"
				+ " sum((a.ContractFee+isnull(a.Tax,0)) * b.PerfPer) contractfee, sum(a.DesignFee * b.PerfPer ) designfee"
				+ " from  (select * from( "
				+ basicSql
				+ ")yjjs ) main "
				+ "inner join tPerformance a on main.PerfPK=a.PK "
				+ "left join tPerfStakeholder b on main.SPK = b.PK "
				+ "left join tEmployee e on b.EmpCode=e.Number "
				+ "left join tDepartment1 d1 on b.Department1=d1.Code "
				+ "left join tDepartment2 d2 on b.Department2=d2.Code "
				+ "where ((b.Role='01' and (d1.DepType in ('3','9','10') or d2.DepType in ('3','9','10'))) or b.Role='24') and b.IsCalcPersonPerf='1' "
				+ "group by b.EmpCode,e.NameChi,d1.Desc2,d2.Desc2 ";
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ")a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ")a order by a.AgainQty desc, a.PKPerf desc";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

	/** 
	 * 报表--绘图员
	 * 
	 * @param page
	 * @param perfCycle
	 * @return
	 */
	public Page<Map<String, Object>> findReportHtyBySql(
			Page<Map<String, Object>> page, PerfCycle perfCycle) {
		List<Object> list = paramList(perfCycle, new ArrayList<Object>());

		String sql = "select a.empcode,a.namechi,a.dept1descr,a.dept2descr,a.sumarea, rank() over (order by a.sumArea desc) num from ( "
				+ "select cs.EmpCode,e.NameChi,d1.Desc2 Dept1Descr,d2.Desc2 Dept2Descr,round(sum(p.Area*1.0/cs2.num),2) sumArea  "
				+ " from ( "
				+ "   select a.CustCode, min(main.PerfPK) PerfPK "
				+ "from ( select distinct PerfPK from ("
				+ basicSql
				+ ")yjjs) main "
				+ "inner join tPerformance a on a.PK=main.PerfPK "
				+ "inner join tPerfCycle b on a.PerfCycleNo=b.No "
				+ "where a.Type='1' "
				+ "and not exists ( "
				+ "select 1 from tPerformance in_a "
				+ "inner join tPerfCycle in_b on in_a.PerfCycleNo=in_b.No "
				+ "where in_a.Type='1' and in_a.CustCode=a.CustCode and in_b.BeginDate<b.BeginDate) "
				+ "and not exists (select 1 from tPerformance in_a where in_a.Type='4' and in_a.RegPerfPk=a.PK and in_a.PerfCycleNo=a.PerfCycleNo) " // 统计了正常业绩，然后施工取消，且正常业绩与退单业绩在同一周期，不算绘图员业绩
				+ "group by a.CustCode "
				+ ") a "
				+ "inner join tPerformance p on a.PerfPK=p.PK "
				+ "inner join tCustomer c on a.CustCode=c.Code "
				+ "inner join tCustStakeholder cs on c.Code=cs.CustCode and cs.Role='03' "
				+ "inner join (select CustCode,Role,count(1) num from tCustStakeholder group by CustCode,Role) cs2 on cs2.CustCode=c.Code and cs2.Role=cs.Role "
				+ "left join tEmployee e on e.Number=cs.EmpCode "
				+ "left join tDepartment1 d1 on e.Department1=d1.Code "
				+ "left join tDepartment2 d2 on e.Department2=d2.Code "
				+ "group by cs.EmpCode,e.NameChi,d1.Desc2,d2.Desc2 ) a";
		page.setResult(this.findListBySql(sql, list.toArray()));
		page.setTotalCount(page.getResult().size());
		return page;
	}

	/**
	 * 报表--业务主任
	 * 
	 * @param page
	 * @param perfCycle
	 * @return
	 */
	public Page<Map<String, Object>> findReportYwzrBySql(
			Page<Map<String, Object>> page, PerfCycle perfCycle) {
		List<Object> list = paramList(perfCycle, new ArrayList<Object>());
		String sql = " select d1.Desc2 dept1descr, d2.Desc2 dept2descr, "
				+ "a.sumrecalperf, a.sumcontractfee, '第'+cast(a.num as nvarchar(10))+'名' num, a.pkperf,e.NameChi busidrcdescr,a.contractfee,a.designfee "
				+ "from (select b.BusiDrc, sum(a.RecalPerf * b.PerfPer) sumRecalPerf, sum((a.ContractFee+a.DesignFee+isnull(a.Tax,0)) * b.PerfPer) sumContractFee, "
				+ "rank() over (order by  sum(case when a.IsCalPkPerf='1' then a.RecalPerf * b.PerfPer else 0 end) desc)num, sum(case when a.IsCalPkPerf='1' then a.RecalPerf * b.PerfPer else 0 end) PKPerf ,"
				+ " sum((a.ContractFee+isnull(a.Tax,0)) * b.PerfPer) contractfee,sum(a.designFee * b.PerfPer) designfee "
				+ " from  (select * from( "
				+ basicSql
				+ ")yjjs ) main "
				+ "inner join tPerformance a on main.PerfPK = a.PK "
				+ "inner join tPerfStakeholder b on main.SPK=b.PK "
				+ "where b.Role = '01' and b.IsCalcDeptPerf='1' and b.BusiDrc is not null and b.BusiDrc<>'' group by b.BusiDrc) a "
				+ "left outer join tEmployee e on e.Number=a.BusiDrc  "
				+ "left outer join tDepartment1 d1 on d1.Code=e.Department1 "
				+ "left outer join tDepartment2 d2 on d2.Code=e.Department2 "
				+ " order by a.num";
		page.setResult(this.findListBySql(sql, list.toArray()));
		page.setTotalCount(page.getResult().size());
		return page;
	}

	/**
	 * 已计算业绩
	 * 
	 * @param page
	 * @param perfCycle
	 * @return
	 */
	public Page<Map<String, Object>> findYjsyjBySql(
			Page<Map<String, Object>> page, PerfCycle perfCycle) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select i.desc2 branchCmpName,j.Descr signCmpName,a.PK,a.PerfCycleNo,a.Type,x1.NOTE TypeDescr,a.CustCode,b.Descr CustDescr,b.Address, "
				+ "a.Quantity,a.Area, a.BasePlan,a.MainPlan,a.IntegratePlan,a.CupboardPlan,a.SoftPlan,a.MainServPlan,a.DesignFee, " 
				+ "a.BaseDisc, a.ContractFee,a.LongFee,a.SoftFee_Furniture,a.PerfAmount, " 
				+ "a.FirstPay,a.MustReceive,a.RealReceive,a.AchieveDate,a.MainProPer , "
				+ "a.SignDate,a.SetDate,a.SetMinus,a.ManageFee_Base,a.ManageFee_InSet,a.ManageFee_Base-a.ManageFee_InSet ManageFee_OutSet,a.ManageFee_Main,a.ManageFee_Int,a.ManageFee_Serv, "
				+ "a.ManageFee_Soft,a.ManageFee_Cup,a.Remarks,a.IsModified,x2.NOTE IsModifiedDescr, "
				+ "a.DataType,x3.NOTE DataTypeDescr,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog, "
				+ "cast(dbo.fGetPerfEmpNameChi(a.PK,'01') as nvarchar(100)) BusinessManDescr,  "
				+ "cast(dbo.fGetPerfLeaderNameChi(a.PK, '01') as nvarchar(100)) BusinessManLeader, "
				+ "cast(dbo.fGetPerfEmpNameChi(a.PK,'00') as nvarchar(100)) DesignManDescr, "
				+ "cast(dbo.fGetPerfLeaderNameChi(a.PK, '00') as nvarchar(100)) DesignManLeader, "
				+ "cast(dbo.fGetPerfEmpNameChi(a.PK,'24') as nvarchar(100)) AgainManDescr,  "
				+ "a.CrtDate,b.CustType,c.Desc1 CustTypeDescr,a.ContractFee+a.DesignFee+a.Tax  ContractAndDesignFee,a.ContractFee+a.Tax ContractAndTax, " 
				+ "a.ManageFee_Base+a.ManageFee_Main+a.ManageFee_Serv+a.ManageFee_Soft+a.ManageFee_Int+a.ManageFee_Cup ManageFee_Sum,a.RegPerfPk, "
				+ "a.TileStatus,x4.NOTE TileStatusDescr,a.BathStatus,x5.NOTE BathStatusDescr,a.TileDeduction,a.BathDeduction,a.MainDeduction,a.RecalPerf,a.PerfPerc,a.PerfDisc,  "
				+ "a.IsChgHolder,X6.NOTE IsChgHolderDescr,a.DocumentNo,a.IsChecked,x7.Note IsCheckedDescr,a.IsCalPkPerf, "
				+ "x8.NOTE IsCalPkPerfDescr,b.DiscRemark,a.Markup,x11.NOTE isInitSignDescr,  "
				+ "(case when a.type in ('3','4','5') and a.RegPerfPk is not null  then cast(dbo.fGetPerfEmpNameChi(a.RegPerfPk,'01') as nvarchar(100)) else cast(dbo.fGetPerfEmpNameChi(b.PerfPk,'01')as nvarchar(100)) end) oldBusinessManDescr, "
				+ "(case when a.type in ('3','4','5') and a.RegPerfPk is not null  then cast(dbo.fGetPerfEmpNameChi(a.RegPerfPk,'00') as nvarchar(100)) else cast(dbo.fGetPerfEmpNameChi(b.PerfPk,'00')as nvarchar(100)) end) oldDesignManDescr, "
				+ "(case when a.type in ('3','4','5') and a.RegPerfPk is not null  then cast(dbo.fGetPerfDept2Descr(a.RegPerfPk,'01') as nvarchar(100)) else cast(dbo.fGetPerfDept2Descr(b.PerfPk,'01')as nvarchar(100)) end) oldBusinessManDescrDep, "
				+ "(case when a.type in ('3','4','5') and a.RegPerfPk is not null  then cast(dbo.fGetPerfDept2Descr(a.RegPerfPk,'00') as nvarchar(100)) else cast(dbo.fGetPerfDept2Descr(b.PerfPk,'00')as nvarchar(100)) end) oldDesignManDescrDep, "
				+ "(case when a.type in ('3','4','5') and a.RegPerfPk is not null  then cast(dbo.fGetPerfDept1Descr(a.RegPerfPk,'01') as nvarchar(100)) else cast(dbo.fGetPerfDept1Descr(b.PerfPk,'01')as nvarchar(100)) end) oldBusiDept1Descr,  "
				+ "(case when a.type in ('3','4','5') and a.RegPerfPk is not null  then cast(dbo.fGetPerfDept1Descr(a.RegPerfPk,'00') as nvarchar(100)) else cast(dbo.fGetPerfDept1Descr(b.PerfPk,'00') as nvarchar(100)) end) oldDesiDept1Descr, "
				+ "cast(dbo.fGetPerfDept2Descr(a.PK,'01') as nvarchar(100)) BusinessManDeptDescr, "
				+ "cast(dbo.fGetPerfDept2Descr(a.PK,'00') as nvarchar(100)) DesignManDeptDescr, "
				+ "cast(dbo.fGetPerfLeaderNameChi(a.RegPerfPk, '01') as nvarchar(100)) OldBusinessManLeader, "
				+ "cast(dbo.fGetPerfLeaderNameChi(a.RegPerfPk, '00') as nvarchar(100)) OldDesignManLeader, "
				+ "a.SoftTokenAmount,a.BaseDeduction,a.ItemDeduction,cast(dbo.fGetPerfBusiDrcNameChi(a.PK) as nvarchar(100)) BusiDrcDescr,dbo.fGetPerfBusiDrcNameChi(a.RegPerfPk) OldBusiDrcDescr, "
				+ "a.BasePerfPer,round(a.PerfAmount-a.BaseDeduction-a.ItemDeduction-a.SoftTokenAmount,2) RealPerfAmount, "
				+ "cast(dbo.fGetEmpNameChi(a.CustCode,'63') as nvarchar(100)) SceneDesignDescr, cast(dbo.fGetEmpNameChi(a.CustCode,'64') as nvarchar(100)) DeepenDesignDescr, "
				+ "a.MarketFund,a.BasePlan-a.BaseDisc-a.MarketFund BasePerfRadix,  "
				+ "case when exists(select 1 from tPerfStakeholder in_a where a.pk=in_a.PerfPK and in_a.Role='01' and in_a.IsCalcPersonPerf='1') and "
				+ "          not exists(select 1 from tPerfStakeholder in_a where a.pk=in_a.PerfPK and in_a.Role='01' and in_a.IsCalcPersonPerf='0') then '是' "
				+ "     when exists(select 1 from tPerfStakeholder in_a where a.pk=in_a.PerfPK and in_a.Role='01' and in_a.IsCalcPersonPerf='0') and "
				+ "          not exists(select 1 from tPerfStakeholder in_a where a.pk=in_a.PerfPK and in_a.Role='01' and in_a.IsCalcPersonPerf='1') then '否' "
				+ "     else '' end IsCalBusiManPerfDescr, "
				+ "case when exists(select 1 from tPerfStakeholder in_a where a.pk=in_a.PerfPK and in_a.Role='00' and in_a.IsCalcPersonPerf='1') and "
				+ "          not exists(select 1 from tPerfStakeholder in_a where a.pk=in_a.PerfPK and in_a.Role='00' and in_a.IsCalcPersonPerf='0') then '是' "
				+ "     when exists(select 1 from tPerfStakeholder in_a where a.pk=in_a.PerfPK and in_a.Role='00' and in_a.IsCalcPersonPerf='0') and "
				+ "          not exists(select 1 from tPerfStakeholder in_a where a.pk=in_a.PerfPK and in_a.Role='00' and in_a.IsCalcPersonPerf='1') then '否' "
				+ "     else '' end IsCalDesiManPerfDescr, "
				+ "case when exists(select 1 from tPerfStakeholder in_a where a.pk=in_a.PerfPK and in_a.Role='24' and in_a.IsCalcPersonPerf='1') and "
				+ "          not exists(select 1 from tPerfStakeholder in_a where a.pk=in_a.PerfPK and in_a.Role='24' and in_a.IsCalcPersonPerf='0') then '是' "
				+ "     when exists(select 1 from tPerfStakeholder in_a where a.pk=in_a.PerfPK and in_a.Role='24' and in_a.IsCalcPersonPerf='0') and "
				+ "          not exists(select 1 from tPerfStakeholder in_a where a.pk=in_a.PerfPK and in_a.Role='24' and in_a.IsCalcPersonPerf='1') then '否' "
				+ "     else '' end IsCalAgainManPerfDescr, "
				+ "case when exists(select 1 from tPerfStakeholder in_a where a.pk=in_a.PerfPK and in_a.Role='01' and in_a.IsCalcDeptPerf='1') and "
				+ "          not exists(select 1 from tPerfStakeholder in_a where a.pk=in_a.PerfPK and in_a.Role='01' and in_a.IsCalcDeptPerf='0') then '是' "
				+ "     when exists(select 1 from tPerfStakeholder in_a where a.pk=in_a.PerfPK and in_a.Role='01' and in_a.IsCalcDeptPerf='0') and "
				+ "          not exists(select 1 from tPerfStakeholder in_a where a.pk=in_a.PerfPK and in_a.Role='01' and in_a.IsCalcDeptPerf='1') then '否' "
				+ "     else '' end IsCalBusiDeptPerfDescr, "
				+ " case when exists(select 1 from tPerfStakeholder in_a where a.pk=in_a.PerfPK and in_a.Role='00' and in_a.IsCalcDeptPerf='1') and "
				+ "           not exists(select 1 from tPerfStakeholder in_a where a.pk=in_a.PerfPK and in_a.Role='00' and in_a.IsCalcDeptPerf='0') then '是' "
				+ "      when exists(select 1 from tPerfStakeholder in_a where a.pk=in_a.PerfPK and in_a.Role='00' and in_a.IsCalcDeptPerf='0') and "
				+ "           not exists(select 1 from tPerfStakeholder in_a where a.pk=in_a.PerfPK and in_a.Role='00' and in_a.IsCalcDeptPerf='1') then '否' "
				+ "      else '' end IsCalDesiDeptPerfDescr, "
				+ "case when exists(select 1 from tPerfStakeholder in_a where a.pk=in_a.PerfPK and in_a.Role='24' and in_a.IsCalcDeptPerf='1') and "
				+ "          not exists(select 1 from tPerfStakeholder in_a where a.pk=in_a.PerfPK and in_a.Role='24' and in_a.IsCalcDeptPerf='0') then '是' "
				+ "     when exists(select 1 from tPerfStakeholder in_a where a.pk=in_a.PerfPK and in_a.Role='24' and in_a.IsCalcDeptPerf='0') and "
				+ "          not exists(select 1 from tPerfStakeholder in_a where a.pk=in_a.PerfPK and in_a.Role='24' and in_a.IsCalcDeptPerf='1') then '否' "
				+ "     else '' end IsCalAgainDeptPerfDescr, "
				+ "a.RealMaterPerf,a.MaxMaterPerf,a.AlreadyMaterPerf,a.MaterPerf,x9.NOTE PayTypeDescr,b.PayType,c.MaxMaterPerfPer,a.SetAdd,a.Gift, "
				+ "isnull(e.Y,l.Y) OldYear, isnull(e.M,l.M) OldMonth, a.PerfExpr, a.PerfExprRemarks, "
				+ "a.BasePlan-a.LongFee BasePlanWithoutLongFee,a.ManageFee_Base+a.LongFee ManageFee_BaseWithLongFee, "
				+ "a.ManageFee_Base+a.ManageFee_Main+a.ManageFee_Serv+a.ManageFee_Soft+a.ManageFee_Int+a.ManageFee_Cup+a.LongFee ManageFee_SumWithLongFee, "
				+ "b.BaseFee_Dirct,isnull(f.LineAmount,0) NoManageAmount,b.SecondPay, "
				+ "a.perfmarkup,case when CHARINDEX('@PerfMarkup@',a.PerfExpr)>0 then case when isnull(a.perfMarkup,'')<>'' and a.perfMarkup<>0 then a.recalperf/a.perfMarkup else a.recalperf end else a.recalperf end befMarkupPerf,"
				+ "cast(dbo.fGetPerfDeptDescr(a.PK,'00',1) as nvarchar(100)) DesiDept1Descr,cast(dbo.fGetPerfDeptDescr(a.PK,'00',2) as nvarchar(100)) DesiDept2Descr, "
				+ "cast(dbo.fGetPerfDeptDescr(a.PK,'01',1) as nvarchar(100)) BusiDept1Descr,cast(dbo.fGetPerfDeptDescr(a.PK,'01',2) as nvarchar(100)) BusiDept2Descr, "
				+ "cast(dbo.fGetPerfDeptDescr(a.PK,'24',2) as nvarchar(100)) PrjDept2Descr,x10.NOTE ConstructTypeDescr,a.PerfAmount-a.SoftTokenAmount PerfAmountWithoutSoftToken, "
				+ "case when isnull(a.PerfExpr,'')<>'' then a.BaseDeduction-a.LongFee else a.BaseDeduction end BaseDeductionWithoutLongFee,a.Tax,x12.note IsAddAllInfoDescr, "
				+ "a.BasePersonalPlan,a.ManageFee_BasePersonal,a.WoodPlan,a.ManageFee_Wood "
				+ "from tPerformance a "
				+ "inner join tCustomer b on a.CustCode=b.Code "
				// + "left join tCusttype c on b.CustType=c.Code " 正常业绩、重签扣业绩  先从重签表取原客户类型
				+ "left join tAgainSign asn on asn.CustCode=a.CustCode and case when a.Type='5' then asn.BackPerfPK else asn.PerfPK end=a.PK " 
				+ "left join tCusttype c on c.Code=isnull(asn.CustType,b.CustType) " 
				+ "left join tXTDM x1 on a.Type=x1.CBM and x1.ID='PERFTYPE' "
				+ "left join tXTDM x2 on a.IsModified=x2.CBM and x2.ID='YESNO' "
				+ "left join tXTDM x3 on a.DataType=x3.CBM and x3.ID='PERFDATATYPE' "
				+ "left join tXTDM x4 on a.TileStatus=x4.CBM and x4.ID='MaterialStatus' "
				+ "left join tXTDM x5 on a.BathStatus=x5.CBM and x5.ID='MaterialStatus' "
				+ "left join tXTDM x6 on a.IsChgHolder=x6.CBM and x6.ID='YESNO' "
				+ "left join tXTDM x7 on a.IsChecked=x7.CBM and x7.ID='YESNO' "
				+ "left join tXTDM x8 on a.IsCalPkPerf=x8.CBM and x8.ID='YESNO' "
				+ "left join tXTDM x9 on b.PayType=x9.CBM and x9.ID='TIMEPAYTYPE' "
				+ "left join tXTDM x10 on b.ConstructType=x10.CBM and x10.ID='CONSTRUCTTYPE' "
				+ "left join tXTDM x11 on a.IsInitSign=x11.CBM and x11.ID='YESNO' "
				+ "left join tXTDM x12 on a.IsAddAllInfo=x12.CBM and x12.ID='YESNO' "
				+ "left join tPerformance d on a.RegPerfPK=d.PK "
				+ "left join tPerfCycle e on d.PerfCycleNo=e.No "
				+ "left join ( " // 计算不计管理费的预算金额
				+ " select a.CustCode, sum(a.LineAmount) LineAmount from tBaseItemPlan a "
				+ " inner join tBaseItem b on a.BaseItemCode=b.Code "
				+ " where b.IsCalMangeFee='0' "
				+ " group by a.CustCode "
				+ ") f on b.Code=f.CustCode  " +
				" left join tBuilder g on g.code = b.BuilderCode " +
				" left join tRegion h on h.code = g.regionCode " +
				" left join tCompany i on i.code = h.cmpCode " +
				" left join tTaxPayee j on j.Code = a.PayeeCode " +
				" left join ( " +
				"		select min(in_a.PerfCycleNo) PerfCycleNo,in_a.CustCode " +
				"		from  tPerformance in_a " +
				"		group by CustCode  " +
				" )k on k.CustCode=a.CustCode " +
				" left join tPerfCycle l on l.no=k.PerfCycleNo " +
				" where a.PerfCycleNo=? ";
		list.add(perfCycle.getNo());
		if (StringUtils.isNotBlank(perfCycle.getCustCode())) {
			sql += " and a.CustCode=? ";
			list.add(perfCycle.getCustCode());
		}
		if (StringUtils.isNotBlank(perfCycle.getAddress())) {
			sql += " and b.address like ? ";
			list.add("%" + perfCycle.getAddress() + "%");
		}
		if (StringUtils.isNotBlank(perfCycle.getIsCalcPersonPerf())) {
			sql += " and exists(select 1 from tPerfStakeholder p where a.pk=p.perfpk and p.IsCalcPersonPerf=?)";
			list.add(perfCycle.getIsCalcPersonPerf());
		}
		if (StringUtils.isNotBlank(perfCycle.getPerfType())) {
			sql += " and a.Type=? ";
			list.add(perfCycle.getPerfType());
		}
		if (StringUtils.isNotBlank(perfCycle.getIsModified())) {
			sql += " and a.IsModified=?";
			list.add(perfCycle.getIsModified());
		}
		if (StringUtils.isNotBlank(perfCycle.getDataType())) {
			sql += " and a.dataType=?";
			list.add(perfCycle.getDataType());
		}
		if (StringUtils.isNotBlank(perfCycle.getIsCalcDeptPerf())) {
			sql += " and exists(select 1 from tPerfStakeholder p where a.pk=p.perfpk and p.IsCalcDeptPerf=?)";
			list.add(perfCycle.getIsCalcDeptPerf());
		}
		if (perfCycle.getAchieveDateFrom() != null) {
			sql += " and a.AchieveDate >= ?";
			list.add(perfCycle.getAchieveDateFrom());
		}
		if (perfCycle.getAchieveDateTo() != null) {
			sql += " and a.AchieveDate <DATEADD(d,1,?)";
			list.add(perfCycle.getAchieveDateTo());
		}
		if (StringUtils.isNotBlank(perfCycle.getIsChecked())) {
			sql += " and a.isChecked=?";
			list.add(perfCycle.getIsChecked());
		}
		if (StringUtils.isNotBlank(perfCycle.getIsCalPkPerf())) {
			sql += " and a.IsCalPkPerf=?";
			list.add(perfCycle.getIsCalPkPerf());
		}
		if (StringUtils.isNotBlank(perfCycle.getSource())) {
			sql += " and b.Source=?";
			list.add(perfCycle.getSource());
		}
		if (StringUtils.isNotBlank(perfCycle.getBusinessMan())) {
			sql += " and exists(select 1 from tPerfStakeholder p where a.pk=p.perfpk and p.role='01' and p.empcode=?)";
			list.add(perfCycle.getBusinessMan());
		}
		if (StringUtils.isNotBlank(perfCycle.getDesignMan())) {
			sql += " and exists(select 1 from tPerfStakeholder p where a.pk=p.perfpk and p.role='00' and p.empcode=?)";
			list.add(perfCycle.getDesignMan());
		}
		if (StringUtils.isNotBlank(perfCycle.getAgainMan())) {
			sql += " and exists(select 1 from tPerfStakeholder p where a.pk=p.perfpk and p.role='24' and p.empcode=?)";
			list.add(perfCycle.getAgainMan());
		}
		if (StringUtils.isNotBlank(perfCycle.getDocumentNo())) {
			sql += " and a.DocumentNo=?";
			list.add(perfCycle.getDocumentNo());
		}
		if ("1".equals(perfCycle.getChkNoLeader())) {
			sql += " and exists (select 1 from tPerfStakeholder in_a where in_a.PerfPK=a.PK and (in_a.LeaderCode is null or in_a.LeaderCode=''))";
		}
		if(StringUtils.isNotBlank(perfCycle.getCheckStatus())){
			sql += " and b.checkStatus in " + "('"+perfCycle.getCheckStatus().replace(",", "','" )+ "')";
		}
		if (StringUtils.isNotBlank(perfCycle.getIsAddAllInfo())) {
			sql += " and a.IsAddAllInfo=?";
			list.add(perfCycle.getIsAddAllInfo());
		}
		if (StringUtils.isNotBlank(perfCycle.getPerfDateType())) {
			if("1".equals(perfCycle.getPerfDateType())){ //旧业绩
				sql += " and isnull(dbo.fGetPerfAchieveDateMin(a.CustCode),getdate())< (select QZ from tXTCS where ID= 'NEWCOMMIDATE') ";
			}else {
				sql += " and isnull(dbo.fGetPerfAchieveDateMin(a.CustCode),getdate())>= (select QZ from tXTCS where ID= 'NEWCOMMIDATE') ";
			}	
		}
		return this.findPageByJdbcTemp(page, sql.toLowerCase(), "a.CrtDate desc,a.DocumentNo desc,a.CustCode desc", list.toArray());
	}
	/**
	 * 未计算业绩
	 * @param page
	 * @param perfCycle
	 * @return
	 */
	public Page<Map<String, Object>> findWjsyjBySql(Page<Map<String, Object>> page,PerfCycle perfCycle) {
		List<Object> list = new ArrayList<Object>();
		String sql = "  exec pCalcPerf_wjs ?,?,?,?,?,?,? ";
		list.add(perfCycle.getCustCode());
		list.add(perfCycle.getAddress());
		list.add(perfCycle.getBusinessMan());
		list.add(perfCycle.getDesignMan());
		list.add(perfCycle.getAgainMan());
		list.add(perfCycle.getDocumentNo());
		list.add(perfCycle.getCheckStatus());
		page.setResult(findListBySql(sql, list.toArray()));
		page.setTotalCount(page.getResult().size());
		return page;
	}
	/**
	 * 生成业绩数据
	 * 
	 * @param no
	 * @param lastUpdatedBy
	 * @param calChgPerf
	 */
	public Map<String, Object> doCount(String no, String lastUpdatedBy,String calChgPerf) {
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pCalcPerf(?,?,?)}");
			call.setString(1, no);
			call.setString(2, lastUpdatedBy);
			call.setString(3, calChgPerf);
			call.execute();
			ResultSet rs = call.getResultSet();
			List<Map<String, Object>> list = BeanConvertUtil
					.resultSetToList(rs);
			if (list.size() != 0 && list != null) {
				if (list.get(0).get("errmsg") != null)
					return list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return null;
	}
	/**
	 * 业绩扣减设置
	 * 
	 * @param perfCycle
	 */
	public void doPerfChgSet(PerfCycle perfCycle) {
		String sql = "update tCustomer SET PerfPercCode=?, TileStatus=?, BathStatus=?  where Code=? ";
		this.executeUpdateBySql(sql, new Object[] { perfCycle.getPerfPerc(), perfCycle.getTileStatus(),
				perfCycle.getBathStatus(),perfCycle.getCustCode() });
	}
	/**
	 * 跳转到业绩扣减设置所需要的数据
	 * 
	 * @param perfCycle
	 */
	public List<Map<String, Object>> findChgPefByCode(PerfCycle perfCycle) {
		String sql = "select code custCode,address,perfPercCode perfPerc,tileStatus,bathStatus,descr custDescr,"
				+ "status custStatus from tCustomer where Code=?";
		return this.findBySql(sql, new Object[] { perfCycle.getCustCode() });
	}
	/**
	 * 跳转到指定客户所需要的数据
	 * 
	 * @param perfCycle
	 */
	public List<Map<String, Object>> beforePointCust(PerfCycle perfCycle) {
		String sql = "select no,y,m,season,beginDate,endDate from tPerfCycle where No=?";
		return this.findBySql(sql, new Object[] { perfCycle.getNo()});
	}
	/**
	 * 指定客户--参与业绩计算
	 * 
	 * @param page
	 * @param perfCycle
	 * @return
	 */
	public Page<Map<String, Object>> findCyyjjsBySql(
			Page<Map<String, Object>> page, PerfCycle perfCycle) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from(select a.PK,a.PerfCycleNo,a.CustCode,b.Descr CustDescr,b.Address,b.SignDate, "
           +"a.IsCalcPerf,a.AchieveDate,a.Remarks,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog "  
           +"from tManualPerfCust a "
           +"inner join tCustomer b on a.CustCode=b.Code "
           +"where a.IsCalcPerf='1' and a.PerfCycleNo=?";
		list.add(perfCycle.getNo());
		if(StringUtils.isNotBlank(perfCycle.getAddress())){
			sql+=" and b.address like ?";
			list.add("%"+perfCycle.getAddress()+"%");
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ")a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ")a ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 指定客户--不参与业绩计算
	 * 
	 * @param page
	 * @param perfCycle
	 * @return
	 */
	public Page<Map<String, Object>> findBcyyjjsBySql(
			Page<Map<String, Object>> page, PerfCycle perfCycle) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from(select a.PK,a.PerfCycleNo,a.CustCode,b.Descr CustDescr,b.Address,b.SignDate, "
           +"a.IsCalcPerf,a.AchieveDate,a.Remarks,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog "  
           +"from tManualPerfCust a "
           +"inner join tCustomer b on a.CustCode=b.Code "
           +"where a.IsCalcPerf='0' and a.PerfCycleNo=?";
		list.add(perfCycle.getNo());
		if(StringUtils.isNotBlank(perfCycle.getAddress())){
			sql+=" and b.address like ?";
			list.add("%"+perfCycle.getAddress()+"%");
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ")a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ")a ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 查是否计算业绩
	 * 
	 * @param perfCycle
	 */
	public List<Map<String, Object>> checkIsCalcPerf(PerfCycle perfCycle) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select IsCalcPerf from tManualPerfCust where PerfCycleNo=? and CustCode=? ";
		list.add(perfCycle.getNo());
		list.add(perfCycle.getCustCode());
		if(perfCycle.getPk()!=null){
			sql+="and pk<>?";
			list.add(perfCycle.getPk());
		}
		return this.findBySql(sql, list.toArray());
	}
	/**
	 * 查所有客户状态,以逗号隔开
	 * 
	 * @param perfCycle
	 */
	public List<Map<String, Object>> findAllCustType() {
		String sql = "SELECT STUFF((SELECT ','+Code FROM  tCusttype for xml path('')),1,1,'') allCustType";
		return this.findBySql(sql, new Object[] {});
	}
	/**
	 * 干系人列表
	 * 
	 * @param page
	 * @param perfCycle
	 * @return
	 */
	public Page<Map<String, Object>> findGxrBySql(
			Page<Map<String, Object>> page, PerfCycle perfCycle) {
		List<Object> list = new ArrayList<Object>();
		String sql="";
		//非独立销售
		if("1".equals(perfCycle.getIsAddAllInfo())||(StringUtils.isBlank(perfCycle.getBaseChgNo())&&StringUtils.isBlank(perfCycle.getChgNo()))){	
			if ("A".equals(perfCycle.getM_umState())) {
					 sql = "select a.perfpk,a.role,b.Descr roledescr,a.empcode,c.NameChi empname,a.perfper, "
							+ "a.department1,d1.Desc2 dept1descr,a.department2,d2.Desc2 dept2descr,a.department3,d3.Desc2 dept3descr, "
							+ "a.leadercode,e.NameChi leadername,a.iscalcdeptperf,x1.NOTE iscalcdeptperfdescr,  "
							+ "a.lastupdate,a.iscalcpersonperf,x2.NOTE iscalcpersonperfdescr, "
							+ "a.busidrc,e2.NameChi busidrcdescr,a.custcode,'ADD'actionlog,'F' expired,cast("+perfCycle.getLastUpdatedBy()+" as nvarchar(20))lastupdatedby from "
							+ "(select case when a.LeaderCode=c.EmpCode then a.LeaderCode else d.EmpCode end LeaderCode, "// 判断下二级部门领导是否发生变化
							+ "case when a.LeaderCode=c.EmpCode then a.Department1 else e.Department1 end Department1, "
							+ "case when a.LeaderCode=c.EmpCode then a.Department2 else e.Department2 end Department2, "
							+ "a.PerfPK,a.CustCode,a.Role,a.EmpCode,a.PerfPer,a.Department3, "
							+ "a.IsCalcDeptPerf,a.LastUpdate,a.IsCalcPersonPerf,a.BusiDrc "
							+ "from(select 0 PerfPK, a.Code custCode, ps.Role, ps.EmpCode, ps.PerfPer, ps.Department1, "
							+ "   ps.Department2, ps.Department3, ps.LeaderCode, ps.IsCalcDeptPerf, "
							+ "   getdate() lastupdate, ps.IsCalcPersonPerf,f.EmpCode BusiDrc "
							+ "   from tCustomer a "
							+ "   inner join tPerformance p on a.PerfPK=p.PK "
							+ "   inner join tPerfStakeholder ps on ps.PerfPK=p.PK "
							+ "   left join tEmployee e on ps.EmpCode=e.Number "
							+ "   left join ( "
							+ "    select min(Number) EmpCode, Department3 from tEmpForPerf "
							+ "    where Position=(select qz from tXTCS x1 where  x1.ID='BUSIDRCCODE') and Department3<>'' "
							+ "    group by Department3 "
							+ "  ) f on f.Department3=e.Department3 "
							+ "  where a.Code=?)a "
							+ "  left join ( "
							+ "	select min(Number) EmpCode, Department1, Department2 from tEmpForPerf "
							+ "    where IsLead='1' and LeadLevel='1' "
							+ "    group by Department1, Department2 "
							+ " ) c on a.Department1=c.Department1 and a.Department2=c.Department2 "
							+ " left join tEmpForPerf e on a.EmpCode=e.Number "
							+ " left join ( "
							+ "    select min(Number) EmpCode, Department1, Department2 from tEmpForPerf "
							+ "    where IsLead='1' and LeadLevel='1' "
							+ "    group by Department1, Department2 "
							+ " ) d on e.Department2=d.Department2 and e.Department1 =d.Department1 "
							+ ")a "
							+ "inner join tCustomer d on d.Code=a.CustCode and d.Expired='F' "
							+ "left outer join tRoll b on b.Code=a.Role "
							+ "left outer join tEmployee c on c.Number=a.EmpCode "
							+ "left outer join tDepartment1 d1 on a.Department1=d1.Code "
							+ "left outer join tDepartment2 d2 on a.Department2=d2.Code "
							+ "left outer join tDepartment3 d3 on a.Department3=d3.Code "
							+ "left outer join tEmployee e on e.Number=a.LeaderCode  "
							+ "left outer join tXTDM x1 on x1.ID='YESNO' and x1.CBM=a.IsCalcDeptPerf "
							+ "left outer join tXTDM x2 on x2.ID='YESNO' and x2.CBM=a.IsCalcPersonPerf "
							+ "left outer join tEmployee e2 on e2.Number=a.BusiDrc  "
							+ "order by a.Role,a.empcode ";
					 list.add(perfCycle.getCustCode());
				}else{
					sql = "select a.perfpk,a.pk,a.role,b.Descr roledescr,a.empcode,c.NameChi empname,a.perfper, "
							+ "a.department1,d1.Desc2 dept1descr,a.department2,d2.Desc2 dept2descr,a.department3,d3.Desc2 dept3descr, "
							+ "a.leadercode,e.NameChi leadername,a.iscalcdeptperf,x1.NOTE iscalcdeptperfdescr,  "
							+ "a.lastupdate,a.lastupdatedby,a.expired,a.actionlog,a.remarks,a.iscalcpersonperf,x2.NOTE iscalcpersonperfdescr, "
							+ "a.busidrc,e2.NameChi busidrcdescr,a.custcode "
							+ "from tPerfStakeholder a   "
							+ "inner join tCustomer d on d.Code=a.CustCode and d.Expired='F' "
							+ "left outer join tRoll b on b.Code=a.Role "
							+ "left outer join tEmployee c on c.Number=a.EmpCode "
							+ "left outer join tDepartment1 d1 on a.Department1=d1.Code "
							+ "left outer join tDepartment2 d2 on a.Department2=d2.Code "
							+ "left outer join tDepartment3 d3 on a.Department3=d3.Code "
							+ "left outer join tEmployee e on e.Number=a.LeaderCode  "
							+ "left outer join tXTDM x1 on x1.ID='YESNO' and x1.CBM=a.IsCalcDeptPerf "
							+ "left outer join tXTDM x2 on x2.ID='YESNO' and x2.CBM=a.IsCalcPersonPerf "
							+ "left outer join tEmployee e2 on e2.Number=a.BusiDrc  "
							+ "where a.PerfPK=? order by a.Role,a.empcode";
					list.add(perfCycle.getPk());
			   }
		}else{
			 if(StringUtils.isNotBlank(perfCycle.getBaseChgNo())){
				 sql = " select * from ( "
					 + " select 0 perfpk,a.Code custcode,b.role,r.Descr roledescr,b.EmpCode empcode,e.NameChi empname, 1.0/c.num perfper,"
				     + " e.department1,d1.Desc2 dept1descr,e.department2,d2.Desc2 dept2descr,e.department3,d3.Desc2 dept3descr,g.EmpCode leadercode,e2.NameChi leadername," 
				     + " '1' iscalcdeptperf,'1' iscalcpersonperf,getdate() lastupdate,cast("+perfCycle.getLastUpdatedBy()+" as nvarchar(20))lastupdatedby,'F' expired," 
				     + " 'ADD' actionlog, "
					 + "  h.EmpCode busidrc,e3.NameChi busidrcdescr,'是' iscalcdeptperfdescr, '是' iscalcpersonperfdescr "
					 + " from tBaseItemChg bic "
					 + " inner join tCustomer a on a.code=bic.CustCode "  
					 + " inner join tBaseChgStakeholder b on b.BaseChgNo=bic.No "  
					 + " inner join (select BaseChgNo,Role,count(1) num from tBaseChgStakeholder group by BaseChgNo,Role) c on c.BaseChgNo=bic.no and c.Role=b.Role "
					 + " inner join tEmployee e on b.EmpCode=e.Number "
					 + " left join ( "
					 + "	select min(Number) EmpCode, Department1, Department2 from tEmpForPerf "
					 + "	where IsLead='1' and LeadLevel='1' and Expired='F' "
					 + "	group by Department1, Department2 "
					 + " ) g on g.Department1=e.Department1 and g.Department2=e.Department2 "
					 + " left join ( "
					 + "	select min(Number) EmpCode, Department3 from tEmpForPerf "
					 + "	where Position=(select qz from tXTCS x1 where  x1.ID='BUSIDRCCODE') and Department3<>'' and Expired='F' "
					 + "	group by Department3 "
					 + " ) h on h.Department3=e.Department3  "
					 + " left outer join tRoll r on r.Code=b.Role "
					 + " left outer join tDepartment1 d1 on e.Department1=d1.Code "
					 + " left outer join tDepartment2 d2 on e.Department1=d2.Code "
					 + " left outer join tDepartment3 d3 on e.Department1=d3.Code "
					 + " left outer join tEmployee e2 on e2.Number=g.EmpCode "
					 + " left outer join tEmployee e3 on e3.Number=h.EmpCode "
					 + " where b.Role='01' and bic.no=? and bic.IsAddAllInfo='0'  ) a order by a.Role,a.empcode ";	 	
				 list.add(perfCycle.getBaseChgNo());	
			 }else{
				 sql = " select * from ( "
						 + " select 0 perfpk,a.Code custcode,b.role,r.Descr roledescr,b.EmpCode empcode,e.NameChi empname, 1.0/c.num perfper,"
					     + " e.department1,d1.Desc2 dept1descr,e.department2,d2.Desc2 dept2descr,e.department3,d3.Desc2 dept3descr,g.EmpCode leadercode,e2.NameChi leadername," 
					     + " '1' iscalcdeptperf,'1' iscalcpersonperf,getdate() lastupdate,cast("+perfCycle.getLastUpdatedBy()+" as nvarchar(20))lastupdatedby,'F' expired," 
					     + " 'ADD' actionlog, "
						 + "  h.EmpCode busidrc,e3.NameChi busidrcdescr,'是' iscalcdeptperfdescr, '是' iscalcpersonperfdescr "
						 + " from tItemChg ic "
						 + " inner join tCustomer a on a.code=ic.CustCode "  
						 + " inner join tItemChgStakeholder b on b.ChgNo=ic.No "  
						 + " inner join (select ChgNo,Role,count(1) num from tItemChgStakeholder group by ChgNo,Role) c on c.ChgNo=ic.no and c.Role=b.Role "
						 + " inner join tEmployee e on b.EmpCode=e.Number "
						 + " left join ( "
						 + "	select min(Number) EmpCode, Department1, Department2 from tEmpForPerf "
						 + "	where IsLead='1' and LeadLevel='1' and Expired='F' "
						 + "	group by Department1, Department2 "
						 + " ) g on g.Department1=e.Department1 and g.Department2=e.Department2 "
						 + " left join ( "
						 + "	select min(Number) EmpCode, Department3 from tEmpForPerf "
						 + "	where Position=(select qz from tXTCS x1 where  x1.ID='BUSIDRCCODE') and Department3<>'' and Expired='F' "
						 + "	group by Department3 "
						 + " ) h on h.Department3=e.Department3  "
						 + " left outer join tRoll r on r.Code=b.Role "
						 + " left outer join tDepartment1 d1 on e.Department1=d1.Code "
						 + " left outer join tDepartment2 d2 on e.Department1=d2.Code "
						 + " left outer join tDepartment3 d3 on e.Department1=d3.Code "
						 + " left outer join tEmployee e2 on e2.Number=g.EmpCode "
						 + " left outer join tEmployee e3 on e3.Number=h.EmpCode "
						 + " where b.Role='01' and ic.no=? and ic.IsAddAllInfo='0' ) a  order by a.Role,a.empcode ";	 	
					 list.add(perfCycle.getChgNo());	  
			 }
		}
		page.setResult(findListBySql(sql, list.toArray()));
		page.setTotalCount(page.getResult().size());
		return page;
	}
	/**
	 * 干系人修改历史列表
	 * 
	 * @param page
	 * @param perfCycle
	 * @return
	 */
	public Page<Map<String, Object>> findGxrxglsBySql(
			Page<Map<String, Object>> page, PerfCycle perfCycle) {
		List<Object> list = new ArrayList<Object>();
		
		String sql = "select a.pk,a.OperType,g.NOTE OperTypeDescr, "
           +"a.Role,b.Descr RoleDescr,a.EmpCode,c.NameChi EmpName, "
           +"a.OldRole,e.Descr OldRoleDescr,a.OldEmpCode,f.NameChi OldEmpName, "
           +"a.CustCode,d.Descr CustName, "
           +"a.LastUpdate,a.LastUpdatedBy,a.Expired, "
           +"a.ActionLog,d.Address from tCustStakeholderHis a "  
           +"left outer join tRoll b on b.Code=a.Role " 
           +"left outer join tEmployee c on c.Number=a.EmpCode " 
           +"left outer join tCustomer d on d.Code=a.CustCode  "
           +"left outer join tRoll e on e.Code=a.OldRole " 
           +"left outer join tEmployee f on f.Number=a.OldEmpCode  "
           +"left outer join tXTDM g on a.OperType=g.CBM and g.ID='CUSTSTHHISTYPE' "
           +"where (Role in ('00','01','24') or OldRole in ('00','01','24')) and a.CustCode=? "
           +"order by a.LastUpdate desc";
		list.add(perfCycle.getCustCode());
		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 基础增减列表
	 * 
	 * @param page
	 * @param perfCycle
	 * @return
	 */
	public Page<Map<String, Object>> findJczjBySql(
			Page<Map<String, Object>> page, PerfCycle perfCycle) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select a.No,a.CustCode,c.Descr CustomerDescr,c.Address,c.Area,a.Status, "
                   +"d.NOTE StatusDescr,a.Date,a.BefAmount,a.DiscAmount,a.Amount,a.Remarks, "
                   +"a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,c.DocumentNo,c.CustType,a.ManageFee,isnull(dbo.fGetBaseChgStakeholder(a.No,'01',default),'') BaseChgStakeholder " 
                   +"from tBaseItemChg a " 
                   +"left outer join tCustomer c on a.CustCode=c.Code " 
                   +"left outer join tXTDM d on a.Status=d.CBM and d.ID='ITEMCHGSTATUS' "
                   +"where 1=1 ";
		if(perfCycle.getPk()!=null){
			sql+=" and a.PerfPK=?";
			list.add(perfCycle.getPk());
		}else{
			sql+=" and a.status='2' and a.perfPk is null and a.CustCode=?";
			list.add(perfCycle.getCustCode());
		}
		if(StringUtils.isNotBlank(perfCycle.getChgNos())){
			sql += " and a.No not in " + "('"+perfCycle.getChgNos().replace(",", "','" )+ "')";
		}
		if (perfCycle.getDateFrom() != null) {
			sql += " and a.Date >= ?";
			list.add(perfCycle.getDateFrom());
		}
		if (perfCycle.getDateTo() != null) {
			sql += " and a.Date <DATEADD(d,1,?)";
			list.add(perfCycle.getDateTo());
		}
		if(StringUtils.isNotBlank(perfCycle.getChgNo())){
			sql+=" and a.No=?";
			list.add(perfCycle.getChgNo());
		}
		if(StringUtils.isNotBlank(perfCycle.getIsAddAllInfo())){
			sql+=" and a.IsAddAllInfo=?";
			list.add(perfCycle.getIsAddAllInfo());
			if ("0".equals(perfCycle.getIsAddAllInfo())){ // 非常规变更的材料增减 只算现场设计所 by zjf20200801
				sql+=" and exists(select 1 from tBaseChgStakeholder in_a " 
					 + " left join tEmpForPerf in_b on in_a.EmpCode=in_b.Number "
					 + " left join tDepartment1 in_c on in_c.Code=in_b.Department1 "
					 + " left join tDepartment2 in_d on in_d.Code=in_b.Department2 " 
					 + " where in_a.BaseChgNo=a.No and (in_c.DepType='8' or in_d.DepType='8'))  " ;		
			}
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 材料增减列表
	 * 
	 * @param page
	 * @param perfCycle
	 * @return
	 */
	public Page<Map<String, Object>> findClzjBySql(
			Page<Map<String, Object>> page, PerfCycle perfCycle) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select a.No,a.IsService,e.NOTE IsServiceDescr,a.ItemType1,b.Descr ItemType1Descr,a.CustCode,c.Descr CustomerDescr,c.Address,c.Area,a.Status, "
				+ "d.NOTE StatusDescr,a.Date,a.BefAmount,a.DiscAmount,a.Amount,a.Remarks, "
				+ "a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,a.AppCZY,a.ConfirmCZY,a.ConfirmDate,g.ZWXM AppCZYDescr,h.ZWXM ConfirmCZYDescr ,  "
				+ "c.ContainMain,c.ContainSoft,c.ContainInt,c.ContainMainServ,c.DocumentNo,c.CustType,f.NOTE CustTypeDescr,a.ManageFee, "
				+ "a.IsCupboard,i.NOTE IsCupboardDescr,a.discCost,isnull(dbo.fGetItemChgStakeholder(a.No,'01',default),'') ChgStakeholder "
				+ "from  tItemChg a "
				+ "left outer join tItemType1 b on ltrim(rtrim(a.ItemType1))=ltrim(rtrim(b.Code)) "
				+ "left outer join tCustomer c on a.CustCode=c.Code "
				+ "left outer join tXTDM d on a.Status=d.CBM and d.ID='ITEMCHGSTATUS' "
				+ "left outer join tXTDM e on a.IsService=e.IBM and e.ID='YESNO' "
				+ "left outer join tXTDM f on c.CustType = f.CBM and f.ID = 'CUSTTYPE' "
				+ "left outer join tCZYBM g  on g.czybh=a.AppCZY "
				+ "left outer join tCZYBM h on h.czybh=a.ConfirmCZY  "
				+ "left outer join tXTDM i on a.IsCupboard=i.CBM and i.ID='YESNO' "
				+ "where 1=1  ";
		if(perfCycle.getPk()!=null){
			sql+=" and a.PerfPK=?";
			list.add(perfCycle.getPk());
		}else{
			sql+=" and a.status='2' and a.perfPk is null and a.CustCode=? ";
			list.add(perfCycle.getCustCode());
		}
		if(StringUtils.isNotBlank(perfCycle.getChgNos())){
			sql += " and a.No not in " + "('"+perfCycle.getChgNos().replace(",", "','" )+ "')";
		}
		if (perfCycle.getDateFrom() != null) {
			sql += " and a.Date >= ?";
			list.add(perfCycle.getDateFrom());
		}
		if (perfCycle.getDateTo() != null) {
			sql += " and a.Date <DATEADD(d,1,?)";
			list.add(perfCycle.getDateTo());
		}
		if(StringUtils.isNotBlank(perfCycle.getChgNo())){
			sql+=" and a.No=?";
			list.add(perfCycle.getChgNo());
		}
		if(StringUtils.isNotBlank(perfCycle.getIsAddAllInfo())){
			sql+=" and a.IsAddAllInfo=?";
			list.add(perfCycle.getIsAddAllInfo());
			if ("0".equals(perfCycle.getIsAddAllInfo())){ // 非常规变更的材料增减 只算现场设计所 by zjf20200801
				sql+=" and exists(select 1 from tItemChgStakeholder in_a " 
					 + " left join tEmpForPerf in_b on in_a.EmpCode=in_b.Number "
					 + " left join tDepartment1 in_c on in_c.Code=in_b.Department1 "
					 + " left join tDepartment2 in_d on in_d.Code=in_b.Department2 " 
					 + " where in_a.ChgNo=a.No and (in_c.DepType='8' or in_d.DepType='8'))  " ;		
			}
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 合同费用增减列表
	 * 
	 * @param page
	 * @param perfCycle
	 * @return
	 */
	public Page<Map<String, Object>> findHtfyzjBySql(
			Page<Map<String, Object>> page, PerfCycle perfCycle) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select a.Code,a.Descr,a.DocumentNO,a.Address,a.Area,b.PK,b.ChgType,d.Note ChgTypeDescr,b.ChgAmount,b.Status, "
				+ "d1.Note StatusDescr,b.ConfirmCZY,c.NameChi ConfirmCZYDescr,b.ConfirmDate, "
				+ "b.AppCZY,c1.NameChi AppCZYDescr,b.Date,b.Remarks,b.LastUpdatedBy,c2.NameChi LastUpdatedByDescr,b.LastUpdate,b.Expired, "
				+ "b.ActionLog,b.ChgNo,b.ItemType1,b.IsService,b.IsCupboard, "
				+ " case when bic.no is not null then isnull(dbo.fGetBaseChgStakeholder(bic.No,'01',default),'') when ic.no is not null then isnull(dbo.fGetItemChgStakeholder(ic.No,'01',default),'') else '' end ChgStakeholder "
				+ "from tConFeeChg b "
				+ "left outer join tCustomer a on b.CustCode=a.Code "
				+ "left outer join tEmployee c on b.ConfirmCZY=c.Number "
				+ "left outer join tEmployee c1 on b.AppCZY=c1.Number "
				+ "left outer join tEmployee c2 on b.LastUpdatedBy=c2.Number  "
				+ "left outer join tXTDM d on b.ChgType=d.CBM and d.ID='CHGTYPE' "
				+ "left outer join tXTDM d1 on b.Status=d1.CBM and d1.ID='CHGSTATUS' "
				+ "left join tBaseItemChg bic on b.ChgNo=bic.No "
				+ "left join tItemChg ic on b.ChgNo=ic.No "
				+ "where 1=1  ";
		if(perfCycle.getPk()!=null){
			sql+=" and b.PerfPK=?";
			list.add(perfCycle.getPk());
		}else{
			sql+=" and b.status='CONFIRMED' and b.perfPk is null and b.CustCode=?";
			list.add(perfCycle.getCustCode());
		}
		if(StringUtils.isNotBlank(perfCycle.getChgNos())){
			sql += " and b.pk not in " + "('"+perfCycle.getChgNos().replace(",", "','" )+ "')";
		}
		if(StringUtils.isNotBlank(perfCycle.getChgType())){
			sql+=" and b.ChgType=?";
			list.add(perfCycle.getChgType());
		}
		if(StringUtils.isNotBlank(perfCycle.getIsAddAllInfo())){
			sql+=" and case when bic.no is not null then bic.IsAddAllInfo when ic.no is not null then ic.IsAddAllInfo else '1' end =?";
			list.add(perfCycle.getIsAddAllInfo());
			if ("0".equals(perfCycle.getIsAddAllInfo())){ // 非常规变更的材料增减 只算现场设计所 by zjf20200801
				sql+=" and ( exists(select 1 from tItemChgStakeholder in_a " 
					+ " left join tEmpForPerf in_b on in_a.EmpCode=in_b.Number "
					+ " left join tDepartment1 in_c on in_c.Code=in_b.Department1  "
					+ " left join tDepartment2 in_d on in_d.Code=in_b.Department2  "
					+ " where in_a.ChgNo=b.ChgNo and (in_c.DepType='8' or in_d.DepType='8') )" 
					+ " or exists(select 1 from dbo.tBaseChgStakeholder in_a " //非常规变更的基础增减只算现场设计所 by zjf20200828
					+ " left join tEmpForPerf in_b on in_a.EmpCode=in_b.Number "
					+ " left join tDepartment1 in_c on in_c.Code=in_b.Department1 "
					+ " left join tDepartment2 in_d on in_d.Code=in_b.Department2 "
					+ " where in_a.BaseChgNo=b.ChgNo and (in_c.DepType='8' or in_d.DepType='8') ) ) ";
			}		
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 付款信息列表
	 * 
	 * @param page
	 * @param perfCycle
	 * @return
	 */
	public Page<Map<String, Object>> findFkxxBySql(
			Page<Map<String, Object>> page, PerfCycle perfCycle) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select a.Amount,a.Date,a.Remarks,b.DocumentNo,a.AddDate "
				+ "from tCustPay a  "
				+ "left join tPayCheckOut b on a.PayCheckOutNo=b.No "
				+ "where a.CustCode=?";
		list.add(perfCycle.getCustCode());
		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 已算材料业绩
	 * 
	 * @param perfCycle
	 */
	public Map<String, Object> getAlreadyMaterPerf(PerfCycle perfCycle) {
		String sql = "select isnull(b.MaterPerf,0)+isnull(c.MaterPerf,0) AlreadyMaterPerf from tCustomer a "
				+ "left join tPerformance b on a.PerfPK=b.PK "
				+ "left join (select a.CustCode, sum(MaterPerf) MaterPerf from tPerformance a "
				+ "	where a.CustCode=? and a.Type='3' group by a.CustCode "
				+ ") c on a.Code=c.CustCode where a.Code=?";
		List<Map<String, Object>>list=this.findBySql(sql, new Object[] { perfCycle.getCustCode(),perfCycle.getCustCode()});
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("AlreadyMaterPerf", 0);
		if(list.size()>0 && list !=null){
			return list.get(0);
		}
		return map;
	}
	/**
	 * 付款方式、每平米材料业绩、面积
	 * 
	 * @param perfCycle
	 */
	public Map<String, Object> getPayType(PerfCycle perfCycle) {
		String sql = "select b.BasePerfPer, isnull(c.PerfExpr,b.PerfExpr) PerfExpr, isnull(c.PerfExprRemarks,b.PerfExprRemarks) PerfExprRemarks,a.PayType, b.MaxMaterPerfPer, a.Area,a.SetDate,a.SignDate,"
				+ " isnull(c.ChgPerfExpr,b.ChgPerfExpr) ChgPerfExpr,isnull(c.ChgPerfExprRemarks,b.ChgPerfExprRemarks) ChgPerfExprRemarks,a.PerfMarkup,a.Tax,a.BaseFee_Comp,a.BaseFee_Dirct  "
				+ " from tCustomer a "
				+ " inner join tCusttype b on a.CustType=b.Code "
				+ " left join (select custcode,min(pk) minpk from tAgainSign group by CustCode) AsMin on AsMin.custcode =a.Code "
				+ " left join tAgainSign Asn on AsMin.minpk=Asn.pk " 
				+ " left join tCustTypePerfExpr c on a.CustType=c.CustType " 
				+ "    and coalesce(dbo.fGetPerfAchieveDateMin(a.Code),Asn.SignDate,a.SignDate)>=c.BeginDate" 
				+ "	          and coalesce(dbo.fGetPerfAchieveDateMin(a.Code),Asn.SignDate,a.SignDate)<=c.EndDate and c.Expired='F' "
				+ " where a.Code=?";
		return this.findBySql(sql, new Object[] { perfCycle.getCustCode() }).get(0);
	}
	/**
	 * 客户原业绩的实际材料业绩
	 * 
	 * @param perfCycle
	 */
	public Map<String, Object> getRegRealMaterPerf(PerfCycle perfCycle) {
		String sql = "select b.RealMaterPerf RegRealMaterPerf from tCustomer a "
          +"inner join tPerformance b on a.PerfPK=b.PK"
          +" where a.Code=?";
		List<Map<String, Object>>list=this.findBySql(sql, new Object[] { perfCycle.getCustCode()});
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("RegRealMaterPerf", 0);
		if(list.size()>0 && list !=null){
			return list.get(0);
		}
		return map;
	}
	/**
	 * 客户的增减实际业绩汇总
	 * 
	 * @param perfCycle
	 */
	public Map<String, Object> getSumChgRealMaterPerf(PerfCycle perfCycle) {
		String sql = "select sum(RealMaterPerf) SumChgRealMaterPerf from tPerformance a where a.Type='3' and a.CustCode=? and a.PK<>?";
		List<Map<String, Object>>list=this.findBySql(sql, new Object[] { perfCycle.getCustCode(),perfCycle.getPk()});
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("SumChgRealMaterPerf", 0);
		if(list.get(0).get("SumChgRealMaterPerf")!=null ){
			return list.get(0);
		}
		return map;
	}
	/**
	 * 是否计算基础优惠
	 * 
	 * @param perfCycle
	 */
	public Map<String, Object> getIsCalcBaseDisc(PerfCycle perfCycle) {
		String sql = "select b.IsCalcBaseDisc from tCustomer a "
				+ "inner join tCusttype b on a.CustType=b.Code "
				+ "where a.Code=?";
		List<Map<String, Object>>list=this.findBySql(sql, new Object[] { perfCycle.getCustCode()});
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("IsCalcBaseDisc", "1");
		if(list.size()>0 && list !=null){
			return list.get(0);
		}
		return map;
	}
	/**
	 * 原业绩达标时间
	 * 
	 * @param perfCycle
	 */
	public Map<String, Object> getRegAchieveDate(PerfCycle perfCycle) {
		String sql = "select b.AchieveDate RegAchieveDate from tCustomer a "
				+ "inner join tPerformance b on a.PerfPK=b.PK "
				+ "where a.Code=?";
		List<Map<String, Object>>list=this.findBySql(sql, new Object[] { perfCycle.getCustCode()});
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("RegAchieveDate", "9999-12-31");
		if(list.size()>0 && list !=null){
			return list.get(0);
		}
		return map;
	}
	/**
	 * 原业绩pk
	 * 
	 * @param perfCycle
	 */
	public List<Map<String, Object>> getRegPerfPK(PerfCycle perfCycle) {
		String sql = "select PerfPK from tCustomer where Code=?";
		return this.findBySql(sql, new Object[] { perfCycle.getCustCode()});
	}
	/**
	 * 查看原业绩
	 * 
	 * @param perfCycle
	 */
	public List<Map<String, Object>> getRegPerformance(PerfCycle perfCycle) {
		String sql = "select a.setAdd,a.pk,a.perfCycleNo,rTrim(a.type)type,x1.NOTE typeDescr,a.custCode,b.Descr custDescr,b.address, "
				+ "a.quantity,a.area,a.designFee,a.basePlan,a.mainPlan,a.integratePlan,a.cupboardPlan, "
				+ "a.softPlan,a.mainServPlan,a.baseDisc,a.contractFee,a.longFee,a.softFee_Furniture,a.perfAmount, "
				+ "a.firstPay,a.mustReceive,a.realReceive,a.achieveDate,a.mainProPer, "
				+ "a.signDate,a.setDate,a.setMinus,a.manageFee_Base,a.manageFee_InSet,a.manageFee_Base-a.manageFee_InSet manageFee_OutSet,a.manageFee_Main,a.manageFee_Int,a.manageFee_Serv, "
				+ "a.manageFee_Soft,a.manageFee_Cup,a.remarks,a.isModified,x2.NOTE isModifiedDescr, "
				+ "a.dataType,x3.NOTE dataTypeDescr,a.lastUpdate,a.lastUpdatedBy,a.expired,a.actionLog, "
				+ "cast(dbo.fGetPerfEmpNameChi(a.PK,'01') as nvarchar(100)) businessManDescr,  "
				+ "cast(dbo.fGetPerfLeaderNameChi(a.PK, '01') as nvarchar(100)) businessManLeader,  "
				+ "cast(dbo.fGetPerfEmpNameChi(a.PK,'00') as nvarchar(100)) designManDescr, "
				+ "cast(dbo.fGetPerfLeaderNameChi(a.PK, '00') as nvarchar(100)) designManLeader, "
				+ "cast(dbo.fGetPerfEmpNameChi(a.PK,'24') as nvarchar(100)) againManDescr,  "
				+ "a.crtDate,b.custType,c.Desc1 custTypeDescr,a.contractFee+a.designFee+a.Tax contractAndDesignFee, a.contractFee+a.Tax contractAndTax,  "
				+ "a.ManageFee_Base+a.ManageFee_Main+a.ManageFee_Serv+a.ManageFee_Soft+a.ManageFee_Int+a.ManageFee_Cup manageFee_Sum,isnull(a.regPerfPk,b.perfPk)regPerfPk, "
				+ "a.tileStatus,x4.NOTE tileStatusDescr,a.bathStatus,x5.NOTE bathStatusDescr,a.tileDeduction,a.bathDeduction,a.mainDeduction,a.recalPerf,a.perfPerc,a.perfDisc, "
				+ "a.isChgHolder,X6.NOTE isChgHolderDescr,a.documentNo,a.isChecked,x7.Note isCheckedDescr,b.discRemark,a.isCalPkPerf, "
				+ "x8.NOTE isCalPkPerfDescr,b.discRemark,a.markup, "
				+ "a.softTokenAmount,a.baseDeduction,a.itemDeduction, "
				+ "a.basePerfPer,round(a.PerfAmount-a.BaseDeduction-a.ItemDeduction-a.SoftTokenAmount,2) realPerfAmount, "
				+ "a.marketFund,a.BasePlan-a.BaseDisc-a.MarketFund basePerfRadix,a.realMaterPerf,a.maxMaterPerf,a.alreadyMaterPerf,a.materPerf,c.maxMaterPerfPer,b.payType, "
				+ "a.perfExpr, a.perfExprRemarks, a.gift,a.perfMarkup,a.tax,b.baseFee_Comp,b.baseFee_Dirct, "
				+ "case when CHARINDEX('@PerfMarkup@',a.PerfExpr)>0 then case when isnull(a.perfMarkup,'')<>'' and a.perfMarkup<>0 then a.recalperf/a.perfMarkup else a.recalperf end else a.recalperf end befMarkupPerf "
				+ ",a.isAddAllInfo,rtrim(a.payeeCode) payeeCode, a.basePersonalPlan,a.manageFee_basePersonal, a.woodPlan, a.manageFee_wood "
				+ "from tPerformance a "
				+ "inner join tCustomer b on a.CustCode=b.Code "
				+ "left join tCusttype c on b.CustType=c.Code "
				+ "left join tXTDM x1 on a.Type=x1.CBM and x1.ID='PERFTYPE' "
				+ "left join tXTDM x2 on a.IsModified=x2.CBM and x2.ID='YESNO' "
				+ "left join tXTDM x3 on a.DataType=x3.CBM and x3.ID='PERFDATATYPE' "
				+ "left join tXTDM x4 on a.TileStatus=x4.CBM and x4.ID='MaterialStatus' "
				+ "left join tXTDM x5 on a.BathStatus=x5.CBM and x5.ID='MaterialStatus' "
				+ "left join tXTDM x6 on a.IsChgHolder=x6.CBM and x6.ID='YESNO' "
				+ "left join tXTDM x7 on a.IsChecked=x7.CBM and x7.ID='YESNO' "
				+ "left join tXTDM x8 on a.IsCalPkPerf=x8.CBM and x8.ID='YESNO' "
				+ "where PK=?";
		
		return this.findBySql(sql, new Object[] { perfCycle.getRegPerfPk() });
	}
	/**
	 * 导入原业绩列表
	 * 
	 * @param page
	 * @param perfCycle
	 * @return
	 */
	public Page<Map<String, Object>> findYyjBySql(
			Page<Map<String, Object>> page, PerfCycle perfCycle) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select (select in_b.DocumentNo from (select max(pk) pk from tPerformance ) in_a " +
				" left join tperformance in_b on in_b.PK = in_a.pk" +
				" where in_b.CustCode= a.CustCode ) PerfDocumentNo,a.SetAdd PerfSetAdd,a.PK,a.Tax,a.PerfCycleNo,a.Type,x1.NOTE TypeDescr,a.CustCode,b.Descr CustDescr,b.Address, "
				+ "a.Quantity,a.Area,a.DesignFee,a.BasePlan,a.MainPlan,a.IntegratePlan,a.CupboardPlan, "
				+ "a.SoftPlan,a.MainServPlan,a.BaseDisc,a.ContractFee,a.LongFee,a.SoftFee_Furniture,a.PerfAmount, "
				+ "a.FirstPay,a.MustReceive,a.RealReceive,a.AchieveDate,a.MainProPer, "
				+ "a.SignDate,a.SetDate,a.SetMinus,a.ManageFee_Base,a.ManageFee_InSet,a.ManageFee_Base-a.ManageFee_InSet ManageFee_OutSet,a.ManageFee_Main,a.ManageFee_Int,a.ManageFee_Serv, "
				+ "a.ManageFee_Soft,a.ManageFee_Cup,a.Remarks,a.IsModified,x2.NOTE IsModifiedDescr, "
				+ "a.DataType,x3.NOTE DataTypeDescr,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog, "
				+ "cast(dbo.fGetPerfEmpNameChi(a.PK,'01') as nvarchar(100)) BusinessManDescr,  "
				+ "cast(dbo.fGetPerfLeaderNameChi(a.PK, '01') as nvarchar(100)) BusinessManLeader,  "
				+ "cast(dbo.fGetPerfEmpNameChi(a.PK,'00') as nvarchar(100)) DesignManDescr, "
				+ "cast(dbo.fGetPerfLeaderNameChi(a.PK, '00') as nvarchar(100)) DesignManLeader, "
				+ "cast(dbo.fGetPerfEmpNameChi(a.PK,'24') as nvarchar(100)) AgainManDescr,  "
				+ "a.CrtDate,b.CustType,c.Desc1 CustTypeDescr,a.ContractFee+a.DesignFee+a.Tax contractAndDesignFee,  "
				+ "a.ManageFee_Base+a.ManageFee_Main+a.ManageFee_Serv+a.ManageFee_Soft+a.ManageFee_Int+a.ManageFee_Cup ManageFee_Sum,a.RegPerfPk, "
				+ "a.TileStatus,x4.NOTE TileStatusDescr,a.BathStatus,x5.NOTE BathStatusDescr,a.TileDeduction,a.BathDeduction,a.MainDeduction,a.RecalPerf,a.PerfPerc,a.PerfDisc, "
				+ "a.IsChgHolder,X6.NOTE IsChgHolderDescr,b.DocumentNo,a.IsChecked,x7.Note IsCheckedDescr,a.IsCalPkPerf, "
				+ "x8.NOTE IsCalPkPerfDescr,b.DiscRemark,a.Markup, "
				+ "(case when a.type in ('3','4','5') and a.RegPerfPk is not null  then cast(dbo.fGetPerfEmpNameChi(a.RegPerfPk,'01') as nvarchar(100)) else '' end) oldBusinessManDescr, "
				+ "(case when a.type in ('3','4','5') and a.RegPerfPk is not null  then cast(dbo.fGetPerfEmpNameChi(a.RegPerfPk,'00') as nvarchar(100)) else '' end) oldDesignManDescr, "
				+ "(case when a.type in ('3','4','5') and a.RegPerfPk is not null  then cast(dbo.fGetPerfDept2Descr(a.RegPerfPk,'01') as nvarchar(100)) else '' end) oldBusinessManDescrDep, "
				+ "(case when a.type in ('3','4','5') and a.RegPerfPk is not null  then cast(dbo.fGetPerfDept2Descr(a.RegPerfPk,'00') as nvarchar(100)) else '' end) oldDesignManDescrDep, "
				+ "cast(dbo.fGetPerfDept2Descr(a.PK,'01') as nvarchar(100)) BusinessManDeptDescr, "
				+ "cast(dbo.fGetPerfDept2Descr(a.PK,'00') as nvarchar(100)) DesignManDeptDescr, "
				+ "cast(dbo.fGetPerfLeaderNameChi(a.RegPerfPk, '01') as nvarchar(100)) OldBusinessManLeader, "
				+ "cast(dbo.fGetPerfLeaderNameChi(a.RegPerfPk, '00') as nvarchar(100)) OldDesignManLeader, "
				+ "a.SoftTokenAmount,a.BaseDeduction,a.ItemDeduction,cast(dbo.fGetPerfBusiDrcNameChi(a.PK) as nvarchar(100)) BusiDrcDescr,dbo.fGetPerfBusiDrcNameChi(a.RegPerfPk) OldBusiDrcDescr, "
				+ "a.BasePerfPer,round(a.PerfAmount-a.BaseDeduction-a.ItemDeduction-a.SoftTokenAmount,2) RealPerfAmount, "
				+ "cast(dbo.fGetEmpNameChi(a.CustCode,'63') as nvarchar(100)) SceneDesignDescr, cast(dbo.fGetEmpNameChi(a.CustCode,'64') as nvarchar(100)) DeepenDesignDescr, "
				+ "a.MarketFund,a.BasePlan-a.BaseDisc-a.MarketFund BasePerfRadix,  "
				+ "case when exists(select 1 from tPerfStakeholder in_a where a.pk=in_a.PerfPK and in_a.Role='01' and in_a.IsCalcPersonPerf='1') and "
				+ "          not exists(select 1 from tPerfStakeholder in_a where a.pk=in_a.PerfPK and in_a.Role='01' and in_a.IsCalcPersonPerf='0') then '是' "
				+ "     when exists(select 1 from tPerfStakeholder in_a where a.pk=in_a.PerfPK and in_a.Role='01' and in_a.IsCalcPersonPerf='0') and "
				+ "          not exists(select 1 from tPerfStakeholder in_a where a.pk=in_a.PerfPK and in_a.Role='01' and in_a.IsCalcPersonPerf='1') then '否' "
				+ "    else '' end IsCalBusiManPerfDescr, "
				+ "case when exists(select 1 from tPerfStakeholder in_a where a.pk=in_a.PerfPK and in_a.Role='00' and in_a.IsCalcPersonPerf='1') and "
				+ "         not exists(select 1 from tPerfStakeholder in_a where a.pk=in_a.PerfPK and in_a.Role='00' and in_a.IsCalcPersonPerf='0') then '是' "
				+ "    when exists(select 1 from tPerfStakeholder in_a where a.pk=in_a.PerfPK and in_a.Role='00' and in_a.IsCalcPersonPerf='0') and "
				+ "         not exists(select 1 from tPerfStakeholder in_a where a.pk=in_a.PerfPK and in_a.Role='00' and in_a.IsCalcPersonPerf='1') then '否' "
				+ "    else '' end IsCalDesiManPerfDescr, "
				+ "case when exists(select 1 from tPerfStakeholder in_a where a.pk=in_a.PerfPK and in_a.Role='24' and in_a.IsCalcPersonPerf='1') and "
				+ "        not exists(select 1 from tPerfStakeholder in_a where a.pk=in_a.PerfPK and in_a.Role='24' and in_a.IsCalcPersonPerf='0') then '是' "
				+ "   when exists(select 1 from tPerfStakeholder in_a where a.pk=in_a.PerfPK and in_a.Role='24' and in_a.IsCalcPersonPerf='0') and "
				+ "       not exists(select 1 from tPerfStakeholder in_a where a.pk=in_a.PerfPK and in_a.Role='24' and in_a.IsCalcPersonPerf='1') then '否' "
				+ "  else '' end IsCalAgainManPerfDescr, "
				+ "case when exists(select 1 from tPerfStakeholder in_a where a.pk=in_a.PerfPK and in_a.Role='01' and in_a.IsCalcDeptPerf='1') and "
				+ "        not exists(select 1 from tPerfStakeholder in_a where a.pk=in_a.PerfPK and in_a.Role='01' and in_a.IsCalcDeptPerf='0') then '是' "
				+ "  when exists(select 1 from tPerfStakeholder in_a where a.pk=in_a.PerfPK and in_a.Role='01' and in_a.IsCalcDeptPerf='0') and "
				+ "       not exists(select 1 from tPerfStakeholder in_a where a.pk=in_a.PerfPK and in_a.Role='01' and in_a.IsCalcDeptPerf='1') then '否' "
				+ "  else '' end IsCalBusiDeptPerfDescr, "
				+ "case when exists(select 1 from tPerfStakeholder in_a where a.pk=in_a.PerfPK and in_a.Role='00' and in_a.IsCalcDeptPerf='1') and "
				+ "       not exists(select 1 from tPerfStakeholder in_a where a.pk=in_a.PerfPK and in_a.Role='00' and in_a.IsCalcDeptPerf='0') then '是' "
				+ "  when exists(select 1 from tPerfStakeholder in_a where a.pk=in_a.PerfPK and in_a.Role='00' and in_a.IsCalcDeptPerf='0') and "
				+ "      not exists(select 1 from tPerfStakeholder in_a where a.pk=in_a.PerfPK and in_a.Role='00' and in_a.IsCalcDeptPerf='1') then '否' "
				+ " else '' end IsCalDesiDeptPerfDescr, "
				+ "case when exists(select 1 from tPerfStakeholder in_a where a.pk=in_a.PerfPK and in_a.Role='24' and in_a.IsCalcDeptPerf='1') and "
				+ "      not exists(select 1 from tPerfStakeholder in_a where a.pk=in_a.PerfPK and in_a.Role='24' and in_a.IsCalcDeptPerf='0') then '是' "
				+ " when exists(select 1 from tPerfStakeholder in_a where a.pk=in_a.PerfPK and in_a.Role='24' and in_a.IsCalcDeptPerf='0') and "
				+ "      not exists(select 1 from tPerfStakeholder in_a where a.pk=in_a.PerfPK and in_a.Role='24' and in_a.IsCalcDeptPerf='1') then '否' "
				+ " else '' end IsCalAgainDeptPerfDescr, "
				+ "a.RealMaterPerf,a.MaxMaterPerf,a.AlreadyMaterPerf,a.MaterPerf,x9.NOTE PayTypeDescr,b.PayType,c.MaxMaterPerfPer,a.SetAdd,a.Gift,  "
				+ "e.Y OldYear,e.M OldMonth,a.PerfExpr,f.Y,f.M "
				+ "from tPerformance main "
				+ "inner join tPerformance a on main.PK=a.PK "
				+ "inner join tCustomer b on a.CustCode=b.Code "
				+ "left join tCusttype c on b.CustType=c.Code "
				+ "left join tXTDM x1 on a.Type=x1.CBM and x1.ID='PERFTYPE' "
				+ "left join tXTDM x2 on a.IsModified=x2.CBM and x2.ID='YESNO' "
				+ "left join tXTDM x3 on a.DataType=x3.CBM and x3.ID='PERFDATATYPE' "
				+ "left join tXTDM x4 on a.TileStatus=x4.CBM and x4.ID='MaterialStatus' "
				+ "left join tXTDM x5 on a.BathStatus=x5.CBM and x5.ID='MaterialStatus' "
				+ "left join tXTDM x6 on a.IsChgHolder=x6.CBM and x6.ID='YESNO' "
				+ "left join tXTDM x7 on a.IsChecked=x7.CBM and x7.ID='YESNO' "
				+ "left join tXTDM x8 on a.IsCalPkPerf=x8.CBM and x8.ID='YESNO' "
				+ "left join tXTDM x9 on b.PayType=x9.CBM and x9.ID='TIMEPAYTYPE' "
				+ "left join tPerformance d on a.RegPerfPK=d.PK "
				+ "left join tPerfCycle e on d.PerfCycleNo=e.No "
				+ "left join tPerfCycle f on a.PerfCycleNo=f.no "
				+ "where a.CustCode=? order by main.PK desc";
		list.add(perfCycle.getCustCode());
		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 原业绩导入的数据
	 * 
	 * @param perfCycle
	 */
	public List<Map<String, Object>> getRegImport(PerfCycle perfCycle) {
		String sql = 
				  "select b.MainProper, b.DocumentNo PerfDocumentNo, b.Area, a.* "
				+ "from (" 
				+ "  select max(PK) PK, -sum(SetAdd) SetAdd,-sum(DesignFee) DesignFee, -sum(BasePlan) BasePlan, -sum(ManageFee) ManageFee, "
				+ "  -sum(MainPlan) MainPlan, -sum(IntegratePlan) IntegratePlan, -sum(CupboardPlan) CupboardPlan, "
				+ "  -sum(SoftPlan) SoftPlan, -sum(MainServPlan) MainServPlan, -sum(BaseDisc) BaseDisc, -sum(ContractFee) ContractFee, "
				+ "  -sum(LongFee) LongFee, -sum(SoftFee_Furniture) SoftFee_Furniture, -sum(PerfAmount) PerfAmount, "
				+ "  -sum(SetMinus) SetMinus, -sum(ManageFee_Base) ManageFee_Base, -sum(ManageFee_InSet) ManageFee_InSet,  "
				+ "  -sum(ManageFee_Main) ManageFee_Main, -sum(ManageFee_Int) ManageFee_Int, -sum(ManageFee_Serv) ManageFee_Serv, -sum(ManageFee_Soft) ManageFee_Soft, "
				+ "  -sum(ManageFee_Cup) ManageFee_Cup, "
				+ "  -sum(Gift) Gift, -sum(SoftTokenAmount) SoftTokenAmount, -sum(BaseDeduction) BaseDeduction, -sum(ItemDeduction) ItemDeduction "
				+ "  from tPerformance where CustCode=? and pk in ('"+ perfCycle.getPks().replace(",", "','") + "')"
				+ ") a "
				+ "left join tPerformance b on a.PK=b.PK ";
		return this.findBySql(sql, new Object[] { perfCycle.getCustCode() });
	}
	/**
	 * 按业绩公式计算
	 * 
	 * @param perfCycle
	 */
	public List<Map<String, Object>> getExp(PerfCycle perfCycle) {
		String sql = " select dbo.fGetExp(?) RecalPerf";
		return this.findBySql(sql, new Object[] {perfCycle.getPerfExpr()});
	}
	/**
	 * 业绩计算新增已计算业绩保存
	 * 
	 * @param perfCycle
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result doSaveProc(PerfCycle perfCycle) {
		Assert.notNull(perfCycle);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pPerformance_forXml(?,?,?,?,?,?,?,?,?,?,?," 
					+"?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," 
					+"?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, perfCycle.getM_umState());
			call.setInt(2, perfCycle.getPk()==null?0:perfCycle.getPk());
			call.setString(3, perfCycle.getPerfCycleNo());
			call.setString(4, perfCycle.getType());
			call.setString(5, perfCycle.getCustCode());
			call.setDouble(6, perfCycle.getQuantity());
			call.setInt(7, perfCycle.getArea()==null?0:perfCycle.getArea());
			call.setDouble(8, perfCycle.getDesignFee());
			call.setDouble(9, perfCycle.getBasePlan());
			call.setDouble(10, perfCycle.getManageFee_Base());
			call.setDouble(11, perfCycle.getMainPlan());
			call.setDouble(12, perfCycle.getIntegratePlan());
			call.setDouble(13, perfCycle.getCupboardPlan());
			call.setDouble(14, perfCycle.getSoftPlan());
			call.setDouble(15, perfCycle.getMainServPlan());
			call.setDouble(16, perfCycle.getBaseDisc());
			call.setDouble(17, perfCycle.getContractFee());
			call.setDouble(18, perfCycle.getLongFee());
			call.setDouble(19, perfCycle.getSoftFee_Furniture());
			call.setDouble(20, perfCycle.getPerfAmount());
			call.setDouble(21, perfCycle.getFirstPay());
			call.setDouble(22, perfCycle.getMustReceive());
			call.setDouble(23, perfCycle.getRealReceive());
			call.setTimestamp(24, perfCycle.getAchieveDate()==null?null:new Timestamp(perfCycle.getAchieveDate().getTime()));
			call.setDouble(25, perfCycle.getMainProPer());
			call.setTimestamp(26, perfCycle.getSignDate()==null?null:new Timestamp(perfCycle.getSignDate().getTime()));
			call.setTimestamp(27, perfCycle.getSetDate()==null?null:new Timestamp(perfCycle.getSetDate().getTime()));
			call.setDouble(28, perfCycle.getSetMinus());
			call.setDouble(29, perfCycle.getManageFee_InSet());
			call.setDouble(30, perfCycle.getManageFee_Main());
			call.setDouble(31, perfCycle.getManageFee_Int());
			call.setDouble(32, perfCycle.getManageFee_Serv());
			call.setDouble(33, perfCycle.getManageFee_Soft());
			call.setDouble(34, perfCycle.getManageFee_Cup());
			call.setString(35, perfCycle.getIsModified());
			call.setString(36, perfCycle.getDataType());
			call.setString(37, perfCycle.getRemarks());
			call.setString(38, perfCycle.getLastUpdatedBy());
			call.setString(39, perfCycle.getTileStatus());
			call.setString(40, perfCycle.getBathStatus());
			call.setDouble(41, perfCycle.getTileDeduction());
			call.setDouble(42, perfCycle.getBathDeduction());
			call.setDouble(43, perfCycle.getMainDeduction());
			call.setDouble(44, perfCycle.getRecalPerf());
			call.setDouble(45, perfCycle.getPerfPerc());
			call.setDouble(46, perfCycle.getPerfDisc());
			call.setString(47, perfCycle.getIsCalPkPerf());
			call.setDouble(48, perfCycle.getMarkup());
			call.setDouble(49, perfCycle.getSoftTokenAmount());
			call.setDouble(50, perfCycle.getBaseDeduction());
			call.setDouble(51, perfCycle.getItemDeduction());
			call.setDouble(52, perfCycle.getBasePerfPer());
			call.setDouble(53, perfCycle.getMarketFund());
			call.setDouble(54, perfCycle.getRealMaterPerf());
			call.setDouble(55, perfCycle.getMaxMaterPerf());
			call.setDouble(56, perfCycle.getAlreadyMaterPerf());
			call.setDouble(57, perfCycle.getMaterPerf());
			call.setString(58, perfCycle.getGift());
			call.setString(59, perfCycle.getPerfExpr());
			call.setString(60, perfCycle.getPerfExprRemarks());
			call.setString(61, perfCycle.getGxrDetailJson());
			call.setString(62, perfCycle.getJczjDetailJson());
			call.setString(63, perfCycle.getClzjDetailJson());
			call.setString(64, perfCycle.getHtfyzjDetailJson());
			call.registerOutParameter(65, Types.INTEGER);
			call.registerOutParameter(66, Types.NVARCHAR);
			call.setDouble(67, perfCycle.getPerfMarkup());
			call.setString(68, perfCycle.getIsInitSign());
			call.setDouble(69, perfCycle.getTax());
			call.setString(70, perfCycle.getPayeeCode());
			call.setString(71, perfCycle.getIsAddAllInfo());
			call.setString(72, perfCycle.getDocumentNo());
			call.setDouble(73, perfCycle.getSetAdd()==null?0.0:perfCycle.getSetAdd());
			call.setString(74, perfCycle.getDelPks());
			call.setDouble(75, perfCycle.getBasePersonalPlan());
			call.setDouble(76, perfCycle.getManageFee_basePersonal());
			call.setDouble(77, perfCycle.getWoodPlan());
			call.setDouble(78, perfCycle.getManageFee_wood());
			call.execute();
			result.setCode(String.valueOf(call.getInt(65)));
			result.setInfo(call.getString(66));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	/**
	 * 修改是否复核
	 * 
	 * @param perfCycle
	 */
	public void changeIsCheck(PerfCycle perfCycle) {
		String sql = "update tPerformance set IsChecked=? where pk=? and IsChecked<>? ";
		if(this.executeUpdateBySql(sql, new Object[] {perfCycle.getIsChecked(),perfCycle.getPk(),perfCycle.getIsChecked()})>0){
			if("1".equals(perfCycle.getIsChecked())){
				sql="update tMtCustInfo set Perf=round(c.RecalPerf * 0.04,2),Status='3',LastUpdate=getdate(), "
						+"LastUpdatedBy=?,ActionLog='EDIT',PerfCompDate=getdate() "
						+"from tMtCustInfo a  "
						+"inner join tCustomer b on a.PK=b.MtCustInfoPK "
						+"inner join tPerformance c on c.CustCode=b.Code "
						+"where c.Type='1' and c.PK=? and a.Status ='2' ";
			}else{
				sql="update tMtCustInfo set Perf=null,Status='2',LastUpdate=getdate(), "
						+"LastUpdatedBy=?,ActionLog='EDIT',PerfCompDate=null "
						+"from tMtCustInfo a  "
						+"inner join tCustomer b on a.PK=b.MtCustInfoPK "
						+"inner join tPerformance c on c.CustCode=b.Code "
						+"where c.Type='1' and c.PK=? and a.Status ='3' ";
			}
			this.executeUpdateBySql(sql, new Object[] { perfCycle.getLastUpdatedBy(),perfCycle.getPk()});
		}
	}
	/**
	 * 是否存在原业绩
	 * 
	 * @param perfCycle
	 */
	public List<Map<String, Object>> isExistRegPerfPk(PerfCycle perfCycle) {
		String sql = " select 1 from tPerformance where RegPerfPk=?";
		return this.findBySql(sql, new Object[] {perfCycle.getPk()});
	}
	/**
	 * 重签扣减业绩是否有对应的正常业绩/纯设计业绩
	 * 
	 * @param perfCycle
	 */
	public List<Map<String, Object>> isMatchedPerf(PerfCycle perfCycle) {
		String sql = " select 1 from tPerformance where Type='5' and RegPerfPK is not null and PK=?";
		return this.findBySql(sql, new Object[] {perfCycle.getPk()});
	}
	/**
	 * 是否存在纯设计转施工生成的业绩
	 * 
	 * @param perfCycle
	 */
	public List<Map<String, Object>> isExistThisPerfPk(PerfCycle perfCycle) {
		String sql = "select 1 from tPerformance" +
				" where ThisPerfPK in (select item from dbo.fStrToTable(?, ',')) and (type ='1' or type ='2')";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[] {perfCycle.getDelPks()});
		return list;
	}
	/**
	 * 计算增减生成的基础单项扣减
	 * 
	 * @param perfCycle
	 */
	public List<Map<String, Object>> calcBaseDeduction(PerfCycle perfCycle) {
		String sql = "select isnull(sum(bicd.LineAmount*(1-bi.PerfPer)),0) BaseDeduction "
				+ "from tBaseItemChgDetail bicd  "
				+ "inner join tBaseItem bi on bicd.BaseItemCode=bi.Code "
				+ "where bi.PerfPer<1 and bicd.No in"
				+ "('"+ perfCycle.getBaseChgNos().replace(",", "','") + "')";
		return this.findBySql(sql, new Object[] {});
	}
	/**
	 * 计算增减生成的材料单品扣减
	 * 
	 * @param perfCycle
	 */
	public List<Map<String, Object>> calcItemDeduction(PerfCycle perfCycle) {
		String sql = "select isnull(sum((icd.LineAmount-icd.DiscCost)*(1-i.PerfPer)),0) ItemDeduction "
				+ "from tItemChgDetail icd  "
				+ "inner join tItem i on icd.ItemCode=i.Code "
				+ "where i.PerfPer<1 and icd.No in"
				+ "('"+ perfCycle.getItemChgNos().replace(",", "','") + "')";
		return this.findBySql(sql, new Object[] {});
	}
	
	/**
	 * 报表--业务员(独立销售)
	 * @param page
	 * @param perfCycle
	 * @return
	 */
	public Page<Map<String, Object>> findReportYwyDlxxBySql(
			Page<Map<String, Object>> page, PerfCycle perfCycle) {
		List<Object> list = paramList(perfCycle, new ArrayList<Object>());
		String sql = "select * from(select b.EmpCode,e.NameChi,d1.Desc2 Dept1Descr,d2.Desc2 Dept2Descr, "
				+ "sum(a.RecalPerf * b.PerfPer) sumRecalPerf,sum((a.ContractFee+a.DesignFee) * b.PerfPer) sumContractFee, "
				+ "sum(case when a.IsCalPkPerf='1' then a.RecalPerf * b.PerfPer else 0 end) PKPerf "
				+ " from  (select * from("
				+ basicSql
				+ ")yjjs ) main "
				+ "inner join tPerformance a on main.PerfPK=a.PK "
				+ "inner join tCustomer c on a.CustCode=c.Code "
				+ "left join tPerfStakeholder b on main.SPK = b.PK "
				+ "left join tEmployee e on b.EmpCode=e.Number "
				+ "left join tDepartment1 d1 on e.Department1=d1.Code "
				+ "left join tDepartment2 d2 on e.Department2=d2.Code "
				+ "left join tDepartment1 d3 on b.Department1=d3.Code "
				+ "left join tDepartment2 d4 on b.Department2=d4.Code "
				+ "where Role='01' and a.IsAddAllInfo='0' and b.IsCalcPersonPerf='1' ";
		
		sql += "group by b.EmpCode,e.NameChi,d1.Desc2,d2.Desc2";
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ")a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ")a order by a.PKPerf desc";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 报表--业务团队
	 * @param page
	 * @param perfCycle
	 * @return
	 */
	public Page<Map<String, Object>> findReportYwtdBySql(
			Page<Map<String, Object>> page, PerfCycle perfCycle) {
		List<Object> list = paramList(perfCycle, new ArrayList<Object>());
		String sql = "select * from(select f.Desc1, "
				+ "sum(a.RecalPerf * b.PerfPer) sumRecalPerf,sum((a.ContractFee+a.DesignFee+isnull(a.Tax,0)) * b.PerfPer) sumContractFee, "
				+ "sum(case when a.IsCalPkPerf='1' then a.RecalPerf * b.PerfPer else 0 end) PKPerf, "
				+ "sum((a.contractFee+isnull(a.Tax,0)) * b.PerfPer ) contractfee,sum(a.designFee * b.PerfPer) designfee "
				+ " from  (select * from("
				+ basicSql
				+ ")yjjs ) main "
				+ "inner join tPerformance a on main.PerfPK=a.PK "
				+ "inner join tCustomer c on a.CustCode=c.Code "
				+ "left join tPerfStakeholder b on main.SPK = b.PK "
				+ "left join tTeamEmp e on b.EmpCode=e.EMNum "
				+ "left join tTeam f on e.TeamCode=f.Code "
				+ "where Role='01' and f.IsCalcPerf='1' ";
		if(StringUtils.isNotBlank(perfCycle.getTeamCode())){
			sql+= " and e.TeamCode in " + "('"+ perfCycle.getTeamCode().replaceAll(",", "','") + "')";
		}
		sql += "group by e.TeamCode,f.Desc1 ";
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ")a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ")a order by a.PKPerf desc";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 报表--设计团队
	 * @param page
	 * @param perfCycle
	 * @return
	 */
	public Page<Map<String, Object>> findReportSjtdBySql(
			Page<Map<String, Object>> page, PerfCycle perfCycle) {
		List<Object> list = paramList(perfCycle, new ArrayList<Object>());
		String sql = "select * from(select f.Desc1, "
				+ "sum(a.RecalPerf * b.PerfPer) sumRecalPerf,sum((a.ContractFee+a.DesignFee+isnull(a.Tax,0)) * b.PerfPer) sumContractFee, "
				+ "sum(case when a.IsCalPkPerf='1' then a.RecalPerf * b.PerfPer else 0 end) PKPerf ,"
				+ " sum((a.ContractFee+isnull(a.Tax,0)) * b.PerfPer ) contractfee, sum(a.DesignFee * b.PerfPer) designfee " 
				+ " from  (select * from("
				+ basicSql
				+ ")yjjs ) main "
				+ "inner join tPerformance a on main.PerfPK=a.PK "
				+ "inner join tCustomer c on a.CustCode=c.Code "
				+ "left join tPerfStakeholder b on main.SPK = b.PK "
				+ "left join tTeamEmp e on b.EmpCode=e.EMNum "
				+ "left join tTeam f on e.TeamCode=f.Code "
				+ "where Role='00' and f.IsCalcPerf='1' ";
		if(StringUtils.isNotBlank(perfCycle.getTeamCode())){
			sql+= " and e.TeamCode in " + "('"+ perfCycle.getTeamCode().replaceAll(",", "','") + "')";
		}
		sql += "group by e.TeamCode,f.Desc1 ";
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ")a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ")a order by a.PKPerf desc";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 签单数据统计
	 * @param page
	 * @param perfCycle
	 * @return
	 */
	public Page<Map<String, Object>> findSignDataJqGridBySql(
			Page<Map<String, Object>> page, PerfCycle perfCycle) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from( select a.DocumentNo,a.CustCode,b.Address, " 
				+ " c.Desc1 CustTypeDescr,a.SignDate,a.PerfCycleNo,isnull(fr.Amount,0) FreeBaseAmount " 
			    + " from tPerformance a " 
				+ " left join tcustomer b on a.CustCode=b.Code " 
				+ " left join tCusttype c on c.code=b.CustType " 
				+ " left join (select sum(LineAmount) Amount,custCode from tbaseitemplan " 
				+ " where BaseItemCode in (select * from fStrToTable(?,',')) " 
				+ " group by custCode)fr on fr.custCode=a.CustCode" 
				+ " where a.Type='1' " ;
		list.add(perfCycle.getFreeBaseItem());
		if(StringUtils.isNotBlank(perfCycle.getNo())){
			sql+=" and a.PerfCycleNo = ? ";
			list.add(perfCycle.getNo());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " )a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += " )a order by a.PerfCycleNo desc";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public List<Map<String, Object>>  getBaseChgSetAdd(PerfCycle perfCycle){
		String sql=" select sum(case when bi.Category not in ('2','4') then chd.LineAmount else 0 end) SetAdd, "
			    +" sum(case when bi.Category='4' then chd.LineAmount else 0 end) SetMinus  " 
				+" from tBaseItemChg ch  " 
				+" left join tBaseItemChgDetail chd on ch.No=chd.no "
				+" left join tBaseItem bi on bi.Code=chd.BaseItemCode "
				+" left join tcustomer c on c.code=ch.CustCode "
				+" left join tCusttype ct on ct.code=c.CustType "
				+" where ct.type='2'  " ;
		if(StringUtils.isBlank(perfCycle.getBaseChgNos())){
			return null;
		}else{
			sql+=" and ch.No in ('"+ perfCycle.getBaseChgNos().replace(",", "','") + "')";
		}
		List<Map<String, Object>> list=this.findBySql(sql, new Object[]{});

		if(list!=null && list.size()>0){
			return list;
		}
		return null;
	}
	
	public List<Map<String, Object>> getMainProPer_chg(PerfCycle perfCycle){
		String sql=" select  round(case when sum(case when b.isoutset = '1' then b.lineamount else round(round(b.qty * i.projectcost * b.markup / 100, 0) + b.processcost, 0) end )=0 then 0  "
				+" else sum(case when b.isoutset = '1' then  b.lineamount - b.qty * case when b.cost = 0 then b.unitprice else b.cost end - b.processcost " 
				+"      else round(round(b.qty * i.projectcost * b.markup / 100, 0) + b.processcost, 0) - b.qty* case when b.cost = 0 then b.unitprice else b.cost end - b.processcost end "
				+"   )/sum(case when b.isoutset = '1' then b.lineamount else round(round(b.qty * i.projectcost * b.markup / 100, 0) + b.processcost, 0) end ) "
			    +" end,4)MainProPer "
				+" from tItemChg a "
				+" left join tItemChgDetail b on a.no=b.no "
				+" inner join titem i on b.itemcode = i.code "
				+" where a.ItemType1 ='ZC' and a.IsService ='0' and b.LineAmount<>0 ";
		if(StringUtils.isBlank(perfCycle.getChgNos())){
			return null;
		}else{
			sql+=" and a.No in ('"+ perfCycle.getChgNos().replace(",", "','") + "')";
		}
		List<Map<String, Object>> list=this.findBySql(sql, new Object[]{});

		if(list!=null && list.size()>0){
			return list;
		}
		return null;
	}
	
	public List<Map<String, Object>> getBasePersonalPlan(PerfCycle perfCycle){
		String sql=" select sum(bicd.LineAmount) basePersonalPlan, "
				+" sum(case when b.isCalMangeFee='1' then bicd.LineAmount * ct.ManageFee_BasePer * ct.ChgManageFeePer else 0 end) manageFee_basePersonal, "
				+" sum(case when f.WorkType1='04' then bicd.LineAmount else 0 end) woodPlan, "
				+" sum(case when f.WorkType1='04' and b.isCalMangeFee='1' then bicd.LineAmount * ct.ManageFee_BasePer *ct.ChgManageFeePer else 0 end) manageFee_wood "
				+" from tBaseItemChg bic "
				+" left join tBaseItemChgDetail bicd on bicd.no=bic.No "
				+" left join tBaseItem b on bicd.BaseItemCode = b.code "
//				+" left join tBaseItemType2 d on b.BaseItemType2 = d.Code "
//				+" left join tWorkType2 e on e.Code = d.MaterWorkType2 "
				+" inner join tCommiBasePersonal d on d.BaseItemType1=b.BaseItemType1 "
				+" left join tCustomer c on bic.CustCode = c.Code "
				+" left join tCusttype ct on ct.code = c.CustType "
				+" left join tBaseItemType2 e on b.BaseItemType2 = e.Code "
				+" left join tWorkType2 f on f.Code = e.MaterWorkType2 "
				+" left join tXTCS cs on cs.ID= 'NEWCOMMIDATE' "
				+" where d.Expired='F' and isnull(dbo.fGetPerfAchieveDateMin(bic.CustCode),getdate()) >= cs.QZ ";
		if(StringUtils.isBlank(perfCycle.getBaseChgNos())){
			return null;
		}else{
			sql+=" and bic.No in ('"+ perfCycle.getBaseChgNos().replace(",", "','") + "')";
		}
		List<Map<String, Object>> list=this.findBySql(sql, new Object[]{});

		if(list!=null && list.size()>0){
			return list;
		}
		return null;
	}
	
	
	/**
	 * 独立销售业绩
	 * @param page
	 * @param perfCycle
	 * @return
	 */
	public Page<Map<String,Object>> findIndependPerfBySql(Page<Map<String,Object>> page, PerfCycle perfCycle) {
		Assert.notNull(perfCycle);
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pCalcPerf_Independ(?,?,?,?,?,?,?,?)}");
			call.setString(1, perfCycle.getNo());
			call.setString(2, perfCycle.getCustCode());
			call.setString(3, perfCycle.getAddress());
			call.setString(4, perfCycle.getBusinessMan());
			call.setString(5, perfCycle.getDesignMan());
			call.setString(6, perfCycle.getDocumentNo());
			call.setString(7, perfCycle.getCheckStatus());
			call.setString(8, perfCycle.getLastUpdatedBy());
			call.execute();
			ResultSet rs = call.getResultSet(); 
			List<Map<String, Object>> list=BeanConvertUtil.resultSetToList(rs);
			page.setResult(list); 	
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return page;
	} 
}
