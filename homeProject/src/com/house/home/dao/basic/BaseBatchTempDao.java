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
import com.house.home.entity.basic.BaseBatchTemp;
import com.house.home.entity.basic.BasePlanTemp;
import com.house.home.entity.project.WorkCost;

@SuppressWarnings("serial")
@Repository
public class BaseBatchTempDao extends BaseDao {

	/**
	 * BaseBatchTemp分页信息
	 * 
	 * @param page
	 * @param baseBatchTemp
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, BaseBatchTemp baseBatchTemp) {
		List<Object> list = new ArrayList<Object>();
		String sql =" select * from(select a.No, a.Descr,a.CustType,b.Desc1 CusttypeDescr,a.LastUpdate,a.LastupdatedBy,a.Expired,a.ActionLog  "
			    	+"from tBaseBatchTemp a  "
			    	+"left outer join tCusttype b on b.Code=a.CustType "
			    	+"where 1=1 ";
		if (StringUtils.isNotBlank(baseBatchTemp.getCustType())) {
			sql += " and  (a.CustType is null or a.CustType='' or a.CustType=? ) ";
			list.add(baseBatchTemp.getCustType());
		}
    	if (StringUtils.isNotBlank(baseBatchTemp.getNo())) {
			sql += " and a.No like ? ";
			list.add("%"+baseBatchTemp.getNo()+"%");
		}
    	if (StringUtils.isNotBlank(baseBatchTemp.getDescr())) {
			sql += " and  a.Descr like ? ";
			list.add("%"+baseBatchTemp.getDescr()+"%");
		}
    	if (StringUtils.isNotBlank(baseBatchTemp.getCustType())) {
			sql += " and a.CustType=? ";
			list.add(baseBatchTemp.getCustType());
		}
    	if (baseBatchTemp.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(baseBatchTemp.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(baseBatchTemp.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(baseBatchTemp.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(baseBatchTemp.getExpired()) || "F".equals(baseBatchTemp.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(baseBatchTemp.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(baseBatchTemp.getActionLog());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.LastUpdate desc ";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 模板区域分页信息
	 * 
	 * @param page
	 * @param baseBatchTemp
	 * @return
	 */
	public Page<Map<String,Object>> goAreaJqGrid(Page<Map<String,Object>> page, BaseBatchTemp baseBatchTemp) {
		List<Object> list = new ArrayList<Object>();
		String sql ="select a.No,a.TempNo,a.Descr,a.AreaType,t1.NOTE AreaTypeDescr,a.DispSeq,a.LastUpdate,a.LastUpdatedBy,a.Expired, "
					+"a.ActionLog from tBaseBatchTempArea a  "
					+"left join dbo.tXTDM t1 on a.AreaType=t1.IBM and ID='AreaType' "
					+"where TempNo=? "
					+"order by a.dispseq asc ";
		list.add(baseBatchTemp.getNo());
		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 模板信息
	 * 
	 * @param page
	 * @param baseBatchTemp
	 * @return
	 */
	public Page<Map<String,Object>> goItemJqGrid(Page<Map<String,Object>> page, BaseBatchTemp baseBatchTemp) {
		List<Object> list = new ArrayList<Object>();
		String sql= "select a.pk,a.AreaNo,a.BaseItemCode,bt.Descr BaseItemDescr,a.Qty," 
					+"a.IsOutSet,t1.NOTE IsOutSetDescr,a.DispSeq,a.LastUpdate,a.LastUpdatedBy, "
		            +"a.Expired,a.ActionLog from tBaseBatchTempItem a  "
		            +"left join tBaseItem bt on a.BaseItemCode=bt.code " 
		            +"left join txtdm t1 on a.IsOutSet=t1.CBM and ID='YESNO' "
		            +"where AreaNo=? "
		            +"order by  a.DispSeq";
		list.add(baseBatchTemp.getNo());
		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 名称不能重复
	 * @param baseBatchTemp
	 * @return
	 */
	public List<Map<String, Object>> checkExistTempDescr(BaseBatchTemp baseBatchTemp) {
		List<Object> paraList = new ArrayList<Object>();
		String sql = "select 1 from tBaseBatchTemp where descr=? ";
		paraList.add(baseBatchTemp.getDescr());
		if("M".equals(baseBatchTemp.getM_umState())){
			sql+=" and No<>?";
			paraList.add(baseBatchTemp.getNo());
		}
		List<Map<String, Object>> list = this.findListBySql(sql,paraList.toArray());
		return list;
	}
	/**
	 * 不是1.固定一项，只能有一个区域类型
	 * @param baseBatchTemp
	 * @return
	 */
	public List<Map<String, Object>> checkExistType(BaseBatchTemp baseBatchTemp) {
		List<Object> paraList = new ArrayList<Object>();
		String sql = "select 1 from tBaseBatchTempArea where AreaType=? and TempNo=?";
		paraList.add(baseBatchTemp.getAreaType());
		paraList.add(baseBatchTemp.getNo());
		if("M".equals(baseBatchTemp.getM_umState())){
			sql+=" and No<>?";
			paraList.add(baseBatchTemp.getAreaNo());
		}
		List<Map<String, Object>> list = this.findListBySql(sql,paraList.toArray());
		return list;
	}
	/**
	 * 区域名称不能重复
	 * @param baseBatchTemp
	 * @return
	 */
	public List<Map<String, Object>> checkExistDescr(BaseBatchTemp baseBatchTemp) {
		List<Object> paraList = new ArrayList<Object>();
		String sql = "select 1 from tBaseBatchTempArea where Descr=? and TempNo=?";
		paraList.add(baseBatchTemp.getAreaDescr());
		paraList.add(baseBatchTemp.getNo());
		if("M".equals(baseBatchTemp.getM_umState())){
			sql+=" and No<>?";
			paraList.add(baseBatchTemp.getAreaNo());
		}
		List<Map<String, Object>> list = this.findListBySql(sql,paraList.toArray());
		return list;
	}
	/**
	 * 保存
	 * 
	 * @param baseBatchTemp
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result doSaveProc(BaseBatchTemp baseBatchTemp) {
		Assert.notNull(baseBatchTemp);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pBaseBatchTempArea_forXml(?,?,?,?,?,?,?,?,?)}");
			call.setString(1, baseBatchTemp.getM_umState());
			call.setString(2, baseBatchTemp.getAreaNo());	
			call.setString(3, baseBatchTemp.getNo());
			call.setString(4, baseBatchTemp.getAreaDescr());
			call.setString(5, baseBatchTemp.getAreaType());
			call.setString(6, baseBatchTemp.getLastUpdatedBy());
			call.setString(7, baseBatchTemp.getBaseBatchTempItemJson());
			call.registerOutParameter(8, Types.INTEGER);
			call.registerOutParameter(9, Types.NVARCHAR);
			call.execute();
			result.setCode(String.valueOf(call.getInt(8)));
			result.setInfo(call.getString(9));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	/**
	 * 更新顺序
	 * @param baseBatchTemp
	 * @return
	 */
	public void updateDispSeq(BaseBatchTemp baseBatchTemp) {
		List<Object> paraList = new ArrayList<Object>();
		String sql = "update tBaseBatchTempArea set dispSeq=?,lastUpdate=getdate(),lastUpdatedBy=?,ActionLog='EDIT' where No=? ";
		paraList.add(baseBatchTemp.getDispSeq());
		paraList.add(baseBatchTemp.getLastUpdatedBy());
		paraList.add(baseBatchTemp.getAreaNo());
		this.executeUpdateBySql(sql, paraList.toArray());
	}
	
}

