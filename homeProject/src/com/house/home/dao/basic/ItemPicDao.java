package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.ItemPic;

@SuppressWarnings("serial")
@Repository
public class ItemPicDao extends BaseDao {

	/**
	 * ItemPic分页信息
	 * 
	 * @param page
	 * @param activity
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemPic itemPic) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select pk,dispseq,pictype,picfile,pic,itemcode,lastupdate,lastupdatedby,expired,actionLog from tItemPic a where 1=1 ";
	
    	if (StringUtils.isNotBlank(itemPic.getItemCode())) {
			sql += " and a.itemCode=? ";
			list.add(itemPic.getItemCode());
		}else{
			return null;
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.PicType desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	public int getCountNum(String itemCode) {
		String sql = "select isnull(max(DispSeq),0) ret from tItemPic a where  a.itemCode=?";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{itemCode});
		if (list!=null && list.size()>0){
			return Integer.parseInt(String.valueOf(list.get(0).get("ret")));
		}
		return 0;
	}

}

