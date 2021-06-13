package com.house.home.dao.project;

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
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.commons.utils.SqlUtil;
import com.house.framework.web.login.UserContext;
import com.house.home.client.service.evt.AddWokerCostEvt;
import com.house.home.client.service.evt.WokerCostApplyEvt;
import com.house.home.client.service.evt.WorkerAppEvt;
import com.house.home.client.service.evt.WorkerSignInEvt;
import com.house.home.entity.project.CustWorker;
import com.house.home.entity.project.Worker;

@SuppressWarnings("serial")
@Repository
public class WorkerDao extends BaseDao {

	/**
	 * worker分页信息
	 * 
	 * @param page
	 * @param worker
	 * @return
	 */
	public Page<Map<String,Object>> findCodePageBySql(Page<Map<String,Object>> page, Worker worker) {
		List<Object> list = new ArrayList<Object>();
		
		String sql = "select * from ( Select (select count(*) from tcustworker cw left join tcustomer c on c.code=cw.custcode  " +
				" where  cw.workercode= a.code and cw.endDate is null and c.status='4' and cw.status<>'2'  " +
				" and not exists( select 1 from tWorkSign a left join tPrjItem2 b on a.PrjItem2=b.Code where  a.IsComplete='1' and a.CustWkPk=cw.Pk "+
                " group by a.CustWkPk,b.worktype12  having count(*)=(select count(1) from tPrjItem2 where worktype12=b.worktype12) )) ondo ," +
                "(select count(*) from tcustworker cw left join tcustomer c on c.code=cw.custcode   " +
				" where  cw.workercode= a.code  and cw.endDate is null and c.status='4' " +
				" and exists( select 1 from tWorkSign a left join tPrjItem2 b on a.PrjItem2=b.Code where  a.IsComplete='1' and a.CustWkPk=cw.Pk "+
                " group by a.CustWkPk,b.worktype12  having count(*)=(select count(1) from tPrjItem2 where worktype12=b.worktype12) )) cmpdo ," +
				"(select count(*) from tcustworker cw left join tcustomer c on c.code=cw.custcode  " +
				" where  cw.workercode= a.code and cw.status='2' and c.status='4' and cw.endDate is null " +
				" and not exists( select 1 from tWorkSign a left join tPrjItem2 b on a.PrjItem2=b.Code where  a.IsComplete='1' and a.CustWkPk=cw.Pk "+
                " group by a.CustWkPk,b.worktype12  having count(*)=(select count(1) from tPrjItem2 where worktype12=b.worktype12) ))StopDo , "+
				" pr.descr prjRegionDescr," +
				" a.*,rTrim(a.Code) Code1,a.NameChi+'('+b.descr+')' NameChiWorkType12,x4.note VehicleDescr ,dbo.fGetWorkerRcvType(a.Code) RcvTypeDescr,b.descr worktype12descr,x.Note isSigndescr," +
				" r2.descr region2descr ,d1.desc1 department1descr,b.qualityFeeBegin," +
				" sb.descr spcbuilderdescr ,x2.note workerleveldescr,dbo.fGetWorkerConRegion(a.code) belongRegionDescr, " +
				" case when mcw.MaxConPlanEnd is not null then DATEADD(DAY,1, mcw.MaxConPlanEnd) when mcw.MaxPlanEnd is not null then DATEADD(DAY,1, mcw.MaxPlanEnd) " +
				" else DATEADD(DAY,-1,GETDATE()) end CanReceiptDate " +
				" from tWorker a " +
				" left join tWorkType12 b on b.code=a.worktype12 " +
				" left join tXTDM x on x.cbm =a.isSign and x.ID='WSIGNTYPE' " +
				" left join tRegion2 r2 on r2.code=a.LiveRegion2 " +
				" left join tdepartment1 d1 on d1.code =a.department1 " +
				" left join tregion r on r.code = a.belongRegion " +
				" left join tSpcBuilder sb on sb.code=a.spcBuilder " +
				" left join tXTDM x2 on x2.cbm=a.Level and x2.id='WORKERLEVEL' " +
				" left join tXTDM x3 on x3.cbm=a.RcvPrjType and x3.id='RCVPRJTYPE' " +//承接工地
				" left join tXTDM x4 on x4.cbm=a.Vehicle and x4.id='VEHICLE' " +//交通工具
				" left join tPrjRegion pr on pr.code=a.PrjRegionCode " +
				" left join (" +
				"   select cw.WorkerCode,max(cw.PlanEnd) MaxPlanEnd,max(cw.ConPlanEnd) MaxConPlanEnd" +
				"   from tCustWorker cw " +
				"   where cw.Status='1' " +
				"   group by cw.WorkerCode " +
				" ) mcw on mcw.WorkerCode = a.Code "+
				//" left join tWorkType12Dept wtd on wtd.Code=a.WorkType12Dept " +
				" where 1=1 " + // and a.isLeave='0' remove by zzr 2018/11/03
				"";
		
		if (StringUtils.isBlank(worker.getExpired()) || "F".equals(worker.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if(StringUtils.isNotBlank(worker.getCode())){
			sql+=" and (a.code like ? or a.NameChi like ?)";
			list.add("%"+worker.getCode()+"%");
			list.add("%"+worker.getCode()+"%");
		}
		if(StringUtils.isNotBlank(worker.getLevel())){
			sql+=" and a.level = ? ";
			list.add(worker.getLevel());
		}
		if(StringUtils.isNotBlank(worker.getPrjRegionCode())){
			sql+=" and a.PrjRegionCode = ? ";
			list.add(worker.getPrjRegionCode());
		}
		if(StringUtils.isNotBlank(worker.getIsSign())){
			sql+=" and a.isSign =? ";
			list.add(worker.getIsSign());
		}
		if(StringUtils.isNotBlank(worker.getRegion())){
			sql+=" and a.LiveRegion = ?";
			list.add(worker.getRegion());
		}
		if(StringUtils.isNotBlank(worker.getRegion2())){
			sql+=" and a.LiveRegion2 = ?";
			list.add(worker.getRegion2());
		}
		if(StringUtils.isNotBlank(worker.getDepartment1())){
			sql+=" and a.department1 = ?";
			list.add(worker.getDepartment1());
		}
		if(StringUtils.isNotBlank(worker.getSpcBuilder())){
			sql += " and a.spcBuilder in " + "('"+worker.getSpcBuilder().replace(",", "','" )+ "')";
		}
		if(StringUtils.isNotBlank(worker.getBelongRegion())){
			sql += " and a.BelongRegion in " + "('"+worker.getBelongRegion().replace(",", "','" )+ "')";
		}
		if(StringUtils.isNotBlank(worker.getNameChi())){
			sql+=" and a.NameChi  like ? ";
			list.add("%"+worker.getNameChi()+"%");
		}
		if(StringUtils.isNotBlank(worker.getWorkType12())){
			sql+=" and (a.WorkType12 =? or a.Code in (select WorkerCode from tWorkerWorkType12  where WorkType12=? ) ) ";
			list.add(worker.getWorkType12());
			list.add(worker.getWorkType12());
		}
		if(StringUtils.isNotBlank(worker.getWorkType12Dept())){
			sql+=" and a.workType12Dept = ? ";
			list.add(worker.getWorkType12Dept());
		}
		if(StringUtils.isNotBlank(worker.getIsLeave())){
			sql+=" and a.isLeave = ? ";
			list.add(worker.getIsLeave());
		}
		if(StringUtils.isNotBlank(worker.getConRegion())){
			sql+=" and ','+a.belongRegion+',' like ?  ";
			list.add("%,"+worker.getConRegion()+",%");
		}
		if(StringUtils.isNotBlank(worker.getWorkerClassify())){
			sql+=" and a.workerClassify = ? ";
			list.add(worker.getWorkerClassify());
		}
		
		if (StringUtils.isNotBlank(worker.getWorkType1())) {
			sql += " and ( b.workType1=? ";
			list.add(worker.getWorkType1());
			if("2".equals(worker.getAppType())){
				sql += " and exists ( "
					 + " 	select 1 from tCustWorker in_tcw left join tWorkType12 in_tw12 on in_tcw.WorkType12 = in_tw12.Code "
					 + " 	where in_tw12.WorkType1 = ? and in_tcw.WorkerCode = a.Code ";
				list.add(worker.getWorkType1());
					 if(StringUtils.isNotBlank(worker.getCustCode())){
						 sql+="and CustCode=?";
						 list.add(worker.getCustCode());
					 }
					sql +=" )";
			}
			sql += " ) ";
			
		}
		if(StringUtils.isNotBlank(worker.getRefCustCode())){
			sql += " and exists ( "
					 + " 	select 1 from tCustWorker in_tcw left join tWorkType12 in_tw12 on in_tcw.WorkType12 = in_tw12.Code "
					 + " 	where in_tcw.WorkerCode = a.Code and in_tcw.CustCode=? )";
			list.add(worker.getRefCustCode());
		}
		if(StringUtils.isNotBlank(worker.getOnDoNum())){
			sql+=" ) a where ondo < ?";
			list.add(worker.getOnDoNum());
		} else{
			sql+=" ) a";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += " order by a.ondo ,a.level desc ";
		}
		return this.findPageBySql(page, sql,list.toArray());
	}
	
	/**
	 * @Description: worker_list分页查询
	 * @author	created by zb
	 * @date	2018-8-17--上午10:28:45
	 * @param page
	 * @param worker 查询参数
	 * @param prjRole 判断操作员是否有工程角色  true-有
	 * @param uc 获取操作员角色
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySqlList(Page<Map<String,Object>> page, Worker worker, 
			UserContext uc) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select * from ( select a.Code, a.NameChi, a.NameChi + '  ' + a.Code Description, d.PrjLevel, e.Descr PrjLevelDescr, d.IsSupvr, t3.NOTE IsSupvrDescr, " +
				"a.Phone, a.IDNum, a.CardID, a.CardID2, a.WorkType12, a.IntroduceEmp, b.descr WorkType12descr, " +
				"a.Remarks, a.LastUpdate, a.LastUpdatedBy, a.Expired, a.ActionLog, c.NameChi IntroduceEmpDescr, a.issign, " +
				"t1.NOTE issignDescr, a.signdate, a.level, t2.Note levelDescr, a.EmpCode, d.namechi empcodeDescr, " +
				"a.LiveRegion, f.Descr LiveRegionDescr, a.LiveRegion2, f2.Descr LiveRegion2Descr, a.Num,a.Department1, " +
				"g.Desc1 Department1Descr, a.SpcBuilder, h.Descr SpcBuilderDescr, a.IsAutoArrange, t4.NOTE IsAutoArrangeDescr, " +
				"a.BelongRegion,dbo.fGetWorkerConRegion(a.code) BelongRegionDescr, a.IsLeave, t5.NOTE IsLeaveDescr, a.Efficiency, a.WorkType12Dept, " +
				"wtd.Descr WorkType12DeptDescr, a.PrjRegionCode, a.RcvPrjType, a.Vehicle, a.Address ,t6.note VehicleDescr,dbo.fGetWorkerRcvType(a.code) rcvprjtypedescr,a.LeaveDate, " +
				"a.Department2, d2.Desc2 Department2Descr, a.LaborCmpCode, j.Descr LaborCmpDescr, a.PersonalProfile, a.IsRegisterMall, t7.NOTE IsRegisterMallDescr " +
				"from tWorker a " +
				"left join tWorkType12 b on b.Code = a.WorkType12 " +
				"left join tEmployee c on c.Number = a.IntroduceEmp " +
				"left join txtdm t1 on a.issign = t1.CBM and t1.ID = 'WSIGNTYPE' " +
				"left join txtdm t2 on a.level = t2.cbm and t2.id = 'WORKERLEVEL' " +
				"left join tEmployee d on d.Number = a.EmpCode " +
				"left join tPrjlevel e on d.PrjLevel=e.Code " +
				"left join txtdm t3 on d.IsSupvr = t3.CBM and t3.ID = 'PRJMANTYPE' " +
				"left join tRegion f on a.LiveRegion=f.Code " +
				"left join tRegion2 f2 on a.LiveRegion2=f2.Code " +
				"left join tDepartment1 g on a.Department1=g.Code " +
				"left join tSpcBuilder h on a.SpcBuilder=h.Code " +
				"left join tRegion i on a.BelongRegion=i.Code " +
				"left join txtdm t4 on a.IsAutoArrange = t4.CBM and t4.ID = 'YESNO' " +
				"left join txtdm t5 on a.IsLeave = t5.CBM and t5.ID = 'YESNO' " +
				"left join tWorkType12Dept wtd on wtd.code=a.WorkType12Dept " +
				"left join txtdm t6 on a.Vehicle = t6.CBM and t6.ID = 'Vehicle' " +
				"left join tDepartment2 d2 on a.Department2 = d2.Code " +
				"left join tLaborCompny j on a.LaborCmpCode = j.Code " +
				"left join txtdm t7 on a.IsRegisterMall = t7.CBM and t7.ID = 'YESNO' "+
				"where 1=1 ";
		if (StringUtils.isNotBlank(worker.getNameChi())) {
			sql += " and a.NameChi like ? ";
			list.add("%"+worker.getNameChi()+"%");
		}
		
		if (StringUtils.isNotBlank(worker.getDescription())) {
            sql += " and (a.Code like ? or a.NameChi like ?) ";
            list.add("%"+worker.getDescription()+"%");
            list.add("%"+worker.getDescription()+"%");
        }
		
		if (StringUtils.isNotBlank(worker.getPhone())) {
			sql += " and a.Phone like ?";
			list.add("%"+worker.getPhone()+"%");
		}
    	if (StringUtils.isNotBlank(worker.getWorkType12())) {
			sql += " and a.WorkType12=? ";
			list.add(worker.getWorkType12());
		}
    	if(StringUtils.isNotBlank(worker.getWorkType12Dept())){
			sql+=" and a.workType12Dept = ? ";
			list.add(worker.getWorkType12Dept());
		}
		if (StringUtils.isBlank(worker.getExpired()) || "F".equals(worker.getExpired())) {
			sql += " and a.Expired='F' and (a.IsLeave = 0 or a.isLeave = '2') ";
		}
		if(StringUtils.isNotBlank(worker.getIsSign())){
			sql+=" and a.isSign in('" + worker.getIsSign().replaceAll(",", "','") + "') ";
		}
		if(StringUtils.isNotBlank(worker.getLevel())){
			sql+=" and a.level = ? ";
			list.add(worker.getLevel());
		}
		if(StringUtils.isNotBlank(worker.getDepartment1())){
			sql+=" and a.department1 = ? ";
			list.add(worker.getDepartment1());
		}
		
		if (StringUtils.isNotBlank(worker.getDepartment2())) {
			sql += " and a.department2 = ? ";
			list.add(worker.getDepartment2());
		}
		
		if (StringUtils.isNotBlank(worker.getLaborCmpCode())) {
            sql += " and a.LaborCmpCode = ? ";
            list.add(worker.getLaborCmpCode());
        }
		
		if(StringUtils.isNotBlank(worker.getIsLeave())){
			sql+=" and a.Isleave = ? ";
			list.add(worker.getIsLeave());
		}
		if(StringUtils.isNotBlank(worker.getSpcBuilder())){
			sql += " and a.spcBuilder in " + "('"+worker.getSpcBuilder().replace(",", "','" )+ "') ";
		}
		if(StringUtils.isNotBlank(worker.getBelongRegion())){
			sql += " and a.BelongRegion in " + "('"+worker.getBelongRegion().replace(",", "','" )+ "') ";
		}
		if (StringUtils.isNotBlank(worker.getPrjRegionCode())) {
			sql += " and a.PrjRegionCode in ('"+worker.getPrjRegionCode().replace(",", "','")+"') "; //增加工程大区条件
		}
		if (StringUtils.isNotBlank(worker.getWorkerClassify())) {
			sql += " and a.WorkerClassify in ('"+worker.getWorkerClassify().replace(",", "','")+"') "; //工人分类
		}
		if(StringUtils.isNotBlank(worker.getIsRegisterMall())){
			sql +=" and a.IsRegisterMall =? ";
			list.add(worker.getIsRegisterMall());
		}
		if(StringUtils.isNotBlank(worker.getMemberName())){
			sql +=  " and exists ( "+
					" select 1 from "+
					" tWorkerMember k where  a.Code = k.WorkerCode "+
					" and k.namechi like ? "+
					" 	)";
			list.add("%"+worker.getMemberName()+"%");
		}
		// 当没有工程角色时坐着或者tPrjRoleWorkType12中PrjRole为空时，可以查看所有工种类型，当有工程角色时，只能查看工程角色内的工种类型
		sql += " and exists ( " +
			   "   select 1 from tCZYBM c " +
			   "   where c.CZYBH=? and ( " +
			   "     c.PrjRole is null or c.PrjRole='' or not exists (select 1 from tPrjRoleWorkType12 where PrjRole=c.PrjRole) " +
			   "     or exists (select 1 from tPrjRoleWorkType12 where PrjRole=c.PrjRole and WorkType12=a.worktype12) " +
			   "   ) " +
			   " ) ";
		list.add(uc.getCzybh());
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " ) a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		} else {
			sql += " ) a order by a.LastUpdate desc ";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * worker分页信息
	 * 
	 * @param page
	 * @param worker
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Worker worker) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select a.code,a.namechi,a.Phone realPhone,a.IDNum,a.CardID,a.WorkType12,a.IntroduceEmp,a.Remarks,a.LastUpdate,"
				+"a.LastUpdatedBy,a.Expired,a.ActionLog,stuff(a.phone,4,4,'****') phone,"
				+"b.Descr workType12Descr,a.CardId2 "
				+"from tWorker a "
				+"left join tWorkType12 b on a.workType12=b.code "
				+"left join tXtdm xt1 on a.IsRegisterMall = xt1.CBM and xt1.ID = 'YESNO' "
				+"where 1=1 ";
		if(StringUtils.isNotBlank(worker.getIsLifeCost()) && "1".equals(worker.getIsLifeCost())){
			sql +=" and a.EmpCode=? ";
			list.add(worker.getEmpCode());
		}
    	if (StringUtils.isNotBlank(worker.getCode())) {
			sql += " and (a.Code like ?  or a.Namechi like ? )";
			list.add("%"+worker.getCode()+"%");
			list.add("%"+worker.getCode()+"%");
		}
    	if (StringUtils.isNotBlank(worker.getWorkType1())) {
			sql += " and ( b.workType1=? ";
			list.add(worker.getWorkType1());
			if("2".equals(worker.getAppType())){
				sql += " or exists ( "
					 + " 	select 1 from tCustWorker in_tcw left join tWorkType12 in_tw12 on in_tcw.WorkType12 = in_tw12.Code "
					 + " 	where in_tw12.WorkType1 = ? and in_tcw.WorkerCode = a.Code )";
				list.add(worker.getWorkType1());
			}
			sql += " ) ";
			
		}
    	if (StringUtils.isNotBlank(worker.getPhone())) {
			sql += " and a.Phone=? ";
			list.add(worker.getPhone());
		}
    	if (StringUtils.isNotBlank(worker.getIdnum())) {
			sql += " and a.IDNum=? ";
			list.add(worker.getIdnum());
		}
    	if (StringUtils.isNotBlank(worker.getCardId())) {
			sql += " and a.CardID=? ";
			list.add(worker.getCardId());
		}
    	if (StringUtils.isNotBlank(worker.getWorkType12())) {
			sql += " and a.WorkType12=? ";
			list.add(worker.getWorkType12());
		}
    	if (StringUtils.isNotBlank(worker.getIntroduceEmp())) {
			sql += " and a.IntroduceEmp=? ";
			list.add(worker.getIntroduceEmp());
		}
    	if (StringUtils.isNotBlank(worker.getRemarks())) {
			sql += " and a.Remarks=? ";
			list.add(worker.getRemarks());
		}
    	if (worker.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(worker.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(worker.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(worker.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(worker.getExpired()) || "F".equals(worker.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(worker.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(worker.getActionLog());
		}
    	if("1".equals(worker.getPlatform())){
    		if(!"1".equals(worker.getIsWithHold())){
    			sql += " and (("
    				 + " 	exists( "
    				 + " 		select 1 from tCustWorker tcw left join tWorkType2 twt2 on tcw.WorkType12 = twt2.Worktype12 "
    				 + " 		where twt2.Code=?  and CustCode=? "
    				 + " 	) and exists( "
    				 + " 		select 1 from tCustWorker where CustCode=? and WorkerCode=a.Code "
    				 + "	) ) or not exists( "
    				 + " 			select 1 from tCustWorker tcw left join tWorkType2 twt2 on tcw.WorkType12 = twt2.Worktype12 "
    				 + " 			where twt2.Code=?  and CustCode=? "
    				 + "		)"
    				 + " )";	
    			if(StringUtils.isBlank(worker.getCustCode())){
    				worker.setCustCode("");
    			}
    			if(StringUtils.isBlank(worker.getWorkType2())){
    				worker.setWorkType2("");
    			}
    			list.add(worker.getWorkType2());
    			list.add(worker.getCustCode());
    			list.add(worker.getCustCode());
    			list.add(worker.getWorkType2());
    			list.add(worker.getCustCode());
    		}
    	}
    	if (StringUtils.isNotBlank(worker.getNameChi())) {
			sql += " and a.NameChi like ? ";
			list.add("%"+worker.getNameChi()+"%");
		}else{
			if("1".equals(worker.getPlatform())){
				if(!"1".equals(worker.getIsWithHold())){
	    			sql += " and ( "
	    				 + " 	exists( "
	    				 + " 		select 1 from tCustWorker tcw left join tWorkType2 twt2 on tcw.WorkType12 = twt2.Worktype12 "
	    				 + " 		where twt2.Code=?  and CustCode=? "
	    				 + " 	) and exists( "
	    				 + " 		select 1 from tCustWorker where CustCode=? and WorkerCode=a.Code "
	    				 + "	) "
	    				 + " ) ";	
	    			if(StringUtils.isBlank(worker.getCustCode())){
	    				worker.setCustCode("");
	    			}
	    			if(StringUtils.isBlank(worker.getWorkType2())){
	    				worker.setWorkType2("");
	    			}
	    			list.add(worker.getWorkType2());
	    			list.add(worker.getCustCode());
	    			list.add(worker.getCustCode());
	    		}				
			}
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Code";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> findMemberPageBySql(Page<Map<String,Object>> page, Worker worker) {
	    
	    List<Object> params = new ArrayList<Object>();
	    
	    String sql = "select * from ("
	    		+ "select a.Code, a.WorkerCode, a.NameChi, a.Phone, "
	            + "    a.IsHeadMan, b.NOTE IsHeadManDescr, a.IDNum, a.CardID, a.Bank, "
	            + "    a.CardID2, a.Bank2, a.SalaryRatio, a.LastUpdate, "
	            + "    a.LastUpdatedBy, a.Expired, a.ActionLog "
	            + "from tWorkerMember a "
	            + "left join tXTDM b on a.IsHeadMan = b.CBM and b.ID = 'YESNO' "
	            + "where 1 = 1 and a.IsHeadMan = '0' ";

	    if (StringUtils.isNotBlank(worker.getCode())) {
            sql += "and a.WorkerCode = ? ";
            params.add(worker.getCode());
        }
	    
	    if (StringUtils.isNotBlank(page.getPageOrderBy())) {
	        sql += ") a order by a." + page.getPageOrderBy() + " " + page.getPageOrder();
	    } else {
	        sql += ") a order by a.Expired";
	    }
	    
	    return this.findPageBySql(page, sql, params.toArray());
	}
	
	public Worker getByPhoneAndMm(String phone, String mm){
		String hql = "select a from Worker a where  a.phone=? and a.mm=? ";		
		List<Worker> list =  this.find(hql, new Object[]{phone,mm});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	public Worker getByPhone(String phone) {
		String hql="select a from Worker a where a.phone=? and a.expired='F'";
		List<Worker> list =  this.find(hql, new Object[]{phone});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	public Page<Map<String, Object>> getSiteConstructList(
			Page<Map<String, Object>> page, String code,String status,String address){
		List<Object> list = new ArrayList<Object>();	
		String sql = " SELECT b.Code,case when b.RealAddress is not null and b.RealAddress <>'' then b.RealAddress else b.Address end Address,a.WorkerCode,a.ComeDate,a.WorkType12,a.PK CustWkPk,a.EndDate ConfirmDate, "
					+" case when (b.projectMan IS NOT null and b.ProjectMan<>'') or(b.OldCode is not null and b.OldCode <>'' and g.ProjectMan is not null and g.ProjectMan<>'') THEN '1' ELSE '0' END canCall,"
		            +" case when a.WorkType12 = '04' then 0 else dbo.fGetWorkload(a.CustCode,a.WorkType12) end gzl,dbo.fGetWorkTypeConDay(a.CustCode,a.WorkType12) contructDay, "
					+" case when (b.OldCode is not null and b.OldCode<>'') and (b.ProjectMan is null or b.ProjectMan='') then g.ProjectMan else b.ProjectMan end ProjectMan, "
					+" case when (b.OldCode is not null and b.OldCode<>'') and (b.ProjectMan is null or b.ProjectMan='') then h.NameChi else c.NameChi end NameChi, "
					+" case when (b.OldCode is not null and b.OldCode<>'') and (b.ProjectMan is null or b.ProjectMan='') then h.Phone else c.Phone end Phone,xt.Note WorkerClassifyDescr ";
		sql	+=" FROM dbo.tCustWorker a "
			 +" LEFT JOIN dbo.tCustomer b ON a.CustCode = b.Code "
			 +" LEFT JOIN dbo.tEmployee c ON b.ProjectMan = c.Number "
			 +" left join tWorker d on d.Code = a.WorkerCode  "
			 +" left join tWorkType12 e on d.WorkType12 = e.Code "  
			 +" left join tPrjProg f on f.CustCode = b.Code and f.PrjItem = e.PrjItem "
			 +" left join tCustomer g on b.OldCode = g.Code"
			 +" left join tEmployee h ON g.ProjectMan = h.Number "
			 +" left join tCustType i on i.Code = b.CustType "
			 +" left join tXTDM xt on i.WorkerClassify = xt.CBM and ID = 'WORKERCLASSIFY' "
			 +" where 1=1 ";
		if("1".equals(status) || StringUtils.isBlank(status)){
			sql += " AND a.EndDate is null ";
		}else{
			sql += " AND a.EndDate is not null ";
		}
		
		if(StringUtils.isNotBlank(code)){
			sql += " and a.WorkerCode=? ";
			list.add(code);
		}
		if(StringUtils.isNotBlank(address)){
			sql += " AND b.Address like ? ";
			list.add("%"+address+"%");
		}
		sql +=" order by a.ComeDate desc ";
		return this.findPageBySql(page, sql,list.toArray());
	}
	public List<Map<String,Object>> getWorkerPrjItem(WorkerAppEvt evt){
		List<Object> params = new ArrayList<Object>();
		String sql = " SELECT a.*, "
					+" CASE WHEN a.CrtDate IS NULL "
					+" THEN CASE WHEN a.signInCount < a.MinDay THEN '0' ELSE '1' END "
					+" ELSE NULL END canSign,ISNULL(a.MinDay,0)-ISNULL(a.signInCount,0) needDays "
					+" FROM ( "
					+" 	select a.PK,a.WorkerCode,a.CustCode,a.WorkType12,a.ComeDate,b.Descr wt12Descr,c.Code,c.Descr pi2Descr,c.MinDay, "
					+" 	c.Seq,c.IsAppWork,d.CrtDate,e.Address ,b.IsTechnology, datediff(d,a.EndDate,getdate()) EndDays, " ;
		if (StringUtils.isNotBlank(evt.getWorkType2())) { //当有工种时，可申请为1 modify by zb on 20190708
			//sql += "	(select COUNT(*) from tPrjItem2 where IsAppWork='1' and WorkType12=? and OfferWorkType2=?) canApplyCount,c.OfferWorkType2, ";
			sql += "	( select COUNT(*) from tPrjItem2 where Seq <= c.Seq and IsAppWork = '1' and WorkType12=? ) canApplyCount,c.OfferWorkType2, ";
			params.add(evt.getWorkType12());
			//params.add(evt.getWorkType2());
		} else {
			sql += " 	( select COUNT(*) from tPrjItem2 where Seq <= c.Seq and IsAppWork = '1' and WorkType12=? ) canApplyCount, b.OfferWorkType2, ";
			params.add(evt.getWorkType12());
		}
			sql += "	ISNULL(t.maxApply,0) max,ISNULL(u.signInCount,0) signInCount"
					+" 	from tCustWorker a  "
					+" 	left join tWorkType12 b on a.WorkType12 = b.Code  "
					+" 	inner join tPrjItem2 c on b.Code = c.WorkType12 and c.Expired='F' "
					+"  left join tCustomer e on e.Code = a.CustCode"
					+"	left join tWorkSign d on d.CustCode = a.CustCode and d.WorkerCode = a.WorkerCode and d.PrjItem2 = c.Code and d.IsComplete='1'";
		if(evt.getCustWkPk() != null && evt.getCustWkPk()>0){
			sql +=" AND CustWkPk=?  ";
			params.add(evt.getCustWkPk());
		}
		sql += " 	LEFT JOIN (select COUNT(*) maxApply,worktype12 from tPrjItem2 where IsAppWork = '1' and WorkType12=? GROUP BY worktype12) t  ON t.worktype12 = a.WorkType12 "
			 + " 	LEFT JOIN (SELECT COUNT(*) signInCount,a.PrjItem2 FROM ( "
			 + " 		select aa.PrjItem2,convert(nvarchar(20), aa.CrtDate, 23) CrtDate "
			 + " 		from dbo.tWorkSign aa WHERE aa.IsComplete='0' AND aa.CustCode=? AND aa.WorkerCode=? ";
		params.add(evt.getWorkType12());
		params.add(evt.getCustCode());
		params.add(evt.getCode());
		if(evt.getCustWkPk() != null && evt.getCustWkPk()>0){
			sql+= " AND aa.CustWkPk=? ";
			params.add(evt.getCustWkPk());
		}
		sql += " 	group by aa.PrjItem2,convert(nvarchar(20), aa.CrtDate, 23) "
			 + " ) a GROUP BY a.PrjItem2) u ON u.PrjItem2 = c.Code"
			 + "	where a.WorkerCode=?  and a.CustCode=? and a.WorkType12=? ";
		params.add(evt.getCode());
		params.add(evt.getCustCode());
		params.add(evt.getWorkType12());
/*		if (StringUtils.isNotBlank(evt.getWorkType2())) {//根据工种分类2选择施工项目 add by zb on 20190704
			sql += " and c.OfferWorkType2=? ";
			params.add(evt.getWorkType2()); 
		}*/
		if(evt.getCustWkPk() != null && evt.getCustWkPk()>0){
			sql +=" AND a.PK=? ";
			params.add(evt.getCustWkPk());
		}
		sql += " ) a "
			 + " ORDER BY a.Seq ";
		return this.findBySql(sql, params.toArray());
	}
	public Map<String,Object> getCustWorkInfo(String workerCode,String custCode,Integer pk){
		List<Object> params = new ArrayList<Object>();
		String sql = " select a.PK,a.CustCode,a.WorkerCode,a.WorkType12,b.Address,b.ProjectMan,c.NameChi,c.CardID,d.OfferWorkType2,e.Descr,c.CardID2,d.confPrjItem, "
					+" tpi1.Descr confPrjItemDescr,d.SalaryCtrl,f.baseSpec " 
					+" from tCustWorker a "
					+" left join tCustomer b on a.CustCode = b.Code "
					+" left join tWorker c on a.WorkerCode = c.Code "
					+" left join tWorkType12 d on a.WorkType12 = d.Code "
					+" left join tWorkType2 e on d.OfferWorkType2 = e.Code "
					+" left join tPrjItem1 tpi1 on tpi1.Code = d.confPrjItem " 
					+" left join tCustType f on f.Code = b.CustType "
					+" where 1=1 ";
		if(StringUtils.isNotBlank(workerCode)){
			sql += " and a.WorkerCode=? ";
			params.add(workerCode);
		}
		if(StringUtils.isNotBlank(custCode)){
			sql += " and a.CustCode=? ";
			params.add(custCode);
		}
		if(pk != null && pk>0){
			sql += " and a.PK=? ";
			params.add(pk);
		}
		List<Map<String,Object>> list =this.findBySql(sql, params.toArray());
		if(list!=null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	public Page<Map<String,Object>> getWokerCostApply(Page<Map<String,Object>> page,WokerCostApplyEvt evt){
		List<Object> list = new ArrayList<Object>();
		String sql = " select b.Address,a.Status,a.ApplyDate,a.PK,"
				    +" case when a.Status='1' then a.WorkAppAmount "
				    +" when a.Status='7' then t.confirmAmount else a.AppAmount end WorkAppAmount, "//,a.WorkAppAmount 修改为根据状态显示工资 update by zzr 2018/04/26
		            +" case when a.Status='1' then '提交' else x1.NOTE end statusDescr "
					+" from tPreWorkCostDetail a "
					+" left join tCustomer b on a.CustCode = b.Code "
					+" left join tXTDM x1 on x1.CBM=a.Status and x1.ID='PRECOSTSTATUS' "
					+" left join tWorkCostDetail t on a.pk=t.refPrePk "
					+" where 1=1 ";
					//+" where a.IsWorkApp='1' ";
		if(StringUtils.isNotBlank(evt.getCustCode())){
			sql += " and b.Code=? ";
			list.add(evt.getCustCode());
		}
		if(StringUtils.isNotBlank(evt.getSalaryType())){
			sql += " and a.SalaryType=? ";
			list.add(evt.getSalaryType());
		}
		if(StringUtils.isNotBlank(evt.getWorkerCode())){
			sql += " and a.WorkerCode=? ";
			list.add(evt.getWorkerCode());
		}
		if(StringUtils.isNotBlank(evt.getWorkType2())){
			sql += " and a.WorkType2=? ";
			list.add(evt.getWorkType2());
		}
		
		if(StringUtils.isNotBlank(evt.getWorkType12())){
			sql += " and exists ( "
				 + " 	select 1 from tPrjItem2 in_a where in_a.WorkType12 = ? and in_a.OfferWorkType2 = a.WorkType2 "
				 + " ) ";
			list.add(evt.getWorkType12());
		}
		
/*		if(evt.getCustWkPk() != null && evt.getCustWkPk() > 0){
			sql += " and a.CustWkPk=? ";
			list.add(evt.getCustWkPk());
		}*/
		sql += " order by a.ApplyDate desc ";
		return this.findPageBySql(page,sql,list.toArray());
	}
	public Map<String,Object> getWorkSignInCount(Integer custWkPk,String custCode,String workerCode){
		List<Object> params = new ArrayList<Object>();
		String sql = " SELECT COUNT(*) todaySignIn "
					+" FROM dbo.tWorkSign "
					+" WHERE CustCode=? AND WorkerCode=? AND IsComplete='0' AND CAST(CrtDate AS DATE)=CAST(GETDATE() AS DATE) ";
		params.add(custCode);
		params.add(workerCode);
		if(custWkPk !=null && custWkPk > 0){
			sql += " AND CustWkPk = ? ";
			params.add(custWkPk);
		}
		List<Map<String,Object>> list = this.findBySql(sql,params.toArray());

		if(list != null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	public Map<String, Object> getAllowSecondSignIn(Integer custWkPk){
		List<Object> params = new ArrayList<Object>();
		String sql = " SELECT datediff(mi,max(CrtDate),getdate()) lastSignInMinAgo  "
					+" FROM dbo.tWorkSign "
					+" WHERE CustWkPk=? AND IsComplete='0' AND CAST(CrtDate AS DATE)=CAST(GETDATE() AS DATE) ";
		params.add(custWkPk);
		
		List<Map<String,Object>> list = this.findBySql(sql,params.toArray());

		if(list != null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	public List<Map<String,Object>> getWokerCostApply(String custCode,String salaryType,String workerCode,Integer custWkPk,String isWorkApp, String workType2, String workType12){
		List<Object> list = new ArrayList<Object>();
		String sql = " select b.Address,a.Status,a.WorkAppAmount,a.ApplyDate,a.PK, "
					+" case when a.Status='1' then '提交' else x1.NOTE end statusDescr, "
					+" case when a.Status in ('6', '7') then c.ConfirmAmount else a.AppAmount end realAmount"
					+" from tPreWorkCostDetail a "
					+" left join tCustomer b on a.CustCode = b.Code "
					+" left join tXTDM x1 on x1.CBM=a.Status and x1.ID='PRECOSTSTATUS' "
					+" left join tWorkCostDetail c on a.PK = c.RefPrePk "
					+" where 1=1 and a.Status!='8' and a.Status!='9' "; // and a.IsWorkApp='1' ";
		if(StringUtils.isNotBlank(custCode)){
			sql += " and b.Code=? ";
			list.add(custCode);
		}
		if(StringUtils.isNotBlank(salaryType)){
			sql += " and a.SalaryType=? ";
			list.add(salaryType);
		}
		if(StringUtils.isNotBlank(workerCode)){
			sql += " and a.WorkerCode=? ";
			list.add(workerCode);
		}
		if(custWkPk!=null && custWkPk>0){
			sql += " and a.CustWkPk=? ";
			list.add(custWkPk);
		}

		if(StringUtils.isNotBlank(isWorkApp)){
			sql += " and a.IsWorkApp=? ";
			list.add(isWorkApp);
		}
		
		if(StringUtils.isNotBlank(workType2)){
			sql += " and a.WorkType2=? ";
			list.add(workType2);
		}
		
		if(StringUtils.isNotBlank(workType12)){
			sql += " and exists( "
				 + " 	select 1 from tPrjItem2 in_a where in_a.OfferWorkType2=a.WorkType2 and in_a.worktype12 = ? and in_a.Expired = 'F' "
				 + " )";
			list.add(workType12);
		}
		
		sql += " order by a.ApplyDate desc ";
		return this.findBySql(sql, list.toArray());
	}

	/**
	 * 工资控制申请，工种分类12模式下，统计所有符合条件的工种分类2工资 modify by zzr 2020/05/11
	 * @param custCode
	 * @param salaryType
	 * @param workerCode
	 * @param custWkPk
	 * @param isWorkApp
	 * @param workType2
	 * @param workType12
	 * @return
	 */
	public List<Map<String,Object>> getWokerCostApplyWorkType12(String custCode,String salaryType,String workerCode,Integer custWkPk,String isWorkApp, String workType2, String workType12){
		List<Object> list = new ArrayList<Object>();
		String sql = " select b.Address,a.Status,a.WorkAppAmount,a.ApplyDate,a.PK, "
					+" case when a.Status='1' then '提交' else x1.NOTE end statusDescr, "
					+" case when a.Status in ('6', '7') then c.ConfirmAmount else a.AppAmount end realAmount"
					+" from tPreWorkCostDetail a "
					+" left join tCustomer b on a.CustCode = b.Code "
					+" left join tXTDM x1 on x1.CBM=a.Status and x1.ID='PRECOSTSTATUS' "
					+" left join tWorkCostDetail c on a.PK = c.RefPrePk "
					+" where 1=1 and a.Status!='8' and a.Status!='9' "; // and a.IsWorkApp='1' ";
		if(StringUtils.isNotBlank(custCode)){
			sql += " and b.Code=? ";
			list.add(custCode);
		}
		if(StringUtils.isNotBlank(salaryType)){
			sql += " and a.SalaryType=? ";
			list.add(salaryType);
		}
		if(StringUtils.isNotBlank(workerCode)){
			sql += " and a.WorkerCode=? ";
			list.add(workerCode);
		}
		if(custWkPk!=null && custWkPk>0){
			sql += " and a.CustWkPk=? ";
			list.add(custWkPk);
		}

		if(StringUtils.isNotBlank(isWorkApp)){
			sql += " and a.IsWorkApp=? ";
			list.add(isWorkApp);
		}
		
		if(StringUtils.isNotBlank(workType2)){
			sql += " and a.WorkType2=? ";
			list.add(workType2);
		}
		
		if(StringUtils.isNotBlank(workType12)){
			sql += " and exists( "
				 + " 	select 1 from tWorkType2 in_a where in_a.Code=a.WorkType2 and in_a.worktype12 = ? "
				 + " )";
			list.add(workType12);
		}
		
		sql += " order by a.ApplyDate desc ";
		return this.findBySql(sql, list.toArray());
	}

	public Map<String,Object> getPrjItem2MaxSeq(String workType12){
		String sql = " SELECT ISNULL(MAX(Seq),0) maxSeq FROM dbo.tPrjItem2 WHERE worktype12=? and Expired='F' ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{workType12});
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	public List<Map<String,Object>> findRegion(Map<String,Object> param){
		List<Object> list = new ArrayList<Object>();
		String sql = "select code id,descr name from tRegion a where a.expired='F'";
		if(StringUtils.isNotBlank((String)param.get("pCode"))){
			sql += " and a.code in ("+param.get("pCode")+") ";
		}
		sql += " order by a.code";
		return this.findBySql(sql, list.toArray());
	}
	
	public List<Map<String,Object>> findRegion2(Map<String,Object> param){
		List<Object> list = new ArrayList<Object>();
		String sql = "select code id,descr name from tRegion2 a where a.expired='F'";
		if(StringUtils.isNotBlank((String)param.get("pCode"))){
			sql += " and a.Regioncode in ("+param.get("pCode")+") ";
		}
		sql += " order by a.code";
		return this.findBySql(sql, list.toArray());
	}
	
	public Page<Map<String,Object>> findOnDoDetailPageBySql(Page<Map<String,Object>> page, String workerCode,String department2) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select a.*,wt.descr workTypedescr,case when a.status='1' then '施工' else '停工' end  statusDescr,c.begindate,c.descr custDescr,c.address,c.area,x1.note layoutdescr,x2.note custtypedescr," +
				" e.namechi projectmandescr,d1.desc1 depa1descr,d2.desc1 depa2descr, " +
				" s.MinCrtDate, s.MaxCrtDate, g.Descr SignPrjItem2, h.NOTE IsCompleteDescr " +
				" from  tCustWorker a " +
				" left join tcustomer c on c.code=a.custcode " +
				" left  join tWorkType12 wt on wt.code=a.Worktype12" +
				" left join temployee e on e.number =c.projectman " +
				" left join tdepartment d1 on d1.code=e.department1" +
				" left join tdepartment d2 on d2.code=e.department2 " +
				" left join txtdm x1 on x1.cbm=c.layout and x1.id='LAYOUT' " +
				" left join txtdm x2 on x2.cbm=c.CustType and x2.id='CUSTTYPE' " +
				" left join txtdm x3 on x3.cbm=a.status and x3.ID='CUSTWKSTATUS'" +
				" left join (select in_a.CustCode, in_b.WorkType12, " +
				"     max(in_a.PK) MaxPk, max(in_a.CrtDate) MaxCrtDate, min(in_a.CrtDate) MinCrtDate " +
                "     from tWorkSign in_a " +
                "         left join tCustWorker in_b on in_a.CustWkPk = in_b.PK " +
                "     group by in_a.CustCode, in_b.WorkType12) s on s.CustCode = a.CustCode and s.WorkType12 = a.WorkType12 " +
                " left join tWorkSign f on s.MaxPk = f.PK " +
                " left join tPrjItem2 g on f.PrjItem2 = g.Code " +
                " left join tXTDM h on f.IsComplete = h.CBM and h.ID = 'YESNO' " +
				" where not exists( select 1 from tWorkSign in_a " +
				"     left join tPrjItem2 in_b on in_a.PrjItem2=in_b.Code " +
				"     where in_a.IsComplete='1' and in_a.CustWkPk=a.Pk "+
                "     group by in_a.CustWkPk, in_b.worktype12 " +
                "     having count(*)=(select count(1) from tPrjItem2 where worktype12=in_b.worktype12) ) " +
                " and a.Expired='F' and a.endDate is null and c.status='4'  ";
		
		if(StringUtils.isNotBlank(workerCode)){
			sql+=" and a.workerCode = ? ";
			list.add(workerCode);
		}
		if(StringUtils.isNotBlank(department2)){
			sql += " and e.department2 in " + "('"+department2.replace(",", "','" )+ "')";
		}
		sql+=" union " ;
		sql+="select a.*,wt.descr workTypedescr,'完工' statusDescr,c.begindate,c.descr custDescr,c.address,c.area,x1.note layoutdescr,x2.note custtypedescr," +
			" e.namechi projectmandescr,d1.desc1 depa1descr,d2.desc1 depa2descr, " +
			" s.MinCrtDate, s.MaxCrtDate, g.Descr SignPrjItem2, h.NOTE IsCompleteDescr " +
			" from  tCustWorker a " +
			" left join tcustomer c on c.code=a.custcode " +
			" left  join tWorkType12 wt on wt.code=a.Worktype12" +
			" left join temployee e on e.number =c.projectman " +
			" left join tdepartment d1 on d1.code=e.department1" +
			" left join tdepartment d2 on d2.code=e.department2 " +
			" left join txtdm x1 on x1.cbm=c.layout and x1.id='LAYOUT' " +
			" left join txtdm x2 on x2.cbm=c.CustType and x2.id='CUSTTYPE' " +
			" left join txtdm x3 on x3.cbm=a.status and x3.ID='CUSTWKSTATUS'" +
			" left join (select in_a.CustCode, in_b.WorkType12, " +
            "     max(in_a.PK) MaxPk, max(in_a.CrtDate) MaxCrtDate, min(in_a.CrtDate) MinCrtDate " +
            "     from tWorkSign in_a " +
            "         left join tCustWorker in_b on in_a.CustWkPk = in_b.PK " +
            "     group by in_a.CustCode, in_b.WorkType12) s on s.CustCode = a.CustCode and s.WorkType12 = a.WorkType12 " +
            " left join tWorkSign f on s.MaxPk = f.PK " +
            " left join tPrjItem2 g on f.PrjItem2 = g.Code " +
            " left join tXTDM h on f.IsComplete = h.CBM and h.ID = 'YESNO' " +
			" where exists( select 1 from tWorkSign in_a " +
			"     left join tPrjItem2 in_b on in_a.PrjItem2=in_b.Code " +
			"     where in_a.IsComplete='1' and in_a.CustWkPk=a.Pk "+
            "     group by in_a.CustWkPk, in_b.worktype12 " +
            "     having count(*)=(select count(1) from tPrjItem2 where worktype12=in_b.worktype12) ) " +
            " and a.Expired='F' and a.endDate is null and c.status='4' ";
		if(StringUtils.isNotBlank(workerCode)){
			sql+=" and a.workerCode = ? ";
			list.add(workerCode);
		}
		if(StringUtils.isNotBlank(department2)){
			sql += " and e.department2 in " + "('"+department2.replace(",", "','" )+ "')";
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += "  order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += "  order by a.pk";
		}
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		resultList=this.findListBySql(sql, list.toArray());
		page.setResult(resultList);
		page.setTotalCount(resultList.size());
		return page;
	}
	
	public Page<Map<String,Object>> findWorkerWorkType12PageBySql(Page<Map<String,Object>> page, String workerCode) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select a.WorkType12 Code,b.Descr WorkType12Descr from tWorkerWorkType12 a " 
				+ " left join tWorkType12 b on a.WorkType12 = b.Code "
				+ " where a.workerCode = ? ";
		list.add(workerCode);
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> findWorkType12PageBySql(Page<Map<String,Object>> page, String workType12Strings) {
		List<Object> list = new ArrayList<Object>();
		String sql = " select a.Code,a.Descr WorkType12Descr from tWorkType12 a " 
				+ " where a.Expired = 'F' ";
		if(StringUtils.isNotBlank(workType12Strings)){
			sql += " and a.Code not in ( "+SqlUtil.resetStatus(workType12Strings)+")";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

//  add by hc  各种施工情况分析   2017/12/04   begin 	
	//通过操作员编号取到该员工应有的工种类型12，然后在页面进入的时候   默认选中所有的已经有的工种类型
	public List<Map<String,Object>> getWorkType12(String czyBH){
		String sql="select ( "
				+ " select rtrim(Code)+','  from tWorkType12 a where "    
				+ " Code in( select rtrim(Code)Code from tWorkType12 a where  ( "
				+ "	(select PrjRole from tCZYBM where CZYBH=?) is null "
				+ "	or( select PrjRole from tCZYBM where CZYBH=?) ='' ) or  Code in( "
				+ "		select WorkType12 From tprjroleworktype12 pr where pr.prjrole = "
				+ "		(select PrjRole from tCZYBM where CZYBH=?) or pr.prjrole = '' "
				+ "		 ) ) "
				+ " for xml path('')) as Code";		
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{czyBH,czyBH,czyBH});
		if (list!=null && list.size()>0){
			Map<String, Object> map = new HashMap<String, Object>();
			 return list;
		}
		return null;
	}
	//在建工地查看
	public Page<Map<String,Object>> findPageBySqlbuilder(Page<Map<String,Object>> page, Worker worker) {
		List<Object> list = new ArrayList<Object>();

		String sql =" select b.Address,b.CustType,x1.NOTE CustTypeDescr,b.Layout,x2.NOTE LayoutDescr, " +
					" b.Area,a.WorkType12,c.Descr WorkType12Descr,b.ConfirmBegin,a.ComeDate," +
					" a.PlanEnd,a.LastUpdate ,MaxWs.MaxCrtDate,MinWs.MinCrtDate " +
					" ,a.ConPlanEnd,(select top 1 crtdate from tWorkSign where iscomplete ='1' and CustWkPk = a.pk)EndDate,x3.Note constructStatusDescr " +
					" from tCustworker a " +
					" left join tCustomer b on a.CustCode=b.Code " +
					" left join tEmployee e on b.ProjectMan=e.Number " +
					" left join tXTDM x1 on b.CustType=x1.CBM and x1.ID='CUSTTYPE' " +
					" left join tXTDM x2 on b.Layout=x2.CBM and x2.ID='LAYOUT' " +
					" left join tWorkType12 c on c.Code=a.WorkType12 " +
					" left join tWorker wk on a.WorkerCode=wk.Code " +
					" left join (" +
					"   select b.PK,max(a.CrtDate)MaxCrtDate " +
					"   from tWorkSign a " +
					"   left join tCustWorker b on b.PK=a.CustWkPk" +
					"   left join tWorkType12 d on d.Code=b.WorkType12 " +
					"   group by b.PK" +
					") MaxWs on a.PK = MaxWs.PK" +
					" left join (" +
					"   select  b.PK,min(a.CrtDate)MinCrtDate " +
					"   from tWorkSign a " +
					"   left join tCustWorker b on b.PK=a.CustWkPk" +
					"   left join tWorkType12 d on d.Code=b.WorkType12 " +
					"   group by b.PK" +
					") MinWs on a.PK = MinWs.PK " +
					" left join tXtdm x3 on x3.Cbm = a.status and x3.id = 'CUSTWKSTATUS'" +
				
					" where 1=1 and a.EndDate is null and b.status='4' " +
					" and a.WorkType12 <>'' and a.WorkType12 is not null "; // @xzy  判断条件加了tcutomer.status='4'  and tCustWorker.workType12 不为空 、null
		if (StringUtils.isNotBlank(worker.getCode())) {
			sql += " and a.workercode= ? ";
			list.add(worker.getCode());
		}
		if (StringUtils.isNotBlank(worker.getDepartment2())) {
			sql += " and e.Department2 in  ("+SqlUtil.resetStatus(worker.getDepartment2())+")";
		}
		if("1".equals(worker.getStatisticalMethods())){
			sql+=" and a.WorkType12 = ? ";
			list.add(worker.getWorkType12());
		}else if("2".equals(worker.getStatisticalMethods())){
			sql+=" and wk.WorkType12 = ? ";
			list.add(worker.getWorkType12());
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	//安排工地查看
		public Page<Map<String,Object>> findPageBySqlarrange(Page<Map<String,Object>> page, Worker worker) {
			List<Object> list = new ArrayList<Object>();

			String sql =" select b.Address,b.CustType,x1.NOTE CustTypeDescr,b.Layout,x2.NOTE LayoutDescr, "
						+ " b.Area,cwa.AppDate,cwa.AppComeDate,a.ComeDate,a.LastUpdate, e.NameChi projectManDescr, "
						+ " d1.Desc1 depa1descr, d2.Desc1 depa2descr,MaxWs.MaxCrtDate,MinWs.MinCrtDate from tCustworker a "
						+ " left join tCustomer b on a.CustCode=b.Code "
						+ " left join tEmployee e on e.Number = b.ProjectMan "
						+ " left join tDepartment1 d1 on d1.Code = e.Department1 "
						+ " left join tDepartment2 d2 on d2.Code = e.Department2 "
						+ " left join tCustWorkerApp cwa on cwa.CustWorkPk = a.PK "
						+ " left join tWorker wk on a.WorkerCode=wk.Code "
						+ " left join (" 
						+ "   select b.PK,max(a.CrtDate)MaxCrtDate "
						+ "   from tWorkSign a " 
						+ "   left join tCustWorker b on b.PK=a.CustWkPk " 
						+ "   left join tWorkType12 d on d.Code=b.WorkType12 " 
						+ "   group by b.PK " 
						+ " ) MaxWs on a.PK = MaxWs.PK " 					
						+ " left join (" 
						+ "	  select  b.PK,min(a.CrtDate)MinCrtDate " 
						+ "   from tWorkSign a "
						+ "   left join tCustWorker b on b.PK=a.CustWkPk" 
						+ "   left join tWorkType12 d on d.Code=b.WorkType12 " 
						+ "   group by b.PK" 
						+ " ) MinWs on a.PK = MinWs.PK " 
						+ " left join tXTDM x1 on b.CustType=x1.CBM and x1.ID='CUSTTYPE' "
						+ " left join tXTDM x2 on b.Layout=x2.CBM and x2.ID='LAYOUT' "
						+ " where 1=1  ";
			if (StringUtils.isNotBlank(worker.getCode())) {
				sql += " and a.workercode= ? ";
				list.add(worker.getCode());
			}
			if ((worker.getBeginDate()) != null) {
				sql += " and a.ComeDate >= ? ";
				list.add(worker.getBeginDate());
			}
			if ((worker.getEndDate()) != null) {
				sql += " and a.ComeDate< DATEADD(DAY,1,?) ";
				list.add(worker.getEndDate());
			}
			if (StringUtils.isNotBlank(worker.getDepartment2())) {
				sql += " and e.Department2 in  ("+SqlUtil.resetStatus(worker.getDepartment2())+")";
			}
			if("1".equals(worker.getStatisticalMethods())){
				sql+=" and a.WorkType12 = ? ";
				list.add(worker.getWorkType12());
			}else if("2".equals(worker.getStatisticalMethods())){
				sql+=" and wk.WorkType12 = ? ";
				list.add(worker.getWorkType12());
			}
			if (StringUtils.isNotBlank(page.getPageOrderBy())){
				sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
			}else{
				sql += " order by b.Address";
			}		
			return this.findPageBySql(page, sql, list.toArray());
		}
	//完工工地查看
		public Page<Map<String,Object>> findPageBySqlcomplete(Page<Map<String,Object>> page, Worker worker) {
			List<Object> list = new ArrayList<Object>();
			String sql =" select c.Address, x2.NOTE custtypedescr, x1.NOTE layoutDescr, c.Area, "+ 
					" 		a.ComeDate, b.LastUpdate confDate, datediff(D, a.ComeDate, a.EndDate)+1 consDays, "+
					"           b.LastUpdate, b.prjleveldescr, e.NameChi projectmandescr, d1.Desc1 Depa1descr, d2.Desc1 Depa2Descr, "+
					"           e2.NameChi confDescr,a.PlanEnd,a.EndDate,c1.confirmAmount ,MaxWs.MaxCrtDate,MinWs.MinCrtDate,a.PK," +
					"           a.ConstructDay,isnull(ws.SignNum,0)SignNum,case when a.WorkType12 in('05','20','02','13','14')then case when isnull(c.InnerArea,0)<>0 then c.InnerArea else c.Area*ct.InnerAreaPer end when a.WorkType12='01' then qqWorkLoad "+ 
					"           when a.WorkType12='03' then smWorkLoad when a.WorkType12='11' then fsWorkLoad when a.WorkType12='12' then zpWorkLoad else 0 end WorkLoad "+
					"    from   tCustWorker a "+					
					"   left  join tCustomer c on c.Code = a.CustCode "+
					"   left join tCustType ct on c.CustType=ct.Code "+
					"	left join (select sum(confirmAmount)confirmAmount,CustCode from "+ 
			        "        tWorkCostDetail group by CustCode)c1 on c.Code=c1.CustCode "+
					"   left join (select   a.LastUpdatedBy, a.prjLevel, x1.NOTE prjleveldescr, a.CustCode, c.Code worktype12, "+
					"                       max(a.LastUpdate) lastupdate "+
					"               from     tPrjProgConfirm a "+
					"                       left join tPrjItem1 b on b.Code = a.PrjItem "+
					"                       left join tWorkType12 c on  c.PrjItem = b.Code "+
					"                       left join tXTDM x1 on x1.CBM = a.prjLevel "+
					"                                             and x1.ID = 'PRJLEVEL' "+
					"               where    a.IsPass = '1' "+
					"              group by a.LastUpdatedBy, a.prjLevel, x1.NOTE, a.CustCode, c.Code "+
					"              ) b on b.CustCode = a.CustCode "+
					"                    and a.WorkType12 = b.worktype12 "+
					"   left join tEmployee e on e.Number = c.ProjectMan "+
					"   left join tDepartment1 d1 on d1.Code = e.Department1 "+
					"   left join tDepartment2 d2 on d2.Code = e.Department2 "+
					" left join tWorker wk on a.WorkerCode=wk.Code "+
					" left join (" +
					"   select b.PK,max(a.CrtDate)MaxCrtDate " +
					"   from tWorkSign a " +
					"   left join tCustWorker b on b.PK=a.CustWkPk" +
					"   left join tWorkType12 d on d.Code=b.WorkType12 " +
					"   group by b.PK" +
					") MaxWs on a.PK = MaxWs.PK" +
					" left join (" +
					"   select  b.PK,min(a.CrtDate)MinCrtDate " +
					"   from tWorkSign a " +
					"   left join tCustWorker b on b.PK=a.CustWkPk" +
					"   left join tWorkType12 d on d.Code=b.WorkType12 " +
					"   group by b.PK" +
					") MinWs on a.PK = MinWs.PK " +
					"   left join tXTDM x1 on x1.CBM = c.Layout "+
					"                         and x1.ID = 'LAYOUT' "+
					"   left join tXTDM x2 on x2.CBM = c.CustType "+
					"                         and x2.ID = 'CUSTTYPE' "+
					"   left join tWorkType12 wt on wt.Code = a.WorkType12 "+
					"   left join temployee e2 on e2.Number = b.LastUpdatedBy "+
					"   left join (select COUNT(*)SignNum,a.CustWkPk from (	 "+					
					"   select  a.CustWkPk from tWorkSign a "+
					"   where a.CrtDate>=? and a.CrtDate< DATEADD(day,1,CONVERT(varchar(100),?, 23))  "+			      			
					"   group by a.CustWkPk,CONVERT(varchar(100), a.crtDate, 112))a group by a.CustWkPk ) ws on a.Pk=ws.CustWkPk "+
					"   left join (select sum(a.Qty) qqWorkLoad,a.CustCode "+ 
					"   from tBaseItemReq a "+
					"   inner join tBaseItem b on a.BaseItemCode=b.Code where b.PrjType='1'  group by a.CustCode) qqwl on a.CustCode=qqwl.CustCode "+
					"   left join (select sum(a.Qty) smWorkLoad,a.CustCode "+ 
					"   from tBaseItemReq a "+
					"   inner join tBaseItem b on a.BaseItemCode=b.Code where b.PrjType='2'  group by a.CustCode) smwl on a.CustCode=smwl.CustCode "+
					"   left join (select sum(a.Qty) zpWorkLoad,a.CustCode "+ 
					"   from tBaseItemReq a "+
					"   inner join tBaseItem b on a.BaseItemCode=b.Code where b.PrjType='3'  group by a.CustCode) zpwl on a.CustCode=zpwl.CustCode "+
					"   left join (select sum(a.Qty) fsWorkLoad,a.CustCode "+ 
					"   from tBaseItemReq a "+
					"   inner join tBaseItem b on a.BaseItemCode=b.Code " +
					"   left join tBaseItemType2 c  on b.BaseItemType2=c.Code  " + 
					"   left join tWorkType2 e on e.Code=c.MaterWorkType2 "	+
					"   where e.code ='010' group by a.CustCode) fswl on a.CustCode=fswl.CustCode "+
					"   where  1 = 1  ";   
			list.add(worker.getBeginDate());
			list.add(worker.getEndDate());
			if (StringUtils.isNotBlank(worker.getCode())) {
				sql += " and a.workercode= ? ";
				list.add(worker.getCode());
			}
			if ((worker.getBeginDate()) != null) {
				sql += " and a.Enddate >= ? ";
				list.add(worker.getBeginDate());
			}
			if ((worker.getEndDate()) != null) {
				sql += " and a.Enddate< DATEADD(DAY,1,?) ";
				list.add(worker.getEndDate());
			}
			if (StringUtils.isNotBlank(worker.getDepartment2())) {
				sql += " and e.Department2 in  ("+SqlUtil.resetStatus(worker.getDepartment2())+")";
			}
			if("1".equals(worker.getStatisticalMethods())){
				sql+=" and a.WorkType12 = ? ";
				list.add(worker.getWorkType12());
			}else if("2".equals(worker.getStatisticalMethods())){
				sql+=" and wk.WorkType12 = ? ";
				list.add(worker.getWorkType12());
			}
			if (StringUtils.isNotBlank(page.getPageOrderBy())){
				sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
			}else{
				sql += " order by c.Address";
			}	
			return this.findPageBySql(page, sql, list.toArray());
		}
	//验收不通过查看
		public Page<Map<String,Object>> findPageBySqlnoPass(Page<Map<String,Object>> page, Worker worker) {
			List<Object> list = new ArrayList<Object>();

			String sql =" select a.WorkerCode,a.CustCode,b.Address, b.CustType, "+ 
					" x1.NOTE CustTypeDescr, b.Layout, x2.NOTE LayoutDescr, "+ 
					" b.Area,d.Descr WorkType12Descr,c.LastUpdatedBy,e.NameChi confDescr,c.Date,c.Remarks,MaxWs.MaxCrtDate,MinWs.MinCrtDate from tCustWorker a "+ 
					" left join (select a.Code,p2.PrjItem from tWorker a " + 
					" 			left join tPrjItem1 p1 on a.WorkType12=p1.worktype12 " +
					" 			left join tPrjProgConfirm p2 on p1.Code=p2.PrjItem and p2.IsPass='0' " +
					" 			group by a.Code,p2.PrjItem" +
					"		)a1 on a.WorkerCode = a1.Code " + 
					" left join tPrjProgConfirm c on a.CustCode= c.CustCode and c.PrjItem=a1.PrjItem "+
					" left join tCustomer b on a.CustCode=b.Code "+
					
					" left join (select  a.CustCode,max(a.CrtDate)MaxCrtDate,d.Code from tWorkSign a " +
					" left join tCustWorker b on b.PK=a.CustWkPk" +
					" left join tWorkType12 d on d.Code=b.WorkType12 " +
					" group by a.CustCode,d.Code) MaxWs on a.custCode=MaxWs.custCode and a.workType12 = maxWs.code" +
					
					" left join (select  a.CustCode,min(a.CrtDate)MinCrtDate,d.Code from tWorkSign a " +
					"  left join tCustWorker b on b.PK=a.CustWkPk" +
					" left join tWorkType12 d on d.Code=b.WorkType12 " +
					" group by a.CustCode,d.Code) MinWs on a.custCode=MinWs.custCode and a.workType12 = minWs.code " +

					" left join tXTDM x1 on b.CustType = x1.CBM "+
					" 			  and x1.ID = 'CUSTTYPE' "+
					" left join tXTDM x2 on b.Layout = x2.CBM "+
					" 			  and x2.ID = 'LAYOUT' "+
					" left join tPrjItem1 p1 on c.PrjItem=p1.Code "+
					" left join tWorkType12 d on d.Code = p1.WorkType12 "+
					" left join tEmployee e on c.LastUpdatedBy=e.Number "+ 
					" left join tEmployee e2 on b.ProjectMan=e2.Number "+ 
					" left join tWorker wk on a.WorkerCode=wk.Code "+
					" where 1=1 " ;
					if (StringUtils.isNotBlank(worker.getCode())) {
						sql += " and a.workercode= ? ";
						list.add(worker.getCode());
					}
					if ((worker.getBeginDate()) != null) {
						sql += " and c.date >= ? ";
						list.add(worker.getBeginDate());
					}
					if ((worker.getEndDate()) != null) {
						sql += " and c.date< DATEADD(DAY,1,?) ";
						list.add(worker.getEndDate());
					}
					if (StringUtils.isNotBlank(worker.getDepartment2())) {
						sql += " and e2.Department2 in  ("+SqlUtil.resetStatus(worker.getDepartment2())+")";
					}
					if("1".equals(worker.getStatisticalMethods())){
						sql+=" and a.WorkType12 = ? ";
						list.add(worker.getWorkType12());
					}else if("2".equals(worker.getStatisticalMethods())){
						sql+=" and wk.WorkType12 = ? ";
						list.add(worker.getWorkType12());
					}
				sql +=" and c.IsPass='0' "+
					" group by a.WorkerCode,a.CustCode ,b.Address, b.CustType, x1.NOTE, "+
					" b.Layout, x2.NOTE , b.Area,d.Descr,c.LastUpdatedBy,e.NameChi,c.Date ,c.Remarks,MaxWs.MaxCrtDate,MinWs.MinCrtDate  "; 
				if (StringUtils.isNotBlank(page.getPageOrderBy())){
					sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
				}else{
					sql += " order by b.Address";
				}	
				return this.findPageBySql(page, sql, list.toArray());
		}
	//5、工资明细查看
		public Page<Map<String,Object>> findPageBySqlconfirmAmount(Page<Map<String,Object>> page, Worker worker) {
			List<Object> list = new ArrayList<Object>();

			String sql =" select  c.code,c.Address,c.CustType,x1.NOTE CustTypeDescr,c.Area,a.AppAmount, "+ 
					" a.ConfirmAmount,a.LastUpdate from tWorkCostDetail a  "+
					" left join tworkcost b on a.no=b.no "+
					" left join tCustomer c on a.CustCode=c.Code "+
					" left join tEmployee e on c.ProjectMan=e.Number "+
					" left join tXTDM x1 on c.CustType=x1.CBM and x1.ID='CUSTTYPE' "+
					" left join tWorker f on a.WorkerCode=f.Code "+
					" left join tWorkType2 g on g.Code = a.WorkType2 "+
					" where a.Status='2' " ;
				if (StringUtils.isNotBlank(worker.getCode())) {
					sql += " and a.WorkerCode= ? ";
					list.add(worker.getCode());
				}
				if ((worker.getBeginDate()) != null) {
					sql += " and b.PayDate >= ? ";
					list.add(worker.getBeginDate());
				}
				if ((worker.getEndDate()) != null) {
					sql += " and b.PayDate< DATEADD(DAY,1,?) ";
					list.add(worker.getEndDate());
				}
				if (StringUtils.isNotBlank(worker.getDepartment2())) {
					sql += " and e.Department2 in  ("+SqlUtil.resetStatus(worker.getDepartment2())+")";
				}
				if(StringUtils.isNotBlank(worker.getWorkType12())){
					if("1".equals(worker.getStatisticalMethods())){
						sql += " and g.WorkType12 = ? ";
					} else {
						sql+=" and f.WorkType12 = ? ";
					}
					list.add(worker.getWorkType12());
				}
				if (StringUtils.isNotBlank(page.getPageOrderBy())){
					sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
				}else{
					sql += " order by c.Address";
				}	
			return this.findPageBySql(page, sql, list.toArray());
		}
	//签到天数查看
		public Page<Map<String,Object>> findPageBySqlcrtDate(Page<Map<String,Object>> page, Worker worker) {
			List<Object> list = new ArrayList<Object>();

			String sql =" select b.Address,a.CrtDate,a.PrjItem2,c.Descr PrjItem2Descr,a.IsComplete,x1.NOTE IsCompleteDescr from tWorkSign a "+
					" left join tCustomer b on a.CustCode=b.Code  "+
					" left join tEmployee e on b.ProjectMan=e.Number "+
					" left join tPrjItem2 c on a.PrjItem2=c.Code "+
					" left join tXTDM x1 on a.IsComplete=x1.CBM and x1.ID='YESNO' "+
					" where 1=1 "; 
					if (StringUtils.isNotBlank(worker.getCode())) {
						sql += " and a.WorkerCode= ? ";
						list.add(worker.getCode());
					}
					if ((worker.getBeginDate()) != null) {
						sql += " and a.CrtDate >= ? ";
						list.add(worker.getBeginDate());
					}
					if ((worker.getEndDate()) != null) {
						sql += " and a.CrtDate< DATEADD(DAY,1,?) ";
						list.add(worker.getEndDate());
					}	
					if (StringUtils.isNotBlank(worker.getAddress())) {
						sql += " and b.address like ? ";
						list.add("%"+worker.getAddress()+"%");
					} 
					if (StringUtils.isNotBlank(worker.getDepartment2())) {
						sql += " and e.Department2 in  ("+SqlUtil.resetStatus(worker.getDepartment2())+")";
					}
					if("1".equals(worker.getStatisticalMethods())){
						sql+=" and exists (select 1 from tCustWorker in_a where in_a.Pk=a.CustWkPk and in_a.WorkType12=? ) ";
						list.add(worker.getWorkType12());
					}else if("2".equals(worker.getStatisticalMethods())){
						sql+=" and exists (select 1 from tWorker in_a where in_a.Code=a.WorkerCode and in_a.WorkType12=? ) ";
						list.add(worker.getWorkType12());
					}
					if (StringUtils.isNotBlank(page.getPageOrderBy())){
						if ("prjitem2descr".equals(page.getPageOrderBy())){
							sql += " order by c.descr "+page.getPageOrder();
						}else if ("iscompletedescr".equals(page.getPageOrderBy())){
							sql += " order by x1.Note "+page.getPageOrder();	
						}
						else{
							sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
						}
					}					
					else{
						sql += " order by a.CrtDate";
					}	
			return this.findPageBySql(page, sql, list.toArray());
		}
	/**
	 * 班组施工分析
	 * @param page
	 * @param pBzsgfx
	 * @return
	 */
	
	
	
	
	@SuppressWarnings("deprecation")
	public Page<Map<String,Object>> findworkTypeConstructDetail(Page<Map<String,Object>> page, Worker worker,String orderBy,String direction) {
		Assert.notNull(worker);
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			//增加统计方式 add by zb on 20200221
			if ("1".equals(worker.getStatisticalMethods())) {
				call = conn.prepareCall("{Call pBzsgfx(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");//pBzsgfx 班组施工分析
			} else if ("2".equals(worker.getStatisticalMethods())) {
				call = conn.prepareCall("{Call pBzsgfx_byWorkerCode(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");//pBzsgfx 班组施工分析——按工人工种统计
			}
			call.setInt(1, page.getPageNo());
			call.setInt(2,page.getPageSize());
			call.setString(3, worker.getWorkType12());
			call.setString(4, worker.getIsSign());
			call.setString(5, worker.getCode());
			call.setString(6, worker.getExpired());
			call.setString(7, worker.getIsLeave());
			call.setTimestamp(8, new java.sql.Timestamp(worker.getBeginDate().getTime()));
			call.setTimestamp(9, new java.sql.Timestamp(worker.getEndDate().getTime()));
			call.setString(10, orderBy);
			call.setString(11, direction);
			call.setString(12, worker.getCustType());
			call.setString(13, worker.getHasBuild());
			call.setString(14, worker.getWorkerClassify());
			call.setString(15, worker.getPrjRegionCode());
			call.setString(16, worker.getWorkType12Dept());
			call.setString(17, worker.getDepartment2());
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
	
	// add by hc 2017/12/04   各种施工情况分析

	public Map<String,Object> getCanApplyTimes(AddWokerCostEvt evt){
		String val = "";
		String sql = " select count(*) canApplyTimes "
				   + " from tPrjItem2 pi2 "
				   + " where pi2.IsAppWork='1' ";
	   if (StringUtils.isNotBlank(evt.getIsNew())) {
/*		  sql += " and pi2.OfferWorkType2=? ";
		  val = evt.getWorkType2();*/
		   sql += " and pi2.WorkType12 = ? ";
		   val = evt.getWorkType12();
	   } else {
		   sql += " and exists( "
			   + " 		select 1 from tWorker where Code=? and WorkType12 = pi2.worktype12 "
			   + " ) ";
		   val = evt.getWorkerCode();
	   }
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{val});
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	public String getBefWorkType12Emp(String code,String workType12){
		String sql = ""
		        + "select c.WorkerCode "
		        + "from tWorkType12 a "
		        + "left join tWorkType12 b on b.Code = a.BefSameWorkType12 "
		        + "left join tCustWorker c on c.WorkType12 = b.Code "
		        + "where a.Code = ? "
		        + "    and c.CustCode = ? "
		        + "order by c.ComeDate desc ";
		
		List<Map<String, Object>> list = this.findBySql(sql, workType12, code);
		
		if (list != null && list.size() > 0) {
			return list.get(0).get("WorkerCode").toString();
		}
		
		return null;
	}

	public List<Map<String, Object>> getWorkPrjItemList(Integer custWkPk){
		String sql = " select * "
				   + " from ( " 
				   + " 		select tpi2.Descr prjItem2Descr,(select count(*) from ( select 1 oneTimes from tWorkSign where CustWkPk=tcw.PK and PrjItem2 = tpi2.Code and IsComplete <> '1' group by convert(nvarchar(20), CrtDate, 23)) out_a ) signCount, "
				   + "		(select min(CrtDate) from tWorkSign where CustWkPk=tcw.PK and PrjItem2 = tpi2.Code and IsComplete <> '1') beginDate, "
				   + " 		(select min(CrtDate) from tWorkSign where CustWkPk=tcw.PK and PrjItem2 = tpi2.Code and IsComplete='1') endDate,tpi2.Seq "
				   + " 		from tCustWorker tcw "
				   + " 		inner join tPrjItem2 tpi2 on tpi2.worktype12 = tcw.WorkType12 and tpi2.Expired='F' "
				   + " 		where tcw.PK=? "
				   + " ) a,( "
				   + " 		select dbo.fGetWorkTypeConDay(tcw.CustCode,tcw.WorkType12) contructDay, "
				   + " 		(case when exists( "
				   + " 			select 1 from tWorkSign where CustWkPk=tcw.PK " 
				   + " 			and PrjItem2 = ( "
				   + " 				select Code "
				   + " 				from tPrjItem2 " 
				   + " 				where worktype12=tcw.WorkType12 " 
				   + " 				and IsComplete='1' "
				   + " 				and Seq = (select max(Seq) from tPrjItem2 where worktype12 = tcw.WorkType12) "
				   + " 			) "
				   + "		) then datediff(day, (select min(CrtDate) from tWorkSign where IsComplete<>'1' and CustWkPk=tcw.PK), "
				   + " 							(select max(CrtDate) from tWorkSign where IsComplete='1' and CustWkPk=tcw.PK))  "
				   + " 		else datediff(day,(select min(CrtDate) from tWorkSign where IsComplete<>'1' and CustWkPk=tcw.PK), getdate()) end)+1 allSign "
				   + " 		from tCustWorker tcw "
				   + " 		where tcw.PK=? "
				   + " ) b "
				   + " order by a.Seq ";
		return this.findBySql(sql, new Object[]{custWkPk, custWkPk});
	}
	
	public Map<String, Object> existPrjProgConfirm(Integer custWkPk){
		String sql = " select case when twt12.confPrjItem is not null and twt12.confPrjItem <> '' and twt12.confPrjItem <> 'null' then "
				   + " case when exists( "
				   + " 		select 1 from tPrjProgConfirm "
				   + " 		where CustCode=tcw.CustCode " 
				   + " 		and PrjItem = twt12.confPrjItem"
				   + " ) then '1' else '0' end "
				   + " else '1' end existConfirm,tpi1.Descr "
				   + " from tCustWorker tcw "
				   + " left join tWorkType12 twt12 on twt12.Code = tcw.WorkType12 "
				   + " left join tPrjItem1 tpi1 on tpi1.Code = twt12.confPrjItem "
				   + " where PK=? ";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[]{custWkPk});
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	/**
	 * 工人信息添加界面查询idnum
	 * @param idnum
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Worker getByIdnum(String idnum) {
		String hql = "from Worker a where a.idnum=?";
		List<Worker> list = this.find(hql, new Object[]{idnum});
		if (list != null && list.size() > 0) 
			return list.get(0);
		else
			return null;
	}
	
	public Page<Map<String, Object>> getWorkerProblemList(
			Page<Map<String, Object>> page, Integer custWkPk){
		List<Object> list = new ArrayList<Object>();	
		String sql = " select a.custWkPk,a.date,x1.note typeDescr,a.no,a.type,a.remark,a.stopDay" +
				" from tworkerProblem a" +
				" left join txtdm x1 on x1.cbm = a.Type and x1.id='WKPBLTYPE' " +
				"where CustWkPk = ? ";
		list.add(custWkPk==null?"":custWkPk);
		
		sql +=" order by a.no desc ";
		return this.findPageBySql(page, sql,list.toArray());
	}

	/**
	 * 工人信息存储过程
	 * @author	created by zb
	 * @date	2018-7-17--下午4:30:52
	 * @param worker
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result doSave(Worker worker) {
		Assert.notNull(worker);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall(
				"{Call pGrXxkGl_forProc(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, worker.getM_umState());/*操作类型*/
			call.setString(2, worker.getCode());
			call.setString(3, worker.getNameChi());
			call.setString(4, worker.getPhone());
			call.setString(5, worker.getIdnum());
			call.setString(6, worker.getCardId());
			call.setString(7, worker.getCardId2());
			call.setString(8, worker.getWorkType12());
			call.setString(9, worker.getIntroduceEmp());
			call.setString(10, worker.getRemarks());
			call.setString(11, worker.getIsSign());
			call.setString(12, worker.getEmpCode());
			call.setTimestamp(13, worker.getSignDate()==null?null : new Timestamp(worker.getSignDate().getTime()));/*由long转为time*/
			call.setString(14, worker.getLevel());
			call.setString(15, worker.getLastUpdatedBy());
			call.setString(16, worker.getExpired());
			call.setString(17, worker.getActionLog());
			call.setString(18, worker.getLiveRegion());
			call.setString(19, worker.getLiveRegion2());
			call.setDouble(20, worker.getNum()==null? 0.0 : worker.getNum());
			call.setString(21, worker.getDepartment1());
			call.setString(22, worker.getSpcBuilder());
			call.setString(23, worker.getIsAutoArrange());
			call.setString(24, worker.getBelongRegion());
			call.setString(25, worker.getIsLeave());
			/*用toPlainString()函数代替toString(),避免输出科学计数法的字符串。*/
			call.setString(26, worker.getEfficiency()==null?null : new String(worker.getEfficiency().toPlainString()));
			call.setString(27, worker.getPrjLevel());
			call.setString(28, worker.getIsSupvr());
			call.setString(29, worker.getWorkType12Dept());
			call.setString(30, worker.getPrjRegionCode());
			call.setString(31, worker.getRcvPrjType());
			call.setString(32, worker.getVehicle());
			call.setString(33, worker.getAddress());
			call.registerOutParameter(34, Types.INTEGER);
			call.registerOutParameter(35, Types.NVARCHAR);
			call.setString(36,worker.getAllowItemApp());
			call.setTimestamp(37, worker.getLeaveDate()==null?null : new Timestamp(worker.getLeaveDate().getTime()));
			call.setString(38, worker.getWorkerClassify());
			call.setString(39, worker.getDepartment2());
			call.setString(40, worker.getBank());
			call.setString(41, worker.getBank2());
			call.setString(42, worker.getMemberXml());
			call.execute();
			result.setCode(String.valueOf(call.getInt(34)));
			result.setInfo(call.getString(35));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	
	/*public Page<Map<String, Object>> getItemBatchList(Page<Map<String,Object>> page){
		
		String sql = " select a.No,a.remarks from tItemBatchHeader where itemtype1='JZ' and WorkType12 = '02'";
		Page<Map<String, Object>> list =  this.findPageBySql(page, sql,list.toArray());
		if (list != null && list.size() > 0) {
			return list;
		}else{
			return null;
		}
	}
*/
	public Page<Map<String, Object>> getItemBatchList(
			Page<Map<String, Object>> page ,String itemType1,int custWkPk){
		List<Object> list = new ArrayList<Object>();	
			String sql = " select a.No code,a.remarks descr from tItemBatchHeader a " +
					" where a.itemtype1= ?  and a.WorkType12 = (select workType12 from tcustWorker where pk=?)  ";
			
			list.add(itemType1);
			list.add(custWkPk);
		return this.findPageBySql(page, sql,list.toArray());
	}
	
	public List<Map<String,Object>> getRatedSalaryList(CustWorker custWorker,String appType){
		List<Object> params = new ArrayList<Object>();
		if("17".equals(custWorker.getWorkType12())){
			String sql="select a.Qty ,c.Descr itemType3Descr,c.Code ,e.Descr uomDescr from tSpecItemReqDt a "
					+" left join tItem b on a.ItemCode=b.Code "
					+" left join tItemType3 c on b.ItemType3 = c.Code "
					+" left join tCustWorker d on a.CustCode=d.CustCode "
					+" left join tUOM e on b.Uom=e.Code "
					+" where  d.WorkType12='17' and b.ItemType2='025' and c.IntInstallFee<>0 and a.CustCode=? ";
			params.add(custWorker.getCustCode());
			return this.findBySql(sql,params.toArray());
		}
		String sql=	" select b.Descr,c.Descr fixAreaDescr,a.Qty,d.Descr uomDescr "
				+" from tBaseCheckItemPlan a "
				+" left join tBaseCheckItem b on b.Code=a.BaseCheckItemCode "
				+" left join tFixArea c on a.FixAreaPK=c.PK"
				+" left join dbo.tUOM d ON d.Code=b.Uom "
				+" left join tBaseItemType2 e on e.Code = b.BaseItemType2 "
				+" where a.CustCode=? ";
		params.add(custWorker.getCustCode());
//		if(StringUtils.isNotBlank(custWorker.getWorkType12())){
//			sql += " and b.WorkType12= ? ";
//			params.add(custWorker.getWorkType12());
//		}
		if("0".equals(custWorker.getSalaryCtrlType())){
			if("2".equals(appType)){
				sql += " and b.WorkType12=(select WorkType12 from tWorkType2 where Code=?) ";
				params.add(custWorker.getWorkType2());
			}else{
				sql += " and b.WorkType12= ? ";
				params.add(custWorker.getWorkType12());
			}
		}else if("1".equals(custWorker.getSalaryCtrlType())){
			sql += " and e.OfferWorkType2 = ? ";
			params.add(custWorker.getWorkType2());
		}else{
			sql += " and b.WorkType12= ? ";
			params.add(custWorker.getWorkType12());
		}
		return this.findBySql(sql,params.toArray());
	}
	
	public Map<String,Object> getRatedSalary(CustWorker custWorker,String appType){
		List<Object> params = new ArrayList<Object>();
		if("17".equals(custWorker.getWorkType12())){
			//衣柜安装定额计算方式
			String sql="select sum(c.IntInstallFee*a.Qty) RatedSalary from tSpecItemReqDt a "
					+" left join tItem b on a.ItemCode=b.Code "
					+" left join tItemType3 c on b.ItemType3 = c.Code "
					+" left join tCustWorker d on a.CustCode=d.CustCode "
					+" where  d.WorkType12='17' and b.ItemType2='025' and a.CustCode=? ";
					params.add(custWorker.getCustCode());
					List<Map<String,Object>> list = this.findBySql(sql, params.toArray());
					if(list != null && list.size() > 0){
						return list.get(0);
					}
					return null;
		}
		
		String sql=	" select sum(a.OfferPri*a.Qty) RatedSalary " 
				 +" from tBaseCheckItemPlan a "
				 +" left join tBaseCheckItem b on b.Code=a.BaseCheckItemCode " 
				 +" left join tBaseItemType2 c on c.Code=b.BaseItemType2 "
				 +" where a.CustCode=? ";
		params.add(custWorker.getCustCode());
		if("0".equals(custWorker.getSalaryCtrlType())){
			if("2".equals(appType)){
				sql += " and b.WorkType12=(select WorkType12 from tWorkType2 where Code=?) ";
				params.add(custWorker.getWorkType2());
			}else{
				sql += " and b.WorkType12= ? ";
				params.add(custWorker.getWorkType12());
			}
		}else if("1".equals(custWorker.getSalaryCtrlType())){
			sql += " and c.OfferWorkType2 = ? ";
			params.add(custWorker.getWorkType2());
		}else{
			sql += " and b.WorkType12= ? ";
			params.add(custWorker.getWorkType12());
		}
		List<Map<String,Object>> list = this.findBySql(sql, params.toArray());
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	public Map<String,Object> getAllowGetMatrail(CustWorker custWorker){
		List<Object> params = new ArrayList<Object>();
		String sql=	" select * from tPreWorkCostDetail a "
					+" where a.Status NOT IN ('8','9')  "
					+" and a.CustCode=? and worktype2 in "
					+" (select in_b.OfferWorkType2 from tCustWorker in_a "
					+" inner join tWorkType12 in_b on in_a.WorkType12=in_b.Code"
					+" where in_a.PK=?)  ";
		params.add(custWorker.getCustCode());
		params.add(custWorker.getPk());
		List<Map<String,Object>> list = this.findBySql(sql,params.toArray());
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	public Map<String,Object> getBefSameWorker(CustWorker custWorker){
		List<Object> params = new ArrayList<Object>();
		String sql ="select b.NameChi WorkerName,b.Phone WorkerPhone,'123' appRemarks from tCustWorker a "
			   +" left join tWorker b on a.WorkerCode=b.Code " +
			   " left join tCustWorkerApp c on c.CustWorkPk  = a.Pk "
			   +" where a.custCode= ? and a.WorkType12=(select BefSameWorkType12 from tCustWorker cw "
			   +" left join tWorkType12 wt on cw.WorkType12=wt.Code " +
			   "   "
			   +" where cw.PK=?)";
//		String sql=	" select w.NameChi WorkerName, w.Phone WorkerPhone from tCustWorker cw "
//			+" left join tWorkType12 wt on cw.WorkType12=wt.Code "
//			+" left join tWorker w on wt.BefSameWorkType12=w.Code where cw.PK=? ";
		params.add(custWorker.getCustCode());
		params.add(custWorker.getPk());
		List<Map<String,Object>> list = this.findBySql(sql,params.toArray());
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	public Map<String,Object> getAppAmountByWt2(CustWorker custWorker){
		List<Object> params = new ArrayList<Object>();
		String sql ="select ISNULL(sum(case when a.Status in ('6', '7') then b.ConfirmAmount else a.AppAmount end),0) AppAmount " 
				+"from tPreWorkCostDetail a "
				+"left join tWorkCostDetail b on a.PK = b.RefPrePk "
				+"where a.CustCode= ? and a.WorkType2= ? and a.Status not in ('1','9','8') ";
		params.add(custWorker.getCustCode());
		params.add(custWorker.getWorkType2());
		List<Map<String,Object>> list = this.findBySql(sql, params.toArray());
		if(list != null && list.size() > 0){
			return list.get(0); 
		}
		return null;
	}
	
	public Map<String,Object> getAppAmountByWt12(CustWorker custWorker){
		List<Object> params = new ArrayList<Object>();
		String sql ="select ISNULL(sum(case when a.Status in ('6', '7') then b.ConfirmAmount else a.AppAmount end),0) AppAmount "
			+"from tPreWorkCostDetail a  "
			+"left join tWorkCostDetail b on a.PK = b.RefPrePk  "
			+"where a.CustCode=? and a.Status not in ('1','9','8') "
			+"and a.WorkType2 in (" 
			+" select in_a.Code " 
			+" from tWorkType2 in_a " 
			+" left join tWorkType2 in_b on in_a.Worktype12=in_b.Worktype12 " 
			+" where in_b.Code=? " 
			+") ";
		params.add(custWorker.getCustCode());
		params.add(custWorker.getWorkType2());
		List<Map<String,Object>> list = this.findBySql(sql, params.toArray());
		if(list != null && list.size() > 0){
			return list.get(0); 
		}
		return null;
	}
	
	public Map<String,Object> getSalaryCtrl(CustWorker custWorker){
		List<Object> params = new ArrayList<Object>();
		String sql =" select b.SalaryCtrl from tWorkType2 a "
				+" left join tWorkType12 b on a.Worktype12=b.Code "
				+" where a.Code=? ";
		params.add(custWorker.getWorkType2());
		List<Map<String,Object>> list = this.findBySql(sql, params.toArray());
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	public List<Map<String,Object>> getTechPhotoList(CustWorker custWorker){
		List<Object> params = new ArrayList<Object>();
		String sql =" select c.Code TechCode,c.Descr TechDescr,isnull(d.photoNum,0)photoNum from tCustWorker a "
				+" left join tWorkType12 b on a.WorkType12 =b.Code "
				+" left join tTechnology c on b.Code=c.WorkType12 "
				+" left join (select in_b.TechCode,count(TechCode) photoNum from tWorkSign in_a "
				+" left join tWorkSignPic in_b on in_a.No=in_b.No "
				+" where in_a.CustWkPk=? and in_b.TechCode is not null " 
				+" group by TechCode) d on d.TechCode=c.Code "
				+" where  b.IsTechnology='1' and  a.PK=?  and c.SourceType= ? and c.Expired='F' order by c.DisSeq ";
		params.add(custWorker.getPk());
		params.add(custWorker.getPk());
		params.add(custWorker.getSourceType());
		return this.findBySql(sql, params.toArray());
	}
	
	
	public List<Map<String,Object>> getNotCompeletePrjItem(WorkerAppEvt evt){
		List<Object> params = new ArrayList<Object>();
		String sql="select a.CustCode,a.WorkerCode,c.Code PrjItem2,d.Address,a.PK CustWkPk from tCustWorker a "
				+" left join tWorkType12 b on a.WorkType12=b.Code "
				+" left join tPrjItem2 c on c.worktype12=b.Code "
				+" left join tcustomer d on a.custCode=d.code "
				+" where a.pk=? and c.Code not in(select prjitem2 from  tWorkSign where CustWkPk=? and IsComplete='1')";
		params.add(evt.getCustWkPk());
		params.add(evt.getCustWkPk());
		return this.findBySql(sql, params.toArray());
	}

	/**
	 * 工种分类2根据tPrjItem2人工工资进行选择
	 * @author	created by zb
	 * @date	2019-7-8--下午3:52:55
	 * @param evt
	 * @return
	 */
	public List<Map<String, Object>> getWorkType2(WorkerAppEvt evt) {
		List<Object> params = new ArrayList<Object>();
		String sql =" select distinct a.OfferWorkType2 WorkType2,b.Descr " +
					"from tPrjItem2 a " +
					"left join tWorkType2 b on b.Code=a.OfferWorkType2 " +
					"left join tWorkSign c on c.PrjItem2=a.Code " +
					"where a.Expired='F' and b.Code is not null " +
					"and c.IsComplete='1' and a.worktype12=? " +
					"and c.CustWkPk=? and CustCode=? ";
		params.add(evt.getWorkType12());
		params.add(evt.getCustWkPk());
		params.add(evt.getCustCode());
		List<Map<String,Object>> list = this.findBySql(sql, params.toArray());
		if(list != null && list.size() > 0){
			return list;
		}
		return null;
	}
	
	public boolean getWorkSignByCustWkPk(int custWkPk) {
		String sql="select * from tWorkSign a where a.CustWkPk=? ";
		List<Map<String,Object>> list =  this.findBySql(sql, new Object[]{custWkPk});
		if (list!=null && list.size()>0){
			return true;
		}
		return false;
	}
	
	/**
	 * 预算防水面积
	 * @author cjg
	 * @date 2019-12-10
	 * @param evt
	 * @return
	 */
	public Double getWaterArea(WorkerSignInEvt evt) {
		List<Object> params = new ArrayList<Object>();
		String sql ="select isnull(sum(Qty),0) WaterCtrlArea "
				+"from tBaseItemReq a "
				+"left join dbo.tBaseItem b on b.Code=a.BaseItemCode "
				+"left join tbaseitemtype2 c on c.Code=b.BaseItemType2 "
				+"where c.MaterWorkType2='010' and a.CustCode=?";
		params.add(evt.getCustCode());
		List<Map<String,Object>> list = this.findBySql(sql, params.toArray());
		return Double.parseDouble(list.get(0).get("WaterCtrlArea").toString());
	}
	
	@SuppressWarnings("deprecation")
	public Result saveWorkerWorkType12ForProc(Worker worker,String xml) {
		Assert.notNull(worker);
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pSaveWorkerWorkType12_forXml(?,?,?,?,?)}");
			call.setString(1, worker.getCode());
			call.setString(2, worker.getLastUpdatedBy());
			call.setString(3, xml);
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
	
	public List<Map<String,Object>>  getWorkerRatedSalaryList(String custCode){
		List<Object> params = new ArrayList<Object>();
		
		String sql=	" select b.Code WorkType12,b.Descr WorkType12Descr,a.RatedSalary "
				+" from (select b.WorkType12,sum(a.OfferPri*a.Qty) RatedSalary  "
				+" 	from tBaseCheckItemPlan a  "
				+" 	left join tBaseCheckItem b on b.Code=a.BaseCheckItemCode  "
				+" 	left join tBaseItemType2 c on c.Code=b.BaseItemType2  "
				+" 	where a.CustCode=? and WorkType12<>'17' "
				+" 	group by b.WorkType12 "
				+" 	union all "
				+" 	select '17' workType12,sum(c.IntInstallFee*a.Qty) RatedSalary from tSpecItemReqDt a  "
				+" 	left join tItem b on a.ItemCode=b.Code  "
				+" 	left join tItemType3 c on b.ItemType3 = c.Code  "
				+" 	left join tCustWorker d on a.CustCode=d.CustCode  "
				+" 	where  d.WorkType12='17' and b.ItemType2='025' and a.CustCode=? "
				+" )a  "
				+" left join tWorkType12 b on a.WorkType12 = b.Code "
				+" where a.RatedSalary is not null "
				+" order by b.DispSeq ";
		params.add(custCode);
		params.add(custCode);
		List<Map<String,Object>> list = this.findBySql(sql, params.toArray());
		if(list != null && list.size() > 0){
			return list;
		}
		return null;
	}
	
	public List<Map<String,Object>> getWorkerRatedSalary(CustWorker custWorker){
		List<Object> params = new ArrayList<Object>();
		if("17".equals(custWorker.getWorkType12())){
			String sql="select a.Qty ,c.Descr itemType3Descr,c.Code ,e.Descr uomDescr from tSpecItemReqDt a "
					+" left join tItem b on a.ItemCode=b.Code "
					+" left join tItemType3 c on b.ItemType3 = c.Code "
					+" left join tCustWorker d on a.CustCode=d.CustCode "
					+" left join tUOM e on b.Uom=e.Code "
					+" where  d.WorkType12='17' and b.ItemType2='025' and c.IntInstallFee<>0 and a.CustCode=? ";
			params.add(custWorker.getCustCode());
			return this.findBySql(sql,params.toArray());
		}
		String sql=	" select b.Descr,c.Descr fixAreaDescr,a.Qty,d.Descr uomDescr "
				+" from tBaseCheckItemPlan a "
				+" left join tBaseCheckItem b on b.Code=a.BaseCheckItemCode "
				+" left join tFixArea c on a.FixAreaPK=c.PK"
				+" left join dbo.tUOM d ON d.Code=b.Uom "
				+" left join tBaseItemType2 e on e.Code = b.BaseItemType2 "
				+" where a.CustCode=? ";
		params.add(custWorker.getCustCode());
		if(StringUtils.isNotBlank(custWorker.getWorkType12())){
			sql += " and b.WorkType12= ? ";
			params.add(custWorker.getWorkType12());
		}
		
		return this.findBySql(sql,params.toArray());
	}
	
	public List<Map<String,Object>> getInstallInfoList(CustWorker custWorker){
		List<Object> params = new ArrayList<Object>();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		// 衣柜安装
		if("17".equals(custWorker.getWorkType12().trim())){
			String sql=" select a.CupUpHigh, a.CupDownHigh from tCupMeasure a where a.IsCupboard = '0' and a.CustCode = ? ";
			params.add(custWorker.getCustCode());
			list = this.findBySql(sql,params.toArray());
		}
		
		// 橱柜安装
		if("18".equals(custWorker.getWorkType12().trim())){
			String sql=" select a.CupUpHigh, a.CupDownHigh from tCupMeasure a where a.IsCupboard = '1' and a.CustCode = ? ";
			params.add(custWorker.getCustCode());
			list = this.findBySql(sql,params.toArray());
		}
		
		// 后期水电安装
		if("14".equals(custWorker.getWorkType12().trim())){
			String sql=" select a.ToiletQty, a.CabinetQty from tCustomer a where a.Code = ? ";
			params.add(custWorker.getCustCode());
			list = this.findBySql(sql,params.toArray());
		}
		
		// 水电预埋
		if("02".equals(custWorker.getWorkType12().trim())){
			String sql=" select c.Remarks ItemType2Descr, b.Descr ItemDescr, a.Qty, d.Descr UomDescr  from tWaterAftInsItemApp a "
					+" left join tWaterAftInsItemRuleDetail b on a.WaterAftInsItemPk = b.PK "
					+" left join tWaterAftInsItemRule c on b.No = c.No "
					+" left join tUOM d on b.Uom = d.Code "
					+" where a.CustCode = ? ";
			params.add(custWorker.getCustCode());
			list = this.findBySql(sql,params.toArray());
		}
	
		
		if(list != null && list.size() > 0){
			return list;
		}
		return null;
	}

    public boolean checkCanDeleteMember(String memberCode) {
        String sql = "select * from tWorkerCostProvide where WorkerMemCode = ?";
        
        return countSqlResult(sql, null, memberCode) < 1;
    }
    
    public List<Map<String, Object>> getWorkSignPic(String no) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select a.pk,a.no,a.photoName,a.isSendYun,b.custCode from tWorkSignPic a " 
				+" left join tWorkSign b on a.No = b.No "
				+" where 1=1 ";
		
		if(StringUtils.isNotBlank(no)){
			sql+=" and a.no = ? ";
			list.add(no);
		}

		return this.findBySql(sql, list.toArray());
	}
    
}
