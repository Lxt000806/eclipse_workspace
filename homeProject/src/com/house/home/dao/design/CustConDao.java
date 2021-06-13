package com.house.home.dao.design;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.SqlUtil;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.design.CustCon;

@SuppressWarnings("serial")
@Repository
public class CustConDao extends BaseDao {

	/**
	 * CustCon分页信息 for app
	 * 
	 * @param page
	 * @param custCon
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CustCon custCon,UserContext uc) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from (select a.PK,a.CustCode,b.Descr,b.Address custAddress,a.ConDate,a.ConMan,c.NameChi ConManDescr, "
				+"b.DesignMan,d.NameChi DesignManDescr,b.BusinessMan,e.NameChi BusinessManDescr, "
				+"a.Remarks,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,b.Status,f.NOTE StatusDescr,a.ResrCustCode,g.NOTE TypeDescr, "
				+"a.conDuration, a.type, tcr.Name callRecord, tcr.mobileFilePath, tcr.status callRecordStatus, a.NextConDate,  "
				+"a.ConWay, h.NOTE ConWayDescr "
				+"from tCustCon a  left outer join tCustomer b on a.CustCode=b.Code  "
				+"left outer join tEmployee c on a.ConMan=c.Number  "
				+"left outer join tEmployee d on b.DesignMan=d.Number "
				+"left outer join tEmployee e on b.BusinessMan=e.Number "
				+"left outer join tXTDM f on b.Status=f.CBM and f.ID='CUSTOMERSTATUS'  " 
				+"left outer join tXTDM g on a.Type=g.CBM and g.ID='CUSTCONTYPE' "
				+"left join tCallRecord tcr on tcr.PK = a.CallRecordPK "
				+"left join tXTDM h on a.ConWay = h.CBM and h.ID = 'CONWAY' "
				+"where 1=1 and (a.ConMan="  +uc.getCzybh();
		
		if(custCon.getHasAllConAuth()){
			sql +=" or 1=1 )";
		}else{
			sql += ")";
		}

    	if (custCon.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(custCon.getPk());
		}
      	if (StringUtils.isNotBlank(custCon.getCustAddress())) {
    		sql += " and b.Address like ? ";
    		list.add("%"+custCon.getCustAddress()+"%");
    	}
      	if (StringUtils.isNotBlank(custCon.getDesignMan())) {
    		sql += " and b.designMan=? ";
    		list.add(custCon.getDesignMan());
    	}
    	if (StringUtils.isNotBlank(custCon.getBusinessMan())) {
    		sql += " and b.businessMan=? ";
    		list.add(custCon.getBusinessMan());
    	}
    	if (StringUtils.isNotBlank(custCon.getCustCode())) {
			sql += " and a.CustCode=? ";
			list.add(custCon.getCustCode());
		}
    	if (custCon.getDateFrom() != null) {
			sql += " and a.ConDate>=? ";
			list.add(custCon.getDateFrom());
		}
    	if (custCon.getDateTo() != null) {
			sql += " and a.ConDate< dateadd(day,1,?) ";
			list.add(custCon.getDateTo());
		}
    	if (StringUtils.isNotBlank(custCon.getStatus())) {
			sql += " and b.status in  ("+custCon.getStatus()+")";
		}
    	if (StringUtils.isNotBlank(custCon.getConMan())) {
			sql += " and a.ConMan=? ";
			list.add(custCon.getConMan());
		}
    	if (StringUtils.isNotBlank(custCon.getDepartment())) {
			sql += " and c.Department in ("+custCon.getDepartment()+")";
		}
    	if (StringUtils.isBlank(custCon.getExpired())
				|| "F".equals(custCon.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(custCon.getType())) {
    		sql += " and a.Type=? ";
    		list.add(custCon.getType());
    	}
    	if(StringUtils.isNotBlank(custCon.getResrCustCode())){
    		sql+=" and a.ResrCustCode = ? ";
    		list.add(custCon.getResrCustCode());
    	}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		}else{
			sql += ") a order by a.Pk";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * CustCon分页信息for bs
	 * 
	 * @param page
	 * @param custCon
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql_bs(Page<Map<String,Object>> page, CustCon custCon,UserContext uc) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from (select a.PK,a.CustCode CustCode,b.Descr,b.Address custAddress,a.ConDate,a.ConMan,c.NameChi ConManDescr, " 
				+"b.DesignMan,d.NameChi DesignManDescr,b.BusinessMan,e.NameChi BusinessManDescr, "  
				+"a.Remarks,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,b.Status,f.NOTE StatusDescr,g.NOTE TypeDescr, "  
				+"isnull(a.conDuration,0)conDuration, a.type, tcr.Name callRecord, tcr.mobileFilePath, tcr.status callRecordStatus, " 
				+"a.NextConDate, a.ConWay, h.NOTE ConWayDescr "
				+"from tCustCon a "   
				+"left join tCustomer b on a.CustCode=b.Code "   
				+"left join tEmployee c on a.ConMan=c.Number "  
				+"left join tEmployee d on b.DesignMan=d.Number "
				+"left join tEmployee e on b.BusinessMan=e.Number " 
				+"left join tXTDM f on b.Status=f.CBM and f.ID='CUSTOMERSTATUS' "   
				+"left join tXTDM g on a.Type=g.CBM and g.ID='CUSTCONTYPE' " 
				+"left join tCallRecord tcr on tcr.PK = a.CallRecordPK "
				+"left join tXTDM h on a.ConWay = h.CBM and h.ID = 'CONWAY' "
				+"where isnull(a.CustCode,'')<>'' and (a.LastUpdatedby="  
				+uc.getCzybh()+" or "+SqlUtil.getCustRight(uc, "b", 0)+" ) ";
		if("1".equals(custCon.getStatistcsMethod())){
			sql+="and 1<>1 ";
		}
    	if (custCon.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(custCon.getPk());
		}
      	if (StringUtils.isNotBlank(custCon.getCustAddress())) {
    		sql += " and b.Address like ? ";
    		list.add("%"+custCon.getCustAddress()+"%");
    	}
      	if (StringUtils.isNotBlank(custCon.getConMan())) {
    		sql += " and a.ConMan=? ";
    		list.add(custCon.getConMan());
    	}
      	if (StringUtils.isNotBlank(custCon.getDesignMan())) {
    		sql += " and b.designMan=? ";
    		list.add(custCon.getDesignMan());
    	}
    	if (StringUtils.isNotBlank(custCon.getBusinessMan())) {
    		sql += " and b.businessMan=? ";
    		list.add(custCon.getBusinessMan());
    	}
    	if (custCon.getDateFrom() != null) {
			sql += " and a.ConDate>=? ";
			list.add(custCon.getDateFrom());
		}
    	if (custCon.getDateTo() != null) {
			sql += " and a.ConDate< dateadd(day,1,?) ";
			list.add(custCon.getDateTo());
		}
    	if (StringUtils.isNotBlank(custCon.getStatus())) {
			sql += " and b.status in  ("+custCon.getStatus()+")";
		}
    	if (StringUtils.isNotBlank(custCon.getConMan())) {
			sql += " and a.ConMan=? ";
			list.add(custCon.getConMan());
		}
    	if (StringUtils.isNotBlank(custCon.getDepartment())) {
			sql += " and c.Department in ("+custCon.getDepartment()+")";
		}
    	if (StringUtils.isBlank(custCon.getExpired())
				|| "F".equals(custCon.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(custCon.getType())) {
    		sql += " and a.Type=? ";
    		list.add(custCon.getType());
    	}
    	if(StringUtils.isNotBlank(custCon.getResrCustCode())){
    		sql+=" and a.ResrCustCode = ? ";
    		list.add(custCon.getResrCustCode());
    	}
    	sql+=" union all ";
    	sql+="select a.PK,a.ResrCustCode CustCode,b.Descr,b.Address custAddress,a.ConDate,a.ConMan,c.NameChi ConManDescr, " 
			+"'' DesignMan,'' DesignManDescr,b.BusinessMan,e.NameChi BusinessManDescr, "
			+"a.Remarks,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,b.Status,'' StatusDescr,g.NOTE TypeDescr, " 
			+"isnull(a.conDuration,0)conDuration, a.type, tcr.Name callRecord, tcr.mobileFilePath, tcr.status callRecordStatus, "
			+"a.NextConDate, a.ConWay, h.NOTE ConWayDescr "
			+"from tCustCon a "  
			+"left join tResrCust b on a.ResrCustCode=b.Code "  
			+"left join tEmployee c on a.ConMan=c.Number "
			+"left join tEmployee e on b.BusinessMan=e.Number " 
			+"left join tDepartment d on e.Department=d.Code "
			+"left join tXTDM g on a.Type=g.CBM and g.ID='CUSTCONTYPE' " 
			+"left join tCallRecord tcr on tcr.PK = a.CallRecordPK "
			+"left join tXTDM h on a.ConWay = h.CBM and h.ID = 'CONWAY' "
			+"where isnull(a.CustCode,'')='' "+SqlUtil.getCustRightByCzy(uc, "b.BusinessMan", "d.Path");
    	if("2".equals(custCon.getStatistcsMethod())){
			sql+="and 1<>1 ";
		}
    	if (StringUtils.isNotBlank(custCon.getCustAddress())) {
    		sql += " and b.Address like ? ";
    		list.add("%"+custCon.getCustAddress()+"%");
    	}
    	if (StringUtils.isNotBlank(custCon.getBusinessMan())) {
    		sql += " and b.BusinessMan=? ";
    		list.add(custCon.getBusinessMan());
    	}
    	if (custCon.getDateFrom() != null) {
			sql += " and a.ConDate>=? ";
			list.add(custCon.getDateFrom());
		}
    	if (custCon.getDateTo() != null) {
			sql += " and a.ConDate< dateadd(day,1,?) ";
			list.add(custCon.getDateTo());
		}
    	if (StringUtils.isNotBlank(custCon.getConMan())) {
			sql += " and a.ConMan=? ";
			list.add(custCon.getConMan());
		}
    	if (StringUtils.isBlank(custCon.getExpired())
				|| "F".equals(custCon.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(custCon.getType())) {
    		sql += " and a.Type=? ";
    		list.add(custCon.getType());
    	}
    	if (StringUtils.isNotBlank(custCon.getDepartment())) {
			sql += " and c.Department in ("+custCon.getDepartment()+")";
		}
    	if (StringUtils.isNotBlank(custCon.getDesignMan())) {
    		sql += " and 1<>1 ";
    	}
    	if (StringUtils.isNotBlank(custCon.getStatus())) {
    		sql += " and 1<>1 ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		}else{
			sql += ") a order by a.Pk";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> findPageBySql_khxx(
			Page<Map<String, Object>> page, CustCon custCon) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from (select a.PK,a.CustCode,b.Descr,b.Address,a.ConDate,a.ConMan,c.NameChi ConManDescr,"
				+ "b.DesignMan,d.NameChi DesignManDescr,b.BusinessMan,e.NameChi BusinessManDescr,"
                + "a.Remarks,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog "
                + "from tCustCon a left outer join tCustomer b on a.CustCode=b.Code and b.Status <> '5' "
                + "left join tEmployee c on a.ConMan=c.Number "
                + "left join tEmployee d on b.DesignMan=d.Number "
                + "left join tEmployee e on b.BusinessMan=e.Number "
                + "where Code = ? ";
        if (StringUtils.isNotBlank(custCon.getCustCode())){
        	list.add(custCon.getCustCode());
        }else{
        	return null;
        }
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		}else{
			sql += ") a ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

}

