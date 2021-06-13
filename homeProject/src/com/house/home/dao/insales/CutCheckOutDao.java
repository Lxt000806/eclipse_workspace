package com.house.home.dao.insales;

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
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.commons.utils.SqlUtil;
import com.house.home.entity.basic.LongFeeRule;
import com.house.home.entity.insales.BaseItem;
import com.house.home.entity.insales.CutCheckOut;

@SuppressWarnings("serial")
@Repository
public class CutCheckOutDao extends BaseDao {

	/**
	 * CutCheckOut分页信息
	 * 
	 * @param page
	 * @param cutCheckOut
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CutCheckOut cutCheckOut) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (" 
				+"select a.No,a.Status,a.CrtCZY,a.CrtDate,a.CompleteDate,a.SubmitDate, "
				+"a.LastUpdate,a.LastUpdatedBy,a.ActionLog,a.Expired,b.NOTE StatusDescr,c.NameChi CrtCzyDescr,a.Remarks "
				+"from tCutCheckOut a  "
				+"left join tXTDM b on a.Status=b.CBM and b.ID='CUTCHECKOUTSTAT' "
				+"left join tEmployee c on a.CrtCZY=c.Number " 
				+"where 1=1 ";

    	if (StringUtils.isNotBlank(cutCheckOut.getNo())) {
			sql += " and a.No like ? ";
			list.add("%"+cutCheckOut.getNo()+"%");
		}
    	if (cutCheckOut.getCrtDateFrom()!= null){
			sql += " and a.CrtDate>= ? ";
			list.add(cutCheckOut.getCrtDateFrom());
		}
		if (cutCheckOut.getCrtDateTo() != null){
			sql += " and a.CrtDate<= ? ";
			list.add(DateUtil.endOfTheDay(cutCheckOut.getCrtDateTo()));
		}
		if (cutCheckOut.getCompleteDateFrom()!= null){
			sql += " and a.CompleteDate>= ? ";
			list.add(cutCheckOut.getCompleteDateFrom());
		}
		if (cutCheckOut.getCompleteDateTo() != null){
			sql += " and a.CompleteDate<= ? ";
			list.add(DateUtil.endOfTheDay(cutCheckOut.getCompleteDateTo()));
		}
    	if (StringUtils.isNotBlank(cutCheckOut.getStatus())) {
			sql += " and a.Status in ("+SqlUtil.resetStatus(cutCheckOut.getStatus())+")";
		}
    	if (StringUtils.isNotBlank(cutCheckOut.getIano())) {
			sql += " and exists(select 1 from tCutCheckOutDetail in_a where a.No=in_a.No and in_a.IANO like ? ) ";
			list.add("%"+cutCheckOut.getIano()+"%");
		}
    	if (StringUtils.isNotBlank(cutCheckOut.getAddress())) {
			sql += " and exists(select 1 from tCutCheckOutDetail in_a " 
				+"left join tItemApp in_b on in_a.IANo=in_b.No " 
				+"left join tCustomer in_c on in_b.CustCode=in_c.Code " 
				+"where a.No=in_a.No and in_c.Address like ? ) ";
			list.add("%"+cutCheckOut.getAddress()+"%");
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.LastUpdate desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * 新增明细列表
	 * 
	 * @param page
	 * @param cutCheckOut
	 * @return
	 */
	public Page<Map<String,Object>> goAddDetailJqGrid(Page<Map<String,Object>> page, CutCheckOut cutCheckOut) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (" 
				+"select g.Address,a.No IANO,d.Descr ItemDescr,c.ItemCode,e.Descr FixAreaDescr, "
				+"b.FixAreaPK,isnull(b.Qty,0) Qty,isnull(b.Qty,0) IAQty,c.CutType,f.NOTE CutTypeDescr,f.NOTE OldCutTypeDescr, "
				+"isnull(h.FactCutFee,0) CutFee,c.Remark ItemReqRemarks,a.ConfirmDate,b.PK RefPk,isnull(b.Qty,0)*isnull(h.FactCutFee,0) Total, "
				+"'0' IsComplete,'否' IsCompleteDescr,b.Remarks ItemAppRemarks,g.Address+' - '+a.No OrderName,d.Size,h.AllowModify "
				+"from tItemApp a  "
				+"left join tItemAppDetail b on a.No=b.No "
				+"left join tItemReq c on b.ReqPK=c.PK  "
				+"left join tItem d on c.ItemCode=d.Code "
				+"left join tFixArea e on c.FixAreaPK=e.PK "
				+"left join tXTDM f on c.CutType=f.CBM and f.ID='CUTTYPE' "
				+"left join tCustomer g on a.CustCode=g.Code "
				+"left join tCutFeeSet h on d.Size=h.Size and h.CutType=c.CutType "
				+"left join tItemType2 i on d.ItemType2=i.Code "
				+"where a.Status='CONFIRMED' and a.ItemType1='ZC' and i.ItemType12='11' " 
				+"and not exists(select 1 from tCutCheckOutDetail in_a where in_a.RefPK=b.PK )";

    	if (StringUtils.isNotBlank(cutCheckOut.getIano())) {
			sql += " and a.No like ? ";
			list.add("%"+cutCheckOut.getIano()+"%");
		}
    	if (cutCheckOut.getConfirmDateFrom()!= null){
			sql += " and a.ConfirmDate>= ? ";
			list.add(cutCheckOut.getConfirmDateFrom());
		}
		if (cutCheckOut.getConfirmDateTo() != null){
			sql += " and a.ConfirmDate<= ? ";
			list.add(DateUtil.endOfTheDay(cutCheckOut.getConfirmDateTo()));
		}
    	if (StringUtils.isNotBlank(cutCheckOut.getSendType())) {
			sql += " and a.SendType=? ";
			list.add(cutCheckOut.getSendType());
		}
    	if (StringUtils.isNotBlank(cutCheckOut.getRemarks())) {
			sql += " and b.Remarks like ? ";
			list.add("%"+cutCheckOut.getRemarks()+"%");
		}
    	if(StringUtils.isNotBlank(cutCheckOut.getIsCut())){
    		if ("1".equals(cutCheckOut.getIsCut())) {
    			sql += " and isnull(c.CutType,'')<>'' ";
    		}else if("0".equals(cutCheckOut.getIsCut())){
    			sql += " and isnull(c.CutType,'')='' ";
    		}
    	}
    	if (StringUtils.isNotBlank(cutCheckOut.getRefpks())) {
			sql += " and b.Pk not in ("+SqlUtil.resetIntStatus(cutCheckOut.getRefpks())+")";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.OrderName";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 明细列表
	 * 
	 * @param page
	 * @param cutCheckOut
	 * @return
	 */
	public Page<Map<String,Object>> goDetailJqGrid(Page<Map<String,Object>> page, CutCheckOut cutCheckOut) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select a.IANo,g.Address+' - '+a.IANo OrderName,g.Address,h.Descr ItemDescr, "
					+"d.Descr FixAreaDescr,a.FixAreaPK,a.ItemCode,a.Qty,a.CutType,a.CutFee, "
					+"e.NOTE CutTypeDescr,a.Qty*a.CutFee Total,a.Remarks,e2.NOTE OldCutTypeDescr, "
					+"c.Remark ItemReqRemarks,a.RefPK,h.Size,j.AllowModify,a.IsComplete,i.NOTE IsCompleteDescr, "
					+"a.LastUpdate,a.LastUpdatedBy,a.ActionLog,a.Expired,a.PK,b.Qty IAQty,a.CompleteDate,l.NOTE StatusDescr, "
					+"k.SubmitDate,k.Remarks OutRemarks,k.No "
					+"from tCutCheckOutDetail a  "
					+"left join tItemAppDetail b on a.RefPK=b.PK " 
					+"left join tItemReq c on b.ReqPK=c.PK "
					+"left join tFixArea d on b.FixAreaPK=d.PK "
					+"left join tXTDM e on a.CutType=e.CBM and e.ID='CUTTYPE' "
					+"left join tXTDM e2 on c.CutType=e2.CBM and e2.ID='CUTTYPE' "
					+"left join tItemApp f on a.IANo=f.No "
					+"left join tCustomer g on f.CustCode=g.Code "
					+"left join tItem h on a.ItemCode=h.Code "
					+"left join tXTDM i on a.IsComplete=i.CBM and i.ID='YESNO' "
					+"left join tCutFeeSet j on a.CutType=j.CutType and h.Size=j.Size "
					+"inner join tCutCheckOut k on a.No=k.No "
					+"left join tXTDM l on k.Status=l.CBM and l.ID='CUTCHECKOUTSTAT' "
					+"where 1=1  " ;
		if(StringUtils.isNotBlank(cutCheckOut.getNo())){
			sql+=" and a.No=? ";
			list.add(cutCheckOut.getNo());
		}
		if(StringUtils.isNotBlank(cutCheckOut.getIsComplete())){
			sql+=" and a.IsComplete=? ";
			list.add(cutCheckOut.getIsComplete());
		}
//		if(StringUtils.isNotBlank(cutCheckOut.getIano())){
//			sql+=" and a.IANo=? ";
//			list.add(cutCheckOut.getIano());
//		}
		if(StringUtils.isNotBlank(cutCheckOut.getAddress())){
			sql+=" and g.Address like ? ";
			list.add("%"+cutCheckOut.getAddress()+"%");
		}
		if (cutCheckOut.getSubmitDateFrom()!= null){
			sql += " and k.SubmitDate>= ? ";
			list.add(cutCheckOut.getSubmitDateFrom());
		}
		if (cutCheckOut.getSubmitDateTo() != null){ 
			sql += " and k.SubmitDate<= ? ";
			list.add(DateUtil.endOfTheDay(cutCheckOut.getSubmitDateTo()));
		}
		if (cutCheckOut.getCompleteDateFrom()!= null){
			sql += " and a.CompleteDate>= ? ";
			list.add(cutCheckOut.getCompleteDateFrom());
		}
		if (cutCheckOut.getCompleteDateTo() != null){ 
			sql += " and a.CompleteDate<= ? ";
			list.add(DateUtil.endOfTheDay(cutCheckOut.getCompleteDateTo()));
		}
		if (StringUtils.isNotBlank(cutCheckOut.getStatus())) {
			sql += " and k.Status in ("+SqlUtil.resetStatus(cutCheckOut.getStatus())+")";
		}
		if (StringUtils.isNotBlank(cutCheckOut.getIano())) {
			sql += " and a.IANo like ?";
			list.add("%"+cutCheckOut.getIano()+"%");
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.OrderName";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 入库列表
	 * 
	 * @param page
	 * @param cutCheckOut
	 * @return
	 */
	public Page<Map<String,Object>> goJqGridCheckIn(Page<Map<String,Object>> page, CutCheckOut cutCheckOut) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from tCutCheckIn where CheckOutNo=? " ;
		list.add(cutCheckOut.getNo());
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 入库明细列表
	 * 
	 * @param page
	 * @param cutCheckOut
	 * @return
	 */
	public Page<Map<String,Object>> goJqGridCheckInDtl(Page<Map<String,Object>> page, CutCheckOut cutCheckOut) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select b.IANo,d.Address,b.ItemCode,e.Descr ItemDescr,f.Descr FixAreaDescr, "
					+"b.Qty,b.CutFee,b.CutFee*b.Qty Total,g.NOTE CutTypeDescr, "
					+"a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog "
					+"from tCutCheckInDetail a  "
					+"left join tCutCheckOutDetail b on a.RefPK=b.PK "
					+"left join tItemApp c on b.IANo=c.No "
					+"left join tCustomer d on c.CustCode=d.Code "
					+"left join tItem e on b.ItemCode=e.Code "
					+"left join tFixArea f on b.FixAreaPK=f.PK "
					+"left join tXTDM g on b.CutType=g.CBM and g.ID='CUTTYPE' "
					+"where a.No=? " ;
		list.add(cutCheckOut.getNo());
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.Address";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 根据规格查切割类型和切割费
	 * @param cutCheckOut
	 * @return
	 */
	public List<Map<String, Object>> getCutTypeBySize(CutCheckOut cutCheckOut) {
		String sql = "select CutType+'-'+cast(FactCutFee as nvarchar(20))+'-'+a.AllowModify Code ,b.NOTE Descr "
					+"from tCutFeeSet a  "
					+"left join tXTDM b on a.CutType=b.CBM and b.ID='CUTTYPE'  "
					+"where a.Size=? and a.Expired='F' ";
		List<Map<String, Object>> list = this.findListBySql(sql,
				new Object[] { cutCheckOut.getSize()});
		return list;
	}
	
	/**
	 * 出库保存
	 * @param cutCheckOut
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result doSaveProc(CutCheckOut cutCheckOut) {
		Assert.notNull(cutCheckOut);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pCutCheckOut(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, cutCheckOut.getNo());
			call.setString(2, cutCheckOut.getM_umState());	
			call.setString(3, cutCheckOut.getCrtCzy());
			call.setString(4, cutCheckOut.getStatus());
			call.setTimestamp(5, cutCheckOut.getCrtDate()==null?null:new Timestamp(cutCheckOut.getCrtDate().getTime()));
			call.setTimestamp(6, cutCheckOut.getSubmitDate()==null?null:new Timestamp(cutCheckOut.getSubmitDate().getTime()));
			call.setTimestamp(7, cutCheckOut.getCompleteDate()==null?null:new Timestamp(cutCheckOut.getCompleteDate().getTime()));
			call.setString(8, cutCheckOut.getRemarks());
			call.setDouble(9, cutCheckOut.getAmount());
			call.setString(10, cutCheckOut.getLastUpdatedBy());
			call.setString(11, cutCheckOut.getCutCheckOutDetailJson());
			call.registerOutParameter(12, Types.INTEGER);
			call.registerOutParameter(13, Types.NVARCHAR);
			call.execute();
			result.setCode(String.valueOf(call.getInt(12)));
			result.setInfo(call.getString(13));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
		
	}
	
	/**
	 * 入库保存
	 * @param cutCheckOut
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result doCheckIn(CutCheckOut cutCheckOut) {
		Assert.notNull(cutCheckOut);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pCutCheckIn(?,?,?,?,?,?,?)}");
			call.setString(1, cutCheckOut.getNo());
			call.setString(2, cutCheckOut.getPks());	
			call.setString(3, cutCheckOut.getRemarks());
			call.setString(4, cutCheckOut.getLastUpdatedBy());
			call.registerOutParameter(5, Types.INTEGER);
			call.registerOutParameter(6, Types.NVARCHAR);
			call.setString(7, cutCheckOut.getCutCheckOutDetailJson());	
			call.execute();
			result.setCode(String.valueOf(call.getInt(5)));
			result.setInfo(call.getString(6));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
		
	}
}

