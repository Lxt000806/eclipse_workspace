package com.house.home.dao.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.project.PrjJobPhoto;

@SuppressWarnings("serial")
@Repository
public class PrjJobPhotoDao extends BaseDao {

	/**
	 * PrjJobPhoto分页信息
	 * 
	 * @param page
	 * @param prjJobPhoto
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, PrjJobPhoto prjJobPhoto) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from tPrjJobPhoto a where 1=1 ";

    	if (prjJobPhoto.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(prjJobPhoto.getPk());
		}
    	if (StringUtils.isNotBlank(prjJobPhoto.getPrjJobNo())) {
			sql += " and a.PrjJobNo=? ";
			list.add(prjJobPhoto.getPrjJobNo());
		}
    	if (StringUtils.isNotBlank(prjJobPhoto.getType())) {
			sql += " and a.Type=? ";
			list.add(prjJobPhoto.getType());
		}
    	if (StringUtils.isNotBlank(prjJobPhoto.getPhotoName())) {
			sql += " and a.PhotoName=? ";
			list.add(prjJobPhoto.getPhotoName());
		}
    	if (prjJobPhoto.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(prjJobPhoto.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(prjJobPhoto.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(prjJobPhoto.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(prjJobPhoto.getExpired()) || "F".equals(prjJobPhoto.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(prjJobPhoto.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(prjJobPhoto.getActionLog());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Pk";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	public List<String> getPhotoListByPrjJobNo(String id, String type) {
		List<Object> params = new ArrayList<Object>(); //改用动态参数列表 add by zzr 2018/04/09
		String sql = "select photoname from tPrjJobPhoto where prjJobNo=?";
		params.add(id);
		
		/*增加type参数,不为空时增加条件 add by zzr 2018/04/09*/
		if(StringUtils.isNotBlank(type)){
			sql += " and type = ? ";
			params.add(type);
		}
		
		List<Map<String,Object>> list = this.findBySql(sql, params.toArray());
		List<String> retList = new ArrayList<String>();
		if (list!=null && list.size()>0){
			for (Map<String,Object> map:list){
				retList.add((String) map.get("photoname"));
			}
		}
		return retList;
	}

	public void deleteByPrjJobNo(String no,String photoName) {
		List<Object> params = new ArrayList<Object>(); 
		String sql = "delete from tPrjJobPhoto where 1=1  ";
		if(StringUtils.isNotBlank(no)){
			sql+=" and prjjobno=? ";
			params.add(no);
		}
		if(StringUtils.isNotBlank(photoName)){
			sql+=" and photoName=? ";
			params.add(photoName);
		}
		this.executeUpdateBySql(sql, params.toArray());
	}

}

