package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.SysLog;

@SuppressWarnings("serial")
@Repository
public class SysLogDao extends BaseDao {

	/**
	 * SysLog分页信息
	 * 
	 * @param page
	 * @param sysLog
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, SysLog sysLog) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select a.*,b.MENU_NAME menuName from tSysLog a left join TSYS_MENU b on "
			+"substring(a.requestUrl,charindex('/admin/',a.requestUrl)+6,charindex('/',substring(a.requestUrl,charindex('/admin/',a.requestUrl)+7,len(a.requestUrl)))+1)="
			+"substring(b.MENU_URL,charindex('/admin/',b.menu_url)+6,charindex('/',substring(b.MENU_URL,charindex('/admin/',b.menu_url)+7,len(b.MENU_URL)))+1) "
			+"where len(b.MENU_URL)>0 ";

    	if (sysLog.getId() != null) {
			sql += " and a.id=? ";
			list.add(sysLog.getId());
		}
    	if (StringUtils.isNotBlank(sysLog.getTitle())) {
			sql += " and a.title=? ";
			list.add(sysLog.getTitle());
		}
    	if (StringUtils.isNotBlank(sysLog.getType())) {
			sql += " and a.type=? ";
			list.add(sysLog.getType());
		}
    	if (StringUtils.isNotBlank(sysLog.getAppType())) {
			sql += " and a.appType=? ";
			list.add(sysLog.getAppType());
		}
    	if (StringUtils.isNotBlank(sysLog.getRequestUrl())) {
			sql += " and a.requestUrl like ? ";
			list.add("%/admin/"+sysLog.getRequestUrl()+"/%");
		}else{
			sql += " and a.requestUrl like '%/admin/%' ";
		}
    	if (StringUtils.isNotBlank(sysLog.getUserAgent())) {
			sql += " and a.userAgent=? ";
			list.add(sysLog.getUserAgent());
		}
    	if (StringUtils.isNotBlank(sysLog.getMethod())) {
			sql += " and a.method=? ";
			list.add(sysLog.getMethod());
		}
    	if (StringUtils.isNotBlank(sysLog.getParams())) {
			sql += " and a.params like ? ";
			list.add("%"+sysLog.getParams()+"%");
		}
    	if (StringUtils.isNotBlank(sysLog.getDescription())) {
			sql += " and a.description=? ";
			list.add(sysLog.getDescription());
		}
    	if (StringUtils.isNotBlank(sysLog.getRemoteAddr())) {
			sql += " and a.remoteAddr=? ";
			list.add(sysLog.getRemoteAddr());
		}
    	if (StringUtils.isNotBlank(sysLog.getOperId())) {
			sql += " and a.operId=? ";
			list.add(sysLog.getOperId());
		}
    	if (sysLog.getDateFrom() != null){
			sql += " and a.operDate>=CONVERT(VARCHAR(10),?,120) ";
			list.add(sysLog.getDateFrom());
		}
		if (sysLog.getDateTo() != null){
			sql += " and a.operDate<CONVERT(VARCHAR(10),dateadd(dd,1,?),120) ";
			list.add(sysLog.getDateTo());
		}
    	if (StringUtils.isNotBlank(sysLog.getClazz())) {
			sql += " and a.clazz=? ";
			list.add(sysLog.getClazz());
		}
    	if (StringUtils.isNotBlank(sysLog.getResponseContent())) {
			sql += " and a.responseContent=? ";
			list.add(sysLog.getResponseContent());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Id desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> findAppPageBySql(Page<Map<String,Object>> page, SysLog sysLog) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select  a.OperId,max(a.OperDate) OperDate ,b.department2,b.NameChi OperDescr,d.Desc1 Department2Descr from tSysLog a " +
				"inner join tCZYBM c on c.CZYBH=a.OperId " +
				" left join tEmployee b on b.Number=a.OperId " +
				" left join tDepartment2 d on d.Code= b.Department2 " +
				" where b.NameChi <>''" +
				"Group by a.OperId ,b.NameChi ,b.department2,d.Desc1 " ;
		
		if (StringUtils.isNotBlank(sysLog.getDepartment2())) {
			sql += " and b.department2= ? ";
			list.add(sysLog.getDepartment2());
		}
		
		if (StringUtils.isNotBlank(sysLog.getProjectMan())) {
			sql += " and a.OperId = ? ";
			list.add(sysLog.getProjectMan());
		}
		
		if (sysLog.getDateFrom() != null){
			sql += " and a.operDate>=? ";
			list.add(sysLog.getDateFrom());
		}
		
		if (sysLog.getDateTo() != null){
			sql += " and a.operDate<=? ";
			list.add(sysLog.getDateTo());
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.OperId desc";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	
}

