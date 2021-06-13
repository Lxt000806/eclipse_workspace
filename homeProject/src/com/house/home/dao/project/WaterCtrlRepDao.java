package com.house.home.dao.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.project.CustWorker;

@SuppressWarnings("serial")
@Repository
public class WaterCtrlRepDao extends BaseDao{
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page,CustWorker custWorker) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select * from (select a.*,isnull(f.waterCtrlNotSendNum,0) waterCtrlNotSendNum," +
				" d.nameChi workerDescr,isnull(b.waterCtrlNum,0)waterCtrlNum,isnull(c.WaterCtrlArea,0)WaterCtrlArea, " +
				" isnull(i.TolietPerim,0)*0.2+isnull(j.KitchenPerim,0)*0.2+isnull(k.BalconyPerim,0)*0.2 WaterCtrlFlaxArea, " + //高柔性防水施工面积计算 add by zb on 20200306
				" wac.WaterArea,case when wac.Status='2' then '是' when wac.Status='1' then '否' else '' end isConfirmed, " +
				" isnull(g.waterCtrlFlaxNum,0)waterCtrlFlaxNum,isnull(h.waterCtrlNotSendFlaxNum,0)waterCtrlNotSendFlaxNum, " +
				" c1.address,c1.documentNo,m.Descr RegionDescr " +
				" from ( select  b.* from (select max(pk)pk,custcode from ( select b.* from (select min(comeDate)comeDate,CustCode,WorkType12 from dbo.tCustWorker " +
				" where WorkType12='11' group by CustCode,WorkType12)a " +
				" left join dbo.tCustWorker b on b.custCode=a.custCode and b.workType12=a.workType12 and a.comeDate=b.comeDate) a  group by a.CustCode  )a left join tcustworker b on a.pk=b.PK)a " +
				" left join (select  sum(case when a.Type='R' then -b.Qty else b.qty end) waterCtrlNum,CustCode from dbo.tItemApp a " +
				" left join dbo.tItemAppDetail b on b.No=a.No " +
				" left join dbo.tItem d on d.Code=b.ItemCode " +
				" left join dbo.tItemType2 e on e.code=d.ItemType2 " +
				" where  e.MaterWorkType2='010' and b.ItemCode not in(select QZ from tXTCS where ID='FLXWATERITEM') and a.status in ('send','Return') group by CustCode)b on b.custcode=a.CustCode " +
				" left join (select  sum(case when a.Type='R' then -b.Qty else b.qty end) waterCtrlFlaxNum,CustCode from dbo.tItemApp a " +
				" left join dbo.tItemAppDetail b on b.No=a.No " +
				" left join dbo.tItem d on d.Code=b.ItemCode " +
				" left join dbo.tItemType2 e on e.code=d.ItemType2 " +
				" where  e.MaterWorkType2='010' and b.ItemCode in(select QZ from tXTCS where ID='FLXWATERITEM') and a.status in ('send','Return') group by CustCode)g on g.custcode=a.CustCode " +
				" left join (select sum(Qty) WaterCtrlArea,CustCode from tBaseItemReq a" +
				" left join dbo.tBaseItem b on b.Code=a.BaseItemCode " +
				" left join tbaseitemtype2 c on c.Code=b.BaseItemType2 " +
				" where c.MaterWorkType2='010'  group by  CustCode)c on c.CustCode=a.CustCode " +
				" left join tWorker d on d.code=a.workerCode " +
				" left join (select  sum( case when a.Type='R' then -b.Qty else b.qty end ) waterCtrlNotSendNum,CustCode " +
				" from dbo.tItemApp a" +
				" left join dbo.tItemAppDetail b on b.No=a.No " +
				" left join dbo.tItem d on d.Code=b.ItemCode" +
				" left join dbo.tItemType2 e on e.code=d.ItemType2 " +
				" where  e.MaterWorkType2='010' and b.ItemCode not in(select QZ from tXTCS where ID='FLXWATERITEM') and a.status in('OPEN','CONFIRMED','CONRETURN') group by CustCode)f on f.custcode=a.CustCode " +
				" left join (select  sum( case when a.Type='R' then -b.Qty else b.qty end ) waterCtrlNotSendFlaxNum,CustCode " +
				" from dbo.tItemApp a" +
				" left join dbo.tItemAppDetail b on b.No=a.No " +
				" left join dbo.tItem d on d.Code=b.ItemCode" +
				" left join dbo.tItemType2 e on e.code=d.ItemType2 " +
				" where  e.MaterWorkType2='010' and b.ItemCode in(select QZ from tXTCS where ID='FLXWATERITEM') and a.status in('OPEN','CONFIRMED','CONRETURN') group by CustCode)h on h.custcode=a.CustCode " +
				" left join ( " +
				" 	select in_b.CustCode,sum(in_b.Perimeter) TolietPerim " +
				" 	from tPrePlanArea in_b " +
				" 	where in_b.FixAreaType='3' " + //卫生间
				" 	group by in_b.CustCode " +
				" )i on i.CustCode=a.CustCode " +
				" left join ( " +
				" 	select in_b.CustCode,sum(in_b.Perimeter) KitchenPerim " +
				" 	from tPrePlanArea in_b " +
				" 	where in_b.FixAreaType='5' " + //厨房
				" 	group by in_b.CustCode " +
				" )j on j.CustCode=a.CustCode " +
				" left join ( " +
				" 	select in_b.CustCode,sum(in_b.Perimeter) BalconyPerim " +
				" 	from tPrePlanArea in_b " +
				" 	where in_b.FixAreaType='4' " + //阳台
				" 	group by in_b.CustCode " +
				" )k on k.CustCode=a.CustCode" +
				" left join tWaterAreaCfm wac on wac.CustCode=a.CustCode " +
				" left join tCustomer c1 on c1.Code=a.CustCode " +
				" left join tCustomer c2 on c2.Code=a.CustCode " +
				" left join tBuilder l on l.Code=c2.BuilderCode " +
				" left join tRegion m on m.Code=l.RegionCode " +
				" where 1=1 and a.workType12='11' " ;
		
		if(custWorker.getDateFrom()!=null){
			sql+=" and a.comedate>= ? ";
			list.add(custWorker.getDateFrom());
			
		}
		if(custWorker.getDateTo()!=null){
			sql+=" and a.comedate <dateAdd(d,1,?)";
			list.add(custWorker.getDateTo());
		}
		if (StringUtils.isNotBlank(custWorker.getWorkerCode())) {
			sql += " and a.WorkerCode=? ";
			list.add(custWorker.getWorkerCode());
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ") a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ") a order by a.comeDate desc ";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> findPageByWorker(Page<Map<String,Object>> page,CustWorker custWorker) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select * from( select a.*,c.NameChi WorkerDescr," +
				" case when isnull(cs1.qz,'')='' or cast(cs1.qz as money)=0 then 0 else round(case when isnull(a.WaterArea,0)=0 then a.WaterCtrlArea else a.WaterArea end /cast(cs1.qz as money),0) end StandardMum, " + 
				" case when isnull(cs3.qz,'')='' or cast(cs1.qz as money)=0 then 0 else round(WaterCtrlFlaxArea/cast(cs3.qz as money),0) end FlxStandardMum, " +
				" case when isnull(cs1.qz,'')='' or cast(cs1.qz as money)=0 then 0 else (round(case when isnull(a.WaterArea,0)=0 then a.WaterCtrlArea else a.WaterArea end/cast(cs1.qz as money),0)-waterctrlnum)*cast(cs2.qz as money) end WaterAward," + 
				" case when isnull(cs4.qz,'')='' or cast(cs1.qz as money)=0 then 0 else (round(WaterCtrlFlaxArea/cast(cs3.qz as money),0)-waterCtrlFlaxNum)*cast(cs4.qz as money) end FlxWaterAward " +
				" from ( " +
				" select a.WorkerCode,sum(isnull(f.waterCtrlNotSendNum,0)) waterCtrlNotSendNum, " +
				" sum(isnull(b.waterCtrlNum,0)) waterCtrlNum,sum(isnull(c.WaterCtrlArea,0)) WaterCtrlArea,  " +
				" sum(isnull(i.TolietPerim,0)*0.2+isnull(j.KitchenPerim,0)*0.2+isnull(k.BalconyPerim,0)*0.2) WaterCtrlFlaxArea, " +
				" sum(isnull(wac.WaterArea, 0)) WaterArea,sum(isnull(g.waterCtrlFlaxNum,0)) waterCtrlFlaxNum, " +
				" sum(isnull(h.waterCtrlNotSendFlaxNum,0)) waterCtrlNotSendFlaxNum   " +
				" from (  " +
				" 	select  b.*  " +
				" 	from ( " +
				" 		select max(pk)pk,custcode  " +
				" 		from (  " +
				" 			select b.*  " +
				" 			from ( " +
				" 				select min(comeDate)comeDate,CustCode,WorkType12  " +
				" 				from dbo.tCustWorker   " +
				" 				where WorkType12='11'  " +
				" 				group by CustCode,WorkType12 " +
				" 			)a   " +
				" 			left join dbo.tCustWorker b on b.custCode=a.custCode and b.workType12=a.workType12  " +
				" 				and a.comeDate=b.comeDate " +
				" 		) a   " +
				" 		group by a.CustCode   " +
				" 	)a  " +
				" 	left join tcustworker b on a.pk=b.PK " +
				" )a   " +
				" left join ( " +
				" 	select  sum(case when a.Type='R' then -b.Qty else b.qty end) waterCtrlNum,CustCode  " +
				" 	from dbo.tItemApp a   " +
				" 	left join dbo.tItemAppDetail b on b.No=a.No   " +
				" 	left join dbo.tItem d on d.Code=b.ItemCode   " +
				" 	left join dbo.tItemType2 e on e.code=d.ItemType2   " +
				" 	where  e.MaterWorkType2='010' and b.ItemCode not in(select QZ from tXTCS where ID='FLXWATERITEM')  " +
				" 	and a.status in ('send','Return')  " +
				" 	group by CustCode " +
				" )b on b.custcode=a.CustCode   " +
				" left join ( " +
				" 	select  sum(case when a.Type='R' then -b.Qty else b.qty end) waterCtrlFlaxNum,CustCode  " +
				" 	from dbo.tItemApp a   " +
				" 	left join dbo.tItemAppDetail b on b.No=a.No   " +
				" 	left join dbo.tItem d on d.Code=b.ItemCode   " +
				" 	left join dbo.tItemType2 e on e.code=d.ItemType2   " +
				" 	where  e.MaterWorkType2='010'  " +
				" 	and b.ItemCode in(select QZ from tXTCS where ID='FLXWATERITEM')  " +
				" 	and a.status in ('send','Return')  " +
				" 	group by CustCode " +
				" )g on g.custcode=a.CustCode   " +
				" left join ( " +
				" 	select sum(Qty) WaterCtrlArea,CustCode  " +
				" 	from tBaseItemReq a  " +
				" 	left join dbo.tBaseItem b on b.Code=a.BaseItemCode   " +
				" 	left join tbaseitemtype2 c on c.Code=b.BaseItemType2   " +
				" 	where c.MaterWorkType2='010'   " +
				" 	group by  CustCode " +
				" )c on c.CustCode=a.CustCode   " +
				" left join tWorker d on d.code=a.workerCode   " +
				" left join ( " +
				" 	select  sum( case when a.Type='R' then -b.Qty else b.qty end ) waterCtrlNotSendNum,CustCode   " +
				" 	from dbo.tItemApp a  " +
				" 	left join dbo.tItemAppDetail b on b.No=a.No   " +
				" 	left join dbo.tItem d on d.Code=b.ItemCode  " +
				" 	left join dbo.tItemType2 e on e.code=d.ItemType2   " +
				" 	where  e.MaterWorkType2='010' and b.ItemCode not in(select QZ from tXTCS where ID='FLXWATERITEM')  " +
				" 	and a.status in('OPEN','CONFIRMED','CONRETURN')  " +
				" 	group by CustCode " +
				" )f on f.custcode=a.CustCode   " +
				" left join ( " +
				" 	select  sum( case when a.Type='R' then -b.Qty else b.qty end ) waterCtrlNotSendFlaxNum,CustCode   " +
				" 	from dbo.tItemApp a  " +
				" 	left join dbo.tItemAppDetail b on b.No=a.No   " +
				" 	left join dbo.tItem d on d.Code=b.ItemCode  " +
				" 	left join dbo.tItemType2 e on e.code=d.ItemType2   " +
				" 	where  e.MaterWorkType2='010' and b.ItemCode in(select QZ from tXTCS where ID='FLXWATERITEM')  " +
				" 	and a.status in('OPEN','CONFIRMED','CONRETURN')  " +
				" 	group by CustCode " +
				" )h on h.custcode=a.CustCode" +
				" left join ( " +
				" 	select in_b.CustCode,sum(in_b.Perimeter) TolietPerim " +
				" 	from tPrePlanArea in_b " +
				" 	where in_b.FixAreaType='3' " + //卫生间
				" 	group by in_b.CustCode " +
				" )i on i.CustCode=a.CustCode " +
				" left join ( " +
				" 	select in_b.CustCode,sum(in_b.Perimeter) KitchenPerim " +
				" 	from tPrePlanArea in_b " +
				" 	where in_b.FixAreaType='5' " + //厨房
				" 	group by in_b.CustCode " +
				" )j on j.CustCode=a.CustCode " +
				" left join ( " +
				" 	select in_b.CustCode,sum(in_b.Perimeter) BalconyPerim " +
				" 	from tPrePlanArea in_b " +
				" 	where in_b.FixAreaType='4' " + //阳台
				" 	group by in_b.CustCode " +
				" )k on k.CustCode=a.CustCode" +
				" left join tWaterAreaCfm wac on wac.CustCode=a.CustCode "+
				" where 1=1 and a.workType12='11' " ;
		
		if(custWorker.getDateFrom()!=null){
			sql+=" and a.comedate>= ? ";
			list.add(custWorker.getDateFrom());
			
		}
		if(custWorker.getDateTo()!=null){
			sql+=" and a.comedate <dateAdd(d,1,?)";
			list.add(custWorker.getDateTo());
		}
		if (StringUtils.isNotBlank(custWorker.getWorkerCode())) {
			sql += " and a.WorkerCode=? ";
			list.add(custWorker.getWorkerCode());
		}
		
		sql += "group by a.WorkerCode ) a  " +
				"left join tWorker c on c.Code=a.WorkerCode " +
				" left join txtcs cs1 on cs1.ID='WATERAREAPERMAT' "+
			    " left join txtcs cs2 on cs2.ID='WATERREWARDAMTPERMAT' "+
			    " left join txtcs cs3 on cs3.ID='FLXWATERAREAPERMAT' "+
			    " left join txtcs cs4 on cs4.ID='FLXWATERREWARDAMTPERMAT' "+
			    " ) a ";	    
		if (StringUtils.isNotBlank(page.getPageOrderBy())){	
			sql +="order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}
}
