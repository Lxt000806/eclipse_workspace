package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.Company;

@SuppressWarnings("serial")
@Repository
public class CompanyDao extends BaseDao {

	/**
	 * 公司分页信息
	 * 
	 * @param page
	 * @param user
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Company company) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select a.*,b.NOTE TypeDescr from tCompany a " +
				"left join tXTDM b on b.CBM=a.Type and b.ID='CMPTYP' " +
				"where 1=1 ";

		if (StringUtils.isNotBlank(company.getCode())) {
			sql += " and a.Code like ? ";
			list.add("%"+company.getCode()+"%");
		}
		if (StringUtils.isNotBlank(company.getDesc2())) {
			sql += " and a.Desc2 like ? ";
			list.add("%"+company.getDesc2()+"%");
		}
		if (StringUtils.isNotBlank(company.getCmpnyName())) {
			sql += " and a.CmpnyName like ? ";
			list.add("%"+company.getCmpnyName()+"%");
		}
		if (StringUtils.isBlank(company.getExpired())
				|| "F".equals(company.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Code";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	@SuppressWarnings("unchecked")
	public Company getByDesc2(String desc2) {
		String hql = "from Company a where a.Desc2=? ";
		List<Company> list = this.find(hql, new Object[]{desc2});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	/**
	 * 打卡地点
	 * @author	created by zb
	 * @date	2019-5-10--下午5:55:30
	 * @param page
	 * @param company
	 * @return
	 */
	public Page<Map<String, Object>> findSignPlacePageBySql(
			Page<Map<String, Object>> page, Company company) {
		String sql = "select pk,Descr,CmpCode,b.Desc2 CmpCodeDescr,Longitudetppc,Latitudetppc,LimitDistance, " +
				"a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog " +
				"from tSignPlace a " +
				"left join tCompany b on b.Code=a.CmpCode " +
				"where CmpCode=? " +
				"and a.Expired = 'F' ";
		return this.findPageBySql(page, sql, new Object[]{company.getCode()});
	}
	/**
	 * 根据所在地点按顺序获取公司列表(app用)
	 * @author	created by zb
	 * @date	2019-5-21--下午3:08:49
	 * @param page
	 * @param company
	 * @return
	 */
	public Page<Map<String, Object>> findCmpListOrderDistanceBySql(
			Page<Map<String, Object>> page, Company company) {
		String sql = "select b.* from ( " +
					"	select CmpCode,cast(sum( " +
					"	ACOS(SIN(ISNULL(Latitudetppc, 0)/(180/PI()))* " +
					"	SIN(?/(180/PI()))+ " +
					"	COS(ISNULL(Latitudetppc, 0)/(180/PI()))* " +
					"	COS(?/(180/PI()))* " +
					"	COS(?/(180/PI())-ISNULL(Longitudetppc, 0)/(180/PI())))* " +
					"	6371.004) as float) distance " +
					"	from tSignPlace " +
					"	where Expired='F' " +
					"	group by CmpCode " +
					") a  " +
					"left join tCompany b on b.Code=a.CmpCode " +
					"where b.Expired='F' " +
					"order by a.distance ";
		return this.findPageBySql(page, sql, new Object[]{company.getLatitude(),company.getLatitude(),company.getLongitude()});
	}
	/**
	 * 根据所在地点按顺序获取打卡地点列表
	 * @author	created by zb
	 * @date	2019-5-21--下午3:15:33
	 * @param page
	 * @param company
	 * @return
	 */
	public Page<Map<String, Object>> findSignPlaceOrderDistancePageBySql(
			Page<Map<String, Object>> page, Company company) {
		String sql = "select * from ( " +
					"	select PK,Descr,CmpCode,Longitudetppc,Latitudetppc,LimitDistance, " +
					"	ACOS(SIN(ISNULL(Latitudetppc, 0)/(180/PI()))* " +
					"	SIN(?/(180/PI()))+ " +
					"	COS(ISNULL(Latitudetppc, 0)/(180/PI()))* " +
					"	COS(?/(180/PI()))* " +
					"	COS(?/(180/PI())-ISNULL(Longitudetppc, 0)/(180/PI())))* " +
					"	6371.004 distance " +
					"	from tSignPlace " +
					"	where Expired='F' and CmpCode=? " +
					") a " +
					"order by distance";
	return this.findPageBySql(page, sql, new Object[]{company.getLatitude(),company.getLatitude(),
			company.getLongitude(),company.getCode()});
	}
}
