package com.house.home.dao.insales;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.conf.CommonConstant;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.SqlUtil;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.insales.Brand;

@SuppressWarnings("serial")
@Repository
public class BrandDao extends BaseDao {
	@Autowired  
	private  HttpServletRequest request;  

	/**
	 * Brand分页信息
	 * 
	 * @param page
	 * @param brand
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Brand brand) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select a.Code,a.Descr,a.ItemType2,b.Descr ItemType2Descr from tBrand a "
		          +"inner join tItemType2 b on a.ItemType2=b.Code "
		          + " inner join tItemType1 c on c.Code=b.ItemType1 "
		          + " where 1=1 " ;

    	if (StringUtils.isNotBlank(brand.getCode())) {
			sql += " and a.Code=? ";
			list.add(brand.getCode());
		}
    	if (StringUtils.isNotBlank(brand.getDescr())) {
			sql += " and a.Descr like ? ";
			list.add("%"+brand.getDescr()+"%");
		}
    	if (StringUtils.isNotBlank(brand.getItemType1())) {
			sql += " and c.code =? ";
			list.add(brand.getItemType1().trim());
		}else{
			UserContext uc=(UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
			String itemRight="";
			for(String str:uc.getItemRight().trim().split(",")){
				itemRight+="'"+str+"',";
			}
			sql += " and c.code in("+itemRight.substring(0, itemRight.length()-1)+") ";
		}
    	if (StringUtils.isNotBlank(brand.getItemType2())) {
			sql += " and a.ItemType2=? ";
			list.add(brand.getItemType2().trim());
		}
    	if (brand.getDateFrom() != null){
			sql += " and a.LastUpdate>= ? ";
			list.add(brand.getDateFrom());
		}
		if (brand.getDateTo() != null){
			sql += " and a.LastUpdate<= ? ";
			list.add(brand.getDateTo());
		}
    	if (StringUtils.isNotBlank(brand.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(brand.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(brand.getExpired()) || "F".equals(brand.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(brand.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(brand.getActionLog());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.LastUpdate desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public List<Map<String,Object>> findBrand(Map<String,Object> param){
		List<Object> list = new ArrayList<Object>();
		String sql = "select a.code id,a.descr name from tBrand a " +
				"left join  titemtype2 b on a.ItemType2=b.Code where a.expired='F' ";
		
		if(StringUtils.isNotBlank((String)param.get("itemType1"))){
			sql += " and b.itemtype1=?";
			list.add((String)param.get("itemType1"));
		}
		if(StringUtils.isNotBlank((String)param.get("itemType2"))){
			sql += " and a.ItemType2=?";
			list.add((String)param.get("itemType2"));
		}
		sql += " order by a.code";
		return this.findBySql(sql, list.toArray());
	}

	/**
	 * 品牌管理分页查询
	 * @author	created by zb
	 * @date	2018-12-24--上午10:42:49
	 * @param page
	 * @param brand
	 * @return
	 */
	public Page<Map<String, Object>> findBrandPageBySql(
			Page<Map<String, Object>> page, Brand brand) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from(select " +
					"a.Code,a.Descr,c.Code itemType1,c.Descr ItemType1Descr, " +
					"a.ItemType2,b.Descr ItemType2Descr,a.LastUpdate, " +
					"a.LastUpdatedBy,a.Expired,a.ActionLog  " +
					"from tBrand a,tItemType2 b,tItemType1 c " +
					"where a.ItemType2=b.Code and b.ItemType1=c.Code ";
		if (StringUtils.isBlank(brand.getExpired()) || "F".equals(brand.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(brand.getCode())) {
			sql += " and a.Code=? ";
			list.add(brand.getCode());
		}
		if (StringUtils.isNotBlank(brand.getDescr())) {
			sql += " and a.Descr like ? ";
			list.add("%"+brand.getDescr()+"%");
		}
		if (StringUtils.isNotBlank(brand.getItemType1())) {
			sql += " and c.code = ? ";
			list.add(brand.getItemType1());
		} else {
			String str = SqlUtil.resetStatus(brand.getItemRight());
			sql += " and c.code in ("+str+") ";
		}
		if (StringUtils.isNotBlank(brand.getItemType2())) {
			sql += " and a.ItemType2=? ";
			list.add(brand.getItemType2());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " ) a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		} else {
			sql += " ) a order by a.LastUpdate desc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * 校验一下商品类型2是否已经存在
	 * @author	created by zb
	 * @date	2018-12-24--下午3:27:17
	 * @param brand
	 * @return false:存在
	 */
	public Boolean checkDescr(Brand brand) {
		String sql = " select 1 from tBrand where ItemType2=? and Descr=? and Code <> ? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{brand.getItemType2(), brand.getDescr(), brand.getCode()});
		if (list!=null && list.size()>0){
			return false;
		}
		return true;
	}

}

