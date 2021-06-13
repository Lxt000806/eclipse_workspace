package com.house.home.dao.project;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.commons.utils.SqlUtil;
import com.house.framework.web.login.UserContext;
import com.house.home.client.service.evt.ModifyConfirmQueryEvt;
import com.house.home.client.service.evt.SitePreparationEvt;
import com.house.home.client.service.evt.WorkerListEvt;
import com.house.home.entity.basic.Xtcs;
import com.house.home.entity.project.PrjProgCheck;

@SuppressWarnings("serial")
@Repository
public class PrjProgCheckDao extends BaseDao {

	/**
	 * PrjProgCheck分页信息
	 * 
	 * @param page
	 * @param prjProgCheck
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, PrjProgCheck prjProgCheck) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select a.*,s1.NOTE PrjItemDescr from tPrjProgCheck a " +
				"left join tXTDM s1 on s1.CBM = a.PrjItem and s1.ID='PRJITEM' " +
				"where 1=1 ";

    	if (StringUtils.isNotBlank(prjProgCheck.getNo())) {
			sql += " and a.No=? ";
			list.add(prjProgCheck.getNo());
		}
    	if (StringUtils.isNotBlank(prjProgCheck.getCustCode())) {
			sql += " and a.CustCode=? ";
			list.add(prjProgCheck.getCustCode());
		}
    	if (StringUtils.isNotBlank(prjProgCheck.getPrjItem())) {
			sql += " and a.PrjItem=? ";
			list.add(prjProgCheck.getPrjItem());
		}
    	if (prjProgCheck.getDateFrom() != null){
			sql += " and a.Date>= ? ";
			list.add(prjProgCheck.getDateFrom());
		}
		if (prjProgCheck.getDateTo() != null){
			sql += " and a.Date<= ? ";
			list.add(prjProgCheck.getDateTo());
		}
    	if (StringUtils.isNotBlank(prjProgCheck.getRemarks())) {
			sql += " and a.Remarks=? ";
			list.add(prjProgCheck.getRemarks());
		}
    	if (StringUtils.isNotBlank(prjProgCheck.getAddress())) {
			sql += " and a.Address=? ";
			list.add(prjProgCheck.getAddress());
		}
    	if (prjProgCheck.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(prjProgCheck.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(prjProgCheck.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(prjProgCheck.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(prjProgCheck.getExpired()) || "F".equals(prjProgCheck.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(prjProgCheck.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(prjProgCheck.getActionLog());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.No";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * PrjProgCheck分页信息
	 * 
	 * @param page
	 * @param prjProgCheck
	 * @return
	 */
	public Page<Map<String,Object>> findConfirmPageBySql(Page<Map<String,Object>> page, PrjProgCheck prjProgCheck,UserContext uc) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from ( select  a.no,a.CustCode,sj.prjitem sjprjitem,s1.note nowspeeddescr, b.Address ,a.PrjItem, b.ProjectMan, e1.NameChi ProjectManDescr,dpt.desc1 DepartmentDescr,b.ConfirmBegin,a.Address GPSAddress ," +
				" x1.NOTE PrjItemDescr,x2.note IsModifyDescr,x3.note BuildStatusDescr,a.ModifyTime,a.Remarks, SafeProm,X5.NOTE SafePromDescr,VisualProm,x6.note VisualPromDescr, ArtProm ,x7.note ArtPromDescr," +
				"	ModifyComplete,x4.NOTE ModifyCompleteDescr,e3.NameChi AppCZYDescr,Date,CompDate, CompCZY,e4.NameChi CompCZYDescr , a.ConfirmDate , e2.NameChi ConfirmCZYDescr,ConfirmCZY ,IsModify " +
				" ,case when a.IsModify = '1' then a.ModifyTime - datediff(hour, a.Date,isnull(a.CompDate, getdate())) else 0 end RemainModifyTime " +
				" from tPrjProgCheck a " +
				"  left join tCustomer b on a.CustCode = b.Code  " +
				" left join tEmployee e1 on b.ProjectMan = e1.Number" +
				" left join tEmployee e2 on a.ConfirmCZY = e2.Number" +
				" left join tEmployee e3 on a.AppCZY = e3.Number" +
				" left join tEmployee e4 on a.CompCZY = e4.Number" +
				" left join tDepartment2 dpt on dpt.code = e1.Department2" +
				" left join tXTDM x1 on a.PrjItem = x1.CBM   and x1.ID = 'PRJITEM'" +
				" left  join  tXTDM x2 on a.IsModify = x2.CBM   and x2.ID = 'YESNO'" +
				" left  join  tXTDM x3 on a.BuildStatus = x3.CBM   and x3.ID = 'BUILDSTATUS' " +
				" left  join  tXTDM x4 on a.ModifyComplete = x4.CBM   and x4.ID = 'YESNO' " +
				" left  join  tXTDM x5 on a.SafeProm = x5.CBM   and x5.ID = 'CHECKSAFEPROM' " +
				" left  join  tXTDM x6 on a.VisualProm = x6.CBM   and x6.ID = 'CHECKPROM' " +
				" left  join  tXTDM x7 on a.ArtProm = x7.CBM   and x7.ID = 'CHECKPROM' " +
				" left join (select MAX(a.PrjItem) prjitem ,a.CustCode from tPrjProg a " +
				" right join ( select  MAX(BeginDate)begindate,CustCode from tPrjProg  group by CustCode " +
				" )b on a.CustCode=b.CustCode and a.BeginDate=b.begindate and a.CustCode=b.CustCode " +
				" group by a.CustCode)sj on a.CustCode=sj.CustCode" +
				" left join tXTDM s1 on s1.CBM=sj.prjitem and s1.ID='PRJITEM'"+
				" where 1=1 and a.IsModify='1' and a.ModifyComplete<>''" +
				" and " +
				 SqlUtil.getCustRight(uc, "b", 0);
		
		if (prjProgCheck.getStatusZG()!=null) {
			if(prjProgCheck.getStatusZG().equals("0")){
				sql +=" and a.ModifyComplete = '0' ";
			}
			if(prjProgCheck.getStatusZG().equals("1")){
				sql +=" and a.ModifyComplete = '1' and a.ConfirmDate is null ";
			}
			if(prjProgCheck.getStatusZG().equals("2")){
				sql +=" and a.ModifyComplete = '1'and a.ConfirmCZY <> '' ";
			}
		}
		
		if (StringUtils.isNotBlank(prjProgCheck.getAddress())) {
			sql += " and b.Address like ? ";
			list.add("%"+prjProgCheck.getAddress()+"%");
		}
		
		if (StringUtils.isNotBlank(prjProgCheck.getDepartment2())) {
			sql += " and e1.Department2 = ? ";
			list.add(prjProgCheck.getDepartment2());
		}
		
		if (StringUtils.isNotBlank(prjProgCheck.getProjectMan())) {
			sql += " and b.projectman = ? ";
			list.add(prjProgCheck.getProjectMan());
		}
		
		if (prjProgCheck.getDateFrom() != null) {
			sql += " and a.Date>= ? ";
			list.add(prjProgCheck.getDateFrom());
		}
		if (prjProgCheck.getDateTo() != null) {
			sql += " and a.Date<= ? ";
			list.add(prjProgCheck.getDateTo());
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a order by a.No desc";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}
		
	
	/**
	 * PrjProgCheck分页信息
	 * 
	 * @param page
	 * @param prjProgCheck
	 * @return
	 */
	public Page<Map<String,Object>> findCheckPageBySql(Page<Map<String,Object>> page, PrjProgCheck prjProgCheck,UserContext uc) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from ( select case when a.custScore is null or custScore = '' then ''" +
				"	 else cast (a.custScore as char(1)) + '星' end custScore,a.custRemarks " +
				",x3.note IsUpPrjProgDescr,x4.note errposiDescr,X5.NOTE SafePromDescr,x6.note VisualPromDescr ,x7.note ArtPromDescr,a.No,a.CustCode,a.PrjItem,a.Remarks,a.Address GPSAddress,a.Date,c.NOTE PrjItemDescr,b.ProjectMan,b.Address, f.NameChi ProjectManDescr, " +
				"a.LastUpdate,a.LastUpdatedBy,a.Expired,g.zwxm CheckMen , a.IsModify, a.ModifyTime, a.ModifyComplete, a.CompRemark, a.CompCZY, a.CompDate, " +
				" x1.note IsModifyDescr , x2.note ModifyCompleteDescr,h.zwxm CompCZYDescr,a.SafeProm,a.VisualProm, a.ArtProm, " +
				" case when a.IsModify = '1' then a.ModifyTime - datediff(hour, a.Date,isnull(a.CompDate, getdate())) else 0 end RemainModifyTime  " +
				" from TPrjProgCheck a " +
				" left join tCustomer b on a.CustCode=b.Code  " +
				" left join tXTDM c on a.PrjItem = c.CBM and c.ID = 'PRJITEM' " +
				" left join tEmployee f on b.ProjectMan=f.Number" +
				" left join tCZYBM g on g.CZYBH=A.LastUpdatedBy" +
				" left join tXTDM  x1 on x1.cbm=a.IsModify and  x1.id ='YESNO' " +
				" left join txtdm x2 on x2.cbm=a.ModifyComplete and  x2.id ='YESNO'" +
				" left join txtdm x3 on x3.cbm=a.IsUpPrjProg and  x3.id ='YESNO'" +
				" left join tCZYBM h on h.CZYBH=a.CompCZY  " +
				" left  join  tXTDM x4 on a.errPosi = x4.CBM   and x4.ID = 'YESNO' " +
				" left  join  tXTDM x5 on a.SafeProm = x5.CBM   and x5.ID = 'CHECKSAFEPROM' " +
				" left  join  tXTDM x6 on a.VisualProm = x6.CBM   and x6.ID = 'CHECKPROM' " +
				" left  join  tXTDM x7 on a.ArtProm = x7.CBM   and x7.ID = 'CHECKPROM' " +
				"where 1=1 and" +
				 SqlUtil.getCustRight(uc, "b", 0);
		
		if (StringUtils.isNotBlank(prjProgCheck.getAddress())) {
			sql += " and b.Address like ? ";
			list.add("%"+prjProgCheck.getAddress()+"%");
		}
		if(StringUtils.isNotBlank(prjProgCheck.getNo())){
			sql+=" and a.no = ?";
			list.add(prjProgCheck.getNo());
		}
		if(StringUtils.isNotBlank(prjProgCheck.getCustScoreCheck())){
			sql+=" and a.custScore in ('"+prjProgCheck.getCustScoreCheck().replace(",", "','")+"')";
		}
		if(StringUtils.isNotBlank(prjProgCheck.getDepartment2())){
			sql+=" and f.department2 = ?";
			list.add(prjProgCheck.getDepartment2());
		}
		if(StringUtils.isNotBlank(prjProgCheck.getLastUpdatedBy())){
			sql+=" and a.LastUpdatedBy = ? ";
			list.add(prjProgCheck.getLastUpdatedBy());
		}
		if(StringUtils.isNotBlank(prjProgCheck.getIsModify())){
			sql+=" and a.isModify =? ";
			list.add(prjProgCheck.getIsModify());
		}
		if(StringUtils.isNotBlank(prjProgCheck.getPrjItem())){
			String str = SqlUtil.resetStatus(prjProgCheck.getPrjItem());
			sql += " and a.PrjItem in (" + str + ")";
		}
		if(StringUtils.isNotBlank(prjProgCheck.getIsUpPrjProg())){
			sql+=" and a.IsUpPrjProg =? ";
			list.add(prjProgCheck.getIsUpPrjProg());
			
		}
		
		if (prjProgCheck.getDateFrom() != null) {
			sql += " and a.date>= ? ";
			list.add(prjProgCheck.getDateFrom());
		}
		if (prjProgCheck.getDateTo() != null) {
			sql += " and a.date-1<= ? ";
			list.add(prjProgCheck.getDateTo());
		}
		if(StringUtils.isNotBlank(prjProgCheck.getErrPosi())){
			sql+=" and a.errPosi =? ";
			list.add(prjProgCheck.getErrPosi());
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a order by a.date desc";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> findPrjCheckPageBySql(Page<Map<String,Object>> page, PrjProgCheck prjProgCheck) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select (select count(1) picNum from tPrjProgPhoto where RefNo= a.no group by RefNo) picNum,x1.note statusDescr,a.prjItem,a.custcode,a.No,x4.NOTE isModifyDescr,X5.NOTE SafePromDescr," +
				" x6.note VisualPromDescr ,x7.note ArtPromDescr,p.Descr,e.NameChi checkCZYdescr " +
				" ,a.Remarks,a.Date,x3.NOTE isUpPrjProgDescr,x2.NOTE errPosiDescr,a.Address,longitude,Latitude,a.isModify " +
				" From tprjProgCheck a " +
				" left join tPrjItem1 p on p.Code=a.PrjItem" +
				" left join tEmployee e on e.Number = a.appCZY " +
				" left join tXTDM x1 on x1.cbm=a.buildStatus and x1.id='Buildstatus' " +
				" left join tXTDM x2 on x2.CBM=a.ErrPosi and x2.ID='YESNO'" +
				" left join tXTDM x3 on x3.CBM=a.IsUpPrjProg and x3.ID='YESNO'" +
				" left join tXTDM x4 on x4.CBM=a.IsModify and x4.ID='YESNO'" +
				" left join tXTDM x5 on a.SafeProm = x5.CBM   and x5.ID = 'CHECKSAFEPROM' " +
				" left join tXTDM x6 on a.VisualProm = x6.CBM   and x6.ID = 'CHECKPROM' " +
				" left join tXTDM x7 on a.ArtProm = x7.CBM   and x7.ID = 'CHECKPROM' " +
				" where 1=1 and a.prjitem <> '' and a.prjitem is not null " ;
		if(StringUtils.isNotBlank(prjProgCheck.getCustCode())){
			sql+=" and a.custcode= ? ";
			list.add(prjProgCheck.getCustCode());
		}
		if(StringUtils.isNotBlank(prjProgCheck.getIsModify())){
			sql+=" and a.IsModify = ? ";
			list.add(prjProgCheck.getIsModify());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += "  order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += "   order by a.date desc";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Page<Map<String, Object>> findPageByLastUpdatedBy(Page page,
			String id,String address,String isModify,String custCode) {
		List<Object> list=new ArrayList<Object>();
		String sql = " select *"
					+" from( " 
					+" select a.No,a.CustCode,b.Address custAddress,a.IsModify,a.PrjItem,c.NOTE prjItemDescr,a.Date,d.NOTE buildStatus, "
					+" CASE WHEN IsModify='0' THEN '无整改' ELSE (CASE WHEN ModifyComplete='1' THEN '已整改' ELSE(CASE WHEN DATEDIFF(HOUR,Date,GETDATE())>ModifyTime  THEN'待整改超时' ELSE '待整改' END) END ) END status "
					+" ,e.NameChi appCzyName ,f.NameChi projectManName"
					+" from tPrjProgCheck a "
					+" inner join tCustomer b on a.CustCode=b.code " 
					+" left join tXTDM c on a.PrjItem=c.CBM and c.ID='PRJITEM' "
					+" left join tXTDM d on a.BuildStatus=d.CBM and d.ID='BUILDSTATUS' "
					+" left join tEmployee e on e.Number = a.AppCZY "
					+" left join temployee f on f.Number=b.ProjectMan "
					+" where 1=1 ";
		if(StringUtils.isNotBlank(id)){
			sql += " and a.appCzy=? ";
			list.add(id);
		}
		if(StringUtils.isNotBlank(custCode)){
			sql += " and a.CustCode=? ";
			list.add(custCode);
		}
		sql+=" ) e where 1=1 ";
		if(StringUtils.isNotBlank(address)){
			sql+="AND e.custAddress LIKE ?";
			list.add("%"+address+"%");
		}
		if(StringUtils.isNotBlank(isModify)){
			if("1".equals(isModify) || "0".equals(isModify)){
				sql+=" and e.IsModify=?";
				list.add(isModify);
			}else{
				sql+=" and e.status=?";
				if("2".equals(isModify)){
					list.add("已整改");
				}else if("3".equals(isModify)){
					list.add("无整改");
				}else if("4".equals(isModify)){
					list.add("待整改");
				}else if("5".equals(isModify)){
					list.add("待整改超时");
				}
			}
		}
		sql+=" order by e.Date desc ";
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public String getSeqNo(String tableName){
		return super.getSeqNo(tableName);
	}

	public Map<String, Object> getByNo(String id, String czybh) {
		Xtcs xtcs = this.get(Xtcs.class, "CheckCtType");
		String sql = "select a.cigaNum,a.No,a.CustCode,b.Address,a.PrjItem,c.NOTE prjItemDescr,a.Date,"
				+"a.address MapAddress,a.remarks,a.isModify,x1.Note isModifyDescr,a.isPushCust,x4.Note isPushCustDescr,a.modifyTime,"
				+"a.modifyComplete,x2.Note modifyCompleteDescr,a.compRemark,a.compDate,a.BuildStatus,x3.NOTE buildStatusDescr,CASE WHEN a.ModifyComplete='1' THEN (a.ModifyTime-DATEDIFF (HOUR,a.Date,a.CompDate)) ELSE (a.ModifyTime-DATEDIFF (HOUR,a.Date,GetDate())) END remainDate,  "
				+"a.safeProm,a.visualProm,a.artProm,d.namechi projectManDescr,f.BuildStatus SafeBuildStatus, " 
				+ "case when f.BuildStatus is null then '否' else '是' end isSafePreparation "
				+ ",h.BuildStatus StopBuildStatus,a.BalconyNum,a.BalconyTitle,a.BedroomNum,a.IsBuildWall,a.IsWood,a.KitchDoorType, "
				+ " a.ToiletNum,case when exists( "
				+ " 		select 1 "
				+ " 		from tczybm a " 
				+ " 		left outer join tEmployee b on b.Number=a.CZYBH "
				+ " 		where b.Department2=(select QZ from tXTCS where ID = 'CheckDept' ) AND a.CZYBH=? "
				+ " ) then '1' else '0' end isCheckDept,case when b.CustType in ('"+xtcs.getQz().trim().replace(",", "','")+"') then '1' else '0' end isCheckCtType, "
				+ " case when a.BalconyNum=0 or a.BalconyNum is null then '0' else '1' end isShowBalconyNum, "
				+ " case when a.BalconyTitle is null then '0' else '1' end isShowBalconyTitle, "
				+ " case when a.BedroomNum=0 or a.BedroomNum is null then '0' else '1' end isShowBedroomNum, "
				+ " case when a.IsBuildWall is null then '0' else '1' end isShowIsBuildWall, "
				+ " case when a.IsWood is null then '0' else '1' end isShowIsWood, "
				+ " case when a.KitchDoorType is null then '0' else '1' end isShowKitchDoorType, "
				+ " case when a.ToiletNum=0 or a.ToiletNum is null then '0' else '1' end isShowToiletNum "
				+"from tPrjProgCheck a "
				+"inner join tCustomer b on a.CustCode=b.code "
				+"left join tXTDM c on a.PrjItem=c.CBM and c.ID='PRJITEM' "
				+"left join tXTDM x1 on a.isModify=x1.CBM and x1.ID='YESNO' "
				+"left join tXTDM x2 on a.modifyComplete=x2.CBM and x2.ID='YESNO'  "
				+"left join tXTDM x3 on a.BuildStatus=x3.CBM and x3.ID='BuildStatus'  "
				+"left join tXTDM x4 on a.isPushCust=x4.CBM and x4.ID='YESNO' "
				+"LEFT JOIN dbo.tEmployee d ON d.Number=b.projectman "
				+ " left join ( "
				+ " 	select  e.CustCode , "
				+ "		( "
				+ " 		select  top 1 BuildStatus " 
				+ "			from tBuilderRep " 
				+ "			where ( BeginDate <= CAST(GETDATE() as date) and EndDate >= CAST(GETDATE() as date)) and Type = '2' and CustCode = e.CustCode " 	
				+ " 	) BuildStatus "
				+ " 	from tBuilderRep e "
				+ " 	group by e.CustCode "
				+ " ) f on f.CustCode=b.Code "
				+ " left join ( "
				+ " 	select  g.CustCode , "
				+ " 	( "
				+ " 		select top 1 BuildStatus " 
				+ "			from tBuilderRep " 
				+ " 		where ( BeginDate <= CAST(GETDATE() as date) and EndDate >= CAST(GETDATE() as date)) and Type = '1' and CustCode = g.CustCode and BuildStatus != '1' "
				+ " 	) BuildStatus "
				+ " 	from tBuilderRep g "
				+ " 	group by g.CustCode "
				+ " ) h on h.CustCode=b.Code "
				+"where a.no=?";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{czybh, id});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	public Page<Map<String, Object>> findPageByCzy(
			Page<Map<String, Object>> page, String czy) {
		String sql="SELECT a.No,b.Address,a.Date ,CASE WHEN a.ModifyComplete='1' THEN (a.ModifyTime-DATEDIFF (HOUR,a.Date,a.CompDate)) ELSE (a.ModifyTime-DATEDIFF (HOUR,a.Date,GetDate())) END remainDate FROM  tPrjProgCheck a " 
				+"INNER JOIN  tCustomer b ON a.CustCode=b.Code "
				+"WHERE IsModify='1' AND compCZY=? AND  ModifyComplete='0'  ORDER BY a.lastUpdate DESC";
		return this.findPageBySql(page, sql, new Object[]{czy});
	}

	public Map<String, Object> getSiteModifyDetailById(String id) {
		String sql="SELECT a.No, b.Address,c.NOTE,a.Date,a.Remarks,a.CompRemark,a.CompDate,a.ModifyTime,a.ModifyComplete,d.NameChi,d.Phone,CASE WHEN a.ModifyComplete='1' THEN (a.ModifyTime-DATEDIFF (HOUR,a.Date,a.CompDate)) ELSE (a.ModifyTime-DATEDIFF (HOUR,a.Date,GetDate())) END remainDate FROM  tPrjProgCheck a "
				+"INNER JOIN  tCustomer b ON a.CustCode=b.Code " 
				+"INNER JOIN tXTDM c ON a.PrjItem=c.IBM "	
				+" LEFT JOIN tEmployee d on a.AppCZY = d.Number "
				+"WHERE  a.no=? and  c.ID='PRJITEM'";
		
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{id});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	public Page<Map<String, Object>> findModifiedPageByCzy(
			Page<Map<String, Object>> page, String czy,String address) {
		List<Object> list=new ArrayList<Object>();
		list.add(czy);
		String sql="SELECT a.No,b.Address,a.Date ,a.ModifyTime,CASE WHEN a.ModifyComplete='1' THEN (a.ModifyTime-DATEDIFF (HOUR,a.Date,a.CompDate)) ELSE (a.ModifyTime-DATEDIFF (HOUR,a.Date,GetDate())) END remainDate FROM  tPrjProgCheck a " 
				+"INNER JOIN  tCustomer b ON a.CustCode=b.Code "
				+"WHERE IsModify='1' AND compCZY=? AND  ModifyComplete='1'  ";
		if(StringUtils.isNotBlank(address)){
			sql+="and b.address like ?";
			list.add("%"+address+"%");
		}
		sql+="ORDER BY a.lastUpdate DESC";
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public boolean isCheckDepart(String czybh){
		boolean result = false;
		String sql = " select * from tczybm a "
					+" left outer join tEmployee b on b.Number=a.CZYBH "
					+" left outer join  tDepartment2 c  on  c.Code=b.Department2 "
					+" where (b.Department2=(select QZ from tXTCS where ID = 'CheckDept' ) or b.Department3=(select QZ from tXTCS where ID = 'CheckDept' )) AND a.CZYBH=?";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{czybh});
		if(list!=null && list.size()>0){
			result = true;
		}
		return result;
	}
	
	public Page<Map<String, Object>> getModifyList(
			Page<Map<String, Object>> page,String address,String custCode){
		List<Object> list=new ArrayList<Object>();
		String sql=" select a.Code custCode,a.Address custAddress,c.ZWXM czyName,b.no,b.Date,b.CompDate,b.Remarks, "
				+" case when b.ConfirmCZY is not null and b.ModifyComplete='1' then '已验收' when b.ModifyComplete='1' then '待验收' else '待整改' end Status "
				+" from tCustomer a "
				+" left join tPrjProgCheck b on b.CustCode = a.Code "
				+" left join tCZYBM c on c.CZYBH = b.AppCZY "
				+" where b.IsModify='1' and b.ModifyComplete is not null ";
		if(StringUtils.isNotBlank(address)){
			sql+=" and a.Address like ? ";
			list.add("%"+address+"%");
		}
		if(StringUtils.isNotBlank(custCode)){
			sql+=" and a.Code=? ";
			list.add(custCode);
		}
		sql += " order by b.Date desc ";
		return this.findPageBySql(page,sql,list.toArray());
	}
	public List<Map<String,Object>> isBegin(String custCode,String prjItem){
		String sql=" select * from tPrjProg "
				  +" where CustCode=? and PrjItem=? and BeginDate is null ";
		return this.findBySql(sql, new Object[]{custCode,prjItem});
	}


	public Page<Map<String, Object>> getModifyConfirmList(Page page,
			ModifyConfirmQueryEvt evt) {
		List<Object> list=new ArrayList<Object>();
		String sql="select * from (SELECT a.no,a.CustCode, b.Address ,a.PrjItem,a.LastUpdate, b.ProjectMan, e1.NameChi ProjectManDescr,dpt.desc1 DepartmentDescr,b.ConfirmBegin,a.Address GPSAddress, "
		          +" x1.NOTE PrjItemDescr,x2.note IsModifyDescr,x3.note BuildStatusDescr,a.ModifyTime,a.Remarks, SafeProm,X5.NOTE SafePromDescr,VisualProm,x6.note VisualPromDescr, ArtProm ,x7.note ArtPromDescr, "
		          +"ModifyComplete,x4.NOTE ModifyCompleteDescr,e3.NameChi AppCZYDescr,Date,CompDate, CompCZY,e4.NameChi CompCZYDescr , a.ConfirmDate , e2.NameChi ConfirmCZYDescr,ConfirmCZY ,IsModify, "
		          +" case when a.IsModify = '1' then a.ModifyTime - datediff(hour, a.Date,isnull(a.CompDate, getdate())) else 0 end RemainModifyTime, "
		         +"case when   a.IsModify='1'  and a.ModifyComplete='0' then (CASE WHEN DATEDIFF(HOUR,a.Date,GETDATE())>a.ModifyTime  THEN '待整改超时' ELSE '待整改未超时' END) else "
		         +"(case when  a.IsModify='1'  and a.ModifyComplete='1' and (ConfirmCZY is null or ConfirmCZY='') then '待验收' " +
		         " else (case when  a.IsModify='1'  and a.ModifyComplete='1' and (ConfirmCZY is not null or ConfirmCZY<>'') then '已验收' end) end "
		         +") end status "
		         +"   from  tPrjProgCheck a  "   
		          +"  left join tCustomer b on a.CustCode = b.Code  "
		          +"  left join tEmployee e1 on b.ProjectMan = e1.Number "
		           +"  left join tEmployee e2 on a.ConfirmCZY = e2.Number   "
		           +"  left join tEmployee e3 on a.AppCZY = e3.Number  "
		           +"  left join tEmployee e4 on a.CompCZY = e4.Number  "
		           +"  left join tDepartment2 dpt on dpt.code = e1.Department2 "
		           +"  left join tXTDM x1 on a.PrjItem = x1.CBM   and x1.ID = 'PRJITEM'"
		           +"  left  join  tXTDM x2 on a.IsModify = x2.CBM   and x2.ID = 'YESNO'"
		           +"  left  join  tXTDM x3 on a.BuildStatus = x3.CBM   and x3.ID = 'BUILDSTATUS'"
		           +"  left  join  tXTDM x4 on a.ModifyComplete = x4.CBM   and x4.ID = 'YESNO' "
		           +" left  join  tXTDM x5 on a.SafeProm = x5.CBM   and x5.ID = 'CHECKSAFEPROM' "
		           +"  left  join  tXTDM x6 on a.VisualProm = x6.CBM   and x6.ID = 'CHECKPROM' "
		           +"   left  join  tXTDM x7 on a.ArtProm = x7.CBM   and x7.ID = 'CHECKPROM' ";
		          
		sql+="where e1.department2 in (SELECT Department2 FROM  dbo.tEmployee WHERE Number=?) "+")a where 1=1  ";
		list.add(evt.getCzy());
		if(StringUtils.isNotBlank(evt.getAddress())){
			sql+="and a.Address like ? ";
			list.add("%"+evt.getAddress()+"%");
		}
		if(StringUtils.isNotBlank(evt.getStatus())){
			sql+="and a.status =? ";
			list.add(evt.getStatus());
		}else{
			sql+="and a.isModify='1' ";
		}
		sql+=" order by a.date desc ";
		return this.findPageBySql(page, sql, list.toArray());
		
	}
	public Page<Map<String,Object>> getSitePreparationList(Page<Map<String,Object>> page,SitePreparationEvt evt){
		List<Object> list = new ArrayList<Object>();
		String sql = " select a.pk,a.CustCode,b.Address,a.BeginDate,a.EndDate,a.Remark,c.note buildStatusDescr, "
					+" case when a.Type = '1' then '停工报备' else (case when a.Type='2' then '安全报备' end) end typeDescr "
					+" from tBuilderRep a "
					+" left join tCustomer b on a.CustCode = b.Code "
					+" left join tXtdm c on a.buildStatus = c.cbm and c.id='BUILDSTATUS' "
					+" where a.Expired='F' ";
		if(StringUtils.isNotBlank(evt.getProjectMan())){
			sql +=" and b.ProjectMan=? ";
			list.add(evt.getProjectMan());
		}
		if(StringUtils.isNotBlank(evt.getAddress())){
			sql +=" and b.Address like ? ";
			list.add("%"+evt.getAddress()+"%");
		}
		if(StringUtils.isNotBlank(evt.getType())){
			if(evt.getType().equals("1") || evt.getType().equals("2")){
				sql +=" and a.Type=? ";
				list.add(evt.getType());
			}else if(evt.getType().equals("3")){
				sql += " and ( a.BeginDate >= cast(getdate() as date) or a.EndDate >= cast(getdate() as date) ) ";
			}
		}
		if (evt.getDate()!= null){
			sql += " and DATEADD(DAY,-1,a.BeginDate)<= ? and DATEADD(DAY,-1,a.EndDate)>=?";
			list.add(evt.getDate());
			list.add(evt.getDate());
		}
		
		sql += " order by a.BeginDate desc ";
		return this.findPageBySql(page, sql, list.toArray());
	}
	public Map<String,Object> getSitePrepartionById(SitePreparationEvt evt){
		List<Object> list = new ArrayList<Object>();
		String sql = " select a.*,b.address from tBuilderRep a " +
				"left join tcustomer b on b.code=a.custCode "+
				"where 1=1 ";
		if(evt.getPk()!=0){
			sql +=" and a.PK=? ";
			list.add(evt.getPk());
		}
		List<Map<String,Object>> lists = this.findBySql(sql, list.toArray());
		if(lists != null && lists.size()>0){
			return lists.get(0);
		}
		return null;
	}
	public boolean isInDepart(String czybh,String depType){
		boolean result = false;
		String sql = " select * "
					+" from tEmployee a "
					+" left join tDepartment1 b on a.Department1 = b.Code "
					+" left join tDepartment2 c on a.Department2 = c.Code "
					+" where ( b.DepType=? or c.DepType=? ) and a.Number=? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{depType,depType,czybh});
		if(list!=null && list.size()>0){
			result = true;
		}
		return result;
	}
	public Map<String,Object> getCustCheck(SitePreparationEvt evt){
		String sql = " select a.AppDate,x1.NOTE MainStatusDescr,a.MainRemark,x2.NOTE SoftStatusDescr,a.SoftRemark,x3.NOTE IntStatusDescr," +
					" a.IntRemark,b.ConfirmDate,x4.NOTE FinStatusDescr,a.FinRemark , x5.note checkSalaryConfStatus "
				   + " from tCustCheck a "
				   + " left join tPrjProg b on a.CustCode=b.CustCode and b.PrjItem='16' "
				   + " left join tXTDM x1 on x1.CBM=a.MainStatus and x1.ID='CUSTCHECKSTS' "
				   + " left join tXTDM x2 on x2.CBM=a.SoftSatus and x2.ID='CUSTCHECKSTS' "
				   + " left join tXTDM x3 on x3.CBM=a.IntStatus and x3.ID='CUSTCHECKSTS' "
				   + " left join tXTDM x4 on x4.CBM=a.FinStatus and x4.ID='CUSTCHECKSTS' "
				   + " left join txtdm x5 on x5.CBM =a.IsSalaryConfirm and x5.id='YESNO'"
				   + " where 1=1 ";
		List<Object> list = new ArrayList<Object>();
		if(StringUtils.isNotBlank(evt.getCustCode())){
			sql += " and a.CustCode=? ";
			list.add(evt.getCustCode());
		}
		List<Map<String,Object>> lists = this.findBySql(sql, list.toArray());
		if(lists!=null&&lists.size()>0){
			return lists.get(0);
		}
		return null;
	}
	public List<Map<String,Object>> sitePreparationJudgeDate(String type,String custCode,Date begin,Date end){
		String sql = " SELECT  PK "
					+" FROM    tBuilderRep "
					+" WHERE   Type = ? "
					+" AND CustCode = ? "
					+" AND  NOT  ( BeginDate > CAST( ? AS DATETIME) OR EndDate < CAST( ? AS DATETIME) ) ";
		return this.findBySql(sql, new Object[]{type,custCode,end,begin});
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Page<Map<String,Object>> getCustWorkerList(Page page,WorkerListEvt evt){
		List<Object> params = new ArrayList<Object>();
		String sql = " SELECT DISTINCT w.NameChi,w.Phone,wt12.Descr as workType12Descr " +
				",cw.ConstructDay as consDay,cw.ComeDate,cw.EndDate,cw.CustCode,cw.WorkerCode,cw.PK,MinWs.MinCrtDate,we.Score,DATEDIFF(DAY,case when MinWs.MinCrtDate is null then cw.comedate else MinWs.MinCrtDate end," +
					" case when ppc.ConfirmDate is null or ppc.ConfirmDate ='' then cw.enddate else ppc.ConfirmDate end ) as relConsDay ";
		if(StringUtils.isNotBlank(evt.getPrjRole())){
			sql += " ,prwt12.PrjRole ";
		}
		sql += " FROM dbo.tCustWorker cw "
		     + " LEFT JOIN dbo.tWorker w ON cw.WorkerCode = w.Code "
			 + " LEFT JOIN dbo.tWorkType12 wt12 ON w.WorkType12 = wt12.Code " +
			 " left join (select  a.CustCode,min(a.CrtDate)MinCrtDate,d.Code from tWorkSign a " +
					"  left join tCustWorker b on b.PK=a.CustWkPk" +
					" left join tWorkType12 d on d.Code=b.WorkType12 " +
					" group by a.CustCode,d.Code) MinWs on cw.custCode=MinWs.custCode and cw.workType12 = minWs.code " 
					+" left join tPrjProg ppc on ppc.CustCode=cw.CustCode and" +
					"  ppc.prjitem =(case when wt12.code='05' then '19' else wt12.PrjItem end)"
					+"left join tWorkerEval we on cw.PK=we.CustWkPk";
		if(StringUtils.isNotBlank(evt.getPrjRole())){
			sql += " LEFT JOIN dbo.tPrjRoleWorkType12 prwt12 ON prwt12.WorkType12 = wt12.Code AND prwt12.PrjRole = ? ";
			params.add(evt.getPrjRole());
		}
		sql += " WHERE cw.CustCode=? ";
		params.add(evt.getCustCode());

		return this.findPageBySql(page, sql, params.toArray());
	}

	
	//    add by hc  工地巡检分析   2017/11/14   begin 
	
	/**
	 * 工地巡检分析
	 * @param page
	 * @param pGdxj_fx
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Page<Map<String,Object>> findPageBySql_gdxj_Tjfs(Page<Map<String,Object>> page, PrjProgCheck prjProgCheck,String orderBy,String direction) {
		Assert.notNull(prjProgCheck);
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pGdxj_fx(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");//pGdxj_fx 工地巡检分析
			call.setInt(1, page.getPageNo());
			call.setInt(2,page.getPageSize());
			call.setString(3, prjProgCheck.getStatistcsMethod());
			call.setTimestamp(4, new java.sql.Timestamp(prjProgCheck.getBeginDate().getTime()));
			call.setTimestamp(5, new java.sql.Timestamp(prjProgCheck.getEndDate().getTime()));
			call.setString(6, prjProgCheck.getDepartment2());
			call.setString(7, prjProgCheck.getDepartment2Check());
			call.setString(8, prjProgCheck.getAddress()); 
			call.setString(9, prjProgCheck.getProjectMan());
			call.setString(10, prjProgCheck.getCheckMan());
			call.setString(11, prjProgCheck.getIsModify());
			call.setString(12, prjProgCheck.getIsModifyComplete());
			call.setString(13, prjProgCheck.getIsUpPrjProg());
			call.setString(14, prjProgCheck.getIncludeErrPosi());
			call.setString(15, orderBy);
			call.setString(16, direction);
			call.setString(17, prjProgCheck.getBuildStatus());
			call.execute();
			ResultSet rs = call.getResultSet(); 
			List<Map<String, Object>> list=BeanConvertUtil.resultSetToList(rs);
			page.setResult(list); 			
			if (!list.isEmpty()) {
				if ("1".equals(prjProgCheck.getStatistcsMethod())){
					page.setTotalCount((Integer)list.get(0).get("totalcount"));
				}
			} else {
				page.setTotalCount(1);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return page;
	} 
	
	/**
	 * 工地巡检分析
	 * @param page
	 * @param pGdxj_fx
	 * @return
	 */
	public Map<String,Object> findRemainModifyTime(String No){
		String sql="select case when a.IsModify = '1' then a.ModifyTime - datediff(hour, a.Date, isnull(a.CompDate, getdate())) " +
				"else 0 end RemainModifyTime from tPrjProgCheck a where No=?";
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{No});
		if (list!=null && list.size()>0){
			Map<String, Object> map = new HashMap<String, Object>();
			 return list.get(0);
		}
		return null;
	}
	//    add by hc  工地巡检分析   2017/11/14   end

	public Map<String,Object> getIntProgress(String custCode){
		String sql = " select tspl1.Descr CupSplDescr,tspl2.Descr IntSplDescr,tspl3.Descr DoorSplDescr,tspl4.Descr TableSplDescr, "
				   + " tcip.CupAppDate,tcip.IntAppDate,tcip.DoorAppDate,tcip.TableAppDate, "
				   + " dateadd(day,(select SendDay from tIntSendDay in_a where in_a.MaterialCode=tcip.CupMaterial and in_a.ItemType12 = '30'),tcip.CupAppDate) cupPlanSendDate, "
				   + " (select Descr from tIntMaterial where Code=tcip.CupMaterial) cupMaterialDescr,tcip.CupSendDate,tcip.CupInstallDate, "
				   + " dateadd(day,(select SendDay from tIntSendDay in_a where in_a.MaterialCode=tcip.IntMaterial and in_a.ItemType12 = '31'),tcip.IntAppDate) intPlanSendDate, "
				   + " (select Descr from tIntMaterial where Code=tcip.IntMaterial) intMaterialDescr,tcip.IntSendDate,tcip.IntInstallDate, "
				   + " a.intJobAppDate,a.intJobDealDate,b.cupJobAppDate,b.cupJobDealDate, tspl1.descr cgSupplDescr,tspl2.descr ygSupplDescr," 
				   +" tspl3.descr tlmSupplDescr , tspl4.Descr tmSupplDescr , tspl1.Code cgSupplCode , tspl2.Code ygSupplCode "
				   + " from tCustIntProg tcip "
				   + " left join tSupplier tspl1 on tspl1.Code = tcip.CupSpl "
				   + " left join tSupplier tspl2 on tspl2.Code = tcip.IntSpl "
				   + " left join tSupplier tspl3 on tspl3.Code = tcip.DoorSpl "
				   + " left join tSupplier tspl4 on tspl4.Code = tcip.TableSpl "
				   + " left join ( "
				   + " 		select CustCode,Date intJobAppDate,DealDate intJobDealDate "
				   + " 		from tPrjJob "
				   + " 		where No=(select max(No) maxNo from tPrjJob where JobType in ('01','04') and CustCode=? group by CustCode ) "
				   + " ) a on a.CustCode = tcip.CustCode "
				   + " left join ( "
				   + " 		select CustCode,Date cupJobAppDate,DealDate cupJobDealDate "
				   + " 		from tPrjJob "
				   + " 		where No=(select max(No) maxNo from tPrjJob where JobType in ('07','25') and CustCode=? group by CustCode ) "
				   + " ) b on b.CustCode = tcip.CustCode "
				   + " where tcip.CustCode=? "; 
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{custCode, custCode, custCode});
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	public Map<String, Object> getRoomInfo(String custCode, String czybh){
		Xtcs xtcs = this.get(Xtcs.class, "CheckCtType");
		String sql = " select tccd.BalconyNum,tccd.BalconyTitle,tccd.BedroomNum,tccd.IsBuildWall,tccd.IsWood,tccd.KitchDoorType, "
				   + " tccd.ToiletNum,case when exists( "
				   + " 		select 1 "
				   + " 		from tczybm a " 
				   + " 		left outer join tEmployee b on b.Number=a.CZYBH "
				   + " 		where b.Department2=(select QZ from tXTCS where ID = 'CheckDept' ) AND a.CZYBH=? "
				   + " ) then '1' else '0' end isCheckDept,case when tc.CustType in ('"+xtcs.getQz().trim().replace(",", "','")+"') then '1' else '0' end isCheckCtType, "
				   + " case when tccd.BalconyNum=0 or tccd.BalconyNum is null then '1' else '0' end isShowBalconyNum, "
				   + " case when tccd.BalconyTitle is null then '1' else '0' end isShowBalconyTitle, "
				   + " case when tccd.BedroomNum=0 or tccd.BedroomNum is null then '1' else '0' end isShowBedroomNum, "
				   + " case when tccd.IsBuildWall is null then '1' else '0' end isShowIsBuildWall, "
				   + " case when tccd.IsWood is null then '1' else '0' end isShowIsWood, "
				   + " case when tccd.KitchDoorType is null then '1' else '0' end isShowKitchDoorType, "
				   + " case when tccd.ToiletNum=0 or tccd.ToiletNum is null then '1' else '0' end isShowToiletNum "
				   + " from tCustomer tc "
				   + " left join tCustCheckData tccd on tccd.CustCode = tc.Code "
				   + " where tc.Code=? ";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[]{czybh, custCode});
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	public Map<String,Object> getCustLoan(String custCode){
		String sql = " select agreeDate,bank,followRemark,signRemark,confuseRemark,amount,firstAmount,firstDate,secondAmount,secondDate "
				   + " from tCustLoan "
				   + " where CustCode=? "; 
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{custCode});
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	public Page<Map<String, Object>> getRegionList(
			Page<Map<String, Object>> page) {
		// TODO Auto-generated method stub
		List<Object> params = new ArrayList<Object>();
		String sql= "select b.code regionCode,b.descr regionDescr from tRegion b " +
				" where 1=1 ";
		return this.findPageBySql(page, sql,params.toArray());
	}
	
	public Map<String,Object> getIntProduce(String custCode,String supplCode){
		String sql = " select b.Address,c.Descr SupplDescr,d.NOTE IsCupboardDescr,a.SetBoardDate,a.arrBoardDate,a.OpenMaterialDate, "
					+" a.SealingSideDate,a.ExHoleDate,a.PackDate,a.InWHDate "
					+" from  tIntProduce a "
					+" left join tCustomer b on a.CustCode = b.Code "
					+" left join tSupplier c on a.SupplCode = c.Code "
					+" left join tXTDM d on a.IsCupboard = d.CBM and d.ID = 'YESNO' "
					+" where a.CustCode = ? and a.SupplCode = ? "; 
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{custCode, supplCode});
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
}

