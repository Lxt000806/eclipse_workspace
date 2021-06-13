package com.house.home.dao.workflow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.workflow.ActUser;

@SuppressWarnings("serial")
@Repository
public class ActUserDao extends BaseDao {

	/**
	 * ActUser分页信息
	 * 
	 * @param page
	 * @param actUser
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ActUser actUser) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from ACT_ID_USER a where 1=1 ";

    	if (StringUtils.isNotBlank(actUser.getId())) {
			sql += " and a.ID_=? ";
			list.add(actUser.getId());
		}
    	if (actUser.getRev() != null) {
			sql += " and a.REV_=? ";
			list.add(actUser.getRev());
		}
    	if (StringUtils.isNotBlank(actUser.getFirst())) {
			sql += " and a.FIRST_ like ? ";
			list.add("%"+actUser.getFirst()+"%");
		}
    	if (StringUtils.isNotBlank(actUser.getLast())) {
			sql += " and a.LAST_ like ? ";
			list.add("%"+actUser.getLast()+"%");
		}
    	if (StringUtils.isNotBlank(actUser.getEmail())) {
			sql += " and a.EMAIL_=? ";
			list.add(actUser.getEmail());
		}
    	if (StringUtils.isNotBlank(actUser.getPwd())) {
			sql += " and a.PWD_=? ";
			list.add(actUser.getPwd());
		}
    	if (StringUtils.isNotBlank(actUser.getPictureId())) {
			sql += " and a.PICTURE_ID_=? ";
			list.add(actUser.getPictureId());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.ID_";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

}

