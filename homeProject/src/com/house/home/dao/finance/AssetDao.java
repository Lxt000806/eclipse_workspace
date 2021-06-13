package com.house.home.dao.finance;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.Null;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.bean.basic.AssetType;
import com.house.home.entity.basic.AssetChg;
import com.house.home.entity.finance.Asset;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

@SuppressWarnings("serial")
@Repository
public class AssetDao extends BaseDao{

	/**
	 * 主页面查询
	 * @param page
	 * @param asset
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, Asset asset) {	
		List<Object> list = new ArrayList<Object>();

		String sql = " select * from ( select a.useman,a.department dept,a.lastUpdate,a.code,a.Descr,a.Model,c.Descr typeDescr,b.Descr uomDescr," +
				" x2.note AddTypeDescr,d.Desc1 deptDescr, e1.NameChi useManDescr, x1.note statusDescr, " +
				" a.Address, a.Qty, a.OriginalValue, a.BeginDate, a.TotalDeprAmount, a.UseYear, x3.note DeprTypeDescr," +
				" a.DeprMonth, a.RemainDeprMonth ,a.NetValue, a.DeprAmountPerMonth, e2.NameChi crtCZYDescr, a.CreateDate, " +
				" x4.NOTE DecTypeDescr, e3.NameChi DecCZYDescr, a.DecDate,a.DecRemarks, a.Remarks, a.status " +
				" from tAsset a" +
				" left join tUOM b on b.Code = a.uom" +
				" left join tAssetType c on c.Code = a.AssetType" +
				" left join tDepartment d on d.Code = a.Department " +
				" left join tEmployee e1 on e1.Number = a.UseMan" +
				" left join tEmployee e2 on e2.Number = a.CreateCZY" +
				" left join tEmployee e3 on e3.Number = a.DecCZY" +
				" left join tXTDM x1 on x1.ID = 'ASSETSTATUS' and x1.cbm = a.Status" +
				" left join tXTDM x2 on x2.ID = 'ASSETADDTYPE' and x2.cbm = a.AddType  " +
				" left join tXTDM x3 on x3.ID = 'ASSETDEPRTYPE' and x3.cbm = a.DeprType " +// 折旧方法
				" left join tXTDM x4 on x4.ID = 'ASSETDECTYPE' and x4.cbm = a.DecType " +
				" where 1=1 ";// 减少方式 
		if(StringUtils.isNotBlank(asset.getCmpCode())){
			sql+=" and d.CmpCode = ? ";
			list.add(asset.getCmpCode());
		}
		if(StringUtils.isNotBlank(asset.getDepartment())){
			sql+=" and a.Department = ? ";
			list.add(asset.getDepartment());
		}
		if(StringUtils.isNotBlank(asset.getAssetType())){
			sql+=" and a.assetType = ? ";
			list.add(asset.getAssetType());
		}
		if(StringUtils.isNotBlank(asset.getUseMan())){
			sql+=" and a.UseMan = ? ";
			list.add(asset.getUseMan());
		}
		if(StringUtils.isNotBlank(asset.getStatus())){
			sql+=" and a.status = ? ";
			list.add(asset.getStatus());
		}
		if(asset.getDateFrom() != null ){
			sql += " and a.BeginDate >= ?";
			list.add(new Timestamp(
					DateUtil.startOfTheDay(asset.getDateFrom()).getTime()));
		}
		if(asset.getDateTo() != null){
			sql+=" and a.BeginDate <= ? ";
			list.add(new Timestamp(
					DateUtil.endOfTheDay(asset.getDateTo()).getTime()));
		}
		
		if(asset.getCreateDateFrom() != null ){
			sql += " and a.CreateDate >= ?";
			list.add(new Timestamp(
					DateUtil.startOfTheDay(asset.getCreateDateFrom()).getTime()));
		}
		if(asset.getCreateDateTo() != null){
			sql+=" and a.CreateDate <= ? ";
			list.add(new Timestamp(
					DateUtil.endOfTheDay(asset.getCreateDateTo()).getTime()));
		}
		
		if(asset.getDecDateFrom() != null){
			sql+=" and a.DecDate >= ?";
			list.add(new Timestamp(
					DateUtil.startOfTheDay(asset.getDecDateFrom()).getTime()));
		}
		if(asset.getDecDateTo() != null){
			sql+=" and a.decDate <= ?";
			list.add(new Timestamp(
				DateUtil.endOfTheDay(asset.getDecDateTo()).getTime()));		
		}
		if(StringUtils.isNotBlank(asset.getRemarks())){
			sql += " and a.remarks like ?";
			list.add("%"+asset.getRemarks()+"%");
		}
		if(StringUtils.isNotBlank(asset.getCode())){
			sql+=" and a.Code = ? ";
			list.add(asset.getCode());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.lastUpdate ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 查询折旧清单
	 * @param page
	 * @param asset
	 * @return
	 */
	public Page<Map<String, Object>> getChgDetailBySql(
			Page<Map<String, Object>> page, Asset asset) {	
		List<Object> list = new ArrayList<Object>();

		String sql = " select * from (" +
				" select a.AssetCode, b.Descr, b.Model, b.OriginalValue, b.TotalDeprAmount, b.NetValue,x1.note typeDescr, " +
				" b.BeginDate,case when a.ChgType = '1' then cast(a.AftValue as money)- cast(a.BefValue as money) else 0 end chgAmount, " +
				" case when a.ChgType = '5' then d1.desc1 else a.BefValue end befValue,a.date, " +
				" case when a.CHgType = '5' then d2.Desc1 else a.AftValue end aftValue, a.AftAddress,a.Remarks, " +
				" c.nameChi chgDescr from tAssetChg a " +
				" left join tAsset b on b.Code = a.AssetCode " +
				" left join tDepartment d1 on d1.code = a.BefValue and a.ChgType = '5'" +
				" left join tDepartment d2 on d2.code = a.AftValue and a.ChgType = '5'" +
				" left join tXTDM x1 on x1.ID = 'ASSETCHGTYPE' and x1.CBM = a.ChgType " +
				" left join tEmployee c on c.NUmber = a.ChgCzy " +
				" where 1=1 ";
		
		if(StringUtils.isNotBlank(asset.getChgType())){
			sql+=" and a.chgType = ? ";
			list.add(asset.getChgType());
		}
		if(StringUtils.isNotBlank(asset.getDepartment())){
			sql += " and b.department = ? ";
			list.add(asset.getDepartment());
		}
		if(StringUtils.isNotBlank(asset.getAssetType())){
			sql += " and b.assetType = ? ";
			list.add(asset.getAssetType());
		}
		if(StringUtils.isNotBlank(asset.getCode())){
			sql += " and a.assetCode = ? ";
			list.add(asset.getCode());
		}
		if(asset.getDateFrom() != null){
			sql+=" and a.Date >= ?";
			list.add(new Timestamp(
				DateUtil.startOfTheDay(asset.getDateFrom()).getTime()));
		}
		if(asset.getDateFrom() != null){
			sql+=" and a.Date <= ?";
			list.add(new Timestamp(
				DateUtil.endOfTheDay(asset.getDateTo()).getTime()));
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.Date desc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 查询折旧清单
	 * @param page
	 * @param asset
	 * @return
	 */
	public Page<Map<String, Object>> getDeprDetailBySql(
			Page<Map<String, Object>> page, Asset asset) {	
		List<Object> list = new ArrayList<Object>();

		String sql = " select * from (" +
				" select a.period,a.AssetCode, b.Descr, a.OriginalValue DeprOriginalValue, a.DeprAmount, a.TotalDeprAmount, a.DeprRate," +
				" b.OriginalValue, d.Desc1 deptDescr, e.Descr assetTypeDescr " +
				" from tAssetDepr a" +
				" left join tAsset b on b.Code = a.AssetCode " +
				" left join tAssetType c on c.Code = b.AssetType " +
				" left join tDepartment d on d.Code = b.Department " +
				" left join tAssetType e on e.code = b.assetType" +
				" where 1=1 ";
		
		if(StringUtils.isNotBlank(asset.getPeriodFrom())){
			sql += " and a.Period >= ?";
			list.add(asset.getPeriodFrom());
		}
		if(StringUtils.isNotBlank(asset.getPeriodTo())){
			sql += " and a.Period <= ? ";
			list.add(asset.getPeriodTo());
		}
		if(StringUtils.isNotBlank(asset.getDepartment())){
			sql += " and b.department = ? ";
			list.add(asset.getDepartment());
		}
		if(StringUtils.isNotBlank(asset.getAssetType())){
			sql += " and b.assetType = ? ";
			list.add(asset.getAssetType());
		}
		if(StringUtils.isNotBlank(asset.getCode())){
			sql += " and a.assetCode = ? ";
			list.add(asset.getCode());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.period ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 折旧分配查询
	 * @param page
	 * @param asset
	 * @return
	 */
	public Page<Map<String, Object>> getDeprGroupListBySql(
			Page<Map<String, Object>> page, Asset asset) {	
		List<Object> list = new ArrayList<Object>();

		String sql = " select * from (" +
				" select sum(a.DeprAmount) deprAmount, d.Desc1 deptDescr, c.Descr assetTypeDescr from tAssetDepr a " +
				" left join tAsset b on b.Code = a.AssetCode" +
				" left join tAssetType c on c.Code = b.AssetType" +
				" left join tDepartment d on d.Code = a.Department" +
				" where 1=1 ";
		
		if(StringUtils.isNotBlank(asset.getPeriodFrom())){
			sql += " and a.Period = ?";
			list.add(asset.getPeriodFrom());
		}
		if(StringUtils.isNotBlank(asset.getPeriodTo())){
			sql += " and a.Period <= ? ";
			list.add(asset.getPeriodTo());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " group by c.Code, c.Descr, d.Desc1, d.Code) a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += " group by c.Code, c.Descr, d.Desc1, d.Code) a order by a.assettypeDescr ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 资产变动页签
	 * @param page
	 * @param asset
	 * @return
	 */
	public Page<Map<String, Object>> getAssetChgByCode(
			Page<Map<String, Object>> page, Asset asset) {	
		List<Object> list = new ArrayList<Object>();

		String sql = " select * from (" +
				" select a.AssetCode, b.Descr, b.Model, b.OriginalValue, b.TotalDeprAmount, b.NetValue,x1.note typeDescr, " +
				" b.BeginDate,case when a.ChgType = '1' then cast(a.AftValue as money)- cast(a.BefValue as money) else 0 end chgAmount, " +
				" case when a.ChgType = '5' then d1.desc1 else a.BefValue end befValue, " +
				" case when a.CHgType = '5' then d2.Desc1 else a.AftValue end aftValue, a.AftAddress,a.Remarks, a.lastUpdate," +
				" c.NameCHi chgDescr" +
				" from tAssetChg a " +
				" left join tAsset b on b.Code = a.AssetCode " +
				" left join tDepartment d1 on d1.code = a.BefValue and a.ChgType = '5'" +
				" left join tDepartment d2 on d2.code = a.AftValue and a.ChgType = '5'" +
				" left join tXTDM x1 on x1.ID = 'ASSETCHGTYPE' and x1.CBM = a.ChgType " +
				" left Join tEmployee c on c.number = a.ChgCzy " +
				" where 1=1 ";
		
		if(StringUtils.isNotBlank(asset.getCode())){
			sql += " and a.assetCode = ? ";
			list.add(asset.getCode());
		}
				
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " ) a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += " ) a order by a.lastUpdate desc";
		}
		return this.findPageBySql(page, sql, list.toArray());
	} 
	
	/**
	 * 资产折旧页签
	 * @param page
	 * @param asset
	 * @return
	 */
	public Page<Map<String, Object>> getAssetDeprByCode(
			Page<Map<String, Object>> page, Asset asset) {	
		List<Object> list = new ArrayList<Object>();

		String sql = " select * from (" +
				" select a.period,a.AssetCode, b.Descr, a.OriginalValue DeprOriginalValue, a.DeprAmount, a.TotalDeprAmount, a.DeprRate," +
				" b.OriginalValue, d.Desc1 deptDescr, e.Descr assetTypeDescr,a.lastUpdate " +
				" from tAssetDepr a" +
				" left join tAsset b on b.Code = a.AssetCode " +
				" left join tAssetType c on c.Code = b.AssetType " +
				" left join tDepartment d on d.Code = b.Department " +
				" left join tAssetType e on e.code = b.assetType" +
				" where 1=1 ";
		
		if(StringUtils.isNotBlank(asset.getCode())){
			sql += " and a.assetCode = ? ";
			list.add(asset.getCode());
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " ) a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += " ) a order by a.Period";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 选择折旧类型查询
	 * @param page
	 * @param asset
	 * @return
	 */
	public Page<Map<String, Object>> findAssetTypeBySql(
			Page<Map<String, Object>> page, AssetType assetType) {	
		List<Object> list = new ArrayList<Object>();

		String sql = " select * from (" +
				" select a.Code, a.Descr, a.RemCode, x1.note typedescr,a.DeprType" +
				" from tAssetType a" +
				" left join tXTDM x1 on x1.ID = 'ASSETDEPRTYPE' and a.DeprType = x1.CBM " +
				" where 1=1 ";
		
		if(StringUtils.isNotBlank(assetType.getDescr())){
			sql += " and a.Descr like ?";
			list.add("%"+assetType.getDescr()+"%");
		}
		if(StringUtils.isNotBlank(assetType.getRemCode())){
			sql += " and a.RemCode like ? ";
			list.add("%"+assetType.getRemCode()+"%");
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " ) a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += " ) a order by a.code ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public String getSufCode(String assetType){
		
		String sql=" select right('0000'+cast(max(cast(replace(a.Code,b.RemCode,'') as int))+1 as nvarchar ),4 ) sufCode " +
				" from tAsset a " +
				" left join tAssetType b on b.Code = a.assetType " +
				" where a.assetType = ? ";
		List<Map<String, Object>> list=this.findBySql(sql, new Object[]{assetType.trim()});
		
		if(list != null && list.size() > 0){
			if(list.get(0).get("sufCode") == null){
				return "0001";
			}
			return list.get(0).get("sufCode").toString();
		}
		
		return "0001";
	}
	
	@SuppressWarnings("deprecation")
	public Result doCalcDepr(String period, String lastUpdatedBy) {
		Assert.notNull(period);
		Assert.notNull(lastUpdatedBy);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pCalcDepr(?,?,?,?)}");
			call.setString(1, period);
			call.setString(2, lastUpdatedBy);
			call.registerOutParameter(3, Types.INTEGER);
			call.registerOutParameter(4, Types.NVARCHAR);
			call.execute();
			result.setCode(String.valueOf(call.getInt(3)));
			result.setInfo(call.getString(4));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	
	@SuppressWarnings("deprecation")
	public Result doBatchChg(AssetChg assetChg) {
		Assert.notNull(assetChg);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pBatchAssetChg(?,?,?,?,?,?)}");
			call.setString(1, assetChg.getDepartment());
			call.setString(2, assetChg.getChgType());
			call.setString(3, assetChg.getLastUpdatedBy());
			call.setString(4, assetChg.getAssetChgDetailXml());
			call.registerOutParameter(5, Types.INTEGER);
			call.registerOutParameter(6, Types.NVARCHAR);
			call.execute();
			result.setCode(String.valueOf(call.getInt(5)));
			result.setInfo(call.getString(6));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	
	public boolean getAssetDeprFlag(String assetCode){
		String sql=" select 1 from tAssetDepr where assetCode = ? ";
		List<Map<String, Object>> list=this.findBySql(sql, new Object[]{assetCode});
		
		if(list != null && list.size() > 0){
			return true;
		}
		
		return false;
	}

}
