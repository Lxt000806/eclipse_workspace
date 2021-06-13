package com.house.home.dao.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.design.Customer;

@SuppressWarnings("serial")
@Repository
public class GzsgqkfxDao extends BaseDao{
	
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page,Customer customer,String isSign,String orderBy,String direction) {
		List<Object> list = new ArrayList<Object>();
		String sql = "  exec pGgzsgqk ?,?,?,?,?,?,?,?,?,?,?,? ";
		list.add(customer.getDateFrom());
		list.add(customer.getDateTo());
		list.add(customer.getCustType());
		list.add(isSign);
		list.add(orderBy);
		list.add(direction);
		list.add(customer.getDepartment2());
		list.add(null==customer.getPrjRegionCode()?"":customer.getPrjRegionCode());
		list.add(customer.getWorkerClassify());
		list.add(customer.getWorkType12Dept());
		if (StringUtils.isBlank(customer.getExpired()) || "F".equals(customer.getExpired())) {
			list.add("F");
		} else {
			list.add("");
		}
		list.add(customer.getWorkType1());
		System.out.println(customer.getWorkType1());
		page.setResult(findListBySql(sql, list.toArray())); 			
		page.setTotalCount(page.getResult().size());
		return page;
	}
	
	/**
	 * 已安排工人
	 * 
	 * */
	public Page<Map<String, Object>> findHasArrPageBySql(Page<Map<String, Object>> page,String workType12,Customer customer,String isSign) {
		List<Object> list = new ArrayList<Object>();
		
		String sql = "select * from (select c.Address,x1.NOTE custtypedescr,x2.note Layoutdescr,c.Area,cwa.AppDate,cwa.AppComeDate," +
				" wt.Descr worktype12descr,w.NameChi workerDescr,MinWs.MinCrtDate,MaxWs.MaxCrtDate," +
				" a.ComeDate,e.NameChi projectManDescr,d1.Desc1 depa1descr ,d2.Desc1 depa2descr,isnull(aw.arrworkload,0) arrworkload from tCustWorker a " +
				" left join tCustomer c on c.Code=a.CustCode " +
				" left join tCustWorkerApp cwa on cwa.CustWorkPk=a.PK" +
				" left join tWorker w on w.Code =a.WorkerCode " +
				" left join tPrjRegion p on p.Code =w.PrjRegionCode " +
				" left join tWorkType12 wt on wt.Code=a.WorkType12 " +
				" left join tEmployee e on e.Number=c.ProjectMan" +
				" left join tDepartment1 d1 on d1.Code=e.Department1" +
				" left join tDepartment2 d2 on d2.Code=e.Department2" +
				" left join tXTDM x1 on x1.CBM=c.CustType and x1.ID='CUSTTYPE'" +
				" left join tXTDM x2 on x2.CBM=c.layout and x2.ID='LAYOUT' " +
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
				" left join(select in_b.worktype12,in_a.custcode,sum(round(in_a.qty*in_a.offerpri,0)) arrworkload "+ 
				" from tbasecheckitemplan in_a "+
				" inner join tbasecheckitem in_b on in_b.code = in_a.basecheckitemcode "+  
				" group by in_b.worktype12,in_a.custcode "+
				" )aw on aw.worktype12=a.worktype12 and aw.CustCode=a.CustCode "+ 

				" where  1=1  ";
		if(StringUtils.isNotBlank(workType12)){
			sql+=" and a.workType12 =?";
			list.add(workType12);
		}
		if(StringUtils.isNotBlank(customer.getPrjRegionCode())){
			sql += " and w.prjRegionCode in " + "('"+customer.getPrjRegionCode().replace(",", "','" )+ "')";
		}
		
		if(StringUtils.isNotBlank(customer.getWorkType12Dept())){
			sql += " and w.WorkType12Dept in " + "('"+customer.getWorkType12Dept().replace(",", "','" )+ "')";
		}
		
		if(StringUtils.isNotBlank(customer.getWorkerClassify())){
			sql += " and w.WorkerClassify in " + "('"+customer.getWorkerClassify().replace(",", "','" )+ "')";
		}
		
		if(customer.getDateFrom()!=null){
			sql+=" and a.comedate >= ?";
			list.add(customer.getDateFrom());
		}
		if(customer.getDateTo()!=null){
			sql+=" and a.comedate<dateAdd(d,1,?) ";
			list.add(customer.getDateTo());
		}
		if(StringUtils.isNotBlank(isSign)){
			sql+=" and w.isSign in  ('"+isSign.replaceAll(",", "','")+"')";
		}
		if(StringUtils.isNotBlank(customer.getDepartment2())){
			sql += " and e.department2 in " + "('"+customer.getDepartment2().replace(",", "','" )+ "')";
		}
		if(StringUtils.isNotBlank(customer.getCustType())){
			sql += " and c.CustType in " + "('"+customer.getCustType().replace(",", "','" )+ "')";
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " )a  order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
			System.out.println(page.getPageOrder());
		} else {
			sql += " )a order by a.comedate desc ";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 工种施工完成情况
	 * 
	 * */
	public Page<Map<String, Object>> findCompletePageBySql(Page<Map<String, Object>> page,String workType12,
			Customer customer,String isSign,String level,String onTimeCmp) {
		List<Object> list = new ArrayList<Object>();
		
		String sql = "select * from (select *,case when constructDays <0 then 0 else constructDays end consDays, case when constructDays<=constructDay then '是' else '否' end isOnTime " +
				" from ( select ppc.pk, a.constructDay, a.CustCode,c.Address,x2.NOTE custtypedescr, " + //增加客户编号 add by zb on 20200224
				" x1.NOTE layoutDescr,c.Area,a.WorkType12,wt.Descr worktype12descr," +
				"  w.NameChi workerdescr,a.ComeDate,ppc.ConfirmDate confDate, DATEDIFF(D,a.ComeDate,a.EndDate)consDay,ppc.ConfirmDate lastupdate ," +
				" xlev.note prjleveldescr,e.NameChi projectmandescr,d1.Desc1 Depa1descr, d2.Desc1 Depa2Descr,e2.NameChi confDescr ," +
				" MaxWs.MaxCrtDate,MinWs.MinCrtDate  ,DATEDIFF(DAY,a.comedate," +
			    "  case when ppc.ConfirmDate is null or ppc.ConfirmDate ='' then a.enddate else ppc.ConfirmDate end )+1 constructDays,a.EndDate ," +
			    " case when convert(varchar(10),a.PlanEnd,120) >= convert(varchar(10),a.endDate,120) then '是' else '否' end EndInTime, " +
			    " si.PrjPassDate, wc.workCompleteDate,isnull(ws.RealSignDays,0)RealSignDays, " +
				" case when a.WorkType12 in('05','20','02','13')then case when isnull(c.InnerArea,0)<>0 then c.InnerArea else c.Area*ct.InnerAreaPer end when a.WorkType12='01' then qqWorkLoad "+ 
				" when a.WorkType12='03' then smWorkLoad when a.WorkType12='11' then fsWorkLoad when a.WorkType12='12' then zpWorkLoad else 0 end WorkLoad "+
				" From tCustWorker a " +
				" left join tWorkType12 wt on wt.Code=a.WorkType12 " +
				" left join tCustomer c on c.Code=a.CustCode" +
				" left join tCustType ct on ct.Code=c.CustType " +
				" left join tWorker  w on w.Code =a.WorkerCode" +
				" left join tPrjRegion p on p.Code =w.PrjRegionCode " +
				/*" left join (" +
				" select a.LastUpdatedBy,a.prjLevel,x1.NOTE prjleveldescr,a.CustCode,c.Code worktype12,max(a.LastUpdate)lastupdate  From tPrjProgConfirm a " +
				" left join tPrjItem1 b on b.Code=a.PrjItem" +
				" left join tWorkType12 c on c.Code=b.worktype12 " +
				" left join tXTDM x1 on x1.CBM=a.prjLevel and x1.ID='PRJLEVEL'" +
				" where a.IsPass='1' group by a.LastUpdatedBy,a.prjLevel,x1.NOTE ,a.CustCode,c.Code  " +
				" ) b on b.CustCode=a.CustCode and a.WorkType12=b.worktype12" +
				 */
				" left join tEmployee e on e.Number=c.ProjectMan" +
				" left join tDepartment1 d1 on d1.Code=e.Department1" +
				" left join tDepartment2 d2 on d2.Code=e.Department2" +
				" left join tXTDM x1 on x1.CBM=c.Layout and x1.ID='LAYOUT'" +
				" left join tXTDM x2 on x2.CBM=c.CustType and x2.ID='CUSTTYPE'" +
				
				" left join tPrjProg ppc on ppc.CustCode=a.CustCode " +
				"	and ppc.prjitem =wt.PrjItem "+
				" left join temployee e2 on e2.Number=ppc.confirmCZY " +
				" left join tXTDM xlev on xlev.CBM=ppc.prjLevel and xlev.ID ='PRJLEVEL' "+
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
				//初检时间
				" left join (" +
				"	select CustCode,in_b.WorkType12,max(CrtDate)PrjPassDate from tSignIn in_a " +
				"	left join tPrjItem1 in_b on in_a.PrjItem=in_b.Code where IsPass='1' "+
				" 	group by CustCode,in_b.WorkType12 " +
				" ) si on si.CustCode = a.CustCode and si.WorkType12=a.Worktype12 "+
				//工人完工时间
				" left join (select max(a.CrtDate)workCompleteDate,a.CustWkPk,b.worktype12 from tWorkSign a " +
					"				left join tPrjItem2 b on a.PrjItem2=b.Code " +
					"			where  a.IsComplete='1' "+
                    " 				group by a.CustWkPk,b.worktype12 " +
                    "				having count(a.PK)=(select count(1) from tPrjItem2 in_pi2 where in_pi2.worktype12=b.worktype12 and in_pi2.expired='F') " +
                    " )  wc on wc.custWkPk=a.PK and wc.worktype12=a.worktype12  " +
				//签到天数
				" left join (" +
				"  select count(*) RealSignDays,c.CustWkPk,c.WorkType12 from ("+
				"   select  a.CustWkPk,b.WorkType12 from tWorkSign a "+
				"   left join tCustWorker b on b.pk=a.CustWkPk " +
				"   group by a.CustWkPk,b.WorkType12,CONVERT(varchar(100), a.crtDate, 112) ) c group by  c.CustWkPk,c.WorkType12 " +
				") ws on a.PK=ws.CustWkPk and a.WorkType12=ws.WorkType12"+
				//完工工作量
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
				" where 1=1   ";//and a.EndDate is not null and  w.IsLeave='0'
		if(StringUtils.isNotBlank(level)){
			sql+=" and w.level= ? ";
			list.add(level);
		}
		if(StringUtils.isNotBlank(customer.getWorkerClassify())){
			sql += " and w.WorkerClassify in " + "('"+customer.getWorkerClassify().replace(",", "','" )+ "')";
		}
		if(StringUtils.isNotBlank(customer.getPrjRegionCode())){
			sql += " and w.prjRegionCode in " + "('"+customer.getPrjRegionCode().replace(",", "','" )+ "')";
		}
		if(StringUtils.isNotBlank(customer.getCustType())){
			sql += " and c.custtype in " + "('"+customer.getCustType().replace(",", "','" )+ "')";
		}
		
		if(StringUtils.isNotBlank(workType12)){
			sql+=" and a.workType12 =? ";
			list.add(workType12);
		}
		if(customer.getDateFrom()!=null){
			sql+=" and a.EndDate >= ?";
			list.add(customer.getDateFrom());
		}
		if(customer.getDateTo()!=null){
			sql+=" and a.EndDate<dateAdd(d,1,?) ";
			list.add(customer.getDateTo());
		}
		if(StringUtils.isNotBlank(customer.getWorkType12Dept())){
			sql += " and w.WorkType12Dept in " + "('"+customer.getWorkType12Dept().replace(",", "','" )+ "')";
		}
		
		if(StringUtils.isNotBlank(customer.getDepartment2())){
			sql+=" and e.department2 in  ('"+customer.getDepartment2().replaceAll(",", "','")+"')";
		}
		
		if(StringUtils.isNotBlank(isSign)){
			sql+=" and w.isSign in  ('"+isSign.replaceAll(",", "','")+"')";
		}
		
		sql+=")a where 1=1 ";
		if("1".equals(onTimeCmp)){
			sql+=" and  a.consDays<=a.constructDay";
		}else if("0".equals(onTimeCmp)){
			sql+=" and  a.consDays>a.constructDay";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += "  )a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += " )a order by a.consDays desc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 班组在建套数
	 * 
	 * */
	public Page<Map<String, Object>> findOndoPageBySql(Page<Map<String, Object>> page,String workType12,Customer customer,String isSign) {
		List<Object> list = new ArrayList<Object>();
		
		String sql = "  select a.code workerCode,a.nameChi workerDescr,wt.descr worktypedescr,x1.note issigndescr" +
				",x2.note Leveldescr,isnull(b.ConsNum,0 )ConsNum from  (" +
				" select count(*)ConsNum ,w.code bcode from tworker w " +
				" left join tcustworker a on a.WorkerCode=w.Code" +
				" left join tcustomer  c on c.Code=a.CustCode " +
				" left join tEmployee e on e.number = c.projectMan" +
				" where a.enddate is null " ;
				if(StringUtils.isNotBlank(customer.getPrjRegionCode())){
					sql+=" and w.prjRegionCode in  ('"+customer.getPrjRegionCode().replaceAll(",", "','")+"')";
				}
				if(StringUtils.isNotBlank(isSign)){
					sql+=" and w.isSign in  ('"+isSign.replaceAll(",", "','")+"')";
				}
				if(StringUtils.isNotBlank(customer.getDepartment2())){
					sql+=" and e.department2 in  ('"+customer.getDepartment2().replaceAll(",", "','")+"')";
				}
				if(StringUtils.isNotBlank(workType12)){
					sql+=" and a.workType12 =? ";
					list.add(workType12);
				}
				sql+=" and w.IsLeave='0' and w.Expired='f'  and c.status='4' " +
				"  group by w.Code)b" +
				" left join tWorker a on b.bCode=a.code " +
				" left join tXTDM x1 on x1.cbm=a.IsSign and x1.id='YESNO' " +
				" left join tXTDM x2 on x2.cbm=a.level and x2.id='WORKERLEVEL' " +
				" left join tWorkType12 wt on wt.code=a.workType12 " +
				" left join tPrjRegion p on p.Code =a.PrjRegionCode " +
				" where 1=1  ";
		if(StringUtils.isNotBlank(customer.getPrjRegionCode())){
			sql+=" and a.prjRegionCode in  ('"+customer.getPrjRegionCode().replaceAll(",", "','")+"')";
		}
		if(StringUtils.isNotBlank(customer.getWorkType12Dept())){
			sql += " and a.WorkType12Dept in " + "('"+customer.getWorkType12Dept().replace(",", "','" )+ "')";
		}
		if(StringUtils.isNotBlank(customer.getWorkerClassify())){
			sql += " and a.WorkerClassify in " + "('"+customer.getWorkerClassify().replace(",", "','" )+ "')";
		}
		if(StringUtils.isNotBlank(isSign)){
			sql+=" and a.isSign in  ('"+isSign.replaceAll(",", "','")+"')";
		}

		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += "   order by b." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += "  order by b.ConsNum desc ";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 停工报备
	 * 
	 * */
	public Page<Map<String, Object>> findBuilderRepPageBySql(Page<Map<String, Object>> page,String workType12,Customer customer,String isSign) {
		List<Object> list = new ArrayList<Object>();
		
		String sql = "select a.*,w.issign,w.NameChi workerDescr from ( select a.workType12,c.Code,d1.Desc1 Depa1descr,d2.Desc1 Depa2Descr,e.namechi projectmandescr,c.Address,wt.Descr worktype12descr,a.BeginDate,a.EndDate," +
				"DATEDIFF(D,a.BeginDate-1,a.EndDate)stopDays from (select case when a.BuildStatus='8' then '01'  when a.BuildStatus='9' then '02'  when a.BuildStatus='11' then '03' " +
				" when a.BuildStatus='10' then '04'  when a.BuildStatus='12' then '05' end workType12,* from tBuilderRep a" +
				" where Type='1')a" +
				" left join tCustomer c on c.Code=a.CustCode" +
				" left join tWorkType12 wt on wt.Code=a.workType12 " +
				" left join tEmployee e on e.Number=c.ProjectMan" +
				" left join tDepartment1 d1 on d1.Code=e.Department1" +
				" left join tDepartment2 d2 on d2.Code=e.Department2" +
				" where a.workType12 is not null ";
		
		if(StringUtils.isNotBlank(workType12)){
			sql+=" and wt.code =? ";
			list.add(workType12);
		}
		if(StringUtils.isNotBlank(customer.getDepartment2())){
			sql += " and e.department2 in " + "('"+customer.getDepartment2().replace(",", "','" )+ "')";
		}
		if(customer.getDateFrom()!=null && customer.getDateTo()!=null){
			sql+=" and ((?<a.BeginDate and a.EndDate>? and a.EndDate<?) " +
					" or (a.BeginDate >=? and a.EndDate<=?)" +
					" or( a.BeginDate>? and a.BeginDate <? and a.EndDate>?)" +
					" or (a.BeginDate<? and a.EndDate>?)) ";
			list.add(customer.getDateFrom());
			list.add(customer.getDateFrom());
			list.add(customer.getDateTo());
			list.add(customer.getDateFrom());
			list.add(customer.getDateTo());
			list.add(customer.getDateFrom());
			list.add(customer.getDateTo());
			list.add(customer.getDateTo());
			list.add(customer.getDateFrom());
			list.add(customer.getDateTo());
		}
		/*if(customer.getDateTo()!=null){
			sql+=" and a.lastupdate<=dateAdd(d,1,?) ";
			list.add(customer.getDateTo());
		}*/
		if(StringUtils.isNotBlank(customer.getCustType())){
			sql += " and c.CustType in " + "('"+customer.getCustType().replace(",", "','" )+ "')";
		}
		
		sql+=" )a " +
			" left join (select MAX(PK)pk,CustCode,WorkType12  from tCustWorker group by CustCode,WorkType12)tcw on tcw.CustCode=a.Code and tcw.WorkType12=a.workType12" +
			" left join tCustWorker b on b.PK=tcw.pk" +
			" left join tWorker w on w.Code=b.WorkerCode "+
			" left join tPrjRegion p on p.Code =w.PrjRegionCode " +
			" where 1=1 " ;
		/*if("1".equals(isSign)){
			sql+=" where  1=1 and w.isSign= ? ";
			list.add(isSign);
		}else{
			sql+=" where  1=1 and w.isSign='0' or w.isSign is null ";
		}*/
		if(StringUtils.isNotBlank(isSign)){
			sql+=" and w.isSign in  ('"+isSign.replaceAll(",", "','")+"')";
		}
		if(StringUtils.isNotBlank(customer.getPrjRegionCode())){
			
			sql+=" and w.prjRegionCode in  ('"+customer.getPrjRegionCode().replaceAll(",", "','")+"')";
		}

		if(StringUtils.isNotBlank(customer.getWorkerClassify())){
			sql += " and w.WorkerClassify in " + "('"+customer.getWorkerClassify().replace(",", "','" )+ "')";
		}
		
		if(StringUtils.isNotBlank(customer.getWorkType12Dept())){
			sql += " and w.WorkType12Dept in " + "('"+customer.getWorkType12Dept().replace(",", "','" )+ "')";
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += " order by a.stopDays ";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 查看班组在建套数
	 * 
	 * */
	public Page<Map<String, Object>> findDetailPageBySql(Page<Map<String, Object>> page,Customer customer) {
		List<Object> list = new ArrayList<Object>();
		
		String sql = "select * From ( select c.address,x2.NOTE CustTypeDescr,x1.NOTE layOutDescr,c.Area,wt.Descr workTypeDescr,c.BeginDate,a.ComeDate,e.NameChi ,d1.Desc1 depa1Descr," +
				" d2.desc1 depa2Descr from tCustWorker a " +
				" left join tCustomer c on c.Code=a.CustCode" +
				" left join tEmployee e on e.Number=c.ProjectMan " +
				" left join tWorkType12 wt on wt.Code=a.WorkType12" +
				" left join tDepartment1 d1 on d1.Code=e.Department1" +
				" left join tDepartment2 d2 on d2.Code=e.Department2" +
				" left join tXTDM x1 on x1.CBM=c.Layout and x1.ID='LAYOUT'" +
				" left join tXTDM x2 on x2.CBM=c.CustType and x2.ID='CUSTTYPE'" +
				" where 1=1 ";
		
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += "  )a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += " )a order by a.code ";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 已验收列表
	 * 
	 * */
	public Page<Map<String, Object>> findHasConfPageBySql(Page<Map<String, Object>> page,String workType12,Customer customer,String isSign) {
		List<Object> list = new ArrayList<Object>();
		
		String sql = "select *From ( select pca.appDate,e.NameChi projectmandescr,case when pca.appDate is null then 0 else datediff(hour,pca.appDate,a.lastupdate) end  confDays " +
				",d1.Desc1 Depa1descr,d2.Desc1 Depa2Descr,e2.namechi confdescr ," +
				" c.ProjectMan,c.Address,a.PrjItem,p1.Descr prjitemDescr,a.LastUpdate confdate,a.LastUpdatedBy,a.prjLevel,x1.NOTE prjlevelDescr,a.Remarks" +
				" from tPrjProgConfirm a" +
				" left join tCustomer c on c.Code=a.CustCode" +
				" left join tPrjItem1 p1 on p1.Code=a.PrjItem " +
				" left join tWorkType12 wt on wt.Code=p1.worktype12" +
				" left join tXTDM x1 on x1.CBM =a.prjLevel and x1.ID='PRJLEVEL'" +
				" left join tEmployee e on e.Number=c.ProjectMan" +
				" left join tDepartment1 d1 on d1.Code=e.Department1" +
				" left join tDepartment2 d2 on d2.Code=e.Department2" +
				" left join tEmployee e2 on e2.Number=a.LastUpdatedBy " +
				" left join tPrjConfirmApp pca on pca.RefConfirmNo=a.No " +
				" left join (select MAX(PK)pk,CustCode,WorkType12  from tCustWorker group by CustCode,WorkType12)tcw " +
				" on tcw.CustCode=a.CustCode and tcw.WorkType12=wt.code" +
				" left join tCustWorker b on b.PK=tcw.pk" +
				" left join tWorker w on w.Code=b.WorkerCode "+
				" left join tPrjRegion p on p.Code =w.PrjRegionCode " +
				" where 1=1 ";
			if(StringUtils.isNotBlank(customer.getPrjRegionCode())){
				sql += " and w.prjRegionCode in " + "('"+customer.getPrjRegionCode().replace(",", "','" )+ "')";
			}
			
			if(StringUtils.isNotBlank(customer.getWorkerClassify())){
				sql += " and w.WorkerClassify in " + "('"+customer.getWorkerClassify().replace(",", "','" )+ "')";
			}
			
			if(StringUtils.isNotBlank(customer.getWorkType12Dept())){
				sql += " and w.workType12Dept in " + "('"+customer.getWorkType12Dept().replace(",", "','" )+ "')";
			}
			
			if(StringUtils.isNotBlank(workType12)){
				sql+=" and wt.code = ? ";
				list.add(workType12);
			}
			
			if(StringUtils.isNotBlank(isSign)){
				sql+=" and w.isSign in  ('"+isSign.replaceAll(",", "','")+"')";
			}
			
			if(customer.getDateFrom()!=null){
				sql+=" and a.date >= ? ";
				list.add(customer.getDateFrom());
			}
			if(customer.getDateTo()!=null){
				sql+=" and a.date<dateAdd(d,1,?) ";
				list.add(customer.getDateTo());
			}
			
			if(StringUtils.isNotBlank(customer.getDepartment2())){
				sql += " and e.department2 in " + "('"+customer.getDepartment2().replace(",", "','" )+ "')";
			}

			if(StringUtils.isNotBlank(customer.getCustType())){
				sql += " and c.CustType in " + "('"+customer.getCustType().replace(",", "','" )+ "')";
			}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += "  )a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += " )a order by a.confdate desc ";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 根据code找工程大区名
	 * @param Code
	 * @return
	 */
	public Map<String , Object>  findPrjRegionDescr(String Code) {
		String sql ="select descr from tPrjRegion where code=?";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{Code});
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	public Page<Map<String, Object>> getWorkSignPicBySql(Page<Map<String, Object>> page,String no) {
		List<Object> list = new ArrayList<Object>();
		
		String sql = " select a.pk, a.photoName, a.lastUpdate, b.nameChi lastUpdatedByDescr from tWorkSignPic a " +
				" left join tWorker b on b.code = a.LastUpdateBy " +
				"where No = ? ";		
		list.add(no);
		return this.findPageBySql(page, sql, list.toArray());
	}

}
