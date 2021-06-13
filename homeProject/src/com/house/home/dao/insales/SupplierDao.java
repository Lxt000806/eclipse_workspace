package com.house.home.dao.insales;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
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
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.commons.utils.SqlUtil;
import com.house.home.entity.insales.Supplier;

@SuppressWarnings("serial")
@Repository
public class SupplierDao extends BaseDao {

	/**
	 * Supplier分页信息
	 * 
	 * @param page
	 * @param supplier
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Supplier supplier,String itemRight) {
		List<Object> list = new ArrayList<Object>();
		
		String sql = "select * from tSupplier a where 1=1 ";
		
		if(StringUtils.isNotBlank(supplier.getItemRight())){
			sql += " and a.ItemType1 in ('"+supplier.getItemRight().trim().replace(",", "','")+"')";
		}
		if(StringUtils.isNotBlank(itemRight)){
			sql += " and a.ItemType1 in ('"+itemRight.trim().replace(",", "','")+"')";
		}
		if (StringUtils.isNotBlank(supplier.getCode())) {
			sql += " and a.Code=? ";
			list.add(supplier.getCode());
		}
		if (StringUtils.isNotBlank(supplier.getDescr())) {
			sql += " and a.Descr like ? ";
			list.add("%"+supplier.getDescr()+"%");
		}
		if (StringUtils.isNotBlank(supplier.getAddress())) {
			sql += " and a.Address=? ";
			list.add(supplier.getAddress());
		}
		if (StringUtils.isNotBlank(supplier.getContact())) {
			sql += " and a.Contact=? ";
			list.add(supplier.getContact());
		}
		if (StringUtils.isNotBlank(supplier.getPhone1())) {
			sql += " and a.Phone1=? ";
			list.add(supplier.getPhone1());
		}
		if (StringUtils.isNotBlank(supplier.getPhone2())) {
			sql += " and a.Phone2=? ";
			list.add(supplier.getPhone2());
		}
		if (StringUtils.isNotBlank(supplier.getFax1())) {
			sql += " and a.Fax1=? ";
			list.add(supplier.getFax1());
		}
		if (StringUtils.isNotBlank(supplier.getFax2())) {
			sql += " and a.Fax2=? ";
			list.add(supplier.getFax2());
		}
		if (StringUtils.isNotBlank(supplier.getMobile1())) {
			sql += " and a.Mobile1=? ";
			list.add(supplier.getMobile1());
		}
		if (StringUtils.isNotBlank(supplier.getMobile2())) {
			sql += " and a.Mobile2=? ";
			list.add(supplier.getMobile2());
		}
		if (StringUtils.isNotBlank(supplier.getEmail1())) {
			sql += " and a.Email1=? ";
			list.add(supplier.getEmail1());
		}
		if (StringUtils.isNotBlank(supplier.getEmail2())) {
			sql += " and a.Email2=? ";
			list.add(supplier.getEmail2());
		}
		if (StringUtils.isNotBlank(supplier.getIsSpecDay())) {
			sql += " and a.IsSpecDay=? ";
			list.add(supplier.getIsSpecDay());
		}
		if (supplier.getSpecDay() != null) {
			sql += " and a.SpecDay=? ";
			list.add(supplier.getSpecDay());
		}
		if (supplier.getBillCycle() != null) {
			sql += " and a.BillCycle=? ";
			list.add(supplier.getBillCycle());
		}
		if (supplier.getDateFrom() != null){
			sql += " and a.LastUpdate>= ? ";
			list.add(supplier.getDateFrom());
		}
		if (supplier.getDateTo() != null){
			sql += " and a.LastUpdate<= ? ";
			list.add(supplier.getDateTo());
		}
		if (StringUtils.isNotBlank(supplier.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(supplier.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(supplier.getExpired()) || "F".equals(supplier.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(supplier.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(supplier.getActionLog());
		}
		if (StringUtils.isNotBlank(supplier.getItemType1())) {
			sql += " and a.ItemType1=? ";
			list.add(supplier.getItemType1());
		}
		if(StringUtils.isNotBlank(supplier.getExistSuppl())){
			sql+=" and not exists ( select 1 from tCmpActivitySuppl cas where cas.supplcode =a.code and cas.no= ? ) ";
			list.add(supplier.getActNo());
		}
		if(StringUtils.isNotBlank(supplier.getIsActSuppl())){
			sql+=" and a.code in (" +
					" select a.SupplCode from dbo.tCmpActivitySuppl a" +
					" left join dbo.tCmpActivity b on a.no=b.No" +
					" left join dbo.tActivity c on c.CmpActNo=b.No " +
					" where c.no=? " +
					")";
			list.add(supplier.getActNo()==null?"":supplier.getActNo());
		}
		
		if(StringUtils.isNotBlank(supplier.getPreAppNo())){
			sql += " and a.Code in ( "
				  +" select c.code "  
				  +" from   tItemPreAppDetail a  "
				  +" left outer join tItem b on b.Code = a.ItemCode  "
				  +" left outer join tSupplier c on c.Code = b.SupplCode "  
				  +" where  a.No=? ) ";
			list.add(supplier.getPreAppNo());
		}
		if(StringUtils.isNotBlank(supplier.getSupplJob())){
			if("0".equals(supplier.getShowAll())){
				sql+=" and exists(select 1 from tItemApp where ItemType1=? and CustCode= ? and Status<>'CANCEL' and a.Code=SupplCode )";
				list.add(supplier.getItemType1());
				list.add(supplier.getCustCode());
			}
			sql+=" and not exists (select 1 from tSupplJob where prjJobNo= ? and SupplCode = a.code )";
			list.add(supplier.getSupplJob());
		}
		if("0".equals(supplier.getShowAll())){
			if("08".equals(supplier.getJobType().trim())){
				sql+=" and exists (select 1 from tItemApp in_b where in_b.CustCode= ? and in_b.SupplCode = a.code and in_b.Status <>'CANCEL' and in_b.ItemType1='JC' and in_b.IsCupboard='1') ";
				list.add(supplier.getCustCode());
			}else if("09".equals(supplier.getJobType().trim())){
				sql+=" and exists (select 1 from tItemApp in_b where in_b.CustCode= ? and in_b.SupplCode = a.code and in_b.Status <>'CANCEL' and in_b.ItemType1='JC' and in_b.IsCupboard='0') ";
				list.add(supplier.getCustCode());
			}
			if("prjJobManage".equals(supplier.getFrom())){
				if(StringUtils.isNotBlank(supplier.getJobType())){
				sql +=  " and ( "+
						" ( "+
						" 	exists( "+
						" 		select * "+
						" 		from tPrjJob in_a "+
						" 		inner join tJobTypeConfItemType in_b on in_a.JobType = in_b.JobType "+
						" 		where in_a.No=? "+
						" 		) and exists( "+
						" 		select *  "+
						" 		from tItemApp in_a  "+
						" 		left join (  "+
						" 			select min(in_a.PK) firstPK,in_a.No  "+
						" 			from tItemAppDetail in_a  "+
						" 			group by in_a.No  "+
						" 		) in_b on in_a.No = in_b.No "+
						" 		left join tItemAppDetail in_c on in_c.pk = in_b.firstPK "+
						" 		left join tItem in_d on in_d.Code = in_c.ItemCode  "+
						" 		where in_a.SupplCode = a.code and in_a.SendType='1' "+
						" 		and in_a.CustCode = ? and in_a.Status = 'CONFIRMED' "+
						" 		and exists ( "+
						" 			select 1   	from tConfItemTypeDt in2_a  "+
						" 			left join tJobTypeConfItemType in2_b on in2_a.ConfItemType = in2_b.ConfItemType "+
						" 			left join tJobType in2_c on in2_c.Code = in2_b.JobType "+
						" 			where in2_b.JobType = ?";
				list.add(supplier.getSupplJob());
				list.add(supplier.getCustCode());
				list.add(supplier.getJobType());
				if("JC".equals(supplier.getItemType1().trim())){
					sql += " and in2_c.IsCupboard = in_a.IsCupboard ";
				}
				sql +=	" and ( "+
						" 				(isnull(in2_a.ItemType3, '') = '' and in2_a.ItemType2 = in_d.ItemType2) "+
						" 				or (in2_a.ItemType3 = in_d.ItemType3 and in2_a.ItemType2 = in_d.ItemType2) "+
						" 			)  	"+
						" 		)   "+
						" 	)  "+
						" )  or not exists(   "+
						" 	select * "+
						" 	from tPrjJob in_a  "+
						" 	inner join tJobTypeConfItemType in_b on in_a.JobType = in_b.JobType "+
						" 	where in_a.No=? "+
						" 	) "+
						" )  ";
				list.add(supplier.getSupplJob());
				}
			}
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Code";
		}
		System.out.println("\n\n"+sql+"\n\n");
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public String getItemType1(String czybh){
		String sql = " select b.ItemType1 from tCzybm a " +
				" left join tSupplier b on b.code=a.EMNum " +
				" where a.Czybh = ? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{czybh});
		if(list != null && list.size() > 0){
			return list.get(0).get("ItemType1").toString();
		}
		return null;
	}

	/**
	 * 供应商分页查询
	 * @author	created by zb
	 * @date	2018-12-25--下午4:41:40
	 * @param page
	 * @param supplier
	 * @return
	 */
	public Page<Map<String, Object>> findSupplierPageBySql(
			Page<Map<String, Object>> page, Supplier supplier) {
		List<Object> list = new ArrayList<Object>();
		String sql ="select * from (select " +
					"a.preOrderDay,a.Code,a.Descr,a.Address,a.Contact,a.Phone1,a.Phone2,a.Fax1,a.Fax2, " +
					"a.Mobile1,a.Mobile2,a.Email1,a.Email2,a.ItemType1,b.Descr ItemType1Descr, " +
					"a.IsSpecDay,c.NOTE IsSpecDayDescr,a.SpecDay,a.BillCycle,a.PrepayBalance, " +
					"a.LastUpdate,a.LastUpdatedBy,a.Expired, " +
					"a.ActionLog , a.ActName,a.CardId,a.Bank,a.IsWeb,x1.NOTE IsWebDescr,  " +
					"a.Department2,dpt2.Desc1 Department2Descr,x2.Note PurchCostModelDescr, " +
					"x3.Note InOrderTypeDescr,x4.Note OutOrderTypeDescr,x5.Note IsContainTaxDescr," +
					"a.IsGroup,x6.Note IsGroupDescr " +
					"from tSupplier a " +
					"left outer join (select * from tXTDM where ID='YESNO') c on a.IsSpecDay=c.CBM  " +
					"left outer join tItemType1 b on b.code=a.ItemType1  " +
					"left outer join tXTDM x1 on x1.ID='YESNO' and x1.CBM=a.IsWeb " +
					"left outer join tXTDM x2 on x2.ID='PurchCostModel' and x2.CBM=a.PurchCostModel " +
					"left outer join tXTDM x3 on x3.ID='OrderType' and x3.CBM=a.InOrderType " +
					"left outer join tXTDM x4 on x4.ID='OrderType' and x4.CBM=a.OutOrderType " +
					"left outer join tXTDM x5 on x5.ID='YESNO' and x5.CBM=a.IsContainTax " +
					"left outer join tXTDM x6 on x6.ID='YESNO' and x6.CBM=a.IsGroup " + //是否集团供应商 add by zb on 20190426
					"left join tDepartment2 dpt2 on dpt2.Code = a.Department2  " +
					"where 1=1 ";
		if (StringUtils.isBlank(supplier.getExpired()) || "F".equals(supplier.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(supplier.getCode())) {
			sql += " and a.Code=? ";
			list.add(supplier.getCode());
		}
		if (StringUtils.isNotBlank(supplier.getDescr())) {
			sql += " and a.Descr like ? ";
			list.add("%"+supplier.getDescr()+"%");
		}
		if (StringUtils.isNotBlank(supplier.getItemType1())) {
			sql += " and a.ItemType1 =? ";
			list.add(supplier.getItemType1());
		} else {
			String str = SqlUtil.resetStatus(supplier.getItemRight());
			sql += " and a.ItemType1 in ("+str+")";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " )a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " )a order by a.LastUpdate desc";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * 供应商存储过程
	 * @author	created by zb
	 * @date	2018-12-27--下午5:08:31
	 * @param supplier
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result doSave(Supplier supplier) {
		Assert.notNull(supplier);
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pGysgl(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, supplier.getM_umState());
			call.setString(2, supplier.getCode());
			call.setString(3, supplier.getDescr());
			call.setString(4, supplier.getAddress());
			call.setString(5, supplier.getContact());
			call.setString(6, supplier.getPhone1());
			call.setString(7, supplier.getPhone2());
			call.setString(8, supplier.getFax1());
			call.setString(9, supplier.getFax2());
			call.setString(10, supplier.getMobile1());
			call.setString(11, supplier.getMobile2());
			call.setString(12, supplier.getEmail1());
			call.setString(13, supplier.getEmail2());
			call.setString(14, supplier.getIsSpecDay());
			if (supplier.getSpecDay() == null) {
				call.setInt(15, 0);
			} else {
				call.setInt(15, supplier.getSpecDay());
			}
			call.setInt(16, supplier.getBillCycle());
			call.setString(17, supplier.getItemType1());
			call.setString(18, supplier.getExpired());
			call.setString(19, supplier.getLastUpdatedBy());
			call.setString(20, supplier.getActName());
			call.setString(21, supplier.getCardID());
			call.setString(22, supplier.getBank());
			call.setString(23, supplier.getIsWeb());
			call.setString(24, supplier.getDepartment2());
			call.setString(25, supplier.getPurchCostModel().trim());
			call.setString(26, supplier.getInOrderType());
			call.setString(27, supplier.getOutOrderType());
			call.setString(28, supplier.getSupplFeeType());
			call.setString(29, supplier.getIsContainTax());
			call.setString(30, supplier.getIsGroup());
			call.setString(31, supplier.getCmpCode());
			call.setInt(32,supplier.getPreOrderDay()== null ?0:supplier.getPreOrderDay());
			call.setString(33, supplier.getSendMode().trim());
			call.setString(34, supplier.getBusinessPhoto());
			call.execute();
			ResultSet rs = call.getResultSet();	
			Map<String, Object> retMsg=BeanConvertUtil.resultSetToList(rs).get(0);
			result.setCode(String.valueOf(retMsg.get("ret")));
			result.setInfo(String.valueOf(retMsg.get("errmsg")));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return result;
	}

	/**
	 * 供应商管理查看详细列表
	 * @author	created by zb
	 * @date	2018-12-28--上午11:20:07
	 * @param page
	 * @param supplier
	 * @return
	 */
	public Page<Map<String, Object>> findPrepayTranJqGridBySql(
			Page<Map<String, Object>> page, Supplier supplier) {
		String sql = "select a.PK,a.Date,a.SplCode,a.PrefixCode,b.Desc2 PrefixDesc,a.Document, " +
					"a.TrsAmount,a.AftAmount,a.Remarks,a.LastUpdate,a.LastUpdatedBy,a.Expired, " +
					"a.ActionLog " +
					"from tPrepayTransaction a " +
					"left join tPrefix b on a.PrefixCode=b.Prefix " +
					"where a.SplCode=? " +
					"order by a.LastUpdate desc";
		return this.findPageBySql(page, sql, new Object[]{supplier.getCode()});
	}

	/**
	 * 发货时限设定详细查询
	 * @author	created by zb
	 * @date	2018-12-29--上午10:15:56
	 * @param page
	 * @param supplier
	 * @return
	 */
	public Page<Map<String, Object>> findSupTimeJqGridBySql(
			Page<Map<String, Object>> page, Supplier supplier) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select a.No,a.ItemType1,b.Descr Itemtype1Descr,a.ProductType,xt1.NOTE ProductTypeDescr, " +
					"a.IsSetItem,xt2.NOTE IsSetItemDescr,a.SendDay,a.Prior,a.Remarks, " +
					"a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,sp.SupplCode,sp.SendTimeNo  " +
					"from tSendTime a  " +
				    "inner join ( " ;
		if (StringUtils.isNotBlank(supplier.getSendTimeNo())) {
			sql +=  "	select SupplCode=?, SendTimeNo=?" ;
			list.add(supplier.getCode());
			list.add(supplier.getSendTimeNo());
		} else {
			sql +=  "	select * from tSupplierTime where supplCode=? " ;
			list.add(supplier.getCode());
		}
			sql +=  ") sp on a.No=sp.SendTimeNo  " +
					"left join titemtype1 b on a.ItemType1=b.Code  " +
					"left join tXTDM xt1 on a.ProductType=xt1.CBM and xt1.ID='APPPRODUCTTYPE'  " +
					"left join tXTDM xt2 on a.IsSetItem=xt2.CBM and xt2.ID='YESNO'  " +
					"where 1=1 ";
		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * 发货时限保存
	 * @author	created by zb
	 * @date	2018-12-29--下午3:58:49
	 * @param supplier
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result doDDDSave(Supplier supplier) {
		Assert.notNull(supplier);
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pGysFhsx_forXml(?,?,?,?,?,?)}");
			call.setString(1, supplier.getCode());
			call.setString(2, supplier.getM_umState());
			call.setString(3, supplier.getLastUpdatedBy());
			call.setString(4, supplier.getDetailJson());
			call.registerOutParameter(5, Types.INTEGER);
			call.registerOutParameter(6, Types.NVARCHAR);
			call.execute();
			result.setCode(String.valueOf(call.getInt(5)));
			result.setInfo(call.getString(6));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	/**
	 * 获取供应商供应公司编号
	 * @author	created by zb
	 * @date	2019-4-29--下午6:28:50
	 * @param page
	 * @param supplier
	 * @return
	 */
	public Page<Map<String, Object>> getCmpCode(Page<Map<String, Object>> page,
			Supplier supplier) {
		String sql = "select * from tsupplCmp where SupplCode = ? ";
		return this.findPageBySql(page, sql, new Object[]{supplier.getCode()});
	}
	
}

