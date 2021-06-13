package com.house.home.dao.project;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.SqlUtil;
import com.house.framework.web.login.UserContext;
import com.house.home.client.service.evt.CustWorkerAppEvt;
import com.house.home.entity.design.Customer;
import com.house.home.entity.design.ResrCust;
import com.house.home.entity.project.CustWorker;
import com.house.home.entity.project.CustWorkerApp;
import com.house.home.entity.project.Worker;
import com.sun.org.apache.bcel.internal.generic.RETURN;

@SuppressWarnings("serial")
@Repository
public class CustWorkerAppDao extends BaseDao {
	
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, CustWorkerApp custWorkerApp,String czybh, UserContext uc) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from ( select  dbo.fGetShouldBanlanceByPayNum(a.CustCode,wt.payNum,default) shouldpay,cw.constructday workload, cw.constructday prjnormday, a.lastupdate," +
				"        a.lastupdatedby, a.pk pk, a.status, e.phone businessphone," +
				"        a.workercode, w.namechi workerdescr, x1.note statusdescr, a.worktype12," +
				"        wt.descr worktype12descr, a.remarks, a.appcomedate," +
				"        x.note custtypedescr, a.appdate, c.address, c.custtype, c.area," +
				"				c.projectman, e.namechi projectmandescr, e.phone, e.department2," +
				"        e.department1, d.desc1 department2descr, d1.desc1 department1descr," +
				"        b.isprjspc, pp.confirmczy, pp.confirmdate, e2.namechi confirmdescr," +
				"        wt.prjitem, a.comedate, c.realaddress, r.descr regiondescr," +
				"        x3.note issysarrangedescr, cw.issysarrange,x5.note signdelaytypedescr,x6.note comedelaytypedescr," +
				"        cast(dbo.fgetempnamechi(c.code, '34') as nvarchar(1000)) mainstewarddescr," +
				"        ( select    count(1)" +
				"          from      tcustworker" +
				"          where     custcode = a.custcode and worktype12 = a.worktype12" +
				"        ) arrtimes,x4.note relcustdescr ,isnull(case when c.innerArea>0 then c.innerArea else c.Area*ct.InnerAreaPer end,0)innerArea, " +
				"        a.arrdate " +
				" from   tcustworkerapp a" +
				"        left join tcustomer c on c.code = a.custcode" +
				"        left join tworktype12 wt on wt.code = a.worktype12" +
				"        left join temployee e on e.number = c.projectman" +
				"        left join tdepartment1 d1 on d1.code = e.department1" +
				"        left join tdepartment2 d on d.code = e.department2" +
				"        left join tbuilder b on b.code = c.buildercode" +
				"        left join tworker w on w.code = a.workercode" +
				"        left join tprjprog pp on c.code = pp.custcode and pp.prjitem = wt.prjitem" +
				"        left join temployee e2 on e2.number = pp.confirmczy" +
				"        left join tregion r on r.code = b.regioncode" +
				"        left join txtdm x on x.cbm = c.custtype and x.id = 'custtype'" +
				"        left join txtdm x1 on x1.cbm = a.status and x1.id = 'workerappsts'" +
				"        left join tcustworker cw on cw.pk = a.custworkpk" +
				"        left join txtdm x3 on x3.cbm = cw.issysarrange and x3.id = 'yesno'" +
				"        left join txtdm x5 on x5.cbm = cw.SignDelayType and x5.id = 'CUSTWKDELAYTYPE' " +
				"        left join txtdm x6 on x6.cbm = cw.ComeDelayType and x6.id = 'CUSTWKDELAYTYPE' " +
				"        left join tcuststakeholder css on css.custcode = a.custcode and css.role = '63'" +
				"		 left join (select max(PlanArrDate) planArrDate,MsgRelCustCode,WorkType12,MsgRelNo from tPersonMessage ina" +
				" 			where WorkType12 is not null and PlanArrDate is not null and WorkType12 <> ''" +
				"			group by MsgRelCustCode,WorkType12,MsgRelNo) q on cast(a.ProgTempAlarmPK as nvarchar(20)) = q.MsgRelNo  " +
				"			and q.MsgRelCustCode = a.CustCode and q.WorkType12 = a.WorkType12" +
				"        left join txtdm x4 on x4.cbm=c.relcust and x4.id='RELCUST' "+
				"        left join tCustType ct on c.CustType=ct.Code "+
				" where  1 = 1 and " + SqlUtil.getCustRight(uc, "c", 0);
		
		if(StringUtils.isNotBlank(custWorkerApp.getAddress())){	
			sql+=" and c.address like ?  ";
			list.add("%"+custWorkerApp.getAddress()+"%");
		}
		if(StringUtils.isNotBlank(custWorkerApp.getDepartment1())){
			sql+=" and e.department1 in" + "('"+custWorkerApp.getDepartment1().replaceAll(",", "','")+"')";
		}
		if(StringUtils.isNotBlank(custWorkerApp.getCustSceneDesigner())){
			sql+=" and css.empCode= ? ";
			list.add(custWorkerApp.getCustSceneDesigner());
		}
		if(StringUtils.isNotBlank(custWorkerApp.getWorkerCode())){
			sql+=" and a.workerCode =?";
			list.add(custWorkerApp.getWorkerCode());
		}
		if (custWorkerApp.getAppDateFrom() != null) {
			sql += " and a.appDate>= ? ";
			list.add(custWorkerApp.getAppDateFrom());
		}
		if (custWorkerApp.getAppDateTo() != null) {
			sql += " and a.appDate< DATEADD(d,1,?) ";
			list.add(custWorkerApp.getAppDateTo());
		}
		if (StringUtils.isNotBlank(custWorkerApp.getIsAutoArrange())) {
			if("1".equals(custWorkerApp.getIsAutoArrange())){
				sql+=" and cw.isSysArrange ='1' ";
				
			}else {
				sql+=" and cw.isSysArrange ='0' ";
			}
		}
		if (StringUtils.isNotBlank(custWorkerApp.getDepartment2())) {
			sql += " and e.department2 in " + "('"+custWorkerApp.getDepartment2().replaceAll(",", "','")+"')";
		}
		if (StringUtils.isNotBlank(custWorkerApp.getWorkType12())) {
			sql += " and a.workType12 in " + "('"+custWorkerApp.getWorkType12().replaceAll(",", "','")+"')";
		}
		if(StringUtils.isNotBlank(custWorkerApp.getStatus())){
			sql+=" and a.status =? ";
			list.add(custWorkerApp.getStatus());
		}
		
		if(StringUtils.isNotBlank(custWorkerApp.getRegion())){
			sql+=" and r.code=? ";
			list.add(custWorkerApp.getRegion());
		}
		if(StringUtils.isNotBlank(custWorkerApp.getIsPrjSpc())){
			sql+=" and b.isPrjSpc =? ";
			list.add(custWorkerApp.getIsPrjSpc());
		}
		if (custWorkerApp.getDateFrom() != null) {
			sql += " and a.ComeDate>= ? ";
			list.add(custWorkerApp.getDateFrom());
		}
		if (custWorkerApp.getDateTo() != null) {
			sql += " and a.ComeDate< DATEADD(d,1,?) ";
			list.add(custWorkerApp.getDateTo());
		}
		if(StringUtils.isNotBlank(custWorkerApp.getPrjRegion())){
			sql+="  and exists ( select  1 from tRegion2 a  where a.PrjRegionCode = ? and a.Code=b.regionCode2 )";
			list.add(custWorkerApp.getPrjRegion());
		}
		if(StringUtils.isNotBlank(czybh)){
			sql+=" and exists (" +
					" select *  from tczybm in_a" +
					" left join tPrjRole in_b on in_a.PrjRole=in_b.Code" +
					" left join tPrjRoleWorkType12 in_c on in_b.Code=in_c.PrjRole" +
					" where  in_a.CZYBH=? and (in_a.PrjRole='' or in_a.PrjRole is null or a.WorkType12=in_c.WorkType12)  " +
					") ";
			list.add(czybh);
		}
		if(StringUtils.isNotBlank(custWorkerApp.getArrangeable())){
			sql+=" and dbo.fGetCanArr(a.CustCode,a.WorkType12,'1')='满足' and dbo.fGetCanArr(a.CustCode,a.WorkType12,'2')='满足' and dbo.fGetCanArr(a.CustCode,a.WorkType12,'3')='满足' ";
		}
		
		// 集成部查询条件
		if (StringUtils.isNotBlank(custWorkerApp.getIntDepartment2())) {
            sql += "and case when a.WorkType12 = '17' then "
                    + "    (select in_a.EmpCode "
                    + "    from tCustStakeholder in_a "
                    + "    left join tEmployee in_b on in_a.EmpCode = in_b.Number "
                    + "    where in_b.Department2 in ('" + custWorkerApp.getIntDepartment2().replace(",", "', '") + "') "
                    + "        and in_a.PK = (select max(in_in_a.PK) "
                    + "            from tCustStakeholder in_in_a "
                    + "            where in_in_a.CustCode = a.CustCode "
                    + "                and in_in_a.Role = '11')) "
                    + "when a.WorkType12 = '18' then "
                    + "    (select in_a.EmpCode "
                    + "    from tCustStakeholder in_a "
                    + "    left join tEmployee in_b on in_a.EmpCode = in_b.Number "
                    + "    where in_b.Department2 in ('" + custWorkerApp.getIntDepartment2().replace(",", "', '") + "') "
                    + "        and in_a.PK = (select max(in_in_a.PK) "
                    + "            from tCustStakeholder in_in_a "
                    + "            where in_in_a.CustCode = a.CustCode "
                    + "                and in_in_a.Role = '61')) "
                    + "end is not null ";
        }
		
		sql+=" )a where 1=1 ";
		if(StringUtils.isNotBlank(custWorkerApp.getMainStewardDescr()) && StringUtils.isNotBlank(custWorkerApp.getMainSteward())){
			sql+=" and a.mainStewardDescr = ? ";
			list.add(custWorkerApp.getMainStewardDescr());
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += " order by a.appDate ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> findPrintPageBySql(
			Page<Map<String, Object>> page, CustWorkerApp custWorkerApp) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select *from ( select dbo.fGetPrjNormDay(a.CustCode,a.worktype12)PrjNormDay,a.lastUpdate,a.lastupdatedby,a.PK pk,a.status,e.phone businessphone,a.WorkerCode,w.NameChi workerDescr ,x1.note statusDescr,a.workType12,wt.descr worktype12descr,a.remarks,a.AppComeDate," +
				"x.note CustTypeDescr,a.AppDate,c.address,c.custType,c.area,c.projectMan,e.NameChi projectManDescr, " +
				"e.phone,e.department2 ,e.department1 ,d.desc1 department2descr,d1.desc1 department1descr ,b.isprjSpc  " +
				",pp.ConfirmCzy,pp.ConfirmDate,e2.NameChi confirmDescr ,ppcf.prjLevel ,x2.note PrjLevelDescr " +
				",wt.prjItem,a.comeDate ,c.realAddress,r.descr RegionDescr ,x3.note isSysArrangeDescr,cw.issysarrange  " +
				"from tCustWorkerApp a " +
				" left join tCustomer c on c.code= a.CustCode " +
				" left join tWorkType12 wt on wt.Code=a.workType12 " +
				" left join tEmployee e on e.Number = c.projectMan" +
				" left join tDepartment1 d1 on d1.code=e.department1" +
				" left join tDepartment2 d on d.code=e.department2" +
				" left join tBuilder b on b.code=c.BuilderCode " +
				" left join tWorker w on w.code =a.workerCode " +
				" left join tPrjProg pp on c.code =pp.CustCode and pp.prjItem=wt.prjItem " +
				" left join tPrjProgConfirm ppcf on ppcf.custCode=c.code and  wt.prjItem=ppcf.prjItem and ispass='1'" +
				" left join tEmployee e2 on e2.Number = pp.ConfirmCzy " +
				" left join tRegion r on r.code=b.RegionCode  " +
				" left join tXTDM x on x.cbm=c.custType and x.Id='CUSTTYPE' " +
				" left join tXTDM x1 on x1.cbm=a.status and x1.Id='WORKERAPPSTS' " +
				" left join tXTDM x2 on x2.cbm=ppcf.prjLevel and x2.Id='PRJLEVEL' " +
				" left join tCustWorker cw on cw.pk=a.custWorkpk  " +
				" left join tXTDM x3 on x3.cbm =cw.isSysArrange and x3.Id='YESNO' " +
				" where 1=1 " ;
		if(StringUtils.isNotBlank(custWorkerApp.getAddress())){	
			sql+=" and c.address like ?  ";
			list.add("%"+custWorkerApp.getAddress()+"%");
		}
		if(StringUtils.isNotBlank(custWorkerApp.getDepartment1())){
			sql+=" and e.department1 in" + "('"+custWorkerApp.getDepartment1().replaceAll(",", "','")+"')";
			
		}
		if(StringUtils.isNotBlank(custWorkerApp.getWorkerCode())){
			sql+=" and a.workerCode =?";
			list.add(custWorkerApp.getWorkerCode());
			
		}
		if (custWorkerApp.getAppDateFrom() != null) {
			sql += " and a.appDate>= ? ";
			list.add(custWorkerApp.getAppDateFrom());
		}
		if (custWorkerApp.getAppDateTo() != null) {
			sql += " and a.appDate< DATEADD(d,1,?) ";
			list.add(custWorkerApp.getAppDateTo());
		}
		if (StringUtils.isNotBlank(custWorkerApp.getIsAutoArrange())) {
			if("1".equals(custWorkerApp.getIsAutoArrange())){
				sql+=" and cw.isSysArrange ='1' ";
				
			}else {
				sql+=" and cw.isSysArrange ='0' ";
			}
		}
		if (StringUtils.isNotBlank(custWorkerApp.getDepartment2())) {
			sql += " and e.department2 in " + "('"+custWorkerApp.getDepartment2().replaceAll(",", "','")+"')";
		}
		if (StringUtils.isNotBlank(custWorkerApp.getWorkType12())) {
			sql += " and a.workType12 in " + "('"+custWorkerApp.getWorkType12().replaceAll(",", "','")+"')";
		}
		if(StringUtils.isNotBlank(custWorkerApp.getStatus())){
			sql+=" and a.status =? ";
			list.add(custWorkerApp.getStatus());
		}
		
		if(StringUtils.isNotBlank(custWorkerApp.getRegion())){
			sql+=" and r.code=? ";
			list.add(custWorkerApp.getRegion());
		}
		if(StringUtils.isNotBlank(custWorkerApp.getIsPrjSpc())){
			sql+=" and b.isPrjSpc =? ";
			list.add(custWorkerApp.getIsPrjSpc());
		}
		if (custWorkerApp.getDateFrom() != null) {
			sql += " and a.LastUpdate>= ? ";
			list.add(custWorkerApp.getDateFrom());
		}
		if (custWorkerApp.getDateTo() != null) {
			sql += " and a.LastUpdate< DATEADD(d,1,?) ";
			list.add(custWorkerApp.getDateTo());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ")a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ")a order by a.appDate ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> findWorkerDetailPageBySql(
			Page<Map<String, Object>> page, CustWorkerApp custWorkerApp) {
 		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select a.num,a.Efficiency,a.WorkerCode,a.WorkerCodeDescr,a.WorkType12Descr,a.level,a.IsSignDescr,"
				+" (select count(*) from tcustworker cw left join tcustomer c on c.code=cw.custcode  where  workercode= a.WorkerCode" +
				" and cw.endDate is null and c.status='4') zjts," +
				" sum(a.monthts) monthts,sum(a.yearts) yearts "
				+" from (select b.num,b.Efficiency,b.Code WorkerCode,b.NameChi WorkerCodeDescr,b.WorkType12,c.Descr WorkType12Descr,xt2.note level, "
				+" d.Address,d.Area,d.BeginDate,d.EndDate,a.CustCode,xt1.Note IsSignDescr,"
				+" g.RegionCode,h.Descr RegionCodeDescr,d.status,"
				+" case when a.endDate is null and a.pk is not null and d.Status='4' then 1 else 0 end zjts,"
				+" case when a.endDate is not null and convert(varchar(6),a.endDate,112)=convert(varchar(6),getdate(),112) then 1 else 0 end monthts,"
				+" case when a.endDate is not null and convert(varchar(4),a.endDate,112)=convert(varchar(4),getdate(),112) then 1 else 0 end yearts"
				+" from tWorker b "
				+" left join tCustWorker a on a.WorkerCode=b.Code"
				+" left join tWorkType12 c on b.WorkType12=c.Code"
				+" left join tcustomer d on a.CustCode=d.Code"
				+" left join tBuilder g on d.BuilderCode=g.Code"
				+" left join tRegion h on g.RegionCode=h.Code"
				+" left join tPrjProg f on c.PrjItem=f.PrjItem and a.CustCode=f.CustCode "
				+" left join txtdm xt1 on xt1.cbm=b.IsSign and xt1.ID='WSIGNTYPE' " +
				" left join txtdm xt2 on xt2.cbm=b.level and xt2.id='WORKERLEVEL' " +
				" where 1=1 " ;
		
			if (StringUtils.isNotBlank(custWorkerApp.getWorkType12())) {
				sql += " and b.workType12 in " + "('"+custWorkerApp.getWorkType12().replaceAll(",", "','")+"')";
			}
			if(StringUtils.isNotBlank(custWorkerApp.getIsSign())){
				sql+="  and b.isSign =? ";
				list.add(custWorkerApp.getIsSign());
			}
			if(StringUtils.isNotBlank(custWorkerApp.getWorkerCode())){
				sql+=" and b.code =? ";
				list.add(custWorkerApp.getWorkerCode());
			}
			if(StringUtils.isNotBlank(custWorkerApp.getRegion())){
				sql+=" and g.RegionCode =?";
				list.add(custWorkerApp.getRegion());
			}
			if(StringUtils.isNotBlank(custWorkerApp.getWorkerLevel())){
				sql+=" and b.level =? ) a group by a.num,a.Efficiency,a.WorkerCode,a.WorkerCodeDescr,a.WorkType12Descr,a.level,a.IsSignDescr )a";
				list.add(custWorkerApp.getWorkerLevel());
			}else{
				sql+=" )a group by a.Efficiency,a.num,a.WorkerCode,a.WorkerCodeDescr,a.WorkType12Descr,a.level,a.IsSignDescr )a ";
			}
			if(StringUtils.isNotBlank(custWorkerApp.getOnDo())){
				sql+=" where a.zjts < ? ";
				list.add(custWorkerApp.getOnDo());
			}
			
			if (StringUtils.isNotBlank(page.getPageOrderBy())) {
				sql += "  order by a." + page.getPageOrderBy() + " "
						+ page.getPageOrder();
			} else {
				sql += "  order by a.zjts desc ";
			}
		
			return this.findPageBySql(page, sql, list.toArray());

	}
	
	public Map<String , Object> getCustPk() {
		String sql=" select max(PK) pk from tCustWorker where 1=1  ";
		
		List<Map<String,Object>> list=this.findBySql(sql,new Object[]{});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public CustWorkerApp getByCode(String custCode,String workType12) {
		String hql = "from CustWorkerApp where custCode=? and workType12=? and status <>'3' ";
		List<CustWorkerApp> list = this.find(hql, new Object[] { custCode,workType12});
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public CustWorkerApp getByWorkerCode(String custCode,String workType12,String workerCode) {
		String hql = "from CustWorkerApp where custCode=? and workType12=? and ? <> '' and  workerCode=? ";
		List<CustWorkerApp> list = this.find(hql, new Object[] { custCode,workType12,workerCode,workerCode});
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	public void doDel(Integer pk) {
		String sql = " delete from tCustWorkerApp where pk= ? ";
		this.executeUpdateBySql(sql, new Object[]{pk});
	}
	
	public void doReturn(Integer pk) {
		String sql = " update tCustWorkerApp set status = '0' tCustWorkerApp where pk= ?  ";
		this.executeUpdateBySql(sql, new Object[]{pk});
	}
	
	public void doCancel(Integer pk ,String lastUpdatedBy) {
		String sql = " update tCustWorkerApp set lastUpdate=getDate() ,LastUpdatedBy= ?  , status='3' where pk= ? ";
		this.executeUpdateBySql(sql, new Object[]{lastUpdatedBy,pk});
	}

	public Page<Map<String,Object>> getWorkerAppList(Page<Map<String,Object>> page,CustWorkerAppEvt evt){
		List<Object> list = new ArrayList<Object>();
		String sql = " SELECT a.PK,b.Address,a.Status,x1.NOTE statusDescr,a.AppDate,c.Descr workType12Descr "
					+" FROM dbo.tCustWorkerApp a "
					+" LEFT JOIN dbo.tCustomer b ON a.CustCode = b.Code "
					+" LEFT JOIN dbo.tXTDM x1 ON x1.CBM = a.Status AND x1.ID='WORKERAPPSTS' "
					+" LEFT JOIN dbo.tWorkType12 c ON a.WorkType12 = c.Code "
					+" WHERE 1=1 ";
		if(StringUtils.isNotBlank(evt.getAddress())){
			sql += " AND b.Address like ? ";
			list.add("%"+evt.getAddress()+"%");
		}
		if(StringUtils.isNotBlank(evt.getStatus())){
			sql += " AND a.Status=? ";
			list.add(evt.getStatus());
		}
		if(StringUtils.isNotBlank(evt.getProjectMan())){
			sql += " AND b.projectMan=? ";
			list.add(evt.getProjectMan());
		}
		sql += " ORDER BY a.AppDate DESC "; 
		return this.findPageBySql(page, sql , list.toArray());
	}
	
	public boolean existWorkApp(String custCode,String workType12,String status){
		List<Object> params = new ArrayList<Object>();
		String sql = " SELECT a.* FROM dbo.tCustWorkerApp a WHERE 1=1 AND  ( a.CustCode=? AND a.WorkType12=? AND "
				   + " a.Status in ('"+(StringUtils.isNotBlank(status) ? status.replace(",", "','") : "")+"')) or exists( "
				   + " 		select 1 "
				   + " 		from tCustWorker in_a "
				   + "		where in_a.CustCode=? and in_a.WorkType12=? and in_a.EndDate is null) ";
		
		params.add(custCode);
		params.add(workType12);
		params.add(custCode);
		params.add(workType12);
		List<Map<String,Object>> list = this.findBySql(sql, params.toArray());
		if(list != null && list.size() > 0){
			return true;
		}
		return false;
	}
	

	public Map<String,Object> getWorkerAppByPk(Integer pk){
		String sql = " SELECT c.Address,cwa.CustCode,cwa.WorkType12,cwa.AppComeDate,cwa.Remarks,"
					+" cwa.Status,x1.NOTE StatusDescr,cwa.AppDate,w.NameChi,w.Phone,cwa.ComeDate, "
					+" c.IsInitSign "
					+" FROM dbo.tCustWorkerApp cwa "
					+" LEFT JOIN dbo.tCustomer c ON cwa.CustCode = c.Code "
					+" LEFT JOIN dbo.tXTDM x1 ON x1.CBM = cwa.Status AND x1.ID = 'WORKERAPPSTS' "
					+" LEFT JOIN dbo.tWorker w ON w.Code = cwa.WorkerCode "
					+" WHERE cwa.PK=? "; 
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{pk});
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	public List<Map<String,Object>> judgeProgTemp(CustWorkerAppEvt evt){
		String sql = " SELECT DISTINCT c.Code,c.Descr,c.Address,ppt.No,ppt.Type "
					+" FROM dbo.tCustomer c "
					+" LEFT JOIN dbo.tPrjProgTemp ppt ON c.PrjProgTempNo = ppt.No "
					+" WHERE c.Code=? AND ppt.Type='2' ";
		return this.findBySql(sql, new Object[]{evt.getCustCode()});
	}
	
	public Map<String,Object> checkCustPay(CustWorkerAppEvt evt,String payNum){
		List<Object> params = new ArrayList<Object>();
/*		String sql = " SELECT ISNULL(a.pay,0) pay,ISNULL(b.spay,0) spay "
					+" FROM tCustomer c " 
					+" LEFT JOIN ( "
					+" 	SELECT SUM(ISNULL(Amount,0)) pay,CustCode "
					+" 	FROM dbo.tCustPay "
					+" 	WHERE CustCode=? "
					+"	GROUP BY CustCode "
					+" ) a ON c.Code = a.CustCode "
					+" FULL JOIN ( ";
		if("1".equals(payNum)){
			sql += " SELECT ISNULL(DesignFee,0)+ISNULL(FirstPay,0) spay,Code ";
		}else if("2".equals(payNum)){
			sql += " SELECT ISNULL(DesignFee,0)+ISNULL(FirstPay,0)+ISNULL(SecondPay,0) spay,Code ";	
		}else if("3".equals(payNum)){
			sql += " SELECT ISNULL(DesignFee,0)+ISNULL(FirstPay,0)+ISNULL(SecondPay,0)+ISNULL(ThirdPay,0) spay,Code ";	
		}else if("4".equals(payNum)){
			sql += " SELECT ISNULL(DesignFee,0)+ISNULL(FirstPay,0)+ISNULL(SecondPay,0)+ISNULL(ThirdPay,0)+ISNULL(FourPay,0) spay,Code ";
		}else{
			sql += " SELECT 0 spay,Code ";
		}
		sql += " FROM dbo.tCustomer "
			  +" WHERE Code = ? "
			  +" ) b ON a.CustCode = b.Code "
			  +" WHERE c.Code = ? ";
		params.add(evt.getCustCode());
		params.add(evt.getCustCode());
		params.add(evt.getCustCode());*/
		String sql = " select dbo.fGetShouldBanlanceByPayNum(?,?,default) shouldBanlance ";
		params.add(evt.getCustCode());
		params.add(payNum);
		List<Map<String,Object>> list = this.findBySql(sql, params.toArray());
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	public Map<String,Object> getConstructDay(String custCode,String workerCode){
		String sql = " select dbo.fGetWorkerConDay (?,?) constructDay"; 
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{custCode,workerCode});
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	public void doAutoArr(String lastUpdatedBy) {
		String sql = " pWorkerArrange ? ";
		this.executeUpdateBySql(sql, new Object[]{lastUpdatedBy});
	}
	
	/**
	 * 未安排工人tab
	 * 
	 * */
	public Page<Map<String, Object>> findNoArrGRPageBySql(
			Page<Map<String, Object>> page, CustWorkerApp custWorkerApp) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (  select a.*,x2.note levelDescr,x1.note isSignDescr,w.descr worktype12Descr ,e.nameChi IntroduceEmpDescr,r1.descr liveDescr" +
				",r2.descr live2Descr,sb.descr spcdescr,r3.descr belongRegionDescr ,d1.desc1 department1Descr,w2.num workernum " +
				" from tAutoArrWorker a " +
				" left join tWorkType12 w on w.code =a.workType12 " +
				" left join temployee e on e.number = a.IntroduceEmp " +
				" left join tRegion r1 on r1.code=a.liveRegion " +
				" left join tRegion r2 on r2.code=a.liveRegion2 " +
				" left join tRegion r3 on r3.code=a.belongRegion " +
				" left join tSpcBuilder sb on sb.code=a.spcBuilder " +
				" left join tDepartment1 d1 on d1.code = a.Department1 " +
				" left join tXTDM x1 on x1.cbm = a.isSign and x1.ID='YESNO' " +
				" left join tXTDM x2 on x2.cbm=a.level and x2.id='WORKERLEVEL' " +
				" left join tworker w2 on w2.code=a.workercode " +
				" where 1=1 ";
		
		if(custWorkerApp.getArrPK()!=null){
			sql+=" and a.workerCode not in (select workerCode from tAutoArrWorkerApp where workerCode is Not null and arrpk = ? )" +
					" and a.arrpk = ? ";
			list.add(custWorkerApp.getArrPK());
			list.add(custWorkerApp.getArrPK());
		}else{
			sql+=" and a.workerCode not in (select workerCode from tAutoArrWorkerApp where workerCode is Not null " +
					"and arrpk = (select max(pk) from tAutoArr ) )" +
					" and a.arrpk = (select max(pk) from tAutoArr ) ";
		}
		if(StringUtils.isNotBlank(custWorkerApp.getWorkerCode())){
			sql+=" and a.WorkerCode=?";
			list.add(custWorkerApp.getWorkerCode());
		}
		if(StringUtils.isNotBlank(custWorkerApp.getWorkType12())){
			sql+=" and a.workType12 =?";
			list.add(custWorkerApp.getWorkType12());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ")a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ")a order by a.issign desc ,a.level desc";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 已自动安排工地
	 *  
	 *  
	 *  */
	public Page<Map<String , Object >>findHasArrGdPageBySql(
			Page<Map<String ,Object>> page, CustWorkerApp custWorkerApp) {
		List<Object> list = new ArrayList<Object>();
		
		String sql="select * from (  select a.*,c.descr custDescr,c.address ,wt.descr workType12descr ,x1.note custtypedescr ,x2.note issupDescr,x4.note regdescr " +
				", d1.desc1 department1descr ,sb.descr spcdescr,r1.descr region1descr,r2.descr region2descr,e.nameChi ProjectMandescr " +
				",w.NameChi workerdescr,r3.descr liveregion1,r4.descr liveregion2 ,x3.note workerlevel,aaw.reqDate,pl.Descr prjLEveldescr " +
				",r5.descr belongregiondescr,aaw.efficiency ,e2.namechi IntroduceEmpDescr,dw.desc1 workerdepartment,sb2.descr workerspcbuilder " +
				",wt2.descr workerworkType12,w.num workernum,x5.note issignDescr,aaw.signdate  " +
				" from tAutoArrWorkerApp a " +
				" left join tCustomer c on c.code=a.custCode " +
				" left join tWorkType12 wt on wt.code=a.WorkType12 " +
				" left join tXTDM x1 on x1.cbm=a.CustType and x1.id='custType' " +
				" left join tXtdm x2 on x2.cbm=a.isSupvr and x2.id='YESNO'" +
				" left join tDepartment1 d1 on d1.code =a.department1 " +
				" left join tSpcBuilder sb on sb.code =a.Spcbuilder " +
				" left join tRegion r1 on r1.code =a.regionCode " +
				" left join tRegion2 r2 on r2.code =a.regionCode2 " +
				" left join temployee e on e.number= a.projectMan " +
				" left join tWorker w on w.Code=a.WorkerCode " +
				" left join tAutoArrWorker aaw on aaw.WorkerCode=a.WorkerCode and aaw.ArrPK=a.arrPK " +
				" left join tRegion r3 on r3.code=aaw.liveRegion " +
				" left join tRegion2 r4 on r4.code=aaw.liveRegion2 " +
				" left join tRegion r5 on r5.code=aaw.belongRegion " +
				" left join tXTDM x3 on x3.cbm=w.level and x3.id='WORKERLEVEL' " +
				" left join tXTDM x4 on x4.cbm=a.RegIsSpcWorker and x4.id='YESNO' " +
				" left join temployee e2 on e2.number=aaw.IntroduceEmp " +
				" left join tprjlevel pl on pl.code =a.prjlevel " +
				" left join tdepartment1 dw on dw.code=aaw.department1 " +
				" left join tspcBuilder sb2 on sb2.code =aaw.spcbuilder  " +
				" left join tWorkType12 wt2 on wt2.code=aaw.WorkType12 " +
				" left join txtdm x5 on x5.cbm=aaw.issign and x5.id='YESNO' " +
				" where 1=1 and a.workerCode is not null ";
		
		if(custWorkerApp.getArrPK()!=null){
			sql+=" and a.arrPk=? ";
			list.add(custWorkerApp.getArrPK());
		}else {
			sql+="and a.arrPk=(select max(pk) from tAutoArr )";
		}
		if(StringUtils.isNotBlank(custWorkerApp.getAddress())){
			sql+=" and c.Address like ? ";
			list.add("%"+custWorkerApp.getAddress()+"%");
		}
		if(StringUtils.isNotBlank(custWorkerApp.getWorkerCode())){
			sql+=" and a.WorkerCode=?";
			list.add(custWorkerApp.getWorkerCode());
		}
		if(StringUtils.isNotBlank(custWorkerApp.getCustCode())){
			sql+=" and a.custCode=? ";
			list.add(custWorkerApp.getCustCode());
		}
		if(StringUtils.isNotBlank(custWorkerApp.getWorkType12())){
			sql+=" and a.workType12 =?";
			list.add(custWorkerApp.getWorkType12());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ")a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ")a order by a.IsSupvr desc,a.prjlevel desc  ";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	
	/**
	 * 未自动自动安排工地
	 *  
	 *  */
	public Page<Map<String , Object >>findNoArrGdPageBySql(
			Page<Map<String ,Object>> page, CustWorkerApp custWorkerApp) {
			
		List<Object> list = new ArrayList<Object>();
			
		String sql=" select * from (  select a.*,dbo.fGetPrjNormDay(a.CustCode,a.worktype12) prjnormday,c.address," +
				"c.descr custDescr ,wt.descr workType12descr ,x1.note custtypedescr ,x2.note issupDescr " +
				", d1.desc1 department1descr ,sb.descr spcdescr,r1.descr region1descr,r2.descr region2descr,e.nameChi ProjectMandescr " +
				",pl.descr prjleveldescr ,x3.note RegIsSpcWorkerdescr  " +
				" from tAutoArrWorkerApp a " +
				" left join tCustomer c on c.code=a.custCode " +
				" left join tWorkType12 wt on wt.code=a.WorkType12 " +
				" left join tXTDM x1 on x1.cbm=a.CustType and x1.id='custType' " +
				" left join tXtdm x2 on x2.cbm=a.isSupvr and x2.id='YESNO'" +
				" left join tDepartment1 d1 on d1.code =a.department1 " +
				" left join tSpcBuilder sb on sb.code =a.Spcbuilder " +
				" left join tRegion r1 on r1.code =a.regionCode " +
				" left join tRegion2 r2 on r2.code =a.regionCode2 " +
				" left join temployee e on e.number= a.projectMan " +
				" left join tprjlevel pl on pl.code =a.prjlevel " +
				" left join txtdm x3 on x3.cbm= a.RegIsSpcWorker and x3.id='YESNO'" +
				" where 1=1 and workerCode is  null ";
		if(custWorkerApp.getArrPK()!=null){
			sql+=" and a.arrPk = ? ";
			list.add(custWorkerApp.getArrPK());
		}else{
			sql+="and a.arrPk=(select max(pk) from tAutoArr ) ";
		}
		if(StringUtils.isNotBlank(custWorkerApp.getAddress())){
			sql+=" and c.Address like ?";
			list.add("%"+custWorkerApp.getAddress()+"%");
		}
		if(StringUtils.isNotBlank(custWorkerApp.getCustCode())){
			sql+=" and a.custCode=? ";
			list.add(custWorkerApp.getCustCode());
		}
		if(StringUtils.isNotBlank(custWorkerApp.getWorkType12())){
			sql+=" and a.workType12 =?";
			list.add(custWorkerApp.getWorkType12());
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ")a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ")a order by a.IsSupvr desc,a.prjlevel desc  ";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 砌墙工作量
	 * 
	 * */
	public Page<Map<String , Object >>find_qqgzl_PageBySql(
			Page<Map<String ,Object>> page, Customer customer) {
			
		List<Object> list = new ArrayList<Object>();
			
		String sql=" select * from (  select a.*,fa.descr areaDescr,b.descr basedescr,u.descr uomdescr from tBaseItemReq a "+
				" inner join tBaseItem b on a.BaseItemCode=b.Code " +
				" left join tfixArea fa on fa.pk=a.fixareapk " +
				" left  join tUOM u on u.code =b.Uom" +
				" where  1=1 and b.PrjType='1' ";
		
		if(StringUtils.isNotBlank(customer.getCode())){
			sql+=" and a.CustCode= ? ";
			list.add(customer.getCode());
		}
		
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ")a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ")a order by a.pk ";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 饰面工作量
	 * */
	public Page<Map<String , Object >>find_smgzl_PageBySql(
			Page<Map<String ,Object>> page, Customer customer) {
			
		List<Object> list = new ArrayList<Object>();
			
		String sql=" select * from (  select a.*,fa.descr areaDescr,b.descr basedescr,u.descr uomdescr from tBaseItemReq a " +
				" inner join tBaseItem b on b.Code=a.BaseItemCode " +
				" left join tfixArea fa on fa.pk=a.fixareapk " +
				" left  join tUOM u on u.code =b.Uom " +
				" where 1=1 and b.PrjType='2'";
		
		if(StringUtils.isNotBlank(customer.getCode())){
			sql+=" and a.CustCode= ? ";
			list.add(customer.getCode());
		}
		
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " ) a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += " ) a  order by a.pk ";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 木作工作量
	 * 
	 * */
	public Page<Map<String , Object >>find_mzgzl_PageBySql(
			Page<Map<String ,Object>> page, Customer customer) {
			
		List<Object> list = new ArrayList<Object>();
			
		String sql="select * from ( select a.*,a.qty*a.unitprice woodAmount,fa.descr areaDescr,b.descr basedescr,u.descr uomdescr from tBaseItemReq a " +
				" inner join tBaseItem b on b.Code=a.BaseItemCode " +
				" inner join tBaseItemType2 c  on b.BaseItemType2=c.Code " +
				" inner join tWorkType2 e on e.Code =c.OfferWorkType2  " +
				" left join tfixArea fa on fa.pk=a.fixareapk " +
				" left  join tUOM u on u.code =b.Uom" +
				" where  e.WorkType1='04' and 1=1";
		
		if(StringUtils.isNotBlank(customer.getCode())){
			sql+=" and a.CustCode= ? ";
			list.add(customer.getCode());
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.pk ";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 木作工作量
	 * 
	 * */
	public Page<Map<String , Object >>find_fsgzl_PageBySql(
			Page<Map<String ,Object>> page, Customer customer) {
			
		List<Object> list = new ArrayList<Object>();
			
		String sql="select *from (select fa.Descr areadescr,a.qty,u.Descr uomdescr,b.descr basedescr from tBaseItemReq a " +
				" left join tBaseItem b on b.Code=a.BaseItemCode  " +
				" left join tBaseItemType2 c  on b.BaseItemType2=c.Code  " +
				" left join tWorkType2 e on e.Code=c.MaterWorkType2 " +
				" left join tfixArea fa on fa.pk=a.fixareapk " +
				" left  join tUOM u on u.code =b.Uom " +
				" where e.code ='010' ";
		
		if(StringUtils.isNotBlank(customer.getCode())){
			sql+=" and a.CustCode= ? ";
			list.add(customer.getCode());
		}
		
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.qty ";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String , Object >>findWorkerArrPageBySql(
			Page<Map<String ,Object>> page, Customer customer) {
			
		List<Object> list = new ArrayList<Object>();
			
		String sql=" select* from (select b.NameChi,c.descr workType12Descr,a.ComeDate  from tCustWorkerApp a " +
				" left join tworker b on b.code =a.WorkerCode " +
				" left join tworkType12 c on c.code=a.WorkType12 " +
				" where 1=1 and status='2' ";
		
		if(StringUtils.isNotBlank(customer.getCode())){
			sql+=" and a.CustCode= ? ";
			list.add(customer.getCode());
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.ComeDate desc ";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Map<String,Object> specialReqForApply(CustWorkerAppEvt evt){
		List<Object> params = new ArrayList<Object>();
		if("11".equals(evt.getWorkType12())){
			String sql = " SELECT a.Code, "
					   + " CASE WHEN b.ConfirmCZY IS NOT NULL AND b.ConfirmCZY != '' THEN '1' ELSE '0' END isConfirm, "
					   + " CASE WHEN c.hasReq='1' THEN c.hasReq ELSE '0' END hasReq, " 
					   + " CASE WHEN d.hasItemApp='1' THEN d.hasItemApp ELSE '0' END hasItemApp "
					   + " FROM dbo.tCustomer a "
					   + " LEFT JOIN ( "
					   + " 		SELECT CustCode,ConfirmCZY "
					   + " 		FROM dbo.tPrjProg "
					   + " 		WHERE CustCode = ? AND PrjItem='7' "
					   + " ) b ON a.Code = b.CustCode "
					   + " LEFT JOIN ( "
					   + " 		SELECT DISTINCT 1 hasReq,ir.CustCode "
					   + " 		FROM dbo.tItemReq ir "
					   + " 		LEFT JOIN dbo.tItem i ON ir.ItemCode = i.Code "
					   + " 		LEFT JOIN dbo.tItemType2 it2 ON it2.Code = i.ItemType2 "
					   + " 		WHERE ir.CustCode=? AND it2.ItemType12='11' "
					   + " 		AND ir.Expired = 'F' AND i.Expired = 'F' AND it2.Expired='F' AND ir.Qty > 0 "
					   + " ) c ON a.Code = c.CustCode "
					   + " LEFT JOIN ( "
					   + " 		SELECT DISTINCT 1 hasItemApp,ia.CustCode "
 					   + " 		FROM dbo.tItemApp ia "
					   + " 		LEFT JOIN dbo.tItemAppDetail iad ON ia.No = iad.No "
					   + " 		LEFT JOIN dbo.tItem i ON i.Code = iad.ItemCode "
					   + " 		LEFT JOIN dbo.tItemType2 it2 ON it2.Code = i.ItemType2 "
					   + " 		WHERE ia.CustCode = ? AND it2.ItemType12 = '11' "
					   /**
					    * 修改 ia.Status not in ('CANCEL','RETURN','CONRETURN') 为ia.Status in ('CONFIRMED','SEND')
					    * 有瓷砖需求,只要存在瓷砖发货或者审核通过的领料单,可以申请防水班组;无瓷砖需求，判断款项是否交齐
					    * update by zzr 2018/05/08
					    */
					   + " 		AND ia.Status IN ('CONFIRMED','SEND') AND ia.Expired = 'F' "
					   + " 		AND iad.Expired = 'F' AND i.Expired = 'F' AND it2.Expired = 'F' "
					   + " ) d ON a.Code = d.CustCode "
					   + " WHERE a.Code = ? ";
			params.add(evt.getCustCode());
			params.add(evt.getCustCode());
			params.add(evt.getCustCode());
			params.add(evt.getCustCode());
			List<Map<String,Object>> list = this.findBySql(sql, params.toArray());
			if(list != null && list.size() > 0){
				return list.get(0);
			}
		}else if("09".equals(evt.getWorkType12())){
			String sql = " select 1 "
					   + " from tCustWorkerApp tcwa "
					   + " where tcwa.CustCode = ? and tcwa.WorkType12 = ? and tcwa.AppDate >= ? and tcwa.AppDate < ? and Status<>'3' ";
			params.add(evt.getCustCode());
			params.add(evt.getWorkType12());
			Date date = DateUtil.startOfTheDay(new Date());
			params.add(date);
			params.add(DateUtil.addDate(date, 1));
			List<Map<String, Object>> list = this.findBySql(sql, params.toArray());
			if(list != null && list.size() > 0){
				return list.get(0);
			}
		}
		return null;
	}
	
	public Map<String,Object> getCustPayType(String custCode){
		String sql = " SELECT PayType,FirstPay,FourPay FROM dbo.tCustomer WHERE Code=? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{custCode});
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	public Map<String,Object> isSignEmp(String czybh){
		String sql = " SELECT isnull(IsSupvr,'0') IsSupvr FROM dbo.tEmployee WHERE Number=? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{czybh});
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	public String getWorkTypeConDay(String custCode,String workType12){
		String sql = " select dbo.fGetWorkTypeConDay(?,?) conDay ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{custCode,workType12});
		if(list != null && list.size() > 0){
			return  list.get(0).get("conDay").toString();
		}
		return "0";
	}

	public Map<String,Object> judgeBefWorkType12(String custCode, String workType12){
		String sql = " select case when twt12sc.PK is not null  then ( "
				   + " 		case when exists (select 1 from tCustWorker where CustCode=? and WorkType12=twt12sc.BefWorkType12) then '1' "
				   + " 		else '0' end )"
				   + " else '1' end result,tw12.Descr "
				   + " from tCustomer tc "
				   + " left join tWorkType12SeqCtrl twt12sc on twt12sc.CustType = tc.CustType and twt12sc.WorkType12=? "
				   + " left join tWorkType12 tw12 on tw12.Code = twt12sc.BefWorkType12 "
				   + " where tc.Code=? ";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[]{custCode, workType12, custCode});
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	public boolean getNeedWorkType2Req(String custCode,String workType12){
		if(getHasNeedWorkType2Req(workType12)){
			return true;
		}else{
			String sql = " select a.Code,a.Descr,a.BaseItemType2,b.* from tBaseItem a " +
					" left join tBaseItemType2 b on b.code =a.BaseItemType2" +
					" left join tbaseitemreq c on c.BaseItemCode =a.Code  " +
					" where c.CustCode=? and c.Qty>0 " +
					" and b.OfferWorkType2=(select NeedWorkType2Req  from tWorkType12 where code= ? )";
			List<Map<String,Object>> list = this.findBySql(sql, new Object[]{custCode,workType12});
			if(list != null && list.size() > 0){
				return  true;
			}
		return false;
		}
	
	}
	
	public boolean getHasNeedWorkType2Req(String workType12){
		String sql = " select isnull(NeedWorkType2Req,'' )NeedWorkType2Req  from tWorkType12 where code= ? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{workType12});
		if(list != null && list.size() > 0){
			if(StringUtils.isNotBlank(list.get(0).get("NeedWorkType2Req").toString())){
				return false;
			}else {
				return true;
			}
		}
		return true;
	}
	
	public String isNeedZF(String custCode){
		String sql = " select 1 from titemreq  a " +
				" left join titem b on b.Code=a.ItemCode " +
				" left join tItemType2 c on c.Code=b.ItemType2 " +
				" left join tItemType12 d on d.Code=c.ItemType12 " +
				" left join tFixArea e on e.PK=a.FixAreaPK" +
				" where " +
				" c.ItemType12='11' and a.qty> 0 and b.Size >= 0.72 and a.CustCode = ?  and e.Descr like '%墙%' ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{custCode});
		if(list != null && list.size() > 0){
			return "true";
		}
		return "false";
	}
	
	public Page<Map<String , Object >>findZFDetailPageBySql(
			Page<Map<String ,Object>> page, Customer customer) {
			
		List<Object> list = new ArrayList<Object>();
			
		String sql=" select * from (select b.Descr fixAreaDescr,c.Descr itemDescr,a.Qty,d.Descr item2Descr,a.Remark from  titemreq a " +
				" left join tFixArea b on b.PK=a.FixAreaPK" +
				" left join titem c on c.Code=a.ItemCode" +
				" left join tItemType2 d on d.Code=c.ItemType2 " +
				" left join tItemType12 e on e.Code =d.ItemType12 " +
				" where e.Code='11' and b.Descr like '%墙%' and a.qty> 0 and  d.ItemType12='11' and c.Size >= 0.72 ";
		
		if(StringUtils.isNotBlank(customer.getCode())){
			sql+=" and a.CustCode= ? ";
			list.add(customer.getCode());
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a  ";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String , Object >>findItemArrPageBySql(
			Page<Map<String ,Object>> page, CustWorkerApp custWorkerApp) {
			
		List<Object> list = new ArrayList<Object>();
			
		String sql="	select x.note typedescr, b.descr itemType2Descr, c.descr itemType1Descr," +
				"        d.LastAppDay, e.descr itemtype3Descr, a.*," +
				"        case when d.AppType = '1' and a.qty = 0 then '不满足'" +
				"             when d.AppType = '1' and a.qty <> 0 then '满足'             " +
				" when d.AppType = '2' and ( a.qty = 0 or a.sendqty = 0) then '不满足'" +
				"             when d.AppType = '2' and a.qty > a.sendqty then '部分满足'" +
				"             when d.AppType = '2' and a.qty = a.sendqty and datediff(d, a.date,getdate()) < d.lastAppDay" +
				"             then '满足' else '不满足' end isManzu from (" +
				"	select a.pk,max(b.Date)date,isnull(sum(Qty),0) qty,isnull(sum(b.SendQty),0) sendQty from tWorkType12Item a " +
				"	left join (	" +
				"		select   a.Date date, b.qty, b.SendQty,c.ItemType1,c.ItemType2,c.itemtype3" +
				"          from      tItemApp a" +
				"                    left join tItemAppDetail b on b.no = a.No" +
				"                    left join titem c on c.Code = b.ItemCode" +
				"          where     a.CustCode = ? and 1 = 1 and a.Status <> 'CANCEL' and exists ( select" +
				"                                                              1  from   tworktype12item ina  where" +
				"                                                              ina.WorkType12 = ? and c.ItemType1 = ina.ItemType1 and ( ina.ItemType2 = '' or ina.ItemType2 is null or c.ItemType2 = ina.ItemType2" +
				" ) and ( ina.ItemType3 = '' or ina.ItemType3 is null or c.ItemType3 = ina.ItemType3" +
				"                                                              )" +
				"                                                            )         " +
				"       ) b on b.ItemType1=a.ItemType1 and (a.ItemType2= '' or a.ItemType2 is null or a.ItemType2=b.ItemType2) and" +
				"		(a.ItemType3= '' or a.ItemType3 is null or a.ItemType3=b.ItemType3) " +
				"		where a.workType12 = ? group by a.ItemType1 ,case when a.ItemType2 is null  then '' else a.ItemType2 end,a.pk" +
				"		,case when a.ItemType3 is null  then '' else a.ItemType3 end" +
				"		) a " +
				"		left join tWorkType12Item d on d.pk=a.PK" +
				"		left join tItemType2 b on b.code = d.ItemType2" +
				"       left join tItemType1 c on c.code = d.ItemType1" +
				"       left join tItemType3 e on e.Code = d.itemtype3" +
				"       left join tXTDM x on x.id = 'apptype' and x.cbm = d.apptype " +
				"       where ( " +
		        "         d.ItemType1='JZ' or exists(" +
		        "	        select 1 from tItemReq in_a " +
		        "           left join tItem in_b on in_a.ItemCode=in_b.Code" +
		        "           where in_a.CustCode=? and in_b.ItemType1=d.ItemType1 and in_b.ItemType2=d.ItemType2 " +
		        "           and (isnull(d.ItemType3,'')='' or in_b.ItemType3=d.ItemType3) and in_a.Qty>0" +
		        "         )" +
		        "       )";
		list.add(custWorkerApp.getCustCode());
		list.add(custWorkerApp.getWorkType12());
		list.add(custWorkerApp.getWorkType12());
		list.add(custWorkerApp.getCustCode());
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String , Object >>findItemArrDetailPageBySql(
			Page<Map<String ,Object>> page, Integer pk,String custCode) {
			
		List<Object> list = new ArrayList<Object>();
			
		String sql="select  x.note statusDescr,c.descr,a.No,d.descr item1Descr ,e.descr item2Descr ,f.descr item3descr,a.Date,dateDiff(d,a.date,getdate()) datefrom,b.qty,b.SendQty" +
				" from    tItemApp a" +
				"        left join tItemAppDetail b on b.no = a.No" +
				"        left join titem c on c.Code = b.ItemCode" +
				"        left join tItemType1 d on d.Code=c.ItemType1" +
				"        left join tItemType2 e on e.Code=c.ItemType2" +
				"        left join tItemType3 f on f.Code=c.ItemType3 " +
				"		 left join tXtdm x on x.cbm=a.status and x.id='ITEMAPPSTATUS' " +
				" where   a.CustCode = ? and 1=1 and a.Status <> 'CANCEL'" +
				" and exists (select 1 from tworktype12item ina where ina.pk = ? and c.ItemType1=ina.ItemType1 " +
				"				and (ina.ItemType2 ='' or ina.ItemType2 is null or c.ItemType2=ina.ItemType2 )" +
				"				and (ina.ItemType3 ='' or ina.ItemType3 is null or c.ItemType3=ina.ItemType3 )" +
				"	)" ;
		list.add(custCode);
		list.add(pk);
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String , Object >>findWorkTypeBefPageBySql(
			Page<Map<String ,Object>> page, CustWorkerApp custWorkerApp) {
			
		List<Object> list = new ArrayList<Object>();
			
		String sql=" select * from (select f.namechi lastUpdatedByDescr ,c.date,d.descr workType12Descr,e.descr befDescr,case when c.IsPass is null then '未验收' " +
				" when c.IsPass='0' then '验收未通过' else '验收通过' end isPass,si.PrjPassDate " +
				"  from tBefWorkType12 a " +
				" left join tWorkType12 d on d.code=a.WorkType12 " +
				" left join tWorkType12 e on e.Code=a.BefWorktype12 " +
				" left join tPrjProgConfirm c on c.PrjItem=e.PrjItem and c.CustCode= ? " +
				" left join tEmployee f on f.number =c.lastUpdatedBy " +
				" left join (select CustCode,in_b.WorkType12,max(CrtDate)PrjPassDate from tSignIn in_a left join tPrjItem1 in_b on in_a.PrjItem=in_b.Code where IsPass='1' "+
				" group by CustCode,in_b.WorkType12 ) si on si.CustCode=? and si.WorkType12=a.BefWorktype12 "+
				" where  1=1 " ;
		list.add(custWorkerApp.getCustCode());
		list.add(custWorkerApp.getCustCode());
		if(StringUtils.isNotBlank(custWorkerApp.getWorkType12())){
			sql+=" and a.WorkType12= ? ";
			list.add(custWorkerApp.getWorkType12());
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a  ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	
	public List<Map<String, Object>> befWorkType12Conf(String custCode, String workType12){
		String sql = "select isnull(count(1),0) confNum,isnull(sum(cast(confNum as int)),0) prjItemNum " +
				" from (select case when a.WorkerArrCtrl ='1' then isnull(c.IsPass,0) " +
				"      when a.WorkerArrCtrl ='2' then isnull(d.IsPass,0) else 1 end confNum " +//支持上工种验收或者项目经理初检可安排。by cjg 20191225
				" from tBefWorkType12 a " +
				" left join tWorkType12 b on b.code=a.BefWorktype12 " +
				" left join tPrjProgConfirm c on c.PrjItem = b.prjItem and c.CustCode= ? and c.IsPass='1'  " +
				" left join ( select in_a.IsPass,in_b.workType12,in_a.CustCode " +
				"			 from tSignIn in_a " +
				"			 left join tPrjItem1 in_b on in_a.prjItem=in_b.Code  " +
				"			 where in_a.CustCode=? and in_a.IsPass='1' " +
				" ) d on  a.BefWorktype12=d.workType12 " +
				" where 1=1 " +
				" and a.WorkType12= ? )a";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[]{custCode,custCode, workType12});
		if(list != null && list.size() > 0){
			return list;
		}
		return null;
	}
	
	public List<Map<String, Object>> itemSatisfy(String custCode, String workType12){
		String sql="select count(1) num ,isManzu from (	select x.note typedescr, b.descr itemType2Descr, c.descr itemType1Descr," +
				"        d.LastAppDay, e.descr itemtype3Descr, a.*," +
				"        case when d.AppType = '1' and a.qty = 0 then '不满足'" +
				"             when d.AppType = '1' and a.qty <> 0 then '满足'             " +
				" when d.AppType = '2' and ( a.qty = 0 or a.sendqty = 0) then '不满足'" +
				"             when d.AppType = '2' and a.qty > a.sendqty then '部分满足'" +
				"             when d.AppType = '2' and a.qty = a.sendqty and datediff(d, a.date,getdate()) < d.lastAppDay" +
				"             then '满足' else '不满足' end isManzu from (" +
				"	select a.pk,max(b.Date)date,isnull(sum(Qty),0) qty,isnull(sum(b.SendQty),0) sendQty from tWorkType12Item a " +
				"	left join (	" +
				"		select   a.Date date, b.qty, b.SendQty,c.ItemType1,c.ItemType2,c.itemtype3" +
				"          from      tItemApp a" +
				"                    left join tItemAppDetail b on b.no = a.No" +
				"                    left join titem c on c.Code = b.ItemCode" +
				"          where     a.CustCode = ? and 1 = 1 and a.Status <> 'CANCEL' and exists ( select" +
				"                                                              1  from   tworktype12item ina  where" +
				"                                                              ina.WorkType12 = ? and c.ItemType1 = ina.ItemType1 and ( ina.ItemType2 = '' or ina.ItemType2 is null or c.ItemType2 = ina.ItemType2" +
				" ) and ( ina.ItemType3 = '' or ina.ItemType3 is null or c.ItemType3 = ina.ItemType3" +
				"                                                              )" +
				"                                                            )         " +
				"       ) b on b.ItemType1=a.ItemType1 and (a.ItemType2= '' or a.ItemType2 is null or a.ItemType2=b.ItemType2) and" +
				"		(a.ItemType3= '' or a.ItemType3 is null or a.ItemType3=b.ItemType3) " +
				"		where a.workType12 = ? group by a.ItemType1 ,case when a.ItemType2 is null  then '' else a.ItemType2 end,a.pk" +
				"		,case when a.ItemType3 is null  then '' else a.ItemType3 end" +
				"		) a " +
				"		left join tWorkType12Item d on d.pk=a.PK" +
				"		left join tItemType2 b on b.code = d.ItemType2" +
				"        left join tItemType1 c on c.code = d.ItemType1" +
				"        left join tItemType3 e on e.Code = d.itemtype3" +
				"        left join tXTDM x on x.id = 'apptype' and x.cbm = d.apptype )" +
				" a group by isManzu" ;
		List<Map<String, Object>> list = this.findBySql(sql, new Object[]{custCode, workType12,workType12});
		if(list != null && list.size() > 0){
			return list;
		}
		return null;
	}
	
	public Page<Map<String , Object >>getReturnDetail(
			Page<Map<String ,Object>> page, CustWorkerApp custWorkerApp) {
			
		List<Object> list = new ArrayList<Object>();
			
		String sql="select * from ( select a.MsgRelCustCode custcode,b.Address,a.CrtDate,a.MsgText from tPersonMessage a" +
				" left join tcustomer b on b.Code=a.MsgRelCustCode" +
				" where  MsgType='17' ";
		
		if(StringUtils.isNotBlank(custWorkerApp.getCustCode())){
			sql+=" and a.MsgRelCustCode = ? ";
			list.add(custWorkerApp.getCustCode());
		}
		if(custWorkerApp.getDateFrom()!= null){
			sql+=" and a.CrtDate >= ?  ";
			list.add(custWorkerApp.getDateFrom());
		}
		if(custWorkerApp.getDateTo()!= null){
			sql+=" and a.crtDate <= ? ";
			list.add(new Timestamp(
					DateUtil.endOfTheDay(custWorkerApp.getDateTo()).getTime()));
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.CrtDate desc ";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public String getAppComeDate(String custCode,String workType12){
		String sql = " select appComeDate from tCustWorkerApp where CustCode=? and WorkType12=? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{custCode,workType12});
		if(list != null && list.size() > 0){
			return  list.get(0).get("appComeDate").toString();
		}
		return "";
	}
	/**
	 * 安排完工人更新消息为已读
	 * @param custCode
	 * @param workType12
	 */
	public void readMsg(CustWorkerApp custWorkerApp){
		String sql = " update tPersonMessage set RcvStatus='1', RcvDate=getdate() "
		+"where ProgMsgType='2' and RcvStatus='0' and MsgRelCustCode=? and WorkType12=? and MsgRelNo=? ";
		this.executeUpdateBySql(sql, new Object[]{custWorkerApp.getCustCode(),
				custWorkerApp.getWorkType12(),custWorkerApp.getProgTempAlarmPk()+""});
	}
	
	public List<Map<String , Object>> getBefTaskComplete(CustWorkerApp custWorkerApp){
		String sql = " select 1 from tPersonMessage a " +
				" where a.WorkType12 = ? and a.MsgRelCustCode = ? and a.RcvDate is null " +
				" and not exists(" +
				"	select 1 from tCustworkerApp ina " +
				"	left join tPersonMessage inb on inb.MsgRelCustCode = ina.CustCode and inb.workType12 = ina.workType12" +
				"	and ina.pk = ? and cast(ina.ProgTempAlarmPK as nvarchar(20)) = inb.MsgRelNo " +
				" where a.Pk = inb.pk)";
		List<Map<String, Object>> list = this.findBySql(sql, 
					new Object[]{custWorkerApp.getWorkType12(),custWorkerApp.getCustCode(),custWorkerApp.getPk()});
		
		return list;
	}
	
	public Page<Map<String, Object>> goDeJqGrid(Page<Map<String, Object>> page, CustWorker custWorker) {
		
		List<Object> params = new ArrayList<Object>();
		
		String sql=	" select b.Descr BaseItemDescr,c.Descr fixAreaDescr,a.Qty,d.Descr UomDescr,isnull(a.Qty, 0) * isnull(a.OfferPri, 0) TotalPrice "
				+" from tBaseCheckItemPlan a "
				+" left join tBaseCheckItem b on b.Code=a.BaseCheckItemCode "
				+" left join tFixArea c on a.FixAreaPK=c.PK"
				+" left join dbo.tUOM d ON d.Code=b.Uom "
				+" left join tBaseItemType2 e on e.Code = b.BaseItemType2 "
				+" where a.CustCode=? ";
		params.add(custWorker.getCustCode());

		if("0".equals(custWorker.getSalaryCtrlType())){
			sql += " and b.WorkType12= ? ";
			params.add(custWorker.getWorkType12());
		}else if("1".equals(custWorker.getSalaryCtrlType())){
			sql += " and e.OfferWorkType2 = ? ";
			params.add(custWorker.getWorkType2());
		}else{
			sql += " and b.WorkType12= ? ";
			params.add(custWorker.getWorkType12());
		}

		return this.findPageBySql(page, sql, params.toArray());
	}
	
	public Page<Map<String, Object>> findCustWorkerAppPageBySql(
			Page<Map<String, Object>> page, CustWorkerApp custWorkerApp) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select * from ( select b.Address, c.NameChi projectDescr,d.Descr workType12Descr,a.AppDate, a.AppComeDate" +
				" from tCustWorkerApp a" +
				" left join tCustomer b on b.Code = a.CustCode" +
				" left join tEmployee c on c.Number = b.ProjectMan" +
				" left join tWorkType12 d on d.Code = a.WorkType12" +
				" where 1=1 and  a.CustCode = ? ";
		list.add(custWorkerApp.getCustCode());
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ")a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ")a order by appDate desc";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> findAppNoArrangePageBySql(
			Page<Map<String, Object>> page, CustWorkerApp custWorkerApp) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select * from ( select b.Address, c.NameChi projectDescr,d.Descr workType12Descr,a.AppDate, a.AppComeDate,a.remarks " +
				" from tCustWorkerApp a" +
				" left join tCustomer b on b.Code = a.CustCode" +
				" left join tEmployee c on c.Number = b.ProjectMan" +
				" left join tWorkType12 d on d.Code = a.WorkType12" +
				" where 1=1 and  a.CustCode = ? and a.CustWorkPk is null ";
		list.add(custWorkerApp.getCustCode());
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ")a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ")a order by appDate desc";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 是否满足前置三个条件
	 * @param custCode
	 * @param workType12
	 * @param type
	 * @return
	 */
	public String getCanArr(String custCode,String workType12,String type ){
		String sql = "select dbo.fGetCanArr(?,?,?) result";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{custCode,workType12,type});
		if(list != null && list.size() > 0){
			return list.get(0).get("result").toString();
		}
		return "";
	}
	
	public Map<String , Object> getCustWorkerAppDataByCustWorkPk(Integer custWkPk){
		String sql = " select top 1 Remarks appRemarks from tCustWorkerApp a" +
				" where a.CustWorkPk = ? ";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[]{custWkPk});
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		
		return new HashMap<String, Object>();
	}
	
	public void updatePersonMessage(CustWorkerApp custWorkerApp) {
	    String sql = " update tPersonMessage set RcvStatus = '1' ,RcvDate = case when RcvDate is null then getdate() else RcvDate end" +
	    		" from tPersonMessage a" +
	    		" where a.ProgMsgType = '2' and a.MsgRelCustCode = ? and a.WorkType12 = ?";
	    
	    executeUpdateBySql(sql, custWorkerApp.getCustCode(), custWorkerApp.getWorkType12());
	}
	
	public boolean getCheckConfirmPrjItem(String custCode, String workType12){
		String sql = "select 1 from tWorkType12 a " +
				" where a.Code = ? and (a.confPrjItem is null or a.confPrjItem = '' or " +
				" 	exists (select 1 from tPrjProg in_a where in_a.PrjItem = a.PrjItem and in_a.CustCode = ? and a.ConfirmDate is not Null)" +
				" )";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[]{workType12, custCode});
		if(list != null && list.size() > 0){
			return true;
		}
		
		return false;
	}
}	



