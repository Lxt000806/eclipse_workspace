package com.house.home.dao.finance;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.home.entity.finance.SupplierPrepayDetail;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
@SuppressWarnings("serial")
@Repository
public class SupplierPrepayDetailDao extends BaseDao{

	/**
	 * SupplierPrepayDetail分页信息
	 * @param page
	 * @param baseItemPlanBak
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, SupplierPrepayDetail supplierPrepayDetail) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select * from(select  a.No, a.ItemType1, a.Type, x1.NOTE TypeDescr, it1.Descr ItemType1Descr, a.AppDate, b.PK, d.Code Supplier,  "
				+ " case when a.Type = '1' then d.Descr " 
				+ "  else f.Descr "
				+ " end SplDescr, b.PUNo, b.Status, x2.NOTE StatusDescr, b.Amount, b.AftAmount, a.PayDate, b.Remarks, b.LastUpdate, "
				+ " b.LastUpdatedBy, b.Expired, b.ActionLog,a.DocumentNo "
				+ " from   tSupplierPrepay a "
				+ "     inner join tSupplierPrepayDetail b on a.No = b.No "
				+ " left join tXTDM x1 on x1.ID = 'SPLPREPAYTYPE' "
				+ "                   and a.Type = x1.CBM "
				+ " left join tItemType1 it1 on a.ItemType1 = it1.Code "
				+ " left join tSupplier d on b.Supplier = d.Code"
				+ " left join tXTDM x2 on x2.ID = 'SPLPDSTATUS' "
				+ " and b.Status = x2.CBM"
				+ " left join tPurchase e on b.PUNo = e.No"
				+ " left join tSupplier f on e.Supplier = f.Code"
				+ " where a.Status='1' and not exists(select 1 from tReturnPayDetail where RefSupplPrepayPK=b.pk) ";
			if (StringUtils.isNotBlank(supplierPrepayDetail.getItemtype1())) {
				sql += " and a.ItemType1 = ? ";
				list.add(supplierPrepayDetail.getItemtype1());
			}
			if (StringUtils.isNotBlank(supplierPrepayDetail.getNo())) {
				sql += " and a.No like ? ";
				list.add("%"+supplierPrepayDetail.getNo()+"%");
			}
			if (StringUtils.isNotBlank(supplierPrepayDetail.getPuNo())) {
				sql += " and  b.puNo like ? ";
				list.add("%"+supplierPrepayDetail.getPuNo()+"%");
			}
			if (StringUtils.isNotBlank(supplierPrepayDetail.getType())) {
				sql += " and a.Type = ? ";
				list.add(supplierPrepayDetail.getType());
			}
			if (StringUtils.isNotBlank(supplierPrepayDetail.getSplcode())) {
				sql += " and d.Code = ? ";
				list.add(supplierPrepayDetail.getSplcode());
			}
			if (StringUtils.isNotBlank(supplierPrepayDetail.getStatus())) {
				sql += " and a.Status in " + "('"+supplierPrepayDetail.getStatus().replace(",", "','" )+ "')";
			}	
			if (supplierPrepayDetail.getPayDateFrom() != null){
				sql += " and a.PayDate>= ? ";
				list.add(supplierPrepayDetail.getPayDateFrom());
			}
	    	if (supplierPrepayDetail.getPayDateTo() != null){
				sql += " and a.PayDate< ? ";
				list.add(DateUtil.addDateOneDay(supplierPrepayDetail.getPayDateTo()));
			}
	    	if (StringUtils.isNotBlank(supplierPrepayDetail.getSpldescr())) {
				sql += " and d.Descr like ? ";
				list.add("%"+supplierPrepayDetail.getSpldescr()+"%");
			}
	    	
	    	if (StringUtils.isNotBlank(supplierPrepayDetail.getUnSelected())) {
				sql += " and b.pk not in(select * from fStrToTable('"+supplierPrepayDetail.getUnSelected()+"',',')) ";
			}
            
			if (StringUtils.isNotBlank(page.getPageOrderBy())){
				sql += " )a  order by "+page.getPageOrderBy()+" "+page.getPageOrder();
			}else{
				sql += " )a order by a.LastUpdate desc ";
			}
			return this.findPageBySql(page, sql, list.toArray());

	}

}
