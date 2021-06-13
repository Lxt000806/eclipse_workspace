package com.house.home.dao.finance;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.SqlUtil;
import com.house.home.entity.finance.IntPerfDetail;

@SuppressWarnings("serial")
@Repository
public class IntPerfDetailDao extends BaseDao {

	/**
	 * IntPerfDetail分页信息
	 * 
	 * @param page
	 * @param intPerfDetail
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, IntPerfDetail intPerfDetail) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from(select a.custCode,a.pk,a.no,a.PerfAmount,a.PerfAmount_Set,"
				+ "a.TotalPerfAmount,a.IsModified,x3.note ismodifieddescr,a.LastUpdate,a.LastUpdatedBy,"
				+ "a.ActionLog,a.Expired,a.type,x1.note TypeDescr,a.IsCupboard,x2.note IsCupboardDescr,b.documentNo,b.Address,"
				+ "b.Custtype,c.desc1 custTypeDescr,a.DesignMan,d.NameChi designManDescr,r1.Descr Region1Descr,"
				+ "r2.Descr Region2Descr,ch.chgNum,b.SignDate,case when a.IsCupboard='0' then b.IntegrateDisc else b.CupBoardDisc end +isnull(chg.DiscAmount,0) DiscAmount,isnull(ia.AppNum,0)AppNum "
				+ "from tIntPerfDetail a "
				+ "left join tCustomer b on a.CustCode =b.Code "
				+ "left join tCusttype c on b.custtype=c.code "
				+ "left join tEmployee d on a.DesignMan=d.Number "
				+ "left join txtdm x1 on x1.cbm =a.Type and x1.id='INTPERFTYPE' "
				+ "left join txtdm x2 on x2.cbm =a.IsCupboard and x2.id='YESNO' "
				+ "left join txtdm x3 on x3.cbm =a.IsModified and x3.id='YESNO' "
				+ "left join tBuilder bd on bd.Code=b.BuilderCode  "
				+ "left join tRegion r1 on r1.Code=bd.RegionCode "
				+ "left join tRegion2 r2 on r2.Code=bd.RegionCode2 "
				+ "left join (select count(1) chgNum,in_c.CustCode,in_c.IsCupboard from tItemChg in_c "
				+ "where in_c.ItemType1='JC' and in_c.Status='2' ";
				if(intPerfDetail.getDateTo()!= null){ // 变更次数截止至本周期结束时间  by zjf20200929
					sql += " and confirmdate<=? ";;
					list.add(new Timestamp(DateUtil.endOfTheDay(intPerfDetail.getDateTo()).getTime()));
				}
				sql += " group by  in_c.CustCode,in_c.IsCupboard )ch on ch.CustCode=a.CustCode and a.IsCupboard=ch.IsCupboard "
				+ "left join (select CustCode,IsCupboard,sum( "
				+ "case when BefAmount>0 then (case when DiscAmount>0 then 1 else -1 end) " 
				+ "when BefAmount<0 then (case when DiscAmount>0 then -1 else 1 end) else 1 end " 
				+ "* DiscAmount)DiscAmount from tItemChg " 
				+ "where ItemType1='JC' and Status='2' ";//and confirmdate>=? and confirmdate<=? 
				if(intPerfDetail.getDateFrom()!= null){
					sql += " and confirmdate>=? ";
					list.add(intPerfDetail.getDateFrom());
				}
				if(intPerfDetail.getDateTo()!= null){
					sql += " and confirmdate<=? ";;
					list.add(new Timestamp(DateUtil.endOfTheDay(intPerfDetail.getDateTo()).getTime()));
				}
				sql += " group by CustCode,IsCupboard )chg on a.CustCode=chg.CustCode and a.IsCupboard=chg.IsCupboard "
				+ "left join (select CustCode,IsCupboard,sum(case when type ='S' then 1 when type='R' then -1 else 0 end )AppNum from tItemApp "
				+ "where ItemType1='JC' and Status in ('CONFIRMED','SEND','RETURN') " ;
				if(intPerfDetail.getDateTo()!= null){ // 领料次数截止至本周期结束时间  by zjf20200929
					sql += " and confirmdate<=? ";
					list.add(new Timestamp(DateUtil.endOfTheDay(intPerfDetail.getDateTo()).getTime()));
				};
				sql +="group by CustCode,IsCupboard )ia on a.CustCode=ia.CustCode and a.IsCupboard=ia.IsCupboard "
				+ "where 1=1 ";
//		list.add(intPerfDetail.getDateFrom());
//		list.add(new Timestamp(DateUtil.endOfTheDay(intPerfDetail.getDateTo()).getTime()));
//		list.add(DateUtil.addDate(intPerfDetail.getDateTo(), 1));
		if (StringUtils.isNotBlank(intPerfDetail.getNo())) {
			sql += " and a.no = ? ";
			list.add(intPerfDetail.getNo());
		}
		if (StringUtils.isNotBlank(intPerfDetail.getAddress())) {
			sql += " and b.address like ? ";
			list.add("%" + intPerfDetail.getAddress() + "%");
		}
		if (StringUtils.isNotBlank(intPerfDetail.getType())) {
			sql += " and a.type = ? ";
			list.add(intPerfDetail.getType());
		}
		if (StringUtils.isNotBlank(intPerfDetail.getIsCupboard())) {
			sql += " and a.iscupboard = ? ";
			list.add(intPerfDetail.getIsCupboard());
		}
		if (StringUtils.isNotBlank(intPerfDetail.getDesignMan())) {
			sql += " and a.designMan = ? ";
			list.add(intPerfDetail.getDesignMan());
		}
		if (StringUtils.isNotBlank(intPerfDetail.getCustType())) {
			sql += " and b.CustType in ("+SqlUtil.resetStatus(intPerfDetail.getCustType())+")";
		}
		if (StringUtils.isBlank(intPerfDetail.getDesignMan())
				&& StringUtils.isNotBlank(intPerfDetail.getIsReportView())) {
			sql += " and (a.designMan is null or a.designMan='') ";
		}
		if (StringUtils.isNotBlank(intPerfDetail.getCustCode())) {
			sql += " and a.custCode = ? ";
			list.add(intPerfDetail.getCustCode());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ")a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ")a order by a.LastUpdate desc";
		}
//		System.out.println(sql);
		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * IntPerfDetail报表
	 * 
	 * @param page
	 * @param intPerfDetail
	 * @return
	 */
	public Page<Map<String, Object>> findReportPageBySql(
			Page<Map<String, Object>> page, IntPerfDetail intPerfDetail) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from(select  b.number,b.namechi,b.department2,sum(a.perfamount)perfamount,"
				+ "sum(a.perfamount_set)perfamount_set,sum(totalperfamount)totalperfamount,c.desc1 department2descr "
				+ "from tIntPerfDetail a "
				+ "left join tEmployee b on a.deSignMan=b.number "
				+ "left join tDepartment2 c on b.department2=c.code "
				+ "where 1=1 ";

		if (StringUtils.isNotBlank(intPerfDetail.getNo())) {
			sql += " and a.no = ? ";
			list.add(intPerfDetail.getNo());
		}
		if (StringUtils.isNotBlank(intPerfDetail.getDepartment2())) {
			sql += " and c.code = ? ";
			list.add(intPerfDetail.getDepartment2());
		}
		if (StringUtils.isNotBlank(intPerfDetail.getDesignMan())) {
			sql += " and a.designMan = ? ";
			list.add(intPerfDetail.getDesignMan());
		}
		if (StringUtils.isNotBlank(intPerfDetail.getIsCupboard())) {
			sql += " and a.isCupBoard = ? ";
			list.add(intPerfDetail.getIsCupboard());
		}
		sql += " group by b.number,b.namechi,b.department2,c.desc1 ";
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ")a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ")a order by a.namechi";
		}
		System.out.println(sql);
		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * IntPerfDetail Map
	 * 
	 * @param pk
	 * @return
	 */
	public Map<String, Object> findMapBySql(String pk) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select a.pk,a.no,a.perfAmount,a.perfAmount_Set,"
				+ "a.totalPerfAmount,a.isModified,x3.note ismodifieddescr,a.lastUpdate,a.lastUpdatedBy,"
				+ "a.actionLog,a.expired,a.type,x1.note typeDescr,a.isCupboard,x2.note isCupboardDescr,b.documentNo,b.address,"
				+ "b.custType,c.desc1 custTypeDescr,a.designMan,d.NameChi designManDescr, " 
				+ "case when b.SignDate>=cs2.QZ and a.type<>'3' then cast(cs1.QZ as float) else 1.0 end perfPer "
				+ "from tIntPerfDetail a "
				+ "left join tCustomer b on a.CustCode =b.Code "
				+ "left join tCusttype c on b.custtype=c.code "
				+ "left join tEmployee d on a.DesignMan=d.Number "
				+ "left join txtdm x1 on x1.cbm =a.Type and x1.id='INTPERFTYPE' "
				+ "left join txtdm x2 on x2.cbm =a.IsCupboard and x2.id='YESNO' "
				+ "left join txtdm x3 on x3.cbm =a.IsModified and x3.id='YESNO' "
				+ "left join tXTCS cs1 on cs1.ID='SoftPerfPer' "
				+ "left join tXTCS cs2 on cs2.ID='TaxLevyDate' "
				+ "where pk=? ";
		list = this.findBySql(sql, new Object[] { pk });
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 修改
	 * 
	 * @param intPerfDetail
	 * @return
	 */
	public void doUpdate(IntPerfDetail intPerfDetail) {
		List<Object> list = new ArrayList<Object>();
		String sql = "update tIntPerfDetail set designMan=?,perfAmount=?,perfAmount_Set=?,totalPerfAmount=?,isModified='1',"
				+ "lastUpdate=getdate(),lastUpdatedBy=? where pk=? ";
		list.add(intPerfDetail.getDesignMan());
		list.add(intPerfDetail.getPerfAmount());
		list.add(intPerfDetail.getPerfAmountSet());
		list.add(intPerfDetail.getTotalPerfAmount());
		list.add(intPerfDetail.getLastUpdatedBy());
		list.add(intPerfDetail.getPk());
		this.executeUpdateBySql(sql, list.toArray());
	}

	/**
	 * 生成业绩数据
	 * 
	 * @param no
	 * @param lastUpdatedBy
	 */
	public void doGetIntPerDetail(String no, String lastUpdatedBy,String prjPerfNo) {
		String sql = " exec pCalcIntPerf ?,?,? ";
		this.executeUpdateBySql(sql, new Object[] { no, lastUpdatedBy,prjPerfNo });
	}
	/**
	 * 查询优惠金额
	 * 
	 * @param intPerfDetail
	 * @return
	 */
	public Map<String, Object> findDisc(IntPerfDetail intPerfDetail) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "";
		if("1".equals(intPerfDetail.getIsCupboard())){
			sql="select  isnull(a.CupboardDisc*a.ContainCup,0) disc  from tCustomer a where a.Code=?";
		}else{
			sql="select  isnull(a.IntegrateDisc*a.ContainInt,0) disc from tCustomer a where a.Code=?";
		}
		list = this.findBySql(sql, new Object[] {intPerfDetail.getCustCode()});
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
