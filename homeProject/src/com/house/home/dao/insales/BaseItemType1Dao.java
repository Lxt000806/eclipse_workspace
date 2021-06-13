package com.house.home.dao.insales;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.dao.MenuDao;
import com.house.framework.entity.Menu;
import com.house.home.entity.insales.BaseItemType1;

@SuppressWarnings("serial")
@Repository
public class BaseItemType1Dao extends BaseDao {
	@Autowired
	private MenuDao menuDao;

	/**
	 * BaseItemType1分页信息
	 * 
	 * @param page
	 * @param baseItemType1
	 * @return
	 */
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, BaseItemType1 baseItemType1) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select a.Code,a.Descr,a.DispSeq,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog from tBaseItemType1 a where 1=1 ";

    	if (StringUtils.isNotBlank(baseItemType1.getCode())) {
			sql += " and a.Code=? ";
			list.add(baseItemType1.getCode());
		}
    	if (StringUtils.isNotBlank(baseItemType1.getDescr())) {
			sql += " and a.Descr like ? ";
			list.add("%"+baseItemType1.getDescr()+"%");
		}
    	if (baseItemType1.getDispSeq() != null) {
			sql += " and a.DispSeq=? ";
			list.add(baseItemType1.getDispSeq());
		}
    	if (baseItemType1.getLength() != null) {
			sql += " and a.Length=? ";
			list.add(baseItemType1.getLength());
		}
    	if (baseItemType1.getLastNumber() != null) {
			sql += " and a.LastNumber=? ";
			list.add(baseItemType1.getLastNumber());
		}
    	if (baseItemType1.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(baseItemType1.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(baseItemType1.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(baseItemType1.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(baseItemType1.getExpired()) || "F".equals(baseItemType1.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(baseItemType1.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(baseItemType1.getActionLog());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " )a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " )a order by a.LastUpdate desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	public List<Menu> getTreeMenu() {
		// TODO Auto-generated method stub
		String sql="select rtrim(a.Code) Code,a.Descr Descr,rtrim(b.Code) Code2,b.Descr Code2Descr " +
				"from tBaseItemType1 a  left outer join tBaseItemType2 b on b.BaseItemType1=a.Code and b.Expired = 'F' " +
				" where a.Expired = 'F'  group by a.Code,b.Code,a.Descr,b.Descr,a.DispSeq,b.DispSeq  order by a.DispSeq,b.DispSeq";
		List<Map<String,Object>> list= this.findBySql(sql, null);
		return menuDao.getMenuListByList(list,"/admin/baseItem/goItemSelect","Code2","Code", "Code2Descr","Descr", null, null,null);
	}

	public List<Map<String, Object>> findBaseItemType1(Map<String, Object> param) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select code id,descr name from tBaseItemType1 a where a.expired='F'";
		if(StringUtils.isNotBlank((String)param.get("pCode"))){
			sql += " and a.code in ("+param.get("pCode")+") ";
		}
		sql += " order by a.dispSeq";
		return this.findBySql(sql, list.toArray());
	}
	public List<Menu> getCheckTreeMenu() {
		// TODO Auto-generated method stub
		String sql="select rtrim(a.Code) Code,a.Descr Descr,rtrim(b.Code) Code2,b.Descr Code2Descr " +
				"from tBaseItemType1 a  left outer join tBaseItemType2 b on b.BaseItemType1=a.Code and b.Expired = 'F' " +
				" where a.Expired = 'F'  group by a.Code,b.Code,a.Descr,b.Descr,a.DispSeq,b.DispSeq  order by a.DispSeq,b.DispSeq";
		List<Map<String,Object>> list= this.findBySql(sql, null);
		return menuDao.getMenuListByList(list,"/admin/baseCheckItem/goItemSelect","Code2","Code", "Code2Descr","Descr", null, null,null);
	}
	/** 
	 * 验证编号是否重复
	 */
	public boolean valideCode(String id){
		List<Object> params = new ArrayList<Object>();
		if (StringUtils.isNotBlank(id)) {
			String 	sql = "select * from tBaseItemType1 a where a.code = ? ";
			params.add(id);
			List<Map<String, Object>> list = findBySql(sql, params.toArray());
			if (list !=null && list.size()>0){
				return true;
			}
		}
		return false;
	}
	/**
	 * 获取基装类型1_PrjAPP
	 * @author	created by zb
	 * @date	2019-3-4--下午4:32:04
	 * @param page
	 * @return
	 */
	public Page<Map<String, Object>> getBaseItemType1(
			Page<Map<String, Object>> page) {
		String sql = "select rtrim(a.Code) Code,a.Descr Descr " +
					"from tBaseItemType1 a " +
					"where a.Expired='F' " +
					"order by a.DispSeq";
		return this.findPageBySql(page, sql);
	}
	
}

