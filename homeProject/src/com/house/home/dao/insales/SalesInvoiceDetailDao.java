package com.house.home.dao.insales;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.insales.SalesInvoiceDetail;

@SuppressWarnings("serial")
@Repository
public class SalesInvoiceDetailDao extends BaseDao {

	/**
	 * SalesInvoiceDetail分页信息
	 * 
	 * @param page
	 * @param salesInvoiceDetail
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, SalesInvoiceDetail salesInvoiceDetail) {
		List<Object> list = new ArrayList<Object>();

		String sql =" select a.no,a.CustName,a.Date,case when a.Type='S' then b.Qty else b.Qty*-1 end Qty " +
				" from tSalesInvoiceDetail b inner join tSalesInvoice a on a.no=b.sino where a.status='OPEN' and a.Expired='F'  and  a.date>=dateadd(day,-365, getdate()) "; 
		if (StringUtils.isNotBlank(salesInvoiceDetail.getItcode())) {
			sql += " and b.itcode=? ";
			list.add(salesInvoiceDetail.getItcode());
		}
    

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * SalesInvoiceDetail分页信息
	 * 
	 * @param page
	 * @param salesInvoiceDetail
	 * @return
	 */
	public Page<Map<String,Object>> findImportSalePageBySql(Page<Map<String,Object>> page, SalesInvoiceDetail salesInvoiceDetail) {
		List<Object> list = new ArrayList<Object>();
		
		String sql ="select * from (select a.Pk,a.SINo,a.ITCode,i.cost,a.Qty,a.Markup,a.Remarks ,a.UnitPrice,u.Descr uomdescr,i.ItemType1,i.Descr itemdescr,i.AllQty, i.SqlCode, " +
				"i.Color,b.Descr sqlcodedescr,a.BefLineAmount,dbo.fGetPurQty(i.Code,'') PurQty,dbo.fGetUseQty(i.Code,'','') UseQty,  " +
				"si.CustCode,s.desc1 custdescr,s.mobile1,s.address,i.itemType2,i.projectCost " +
				"from tSalesInvoiceDetail a " 
				+" left outer join tItem i on i.Code=a.ITCode "
				+" left join tSalesInvoice si on si.No=a.SINo "
				+" left outer join tUom u on u.Code=i.Uom "
				+" left join tSaleCust s on s.Code=si.CustCode"
				+" left outer join tBrand b on i.SqlCode=b.Code ";
				//+" left outer join tPurchaseDetail pd on pd.ITCode= a.ITCode"
				//+" where 1=1";
		
		if (StringUtils.isNotBlank(salesInvoiceDetail.getPuno())) {
			sql += "LEFT JOIN (select PUNo,ITCode  from tPurchaseDetail where 1=1 ) pd on pd.ITCode=a.ITCode where 1=1 " +
					" and pd.PUNo=? " ;
			list.add(salesInvoiceDetail.getPuno());
		}else{
			sql+="where 1=1";
		}
		
		if (StringUtils.isNotBlank(salesInvoiceDetail.getSino())) {
			sql += " and a.SINo=? ";
			list.add(salesInvoiceDetail.getSino());
		}
		if (StringUtils.isNotBlank(salesInvoiceDetail.getItemType1())) {
			sql += " and i.ItemType1=? ";
			list.add(salesInvoiceDetail.getItemType1());
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.itemdescr";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

}

