package com.house.home.dao.design;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.design.IntProd;

@SuppressWarnings("serial")
@Repository
public class IntProdDao extends BaseDao {

	/**
	 * IntProd分页信息
	 * 
	 * @param page
	 * @param intProd
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, IntProd intProd) {
		List<Object> list = new ArrayList<Object>();
		String sql = "SELECT a.*,b.NOTE IsCupboardDescr,c.CustCode,d.Descr CustName,d.Address ,c.Descr FixAreaDescr FROM  dbo.tIntProd a "
				+" left outer join tXTDM b on b.CBM=a.IsCupboard and b.ID='YESNO' "
                +" left outer join tFixArea c on c.PK=a.FixAreaPK " 
                 +" left outer join tCustomer d on d.Code=c.CustCode where  a.Expired='F' "; 
		
		if (StringUtils.isNotBlank(intProd.getCustCode())) {// 制作橱柜解单时增加 --add by zb
			sql += " and c.CustCode = ? ";
			list.add(intProd.getCustCode());
		}
    	if (intProd.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(intProd.getPk());
		}
    	if (StringUtils.isNotBlank(intProd.getDescr())) {
			sql += " and a.Descr=? ";
			list.add(intProd.getDescr());
		}
    	if (intProd.getFixAreaPk() != null) {
			sql += " and a.FixAreaPK=? ";
			list.add(intProd.getFixAreaPk());
		}
    	if (StringUtils.isNotBlank(intProd.getIsCupboard())) {
			sql += " and a.IsCupboard=? ";
			list.add(intProd.getIsCupboard());
		}
    	if (intProd.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(intProd.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(intProd.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(intProd.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(intProd.getExpired()) || "F".equals(intProd.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(intProd.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(intProd.getActionLog());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Pk";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	public boolean isExisted(IntProd intProd) {
		List<Object> list = new ArrayList<Object>();
		String sql="select 1 from tIntProd a  where 1=1 ";
		if(StringUtils.isNotBlank(intProd.getDescr())){
			sql+=" and a.Descr=? ";
			list.add(intProd.getDescr().trim());
		}
		if(intProd.getFixAreaPk()!=null){
			sql+=" and a.FixAreaPK=?";
			list.add(intProd.getFixAreaPk());
		}
		if(intProd.getPk()!=null){
			sql+=" and a.PK <> ? ";
			list.add(intProd.getPk());
		}
		List<Map<String,Object>> list1= this.findBySql(sql, list.toArray());
		if(list1!=null&&list1.size()>0){
			return true;
		}
		return false;
	}

}

