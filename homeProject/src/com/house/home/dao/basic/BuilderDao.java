package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.home.entity.basic.Builder;

@SuppressWarnings("serial")
@Repository
public class BuilderDao extends BaseDao {

	/**
	 * 项目名称分页信息
	 * 
	 * @param page
	 * @param user
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, Builder builder) {
		List<Object> list = new ArrayList<Object>();
				String sql = "select * from (select a.code,a.Descr,a.Alias,a.GroupCode,b.Descr GroupCodeDescr,a.prjleader,f.nameChi prjleaderdescr,"
						+ "a.AddressType,c.Note AddressTypeDescr,a.Remarks,a.DelivDate,a.BuilderType,x2.note BuilderTypeDescr,"
						+ "a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,a.DectStatus ,a.Adress,a.HouseNum ,a.SendRegion,g.Descr SendRegionDescr, "
						+ "a.HouseType,a.MajorArea ,a.ReferPrice,a.Property ,a.Developer,a.RegionCode,x1.NOTE DectStatusDescr,d.Descr RegionDescr,"
						+ "a.Longitude, a.Latitude,x4.Note HouseTypeDescr,a.RegionCode2, e.Descr Region2Descr,a.OffSet,a.IsPrjSpc,x3.Note IsPrjSpcdescr "
						+ "from tBuilder a "
						+ "left join tBuilderGroup b on a.GroupCode=b.Code "
						+ "left join tXTDM c on c.CBM=a.AddressType and c.ID='ADDRESSTYPE' "
						+ "left join tXTDM x1 on x1.cbm=a.DectStatus and x1.id='BLDDECTSTS' "
						+ "left join tRegion d on d.Code=a.RegionCode "
						+ "left join tXTDM x2 on x2.cbm=a.BuilderType and x2.id='BUILDERTYPE' "
						+ "left join tRegion2 e on e.Code=a.RegionCode2 "
						+ "left join tXTDM x3 on x3.cbm=a.IsPrjSpc and x3.id='YESNO' "
						+ "left join tXTDM x4 on x4.cbm=a.HouseType and x4.id='HOUSETYPE' "
						+ "left join tEmployee f on a.PrjLeader=f.Number "
						+ "left join tSendRegion g on a.SendRegion=g.No "
						+ " where 1=1 ";

		if (StringUtils.isNotBlank(builder.getCode())) {
			sql += " and a.Code=? ";
			list.add(builder.getCode());
		}
		if (StringUtils.isNotBlank(builder.getIsPrjSpc())) {
			sql += " and a.IsPrjSpc=? ";
			list.add(builder.getIsPrjSpc());
		}
		if (StringUtils.isNotBlank(builder.getPrjLeader())) {
			sql += " and a.PrjLeader=? ";
			list.add(builder.getPrjLeader());
		}
		if (StringUtils.isNotBlank(builder.getDescr())) {
			sql += " and (a.Descr like ? or a.alias like ?) ";
			list.add("%" + builder.getDescr() + "%");
			list.add("%" + builder.getDescr() + "%");
		}
		if (StringUtils.isNotBlank(builder.getAdress())) {
			sql += " and a.Adress like ? ";
			list.add("%" + builder.getAdress() + "%");
		}
		if (StringUtils.isBlank(builder.getExpired())
				|| "F".equals(builder.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (builder.getDelivDateFrom() != null) {
			sql += " and a.DelivDate>= ? ";
			list.add(builder.getDelivDateFrom());
		}
		if (builder.getDelivDateTo() != null) {
			sql += " and a.DelivDate< ? ";
			list.add(DateUtil.addInteger(builder.getDelivDateTo(),
					Calendar.DATE, 1));
		}
		if("1".equals(builder.getOnlyShowSendRegionNull())){
			sql += " and isnull(a.SendRegion,'')='' ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

	public Page<Map<String, Object>> findSpcBuilderAddPageBySql(
			Page<Map<String, Object>> page, Builder builder, String arr) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (SELECT a.*,b.Descr GroupCodeDescr,c.NOTE AddressTypeDescr,r1.descr regiondescr1 ,r2.descr regiondescr2 FROM tBuilder a "
				+ "LEFT JOIN tBuilderGroup b ON a.GroupCode=b.Code "
				+ " left join tRegion r1 on r1.code=a.regioncode "
				+ " left join tregion2 r2 on r2.code=a.regioncode2 "
				+ "LEFT JOIN tXTDM c ON a.AddressType=c.CBM AND c.ID='ADDRESSTYPE'"
				+ " where 1=1   ";
		/*
		 * if(StringUtils.isNotBlank(builder.getSpcBuilder())){
		 * sql+=" and (a.spcBuilder is null or a.SpcBuilder= ? ) ";
		 * list.add(builder.getSpcBuilder()); }else{
		 * sql+=" and a.spcBuilder is null "; }
		 */
		/*
		 * if(StringUtils.isNotBlank(arr)){ sql += " and a.code not in " +
		 * "('"+arr.replaceAll(",", "','")+"')"; }
		 */

		if (StringUtils.isNotBlank(builder.getDescr())) {
			sql += " and (a.Descr like ? or a.alias like ?) ";
			list.add("%" + builder.getDescr() + "%");
			list.add("%" + builder.getDescr() + "%");
		}

		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.Code ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

	public Page<Map<String, Object>> findSpcBuilderPageBySql(
			Page<Map<String, Object>> page, Builder builder) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select c.*,b.BuilderDelivCode delivcode, dbo.fGetBuilderNum(b.BuilderDelivCode) builderCode,r1.descr regiondescr1,r2.descr regiondescr2 "
				+ " from tSpcBuilder a"
				+ " left join tSpcBuilderDeliv b on a.code=b.SpcBuilder"
				+ " left join tBuilder c on c.code=b.BuilderCode"
				+ " left join tRegion r1 on c.regionCode = r1.code  "
				+ " left join tRegion2 r2 on c.regionCode2 = r2.code "
				+ " where 1=1 ";

		if (StringUtils.isNotBlank(builder.getSpcBuilder())) {
			sql += " and a.code =? ";
			list.add(builder.getSpcBuilder());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += " order by a.code ";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	@SuppressWarnings("unchecked")
	public List<Builder> findByNoExpired(String condition) {
		String hql = "from Builder ";
		if (StringUtils.isNotBlank(condition)) {
			hql += condition;
		}
		return this.find(hql);
	}

	@SuppressWarnings("unchecked")
	public Builder getByDescr(String descr) {
		String hql = "from Builder a where a.descr=?";
		List<Builder> list = this.find(hql, new Object[] { descr });
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 根据code查询builder
	 * 
	 * @param code
	 * @return
	 */
	public Map<String, Object> findByCode(String code) {
		String sql = "select a.code,a.Descr,a.Alias,a.GroupCode,b.Descr GroupCodeDescr,"
				+ "a.AddressType,c.Note AddressTypeDescr,a.Remarks,a.DelivDate,a.BuilderType,x2.note BuilderTypeDescr,"
				+ "a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,a.DectStatus ,a.Adress,a.HouseNum ,a.sendRegion,"
				+ "a.HouseType,a.MajorArea ,a.ReferPrice,a.Property ,a.Developer,a.RegionCode,x1.NOTE DectStatusDescr,d.Descr RegionDescr,"
				+ "a.Longitude, a.Latitude,a.PrjLeader,f.NameChi PrjLeaderDescr,x4.Note HouseTypeDescr,a.RegionCode2, e.Descr Region2Descr,a.OffSet,a.IsPrjSpc,x3.Note IsPrjSpcdescr "
				+ " ,splSendRegion "
				+ "from tBuilder a "
				+ "left join tBuilderGroup b on a.GroupCode=b.Code "
				+ "left join tXTDM c on c.CBM=a.AddressType and c.ID='ADDRESSTYPE' "
				+ "left join tXTDM x1 on x1.cbm=a.DectStatus and x1.id='BLDDECTSTS' "
				+ "left join tRegion d on d.Code=a.RegionCode "
				+ "left join tXTDM x2 on x2.cbm=a.BuilderType and x2.id='BUILDERTYPE' "
				+ "left join tRegion2 e on e.Code=a.RegionCode2 "
				+ "left join tXTDM x3 on x3.cbm=a.IsPrjSpc and x3.id='YESNO' "
				+ "left join tXTDM x4 on x4.cbm=a.HouseType and x4.id='HOUSETYPE' "
				+ "left join tEmployee f on f.Number=a.PrjLeader"
				+ " where a.code=? ";
		List<Map<String, Object>> list = this.findBySql(sql,
				new Object[] { code });
		System.out.println(list);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;

	}

	/**
	 * 一级区域
	 * 
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> findRegionCode1(Map<String, Object> param) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select Code id, Descr name from tRegion  where Expired='F'  ";
		if (StringUtils.isNotBlank((String) param.get("pCode"))) {
			sql += " and code not  in (" + param.get("pCode") + ") ";
		}
		sql += " order by Code";
		return this.findBySql(sql, list.toArray());
	}

	/**
	 * 二级区域
	 * 
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> findRegionCode2(Map<String, Object> param) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select Code id, Descr name from tRegion2 where Expired='F' ";
		if (StringUtils.isNotBlank((String) param.get("pCode"))) {
			sql += " and RegionCode=?";
			list.add((String) param.get("pCode"));
		}
		sql += " order by Code";
		return this.findBySql(sql, list.toArray());
	}

	/**
	 * 修改工程信息
	 * 
	 * @param builder
	 */
	public void infoUpdate(Builder builder) {
		List<Object> list = new ArrayList<Object>();
		String sql = "UPDATE  tBuilder SET OffSet=?,IsPrjSpc=?,RegionCode=?,RegionCode2=?,Latitude=?,"
				+ "Longitude=?,PrjLeader=?,LastUpdate=getdate(),ActionLog='EDIT',LastUpdatedBy=?  where Code=?";
		list.add(builder.getOffset());
		list.add(builder.getIsPrjSpc());
		list.add(builder.getRegionCode());
		list.add(builder.getRegionCode2());
		list.add(builder.getLatitude());
		list.add(builder.getLongitude());
		list.add(builder.getPrjLeader());
		list.add(builder.getLastUpdatedBy());
		list.add(builder.getCode());
		this.executeUpdateBySql(sql, list.toArray());
	}
	/**
	 * 修改配送区域
	 * 
	 * @param builder
	 */
	public void regionUpdate(Builder builder) {
		List<Object> list = new ArrayList<Object>();
		String sql = "UPDATE  tBuilder SET SendRegion=?,LastUpdatedBy=?, LastUpdate=getdate(),ActionLog='EDIT',splSendRegion=?  where Code=?";
		list.add(builder.getSendRegion());
		list.add(builder.getLastUpdatedBy());
		list.add(builder.getSplSendRegion());
		list.add(builder.getCode());
		this.executeUpdateBySql(sql, list.toArray());
	}
	/**
	 * 根据二级区域查工程大区
	 * 
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> findPrjRegion(String regionCode2) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select a.Descr from dbo.tPrjRegion a "
				+ "left join dbo.tRegion2 b on a.Code=b.PrjRegionCode "
				+ "where b.Code=?";
		list.add(regionCode2);
		return this.findBySql(sql, list.toArray());
	}
	
	public Page<Map<String, Object>> getBuilderList(
			Page<Map<String, Object>> page, Builder builder) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select code,descr from tbuilder a " +
				" where 1=1 and a.expired = 'F' ";
		
		if(StringUtils.isNotBlank(builder.getDescr())){
			sql+=" and a.descr like  ?";
			list.add("%"+builder.getDescr()+"%");
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
}
