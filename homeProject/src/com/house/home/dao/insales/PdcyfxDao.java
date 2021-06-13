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
import com.house.framework.web.login.UserContext;
import com.house.home.entity.insales.ItemBatchHeader;
import com.house.home.entity.insales.ItemPlanLog;

@SuppressWarnings("serial")
@Repository
public class PdcyfxDao extends BaseDao {
	@Autowired
	private HttpServletRequest request;

	/**
	 * 预算修改日志编号
	 * 
	 * @param page
	 * @param itemPlanLog
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemBatchHeader itemBatchHeader) {
		List<Object> list = new ArrayList<Object>(); 
		String itemSql1 = "";
		String itemSql2 = "";
		
		if (StringUtils.isNotBlank(itemBatchHeader.getItemType1())) {
			itemSql1 = itemSql1 + " and t3.ItemType1='"+itemBatchHeader.getItemType1()+"'";
	    	itemSql2 = itemSql2 + " and t5.ItemType1='"+itemBatchHeader.getItemType1()+"'";
		}else{
			UserContext uc=(UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
			itemSql1 = itemSql1 + " and t3.ItemType1 in "+ "('"+uc.getItemRight().replaceAll(",", "','")+"')"; 
		    itemSql2 = itemSql2 + " and t5.ItemType1 in "+ "('"+uc.getItemRight().replaceAll(",", "','")+"')"; 
		
		}
		if (StringUtils.isNotBlank(itemBatchHeader.getItemType2())){
			itemSql1= itemSql1 + " and t3.ItemType2='"+itemBatchHeader.getItemType2()+"'";
			itemSql2 = itemSql2 + " and t5.ItemType2='"+itemBatchHeader.getItemType2()+"'";
			
		}
		if (StringUtils.isNotBlank(itemBatchHeader.getItemType3())){
			itemSql1= itemSql1 + " and t3.ItemType3='"+itemBatchHeader.getItemType3()+"'";
			itemSql2 = itemSql2 + " and t5.ItemType3='"+itemBatchHeader.getItemType3()+"'";	
		} 
		String sql = "select * from (select isNull(a.ITCode,b.ITCode) ITCode,isNull(a.Descr,b.Descr) ITName,"
				  + " isNull(b.QtyCal,0) QtyCal,isNull(a.Qty,0) Qty,(isNull(a.Qty,0)-isNull(b.QtyCal,0)) QtyNum, " 
				  +"  isNull(a.ItemType1,b.ItemType1) ItemType1 ,isNull(a.ItemType2,b.ItemType2) ItemType2,isNull(a.ItemType3,b.ItemType3) ItemType3  from "
				  + "( "
				  + " select t2.ITCode,t3.Descr,t2.Qty,t3.ItemType1,t3.ItemType2,t3.ItemType3 from tItemBatchHeader t1 "
                  + " left outer join tItemBatchDetail t2 on t1.No = t2.IBDNo  "
                  + " left outer join tItem t3 on t2.ITCode = t3.Code "
                  + " where t1.BatchType='2' and t1.No in (select * from dbo.fStrToTable(?,',')) ";
		list.add(itemBatchHeader.getNo());          
		sql = sql + itemSql1
                  + " ) a full outer join "
                  + " ( "
                  + " select t4.ITCode,t5.Descr,t4.QtyCal,t5.ItemType1,t5.ItemType2,t5.ItemType3 from tItemWHBal t4 "
                  + " left outer join tItem t5 on t4.ITCode = t5.Code "
                  + " where t4.WHCode=? " ;
		list.add(itemBatchHeader.getWhCode());
		sql = sql + itemSql2
                  + " ) b on  a.ITCode = b.ITCode ";  
				if (StringUtils.isBlank(itemBatchHeader.getContainDiff())){
					sql = sql +=" where isNull(b.QtyCal,0)<>isNull(a.Qty,0) " ;
					
				}  
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.ItemType1,a.ItemType2,a.ItemType3,a.ITCode ";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}


}

