package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.InterfaceLog;

@SuppressWarnings("serial")
@Repository
public class InterfaceLogDao extends BaseDao {

	/**
	 * InterfaceLog分页信息
	 * 
	 * @param page
	 * @param interfaceLog
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, InterfaceLog interfaceLog) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from tInterfaceLog a where 1=1 ";

    	if (interfaceLog.getId() != null) {
			sql += " and a.id=? ";
			list.add(interfaceLog.getId());
		}
    	if (StringUtils.isNotBlank(interfaceLog.getClazz())) {
			sql += " and a.clazz=? ";
			list.add(interfaceLog.getClazz());
		}
    	if (StringUtils.isNotBlank(interfaceLog.getJavaFun())) {
			sql += " and a.javaFun=? ";
			list.add(interfaceLog.getJavaFun());
		}
    	if (interfaceLog.getDateFrom() != null){
			sql += " and a.requestTime>= ? ";
			list.add(interfaceLog.getDateFrom());
		}
		if (interfaceLog.getDateTo() != null){
			sql += " and a.requestTime<= ? ";
			list.add(interfaceLog.getDateTo());
		}
    	if (interfaceLog.getDateFrom() != null){
			sql += " and a.returnTime>= ? ";
			list.add(interfaceLog.getDateFrom());
		}
		if (interfaceLog.getDateTo() != null){
			sql += " and a.returnTime<= ? ";
			list.add(interfaceLog.getDateTo());
		}
    	if (StringUtils.isNotBlank(interfaceLog.getContent())) {
			sql += " and a.content=? ";
			list.add(interfaceLog.getContent());
		}
    	if (StringUtils.isNotBlank(interfaceLog.getReturnCode())) {
			sql += " and a.returnCode=? ";
			list.add(interfaceLog.getReturnCode());
		}
    	if (StringUtils.isNotBlank(interfaceLog.getReturnInfo())) {
			sql += " and a.returnInfo=? ";
			list.add(interfaceLog.getReturnInfo());
		}
    	if (StringUtils.isNotBlank(interfaceLog.getResponseContent())) {
			sql += " and a.responseContent=? ";
			list.add(interfaceLog.getResponseContent());
		}
    	if (StringUtils.isNotBlank(interfaceLog.getRequestContent())) {
			sql += " and a.requestContent=? ";
			list.add(interfaceLog.getRequestContent());
		}
    	if (StringUtils.isNotBlank(interfaceLog.getRequestIp())) {
			sql += " and a.requestIp=? ";
			list.add(interfaceLog.getRequestIp());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Id";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

}

