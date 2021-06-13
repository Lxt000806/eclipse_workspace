package com.house.home.dao.basic;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Timestamp;
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
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.entity.basic.Department1;
import com.house.home.entity.basic.Department2;
import com.house.home.entity.basic.Department3;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.Information;

@SuppressWarnings("serial")
@Repository
public class InformationDao extends BaseDao {

	/**
	 * Information分页信息
	 * 
	 * @param page
	 * @param information
	 * @return
	 */
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Information information) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select t.Number,t.InfoType,t.Status,ir.Status ReadStatus,t.SendCZY,t.SendDate,t.confirmDate,"
				  +"t.InfoTitle,t.InfoText,e.NameChi SendCZYDescr,d1.Desc1 Department1Descr,x.NOTE StatusDescr "
				  +"from tInformation t "
	              +"inner join tInfoRead IR on t.Number = IR.InfoNum "
	              +"left outer join tEmployee e on e.Number=t.SendCZY "
	              +"left outer join tDepartment1 d1 on d1.code=e.Department1 "
	              +"left outer join tXTDM x on x.CBM=ir.Status and x.id='INFOREADSTATUS' "
	              +"where 1=1 ";

			if (StringUtils.isNotBlank(information.getRcvCzy())) {
				sql += " and ir.rcvCzy=? ";
				list.add(information.getRcvCzy());
			}else{
				return null;
			}
	    	if (StringUtils.isNotBlank(information.getNumber())) {
				sql += " and t.Number=? ";
				list.add(information.getNumber());
			}
	    	if (StringUtils.isNotBlank(information.getInfoType())) {
				sql += " and t.InfoType=? ";
				list.add(information.getInfoType());
			}
	    	if (StringUtils.isNotBlank(information.getStatus())) {
				sql += " and t.Status=? ";
				list.add(information.getStatus());
			}
	    	if (StringUtils.isNotBlank(information.getReadStatus())) {
				sql += " and IR.Status=? ";
				list.add(information.getReadStatus());
			}
	    	if (StringUtils.isNotBlank(information.getDepartment1())) {
				sql += " and e.Department1=? ";
				list.add(information.getDepartment1());
			}
	    	if (StringUtils.isNotBlank(information.getSendCZY())) {
				sql += " and t.SendCZY=? ";
				list.add(information.getSendCZY());
			}
	    	if (information.getSendDate() != null) {
				sql += " and t.SendDate=? ";
				list.add(information.getSendDate());
			}
	    	if (information.getDateFrom() != null) {
				sql += " and t.ConfirmDate>=CONVERT(VARCHAR(10),?,120) ";
				list.add(information.getDateFrom());
			}
	    	if (information.getDateTo() != null) {
				sql += " and t.ConfirmDate<CONVERT(VARCHAR(10),dateadd(dd,1,?),120) ";
				list.add(information.getDateTo());
			}
	    	if (StringUtils.isNotBlank(information.getConfirmCZY())) {
				sql += " and t.ConfirmCZY=? ";
				list.add(information.getConfirmCZY());
			}
	    	if (StringUtils.isNotBlank(information.getInfoTitle())) {
				sql += " and t.InfoTitle like ? ";
				list.add("%"+information.getInfoTitle()+"%");
			}
	    	if (StringUtils.isNotBlank(information.getInfoText())) {
				sql += " and t.InfoText like ? ";
				list.add("%"+information.getInfoText()+"%");
			}
	    	if (information.getLastUpdate() != null) {
				sql += " and t.LastUpdate=? ";
				list.add(information.getLastUpdate());
			}
	    	if (StringUtils.isNotBlank(information.getLastUpdatedBy())) {
				sql += " and t.LastUpdatedBy=? ";
				list.add(information.getLastUpdatedBy());
			}
			if (StringUtils.isBlank(information.getExpired()) || "F".equals(information.getExpired())) {
				sql += " and t.Expired='F' ";
			}
	    	if (StringUtils.isNotBlank(information.getActionLog())) {
				sql += " and t.ActionLog=? ";
				list.add(information.getActionLog());
			}
			if (StringUtils.isNotBlank(page.getPageOrderBy())){
				sql += ") a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
			}else{
				sql += ") a order by a.ReadStatus,a.ConfirmDate desc";
			}

			return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> findPageBySqlForInfo(Page<Map<String,Object>> page, Information information) {
		List<Object> list = new ArrayList<Object>();

		String sql ="select a.Number,a.InfoType,t1.NOTE as InfoTypeDesc,a.Status, t2.NOTE as StatusDesc,dbo.f_getInfoCata(a.Number,'1') as InfoCata,dbo.f_getInfoCata(a.Number,'2') as InfoCataDesc,"
                + "d1.Desc1 as Department1,d2.Desc1 as Department2,d3.Desc1 as Department3,a.SendCZY,c.NameChi,a.SendDate,a.ConfirmDate,a.ConfirmCZY,"
                + "a.InfoTitle,a.InfoText,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog from tInformation a "
                + "left outer join tEmployee c on a.SendCZY = c.Number "
                + "left outer join tDepartment1 d1 on c.Department1 = d1.Code "
                + "left outer join tDepartment2 d2 on c.Department2 = d2.Code "
                + "left outer join tDepartment3 d3 on c.Department3 = d3.Code "
                + "left outer join tXTDM t1 on t1.CBM=a.InfoType and t1.ID='INFOTYPE' "
                + "left outer join tXTDM t2 on t2.CBM=a.Status and t2.ID='INFOSTATUS' "
                + "where 1=1 ";
		if (information.getSendDateFrom() != null){
			sql += " and a.SendDate>= ? ";
			list.add(information.getSendDateFrom());
		}
		if (information.getSendDateTo() != null){
			sql += " and a.SendDate<= ? ";
			list.add(information.getSendDateTo());
		}
		if (StringUtils.isNotBlank(information.getSendCZY())){
			sql += " and a.SendCZY=? ";
			list.add(information.getSendCZY());
		}
		if (StringUtils.isNotBlank(information.getInfoTitle())){
			sql += " and a.InfoTitle like ? ";
			list.add("%"+information.getInfoTitle()+"%");
		}
		if (StringUtils.isNotBlank(information.getInfoText())){
			sql += " and a.InfoText like ? ";
			list.add("%"+information.getInfoText()+"%");
		}
		if(StringUtils.isNotBlank(information.getInfoCata())){
			sql += " and exists (select 1 from tCatOfInfo where InfoNum=a.Number and InfoCata in ('"+information.getInfoCata().replace(",", "','")+"'))";
		}
		if (StringUtils.isNotBlank(information.getInfoType())){
			sql += " and a.InfoType=? ";
			list.add(information.getInfoType());
		}
		if (StringUtils.isNotBlank(information.getSendCZY())){
			sql += " and a.SendCZY=? ";
			list.add(information.getSendCZY());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += "order by a.LastUpdate desc,a.Number desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	public Long getCount(Information information) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select count(1) num from tInformation t"
                +" inner join tInfoRead IR on t.Number = IR.InfoNum"
                +" where 1=1 ";
		if (StringUtils.isNotBlank(information.getRcvCzy())){
			sql += " and ir.RcvCZY=? ";
			list.add(information.getRcvCzy());
		}else{
			return 0l;
		}
		if (StringUtils.isNotBlank(information.getInfoType())){
			sql += " and t.InfoType=? ";
			list.add(information.getInfoType());
		}
		if (StringUtils.isNotBlank(information.getStatus())){
			sql += " and t.Status=? ";
			list.add(information.getStatus());
		}
		if (StringUtils.isNotBlank(information.getReadStatus())){
			sql += " and ir.Status=? ";
			list.add(information.getReadStatus());
		}
		List<Map<String,Object>> retList = this.findBySql(sql, list.toArray());
		if (retList!=null && retList.size()>0){
			return Long.valueOf(String.valueOf(retList.get(0).get("num")));
		}
		return 0l;
	}

	public Map<String, Object> getByNumber(String number) {
		String sql = "select a.Number,a.InfoType,a.Status,a.SendCZY,b.NameChi SendCzyDescr,a.SendDate,"
				+"a.InfoTitle,a.InfoText,(select CONVERT(VARCHAR(100), InfoCata)+',' from tCatOfInfo where InfoNum=? FOR XML path('')) infoCata from tInformation a "
				+"left join tEmployee b on a.SendCZY=b.Number " 
				+"where a.number=? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{number,number});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	public Page<Map<String,Object>> findPageBySqlForBS(Page<Map<String,Object>> page, Information information) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select a.Number,a.InfoType,t1.NOTE as InfoTypeDesc,a.Status, t2.NOTE as StatusDesc,dbo.f_getInfoCata(a.Number,'1') as InfoCata,dbo.f_getInfoCata(a.Number,'2') as InfoCataDesc, "
				+" d1.Desc1 as Department1,d2.Desc1 as Department2,d3.Desc1 as Department3,a.SendCZY,c.NameChi,a.SendDate,a.ConfirmDate,a.ConfirmCZY, "
				+" a.InfoTitle,a.InfoText,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog from tInformation a "
                +" left outer join tEmployee c on a.SendCZY = c.Number "
                +" left outer join tDepartment1 d1 on c.Department1 = d1.Code " 
                +" left outer join tDepartment2 d2 on c.Department2 = d2.Code "
                +" left outer join tDepartment3 d3 on c.Department3 = d3.Code "
                +" left outer join tXTDM t1 on t1.CBM=a.InfoType and t1.ID='INFOTYPE' "
                +" left outer join tXTDM t2 on t2.CBM=a.Status and t2.ID='INFOSTATUS' "
                +" where 1=1  ";

		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ") a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ") a order by a.ReadStatus,a.ConfirmDate desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> findInfoReadPageBySql(Page<Map<String,Object>> page, Information information) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select a.PK,a.InfoNum,a.RcvCZY,c.NameChi,a.Status,t1.NOTE as StatusDesc,d1.Desc1 as Department1,d2.Desc1 as Department2,d3.Desc1 as Department3, "
		          + "a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog from tInfoRead a "
		          + "left outer join tEmployee c on a.RcvCZY = c.Number "
		          + "left outer join tDepartment1 d1 on c.Department1 = d1.Code "
		          + "left outer join tDepartment2 d2 on c.Department2 = d2.Code "
		          + "left outer join tDepartment3 d3 on c.Department3 = d3.Code "
		          + "left outer join tXTDM t1 on t1.CBM=a.Status and t1.ID='INFOREADSTATUS' "
		          + "where a.InfoNum = ? ";
		list.add(information.getNumber());
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.LastUpdate asc,a.PK asc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> findInfoAttachPageBySql(Page<Map<String,Object>> page, Information information){
		List<Object> list = new ArrayList<Object>();

		String sql = "select PK,InfoNum,FileName,FtpPath,DispSeq,LastUpdate,LastUpdatedBy,Expired,ActionLog from  tInfoAttach "
				+" where InfoNum=? "
				+" order by DispSeq ";
		list.add(information.getNumber());
		return this.findPageBySql(page, sql, list.toArray());
	}
	 
	public List<Map<String,Object>> getChildrenNodes(String higherDept){
		String sql="(select  code,'2' Type,Desc2 NameChi,DispSeq,HigherDep,'true' isParent,b.Department1,b.Department2,b.Department3 from tDepartment a "
				+" 	left join ( "
				+" 		select code Department1,null Department2,null Department3,Department from  tDepartment1 "
				+"		union all "
				+"		select Department1 Department1,code Department2,null Department3,Department from  tDepartment2 "
				+"		union all "
				+"		select in_b.Department1 Department1,in_a.Department2 department2,in_a.code Department3,in_a.Department from  tDepartment3 in_a "
				+"		left join tDepartment2 in_b on in_a.Department2=in_b.Code "
				+"	)b on a.Code=b.Department"
				+" where Expired='F' and HigherDep= ?) "
				+" union all "
				+" (select b.EmpCode code,'1' Type,a.NameChi,'0' DispSeq,b.Department HigherDep,'false' isParent,a.Department1,a.Department2,a.Department3 from tEmployee a "
				+" left join tDepEmp b on a.Number=b.EmpCode "
				+" where b.Expired='F' and b.Department=? )";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{higherDept,higherDept});
		return list;
	}
	
	public List<Map<String,Object>> getDepEmpNodes(){
		String sql="(select  code id,'2' Type,Desc2 name,DispSeq,HigherDep pid,'true' isParent,b.Department1,b.Department2,b.Department3,'' rcvCZY  from tDepartment a "
				+" left join ( "
				+"select code Department1,'' Department2,'' Department3,Department from  tDepartment1 "  
				+"union all "  
				+"select Department1 Department1,code Department2,'' Department3,Department from  tDepartment2 "  
				+"union all "  
				+"select in_b.Department1 Department1,in_a.Department2 department2,in_a.code Department3,in_a.Department from  tDepartment3 in_a "  
				+"left join tDepartment2 in_b on in_a.Department2=in_b.Code "  
				+")b on a.Code=b.Department " 
				+"where Expired='F' ) "  
				+"union all  " 
				+"(select a.Number id,'1' Type,a.NameChi name,'0' DispSeq,a.Department pid,'false' isParent,a.Department1,a.Department2,a.Department3,a.Number rcvCZY from tEmployee a "  
				+"where a.Expired='F')";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{});
		return list;
	}
	
	public List<Map<String,Object>> getReceiveNodes(String infoNum){
		String sql="(select distinct code id,'2' Type,Desc2 name,DispSeq,HigherDep pid,'true' isParent,b.Department1,b.Department2,b.Department3,'' rcvCZY from tDepartment a "
				+" left join ( "
				+" 		select code Department1,'' Department2,'' Department3,Department from  tDepartment1 "
				+" 		union all "
				+" 		select Department1 Department1,code Department2,'' Department3,Department from  tDepartment2 " 
				+" 		union all "
				+"  	select in_b.Department1 Department1,in_a.Department2 department2,in_a.code Department3,in_a.Department from  tDepartment3 in_a "
				+"  	left join tDepartment2 in_b on in_a.Department2=in_b.Code "
				+"  )b on a.Code=b.Department "
				+"  inner join tInfoRcvCZY c on c.Department1=b.Department1 and c.Department2=b.Department2 and c.Department3=b.Department3 " 
				+"  where a.Expired='F' and c.InfoNum=?) "
				+"  union all "
				+"  (select a.Number id,'1' Type,a.NameChi name,'0' DispSeq,a.Department pid,'false' isParent,a.Department1,a.Department2,a.Department3,a.Number rcvCZY from tEmployee a "
				+"  inner join tInfoRcvCZY b on a.Number=b.RcvCZY "
				+"  where a.Expired='F' and b.InfoNum= ?) ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{infoNum,infoNum});
		return list;
	}
	
	public Result doSave(Information information){
		Assert.notNull(information);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pGgxxgl_forXml(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, information.getM_umState());
			call.setString(2, information.getNumber());
			call.setString(3, information.getStatus());
			call.setString(4, information.getInfoType());
			call.setString(5, information.getSendCZY());
			call.setString(6, information.getConfirmCZY());
			call.setString(7, information.getInfoCata());
			call.setString(8, information.getInfoTitle());
			call.setString(9, information.getInfoText());
			call.setString(10, information.getLastUpdatedBy());
			call.setString(11, information.getExpired());
			call.setString(12, information.getInfoAttach());
			call.setString(13, information.getRcvCzys());
			call.registerOutParameter(14, Types.INTEGER);
			call.registerOutParameter(15, Types.NVARCHAR);
			call.execute();
			result.setCode(String.valueOf(call.getInt(14)));
			result.setInfo(call.getString(15));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	
	public String getNeedSendInfomation(String czybm){
		String sql=" select top 1 t.Number,t.InfoType from tInformation t "
             +"left outer join tInfoRead IR on t.Number = IR.InfoNum "
             +"where (t.InfoType = 1 and t.status=3) "
//             +"or (t.InfoType = 2 and t.status=3 and IR.RcvCZY=?)) "
             +" and not exists(select 1 from tInfoRead tt "
             +"where tt.InfoNum=t.Number and tt.Status=1 and tt.RcvCZY=?) "
             +"order by t.InfoType,t.ConfirmDate desc";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{czybm});
		if(list.size()>0){
			return list.get(0).get("Number").toString();
		}
		return "";
	}
}

