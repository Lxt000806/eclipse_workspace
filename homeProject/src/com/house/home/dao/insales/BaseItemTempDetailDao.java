package com.house.home.dao.insales;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.CustType;
import com.house.home.entity.insales.BaseItemTempDetail;
import com.house.home.service.basic.CustTypeService;
@SuppressWarnings("serial")
@Repository
public class BaseItemTempDetailDao extends BaseDao {
	/**
	 * BaseItemTemp分页信息
	 * 
	 * @param page
	 * @param baseItemType2
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, BaseItemTempDetail baseItemTempDetail) {
		List<Object> list = new ArrayList<Object>();

		String sql =" select a.BaseItemCode,a.DispSeq,"
                + "     a.Qty,b.Cost,b.OfferPri unitprice,b.Material,round(a.Qty*b.OfferPri,0)+round(a.Qty*b.Material,0) lineAmount,"
                + " b.Remark,b.descr baseItemDescr,b.Category,rtrim(b.BaseItemType1) BaseItemType1,c.descr uom,b.iscalmangefee,b.allowpricerise,b.IsFixPrice "
                + " from tBaseItemTempDetail a "
                + " left outer join tBaseItem b on a.BaseItemCode=b.Code "
                + " left join tUom c on b.uom=c.code "
                + " where b.Expired='F' ";
		
        if (StringUtils.isNotBlank(baseItemTempDetail.getNo())) {
            sql += " and  a.No=?  ";
            list.add(baseItemTempDetail.getNo());
        }
        
        if (StringUtils.isNotBlank(baseItemTempDetail.getCustType())) {
            if (StringUtils.isNotBlank(baseItemTempDetail.getCanUseComBaseItem())) {
                if ("0".equals(baseItemTempDetail.getCanUseComBaseItem())) {
                    sql += " and ( b.custType = ? or b.CustTypeGroupNo in (select No from tCustTypeGroupDt where CustType = ?) ) ";
                } else {
                    sql += " and ( (b.custType = ? or b.CustTypeGroupNo in (select No from tCustTypeGroupDt where CustType = ?))"
                            + " or ((b.custType = '' or b.custType is null) and (b.CustTypeGroupNo is null or b.CustTypeGroupNo = '')) ) ";
                }
                list.add(baseItemTempDetail.getCustType());
                list.add(baseItemTempDetail.getCustType());
            }
        }
			
        return this.findPageBySql(page, sql, list.toArray());
	}
}
