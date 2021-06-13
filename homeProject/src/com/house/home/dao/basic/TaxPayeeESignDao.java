package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.Organization;
import com.house.home.entity.basic.TaxPayeeESign;

@SuppressWarnings("serial")
@Repository
public class TaxPayeeESignDao extends BaseDao {

	/**
	 * TaxPayeeESign分页信息
	 * 
	 * @param page
	 * @param taxPayeeESign
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, TaxPayeeESign taxPayeeESign) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from ( "
				+"select a.PK,a.OrgId,a.SealId,a.SealId SealIdBtn,a.PayeeCode,b.Descr PayeeDescr, "
				+"a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,c.Name OrgName "
				+"from tTaxPayeeESign a "
				+"left join tTaxPayee b on a.PayeeCode = b.Code "
				+"left join tOrganization c on a.OrgId = c.OrgId "
				+"where 1=1 ";

    	if (taxPayeeESign.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(taxPayeeESign.getPk());
		}
    	if (StringUtils.isNotBlank(taxPayeeESign.getPayeeCode())) {
			sql += " and a.PayeeCode=? ";
			list.add(taxPayeeESign.getPayeeCode());
		}
		if (StringUtils.isBlank(taxPayeeESign.getExpired()) || "F".equals(taxPayeeESign.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.Pk";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 印章是否启用
	 * 
	 * @param page
	 * @param sealId
	 * @return
	 */
	public List<Map<String,Object>> isEnableSeal(String sealId) {

		String sql = "select 1 from tTaxPayeeESign where SealId = ? ";

		return this.findBySql(sql, sealId);
	}
	
	/**
	 * 机构是否启用
	 * 
	 * @param page
	 * @param orgId
	 * @return
	 */
	public List<Map<String,Object>> isEnableOrg(String orgId) {

		String sql = "select 1 from tTaxPayeeESign where OrgId = ? ";

		return this.findBySql(sql, orgId);
	}
}

