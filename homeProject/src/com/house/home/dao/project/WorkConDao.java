package com.house.home.dao.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.CmpCustType;
import com.house.home.entity.project.WorkCon;

@SuppressWarnings("serial")
@Repository
public class WorkConDao extends BaseDao {

	/**
	 * WorkCon分页信息
	 * 
	 * @param page
	 * @param workCon
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, WorkCon workCon) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select a.PK,a.CustCode,a.WorkName,a.WorkType2,a.Amount,a.Area," 
				  +"a.ConstructDay,a.Remarks,c.Descr WorkType1Descr,"
		          +" b.Descr WorkType2Descr,a.LastUpdate,d.Address,"
		          +" a.LastUpdatedBy,a.Expired,a.ActionLog "
		          +" from tWorkCon a,tWorkType2 b,tWorkType1 c,tCustomer d "
		          +" where a.WorkType2=b.Code and b.WorkType1=c.Code and a.CustCode=d.Code ";

    	if (StringUtils.isNotBlank(workCon.getCustCode())) {
			sql += " and a.CustCode=? ";
			list.add(workCon.getCustCode());
		}
    	if (StringUtils.isNotBlank(workCon.getWorkType1())) {
			sql += " and c.Code=? ";
			list.add(workCon.getWorkType1());
		}
    	if (StringUtils.isNotBlank(workCon.getWorkType2())) {
			sql += " and a.WorkType2=? ";
			list.add(workCon.getWorkType2());
		}
		if (StringUtils.isBlank(workCon.getExpired()) || "F".equals(workCon.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.LastUpdate desc";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 重复判断
	 * @param cmpCustType
	 * @return
	 */
	public List<Map<String, Object>> checkExist(WorkCon workCon) {
		List<Object> paraList = new ArrayList<Object>();
		String sql = "select 1 from tWorkCon where WorkType2=? and CustCode=?";
		paraList.add(workCon.getWorkType2());
		paraList.add(workCon.getCustCode());
		if("M".equals(workCon.getM_umState())){
			sql+=" and pk<>?";
			paraList.add(workCon.getPk());
		}
		List<Map<String, Object>> list = this.findListBySql(sql,paraList.toArray());
		return list;
	}
}

