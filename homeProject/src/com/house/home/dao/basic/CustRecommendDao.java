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
import com.house.framework.commons.utils.SqlUtil;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.design.CustRecommend;

@SuppressWarnings("serial")
@Repository
public class CustRecommendDao extends BaseDao {
	
	public Page<Map<String, Object>> getCustRecommendList(
			Page<Map<String, Object>> page, CustRecommend custRecommend) {
		List<Object> params = new ArrayList<Object>();
		String sql = " select a.PK,rc.Address,rc.Descr CustName,rc.CrtDate RecommendDate,rc.Mobile1 CustPhone, "
				+" case when rcm.CustCode is null or rcm.CustCode='' then a.Status else b.Status end status, "
				+" case when rcm.CustCode is null or rcm.CustCode='' then x1.NOTE else x2.NOTE end statusDescr "
				+" from tCustRecommend a "
				+" left join tResrCust rc on a.ResrCustCode=rc.Code "
				+" left join tResrCustMapper rcm on rcm.ResrCustCode=rc.Code "
				+" left join tCustomer b on rcm.CustCode=b.Code "
				+" left join tXTDM x1 on a.Status=x1.CBM and x1.ID='RECOMMENDSTATUS' "
				+" left join tXTDM x2 on b.Status=x2.CBM and x2.ID='CUSTOMERSTATUS' "
				+" where 1=1 ";
		
		if(StringUtils.isNotEmpty(custRecommend.getRecommender())){
			if(StringUtils.isNotEmpty(custRecommend.getIsSelf())){
				if("1".equals(custRecommend.getIsSelf())){
					sql +=" and a.Recommender = ? ";
					params.add(custRecommend.getRecommender());
				}else{
					sql +=" and a.Recommender <> ? and rc.BusinessMan = ? ";
					params.add(custRecommend.getRecommender());
					params.add(custRecommend.getRecommender());
				}
			}else{
				sql += " and a.Recommender = ? ";
				params.add(custRecommend.getRecommender());
			}
			
		}
		
		if(StringUtils.isNotEmpty(custRecommend.getAddress())){
			sql += " and rc.Address like ? ";
			params.add("%"+custRecommend.getAddress()+"%");
		}
		if(StringUtils.isNotEmpty(custRecommend.getStatus())){
			int statusIndex = Integer.parseInt(custRecommend.getStatus());
			if(statusIndex>3){
				String status=statusIndex-1+"";
				sql += " and b.status = ? ";
				params.add(status);
			}else{
				String status=statusIndex+"";
				sql += " and a.status = ? ";
				params.add(status);
			}
		}
		
		if(StringUtils.isNotEmpty(custRecommend.getPortalAccount())){
			sql+=" and a.Recommender = (select pk from tCustAccount where mobile1= ? ) ";
			params.add(custRecommend.getPortalAccount());
		}
		if(StringUtils.isNotEmpty(custRecommend.getSearchInfo())){
			sql+=" and (rc.Address like ? or rc.Descr like ? ) ";
			params.add("%"+custRecommend.getSearchInfo()+"%");
			params.add("%"+custRecommend.getSearchInfo()+"%");
		}
		sql += "order by rc.CrtDate desc";
		return this.findPageBySql(page, sql, params.toArray());
	}
	
	public Page<Map<String, Object>> goJqGrid(Page<Map<String, Object>> page, CustRecommend custRecommend, UserContext uc){
		List<Object> params = new ArrayList<Object>();
		String sql = "select * from (select a.PK,rc.Address,rc.Descr CustName,rc.CrtDate RecommendDate,rc.Remark Remarks,rc.CustResStat Status,a.ConfirmDate,a.ConfirmRemarks, "
				+" rcm.CustCode,b.Status CustStatus, x1.NOTE StatusDescr,x2.NOTE CustStatusDescr,rc.LastUpdate,rc.LastUpdatedBy,rc.Expired,rc.ActionLog, "
				+" a.RecommendSource,x3.NOTE RecommendSourceDescr,a.Recommender,a.RecommenderType,x4.NOTE RecommenderTypeDescr,rc.BusinessMan Manager,f.NameChi ManagerDescr, "
				+" case a.RecommenderType when '1' then c.NameChi when '2' then d.NameChi else g.NickName end RecommenderDescr, "
				+" case when rc.BusinessMan=? then rc.Mobile1 else left(rc.Mobile1,3)+'****'+right(rc.mobile1,4) end CustPhone  "
				+" from tCustRecommend a "
				+" left join tResrCust rc on a.ResrCustCode = rc.Code "
				+" left join tResrCustMapper rcm on rcm.ResrCustCode = rc.Code"
				+" left join tCustomer b on rcm.CustCode=b.Code "
				+" left join tWorker c on a.Recommender=c.Code "
				+" left join tXTDM x1 on rc.CustResStat=x1.CBM and x1.ID='CUSTRESSTAT' "
				+" left join tXTDM x2 on b.Status=x2.CBM and x2.ID='CUSTOMERSTATUS' "
				+" left join tXTDM x3 on a.RecommendSource=x3.CBM and x3.ID='RECOMMENDSOURCE' "
				+" left join tXTDM x4 on a.RecommenderType=x4.CBM and x4.ID='RECOMMENDERTYPE' "
				+" left join tEmployee d on a.Recommender=d.Number"
				+" left join tCustomer e on a.Recommender=e.Code "
				+" left join tEmployee f on rc.BusinessMan=f.Number "
				+" left join tCustAccount g on a.Recommender=g.pk "
				+" left join tCustMapped h on g.pk=h.CustAccountPK and h.pk=(select Max(PK) from tCustMapped where CustAccountPK = g.Pk) "
				+" left join tCustomer i on h.CustCode = i.Code "
				+" left join tDepartment j on f.Department=j.Code "
				+" where ((a.RecommenderType='1' and exists(select 1 from TSYS_CZYBM_AUTHORITY where CZYBH=? and AUTHORITY_ID='1720')) "
				+" or (a.RecommenderType<>'1' and (rc.BusinessMan is null or rc.BusinessMan='' ) and exists(select 1 from TSYS_CZYBM_AUTHORITY where CZYBH=? and AUTHORITY_ID='1719')) "
				+" or ((rc.BusinessMan is not null or rc.BusinessMan <>'') ";
		sql+= SqlUtil.getCustRightByCzy(uc, "rc.BusinessMan", "j.path")+"))";		
		params.add(uc.getCzybh().trim());
		params.add(uc.getCzybh().trim());
		params.add(uc.getCzybh().trim());
		
		if(StringUtils.isNotEmpty(custRecommend.getAddress())){
			sql += " and rc.Address like ? ";
			params.add("%"+custRecommend.getAddress()+"%");
		}
		
		if(StringUtils.isNotEmpty(custRecommend.getStatus())){
			//待修改status逻辑
			sql += " and rc.CustResStat in " + "('"+custRecommend.getStatus().replace(",", "','" )+ "')";
		}
		
		if(StringUtils.isNotEmpty(custRecommend.getRecommender())){
			sql += " and a.Recommender = ? ";
			params.add(custRecommend.getRecommender());
		}
		
		if(StringUtils.isNotEmpty(custRecommend.getWorkType12())){
			sql += " and c.WorkType12 in " + "('"+custRecommend.getWorkType12().replace(",", "','" )+ "')";
		}
		
		if(StringUtils.isNotEmpty(custRecommend.getRecommendSource())){
			sql += " and a.RecommendSource in " + "('"+custRecommend.getRecommendSource().replace(",", "','" )+ "')";
		}
		
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.LastUpdate desc ";
		}
		
		return this.findPageBySql(page, sql, params.toArray());
	}
	
	public Page<Map<String, Object>> goCountJqGrid(Page<Map<String, Object>> page, CustRecommend custRecommend, UserContext uc){
		List<Object> params = new ArrayList<Object>();
		String sql = "select case a.RecommenderType when '1' then g.NameChi when '2' then h.NameChi else i.NickName end RecommendName, " 
				+" count(a.pk) Count,count(b.Code) ArriveCount,count(c.Code) SetCount,count(d.Code) SignCount, "
				+" count(e.Code) ReturnSetCount, count(f.Code) ReturnSignCount "
				+" from tCustRecommend a "
				+" left join tResrCustMapper rcm on a.ResrCustCode = rcm.ResrCustCode"
				+" left join tCustomer b on b.Code=rcm.CustCode and b.VisitDate is not null "
				+" left join tCustomer c on c.Code=rcm.CustCode and c.SetDate is not null and c.SetDate<>'' and c.EndCode<>'6' "
				+" left join tCustomer d on d.Code=rcm.CustCode and d.SignDate is not null and d.SignDate<>'' and d.EndCode<>'4' "
				+" left join tCustomer e on e.Code=rcm.CustCode and e.EndCode='6' "
				+" left join tCustomer f on f.Code=rcm.CustCode and f.EndCode='4' "
				+" left join tWorker g on a.Recommender=g.Code "
				+" left join tEmployee h on a.Recommender=h.Number "
				+" left join tCustAccount i on a.Recommender=i.pk "
				+" left join tCustMapped j on i.pk=j.CustAccountPK and j.pk=(select Max(PK) from tCustMapped where CustAccountPK = i.Pk)"
				+" left join tCustomer k on j.CustCode = k.Code"
				+" where 1=1 ";
		
		if (custRecommend.getDateFrom() != null) {
			sql += " and a.confirmDate>= ? ";
			params.add(custRecommend.getDateFrom() );
		}
		
		if (custRecommend.getDateTo()!= null) {
			sql += " and a.confirmDate< ? ";
			params.add(DateUtil.addInteger(custRecommend.getDateTo(),
					Calendar.DATE, 1));
		}
		
		if(StringUtils.isNotEmpty(custRecommend.getRecommender())){
			sql += " and a.Recommender = ?";
			params.add(custRecommend.getRecommender());
		}
		
		if(StringUtils.isNotEmpty(custRecommend.getRecommendSource())){
			sql += " and a.RecommendSource in " + "('"+custRecommend.getRecommendSource().replace(",", "','" )+ "')";
		}
		
		sql+=" group by a.RecommenderType,g.NameChi,h.NameChi,a.Recommender,i.NickName";
		
		
		return this.findPageBySql(page, sql, params.toArray());
	}
	
	@SuppressWarnings("unchecked")
	public CustRecommend getCustRecommendByCustPhone(String custPhone) {
		String hql="from CustRecommend WHERE CustPhone=? ";
		List<CustRecommend> list=this.find(hql, new Object[]{custPhone});
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}

	public Map<String, Object> getCustRecommendDetail(int pk){
		String sql="select a.PK, b.Address,b.Descr CustName,b.Mobile1 CustPhone,b.Remark Remarks,b.CrtDate RecommendDate,a.ConfirmDate ,a.ConfirmRemarks,a.status,x1.NOTE StatusDescr, " 
				+" case a.RecommenderType when '2' then c.NameChi when '3' then d.NickName end RecommenderDescr "
				+" from tCustRecommend a " 
				+" left join tResrCust b on a.ResrCustCode=b.Code " 
				+" left join tXTDM x1 on x1.cbm=a.Status and x1.id='RECOMMENDSTATUS' "
				+" left join tEmployee c on a.Recommender=c.Number "
				+" left join tCustAccount d on d.PK=a.Recommender "
				+" where a.Pk=? ";
		List<Map<String, Object>> list=this.findBySql(sql, new Object[]{pk});
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
		
	}
	
	
}
