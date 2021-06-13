package com.house.home.dao.insales;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.insales.ItemPreAppPhoto;

@SuppressWarnings("serial")
@Repository
public class ItemPreAppPhotoDao extends BaseDao {

	/**
	 * ItemPreAppPhoto分页信息
	 * 
	 * @param page
	 * @param itemPreAppPhoto
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemPreAppPhoto itemPreAppPhoto) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from tItemPreAppPhoto a where 1=1 ";

    	if (itemPreAppPhoto.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(itemPreAppPhoto.getPk());
		}
    	if (StringUtils.isNotBlank(itemPreAppPhoto.getNo())) {
			sql += " and a.No=? ";
			list.add(itemPreAppPhoto.getNo());
		}
    	if (StringUtils.isNotBlank(itemPreAppPhoto.getPhotoName())) {
			sql += " and a.PhotoName=? ";
			list.add(itemPreAppPhoto.getPhotoName());
		}
    	if (StringUtils.isNotBlank(itemPreAppPhoto.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(itemPreAppPhoto.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(itemPreAppPhoto.getExpired()) || "F".equals(itemPreAppPhoto.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(itemPreAppPhoto.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(itemPreAppPhoto.getActionLog());
		}
    	if (itemPreAppPhoto.getDateFrom() != null){
			sql += " and a.LastUpdate>= ? ";
			list.add(itemPreAppPhoto.getDateFrom());
		}
		if (itemPreAppPhoto.getDateTo() != null){
			sql += " and a.LastUpdate<= ? ";
			list.add(itemPreAppPhoto.getDateTo());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Pk";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	public List<Map<String,Object>> getPhotoList(String no) {
		String sql = "select photoName from tItemPreAppPhoto where no=?";
		
		return this.findBySql(sql, new Object[]{no});
	}

	@SuppressWarnings("unchecked")
	public ItemPreAppPhoto getByPhotoName(String id) {
		String hql = "from ItemPreAppPhoto where photoName=?";
		List<ItemPreAppPhoto> list = this.find(hql, new Object[]{id});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

}

