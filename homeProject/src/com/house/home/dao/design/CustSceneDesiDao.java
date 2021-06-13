package com.house.home.dao.design;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.design.Customer;

@SuppressWarnings("serial")
@Repository
public class CustSceneDesiDao extends BaseDao{
	
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();

		String sql ="select * from( select a.code,a.address,c.desc1 CustTypeDescr,a.status,x.note statusDescr,a.SignDate,e.namechi custSceneDesiDescr," +
				" e2.NameCHi designerDescr,f.descr regionDescr,d2.desc1 Designdepartment2,b.lastUpdate,b.lastUpdatedBy,b.expired,b.actionLog, d3.desc1 ProManDepartment2, " +
				" a.ConfirmBegin,a.ContractFee " +
				" from tcustomer a " +
				" left join tCustStakeholder b on b.CustCode=a.Code and b.Role='63' " +
				" left join temployee e on e.number =b.empCode " +
				" left join tEmployee e2 on e2.number =a.DesignMan " +
				" left join tDepartment2 d2 on d2.code=e2.department2 " +
				" left join tCustType c on c.code=a.custType " +
				" left join tXTDM x on x.cbm=a.Status and x.id='CUSTOMERSTATUS' " +
				" left join tEmployee e3 on e3.Number = a.ProjectMan " +
				" left join tDepartment2 d3 on d3.code=e3.department2 " +
				" left join tBuilder d on d.code=a.builderCode " +
				" left join tRegion f on f.code=d.regionCode " +
				" where 1=1 and a.expired='F'";
		if(StringUtils.isNotBlank(customer.getAddress())){
			sql+=" and a.address like ? ";
			list.add("%"+customer.getAddress()+"%");
		}
		if(StringUtils.isNotBlank(customer.getArrangeStatus())){
			if("1".equals(customer.getArrangeStatus())){
				sql+=" and (b.empCode <> '' or b.empCode is not null) ";
			}else if("2".equals(customer.getArrangeStatus())){//待安排
				sql+=" and (b.empCode='' or b.empCode is null) ";
			}
		}
		if(StringUtils.isNotBlank(customer.getIsSchemeDesigner())){
			if("1".equals(customer.getIsSchemeDesigner())){
				sql+=" and e2.isSchemeDesigner ='1' and  e2.isSchemeDesigner is not null ";
			}
		}
		if(StringUtils.isNotBlank(customer.getCustSceneDesi())){
			sql+=" and b.empcode=? ";
			list.add(customer.getCustSceneDesi());
		}
		if(customer.getDateFrom()!=null){
			sql+=" and b.lastUpdate>=? ";
			list.add(customer.getDateFrom());
		}
		if(customer.getDateTo()!=null){
			sql+=" and b.lastUpdate<DATEADD(d,1,?)";
			list.add(customer.getDateTo());
		}
		if(customer.getSignDateFrom()!=null){
			sql+=" and a.signDate>=? ";
			list.add(customer.getSignDateFrom());
		}
		if(customer.getSignDateTo()!=null){
			sql+=" and a.signDate<DATEADD(d,1,?)";
			list.add(customer.getSignDateTo());
		}if(StringUtils.isNotBlank(customer.getCustType())){
			sql += " and a.custType in " + "('"+customer.getCustType().replace(",", "','" )+ "')";
		}
		if(StringUtils.isNotBlank(customer.getStatus())){
			sql += " and a.Status in " + "('"+customer.getStatus().replace(",", "','" )+ "')";
		}
		if(StringUtils.isNotBlank(customer.getDesignMan())){
			sql+=" and a.designMan=? ";
			list.add(customer.getDesignMan());
		}
		if (StringUtils.isNotBlank(customer.getDepartment2())) {/*工程部搜索条件*/
			sql += " and e3.department2 in " + "('"+customer.getDepartment2().replaceAll(",", "','")+"')";
		}
		if(customer.getBeginDateFrom()!=null){/*开工时间*/
			sql += " and a.ConfirmBegin>= ? ";
			list.add(customer.getBeginDateFrom());
		}
		if(customer.getBeginDateTo()!=null){
			sql += " and a.ConfirmBegin<= ? ";
			list.add(customer.getBeginDateTo());
		}
		if(StringUtils.isNotBlank(customer.getRegion())){
			sql+=" and d.RegionCode = ? ";
			list.add(customer.getRegion());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " ) a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += " ) a order by a.lastupdate desc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public String getDesignerCode(String code){
		String sql=" select EmpCode from tCustStakeholder where custCode= ? and Expired ='F' and role='00' ";
		
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{code});
		
		if (list!=null && list.size()>0){
			 return list.get(0).get("EmpCode").toString();
		}
		return "";
	}
	
	public String getSceneDesignerCode(String code){
		String sql=" select EmpCode from tCustStakeholder where custCode= ? and Expired ='F' and role='63' ";
		
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{code});
		
		if (list!=null && list.size()>0){
			 return list.get(0).get("EmpCode").toString();
		}
		return "";
	}
	
	public void doUpdateSceneDesi(String code,String custSceneDesi,String lastUpdatedBy,String oldEmpCode) {
		String sql = "update tCustStakeholder set empCode=?,actionLog='EDIT',lastUpdatedBy=?,lastUpdate=getdate() where custCode=? and role='63' " ;
		this.executeUpdateBySql(sql, new Object[]{custSceneDesi,lastUpdatedBy,code});
		
		String sqlHis=" insert into tCustStakeholderHis(OperType, Role, OldRole, EmpCode, OldEmpCode, CustCode, LastUpdate,LastUpdatedBy, Expired, ActionLog) " +
				" values('2','63','63',?,?,?, getdate(),?,'F','ADD')";
		this.executeUpdateBySql(sqlHis, new Object[]{custSceneDesi,oldEmpCode,code,lastUpdatedBy});
	}
	
	public void doSaveSceneDesi(String code,String custSceneDesi,String lastUpdatedBy ) {
		String sql = "insert into tCustStakeholder(Role, EmpCode, CustCode, LastUpdate,LastUpdatedBy, Expired, ActionLog) " +
				" values('63',?,?,getdate(),?,'F','ADD')";
		this.executeUpdateBySql(sql, new Object[]{custSceneDesi,code,lastUpdatedBy});
	
		String sqlHis=" insert into tCustStakeholderHis(OperType, Role, OldRole, EmpCode, OldEmpCode, CustCode, LastUpdate,LastUpdatedBy, Expired, ActionLog) " +
				" values ('1','63',null,?,null,?,getDate(),?,'F','ADD')";
		this.executeUpdateBySql(sqlHis, new Object[]{custSceneDesi,code,lastUpdatedBy});
		
	}
	
	public void doDeleteSceneDesi(String code,String lastUpdatedBy,String oldEmpCode) {
		String sql = "delete from tCustStakeholder where custCode=? and role='63' and empCode = ?  ";
		this.executeUpdateBySql(sql, new Object[]{code,oldEmpCode});
		
		String sqlHis=" insert into tCustStakeholderHis(OperType, Role, OldRole, EmpCode, OldEmpCode, CustCode, LastUpdate,LastUpdatedBy, Expired, ActionLog) " +
				"values('3',null,'63',null,?,?,getDate(),?,'F','ADD')";
		this.executeUpdateBySql(sqlHis, new Object[]{oldEmpCode,code,lastUpdatedBy});
		
	}
	
	public String getIsAddCustType(){
		String sql=" select code from tcustType where IsAddAllInfo='1' and expired='F' ";
		
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{});
		if (list!=null && list.size()>0){
			String custList="";
			for(int i=0;i<list.size();i++){
				custList+=list.get(i).get("code")+",";
			}
			return custList.substring(0,custList.length()-1);
		}
		return "";
	}
	
	public Map<String, Object> getSceneDesiDepartment() {
		String sql = "select  Department1,Department2 from  tRoll  a where a.Code='63' ";	
		List<Map<String, Object>> list =  this.findBySql(sql, new Object[]{});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

}
