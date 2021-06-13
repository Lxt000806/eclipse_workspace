package com.house.home.dao.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.project.PrjProg;
import com.house.home.entity.project.PrjProgPhoto;

@SuppressWarnings("serial")
@Repository
public class PrjProgPhotoDao extends BaseDao {

	/**
	 * PrjProgPhoto分页信息
	 * 
	 * @param page
	 * @param prjProgPhoto
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, PrjProgPhoto prjProgPhoto) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select a.*,s1.note prjitemdescr,case when a.isPushCust ='1' then '可见' else '不可见' end ispushcustdescr  from tPrjProgPhoto a " +
				" left join tXTDM s1 on s1.cbm=a.prjItem and s1.id='PRJITEM' " +
				"where 1=1 ";

    	if (prjProgPhoto.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(prjProgPhoto.getPk());
		}
    	if (StringUtils.isNotBlank(prjProgPhoto.getCustCode())) {
			sql += " and a.CustCode=? ";
			list.add(prjProgPhoto.getCustCode());
		}
    	if (StringUtils.isNotBlank(prjProgPhoto.getPrjItem())) {
			sql += " and a.PrjItem=? ";
			list.add(prjProgPhoto.getPrjItem());
		}
    	if (StringUtils.isNotBlank(prjProgPhoto.getType())) {
			sql += " and a.Type=? ";
			list.add(prjProgPhoto.getType());
		}
    	if (StringUtils.isNotBlank(prjProgPhoto.getPhotoName())) {
			sql += " and a.PhotoName=? ";
			list.add(prjProgPhoto.getPhotoName());
		}
    	if (StringUtils.isNotBlank(prjProgPhoto.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(prjProgPhoto.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(prjProgPhoto.getExpired()) || "F".equals(prjProgPhoto.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(prjProgPhoto.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(prjProgPhoto.getActionLog());
		}
    	if (prjProgPhoto.getDateFrom() != null){
			sql += " and a.LastUpdate>= ? ";
			list.add(prjProgPhoto.getDateFrom());
		}
		if (prjProgPhoto.getDateTo() != null){
			sql += " and a.LastUpdate<= ? ";
			list.add(prjProgPhoto.getDateTo());
		}
		if(StringUtils.isNotBlank(prjProgPhoto.getRefNo())){
			sql+=" and a.refNo = ? ";
			list.add(prjProgPhoto.getRefNo());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Pk";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * PrjProgPhoto分页信息
	 * 
	 * @param page
	 * @param prjProgPhoto
	 * @return
	 */
	public Page<Map<String,Object>> findPrjProgPhotoPageBySql(Page<Map<String,Object>> page, PrjProgPhoto prjProgPhoto) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select  a.PK,a.CustCode,b.Address,b.ProjectMan,d.NameChi ProjectManDescr, c.NOTE PrjItemDescr,a.LastUpdate from tPrjProgPhoto a " +
				" left join tCustomer b on a.CustCode=b.Code " +
				" left join tXTDM c on a.PrjItem = c.CBM and c.ID = 'PRJITEM' " +
				" left join tEmployee d on b.ProjectMan=d.Number" +
				" where 1=1 ";
		
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ") a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ") a order by a.LastUpdate desc";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
		
	}
	
	/**
	 * 查看页面 施工图片 分页信息
	 * 
	 * @param page
	 * @param prjProgPhoto
	 * @return
	 */
	public Page<Map<String,Object>> findConstructionPicturePageBySql(Page<Map<String,Object>> page, PrjProgPhoto prjProgPhoto) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select max(a.pk) pk ,s1.Note prjDescr,a.custcode,a.prjItem ,a.type,b.Planbegin  from tPrjProgPhoto a " +
				" left join tXTDM s1 on s1.CBM = a.prjitem and s1.ID='PRJITEM' "+
				" left join tprjprog b on b.CustCode=a.CustCode and b.PrjItem=a.PrjItem " +
				" where 1=1 ";
		
		if(StringUtils.isNotBlank(prjProgPhoto.getCustCode())){
			sql+=" and a.custCode=? ";
			list.add(prjProgPhoto.getCustCode());
			
		}
		if(StringUtils.isNotBlank(prjProgPhoto.getType())){
			sql+=" and a.type = ?";
			list.add(prjProgPhoto.getType());
			
		}
		if(StringUtils.isNotBlank(prjProgPhoto.getPrjItem())){
			sql+=" and a.prjItem = ?";
			list.add(prjProgPhoto.getPrjItem());
			
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " group by a.custCode,a.type,a.prjitem  order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += "  group by s1.note,a.custCode,a.type,a.prjitem,b.Planbegin  order by b.Planbegin ";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
		
	}
	
	
	
	
	
	
	/**
	 * PrjProgPhoto分页信息
	 * 
	 * @param page
	 * @param prjProgPhoto
	 * @return
	 */
	public Page<Map<String,Object>> findPrjPageBySql(Page<Map<String,Object>> page, PrjProgPhoto prjProgPhoto) {
		List<Object> list = new ArrayList<Object>();
		String sql = "  select MAX(a.PK) maxpk,a.type,ppc.address addresscheck ,pp.Date confrimDate,pp.address addressconfirm," +
				"a.custcode,a.prjitem,ppt.photoName,s1.note prjdescr " +
				" ,ppc.Date checkDate " +
				"from tPrjProgPhoto a " +
				"left join  tXTDM s1 on s1.CBM = a.PrjItem and s1.ID = 'PRJITEM' " +
				" left join  tPrjProgPhoto ppt on ppt.pk= a.pk "+
				" left join tPrjProgCheck ppc on ppc.no = a.refno " +
				" left join tPrjProgConfirm pp on pp.No= a.refno"+
				" where 1=1 and a.Expired='F'"  ;
					
		
		if (StringUtils.isNotBlank(prjProgPhoto.getCustCode())) {
			sql += " and a.CustCode =? ";
			list.add(prjProgPhoto.getCustCode());
		}
		
		if (StringUtils.isNotBlank(prjProgPhoto.getPrjItem())) {
			sql += " and a.prjItem =? ";
			list.add(prjProgPhoto.getPrjItem());
		}
		if(StringUtils.isNotBlank(prjProgPhoto.getRefNo())){
			sql+=" and a.refNo=? ";
			list.add(prjProgPhoto.getRefNo());
		}
		
		if (StringUtils.isNotBlank(prjProgPhoto.getType())) {
			sql += " and a.type =?  ";
			list.add(prjProgPhoto.getType());
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += "   group by a.CustCode,a.prjitem  order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += "  group by a.CustCode,a.prjitem ,a.type,ppt.photoname,pp.Date,ppc.Date,pp.address,ppc.address,s1.note order by ppc.Date ";
		}
		
		return this.findPageBySql(page, sql, list.toArray());

		
	}
	
	/**
	 * PrjProgPhoto分页信息
	 * 
	 * @param page
	 * @param prjProgPhoto
	 * @return
	 */
	public Page<Map<String,Object>> findYsPrjPageBySql(Page<Map<String,Object>> page, PrjProgPhoto prjProgPhoto) {
		List<Object> list = new ArrayList<Object>();
		String sql = "  select MAX(a.PK) maxpk,a.type,ppc.address addresscheck ,pp.Date confrimDate,pp.address addressconfirm," +
				"a.custcode,a.prjitem,ppt.photoName,s1.note prjdescr " +
				" ,ppc.Date checkDate " +
				"from tPrjProgPhoto a " +
				"left join  tXTDM s1 on s1.CBM = a.PrjItem and s1.ID = 'PRJITEM' " +
				" left join  tPrjProgPhoto ppt on ppt.pk= a.pk "+
				" left join tPrjProgCheck ppc on ppc.no = a.refno " +
				" left join tPrjProgConfirm pp on pp.No= a.refno"+
				" where 1=1 and a.Expired='F'"  ;
					
		
		if (StringUtils.isNotBlank(prjProgPhoto.getCustCode())) {
			sql += " and a.CustCode =? ";
			list.add(prjProgPhoto.getCustCode());
		}
		
		if (StringUtils.isNotBlank(prjProgPhoto.getPrjItem())) {
			sql += " and a.prjItem =? ";
			list.add(prjProgPhoto.getPrjItem());
		}
		
		if (StringUtils.isNotBlank(prjProgPhoto.getType())) {
			sql += " and a.type =?  ";
			list.add(prjProgPhoto.getType());
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += "   group by a.CustCode,a.prjitem  order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += "  group by a.CustCode,a.prjitem ,a.type,ppt.photoname,pp.Date,ppc.Date,pp.address,ppc.address,s1.note order by ppc.Date ";
		}
		
		return this.findPageBySql(page, sql, list.toArray());

		
	}
	
	/**
	 * PrjProgPhoto分页信息
	 * 
	 * @param page
	 * @param prjProgPhoto
	 * @return
	 */
	public Page<Map<String,Object>> findXjPrjPageBySql(Page<Map<String,Object>> page, PrjProgPhoto prjProgPhoto) {
		List<Object> list = new ArrayList<Object>();
		String sql = "  select a.refno,a.type,a.photoname,a.prjitem from tPrjProgPhoto a  where 1=1"  ;
					
		if(StringUtils.isNotBlank(prjProgPhoto.getRefNo())){
			sql+=" and a.refNo=? ";
			list.add(prjProgPhoto.getRefNo());
			
		}
		if (StringUtils.isNotBlank(prjProgPhoto.getType())) {
			sql += " and a.Type in " + "('"+prjProgPhoto.getType().replace(",", "','" )+ "')";
		}
		
		if(StringUtils.isNotBlank(prjProgPhoto.getPrjItem())){
			sql+=" and a.PrjItem = ? ";
			list.add(prjProgPhoto.getPrjItem());
			
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += "    order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += "  order by a.RefNo";
		}
		
		return this.findPageBySql(page, sql, list.toArray());

		
	}
	
	
	public List<Map<String,Object>> getPhotoList(String custCode, String prjItem) {
		String sql = "select photoName from tPrjProgPhoto where custCode=? and prjItem=? and type='1'";
		
		return this.findBySql(sql, new Object[]{custCode,prjItem});
	}

	public List<String> getPhotoListByTypeAndRefNo(String type, String no) {
		
		String sql = "select photoname from tPrjProgPhoto where type=? and no=?";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{type,no});
		List<String> retList = new ArrayList<String>();
		if (list!=null && list.size()>0){
			for (Map<String,Object> map: list){
				retList.add((String) map.get("photoname"));
			}
		}
		return retList;
	}

	public List<String> getPhotoListByRefNo(String id,String type) {
		String sql = "select photoname from tPrjProgPhoto where refNo=? ";
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		if (StringUtils.isNotBlank(type)){
			sql += " and type=? ";
			list = this.findBySql(sql, new Object[]{id,type});
		}else{
			list = this.findBySql(sql, new Object[]{id});
		}
		List<String> retList = new ArrayList<String>();
		if (list!=null && list.size()>0){
			for (Map<String,Object> map: list){
				retList.add((String) map.get("photoname"));
			}
		}
		return retList;
	}

	public PrjProgPhoto getByPhotoName(String id) {
		String hql = "from PrjProgPhoto where photoName=?";
		List<PrjProgPhoto> list = this.find(hql, new Object[]{id});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	public void updatePhotoPustStatus(PrjProgPhoto prjProgPhoto) {
		if(StringUtils.isNotBlank(prjProgPhoto.getCustCode()) && StringUtils.isNotBlank(prjProgPhoto.getRefNo())){
			String sql = " update tPrjProgPhoto set IsPushCust = b.IsPushCust from tPrjProgPhoto a " +
					" left join tPrjProgConfirm b on a.RefNo = b.No " +
					" where a.CustCode= ? and a.Type='2' and b.IsPushCust = '0' and b.No = ? ";
			this.executeUpdateBySql(sql, new Object[]{prjProgPhoto.getCustCode(),prjProgPhoto.getRefNo()});
		}
	}

}

