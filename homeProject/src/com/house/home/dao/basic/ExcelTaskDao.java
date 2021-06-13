package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.home.entity.basic.ExcelTask;

@SuppressWarnings("serial")
@Repository
public class ExcelTaskDao extends BaseDao {

	/**
	 * ExcelTask分页信息
	 * 
	 * @param page
	 * @param excelTask
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ExcelTask excelTask) {

		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select a.PK,a.FileName,a.LastUpdate,a.Result,b.NOTE Status,c.NOTE ImportPlan, "
				+"left(d.Emps,len(d.Emps)-1)Emps "
				+"from tExcelTask a  "
				+"left join tXTDM b on a.Status=b.CBM and b.ID='EXCELTASKSTAT' "
				+"left join tXTDM c on a.ImportPlan=c.CBM and c.ID='IMPORTPLAN' "
				+"left join ( " 
				+"  select pk, "
				+"  (select NameChi+',' from tEmployee " 
				+"  where emps like '%'+Number+'%' " 
				+"  for xml path('')) as Emps "
				+"  from tExcelTask " 
				+") d on a.PK=d.PK  "
				+"where a.Type='1' and LastUpdatedBy=? ";
		list.add(excelTask.getLastUpdatedBy());
		if(StringUtils.isNotBlank(excelTask.getStatus())){
			sql+="and a.Status=?";
			list.add(excelTask.getStatus());
		}
		if (excelTask.getDateFrom() != null){
			sql += " and a.LastUpdate>= ? ";
			list.add(excelTask.getDateFrom());
		}
		if (excelTask.getDateTo() != null){
			sql += " and a.LastUpdate<= ? ";
			list.add(DateUtil.endOfTheDay(excelTask.getDateTo()));
		}
    	if (StringUtils.isNotBlank(excelTask.getUrl())) {
			sql += " and a.Url=? ";
			list.add(excelTask.getUrl());
		}
		if (StringUtils.isBlank(excelTask.getExpired()) || "F".equals(excelTask.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ")a  order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ")a  order by a.LastUpdate desc ";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * 获取最新添加的任务pk
	 * @author cjg
	 * @date 2020-2-21
	 * @return
	 */
	public Integer getMaxExcelTaskPk() {
		String sql = "select max(PK)PK from tExcelTask ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{});
		if (list != null && list.size() > 0) {
			return Integer.parseInt(list.get(0).get("PK").toString());
		}
		return null;
	}
}

