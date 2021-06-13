package com.house.home.dao.design;

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
import com.house.framework.web.login.UserContext;
import com.house.home.entity.design.CustDoc;
import com.house.home.entity.design.Customer;

@SuppressWarnings("serial")
@Repository
public class CustDocDao extends BaseDao {

	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page,CustDoc custDoc,UserContext uc ) {
		List<Object> list = new ArrayList<Object>();
		
		String sql = " select * from (" +
				" select a.Code,a.DocumentNo,a.Address,a.Status,a.Area,a.InnerArea,a.SetDate,a.SignDate,a.LastUpdate," +
				" e.namechi designManDescr,f.desc1 designManDept2,dpp.SubmitDate ,case when dpp.status='4' then dpp.confirmDate else null end passDate,dpp.lastUpdate orderLastUpdate,x1.note custStatusdescr,x2.note custtypedescr,x3.note layoutdescr,isnull(b1.docNum1,0)docNum1," +
				" isnull(b2.docNum2,0)docNum2,isnull(b3.docNum3,0)docNum3,isNull(x4.note,'待上传') picStatusDescr,isnull(dpp.status,'0' )picStatus, " +
				" dpp.DrawNoChg,dpp.IsFullColorDraw,x5.NOTE DrawNoChgDescr,x6.NOTE IsFullColorDrawDescr,x7.NOTE IsInitSignDescr,"+
				" isnull(b4.docNum4,0)docNum4,isnull(b5.docNum5,0)docNum5,isnull(b6.docNum6,0)docNum6,dpp.confirmremark,h.Descr RegionDescr,i.Desc2 Department1Descr, " +
				" dpp.DrawQty, dpp.Draw3DQty, dpp.DrawNoChgDate, j.ConfirmDate ChgDocConfirmDate " +
				" from tcustomer a " +
				" left join (select count(*) DocNum1,custcode from tCustDoc where DocType2='1' group by custCode)b1 on b1.custCode =a.code "+
				" left join (select count(*) DocNum2,custcode from tCustDoc where DocType2='2' group by custCode)b2 on b2.custCode =a.code "+
				" left join (select count(*) DocNum3,custcode from tCustDoc where DocType2='3' group by custCode)b3 on b3.custCode =a.code "+
				" left join (select count(*) DocNum4,custcode from tCustDoc where DocType2='4' group by custCode)b4 on b4.custCode =a.code "+
				" left join (select count(*) DocNum5,custcode from tCustDoc where DocType2='5' group by custCode)b5 on b5.custCode =a.code "+									
				" left join (select count(*) DocNum6,custcode from tCustDoc where DocType2='6' group by custCode)b6 on b6.custCode =a.code "+									
				" left join tXTDM x1 on x1.cbm=a.status and x1.id='CUSTOMERSTATUS' " +
				" left join tXTDM x2 on x2.cbm=a.CustType and x2.id='CUSTTYPE' " +
				" left join tXTDM x3 on x3.cbm=a.layOut and x3.id='LAYOUT' " + 
				" left join tDesignPicPrg dpp on dpp.custcode=a.code " +
				" left join txtdm x4 on x4.cbm=dpp.status and x4.id='PICPRGSTS' " +
				" left join tXTDM x5 on x5.cbm=dpp.DrawNoChg and x5.id='YESNO' " +
				" left join tXTDM x6 on x6.cbm=dpp.IsFullColorDraw and x6.id='DRAWTYPE' " +
				" left join tXTDM x7 on x7.cbm=a.IsInitSign and x7.id='YESNO' " +
				" left join tEmployee e on e.number = a.designMan " +
				" left join tDepartment2 f on f.code=e.department2 "  +
				" left join tBuilder g on g.Code=a.BuilderCode " +
				" left join tRegion h on h.Code=g.RegionCode " +
				" left join tDepartment1 i on i.code=e.department1 "  +
				" outer apply ( " +
				"     select in_a.ConfirmCZY, " +
				"         in_a.ConfirmDate, in_a.ConfirmRemark " +
				"     from tCustDoc in_a " +
				"     where in_a.PK = ( " +
				"         select max(in1_a.PK) " +
				"         from tCustDoc in1_a " +
				"         where in1_a.CustCode = a.Code " +
				"             and in1_a.Type = '2' " +
				"             and in1_a.Status = '4' " +
				"     ) " +
				" ) j " +
				" where 1=1 and a.expired='F' and a.status in ('1','2','3','4','5') and  "+ SqlUtil.getCustRight(uc, "a", 0);
		if(StringUtils.isNotBlank(custDoc.getAddress())){
			sql+=" and a.address like ?";
			list.add("%"+custDoc.getAddress()+"%");
		}
		if(StringUtils.isNotBlank(custDoc.getStatus())){
			sql += " and isnull(dpp.status,'0') in  " + "('"+custDoc.getStatus().replaceAll(",", "','")+"')";
		}
		if(StringUtils.isNotBlank(custDoc.getCustStatus())){
			sql += " and a.status in  " + "('"+custDoc.getCustStatus().replaceAll(",", "','")+"')";
		}
		if(custDoc.getSignDateFrom()!=null){
			sql+=" and a.SignDate>= ? ";
			list.add(custDoc.getSignDateFrom());
		}
		if(custDoc.getSignDateTo()!=null){
			sql+=" and a.SignDate< ? ";
			list.add(new Timestamp(
					DateUtil.endOfTheDay(custDoc.getSignDateTo()).getTime()));
		}
		if(StringUtils.isNotBlank(custDoc.getCustType())){
			sql += " and a.CustType in  " + "('"+custDoc.getCustType().replaceAll(",", "','")+"')";
			
		}
		if(custDoc.getSubmitDateFrom()!=null){
			sql+=" and dpp.SubmitDate> ? ";
			list.add(custDoc.getSubmitDateFrom());
		}
		if(custDoc.getSubmitDateTo()!=null){
			sql+=" and dpp.SubmitDate< ? ";
			list.add(new Timestamp(
					DateUtil.endOfTheDay(custDoc.getSubmitDateTo()).getTime()));
		}
		if(custDoc.getConfirmPassDateFrom()!=null){
			sql+=" and dpp.ConfirmDate> ? and dpp.status='4' ";
			list.add(custDoc.getConfirmPassDateFrom());
		}
		if(custDoc.getConfirmPassDateTo()!=null){
			sql+=" and dpp.ConfirmDate< ? and dpp.status='4' ";
			list.add(new Timestamp(
					DateUtil.endOfTheDay(custDoc.getConfirmPassDateTo()).getTime()));
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " )a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ")a  ";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> findDocPageBySql(
			Page<Map<String, Object>> page,CustDoc custDoc,String docType1 ) {
		List<Object> list = new ArrayList<Object>();
		
		String sql = "select * from ( select a.*,b.descr typedescr,c.namechi UploadDescr,c.namechi updateDescr,x.note statusDescr" +
				" ,e.address,f.nameChi confirmCzyDescr,x2.note custDocTypeDescr,g.Desc1 CustTypeDescr,h.IsFullColorDraw, h.DrawQty, " +
				" h.Draw3DQty,h.ConfirmRemark confirmRemark2 " +
				" from tCustDoc a " +
				" left join tCustDocType2 b on b.code=a.DocType2 " +
				" left join tEmployee c on c.number =a.UploadCZY " +
				" left join tEmployee d on d.number =a.LastUpdatedBy " +
				" left join temployee f on f.number =a.ConfirmCZY " +
				" left join tXtdm x on x.id='PICPRGSTS' and x.cbm = a.status " +
				" left join tCustomer e on e.code = a.custCode " +
				" left join tXtdm x2 on x2.id='CUSTDOCTYPE' and x2.cbm = a.type "+
				" left join tCustType g on e.CustType=g.Code " +
				" left join tDesignPicPrg h on h.CustCode = a.CustCode "+
				" where 1=1   ";
		
		//资料明细仅显示非过期 add by zb on  20200103
		if (StringUtils.isNotBlank(custDoc.getCustDocExpired())) {
			if ("F".equals(custDoc.getCustDocExpired())) {
				sql += " and a.expired = 'F' ";
			}
		}
		if(StringUtils.isNotBlank(custDoc.getIsPrePlanAreaChg())){
			sql+=" and a.isPrePlanAreaChg = ? ";
			list.add(custDoc.getIsPrePlanAreaChg());
		}
		if(StringUtils.isNotBlank(docType1)){
			sql+=" and b.docType1= ?";
			list.add(docType1);
		}
		if(StringUtils.isNotBlank(custDoc.getCustCode())){
			sql+=" and a.custCode = ? ";
			list.add(custDoc.getCustCode());
		}
		if(StringUtils.isNotBlank(custDoc.getAddress())){
			sql+=" and e.address like ?";
			list.add("%"+custDoc.getAddress()+"%");
		}
		if(StringUtils.isNotBlank(custDoc.getStatus())){
			sql += " and a.status in  " + "('"+custDoc.getStatus().replaceAll(",", "','")+"')";
		}
		if(StringUtils.isNotBlank(custDoc.getType())){
			sql+=" and a.type = ? ";
			list.add(custDoc.getType());
		}
		if(StringUtils.isNotBlank(custDoc.getDocType2())){
			sql+=" and a.docType2 in ('" + custDoc.getDocType2().replace(",", "', '") + "') ";
		}
		if(custDoc.getConfirmDateFrom()!=null){
			sql+=" and a.ConfirmDate>= ? ";
			list.add(custDoc.getConfirmDateFrom());
		}
		if(custDoc.getConfirmDateTo()!=null){
			sql+=" and a.ConfirmDate< ? ";
			list.add(DateUtil.addDateOneDay(custDoc.getConfirmDateTo()));
		}
		if(StringUtils.isNotBlank(custDoc.getIsPrePlanAreaChg())){
			sql+=" and a.isPrePlanAreaChg = ? ";
			list.add(custDoc.getIsPrePlanAreaChg());
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ")a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ")a order by a.UploadDate desc ";
		}
		System.out.println(sql);
		System.out.println(list);
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> findChgPageBySql(
			Page<Map<String, Object>> page,CustDoc custDoc,String docType1,UserContext uc ) {
		List<Object> list = new ArrayList<Object>();
		
		String sql = "select * from ( select a.*,b.descr typedescr,c.namechi UploadDescr,c.namechi updateDescr,x.note statusDescr" +
				" ,e.address,f.nameChi confirmCzyDescr,x2.note custDocTypeDescr from tCustDoc a " +
				" left join tCustDocType2 b on b.code=a.DocType2 " +
				" left join tEmployee c on c.number =a.UploadCZY " +
				" left join tEmployee d on d.number =a.LastUpdatedBy " +
				" left join temployee f on f.number =a.ConfirmCZY " +
				" left join tXtdm x on x.id='PICPRGSTS' and x.cbm = a.status " +
				" left join tCustomer e on e.code = a.custCode " +
				" left join tXtdm x2 on x2.id='CUSTDOCTYPE' and x2.cbm = a.type "+
				" where 1=1 and a.type='2' and " + SqlUtil.getCustRight(uc, "e", 0);
		if(StringUtils.isNotBlank(docType1)){
			sql+=" and b.docType1= ?";
			list.add(docType1);
		}
		if(StringUtils.isNotBlank(custDoc.getLastUpdatedBy())){
			sql+=" and a.UpLoadCzy = ? ";
			list.add(custDoc.getLastUpdatedBy());
		}
		if(StringUtils.isNotBlank(custDoc.getCustCode())){
			sql+=" and a.custCode = ? ";
			list.add(custDoc.getCustCode());
		}
		if(StringUtils.isNotBlank(custDoc.getAddress())){
			sql+=" and e.address like ?";
			list.add("%"+custDoc.getAddress()+"%");
		}
		if(StringUtils.isNotBlank(custDoc.getStatus())){
			sql += " and a.status in  " + "('"+custDoc.getStatus().replaceAll(",", "','")+"')";
		}
		if(StringUtils.isNotBlank(custDoc.getType())){
			sql+=" and a.type = ? ";
			list.add(custDoc.getType());
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ")a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ")a order by a.lastUpdate desc ";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> goDocChgConByJqGrid(
			Page<Map<String, Object>> page,CustDoc custDoc) {
		List<Object> list = new ArrayList<Object>();
		
		String sql = "select * from ( select a.address,b.qqConfirmDate," +
				" g.ConfirmDate MainConfirmDate, " +
				" f.NameChi sceneDesignMan, d.NameChi mainEmployee , case when j.ConfirmDate is null then k.DrawNoChgDate else j.ConfirmDate end ConfirmDate" +
				" from tCustomer a" +
				" left join (" +
				"	select max(ConfirmDate)qqConfirmDate,in_a.CustCode " +
				"	from tPrjProg in_a " +
				"	where in_a.PrjItem='3' group by in_a.CustCode " +
				" )b on b.custCode = a.Code" +
				" left join (" +
				"	select max(a.EmpCode) EmpCode, a.CustCode " +
				"	from tCustStakeholder a where a.Role = '34' group by a.CustCode" +
				" ) c on c.CustCode = a.Code " +
				" left join tEmployee d on d.Number = c.EmpCode" +
				" left join (" +
				"	select max(a.EmpCode) EmpCode, a.CustCode " +
				"	from tCustStakeholder a where a.Role = '63' group by a.CustCode " +
				" ) e on e.CustCode = a.Code " +
				" left join tEmployee f on f.Number = e.EmpCode" +
				" left join tCustItemConfDate g on g.CustCode = a.Code and g.ItemTimeCode = '01'" +
				" left join (select	min(a.PK) pk, a.CustCode" +
				"			from	tCustDoc a" +
				"			where	a.Type = '2' and a.ConfirmDate is not null" +
				"			group by a.CustCode) i on i.CustCode = a.Code" +
				" left join tCustDoc j on j.pk = i.pk" +
				" left join tDesignPicPrg k on k.CustCode = a.Code and k.DrawNoChg = '1'" +
				" where 1=1 and a.expired = 'F' and exists(select 1 from tcustDoc in_a where in_a.custCode = a.Code ) " ;
		if(custDoc.getDateFrom() != null){
			sql+=" and b.qqConfirmDate >= ?";
			list.add(new Timestamp(
					DateUtil.startOfTheDay( custDoc.getDateFrom()).getTime()));
		}
		if(custDoc.getDateTo() != null){
			sql+=" and b.qqConfirmDate <= ?";
			list.add(new Timestamp(
					DateUtil.endOfTheDay(custDoc.getDateTo()).getTime()));
		}
		
		if(StringUtils.isNotBlank(custDoc.getItemConfirmCompleted())){
			if("1".equals(custDoc.getItemConfirmCompleted())){
				sql+=" and g.ConfirmDate is not null";
			} 
			if("0".equals(custDoc.getItemConfirmCompleted())){
				sql+=" and g.ConfirmDate is null";
			}
		}
		sql+=" )a where 1=1 ";
		
		if(StringUtils.isNotBlank(custDoc.getDocChgCompleted())){
			if("1".equals(custDoc.getDocChgCompleted())){
				sql+=" and a.DocConfirmDate is not Null ";
			} 
			if("0".equals(custDoc.getDocChgCompleted())){
				sql+=" and a.DocConfirmDate is null " ;
			}
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += " order by a.qqConfirmDate desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public void doDeleteDoc(String custCode,String docName){
		
		String sql=" delete from tCustDoc where CustCode = ? and DocName= ? " ;
		
		this.executeUpdateBySql(sql, new Object[]{custCode,docName});
	}

	public void doEditDescr(int pk,String descr,String czybh){
		
		String sql=" update tCustDoc set descr = ?,lastUpdate=getdate(),lastUpdatedBy=?,actionLog='EDIT' where pk=? " ;
		
		this.executeUpdateBySql(sql, new Object[]{descr,czybh,pk });
	}
	
	
	public Map<String, Object> getMinDocType2(String docType1){
		String sql=" select  top 1 Code,MaxLen*1024 MaxLen,FileType from  tCustDocType2 where DocType1= ? ";
		List<Map<String, Object>> list=this.findBySql(sql, new Object[]{docType1});
		
		if(list!=null && list.size()>0){
			Map<String, Object> map= list.get(0);
			return map;
		}
		return null;
	}
	
	public Map<String, Object> getDocType2Info(String docType2){
		String sql=" select MaxLen*1024 MaxLen,FileType from tCustDocType2 where code = ? ";
		List<Map<String, Object>> list=this.findBySql(sql, new Object[]{docType2});
		
		if(list!=null && list.size()>0){
			Map<String, Object> map= list.get(0);
			return map;
		}
		
		return null;
	}
	
	public void doSaveDesignPic(String custCode,String lastUpdatedBy){
		String sql=" insert into tDesignPicPrg( CustCode, Status, SubmitDate, SubmitCZY," +
				" ConfirmDate, ConfirmCZY, ConfirmRemark," +
				" LastUpdate, LastUpdatedBy, Expired, ActionLog )" +
				" select ?,'1',null,null,null,null,null,getdate(),?,'F','Add' " +
				" where not exists (select 1 from tDesignPicPrg where CustCode= ? )";
		this.executeUpdateBySql(sql,new Object[]{custCode,lastUpdatedBy,custCode });
	}
	
	public void updateDesignPicSub(String custCode, String status, String submitCZY, String confirmCZY,
			String confirmRemarks, String LastUpdatedby){
		
		String sql=" update tDesignPicPrg set status=?,submitDate=getdate(),submitCzy=?," +
					"lastUpdate=getdate(),lastUpdatedBy = ? where custCode = ?  and (status='1' or status= '3')";
		this.executeUpdateBySql(sql,new Object[]{status,submitCZY,LastUpdatedby,custCode});
	}
	
	public void updateDesignDocStatus(String custCode, String LastUpdatedby,String status){
		
		String sql=" update tCustDoc set status = ? ,lastUpdate = getDate(),lastUpdatedBy = ? " +
				" where custCode = ?  ";
		this.executeUpdateBySql(sql,new Object[]{status,LastUpdatedby,custCode});
	}
	
	public void doRetComfirm(String custCode, String status, String submitCZY, String confirmCZY,
			String confirmRemarks, String LastUpdatedby){
		
		String sql=" update tDesignPicPrg set status=?,confirmRemark= ?,submitDate=null,submitCzy=null," +
					"lastUpdate=getdate(),lastUpdatedBy = ? where custCode = ?  and status='4' ";
		 this.executeUpdateBySql(sql,new Object[]{status,confirmRemarks,LastUpdatedby,custCode});
	}
	
	public void updateDesignPicCon(String custCode, String status, String submitCZY, String confirmCZY,
			String confirmRemarks, String LastUpdatedby, String isFullColorDraw, Integer drawQty, Integer draw3dQty){
		List<Object> list = new ArrayList<Object>();
		
		String sql=" update tDesignPicPrg set status=?,confirmDate=getdate(),confirmCzy=? ,confirmRemark= ?" +
				",lastUpdate=getDate(),lastUpdatedBy=?, DrawQty = ? ,Draw3DQty=? " ;
		list.add(status);
		list.add(confirmCZY);
		list.add(confirmRemarks);
		list.add(LastUpdatedby);
		list.add(drawQty);
		list.add(draw3dQty);
		if(StringUtils.isNotBlank(isFullColorDraw)){
			sql+=",isFullColorDraw=? ";
			list.add(isFullColorDraw);
		}
		sql+="where custcode=? and status= '2'";
		list.add(custCode);
		this.executeUpdateBySql(sql,list.toArray());
	}
	
	public void updateCustDocStatus(String custCode, String LastUpdatedby){
		String sql=" update tCustDoc set status = '2',lastUpdatedby = ? , lastUpdate = getdate()  where custCode =? and type ='2' and status not in ('4','5')" ;
		this.executeUpdateBySql(sql,new Object[]{LastUpdatedby,custCode});
	}
	/**
	 * 是否有管理套内面积权限
	 * 
	 * @param custDoc
	 * @return
	 */
	public List<Map<String, Object>> hasManageAreaRight(String czy) {
		String sql = "select 1 from TSYS_CZYBM_AUTHORITY where CZYBH=? and AUTHORITY_ID=1306";
		List<Map<String, Object>> list = this.findBySql(sql,new Object[] {czy});
		return list;
	}
	/**
	 * 修改套内面积
	 * @param customer
	 */
	public void updateInnerArea(Customer customer){
		String sql=" update tCustomer set InnerArea=?,Lastupdate=getdate(),lastupdatedby=?,actionlog='EDIT' where Code=?" ;
		this.executeUpdateBySql(sql,new Object[]{customer.getInnerArea(),customer.getLastUpdatedBy(),customer.getCode()});
	}
	
	public boolean getIsAllowChg(String custCode){
		String sql=" select 1 from tCustDoc where custCode = ? and type ='2' and status in ('0','1','2','3') ";
		List<Map<String, Object>> list=this.findBySql(sql, new Object[]{custCode});
		if(list!=null && list.size()>0){
			return false;
		}
		return true;
	}
	
	public boolean getIsAllowCommi(String custCode){
		String sql=" select 1 from tCustDoc where custCode = ? and type ='2' and status in ('1','3') ";
		List<Map<String, Object>> list=this.findBySql(sql, new Object[]{custCode});
		if(list!=null && list.size()>0){
			return true;
		}
		return false;
	}
	
	
	/**
	 * 更新项目资料表
	 * 客户表与项目资料表为一对多关系
	 * 
	 * @param custDoc
	 */
	public void updateCustDoc(CustDoc custDoc) {
	    List<Object> parameters = new ArrayList<Object>();
	    
	    String sql = "update tCustDoc set Status = ?, ";
	    parameters.add(custDoc.getStatus());
	    
	    if (StringUtils.isNotBlank(custDoc.getIsPrePlanAreaChg())) {
            sql += "IsPrePlanAreaChg = ?, ";
            parameters.add(custDoc.getIsPrePlanAreaChg());
        }
	    
	    if (StringUtils.isNotBlank(custDoc.getConfirmRemark())) {
            sql += "ConfirmRemark = ?, ";
            parameters.add(custDoc.getConfirmRemark());
        }
	    
        sql += "ConfirmCZY = ?, ConfirmDate = getdate(), " +
               "LastUpdatedby = ?, LastUpdate = getDate(), " +
               "ActionLog = 'EDIT' " +
               "where PK = ? ";
        
        parameters.add(custDoc.getLastUpdatedBy());
        parameters.add(custDoc.getLastUpdatedBy());
        parameters.add(custDoc.getPk());
	    
        executeUpdateBySql(sql, parameters.toArray());
	}
	
	/**
	 * 更新设计图纸上传流程表
	 * 客户表与设计图纸上传流程表为一对一关系
	 * 
	 * @param custDoc
	 */
	public void updateDesignPicPrg(CustDoc custDoc) {
	    
	    List<Object> parameters = new ArrayList<Object>();
	    
        String sql = "update tDesignPicPrg set ";
        
        if (StringUtils.isNotBlank(custDoc.getIsFullColorDraw())) {
            sql += "IsFullColorDraw = ?, ";
            parameters.add(custDoc.getIsFullColorDraw());
        }
        
        if (custDoc.getDrawQty() != null) {
            sql += "DrawQty = ?, ";
            parameters.add(custDoc.getDrawQty());
        }
        
        if (custDoc.getDraw3DQty() != null) {
            sql += "Draw3DQty = ?, ";
            parameters.add(custDoc.getDraw3DQty());
        }
        
        sql += "LastUpdate = getDate(), LastUpdatedBy = ?, ActionLog = 'EDIT' " +
        	   "from tDesignPicPrg a " +
	           "left join tCustDoc b on b.CustCode = a.CustCode " +
	           "where b.PK = ?" ;
        
        parameters.add(custDoc.getLastUpdatedBy());
        parameters.add(custDoc.getPk());
        
        executeUpdateBySql(sql, parameters.toArray());
	}
	
	public void doFinishChg(CustDoc custDoc,String lastUpdatedBy){
		String sql=" update tCustDoc set status = ? ,lastUpdatedby = ? ,lastUpdate = getDate() " +
				" where pk = ? " ;
		this.executeUpdateBySql(sql,new Object[]{custDoc.getStatus(),lastUpdatedBy,custDoc.getPk()});
	}
	/**
	 * 根据客户、文件类型2查询数据
	 * @author	created by zb
	 * @date	2020-1-2--下午5:47:30
	 * @param custCode
	 * @param upDocType2
	 * @return
	 */
	public List<Map<String, Object>> findCustDoc(String custCode, String upDocType2) {
		String sql = "select * from tCustDoc where CustCode=? and DocType2=? order by LastUpdate desc ";
		return this.findBySql(sql, new Object[]{custCode, upDocType2});
	}
	
	public void doDrawNoChg(CustDoc custDoc,String lastUpdatedBy){
		String sql=" update tDesignPicPrg set DrawNoChg = '1' ,DrawNoChgDate=getdate(),lastUpdatedby = ? ,lastUpdate = getdate() " +
				" where CustCode=? " ;
		this.executeUpdateBySql(sql,new Object[]{lastUpdatedBy,custDoc.getCustCode()});
	}
	
	public boolean getIsAllowAdd(String custCode){
		String sql1 = " select 1 from tCustDoc a " +
					  " where a.type is not null and a.CustCode= ? " +
					  " and a.DocType2='6' ";
		List<Map<String, Object>> list1=this.findBySql(sql1, new Object[]{custCode});
		String sql2 = " select 1 from tCustDoc a " +
					  " where a.type is not null and a.CustCode= ? " +
					  " and (a.DocType2 = '3' or a.DocType2='10' ) ";
		List<Map<String, Object>> list2=this.findBySql(sql2, new Object[]{custCode});
		if(list2!=null && list2.size()>0){
			if(list1==null || list1.size()==0){
				return false;
			}
		}
		return true;
	}
	
}
