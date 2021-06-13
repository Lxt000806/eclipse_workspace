package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.house.framework.commons.conf.CommonConstant;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.ItemTransform;
import com.house.home.entity.basic.ItemTransformDetail;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@SuppressWarnings("serial")
@Repository
public class ItemTransformDao extends BaseDao {
	
	@Autowired
	private HttpServletRequest request;

    public Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page,
            ItemTransform itemTransfrom) {
        
        List<Object> params = new ArrayList<Object>();

        String sql = "select * from ("
                + "select a.No, a.ItemCode, b.Descr ItemDescr, "
                + "    a.Remarks, a.LastUpdate, a.LastUpdatedBy, "
                + "    a.Expired, a.ActionLog, c.Descr uomDescr "
                + "from tItemTransform a "
                + "left join tItem b on b.Code = a.ItemCode "
                +" left join tuom c on b.uom=c.Code " 
                + "where 1 = 1 ";
         
        if (StringUtils.isNotBlank(itemTransfrom.getNo())) {
            sql += "and a.No = ? ";
            params.add(itemTransfrom.getNo());
        }
        
        if (StringUtils.isNotBlank(itemTransfrom.getItemCode())) {
            sql += "and a.ItemCode = ? ";
            params.add(itemTransfrom.getItemCode());
        }
        
        if (StringUtils.isNotBlank(itemTransfrom.getRemarks())) {
            sql += "and a.Remarks like ? ";
            params.add("%" + itemTransfrom.getRemarks() + "%");
        }
        
        if (StringUtils.isNotBlank(itemTransfrom.getItemType1())) {
			sql += " and b.ItemType1=? ";
			params.add(itemTransfrom.getItemType1());
		}else {
			UserContext uc = (UserContext) request.getSession().getAttribute(
					CommonConstant.USER_CONTEXT_KEY);
		    String itemRight = "";
			for (String str : uc.getItemRight().trim().split(",")) {
				itemRight += "'" + str + "',";
			}
			sql += " and b.ItemType1 in("
					+ itemRight.substring(0, itemRight.length() - 1) + ") ";
		}
        if (StringUtils.isBlank(itemTransfrom.getExpired())
                || "F".equals(itemTransfrom.getExpired())) {
            sql += "and a.Expired = 'F' ";
        }
    
        if (StringUtils.isNotBlank(page.getPageOrderBy())) {
            sql += ") a order by a." + page.getPageOrderBy() + " " + page.getPageOrder();
        } else {
            sql += ") a order by a.LastUpdate desc";
        }
        
        return findPageBySql(page, sql, params.toArray());
    }

    public Page<Map<String, Object>> findDetailPageBySql(Page<Map<String, Object>> page,
            ItemTransform itemTransform) {
        List<Object> params = new ArrayList<Object>();

        String sql = "select * from ("
                + "select a.PK, a.ItemCode, b.Descr ItemDescr, "
                + "    a.TransformPer, a.LastUpdate, a.LastUpdatedBy, "
                + "    a.Expired, a.ActionLog "
                + "from tItemTransformDetail a "
                + "left join tItem b on b.Code = a.ItemCode "
                + "where a.No = ? ";
        
        params.add(itemTransform.getNo());
        
        if (StringUtils.isNotBlank(page.getPageOrderBy())) {
            sql += ") a order by a." + page.getPageOrderBy() + " " + page.getPageOrder();
        } else {
            sql += ") a order by a.PK ";
        }
        
        return findPageBySql(page, sql, params.toArray());
    }
    

    public List<ItemTransformDetail> findTransformDetailsByNo(String itemTransformNo) {
        
        String hql = "from ItemTransformDetail detail where detail.no = ? ";
        
        @SuppressWarnings("unchecked")
        List<ItemTransformDetail> details = (List<ItemTransformDetail>) find(hql, itemTransformNo);
        
        return  details;
    }

}
