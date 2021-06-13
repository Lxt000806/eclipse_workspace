package com.house.home.dao.insales;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.finance.GiftCheckOut;
import com.house.home.entity.insales.GiftApp;
import com.house.home.entity.insales.ItemBalAdjDetail;
import com.house.home.entity.insales.ItemTransferHeader;

@SuppressWarnings("serial")
@Repository
public class ItemTransferHeaderDao extends BaseDao{
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page,
			ItemTransferHeader itemTransferHeader) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from( select a.No,a.Date,a.UseGIT ,s.NOTE UseGITDescr,a.FromWHCode,b.Desc1 FromWHDescr,a.ToWHCode,c.Desc1 ToWHDescr, " +
				" a.Remarks,a.IsGITDeal, t.NOTE IsGITDealDescr,a.DealType,t2.NOTE DealTypeDescr,a.DealDate, " +
				" a.Status,t3.NOTE StatusDescr,a.AppEmp,e1.NameChi AppEmpDescr,a.AppDate, " +
				" a.ConfirmEmp,e2.NameChi ConfirmEmpDescr,a.ConfirmDate,a.DocumentNo, " +
				" a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog " +
				" from tItemTransferHeader a " +
				" left outer join tWareHouse b on a.FromWHCode  = b.Code " +
				" left outer join tWareHouse c on a.ToWHCode = c.Code " +
				" left outer join tXTDM s on a.UseGIT = s.CBM and s.ID = 'YESNO' " +
				" left outer join tXTDM t on a.IsGITDeal = t.CBM and t.ID = 'ISGITDEAL' " +
				" left outer join tXTDM t2 on a.DealType = t2.CBM and t2.ID = 'DEALTYPE' " +
				" left outer join tXTDM t3 on a.Status=t3.CBM and t3.ID='ITEMTRANSTATUS' " +
				" left outer join tEmployee e1 on e1.Number=a.AppEmp " +
				" left outer join tEmployee e2 on e2.Number=a.ConfirmEmp " +
				" left outer join tCZYBM d on a.LastUpdatedBy = d.CZYBH " +
				" where 1= 1 " ;
		if(StringUtils.isNotBlank(itemTransferHeader.getNo())){
			sql+=" and a.no= ? ";
			list.add(itemTransferHeader.getNo());
		}
		if(StringUtils.isNotBlank(itemTransferHeader.getStatus())){
			sql += " and a.Status in " + "('"+itemTransferHeader.getStatus().replace(",", "','" )+ "')";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.lastUpdate desc";
		}
		return findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> findDetailPageBySql(Page<Map<String,Object>> page,
			ItemTransferHeader itemTransferHeader) {
		List<Object> list = new ArrayList<Object>();
		String sql = " select  * from (select  c.descr itemtype2descr,b.descr itDescr,a.pk,a.Seq,a.ITHNo,a.ITCode,a.TrfQty,a.FromQty,a.ToQty,a.Remarks detailremarks,a.LastUpdate," +
				" a.LastUpdatedBy,a.Expired,a.ActionLog,a.Cost avgcost,a.fromQty fromAfterQty  ," +
				" a.toQty toAfterQty, " +
				" a.TrfQty*a.cost amountCost from tItemTransferDetail a " +
				" left join tItemTransferHeader Ith on Ith.no = a.ITHno " +
				" left join tItem b on b.code=a.itCode" +
				" left join tItemType2 c on c.code=b.itemType2 " +
				" left join tItemWHBal d1 on d1.itcode=a.itcode and d1.whcode= ith.fromWHCode " +//源仓库
				" left join tItemWHBal d2 on d2.itcode=a.itcode and d2.whcode= ith.toWHCode " +//目的仓库
				" where 1=1 ";
		if(StringUtils.isNotBlank(itemTransferHeader.getNo())){
			sql+=" and a.ITHNo = ? ";
			list.add(itemTransferHeader.getNo());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.lastUpdate desc";
		}
		
		return findPageBySql(page, sql, list.toArray());
	}
	
	@SuppressWarnings("deprecation")
	public Result saveItemTransfer(ItemTransferHeader itemTransferHeader) {
		Assert.notNull(itemTransferHeader);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pckzy_tItemWHBal_forXml(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, itemTransferHeader.getNo());
			call.setString(2, itemTransferHeader.getM_umState());
			call.setTimestamp(3, itemTransferHeader.getDate()== null ? null
					: new Timestamp(itemTransferHeader.getDate().getTime()));
			call.setString(4, itemTransferHeader.getUseGIT());
			call.setString(5, itemTransferHeader.getFromWHCode());
			call.setString(6, itemTransferHeader.getToWHCode());
			call.setString(7, itemTransferHeader.getRemarks());
			call.setString(8, itemTransferHeader.getStatus());
			call.setString(9, itemTransferHeader.getAppEmp());
			call.setString(10, itemTransferHeader.getConfirmEmp());
			call.setString(11, itemTransferHeader.getDocumentNo());
			call.setString(12, itemTransferHeader.getLastUpdatedBy());

			call.registerOutParameter(13, Types.INTEGER);
			call.registerOutParameter(14, Types.NVARCHAR);
			call.setString(15, itemTransferHeader.getItemTransferDetailXml());
			call.execute();
			result.setCode(String.valueOf(call.getInt(13)));
			result.setInfo(call.getString(14));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	
	
	public Map<String , Object> getFromQty(String itCode,String fromWHCode){
		String sql = " select ITCode,WHCode,QtyCal,AvgCost from tItemWHBal where ITCode = ? and WHCode = ?  ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{itCode,fromWHCode});
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	public String getToQty(String itCode,String toWHCode){
		String sql = " select ITCode,WHCode,QtyCal,AvgCost from tItemWHBal where ITCode = ? and WHCode = ? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{itCode,toWHCode});
		if(list != null && list.size() > 0){
			return list.get(0).get("QtyCal").toString();
		}
		return "0";
	}
	
	public String getPosiQty( String itCode,String fromWHCode) {
		String sql = "select isnull(sum(QtyCal),0) QtyCal from tWHPosiBal a " +
					"  inner join tWareHousePosi b on a.WHPPk=b.PK " +
					" where b.WHCode=? and a.ItCode=?  " ;
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{fromWHCode ,itCode});
		if (list!=null && list.size()>0){
			 return list.get(0).get("QtyCal").toString();
		}
		return null;
	}
	/**
	 * 根据供应商汇总amount
	 * @author	created by zb
	 * @date	2019-4-18--下午4:50:21
	 * @param page
	 * @param itemTransferHeader
	 * @return
	 */
	public Page<Map<String, Object>> findOrderBySupplPageBySql(
			Page<Map<String, Object>> page, ItemTransferHeader itemTransferHeader) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select b.SupplCode,c.Descr supplierDescr,SUM(a.ToQty*a.Cost) allCost " 
				+ " from tItemTransferDetail a "
				+ " left join titem b on a.ITCode=b.Code "
				+ " left join tSupplier c on c.Code=b.SupplCode"
				+ " where 1=1 ";
		if (StringUtils.isNotBlank(itemTransferHeader.getNo())) {
			sql += " and a.ITHNo =?";
			list.add(itemTransferHeader.getNo());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " group by b.SupplCode,c.Descr order by a."
					+ page.getPageOrderBy() + " " + page.getPageOrder();
		} else {
			sql += " group by b.SupplCode,c.Descr order by b.supplcode ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	
}
