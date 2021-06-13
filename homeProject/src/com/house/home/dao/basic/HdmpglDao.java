package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.StringUtil;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.Hdmpgl;

@SuppressWarnings("serial")
@Repository
public class HdmpglDao extends BaseDao{

	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, Hdmpgl hdmpgl,UserContext uc) {
		List<Object> list = new ArrayList<Object>();		
		String sql = " select * from ( select a.PK, a.ActNo, b.ActName, a.TicketNo,dt1.Desc1 DesingManDept2Descr, a.Status, xt.NOTE StatusDescr, a.Descr, a.Address, a.DesignMan, e1.NameChi DesignManDescr, a.BusinessMan," +
				" e2.NameChi BusinessManDescr,dt2.Desc1 BusinessManDept2Descr, a.ProvideDate, a.SignDate, a.LastUpdate, a.LastUpdatedBy, a.Expired, a.ActionLog, a.ProvideType, xt2.NOTE ProvideTypeDescr," +
				" a.ProvideCZY, czy.ZWXM ProvideCZYDescr, a.BusiManName,c.Desc1 Department2Descr, a.BusiManPhone, case when a.ProvideType = '1' then e2.NameChi  else a.BusiManName " +
				" end RealBusiManName from    tTicket a " +
				" left join tActivity b on a.ActNo = b.No " +
				" left join tXTDM xt on a.Status = xt.CBM and xt.ID = 'TICKETSTATUS' " +
				" left join tEmployee e1 on a.DesignMan = e1.Number " +
				" left join tEmployee e2 on a.BusinessMan = e2.Number " +
				" left join tDepartment2 dt1 on e1.Department2=dt1.Code " +
				" left join tDepartment2 dt2 on e2.Department2=dt2.Code " +
				" left join tCZYBM czy on czy.CZYBH = a.ProvideCZY " +
				" left join tEmployee e3 on czy.EMNum = e3.Number" +
				" left join tXTDM xt2 on a.ProvideType = xt2.CBM and xt2.ID = 'TICKETPROVIDE' " +
				" left join (select a.PK,c.Desc1 from tTicket  a " +
				" 			left join tEmployee b on a.ProvideCZY=b.Number " +
				"			left join tDepartment2 c on b.Department2=c.Code " +
				"			where ProvideType='1' )c on a.PK=c.PK " +
				" where   1=1   " +
				" ";
			if("1".equals(uc.getCustRight())){
				sql+=" and (a.ProvideCZY= ?  or a.ProvideCZY='' or a.ProvideCZY is null) ";
				list.add(uc.getCzybh());
			}else if("2".equals(uc.getCustRight())){
				sql+="  and (exists (select 1 from tCZYDept in_a where (in_a.Department1=e3.Department1)" +
						"  and (in_a.Department2=e3.Department2 or in_a.Department2='' or in_a.Department2 is null)" +
						"  and (in_a.Department3=e3.Department3 or in_a.Department3='' or in_a.Department3 is null)" +
						"  and in_a.CZYBH=? " +
						"  ) or a.ProvideCZY='' or a.ProvideCZY is null) ";
				list.add(uc.getCzybh());
			}
			
			if("2".equals(uc.getCzylb())){
				sql+=" and isnull(a.provideType,'') <>'1' ";
			}
			if (StringUtils.isBlank(hdmpgl.getExpired()) || "F".equals(hdmpgl.getExpired())) {
				sql += " and a.Expired='F' ";
			}
			if(StringUtils.isNotBlank(hdmpgl.getBusinessMan())){
				sql+=" and a.businessman = ? ";
				list.add(hdmpgl.getBusinessMan());
			}
			if(StringUtils.isNotBlank(hdmpgl.getActNo())){
				sql+=" and a.actNo=?";
				list.add(hdmpgl.getActNo());
			}
			if(StringUtils.isNotBlank(hdmpgl.getProvideCZY())){
				sql+=" and a.provideCzy = ? ";
				list.add(hdmpgl.getProvideCZY());
			}
			if(StringUtils.isNotBlank(hdmpgl.getProvideType())){
				sql+=" and a.provideType= ? ";
				list.add(hdmpgl.getProvideType());
			}
			if(StringUtils.isNotBlank(hdmpgl.getActDescr())){
				sql+=" and b.actName like ?";
				list.add("%"+hdmpgl.getActDescr()+"%");
			}
			if(StringUtils.isNotBlank(hdmpgl.getTicketNo())){
				sql+=" and a.ticketNo=?";
				list.add(hdmpgl.getTicketNo());
			}
			if(StringUtils.isNotBlank(hdmpgl.getStatus())){
				sql+=" and a.status=?";
				list.add(hdmpgl.getStatus());
			}
			if(hdmpgl.getDateFrom()!=null){
				sql+=" and a.provideDate>= ? ";
				list.add(hdmpgl.getDateFrom());
			}
			if(hdmpgl.getDateTo()!=null){
				sql+=" and a.provideDate <dateAdd(d,1,?)";
				list.add(hdmpgl.getDateTo());
			}
			if(hdmpgl.getSignDateFrom()!=null){
				sql+=" and a.signDate>= ? ";
				list.add(hdmpgl.getSignDateFrom());
			}
			if(hdmpgl.getSignDateTo()!=null){
				sql+=" and a.signDate< dateAdd(d,1,?)";
				list.add(hdmpgl.getSignDateTo());
			}
			if(StringUtils.isNotBlank(hdmpgl.getDepartment1())){
				sql+=" and (e1.Department1 =? or e2.Department1 = ? )";
				list.add(hdmpgl.getDepartment1());
				list.add(hdmpgl.getDepartment1());
			}
			if(StringUtils.isNotBlank(hdmpgl.getPlanSupplType())){
				sql+=" and a.PlanSupplType like ? ";
				list.add("%" +hdmpgl.getPlanSupplType()+ "%");
			}
			if(StringUtils.isNotBlank(hdmpgl.getDepartment2())){
				sql+="  and (e1.Department2 =? or e2.Department2 =?) ";
				list.add(hdmpgl.getDepartment2());
				list.add(hdmpgl.getDepartment2());
			}
			if(StringUtils.isNotBlank(hdmpgl.getAddress())){
				sql+=" and a.address like ? ";
				list.add("%"+hdmpgl.getAddress()+"%");
			}
			if(StringUtils.isNotBlank(hdmpgl.getPhone())){
				sql+=" and a.Phone=?";
				list.add(hdmpgl.getPhone());
			}
			if (StringUtils.isNotBlank(page.getPageOrderBy())) {
				sql += ")a  order by a." + page.getPageOrderBy() + " "
						+ page.getPageOrder();
			} else {
				sql += ")a order by a.LastUpdate desc ";
			}
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 下定明细
	 * 
	 * @param page
	 * @param hdmpgl
	 * @return
	 */
	public Page<Map<String, Object>> findDetail_OrderJqGrid(
			Page<Map<String, Object>> page, Hdmpgl hdmpgl) {
		List<Object> list = new ArrayList<Object>();		
		String sql = " select * from ( select c.code SupplCode,a.actName,e.descr custDescr,e.address ,b.ticketNo,b.lastUpdate,b.lastUpdatedBy,d.zwxm,c.descr supplDescr,x1.note supplTypeDescr  " +
				" from dbo.tActivity a " +
				" left join tActivitySet b on a.No=b.ActNo " +
				" left join tSupplier c on c.code=b.supplCode  " +
				" left join tczybm d on d.czybh = b.lastUpdatedBy  " +
				" left join tTicket e on e.ticketNo =b.ticketNo " +
				" left join tXtdm x1 on x1.cbm=b.supplType and x1.ID='ACTSPLTYPE' " +
				" where 1=1 ";
		if(StringUtils.isNotBlank(hdmpgl.getActNo())){
			sql+=" and b.ActNo = ?  ";
	 		list.add(hdmpgl.getActNo());
	 	}
		 
		if(StringUtils.isNotBlank(hdmpgl.getTicketNo())){
	 		sql+=" and b.TicketNo=? ";
	 		list.add(hdmpgl.getTicketNo());
	 	}
			
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " )a  order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ")a order by a.SupplCode  ";
		}
	
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> findDetail_giftJqGrid(
			Page<Map<String, Object>> page, Hdmpgl hdmpgl) {
		List<Object> list = new ArrayList<Object>();		
		String sql = "select * from ( select  a.actName,b.qty ,b.ticketNo,b.lastUpdate ,b.lastUpdatedBy,d.zwxm,c.descr itemDescr,e.descr custdescr,e.address " +
				" ,f.Descr uomDescr " +
				" from tActivity a " +
				" left join tActivityGift b on a.No=b.ActNo " +
				" left join tItem c on c.code = b.ItemCode " +
				" left join tUom f on f.code=c.Uom " +
				" left join tczybm d on d.czybh = b.lastUpdatedBy " +
				" left join tTicket e on e.ticketNo =b.ticketNo " +
				" where 1=1 ";
		if(StringUtils.isNotBlank(hdmpgl.getActNo())){
			sql+=" and b.ActNo = ?  ";
	 		list.add(hdmpgl.getActNo());
	 	}
		 
		if(StringUtils.isNotBlank(hdmpgl.getTicketNo())){
	 		sql+=" and b.TicketNo=? ";
	 		list.add(hdmpgl.getTicketNo());
	 	}
			
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " ) a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += " ) a order by a.LastUpdate desc ";
		}
	
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 
	 *	下定明细查询 
	 * 
	 * @param page
	 * @param hdmpgl
	 * @return
	 */
	public Page<Map<String, Object>> findOrderDetailJqGrid(
			Page<Map<String, Object>> page, Hdmpgl hdmpgl) {
		List<Object> list = new ArrayList<Object>();		
		String sql = "select * from ( select e.zwxm provideDescr,b.ProvideType,b.ProvideDate  ,c.ActName,a.TicketNo,b.Descr custDescr,b.Address ,d.Descr supplDescr," +
				" x1.NOTE supplTypeDescr ,g.Desc1 department1descr ,h.Desc2 department2Descr,a.LastUpdate orderDate,x2.note provideTypeDescr " +
				" from tActivitySet a " +
				" left join dbo.tTicket b on b.TicketNo=a.TicketNo" +
				" left join dbo.tActivity c on c.No=a.ActNo" +
				" left join tsupplier d on d.Code=a.SupplCode" +
				" left join dbo.txtdm x1 on x1.cbm=a.SupplType and x1.ID='ACTSPLTYPE'" +
				" left join dbo.tXTDM x2 on x2.cbm=b.ProvideType and x2.id='TICKETPROVIDE'" +
				" left join dbo.tCZYBM e on e.CZYBH=b.ProvideCZY " +
				" left join temployee f on f.Number=e.CZYBH" +
				" left join dbo.tDepartment1 g on g.Code=f.Department1" +
				" left join dbo.tDepartment2 h on h.Code=f.Department2" +
				" where 1=1 and a.expired = 'F' ";
		if(StringUtils.isNotBlank(hdmpgl.getActNo())){
			sql+=" and b.ActNo = ?  ";
	 		list.add(hdmpgl.getActNo());
	 	}
		 
		if(StringUtils.isNotBlank(hdmpgl.getTicketNo())){
	 		sql+=" and b.TicketNo=? ";
	 		list.add(hdmpgl.getTicketNo());
	 	}
		if(StringUtils.isNotBlank(hdmpgl.getProvideCZY())){
			sql+=" and b.provideCZY = ? ";
			list.add(hdmpgl.getProvideCZY());
			
		}
		if(StringUtils.isNotBlank(hdmpgl.getDepartment1())){
			sql+=" and g.code = ? ";
			list.add(hdmpgl.getDepartment1());
		}
		if(StringUtils.isNotBlank(hdmpgl.getDepartment2())){
			sql+=" and h.code = ? ";
			list.add(hdmpgl.getDepartment2());
		}
		if(StringUtils.isNotBlank(hdmpgl.getProvideType())){
			sql+=" and b.provideType = ? ";
			list.add(hdmpgl.getProvideType());
		}
		if(StringUtils.isNotBlank(hdmpgl.getSupplCode())){
			sql+=" and d.code= ? ";
			list.add(hdmpgl.getSupplCode());
		}
			
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " ) a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += " ) a order by a.ticketNo  ";
		}
	
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 查找礼品发放明细
	 * 
	 * @param page
	 * @param hdmpgl
	 * @return
	 */
	public Page<Map<String, Object>> findGiftDetailJqGrid(
			Page<Map<String, Object>> page, Hdmpgl hdmpgl) {
		List<Object> list = new ArrayList<Object>();		
		String sql = "select * from (select b.ActName,a.TicketNo,a.qty,c.descr custDescr,c.Address,a.ItemCode,i.Descr itemDescr,d.ZWXM ,f.Desc1 department1Descr,g.Desc1 department2Descr " +
				" ,h.namechi businessManDescr,c.ProvideDate ,x1.NOTE provideTypeDescr,j.desc1 businessdep " +
				" ,k.Descr uomDescr " +
				" from dbo.tActivityGift  a " +
				" left join dbo.tActivity b on b.No=a.ActNo" +
				" left join dbo.tTicket c on c.TicketNo=a.TicketNo " +
				" left join dbo.tCZYBM d on d.CZYBH=c.ProvideCZY " +
				" left join dbo.tEmployee e on e.number =d.CZYBH " +
				" left join dbo.tDepartment1 f on f.Code=e.Department1" +
				" left join dbo.tDepartment2 g on g.Code=e.Department2" +
				" left join dbo.tEmployee h on h.Number=c.businessman" +
				" left join dbo.tItem i on i.code = a.ItemCode " +
				" left join tUom k on k.code=i.Uom " +
				" left join dbo.tXTDM x1 on x1.cbm=c.ProvideType and x1.id='TICKETPROVIDE' " +
				" left join tdepartment2 j on j.code = h.department2 " +
				" where 1=1 ";
		if(StringUtils.isNotBlank(hdmpgl.getActNo())){
			sql+=" and b.No = ?  ";
	 		list.add(hdmpgl.getActNo());
	 	}
		if (StringUtils.isBlank(hdmpgl.getExpired())
				|| "F".equals(hdmpgl.getExpired())) {
			sql += " and i.Expired='F' ";
		}
		if(StringUtils.isNotBlank(hdmpgl.getTicketNo())){
	 		sql+=" and b.TicketNo=? ";
	 		list.add(hdmpgl.getTicketNo());
	 	}
		if(StringUtils.isNotBlank(hdmpgl.getProvideCZY())){
			sql+=" and c.provideCZY = ? ";
			list.add(hdmpgl.getProvideCZY());
			
		}
		if(StringUtils.isNotBlank(hdmpgl.getDepartment1())){
			sql+=" and f.code = ? ";
			list.add(hdmpgl.getDepartment1());
		}
		if(StringUtils.isNotBlank(hdmpgl.getDepartment2())){
			sql+=" and g.code = ? ";
			list.add(hdmpgl.getDepartment2());
		}
		if(StringUtils.isNotBlank(hdmpgl.getProvideType())){
			sql+=" and c.provideType = ? ";
			list.add(hdmpgl.getProvideType());
		}
		if(StringUtils.isNotBlank(hdmpgl.getSupplCode())){
			sql+=" and d.code= ? ";
			list.add(hdmpgl.getSupplCode());
		}
		if(StringUtils.isNotBlank(hdmpgl.getItemCode())){
			sql+=" and i.code = ? ";
			list.add(hdmpgl.getItemCode());
			
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " )a  order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.TicketNo  ";
		}
	
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	
	
	
	/**
	 * 收回门票
	 * 
	 * */
	public void doCallBack(Integer pk,String lastUpdatedBy){
		
			String sql = " update tTicket set status='1',descr=null,address=null,provideDate=null,lastupdate=getDate(),lastUpdatedBy=?,provideType=null," +
					" provideCZY=null,planSupplType='',busiManName=null,busiManPhone=null,businessMan=null,designMan=null,phone=null where pk=?";
			
			this.executeUpdateBySql(sql, new Object[]{lastUpdatedBy,pk});
	}
	
	
	/**
	 * 门票生成
	 * 
	 * */
	public void doCreate(String actNo,Integer length,String prefix , Integer ticketNum,String lastUpdatedBy){
		
		String sql = " insert into tTicket(ActNo,TicketNo,Status,LastUpdate,LastUpdatedBy,Expired,ActionLog) " +
				"values(?,dbo.fGetSerialNo(?,?,?),'1',getdate(),?,'F','ADD') ";
		
		this.executeUpdateBySql(sql, new Object[]{actNo,prefix,ticketNum,length,lastUpdatedBy});
	}
	
	/**
	 * 生成门票判断是否重复
	 *  
	 *  */ 
	public boolean isRepetition(String prefix,Integer numFrom,Integer numTo,Integer length,String actNo){
		boolean result = true;
		String sql = " select * from tTicket where TicketNo>=dbo.fGetSerialNo(?,?,?) and ActNo=? " +
				" and TicketNo<=dbo.fGetSerialNo(?,?,?) and len(TicketNo)=? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{prefix,numFrom,length,actNo,prefix,numTo,length,length});
		if(list != null && list.size() > 0){
			result = false;
		}
		return result;
	}
	
	/**
	 * 删除门票，判断是否已发放或签到
	 * 
	 * */
	public boolean isSend(String prefix,Integer numFrom,Integer numTo,Integer length,String actNo){
		boolean result = true;
		String sql = " select * from tTicket where TicketNo>=dbo.fGetSerialNo(?,?,?) and ActNo=? " +
				" and (status='2' or status='3') and TicketNo<=dbo.fGetSerialNo(?,?,?)  ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{prefix,numFrom,length,actNo,prefix,numTo,length});
		if(list != null && list.size() > 0){
			result = false;
		}
		return result;
	}
	
	public boolean hasTicket(String prefix,Integer numFrom,Integer numTo,Integer length,String actNo){
		boolean result = false;
		String sql = " select a.TicketNo,b.Times,b.Sites,b.ActName" +
				" from tTicket a inner join tActivity b on a.ActNo=b.No  " +
				" where a.TicketNo>=dbo.fGetSerialNo(?,?,?) and a.ActNo=?" +
				" and a.TicketNo<=dbo.fGetSerialNo(?,?,?) and a.Expired='F'";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{prefix,numFrom,length,actNo,prefix,numTo,length});
		if(list != null && list.size() > 0){
			result = true;
		}
		return result;
	}
	
	public void doDeleteTickets(String prefix,Integer numFrom,Integer numTo,Integer length,String actNo){
		
		String sql = " delete from tTicket where actNo= ? and" +
				" ticketNo>=dbo.fGetSerialNo(?,?,?) " +
				" and ticketNo<=dbo.fGetSerialNo(?,?,?) ";
		
		this.executeUpdateBySql(sql, new Object[]{actNo,prefix,numFrom,length,prefix,numTo,length});
	}
	
	/**
	 * 门票签到
	 * 
	 * */
	public void doSign(Integer pk,String lastUpdatedBy,String status){
		
		String sql = " update tTicket set Status='3', SignDate=getdate(),LastUpdate=getdate(),LastUpdatedBy= ? ,ActionLog='EDIT' " +
				" where status=? and pk= ? ";
		
		this.executeUpdateBySql(sql, new Object[]{lastUpdatedBy,status,pk});
	}
	
	public String  getPk(String actNo,String ticketNo){
		String sql = " select PK from tTicket where actNo=? and ticketNo = ? and status='1' ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{actNo,ticketNo});
		
		if(list != null && list.size() > 0){
			return String.valueOf(list.get(0).get("PK"));
		}
		return null;
	}
	
	public Map<String, Object>  getValidActNum(){
		String sql = " select * from tActivity where expired='F' and getDate() between begindate and endDate  ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{});
		
		if(list != null && list.size()==1){
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 业主电话是否已存在
	 * @param actNo 活动编号
	 * @param phone 业主电话
	 */
	public boolean isExistPhone(String actNo, String phone) {
		String sql = " select 1 from tTicket a where a.ActNo=? and a.Phone=? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{actNo, phone});
		
		if(list != null && list.size()>0){
			return true;
		}
		return false; 
	}
	
}
