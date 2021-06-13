package com.house.home.dao.query;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.home.entity.design.Customer;
import com.house.home.entity.insales.Purchase;
import com.house.home.entity.project.CustCheck;
import com.house.home.entity.project.WorkType12;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

@Repository
@SuppressWarnings("serial")
public class WkTpAnly_CTDao extends BaseDao{

	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page,WorkType12 workType12) {
		List<Object> list = new ArrayList<Object>();
		String sql = "  exec pWkTpAnly_CT ?,?,?,? ";
		list.add(new Timestamp(
				DateUtil.startOfTheDay( workType12.getDateFrom()).getTime()));
		list.add(new Timestamp(
				DateUtil.endOfTheDay(workType12.getDateTo()).getTime()));
		list.add(workType12.getCode());
		list.add(workType12.getIsSign());
		page.setResult(findListBySql(sql, list.toArray())); 			
		page.setTotalCount(page.getResult().size());
		return page;
	}
	
	public Page<Map<String, Object>> findDetailPageBySql(
			Page<Map<String, Object>> page, WorkType12 workType12,String layOut,String area) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select *from ( select case when c.ConfirmDate is null or c.ConfirmDate = '' then 0 " +
				" when datediff(d,a.ComeDate,c.ConfirmDate) <a.ConstructDay then 0 " +
				" else datediff(d,a.ComeDate,c.ConfirmDate)-a.ConstructDay end 拖期天数 ,x1.note 星级," +
				" w.nameChi 工人,f.code,d.Address 楼盘,case when CustType='5' and d.Area<=70 then '70以下'  " +
				" when CustType='5' and d.Area<=100 then '71-100' " +
				" when CustType='5' and d.Area>100 then '100以上' else '' end 面积,a.WorkerCode 工人编号,b.Descr 工种," +
				" f.Desc1  客户类型,j.note 户型,a.ComeDate 进场时间,i.note 工地类型,d.ConstructType," +
				" FirstSign 首次签到时间,c.ConfirmDate 验收时间,a.EndDate 结束时间,a.planend 计划结束时间,a.ConstructDay 计划工期," +
				" datediff(day,case when FirstSign is null or FirstSign>c.ConfirmDate then a.ComeDate else FirstSign end,isnull(c.ConfirmDate,a.EndDate)) 实际工期," +
				" case when a.constructday >(datediff(day,case when FirstSign is null or FirstSign>c.ConfirmDate then a.ComeDate" +
				" else FirstSign end,isnull(c.ConfirmDate,a.EndDate))) then 1 else 0 end 按时完成" +
				" from dbo.tCustWorker a" +
				" inner join tcustomer d on a.CustCode=d.code" +
				" inner join tworktype12 b on a.WorkType12=b.code" +
				" inner join dbo.tCusttype f on d.CustType=f.code" +
				" left join tWorker w on w.code=a.WorkerCode" +
				" left join tPrjProg c on a.CustCode=c.CustCode " +
				" and case when b.code='05' then '19' else b.PrjItem end=c.PrjItem " +
				" left join txtdm i on i.id='CONSTRUCTTYPE' and i.CBM=d.ConstructType" +
				" left join txtdm j on j.id='LAYOUT' and j.CBM=d.LAYOUT " +
				" left join" +
				" (select CustWkPk,min(CrtDate) FirstSign from dbo.tWorkSign group by CustWkPk) g on a.pk=g.CustWkPk " +
				" left join txtdm x1 on x1.cbm =w.level and x1.id='WORKERLEVEL' " +
				" where 1=1 " ;
			
			if(StringUtils.isNotBlank(workType12.getCode())){
				sql+=" and a.workType12 = ? ";
				list.add(workType12.getCode());
			}
			if(workType12.getDateFrom()!=null){
				sql+=" and a.endDate >= ? ";
				list.add(workType12.getDateFrom());
			}
			if(workType12.getDateTo()!=null){
				sql+=" and a.endDate < dateAdd(d,1,?) ";
				list.add(workType12.getDateTo());
			}
			if(StringUtils.isNotBlank(workType12.getIsSign())){
				sql+=" and w.isSign = ? ";
				list.add(workType12.getIsSign());
			}
			if(StringUtils.isNotBlank(workType12.getConstructType())){
				sql+=" and f.code = ? ";
				list.add(workType12.getConstructType());
			}
			if(StringUtils.isNotBlank(layOut)){
				sql+=" and j.note = ? ";
				list.add(layOut);
			}
			if(StringUtils.isNotBlank(area)){
				if("71-100".equals(area.trim())){
					sql+=" and d.area >70 and d.area<=100 ";
				}
				if("100以上".equals(area.trim())){
					sql+=" and d.area > 100 ";
				}
				if("70以下".equals(area.trim())){
					sql+=" and d.area <= 70 ";
				}
			}
			
			if (StringUtils.isNotBlank(page.getPageOrderBy())) {
				sql += ") a order by a." + page.getPageOrderBy() + " "
						+ page.getPageOrder();
			} else {
				sql += ") a  ";
			}
				
		return this.findPageBySql(page, sql,  list.toArray());
	}
}
