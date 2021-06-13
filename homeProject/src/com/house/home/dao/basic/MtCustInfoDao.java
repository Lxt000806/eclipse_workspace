package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.SqlUtil;
import com.house.home.entity.basic.MtCustInfo;
import com.house.home.entity.design.CustCon;
import com.house.home.entity.design.Customer;
import com.house.home.entity.project.FixDuty;

@SuppressWarnings("serial")
@Repository
public class MtCustInfoDao extends BaseDao {

	public boolean existsCustCodeMT(String custCodeMT){
		String sql = " select 1 from tMtCustInfo where CustCodeMT = ? ";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[]{custCodeMT});
		if(list != null && list.size() > 0){
			return true;
		}
		return false;
	}
	/**
	 * MtCustInfo分页信息
	 * 
	 * @param page
	 * @param mtCustInfo
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, MtCustInfo mtCustInfo) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select a.PK,a.Status,b.NOTE statusDescr,e.Descr RegionDescr,a.CustDescr, " 
						+"a.CustPhone,a.Gender,a.Address,a.Area,a.Layout,a.IsFixtures,a.Remark, "  
						+"g3.NameChi BusinessMan,d.Descr Region2,a.ShopName,a.Manage,a.ManagePhone, "  
						+"a.LastUpdate,a.LastUpdatedBy,a.ActionLog,a.Expired,c.address YjAddress,f.ZWXM czy," 
						+"case when h.Desc2 is not null then h.Desc2  else i.Desc2 end departmentDescr,c.CrtDate, "
						+"j.NOTE YjStatus,c.SetDate,c.SignDate, c.DesignMan, g2.NameChi DesignManDescr,k1.Remarks ConRemarks,c.Code CustCode, "
						+"g2.Department2, k.Desc1 Department2Descr,c.Remarks CustRemarks,a.perf,a.PerfCompDate,sendDate  "
						+"from tMtCustInfo a  "  
						+"left join tXTDM b on a.Status=b.CBM and b.ID='MTCUSTINFOSTAT' "   
						+"left join tCustomer c on a.PK=c.MtCustInfoPK " 
						+"left join tMtRegion d on a.Region2Code=d.RegionCode and d.BelongRegionCode <>'' " 
						+"left join tMtRegion e on a.RegionCode=e.RegionCode and e.BelongRegionCode ='' " 
						+"left join tCZYBM f on d.CZYBH=f.CZYBH " 
						+"left join tEmployee g on g.Number=f.EmNum "
						+"left join tDepartment2 h on h.Code=g.Department2 " 
						+"left join tDepartment1 i on i.Code=g.Department1 " 
						+"left join tXTDM j on j.CBM=c.status and j.ID='CUSTOMERSTATUS' " 
						+"left join (select in_c.* from tMtCustInfo in_a "
						+"left join tcustomer in_b on in_a.PK=in_b.MtCustInfoPK "
						+"left join tCustCon in_c on in_b.Code=in_c.CustCode "
						+"where in_c.ConDate in (select max(ConDate) ConDate from tCustCon where CustCode=in_b.Code)"
						+") k1 on k1.CustCode = c.Code "
						+"left join tEmployee g2 on g2.Number=c.DesignMan "
						+"left join tDepartment2 k on k.Code=g2.Department2 " +
						"left join tEmployee g3 on g3.Number=c.BusinessMan " //modify by zb on 20190626
						+"where 1=1 ";

		if (StringUtils.isNotBlank(mtCustInfo.getAddress())) {
			sql += " and a.Address like ? ";
			list.add("%"+mtCustInfo.getAddress()+"%");
		}
    	if (StringUtils.isNotBlank(mtCustInfo.getStatus())) {
			sql += " and a.Status in ('"+mtCustInfo.getStatus().replaceAll(",", "','") + "')";
		}
    	if (mtCustInfo.getDateFrom() != null) {
			sql += " and a.CrtDate>=? ";
			list.add(mtCustInfo.getDateFrom());
		}
    	if (mtCustInfo.getDateTo() != null) {
			sql += " and a.CrtDate<dateadd(day,1,?) ";
			list.add(mtCustInfo.getDateTo());
		}
    	if (StringUtils.isNotBlank(mtCustInfo.getRegionDescr())) {
			sql += " and a.RegionDescr=? ";
			list.add(mtCustInfo.getRegionDescr());
		}
    	if (StringUtils.isNotBlank(mtCustInfo.getIsFixtures())) {
    		mtCustInfo.setIsFixtures("0".equals(mtCustInfo.getIsFixtures())?"否":"是");
			sql += " and a.IsFixtures=? ";
			list.add(mtCustInfo.getIsFixtures());
		}
    	if (StringUtils.isNotBlank(mtCustInfo.getLayout())) {
    		String layout="";
    		if("0".equals(mtCustInfo.getLayout())){
    			layout="平层";
    		}else if("1".equals(mtCustInfo.getLayout())){
    			layout="复式";
    		}else{
    			layout="别墅";
    		}
			sql += " and a.Layout=? ";
			list.add(layout);
		}
    	if(StringUtils.isNotBlank(mtCustInfo.getIsDistribute())){
    		if("0".equals(mtCustInfo.getIsDistribute())){
    			sql += " and a.BusinessMan is null ";
    		}else{
    			sql += " and a.BusinessMan is not null ";
    		}
    	}
		if (StringUtils.isBlank(mtCustInfo.getExpired()) || "F".equals(mtCustInfo.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if(!"3".equals(mtCustInfo.getCustRight().trim())){
			sql += " and (a.Region2Code in (select MtRegionCode from tCZYMtRegion where CZYBH=?) "
				 + " or exists(select 1 from TSYS_CZYBM_AUTHORITY in_a where in_a.CZYBH=? and in_a.AUTHORITY_ID='2054'))";
			list.add(mtCustInfo.getLastUpdatedBy());
			list.add(mtCustInfo.getLastUpdatedBy());
		}
		if (StringUtils.isNotBlank(mtCustInfo.getCzybh())) {
			sql += " and d.CZYBH=? ";
			list.add(mtCustInfo.getCzybh());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.Pk";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 取消
	 * 
	 * @param page
	 * @param mtCustInfo
	 * @return
	 */
	public void cancel(MtCustInfo mtCustInfo) {
		List<Object> list = new ArrayList<Object>();

		String sql = "update tMtCustInfo set status='5',lastupdate=getdate(),lastupdatedby=?,actionlog='EDIT' where pk=?";
		list.add(mtCustInfo.getLastUpdatedBy());
		list.add(mtCustInfo.getPK());
		executeUpdateBySql(sql, list.toArray()).toString();
	}
	
	public Map<String, Object> getMtPerfNoSend(){
		String sql = " select top 1 a.PK, a.custCodeMT,round(a.Perf, 2) achMoney,convert(datetime, a.PerfCompDate, 122) achTime,convert(datetime, b.signDate, 122) signTime "
				   + " from tMtCustInfo a "
				   + " left join tCustomer b on a.PK = b.MtCustInfoPK "
				   + " where a.Status = '3' and a.Perf > 0 and a.SendDate is null ";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[]{});
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	/**
	 * MtRegion分页信息
	 * 
	 * @param page
	 * @param mtCustInfo
	 * @return
	 */
	public Page<Map<String,Object>> goMtRegionJqGrid(Page<Map<String,Object>> page) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select b.ZWXM czy,a.Descr region2Descr,e.Descr regionDescr,a.regionCode, "
				+"case when isnull(d2.Desc2,'')<>'' then d2.Desc2 else d1.Desc2 end departmentDescr "
				+"from tMtRegion a  "
				+"left join tCZYBM b on a.CZYBH=b.CZYBH "
				+"left join tEmployee c on b.EMNum=c.Number "
				+"left join tDepartment1 d1 on c.Department1=d1.Code "
				+"left join tDepartment2 d2 on c.Department2=d2.Code "
				+"left join tMtRegion e on a.BelongRegionCode=e.RegionCode "
				+"where isnull(a.BelongRegionCode,'') <>''";
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.regionDescr";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 设置对接人
	 * 
	 * @param page
	 * @param mtCustInfo
	 * @return
	 */
	public void setCzy(MtCustInfo mtCustInfo) {
		List<Object> list = new ArrayList<Object>();

		String sql = "update tMtRegion set CZYBH=?,LastUpdate=getdate(),LastUpdatedBy=?,ActionLog='EDIT' "
				+"where RegionCode in ('"+mtCustInfo.getCodes().replaceAll(",", "','") + "')";
		list.add(mtCustInfo.getCzybh());
		list.add(mtCustInfo.getLastUpdatedBy());
		executeUpdateBySql(sql, list.toArray()).toString();
	}
	
	public Page<Map<String,Object>> findCustConPageBySql(Page<Map<String,Object>> page, MtCustInfo mtCustInfo) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from (select a.PK,a.CustCode,b.Descr,b.Address custAddress,a.ConDate,a.ConMan,c.NameChi ConManDescr, "
				+"b.DesignMan,d.NameChi DesignManDescr,b.BusinessMan,e.NameChi BusinessManDescr, "
				+"a.Remarks,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,b.Status,f.NOTE StatusDescr "
				+"from tCustCon a  left outer join tCustomer b on a.CustCode=b.Code  "
				+"left outer join tEmployee c on a.ConMan=c.Number  "
				+"left outer join tEmployee d on b.DesignMan=d.Number "
				+"left outer join tEmployee e on b.BusinessMan=e.Number "
				+"left outer join tXTDM f on b.Status=f.CBM and f.ID='CUSTOMERSTATUS'  "
				+"where 1=1  and a.CustCode=? ";
			
		list.add(mtCustInfo.getCustCode());
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		}else{
			sql += ") a order by a.Pk";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

	public String getCustCodeByPk(MtCustInfo mtCustInfo){
		String sql = " select Code CustCode from tCustomer where MtCustInfoPK = ?";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[]{mtCustInfo.getPK()});
		if(list != null && list.size() > 0){
			return list.get(0).get("CustCode").toString();
		}
		return "";
	}
	
	public List<Map<String, Object>> getCustPerfData(Customer customer){
		String sql = " select sum(PerfAmount) PerfAmount,AchieveDate from tPerformance " +
				"	where Custcode = ? and Type = '1' group  by AchieveDate ,CustCode ,Type ";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[]{customer.getCode()});
		if(list != null && list.size() > 0){
			return list;
		}
		return null;
	}
}

