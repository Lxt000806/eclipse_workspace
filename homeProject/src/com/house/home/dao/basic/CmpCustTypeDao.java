package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.BaseBatchTemp;
import com.house.home.entity.basic.CmpCustType;

@SuppressWarnings("serial")
@Repository
public class CmpCustTypeDao extends BaseDao {

	/**
	 * CmpCustType分页信息
	 * 
	 * @param page
	 * @param cmpCustType
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page, CmpCustType cmpCustType) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from(select a.pk,c.Desc1 Company,a.CmpnyName,b.Desc1 CustTypeDescr,a.LogoFile,a.CmpnyFullName,"
				+ "a.CmpnyAddress,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,d.Descr PayeeDescr,e.Descr DesignPayeeDescr "
				+ "from tCmpCustType a "
				+ "left join tCusttype b on a.CustType=b.Code "
				+ "left join tCompany c on a.CmpCode=c.Code "
				+ "left join tTaxPayee d on a.PayeeCode=d.Code "
				+ "left join tTaxPayee e on a.DesignPayeeCode=e.Code "
				+ "where 1=1 ";

		if (StringUtils.isNotBlank(cmpCustType.getCmpCode())) {
			sql += " and a.CmpCode=? ";
			list.add(cmpCustType.getCmpCode());
		}
		if (StringUtils.isNotBlank(cmpCustType.getCustType())) {
			sql += " and a.CustType in ('"+cmpCustType.getCustType().trim().replace(",", "','")+"')";
		}
		if (StringUtils.isBlank(cmpCustType.getExpired())
				|| "F".equals(cmpCustType.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ")a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ")a order by a.LastUpdate desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 客户类型重复判断
	 * @param cmpCustType
	 * @return
	 */
	public List<Map<String, Object>> checkExistType(CmpCustType cmpCustType) {
		List<Object> paraList = new ArrayList<Object>();
		String sql = "select 1 from tCmpCustType where cmpCode=? and custType=?";
		paraList.add(cmpCustType.getCmpCode());
		paraList.add(cmpCustType.getCustType());
		if("M".equals(cmpCustType.getM_umState())){
			sql+=" and pk<>?";
			paraList.add(cmpCustType.getPk());
		}
		List<Map<String, Object>> list = this.findListBySql(sql,paraList.toArray());
		return list;
	}
	
	/**
	 * 根据客户编号获取公司logo
	 * @param custCode
	 * @return
	 */
	public String getLogoFile(String custCode){
		String sql=" select case when isnull(cct.LogoFile,'')='' then 'logo.jpg' else cct.LogoFile end LogoFile from tCustomer a "
				  +" left join tBuilder b on b.code = a.BuilderCode "
				  +" left join tRegion c on c.code = b.regionCode "
				  +" left join tCmpCustType cct on cct.CustType=a.CustType and cct.CmpCode=c.CmpCode " 
				  +" where a.code=? ";
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{custCode});
		if (list!=null && list.size()>0){
			return list.get(0).get("LogoFile").toString();
		}		
		return "logo.jpg";
	}
	
	
}

