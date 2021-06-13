package com.house.home.dao.driver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.driver.ItemSendPhoto;
import com.house.home.entity.project.PrjProgPhoto;
@SuppressWarnings("serial")
@Repository
public class ItemAppSendPhotoDao extends BaseDao {

	public List<Map<String, Object>> getPhotoList(String no,String type) {
		List<Object> list=new ArrayList<Object>();
		String sql = "select photoName from tItemSendPhoto where sendNo=?";
		list.add(no);
		if(StringUtils.isNotBlank(type)){
			sql+=" and type=?";
			list.add(type);
		}
		return this.findBySql(sql,list.toArray());
	}

	public ItemSendPhoto getByPhotoName(String id) {
		String hql = "from ItemSendPhoto where photoName=?";
		List<ItemSendPhoto> list = this.find(hql, new Object[]{id});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemSendPhoto itemSendPhoto) {
		List<Object> list = new ArrayList<Object>();
		String sql = "  select pk,sendno,photoname,type from titemsendphoto where sendno=?";
		list.add(itemSendPhoto.getSendNo());
		return this.findPageBySql(page, sql, list.toArray());
	}
	public void delPhotoList(String id) {
		List<Object> list = new ArrayList<Object>();
		String sql = "  delete  from tItemSendPhoto where sendNo =?";
		list.add(id);
		executeUpdateBySql(sql, list.toArray());
	}
}
