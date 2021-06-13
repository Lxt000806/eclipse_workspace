package com.house.home.dao.design;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.entity.design.CustContract;
import com.house.home.entity.design.CustContractFile;
import com.house.home.entity.design.Customer;

@SuppressWarnings("serial")
@Repository
public class CustContractDao extends BaseDao {

	/**
	 * CustContract分页信息
	 * 
	 * @param page
	 * @param custContract
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CustContract custContract) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select a.PK,b.Address,b.Descr,b.DocumentNo,a.ConNo,a.ConDescr,a.PartyAID,a.PartyAName,a.PartyAPhone, "
				+"a.SignDate,a.CustCode,x4.NOTE IsFutureConDescr,x1.NOTE StatusDescr,x2.NOTE ConModeDescr,x3.NOTE ConTypeDescr, "
				+"d.NameChi AppCZYDescr,a.AppDate,e.NameChi ConfirmCZYDescr,a.ConfirmDate,a.ConfirmRemarks,a.Status, "
				+"f.NameChi EndDescr,a.EndDate,isnull(a.EndRemarks,'') EndRemarks,a.PartASignDate, "
				+"a.OriginalDoc,a.EffectDoc,a.PartBDescr,a.PartBRepName,a.FlowId,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog, "
				+"c.LastPrintDate,c.LastPrintCZY,isnull(g.PrintNum,0)PrintNum, " 
				+"case when isnull(a.EffectDoc,'') <> ''  then a.ConDescr +'.pdf' else '' end EffectDocDescr "
				+"from tCustContract a  "
				+"left join tCustomer b on a.CustCode = b.Code "
				+"outer apply ( "
				+"	select top 1 in_b.NameChi LastPrintCZY,in_a.PrintDate LastPrintDate "
				+"	from tCustContractPrintLog in_a  "
				+"	left join tEmployee in_b on in_a.PrintCZY = in_b.Number "
				+"	where a.PK = in_a.ConPK "
				+"	order by PrintDate desc "
				+")c "
				+"left join ( "
				+"	select count(*)PrintNum,ConPK from tCustContractPrintLog in_a group by ConPK "
				+")g on a.PK = g.ConPK "
				+"left join tEmployee d on a.AppCZY = d.Number "
				+"left join tEmployee e on a.ConfirmCZY = e.Number "
				+"left join tEmployee f on a.EndCZY = f.Number "
				+"left join tXTDM x1 on a.Status = x1.CBM and x1.ID='CONTRACTSTAT' "
				+"left join tXTDM x2 on a.ConMode = x2.CBM and x2.ID='CONTRACTMODE' "
				+"left join tXTDM x3 on a.ConType = x3.CBM and x3.ID='CONTRACTTYPE' "
				+"left join tXTDM x4 on a.IsFutureCon = x4.CBM and x4.ID='YESNO' "
				+"where 1=1 ";
		if (StringUtils.isNotBlank(custContract.getCustCode())) {
			sql += " and a.CustCode=? ";
			list.add(custContract.getCustCode());
		}
		
		if (StringUtils.isNotBlank(custContract.getConType())) {
			sql += " and a.ConType=? ";
			list.add(custContract.getConType());
		}
		
		return this.findPageByJdbcTemp(page, sql.toLowerCase(), "a.SignDate desc,a.PK desc", list.toArray());
	}
	
	/**
	 * 获取跳转到创建合同所需要的默认信息（不存在历史合同记录）
	 * 
	 * @param custContract
	 * @return
	 */
	public Map<String,Object> getConInfo(CustContract custContract) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select isnull(ConPhone,Mobile1)partyAphone,isnull(d.cmpnyFullName,'') partBdescr, "
				+"a.code,a.address,a.layout,a.area,isnull(e.nameChi,'') partBrepName,a.designMan, "
				+"a.status,a.Descr partyAname,a.ConId partyAid,case when a.IsInitSign = '1' then '0' else '' end isFutureCon, "
				+"a.isInitSign,rtrim(f.Position)position,g.Type custTypeType,case when g.Type = '2' then '3' else '' end conMode, "
				+"case when g.ContractFeeRepType='1' then f.DesignFee else f.StdDesignFee end designFee,a.contractFee,  "
				+"cast(dbo.fGetEmpNameChi(a.Code,'01') as nvarchar(1000)) businessMan,cast(dbo.fGetEmpNameChi(a.Code,'24') as nvarchar(1000)) againMan,"
				+"a.designStyle,a.constructType,a.planAmount,a.expectMoveIntoDate,f.prePay "
				+"from tCustomer a  "
				+"left join tBuilder b on a.BuilderCode = b.Code "
				+"left join tRegion c on b.RegionCode = c.Code "
				+"left join tCmpCustType d on a.CustType = d.CustType and c.CmpCode = d.CmpCode "
				+"left join tEmployee e on a.DesignMan = e.Number "
				+"left join tCustProfit f on a.Code = f.CustCode "
				+"left join tCusttype g on a.CustType = g.Code "
				+"where 1=1 ";
		if (StringUtils.isNotBlank(custContract.getCustCode())) {
			sql += " and a.Code=? ";
			list.add(custContract.getCustCode());
		}
		List<Map<String, Object>> resultList = this.findBySql(sql, list.toArray());
		if(resultList != null && resultList.size() > 0){
			return resultList.get(0);
		}
		return null;
	}
	
	/**
	 * 获取跳转到创建合同所需要的默认信息（存在历史合同记录）
	 * 
	 * @param custContract
	 * @return
	 */
	public Map<String,Object> getHisConInfo(CustContract custContract) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select a.pk,a.partyAphone,a.partBdescr,a.CustCode code,b.address,b.layout,b.area,a.partBrepName, "
				+"b.designMan,a.status conStatus,b.status,a.partyAname,a.partyAid,a.isFutureCon,b.isInitSign,rtrim(f.Position)position, "
				+"case when g.ContractFeeRepType='1' then f.DesignFee else f.StdDesignFee end designFee, "
				+"g.Type custTypeType,b.contractFee,a.conMode,b.contractDay,b.beginDate,a.concreteAddress,b.Address + h.NOTE +'.pdf' docDescr, "
				+"cast(dbo.fGetEmpNameChi(b.Code,'01') as nvarchar(1000)) businessMan,cast(dbo.fGetEmpNameChi(b.Code,'24') as nvarchar(1000)) againMan,"
				+"b.expectMoveIntoDate,b.designStyle,b.constructType,b.planAmount,f.prePay,a.planeDrawDay,a.fullDrawDay "
				+"from tCustContract a  "
				+"left join tCustomer b on a.CustCode = b.Code  "
				+"left join tEmployee e on b.DesignMan = e.Number  "
				+"left join tCustProfit f on b.Code = f.CustCode  "
				+"left join tCusttype g on b.CustType = g.Code "
				+"left join tXTDM h on a.ConType = h.CBM and h.ID = 'CONTRACTTYPE' "
				+"where 1=1 ";
		if(custContract.getPk() != null){//有pk的，查看/编辑/审核合同用
			sql += " and a.PK = ? ";
			list.add(custContract.getPk());
		}else {//没pk的，创建合同用，取最大pk
			sql += "and a.PK = (select max(PK) from tCustContract where CustCode = ? and ConType = ?) ";
			list.add(custContract.getCustCode());
			list.add(custContract.getConType());
		}
		
		List<Map<String, Object>> resultList = this.findBySql(sql, list.toArray());
		
		if(resultList != null && resultList.size() > 0){
			return resultList.get(0);
		}
		return null;
	}
	
	/**
	 * 创建合同前置条件：未到公司、已到公司、订单状态，不存在已申请、已审核、已签约状态的合同记录
	 * 
	 * @param custContract
	 * @return
	 */
	public List<Map<String,Object>> hasCon(CustContract custContract) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select 1 from tCustContract a  "
					+"left join tCustomer b on a.CustCode = b.Code "
					+"where a.CustCode = ? and a.Status in('2','3','4') ";
		
		list.add(custContract.getCustCode());
		
		if (StringUtils.isNotBlank(custContract.getConType())) {
			sql += " and a.ConType=? ";
			list.add(custContract.getConType());
		}
		
		return this.findBySql(sql, list.toArray());
	}

	/**
	 * 获取合同编号
	 * 
	 * @param custType
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result getConNo(String custType) {
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pGetConNo_forProc(?,?,?)}");
			call.setString(1, custType);
			call.registerOutParameter(2, Types.INTEGER);
			call.registerOutParameter(3, Types.NVARCHAR);
			call.execute();
			result.setCode(String.valueOf(call.getInt(2)));
			result.setInfo(call.getString(3));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
		
	}
	
	/**
	 * 获取合同模板和版本号
	 * 
	 * @param custContract
	 * @return
	 */
	public Map<String,Object> getContractTemp(String custType,String type,String builderCode) {

		String sql = "select * from tCustConTractTemp where CustType=? and Type=? " +
				"and (isnull(BuilderCode,'') = '' or BuilderCode =? ) and Expired='F' and Status = '1' ";
		
		List<Map<String, Object>> resultList = this.findBySql(sql, custType,type,builderCode);
		if(resultList != null && resultList.size() > 0){
			return resultList.get(0);
		}
		return null;
	}
	
	/**
	 * 获取历史合同模板和版本号
	 * 
	 * @param custContract
	 * @return
	 */
	public Map<String,Object> getHisContractTemp(Integer conPk,Integer conTempPk ,String type) {
		List<Object> list = new ArrayList<Object>();
		
		String sql = "select b.* from tCustContractTempMapper a "
				+"left join tCustContractTemp b on a.ConTempPK = b.PK  "
				+"left join tCustContract c on a.ConPK = c.PK "
				+"where a.ConPK = ? ";
		list.add(conPk);
		
		if(conTempPk != null){
			sql += "and a.ConTempPK = ? ";
			list.add(conTempPk);
		}
		
		if(StringUtils.isNotBlank(type)){
			sql += "and b.Type = ? ";
			list.add(type);
		}
		
		List<Map<String, Object>> resultList = this.findBySql(sql, list.toArray());
		if(resultList != null && resultList.size() > 0){
			return resultList.get(0);
		}
		return null;
	}
	
	/**
	 * 获取合同模板文本域
	 * 
	 * @param custContract
	 * @return
	 */
	public List<Map<String,Object>> getContractTempField(Integer ConTempPK) {

		String sql = "select * from tCustContractTempField where ConTempPK = ? order by Dispseq";
		
		return this.findBySql(sql,ConTempPK );
	}
	
	/**
	 * 获取重签表原客户类型
	 * 
	 * @param custContract
	 * @return
	 */
	public Map<String,Object> getOldCustType(String custCode) {

		String sql = "select CustType from tAgainSign where CustCode=? order by NewSignDate desc";
		
		List<Map<String, Object>> resultList = this.findBySql(sql, custCode);
		if(resultList != null && resultList.size() > 0){
			return resultList.get(0);
		}
		return null;
	}
	
	/**
	 * 获取合同模板类型
	 * 
	 * @param custContract
	 * @return
	 */
	public String getConTypeDescr(String type) {

		String sql = "select NOTE from tXTDM where ID = 'CONTRACTTYPE' and CBM = ?  ";
		
		List<Map<String, Object>> resultList = this.findBySql(sql, type);
		if(resultList != null && resultList.size() > 0){
			return resultList.get(0).get("NOTE").toString();
		}
		return "";
	}
	
	/**
	 * 根据flowId查合同
	 * 
	 * @param custContract
	 * @return
	 */
	public Map<String,Object> getCustConByFlowId(String flowId) {

		String sql = "select  PK,ConNo ,isnull(ConDescr ,'')ConDescr,PartyAPhone ,PartyAID ,PartyAName ,SignDate ," +
				"ConcreteAddress ,CustCode ,IsFutureCon ,Status ,ConMode ,ConType ,AppCZY ,AppDate ,ConfirmCZY ,ConfirmDate ,ConfirmRemarks ," +
				"isnull(OriginalDoc,'') OriginalDoc,isnull(EffectDoc,'')EffectDoc ,PartBDescr ,PartBRepName ," +
				"FlowId ,LastUpdate ,LastUpdatedBy ,Expired ,ActionLog,PlaneDrawDay,FullDrawDay " +
				"from tCustContract where FlowId=?  ";
		
		List<Map<String, Object>> resultList = this.findBySql(sql, flowId);
		if(resultList != null && resultList.size() > 0){
			return resultList.get(0);
		}
		return null;
	}
	
	/**
	 * 最近一条记录是否为已申请、已审核、已签约
	 * 
	 * @param custContract
	 * @return
	 */
	public List<Map<String,Object>> latelyCon(CustContract custContract) {

		String sql = "select 1 from tCustContract a " +
				"where a.PK = (select max(in_a.PK) from tCustContract in_a where in_a.CustCode=? and in_a.ConType = ? ) " +
				"and a.Status in('2','3','4')";
		
		return this.findBySql(sql, custContract.getCustCode(),custContract.getConType());
	}
	
	/**
	 * 最近一条已终止记录
	 * 
	 * @param custContract
	 * @return
	 */
	public Map<String,Object> latelySignedCon(CustContract custContract) {

		String sql = "select * from tCustContract a " +
				"where a.PK = (select max(in_a.PK) from tCustContract in_a where in_a.CustCode=? and in_a.Status = '6' and in_a.ConType = ? ) ";
		List<Map<String, Object>> resultList = this.findBySql(sql, custContract.getCustCode(),custContract.getConType());
		if(resultList != null && resultList.size() > 0){
			return resultList.get(0);
		}
		return null;
	}
	
	/**
	 * 是否有草稿状态的合同
	 * 
	 * @param custContract
	 * @return
	 */
	public List<Map<String, Object>> hasDraftCon(CustContract custContract) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select 1 from tCustContract a where a.CustCode = ? and a.Status = '1' ";
		
		list.add(custContract.getCustCode());
		
		if (StringUtils.isNotBlank(custContract.getConType())) {
			sql += " and a.ConType=? ";
			list.add(custContract.getConType());
		}
		
		return this.findBySql(sql, list.toArray());
	}
	
	/**
	 * 合同表达式取值
	 * @param custContractPK
	 * @param key
	 * @return
	 */
	public String getCustContractValue(Integer custContractPK, String key) {

		String sql = "select Value from tCustContractValue where CustContractPK = ? and TheKey = ? ";
		
		List<Map<String, Object>> resultList = this.findBySql(sql, custContractPK, key);
		if(resultList != null && resultList.size() > 0){
			return resultList.get(0).get("Value").toString();
		}
		return "-";
	}
	
	/**
	 * 根据合同pk删除值
	 * @param custContractPk
	 * @return
	 */
	public Long delValueByCustContractPK(Integer custContractPk) {

		String sql = "delete from tCustContractValue where CustContractPK = ?";
		
		return this.executeUpdateBySql(sql, custContractPk);
	}
	
	/**
	 * 根据编码查描述
	 * @param code
	 * @param id
	 * @return
	 */
	public String getXtdmNote(String code,String id){
		String sql=" select NOTE from tXTDM where CBM = ? and ID = ? ";
		
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{code,id});
		
		if (list!=null && list.size()>0){
			 return list.get(0).get("NOTE").toString();
		}
		return "";
	}
	
	/**
	 * 是否包含主材，集成，软装预算
	 * @param layout
	 * @return
	 */
	public Map<String, Object> hasItemPlan(String custCode){
		String sql="select "
		+"(select distinct 1 from tItemPlan where ItemType1 = 'ZC' and IsService = '0' and Qty > 0 and CustCode = ?) hasZC, "
		+"(select distinct 1 from tItemPlan where ItemType1 = 'RZ' and Qty > 0 and CustCode = ?) hasRZ, "
		+"(select distinct 1 from tItemPlan where ItemType1 = 'JC' and Qty > 0 and CustCode = ?) hasJC, "
		+"(select distinct 1 from tBaseItemPlan where Qty > 0 and CustCode = ?) hasJZ, "
		+"(select distinct 1 from tItemPlan where ItemType1 = 'ZC' and IsService = '1' and Qty > 0 and CustCode = ?) hasSev ";
		
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{custCode,custCode,custCode,custCode,custCode});
		
		if (list!=null && list.size()>0){
			 return list.get(0);
		}
		return null;
	}
	
	/**
	 * 获取易签宝收款单位表记录
	 * @param custCode
	 * @return
	 */
	public Map<String, Object> getTaxPayeeESign(CustContract custContract){
		String sql="select e.OrgId,e.SealId from tCustomer a "
			+"left join tBuilder b on a.BuilderCode = b.Code  "
			+"left join tRegion c on b.RegionCode = c.Code "
			+"left join tCmpCustType d on a.CustType = d.CustType and c.CmpCode = d.CmpCode ";
		if("1".equals(custContract.getConType())){
			sql+="left join tTaxPayeeESign e on d.PayeeCode = e.PayeeCode ";
		}else {
			sql+="left join tTaxPayeeESign e on d.DesignPayeeCode = e.PayeeCode ";
		}
		sql+="where a.Code = ? ";
		
		List<Map<String,Object>> list=this.findBySql(sql,custContract.getCustCode());
		
		if (list!=null && list.size()>0){
			 return list.get(0);
		}
		return null;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<CustContractFile> findCrossPageSealFilesByConPk(Integer conPk){
		String hql = "from CustContractFile where isCrossPageSeal='1' and conPk=?";
		return this.find(hql, new Object[] { conPk });
	} 
	
	/**
	 * 设计费标准金额转编码
	 * @param layout
	 * @return
	 */
	public String getPositionCode(String designFee){
		String sql=" select position from tDesignFeeSd where DesignFee = ? ";
		
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{designFee});
		
		if (list!=null && list.size()>0){
			 return list.get(0).get("position").toString();
		}
		return "";
	}
	
	/**
	 * 是否存在审批级别不为空的记录
	 * @param customer
	 * @return
	 */
	public List<Map<String, Object>> hasConfirmLevel(Customer customer) {
		String sql = "select 1 "
					+"from tCustGift a  "
					+"left join tGift b on a.GiftPK = b.PK  "
					+"where a.CustCode = ? and isnull(b.ConfirmLevel,'')<>'' ";
		List<Map<String,Object>> list = this.findBySql(sql, customer.getCode());
		if (list!=null && list.size()>0){
			return list ;
		}
		return null;
	}
	
	/**
	 * 优惠审批状态是否为已审批
	 * @param customer
	 * @return
	 */
	public List<Map<String, Object>> isConfirmedDisc(Customer customer) {
		String sql = "select 1 from ( "
					+"	select max(WfProcInstNo)WfProcInstNo  "
					+"	from tWfCust_CustGiftApp in_a  "
					+"	where in_a.CustCode = ? "
					+")a  "
					+"left join tWfProcInst b on b.No = a.WfProcInstNo "
					+"where b.Status = '2'";
		List<Map<String,Object>> list = this.findBySql(sql, customer.getCode());
		if (list!=null && list.size()>0){
			return list ;
		}
		return null;
	}
	
	/**
	 * 公司信息
	 * @param customer
	 * @return
	 */
	public Map<String, Object> getCmpInfo(Customer customer) {
		String sql = "select d.CmpnyAddress,d.CmpnyFullName CmpnyName,e.CmpnyPhone "
					+"from tCustomer a  "
					+"left join tBuilder b on a.BuilderCode = b.Code "
					+"left join tRegion c on b.RegionCode = c.Code "
					+"left join tCmpCustType d on c.CmpCode = d.CmpCode and d.CustType = a.CustType "
					+"left join tCompany e on d.CmpCode = e.Code "
					+"where a.Code = ? ";
		List<Map<String,Object>> list = this.findBySql(sql, customer.getCode());
		if (list!=null && list.size()>0){
			return list.get(0) ;
		}
		return null;
	}
	
	/**
	 * 获取默认报表
	 * @param customer
	 * @return
	 */
	public Map<String, Object> getDefaultReport(Customer customer) {
		String sql = " select Code DefaultReport "
				 +"from tCustTypePlanReport a "
				 +"left join tCustPlanReport b on a.CustPlanReport = b.Code "
				 +"where a.CustType = ? ";
		List<Map<String,Object>> list = this.findBySql(sql, customer.getCustType());
		if (list!=null && list.size()>0){
			return list.get(0) ;
		}
		return null;
	}
}

